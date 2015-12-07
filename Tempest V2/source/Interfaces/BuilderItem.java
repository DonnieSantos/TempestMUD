import java.io.*;
import java.util.*;

public class BuilderItem extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int MAIN_MENU            = 0;
  public static final int SET_NAME             = 1;
  public static final int SET_LNAME            = 2;
  public static final int SET_GDESC            = 3;
  public static final int SET_LEVEL            = 4;
  public static final int SET_WORTH            = 5;
  public static final int SET_TYPE             = 6;
  public static final int SET_CONTAINERMAX     = 7;
  public static final int SET_FLAG             = 8;
  public static final int SET_BOARD            = 9;
  public static final int SET_PLACE            = 10;
  public static final int SET_CLASS            = 11;
  public static final int SET_DTYPE            = 12;
  public static final int SET_DECAY_TIME       = 13;

  public static final int MENU_CONTAINED       = 14;
  public static final int ADD_CONTAINED        = 15;
  public static final int REM_CONTAINED        = 16;

  public static final int MENU_STATS           = 17;
  public static final int SET_STR              = 18;
  public static final int SET_DEX              = 19;
  public static final int SET_CON              = 20;
  public static final int SET_INT              = 21;
  public static final int SET_WIS              = 22;

  public static final int MENU_BONUSES         = 23;
  public static final int SET_HR               = 24;
  public static final int SET_DR               = 25;
  public static final int SET_MR               = 26;
  public static final int SET_AC               = 27;
  public static final int SET_HP               = 28;
  public static final int SET_MN               = 29;
  public static final int SET_MV               = 30;

  public static final int MENU_BLOCKER         = 31;
  public static final int SET_BLOCKER_DIR      = 32;
  public static final int SET_BLOCKER_TYPE     = 33;
  public static final int SET_BLOCKER_IMMORTAL = 34;
  public static final int SET_BLOCKER_LEVEL    = 35;
  public static final int SET_BLOCKER_CLASS    = 36;
  public static final int SET_BLOCKER_CLAN     = 37;
  public static final int SET_BLOCKER_RELIGION = 38;

  public static final int MENU_LIQUID          = 39;
  public static final int SET_LIQUID_TYPE      = 40;
  public static final int SET_LIQUID_USES      = 41;
  public static final int SET_LIQUID_MAX       = 42;

  public static final int MENU_CHARGED         = 43;
  public static final int ADD_CHARGED_NAME     = 44;
  public static final int ADD_CHARGED_LEVEL    = 45;
  public static final int REM_CHARGED          = 46;
  public static final int SET_CHARGED_USES     = 47;
  public static final int SET_CHARGED_MAX      = 48;

  public static final int MENU_INNATE          = 49;
  public static final int ADD_INNATE_NAME      = 50;
  public static final int ADD_INNATE_LEVEL     = 51;
  public static final int REM_INNATE           = 52;

  public static final int MENU_ACTION          = 53;
  public static final int EDIT_ACTION          = 54;
  public static final int COPY_ACTION_INDEX    = 55;
  public static final int COPY_ACTION_ID       = 56;

  public static final int DELETE_ITEM_GLOBAL   = 57;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public int viewMode()                { return NO_OUTPUT;    }
  public void setInventory()           { inInventory = true;  }
  public void echo(String s)           { utilityQueue.add(s); }
  public void addOutput(String s)      { return;              }
  public void addFightOutput(String s) { return;              }
  public void setEditStamp(String s)   { editStamp = s;       }
  public void setMarkStamp(String s)   { markStamp = s;       }
  public void focusGained()            {                      }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private Item ITEM;
  private int state;
  private int integerHolder;
  private int builderLevel;
  private boolean inInventory;
  private boolean GLOBAL_DELETE;
  private boolean PROTOTYPE_ITEM;
  private String editStamp;
  private String markStamp;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public BuilderItem(Client c, Item i, boolean prototype)

  {
    super(c);
    ITEM = i;
    PROTOTYPE_ITEM = prototype;
    GLOBAL_DELETE = false;
    state = MAIN_MENU;
    inInventory = false;
    c.setClientState(CSTATE_BUILDING);
    builderLevel = c.getCharInfoBackup().getLevel();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void flushUtilityQueue()

  {
    output = "";

    while (!utilityQueue.isEmpty())
      output += utilityQueue.removeFirst().toString();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleOutput()

  {
    if (!active) return;

    flushUtilityQueue();
    myClient.msg(output + getPrompt());
    menuMsg = "";
    active = false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    if (!PROTOTYPE_ITEM) ITEM.setEditStamp(editStamp);
    if (!PROTOTYPE_ITEM) ITEM.setMarkStamp(markStamp);

    myClient.popInterface();

    if (!inInventory)

    {
      int inum = ITEM.getZoneID();
      WriteThread.addObject(World.getZone(inum));
    }

    else myClient.getCharInfo().castChar().save();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    if (state == MAIN_MENU)                  return getItemMenu();
    else if (state == SET_NAME)              return "Enter New Item Name: ";
    else if (state == SET_LNAME)             return "Enter New Lowercase Item Name: ";
    else if (state == SET_GDESC)             return "Enter New Ground Description: ";
    else if (state == SET_LEVEL)             return "Enter New Level: ";
    else if (state == SET_WORTH)             return "Enter New Worth: ";
    else if (state == SET_TYPE)              return getTypeMenu();
    else if (state == SET_CONTAINERMAX)      return "Enter Container Max: ";
    else if (state == SET_FLAG)              return getFlagList();
    else if (state == SET_BOARD)             return getBoardList();
    else if (state == SET_PLACE)             return getPlacesMenu();
    else if (state == SET_CLASS)             return getClassesMenu();
    else if (state == SET_DTYPE)             return getDTypeMenu();

    else if (state == MENU_LIQUID)           return getLiquidMenu();
    else if (state == SET_LIQUID_TYPE)       return "Select Liquid Type: ";
    else if (state == SET_LIQUID_USES)       return "Enter Number of Uses: ";
    else if (state == SET_LIQUID_MAX)        return "Enter the Maximum Number of Uses: ";
    else if (state == SET_DECAY_TIME)        return "Enter the Decay Time: ";

    else if (state == MENU_CONTAINED)        return getContainedMenu();
    else if (state == ADD_CONTAINED)         return "Enter Item ID To Add: ";
    else if (state == REM_CONTAINED)         return "Enter Item ID To Remove: ";

    else if (state == MENU_STATS)            return getStatMenu();
    else if (state == SET_STR)               return "Enter Required Strength: ";
    else if (state == SET_DEX)               return "Enter Required Dexterity: ";
    else if (state == SET_CON)               return "Enter Required Constitution: ";
    else if (state == SET_INT)               return "Enter Required Intelligence: ";
    else if (state == SET_WIS)               return "Enter Required Wisdom: ";

    else if (state == MENU_BONUSES)          return getBonusesMenu();
    else if (state == SET_HR)                return "Enter Hitroll: ";
    else if (state == SET_DR)                return "Enter Damageroll: ";
    else if (state == SET_MR)                return "Enter Magic Resistance: ";
    else if (state == SET_AC)                return "Enter Armorclass: ";
    else if (state == SET_HP)                return "Enter Hitpoints: ";
    else if (state == SET_MN)                return "Enter Manapoints: ";
    else if (state == SET_MV)                return "Enter Movepoints: ";

    else if (state == MENU_BLOCKER)          return getBlockerMenu();
    else if (state == SET_BLOCKER_DIR)       return getBlockerDirMenu();
    else if (state == SET_BLOCKER_TYPE)      return getBlockerTypeMenu();
    else if (state == SET_BLOCKER_IMMORTAL)  return "Select Min-Level to pass (100-110): ";
    else if (state == SET_BLOCKER_LEVEL)     return "Select Min-Level to pass (1-99): ";
    else if (state == SET_BLOCKER_CLASS)     return getBlockerClassInfo() + "Classes:  ";
    else if (state == SET_BLOCKER_CLAN)      return "Enter Clan Name: ";
    else if (state == SET_BLOCKER_RELIGION)  return "Enter Religion Name: ";

    else if (state == MENU_CHARGED)          return getChargeSpellMenu();
    else if (state == ADD_CHARGED_NAME)      return "Enter Name of the Spell: ";
    else if (state == ADD_CHARGED_LEVEL)     return "Enter Level of Spell (0-100): ";
    else if (state == REM_CHARGED)           return "Enter Spell Index to Remove: ";
    else if (state == SET_CHARGED_USES)      return "Enter the Number of Uses: ";
    else if (state == SET_CHARGED_MAX)       return "Enter the Maximum Number of Uses: ";

    else if (state == MENU_INNATE)           return getInnateMenu();
    else if (state == ADD_INNATE_NAME)       return "Enter Name of the Effect: ";
    else if (state == ADD_INNATE_LEVEL)      return "Enter Level of Effect (0-100): ";
    else if (state == REM_INNATE)            return "Enter Effect Index to Remove: ";

    else if (state == MENU_ACTION)           return getActionMenu();
    else if (state == EDIT_ACTION)           return "Enter Action to Edit: ";
    else if (state == COPY_ACTION_INDEX)     return "Enter Response Type: ";
    else if (state == COPY_ACTION_ID)        return "Enter Action ID: ";

    else if (state == DELETE_ITEM_GLOBAL)    return "Type 'delete' if you are sure: ";

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;

    String input = clearWhiteSpace(myClient.getCommand());

    if (state == MAIN_MENU)                  processItemMenu(input);
    else if (state == SET_NAME)              processNameChange(input);
    else if (state == SET_LNAME)             processLNameChange(input);
    else if (state == SET_GDESC)             processGroundDesc(input);
    else if (state == SET_LEVEL)             processChangeLevel(input);
    else if (state == SET_WORTH)             processChangeWorth(input);
    else if (state == SET_TYPE)              processChangeType(input);
    else if (state == SET_CONTAINERMAX)      processContainerMax(input);
    else if (state == SET_FLAG)              processChangeFlag(input);
    else if (state == SET_BOARD)             processChangeBoard(input);
    else if (state == SET_PLACE)             processPlaceSet(input);
    else if (state == SET_CLASS)             processClassSet(input);
    else if (state == SET_DTYPE)             processChangeDType(input);

    else if (state == MENU_LIQUID)           processLiquidMenu(input);
    else if (state == SET_LIQUID_TYPE)       processLiquidType(input);
    else if (state == SET_LIQUID_USES)       processLiquidUses(input);
    else if (state == SET_LIQUID_MAX)        processLiquidMax(input);
    else if (state == SET_DECAY_TIME)        processDecayTime(input);

    else if (state == MENU_CONTAINED)        processContainedMenu(input);
    else if (state == ADD_CONTAINED)         processAddContained(input);
    else if (state == REM_CONTAINED)         processRemoveContained(input);

    else if (state == MENU_STATS)            processStatMenu(input);
    else if (state == SET_STR)               processChangeStat(input);
    else if (state == SET_DEX)               processChangeStat(input);
    else if (state == SET_CON)               processChangeStat(input);
    else if (state == SET_INT)               processChangeStat(input);
    else if (state == SET_WIS)               processChangeStat(input);

    else if (state == MENU_BONUSES)          processBonusMenu(input);
    else if (state == SET_HR)                processChangeBonus(input);
    else if (state == SET_DR)                processChangeBonus(input);
    else if (state == SET_AC)                processChangeBonus(input);
    else if (state == SET_MR)                processChangeBonus(input);
    else if (state == SET_HP)                processChangeBonus(input);
    else if (state == SET_MN)                processChangeBonus(input);
    else if (state == SET_MV)                processChangeBonus(input);

    else if (state == MENU_BLOCKER)          processBlockerMenu(input);
    else if (state == SET_BLOCKER_DIR)       processBlockerDir(input);
    else if (state == SET_BLOCKER_TYPE)      processBlockerType(input);
    else if (state == SET_BLOCKER_IMMORTAL)  processBlockerImmortal(input);
    else if (state == SET_BLOCKER_LEVEL)     processBlockerLevel(input);
    else if (state == SET_BLOCKER_CLASS)     processBlockerClass(input);
    else if (state == SET_BLOCKER_CLAN)      processBlockerClan(input);
    else if (state == SET_BLOCKER_RELIGION)  processBlockerReligion(input);

    else if (state == MENU_CHARGED)          processChargedMenu(input);
    else if (state == ADD_CHARGED_NAME)      processAddChargedName(input);
    else if (state == ADD_CHARGED_LEVEL)     processAddChargedLevel(input);
    else if (state == REM_CHARGED)           processRemoveCharged(input);
    else if (state == SET_CHARGED_USES)      processChargedUses(input);
    else if (state == SET_CHARGED_MAX)       processChargedMax(input);

    else if (state == MENU_INNATE)           processInnateMenu(input);
    else if (state == ADD_INNATE_NAME)       processInnateName(input);
    else if (state == ADD_INNATE_LEVEL)      processInnateLevel(input);
    else if (state == REM_INNATE)            processInnateRemove(input);

    else if (state == MENU_ACTION)           processActionMenu(input);
    else if (state == EDIT_ACTION)           processEditAction(input);
    else if (state == COPY_ACTION_INDEX)     processCopyActionIndex(input);
    else if (state == COPY_ACTION_ID)        processCopyActionID(input);

    else if (state == DELETE_ITEM_GLOBAL)    processDeleteItem(input);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processActionMenu(String input)

  {
    if ((input.length() == 0) || (input.equals("3"))) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1")) state = EDIT_ACTION;
    if (input.equals("2")) state = COPY_ACTION_INDEX;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processEditAction(String input)

  {
    if (input.length() == 0) {
      errorMsg("Edit Action cancelled.");
      state = MENU_ACTION;
      return; }

    try

    {
      int index = Integer.parseInt(input)-1;
      if (index < 0) throw new Exception();
      if (index >= Item.NUM_RESPONSES) throw new Exception();

      Action A = ITEM.getResponse(index);
      BuilderAction BA = new BuilderAction(myClient, A, false);
      myClient.pushInterface(BA);
      goodMsg("Action changed.");
    }

    catch (Exception e) { errorMsg("Invalid action index."); }

    state = MENU_ACTION;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processCopyActionIndex(String input)

  {
    if (input.length() == 0) {
      errorMsg("Copy Action cancelled.");
      state = MENU_ACTION;
      return; }

    try

    {
      int index = Integer.parseInt(input)-1;
      if (index < 0) throw new Exception();
      if (index >= Item.NUM_RESPONSES) throw new Exception();

      integerHolder = index;
      state = COPY_ACTION_ID;
      return;
    }

    catch (Exception e) { errorMsg("Invalid action index."); }

    state = MENU_ACTION;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processCopyActionID(String input)

  {
    if (input.length() == 0) {
      errorMsg("Copy Action cancelled.");
      state = MENU_ACTION;
      return; }

    try

    {
      int ID = Integer.parseInt(input);
      if (ID < 0) throw new Exception("Invalid action ID.");
      if (ID >= World.MAX_ACTIONS) throw new Exception("Invalid action ID.");

      Action A = World.getAction(ID);

      if (A == null) throw new Exception("That action does not exist.");

      A = A.replicate();
      A.setCustom(ITEM.getResponse(integerHolder).getCustom());
      ITEM.setResponse(integerHolder, A);
      goodMsg("Action copied.");
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid action ID."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_ACTION;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void tryGlobalDelete()

  {
    if (!PROTOTYPE_ITEM) {
      errorMsg("You can only delete items from the global list.");
      state = MAIN_MENU;
      return; }

    state = DELETE_ITEM_GLOBAL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processDeleteItem(String input)

  {
    if (!input.equalsIgnoreCase("delete")) {
      errorMsg("Item deletion cancelled.");
      state = MAIN_MENU;
      return; }

    GLOBAL_DELETE = true;
    exit();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processInnateMenu(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1")) { state = ADD_INNATE_NAME; }
    if (input.equals("2")) { state = REM_INNATE;      }
    if (input.equals("3")) { state = MAIN_MENU;       }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processInnateName(String input)

  {
    if (input.length() == 0) {
      errorMsg("Add innate spell cancelled.");
      state = MENU_INNATE;
      return; }

    input = input.toLowerCase();
    if (!input.startsWith("effect")) input = "effect " + input;
    Effect effect = Effect.createEffect(input, null, 1, 1);

    if (effect == null) {
      errorMsg("There is no such effect.");
      state = MENU_INNATE;
      return; }

    integerHolder = effect.getID();
    state = ADD_INNATE_LEVEL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processInnateLevel(String input)

  {
    if (input.length() == 0) {
      errorMsg("Add innate spell cancelled.");
      state = MENU_INNATE;
      return; }

    try

    {
      int level = Integer.parseInt(input);
      if (level < 0) throw new Exception();
      if (level > 100) throw new Exception();

      ITEM.getWearList().add(Effect.getEffectName(integerHolder));
      ITEM.getWearLevels().add(new Integer(level));

      goodMsg(Effect.getEffectName(integerHolder) + " added successfully.");
    }

    catch (Exception e) { errorMsg("Invalid effect level."); }

    state = MENU_INNATE;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processInnateRemove(String input)

  {
    if (input.length() == 0) {
      errorMsg("Innate spell removal cancelled.");
      state = MENU_INNATE;
      return; }

    try

    {
      int index = Integer.parseInt(input)-1;
      if (index < 0) throw new Exception();
      if (index >= ITEM.getWearList().size()) throw new Exception();

      ITEM.getWearList().remove(index);
      ITEM.getWearLevels().remove(index);
      goodMsg("Effect removed successfully.");
    }

    catch (Exception e) { errorMsg("Invalid effect index."); }

    state = MENU_INNATE;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processChargedMenu(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1")) { state = ADD_CHARGED_NAME; }
    if (input.equals("2")) { state = REM_CHARGED;      }
    if (input.equals("3")) { state = SET_CHARGED_USES; }
    if (input.equals("4")) { state = SET_CHARGED_MAX;  }
    if (input.equals("5")) { state = MAIN_MENU;        }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processAddChargedName(String input)

  {
    if (input.length() == 0) {
      errorMsg("Add charged spell cancelled.");
      state = MENU_CHARGED;
      return; }

    Spell spell = Spell.createSpell(input, null, 0);

    if (spell == null) {
      errorMsg("There is no such spell.");
      state = MENU_CHARGED;
      return; }

    integerHolder = spell.getID();
    state = ADD_CHARGED_LEVEL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processAddChargedLevel(String input)

  {
    if (input.length() == 0) {
      errorMsg("Add charged spell cancelled.");
      state = MENU_CHARGED;
      return; }

    try

    {
      int level = Integer.parseInt(input);
      if (level < 0) throw new Exception();
      if (level > 100) throw new Exception();

      ArrayList levels = ITEM.getUseLevels();
      ArrayList snames = ITEM.getUseSpells();

      levels.add(new Integer(level));
      snames.add(Spell.getSpellName(integerHolder));

      ArrayList spells = new ArrayList();

      for (int i=0; i<snames.size(); i++)

      {
        Spell newSpell = Spell.createSpell((String)snames.get(i), null, 0);
        spells.add(newSpell);
      }

      // Sort the spells by spell type, so that the harmful spells will execute first.

      for (int b=0; b<spells.size()-1; b++)
      for (int i=0; i<spells.size()-1; i++)
      if (((Spell)spells.get(i)).getSpellType() > ((Spell)spells.get(i+1)).getSpellType())

      {
        Integer L1 = (Integer) levels.get(i);
        Integer L2 = (Integer) levels.get(i+1);
        String N1 = (String) snames.get(i);
        String N2 = (String) snames.get(i+1);
        Spell S1 = (Spell) spells.get(i);
        Spell S2 = (Spell) spells.get(i+1);

        levels.set(i, L2);  levels.set(i+1, L1);
        snames.set(i, N2);  snames.set(i+1, N1);
        spells.set(i, S2);  spells.set(i+1, S1);
      }

      goodMsg("Charged spell added.");
    }

    catch (Exception e) { errorMsg("Invalid charged spell level."); }

    state = MENU_CHARGED;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processRemoveCharged(String input)

  {
    if (input.length() == 0) {
      errorMsg("Charged spell removal cancelled.");
      state = MENU_CHARGED;
      return; }

    try

    {
      int index = Integer.parseInt(input) - 1;
      if (index < 0) throw new Exception();
      if (index >= ITEM.getUseLevels().size()) throw new Exception();

      ITEM.getUseLevels().remove(index);
      ITEM.getUseSpells().remove(index);
      goodMsg("Charged spell removed.");
    }

    catch (Exception e) { errorMsg("Invalid charged spell index."); }

    state = MENU_CHARGED;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processChargedUses(String input)

  {
    if (input.length() == 0) {
      errorMsg("Set Uses cancelled.");
      state = MENU_CHARGED;
      return; }

    try

    {
      int uses = Integer.parseInt(input);
      if (uses < 0) throw new Exception("Invalid number of uses.");
      if (uses > ITEM.getMaxUses())
        throw new Exception("The number of uses must be equal to or less than the maximum uses.");

      ITEM.setNumUses(uses);
      goodMsg("Number of uses set.");
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid number of uses."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_CHARGED;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processChargedMax(String input)

  {
    if (input.length() == 0) {
      errorMsg("Set Max Uses cancelled.");
      state = MENU_CHARGED;
      return; }

    try

    {
      int max = Integer.parseInt(input);
      if (max < 0) throw new Exception();
      if (max < ITEM.getNumUses()) ITEM.setNumUses(max);

      ITEM.setMaxUses(max);
      goodMsg("Maximum number of uses set.");
    }

    catch (Exception e) { errorMsg("Invalid number of uses."); }

    state = MENU_CHARGED;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processChangeDType(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    boolean valid = true;

    if (input.equals("1"))       { ITEM.setDType("Stab");     }
    else if (input.equals("2"))  { ITEM.setDType("Slash");    }
    else if (input.equals("3"))  { ITEM.setDType("Pierce");   }
    else if (input.equals("4"))  { ITEM.setDType("Bludgeon"); }
    else if (input.equals("5"))  { ITEM.setDType("Chop");     }
    else if (input.equals("6"))  { ITEM.setDType("Whip");     }
    else if (input.equals("7"))  { ITEM.setDType("Arrow");    }
    else if (input.equals("8"))  { ITEM.setDType("Star");     }
    else if (input.equals("9"))  { ITEM.setDType("Fist");     }
    else if (input.equals("10")) { ITEM.setDType("Thrust");   }
    else if (input.equals("11")) { ITEM.setDType("Gouge");    }
    else if (input.equals("12")) { ITEM.setDType("Blast");    }
    else if (input.equals("13")) { state = MAIN_MENU; return; }
    else valid = false;

    if (valid) { goodMsg("Damage type set."); }
    else { errorMsg("Invalid selection."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processChangeBonus(String input)

  {
    if (input.length() == 0) {
      errorMsg("Stat bonus modification cancelled.");
      state = MENU_BONUSES;
      return; }

    try

    {
      int newStat = Integer.parseInt(input);
      if (newStat < 0) throw new Exception();

      if (state == SET_MR)
      if (newStat > 100) throw new Exception();
      if (newStat > 999) throw new Exception();

      if (state == SET_HR)      ITEM.setHR(newStat);
      else if (state == SET_DR) ITEM.setDR(newStat);
      else if (state == SET_MR) ITEM.setMR(newStat);
      else if (state == SET_AC) ITEM.setAC(newStat);
      else if (state == SET_HP) ITEM.setHP(newStat);
      else if (state == SET_MN) ITEM.setMN(newStat);
      else if (state == SET_MV) ITEM.setMV(newStat);

      goodMsg("Bonus set.");
    }

    catch (Exception e) { errorMsg("Invalid bonus."); }

    state = MENU_BONUSES;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processBonusMenu(String input)

  {
    if (input.length() == 0)    state = MAIN_MENU;
    else if (input.equals("1")) state = SET_HR;
    else if (input.equals("2")) state = SET_DR;
    else if (input.equals("3")) state = SET_MR;
    else if (input.equals("4")) state = SET_AC;
    else if (input.equals("5")) state = SET_HP;
    else if (input.equals("6")) state = SET_MN;
    else if (input.equals("7")) state = SET_MV;
    else if (input.equals("8")) state = MAIN_MENU;
    else errorMsg("Invalid selection.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processChangeStat(String input)

  {
    if (input.length() == 0) {
      errorMsg("Stat requirement modification cancelled.");
      state = MENU_STATS;
      return; }

    try

    {
      int newStat = Integer.parseInt(input);

      if (newStat < 0) throw new Exception();
      if (newStat > 999) throw new Exception();

      if (state == SET_STR)      ITEM.setSTR(newStat);
      else if (state == SET_DEX) ITEM.setDEX(newStat);
      else if (state == SET_CON) ITEM.setCON(newStat);
      else if (state == SET_INT) ITEM.setINT(newStat);
      else if (state == SET_WIS) ITEM.setWIS(newStat);

      goodMsg("Stat set.");
    }

    catch (Exception e) { errorMsg("Invalid required stat."); }

    state = MENU_STATS;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processStatMenu(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1"))      state = SET_STR;
    else if (input.equals("2")) state = SET_DEX;
    else if (input.equals("3")) state = SET_CON;
    else if (input.equals("4")) state = SET_INT;
    else if (input.equals("5")) state = SET_WIS;
    else if (input.equals("6")) state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRemoveContained(String input)

  {
    if (input.length() == 0) {
      state = MENU_CONTAINED;
      errorMsg("Removal of contained item cancelled.");
      return; }

    try

    {
      int num = Integer.parseInt(input);
      int index = -1;
      ArrayList list = ITEM.getLoadList();

      for (int i=list.size()-1; i>=0; i--)
        if (((Integer)list.get(i)).intValue() == num)
          index = i;

      if (index == -1)
        throw new Exception("Item not found in contained list.");

      int i = ((Integer)list.remove(index)).intValue();
      Item remItem = World.getItem(i);

      if (remItem == null) goodMsg("Bad item removed.");
      else goodMsg(remItem.getName() + " has been removed.");
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid number."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_CONTAINED;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processAddContained(String input)

  {
    if (input.length() == 0) {
      state = MENU_CONTAINED;
      errorMsg("Addition of a contained item cancelled.");
      return; }

    try

    {
      int num = Integer.parseInt(input);
      if (num < 0) throw new Exception("That item does not exist.");
      if (num >= World.MAX_ITEMS) throw new Exception("That item does not exist.");

      Item cItem = World.getItem(num);

      if (cItem == null)
        throw new Exception("That item does not exist.");

      if (cItem.findType(Item.ITEM_CONTAINER) != -1)
        throw new Exception("You can't add containers to the contained list.");

      ITEM.getLoadList().add(new Integer(num));
      goodMsg(cItem.getName() + " added to the contained list.");
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid item number."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_CONTAINED;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processContainedMenu(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1"))      state = ADD_CONTAINED;
    else if (input.equals("2")) state = REM_CONTAINED;
    else if (input.equals("3")) state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processPlaceSet(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    input = properPlaceName(input);

    String placeList = " Weapon Hold Body Head Feet Back Hands "
                     + "Waist Legs Right_Ear Left_Ear Right_Wrist Left_Wrist "
                     + "Right_Finger Left_Finger Neck Either_Ear "
                     + "Either_Wrist Either_Finger ";

    if (placeList.indexOf(" " + input + " ") == -1) {
      errorMsg("Invalid place.");
      return; }

    String places = ITEM.getPlaces();

    if (places.equals("None")) places = "";

    if (places.indexOf(input) == -1) places += " " + input;
    else places = places.replaceFirst(input, "");

    places = places.trim();

    if (places.length() == 0) places = "None";

    ITEM.setPlaces(places);
    goodMsg("Place toggled.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processClassSet(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    input = properName(input);

    String curClasses = ITEM.getClasses();
    String classList = "Wa Kn Br Pa Cr Dk Ap "
                     + "Th As Rg Ra Me Sb Mc "
                     + "Cl Dr Mo Pr Hl Dc Al "
                     + "Mg Wi Il Sc Sm Wl Sh All";

    if ((classList.indexOf(input) == -1) || (input.length() < 2)) {
      errorMsg("Invalid class.");
      return; }

    if (curClasses.equals("All") && !input.equals("All")) {
      errorMsg("This item already can be worn by all classes.");
      return; }

    if (curClasses.equals("None")) curClasses = "";

    if (input.equals("All") && !curClasses.equals("All"))
      curClasses = "All";
    else if (curClasses.indexOf(input) != -1)
      curClasses = curClasses.replaceFirst(input, "");
    else curClasses += " " + input;

    curClasses = curClasses.trim();

    if (curClasses.length() == 0)  curClasses = "None";

    ITEM.setClasses(curClasses);
    goodMsg("Class set.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processChangeBoard(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    try

    {
      int boardNum = Integer.parseInt(input);
      if (boardNum < 0) throw new Exception();
      if (boardNum >= World.MAX_BOARDS) throw new Exception();
      if (World.getBoard(boardNum) == null) throw new Exception();

      ITEM.setBoard(boardNum);
      goodMsg("Message board link set to: #N" + World.getBoard(boardNum).getBoardName() + "#g.");
    }

    catch (Exception e) { errorMsg("Invalid board number."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processChangeFlag(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    if (!ITEM.toggleFlag(input))
      errorMsg("That flag does not exist.");
    else goodMsg("Flag toggled.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processContainerMax(String input)

  {
    if (input.length() == 0) {
      errorMsg("Set maximum container capacity cancelled.");
      state = MAIN_MENU;
      return; }

    int max = 0;

    try

    {
      max = Integer.parseInt(input);
      if (max <= 0) throw new Exception();
      if (max > 100) throw new Exception();

      ITEM.setMaxItems(max);
      goodMsg("Max items set to: " + max + "#g.");
    }

    catch (Exception e) { errorMsg("The value must be between 1 and 100."); }

    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processChangeType(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    String strType = "";

    if (input.equals("1"))       { strType = "NORMAL";         }
    else if (input.equals("2"))  { strType = "CONTAINER";      }
    else if (input.equals("3"))  { strType = "BOARD";          }
    else if (input.equals("4"))  { strType = "FOUNTAIN";       }
    else if (input.equals("5"))  { strType = "BLOCKER";        }
    else if (input.equals("6"))  { strType = "GOLD";           }
    else if (input.equals("7"))  { strType = "SCROLL";         }
    else if (input.equals("8"))  { strType = "CONSUMEFOOD";    }
    else if (input.equals("9"))  { strType = "DRINKCONTAINER"; }
    else if (input.equals("10")) { strType = "EQUIPMENT";      }
    else if (input.equals("11")) { strType = "EQCONTAINER";    }
    else if (input.equals("12")) { state = MAIN_MENU; return;  }
    else { errorMsg("Invalid selection."); return; }

    setType(strType);
    goodMsg("Type set.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processItemMenu(String input)

  {
    if (input.length() <= 0)               { exit();                   }
    else if (input.equals("1"))            { state = SET_NAME;         }
    else if (input.equals("2"))            { state = SET_LNAME;        }
    else if (input.equals("3"))            { state = SET_GDESC;        }
    else if (input.equals("4"))            { writeLookDesc();          }
    else if (input.equals("5"))            { writeExactLookDesc();     }
    else if (input.equals("6"))            { state = SET_LEVEL;        }
    else if (input.equals("9"))            { state = MENU_STATS;       }
    else if (input.equals("8"))            { state = SET_WORTH;        }
    else if (input.equals("7"))            { state = SET_FLAG;         }
    else if (input.equalsIgnoreCase("A"))  { state = SET_TYPE;         }
    else if (input.equalsIgnoreCase("B"))  { state = SET_CLASS;        }
    else if (input.equalsIgnoreCase("C"))  { state = SET_PLACE;        }
    else if (input.equalsIgnoreCase("D"))  { state = SET_DTYPE;        }
    else if (input.equalsIgnoreCase("E"))  { state = MENU_BONUSES;     }
    else if (input.equalsIgnoreCase("G"))  { state = SET_BOARD;        }
    else if (input.equalsIgnoreCase("N"))  { state = MENU_CONTAINED;   }
    else if (input.equalsIgnoreCase("I"))  { state = MENU_BLOCKER;     }
    else if (input.equalsIgnoreCase("K"))  { state = MENU_LIQUID;      }
    else if (input.equalsIgnoreCase("F"))  { state = SET_CONTAINERMAX; }
    else if (input.equalsIgnoreCase("O"))  { state = SET_DECAY_TIME;   }
    else if (input.equalsIgnoreCase("R"))  { state = MENU_CHARGED;     }
    else if (input.equalsIgnoreCase("P"))  { state = MENU_INNATE;      }
    else if (input.equalsIgnoreCase("M"))  { state = MENU_ACTION;      }
    else if (input.equalsIgnoreCase("X"))  { tryGlobalDelete();        }
    else if (input.equalsIgnoreCase("Q"))  { exit();                   }

    boolean equipment = (ITEM.findType(Item.ITEM_EQUIPMENT) != -1);
    boolean weapon    = ((equipment) && (ITEM.getPlaces().indexOf("Weapon") != -1));
    boolean container = (ITEM.findType(Item.ITEM_CONTAINER) != -1);
    boolean board     = (ITEM.findType(Item.ITEM_BOARD) != -1);
    boolean blocker   = (ITEM.findType(Item.ITEM_BLOCKER) != -1);
    boolean decay     = (!ITEM.getFlag(Item.FLAG_NO_DECAY));
    boolean liquid    = (ITEM.findType(Item.ITEM_DRINKCONTAINER) != -1);
    boolean fountain  = (ITEM.findType(Item.ITEM_FOUNTAIN) != -1);
    boolean hasLiquid = (liquid || fountain);

    if ((state == MENU_CHARGED) && (!equipment))     { state = MAIN_MENU; errorMsg("Item is not of type equipment.");  }
    if ((state == MENU_INNATE) && (!equipment))      { state = MAIN_MENU; errorMsg("Item is not of type equipment.");  }
    if ((state == MENU_STATS) && (!equipment))       { state = MAIN_MENU; errorMsg("Item is not of type equipment.");  }
    if ((state == MENU_BONUSES) && (!equipment))     { state = MAIN_MENU; errorMsg("Item is not of type equipment.");  }
    if ((state == SET_CLASS) && (!equipment))        { state = MAIN_MENU; errorMsg("Item is not of type equipment.");  }
    if ((state == SET_PLACE) && (!equipment))        { state = MAIN_MENU; errorMsg("Item is not of type equipment.");  }
    if ((state == MENU_CONTAINED) && (!container))   { state = MAIN_MENU; errorMsg("Item is not a container.");        }
    if ((state == SET_CONTAINERMAX) && (!container)) { state = MAIN_MENU; errorMsg("Item is not a container.");        }
    if ((state == SET_DTYPE) && (!weapon))           { state = MAIN_MENU; errorMsg("Item is not a weapon.");           }
    if ((state == SET_BOARD) && (!board))            { state = MAIN_MENU; errorMsg("Item is not a board.");            }
    if ((state == MENU_BLOCKER) && (!blocker))       { state = MAIN_MENU; errorMsg("Item is not a blocker.");          }
    if ((state == MENU_LIQUID) && (!hasLiquid))      { state = MAIN_MENU; errorMsg("Item does not contain liquid.");   }
    if ((state == SET_DECAY_TIME) && (!decay))       { state = MAIN_MENU; errorMsg("Item is flagged as NO_DECAY.");    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processDecayTime(String input)

  {
    if (input.length() == 0) {
      errorMsg("Set Decay Time cancelled.");
      state = MAIN_MENU;
      return; }

    try

    {
      int time = Integer.parseInt(input);
      if (time <= 0) throw new Exception("The decay time must be greater than 0.");

      ITEM.setDecayTime(time);
      goodMsg("Decay time set to: " + time + "#g.");
    }

    catch (NumberFormatException e) { errorMsg("Invalid number."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processLiquidMenu(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1")) { state = SET_LIQUID_TYPE; }
    if (input.equals("2")) { state = SET_LIQUID_USES; }
    if (input.equals("3")) { state = SET_LIQUID_MAX;  }
    if (input.equals("4")) { state = MAIN_MENU;       }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processLiquidType(String input)

  {
    if (input.length() == 0) {
      errorMsg("Liquid type selection cancelled.");
      state = MENU_LIQUID;
      return; }

    int liquidID = Liquids.getLiquidID(input);

    if (liquidID == -1) {
      errorMsg("There is no liquid by that name.");
      state = MENU_LIQUID;
      return; }

    ITEM.setLiquidType(liquidID);
    goodMsg("Liquid type set to: " + Liquids.getLiquidName(ITEM.getLiquidType()) + "#g.");
    state = MENU_LIQUID;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processLiquidUses(String input)

  {
    if (input.length() == 0) {
      errorMsg("Set Uses cancelled.");
      state = MENU_LIQUID;
      return; }

    try

    {
      int uses = Integer.parseInt(input);
      if (uses < 0) throw new Exception("The number of uses must be zero or greater.");
      if (uses > ITEM.getLiquidMax())
        throw new Exception("The number of uses must be equal to or less than the maximum uses.");

      ITEM.setLiquidUses(uses);
      goodMsg("Liquid amount set to: " + uses + "#g.");
    }

    catch (NumberFormatException e) { errorMsg("Invalid amount of uses."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_LIQUID;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processLiquidMax(String input)

  {
    if (input.length() == 0) {
      errorMsg("Set Max Uses cancelled.");
      state = MENU_LIQUID;
      return; }

    try

    {
      int max = Integer.parseInt(input);
      if (max < 0) throw new Exception("The maximum number of uses must be zero or greater.");

      ITEM.setLiquidMax(max);
      goodMsg("Maximum liquid amount set to: " + max + "#g.");

      if (ITEM.getLiquidUses() > max) ITEM.setLiquidUses(max);
    }

    catch (NumberFormatException e) { errorMsg("Invalid amount of maximum uses."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_LIQUID;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processChangeLevel(String input)

  {
    if (input.length() == 0) {
      errorMsg("Set Level cancelled.");
      state = MAIN_MENU;
      return; }

    try

    {
      int level = Integer.parseInt(input);
      if (level > builderLevel) throw new Exception();
      if (level < 1) throw new Exception();

      ITEM.setLevel(level);
      goodMsg("Level set to: " + level + "#g.");
    }

    catch (Exception e) { errorMsg("The level must be a number between 1 and " + builderLevel + "."); }

    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processChangeWorth(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      errorMsg("Set Worth cancelled.");
      return; }

    try

    {
      int worth = Integer.parseInt(input);
      if (worth < 0) throw new Exception();
      if (worth > 1000000) throw new Exception();

      ITEM.setWorth(worth);
      goodMsg("Worth set to: " + worth + "#g.");
    }

    catch (Exception e) { errorMsg("The worth must be a number between 0 and 1000000."); }

    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void writeLookDesc()

  {
    Writable w = ITEM.getLDescWrit();
    w.setFormat(Writable.FORMAT_IDESC);
    Composer newComposer = new Composer(myClient.getCharInfo(), w);
    newComposer.setLineWidth(ITEM_FORMATTED_WIDTH);
    newComposer.displayInfo();
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void writeExactLookDesc()

  {
    Writable w = ITEM.getLDescWrit();
    w.setFormat(Writable.FORMAT_EXACT);
    Composer newComposer = new Composer(myClient.getCharInfo(), w);
    newComposer.setLineWidth(ITEM_EXACT_WIDTH);
    newComposer.displayInfo();
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processGroundDesc(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      errorMsg("Set ground description cancelled.");
      return; }

    if (visibleSize(input) > ITEM_GDESC_LENGTH) {
      state = MAIN_MENU;
      errorMsg("Ground description exceeds maximum length.");
      return; }

    ITEM.setGDesc(input);
    goodMsg("Ground description set to: " + input + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processNameChange(String input)

  {
    if (input.length() == 0) {
      errorMsg("Set Name cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > ITEM_NAME_LENGTH) {
      errorMsg("Item name exceeds maximum length.");
      state = MAIN_MENU;
      return; }

    ITEM.setName(input);
    goodMsg("Name set to: " + input + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processLNameChange(String input)

  {
    if (input.length() == 0) {
      errorMsg("Set lowercase name cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > ITEM_NAME_LENGTH) {
      errorMsg("Item name exceeds maximum length.");
      state = MAIN_MENU;
      return; }

    ITEM.setLName(input);
    goodMsg("Lowercase name set to: " + input + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processBlockerMenu(String input)

  {
    if (input.equals("")) {
      state = MAIN_MENU;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Selection.");
      state = MENU_BLOCKER;
      return; }

    int selection;

    try { selection = Integer.parseInt(input); }
    catch (Exception e) { errorMsg("Invalid Selection.");  return; }

    if (selection == 1)  { state = SET_BLOCKER_TYPE;  return; }
    if (selection == 2)  { state = SET_BLOCKER_DIR;   return; }
    if (selection == 3)  { state = MAIN_MENU;         return; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processBlockerDir(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Set Blocker Direction cancelled.");
      state = MENU_BLOCKER;
      return; }

    int choice = -1;
    String cmd = input.toLowerCase();

    if (match(cmd, "n", "north"))  choice = DIRECTION_N;
    if (match(cmd, "s", "south"))  choice = DIRECTION_S;
    if (match(cmd, "e", "east"))   choice = DIRECTION_E;
    if (match(cmd, "w", "west"))   choice = DIRECTION_W;
    if (match(cmd, "u", "up"))     choice = DIRECTION_U;
    if (match(cmd, "d", "down"))   choice = DIRECTION_D;

    if (choice == -1) {
      errorMsg("Invalid direction specified.");
      state = SET_BLOCKER_DIR;
      return; }

    ITEM.setBlockerDir(choice);
    goodMsg("Blocker direction set.");
    state = MENU_BLOCKER;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processBlockerType(String input)

  {
    if (input.equals("")) {
      errorMsg("Set Blocker Type cancelled.");
      state = MENU_BLOCKER;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Number.");
      state = SET_BLOCKER_TYPE;
      return; }

    int selection;

    try { selection = Integer.parseInt(input); }
    catch (Exception e) { errorMsg("Invalid Number.");  return; }

    if (selection < 1) { errorMsg("Invalid Selection.");  return; }
    if (selection > 6) { errorMsg("Invalid Selection.");  return; }

    if (selection == 1)

    {
      ITEM.setBlockerType(Blocker.BLOCKER_NORMAL);
      goodMsg("Normal Blocker type set to block all.");
      state = MENU_BLOCKER;
    }

    else if (selection == 2) { state = SET_BLOCKER_IMMORTAL; }
    else if (selection == 3) { state = SET_BLOCKER_LEVEL;    }
    else if (selection == 4) { state = SET_BLOCKER_CLASS;    }
    else if (selection == 5) { state = SET_BLOCKER_CLAN;     }
    else if (selection == 6) { state = SET_BLOCKER_RELIGION; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processBlockerImmortal(String input)

  {
    if (input.equals("")) {
      errorMsg("Set Blocker Type cancelled.");
      state = SET_BLOCKER_TYPE;
      return; }

    int num;
    try { num = Integer.parseInt(input); }
    catch (Exception e) { return; }
    if (num < 100) return;
    if (num > 110) return;

    ITEM.setBlockerType(Blocker.BLOCKER_IMMORTAL);
    ITEM.setBlockerParam(input);
    goodMsg("Immortal Blocker set, minimum level " + input + ".");
    state = MENU_BLOCKER;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processBlockerLevel(String input)

  {
    if (input.equals("")) {
      errorMsg("Set Blocker Type cancelled.");
      state = SET_BLOCKER_TYPE;
      return; }

    int num;
    try { num = Integer.parseInt(input); }
    catch (Exception e) { return; }
    if (num < 1) return;
    if (num > 99) return;

    ITEM.setBlockerType(Blocker.BLOCKER_LEVEL);
    ITEM.setBlockerParam(input);
    goodMsg("Level Blocker set, minimum level " + input + ".");
    state = MENU_BLOCKER;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processBlockerClass(String input)

  {
    if (input.equals("")) {
      errorMsg("Set Blocker Type cancelled.");
      state = SET_BLOCKER_TYPE;
      return; }

    ITEM.setBlockerType(Blocker.BLOCKER_CLASS);
    ITEM.setBlockerParam(input);
    goodMsg("Class blocker parameters set.");
    state = MENU_BLOCKER;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processBlockerClan(String input)

  {
    if (input.equals("")) {
      errorMsg("Set Blocker Type cancelled.");
      state = SET_BLOCKER_TYPE;
      return; }

    try

    {
      Clan c = World.getClan(input);

      if (c == null) {
        errorMsg("No such clan exists.");
        state = SET_BLOCKER_TYPE;
        return; }

      ITEM.setBlockerType(Blocker.BLOCKER_CLAN);
      ITEM.setBlockerParam(input);
      goodMsg("Clan Blocker set for " + c.getName() + "#g.");
      state = MENU_BLOCKER;
    }

    catch (Exception e) { errorMsg("Invalid Clan."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processBlockerReligion(String input)

  {
    if (input.equals("")) {
      errorMsg("Set Blocker Type cancelled.");
      state = SET_BLOCKER_TYPE;
      return; }

    try

    {
      Religion r = World.getReligion(input);

      if (r == null) {
        errorMsg("No such religion exists.");
        state = SET_BLOCKER_TYPE;
        return; }

      ITEM.setBlockerType(Blocker.BLOCKER_RELIGION);
      ITEM.setBlockerParam(input);
      goodMsg("Religion Blocker set for " + r.getName() + "#g.");
      state = MENU_BLOCKER;
    }

    catch (Exception e) { errorMsg("Invalid Religion."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getFlagList()

  {
    ArrayList menu = new ArrayList();

    String temp = "";
    Flag[] flagList = ITEM.getFlags();
    int numFlags = flagList.length;
    int rowCount = 0;

    menu.add("center");
    menu.add("#nFlags#N");
    menu.add("-");

    for (int i=0; i<numFlags; i++)

    {
      rowCount++;

      temp += getBooleanColor(flagList[i].isEnabled());
      temp += String.format("%1$-13s ", flagList[i].getName());

      if (rowCount == 4)

      {
        rowCount = 0;
        menu.add(temp.trim());
        temp = "";
      }
    }

    if (temp.length() > 0) menu.add(temp.trim());

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nEnter Name Of Flag To Toggle: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getItemMenu()

  {
    String exact = "Off";
    if (ITEM.getLDescWrit().getFormat() == Writable.FORMAT_EXACT) exact = "On";

    boolean equipment = (ITEM.findType(Item.ITEM_EQUIPMENT) != -1);
    boolean weapon    = ((equipment) && (ITEM.getPlaces().indexOf("Weapon") != -1));
    boolean container = (ITEM.findType(Item.ITEM_CONTAINER) != -1);
    boolean board     = (ITEM.findType(Item.ITEM_BOARD) != -1);
    boolean blocker   = (ITEM.findType(Item.ITEM_BLOCKER) != -1);
    boolean decay     = (!ITEM.getFlag(Item.FLAG_NO_DECAY));
    boolean liquid    = (ITEM.findType(Item.ITEM_DRINKCONTAINER) != -1);
    boolean fountain  = (ITEM.findType(Item.ITEM_FOUNTAIN) != -1);
    boolean hasLiquid = (liquid || fountain);

    String N_Color = condColor("#c",container);
    String O_Color = condColor("#c",decay);
    String P_Color = condColor("#c",equipment);
    String R_Color = condColor("#c",equipment);
    String K_Color = condColor("#c",hasLiquid);
    String X_Color = condColor("#b", PROTOTYPE_ITEM);

    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#yItem Editor#N");
    menu.add("-");
    menu.add("Name:   " + ITEM.getName());
    menu.add("LName:  " + ITEM.getLName());
    menu.add("ItemID: " + ITEM.getID());
    menu.add("Type:   " + getTypeString());
    menu.add("Exact:  " + exact);
    menu.add("Level:  " + ITEM.getLevel());
    menu.add("Worth:  " + ITEM.getWorth());
    menu.add("Flags:  " + Flag.flagCount(ITEM.getFlags()));
    menu.add("-");
    menu.add("#g1.#N) Change Uppercase Name.          #cA.#N) Change Item Type.");
    menu.add("#g2.#N) Change Lowercase Name.          " + condColor("#c",equipment) + "B.#N) Set Item Classes.");
    menu.add("#g3.#N) Change Ground Description.      " + condColor("#c",equipment) + "C.#N) Set Item Places.");
    menu.add("#g4.#N) Write Look Description.         " + condColor("#c",weapon)    + "D.#N) Set Damage Type.");
    menu.add("#g5.#N) Write Exact Look Description.   " + condColor("#c",equipment) + "E.#N) Set Stat Bonuses.");
    menu.add("#g6.#N) Modify Item Level.              " + condColor("#c",container) + "F.#N) Set Container Max.");
    menu.add("#g7.#N) Add/Remove Flags.               " + condColor("#c",board)     + "G.#N) Set Board Name.");
    menu.add("#g8.#N) Modify Worth.                   " + condColor("#c",blocker)   + "I.#N) Set Blocker Type.");
    menu.add(condColor("#g",equipment) + "9.#N) Modify Stat Requirements.       #NJ.#N) Set Light Ticks.");
    menu.add("                                    " + K_Color + "K.#N) Modify Liquid Info.");
    menu.add(N_Color + "N.#N) Add Contained Item.");
    menu.add(O_Color + "O.#N) Set Decay Time.");
    menu.add(P_Color + "P.#N) Modify Innate Effects.          #yH.#N) Help!");
    menu.add(R_Color + "R.#N) Modify Charged Spells.          " + X_Color + "X.#N) Delete Item.");
    menu.add("#cM.#N) Add/Remove Action.              #rQ.#N) Save and Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getActionMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#n                 Actions                 #N");
    menu.add("-");

    menu.add("        ( #r1#N) Equip    ( #r7#N) Drink");
    menu.add("        ( #r2#N) Unequip  ( #r8#N) Eat");
    menu.add("        ( #r3#N) Drop     ( #r9#N) Empty");
    menu.add("        ( #r4#N) Get      (#r10#N) Fill");
    menu.add("        ( #r5#N) Put      (#r11#N) Close");
    menu.add("        ( #r6#N) Give     (#r12#N) Open");

    menu.add("-");
    menu.add("#N[#c1#N] Edit Action.");
    menu.add("#N[#c2#N] Copy Action.");
    menu.add("#N[#c3#N] Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getDTypeMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#n               Damage Type               #N");
    menu.add("-");
    menu.add("#nCurrent Damage Type:  #N" + ITEM.getDType());
    menu.add("-");
    menu.add("#n[#c 1#n]#N Stab");
    menu.add("#n[#c 2#n]#N Slash");
    menu.add("#n[#c 3#n]#N Pierce");
    menu.add("#n[#c 4#n]#N Bludgeon");
    menu.add("#n[#c 5#n]#N Chop");
    menu.add("#n[#c 6#n]#N Whip");
    menu.add("#n[#c 7#n]#N Arrow");
    menu.add("#n[#c 8#n]#N Star");
    menu.add("#n[#c 9#n]#N Fist");
    menu.add("#n[#c10#n]#N Thrust");
    menu.add("#n[#c11#n]#N Gouge");
    menu.add("#n[#c12#n]#N Blast");
    menu.add("#n[#c13#n]#N Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getBonusesMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#n              Stat Bonuses               #N");
    menu.add("-");
    menu.add("#n[#c1#n]#N Hitroll     #R:#N  " + ITEM.getHR());
    menu.add("#n[#c2#n]#N Damroll     #R:#N  " + ITEM.getDR());
    menu.add("#n[#c3#n]#N Resistance  #R:#N  " + ITEM.getMR());
    menu.add("#n[#c4#n]#N Armorclass  #R:#N  " + ITEM.getAC());
    menu.add("#n[#c5#n]#N Hitpoints   #R:#N  " + ITEM.getHP());
    menu.add("#n[#c6#n]#N Manapoints  #R:#N  " + ITEM.getMN());
    menu.add("#n[#c7#n]#N Movepoints  #R:#N  " + ITEM.getMV());
    menu.add("#n[#c8#n]#N Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getStatMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#n               Stat Requirements               ");
    menu.add("-");
    menu.add("#n[#c1#n]#N STR  #R:#N  " + ITEM.getSTR());
    menu.add("#n[#c2#n]#N DEX  #R:#N  " + ITEM.getDEX());
    menu.add("#n[#c3#n]#N CON  #R:#N  " + ITEM.getCON());
    menu.add("#n[#c4#n]#N INT  #R:#N  " + ITEM.getINT());
    menu.add("#n[#c5#n]#N WIS  #R:#N  " + ITEM.getWIS());
    menu.add(" ");
    menu.add("#n[#c6#n]#N Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getContainedMenu()

  {
    ArrayList ilist = ITEM.getLoadList();
    int size = ilist.size();

    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("               #nContained Items#N               ");
    menu.add("-");

    if (size == 0) menu.add("#gThere are no contained items.");

    else for (int i=0; i<size; i++)

    {
      int num = ((Integer)ilist.get(i)).intValue();
      Item theItem = World.getItem(num);
      String temp = "";
      temp += "[#r" + num + "#N] ";
      temp += theItem.getName() + "#N";
      menu.add(temp);
    }

    menu.add("-");
    menu.add("[#c1#N] Add Contained Item.");
    menu.add("[#c2#N] Remove Contained Item.");
    menu.add("[#c3#N] Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getBoardList()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#n               Board Link               #N");
    menu.add("-");

    if (ITEM.getBoard() != null) menu.add("#nCurrent Board:#N  " + ITEM.getBoard().getBoardName());
    else menu.add("#nCurrent Board: #NNone");
    menu.add("-");

    for (int i=0; i<World.MAX_BOARDS; i++)
    if (World.getBoard(i) != null)
      menu.add("[#r" + i + "#N] " + World.getBoard(i).getBoardName());

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nEnter Board ID: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getTypeMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#n               Item Type#N               ");
    menu.add("-");
    menu.add("#nCurrent Type:  #N" + getTypeString());
    menu.add("-");
    menu.add("[#c 1#N] Normal");
    menu.add("[#c 2#N] Container");
    menu.add("[#c 3#N] Board");
    menu.add("[#c 4#N] Fountain");
    menu.add("[#c 5#N] Blocker");
    menu.add("[#c 6#N] Gold");
    menu.add("[#c 7#N] Scroll");
    menu.add("[#c 8#N] Food");
    menu.add("[#c 9#N] Liquid Container");
    menu.add("[#c10#N] Equipment");
    menu.add("[#c11#N] Equipable Container");
    menu.add("[#c12#N] Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getPlacesMenu()

  {
    ArrayList menu = new ArrayList();
    String places = ITEM.getPlaces();
    String temp   = "";

    menu.add("center");
    menu.add("#nPlaces#N");
    menu.add("-");

    if (places.indexOf("Weapon") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Weapon");
    if (places.indexOf("Hold") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Hold");
    if (places.indexOf("Body") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Body");
    if (places.indexOf("Head") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Head");

    menu.add(temp);
    temp = "";

    if (places.indexOf("Feet") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Feet");
    if (places.indexOf("Back") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Back");
    if (places.indexOf("Hands") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Hands");
    if (places.indexOf("Waist") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Waist");

    menu.add(temp);
    temp = "";

    if (places.indexOf("Legs") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Legs");
    if (places.indexOf("Right_Ear") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Right_Ear");
    if (places.indexOf("Left_Ear") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Left_Ear");
    if (places.indexOf("Right_Wrist") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Right_Wrist");

    menu.add(temp);
    temp = "";

    if (places.indexOf("Left_Wrist") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Left_Wrist");
    if (places.indexOf("Right_Finger") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Right_Finger");
    if (places.indexOf("Left_Finger") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Left_Finger");
    if (places.indexOf("Neck") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Neck");

    menu.add(temp);
    temp = "";

    if (places.indexOf("Either_Ear") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Either_Ear");
    if (places.indexOf("Either_Wrist") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Either_Wrist");
    if (places.indexOf("Either_Finger") != -1) temp += "#g"; else temp += "#r";
    temp += String.format("%1$-13s ", "Either_Finger");

    menu.add(temp);

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nEnter Name of Place to Toggle: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getClassesMenu()

  {
    ArrayList menu = new ArrayList();
    String classes = ITEM.getClasses();
    String temp = "";
    boolean ALL = false;

    menu.add("center");
    menu.add("#nClasses#N");
    menu.add("-");
    menu.add("center");

    if (classes.indexOf("All") != -1) { temp += "#g"; ALL = true; }
    else temp += "#r";

    menu.add(temp + "All");
    temp = "";

    if (classes.indexOf("Wa") != -1) temp += "#g";
    else temp += "#r"; temp += "Wa ";
    if (classes.indexOf("Kn") != -1) temp +="#g";
    else temp +="#r"; temp += "Kn ";
    if (classes.indexOf("Br") != -1) temp +="#g";
    else temp +="#r"; temp += "Br ";
    if (classes.indexOf("Pa") != -1) temp +="#g";
    else temp +="#r"; temp += "Pa ";
    if (classes.indexOf("Cr") != -1) temp +="#g";
    else temp +="#r"; temp += "Cr ";
    if (classes.indexOf("Dk") != -1) temp +="#g";
    else temp +="#r"; temp += "Dk ";
    if (classes.indexOf("Ap") != -1) temp +="#g";
    else temp +="#r"; temp += "Ap";

    menu.add(temp);
    temp = "";

    if (classes.indexOf("Th") != -1) temp +="#g";
    else temp +="#r"; temp += "Th ";
    if (classes.indexOf("As") != -1) temp +="#g";
    else temp +="#r"; temp += "As ";
    if (classes.indexOf("Rg") != -1) temp +="#g";
    else temp +="#r"; temp += "Rg ";
    if (classes.indexOf("Ra") != -1) temp +="#g";
    else temp +="#r"; temp += "Ra ";
    if (classes.indexOf("Me") != -1) temp +="#g";
    else temp +="#r"; temp += "Me ";
    if (classes.indexOf("Sb") != -1) temp +="#g";
    else temp +="#r"; temp += "Sb ";
    if (classes.indexOf("Mc") != -1) temp +="#g";
    else temp +="#r"; temp += "Mc";

    menu.add(temp);
    temp = "";

    if (classes.indexOf("Cl") != -1) temp +="#g";
    else temp +="#r"; temp += "Cl ";
    if (classes.indexOf("Dr") != -1) temp +="#g";
    else temp +="#r"; temp += "Dr ";
    if (classes.indexOf("Mo") != -1) temp +="#g";
    else temp +="#r"; temp += "Mo ";
    if (classes.indexOf("Pr") != -1) temp +="#g";
    else temp +="#r"; temp += "Pr ";
    if (classes.indexOf("Hl") != -1) temp +="#g";
    else temp +="#r"; temp += "Hl ";
    if (classes.indexOf("Dc") != -1) temp +="#g";
    else temp +="#r"; temp += "Dc ";
    if (!ALL)
    if (classes.indexOf("Al") != -1) temp +="#g";
    else temp +="#r"; temp += "Al";

    menu.add(temp);
    temp = "";

    if (classes.indexOf("Mg") != -1) temp +="#g";
    else temp +="#r"; temp += "Mg ";
    if (classes.indexOf("Wi") != -1) temp +="#g";
    else temp +="#r"; temp += "Wi ";
    if (classes.indexOf("Il") != -1) temp +="#g";
    else temp +="#r"; temp += "Il ";
    if (classes.indexOf("Sc") != -1) temp +="#g";
    else temp +="#r"; temp += "Sc ";
    if (classes.indexOf("Sm") != -1) temp +="#g";
    else temp +="#r"; temp += "Sm ";
    if (classes.indexOf("Wl") != -1) temp +="#g";
    else temp +="#r"; temp += "Wl ";
    if (classes.indexOf("Sh") != -1) temp +="#g";
    else temp +="#r"; temp += "Sh";

    menu.add(temp);
    temp = "";

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nEnter Name of Class to Toggle: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getBlockerMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("          #nItem Blocker Settings:          ");
    menu.add("-");

    menu.add("#n[#c1#n]#N Set Blocker Type.");
    menu.add("#n[#c2#n]#N Set Blocker Direction.");
    menu.add("#n[#c3#n]#N Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getBlockerDirMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("           #nSet Blocker Direction:           ");
    menu.add("-");

    menu.add("#n(#cN#n)#Corth");
    menu.add("#n(#cS#n)#Couth");
    menu.add("#n(#cE#n)#Ceast");
    menu.add("#n(#cW#n)#Cest");
    menu.add("#n(#cU#n)#Cp");
    menu.add("#n(#cD#n)#Cown");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getBlockerTypeMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("              #nSet Blocker Type:             ");
    menu.add("-");

    menu.add("#n[#c1#n]#N Normal Block-All.");
    menu.add("#n[#c2#n]#N Immortal Only.");
    menu.add("#n[#c3#n]#N Min-Level Blocker.");
    menu.add("#n[#c4#n]#N Class Blocker.");
    menu.add("#n[#c5#n]#N Clan Blocker.");
    menu.add("#n[#c6#n]#N Religion Blocker.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getLiquidMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("               #nLiquid Information               ");
    menu.add("-");
    menu.add("#NType    : #n" + Liquids.getLiquidName(ITEM.getLiquidType()));
    menu.add("#NUses    : #n" + ITEM.getLiquidUses());
    menu.add("#NMax Uses: #n" + ITEM.getLiquidMax());
    menu.add("-");
    menu.add("#n[#c1#n]#N Set Liquid Type.");
    menu.add("#n[#c2#n]#N Set Uses.");
    menu.add("#n[#c3#n]#N Set Maximum Uses.");
    menu.add("#n[#c4#n]#N Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getBlockerClassInfo()

  {
    String temp = "\r\n"

    + "Enter all professions that may pass.\r\n"
    + "Example:  Assassin Shadowblade Dark Cleric Paladin\r\n\r\n";

    return temp;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String getChargeSpellMenu()

  {
    ArrayList menu = new ArrayList();
    ArrayList levels = ITEM.getUseLevels();
    ArrayList spells = ITEM.getUseSpells();
    int size = levels.size();

    menu.add("center");
    menu.add("                 #nCharged Spells#N                 ");

    if (size > 0) menu.add("-");
    if (size > 0) menu.add("     #nLevel  Name#N");

    menu.add("-");

    if (size == 0) menu.add("#gThere are no charged spells on this item.");

    else for (int i=0; i<size; i++)

    {
      int lvl      = ((Integer)levels.get(i)).intValue();
      String temp  = "";
      String name  = (String)spells.get(i);
      temp += "(#r" + String.format("%1$2s", i+1) + "#N) ";
      temp += "[#g" + String.format("%1$3s", lvl) + "#N] ";
      menu.add(temp + " " + name);
    }

    if (size > 0) menu.add("-");
    if (size > 0) menu.add("Uses:         #n" + ITEM.getNumUses() + "#N");
    if (size > 0) menu.add("Maximum Uses: #n" + ITEM.getMaxUses() + "#N");

    menu.add("-");
    menu.add("#n[#c1#n]#N Add Charged Spell.");
    menu.add("#n[#c2#n]#N Remove Charged Spell.");
    menu.add("#n[#c3#n]#N Set Number of Uses.");
    menu.add("#n[#c4#n]#N Set Maximum Number of Uses.");
    menu.add("#n[#c5#n]#N Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String getInnateMenu()

  {
    ArrayList menu = new ArrayList();
    ArrayList levels = ITEM.getWearLevels();
    ArrayList spells = ITEM.getWearList();
    int size = levels.size();

    menu.add("center");
    menu.add("                 #nInnate Effects#N                 ");

    if (size > 0) menu.add("-");
    if (size > 0) menu.add("     #nLevel  Name#N");

    menu.add("-");

    if (size == 0) menu.add("#gThere are no innate effects on this item.");

    else for (int i=0; i<size; i++)

    {
      int lvl      = ((Integer)levels.get(i)).intValue();
      String temp  = "";
      String name  = (String)spells.get(i);
      temp += "(#r" + String.format("%1$2s", i+1) + "#N) ";
      temp += "[#g" + String.format("%1$3s", lvl) + "#N] ";
      menu.add(temp + name);
    }

    menu.add("-");
    menu.add("#n[#c1#n]#N Add Innate Effect.");
    menu.add("#n[#c2#n]#N Remove Innate Effect.");
    menu.add("#n[#c3#n]#N Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getTypeString()

  {
    String itemType = "None";

    if (ITEM.findType(Item.ITEM_GENERIC) != -1)        itemType = "Normal";
    if (ITEM.findType(Item.ITEM_BOARD) != -1)          itemType = "Board";
    if (ITEM.findType(Item.ITEM_FOUNTAIN) != -1)       itemType = "Fountain";
    if (ITEM.findType(Item.ITEM_BLOCKER) != -1)        itemType = "Blocker";
    if (ITEM.findType(Item.ITEM_FOOD) != -1)           itemType = "Food";
    if (ITEM.findType(Item.ITEM_GOLD) != -1)           itemType = "Gold";
    if (ITEM.findType(Item.ITEM_CORPSE) != -1)         itemType = "Corpse";
    if (ITEM.findType(Item.ITEM_DRINKCONTAINER) != -1) itemType = "Liquid Container";
    if (ITEM.findType(Item.ITEM_SCROLL) != -1)         itemType = "Scroll";
    if (ITEM.findType(Item.ITEM_EQUIPMENT) != -1)      itemType = "Equipment";
    if (ITEM.findType(Item.ITEM_CONTAINER) != -1)      itemType = "Container";
    if (ITEM.findType(Item.ITEM_EQUIPMENT) != -1
      && ITEM.findType(Item.ITEM_CONTAINER) != -1)     itemType = "Equipable Container";

    return itemType;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void setType(String s)

  {
    ITEM.clearTypes();
    ITEM.setBlockerType(-1);
    ITEM.setBlockerDir(-1);
    ITEM.setBlockerParam("");

    if (s.equals("NORMAL"))              ITEM.addType(Item.ITEM_GENERIC);
    else if (s.equals("CONTAINER"))      ITEM.addType(Item.ITEM_CONTAINER);
    else if (s.equals("BOARD"))          ITEM.addType(Item.ITEM_BOARD);
    else if (s.equals("FOUNTAIN"))       ITEM.addType(Item.ITEM_FOUNTAIN);
    else if (s.equals("EQUIPMENT"))      ITEM.addType(Item.ITEM_EQUIPMENT);
    else if (s.equals("GOLD"))           ITEM.addType(Item.ITEM_GOLD);
    else if (s.equals("CORPSE"))         ITEM.addType(Item.ITEM_CORPSE);
    else if (s.equals("CONSUMEFOOD"))    ITEM.addType(Item.ITEM_FOOD);
    else if (s.equals("DRINKCONTAINER")) ITEM.addType(Item.ITEM_DRINKCONTAINER);

    else if (s.equals("EQCONTAINER")) {
      ITEM.addType(Item.ITEM_EQUIPMENT);
      ITEM.addType(Item.ITEM_CONTAINER); }

    else if (s.equals("SCROLL")) {
      ITEM.addType(Item.ITEM_SCROLL);
      ITEM.addType(Item.ITEM_USEABLE); }

    else if (s.equals("BLOCKER")) {
      ITEM.addType(Item.ITEM_BLOCKER);
      ITEM.setBlockerType(Blocker.BLOCKER_NORMAL);
      ITEM.setBlockerDir(DIRECTION_N); }

    else ITEM.addType(Item.ITEM_GENERIC);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static void enterItemBuilder(Client myClient, String input)

  {
    boolean prototype_item = false;
    Entity ENT = myClient.getCharInfoBackup();

    if (ENT.getPlayerState() == PSTATE_FIGHTING) {
      ENT.echo("You can't do that while fighting.");
      return; }

    int iID = -1;
    Item eItem;
    boolean notInteger = false;

    try

    {
      iID = Integer.parseInt(input);
      if (iID < 0) throw new Exception();
      if (iID >= World.MAX_ITEMS) throw new Exception();
      eItem = World.getItem(iID);
      if (eItem == null) throw new Exception();
      prototype_item = true;
    }

    catch (NumberFormatException nfe)

    {
      ArrayList inv = ENT.getInventory().getList();

      ArrayList newList = new ArrayList(inv);
      newList = ItemUse.parseListQuery(newList, input);

      if (newList.isEmpty()) {
        ENT.echo("Item not found.");
        return; }

      if (newList.size() > 1) {
        ENT.echo("Item not found.");
        return; }

      eItem = (Item)newList.get(0);
    }

    catch (Exception e)

    {
      myClient.getCharInfo().echo("Item not found.");
      return;
    }

    boolean inInventory = ENT.getInventory().contains(eItem);

    if (!inInventory)

    {
      int inum = eItem.getZoneID();
      Zone Z = World.getZone(inum);

      if (Z.getBusy()) {
        ENT.echo(Z.getName() + " is busy.");
        return; }

      if ((ENT.getLevel() < 110) && (!ENT.getName().equalsIgnoreCase(Z.getOwner()))) {
        ENT.echo("You are not the owner of " + Z.getName() + "#N.");
        return; }

      Z.setBusy(true);
    }

    BuilderItem BI = new BuilderItem(myClient, eItem, prototype_item);
    if (inInventory) BI.setInventory();
    BI.setEditStamp(SystemTime.getItemTimeStamp());
    BI.setMarkStamp("Edited by " + ENT.getName() + ".");
    myClient.pushInterface(BI);
    myClient.getCharInfo().blindEmote("starts editing an item.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void exit()

  {
    if (!PROTOTYPE_ITEM) ITEM.setEditStamp(editStamp);
    if (!PROTOTYPE_ITEM) ITEM.setMarkStamp(markStamp);

    myClient.popInterface();
    myClient.setClientState(CSTATE_NORMAL);
    myClient.getCharInfo().blindEmote("has finished editing an item.");

    if (GLOBAL_DELETE)

    {
      World.globalItemDelete(ITEM.getID());
      World.save();
      myClient.getCharInfo().look("#rItem " + ITEM.getID() + " globally deleted.#N");
    }

    else if (!inInventory)

    {
      int inum = ITEM.getZoneID();
      Zone Z = World.getZone(inum);

      myClient.getCharInfo().look(Z.getName() + " saved.");
      WriteThread.addObject(Z);
    }

    else

    {
      myClient.getCharInfo().castChar().save();
      myClient.getCharInfo().look(myClient.getCharInfo().getName() + " saved.");
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}