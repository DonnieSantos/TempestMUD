import java.io.*;
import java.lang.*;
import java.util.*;

public class World extends Utility

{
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static Room roomList[];
  private static Item itemList[];
  private static Clan clanList[];
  private static Spell spellList[];
  private static Skill skillList[];
  private static Mobile mobList[];
  private static Religion religionList[];
  private static MessageBoard boardList[];
  private static BankChain bankList[];
  private static Action actionList[];
  private static Zone zoneList[];
  private static ArrayList fightList;
  private static ArrayList charList;
  private static ArrayList accountList;

  public static final int MAX_ROOMS   = 10000;
  public static final int MAX_ITEMS   = 10000;
  public static final int MAX_MOBS    = 10000;
  public static final int MAX_ACTIONS = 10000;
  public static final int MAX_CLANS   = 100;
  public static final int MAX_BOARDS  = 100;
  public static final int MAX_BANKS   = 100;
  public static final int MAX_ZONES   = 100;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public World()                              {                                                }
  public static Char getCharacter(int i)      { return (Char) charList.get(i);                 }
  public static Account getAccount(int i)     { return (Account) accountList.get(i);           }
  public static Room getRoom(int i)           { return roomList[bound(i,0,MAX_ROOMS-1)];       }
  public static Item getItem(int i)           { return itemList[bound(i,0,MAX_ITEMS-1)];       }
  public static Clan getClan(int i)           { return clanList[bound(i,0,MAX_CLANS-1)];       }
  public static Spell getSpell(int i)         { return spellList[bound(i,0,getNumSpells()-1)]; }
  public static Skill getSkill(int i)         { return skillList[bound(i,0,getNumSkills()-1)]; }
  public static Mobile getMob(int i)          { return mobList[bound(i,0,MAX_MOBS-1)];         }
  public static Religion getReligion(int i)   { return religionList[bound(i,0,MAX_CLANS-1)];   }
  public static MessageBoard getBoard(int i)  { return boardList[bound(i,0,MAX_BOARDS-1)];     }
  public static BankChain getBank(int i)      { return bankList[bound(i,0,MAX_BANKS-1)];       }
  public static Action getAction(int i)       { return actionList[bound(i,0,MAX_ACTIONS-1)];   }
  public static Zone getZone(int i)           { return zoneList[bound(i,0,MAX_ZONES-1)];       }
  public static void setMob(Mobile m)         { mobList[m.getID()] = m;                        }
  public static void setRoom(Room r)          { roomList[r.getID()] = r;                       }
  public static void setItem(Item i)          { itemList[i.getID()] = i;                       }
  public static void setClan(Clan c)          { clanList[c.getID()] = c;                       }
  public static void setReligion(Religion r)  { religionList[r.getID()] = r;                   }
  public static void setBoard(MessageBoard b) { boardList[b.getID()] = b;                      }
  public static void setBank(BankChain b)     { bankList[b.getID()] = b;                       }
  public static void setAction(Action A)      { actionList[A.getID()] = A;                     }
  public static void setZone(Zone Z)          { zoneList[Z.getID()] = Z;                       }
  public static int getNumCharacters()        { return charList.size();                        }
  public static int getNumAccounts()          { return accountList.size();                     }
  public static int getNumZones()             { return MAX_ZONES;                              }
  public static int getSize()                 { return MAX_ROOMS;                              }
  public static int getItemSize()             { return MAX_ITEMS;                              }
  public static int getClanSize()             { return MAX_CLANS;                              }
  public static int getReligionSize()         { return MAX_CLANS;                              }
  public static int getMobSize()              { return MAX_MOBS;                               }
  public static int getActionSize()           { return MAX_ACTIONS;                            }
  public static int getNumBanks()             { return MAX_BANKS;                              }
  public static int getNumBoards()            { return MAX_BOARDS;                             }
  public static int getNumSpells()            { return Spell.getNumSpells();                   }
  public static int getNumSkills()            { return Skill.getNumSkills();                   }
  public static void removeMob(int mID)       { mobList[mID] = null;                           }
  public static void removeRoom(int rID)      { roomList[rID] = null;                          }
  public static void removeItem(int iID)      { itemList[iID] = null;                          }
  public static void removeClan(int cID)      { clanList[cID] = null;                          }
  public static void removeReligion(int rID)  { religionList[rID] = null;                      }
  public static void removeBoard(int bID)     { boardList[bID] = null;                         }
  public static void removeBank(int bID)      { bankList[bID] = null;                          }
  public static void removeAction(int aID)    { actionList[aID] = null;                        }
  public static void removeZone(int zID)      { zoneList[zID] = null;                          }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void save()

  {
    FileManager.saveZones(zoneList);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void loadWorld()

  {
    zoneList = new Zone [MAX_ZONES];
    roomList = new Room [MAX_ROOMS];
    itemList = new Item [MAX_ITEMS];
    clanList = new Clan [MAX_CLANS];
    religionList = new Religion [MAX_CLANS];
    boardList = new MessageBoard [MAX_BOARDS];
    bankList = new BankChain [MAX_BANKS];
    mobList = new Mobile [MAX_MOBS];
    actionList = new Action [MAX_ACTIONS];

    for (int i=0; i<MAX_ZONES; i++) zoneList[i] = null;
    for (int i=0; i<MAX_ROOMS; i++) roomList[i] = null;
    for (int i=0; i<MAX_ITEMS; i++) itemList[i] = null;
    for (int i=0; i<MAX_CLANS; i++) clanList[i] = null;
    for (int i=0; i<MAX_CLANS; i++) religionList[i] = null;
    for (int i=0; i<MAX_BOARDS; i++) boardList[i] = null;
    for (int i=0; i<MAX_BANKS; i++) bankList[i] = null;
    for (int i=0; i<MAX_MOBS; i++) mobList[i] = null;
    for (int i=0; i<MAX_ACTIONS; i++) actionList[i] = null;

    fightList = new ArrayList();
    charList = new ArrayList();
    accountList = new ArrayList();

    FileManager.loadBanks();
    FileManager.loadZones();
    FileManager.loadBankAccounts();
    FileManager.loadClans();
    FileManager.loadReligions();
    FileManager.loadBoards();
    FileManager.loadActions();
    FileManager.saveClans();
    FileManager.saveReligions();

    initSpellList();
    initSkillList();

    repopMobs();
    repopItems();

    FileManager.loadAllAccounts();
    FileManager.loadAllPlayers();

    Help.load();

    destroyDuplicateItems();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void animate()

  {
    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)

    {
      for (int j=0; j<roomList[i].getNumEnts(); j++)
      if (!roomList[i].getEnt(j).isPlayer())
        roomList[i].getEnt(j).castMob().setActed(false);
    }

    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)
      roomList[i].animate();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void tickUpdate()

  {
    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)

    {
      int numItems = roomList[i].getNumItems();
      int numEnts = roomList[i].getNumEnts();

      if (numItems > 0)
        ItemUse.updateDecayItemList(roomList[i].getItemList().getList());

      for (int j=numEnts-1; j>=0; j--)
        roomList[i].getEnt(j).tickUpdate();
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void addFightList(Room r)

  {
    if (!fightList.contains(r))
      fightList.add(r);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void removeFightList(Room r)

  {
    if (fightList.contains(r))
      fightList.remove(fightList.indexOf(r));
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void updateFightList()

  {
    int size = fightList.size();

    for (int i=0; i<size; i++)
      ((Room)fightList.get(i)).updateCombat();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void repopMobs()

  {
    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].repopMobs();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void repopItems()

  {
    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].repopItems();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void clearMobs()

  {
    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].clearMobs();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void clearItems()

  {
    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].clearMobs();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int createRoom(Zone Z)

  {
    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] == null)

    {
      roomList[i] = new Room(i);
      roomList[i].setTitle("New Room");
      roomList[i].setDesc("Blank Description");
      roomList[i].setZoneID(Z.getID());
      zoneList[Z.getID()].addRoom(i);
      return i;
    }

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int createItem(Zone Z)

  {
    for (int i=0; i<MAX_ITEMS; i++)
    if (itemList[i] == null)

    {
      itemList[i] = new Item(i);
      itemList[i].setZoneID(Z.getID());
      zoneList[Z.getID()].addItem(i);
      return i;
    }

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int createMobile(Zone Z)

  {
    for (int i=0; i<MAX_MOBS; i++)
    if (mobList[i] == null)

    {
      mobList[i] = new Mobile(i);
      mobList[i].setZoneID(Z.getID());
      Z.addMob(i);
      return i;
    }

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int createAction(String aname)

  {
    for (int i=0; i<MAX_ACTIONS; i++)
    if (actionList[i] == null)

    {
      actionList[i] = new Action(i);
      actionList[i].setName(aname);
      return i;
    }

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int createClan()

  {
    for (int i=0; i<MAX_CLANS; i++)
    if (clanList[i] == null)

    {
      clanList[i] = new Clan(i, "Generic Clan");
      return i;
    }

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int createReligion()

  {

    for (int i=0; i<MAX_CLANS; i++)
    if (religionList[i] == null)

    {
      religionList[i] = new Religion(i, "Generic Religion");
      return i;
    }

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int createBoard(String input)

  {
    for (int i=0; i<MAX_BOARDS; i++)
    if (boardList[i] == null)

    {
      boardList[i] = new MessageBoard(input, i);
      return i;
    }

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int createBankChain(String name)

  {
    for (int i=0; i<MAX_BANKS; i++)
    if (bankList[i] == null)

    {
      bankList[i] = new BankChain(name, i);
      return i;
    }

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int createZone(String name)

  {
    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] == null)

    {
      zoneList[i] = new Zone(i);
      zoneList[i].setName(name);
      return i;
    }

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Zone getZone(String zname)

  {
    if (number(zname)) return getZone(Integer.parseInt(zname));

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
    if (abbreviation(zname, zoneList[i].getName()))
      return zoneList[i];

    return null;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Item getItem(String iname)

  {
    if (number(iname)) return getItem(Integer.parseInt(iname));

    iname = removeColors(iname);
    int targetNum = clipNumber(iname);
    iname = clipString(iname);
    if (iname.length() < 3) return null;

    for (int i=0; i<MAX_ITEMS; i++)
    if (itemList[i] != null)
    if (abbreviation(iname, itemList[i].getName()))

    {
      targetNum--;
      if (targetNum <= 0) return itemList[i];
    }

    return null;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Mobile getMob(String mname)

  {
    if (number(mname)) return getMob(Integer.parseInt(mname));

    mname = removeColors(mname);
    int targetNum = clipNumber(mname);
    mname = clipString(mname);
    if (mname.length() < 3) return null;

    for (int i=0; i<MAX_MOBS; i++)
    if (mobList[i] != null)
    if ((abbreviation(mname, mobList[i].getName())) || (mobList[i].checkTargets(mname)))

    {
      targetNum--;
      if (targetNum <= 0) return mobList[i];
    }

    return null;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Clan getClan(String cname)

  {
    for (int i=0; i<MAX_CLANS; i++)
    if (clanList[i] != null)
    if (removeColors(clanList[i].getName()).equalsIgnoreCase(cname))
      return clanList[i];

    return null;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Religion getReligion(String rname)

  {
    for (int i=0; i<MAX_CLANS; i++)
    if (religionList[i] != null)
    if (removeColors(religionList[i].getName()).equalsIgnoreCase(rname))
      return religionList[i];

    return null;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void insertAccount(Account A)

  {
    int size = accountList.size();
    String name = A.getUsername();

    if (size == 0) {
      accountList.add(A);
      return; }

    for (int i=0; i<size; i++)
    if (!lessThan(getAccount(i).getUsername(), name)) {
      accountList.add(i, A);
      return; }

    accountList.add(A);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void removeAccount(String name)

  {
    int index = findAccountIndex(name);
    if (index == -1) return;
    accountList.remove(index);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void setAccount(Account A)

  {
    int index = findAccountIndex(A.getUsername());
    if (index == -1) return;
    accountList.set(index, A);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int findAccountIndex(String name)

  {
    name = name.toLowerCase();

    int start = 0;
    int end = accountList.size()-1;

    while (start < end)

    {
      int med = start + (end - start) / 2;
      String s = getAccount(med).getUsername();

      if (s.equalsIgnoreCase(name)) return med;
      else if (lessThan(s, name)) start = med + 1;
      else end = med - 1;
    }

    if ((start >= 0) && (start < accountList.size()))
    if (getAccount(start).getUsername().equalsIgnoreCase(name))
      return start;

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Account findAccount(String name)

  {
    int index = findAccountIndex(name);
    if (index == -1) return null;
    return getAccount(index);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void insertCharacter(Entity E)

  {
    int size = charList.size();
    String name = E.getName();

    if (size == 0) {
      charList.add(E);
      return; }

    for (int i=0; i<size; i++)
    if (!lessThan(getCharacter(i).getName(), name)) {
      charList.add(i, E);
      return; }

    charList.add(E);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void removeCharacter(String name)

  {
    int index = findCharacterIndex(name);
    if (index == -1) return;
    charList.remove(index);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void setCharacter(Entity newChar)

  {
    int index = findCharacterIndex(newChar.getName());
    if (index == -1) return;
    charList.set(index, newChar);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Entity findCharacter(String name)

  {
    int index = findCharacterIndex(name);
    if (index == -1) return null;
    return getCharacter(index);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int findCharacterIndex(String name)

  {
    name = properName(name);

    int start = 0;
    int end = charList.size()-1;

    while (start < end)

    {
      int med = start + (end - start) / 2;
      Entity E = getCharacter(med);

      if (E.getName().equalsIgnoreCase(name)) return med;
      else if (lessThan(E.getName(), name)) start = med + 1;
      else end = med - 1;
    }

    if ((start >= 0) && (start < charList.size()))
    if (getCharacter(start).getName().equalsIgnoreCase(name))
      return start;

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Entity findGlobalEntity(String target)

  {
    int targetNum = clipNumber(target);
    target = clipString(target);

    int csize = ClientList.getSize();

    for (int i=0; i<csize; i++)

    {
      Client C = ClientList.getClient(i);

      if ((shortFor(target, C.getCharInfo().getLName()))
      && (C.getClientState() != CSTATE_LOGIN)
      && (C.getClientState() != CSTATE_POSSESS))
        targetNum--;

      if (targetNum <= 0) return C.getCharInfo();
    }

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)

    {
      Zone Z = zoneList[i];
      int rsize = Z.getNumRooms();

      for (int j=0; j<rsize; j++)

      {
        Room R = Z.getRoomObj(j);
        int esize = R.getSize();

        for (int k=0; k<esize; k++)

        {
          Entity E = R.getEnt(k);

          if ((!E.isPlayer()) && (E.checkTargets(target)))
            targetNum--;

          if (targetNum <= 0) return E;
        }
      }
    }

    return null;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Room findHometown(Entity ENT)

  {
    String town = removeColors(ENT.castChar().getHometown());

    if (town.equals("Hydecka"))       town = "Damascus";
    else if (town.equals("Almeccia")) town = "Lourdes";
    else if (town.equals("Ravenna"))  town = "Odessa";
    else if (town.equals("Volaris"))  town = "Scion";

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)

    {
      if (Utility.removeColors(zoneList[i].getName()).equals(town))
        return roomList[zoneList[i].getRoom(0)];
    }

    return roomList[1];
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static void initSpellList()

  {
    int num = Spell.getNumSpells();
    spellList = new Spell [num];

    for (int i=0; i<num; i++)

    {
      String spellName = Spell.getSpellName(i);
      spellName = getProperClassName(spellName);

      try

      {
        Class C = Class.forName(spellName);
        spellList[i] = (Spell) C.newInstance();
        spellList[i].init(null,0);
      }

      catch (Exception e) { System.out.println(e); }
      catch (Throwable t) { System.out.println(t); }
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static void initSkillList()

  {
    int num = Skill.getNumSkills();
    skillList = new Skill [num];

    for (int i=0; i<num; i++)

    {
      String skillName = Skill.getSkillName(i);
      skillName = getProperClassName(skillName);

      try

      {
        Class C = Class.forName(skillName);
        skillList[i] = (Skill) C.newInstance();
        skillList[i].init(null,0);
      }

      catch (Exception e) { System.out.println(e); }
      catch (Throwable t) { System.out.println(t); }
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void globalZoneDelete(int znum)

  {
    Zone Z = zoneList[znum];

    Z.clearMobs();
    Z.clearItems();
    Z.movePlayersOut();
    zoneList[znum] = null;

    Z.globalDelete();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void globalRoomDelete(int rnum)

  {
    roomList[rnum].clearMobs();
    roomList[rnum].clearItems();
    roomList[rnum].clearMobPopulation();
    roomList[rnum].clearItemPopulation();
    roomList[rnum].movePlayersOut();
    roomList[rnum] = null;

    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)
      roomList[i].globalRoomDelete(rnum);

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].globalRoomDelete(rnum);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void globalMobDelete(int mnum)

  {
    mobList[mnum] = null;

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].globalMobDelete(mnum);

    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)
      roomList[i].globalMobDelete(mnum);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void globalItemDelete(int inum)

  {
    itemList[inum] = null;

    for (int i=0; i<MAX_ITEMS; i++)
    if (itemList[i] != null)
      itemList[i].globalItemDelete(inum);

    for (int i=0; i<MAX_MOBS; i++)
    if (mobList[i] != null)
      mobList[i].globalItemDelete(inum);

    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)
      roomList[i].globalItemDelete(inum);

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].globalItemDelete(inum);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void globalActionDelete(int anum)

  {
    actionList[anum] = null;

    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)
      roomList[i].globalActionDelete(anum);

    for (int i=0; i<MAX_MOBS; i++)
    if (mobList[i] != null)
      mobList[i].globalActionDelete(anum);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void globalRenumber(Entity SRC, String rem)

  {
    if (!rem.equalsIgnoreCase("yes")) {
      SRC.echo("To reorder all rooms, type: 'renumber YES'");
      return; }

    SRC.echo("Renumbering...");

    int[] indexList = renumberRooms();
    renumberItems();
    renumberMobs();

    SRC.echo("Updating playerfile loadrooms...");

    for (int i=0; i<charList.size(); i++)
    if (!getCharacter(i).isOnline())

    {
      getCharacter(i).setRoom(indexList[getCharacter(i).getRoom().getID()]);
      getCharacter(i).save();
    }

    save();
    SRC.echo("Done.");
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static int[] renumberRooms()

  {
    int[] indexList = new int[MAX_ROOMS];
    int index = 0;

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)

    {
      Zone Z = zoneList[i];
      int numRooms = Z.getNumRooms();

      for (int j=0; j<numRooms; j++)
        indexList[Z.getRoomObj(j).getID()] = index++;
    }

    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)
      roomList[i].globalRoomRenumber(indexList);

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].globalRoomRenumber(indexList);

    Room[] newList = new Room[MAX_ROOMS];

    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)
      newList[roomList[i].getID()] = roomList[i];

    roomList = newList;

    return indexList;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void renumberItems()

  {
    int[] indexList = new int[MAX_ITEMS];
    int index = 0;

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)

    {
      Zone Z = zoneList[i];
      int numItems = Z.getNumItems();

      for (int j=0; j<numItems; j++)
        indexList[Z.getItemObj(j).getID()] = index++;
    }

    for (int i=0; i<MAX_ITEMS; i++)
    if (itemList[i] != null)
      itemList[i].globalItemRenumber(indexList);

    for (int i=0; i<MAX_MOBS; i++)
    if (mobList[i] != null)
      mobList[i].globalItemRenumber(indexList);

    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)
      roomList[i].globalItemRenumber(indexList);

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].globalItemRenumber(indexList);

    Item[] newList = new Item[MAX_ITEMS];

    for (int i=0; i<MAX_ITEMS; i++)
    if (itemList[i] != null)
      newList[itemList[i].getID()] = itemList[i];

    itemList = newList;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void renumberMobs()

  {
    int[] indexList = new int[MAX_ITEMS];
    int index = 0;

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)

    {
      Zone Z = zoneList[i];
      int numMobs = Z.getNumMobs();

      for (int j=0; j<numMobs; j++)
        indexList[Z.getMobObj(j).getID()] = index++;
    }

    for (int i=0; i<MAX_MOBS; i++)
    if (mobList[i] != null)
      mobList[i].globalMobRenumber(indexList);

    for (int i=0; i<MAX_ROOMS; i++)
    if (roomList[i] != null)
      roomList[i].globalMobRenumber(indexList);

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].globalMobRenumber(indexList);

    Mobile[] newList = new Mobile[MAX_MOBS];

    for (int i=0; i<MAX_MOBS; i++)
    if (mobList[i] != null)
      newList[mobList[i].getID()] = mobList[i];

    mobList = newList;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void completeMobReset(Entity SRC, String rem)

  {
    if (!rem.equals("yes")) {
      SRC.echo("To destroy all moblists and populations, type: 'fragmobs YES'");
      return; }

    SRC.echo("Destroying all Mobile Lists and Populations.");

    for (int i=0; i<MAX_ZONES; i++)
    if (zoneList[i] != null)

    {
      zoneList[i].clearMobs();
      zoneList[i].clearMoblist();
      zoneList[i].clearMobPopulation();
    }

    for (int i=0; i<MAX_MOBS; i++)
      mobList[i] = null;

    save();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void destroyDuplicateItems()

  {
    try

    {
      System.out.print("Detecting Duplicate Items..... ");

      ArrayList duplicateList = new ArrayList();
      ArrayList allItems = new ArrayList();
      ArrayList entList  = new ArrayList();
      ArrayList saveList = new ArrayList();

      for (int i=0; i<charList.size(); i++)

      {
        ArrayList A = getCharacter(i).getAllItems();
        allItems.addAll(A);

        for (int j=0; j<A.size(); j++)
          entList.add(getCharacter(i));
      }

      int size = allItems.size();

      for (int i=0; i<size; i++)
      for (int j=i; j<size; j++)
      if (i != j)
      if (!duplicateList.contains(allItems.get(j)))
      if (Item.compare((Item)allItems.get(i), (Item)allItems.get(j)))

      {
        duplicateList.add(allItems.get(j));
        String iName = ((Item)allItems.get(j)).getName();
        String pName = ((Entity)entList.get(j)).getName();
        SystemLog.addLog("Destroyed " + iName + " on " + pName + ".");
      }

      for (int i=0; i<duplicateList.size(); i++)
        ((Item)duplicateList.get(i)).decayObject();

      for (int i=0; i<duplicateList.size(); i++)

      {
        int index = allItems.indexOf(duplicateList.get(i));

        if (!saveList.contains(entList.get(index)))
          saveList.add(entList.get(index));
      }

      for (int i=0; i<saveList.size(); i++)
        ((Entity)saveList.get(i)).castChar().save();

      System.out.println("Done.");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static void modifyItem(Entity SRC, int inum)

  {
    if ((inum < 0) || (inum >= MAX_ITEMS)) return;

    Item I = World.getItem(inum);

    if (I == null) return;

    SRC.echo("Modifying: " + I.getName() + "...");

    ArrayList allItems = new ArrayList();
    ArrayList entList  = new ArrayList();
    ArrayList saveList = new ArrayList();

    for (int i=0; i<charList.size(); i++)

    {
      Entity E = (Entity) charList.get(i);
      ArrayList A = E.getAllItems();
      allItems.addAll(A);

      for (int j=0; j<A.size(); j++)
        entList.add(E);
    }

    for (int i=0; i<allItems.size(); i++)
    if (sameItem(I, (Item)allItems.get(i)))

    {
      if (!saveList.contains(entList.get(i)));
        saveList.add(entList.get(i));

      SRC.echo("Found item on " + ((Entity)entList.get(i)).getName() + ".");

      Item.copyItem(I, (Item)allItems.get(i));
    }

    for (int i=0; i<saveList.size(); i++)
      ((Entity)entList.get(i)).castChar().save();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
}