import java.util.*;
import java.io.*;

public abstract class Entity extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final long serialVersionUID = 13;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean GOOD = false;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  protected int currentHP;
  protected int currentMN;
  protected int currentMV;
  protected int maxHP;
  protected int maxMN;
  protected int maxMV;
  protected int level;
  protected int myRoom;
  protected int backupRoom;
  protected int alignment;
  protected int blockerType;
  protected int blockerDir;
  protected int STR, DEX, CON, INT, WIS;
  protected int HR, DR, AC, MR, gold;
  protected int EQ_HP, EQ_MN, EQ_MV;
  protected int playerState;
  protected int intermittentSkill;
  protected int trackDir;
  protected String name;
  protected String gender;
  protected String title;
  protected String blockerParam;
  protected boolean poisoned;
  protected boolean SANCD;
  protected Entity following;
  protected Entity myTarget;
  protected Entity myTrack;
  protected Client myClient;
  protected ArrayList followers;
  protected ArrayList targetList;
  protected ArrayList spellList;
  protected ArrayList skillList;
  protected ArrayList effectList;
  protected Group myGroup;
  protected Item[] equipment;
  protected Writable description;
  protected Schedule schedule;
  protected boolean hasAttacked;
  protected boolean followable;
  protected boolean autoassist;
  protected ItemList inventory;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public abstract void stat();
  public abstract int getTrueResistance();
  public abstract int getClanNum();
  public abstract int getReligionNum();
  public abstract String getFullName();
  public abstract String getTitle();
  public abstract String getCharClass();
  public abstract String getSClass();
  public abstract String getCharClassColor();
  public abstract String getSClassColor();
  public abstract boolean isPlayer();
  public abstract boolean isGhost();
  public abstract boolean checkTargets(String s);
  public abstract void die(boolean diedInCombat, boolean makeCorpse);
  public abstract void receiveExperience(Entity target);
  public abstract void gainExperience(int num);
  public abstract void regen();
  public abstract void setClan(int i);
  public abstract void setReligion(int i);
  public abstract void setFullName(String s);

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Char castChar()                  { return (Char)this;                 }
  public Mobile castMob()                 { return (Mobile)this;               }
  public Ghost castGhost()                { return (Ghost)this;                }
  public Writable getDescWrite()          { return description;                }
  public boolean isDead()                 { return (currentHP <= 0);           }
  public void setDesc(String s)           { description.write(s);              }
  public void setDesc(Writable w)         { description = w;                   }
  public void setName(String s)           { name = s;                          }
  public void setGender(String s)         { gender = s;                        }
  public void setTitle(String s)          { title = s;                         }
  public void setMaxHP(int i)             { maxHP = i;                         }
  public void setMaxMN(int i)             { maxMN = i;                         }
  public void setMaxMV(int i)             { maxMV = i;                         }
  public void setSTR(int i)               { STR = i;                           }
  public void setDEX(int i)               { DEX = i;                           }
  public void setCON(int i)               { CON = i;                           }
  public void setINT(int i)               { INT = i;                           }
  public void setWIS(int i)               { WIS = i;                           }
  public void setHR(int i)                { HR = i;                            }
  public void setDR(int i)                { DR = i;                            }
  public void setAC(int i)                { AC = i;                            }
  public void setMR(int i)                { MR = i;                            }
  public void setAlignment(int i)         { alignment = i;                     }
  public void setGold(int i)              { gold = i;                          }
  public void setClient(Client c)         { myClient = c;                      }
  public void setPlayerState(int i)       { playerState = i;                   }
  public void setRoom(int i)              { myRoom = i;                        }
  public void setBackupRoom(int i)        { backupRoom = i;                    }
  public void setFollowing(Entity e)      { following = e;                     }
  public void setIntermittentSkill(int i) { intermittentSkill = i;             }
  public void scheduleEvent(int n, int m) { schedule.scheduleEvent(n, m);      }
  public void cancelEvent(int n)          { schedule.cancelEvent(n);           }
  public boolean checkSchedule(int n)     { return schedule.checkEvent(n);     }
  public int getCurrentHP()               { return bound(currentHP,0,9999);    }
  public int getCurrentMN()               { return bound(currentMN,0,9999);    }
  public int getCurrentMV()               { return bound(currentMV,0,9999);    }
  public int getMaxHP()                   { return bound(maxHP+EQ_HP,1,9999);  }
  public int getMaxMN()                   { return bound(maxMN+EQ_MN,1,9999);  }
  public int getMaxMV()                   { return bound(maxMV+EQ_MV,1,9999);  }
  public int getNaturalHP()               { return bound(maxHP,1,9999);        }
  public int getNaturalMN()               { return bound(maxMN,1,9999);        }
  public int getNaturalMV()               { return bound(maxMV,1,9999);        }
  public int getHR()                      { return bound(HR,0,999);            }
  public int getDR()                      { return bound(DR,0,999);            }
  public int getAC()                      { return bound(AC,0,9999);           }
  public int getMR()                      { return bound(MR,0,100);            }
  public int getSTR()                     { return bound(STR,0,999);           }
  public int getDEX()                     { return bound(DEX,0,999);           }
  public int getCON()                     { return bound(CON,0,999);           }
  public int getINT()                     { return bound(INT,0,999);           }
  public int getWIS()                     { return bound(WIS,0,999);           }
  public int getEQHP()                    { return EQ_HP;                      }
  public int getEQMN()                    { return EQ_MN;                      }
  public int getEQMV()                    { return EQ_MV;                      }
  public int getGold()                    { return gold;                       }
  public int getAlignment()               { return alignment;                  }
  public int getPlayerState()             { return playerState;                }
  public int getLevel()                   { return level;                      }
  public int getIntermittentSkill()       { return intermittentSkill;          }
  public int getBlockerType()             { return blockerType;                }
  public int getBlockerDir()              { return blockerDir;                 }
  public String getBlockerParam()         { return blockerParam;               }
  public String getName()                 { return name;                       }
  public String getLName()                { return name.toLowerCase();         }
  public String getPName()                { return properName(name);           }
  public String getDesc()                 { return description.getString();    }
  public String getGender()               { return gender;                     }
  public Client getClient()               { return myClient;                   }
  public Room getRoom()                   { return World.getRoom(myRoom);      }
  public Room getBackupRoom()             { return World.getRoom(backupRoom);  }
  public Zone getZone()                   { return getRoom().getZone();        }
  public boolean isPoisoned()             { return poisoned;                   }
  public void backupRoom()                { backupRoom = myRoom;               }
  public void restoreRoom()               { myRoom = backupRoom;               }
  public Item[] getEquipment()            { return equipment;                  }
  public Item getEquipment(int i)         { return equipment[i];               }
  public void setEQHP(int i)              { EQ_HP = i;                         }
  public void setEQMN(int i)              { EQ_MN = i;                         }
  public void setEQMV(int i)              { EQ_MV = i;                         }
  public void setPoisoned(boolean b)      { poisoned = b;                      }
  public void setSANC(boolean b)          { SANCD = b;                         }
  public boolean getSANC()                { return SANCD;                      }
  public Entity getTarget()               { return myTarget;                   }
  public Entity getFollowing()            { return following;                  }
  public void setGroup(Group G)           { myGroup = G;                       }
  public Group getGroup()                 { return myGroup;                    }
  public int getNumFollowers()            { return followers.size();           }
  public Entity getFollower(int i)        { return (Entity) followers.get(i);  }
  public void addFollower(Entity ENT)     { followers.add(ENT);                }
  public void removeFollower(int i)       { followers.remove(i);               }
  public void addSkill(Skill S)           { skillList.add(S);  sortSkills();   }
  public void addSpell(Spell S)           { spellList.add(S);  sortSpells();   }
  public Spell getSpell(int i)            { return (Spell) spellList.get(i);   }
  public Skill getSkill(int i)            { return (Skill) skillList.get(i);   }
  public Effect getEffect(int i)          { return (Effect) effectList.get(i); }
  public int getNumEffects()              { return effectList.size();          }
  public void stopTracking()              { myTrack = null;  trackDir = -1;    }
  public void setTracking(Entity e)       { myTrack = e;                       }
  public void setTrackingDir(int i)       { trackDir = i;                      }
  public Entity getTracking()             { return myTrack;                    }
  public int getTrackingDir()             { return trackDir;                   }
  public void setBlockerType(int i)       { blockerType = i;                   }
  public void setBlockerDir(int i)        { blockerDir = i;                    }
  public void setBlockerParam(String s)   { blockerParam = s;                  }
  public boolean hasAttacked()            { return hasAttacked;                }
  public void setHasAttacked(boolean b)   { hasAttacked = b;                   }
  public void setFollowable(boolean b)    { followable = b;                    }
  public void setAutoassist(boolean b)    { autoassist = b;                    }
  public boolean getAutoassist()          { return autoassist;                 }
  public boolean getFollowable()          { return followable;                 }
  public boolean getNoHassle()            { return (level>99);                 }
  public ItemList getInventory()          { return inventory;                  }
  public void setInventory(ItemList I)    { inventory = I;                     }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Entity() { }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  String he()      { if (gender.equals("male")) return "he";      return "she";     }
  String He()      { if (gender.equals("male")) return "He";      return "She";     }
  String his()     { if (gender.equals("male")) return "his";     return "her";     }
  String His()     { if (gender.equals("male")) return "His";     return "Her";     }
  String him()     { if (gender.equals("male")) return "him";     return "her";     }
  String Him()     { if (gender.equals("male")) return "Him";     return "Her";     }
  String himself() { if (gender.equals("male")) return "himself"; return "herself"; }
  String Himself() { if (gender.equals("male")) return "Himself"; return "Herself"; }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void lockCommands(int i)

  {
    if (myClient != null) myClient.lockCommands(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void restore()

  {
    currentHP = getMaxHP();
    currentMN = getMaxMN();
    currentMV = getMaxMV();

    for (int i=effectList.size()-1; i>=0; i--)
    if (getEffect(i).getHarmful()) effectList.remove(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setCurrentHP(int i)

  {
    currentHP = i;
    if (currentHP < 0) currentHP = 0;
    if (currentHP > getMaxHP()) currentHP = getMaxHP();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setCurrentMN(int i)

  {
    currentMN = i;
    if (currentMN < 0) currentMN = 0;
    if (currentMN > getMaxMN()) currentMN = getMaxMN();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setCurrentMV(int i)

  {
    currentMV = i;
    if (currentMV < 0) currentMV = 0;
    if (currentMV > getMaxMV()) currentMV = getMaxMV();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setLevel(int i)

  {
    if (isPlayer()) level = bound(i,1,110);
    else level = bound(i,1,99);
    if (isPlayer()) this.castChar().setExpNeeded();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean sleepCheck()

  {
    if (playerState == PSTATE_SLEEPING) {
      echo("You can't do that in your sleep!");
      return true; }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getIllumination()

  {
    return 0;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void sit(String input)

  {
    if (playerState == PSTATE_FIGHTING) {
      echo("You can't sit in the middle of combat.");
      return; }

    if (playerState == PSTATE_SITTING) {
      echo("You're already sitting down.");
      return; }

    String outputMe = "";
    String outputYou = "";

    if (playerState == PSTATE_SLEEPING) {
      outputMe  = "You wake up and sit down on the ground.";
      outputYou = getName() + " wakes up and sits down on the ground."; }
    else if (playerState == PSTATE_RESTING) {
      outputMe  = "You stop resting and sit up.";
      outputYou = getName() + " stops resting and sits up."; }
    else if (playerState == PSTATE_STANDING) {
      outputMe  = "You sit down on the ground.";
      outputYou = getName() + " sits down and rests."; }

    playerState = PSTATE_SITTING;

    sendToAwake(outputYou);
    echo(outputMe);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void rest(String input)

  {
    if (playerState == PSTATE_FIGHTING) {
      echo("You can't rest in the middle of combat.");
      return; }

    if (playerState == PSTATE_RESTING) {
      echo("You're already resting.");
      return; }

    String outputMe = "";
    String outputYou = "";

    if (playerState == PSTATE_STANDING) {
      outputMe  = "You lay down and rest.";
      outputYou = getName() + " lays down and rests."; }
    else if (playerState == PSTATE_SITTING) {
      outputMe  = "You stop sitting and lay down.";
      outputYou = getName() + " stops sitting and lays down."; }
    else if (playerState == PSTATE_SLEEPING) {
      outputMe  = "You wake up and rest.";
      outputYou = getName() + " wakes up and rests."; }

    playerState = PSTATE_RESTING;

    sendToAwake(outputYou);
    echo(outputMe);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void sleep(String input)

  {
    if (playerState == PSTATE_FIGHTING) {
      echo("You can't sleep in the middle of combat.");
      return; }

    if (playerState == PSTATE_SLEEPING) {
      echo("You're already sleeping.");
      return; }

    String outputMe = "You go to sleep.";
    String outputYou = getName() + " goes to sleep.";

    playerState = PSTATE_SLEEPING;

    sendToAwake(outputYou);
    echo(outputMe);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void stand(String input)

  {
    if (playerState == PSTATE_FIGHTING) {
      echo("You're already standing.");
      return; }

    if (playerState == PSTATE_STANDING) {
      echo("You're already standing.");
      return; }

    String outputMe = "";
    String outputYou = "";

    if (playerState == PSTATE_SITTING) {
      outputMe  = "You stop sitting and stand up.";
      outputYou = getName() + " stops sitting and stands up."; }
    else if (playerState == PSTATE_RESTING) {
      outputMe  = "You stop resting and stand up.";
      outputYou = getName() + " stops resting and stands up."; }
    else if (playerState == PSTATE_SLEEPING) {
      outputMe  = "You wake and stand up.";
      outputYou = getName() + " wakes and stands up."; }

    playerState = PSTATE_STANDING;

    sendToAwake(outputYou);
    echo(outputMe);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void prename(String s)

  {
    if (!this.isPlayer()) return;

    s = clearWhiteSpace(s);

    if (s.equals("")) {
      this.castChar().setPrename(s);
      echo("You now have no prename.");
      return; }

    if (visibleSize(s) > MAX_PRENAME_LENGTH) {
      echo("Prename exceeds maximum length.");
      return; }

    this.castChar().setPrename(s);
    echo("Your prename is now: " + s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void title(String s)

  {
    if (!this.isPlayer()) return;

    s = clearWhiteSpace(s);

    if (s.equals("")) {
      setTitle(s);
      echo("You now have no title.");
      return; }

    if ((isPlayer()) && (visibleSize(s) > MAX_PTITLE_LENGTH)) {
      echo("Title exceeds maximum length.");
      return; }

    setTitle(s);
    echo("Your title is now: " + s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getAlignString()

  {
    if (alignment == ALIGN_NEUTRAL) return "Neutral";
    if (alignment == ALIGN_LIGHT)   return "Light";
    if (alignment == ALIGN_DARK)    return "Dark";
    return "None";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void say(String s)

  {
    if ((s.length() <= 0) && (myClient != null)) {
      myClient.displaySayHistory();
      return; }

    if (playerState == PSTATE_SLEEPING) {
      echo("You can't talk in your sleep!");
      return; }

    String forMe = align("#nYou say, '" + s + "#n'#N");
    String forThem = align("#n" + name + " says, '" + s + "#n'#N");

    if (myClient != null) myClient.addSayHistory(forMe, forThem);

    echo(forMe);
    sendToAwake(forThem);
    getRoom().examineSay(this, s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void chat(String s)

  {
    s = variableAlign("#c[#n" + getName() + "#c]#n -> #g" + s, 65);
    ClientList.echoAll(s, CSTATE_NORMAL);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void emote(String str)

  {
    if (sleepCheck()) return;
    str = roomAlign(name + " " + str);
    echo(str);
    sendToAwake(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void blindEmote(String str)

  {
    str = roomAlign(name + " " + str);
    sendToAwake(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void xBlindEmote(Entity ENT, String str)

  {
    str = roomAlign(name + " " + str);
    sendToAwake(ENT, str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setActive(boolean b)

  {
    if (myClient != null)
      myClient.setActive(b);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void echo(String s)

  {
    if (myClient != null)
      myClient.addOutput(s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void fightEcho(String s)

  {
    if (myClient != null)
      myClient.addFightOutput(s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void awakeEcho(String s)

  {
    if ((myClient != null) && (playerState != PSTATE_SLEEPING))
      myClient.addOutput(s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void awakeFightEcho(String s)

  {
    if ((myClient != null) && (playerState != PSTATE_SLEEPING))
      myClient.addFightOutput(s);
  }

  ////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////

  public void sendToAwake(String s)

  {
    getRoom().xecho(this, s, ECHO_AWAKE);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void sendToAwake(Entity ENT, String s)

  {
    getRoom().xecho(this, ENT, s, ECHO_AWAKE);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void smsg(String s)

  {
    ClientList.systemMessage(s, getLevel(), this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void smsg(String s, Entity ENT)

  {
    ClientList.systemMessage(s, getLevel(), ENT);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addLegendMark(String s)

  {
    if (isPlayer()) castChar().addLegendMark(s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void passiveOutput(Entity T, String s1, String s2, String s3, String s4)

  {
    fightEcho(s1);
    if (this != T) T.fightEcho(s2);
    getGroup().combatMessage(this, s3);
    if (this != T) T.getGroup().combatMessage(T, s3);
    getRoom().observerEcho(this, T, s4);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void combatOutput(Entity T, String s1, String s2, String s3, String s4)

  {
    fightEcho(s1);
    if (this != T) T.fightEcho(s2);
    getGroup().combatMessage(this, s3);
    if (this != T) T.getGroup().combatMessage(T, s3);
    getRoom().observerEcho(this, T, s4);

    if (T.isDead())

    {
      T.getRoom().fightEcho(T.getPName() + " is dead!", ECHO_AWAKE);
      receiveExperience(T);
      T.die(true, true);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean tell(String trg, String msg, boolean first)

  {
    String str;
    int size = ClientList.getSize();

    if (trg.equals("")) {
      echo("Who do you want to tell?");
      return false; }

    for (int i=0; i<size; i++)

    {
      Client C = ClientList.getClient(i);

      if (((first) && (trg == C.getCharInfo().getLName()))
      || ((!first) && (shortFor(trg, C.getCharInfo().getLName()))))

      {
        if (msg.equals(""))
          echo("What do you want to tell them?");
        else if (C.getClientState() == CSTATE_LINKDEAD)
          echo(C.getCharInfo().getName() + " is linkdead.");
        else if (C.getClientState() == CSTATE_WRITING)
          echo(C.getCharInfo().getName() + " is writing a message.");
        else if (C.getClientState() == CSTATE_BUILDING)
          echo(C.getCharInfo().getName() + " is building.");

        else

        {
          str = "#CYou tell " + C.getCharInfo().getName() + ", '" + msg + "#C'#N";
          echo(align(str));
          str = "#C" + name + " tells you, '" + msg + "#C'#N";
          C.addOutput(align(str));
        }

        return true;
      }
    }

    if (!first) return false;
    if (tell(trg, msg, false)) return true;
    echo("There is nobody around by that name.");
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void look(String pretext)

  {
    if (sleepCheck()) return;

    if (getRoom().isDark()) {
      echo("It's too dark to see in here.");
      return; }

    boolean no_exit = true;
    String str = "";
    String VN;

    str += getRoom().displayItemList();
    str += getRoom().getLookTitles(this);

    if (getLevel() < 100)

    {
      str = str + "\r\n" + "[Exits: ";
      if (getRoom().getN() > 0) { str = str + "#Cn#N"; no_exit = false; }
      if (getRoom().getE() > 0) { str = str + "#Ce#N"; no_exit = false; }
      if (getRoom().getS() > 0) { str = str + "#Cs#N"; no_exit = false; }
      if (getRoom().getW() > 0) { str = str + "#Cw#N"; no_exit = false; }
      if (getRoom().getU() > 0) { str = str + "#Cu#N"; no_exit = false; }
      if (getRoom().getD() > 0) { str = str + "#Cd#N"; no_exit = false; }
      if (no_exit) str += "#CNone#N";
      str = str + "]";
    }

    else

    {
      str = str + "\r\n" + "[Exits: ";
      if (getRoom().getN() > 0)
        { str = str + "#Cn #n(#c" + getRoom().getN() + "#n)  #N"; no_exit = false; }
      if (getRoom().getE() > 0)
        { str = str + "#Ce #n(#c" + getRoom().getE() + "#n)  #N"; no_exit = false; }
      if (getRoom().getS() > 0)
        { str = str + "#Cs #n(#c" + getRoom().getS() + "#n)  #N"; no_exit = false; }
      if (getRoom().getW() > 0)
        { str = str + "#Cw #n(#c" + getRoom().getW() + "#n)  #N"; no_exit = false; }
      if (getRoom().getU() > 0)
        { str = str + "#Cu #n(#c" + getRoom().getU() + "#n)  #N"; no_exit = false; }
      if (getRoom().getD() > 0)
        { str = str + "#Cd #n(#c" + getRoom().getD() + "#n)  #N"; no_exit = false; }
      if (no_exit) str += "#CNone#N";
      str = str + "]";
    }

    if (getLevel() < 100) VN = "";
    else VN = "#n (#c" + getRoom().getID() + "#n)#N";

    String output = getZone().getColor() + getRoom().getTitle() + "#N"
                  + VN + "\r\n" + getRoom().getDesc() + str;

    if (pretext.length() <= 0) echo(output);
    else echo(pretext + "\r\n\n" + output);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void lookAt(String target)

  {
    if (sleepCheck()) return;

    if (getRoom().isDark()) {
      echo("It's too dark to see in here.");
      return; }

    if ((key(target).equals("i")) || (key(target).equals("in"))) {
      ItemUse.displayItemListContainer(this, last(target));
      return; }

    if (target.equals("board")) { read("board"); return; }
    if (ItemUse.findItemLook(this, target)) return;

    target = key(target);
    Entity ENT = getRoom().findEntity(this, target);

    if ((getRoom().getNumLooks() > 6) && (getRoom().searchLooks(target)))
      echo(getRoom().getLook(getRoom().findLook(target)));

    else if ((target.equals("n")) || (target.equals("north"))) echo(getRoom().getLook(0));
    else if ((target.equals("s")) || (target.equals("south"))) echo(getRoom().getLook(1));
    else if ((target.equals("e")) || (target.equals("east")))  echo(getRoom().getLook(2));
    else if ((target.equals("w")) || (target.equals("west")))  echo(getRoom().getLook(3));
    else if ((target.equals("u")) || (target.equals("up")))    echo(getRoom().getLook(4));
    else if ((target.equals("d")) || (target.equals("down")))  echo(getRoom().getLook(5));

    else if (ENT != null)

    {
      echo(ENT.getDesc() + "\r\n" + Equipment.displayEquipment(ENT, true));

      if (ENT != this)

      {
        ENT.awakeEcho(name + " looks at you.");
        getRoom().xecho(this, ENT, name + " looks at " + ENT.getName() + ".");
      }
    }

    else if (match(target, "no", "north")) echo(getRoom().getLook(0));
    else if (match(target, "so", "south")) echo(getRoom().getLook(1));
    else if (match(target, "ea", "east"))  echo(getRoom().getLook(2));
    else if (match(target, "we", "west"))  echo(getRoom().getLook(3));
    else if (match(target, "up", "up"))    echo(getRoom().getLook(4));
    else if (match(target, "do", "down"))  echo(getRoom().getLook(5));

    else echo("There is no " + target + " here.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void lookTrack()

  {
    if (myTrack == null) return;
    Track T = (Track) skillList.get(0);
    if (T == null) return;
    echo("");
    T.track(myTrack);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String tryMove(int dir)

  {
    GOOD = false;

    if ((isPlayer()) && (myClient == null))
      return "Unposessed Immortal";

    if (getPlayerState() == PSTATE_FIGHTING)
      return "You're in the middle of a fight!";

    if (getPlayerState() == PSTATE_SLEEPING)
      return "You can't move in your sleep!";

    if (getPlayerState() == PSTATE_RESTING)
      return "Try standing up first.";

    if (getPlayerState() == PSTATE_SITTING)
      return "Try standing up first.";

    if ((myClient != null) && (myClient.getClientState() == CSTATE_IMM_AT))
      return "You can't do that.";

    GOOD = true;

    return getRoom().checkBlockers(this, dir);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void move(String dir)

  {
    if (dir.equalsIgnoreCase("N")) move(DIRECTION_N);
    else if (dir.equalsIgnoreCase("S")) move(DIRECTION_S);
    else if (dir.equalsIgnoreCase("E")) move(DIRECTION_E);
    else if (dir.equalsIgnoreCase("W")) move(DIRECTION_W);
    else if (dir.equalsIgnoreCase("U")) move(DIRECTION_U);
    else if (dir.equalsIgnoreCase("D")) move(DIRECTION_D);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void move(int dir)

  {
    int target = 0;
    String incoming = "";
    String direction = "";
    ArrayList A = new ArrayList();

    if (dir == DIRECTION_N)      { target = getRoom().getN();  direction = "north";  incoming = "in from the south"; }
    else if (dir == DIRECTION_S) { target = getRoom().getS();  direction = "south";  incoming = "in from the north"; }
    else if (dir == DIRECTION_E) { target = getRoom().getE();  direction = "east";   incoming = "in from the west";  }
    else if (dir == DIRECTION_W) { target = getRoom().getW();  direction = "west";   incoming = "in from the east";  }
    else if (dir == DIRECTION_U) { target = getRoom().getU();  direction = "up";     incoming = "up from below";     }
    else if (dir == DIRECTION_D) { target = getRoom().getD();  direction = "down";   incoming = "down from above";   }

    Room last = getRoom();
    Room current = World.getRoom(target);
    String walks = getPName() + " walks " + direction + ".";
    String walksin = getPName() + " walks " + incoming + ".";
    String youwalk = "You walk " + direction + ".";
    String otherswalk;

    String leader_move = tryMove(dir);

    if (target == 0) {
      echo("There is no exit in that direction.");
      return; }

    if (leader_move != "OK") {
      echo(leader_move);
      return; }

    for (int i=0; i<followers.size(); i++)

    {
      String temp = getFollower(i).tryMove(dir);

      if ((getFollower(i).getRoom() == last) && (temp.equals("OK")))

      {
        walks += "\r\n" + getFollower(i).getPName() + " walks " + direction + ".";
        walksin += "\r\n" + getFollower(i).getPName() + " walks " + incoming + ".";
        youwalk += "\r\n" + getFollower(i).getPName() + " follows you.";
        last.leave(getFollower(i), LEAVE_MOVE);
        current.enter(getFollower(i), ENTER_MOVE);
        A.add(getFollower(i).getName());
      }
    }

    getRoom().leave(this, LEAVE_MOVE);
    current.enter(this, ENTER_MOVE);

    if (dir != trackDir)
      stopTracking();

    look(youwalk);
    lookTrack();

    last.pblindEcho(this, walks);
    current.pblindEcho(this, walksin);

    for (int i=0; i<followers.size(); i++)

    {
      Entity F = getFollower(i);

      if (searchArrayList(A, F.getName()))

      {
        otherswalk = name + " walks " + direction + ".";

        for (int j=0; j<followers.size(); j++)
          if ((getFollower(j).getName() != F.getName()) && (searchArrayList(A,getFollower(j).getName())))
            otherswalk += "\r\n" + getFollower(j).getPName() + " walks " + direction + ".";

        if (dir == F.getTrackingDir())
          F.stopTracking();

        F.look(otherswalk + "\r\nYou follow " + name + " " + direction + ".");
        F.lookTrack();
      }

      else if (F.getRoom() == last) {
        String temp = F.tryMove(dir);
        if (GOOD) F.echo(walks + "\r\n" + temp);
        else F.echo(walks); }

      else if (F.getRoom() == current)
        F.echo(walksin);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void speedwalk(String str)

  {
    if (myClient == null) return;

    if (playerState == PSTATE_FIGHTING) {
      echo("You're in the middle of a fight!");
      return; }

    if (str.length() == 0) return;

    if (str.length() == 1) {
      myClient.putCommand(str);
      return; }

    String s = str.substring(1, 2);
    String num = str.substring(0, 1);

    if (number(num)) {
      int n = Integer.parseInt(num) - 1;
      str = str.substring(1, str.length());
      str = n + str;
      if (str.charAt(0) == '1') str = str.substring(1, str.length());
      else if (str.charAt(0) == '0') str = str.substring(2, str.length()); }

    else {
      str = str.substring(1, str.length());
      s = num; }

    move(s.toUpperCase());

    myClient.forceCommand(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void read(String input)

  {
    if (input.equals("board")) {
      MessageBoard.readBoard(this);
      return; }

    ArrayList ilist = getRoom().getItemList().getList();
    Item board = null;
    int size = ilist.size();

    for (int i=0; i<size; i++)
    if (board == null)
    if (((Item)ilist.get(i)).getBoard() != null)
      board = ((Item)ilist.get(i));

    if (board == null) {
      echo("There is no message board here.");
      return; }

    board.getBoard().readNote(this, input);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void remove(String input)

  {
    if (number(input))

    {
      ArrayList ilist = getRoom().getItemList().getList();
      Item board = null;
      int size = ilist.size();

      for (int i=0; i<size; i++)
        if (board == null)
        if (((Item)ilist.get(i)).getBoard() != null)
          board = ((Item)ilist.get(i));

      if (board == null) {
        echo("There is no message board here.");
        return; }

      board.getBoard().removeNote(this, input);
    }

    else Equipment.unequip(this, input);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void follow(String target)

  {
    boolean was_grouped = false;
    Entity ENT = getRoom().findEntity(this, target);

    if (ENT == this)

    {
      if (following == this) echo("You are already following yourself.");

      else

      {
        Entity last = following;
        String stops = getPName() + " stops following " + following.getName() + ".";
        String leaving = getPName() + " is leaving this group.";

        boolean grouped = following.getGroup().inGroup(this);

        stopFollowing();

        echo("You stop following " + last.getName() + ".");
        last.echo(getPName() + " stops following you.");
        last.getGroup().groupRoomMessage(getRoom(), last, stops);
        last.getGroup().groupDistMessage(getRoom(), last, leaving);
        getRoom().gblindEcho(last, this, last, null, stops);

        if (grouped)

        {
          echo("You leave " + possessive(last.getName()) + " group.");
          last.echo(leaving);
          last.getGroup().groupRoomMessage(getRoom(), last, leaving);
        }
      }
    }

    else if (ENT != null)

    {
      String n = ENT.getName();
      String N = ENT.getPName();
      String f = ENT.getFollowing().getName();
      String m = ENT.getFollowing().him();
      String h = ENT.He();

      if (following == ENT)
        echo("You are already following " + n + ".");
      else if (ENT.getFollowing() == this)
        echo("You cannot follow " + n + ".  " + h + " is already following you.");
      else if (followers.size() > 0)
        echo("You are already being followed.");
      else if (ENT.getFollowing() != ENT)
        echo(N + " is following " + f + ".  Try following " + m + " instead.");
      else if (!ENT.getFollowable())
        echo(N + " does not want to be followed right now.");

      else

      {
        Entity stop = following;
        String starts = getPName() + " starts following " + ENT.getName() + ".";
        String stops = getPName() + " stops following " + ENT.getName() + ".";
        String leaving = getPName() + " is leaving this group.";
        String forgroup = getPName() + " starts following " + n + ".";

        if (following != this)

        {
          boolean grouped = following.getGroup().inGroup(this);
          was_grouped = grouped;

          echo("You stop following " + following.getName() + ".");
          if (grouped) echo("You leave " + possessive(stop.getName()) + " group.");

          following.echo(getPName() + " stops following you.");
          if (grouped) following.echo(getPName() + " is leaving this group.");
          if (following.getRoom() == getRoom()) following.echo(starts);

          ENT.echo(getPName() + " stops following " + following.getName() + ".");
          if (!grouped) getRoom().xecho(this, ENT, following, stops);

          stop.getGroup().groupRoomMessage(getRoom(), stop, stops);
          if (grouped) stop.getGroup().groupRoomMessage(getRoom(), stop, leaving);

          stopFollowing();
        }

        ENT.addFollower(this);
        following = ENT;

        echo("You start following " + n + ".");
        following.echo(getPName() + " starts following you.");

        if (was_grouped)

        {
          getRoom().gblindEcho(stop, this, stop, following, starts);
          stop.getGroup().groupRoomMessage(getRoom(), stop, forgroup);
          stop.getGroup().groupDistMessage(getRoom(), stop, leaving);
        }

        else getRoom().xecho(this, stop, following, starts);
      }
    }

    else echo("There's nobody here by that name.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void ditch(String target)

  {
    int target_num = clipNumber(target);
    target = clipString(target);

    if (followers.size() <= 0) {
      echo("There is nobody following you.");
      return; }

    for (int i=0; i<followers.size(); i++)

    {
      if (getFollower(i).checkTargets(target))
        target_num--;

      if (target_num <= 0)

      {
        getFollower(i).follow(getFollower(i).getLName());
        return;
      }
    }

    echo("Nobody by that name is following you.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeFollower(Entity ENT)

  {
    for (int i=0; i<followers.size(); i++)
    if (getFollower(i) == ENT)
    followers.remove(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void stopFollowing()

  {
    if (following != this) {
      following.removeFollower(this);
      following = this; }

    myGroup.destroyGroup();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearGroup()

  {
    followers = new ArrayList();
    myGroup = new Group(this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearFollowers()

  {
    for (int i=followers.size()-1; i>=0; i--)

    {
      Entity F = getFollower(i);
      F.setFollowing(F);
      F.setGroup(new Group(F));
      removeFollower(F);
    }

    myGroup = new Group(this);

    if (getFollowing() != this)

    {
      getFollowing().removeFollower(this);
      getFollowing().getGroup().removeMemberSilent(this);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void terminateGroupAndFollowers(boolean combatRedirect)

  {
    if (combatRedirect) getRoom().setCombatRedirect(true);

    stopFighting();
    stopTracking();

    if (isPlayer()) follow(getName());
    else follow(first(castMob().getKeywords()));
    Group.ungroup(this, "all");

    if (combatRedirect) getRoom().setCombatRedirect(false);

    if (getClient() != null)
      getClient().clearOutputBuffers();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ArrayList groupAll()

  {
    ArrayList namesAdded = new ArrayList();

    for (int i=0; i<followers.size(); i++)

    {
      Entity Follower = getFollower(i);

      if ((Follower.getRoom() == getRoom()) && (!myGroup.inGroup(Follower)))

      {
        for (int j=0; j<myGroup.getSize(); j++)
        if (Follower != myGroup.getMember(j))

        {
          if (!searchArrayList(namesAdded, Follower.getName()))
            namesAdded.add(Follower.getName());

          Follower.getGroup().addMemberSilent(myGroup.getMember(j));
          myGroup.getMember(j).getGroup().addMemberSilent(Follower);
        }
      }
    }

    return namesAdded;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean targetingGroup(Group G)

  {
    if (myTarget != null)
    if (G.inGroup(myTarget))
      return true;

    int size = targetList.size();

    for (int i=0; i<size; i++)
    if (G.inGroup((Entity)targetList.get(i)))
      return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setTarget(Entity targ)

  {
    if (myTarget == targ) { return;                  }
    if (myTarget == null) { myTarget = targ; return; }
    if (targetList.contains(targ)) { return;         }

    targetList.add(targ);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeTarget(Entity targ)

  {
    if (myTarget == targ)

    {
      if (targetList.size() > 0) myTarget = (Entity)targetList.remove(0);
      else myTarget = null;
    }

    if (targetList.contains(targ))
      targetList.remove(targetList.indexOf(targ));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearTargets()

  {
    myTarget = null;
    targetList.clear();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void tickUpdate()

  {
    regen();

    for (int i=effectList.size()-1; i>=0; i--)
      getEffect(i).decrement();

    List eqlist = Arrays.asList(getEquipment());

    ItemUse.updateDecayItemList(getInventory().getList());
    ItemUse.updateDecayItemList(new ArrayList(eqlist));

    if (!isPlayer()) castMob().decrementQuestions();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int resistMagic(int damage)

  {
    return damage - ((damage * getTrueResistance()) / 100);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void stopFighting()

  {
    playerState = PSTATE_STANDING;
    clearTargets();
    getRoom().updateTargets();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void executeSkill(String key, String rem)

  {
    if (rem.length() <= 0) rem = key;
    else rem = key + " " + rem;

    int found = -1;

    for (int i=0; i<skillList.size(); i++)
      if (rem.indexOf(getSkill(i).getName().toLowerCase()) == 0) found = i;

    if (found == -1) {
      echo("You can't do that.");
      return; }

    String trg = rem.substring(getSkill(found).getName().length(), rem.length());
    if (trg.length() > 1) trg = trg.substring(1, trg.length());

    getSkill(found).execute(trg);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Skill findSkill(int skillNum)

  {
    if (skillList == null) return null;
    for (int i=0; i<skillList.size(); i++)
    if (getSkill(i).getID() == skillNum) return getSkill(i);
    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Skill findSkill(String skillName)

  {
    if (skillList == null) return null;
    for (int i=0; i<skillList.size(); i++)
    if (getSkill(i).getName().equalsIgnoreCase(skillName)) return getSkill(i);
    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Spell findSpell(int spellNum)

  {
    if (spellList == null) return null;
    for (int i=0; i<spellList.size(); i++)
    if (getSpell(i).getID() == spellNum) return getSpell(i);
    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Spell findSpell(String spellName)

  {
    if (spellList == null) return null;
    for (int i=0; i<spellList.size(); i++)
    if (getSpell(i).getName().equalsIgnoreCase(spellName)) return getSpell(i);
    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Ability findAbility(String abilityName)

  {
    Skill Sk = findSkill(abilityName);
    if (Sk != null) return Sk;
    return findSpell(abilityName);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Ability removeAbility(String abilityName)

  {
    for (int i=skillList.size()-1; i>=0; i--)
    if (getSkill(i).getName().equalsIgnoreCase(abilityName))
      return (Skill) skillList.remove(i);

    for (int i=spellList.size()-1; i>=0; i--)
    if (getSpell(i).getName().equalsIgnoreCase(abilityName))
      return (Spell) spellList.remove(i);

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void cast(String rem)

  {
    int found = -1;

    for (int i=0; i<spellList.size(); i++)
      if (rem.indexOf(getSpell(i).getName().toLowerCase()) == 0) found = i;

    if (found == -1) {
      echo("You don't know any spell by that name.");
      return; }

    String trg = rem.substring(getSpell(found).getName().length(), rem.length());
    if (trg.length() > 1) trg = trg.substring(1, trg.length());

    getSpell(found).cast(trg);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void sortSkills()

  {
    for (int b=0; b<skillList.size()-1; b++)
    for (int i=0; i<skillList.size()-1; i++)
    if (getSkill(i).getImp() > getSkill(i+1).getImp())

    {
      Skill S1 = getSkill(i);
      Skill S2 = getSkill(i+1);
      skillList.set(i, S2);
      skillList.set(i+1, S1);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void sortSpells()

  {
    for (int b=0; b<spellList.size()-1; b++)
    for (int i=0; i<spellList.size()-1; i++)
    if (getSpell(i).getImp() > getSpell(i+1).getImp())

    {
      Spell S1 = getSpell(i);
      Spell S2 = getSpell(i+1);
      spellList.set(i, S2);
      spellList.set(i+1, S1);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void displaySkills(boolean spells)

  {
    int x, lsize = 0, rsize = 0, count = 0;
    String tempstr = "";

    int size = skillList.size();
    if (spells) size = spellList.size();

    for (int i=0; i<size; i++)

    {
      count++;
      if (spells) x = getSpell(i).getName().length();
      else x = getSkill(i).getName().length();
      if (((count % 2) == 1) && (x > lsize)) lsize = x;
      if (((count % 2) == 0) && (x > rsize)) rsize = x;
    }

    String header = "Skills";
    if (spells) header = "Spells";

    if (lsize == 0) {
      tempstr += "  #n+-------------------+\r\n";
      tempstr += "  | #NNo " + header + " Learned #n|\r\n";
      tempstr += "  +-------------------+#N"; }

    else

    {
      count = 0;
      String border = "  #n+" + xchars(lsize+12,"-") + "+#N";
      if (rsize > 0) border += "#n" + xchars(rsize+12,"-") + "+#N";
      tempstr += border + "\r\n";

      for (int i=0; i<size; i++)

      {
        count++;
        String S;
        int L;

        if (spells) S = getSpell(i).getName();
        else S = getSkill(i).getName();
        if (spells) L = getSpell(i).getLevel();
        else L = getSkill(i).getLevel();

        if ((count % 2) == 0) x = rsize+1;     else x = lsize+1;
        if ((count % 2) != 0) tempstr += "  #n| #Y";  else tempstr += " #Y";
        tempstr += S + xchars(x-S.length()," ") + "#n[#N";
        tempstr += tenSpace(L,2) + L + "#n/#N100#n] #n|";
        if ((count % 2) == 0) tempstr += "\r\n";
      }

      if ((count % 2) == 1) {
        if (count > 1) tempstr += xchars(12+rsize," ") + "#n|";
        tempstr += "\r\n"; }

      tempstr += border;
    }

    echo(tempstr);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String displayAllSkills(boolean spells)

  {
    int count = 0;

    String border = "#n+" + xchars(31,"-") + "+" + xchars(31,"-") + "+#N";
    String tempstr = "\r\n" + border + "\r\n";

    int size = World.getNumSkills();
    if (spells) size = World.getNumSpells();

    for (int i=0; i<size; i++)

    {
      Ability S;

      if (spells) S = World.getSpell(i);
      else S = World.getSkill(i);

      if ((S.getClasses().indexOf(getSClass()) != -1)
      || (S.getClasses().equalsIgnoreCase("All")))

      {
        count++;
        if ((count % 2) != 0) tempstr += "#n| #Y";  else tempstr += " #Y";
        tempstr += S.getName() + xchars(30-S.getName().length()," ") + "#n|";
        if ((count % 2) == 0) tempstr += "\r\n";
      }
    }

    if ((count % 2) == 1)
      tempstr += xchars(31," ") + "#n|\r\n";

    tempstr += border;

    if (count == 0) return "";
    return tempstr;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean addEffect(Effect e)

  {
    boolean foundBetter = false;

    for (int i=0; i<effectList.size(); i++)
    if (getEffect(i).getID() == e.getID())

    {
      Effect E = getEffect(i);

      if ((E.getPermanent())
      || ((E.getDuration() > e.getDuration()) && (!e.getPermanent())))
        foundBetter = true;
    }

    if (foundBetter) return false;

    int pos = findEffect(e.getID());

    if (pos != -1) {
      effectList.set(pos, e);
      return false; }

    effectList.add(e);

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean removeEffect(Effect e)

  {
    boolean removed = false;

    for (int i=effectList.size()-1; i>=0; i--)
    if (getEffect(i).getID() == e.getID())

    {
      removed = true;
      effectList.remove(i);
    }

    return removed;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int findEffect(int idNum)

  {
    for (int i=0; i<effectList.size(); i++)
    if (getEffect(i).getID() == idNum) return i;
    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean removeItemEffect(String s)

  {
    boolean removed = false;

    for (int i=effectList.size()-1; i>=0; i--)
    if (getEffect(i).getName().equalsIgnoreCase(s))

    {
      getEffect(i).dissipate(true);
      removed = true;
    }

    return removed;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Item removeItemInventory(String str)

  {
    ArrayList itemList = getInventory().getList();

    for (int i=0; i<itemList.size(); i++)

    {
      Item I = (Item)itemList.get(i);

      if (abbreviation(removeColors(I.getName()), str)) {
        inventory.remove(I);
        return I; }
    }

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ArrayList getAllItems()

  {
    ArrayList A = new ArrayList(getInventory().getList());
    A.addAll(Arrays.asList(equipment));

    while(A.contains(null)) A.remove(A.indexOf(null));

    int size = A.size();

    for (int i=0; i<size; i++)
      expandItemList(A, (Item)A.get(i));

    return A;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ArrayList getAllTopItems()

  {
    ArrayList A = new ArrayList(getInventory().getList());

    for (int i=0; i<Equipment.NUM_PLACES; i++)
    if (equipment[i] != null)
    if (!A.contains(equipment[i]))
      A.add(equipment[i]);

    return A;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void expandItemList(ArrayList A, Item I)

  {
    ArrayList ilist = I.getItemList().getList();
    int size = I.getNumItems();

    for (int i=0; i<size; i++)

    {
      A.add(ilist.get(i));
      expandItemList(A, (Item)ilist.get(i));
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void itemIn(Item I, int type)

  {
    I.setRoom(null);
    I.setParent(null);
    I.setEntity(this);

    if (type == IGETR)          { }
    else if (type == IGETC)     { }
    else if (type == IDISARM)   { }
    else if (type == ILOAD)     { }
    else if (type == IPOPE)     { }
    else if (type == IBUY)      { }
    else if (type == IWITHDRAW) { }
    else if (type == IGIVE)     { }
    else System.out.println("Unexpected item in type: " + type);

    if (I.isGold()) setGold(getGold() + I.getWorth());
    else addItemInventory(I);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void itemOut(Item I, int type)

  {
    if (type == IDROP)             { }
    else if (type == IDISARM)      { }
    else if (type == IDECAYE)      { }
    else if (type == IPURGE)       { }
    else if (type == ISAC)         { }
    else if (type == IDEPOSIT)     { }
    else if (type == IEAT)         { }
    else if (type == IUSE)         { }
    else if (type == IPUT)         { }
    else if (type == ISELL)        { }
    else if (type == IENTERCORPSE) { }
    else if (type == IGIVE)        { }
    else System.out.println("Unexpected item out type: " + type);

    if (I.isGold()) setGold(getGold() - I.getWorth());
    else removeItemInventory(I);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addItemInventory(Item I)     { inventory.add(I);             }
  public void removeItemInventory(Item I)  { inventory.remove(I);          }
  public boolean canFit(Item I)            { return inventory.canFit(I);   }
  public boolean findItemInventory(Item I) { return inventory.contains(I); }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}
