import java.io.*;
import java.util.*;

public class ClientList extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long TIME_LD      = 5000;
  private static final long TIME_LOCK    = 100;
  private static final long FIGHT_TICK   = 100;
  private static final long ANIMATE_TICK = 1000;
  private static long TIME_TICK          = getRandomInt(55000, 75000);

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static long previousTime = System.currentTimeMillis();
  private static long ldTimer      = 0;
  private static long tickTimer    = 0;
  private static long lockTimer    = 0;
  private static long fightTimer   = 0;
  private static long animateTimer = 0;

  private static ArrayList list         = new ArrayList();
  private static ArrayList linkdeadList = new ArrayList();
  private static ArrayList removeList   = new ArrayList();

  private static Client currentClient;
  private static int listSize;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int getSize()                     { return list.size();         }
  public static void addClient(Client newClient)  { list.add(newClient);        }
  public static void removeClient(int index)      { list.remove(index);         }
  public static void addLinkdead(Client ldClient) { linkdeadList.add(ldClient); }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void removeClient(Client remClient)

  {
    if (!list.contains(remClient)) return;
    int index = list.indexOf(remClient);
    list.remove(index);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Account findAccount(String accountName)

  {
    int size = ClientList.getSize();

    for (int i=0; i<size; i++)
      if (getClient(i).getAccount().getUsername().equalsIgnoreCase(accountName))
        return getClient(i).getAccount();

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Client findClientExclude(Client C, String accountName)

  {
    int size = ClientList.getSize();

    for (int i=0; i<size; i++)
      if (C != getClient(i))
      if (getClient(i).getAccount().getUsername().equalsIgnoreCase(accountName))
        return getClient(i);

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Client findClientObject(String accountName)

  {
    int index = findClient(accountName);
    if (index == -1) return null;
    return getClient(index);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int findClient(String accountName)

  {
    int size = ClientList.getSize();

    for (int i=0; i<size; i++)
      if (getClient(i).getAccount().getUsername().equalsIgnoreCase(accountName))
        return i;

    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Entity findCharacter(String clientName)

  {
    Entity ENT = World.findCharacter(clientName);
    if (ENT == null) return null;
    if (ENT.getClient() == null) return null;
    return ENT;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean contains(Client conClient)

  {
    return list.contains(conClient);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Client getClient(int index)

  {
    if (index < list.size()) return (Client)list.get(index);
    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void removeLinkdead(Client ldClient)

  {
    if (!linkdeadList.contains(ldClient)) return;
    int index = linkdeadList.indexOf(ldClient);
    linkdeadList.remove(index);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void queueClientRemove(Client remClient)

  {
    removeList.add(remClient);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void processRemoveQueue()

  {
    for (int i=0; i<removeList.size(); i++)

    {
      removeClient((Client)removeList.get(i));
      removeLinkdead((Client)removeList.get(i));
    }

    removeList.clear();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void sendAllClients(String s)

  {
    for (int i=0; i<list.size(); i++)
      ((Client)list.get(i)).addOutput(s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void tick()

  {
    int size = ClientList.getSize();

    for (int i=0; i<size; i++)
      ClientList.getClient(i).updateIdleTime();

    World.tickUpdate();

    TIME_TICK = getRandomInt(55000, 75000);
    tickTimer = 0;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void updateLinkdead()

  {
    int size = linkdeadList.size();

    for (int i=0; i<size; i++)
        ((Client)linkdeadList.get(i)).updateLinkdeadState();

    ldTimer = 0;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void echoAll(String s, int cState)

  {
    int size = getSize();

    for (int i=0; i<size; i++)
      if (getClient(i).getClientState() == cState)
        getClient(i).addOutput(s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void systemMessage(String msg, int level, Entity ENT)

  {
    if ((ENT == null) || (ENT.getClient() == null)) msg = "#G[ " + msg + " ]#N";
    else msg = "#G[ " + msg + " (" + ENT.getClient().getIPAddress() + ") ]#N";

    if (level < 100) level = 100;

    for (int i=0; i<getSize(); i++)

    {
      Client C = getClient(i);
      int st = C.getClientState();

      if (C.getCharInfoBackup().getLevel() >= level)
      if ((st == CSTATE_NORMAL) || (st == CSTATE_POSSESS))
        C.addOutput(msg);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void accountMessage(String msg, Client CLT)

  {
    msg = "#G[ " + msg + " (" + CLT.getIPAddress() + ") ]#N";

    for (int i=0; i<getSize(); i++)

    {
      Client C = getClient(i);
      int st = C.getClientState();

      if (C.getCharInfoBackup().getLevel() == 110)
      if ((st == CSTATE_NORMAL) || (st == CSTATE_POSSESS))
        C.addOutput(msg);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////


  public static void updateCommandLocks(long elapsed)

  {
    int size = getSize();

    for (int i=0; i<size; i++)
      if (getClient(i).commandsLocked())
        getClient(i).updateCommandLock(elapsed);

    lockTimer = 0;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void mudTimer()

  {
    long currentTime = System.currentTimeMillis();
    long elapsed = currentTime - previousTime;

    ldTimer      += elapsed;
    tickTimer    += elapsed;
    lockTimer    += elapsed;
    fightTimer   += elapsed;
    animateTimer += elapsed;

    if (ldTimer    >= TIME_LD)        ClientList.updateLinkdead();
    if (tickTimer  >= TIME_TICK)      ClientList.tick();
    if (lockTimer  >= TIME_LOCK)      ClientList.updateCommandLocks(lockTimer);
    if (animateTimer >= ANIMATE_TICK)

    {
      World.animate();
      animateTimer = 0;
    }

    if (fightTimer >= FIGHT_TICK)

    {
      World.updateFightList();
      fightTimer = 0;
    }

    previousTime = currentTime;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void spin()

  {
    mudTimer();

    listSize = list.size();

    for (int i=0; i<listSize; i++)

    {
      currentClient = getClient(i);
      currentClient.processInput();
      currentClient.handleInput();
      currentClient.handleOutput();
    }

    processRemoveQueue();
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String userList()

  {
    String name;
    String tempstring = "";
    int SIZE = ClientList.getSize();

    for (int i=0; i<SIZE; i++)

    {
      Client currentClient = getClient(i);

      if (currentClient.loggingIn() == false)

      {
        int lev;

        if (currentClient.getClientState() == CSTATE_POSSESS)
          lev = currentClient.getCharInfoBackup().getLevel();
        else lev = currentClient.getCharInfo().getLevel();

        if (currentClient.getClientState() == CSTATE_POSSESS)
          tempstring += "[#Y " + tenSpace(lev,2) + currentClient.getCharInfoBackup().getLevel() + " ";
        else tempstring += "[#Y " + tenSpace(lev,2) + currentClient.getCharInfo().getLevel() + " ";

        if (currentClient.getClientState() == CSTATE_POSSESS)
          tempstring += currentClient.getCharInfoBackup().getSClassColor() + " ] ";
        else tempstring += currentClient.getCharInfo().getSClassColor() + " ] ";
      }

      else tempstring += "[ ------ ] ";

      if (currentClient.loggingIn() == true)
        name = "[#y " + currentClient.getAccount().getUsername();
      else if (currentClient.getClientState() == CSTATE_POSSESS)
        name = "[#c " + currentClient.getCharInfoBackup().getName();
      else name = "[#c " + currentClient.getCharInfo().getName();

      tempstring += name;
      tempstring += xchars(14-visibleSize(name)," ") + "#N ] [#b ";
      tempstring += currentClient.getIPAddress();
      tempstring += xchars(15-visibleSize(currentClient.getIPAddress())," ") + "#N ] [";

      if (currentClient.getClientState() == CSTATE_NORMAL)         tempstring += "#g ONLINE  ";
      else if (currentClient.getClientState() == CSTATE_WRITING)   tempstring += "#G WRITING ";
      else if (currentClient.getClientState() == CSTATE_LINKDEAD)  tempstring += "#R LINKDEAD";
      else if (currentClient.getClientState() == CSTATE_POSSESS)   tempstring += "#m POSSESS ";
      else if (currentClient.getClientState() == CSTATE_CREATION)  tempstring += "#r CREATION";
      else if (currentClient.getClientState() == CSTATE_LOGIN)     tempstring += "#r LOGIN   ";
      else if (currentClient.getClientState() == CSTATE_MENU)      tempstring += "#y MENU    ";
      else if (currentClient.getClientState() == CSTATE_BUILDING)  tempstring += "#n BUILDING";

      tempstring += "#N ] [#N ";

      String idleTime = String.valueOf(currentClient.getIdleTime());
      if (currentClient.getIdleTime() < 10) idleTime = "0" + idleTime;
      tempstring += idleTime + "#N ] [#N ";
      tempstring += currentClient.getLoginTime() + " #N]\r\n";
    }

    String border = "#n+------------------------------------------------------------------------------+#N";
    String header = "#n[ Level  ] [ Player Name  ] [ IP Address      ] [ Status   ] [Idle] [ Login    ]#N";
    String clrbrd = "+------------------------------------------------------------------------------+";

    tempstring = border + "\r\n" + header + "\r\n" + border + "\r\n" + tempstring + clrbrd;

    return tempstring;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String whoList()

  {
    String tempstring = "", xs, pspace;
    int count = 0;
    int SIZE = ClientList.getSize();

    for (int i=0; i<SIZE; i++)

    {
      Client currentClient = ClientList.getClient(i);

      if ((currentClient.getClientState() != CSTATE_LOGIN) && (currentClient.getClientState() != CSTATE_CREATION)
      && (currentClient.getCharInfo().isPlayer()))

      {
        xs = "";
        pspace = " ";
        if (currentClient.getCharInfo().getLevel() < 100) xs += " ";
        if (currentClient.getCharInfo().getLevel() < 10)  xs += " ";
        if (currentClient.getCharInfo().castChar().getPrename().equals("")) pspace = "";

        tempstring += "[#Y " + xs + currentClient.getCharInfo().getLevel() + " ";
        tempstring += currentClient.getCharInfo().getSClassColor() + " ] ";
        tempstring += currentClient.getCharInfo().castChar().getPrename() + "#C" + pspace;
        tempstring += currentClient.getCharInfo().getName()   + "#N ";
        tempstring += currentClient.getCharInfo().getTitle() + "#N\n\r";
        count++;
      }
    }

    tempstring += "\r\nNumber of players: " + count;

    return tempstring;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////
}