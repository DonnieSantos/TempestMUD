import java.io.*;
import java.lang.*;
import java.util.*;

public class Item extends Utility implements Externalizable, Cloneable

{
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 5;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static final int NUM_RESPONSES        = 12;
  public static final int RESPONSE_EQUIP       = 0;
  public static final int RESPONSE_UNEQUIP     = 1;
  public static final int RESPONSE_DROP        = 2;
  public static final int RESPONSE_GET         = 3;
  public static final int RESPONSE_PUT         = 4;
  public static final int RESPONSE_GIVE        = 5;
  public static final int RESPONSE_DRINK       = 6;
  public static final int RESPONSE_EAT         = 7;
  public static final int RESPONSE_EMPTY       = 8;
  public static final int RESPONSE_FILL        = 9;
  public static final int RESPONSE_CLOSE       = 10;
  public static final int RESPONSE_OPEN        = 11;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static final int NUM_FLAGS            = 16;
  public static final int FLAG_NO_GET          = 0;
  public static final int FLAG_NO_SACRIFICE    = 1;
  public static final int FLAG_NO_DROP         = 2;
  public static final int FLAG_GOOD            = 3;
  public static final int FLAG_EVIL            = 4;
  public static final int FLAG_NEUTRAL         = 5;
  public static final int FLAG_BURNING         = 6;
  public static final int FLAG_FREEZING        = 7;
  public static final int FLAG_SHOCKING        = 8;
  public static final int FLAG_UNTOUCHABLE     = 9;
  public static final int FLAG_PERISH          = 10;
  public static final int FLAG_NO_DECAY        = 11;
  public static final int FLAG_INV_DECAY       = 12;
  public static final int FLAG_STICKY          = 13;
  public static final int FLAG_STACKABLE       = 14;
  public static final int FLAG_NO_DEPOSIT      = 15;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static final int ITEM_GENERIC         = 1;
  public static final int ITEM_EQUIPMENT       = 2;
  public static final int ITEM_CONTAINER       = 3;
  public static final int ITEM_BOARD           = 4;
  public static final int ITEM_CORPSE          = 5;
  public static final int ITEM_GOLD            = 6;
  public static final int ITEM_BLOCKER         = 7;
  public static final int ITEM_SCROLL          = 8;
  public static final int ITEM_FOOD            = 9;
  public static final int ITEM_DRINKCONTAINER  = 10;
  public static final int ITEM_FOUNTAIN        = 11;
  public static final int ITEM_CORPSE_PLAYER   = 12;
  public static final int ITEM_USEABLE         = 13;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private int id;
  private int zoneID;
  private int level;
  private int worth;
  private int hitRoll;
  private int damRoll;
  private int armorClass;
  private int resistance;
  private int hitPoints;
  private int manaPoints;
  private int movePoints;
  private int strength;
  private int dexterity;
  private int intelligence;
  private int constitution;
  private int wisdom;
  private int maxItems;
  private int maxUses;
  private int numUses;
  private int liquidType;
  private int liquidAmount;
  private int liquidMax;
  private int blockerType;
  private int blockerDir;
  private int boardID;
  private int decayTime;
  private String name;
  private String lname;
  private String groundDesc;
  private String timeStamp;
  private String typeStamp;
  private String editStamp;
  private String markStamp;
  private String classes;
  private String places;
  private String dtype;
  private String blockerParam;
  private Room myRoom;
  private Item parentContainer;
  private Flag[] flagList;
  private Entity myEntity;
  private boolean isDecaying;
  private Writable lookDesc;
  private Action[] responseList;
  private ArrayList loadList;
  private ArrayList wearSpellList;
  private ArrayList wearListLevels;
  private ArrayList useSpellList;
  private ArrayList useListLevels;
  private ArrayList types;
  private PopMember popMember;
  private ItemList itemList;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public Item(int ID)

  {
    this();
    id = ID;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public Item()

  {
    id     = 0;
    zoneID = 0;
    level  = 1;
    worth  = 0;

    name       = "Generic Item";
    lname      = "generic item";
    lookDesc   = new Writable("A generic item.", Writable.FORMAT_IDESC);
    groundDesc = "A generic item has been left here.";
    timeStamp  = "";
    typeStamp  = "";
    editStamp  = "";
    markStamp  = "";
    popMember  = null;

    blockerType  = -1;
    blockerDir   = -1;
    blockerParam = "";

    myEntity   = null;
    myRoom     = null;

    hitRoll      = 0;
    damRoll      = 0;
    armorClass   = 0;
    resistance   = 0;
    hitPoints    = 0;
    manaPoints   = 0;
    movePoints   = 0;
    strength     = 0;
    dexterity    = 0;
    intelligence = 0;
    constitution = 0;
    wisdom       = 0;

    classes    = "None";
    places     = "None";
    dtype      = "Stab";

    liquidType   = 0;
    liquidAmount = 0;
    liquidMax    = 0;

    itemList       = new ItemList(0, ItemList.TYPE_CONTAINER);
    types          = new ArrayList();
    loadList       = new ArrayList();
    wearSpellList  = new ArrayList();
    wearListLevels = new ArrayList();
    useSpellList   = new ArrayList();
    useListLevels  = new ArrayList();
    maxUses = 0;
    numUses = 0;
    maxItems = 0;

    parentContainer = null;
    boardID = -1;

    flagList                    = new Flag[NUM_FLAGS];
    flagList[FLAG_NO_GET]       = new Flag("NO_GET", false);
    flagList[FLAG_NO_SACRIFICE] = new Flag("NO_SACRIFICE", false);
    flagList[FLAG_NO_DROP]      = new Flag("NO_DROP", false);
    flagList[FLAG_GOOD]         = new Flag("GOOD", false);
    flagList[FLAG_EVIL]         = new Flag("EVIL", false);
    flagList[FLAG_NEUTRAL]      = new Flag("NEUTRAL", false);
    flagList[FLAG_BURNING]      = new Flag("BURNING", false);
    flagList[FLAG_FREEZING]     = new Flag("FREEZING", false);
    flagList[FLAG_SHOCKING]     = new Flag("SHOCKING", false);
    flagList[FLAG_UNTOUCHABLE]  = new Flag("UNTOUCHABLE", false);
    flagList[FLAG_PERISH]       = new Flag("PERISH", false);
    flagList[FLAG_NO_DECAY]     = new Flag("NO_DECAY", false);
    flagList[FLAG_INV_DECAY]    = new Flag("INV_DECAY", false);
    flagList[FLAG_STICKY]       = new Flag("STICKY", false);
    flagList[FLAG_STACKABLE]    = new Flag("STACKABLE", false);
    flagList[FLAG_NO_DEPOSIT]   = new Flag("NO_DEPOSIT", false);

    isDecaying = false;
    decayTime  = 0;

    initResponses();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public int getID()                         { return id;                           }
  public int getZoneID()                     { return zoneID;                       }
  public int getLevel()                      { return level;                        }
  public int getWorth()                      { return worth;                        }
  public int getHR()                         { return hitRoll;                      }
  public int getDR()                         { return damRoll;                      }
  public int getAC()                         { return armorClass;                   }
  public int getMR()                         { return resistance;                   }
  public int getHP()                         { return hitPoints;                    }
  public int getMN()                         { return manaPoints;                   }
  public int getMV()                         { return movePoints;                   }
  public int getSTR()                        { return strength;                     }
  public int getDEX()                        { return dexterity;                    }
  public int getCON()                        { return constitution;                 }
  public int getINT()                        { return intelligence;                 }
  public int getWIS()                        { return wisdom;                       }
  public int getMaxItems()                   { return maxItems;                     }
  public int getNumItems()                   { return itemList.getNumItems();       }
  public int getDecayTime()                  { return decayTime;                    }
  public int getNumUses()                    { return numUses;                      }
  public int getMaxUses()                    { return maxUses;                      }
  public int getLiquidType()                 { return liquidType;                   }
  public int getLiquidUses()                 { return liquidAmount;                 }
  public int getLiquidMax()                  { return liquidMax;                    }
  public int getBlockerType()                { return blockerType;                  }
  public int getBlockerDir()                 { return blockerDir;                   }
  public String getBlockerParam()            { return blockerParam;                 }
  public String getClasses()                 { return classes;                      }
  public String getPlaces()                  { return places;                       }
  public String getName()                    { return name;                         }
  public String getLName()                   { return lname;                        }
  public String getLDesc()                   { return lookDesc.getString();         }
  public String getGDesc()                   { return groundDesc;                   }
  public String getTimeStamp()               { return timeStamp;                    }
  public String getTypeStamp()               { return typeStamp;                    }
  public String getEditStamp()               { return editStamp;                    }
  public String getMarkStamp()               { return markStamp;                    }
  public String getDType()                   { return dtype;                        }
  public Entity getEntity()                  { return myEntity;                     }
  public Item getParent()                    { return parentContainer;              }
  public Room getRoom()                      { return myRoom;                       }
  public Flag[] getFlags()                   { return flagList;                     }
  public Writable getLDescWrit()             { return lookDesc;                     }
  public ItemList getItemList()              { return itemList;                     }
  public ArrayList getLoadList()             { return loadList;                     }
  public ArrayList getWearList()             { return wearSpellList;                }
  public ArrayList getWearLevels()           { return wearListLevels;               }
  public ArrayList getUseLevels()            { return useListLevels;                }
  public ArrayList getUseSpells()            { return useSpellList;                 }
  public boolean isDecaying()                { return isDecaying;                   }
  public boolean getFlag(int i)              { return flagList[i].isEnabled();      }
  public Action[] getResponses()             { return responseList;                 }
  public Action getResponse(int i)           { return responseList[i];              }
  public PopMember getPopMember()            { return popMember;                    }
  public void setFlags(Flag[] F)             { flagList = F;                        }
  public void setID(int i)                   { id = i;                              }
  public void setZoneID(int i)               { zoneID = i;                          }
  public void setLevel(int i)                { level = i;                           }
  public void setWorth(int i)                { worth = i;                           }
  public void setHR(int i)                   { hitRoll = i;                         }
  public void setDR(int i)                   { damRoll = i;                         }
  public void setAC(int i)                   { armorClass = i;                      }
  public void setMR(int i)                   { resistance = i;                      }
  public void setHP(int i)                   { hitPoints = i;                       }
  public void setMN(int i)                   { manaPoints = i;                      }
  public void setMV(int i)                   { movePoints = i;                      }
  public void setSTR(int i)                  { strength = i;                        }
  public void setDEX(int i)                  { dexterity = i;                       }
  public void setCON(int i)                  { constitution = i;                    }
  public void setINT(int i)                  { intelligence = i;                    }
  public void setWIS(int i)                  { wisdom = i;                          }
  public void setClasses(String s)           { classes = s;                         }
  public void setPlaces(String s)            { places = s;                          }
  public void setName(String s)              { name = s;                            }
  public void setLName(String s)             { lname = s;                           }
  public void setLDesc(String s)             { lookDesc.write(s);                   }
  public void setGDesc(String s)             { groundDesc = s;                      }
  public void setDType(String s)             { dtype = s;                           }
  public void setTimeStamp(String s)         { timeStamp = s;                       }
  public void setTypeStamp(String s)         { typeStamp = s;                       }
  public void setEditStamp(String s)         { editStamp = s;                       }
  public void setMarkStamp(String s)         { markStamp = s;                       }
  public void setEntity(Entity e)            { myEntity = e;                        }
  public void setParent(Item i)              { parentContainer = i;                 }
  public void setRoom(Room r)                { myRoom = r;                          }
  public void setItemList(ItemList a)        { itemList = a;                        }
  public void setTypeList(ArrayList a)       { types = a;                           }
  public void setLoadList(ArrayList a)       { loadList = a;                        }
  public void setUseSpellList(ArrayList a)   { useSpellList = a;                    }
  public void setWearSpellList(ArrayList a)  { wearSpellList = a;                   }
  public void setWearListLevels(ArrayList a) { wearListLevels = a;                  }
  public void setUseListLevels(ArrayList a)  { useListLevels = a;                   }
  public void setLDescWrit(Writable w)       { lookDesc = w;                        }
  public void setBoard(int bID)              { boardID = bID;                       }
  public void setDecayTime(int i)            { decayTime = i;                       }
  public void setNumUses(int i)              { numUses = i;                         }
  public void setMaxUses(int i)              { maxUses = i;                         }
  public void setBlockerType(int i)          { blockerType = i;                     }
  public void setBlockerDir(int i)           { blockerDir = i;                      }
  public void setBlockerParam(String s)      { blockerParam = s;                    }
  public void setLiquidType(int i)           { liquidType = i;                      }
  public void setLiquidUses(int i)           { liquidAmount = i;                    }
  public void setLiquidMax(int i)            { liquidMax = i;                       }
  public void clearTypes()                   { types.clear();                       }
  public void setResponses(Action[] A)       { responseList = A;                    }
  public void setResponse(int i, Action A)   { responseList[i] = A;                 }
  public void setPopMember(PopMember PM)     { popMember = PM;                      }
  public boolean toggleFlag(String s)        { return Flag.toggleFlag(flagList, s); }
  public ArrayList resizeItemList(int size)  { return itemList.resize(size);        }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public boolean isGold()         { return findType(ITEM_GOLD) != -1;           }
  public boolean isContainer()    { return findType(ITEM_CONTAINER) != -1;      }
  public boolean isPlayerCorpse() { return findType(ITEM_CORPSE_PLAYER) != -1;  }
  public boolean isEquipment()    { return findType(ITEM_EQUIPMENT) != -1;      }
  public boolean isFood()         { return findType(ITEM_FOOD) != -1;           }
  public boolean isDrinkCont()    { return findType(ITEM_DRINKCONTAINER) != -1; }
  public boolean isFountain()     { return findType(ITEM_FOUNTAIN) != -1;       }
  public boolean isScroll()       { return findType(ITEM_SCROLL) != -1;         }
  public boolean isEmpty()        { return getNumItems() == 0;                  }
  public boolean hasRoom()        { return getNumItems() < maxItems;            }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void destroy(int type)

  {
    ArrayList ilist = itemList.getList();

    for (int i=ilist.size()-1; i>=0; i--)
      ((Item)ilist.get(i)).destroy(type);

    if (myEntity != null)

    {
      if (Equipment.isEquipped(myEntity, this))
        Equipment.dereference(myEntity, this);

      myEntity.itemOut(this, type);
    }

    else if (myRoom != null) myRoom.itemOut(this, type);

    else if (parentContainer != null) parentContainer.itemOut(this, type);

    if (popMember != null)

    {
      popMember.unlinkItem(this);
      popMember = null;
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public int getIllumination()

  {
    return 0;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void setMaxItems(int i)

  {
    if (i % 2 == 1) i++;
    maxItems = i;
    itemList.resize(i);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void destroyPerishables()

  {
    ArrayList ilist = itemList.getList();

    for (int i=ilist.size()-1; i>=0; i--)

    {
      Item I = (Item)ilist.get(i);
      if (I.getFlag(FLAG_PERISH))
        I.destroy(IPERISH);
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public int getMaxLevel()

  {
    int maxLevel = getLevel();
    ArrayList ilist = itemList.getList();

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item)ilist.get(i);
      if (I.getLevel() > maxLevel) maxLevel = I.getLevel();
    }

    return maxLevel;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void removeFromPopulation(boolean permanent)

  {
    if (permanent) popMember.unlinkAll();
    else popMember.unlinkItem(this);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public Item replicate()

  {
    Item I = replicate(typeStamp);
    I.setTimeStamp(I.getTimeStamp());
    I.setItemList(itemList.replicate());
    I.getItemList().setContainer(I);

    return I;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public Item replicate(String stampType)

  {
    Item newItem = null;

    try

    {
      newItem = (Item)super.clone();
      newItem.setTypeList(new ArrayList(types));
      newItem.setLDescWrit(new Writable(lookDesc.getRawString(), lookDesc.getFormat()));
      newItem.setLoadList(new ArrayList(loadList));
      newItem.setItemList(new ItemList(maxItems, ItemList.TYPE_CONTAINER));
      newItem.getItemList().setContainer(newItem);

      Flag[] newFlags = new Flag[NUM_FLAGS];

      for (int i=0; i<NUM_FLAGS; i++)
        newFlags[i] = flagList[i].replicate();

      newItem.setFlags(newFlags);

      Action[] newActions = new Action[NUM_RESPONSES];

      for (int i=0; i<NUM_RESPONSES; i++)
        newActions[i] = responseList[i].replicate();

      newItem.setResponses(newActions);

      if (isContainer())
      for (int i=0; i<loadList.size(); i++)

      {
        int NUM = ((Integer)loadList.get(i)).intValue();
        Item ITEM = World.getItem(NUM).replicate(stampType);

        if ((ITEM != null) && (ITEM.isContainer()))
          Create.item(ITEM, newItem, IREPLICATE);
      }

      if (newItem.getFlag(FLAG_INV_DECAY))
        newItem.startDecay(newItem.getDecayTime());

      newItem.setTypeStamp(stampType);
      newItem.setTimeStamp(SystemTime.getItemTimeStamp());
    }

    catch (Exception e) { System.out.println(e); }

    return newItem;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean compare(Item I1, Item I2)

  {
    return I1.getTimeStamp().equals(I2.getTimeStamp());
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void copyItem(Item exampleItem, Item modifiedItem)

  {
    modifiedItem.setLevel(exampleItem.getLevel());
    modifiedItem.setWorth(exampleItem.getWorth());
    modifiedItem.setPlaces(exampleItem.getPlaces());
    modifiedItem.setClasses(exampleItem.getClasses());

    modifiedItem.setSTR(exampleItem.getSTR());
    modifiedItem.setDEX(exampleItem.getDEX());
    modifiedItem.setCON(exampleItem.getCON());
    modifiedItem.setINT(exampleItem.getINT());
    modifiedItem.setWIS(exampleItem.getWIS());

    modifiedItem.setHR(exampleItem.getHR());
    modifiedItem.setDR(exampleItem.getDR());
    modifiedItem.setAC(exampleItem.getAC());
    modifiedItem.setMR(exampleItem.getMR());
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void initResponses()

  {
    responseList = new Action[NUM_RESPONSES];
    responseList[RESPONSE_EQUIP] = new Action(-1);
    responseList[RESPONSE_EQUIP].setCustom("EQUIP");
    responseList[RESPONSE_EQUIP].setName("Equip");
    responseList[RESPONSE_UNEQUIP] = new Action(-1);
    responseList[RESPONSE_UNEQUIP].setCustom("UNEQUIP");
    responseList[RESPONSE_UNEQUIP].setName("Unequip");
    responseList[RESPONSE_DROP] = new Action(-1);
    responseList[RESPONSE_DROP].setCustom("DROP");
    responseList[RESPONSE_DROP].setName("Drop");
    responseList[RESPONSE_GET] = new Action(-1);
    responseList[RESPONSE_GET].setCustom("GET");
    responseList[RESPONSE_GET].setName("Get");
    responseList[RESPONSE_PUT] = new Action(-1);
    responseList[RESPONSE_PUT].setCustom("PUT");
    responseList[RESPONSE_PUT].setName("Put");
    responseList[RESPONSE_GIVE] = new Action(-1);
    responseList[RESPONSE_GIVE].setCustom("GIVE");
    responseList[RESPONSE_GIVE].setName("Give");
    responseList[RESPONSE_DRINK] = new Action(-1);
    responseList[RESPONSE_DRINK].setCustom("DRINK");
    responseList[RESPONSE_DRINK].setName("Drink");
    responseList[RESPONSE_EAT] = new Action(-1);
    responseList[RESPONSE_EAT].setCustom("EAT");
    responseList[RESPONSE_EAT].setName("Eat");
    responseList[RESPONSE_EMPTY] = new Action(-1);
    responseList[RESPONSE_EMPTY].setCustom("EMPTY");
    responseList[RESPONSE_EMPTY].setName("Empty");
    responseList[RESPONSE_FILL] = new Action(-1);
    responseList[RESPONSE_FILL].setCustom("FILL");
    responseList[RESPONSE_FILL].setName("Fill");
    responseList[RESPONSE_CLOSE] = new Action(-1);
    responseList[RESPONSE_CLOSE].setCustom("CLOSE");
    responseList[RESPONSE_CLOSE].setName("Close");
    responseList[RESPONSE_OPEN] = new Action(-1);
    responseList[RESPONSE_OPEN].setCustom("OPEN");
    responseList[RESPONSE_OPEN].setName("Open");
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void startDecay(int numTicks)

  {
    decayTime = numTicks;
    isDecaying = true;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void stopDecay()

  {
    decayTime = 0;
    isDecaying = false;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public MessageBoard getBoard()

  {
    if (boardID == -1) return null;
    return World.getBoard(boardID);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void addType(int i)

  {
    if (findType(i) == -1)
      types.add(new Integer(i));
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void remType(int i)

  {
    int index = findType(i);
    if (index != -1) types.remove(index);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public int findType(int j)

  {
    int size = types.size();

    for (int i=0; i<size; i++)
    if (((Integer)types.get(i)).intValue() == j)
      return i;

    return -1;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void action(int actionNum)

  {
    if (myEntity == null) Ghost.itemRoom = myRoom;
    else Ghost.itemRoom = myEntity.getRoom();

    if (actionNum == RESPONSE_EQUIP)   castWearSpells();
    if (actionNum == RESPONSE_UNEQUIP) dissipateWearSpells();

    responseList[actionNum].tryAction();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void castWearSpells()

  {
    int size = wearSpellList.size();

    for (int i=0; i<size; i++)

    {
      String effectName = (String)wearSpellList.get(i);
      int level = ((Integer)wearListLevels.get(i)).intValue();
      effectName = "Effect" + getProperClassName(effectName);
      Effect E = null;
      Class C  = null;

      try

      {
        C = Class.forName(effectName);
        E = (Effect) C.newInstance();
        E.initItem(myEntity, level, -1);
      }

      catch (Exception e) { System.out.println("Invalid effect on item: " + id); }
      catch (Throwable t) { System.out.println("Invalid effect on item: " + id); }
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void castUseSpells()

  {
    castSpells(useSpellList, useListLevels, myEntity.getName());
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void use(String targ)

  {
    if ((!isScroll()) && (getMaxUses() == 0)) {
      myEntity.echo("You can't use " + getLName() + ".");
      return; }

    if ((!isScroll()) && (getNumUses() <= 0)) {
      myEntity.echo("There are no more charges left.");
      return; }

    if (castSpells(useSpellList, useListLevels, targ))
      numUses--;

    if (isScroll()) Destroy.item(this, myEntity, IUSE);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void dissipateWearSpells()

  {
    int size = wearSpellList.size();

    for (int i=0; i<size; i++)

    {
      String spellName = (String)wearSpellList.get(i);
      if (Equipment.shouldRemoveEffect(myEntity, spellName))
        myEntity.removeItemEffect(spellName);
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public boolean hasInnateSpell(String spellName)

  {
    int size = wearSpellList.size();

    for (int i=0; i<size; i++)
    if (((String)wearSpellList.get(i)).equals(spellName))
      return true;

    return false;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private boolean castSpells(ArrayList spells, ArrayList spellLevels, String targ)

  {
    boolean casted = false;
    int size = spells.size();

    for (int i=0; i<size; i++)

    {
      int spellLevel = ((Integer)spellLevels.get(i)).intValue();
      String spellName = getProperClassName((String)spells.get(i));
      Spell S = null;
      Class C = null;
      boolean check = false;

      try

      {
        C = Class.forName(spellName);
        S = (Spell) C.newInstance();
        S.init(myEntity, spellLevel);

        if (i == 0) check = S.itemCast(targ, this, true);
        else check = S.itemCast(targ, this, false);

        if ((check == true) && (casted == false)) casted = true;
        if (check == false) return casted;
      }

      catch (Exception e) { e.printStackTrace(); }
      catch (Throwable t) { t.printStackTrace(); }
    }

    return casted;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void drink()

  {
    if (liquidAmount <= 0) {
      myEntity.echo(properName(getLName()) + " is empty.");
      return; }

    liquidAmount--;
    Liquids.drink(myEntity, liquidType, getLName());
    if (liquidAmount == 0) liquidType = 0;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void fill(Item POOL)

  {
    if (liquidType != POOL.getLiquidType())
    if (liquidAmount > 0)
    if (liquidAmount == liquidMax) {
      myEntity.echo(properName(getLName()) + " is already full of " + Liquids.getDesc(liquidType) + ".");
      return; }

    if (liquidType != POOL.getLiquidType())
    if (liquidAmount > 0) {
      myEntity.echo(properName(getLName()) + " still has some " + Liquids.getDesc(liquidType) + " left in it.");
      return; }

    if (liquidMax > 0)
    if (liquidAmount == liquidMax) {
      myEntity.echo(properName(getLName()) + " is already full.");
      return; }

    liquidAmount = liquidMax;
    liquidType = POOL.getLiquidType();
    myEntity.echo("You fill " + getLName() + " from " + POOL.getLName() + ".");
    myEntity.sendToAwake(myEntity.getName() + " fills " + getLName() + " from " + POOL.getLName() + ".");
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void empty()

  {
    if (liquidAmount == 0) {
      myEntity.echo(properName(getLName()) + " is already empty.");
      return; }

    liquidAmount = 0;
    liquidType = 0;

    myEntity.echo("You empty " + getLName() + " on to the ground.");
    myEntity.sendToAwake(myEntity.getName() + " empties " + getLName() + " on to the ground.");
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void updateDecay()

  {
    if (!isDecaying) return;
    decayTime--;
    if (decayTime <= 0) decayObject();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void decayObject()

  {
    if (myRoom != null) {
      Destroy.item(this, myRoom, IDECAYR);
      myRoom.echo(properName(getLName()) + " decays into dust.", ECHO_AWAKE); }

    if (myEntity != null) {
      Destroy.item(this, myEntity, IDECAYE);
      myEntity.getRoom().echo(properName(getLName()) + " decays into dust.", ECHO_AWAKE); }

    if (parentContainer != null) Destroy.item(this, parentContainer, IDECAYC);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public String getIdentify(Entity SRC)

  {
    ArrayList A = new ArrayList();

    String temp = getName() + "#N.";
    if (SRC.getLevel() > 99) temp += " #n(#c" + getID() + "#n)#N";
    A.add(temp);

    if (findType(ITEM_EQUIPMENT) != -1)

    {
      A.add("Level " + getLevel() + " (" + getClasses() + ")");
      if (getPlaces().indexOf("None") == -1) A.add("Equipable As: " + getPlaces() + ".");

      if (getSTR() > 0) A.add("Required Strength: " + getSTR() + ".");
      if (getDEX() > 0) A.add("Required Dexterity: " + getDEX() + ".");
      if (getCON() > 0) A.add("Required Constitution: " + getCON() + ".");
      if (getINT() > 0) A.add("Required Intelligence: " + getINT() + ".");
      if (getWIS() > 0) A.add("Required Wisdom: " + getWIS() + ".");

      if (getHR() > 0) A.add("Increases Hitroll by " + getHR() + ".");
      else if (getHR() < 0) A.add("Decreases Hitroll by " + getHR() + ".");
      if (getDR() > 0) A.add("Increases Damroll by " + getDR() + ".");
      else if (getDR() < 0) A.add("Decreases Damroll by " + getDR() + ".");
      if (getAC() > 0) A.add("Increases Armorclass by " + getAC() + ".");
      else if (getAC() < 0) A.add("Decreases Armorclass by " + getAC() + ".");
      if (getMR() > 0) A.add("Increases Resistance by " + getMR() + ".");
      else if (getMR() < 0) A.add("Decreases Resistance by " + getMR() + ".");

      if (getHP() > 0) A.add("Raises Health by " + getHP() + ".");
      else if (getHP() < 0) A.add("Reduces Health by " + getHP() + ".");
      if (getMN() > 0) A.add("Raises Mana by " + getMN() + ".");
      else if (getMN() < 0) A.add("Reduces Mana by " + getMN() + ".");
      if (getMV() > 0) A.add("Raises Movement by " + getMV() + ".");
      else if (getMV() < 0) A.add("Reduces Movement by " + getMV() + ".");
    }

    if (SRC.getLevel() > 99)

    {
      if (getTimeStamp().length() > 0) A.add("-");
      if (getTimeStamp().length() > 0) A.add(getTypeStamp());
      if (getTimeStamp().length() > 0) A.add(getTimeStamp());
      if (getMarkStamp().length() > 0) A.add(getMarkStamp());
      if (getEditStamp().length() > 0) A.add(getEditStamp());
    }

    return boxify(A,2);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void globalItemDelete(int inum)

  {
    for (int i=loadList.size()-1; i>=0; i--)
    if (getInt(loadList,i) == inum)
      loadList.remove(i);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void globalItemRenumber(int[] indexList)

  {
    id = indexList[id];
    for (int i=0; i<loadList.size(); i++)
      loadList.set(i, new Integer(indexList[getInt(loadList,i)]));
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void itemIn(Item I, int type)

  {
    I.setParent(this);
    I.setEntity(null);
    I.setRoom(null);

    if (type == IPOPC)             { }
    else if (type == IPUT)         { }
    else if (type == IENTERCORPSE) { }
    else if (type == IREPLICATE)   { }
    else System.out.println("Unexpected item in type: " + type);

    itemList.add(I);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void itemOut(Item I, int type)

  {
    if (type == IGETC)        { }
    else if (type == IDECAYC) { }
    else if (type == ISAC)    { }
    else System.out.println("Unexpected item out type: " + type);

    itemList.remove(I);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public boolean canFit(Item I)

  {
    return itemList.canFit(I);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ID");           out.writeObject(new Integer(id));
      out.writeObject("ZONEID");       out.writeObject(new Integer(zoneID));
      out.writeObject("LEVEL");        out.writeObject(new Integer(level));
      out.writeObject("WORTH");        out.writeObject(new Integer(worth));
      out.writeObject("TYPES");        out.writeObject(types);
      out.writeObject("NAME");         out.writeObject(name);
      out.writeObject("LNAME");        out.writeObject(lname);
      out.writeObject("LDESC");        out.writeObject(lookDesc);
      out.writeObject("GDESC");        out.writeObject(groundDesc);
      out.writeObject("TIMESTAMP");    out.writeObject(timeStamp);
      out.writeObject("TYPESTAMP");    out.writeObject(typeStamp);
      out.writeObject("EDITSTAMP");    out.writeObject(editStamp);
      out.writeObject("MARKSTAMP");    out.writeObject(markStamp);
      out.writeObject("DTYPE");        out.writeObject(dtype);
      out.writeObject("HITROLL");      out.writeObject(new Integer(hitRoll));
      out.writeObject("DAMROLL");      out.writeObject(new Integer(damRoll));
      out.writeObject("ARMORCLASS");   out.writeObject(new Integer(armorClass));
      out.writeObject("RESISTANCE");   out.writeObject(new Integer(resistance));
      out.writeObject("HITPOINTS");    out.writeObject(new Integer(hitPoints));
      out.writeObject("MANAPOINTS");   out.writeObject(new Integer(manaPoints));
      out.writeObject("MOVEPOINTS");   out.writeObject(new Integer(movePoints));
      out.writeObject("STRENGTH");     out.writeObject(new Integer(strength));
      out.writeObject("DEXTERITY");    out.writeObject(new Integer(dexterity));
      out.writeObject("INTELLIGENCE"); out.writeObject(new Integer(intelligence));
      out.writeObject("CONSTITUTION"); out.writeObject(new Integer(constitution));
      out.writeObject("WISDOM");       out.writeObject(new Integer(wisdom));
      out.writeObject("BOARDID");      out.writeObject(new Integer(boardID));
      out.writeObject("CLASSES");      out.writeObject(classes);
      out.writeObject("PLACES");       out.writeObject(places);
      out.writeObject("ITEMLIST");     out.writeObject(itemList);
      out.writeObject("MAXITEMS");     out.writeObject(new Integer(maxItems));
      out.writeObject("FLAGS");        out.writeObject(flagList);
      out.writeObject("LOADITEMS");    out.writeObject(loadList);
      out.writeObject("BLOCKERTYPE");  out.writeObject(new Integer(blockerType));
      out.writeObject("BLOCKERDIR");   out.writeObject(new Integer(blockerDir));
      out.writeObject("BLOCKERPARAM"); out.writeObject(blockerParam);
      out.writeObject("DECAYTIME");    out.writeObject(new Integer(decayTime));
      out.writeObject("LIQUIDTYPE");   out.writeObject(new Integer(liquidType));
      out.writeObject("LIQUIDAMOUNT"); out.writeObject(new Integer(liquidAmount));
      out.writeObject("LIQUIDMAX");    out.writeObject(new Integer(liquidMax));
      out.writeObject("NUMUSES");      out.writeObject(new Integer(numUses));
      out.writeObject("MAXUSES");      out.writeObject(new Integer(maxUses));
      out.writeObject("INNATELIST");   out.writeObject(wearSpellList);
      out.writeObject("INNATELEVELS"); out.writeObject(wearListLevels);
      out.writeObject("CHARGELIST");   out.writeObject(useSpellList);
      out.writeObject("CHARGELEVELS"); out.writeObject(useListLevels);
      out.writeObject("RESPONSES");    out.writeObject(responseList);
      out.writeObject("ITEM END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("ITEM END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("ID"))          id = ((Integer)in.readObject()).intValue();
        else if (tag.equals("ZONEID")) zoneID = ((Integer)in.readObject()).intValue();
        else if (tag.equals("LEVEL"))  level = ((Integer)in.readObject()).intValue();
        else if (tag.equals("WORTH"))  worth = ((Integer)in.readObject()).intValue();
        else if (tag.equals("TYPES"))  types = (ArrayList)in.readObject();
        else if (tag.equals("NAME"))   name = (String)in.readObject();
        else if (tag.equals("LNAME"))  lname = (String)in.readObject();
        else if (tag.equals("LDESC"))  lookDesc = (Writable)in.readObject();
        else if (tag.equals("GDESC"))  groundDesc = (String)in.readObject();
        else if (tag.equals("TIMESTAMP")) timeStamp = (String)in.readObject();
        else if (tag.equals("TYPESTAMP")) typeStamp = (String)in.readObject();
        else if (tag.equals("EDITSTAMP")) editStamp = (String)in.readObject();
        else if (tag.equals("MARKSTAMP")) markStamp = (String)in.readObject();
        else if (tag.equals("DTYPE")) dtype = (String)in.readObject();
        else if (tag.equals("HITROLL")) hitRoll = ((Integer)in.readObject()).intValue();
        else if (tag.equals("DAMROLL")) damRoll = ((Integer)in.readObject()).intValue();
        else if (tag.equals("ARMORCLASS")) armorClass = ((Integer)in.readObject()).intValue();
        else if (tag.equals("RESISTANCE")) resistance = ((Integer)in.readObject()).intValue();
        else if (tag.equals("HITPOINTS")) hitPoints = ((Integer)in.readObject()).intValue();
        else if (tag.equals("MANAPOINTS")) manaPoints = ((Integer)in.readObject()).intValue();
        else if (tag.equals("MOVEPOINTS")) movePoints = ((Integer)in.readObject()).intValue();
        else if (tag.equals("STRENGTH")) strength = ((Integer)in.readObject()).intValue();
        else if (tag.equals("DEXTERITY")) dexterity = ((Integer)in.readObject()).intValue();
        else if (tag.equals("INTELLIGENCE")) intelligence = ((Integer)in.readObject()).intValue();
        else if (tag.equals("CONSTITUTION")) constitution = ((Integer)in.readObject()).intValue();
        else if (tag.equals("WISDOM")) wisdom = ((Integer)in.readObject()).intValue();
        else if (tag.equals("BOARDID")) boardID = ((Integer)in.readObject()).intValue();
        else if (tag.equals("CLASSES")) classes = (String)in.readObject();
        else if (tag.equals("PLACES")) places = (String)in.readObject();
        else if (tag.equals("ITEMLIST")) itemList = (ItemList)in.readObject();
        else if (tag.equals("MAXITEMS")) maxItems = ((Integer)in.readObject()).intValue();
        else if (tag.equals("FLAGS")) Flag.setFlags((Flag[])in.readObject(), flagList);
        else if (tag.equals("LOADITEMS")) loadList = (ArrayList)in.readObject();
        else if (tag.equals("BLOCKERTYPE")) blockerType = ((Integer)in.readObject()).intValue();
        else if (tag.equals("BLOCKERDIR")) blockerDir = ((Integer)in.readObject()).intValue();
        else if (tag.equals("BLOCKERPARAM")) blockerParam = (String)in.readObject();
        else if (tag.equals("DECAYTIME")) decayTime = ((Integer)in.readObject()).intValue();
        else if (tag.equals("LIQUIDTYPE")) liquidType = ((Integer)in.readObject()).intValue();
        else if (tag.equals("LIQUIDAMOUNT")) liquidAmount = ((Integer)in.readObject()).intValue();
        else if (tag.equals("LIQUIDMAX")) liquidMax = ((Integer)in.readObject()).intValue();
        else if (tag.equals("INNATELIST")) wearSpellList = (ArrayList)in.readObject();
        else if (tag.equals("INNATELEVELS")) wearListLevels = (ArrayList)in.readObject();
        else if (tag.equals("CHARGELIST")) useSpellList = (ArrayList)in.readObject();
        else if (tag.equals("CHARGELEVELS")) useListLevels = (ArrayList)in.readObject();
        else if (tag.equals("NUMUSES")) numUses = ((Integer)in.readObject()).intValue();
        else if (tag.equals("MAXUSES")) maxUses = ((Integer)in.readObject()).intValue();
        else if (tag.equals("RESPONSES")) Action.updateResponses((Action[])in.readObject(), responseList);
        else if (!tag.equals("ITEM END")) in.readObject();
      }

      itemList.setContainer(this);
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
}
