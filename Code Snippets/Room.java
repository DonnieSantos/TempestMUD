import java.util.*;
import java.io.*;

public class Room extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int id;
  private int num_mobs;
  private int num_people;
  private int N, E, S, W, U, D;
  private boolean LAWFULL, PKOK, NO_MAGIC, REGEN;
  private boolean INDOORS, NO_SUMMON, NO_QUIT, SILENT;
  private boolean PRIVATE, NO_DROP, NO_MOB, DONATION;
  private String title, desc;
  private ArrayList lookList;
  private ArrayList lookTargs;
  private ArrayList eventList;
  private ArrayList eventFreq;
  private ArrayList entList;
  private ArrayList itemList;
  private ArrayList mobExits;
  private double timer;
  private Zone myZone;
  private MobCounter MC;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)     { }
  public void writeExternal(ObjectOutput out)  { }

  public void setTitle(String s)  { title = s;                       }
  public int getSize()            { return entList.size();           }
  public int getID()              { return id;                       }
  public String getTitle()        { return title;                    }
  public String getDesc()         { return desc;                     }
  public int getNumLooks()        { return lookList.size();          }
  public String getLook(int n)    { return (String) lookList.get(n); }
  public int getN()               { return N;                        }
  public int getE()               { return E;                        }
  public int getS()               { return S;                        }
  public int getW()               { return W;                        }
  public int getU()               { return U;                        }
  public int getD()               { return D;                        }
  public boolean occupied()       { return bool(num_people);         }
  public boolean mobOccupied()    { return bool(num_mobs);           }
  public int getNumMobexits()     { return mobExits.size();          }
  public int getMobExit(int n)    { return getInt(mobExits, n);      }
  public int getNumItems()        { return itemList.size();          }
  public Item getItem(int n)      { return (Item) itemList.get(n);   }
  public Zone getZone()           { return myZone;                   }
  public void setZone(Zone z)     { myZone = z;                      }
  public Entity getEntity(int n)  { return (Entity) entList.get(n);  }
  public boolean getLAWFULL()     { return LAWFULL;                  }
  public boolean getPKOK()        { return PKOK;                     }
  public boolean getNO_MOB()      { return NO_MOB;                   }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Room(int ID)

  {
    id = ID;
    lookList = new ArrayList();
    lookTargs = new ArrayList();
    eventList = new ArrayList();
    eventFreq = new ArrayList();
    entList = new ArrayList();
    itemList = new ArrayList();
    mobExits = new ArrayList();
    MC = new Mobcounter();

    LAWFULL    =  false;
    PKOK       =  false;
    NO_MAGIC   =  false;
    REGEN      =  false;
    INDOORS    =  false;
    NO_SUMMON  =  false;
    NO_QUIT    =  false;
    SILENT     =  false;
    PRIVATE    =  false;
    NO_DROP    =  false;
    NO_MOB     =  false;
    DONATION   =  false;

    N = 0;
    E = 0;
    S = 0;
    W = 0;
    U = 0;
    D = 0;

    initLooks();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void toggleFlag(String str)

  {
    if (str == "LAWFULL")   LAWFULL    =  (!LAWFULL);
    if (str == "PKOK")      PKOK       =  (!PKOK);
    if (str == "NO_MAGIC")  NO_MAGIC   =  (!NO_MAGIC);
    if (str == "REGEN")     REGEN      =  (!REGEN);
    if (str == "INDOORS")   INDOORS    =  (!INDOORS);
    if (str == "NO_SUMMON") NO_SUMMON  =  (!NO_SUMMON);
    if (str == "NO_QUIT")   NO_QUIT    =  (!NO_QUIT);
    if (str == "SILENT")    SILENT     =  (!SILENT);
    if (str == "PRIVATE")   PRIVATE    =  (!PRIVATE);
    if (str == "NO_DROP")   NO_DROP    =  (!NO_DROP);
    if (str == "NO_MOB")    NO_MOB     =  (!NO_MOB);
    if (str == "DONATION")  DONATION   =  (!DONATION);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setDesc(String str, boolean exact)

  {
    if (exact) desc = str;
    if (exact) return;

    str = "   " + str;
    desc = roomAlign(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setExit(char ex, int target)

  {
    if (ex == 'N') N = target;
    else if (ex == 'S') S = target;
    else if (ex == 'E') E = target;
    else if (ex == 'W') W = target;
    else if (ex == 'U') U = target;
    else if (ex == 'D') D = target;

    setMobExits();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addEvent(String event, int frequency)

  {
    eventList.add(event);
    eventFreq.add(new Integer(frequency));
    normalizeFrequency();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void normalizeFrequency()

  {
    int total = 0;

    for (int i=0; i<eventFreq.size()-1; i++)
    for (int j=0; j<eventFreq.size()-1; j++)

    {
      if (getInt(eventFreq, j) > getInt(eventFreq, j+1))

      {
        int temp = getInt(eventFreq, j);
        eventFreq.set(j, eventFreq.get(j+1));
        eventFreq.set(j+1, new Integer(temp));

        String temp2 = (String) eventList.get(j);
        eventList.set(j, eventList.get(j+1));
        eventList.set(j+1, temp2);
      }
    }

    for (int i=0; i<eventList.size(); i++)

    {
      int freq = getInt(eventFreq, i) + total;
      eventFreq.set(i, new Integer(freq));
      total += getInt(eventFreq, i);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setMobExits()

  {
    mobExits = new ArrayList();

    if (N > 0) mobExits.add(new Integer(N));
    if (E > 0) mobExits.add(new Integer(E));
    if (S > 0) mobExits.add(new Integer(S));
    if (W > 0) mobExits.add(new Integer(W));
    if (U > 0) mobExits.add(new Integer(U));
    if (D > 0) mobExits.add(new Integer(D));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void initLooks()

  {
    lookList = new ArrayList();

    String s1 = "You see nothing out of the ordinary to the north.";
    String s2 = "You see nothing out of the ordinary to the south.";
    String s3 = "You see nothing out of the ordinary to the east.";
    String s4 = "You see nothing out of the ordinary to the west.";
    String s5 = "You see nothing out of the ordinary above you.";
    String s6 = "You see nothing out of the ordinary below you.";

    lookList.add(s1);
    lookList.add(s2);
    lookList.add(s3);
    lookList.add(s4);
    lookList.add(s5);
    lookList.add(s6);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addLook(String target, String str)

  {
    int n = lookList.indexOf(target);
    if (n != -1) return;
    if (direction(target)) return;
    lookList.add(str);
    lookTargs.add(target);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void modifyLook(String target, String str)

  {
    if (direction(target))

    {
      if (target.substring(0,1) == "n") lookList.set(0, roomAlign(str));
      else if (target.substring(0,1) == "s") lookList.set(1, roomAlign(str));
      else if (target.substring(0,1) == "e") lookList.set(2, roomAlign(str));
      else if (target.substring(0,1) == "w") lookList.set(3, roomAlign(str));
      else if (target.substring(0,1) == "u") lookList.set(4, roomAlign(str));
      else if (target.substring(0,1) == "d") lookList.set(5, roomAlign(str));
    }

    else

    {
      int n = lookList.indexOf(target);
      if (n != -1) lookList.set(n, roomAlign(str));
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeLook(String target)

  {
    int n = lookList.indexOf(target);
    if (n == -1) return;
    lookList.remove(n);
    lookTargs.remove(n);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean searchLooks(String target)

  {
    int n = lookTargs.indexOf(target);
    if (n == -1) return false;
    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void gain(Entity ENT)

  {
    entList.add(ENT);

    if (ENT.isPlayer()) num_people++;
    else num_mobs++;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void loss(Entity ENT)

  {
    int n = entList.indexOf(ENT);
    if (n == -1) return;
    if (ENT.isPlayer()) num_people--;
    else num_mobs--;
    entList.remove(n);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearMobs()

  {
    ArrayList A = new ArrayList();

    for (int i=0; i<entList.size(); i++)
    if (!getEntity(i).isPlayer)

    {
      Mobile mob = (Mobile) getEntity(i);
      myZone.killMob(mob);
      A.add(new Integer(i));
    }

    for (int i=A.size()-1; i>0; i--)

    {
      int n = getInt(A,i);
      entList.remove(n);
      num_mobs--;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void considerEvent()

  {
    if (!occupied()) return;

    boolean acted = false;
    int n = randomInt(0, 60000);

    for (int i=0; i<eventList.size(); i++)

    {
      if (!acted)

      {
        if (getInt(eventFreq,i) >= n) {
          echo((String)eventList.get(i));
          acted = true; }
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void echo(String str)

  {
    for (int i=0; i<entList.size(); i++)
      getEnt(i).echo(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void promptAll()

  {
    for (int i=0; i<entList.size(); i++)
    if (getEnt(i).getClient() != null)

    {
      String p = getEnt(i).getClient().prompt();

      if (!getEnt(i).getClient().getAnsiMode())
        getEnt(i).echo("\r\n" + p + "\r\n");
      else getEnt(i).echo("");
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void examineSay(String str)

  {
    for (int i=0; i<entList.size(); i++)
      if ((!getEnt(i).isPlayer()) && (getEnt(i).getClient() == null))
        getEnt(i).examineSay(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Entity findEntity(Entity SRC, String target)

  {
    int target_num = clipNumber(target);
    target = clipString(target);

    if ((SRC.getRoom() == this) && (SRC.checkTargets(target))) {
      if (target_num == 1) return SRC;
      target_num--; }

    for (int i=0; i<entList.size(); i++)
    if (getEntity(i).isPlayer())

    {
      if ((getEntity(i) != SRC)
      && (getEntity(i).checkTargets(target))
      && (getEntity(i).getClient().getClient.State() != CSTATE_LOGIN))
        target_num--;

      if (!target_num) return getEntity(i);
    }

    for (int i=0; i<entList.size(); i++)
    if (!getEntity(i).isPlayer())

    {
      if ((getEntity(i).checkTargets(target))
      && (getEntity(i) != SRC))
        target_num--;

      if (!target_num) return getEntity(i);
    }

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Entity findCharacter(Entity SRC, string target)

  {
    for (int i=0; i<entList.size(); i++)
      if ((getEntity(i).getName() == target)
      && (getEntity(i).isPlayer())
      && (getEntity(i) != SRC))
        return getEntity(i);

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int findNumEnts(string target)

  {
    int num_ents = 0;

    for (int i=0; i<entList.size(); i++)

    {
      if (getEntity(i).isPlayer()) {
        if ((getEntity(i).checkTargets(target))
        && (getEntity(i).getClient().getClientState() != CSTATE_LOGIN))
          num_ents++; }

      else if (getEntity(i).checkTargets(target))
        num_ents++;
    }

    return num_ents;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getGroupsize(Group the_group)

  {
    int count = 0;

    for (int i=0; i<entList.size(); i++)
      if (the_group.in_group(getEnt(i)))
        count++;

    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getScoutNames(int dist, string dir)

  {
    MC.clear();

    string tempstr = "";

    if (dist == 1) {
      if (dir == "N") tempstr += "\r\n  To the north:";
      else if (dir == "S") tempstr += "\r\n  To the south:";
      else if (dir == "E") tempstr += "\r\n  To the east:";
      else if (dir == "W") tempstr += "\r\n  To the west:"; }

    for (int i=0; i<entList.size(); i++)

    {
      if (getEnt(i).isPlayer()) {
      if (getEnt(i).getClient().getClientState() != CSTATE_LOGIN)
        tempstr += "\r\n    " + dist + " - " + getEnt(i).getName(); }

      else MC.add(getEnt(i).getInfo().getID());
    }

    tempstr += MC.shortStack(dist);

    return tempstr;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getLookTitles(Entity SRC)

  {
    MC.clear();

    String tspace, tempstr = "", mob_fighters = "";

    for (int i=0; i<entList.size(); i++)

    {
      Entity ENT = getEntity(i);

      if (ENT.isPlayer())

      {
        if ((ENT != SRC) && (ENT.getClient().getClientState() != CSTATE_LOGIN))

        {
          tspace = " ";
          if (visibleSize(ENT.getTitle()) < 1) tspace = "";

          tempstr += "\r\n" + ENT.getPrename() + "#C" + ENT.getName();

          if (ENT.getPlayerState() == PSTATE_FIGHTING)

          {
            Entity target = ENT.getTarget();
            string tname = "";
            if (target != null) tname = target.getName();

            if (target == SRC) tname = "YOU";

            if (target != null) tempstr += "#N is here fighting " + tname + "#N!";
            else tempstr += "#N " + ENT.getTitle() + tspace + "#Nis standing here.";
          }

          else tempstr += "#N " + ENT.getTitle() + tspace + "#Nis standing here.";

          if (ENT.getClient().getClientState() == CSTATE_LINKDEAD)
          tempstr += " #N(#Rlinkdead#N)";

          if (ENT.getClient().getClientState() == CSTATE_WRITING)
          tempstr += " #N(#Gwriting#N)";
        }
      }

      else if (ENT != SRC)

      {
        if (ENT.getPlayerState() == PSTATE_FIGHTING)

        {
          Entity target = ENT.getTarget();
          string tname = "";
          if (target != null) tname = target.getName();

          if (target == SRC) tname = "YOU";

          String tnull =
          tnull +=

          if (target != null) mob_fighters += "\r\n#Y" + ENT.getName() + "#Y ";
          if (target != null) mob_fighters += "is here #Nfighting " + tname + "#N!";
          if (target == null) MC.add(ENT.getInfo().getID());
        }

        else MC.add(ENT.getInfo().getID());
      }
    }

    tempstr += mob_fighters;
    tempstr += MC.stack();

    return tempstr;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String checkBlockers(Entity ENT, String dir)

  {
    for (int i=0; i<itemList.size(); i++)
    if (getItem(i).isBlocker())
    if ((Blocker)getItem(i).getBlockDir() == dir)

    {
      if (!(Blocker)getItem(i).tryPass(ENT))
        return (Blocker)getItem(i).errorMsg();
    }

    for (int i=0; i<entList.size(); i++)
    if (getEnt(i).isBlocker())
    if (getEnt(i).getBlocker().getBlockDir() == dir)

    {
      if (!getEnt(i).getBlocker().tryPass(ENT))
        return getEnt(i).getBlocker().errorMsg();
    }

    return "OK";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ArrayList roomDistance(Room loc)

  {
    int index, size, new_rooms[10000];
    ArrayList IRA = new ArrayList();
    ArrayList DIR = new ArrayList();
    int found = 1, i = 0;

    ArrayList dist_and_dir = new ArrayList();

    if (loc == this) return null;

    IRA.add(id);
    DIR.add(0);

    while (true)

    {
      i++;
      dist_and_dir.add(new Integer(i));

      if (!found) return null;
      found = 0;

      if (i < 3) index = i - 1;
      else index += new_rooms[i-2];

      size = IRA.size();
      new_rooms[i] = 0;

      for (int j=index; j<size; j++)

      {
        Room current_room = World.getRoom(getInt(IRA, j));

        Integer CC = new Integer(current_room.getID());
        Integer CN = new Integer(current_room.getN());
        Integer CS = new Integer(current_room.getS());
        Integer CE = new Integer(current_room.getE());
        Integer CW = new Integer(current_room.getW());
        Integer CU = new Integer(current_room.getU());
        Integer CD = new Integer(current_room.getD());

        if (current_room.getN())
        if (IRA.indexOf(CN) == -1)

        {
          found = 1;
          new_rooms[i]++;
          IRA.add(new Integer(current_room.getN()));
          if (i == 1) DIR.add(new Integer(1));
          else DIR->put(DIR.get(IRA->indexOf(CC)-1));
          if (World.getRoom(current_room.getN()) == loc) {
            dist_and_dir.add(new Integer(getInt(DIR, DIR.size()-1)));
            return dist_and_dir; }
        }

        if (current_room.getS())
        if (IRA.indexOf(CS) == -1)

        {
          found = 1;
          new_rooms[i]++;
          IRA.add(new Integer(current_room.getS()));
          if (i == 1) DIR.add(new Integer(2));
          else DIR->put(DIR.get(IRA->indexOf(CC)-1));
          if (World.getRoom(current_room.getS()) == loc) {
            dist_and_dir.add(new Integer(getInt(DIR, DIR.size()-1)));
            return dist_and_dir; }
        }

        if (current_room.getE())
        if (IRA.indexOf(CE) == -1)

        {
          found = 1;
          new_rooms[i]++;
          IRA.add(new Integer(current_room.getE()));
          if (i == 1) DIR.add(new Integer(3));
          else DIR->put(DIR.get(IRA->indexOf(CC)-1));
          if (World.getRoom(current_room.getE()) == loc) {
            dist_and_dir.add(new Integer(getInt(DIR, DIR.size()-1)));
            return dist_and_dir; }
        }

        if (current_room.getW())
        if (IRA.indexOf(CW) == -1)

        {
          found = 1;
          new_rooms[i]++;
          IRA.add(new Integer(current_room.getW()));
          if (i == 1) DIR.add(new Integer(4));
          else DIR->put(DIR.get(IRA->indexOf(CC)-1));
          if (World.getRoom(current_room.getW()) == loc) {
            dist_and_dir.add(new Integer(getInt(DIR, DIR.size()-1)));
            return dist_and_dir; }
        }

        if (current_room.getU())
        if (IRA.indexOf(CU) == -1)

        {
          found = 1;
          new_rooms[i]++;
          IRA.add(new Integer(current_room.getU()));
          if (i == 1) DIR.add(new Integer(5));
          else DIR->put(DIR.get(IRA->indexOf(CC)-1));
          if (World.getRoom(current_room.getU()) == loc) {
            dist_and_dir.add(new Integer(getInt(DIR, DIR.size()-1)));
            return dist_and_dir; }
        }

        if (current_room.getD())
        if (IRA.indexOf(CD) == -1)

        {
          found = 1;
          new_rooms[i]++;
          IRA.add(new Integer(current_room.getD()));
          if (i == 1) DIR.add(new Integer(6));
          else DIR->put(DIR.get(IRA->indexOf(CC)-1));
          if (World.getRoom(current_room.getD()) == loc) {
            dist_and_dir.add(new Integer(getInt(DIR, DIR.size()-1)));
            return dist_and_dir; }
        }
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}