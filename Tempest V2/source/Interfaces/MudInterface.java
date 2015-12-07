import java.io.*;
import java.util.*;

public abstract class MudInterface extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int NO_OUTPUT  = 1;
  public static final int ALL_OUTPUT = 2;
  public static final int UNDECIDED  = 3;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  protected String output;
  protected String topSpace;
  protected boolean active;
  protected Client myClient;
  protected MessageQueue normalOutput;
  protected MessageQueue fightOutput;
  protected MessageQueue skillOutput;
  protected boolean normalRedirect;
  protected boolean combatRedirect;
  protected boolean skillRedirect;
  protected LinkedList utilityQueue;
  protected ArrayList QList;
  protected String menuMsg;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public abstract int viewMode();
  public abstract void addOutput(String s);
  public abstract void addFightOutput(String s);
  public abstract void handleInput();
  public abstract void handleOutput();
  public abstract void onDisconnect();
  public abstract String getPrompt();
  public abstract void focusGained();
  public abstract void exit();

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean getActive()               { return active;                      }
  public void setActive(boolean b)         { active = b;                         }
  public void stackingOn()                 { normalOutput.startStacking();       }
  public void stackingOff()                { normalOutput.stopStacking();        }
  public void freezeFightOutput()          { fightOutput.freeze();               }
  public void setNormalRedirect(boolean b) { normalRedirect = b;                 }
  public void setCombatRedirect(boolean b) { combatRedirect = b;                 }
  public void setSkillRedirect(boolean b)  { skillRedirect = b;                  }
  public Entity getCharInfo()              { return myClient.getCharInfo();      }
  public MessageQueue getQueue(int i)      { return (MessageQueue) QList.get(i); }
  public void errorMsg(String msg)         { menuMsg = "#r" + msg;               }
  public void goodMsg(String msg)          { menuMsg = "#g" + msg;               }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public MudInterface(Client C)

  {
    myClient = C;
    active = false;
    menuMsg = "";

    normalOutput = new MessageQueue();
    fightOutput  = new MessageQueue();
    skillOutput  = new MessageQueue();
    utilityQueue = new LinkedList();

    QList = new ArrayList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Char getChar()

  {
    return myClient.getCharInfoBackup().castChar();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  protected void putOutput(MessageQueue queue, String s)

  {
    if (!QList.contains(queue)) QList.add(queue);
    queue.put(s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearOutputBuffers()

  {
    normalOutput.clear();
    fightOutput.clear();
    skillOutput.clear();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  protected void sendQueue(MessageQueue queue)

  {
    if (queue.isEmpty()) return;

    if ((active) && (!getChar().getAnsiMode())) topSpace = "";
    else topSpace = "\r\n";

    if (!getChar().getAnsiMode()) output = topSpace + queue.flush() + "\r\n\n";
    else output = topSpace + queue.flush();

    active = false;
    myClient.msg(output + getPrompt());
    output = "";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String tryPrompt()

  {
    if (utilityQueue.isEmpty()) return getPrompt();
    return "";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}