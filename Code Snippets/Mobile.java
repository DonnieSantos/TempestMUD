import java.io.*;
import java.util.*;

public class Mobile extends Entity implements Cloneable

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static final long serialVersionUID = 13;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int NUM_FLAGS         =  16;
  public static final int NUM_RESPONSES     =  1;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int FLAG_MOBILE       =  0;
  public static final int FLAG_AGGRO        =  1;
  public static final int FLAG_BARD         =  2;
  public static final int FLAG_NPC          =  3;
  public static final int FLAG_MERCHANT     =  4;
  public static final int FLAG_BANKER       =  5;
  public static final int FLAG_HERBALIST    =  6;
  public static final int FLAG_SKILLMASTER  =  7;
  public static final int FLAG_SPELLMASTER  =  8;
  public static final int FLAG_GUARD        =  9;
  public static final int FLAG_BLOCKER      =  10;
  public static final int FLAG_CLERGY       =  11;
  public static final int FLAG_TEACHER      =  12;
  public static final int FLAG_FLYING       =  13;
  public static final int FLAG_UNDEAD       =  14;
  public static final int FLAG_SANCD        =  15;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int RESPONSE_AGGRO    =  0;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int ID;
  private int zoneID;
  private int speed;
  private int experience;
  private int ecount;
  private Flag[] flagList;
  private String keywords;
  private String storeName;
  private String blockType;
  private String blockDir;
  private String fullSay;
  private String[] exits;
  private boolean acted;
  private ArrayList ipopList;
  private ArrayList ipopFreq;
  private ArrayList wearList;
  private ArrayList wearFreq;
  private ArrayList sellList;
  private ArrayList sayMemory;
  private ArrayList questions;
  private ArrayList actions;
  private ArrayList actionInts;
  private Action[] responseList;
  private PopMember popMember;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public String getFullName()                   { return name;                        }
  public String getTitle()                      { return "#Y" + title + "#N";         }
  public String getCharClass()                  { return "";                          }
  public String getSClass()                     { return "";                          }
  public String getCharClassColor()             { return "";                          }
  public String getSClassColor()                { return "";                          }
  public boolean isPlayer()                     { return false;                       }
  public boolean isGhost()                      { return false;                       }
  public boolean getActed()                     { return acted;                       }
  public void setFullName(String s)             { return;                             }
  public void receiveExperience(Entity target)  { return;                             }
  public void gainExperience(int num)           { return;                             }
  public int getClanNum()                       { return -1;                          }
  public int getReligionNum()                   { return -1;                          }
  public void setClan(int i)                    { return;                             }
  public void setReligion(int i)                { return;                             }
  public void setExperience(int i)              { experience = i;                     }
  public void setKeywords(String s)             { keywords = s;                       }
  public void setID(int i)                      { ID = i;                             }
  public void setZoneID(int i)                  { zoneID = i;                         }
  public void setSpeed(int i)                   { speed = i;                          }
  public void setStoreName(String s)            { storeName = s;                      }
  public void setActed(boolean b)               { acted = b;                          }
  public void setPopMember(PopMember P)         { popMember = P;                      }
  public int getSpeed()                         { return speed;                       }
  public int getID()                            { return ID;                          }
  public int getZoneID()                        { return zoneID;                      }
  public int getNumIpops()                      { return ipopList.size();             }
  public int getNumWears()                      { return wearList.size();             }
  public int getNumSells()                      { return sellList.size();             }
  public int getNumSkills()                     { return skillList.size();            }
  public int getNumSpells()                     { return spellList.size();            }
  public int getExperience()                    { return experience;                  }
  public int getTrueResistance()                { return getMR();                     }
  public String getStoreName()                  { return storeName;                   }
  public String getBlockType()                  { return blockType;                   }
  public String getBlockDir()                   { return blockDir;                    }
  public String getKeywords()                   { return keywords;                    }
  public boolean getFlag(int i)                 { return flagList[i].isEnabled();     }
  public Flag[] getFlags()                      { return flagList;                    }
  public void setIpopList(ArrayList A)          { ipopList = A;                       }
  public void setIpopFreq(ArrayList A)          { ipopFreq = A;                       }
  public void setWearList(ArrayList A)          { wearList = A;                       }
  public void setWearFreq(ArrayList A)          { wearFreq = A;                       }
  public void setSellList(ArrayList A)          { sellList = A;                       }
  public void setItemList(ArrayList A)          { itemList = A;                       }
  public void setSayMemory(ArrayList A)         { sayMemory = A;                      }
  public void setQuestions(ArrayList A)         { questions = A;                      }
  public void setEquipment(Item[] I)            { equipment = I;                      }
  public void setEffectList(ArrayList A)        { effectList = A;                     }
  public void setActions(ArrayList A)           { actions = A;                        }
  public void setExits(String[] S)              { exits = S;                          }
  public void setSkills(ArrayList A)            { skillList = A;                      }
  public void setSpells(ArrayList A)            { spellList = A;                      }
  public void setActionInts(ArrayList A)        { actionInts = A;                     }
  public void setTargetList(ArrayList A)        { targetList = A;                     }
  public void setFollowers(ArrayList A)         { followers = A;                      }
  public void setResponses(Action[] A)          { responseList = A;                   }
  public void setFlags(Flag[] F)                { flagList = F;                       }
  public void setResponse(int i, Action A)      { responseList[i] = A;                }
  public ArrayList getSellList()                { return sellList;                    }
  public ArrayList getIpopList()                { return ipopList;                    }
  public ArrayList getIpopFreq()                { return ipopFreq;                    }
  public ArrayList getWearList()                { return wearList;                    }
  public ArrayList getWearFreq()                { return wearFreq;                    }
  public ArrayList getActions()                 { return actions;                     }
  public ArrayList getSkills()                  { return skillList;                   }
  public ArrayList getSpells()                  { return spellList;                   }
  public ArrayList getActionInts()              { return actionInts;                  }
  public Action getAction(int i)                { return (Action) actions.get(i);     }
  public Action getResponse(int i)              { return responseList[i];             }
  public void addAction(Action A)               { actions.add(A);                     }
  public int getSellItem(int i)                 { return getInt(sellList, i);         }
  public Question getQuestion(int i)            { return (Question) questions.get(i); }
  public void popEq()                           { return;                             }
  public int getNumActions()                    { return actions.size();              }
  public int getPopItem(int i)                  { return getInt(ipopList, i);         }
  public double getPopFreq(int i)               { return getDouble(ipopFreq, i);      }
  public int getWearItem(int i)                 { return getInt(wearList, i);         }
  public double getWearFreq(int i)              { return getDouble(wearFreq, i);      }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Mobile() { this(0); }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Mobile(int mnum)

  {
    ID           =  mnum;
    myRoom       =  0;
    backupRoom   =  0;
    zoneID       =  0;
    popMember    =  null;

    STR          =  0;
    DEX          =  0;
    CON          =  0;
    INT          =  0;
    WIS          =  0;
    HR           =  0;
    DR           =  0;
    AC           =  0;
    MR           =  0;
    currentHP    =  10;
    currentMN    =  10;
    currentMV    =  10;
    maxHP        =  10;
    maxMN        =  10;
    maxMV        =  10;
    gold         =  0;
    level        =  1;

    speed        =  0;
    storeName    = "";
    blockType    = "";
    blockDir     = "";
    fullSay      = "";

    gender       =  "male";
    name         =  "Generic Mobile";
    keywords     =  "generic mobile mob";
    title        =  "A Generic Mobile is standing here.";
    description  =  new Writable("Description.", Writable.FORMAT_DESC);
    myTarget     =  null;
    myClient     =  null;
    playerState  =  PSTATE_STANDING;
    alignment    =  ALIGN_NEUTRAL;

    skillList    =  new ArrayList();
    spellList    =  new ArrayList();
    ipopList     =  new ArrayList();
    ipopFreq     =  new ArrayList();
    wearList     =  new ArrayList();
    wearFreq     =  new ArrayList();
    sellList     =  new ArrayList();
    itemList     =  new ArrayList();
    sayMemory    =  new ArrayList();
    questions    =  new ArrayList();
    exits        =  new String[6];
    actionInts   =  new ArrayList();
    effectList   =  new ArrayList();
    actions      =  new ArrayList();

    equipment    =  new Item [Equipment.NUM_PLACES];
    responseList =  new Action [NUM_RESPONSES];

    initFlags();
    initResponseList();

    SANCD = false;
    poisoned = false;

    following = this;
    followers = new ArrayList();
    myGroup = new Group(this);
    targetList = new ArrayList();
    intermittentSkill = 0;

    schedule = new Schedule();

    myTarget = null;
    myTrack = null;
    trackDir = -1;

    blockerType  = -1;
    blockerDir   = -1;
    blockerParam = "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Mobile replicate()

  {
    Mobile newMob = null;

    try

    {
      newMob = (Mobile)clone();

      newMob.setDesc(new Writable(description.getRawString(), description.getFormat()));

      newMob.setFollowing(newMob);
      newMob.setFollowers(new ArrayList());
      newMob.setGroup(new Group(newMob));
      newMob.setTargetList(new ArrayList());
      newMob.setResponses(new Action[NUM_RESPONSES]);
      newMob.setIpopList(copyIntList(ipopList));
      newMob.setIpopFreq(copyDoubleList(ipopFreq));
      newMob.setWearList(copyIntList(wearList));
      newMob.setWearFreq(copyDoubleList(wearFreq));
      newMob.setSellList(copyIntList(sellList));
      newMob.setQuestions(new ArrayList());
      newMob.setSayMemory(new ArrayList());
      newMob.setItemList(new ArrayList());
      newMob.setEquipment(new Item[Equipment.NUM_PLACES]);
      newMob.setEffectList(new ArrayList());
      newMob.setActions(new ArrayList());
      newMob.setExits(new String[6]);
      newMob.setSkills(new ArrayList());
      newMob.setSpells(new ArrayList());
      newMob.setActionInts(copyIntList(actionInts));

      newMob.initFlags();
      Flag.setFlags(flagList, newMob.getFlags());

      for (int i=0; i<responseList.length; i++)
      if (responseList[i] != null)

      {
        Action A = responseList[i].replicate();
        newMob.setResponse(i, A);
        A.setOwner(newMob);
      }

      for (int i=0; i<actionInts.size(); i++)

      {
        int anum = ((Integer)actionInts.get(i)).intValue();
        Action A = World.getAction(anum).replicate();
        A.setOwner(newMob);
        newMob.addAction(A);
      }

      for (int i=0; i<skillList.size(); i++)

      {
        int level    = ((Skill)skillList.get(i)).getLevel();
        String sName = ((Skill)skillList.get(i)).getName();
        newMob.addSkill(Skill.createSkill(sName, newMob, level));
      }

      for (int i=0; i<spellList.size(); i++)

      {
        int level    = ((Spell)spellList.get(i)).getLevel();
        String sName = ((Spell)spellList.get(i)).getName();
        newMob.addSpell(Spell.createSpell(sName, newMob, level));
      }
    }

    catch (Exception e) { System.out.println(e); }

    newMob.restore();
    newMob.popEquipment();

    return newMob;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void invokeMobTemplate(int tempLevel)

  {
    if (tempLevel > 99) return;

    level = tempLevel;

    maxHP = (level * 100) + 100;
    maxMN = (level * 100) + 100;
    maxMV = (level * 100) + 100;

    STR = (int)(level * 2)    + 2;
    DEX = (int)(level * 1.5)  + 2;
    CON = (int)(level * 1.5)  + 2;
    INT = (int)(level)        + 1;
    WIS = (int)(level)        + 1;

    HR  = (int)(level / 5)    + 1;
    DR  = (int)(level / 4)    + 1;
    AC  = (int)(level * 10)   + 10;
    MR  = (int)(level / 3);

    experience = (level * 1000);
    gold = (level * 10) / 2;

    restore();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void globalItemDelete(int inum)

  {
    for (int i=ipopList.size()-1; i>=0; i--)
    if (getInt(ipopList,i) == inum) {
      ipopList.remove(i);
      ipopFreq.remove(i); }

    for (int i=wearList.size()-1; i>=0; i--)
    if (getInt(wearList,i) == inum) {
      wearList.remove(i);
      wearFreq.remove(i); }

    for (int i=sellList.size()-1; i>=0; i--)
    if (getInt(sellList,i) == inum)
      sellList.remove(i);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void globalItemRenumber(int[] indexList)

  {
    for (int i=0; i<ipopList.size(); i++)
      ipopList.set(i, new Integer(indexList[getInt(ipopList,i)]));
    for (int i=0; i<wearList.size(); i++)
      wearList.set(i, new Integer(indexList[getInt(wearList,i)]));
    for (int i=0; i<sellList.size(); i++)
      sellList.set(i, new Integer(indexList[getInt(sellList,i)]));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void globalMobRenumber(int[] indexList)

  {
    ID = indexList[ID];
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void globalActionDelete(int anum)

  {
    for (int i=actionInts.size()-1; i>=0; i--)
    if (((Action)actionInts.get(i)).getID() == anum)
      actionInts.remove(i);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void popEquipment()

  {
    for (int i=0; i<ipopList.size(); i++)
    if (World.getItem(getPopItem(i)) != null)

    {
      Item I = World.getItem(getPopItem(i)).replicate("Popped on " + getName() + ".");

      if (getRandomDouble(0.0, 100.0) < getPopFreq(i))
        ItemUse.addItemEntity(I, this);
    }

    for (int i=0; i<wearList.size(); i++)
    if (World.getItem(getWearItem(i)) != null)

    {
      Item I = World.getItem(getWearItem(i)).replicate("Popped on " + getName() + ".");

      if (getRandomDouble(0.0, 100.0) < getWearFreq(i)) {
        ItemUse.addItemEntity(I, this);
        Equipment.equip(this, I, false); }
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void initFlags()

  {
    flagList                   = new Flag[NUM_FLAGS];
    flagList[FLAG_MOBILE]      = new Flag("MOBILE", false);
    flagList[FLAG_AGGRO]       = new Flag("AGGRO", false);
    flagList[FLAG_BARD]        = new Flag("BARD", false);
    flagList[FLAG_NPC]         = new Flag("NPC", false);
    flagList[FLAG_MERCHANT]    = new Flag("MERCHANT", false);
    flagList[FLAG_BANKER]      = new Flag("BANKER", false);
    flagList[FLAG_HERBALIST]   = new Flag("HERBALIST", false);
    flagList[FLAG_SKILLMASTER] = new Flag("SKILLMASTER", false);
    flagList[FLAG_SPELLMASTER] = new Flag("SPELLMASTER", false);
    flagList[FLAG_GUARD]       = new Flag("GUARD", false);
    flagList[FLAG_BLOCKER]     = new Flag("BLOCKER", false);
    flagList[FLAG_CLERGY]      = new Flag("CLERGY", false);
    flagList[FLAG_TEACHER]     = new Flag("TEACHER", false);
    flagList[FLAG_FLYING]      = new Flag("FLYING", false);
    flagList[FLAG_UNDEAD]      = new Flag("UNDEAD", false);
    flagList[FLAG_SANCD]       = new Flag("SANCD", false);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void initResponseList()

  {
    responseList[RESPONSE_AGGRO] = new Action(-1);
    responseList[RESPONSE_AGGRO].setOwner(this);
    responseList[RESPONSE_AGGRO].setCustom("AGGRO");
    responseList[RESPONSE_AGGRO].setName("Aggression");
    responseList[RESPONSE_AGGRO].setHostile(true);
    responseList[RESPONSE_AGGRO].addCommand("", 0, 5);
    responseList[RESPONSE_AGGRO].addCommand("evaluate all", 0);
    responseList[RESPONSE_AGGRO].addCommand("attackrand", 1);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void considerAction()

  {
    if (acted) return;
    if (getClient() != null) return;

    for (int i=actions.size()-1; i>=0; i--)
    if (getAction(i).tryAction())

    {
      actionInts.remove(i);
      actions.remove(i);
    }

    if ((getRoom().occupied()) && (playerState != PSTATE_FIGHTING))
    if (getFlag(FLAG_AGGRO))
      responseList[RESPONSE_AGGRO].tryAction();

    acted = true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void attackRand(String rem)

  {
    if (Evaluator.targets.size() == 0) return;

    int r = getRandomInt(0, Evaluator.targets.size()-1);
    Entity targ = Evaluator.getTarget(r);

    if (rem.length() == 0) rem = "kill";
    Interpreter.interpretCommand(this, rem + " " + targ.getName());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void regen()

  {
    if (poisoned) return;

    setCurrentHP((getCurrentHP()+(int)((getMaxHP()+10) * 0.2)));
    setCurrentMN((getCurrentMN()+(int)((getMaxMN()+10) * 0.2)));
    setCurrentMV((getCurrentMV()+(int)((getMaxMV()+10) * 0.2)));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void die(boolean diedInCombat, boolean makeCorpse)

  {
    if (myClient != null)

    {
      myClient.flushCommands();
      Immortal.unpossess(myClient, true);
    }

    terminateGroupAndFollowers(diedInCombat);

    if (makeCorpse)
      ItemUse.addItemRoom(ItemUse.makeCorpse(this), getRoom());

    getRoom().loss(this);

    if (popMember != null)

    {
      int rnum = popMember.getRNum();
      World.getRoom(rnum).getZone().mobDied(this);
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean checkTargets(String str)

  {
    if (findKeyword(str, keywords)) return true;
    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean toggleFlag(String s)

  {
    return Flag.toggleFlag(flagList, s);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void respond(String s)

  {
    if (playerState == PSTATE_SLEEPING) return;

    String forMe = align("#nYou say, '" + s + "#n'#N");
    String forThem = align("\r\n#n" + name + " says, '" + s + "#n'#N");

    echo(forMe);
    getRoom().xecho(this, forThem, ECHO_AWAKE);
  }


  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void decrementQuestions()

  {
    for (int i=questions.size()-1; i>=0; i--)

    {
      getQuestion(i).decrement();

      if (getQuestion(i).getCounter() <= 0)
        questions.remove(i);
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean heard(String s)

  {
    if (sayMemory == null) return false;

    for (int i=0; i<sayMemory.size(); i++)

    {
      String S = (String) sayMemory.get(i);
      if (S.equalsIgnoreCase(removeColors(s))) return true;
    }

    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean HEARD(String s)

  {
    if (fullSay.indexOf(removeColors(s.toLowerCase())) == -1) return false;
    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void act(String s)

  {
    Interpreter.interpretCommand(this, s);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void initSayMemory(String s)

  {
    boolean adding = false;
    String currentWord = "";
    fullSay = removeColors(s.toLowerCase());

    sayMemory.clear();

    while (s.length() > 0)

    {
      if (letter(s.charAt(0))) {
        currentWord += s.charAt(0);
        adding = true; }

      else if (adding) {
        sayMemory.add(currentWord);
        currentWord = "";
        adding = false; }

      s = s.substring(1, s.length());
    }

    if (currentWord.length() > 0) sayMemory.add(currentWord);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean talkingToMe()

  {
    String str = keywords;

    while (str.length() > 0)

    {
      if (heard(first(str))) return true;
      str = last(str);
    }

    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void examineSay(Entity SRC, String s)

  {
    if (!getFlag(FLAG_NPC)) return;
    if (SRC == null) return;

    initSayMemory(s);

    if (questionWasAnswered(SRC, s)) return;

    if (talkingToMe())

    {
      if (Interaction.tryChangePlanError(this))              return;
      if (Interaction.tryNotBardError(this))                 return;
      if (Interaction.tryChangePlan(this, SRC))              return;
      if (Interaction.tryChangeDescription(this, SRC))       return;
      if (Interaction.tryAmBard(this))                       return;
      if (Interaction.tryCannotTeach(this))                  return;
      if (Interaction.tryNotSkillmaster(this))               return;
      if (Interaction.tryNotMerchant(this))                  return;

      if (Interaction.tryMentorLeave(this, SRC))             return;
      if (Interaction.tryMentorRequest(this, SRC, s))        return;
      if (Interaction.tryMentorInfo(this))                   return;

      if (Interaction.tryShopList(this, SRC))                return;
      if (Interaction.tryBuyRequest(this, SRC, s))           return;
      if (Interaction.trySellRequest(this, SRC, s))          return;
      if (Interaction.tryDepositRequest(this, SRC, s))       return;
      if (Interaction.tryWithdrawRequest(this, SRC, s))      return;
      if (Interaction.tryAccountRequest(this, SRC))          return;

      if (Interaction.trySingleSkillInfo(this, SRC, false))  return;
      if (Interaction.tryAllSkillInfo(this, SRC, false))     return;
      if (Interaction.trySingleSkillInfo(this, SRC, true))   return;
      if (Interaction.tryAllSkillInfo(this, SRC, true))      return;

      if (Interaction.tryHowAreYou(this, SRC))               return;
      if (Interaction.tryWhatsUp(this, SRC))                 return;
      if (Interaction.tryHello(this, SRC))                   return;
      if (Interaction.tryHail(this, SRC))                    return;
      if (Interaction.tryThanks(this, SRC))                  return;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean addQuestion(Question Q)

  {
    String responder = Q.getResponder().getName();

    for (int i=0; i<questions.size(); i++)

    {
      String resp = getQuestion(i).getResponder().getName();
      if (resp.equalsIgnoreCase(responder)) return false;
    }

    questions.add(Q);
    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean questionWasAnswered(Entity responder, String s)

  {
    for (int i=0; i<questions.size(); i++)
    if (getQuestion(i).getResponder() == responder)

    {
      if (getQuestion(i).receiveAnswer(s))

      {
        questions.remove(i);
        return true;
      }
    }

    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean moveWithinZone()

  {
    ecount = 0;

    if (getRoom().getN() != 0)
    if (getRoom().getZone().getID() == World.getRoom(getRoom().getN()).getZone().getID())
    if (tryMove(DIRECTION_N).equals("OK")) {
      exits[ecount] = "N";
      ecount++; }

    if (getRoom().getS() != 0)
    if (getRoom().getZone().getID() == World.getRoom(getRoom().getS()).getZone().getID())
    if (tryMove(DIRECTION_S).equals("OK")) {
      exits[ecount] = "S";
      ecount++; }

    if (getRoom().getE() != 0)
    if (getRoom().getZone().getID() == World.getRoom(getRoom().getE()).getZone().getID())
    if (tryMove(DIRECTION_E).equals("OK")) {
      exits[ecount] = "E";
      ecount++; }

    if (getRoom().getW() != 0)
    if (getRoom().getZone().getID() == World.getRoom(getRoom().getW()).getZone().getID())
    if (tryMove(DIRECTION_W).equals("OK")) {
      exits[ecount] = "W";
      ecount++; }

    if (getRoom().getU() != 0)
    if (getRoom().getZone().getID() == World.getRoom(getRoom().getU()).getZone().getID())
    if (tryMove(DIRECTION_U).equals("OK")) {
      exits[ecount] = "U";
      ecount++; }

    if (getRoom().getD() != 0)
    if (getRoom().getZone().getID() == World.getRoom(getRoom().getD()).getZone().getID())
    if (tryMove(DIRECTION_D).equals("OK")) {
      exits[ecount] = "D";
      ecount++; }

    if (ecount == 0) return false;

    ecount = getRandomInt(0, ecount-1);
    exits[0] = exits[ecount];

    move(exits[ecount]);
    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean moveAcrossZone()

  {
    ecount = 0;

    if ((getRoom().getN() != 0) && (tryMove(DIRECTION_N).equals("OK"))) { exits[ecount] = "N";  ecount++; }
    if ((getRoom().getS() != 0) && (tryMove(DIRECTION_S).equals("OK"))) { exits[ecount] = "S";  ecount++; }
    if ((getRoom().getE() != 0) && (tryMove(DIRECTION_E).equals("OK"))) { exits[ecount] = "E";  ecount++; }
    if ((getRoom().getW() != 0) && (tryMove(DIRECTION_W).equals("OK"))) { exits[ecount] = "W";  ecount++; }
    if ((getRoom().getU() != 0) && (tryMove(DIRECTION_U).equals("OK"))) { exits[ecount] = "U";  ecount++; }
    if ((getRoom().getD() != 0) && (tryMove(DIRECTION_D).equals("OK"))) { exits[ecount] = "D";  ecount++; }

    if (ecount == 0) return false;

    ecount = getRandomInt(0, ecount-1);
    exits[0] = exits[ecount];

    move(exits[ecount]);
    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean seekRoom(String numberString)

  {
    if (numberString.length() <= 0) return false;
    if (!number(numberString)) return false;
    int num = Integer.parseInt(numberString);

    Room destination = World.getRoom(num);

    int[] dist_and_dir = getRoom().roomDistance(destination);

    if (dist_and_dir == null) return false;

    switch (dist_and_dir[1])

    {
      case 1:  if (tryMove(DIRECTION_N).equals("OK"))  { move(DIRECTION_N);  return true; }
      case 2:  if (tryMove(DIRECTION_S).equals("OK"))  { move(DIRECTION_S);  return true; }
      case 3:  if (tryMove(DIRECTION_E).equals("OK"))  { move(DIRECTION_E);  return true; }
      case 4:  if (tryMove(DIRECTION_W).equals("OK"))  { move(DIRECTION_W);  return true; }
      case 5:  if (tryMove(DIRECTION_U).equals("OK"))  { move(DIRECTION_U);  return true; }
      case 6:  if (tryMove(DIRECTION_D).equals("OK"))  { move(DIRECTION_D);  return true; }
    }

    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public String getShopList()

  {
    String temp = "";
    int max1 = 0, max2 = 0, top = 0, bottom = 0, width = 0;

    ArrayList wares = new ArrayList();
    ArrayList prices = new ArrayList();

    sortSellList();

    for (int i=0; i<sellList.size(); i++)

    {
      Item I = World.getItem(getSellItem(i));

      if (I != null) {
        wares.add(I.getName());
        prices.add(expCommas(""+I.getWorth())); }
    }

    for (int i=0; i<wares.size(); i++)

    {
      int vs1 = visibleSize(((String)wares.get(i)));
      int vs2 = visibleSize(((String)prices.get(i)));
      if (vs1 > max1) max1 = vs1;
      if (vs2 > max2) max2 = vs2;
    }

    top = visibleSize(storeName) + 1;
    if (wares.size() == 0) bottom = 9;
    else bottom = max1 + max2 + 11;
    if (top >= bottom) width = top;
    else width = bottom;

    String border = "#n+-" + xchars(width,"-") + "-+#N";
    temp  = "\r\n  " + border + "\r\n" + "  #n|#N " + storeName + "#N:";
    temp += xchars(width-visibleSize(storeName)," ") + "#n|#N\r\n  ";
    temp += border + "\r\n";

    if (wares.size() == 0)

    {
      temp += "  #n| #NSold Out!" + xchars(width-9," ") + " #n|#N\r\n";
      temp += "  " + border;
      return temp;
    }

    for (int i=0; i<wares.size(); i++)

    {
      temp += "  #n|#N " + ((String)wares.get(i));
      temp += xchars(max1-visibleSize(((String)wares.get(i)))," ");
      temp += "  #N--  ";
      temp += xchars(max2-visibleSize(((String)prices.get(i)))," ");
      temp += "#N" + ((String)prices.get(i)) + " #NGold #n|#N\r\n";
    }

    temp += "  " + border;

    return temp;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void sortSellList()

  {
    for (int b=0; b<sellList.size()-1; b++)
    for (int i=0; i<sellList.size()-1; i++)

    {
      Integer S1 = (Integer) sellList.get(i);
      Integer S2 = (Integer) sellList.get(i+1);

      Item I1 = World.getItem(S1.intValue());
      Item I2 = World.getItem(S2.intValue());

      if (I1.getWorth() > I2.getWorth())

      {
        sellList.set(i,S2);
        sellList.set(i+1,S1);
      }
    }
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
    String pf = xchars(14," ") + tenSpace(level,2);
    String stSTR = tenSpace(getSTR(),2);  String stHR = tenSpace(getHR(),2);
    String stDEX = tenSpace(getDEX(),2);  String stDR = tenSpace(getDR(),2);
    String stCON = tenSpace(getCON(),2);  String stAC = tenSpace(getAC(),3);
    String stINT = tenSpace(getINT(),2);  String stMR = tenSpace(getMR(),2);
    String stWIS = tenSpace(getWIS(),2);
    String xs = tenSpace(getCurrentHP(),3) + tenSpace(getMaxHP(),3) + " ";
    String ys = tenSpace(getCurrentMN(),3) + tenSpace(getMaxMN(),3) + " ";
    String zs = tenSpace(getCurrentMV(),3) + tenSpace(getMaxMV(),3) + " ";
    String exp  = expCommas("" + 0);
    String expn = expCommas("" + 0);
    String gld  = expCommas("" + gold);
    String es = xchars(11-exp.length()," ");
    String ns = xchars(11-expn.length()," ");
    String gs = xchars(11-gld.length()," ");

    String ST = "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+";
    ST += side(0) + "#n\r\n  |  #C" + name + "#N" + nm + "#n+-------------------+";
    ST += "  |#N" + side(1) + "\r\n  #n|  #NLevel " + level + " Mobile" + pf;
    ST += "#n[#N     STR - " + stSTR + getSTR() + "     #n]  |#N" + side(2) + "#n\r\n";
    ST += "  |                                #n[     #NDEX - " + stDEX;
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

    echo(ST);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("MOB END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("ID"))                ID = ((Integer)in.readObject()).intValue();
        else if (tag.equals("ZONEID"))       zoneID = ((Integer)in.readObject()).intValue();
        else if (tag.equals("NAME"))         setName((String)in.readObject());
        else if (tag.equals("KEYWORDS"))     setKeywords((String)in.readObject());
        else if (tag.equals("TITLE"))        setTitle((String)in.readObject());
        else if (tag.equals("IPOPLIST"))     ipopList = (ArrayList)in.readObject();
        else if (tag.equals("IPOPFREQ"))     ipopFreq = (ArrayList)in.readObject();
        else if (tag.equals("WEARLIST"))     wearList = (ArrayList)in.readObject();
        else if (tag.equals("WEARFREQ"))     wearFreq = (ArrayList)in.readObject();
        else if (tag.equals("SELLLIST"))     sellList = (ArrayList)in.readObject();
        else if (tag.equals("STORENAME"))    storeName = (String)in.readObject();
        else if (tag.equals("GENDER"))       gender = (String)in.readObject();
        else if (tag.equals("ALIGNMENT"))    alignment = ((Integer)in.readObject()).intValue();
        else if (tag.equals("EXPERIENCE"))   experience = ((Integer)in.readObject()).intValue();
        else if (tag.equals("LEVEL"))        level = ((Integer)in.readObject()).intValue();
        else if (tag.equals("DESC"))         description = (Writable)in.readObject();
        else if (tag.equals("EQUIPMENT"))    equipment = (Item[])((List)in.readObject()).toArray();
        else if (tag.equals("ITEMLIST"))     itemList = (ArrayList)in.readObject();
        else if (tag.equals("STR"))          STR = ((Integer)in.readObject()).intValue();
        else if (tag.equals("DEX"))          DEX = ((Integer)in.readObject()).intValue();
        else if (tag.equals("CON"))          CON = ((Integer)in.readObject()).intValue();
        else if (tag.equals("INT"))          INT = ((Integer)in.readObject()).intValue();
        else if (tag.equals("WIS"))          WIS = ((Integer)in.readObject()).intValue();
        else if (tag.equals("HR"))           HR = ((Integer)in.readObject()).intValue();
        else if (tag.equals("DR"))           DR = ((Integer)in.readObject()).intValue();
        else if (tag.equals("AC"))           AC = ((Integer)in.readObject()).intValue();
        else if (tag.equals("MR"))           MR = ((Integer)in.readObject()).intValue();
        else if (tag.equals("GOLD"))         gold = ((Integer)in.readObject()).intValue();
        else if (tag.equals("PLAYERSTATE"))  playerState = ((Integer)in.readObject()).intValue();
        else if (tag.equals("MAXHP"))        maxHP = ((Integer)in.readObject()).intValue();
        else if (tag.equals("MAXMN"))        maxMN = ((Integer)in.readObject()).intValue();
        else if (tag.equals("MAXMV"))        maxMV = ((Integer)in.readObject()).intValue();
        else if (tag.equals("SPEED"))        speed = ((Integer)in.readObject()).intValue();
        else if (tag.equals("SPELLS"))       spellList = (ArrayList)in.readObject();
        else if (tag.equals("SKILLS"))       skillList = (ArrayList)in.readObject();
        else if (tag.equals("POISONED"))     poisoned = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("FLAGS"))        Flag.setFlags((Flag[])in.readObject(), flagList);
        else if (tag.equals("BLOCKERTYPE"))  blockerType = ((Integer)in.readObject()).intValue();
        else if (tag.equals("BLOCKERDIR"))   blockerDir = ((Integer)in.readObject()).intValue();
        else if (tag.equals("BLOCKERPARAM")) blockerParam = (String)in.readObject();
        else if (tag.equals("ACTIONINTS"))   actionInts = (ArrayList)in.readObject();
        else if (tag.equals("RESPONSES"))    Action.updateResponses((Action[])in.readObject(), responseList);
        else if (!tag.equals("MOB END"))     in.readObject();
      }

      for (int i=0; i<skillList.size(); i++)   ((Skill)skillList.get(i)).setOwner(this);
      for (int i=0; i<spellList.size(); i++)   ((Spell)spellList.get(i)).setOwner(this);
      for (int i=0; i<effectList.size(); i++)  getEffect(i).setOwner(this);

      for (int i=0; i<itemList.size(); i++)
        ((Item)itemList.get(i)).setEntity(this);

      for (int i=0; i<Equipment.NUM_PLACES; i++)
      if (equipment[i] != null)
        equipment[i].setEntity(this);
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ID");           out.writeObject(new Integer(ID));
      out.writeObject("ZONEID");       out.writeObject(new Integer(zoneID));
      out.writeObject("NAME");         out.writeObject(name);
      out.writeObject("KEYWORDS");     out.writeObject(keywords);
      out.writeObject("TITLE");        out.writeObject(title);
      out.writeObject("IPOPLIST");     out.writeObject(ipopList);
      out.writeObject("IPOPFREQ");     out.writeObject(ipopFreq);
      out.writeObject("WEARLIST");     out.writeObject(wearList);
      out.writeObject("WEARFREQ");     out.writeObject(wearFreq);
      out.writeObject("SELLLIST");     out.writeObject(sellList);
      out.writeObject("STORENAME");    out.writeObject(storeName);
      out.writeObject("GENDER");       out.writeObject(gender);
      out.writeObject("ALIGNMENT");    out.writeObject(new Integer(alignment));
      out.writeObject("EXPERIENCE");   out.writeObject(new Integer(experience));
      out.writeObject("LEVEL");        out.writeObject(new Integer(level));
      out.writeObject("DESC");         out.writeObject(description);
      out.writeObject("EQUIPMENT");    out.writeObject(Arrays.asList(equipment));
      out.writeObject("ITEMLIST");     out.writeObject(itemList);
      out.writeObject("STR");          out.writeObject(new Integer(STR));
      out.writeObject("DEX");          out.writeObject(new Integer(DEX));
      out.writeObject("CON");          out.writeObject(new Integer(CON));
      out.writeObject("INT");          out.writeObject(new Integer(INT));
      out.writeObject("WIS");          out.writeObject(new Integer(WIS));
      out.writeObject("HR");           out.writeObject(new Integer(HR));
      out.writeObject("DR");           out.writeObject(new Integer(DR));
      out.writeObject("AC");           out.writeObject(new Integer(AC));
      out.writeObject("MR");           out.writeObject(new Integer(MR));
      out.writeObject("GOLD");         out.writeObject(new Integer(gold));
      out.writeObject("PLAYERSTATE");  out.writeObject(new Integer(playerState));
      out.writeObject("MAXHP");        out.writeObject(new Integer(maxHP));
      out.writeObject("MAXMN");        out.writeObject(new Integer(maxMN));
      out.writeObject("MAXMV");        out.writeObject(new Integer(maxMV));
      out.writeObject("SPEED");        out.writeObject(new Integer(speed));
      out.writeObject("POISONED");     out.writeObject(new Boolean(poisoned));
      out.writeObject("FLAGS");        out.writeObject(flagList);
      out.writeObject("SPELLS");       out.writeObject(spellList);
      out.writeObject("SKILLS");       out.writeObject(skillList);
      out.writeObject("RESPONSES");    out.writeObject(responseList);
      out.writeObject("BLOCKERTYPE");  out.writeObject(new Integer(blockerType));
      out.writeObject("BLOCKERDIR");   out.writeObject(new Integer(blockerDir));
      out.writeObject("BLOCKERPARAM"); out.writeObject(new String(blockerParam));
      out.writeObject("ACTIONINTS");   out.writeObject(actionInts);
      out.writeObject("MOB END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}