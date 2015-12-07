public class Mobdesc implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int id;
  private int zoneID;
  private int STR, DEX, CON, INT, WIS;
  private int level, HP, MN, AC, MR, HR, DR;
  private int exp, gold, align, speed;
  private boolean MOBILE, AGGRO, BARD, NPC;
  private boolean SANCD, MERCHANT, BANKER;
  private boolean SKILLMASTER, SPELLMASTER;
  private String name;
  private String gender;
  private String title;
  private String desc;
  private String storeName;
  private String blockType;
  private String blockDir;

  private ArrayList skillList;
  private ArrayList spellList;
  private ArrayList skillFreq;
  private ArrayList spellFreq;
  private ArrayList actionList;
  private ArrayList actionFreq;
  private ArrayList targetList;
  private ArrayList itemList;
  private ArrayList itemFreq;
  private ArrayList wearList;
  private ArrayList wearFreq;
  private ArrayList sellList;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  // Do we need these?

  public String getSkill(int n) { return (String) skillList.get(n); }
  public String getSpell(int n) { return (String) spellList.get(n); }

  // Or just something like this?

  public ArrayList getSkills() { return skillList; }
  public ArrayList getSpells() { return spellList; }

  // Not sure if we need these either...

  public int getNumSkills()        { return skillList.size();  }
  public int getNumSpells()        { return spellList.size();  }
  public int getNumActions()       { return actionList.size(); }
  public int getNumTargets()       { return targetList.size(); }
  public int getNumItems()         { return itemList.size();   }
  public int getNumWears()         { return wearList.size();   }
  public int getNumSells()         { return sellList.size();   }

  // But we definitely need these:

  public int getID()               { return id;          }
  public int getZoneID()           { return zoneID;      }
  public int getLevel()            { return level;       }
  public int getSTR()              { return STR;         }
  public int getDEX()              { return DEX;         }
  public int getCON()              { return CON;         }
  public int getINT()              { return INT;         }
  public int getWIS()              { return WIS;         }
  public int getHP()               { return HP;          }
  public int getMN()               { return MN;          }
  public int getMV()               { return MV;          }
  public int getAC()               { return AC;          }
  public int getMR()               { return MR;          }
  public int getHR()               { return HR;          }
  public int getDR()               { return DR;          }
  public int getExp()              { return exp;         }
  public int getGold()             { return gold;        }
  public int getAlign()            { return align;       }
  public int getSpeed()            { return speed;       }
  public boolean getMOBILE()       { return MOBILE;      }
  public boolean getAGGRO()        { return AGGRO;       }
  public boolean getBARD()         { return BARD;        }
  public boolean getNPC()          { return NPC;         }
  public boolean getSANCD()        { return SANCD;       }
  public boolean getMERCHANT()     { return MERCHANT;    }
  public boolean getBANKER()       { return BANKER;      }
  public boolean getSKILLMASTER()  { return SKILLMASTER; }
  public boolean getSPELLMASTER()  { return SPELLMASTER; }
  public String getName()          { return name;        }
  public String getGender()        { return gender;      }
  public String getTitle()         { return title;       }
  public String getDesc()          { return desc;        }
  public String getStoreName()     { return storeName;   }
  public String getBlockType()     { return blockType;   }
  public String getBlockDir()      { return blockDir;    }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Mobdesc()

  {
    skillList   =  new ArrayList();
    spellList   =  new ArrayList();
    skillFreq   =  new ArrayList();
    spellFreq   =  new ArrayList();
    actionList  =  new ArrayList();
    actionFreq  =  new ArrayList();
    targetList  =  new ArrayList();
    itemList    =  new ArrayList();
    itemFreq    =  new ArrayList();
    wearList    =  new ArrayList();
    wearFreq    =  new ArrayList();
    sellList    =  new ArrayList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}