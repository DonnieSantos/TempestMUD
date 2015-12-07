import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public abstract class Char extends Entity implements Cloneable

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static final long serialVersionUID = 13;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  protected int experience, expNeeded;
  protected int lastExperience, dPoints;
  protected int clanID;
  protected int religionID;
  protected int screenSize;
  protected String accountName;
  protected String creationDate;
  protected String lastLogin;
  protected String fullname;
  protected String prename;
  protected String hometown;
  protected String religionName;
  protected String myMentor;
  protected String myStudent;
  protected String myRequest;
  protected String dietyTitle;
  protected String snooping;
  protected String myPrompt;
  protected Legend myLegend;
  protected boolean ansiMode;
  protected boolean colorMode;
  protected Writable plan;
  protected AliasList myAliases;
  protected ArrayList students;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public abstract void regen();
  public abstract void levelUp();
  public abstract String getSClass();
  public abstract String getCharClass();
  public abstract String getSClassColor();
  public abstract String getCharClassColor();
  public abstract boolean isWarrior();
  public abstract boolean isThief();
  public abstract boolean isMage();
  public abstract boolean isCleric();

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean isOnline()              { return (myClient != null);                }
  public boolean isPlayer()              { return true;                              }
  public boolean isGhost()               { return false;                             }
  public void creationStamp()            { creationDate = SystemTime.getTimeStamp(); }
  public void loginStamp()               { lastLogin = SystemTime.getTimeStamp();    }
  public void setAccountName(String s)   { accountName = s;                          }
  public void setFullName(String s)      { fullname = s;                             }
  public void setDietyTitle(String s)    { dietyTitle = s;                           }
  public void setPlan(String s)          { plan.write(s);                            }
  public void setHometown(String s)      { hometown = s;                             }
  public void setPrename(String s)       { prename = s;                              }
  public void setDPoints(int i)          { dPoints = i;                              }
  public void setExp(int i)              { experience = i;                           }
  public void setClan(int i)             { clanID = i;                               }
  public void setReligion(int i)         { religionID = i;                           }
  public void setColorMode(boolean b)    { colorMode = b;                            }
  public void setFollowable(boolean b)   { followable = b;                           }
  public void setAutoassist(boolean b)   { autoassist = b;                           }
  public void addLegendMark(String s)    { getLegend().addMark(s);                   }
  public void setStudent(String s)       { myStudent = s;                            }
  public void setRequest(String s)       { myRequest = s;                            }
  public void setMentor(String s)        { myMentor = s;                             }
  public void setSnooping(String s)      { snooping = s;                             }
  public void setEquipment(Item[] E)     { equipment = E;                            }
  public void setPlan(Writable W)        { plan = W;                                 }
  public void setAliasList(AliasList AL) { myAliases = AL;                           }
  public void setSchedule(Schedule S)    { schedule = S;                             }
  public void setSkillList(ArrayList A)  { skillList = A;                            }
  public void setSpellList(ArrayList A)  { spellList = A;                            }
  public void setEffectList(ArrayList A) { effectList = A;                           }
  public int getExperience()             { return experience;                        }
  public int getTrueResistance()         { return (int)(getMR() * RESISTANCE_CAP);   }
  public String getAccountName()         { return accountName;                       }
  public String getFullName()            { return name;                              }
  public String getTitle()               { return title;                             }
  public String getDietyTitle()          { return dietyTitle;                        }
  public String getPlan()                { return plan.getString();                  }
  public String getPrename()             { return prename;                           }
  public String getHometown()            { return hometown;                          }
  public String getMentor()              { return myMentor;                          }
  public String getStudent()             { return myStudent;                         }
  public String getRequest()             { return myRequest;                         }
  public String getStudent(int i)        { return (String) students.get(i);          }
  public String getSnooping()            { return snooping;                          }
  public String getPrompt()              { return myPrompt;                          }
  public Writable getPlanWrite()         { return plan;                              }
  public AliasList getAliases()          { return myAliases;                         }
  public int getScreenSize()             { return screenSize;                        }
  public boolean getAnsiMode()           { return ansiMode;                          }
  public boolean getColorMode()          { return colorMode;                         }
  public boolean getFollowable()         { return followable;                        }
  public boolean getAutoassist()         { return autoassist;                        }
  public Clan getClan()                  { return World.getClan(clanID);             }
  public Religion getReligion()          { return World.getReligion(religionID);     }
  public Legend getLegend()              { return myLegend;                          }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Char()

  {
    name           = "LOGGING IN";
    fullname       = "";
    accountName    = "";
    creationDate   = "";
    lastLogin      = "";
    description    = new Writable("Generic description", Writable.FORMAT_DESC);
    plan           = new Writable("Generic plan", Writable.FORMAT_EXACT);
    gender         = "male";
    prename        = "";
    title          = "the Newbie";
    dietyTitle     = "None";
    hometown       = "Damascus";
    religionName   = "";
    myClient       = null;
    currentHP      = 100;
    currentMN      = 100;
    currentMV      = 100;
    level          = 100;
    dPoints        = 0;
    lastExperience = -1;
    experience     = 0;
    maxHP          = 100;
    maxMN          = 100;
    maxMV          = 100;
    playerState    = PSTATE_STANDING;
    alignment      = ALIGN_NEUTRAL;
    HR             = 0;
    DR             = 0;
    AC             = 0;
    MR             = 0;
    gold           = 0;
    clanID         = -1;
    religionID     = -1;

    STR = 0;
    DEX = 0;
    CON = 0;
    INT = 0;
    WIS = 0;
    HR  = 0;
    DR  = 0;
    AC  = 0;
    MR  = 0;

    EQ_HP = 0;
    EQ_MN = 0;
    EQ_MV = 0;

    SANCD = false;
    poisoned = false;

    screenSize = 25;
    ansiMode   = false;
    colorMode  = true;
    followable = true;
    autoassist = false;

    inventory = new ItemList(INV_START_SIZE, ItemList.TYPE_INVENTORY);
    equipment = new Item[Equipment.NUM_PLACES];
    myAliases = new AliasList(this);

    following = this;
    followers = new ArrayList();
    myGroup = new Group(this);
    targetList = new ArrayList();
    intermittentSkill = 0;

    myTarget = null;
    myTrack = null;
    trackDir = -1;

    spellList  = new ArrayList();
    skillList  = new ArrayList();
    effectList = new ArrayList();
    students   = new ArrayList();

    myLegend = new Legend(this);
    schedule = new Schedule();

    myStudent = "";
    myRequest = "";
    myMentor  = "";
    snooping  = "";

    blockerType  = -1;
    blockerDir   = -1;
    blockerParam = "";

    setExpNeeded();
    initPrompt();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean exists(String cname)

  {
    return (World.findCharacter(cname) != null);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean fileExists(String cname)

  {
    return FileManager.charFileExists(cname);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void die(boolean diedInCombat, boolean makeCorpse)

  {
    SystemLog.doubleLog(this, "was killed.");

    stopFighting();
    stopTracking();

    if (getLevel() > 99) makeCorpse = false;

    if (makeCorpse) Create.item(ItemUse.makeCorpse(this), getRoom(), ICREATECORPSE);

    getRoom().leave(this, LEAVE_DIE);
    World.findHometown(this).enter(this, ENTER_REBORN);

    look("\r\n#cBy the grace of Genevieve, you are revived and transported to safety!#N");
    setCurrentHP(1);

    save();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void save()

  {
    WriteThread.addObject(replicate());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void saveCharacter()

  {
    WriteThread.addObject(replicate());
    echo("#nSaving Character Information.#N");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void deleteCharacter()

  {
    if (clanID != -1) World.getClan(clanID).removeMember(name);
    if (religionID != -1) World.getReligion(religionID).removeMember(name);

    for (int i=0; i<World.MAX_BANKS; i++)
    if (World.getBank(i) != null)
      World.getBank(i).closeAccount(name);

    WriteThread.msg("deletecharacter " + getName());
    Account A = World.findAccount(accountName);
    World.removeCharacter(name);
    AccessManager.manager.removeCharFreeze(this);
    A.updateCharacters();
    A.save();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void quit()

  {
    if (playerState == PSTATE_FIGHTING) {
      echo("You can't quit in the middle of combat.");
      return; }

    SystemLog.doubleLog(this, "has quit the game.");
    sendToAwake(getName() + " has left the game.");
    myClient.setClientState(CSTATE_MENU);
    myClient.popInterface();
    resetVars();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void resetVars()

  {
    stopFighting();
    stopTracking();
    decrementEffectsOnExit();
    terminateGroupAndFollowers(false);
    getRoom().leave(this, LEAVE_QUIT);

    stopSnooping();
    myClient.clearSnoopers();

    playerState = PSTATE_STANDING;
    intermittentSkill = 0;
    myStudent = "";
    myRequest = "";
    snooping  = "";

    save();
    myClient = null;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void enterGame()

  {
    loginStamp();
    myClient.setClientState(CSTATE_NORMAL);
    if (ansiMode) myClient.clearScreen(false);
    getRoom().enter(this, ENTER_LOGIN);
    SystemLog.doubleLog(this, "entering game.");
    myClient.pushInterface(new PlayerInterface(myClient));
    look("#cWelcome To The Tempest!#N");
    sendToAwake(getName() + " has entered the game.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void decrementEffectsOnExit()

  {
    for (int i=0; i<effectList.size(); i++)
      getEffect(i).quitDecrement();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void initPrompt()

  {
    myPrompt  = "#c< #g";
    myPrompt += "hp#Ghp #m";
    myPrompt += "mn#Mmn #b";
    myPrompt += "mv#Bmv #c>#N";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void setPrompt(String s)

  {
    if (s.length() == 0)

    {
      echo("#NPrompt Usage#n: #R[#Nprompt <format>#R]#N");
      echo("#N\r\nInsertable variables#n: %hp %mn %mv %mhp %mmn %mmv %e#N");
      echo("#N\r\nExample Usage#n:#N 'prompt < %hp / %mhp >'");
      return;
    }

    myPrompt = s;
    echo("Ok, your prompt is now: " + myPrompt);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void stopSnooping()

  {
    if (snooping.length() == 0) return;
    Entity ENT = ClientList.findCharacter(snooping);
    if (ENT == null) return;
    ENT.getClient().removeSnooper(myClient);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void purged(Entity SRC, int type)

  {
    if (type == PURGE_NORMAL)

    {
      SRC.echo("#rYou purge " + name + " with a blast of fire!#N");
      if (isOnline()) getRoom().xecho(SRC, name + " has been purged by the Gods.", ECHO_AWAKE);
    }

    if (type == PURGE_FREEZE)

    {
      String str =  "#c" + SRC.getName() + " #cpoints at " + name + " #cand encases ";
             str += SRC.him() + " in a huge block of ice!#N";

      SRC.echo("#cYou have encased " + name + " in a block of ice!#N");
      if (isOnline()) getRoom().xecho(SRC, str, ECHO_AWAKE);
    }

    if (type == PURGE_DISINTEGRATE)

    {
      SRC.echo("#RYou disintegrate " + name + " with a torrent of lava!#N");
      String s = " disintegrates " + name + " with a torrent of lava!#N";
      if (isOnline()) getRoom().xecho(SRC, "#R" + SRC.getName() + s);
    }

    if (isOnline())

    {
      getClient().disconnect();
      ClientList.queueClientRemove(getClient());
      resetVars();
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public int getClanNum()

  {
    if ((clanID < 0) || (clanID >= World.getClanSize())) clanID = -1;
    return clanID;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public int getReligionNum()

  {
    if (level >= 100) return -1;

    if ((religionID < 0) || (religionID >= World.getReligionSize()))
      religionID = -1;

    return religionID;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void updateMemberships(String oldName)

  {
    boolean Save = false;

    int c = getClanNum();
    int r = getReligionNum();

    if ((c != -1) && (World.getClan(c) == null))     { setClan(-1);      c = -1;  Save = true; }
    if ((r != -1) && (World.getReligion(r) == null)) { setReligion(-1);  r = -1;  Save = true; }

    if (c != -1) World.getClan(c).updateMember(oldName, this, false);
    if (r != -1) World.getReligion(r).updateMember(oldName, this, false);

    if (Save) save();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void updateObjectPointers()

  {
    myAliases.setOwner(this);
    myLegend.setOwner(this);

    for (int i=0; i<skillList.size(); i++)   getSkill(i).init(this, getSkill(i).getLevel());
    for (int i=0; i<spellList.size(); i++)   getSpell(i).init(this, getSpell(i).getLevel());
    for (int i=0; i<effectList.size(); i++)  getEffect(i).setOwner(this);

    inventory.setEntity(this);

    for (int i=0; i<Equipment.NUM_PLACES; i++)
    if (equipment[i] != null)
      equipment[i].setEntity(this);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Char setClass(String s)

  {
    try

    {
      Entity C = (Entity)Class.forName(s).newInstance();
      Field[] charFields = Char.class.getDeclaredFields();
      Field[] entFields = Entity.class.getDeclaredFields();

      for (int i=0; i<charFields.length; i++)
      if (!Modifier.isFinal(charFields[i].getModifiers()))
      if (!Modifier.isStatic(charFields[i].getModifiers()))
        charFields[i].set(C, charFields[i].get(this));

      for (int i=0; i<entFields.length; i++)
      if (!Modifier.isFinal(entFields[i].getModifiers()))
      if (!Modifier.isStatic(entFields[i].getModifiers()))
        entFields[i].set(C, entFields[i].get(this));

      clearFollowers();
      C.clearFollowers();

      stopFighting();
      setFollowing(this);
      setGroup(new Group(this));
      C.stopFighting();
      C.setFollowing(C);
      C.setGroup(new Group(C));

      C.setClient(myClient);

      if (myClient != null)

      {
        myClient.setCharInfo(C);
        myClient.setCharInfoBackup(C);

        Room R = getRoom();
        R.leave(this, LEAVE_NEWCLASS);
        R.enter(C, ENTER_NEWCLASS);
      }

      C.castChar().updateMemberships(C.getName());
      C.castChar().updateObjectPointers();

      return C.castChar();
    }

    catch (Exception e) { e.printStackTrace(); return null; }
    catch (Throwable t) { t.printStackTrace(); return null; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Char replicate()

  {
    try

    {
      Char C = (Char)clone();

      ArrayList eqReplicated = new ArrayList();
      Item[] eqList = new Item[Equipment.NUM_PLACES];
      for (int i=0; i<Equipment.NUM_PLACES; i++)

      {
        if (!eqReplicated.contains(equipment[i]))

        {
          if (equipment[i] == null) eqList[i] = null;
          else eqList[i] = equipment[i].replicate();
        }
        else eqList[i] = eqList[eqReplicated.indexOf(equipment[i])];
        eqReplicated.add(equipment[i]);
      }

      ArrayList newSkills = new ArrayList(skillList.size());
      for (int i=0; i<skillList.size(); i++)
        newSkills.add(((Skill)skillList.get(i)).replicate());

      ArrayList newSpells = new ArrayList(spellList.size());
      for (int i=0; i<spellList.size(); i++)
        newSpells.add(((Spell)spellList.get(i)).replicate());

      ArrayList newEffects = new ArrayList(effectList.size());
      for (int i=0; i<effectList.size(); i++)
        newEffects.add(((Effect)effectList.get(i)).replicate());

      C.setInventory(inventory.replicate());
      C.setEquipment(eqList);
      C.setDesc(description.replicate());
      C.setPlan(plan.replicate());
      C.setAliasList(myAliases.replicate());
      C.setSchedule(schedule.replicate());
      C.setSkillList(newSkills);
      C.setSpellList(newSpells);
      C.setEffectList(newEffects);

      return C;
    }

    catch (Throwable t) { return new Warrior(); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void gainExperience(int num)

  {
    if (level < 99)

    {
      if (num > (expNeeded / 100))
        num = (expNeeded / 100);

      experience += num;
      lastExperience = num;

      if (experience >= expNeeded) levelUp();
      else getClient().addFightOutput("#yYou receive " + num + " experience!#N");
    }

    else experience = 0;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void receiveExperience(Entity target)

  {
    if (target.isPlayer()) return;
    Mobile m = target.castMob();

    int total_exp = m.getExperience();
    int percentage = getExpPercent(getRoom().getGroupsize(myGroup));
    int shared_exp = ((total_exp * percentage) / 100);

    myGroup.divideExperience(getRoom(), shared_exp);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void setExpNeeded()

  {
    experience = 0;
    expNeeded = getExpNeeded(level);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void addStat(Entity SRC, String str)

  {
    String output = "";

    if (dPoints <= 0) {
      SRC.echo("You do not have any stat points to spend.");
      return; }

    if (str.equals("str")) {
      dPoints--; STR++;
      output = "#gYou feel stronger!#N"; }

    else if (str.equals("dex")) {
      dPoints--; DEX++;
      output = "#gYour dexterity has improved!#N"; }

    else if (str.equals("con")) {
      dPoints--; CON++;
      output = "#gA warm rush of vitality fills you!#N"; }

    else if (str.equals("int")) {
      dPoints--; INT++;
      output = "#gArcane power surges through you!#N"; }

    else if (str.equals("wis")) {
      dPoints--; WIS++;
      output = "#gYou are blessed with understanding and insight!#N"; }

    else {
      output += "Add Usage#n:#N #R[#Nadd str#R] [#Nadd dex#R] ";
      output += "[#Nadd con#R] [#Nadd int#R] [#Nadd wis#R]#N"; }

    SRC.echo(output);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean addStudent(String studentName)

  {
    for (int i=students.size()-1; i>=0; i--)
      if (getStudent(i).equalsIgnoreCase(studentName))
        return false;

    students.add(studentName);
    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean checkTargets(String str)

  {
    return (shortFor(str.toLowerCase(), name.toLowerCase()));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public String side(int line)

  {
    if (getNumEffects() == 0) return "";

    if (line == 0) return "#n-=-=-=-=-+#N";
    if (line == 15) return "#n-=-=-=-=-+#N";
    if (getNumEffects() < line) return "         #n|#N";

    Effect E = getEffect(line-1);

    String sign = "#N " + E.getSign() + "#N ";

    if (E.getPermanent()) sign += "  A #n|#N";
    else sign += tenSpace(E.getDuration(),2) + E.getDuration() + " #n|#N";

    return sign;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void stat()

  {
    String nm = xchars(30-(int)name.length()," ");
    String hm = xchars(20-visibleSize(hometown)," ");
    String pf = xchars(20-(int)getCharClass().length()," ") + tenSpace(level,2);
    String stSTR = tenSpace(getSTR(),2);  String stHR = tenSpace(getHR(),2);
    String stDEX = tenSpace(getDEX(),2);  String stDR = tenSpace(getDR(),2);
    String stCON = tenSpace(getCON(),2);  String stAC = tenSpace(getAC(),3);
    String stINT = tenSpace(getINT(),2);  String stMR = tenSpace(getMR(),2);
    String stWIS = tenSpace(getWIS(),2);
    String xs = tenSpace(getCurrentHP(),3) + tenSpace(getMaxHP(),3) + " ";
    String ys = tenSpace(getCurrentMN(),3) + tenSpace(getMaxMN(),3) + " ";
    String zs = tenSpace(getCurrentMV(),3) + tenSpace(getMaxMV(),3) + " ";
    String exp  = expCommas("" + experience);
    String expn = expCommas("" + expNeeded);
    String gld  = expCommas("" + gold);
    String es = xchars(11-exp.length()," ");
    String ns = xchars(11-expn.length()," ");
    String gs = xchars(11-gld.length()," ");

    String ST = "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+";
    ST += side(0) + "#n\r\n  |  #C" + name + "#N" + nm + "#n+-------------------+";
    ST += "  |#N" + side(1) + "\r\n  #n|  #NLevel " + level + " " + getCharClass() + pf;
    ST += "#n[#N     STR - " + stSTR + getSTR() + "     #n]  |#N" + side(2) + "#n\r\n";
    ST += "  |  #NHometown#n:#N " + hometown + hm + "#n[     #NDEX - " + stDEX;
    ST += getDEX() + "     #n]  |#N" + side(3) + "#n\r\n  |                            ";
    ST += "    [     #NCON - " + stCON + getCON() + "     #n]  |#N" + side(4) + "#n\r\n";
    ST += "  |     +--------------------+     [     #NINT - " + stINT + getINT() + "   ";
    ST += "  #n]  |#N" + side(5) + "#n\r\n  |     [#N Health = #g" + getCurrentHP();
    ST += "#N/#G" + getMaxHP() + xs + "#n]     [     #NWIS - " + stWIS + getWIS();
    ST += "     #n]  |#N" + side(6) + "#n\r\n  |     [   #NMana = #m" + getCurrentMN() + "#N/#M";
    ST += getMaxMN() + ys + "#n]     +-------------------+  |#N" + side(7) + "#n\r\n";
    ST += "  |     [   #NMove = #b" + getCurrentMV() + "#N/#B" + getMaxMV() + zs + "#n]   ";
    ST += "  [    #NHitroll#n:#N  " + stHR + getHR() + "  #n]  |#N" + side(8);
    ST += "#n\r\n  |     +--------------------+     [    #NDamroll#n:#N  " + stDR;
    ST += getDR() + "  #n]  |#N" + side(9) + "#n\r\n  |                              ";
    ST += "  [ #NArmorclass#n:#N " + stAC + getAC() + "  #n]  |#N" + side(10) + "#n\r\n";
    ST += "  |  +--------------------------+  [ #NResistance#n:#N  " + stMR + getMR();
    ST += "  #n]  |#N" + side(11) + "#n\r\n  |  [ #NExperience = #r" + es + exp;
    ST += " #n]  +-------------------+  |#N" + side(12) + "#n\r\n  |  [ #NNext";
    ST += " Level = #R" + ns + expn + " #n]  [ #NGold#n: #y" + gs + gld + " #n]";
    ST += "  |#N" + side(13) + "#n\r\n  |  +--------------------------+";
    ST += "  +-------------------+  |#N" + side(14) + "#n\r\n  ";
    ST += "+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N" + side(15);

    if (dPoints > 0)

    {
      String extra = tenSpace(dPoints,2);
      ST += "\r\n  #n+-------------------------------------------------------+";
      ST += "\r\n  | #NYou have #r" + dPoints + " #Nstat points to spend. ";
      ST += extra + "#N(Usage: #R[#Nadd STR#R]#N) #n|";
      ST += "\r\n  +-------------------------------------------------------+#N";
    }

    echo(ST);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public String getReport(int lev)

  {
    String rep = "";
    String cln = "None";
    String rlg = "None";
    int rid = getRoom().getID();

    Clan C = null;
    Religion R = null;
    if (clanID != -1) C = World.getClan(clanID);
    if (religionID != -1) R = World.getReligion(religionID);
    if (C != null) cln = C.getName();
    if (R != null) rlg = R.getName();

    String exp = expCommas("" + experience);
    String xpn = expCommas("" + expNeeded);
    String gld = expCommas("" + gold);

    String mentorName = myMentor;
    if (mentorName.length() == 0) mentorName = "None";

    rep += "  #n+-----------------------------------+-----------------------------------+\r\n";
    rep += "  |#Y Name      #N::  " + name + whiteSpace(20-name.length()) + "#n|#N ";
    rep += "#gCurrent HP    #N::  #g" + getCurrentHP() + tenSpace(getCurrentHP(),3) + "            #n|\r\n";
    rep += "  |#Y Account   #N::  " + accountName + whiteSpace(20-accountName.length()) + "#n|#N ";
    rep += "#GMaximum HP    #N::  #G" + getMaxHP() + tenSpace(getMaxHP(),3) + "            #n|\r\n";
    rep += "  |#Y Level     #N::  " + level + tenSpace(level,2) + "                 #n|#N ";
    rep += "#GNatural HP    #N::  #G" + getNaturalHP() + tenSpace(getNaturalHP(),3) + "            #n|\r\n";
    rep += "  |#Y Gender    #N::  " + gender + whiteSpace(20-gender.length()) + "#n|#N ";
    rep += "#mCurrent MN    #N::  #m" + getCurrentMN() + tenSpace(getCurrentMN(),3) + "            #n|\r\n";
    rep += "  |#Y Class     #N::  " + getCharClass() + whiteSpace(20-getCharClass().length()) + "#n|#N ";
    rep += "#MMaximum MN    #N::  #M" + getMaxMN() + tenSpace(getMaxMN(),3) + "            #n|\r\n";
    rep += "  |#Y Hometown  #N::  " + hometown + whiteSpace(20-visibleSize(hometown)) + "#n|#N ";
    rep += "#MNatural MN    #N::  #M" + getNaturalMN() + tenSpace(getNaturalMN(),3) + "            #n|\r\n";
    rep += "  |#Y Aligned   #N::  " + getAlignString() + whiteSpace(20-visibleSize(getAlignString())) + "#n|#N ";
    rep += "#bCurrent MV    #N::  #b" + getCurrentMV() + tenSpace(getCurrentMV(),3) + "            #n|\r\n";
    rep += "  |#Y Mentor    #N::  " + mentorName + whiteSpace(20-visibleSize(mentorName)) + "#n|#N ";
    rep += "#BMaximum MV    #N::  #B" + getMaxMV() + tenSpace(getMaxMV(),3) + "            #n|\r\n";
    rep += "  |#Y Prename   #N::  " + prename + whiteSpace(20-visibleSize(prename)) + "#n|#N ";
    rep += "#BNatural MV    #N::  #B" + getNaturalMV() + tenSpace(getNaturalMV(),3) + "            #n|\r\n";
    rep += "  |#Y Room      #N::  " + rid + tenSpace(rid,4) + "               #n";
    rep += "+---------------++------------------+\r\n";
    rep += "  |#Y Screen    #N::  " + screenSize + tenSpace(screenSize,2) + "                 #n|#N ";
    rep += "#YSTR  #N::  " + getSTR() + tenSpace(getSTR(),2) + "  #n||#Y  HR    #N::  " + getHR() + tenSpace(getHR(),3) + "  #n|\r\n";
    rep += "  |#Y ANSI      #N::  " + onOff(ansiMode) + "                 #n|#N ";
    rep += "#YDEX  #N::  " + getDEX() + tenSpace(getDEX(),2) + "  #n||#Y  DR    #N::  " + getDR() + tenSpace(getDR(),3) + "  #n|\r\n";
    rep += "  |#Y Color     #N::  " + onOff(colorMode) + "                 #n|#N ";
    rep += "#YCON  #N::  " + getCON() + tenSpace(getCON(),2) + "  #n||#Y  AC    #N::  " + getAC() + tenSpace(getAC(),4) + " #n|\r\n";
    rep += "  |#Y Follow    #N::  " + onOff(followable) + "                 #n|#N ";
    rep += "#YINT  #N::  " + getINT() + tenSpace(getINT(),2) + "  #n||#Y  MR    #N::  " + getMR() + tenSpace(getMR(),3) + "  #n|\r\n";
    rep += "  |#Y Assist    #N::  " + onOff(autoassist) + "                 #n|#N ";
    rep += "#YWIS  #N::  " + getWIS() + tenSpace(getWIS(),2) + "  #n||#R  Dpts  #N::  " + dPoints + tenSpace(dPoints,3) + "  #n|\r\n";
    rep += "  +-----------------------------------+---------------++------------------+\r\n";
    rep += "  |#Y Created   #N::  " + creationDate + whiteSpace(56-visibleSize(creationDate)) + "#n|\r\n";
    rep += "  |#Y Lastlog   #N::  " + lastLogin + whiteSpace(56-visibleSize(lastLogin)) + "#n|\r\n";
    rep += "  |#Y Title     #N::  " + title + whiteSpace(56-visibleSize(title)) + "#n|\r\n";
    rep += "  |#Y Clan      #N::  " + cln + whiteSpace(56-visibleSize(cln)) + "#n|\r\n";
    rep += "  |#Y Religion  #N::  " + rlg + whiteSpace(56-visibleSize(rlg)) + "#n|\r\n";
    rep += "  |#Y Exp       #N::  " + exp + whiteSpace(56-exp.length()) + "#n|\r\n";
    rep += "  |#Y Needed    #N::  " + xpn + whiteSpace(56-xpn.length()) + "#n|\r\n";
    rep += "  |#Y Gold      #N::  " + gld + whiteSpace(56-gld.length()) + "#n|\r\n";
    rep += "  |#Y Bank      #N::  " + gld + whiteSpace(56-gld.length()) + "#n|\r\n";
    rep += "  +-----------------------------------------------------------------------+#N";

    return rep;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String findPlan(String nm)

  {
    Entity ENT = World.findCharacter(nm);
    if (ENT == null) return "There is no such person.";
    return ENT.castChar().getFullPlan();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public String getFullPlan()

  {
    String rawPlan = getPlan();
    String cclass = getCharClassColor();
    String border = "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";
    String clrbrd = "  #n|                                                           |#N";

    String clanName = "None";

    if ((getClanNum() != -1) && (World.getClan(getClanNum()) != null))

    {
      clanName = World.getClan(getClanNum()).getName();
      clanName += " " + World.getClan(getClanNum()).getMemberRankName(getName(), getGender());
    }

    clanName += xchars(41-visibleSize(clanName)," ");

    String relgName = "None";

    if (getLevel() > 99) relgName = getDietyTitle();

    else if ((getReligionNum() != -1) && (World.getReligion(getReligionNum()) != null))

    {
      relgName = World.getReligion(getReligionNum()).getName();
      relgName += " " + World.getReligion(getReligionNum()).getMemberRankName(getName(), getGender());
    }

    relgName += xchars(41-visibleSize(relgName)," ");

    String fullPlan = border + "\r\n";

    fullPlan += "  #n|#N " + getName() + " " + getTitle();
    fullPlan += xchars(56-getName().length()-visibleSize(getTitle())," ") + " #n|#N\r\n";
    fullPlan += border + "\r\n";

    fullPlan += "  #n|#N Level " + getLevel() + tenSpace(getLevel(),2) + "      #n:#N ";
    fullPlan += cclass + xchars(40-visibleSize(cclass)," ") + " #n|#N\r\n";

    fullPlan += "  #n|#N Clantitle Rank#n :#N " + clanName + "#n|#N\r\n";
    fullPlan += "  #n|#N Religious Rank#n :#N " + relgName + "#n|#N\r\n";

    fullPlan += border + "\r\n" + clrbrd + "\r\n";

    while (rawPlan.length() > 0)

    {
      int ns = 57-visibleSize(getLine(rawPlan));
      String xs = xchars(ns, " ") + " #n|#N\r\n";
      fullPlan += "  #n|#N " + getLine(rawPlan) + xs;
      rawPlan = removeLine(rawPlan);
    }

    fullPlan += clrbrd + "\r\n" + border;

    return fullPlan;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void toggle(String str)

  {
    if (match(str, "a", "ansi")) setAnsiMode(!ansiMode);

    else if (match(str, "c", "color")) {
      colorMode = (!colorMode);
      if (colorMode) echo("Color is now enabled.");
      else echo("Color is now disabled."); }

    else if (match(str, "f", "follow")) {
      followable = (!followable);
      if (followable) echo("You can now be followed.");
      else echo("You can no longer be followed."); }

    else if (match(str, "a", "assist")) {
      autoassist = (!autoassist);
      if (autoassist) echo("You will now automatically assist group members.");
      else echo("You will no longer automatically assist group members."); }

    else

    {
      String toginfo;
      String E = "#gEnabled#N ";
      String D = "#rDisabled#N";

      toginfo  = "  #n+-------------------------+\r\n  ";
      toginfo += "|     #yToggle Settings#n     |\r\n  ";
      toginfo += "| #N----------------------#n  |\r\n  ";
      toginfo += "| #R[#Ntog ansi#R]#N     ";
      if (ansiMode) toginfo += E;  else toginfo += D;
      toginfo += " #n|\r\n  | #R[#Ntog color#R]#N    ";
      if (colorMode) toginfo += E;  else toginfo += D;
      toginfo += " #n|\r\n  | #R[#Ntog follow#R]#N   ";
      if (followable) toginfo += E;  else toginfo += D;
      toginfo += " #n|\r\n  | #R[#Ntog assist#R]#N   ";
      if (autoassist) toginfo += E;  else toginfo += D;
      toginfo += " #n|\r\n  +-------------------------+";

      echo(toginfo);
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void setAnsiMode(boolean ANSI_MODE)

  {
    ansiMode = ANSI_MODE;

    if (!ansiMode) myClient.msg(Ansi.maxMargin);

    myClient.clearScreen(false);

    if (ansiMode) echo("ANSI Display is now enabled.");
    else echo("ANSI Display is now disabled.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void setScreenSize(String numLines, boolean audible)

  {
    if (!number(numLines)) {
      echo("#NResize Usage#n:#N #R[#Nresize <num lines>#R]#N");
      return; }

    screenSize = Integer.parseInt(numLines);
    if (screenSize > MAX_SCREEN_SIZE) screenSize = MAX_SCREEN_SIZE;
    if (ansiMode) myClient.clearScreen(true);
    if (audible) echo("#NScreen size is now #n" + screenSize + "#N lines.#N");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("NAME");        out.writeObject(name);
      out.writeObject("FULLNAME");    out.writeObject(fullname);
      out.writeObject("ACCOUNTNAME"); out.writeObject(accountName);
      out.writeObject("CREATION");    out.writeObject(creationDate);
      out.writeObject("LASTLOGIN");   out.writeObject(lastLogin);
      out.writeObject("DESCRIPTION"); out.writeObject(description);
      out.writeObject("PLAN");        out.writeObject(plan);
      out.writeObject("GENDER");      out.writeObject(gender);
      out.writeObject("HOMETOWN");    out.writeObject(hometown);
      out.writeObject("TITLE");       out.writeObject(title);
      out.writeObject("DIETYTITLE");  out.writeObject(dietyTitle);
      out.writeObject("PRENAME");     out.writeObject(prename);
      out.writeObject("MENTOR");      out.writeObject(myMentor);
      out.writeObject("MYPROMPT");    out.writeObject(myPrompt);
      out.writeObject("ROOMID");      out.writeObject(new Integer(myRoom));
      out.writeObject("CURRENTHP");   out.writeObject(new Integer(currentHP));
      out.writeObject("CURRENTMN");   out.writeObject(new Integer(currentMN));
      out.writeObject("CURRENTMV");   out.writeObject(new Integer(currentMV));
      out.writeObject("MAXHP");       out.writeObject(new Integer(maxHP));
      out.writeObject("MAXMN");       out.writeObject(new Integer(maxMN));
      out.writeObject("MAXMV");       out.writeObject(new Integer(maxMV));
      out.writeObject("LEVEL");       out.writeObject(new Integer(level));
      out.writeObject("DPOINTS");     out.writeObject(new Integer(dPoints));
      out.writeObject("ALIGNMENT");   out.writeObject(new Integer(alignment));
      out.writeObject("STR");         out.writeObject(new Integer(STR));
      out.writeObject("DEX");         out.writeObject(new Integer(DEX));
      out.writeObject("CON");         out.writeObject(new Integer(CON));
      out.writeObject("INT");         out.writeObject(new Integer(INT));
      out.writeObject("WIS");         out.writeObject(new Integer(WIS));
      out.writeObject("HR");          out.writeObject(new Integer(HR));
      out.writeObject("DR");          out.writeObject(new Integer(DR));
      out.writeObject("AC");          out.writeObject(new Integer(AC));
      out.writeObject("MR");          out.writeObject(new Integer(MR));
      out.writeObject("EXP");         out.writeObject(new Integer(experience));
      out.writeObject("GOLD");        out.writeObject(new Integer(gold));
      out.writeObject("EQHP");        out.writeObject(new Integer(EQ_HP));
      out.writeObject("EQMN");        out.writeObject(new Integer(EQ_MN));
      out.writeObject("EQMV");        out.writeObject(new Integer(EQ_MV));
      out.writeObject("CLAN");        out.writeObject(new Integer(clanID));
      out.writeObject("RELIGION");    out.writeObject(new Integer(religionID));
      out.writeObject("POISONED");    out.writeObject(new Boolean(poisoned));
      out.writeObject("SCREENSIZE");  out.writeObject(new Integer(screenSize));
      out.writeObject("ANSIMODE");    out.writeObject(new Boolean(ansiMode));
      out.writeObject("COLORMODE");   out.writeObject(new Boolean(colorMode));
      out.writeObject("FOLLOWABLE");  out.writeObject(new Boolean(followable));
      out.writeObject("AUTOASSIST");  out.writeObject(new Boolean(autoassist));
      out.writeObject("EFFECTS");     out.writeObject(effectList);
      out.writeObject("SCHEDULE");    out.writeObject(schedule.getLongEvents());
      out.writeObject("LEGEND");      out.writeObject(myLegend);
      out.writeObject("INVENTORY");   out.writeObject(inventory);
      out.writeObject("EQUIPMENT");   out.writeObject(Arrays.asList(equipment));
      out.writeObject("ALIASES");     out.writeObject(myAliases);
      out.writeObject("SPELLS");      out.writeObject(spellList);
      out.writeObject("SKILLS");      out.writeObject(skillList);
      out.writeObject("STUDENTS");    out.writeObject(students);
      out.writeObject("PFILE END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("PFILE END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("NAME"))             setName((String)in.readObject());
        else if (tag.equals("FULLNAME"))    setFullName((String)in.readObject());
        else if (tag.equals("ACCOUNTNAME")) setAccountName((String)in.readObject());
        else if (tag.equals("CREATION"))    creationDate = (String)in.readObject();
        else if (tag.equals("LASTLOGIN"))   lastLogin = (String)in.readObject();
        else if (tag.equals("CLAN"))        setClan(((Integer)in.readObject()).intValue());
        else if (tag.equals("RELIGION"))    setReligion(((Integer)in.readObject()).intValue());
        else if (tag.equals("DESCRIPTION")) setDesc(((Writable)in.readObject()).getString());
        else if (tag.equals("PLAN"))        setPlan(((Writable)in.readObject()).getString());
        else if (tag.equals("ROOMID"))      setRoom(((Integer)in.readObject()).intValue());
        else if (tag.equals("GENDER"))      setGender((String)in.readObject());
        else if (tag.equals("HOMETOWN"))    setHometown((String)in.readObject());
        else if (tag.equals("TITLE"))       setTitle((String)in.readObject());
        else if (tag.equals("DIETYTITLE"))  setDietyTitle((String)in.readObject());
        else if (tag.equals("PRENAME"))     setPrename((String)in.readObject());
        else if (tag.equals("MYPROMPT"))    setPrompt((String)in.readObject());
        else if (tag.equals("MENTOR"))      setMentor((String)in.readObject());
        else if (tag.equals("MAXHP"))       setMaxHP(((Integer)in.readObject()).intValue());
        else if (tag.equals("MAXMN"))       setMaxMN(((Integer)in.readObject()).intValue());
        else if (tag.equals("MAXMV"))       setMaxMV(((Integer)in.readObject()).intValue());
        else if (tag.equals("CURRENTHP"))   currentHP = ((Integer)in.readObject()).intValue();
        else if (tag.equals("CURRENTMN"))   currentMN = ((Integer)in.readObject()).intValue();
        else if (tag.equals("CURRENTMV"))   currentMV = ((Integer)in.readObject()).intValue();
        else if (tag.equals("LEVEL"))       setLevel(((Integer)in.readObject()).intValue());
        else if (tag.equals("DPOINTS"))     setDPoints(((Integer)in.readObject()).intValue());
        else if (tag.equals("ALIGNMENT"))   setAlignment(((Integer)in.readObject()).intValue());
        else if (tag.equals("STR"))         setSTR(((Integer)in.readObject()).intValue());
        else if (tag.equals("DEX"))         setDEX(((Integer)in.readObject()).intValue());
        else if (tag.equals("CON"))         setCON(((Integer)in.readObject()).intValue());
        else if (tag.equals("INT"))         setINT(((Integer)in.readObject()).intValue());
        else if (tag.equals("WIS"))         setWIS(((Integer)in.readObject()).intValue());
        else if (tag.equals("HR"))          setHR(((Integer)in.readObject()).intValue());
        else if (tag.equals("DR"))          setDR(((Integer)in.readObject()).intValue());
        else if (tag.equals("AC"))          setAC(((Integer)in.readObject()).intValue());
        else if (tag.equals("MR"))          setMR(((Integer)in.readObject()).intValue());
        else if (tag.equals("EXP"))         setExp(((Integer)in.readObject()).intValue());
        else if (tag.equals("GOLD"))        setGold(((Integer)in.readObject()).intValue());
        else if (tag.equals("EQHP"))        setEQHP(((Integer)in.readObject()).intValue());
        else if (tag.equals("EQMN"))        setEQMN(((Integer)in.readObject()).intValue());
        else if (tag.equals("EQMV"))        setEQMV(((Integer)in.readObject()).intValue());
        else if (tag.equals("SCREENSIZE"))  screenSize = ((Integer)in.readObject()).intValue();
        else if (tag.equals("ANSIMODE"))    ansiMode = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("COLORMODE"))   colorMode = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("FOLLOWABLE"))  followable = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("AUTOASSIST"))  autoassist = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("POISONED"))    poisoned = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("EFFECTS"))     effectList = (ArrayList)in.readObject();
        else if (tag.equals("SCHEDULE"))    schedule.setEvents((Long[])in.readObject());
        else if (tag.equals("INVENTORY"))   inventory = (ItemList)in.readObject();
        else if (tag.equals("EQUIPMENT"))   equipment = (Item[])((List)in.readObject()).toArray();
        else if (tag.equals("ALIASES"))     myAliases = (AliasList)in.readObject();
        else if (tag.equals("SPELLS"))      spellList = (ArrayList)in.readObject();
        else if (tag.equals("SKILLS"))      skillList = (ArrayList)in.readObject();
        else if (tag.equals("STUDENTS"))    students = (ArrayList)in.readObject();
        else if (tag.equals("LEGEND"))      myLegend = (Legend)in.readObject();
        else if (!tag.equals("PFILE END"))  in.readObject();
      }

      if (getRoom() == null) setRoom(World.findHometown(this).getID());

      updateMemberships(getName());
      updateObjectPointers();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}