import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.channels.*;
import java.nio.*;

public class Client extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int clientState;
  private int linkdeadTime;
  private int idleTime;
  private int commandLock;
  private long commandLockTimer;
  private String loginTime;
  private String inprogressCommand;
  private String lastCommand;
  private String ipAddress;
  private Entity charInfo;
  private Entity charInfoBackup;
  private Account myAccount;
  private MessageQueue commandQueue;
  private MudInterface myInterface;
  private ByteBuffer byteBufferIn;
  private LinkedList sayHistory;
  private LinkedList interfaceStack;
  private SocketChannel clientSocket;
  private ArrayList snoopList;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Char getChar()                       { return charInfoBackup.castChar(); }
  public int getClientState()                 { return clientState;               }
  public int getIdleTime()                    { return idleTime;                  }
  public boolean commandWaiting()             { return !commandQueue.isEmpty();   }
  public boolean commandsLocked()             { return bool(commandLock);         }
  public Entity getCharInfo()                 { return charInfo;                  }
  public Entity getCharInfoBackup()           { return charInfoBackup;            }
  public String getLoginTime()                { return loginTime;                 }
  public String getLastCommand()              { return lastCommand;               }
  public String getCommand()                  { return commandQueue.get();        }
  public void flushCommands()                 { commandQueue.clear();             }
  public void putCommand(String s)            { commandQueue.put(s);              }
  public void forceCommand(String s)          { commandQueue.putFront(s);         }
  public void lockCommands(int i)             { commandLock = i;                  }
  public void setCharInfo(Entity e)           { charInfo = e;                     }
  public void setCharInfoBackup(Entity e)     { charInfoBackup = e;               }
  public void setClientState(int i)           { clientState = i;                  }
  public void setSocket(SocketChannel s)      { clientSocket = s;                 }
  public void setAccount(Account A)           { myAccount = A;                    }
  public void addOutput(String s)             { myInterface.addOutput(s);         }
  public void addFightOutput(String s)        { myInterface.addFightOutput(s);    }
  public void drawPrompt()                    { msg(myInterface.getPrompt());     }
  public void freezeFightOutput()             { myInterface.freezeFightOutput();  }
  public void handleOutput()                  { myInterface.handleOutput();       }
  public void handleInput()                   { myInterface.handleInput();        }
  public void stackingOn()                    { myInterface.stackingOn();         }
  public void stackingOff()                   { myInterface.stackingOff();        }
  public void setNormalRedirect(boolean b)    { myInterface.setNormalRedirect(b); }
  public void setCombatRedirect(boolean b)    { myInterface.setCombatRedirect(b); }
  public void setSkillRedirect(boolean b)     { myInterface.setSkillRedirect(b);  }
  public void setActive(boolean b)            { myInterface.setActive(b);         }
  public void clearOutputBuffers()            { myInterface.clearOutputBuffers(); }
  public void addSnooper(Client snooper)      { snoopList.add(snooper);           }
  public MudInterface getInterface()          { return myInterface;               }
  public Account getAccount()                 { return myAccount;                 }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Client(SocketChannel cSocket)

  {
    try

    {
      clientSocket      = cSocket;
      clientState       = CSTATE_LOGIN;
      linkdeadTime      = 0;
      idleTime          = 0;
      commandLock       = 0;
      commandLockTimer  = 0;
      inprogressCommand = "";
      lastCommand       = "";
      loginTime         = SystemTime.getTime();
      myAccount         = new Account();
      charInfo          = new Warrior(this);
      charInfoBackup    = charInfo;
      commandQueue      = new MessageQueue();
      byteBufferIn      = ByteBuffer.allocateDirect(10000);
      ipAddress         = "";

      sayHistory      = new LinkedList();
      interfaceStack  = new LinkedList();
      snoopList       = new ArrayList();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean loggingIn()

  {
    if (getClientState() == CSTATE_LOGIN)     return true;
    if (getClientState() == CSTATE_CREATION)  return true;
    if (getClientState() == CSTATE_MENU)      return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void pushPageBreak(String str)

  {
    pushInterface(new PageBreak(this, str));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void pushInterface(MudInterface M)

  {
    if (myInterface != null) M.setActive(myInterface.getActive());
    interfaceStack.addFirst(M);
    myInterface = M;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void popInterface()

  {
    boolean active = myInterface.getActive();
    interfaceStack.removeFirst();
    myInterface = (MudInterface)interfaceStack.getFirst();
    myInterface.setActive(active);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean viewModeEnabled()

  {
    for (int i=0; i<interfaceStack.size(); i++)

    {
      int mode = ((MudInterface)interfaceStack.get(i)).viewMode();
      if (mode == MudInterface.ALL_OUTPUT) return true;
      if (mode == MudInterface.NO_OUTPUT) return false;
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getIPAddress()

  {
    if (clientSocket.socket().getInetAddress() == null) return ipAddress;
    ipAddress = clientSocket.socket().getInetAddress().getHostAddress();
    return ipAddress;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void msg(String s)

  {
    if (clientState == CSTATE_LINKDEAD) return;

    msgSnoopers(s);

    if (getChar().getColorMode()) s = colorize(s);
    else s = removeColors(s);

    if (getChar().getAnsiMode()) s = Ansi.drawAboveBar(s, getChar().getScreenSize());

    try { clientSocket.write(ByteBuffer.wrap(s.getBytes())); }
    catch (Exception e) { enterLinkdeadState(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void updateCommandLock(long elapsed)

  {
    commandLockTimer += elapsed;
    if (commandLockTimer < commandLock) return;
    commandLock = 0;
    commandLockTimer = 0;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void disconnect()

  {
    try { clientSocket.socket().close(); }
    catch (Exception e) { System.out.println(e); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void updateIdleTime()

  {
    if (!myInterface.getActive()) idleTime++;
    else idleTime = 0;

    if (idleTime > 99) idleTime = 99;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  // Fix this
  public void sendToAwake(String str)

  {
    charInfo.getRoom().xecho(charInfo, str, ECHO_AWAKE);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void exitBuilder()

  {
    if (interfaceStack.size() == 3)
    if (clientState == CSTATE_WRITING)
      return;

    while (interfaceStack.size() > 2)
      myInterface.exit();

    charInfo.echo("\r\nBuilder exited for world save.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void enterLinkdeadState()

  {
    while (interfaceStack.size() > 2)
      myInterface.onDisconnect();

    myInterface.onDisconnect();
    setClientState(CSTATE_LINKDEAD);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void updateLinkdeadState()

  {
    linkdeadTime++;

    if (linkdeadTime < LD_TIMEOUT) return;
    if (getCharInfo().getPlayerState() == PSTATE_FIGHTING) return;

    sendToAwake(charInfo.getName() + " has dropped from the game.");
    charInfo.castChar().resetVars();
    ClientList.queueClientRemove(this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void exitLinkdeadState()

  {
    if (clientState != CSTATE_LINKDEAD) return;
    linkdeadTime = 0;
    clearOutputBuffers();
    ClientList.removeLinkdead(this);
    setClientState(CSTATE_NORMAL);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void processInput()

  {
    if (!clientSocket.isConnected()) return;
    if (clientState == CSTATE_LINKDEAD) return;

    try

    {
      byteBufferIn.clear();

      if (clientSocket.read(byteBufferIn) == -1) {
        enterLinkdeadState();
        return; }

      byteBufferIn.flip();

      while (byteBufferIn.hasRemaining())

      {
        char temp = (char)byteBufferIn.get();

        if ((temp == '\r') || (temp == '\n'))

        {
          if (inprogressCommand.length() > 0)
          if ((clientState == CSTATE_NORMAL) || (clientState == CSTATE_POSSESS))
          if ((inprogressCommand.charAt(0) != '!') && (inprogressCommand.charAt(0) != 92))
            lastCommand = inprogressCommand;

          if ((temp == '\r') && (byteBufferIn.hasRemaining())) byteBufferIn.get();
          commandQueue.put(inprogressCommand);
          inprogressCommand = "";
          idleTime = 0;
          myInterface.setActive(true);
        }

        else if ((int)temp >= 32 && (int)temp <= 126 && inprogressCommand.length() < 500)
          inprogressCommand += temp;
        else if (((int)temp == 127 || (int)temp == 8) && inprogressCommand.length() > 0)
          inprogressCommand = inprogressCommand.substring(0, inprogressCommand.length() - 1);
      }
    }

    catch (IOException e) { enterLinkdeadState(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void reconnect(Client oldClient)

  {
    ClientList.queueClientRemove(this);

    Entity ENT = oldClient.getCharInfo();

    SystemLog.doubleLog(ENT, "has reconnected.");
    oldClient.disconnect();
    oldClient.setSocket(clientSocket);
    oldClient.exitLinkdeadState();
    oldClient.sendToAwake(oldClient.getCharInfo().getName() + " has reconnected.");
    oldClient.getInterface().setActive(true);
    oldClient.getCharInfo().look("#cYou reassume your body!#N");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearScreen(boolean prompt)

  {
    msg(Ansi.clearScreen);
    if (prompt) msg(myInterface.getPrompt());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addSayHistory(String s1, String s2)

  {
    addSayHistory(s1);
    charInfo.getRoom().addSayHistory(charInfo, s2);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addSayHistory(String str)

  {
    sayHistory.addLast(str);
    if (sayHistory.size() > MAX_SAY_HISTORY) sayHistory.removeFirst();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void displaySayHistory()

  {
    int count = 0;
    String str = "#nSay History#r:#N";

    for (int i=0; i<sayHistory.size(); i++)

    {
      count++;
      str += "\r\n#R[#n";
      if (count < 10) str += " ";
      str += count + "#R]#N ";
      str += removeColors(((String)sayHistory.get(i)));
    }

    myInterface.addOutput(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeSnooper(Client snooper)

  {
    for (int i=snoopList.size()-1; i>=0; i--)

    {
      Client CLT = (Client) snoopList.get(i);
      if (CLT == snooper) snoopList.remove(i);
      else System.out.println("No Match");
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void msgSnoopers(String s)

  {
    for (int i=0; i<snoopList.size(); i++)
      ((Client)snoopList.get(i)).msg("\r\n" + s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearSnoopers()

  {
    for (int i=snoopList.size()-1; i>=0; i--)

    {
      Client CLT = (Client) snoopList.get(i);
      CLT.addOutput("Your victim is no longer among us.");
      snoopList.remove(i);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}