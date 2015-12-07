import java.io.*;
import java.util.*;

public class Ghost extends Entity implements Cloneable

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String targetEcho;
  public static String othersEcho;
  public static Room itemRoom;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public String getName()                       { return name;    }
  public String getFullName()                   { return name;    }
  public String getTitle()                      { return "";      }
  public String getCharClass()                  { return "";      }
  public String getSClass()                     { return "";      }
  public String getCharClassColor()             { return "";      }
  public String getSClassColor()                { return "";      }
  public boolean isGhost()                      { return true;    }
  public boolean isPlayer()                     { return false;   }
  public boolean checkTargets(String s)         { return false;   }
  public void stat()                            { return;         }
  public void die(boolean d, boolean m)         { return;         }
  public void receiveExperience(Entity target)  { return;         }
  public void gainExperience(int num)           { return;         }
  public void regen()                           { return;         }

  public int getTrueResistance()                { return -1;      }
  public int getClanNum()                       { return -1;      }
  public int getReligionNum()                   { return -1;      }
  public void setClan(int i)                    { return;         }
  public void setReligion(int i)                { return;         }
  public void setFullName(String s)             { return;         }

  public void setScreenSize(int i)              { return;         }
  public void setAnsiMode(boolean b)            { return;         }
  public void setColorMode(boolean b)           { return;         }
  public void setFollowable(boolean b)          { return;         }
  public void setAutoassist(boolean b)          { return;         }

  public void echoToOthers(String s)            { othersEcho = s; }
  public void echoToTargets(String s)           { targetEcho = s; }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Ghost()

  {
    name = "Shriekie";
    level = 1;

    inventory    =  new ItemList(INV_START_SIZE, ItemList.TYPE_INVENTORY);
    equipment    =  new Item[Equipment.NUM_PLACES];

    spellList    =  new ArrayList();
    skillList    =  new ArrayList();
    effectList   =  new ArrayList();

    following = this;
    followers = new ArrayList();
    myGroup = new Group(this);
    targetList = new ArrayList();
    intermittentSkill = 0;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void echo(String s)                     { return; }
  public void fightEcho(String s)                { return; }
  public void awakeEcho(String s)                { return; }
  public void awakeFightEcho(String s)           { return; }
  public void sendToAwake(String s)              { return; }
  public void sendToAwake(Entity ENT, String s)  { return; }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void ghostEcho(String s)

  {
    super.sendToAwake(s);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void ghostMove(String s)

  {
    int newRoomID;
    Room targetRoom;

    if (!s.equalsIgnoreCase("here"))

    {
      try { newRoomID = Integer.parseInt(s); }
      catch (Exception e) { return; }

      if (newRoomID < World.getSize())
        targetRoom = World.getRoom(newRoomID);
      else return;

      setRoom(targetRoom.getID());
    }

    else setRoom(Ghost.itemRoom.getID());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void ghostLoad(String s)

  {
    Immortal.load(this, s);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void echoTargets()

  {
    for (int i=0; i<Evaluator.getNumTargets(); i++)
      Evaluator.getTarget(i).echo(targetEcho);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void echoOthers()

  {
    int targSize = Evaluator.targets.size();
    int entSize  = getRoom().getNumEnts();
    ArrayList targs = Evaluator.targets;

    for (int i=0; i<targSize; i++)
    for (int j=0; j<entSize; j++)
    if (!targs.contains(getRoom().getEnt(j)))
      getRoom().getEnt(j).echo(othersEcho.replaceAll("%", Evaluator.getTarget(i).getName()));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void giveDamage(String s)

  {
    int max, min, r;

    try

    {
      max = Integer.parseInt(last(s));
      min = Integer.parseInt(first(s));
    }

    catch (Exception e) { return; }

    for (int i=0; i<Evaluator.getNumTargets(); i++)

    {
      echoTargets();
      echoOthers();

      r = getRandomInt(min, max);
      Evaluator.getTarget(i).setCurrentHP(Evaluator.getTarget(i).getCurrentHP() - r);
      if (Evaluator.getTarget(i).getCurrentHP() <= 0) Evaluator.getTarget(i).die(false, true);
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void giveDamageRandom(String s)

  {
    int max, min, r, targ;

    try

    {
      max = Integer.parseInt(last(s));
      min = Integer.parseInt(first(s));
      targ = getRandomInt(0, Evaluator.getNumTargets()-1);
      r = getRandomInt(min, max);
      Entity Target = Evaluator.getTarget(targ);
      Evaluator.targets.clear();
      Evaluator.targets.add(Target);
      echoTargets();
      echoOthers();
      Target.setCurrentHP(Target.getCurrentHP() - r);
      if (Target.getCurrentHP() <= 0) Target.die(false, true);
    }

    catch (Exception e) { return; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}