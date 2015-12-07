public class Cleric extends Char

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public final static int HP_MULT  = 13;
  public final static int MN_MULT  = 18;
  public final static int MV_MULT  = 18;
  public final static int START_HP = 120;
  public final static int START_MN = 119;
  public final static int START_MV = 120;
  public final static String CLASS_COLOR = "#G";

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getCharClass()      { return "Cleric";                 }
  public String getSClass()         { return "Cl";                     }
  public String getCharClassColor() { return CLASS_COLOR + "Cleric#N"; }
  public String getSClassColor()    { return CLASS_COLOR + "Cl#N";     }
  public boolean isWarrior()        { return false;                    }
  public boolean isThief()          { return false;                    }
  public boolean isMage()           { return false;                    }
  public boolean isCleric()         { return true;                     }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Cleric(Client C)

  {
    this();
    myClient = C;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Cleric()

  {
    super();

    maxHP = START_HP;
    maxMN = START_MN;
    maxMV = START_MV;
    STR = 1;
    DEX = 1;
    CON = 1;
    INT = 2;
    WIS = 5;

    restore();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void levelUp()

  {
    if (level < 99)

    {
      level++;
      dPoints += 5;
      setExpNeeded();

      maxHP += (HP_MULT + (CON/10));
      maxMN += (MN_MULT + (WIS/10));
      maxMV += (MV_MULT + (CON/20));

      getClient().addFightOutput("#gYour level has increased!#N");
      WriteThread.addObject(replicate());
    }

    else

    {
      experience = 0;
      expNeeded = 0;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void regen()

  {
    if (poisoned) return;

    int CON_MAX = getCON();  if (getCON() > 200) CON_MAX = 200;
    int WIS_MAX = getWIS();  if (getWIS() > 200) WIS_MAX = 200;

    float h_percent = (float)(0.10) + ((float)CON_MAX/2000);
    float m_percent = (float)(0.10) + ((float)WIS_MAX/2000);
    float v_percent = (float)(0.10) + ((float)CON_MAX/2000);

    int h = (int)((float)(getMaxHP()+10) * h_percent);
    int m = (int)((float)(getMaxMN()+10) * m_percent);
    int v = (int)((float)(getMaxMV()+10) * v_percent);

    setCurrentHP(getCurrentHP()+h);
    setCurrentMN(getCurrentMN()+m);
    setCurrentMV(getCurrentMV()+v);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}