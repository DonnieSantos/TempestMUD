import java.util.*;
import java.io.*;

public class Room extends Utility implements Externalizable

{
  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1;

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static final int TERRAIN_FLAT_NORMAL        = 0;
  public static final int TERRAIN_FLAT_ICY           = 1;
  public static final int TERRAIN_FLAT_MUDDY         = 2;
  public static final int TERRAIN_FLAT_ROCKY         = 3;
  public static final int TERRAIN_ROAD_DIRT          = 4;
  public static final int TERRAIN_ROAD_STONE         = 5;
  public static final int TERRAIN_MOUNTAIN_LEVEL     = 6;
  public static final int TERRAIN_MOUNTAIN_HILLS     = 7;
  public static final int TERRAIN_MOUNTAIN_ASCENDING = 8;
  public static final int TERRAIN_MOUNTAIN_STEEP     = 9;
  public static final int TERRAIN_FOREST_BRUSH       = 10;
  public static final int TERRAIN_FOREST_SPARSE      = 11;
  public static final int TERRAIN_FOREST_DENSE       = 12;
  public static final int TERRAIN_SWAMP_LIGHT        = 13;
  public static final int TERRAIN_SWAMP_BOGGY        = 14;
  public static final int TERRAIN_WATER_SHALLOW      = 15;
  public static final int TERRAIN_WATER_DEEP         = 16;
  public static final int TERRAIN_WATER_SEA          = 17;
  public static final int TERRAIN_SPECIAL_AIRBORNE   = 18;
  public static final int TERRAIN_SPECIAL_SHIMMY     = 19;
  public static final int TERRAIN_SPECIAL_REPEL      = 20;
  public static final int TERRAIN_SPECIAL_CRAWL      = 21;

  public static final int NUM_FLAGS        = 29;
  public static final int FLAG_LAWFULL     = 0;
  public static final int FLAG_PKOK        = 1;
  public static final int FLAG_NO_MAGIC    = 2;
  public static final int FLAG_REGEN       = 3;
  public static final int FLAG_INDOORS     = 4;
  public static final int FLAG_NO_SUMMON   = 5;
  public static final int FLAG_NO_QUIT     = 6;
  public static final int FLAG_SILENT      = 7;
  public static final int FLAG_PRIVATE     = 8;
  public static final int FLAG_NO_DROP     = 9;
  public static final int FLAG_NO_MOB      = 10;
  public static final int FLAG_DONATION    = 11;
  public static final int FLAG_ANIMATE     = 12;
  public static final int FLAG_DARK        = 13;
  public static final int FLAG_SHROUD      = 14;
  public static final int FLAG_HOLYGROUND  = 15;
  public static final int FLAG_TUNNEL      = 16;
  public static final int FLAG_UNDERWATER  = 17;
  public static final int FLAG_DAMAGE      = 18;
  public static final int FLAG_DEATHTRAP   = 19;
  public static final int FLAG_NO_TRACK    = 20;
  public static final int FLAG_NO_SCOUT    = 21;
  public static final int FLAG_NO_DISPEL   = 22;
  public static final int FLAG_NO_TELEPORT = 23;
  public static final int FLAG_NO_FLEE     = 24;
  public static final int FLAG_NO_SPELL    = 25;
  public static final int FLAG_REGEN_HP    = 26;
  public static final int FLAG_REGEN_MN    = 27;
  public static final int FLAG_NO_GET      = 28;

  public static final int NUM_RESPONSES  = 2;
  public static final int RESPONSE_ENTER = 0;
  public static final int RESPONSE_EXIT  = 1;

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  private int id;
  private int zoneID;
  private int bankID;
  private int num_mobs;
  private int num_people;
  private int lightCount;
  private int N, E, S, W, U, D;
  private int terrain;
  private String title;
  private ArrayList lookList;
  private ArrayList lookTargs;
  private ArrayList entList;
  private ArrayList actionList;
  private ArrayList mobPopulation;
  private ArrayList itemPopulation;
  private LinkedList fightList;
  private Flag[] flagList;
  private double timer;
  private int fightTicker;
  private Stacker stacker;
  private Writable desc;
  private BankBranch bankbranch;
  private Action[] responseList;
  private ItemList itemList;

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public Room()                            { this(0);                                  }
  public int getSize()                     { return entList.size();                    }
  public int getID()                       { return id;                                }
  public int getNumItems()                 { return itemList.getNumItems();            }
  public int getNumEnts()                  { return entList.size();                    }
  public int getN()                        { return N;                                 }
  public int getE()                        { return E;                                 }
  public int getS()                        { return S;                                 }
  public int getW()                        { return W;                                 }
  public int getU()                        { return U;                                 }
  public int getD()                        { return D;                                 }
  public int getTerrain()                  { return terrain;                           }
  public int getNumLooks()                 { return lookList.size();                   }
  public int getNumTargs()                 { return lookTargs.size();                  }
  public int getZoneID()                   { return zoneID;                            }
  public int getBankID()                   { return bankID;                            }
  public int getNumActions()               { return actionList.size();                 }
  public int getMobPopSize()               { return mobPopulation.size();              }
  public int getItemPopSize()              { return itemPopulation.size();             }
  public PopMember getMobPop(int i)        { return (PopMember) mobPopulation.get(i);  }
  public PopMember getItemPop(int i)       { return (PopMember) itemPopulation.get(i); }
  public ArrayList getEntList()            { return entList;                           }
  public String getTitle()                 { return title;                             }
  public String getDesc()                  { return desc.getString();                  }
  public String getLookTarg(int i)         { return (String)lookTargs.get(i);          }
  public boolean occupied()                { return bool(num_people);                  }
  public boolean mobOccupied()             { return bool(num_mobs);                    }
  public boolean hasBankBranch()           { return (bankbranch != null);              }
  public boolean getFlag(int i)            { return flagList[i].isEnabled();           }
  public Zone getZone()                    { return World.getZone(zoneID);             }
  public Entity getEnt(int n)              { return (Entity) entList.get(n);           }
  public Entity getFighter(int n)          { return (Entity) fightList.get(n);         }
  public Writable getLookWrit(int i)       { return (Writable)lookList.get(i);         }
  public Writable getDescWrite()           { return desc;                              }
  public ArrayList getActionList()         { return actionList;                        }
  public BankBranch getBankBranch()        { return bankbranch;                        }
  public Action getAction(int i)           { return (Action) actionList.get(i);        }
  public Flag[] getFlags()                 { return flagList;                          }
  public void setID(int i)                 { id = i;                                   }
  public void setDesc(String s)            { desc.write(s);                            }
  public void setTitle(String s)           { title = s;                                }
  public void setDescWrite(Writable w)     { desc = w;                                 }
  public void setZoneID(int i)             { zoneID = i;                               }
  public void setBankID(int i)             { bankID = i;                               }
  public void setBankBranch(BankBranch BB) { bankbranch = BB;                          }
  public void setTerrain(int i)            { terrain = i;                              }
  public void setResponses(Action[] A)     { responseList = A;                         }
  public void setResponse(int i, Action A) { responseList[i] = A;                      }
  public Action[] getResponses()           { return responseList;                      }
  public Action getResponse(int i)         { return responseList[i];                   }
  public void addAction(Action A)          { actionList.add(A);                        }
  public void addMobPop(PopMember PM)      { mobPopulation.add(PM);                    }
  public void addItemPop(PopMember PM)     { itemPopulation.add(PM);                   }
  public ItemList getItemList()            { return itemList;                          }
  public String displayItemList()          { return itemList.getDisplay();             }
  public boolean toggleFlag(String str)    { return Flag.toggleFlag(flagList, str);    }
  public void action(int actionNum)        { responseList[actionNum].tryAction();      }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public Room(int ID)

  {
    id = ID;
    zoneID = 0;
    bankID = -1;
    lightCount = 0;
    lookList = new ArrayList();
    lookTargs = new ArrayList();
    entList = new ArrayList();
    mobPopulation = new ArrayList();
    itemPopulation = new ArrayList();
    actionList = new ArrayList();
    fightList = new LinkedList();
    stacker = new Stacker();
    desc = new Writable(Writable.FORMAT_RDESC);
    bankbranch = null;
    terrain = TERRAIN_FLAT_NORMAL;
    flagList = new Flag[NUM_FLAGS];
    itemList = new ItemList(100, ItemList.TYPE_ROOM);

    flagList[FLAG_LAWFULL]     = new Flag("LAWFULL", false);
    flagList[FLAG_PKOK]        = new Flag("PKOK", false);
    flagList[FLAG_NO_MAGIC]    = new Flag("NO_MAGIC", false);
    flagList[FLAG_REGEN]       = new Flag("REGEN", false);
    flagList[FLAG_INDOORS]     = new Flag("INDOORS", false);
    flagList[FLAG_NO_SUMMON]   = new Flag("NO_SUMMON", false);
    flagList[FLAG_NO_QUIT]     = new Flag("NO_QUIT", false);
    flagList[FLAG_SILENT]      = new Flag("SILENT", false);
    flagList[FLAG_PRIVATE]     = new Flag("PRIVATE", false);
    flagList[FLAG_NO_DROP]     = new Flag("NO_DROP", false);
    flagList[FLAG_NO_MOB]      = new Flag("NO_MOB", false);
    flagList[FLAG_DONATION]    = new Flag("DONATION", false);
    flagList[FLAG_ANIMATE]     = new Flag("ANIMATE", false);
    flagList[FLAG_DARK]        = new Flag("DARK", false);
    flagList[FLAG_SHROUD]      = new Flag("SHROUD", false);
    flagList[FLAG_HOLYGROUND]  = new Flag("HOLYGROUND", false);
    flagList[FLAG_TUNNEL]      = new Flag("TUNNEL", false);
    flagList[FLAG_UNDERWATER]  = new Flag("UNDERWATER", false);
    flagList[FLAG_DAMAGE]      = new Flag("DAMAGE", false);
    flagList[FLAG_DEATHTRAP]   = new Flag("DEATHTRAP", false);
    flagList[FLAG_NO_TRACK]    = new Flag("NO_TRACK", false);
    flagList[FLAG_NO_SCOUT]    = new Flag("NO_SCOUT", false);
    flagList[FLAG_NO_DISPEL]   = new Flag("NO_DISPEL", false);
    flagList[FLAG_NO_TELEPORT] = new Flag("NO_TELEPORT", false);
    flagList[FLAG_NO_FLEE]     = new Flag("NO_FLEE", false);
    flagList[FLAG_NO_SPELL]    = new Flag("NO_SPELL", false);
    flagList[FLAG_REGEN_HP]    = new Flag("REGEN_HP", false);
    flagList[FLAG_REGEN_MN]    = new Flag("REGEN_MN", false);
    flagList[FLAG_NO_GET]      = new Flag("NO_GET", false);

    N = 0;
    E = 0;
    S = 0;
    W = 0;
    U = 0;
    D = 0;

    initLooks();
    initResponses();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public boolean isDark()

  {
    if ((getFlag(Room.FLAG_DARK)) && (lightCount <= 0)) return true;
    return false;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public String getLook(int n)

  {
    Writable w = (Writable)lookList.get(n);
    return w.getString();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void initResponses()

  {
    responseList = new Action[NUM_RESPONSES];

    responseList[RESPONSE_ENTER] = new Action(-1);
    responseList[RESPONSE_ENTER].setCustom("ENTER");
    responseList[RESPONSE_ENTER].setName("Enter");

    responseList[RESPONSE_EXIT] = new Action(-1);
    responseList[RESPONSE_EXIT].setCustom("EXIT");
    responseList[RESPONSE_EXIT].setName("Exit");
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  private void gain(Entity ENT)

  {
    entList.add(ENT);

    if (ENT.isPlayer()) num_people++;
    else num_mobs++;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  private void loss(Entity ENT)

  {
    int n = entList.indexOf(ENT);
    if (n == -1) return;
    if (ENT.isPlayer()) num_people--;
    else num_mobs--;
    entList.remove(n);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void enter(Entity E, int type)

  {
    lightCount += E.getIllumination();

    E.setRoom(id);
    gain(E);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void leave(Entity E, int type)

  {
    loss(E);

    lightCount -= E.getIllumination();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void itemIn(Item I, int type)

  {
    I.setRoom(this);
    I.setParent(null);
    I.setEntity(null);

    if (type == IDROP)              { }
    else if (type == IDISARM)       { }
    else if (type == ILOAD)         { }
    else if (type == IPOPR)         { }
    else if (type == ICREATECORPSE) { }
    else System.out.println("Unexpected item in type: " + type);

    itemList.add(I);

    lightCount += I.getIllumination();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void itemOut(Item I, int type)

  {
    if (type == IGETR)        { }
    else if (type == IDECAYR) { }
    else if (type == ISAC)    { }
    else if (type == IPURGE)  { }
    else System.out.println("Unexpected item out type: " + type);

    itemList.remove(I);

    lightCount -= I.getIllumination();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void clearMobPopulation()

  {
    for (int i=0; i<getMobPopSize(); i++)
      getMobPop(i).unlinkAll();

    mobPopulation = new ArrayList();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void clearItemPopulation()

  {
    for (int i=0; i<getItemPopSize(); i++)
      getItemPop(i).unlinkAll();

    itemPopulation = new ArrayList();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void removeMobPop(int n)

  {
    if (n >= mobPopulation.size()) return;
    getMobPop(n).unlinkAll();
    mobPopulation.remove(n);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void removeItemPop(int n)

  {
    if (n >= itemPopulation.size()) return;
    getItemPop(n).unlinkAll();
    itemPopulation.remove(n);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void repopMobs()

  {
    for (int i=0; i<getMobPopSize(); i++)
      getMobPop(i).pop();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void repopItems()

  {
    for (int i=0; i<getItemPopSize(); i++)
      getItemPop(i).pop();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void clearMobs()

  {
    int size = entList.size();

    for (int i=size-1; i>=0; i--)
    if (!getEnt(i).isPlayer())
      getEnt(i).die(false, false);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void clearItems()

  {
    ArrayList ilist = itemList.getList();
    int size = ilist.size();

    for (int i=size-1; i>=0; i--)
      ((Item)ilist.get(i)).destroy(ICLEAR);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void animate()

  {
    for (int i=0; i<entList.size(); i++)
      if (!getEnt(i).isPlayer())
        getEnt(i).castMob().considerAction();

    considerAction();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void considerAction()

  {
    if (!getFlag(Room.FLAG_ANIMATE)) return;
    if (!occupied()) return;

    for (int i=0; i<getNumActions(); i++)
      getAction(i).tryAction();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void mobPopSwap(int i, int j)

  {
    PopMember P1 = getMobPop(i);
    PopMember P2 = getMobPop(j);
    mobPopulation.set(i, P2);
    mobPopulation.set(j, P1);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void itemPopSwap(int i, int j)

  {
    PopMember P1 = getItemPop(i);
    PopMember P2 = getItemPop(j);
    itemPopulation.set(i, P2);
    itemPopulation.set(j, P1);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void setExit(String ex, int target)

  {
    ex = ex.toLowerCase();
    if (!direction(ex)) return;

    if ((ex.charAt(0) == 'n') || (ex.charAt(0) == 'N'))      N = target;
    else if ((ex.charAt(0) == 's') || (ex.charAt(0) == 'S')) S = target;
    else if ((ex.charAt(0) == 'e') || (ex.charAt(0) == 'E')) E = target;
    else if ((ex.charAt(0) == 'w') || (ex.charAt(0) == 'W')) W = target;
    else if ((ex.charAt(0) == 'u') || (ex.charAt(0) == 'U')) U = target;
    else if ((ex.charAt(0) == 'd') || (ex.charAt(0) == 'D')) D = target;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void setExit(int ex, int target)

  {
    if (ex == DIRECTION_N) N = target;
    if (ex == DIRECTION_S) S = target;
    if (ex == DIRECTION_E) E = target;
    if (ex == DIRECTION_W) W = target;
    if (ex == DIRECTION_U) U = target;
    if (ex == DIRECTION_D) D = target;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public int getNumExits()

  {
    int count = 6;

    if (N == 0) count--;
    if (S == 0) count--;
    if (E == 0) count--;
    if (W == 0) count--;
    if (U == 0) count--;
    if (D == 0) count--;

    return count;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void initLooks()

  {
    lookList = new ArrayList();

    String n = "You see nothing out of the ordinary ";

    Writable s1 = new Writable(n + "to the north.", Writable.FORMAT_LDESC);
    Writable s2 = new Writable(n + "to the south.", Writable.FORMAT_LDESC);
    Writable s3 = new Writable(n + "to the east.",  Writable.FORMAT_LDESC);
    Writable s4 = new Writable(n + "to the west.",  Writable.FORMAT_LDESC);
    Writable s5 = new Writable(n + "above you.",    Writable.FORMAT_LDESC);
    Writable s6 = new Writable(n + "below you.",    Writable.FORMAT_LDESC);

    lookList.add(s1);
    lookList.add(s2);
    lookList.add(s3);
    lookList.add(s4);
    lookList.add(s5);
    lookList.add(s6);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void addLook(String target, Writable w)

  {
    int n = lookTargs.indexOf(target);
    if (n != -1) return;
    if (direction(target)) return;
    lookList.add(w);
    lookTargs.add(target);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void removeLook(String target)

  {
    int n = lookTargs.indexOf(target);
    if (n == -1) return;

    lookList.remove(n+6);
    lookTargs.remove(n);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public boolean searchLooks(String target)

  {
    for (int i=0; i<lookTargs.size(); i++)

    {
      String s = (String) lookTargs.get(i);
      if (findKeyword(target, s)) return true;
    }

    return false;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public int findLook(String target)

  {

    for (int i=0; i<lookTargs.size(); i++)

    {
      String s = (String) lookTargs.get(i);
      if (findKeyword(target, s)) return i+6;
    }

    return -1;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void addSayHistory(Entity SRC, String str)

  {
    for (int i=0; i<entList.size(); i++)
    if ((getEnt(i) != SRC) && (getEnt(i).getClient() != null))
    getEnt(i).getClient().addSayHistory(str);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void echo(String str, int mode)

  {
    for (int i=0; i<entList.size(); i++)

    {
      if (mode == ECHO_ALL) getEnt(i).echo(str);
      else if (mode == ECHO_AWAKE) getEnt(i).awakeEcho(str);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void fightEcho(String str, int mode)

  {
    for (int i=0; i<entList.size(); i++)

    {
      if (mode == ECHO_ALL) getEnt(i).fightEcho(str);
      else if (mode == ECHO_AWAKE) getEnt(i).awakeFightEcho(str);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void xfightEcho(Entity ENT, String str, int mode)

  {
    for (int i=0; i<entList.size(); i++)
    if (getEnt(i) != ENT)

    {
      if (mode == ECHO_ALL) getEnt(i).fightEcho(str);
      else if (mode == ECHO_AWAKE) getEnt(i).awakeFightEcho(str);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void xecho(Entity e1, String s)

  {
    xecho(e1, s, ECHO_ALL);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void xecho(Entity e1, Entity e2, String s)

  {
    xecho(e1, e2, s, ECHO_ALL);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void xecho(Entity e1, Entity e2, Entity e3, String s)

  {
    xecho(e1, e2, e3, s, ECHO_ALL);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void xecho(Entity SRC, String str, int mode)

  {
    for (int i=0; i<entList.size(); i++)
    if (getEnt(i) != SRC)

    {
      if (mode == ECHO_ALL) getEnt(i).echo(str);
      else if (mode == ECHO_AWAKE) getEnt(i).awakeEcho(str);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void xecho(Entity SRC1, Entity SRC2, String str, int mode)

  {
    for (int i=0; i<entList.size(); i++)
    if ((getEnt(i) != SRC1) && (getEnt(i) != SRC2))

    {
      if (mode == ECHO_ALL) getEnt(i).echo(str);
      else if (mode == ECHO_AWAKE) getEnt(i).awakeEcho(str);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void xecho(Entity SRC1, Entity SRC2, Entity SRC3, String str, int mode)

  {
    for (int i=0; i<entList.size(); i++)
    if ((getEnt(i) != SRC1) && (getEnt(i) != SRC2) && (getEnt(i) != SRC3))

    {
      if (mode == ECHO_ALL) getEnt(i).echo(str);
      else if (mode == ECHO_AWAKE) getEnt(i).awakeEcho(str);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void gblindEcho(Entity ENT, Entity s1, Entity s2, Entity s3, String str)

  {
    for (int i=0; i<entList.size(); i++)
    if (!ENT.getGroup().inGroup(getEnt(i)))
    if ((getEnt(i) != s1) && (getEnt(i) != s2) && (getEnt(i) != s3))
      getEnt(i).echo(str);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void observerEcho(Entity E1, Entity E2, String str)

  {
    Group G1 = E1.getGroup();
    Group G2 = E2.getGroup();

    for (int i=0; i<entList.size(); i++)
    if ((!G1.inGroup(getEnt(i))) && (!G2.inGroup(getEnt(i))))
      getEnt(i).awakeFightEcho(str);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void pblindEcho(Entity ENT, String str)

  {
    for (int i=0; i<entList.size(); i++)
    if ((getEnt(i) != ENT) && (getEnt(i).getFollowing() != ENT))
      getEnt(i).echo(str);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void examineSay(Entity SRC, String s)

  {
    if (!mobOccupied()) return;

    for (int i=0; i<entList.size(); i++)
    if ((!getEnt(i).isPlayer()) && (getEnt(i).getClient() == null))
    getEnt(i).castMob().examineSay(SRC, s);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public Entity findEntity(Entity SRC, String target)

  {
    int target_num = clipNumber(target);
    target = clipString(target);

    if ((SRC.getRoom() == this) && (SRC.checkTargets(target))) {
      if (target_num == 1) return SRC;
      target_num--; }

    for (int i=0; i<entList.size(); i++)
    if (getEnt(i).isPlayer())

    {
      if ((getEnt(i) != SRC)
      && (getEnt(i).checkTargets(target))
      && (getEnt(i).getClient().getClientState() != CSTATE_LOGIN))
        target_num--;

      if (target_num <= 0) return getEnt(i);
    }

    for (int i=0; i<entList.size(); i++)
    if (!getEnt(i).isPlayer())

    {
      if ((getEnt(i).checkTargets(target))
      && (getEnt(i) != SRC))
        target_num--;

      if (target_num <= 0) return getEnt(i);
    }

    return null;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public Entity findCharacter(Entity SRC, String target)

  {
    for (int i=0; i<entList.size(); i++)
      if ((getEnt(i).getName().equals(target))
      && (getEnt(i).isPlayer())
      && (getEnt(i) != SRC))
        return getEnt(i);

    return null;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public int findNumEnts(String target)

  {
    int num_ents = 0;

    for (int i=0; i<entList.size(); i++)

    {
      if (getEnt(i).isPlayer()) {
        if ((getEnt(i).checkTargets(target))
        && (getEnt(i).getClient().getClientState() != CSTATE_LOGIN))
          num_ents++; }

      else if (getEnt(i).checkTargets(target))
        num_ents++;
    }

    return num_ents;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public int getGroupsize(Group the_group)

  {
    int count = 0;

    for (int i=0; i<entList.size(); i++)
      if (the_group.inGroup(getEnt(i)))
        count++;

    return count;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public String getScoutNames(int dist, String dir)

  {
    stacker.clear();

    String tempstr = "";

    if (dist == 1)

    {
      if (dir == "N") tempstr += "\r\n  To the north:";
      else if (dir == "S") tempstr += "\r\n  To the south:";
      else if (dir == "E") tempstr += "\r\n  To the east:";
      else if (dir == "W") tempstr += "\r\n  To the west:";
      else if (dir == "U") tempstr += "\r\n  Above you:";
      else if (dir == "D") tempstr += "\r\n  Below you:";
    }

    for (int i=0; i<entList.size(); i++)

    {
      if (getEnt(i).isPlayer()) {
      if (getEnt(i).getClient().getClientState() != CSTATE_LOGIN)
        tempstr += "\r\n    " + dist + " - " + getEnt(i).getName(); }

      else stacker.add(getEnt(i).getName());
    }

    tempstr += stacker.shortStack(dist);

    return tempstr;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public String getLookTitles(Entity SRC)

  {
    stacker.clear();

    String tspace, pspace, tempstr = "", mob_fighters = "";

    for (int i=0; i<entList.size(); i++)

    {
      Entity ENT = getEnt(i);

      if (ENT.isPlayer())

      {
        String pstate = "";
        int p = ENT.getPlayerState();
        int L = ENT.getTitle().length();

        if ((L == 0) || (!punctuation(ENT.getTitle().charAt(L-1))))

        {
          if (p == PSTATE_STANDING)      pstate = "#Nis standing here.";
          else if (p == PSTATE_SITTING)  pstate = "#Nis sitting here.";
          else if (p == PSTATE_RESTING)  pstate = "#Nis resting here.";
          else if (p == PSTATE_SLEEPING) pstate = "#Nis sleeping here.";
        }

        else pstate = "#N";

        if ((ENT != SRC) && (ENT.getClient().getClientState() != CSTATE_LOGIN))

        {
          tspace = " ";
          pspace = " ";
          if (visibleSize(ENT.getTitle()) < 1) tspace = "";
          if (ENT.castChar().getPrename().equals("")) pspace = "";

          tempstr += "\r\n" + ENT.castChar().getPrename() + pspace + "#C" + ENT.getName();

          if (ENT.getPlayerState() == PSTATE_FIGHTING)

          {
            Entity target = ENT.getTarget();
            String tname = "";
            if (target != null) tname = target.getName();

            if (target == SRC) tname = "YOU";

            if (target != null) tempstr += "#N is here fighting " + tname + "#N!";
            else tempstr += "#N " + ENT.getTitle() + tspace + "#Nis standing here.";
          }

          else tempstr += "#N " + ENT.getTitle() + tspace + pstate;

          if (ENT.getClient().getClientState() == CSTATE_LINKDEAD)
          tempstr += " #N(#Rlinkdead#N)";

          if (ENT.getClient().getClientState() == CSTATE_WRITING)
          tempstr += " #N(#Gwriting#N)";

          if (ENT.getClient().getClientState() == CSTATE_BUILDING)
          tempstr += " #N(#nbuilding#N)";
        }
      }

      else if (ENT != SRC)

      {
        if (ENT.getPlayerState() == PSTATE_FIGHTING)

        {
          Entity target = ENT.getTarget();
          String tname = "";
          if (target != null) tname = target.getName();

          if (target == SRC) tname = "YOU";

          if (target != null) mob_fighters += "\r\n#Y" + ENT.getPName() + "#Y ";
          if (target != null) mob_fighters += "is here #Nfighting " + tname + "#N!";
          if (target == null) stacker.add(ENT.getTitle());
        }

        else stacker.add(ENT.getTitle());
      }
    }

    tempstr += mob_fighters;
    tempstr += stacker.stack();

    return tempstr;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public String checkBlockers(Entity ENT, int dir)

  {
    ArrayList ilist = itemList.getList();

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item)ilist.get(i);

      if (I.getBlockerDir() == dir)
      if (!Blocker.tryPass(ENT, I))
        return Blocker.getErrorMsg(I);
    }

    for (int i=0; i<entList.size(); i++)
    if (getEnt(i).getBlockerDir() == dir)
    if (!Blocker.tryPass(ENT, getEnt(i)))
      return Blocker.getErrorMsg(getEnt(i));

    return "OK";
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void allAssist()

  {
    int size = entList.size();

    for (int i=0; i<size; i++)
    if (getEnt(i).getPlayerState() == PSTATE_FIGHTING)
      getEnt(i).getGroup().groupAssist(getEnt(i));
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void checkFight()

  {
    int count = 0;
    int size = entList.size();

    for (int i=0; i<size; i++)
      if (getEnt(i).getPlayerState() == PSTATE_FIGHTING)
        count++;

    if (count < 2)

    {
      World.removeFightList(this);
      fightTicker = 0;
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void updateTargets()

  {
    int size = entList.size();

    for (int i=0; i<size; i++)
    for (int j=0; j<size; j++)
    if ((getEnt(i).targetingGroup(getEnt(j).getGroup()))
    && (getEnt(j).getPlayerState() == PSTATE_FIGHTING))
      getEnt(j).setTarget(getEnt(i));

    for (int i=0; i<size; i++)
    for (int j=0; j<size; j++)
      if (getEnt(i).getPlayerState() != PSTATE_FIGHTING)
        getEnt(j).removeTarget(getEnt(i));

    for (int i=0; i<size; i++)
      if (getEnt(i).getTarget() == null)
        getEnt(i).setPlayerState(PSTATE_STANDING);

    checkFight();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void addMeters()

  {
    int size = entList.size();

    for (int i=0; i<size; i++)

    {
      Entity E = getEnt(i);

      if (E.getPlayerState() == PSTATE_FIGHTING)
      if ((E.getTarget().getCurrentHP() > 0) && (E.getCurrentHP() > 0))
      if (E.getClient() != null) {
        String s = Combat.hitpointMeter(E);
        if (s.length() > 0) E.getClient().addFightOutput(s); }
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void addFighter(Entity E)

  {
    if (fightList.contains(E)) return;
    fightList.addFirst(E);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void fight()

  {
    Entity T, E;

    for (int i=fightList.size()-1; i>=0; i--)

    {
      if (!entList.contains(getFighter(i)))
        fightList.remove(i);

      else if (getFighter(i).getPlayerState() != PSTATE_FIGHTING)
        fightList.remove(i);

      else getFighter(i).setHasAttacked(false);
    }

    for (int i=fightList.size()-1; i>=0; i--)
    if (!getFighter(i).hasAttacked())

    {
      E = getFighter(i);
      T = E.getTarget();

      if (T != null)

      {
        if (E.getClient() == null)
          T = T.getGroup().getRandomTarget(E, T);

        E.setHasAttacked(true);
        Combat.attack(E, T);
      }
    }

    addMeters();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void destroyDead()

  {
    ArrayList dieList = new ArrayList(entList);
    int size = dieList.size();

    for (int i=0; i<size; i++)
    if (((Entity)dieList.get(i)).getCurrentHP() <= 0)

    {
      Entity e = ((Entity)dieList.get(i));

      for (int j=0; j<size; j++)
        ((Entity)dieList.get(j)).removeTarget(e);

      e.die(true, true);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void updateCombat()

  {
    fightTicker++;

    if (fightTicker >= 30)

    {
      allAssist();
      updateTargets();
      fight();
      destroyDead();
      updateTargets();

      fightTicker = 0;
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public int[] roomDistance(Room loc)

  {
    boolean found = true;
    int index = 0, size = 0, i = 0;
    int dist_and_dir[] = new int [2];
    int new_rooms[] = new int [World.getSize()];
    IntArray IRA = new IntArray();
    IntArray DIR = new IntArray();

    dist_and_dir[0] = 0;
    dist_and_dir[1] = 0;

    if (loc == this) return dist_and_dir;

    IRA.put(id);
    DIR.put(0);

    while (true)

    {
      i++;
      dist_and_dir[0] = i;

      if (!found) return null;
      found = false;

      if (i < 3) index = i - 1;
      else index += new_rooms[i-2];

      size = IRA.size();
      new_rooms[i] = 0;

      for (int j=index; j<size; j++)

      {
        Room current_room = World.getRoom(IRA.get(j));

        if (current_room.getN() != 0)
        if (IRA.search(current_room.getN()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getN());
          if (i == 1) DIR.put(1);
          else DIR.put(DIR.get(IRA.search(current_room.getID())));
          if (World.getRoom(current_room.getN()) == loc) {
            dist_and_dir[1] = DIR.get(DIR.size()-1);
            return dist_and_dir; }
        }

        if (current_room.getS() != 0)
        if (IRA.search(current_room.getS()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getS());
          if (i == 1) DIR.put(2);
          else DIR.put(DIR.get(IRA.search(current_room.getID())));
          if (World.getRoom(current_room.getS()) == loc) {
            dist_and_dir[1] = DIR.get(DIR.size()-1);
            return dist_and_dir; }
        }

        if (current_room.getE() != 0)
        if (IRA.search(current_room.getE()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getE());
          if (i == 1) DIR.put(3);
          else DIR.put(DIR.get(IRA.search(current_room.getID())));
          if (World.getRoom(current_room.getE()) == loc) {
            dist_and_dir[1] = DIR.get(DIR.size()-1);
            return dist_and_dir; }
        }

        if (current_room.getW() != 0)
        if (IRA.search(current_room.getW()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getW());
          if (i == 1) DIR.put(4);
          else DIR.put(DIR.get(IRA.search(current_room.getID())));
          if (World.getRoom(current_room.getW()) == loc) {
            dist_and_dir[1] = DIR.get(DIR.size()-1);
            return dist_and_dir; }
        }

        if (current_room.getU() != 0)
        if (IRA.search(current_room.getU()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getU());
          if (i == 1) DIR.put(5);
          else DIR.put(DIR.get(IRA.search(current_room.getID())));
          if (World.getRoom(current_room.getU()) == loc) {
            dist_and_dir[1] = DIR.get(DIR.size()-1);
            return dist_and_dir; }
        }

        if (current_room.getD() != 0)
        if (IRA.search(current_room.getD()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getD());
          if (i == 1) DIR.put(6);
          else DIR.put(DIR.get(IRA.search(current_room.getID())));
          if (World.getRoom(current_room.getD()) == loc) {
            dist_and_dir[1] = DIR.get(DIR.size()-1);
            return dist_and_dir; }
        }
      }
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void movePlayersOut()

  {
    int size = ClientList.getSize();
    Entity e;

    for (int i=0; i<size; i++)

    {
      e = ClientList.getClient(i).getCharInfo();

      if (e.getRoom() == this)

      {
        e.stopFighting();
        e.stopTracking();
        leave(e, LEAVE_TELEPORT);
        World.findHometown(e).enter(e, ENTER_TELEPORT);
        e.look("The room has been removed.  Moving to hometown.");
      }

      e = ClientList.getClient(i).getCharInfoBackup();

      if (e.getRoom() == this)

      {
        e.stopFighting();
        e.stopTracking();
        leave(e, LEAVE_TELEPORT);
        World.findHometown(e).enter(e, ENTER_TELEPORT);
        e.look("The room has been removed.  Moving to hometown.");
      }
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void stackingOn()

  {
    int size = entList.size();

    for (int i=0; i<size; i++)
    if (((Entity)entList.get(i)).getClient() != null)
    ((Entity)entList.get(i)).getClient().stackingOn();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void stackingOff()

  {
    int size = entList.size();

    for (int i=0; i<size; i++)
    if (((Entity)entList.get(i)).getClient() != null)
    ((Entity)entList.get(i)).getClient().stackingOff();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void setNormalRedirect(boolean b)

  {
    for (int i=0; i<entList.size(); i++)
    if (getEnt(i).getClient() != null)
    getEnt(i).getClient().setNormalRedirect(b);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void setCombatRedirect(boolean b)

  {
    for (int i=0; i<entList.size(); i++)
    if (getEnt(i).getClient() != null)
    getEnt(i).getClient().setCombatRedirect(b);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void setSkillRedirect(boolean b)

  {
    for (int i=0; i<entList.size(); i++)
    if (getEnt(i).getClient() != null)
    getEnt(i).getClient().setSkillRedirect(b);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void globalRoomDelete(int rnum)

  {
    if (N == rnum) N = 0;
    if (S == rnum) S = 0;
    if (E == rnum) E = 0;
    if (W == rnum) W = 0;
    if (U == rnum) U = 0;
    if (D == rnum) D = 0;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void globalMobDelete(int mnum)

  {
    for (int i=mobPopulation.size()-1; i>=0; i--)

    {
      PopMember PM = getMobPop(i);
      int size = PM.getNumTypes();

      for (int j=size-1; j>=0; j--)
      if (PM.getIDNum(j) == mnum)
        PM.removeType(j);

      if (PM.getNumTypes() == 0)
        removeMobPop(i);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void globalItemDelete(int inum)

  {
    for (int i=itemPopulation.size()-1; i>=0; i--)

    {
      PopMember PM = getItemPop(i);
      int size = PM.getNumTypes();

      for (int j=size-1; j>=0; j--)
      if (PM.getIDNum(j) == inum)
        PM.removeType(j);

      if (PM.getNumTypes() == 0)
        removeItemPop(i);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void globalActionDelete(int anum)

  {
    for (int i=actionList.size()-1; i>=0; i--)
    if (((Action)actionList.get(i)).getID() == anum)
      actionList.remove(i);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void globalRoomRenumber(int[] indexList)

  {
    for (int i=0; i<entList.size(); i++)

    {
      int index = getEnt(i).getBackupRoom().getID();
      getEnt(i).setRoom(indexList[id]);
      getEnt(i).setBackupRoom(indexList[index]);
    }

    id  =  indexList[id];
    N   =  indexList[N];
    S   =  indexList[S];
    E   =  indexList[E];
    W   =  indexList[W];
    U   =  indexList[U];
    D   =  indexList[D];
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void globalMobRenumber(int[] indexList)

  {
    for (int i=0; i<mobPopulation.size(); i++)
    for (int j=0; j<getMobPop(i).getNumTypes(); j++)
      getMobPop(i).setIDNum(j, indexList[getMobPop(i).getIDNum(j)]);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void globalItemRenumber(int[] indexList)

  {
    for (int i=0; i<itemPopulation.size(); i++)
    for (int j=0; j<getItemPop(i).getNumTypes(); j++)
      getItemPop(i).setIDNum(j, indexList[getItemPop(i).getIDNum(j)]);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("ROOM END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("ID"))              id             = (((Integer)in.readObject()).intValue());
        else if (tag.equals("ZONEID"))     zoneID         = ((Integer)in.readObject()).intValue();
        else if (tag.equals("TITLE"))      title          = (String)in.readObject();
        else if (tag.equals("DESCWRITE"))  desc           = (Writable)in.readObject();
        else if (tag.equals("N"))          N              = ((Integer)in.readObject()).intValue();
        else if (tag.equals("E"))          E              = ((Integer)in.readObject()).intValue();
        else if (tag.equals("S"))          S              = ((Integer)in.readObject()).intValue();
        else if (tag.equals("W"))          W              = ((Integer)in.readObject()).intValue();
        else if (tag.equals("U"))          U              = ((Integer)in.readObject()).intValue();
        else if (tag.equals("D"))          D              = ((Integer)in.readObject()).intValue();
        else if (tag.equals("TERRAIN"))    terrain        = ((Integer)in.readObject()).intValue();
        else if (tag.equals("LOOKDESCS"))  lookList       = (ArrayList)in.readObject();
        else if (tag.equals("LOOKTARGS"))  lookTargs      = (ArrayList)in.readObject();
        else if (tag.equals("BRANCH"))     bankbranch     = (BankBranch)in.readObject();
        else if (tag.equals("ACTIONS"))    actionList     = (ArrayList)in.readObject();
        else if (tag.equals("MPOP"))       mobPopulation  = (ArrayList)in.readObject();
        else if (tag.equals("IPOP"))       itemPopulation = (ArrayList)in.readObject();
        else if (tag.equals("RESPONSES"))  Action.updateResponses((Action[])in.readObject(), responseList);
        else if (tag.equals("FLAGS"))      Flag.setFlags((Flag[])in.readObject(), flagList);
        else if (!tag.equals("ROOM END"))  in.readObject();
      }

      itemList.setRoom(this);

      if (bankbranch != null)

      {
        int branchID = bankbranch.getChainID();
        BankChain BC = World.getBank(branchID);
        if (BC == null) bankbranch = null;
        else BC.addBranch(bankbranch);
      }

      for (int i=0; i<mobPopulation.size(); i++)

      {
        getMobPop(i).unlinkMember();
        getMobPop(i).setRoom(this);
      }

      for (int i=0; i<itemPopulation.size(); i++)

      {
        getItemPop(i).unlinkMember();
        getItemPop(i).setRoom(this);
      }
    }

    catch(Exception e) { e.printStackTrace(); }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ID");           out.writeObject(new Integer(id));
      out.writeObject("ZONEID");       out.writeObject(new Integer(zoneID));
      out.writeObject("N");            out.writeObject(new Integer(N));
      out.writeObject("E");            out.writeObject(new Integer(E));
      out.writeObject("S");            out.writeObject(new Integer(S));
      out.writeObject("W");            out.writeObject(new Integer(W));
      out.writeObject("U");            out.writeObject(new Integer(U));
      out.writeObject("D");            out.writeObject(new Integer(D));
      out.writeObject("TERRAIN");      out.writeObject(new Integer(terrain));
      out.writeObject("FLAGS");        out.writeObject(flagList);
      out.writeObject("TITLE");        out.writeObject(title);
      out.writeObject("DESCWRITE");    out.writeObject(desc);
      out.writeObject("LOOKDESCS");    out.writeObject(lookList);
      out.writeObject("LOOKTARGS");    out.writeObject(lookTargs);
      out.writeObject("BRANCH");       out.writeObject(bankbranch);
      out.writeObject("RESPONSES");    out.writeObject(responseList);
      out.writeObject("ACTIONS");      out.writeObject(actionList);
      out.writeObject("MPOP");         out.writeObject(mobPopulation);
      out.writeObject("IPOP");         out.writeObject(itemPopulation);
      out.writeObject("ROOM END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////
}
