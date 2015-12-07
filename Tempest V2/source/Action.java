import java.util.*;
import java.io.*;

public class Action extends Utility implements Externalizable, Cloneable

{
  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 17;

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  private int id;
  private int delay;
  private int counter;
  private int currentCommand;
  private String name;
  private String custom;
  private ArrayList commands;
  private ArrayList delayMins;
  private ArrayList delayMaxs;
  private boolean random;
  private boolean repeats;
  private boolean hostile;
  private boolean done;
  private Entity owner;
  private Entity user;

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static final Entity ghost = new Ghost();

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void setOwner(Entity E)         { owner = E;                            }
  public void setID(int i)               { id = i;                               }
  public void setName(String s)          { name = s;                             }
  public void setCommands(ArrayList A)   { commands = A;                         }
  public void setRandom(boolean b)       { random = b;                           }
  public void setRepeats(boolean b)      { repeats = b;                          }
  public void setHostile(boolean b)      { hostile = b;                          }
  public void setDone(boolean b)         { done = b;                             }
  public void setDelayMins(ArrayList A)  { delayMins = A;                        }
  public void setDelayMaxs(ArrayList A)  { delayMaxs = A;                        }
  public void setCustom(String s)        { custom = s;                           }
  public Entity getOwner()               { return owner;                         }
  public String getName()                { return name;                          }
  public String getCommand(int i)        { return (String)commands.get(i);       }
  public String getCustom()              { return custom;                        }
  public ArrayList getCommands()         { return commands;                      }
  public ArrayList getDelayMins()        { return delayMins;                     }
  public ArrayList getDelayMaxs()        { return delayMaxs;                     }
  public boolean getRandom()             { return random;                        }
  public boolean getRepeats()            { return repeats;                       }
  public boolean getHostile()            { return hostile;                       }
  public boolean getDone()               { return done;                          }
  public int getDelayMin(int i)          { return Utility.getInt(delayMins, i);  }
  public int getDelayMax(int i)          { return Utility.getInt(delayMaxs, i);  }
  public int getID()                     { return id;                            }
  public int getNumCommands()            { return commands.size();               }
  public static Ghost getGhost()         { return ghost.castGhost();             }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public Action() { this(0); }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public Action(int i)

  {
    id             = i;
    name           = "Generic Action";
    custom         = "";
    commands       = new ArrayList();
    delayMins      = new ArrayList();
    delayMaxs      = new ArrayList();
    random         = false;
    repeats        = false;
    hostile        = false;
    done           = false;
    counter        = 0;
    owner          = null;
    currentCommand = 0;
    delay          = 0;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public Action replicate()

  {
    Action A = null;

    try

    {
      A = (Action)clone();
      A.setOwner(null);
      A.setCommands(copyStringList(commands));
      A.setDelayMins(copyIntList(delayMins));
      A.setDelayMaxs(copyIntList(delayMaxs));
    }

    catch (Exception e) { e.printStackTrace(); }

    return A;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public boolean insertCommand(String command, int delay, int index)

  {
    if (index < 0) return false;
    if (index >= commands.size()) return false;

    commands.add(index, command);
    delayMins.add(index, new Integer(delay));
    delayMaxs.add(index, new Integer(delay));
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public boolean insertCommand(String command, int min, int max, int index)

  {
    if (index < 0) return false;
    if (index >= commands.size()) return false;

    commands.add(index, command);
    delayMins.add(index, new Integer(min));
    delayMaxs.add(index, new Integer(max));
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public boolean removeCommand(int index)

  {
    if (index < 0) return false;
    if (index >= commands.size()) return false;

    commands.remove(index);
    delayMins.remove(index);
    delayMaxs.remove(index);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void addCommand(String command, int delay)

  {
    commands.add(command);
    delayMins.add(new Integer(delay));
    delayMaxs.add(new Integer(delay));
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void addCommand(String command, int min, int max)

  {
    commands.add(command);
    delayMins.add(new Integer(min));
    delayMaxs.add(new Integer(max));
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void executeAction()

  {
    if (random) executeRandomAction();
    else executeNormalAction();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  private void executeRandomAction()

  {
    currentCommand = Utility.getRandomInt(0, commands.size()-1);
    String command = (String)commands.get(currentCommand);

    int mn = getDelayMin(currentCommand);
    int mx = getDelayMax(currentCommand);
    delay = Utility.getRandomInt(mn, mx);

    if (owner != null) user = owner;
    else user = ghost;

    Interpreter.interpretCommand(user, command);
    if (!repeats) done = true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  private void executeNormalAction()

  {
    int mn = getDelayMin(currentCommand);
    int mx = getDelayMax(currentCommand);
    delay = Utility.getRandomInt(mn, mx);

    String command = (String)commands.get(currentCommand);

    if (owner != null) user = owner;
    else user = ghost;

    Interpreter.interpretCommand(user, command);
    currentCommand++;

    if (currentCommand == commands.size()) {
      if (!repeats) done = true;
      currentCommand = 0; }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public boolean tryAction()

  {
    Evaluator.checkHostile = hostile;
    return tryActionCommand();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  private boolean tryActionCommand()

  {
    if (commands.size() == 0) return false;

    counter++;

    if (counter < delay) return false;

    executeAction();
    counter = 0;

    if ((!done) && (delay == 0)) tryActionCommand();

    if (owner == null) done = false;

    if (done) return true;
    return false;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public boolean isInfinite()

  {
    if (!repeats) return false;

    int size = delayMins.size();

    for (int i=0; i<size; i++)
      if (getDelayMin(i) > 0)
        return false;

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void updateResponses(Action[] oldList, Action[] newList)

  {
    int newSize = newList.length;
    int oldSize = oldList.length;

    for (int i=0; i<newSize; i++)
    for (int j=0; j<oldSize; j++)
      if (oldList[j].getCustom().equals(newList[i].getCustom()))
        newList[i] = oldList[j];
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ID");         out.writeObject(new Integer(id));
      out.writeObject("NAME");       out.writeObject(name);
      out.writeObject("COMMANDS");   out.writeObject(commands);
      out.writeObject("DELAYMINS");  out.writeObject(delayMins);
      out.writeObject("DELAYMAXS");  out.writeObject(delayMaxs);
      out.writeObject("RANDOM");     out.writeObject(new Boolean(random));
      out.writeObject("REPEATS");    out.writeObject(new Boolean(repeats));
      out.writeObject("HOSTILE");    out.writeObject(new Boolean(hostile));
      out.writeObject("CUSTOM");     out.writeObject(custom);
      out.writeObject("ACTION END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("ACTION END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("ID"))                id = ((Integer)in.readObject()).intValue();
        else if (tag.equals("NAME"))         name = ((String)in.readObject());
        else if (tag.equals("COMMANDS"))     commands = (ArrayList)in.readObject();
        else if (tag.equals("DELAYMINS"))    delayMins = (ArrayList)in.readObject();
        else if (tag.equals("DELAYMAXS"))    delayMaxs = (ArrayList)in.readObject();
        else if (tag.equals("RANDOM"))       random = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("REPEATS"))      repeats = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("HOSTILE"))      hostile = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("CUSTOM"))       custom = (String)in.readObject();
        else if (!tag.equals("ACTION END"))  in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////
}