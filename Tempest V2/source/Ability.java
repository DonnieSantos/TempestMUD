import java.io.*;
import java.util.*;

abstract class Ability implements Cloneable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  protected int id;
  protected int level;
  protected int timesUsed;

  protected String name;
  protected String desc;
  protected String prof;
  protected int importance;
  protected int learnRate;

  protected ArrayList prereqs;
  protected ArrayList levreqs;
  protected int reqSTR;
  protected int reqDEX;
  protected int reqCON;
  protected int reqINT;
  protected int reqWIS;

  protected String selfOut;
  protected String targOut;
  protected String grupOut;
  protected String obsvOut;

  protected String mn;
  protected String my;
  protected String tn;
  protected String ty;

  protected String Mn;
  protected String My;
  protected String Tn;
  protected String Ty;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  abstract boolean isSkill();
  abstract boolean isSpell();

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getID()                   { return id;               }
  public int getLevel()                { return level;            }
  public int getSTR()                  { return reqSTR;           }
  public int getDEX()                  { return reqDEX;           }
  public int getCON()                  { return reqCON;           }
  public int getINT()                  { return reqINT;           }
  public int getWIS()                  { return reqWIS;           }
  public int getImp()                  { return importance;       }
  public String getDesc()              { return desc;             }
  public String getName()              { return name;             }
  public String getClasses()           { return prof;             }
  public ArrayList getPrereqs()        { return prereqs;          }
  public ArrayList getLevreqs()        { return levreqs;          }
 
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Ability()

  {
    // Used to polymorph skills and spells.
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}