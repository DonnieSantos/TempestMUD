import java.io.*;
import java.util.*;

public class BuilderMob extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int MAIN_MENU            =  0;
  public static final int SET_NAME             =  1;
  public static final int SET_KEYWORDS         =  2;
  public static final int SET_TITLE            =  3;
  public static final int SET_FLAGS            =  4;
  public static final int SET_GENDER           =  5;
  public static final int SET_GOLD             = 20;
  public static final int SET_EXP              = 21;
  public static final int SET_ALIGN            = 25;
  public static final int SET_SPEED            = 26;
  public static final int SET_SHOP             = 27;

  public static final int MENU_STATS           =  6;
  public static final int SET_STR              =  7;
  public static final int SET_DEX              =  8;
  public static final int SET_CON              =  9;
  public static final int SET_INT              = 10;
  public static final int SET_WIS              = 11;
  public static final int SET_HR               = 12;
  public static final int SET_DR               = 13;
  public static final int SET_AC               = 14;
  public static final int SET_MR               = 15;
  public static final int SET_HP               = 16;
  public static final int SET_MN               = 17;
  public static final int SET_MV               = 18;
  public static final int SET_LVL              = 19;

  public static final int MENU_SELL            = 22;
  public static final int ADD_SELL             = 23;
  public static final int REM_SELL             = 24;

  public static final int MENU_LOAD            = 28;
  public static final int ADD_LOAD             = 29;
  public static final int ADD_LOADFREQ         = 30;
  public static final int REM_LOAD             = 31;

  public static final int MENU_WEAR            = 32;
  public static final int ADD_WEAR             = 33;
  public static final int ADD_WEARFREQ         = 34;
  public static final int REM_WEAR             = 35;

  public static final int MENU_ACTION          = 36;
  public static final int ADD_ACTION           = 37;
  public static final int REM_ACTION           = 38;
  public static final int COPY_AGGRO           = 56;

  public static final int MENU_BLOCKER         = 39;
  public static final int SET_BLOCKER_DIR      = 40;
  public static final int SET_BLOCKER_TYPE     = 41;
  public static final int SET_BLOCKER_IMMORTAL = 42;
  public static final int SET_BLOCKER_LEVEL    = 43;
  public static final int SET_BLOCKER_CLASS    = 44;
  public static final int SET_BLOCKER_CLAN     = 45;
  public static final int SET_BLOCKER_RELIGION = 46;

  public static final int MENU_SKILL           = 47;
  public static final int ADD_SKILL            = 48;
  public static final int ADD_SKILL_LEVEL      = 49;
  public static final int REM_SKILL            = 50;

  public static final int MENU_SPELL           = 51;
  public static final int ADD_SPELL            = 52;
  public static final int ADD_SPELL_LEVEL      = 53;
  public static final int REM_SPELL            = 54;

  public static final int DELETE_MOB_GLOBAL    = 55;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private int state;
  private int intHolder;
  private Mobile MOBILE;
  private boolean inRoom;
  private boolean MOBILE_PROTOTYPE;
  private boolean GLOBAL_DELETE;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public int viewMode()                { return NO_OUTPUT;    }
  public void inRoom()                 { inRoom = true;       }
  public void echo(String s)           { utilityQueue.add(s); }
  public void addOutput(String s)      { return;              }
  public void addFightOutput(String s) { return;              }
  public void focusGained()            {                      }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public BuilderMob(Client c, Mobile m, boolean prototype)

  {
    super(c);
    MOBILE = m;
    MOBILE_PROTOTYPE = prototype;
    inRoom = false;
    state = MAIN_MENU;
    MOBILE.setClient(c);
    GLOBAL_DELETE = false;
    c.setClientState(CSTATE_BUILDING);
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
    active = false;
    menuMsg = "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    myClient.popInterface();
    if (inRoom) return;

    int znum = MOBILE.castMob().getZoneID();
    WriteThread.addObject(World.getZone(znum));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    if (state == MAIN_MENU)                  return getMobMenu();
    else if (state == SET_FLAGS)             return getFlagMenu();
    else if (state == MENU_STATS)            return getStatMenu();
    else if (state == MENU_SKILL)            return getSkillMenu();
    else if (state == MENU_SPELL)            return getSpellMenu();
    else if (state == MENU_ACTION)           return getActionMenu();
    else if (state == MENU_SELL)             return getSellMenu();
    else if (state == MENU_LOAD)             return getLoadMenu();
    else if (state == MENU_WEAR)             return getWearMenu();
    else if (state == MENU_BLOCKER)          return getBlockerMenu();
    else if (state == SET_BLOCKER_DIR)       return getBlockerDirMenu();
    else if (state == SET_BLOCKER_TYPE)      return getBlockerTypeMenu();

    else if (state == SET_NAME)              return "Enter New Mobile Name: ";
    else if (state == SET_KEYWORDS)          return "Enter New Mobile Keywords: ";
    else if (state == SET_TITLE)             return "Enter New Mobile Title: ";
    else if (state == SET_GENDER)            return "Enter Gender #N( #bmale #N/#y female#N ) #R:#N ";
    else if (state == SET_GOLD)              return "Enter Gold: ";
    else if (state == SET_EXP)               return "Enter Experience: ";
    else if (state == SET_ALIGN)             return "Enter Alignment #c(#Nneutral#c/#Nlight#c/#Ndark#c)#N #R:#N ";
    else if (state == SET_SPEED)             return "Enter Speed (0-100): ";
    else if (state == SET_SHOP)              return "Enter Shop Name: ";

    else if (state == SET_STR)               return "Enter Strength: ";
    else if (state == SET_DEX)               return "Enter Dexterity: ";
    else if (state == SET_CON)               return "Enter Constitution: ";
    else if (state == SET_INT)               return "Enter Intelligence: ";
    else if (state == SET_WIS)               return "Enter Wisdom: ";
    else if (state == SET_HR)                return "Enter Hit Roll: ";
    else if (state == SET_DR)                return "Enter Damage Roll: ";
    else if (state == SET_AC)                return "Enter Armorclass: ";
    else if (state == SET_MR)                return "Enter Magic Resistance: ";
    else if (state == SET_HP)                return "Enter Hitpoints: ";
    else if (state == SET_MN)                return "Enter Mana Points: ";
    else if (state == SET_MV)                return "Enter Movement Points: ";
    else if (state == SET_LVL)               return "Enter Mob Level: ";

    else if (state == ADD_SELL)              return "Enter Item ID To Add: ";
    else if (state == REM_SELL)              return "Enter Item ID To Remove: ";

    else if (state == ADD_LOAD)              return "Enter The Item Number To Add: ";
    else if (state == ADD_LOADFREQ)          return "Enter Pop Rate (0.0 - 100.0): ";
    else if (state == REM_LOAD)              return "Enter Item ID To Remove: ";

    else if (state == ADD_WEAR)              return "Enter The Item Number To Add: ";
    else if (state == ADD_WEARFREQ)          return "Enter Pop Rate (0.0 - 100.0): ";
    else if (state == REM_WEAR)              return "Enter Item ID To Remove: ";

    else if (state == ADD_ACTION)            return "Enter Action ID to Add: ";
    else if (state == REM_ACTION)            return "Enter Action Number to Remove: ";
    else if (state == COPY_AGGRO)            return "Enter Action ID to copy for Aggression: ";

    else if (state == SET_BLOCKER_IMMORTAL)  return "Select Min-Level to pass (100-110): ";
    else if (state == SET_BLOCKER_LEVEL)     return "Select Min-Level to pass (1-99): ";
    else if (state == SET_BLOCKER_CLASS)     return getBlockerClassInfo() + "Classes:  ";
    else if (state == SET_BLOCKER_CLAN)      return "Enter Clan Name: ";
    else if (state == SET_BLOCKER_RELIGION)  return "Enter Religion Name: ";

    else if (state == ADD_SKILL)             return "Enter Skill Name to Add: ";
    else if (state == ADD_SKILL_LEVEL)       return "Enter Skill Level (0-100): ";
    else if (state == REM_SKILL)             return "Enter Skill Number to Remove: ";
    else if (state == ADD_SPELL)             return "Enter Spell Name to Add: ";
    else if (state == ADD_SPELL_LEVEL)       return "Enter Spell Level (0-100): ";
    else if (state == REM_SPELL)             return "Enter Spell Number to Remove: ";

    else if (state == DELETE_MOB_GLOBAL)     return "Type 'delete' if you are sure: ";

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;

    String input = clearWhiteSpace(myClient.getCommand());

    if (state == MAIN_MENU)                  processMobMenu(input);
    else if (state == SET_NAME)              processNameChange(input);
    else if (state == SET_KEYWORDS)          processKeywordsChange(input);
    else if (state == SET_TITLE)             processTitleChange(input);
    else if (state == SET_FLAGS)             processFlags(input);
    else if (state == SET_GENDER)            processGender(input);
    else if (state == SET_GOLD)              processGold(input);
    else if (state == SET_EXP)               processExp(input);
    else if (state == SET_ALIGN)             processAlign(input);
    else if (state == SET_SPEED)             processSpeed(input);
    else if (state == SET_SHOP)              processShop(input);

    else if (state == MENU_STATS)            processStatMenu(input);
    else if (state == SET_STR)               processChangeStat(input);
    else if (state == SET_DEX)               processChangeStat(input);
    else if (state == SET_CON)               processChangeStat(input);
    else if (state == SET_INT)               processChangeStat(input);
    else if (state == SET_WIS)               processChangeStat(input);
    else if (state == SET_HR)                processChangeStat(input);
    else if (state == SET_DR)                processChangeStat(input);
    else if (state == SET_AC)                processChangeStat(input);
    else if (state == SET_MR)                processChangeStat(input);
    else if (state == SET_HP)                processChangeStat(input);
    else if (state == SET_MN)                processChangeStat(input);
    else if (state == SET_MV)                processChangeStat(input);
    else if (state == SET_LVL)               processChangeStat(input);

    else if (state == MENU_SELL)             processSellMenu(input);
    else if (state == ADD_SELL)              processSellAdd(input);
    else if (state == REM_SELL)              processSellRem(input);

    else if (state == MENU_LOAD)             processLoadMenu(input);
    else if (state == ADD_LOAD)              processLoadAdd(input);
    else if (state == ADD_LOADFREQ)          processLoadFreq(input);
    else if (state == REM_LOAD)              processLoadRem(input);

    else if (state == MENU_WEAR)             processWearMenu(input);
    else if (state == ADD_WEAR)              processWearAdd(input);
    else if (state == ADD_WEARFREQ)          processWearFreq(input);
    else if (state == REM_WEAR)              processWearRem(input);

    else if (state == MENU_ACTION)           processMenuAction(input);
    else if (state == ADD_ACTION)            processAddAction(input);
    else if (state == REM_ACTION)            processRemAction(input);
    else if (state == COPY_AGGRO)            processCopyAggro(input);

    else if (state == MENU_BLOCKER)          processBlockerMenu(input);
    else if (state == SET_BLOCKER_DIR)       processBlockerDir(input);
    else if (state == SET_BLOCKER_TYPE)      processBlockerType(input);
    else if (state == SET_BLOCKER_IMMORTAL)  processBlockerImmortal(input);
    else if (state == SET_BLOCKER_LEVEL)     processBlockerLevel(input);
    else if (state == SET_BLOCKER_CLASS)     processBlockerClass(input);
    else if (state == SET_BLOCKER_CLAN)      processBlockerClan(input);
    else if (state == SET_BLOCKER_RELIGION)  processBlockerReligion(input);

    else if (state == MENU_SKILL)            processSkillMenu(input);
    else if (state == ADD_SKILL)             processSkillAdd(input);
    else if (state == ADD_SKILL_LEVEL)       processSkillLevel(input);
    else if (state == REM_SKILL)             processSkillRemove(input);
    else if (state == MENU_SPELL)            processSpellMenu(input);
    else if (state == ADD_SPELL)             processSpellAdd(input);
    else if (state == ADD_SPELL_LEVEL)       processSpellLevel(input);
    else if (state == REM_SPELL)             processSpellRemove(input);

    else if (state == DELETE_MOB_GLOBAL)     processDeleteMob(input);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void tryGlobalDelete()

  {
    if (!MOBILE_PROTOTYPE) {
      errorMsg("You can only delete mobiles from the global list.");
      state = MAIN_MENU;
      return; }

    state = DELETE_MOB_GLOBAL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processDeleteMob(String input)

  {
    if (!input.equalsIgnoreCase("delete")) {
      errorMsg("Mobile deletion cancelled.");
      state = MAIN_MENU;
      return; }

    GLOBAL_DELETE = true;
    exit();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processSpellMenu(String input)

  {
    if (input.length() <= 0) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1"))  { state = ADD_SPELL; }
    if (input.equals("2"))  { state = REM_SPELL; }
    if (input.equals("3"))  { state = MAIN_MENU; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processSpellAdd(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Add Spell cancelled.");
      state = MENU_SPELL;
      return; }

    Spell spell = Spell.createSpell(input, MOBILE, 0);

    if (spell == null) {
      errorMsg("There is no such spell.");
      state = MENU_SPELL;
      return; }

    ArrayList spells = MOBILE.getSpells();

    for (int i=0; i<spells.size(); i++)
    if (spell.getID() == ((Spell)spells.get(i)).getID()) {
      errorMsg("This mobile already knows " + spell.getName() + ".");
      state = MENU_SPELL;
      return; }

    intHolder = spell.getID();
    state = ADD_SPELL_LEVEL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processSpellLevel(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Add Spell cancelled.");
      state = MENU_SPELL;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Level.");
      state = MENU_SPELL;
      return; }

    try

    {
      int level = Integer.parseInt(input);
      if ((level < 0) || (level > 100)) return;

      MOBILE.addSpell(Spell.createSpell(Spell.getSpellName(intHolder), MOBILE, level));
      goodMsg("Spell Added to Mobile.");
      state = MENU_SPELL;
    }

    catch (Exception e) { errorMsg("Invalid Level."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processSpellRemove(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Remove Spell cancelled.");
      state = MENU_SPELL;
      return; }

    try

    {
      int index = Integer.parseInt(input)-1;

      if (index < 0) return;
      if (index >= MOBILE.getSpells().size()) return;

      MOBILE.getSpells().remove(index);
      goodMsg("Spell Removed from Mobile.");
      state = MENU_SPELL;
    }

    catch (Exception e) { errorMsg("Invalid Spell Number."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processSkillMenu(String input)

  {
    if (input.length() <= 0) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1"))  { state = ADD_SKILL; }
    if (input.equals("2"))  { state = REM_SKILL; }
    if (input.equals("3"))  { state = MAIN_MENU; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processSkillAdd(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Add Skill cancelled.");
      state = MENU_SKILL;
      return; }

    Skill skill = Skill.createSkill(input, MOBILE, 0);

    if (skill == null) {
      errorMsg("There is no such skill.");
      state = MENU_SKILL;
      return; }

    ArrayList skills = MOBILE.getSkills();

    for (int i=0; i<skills.size(); i++)
    if (skill.getID() == ((Skill)skills.get(i)).getID()) {
      errorMsg("This mobile already knows " + skill.getName() + ".");
      state = MENU_SKILL;
      return; }

    intHolder = skill.getID();
    state = ADD_SKILL_LEVEL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processSkillLevel(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Add Skill cancelled.");
      state = MENU_SKILL;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Level.");
      state = MENU_SKILL;
      return; }

    try

    {
      int level = Integer.parseInt(input);
      if ((level < 0) || (level > 100)) return;

      MOBILE.addSkill(Skill.createSkill(Skill.getSkillName(intHolder), MOBILE, level));
      goodMsg("Skill Added to Mobile.");
      state = MENU_SKILL;
    }

    catch (Exception e) { errorMsg("Invalid Level."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processSkillRemove(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Remove Skill cancelled.");
      state = MENU_SKILL;
      return; }

    try

    {
      int index = Integer.parseInt(input)-1;

      if (index < 0) return;
      if (index >= MOBILE.getSkills().size()) return;

      MOBILE.getSkills().remove(index);
      goodMsg("Skill Removed from Mobile.");
      state = MENU_SKILL;
    }

    catch (Exception e) { errorMsg("Invalid Skill Number."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processAddAction(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Add Action cancelled.");
      state = MENU_ACTION;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Number.");
      state = MENU_ACTION;
      return; }

    try

    {
      int num = Integer.parseInt(input);

      if ((num < 0) || (num >= World.getActionSize())) {
        errorMsg("Invalid Action ID.");
        state = MENU_ACTION;
        return; }

      Action A = World.getAction(num);

      if (A == null) {
        errorMsg("That action does not exist.");
        state = MENU_ACTION;
        return; }

      MOBILE.getActionInts().add(new Integer(num));

      if (inRoom)

      {
        Action newAction = A.replicate();
        newAction.setOwner(MOBILE);
        MOBILE.addAction(newAction);
      }

      goodMsg("Action Added to Mobile.");
      state = MENU_ACTION;
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid Action ID."); }
    catch (Exception e)               { errorMsg("Invalid Action ID."); }
  }


  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processRemAction(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Remove Action cancelled.");
      state = MENU_ACTION;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Number.");
      state = MENU_ACTION;
      return; }

    try

    {
      int index = Integer.parseInt(input)-1;

      if (index < 0) return;
      if (index >= MOBILE.getActionInts().size()) return;

      if (inRoom) MOBILE.getActions().remove(index);
      MOBILE.getActionInts().remove(index);

      goodMsg("Action Removed from Mobile.");
      state = MENU_ACTION;
    }

    catch (Exception e) { errorMsg("Invalid Action Number."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processMenuAction(String input)

  {
    if ((input.length() <= 0) || (input.equals("5")))
      state = MAIN_MENU;

    else if (input.equals("1")) state = ADD_ACTION;
    else if (input.equals("2")) state = REM_ACTION;

    else if (input.equals("3"))

    {
      Action A = MOBILE.getResponse(Mobile.RESPONSE_AGGRO);
      BuilderAction BA = new BuilderAction(myClient, A, false);
      myClient.pushInterface(BA);
      state = MENU_ACTION;
    }

    else if (input.equals("4")) state = COPY_AGGRO;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processCopyAggro(String input)

  {
    if (input.length() == 0) {
      errorMsg("Copy Aggression Action cancelled.");
      state = MENU_ACTION;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Number.");
      state = MENU_ACTION;
      return; }

    try

    {
      int anum = Integer.parseInt(input);

      if ((anum < 0) || (anum >= World.MAX_ACTIONS)) {
        errorMsg("There is no action with that ID number.");
        state = MENU_ACTION;
        return; }

      Action A = World.getAction(anum);

      if (A == null) {
        errorMsg("There is no action with that ID number.");
        state = MENU_ACTION;
        return; }

      A.setID(-1);
      A.setCustom("AGGRO");
      MOBILE.setResponse(Mobile.RESPONSE_AGGRO, A);
      goodMsg("Action " + anum + " copied to Mobile aggression action.");
      state = MENU_ACTION;
    }

    catch(Exception e) { errorMsg("Invalid Action ID.");  state = MENU_ACTION; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processWearRem(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Remove Worn Item cancelled.");
      state = MENU_WEAR;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Number.");
      state = MENU_WEAR;
      return; }

    try

    {
      int num = Integer.parseInt(input);

      if ((num < 0) || (num > World.getItemSize())) {
        errorMsg("Invalid Number.");
        state = MENU_WEAR;
        return; }

      ArrayList wList = MOBILE.getWearList();
      int size = wList.size();
      int index = -1;

      for (int i=0; i<size; i++)
      if (((Integer)wList.get(i)).intValue() == num)
        index = i;

      if (index == -1) {
        errorMsg("That item is not in the wear list.");
        state = MENU_WEAR;
        return; }

      wList.remove(index);
      MOBILE.getWearFreq().remove(index);
      goodMsg("Item removed from the wear list.#N");
      state = MENU_WEAR;
    }

    catch (Exception e) { errorMsg("Invalid Number.");  state = MENU_WEAR; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processWearFreq(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Add Worn Item cancelled.");
      state = MENU_WEAR;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Number.");
      state = MENU_WEAR;
      return; }

    try

    {
      double frq = Double.parseDouble(input);

      if ((frq < 0.0) || (frq > 100.0)) return;

      MOBILE.getWearList().add(new Integer(intHolder));
      MOBILE.getWearFreq().add(new Double(frq));
      goodMsg("Item added to the wear list.");
      state = MENU_WEAR;
    }

    catch (Exception e) { errorMsg("Invalid Number.");  state = MENU_WEAR; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processWearAdd(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Add Worn Item cancelled.");
      state = MENU_WEAR;
      return; }

    try

    {
      int num = -1;
      Item I = World.getItem(input);
      if (I != null) num = I.getID();
      else num = Integer.parseInt(input);

      if ((num < 0) || (num >= World.MAX_ITEMS) || (World.getItem(num) == null)) {
        errorMsg("That item does not exist.");
        state = MENU_WEAR;
        return; }

      intHolder = num;
      state = ADD_WEARFREQ;
    }

    catch (Exception e) { errorMsg("Invalid Number.");  state = MENU_WEAR; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processWearMenu(String input)

  {
    if ((input.length() <= 0) || (input.equals("3")))
      state = MAIN_MENU;

    else if (input.equals("1")) state = ADD_WEAR;
    else if (input.equals("2")) state = REM_WEAR;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processLoadRem(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Remove Load Item cancelled.");
      state = MENU_LOAD;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Number.");
      state = MENU_LOAD;
      return; }

    try

    {
      int num = Integer.parseInt(input);

      ArrayList lList = MOBILE.getIpopList();
      int size = lList.size();
      int index = -1;

      for (int i=0; i<size; i++)
      if (((Integer)lList.get(i)).intValue() == num)
        index = i;

      if (index == -1) {
        errorMsg("That item is not in the load list.");
        state = MENU_LOAD;
        return; }

      lList.remove(index);
      MOBILE.getIpopFreq().remove(index);
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid Item Number.");  return; }
    catch (Exception e)                { errorMsg("Invalid Item Number.");  return; }

    goodMsg("Item removed from the load list.");
    state = MENU_LOAD;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processLoadFreq(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Remove Load Item cancelled.");
      state = MENU_LOAD;
      return; }

    try

    {
      double frq = Double.parseDouble(input);
      if ((frq < 0.0) || (frq > 100.0)) return;
      MOBILE.getIpopList().add(new Integer(intHolder));
      MOBILE.getIpopFreq().add(new Double(frq));
    }

    catch (Exception e) { return; }

    goodMsg("Item added to the load list.");
    state = MENU_LOAD;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processLoadAdd(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Add Load Item cancelled.");
      state = MENU_LOAD;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Number.");
      state = MENU_LOAD;
      return; }

    try

    {
      int num = -1;
      Item I = World.getItem(input);
      if (I != null) num = I.getID();
      else num = Integer.parseInt(input);

      if ((num < 0) || (num >= World.MAX_ITEMS) || (World.getItem(num) == null))

      {
        errorMsg("That item does not exist.");
        state = MENU_LOAD;
        return;
      }

      intHolder = num;
      state = ADD_LOADFREQ;
    }

    catch (Exception e) { errorMsg("Invalid Number.");  state = MENU_LOAD; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processLoadMenu(String input)

  {
    if ((input.length() <= 0) || (input.equals("3")))
      state = MAIN_MENU;

    else if (input.equals("1")) state = ADD_LOAD;
    else if (input.equals("2")) state = REM_LOAD;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processShop(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Set Shop Name cancelled.");
      state = MAIN_MENU;
      return; }

    if (input.length() > 60) input = input.substring(0, 59);

    MOBILE.setStoreName(input);
    goodMsg("Mobile Shop Name set.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processAlign(String input)

  {
    if (input.length() <= 0) {
      state = MAIN_MENU;
      return; }

    int num;

    if (input.equalsIgnoreCase("neutral"))    num = ALIGN_NEUTRAL;
    else if (input.equalsIgnoreCase("light")) num = ALIGN_LIGHT;
    else if (input.equalsIgnoreCase("dark"))  num = ALIGN_DARK;
    else num = -1;

    if (num == -1) return;

    String align = "";

    if (num == ALIGN_NEUTRAL)     align = "neutral";
    else if (num == ALIGN_LIGHT)  align = "light";
    else if (num == ALIGN_DARK)   align = "dark";

    MOBILE.setAlignment(num);
    goodMsg("Alignment set to " + align + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processSpeed(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Set Speed cancelled.");
      state = MAIN_MENU;
      return; }

    int num;
    try { num = Integer.parseInt(input); }
    catch (Exception e) { return; }

    if (num < 0) return;
    if (num > 100) return;

    MOBILE.setSpeed(num);
    goodMsg("Mobile Speed set to " + num + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processSellRem(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Remove Sell Item cancelled.");
      state = MENU_SELL;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Number.");
      state = MENU_SELL;
      return; }

    try

    {
      int num = Integer.parseInt(input);

      if ((num < 0) || (num >= World.MAX_ITEMS)) {
        errorMsg("Invalid Sell Item.");
        state = MENU_SELL;
        return; }

      ArrayList slist = MOBILE.getSellList();
      int size = slist.size();
      int index = -1;

      for (int i=0; i<size; i++)
      if (((Integer)slist.get(i)).intValue() == num)
        index = i;

      if (index == -1) {
        errorMsg("That item is not for sale.");
        state = MENU_SELL;
        return; }

      slist.remove(index);
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid Item Number.");  return; }
    catch (Exception e)               { errorMsg("Invalid Item Number.");  return; }

    goodMsg("Item Removed from Sell List.");
    state = MENU_SELL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processSellAdd(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Add Sell Item cancelled.");
      state = MENU_SELL;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Number.");
      state = MENU_SELL;
      return; }

    try

    {
      int num = Integer.parseInt(input);

      if ((num < 0) || (num >= World.MAX_ITEMS)) {
        errorMsg("Invalid Sell Item.");
        state = MENU_SELL;
        return; }

      if (World.getItem(num) == null) {
        errorMsg("That item does not exist.");
        state = MENU_SELL;
        return; }

      ArrayList slist = MOBILE.getSellList();
      int size = slist.size();

      for (int i=0; i<size; i++)
      if (((Integer)slist.get(i)).intValue() == num) {
        errorMsg("That item is already for sale.");
        state = MENU_SELL;
        return; }

      slist.add(new Integer(num));
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid Item Number.");  return; }
    catch (Exception e)               { errorMsg("Invalid Item Number.");  return; }

    goodMsg("Item Added to Sell List.");
    state = MENU_SELL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processSellMenu(String input)

  {
    if (input.length() <= 0) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1"))       state = ADD_SELL;
    else if (input.equals("2"))  state = REM_SELL;
    else if (input.equals("3"))  state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processExp(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Set Experience cancelled.");
      state = MAIN_MENU;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Experience Amount.");
      state = MAIN_MENU;
      return; }

    try

    {
      int num = Integer.parseInt(input);
      if ((num < 0) || (num > 1000000)) throw new Exception("Invalid Experience Amount.");
      MOBILE.setExperience(num);
      goodMsg("Experience amount set.");
      state = MAIN_MENU;
    }

    catch (Exception e) { errorMsg("Invalid Experience Amount.");  state = MAIN_MENU; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processGold(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Set Gold cancelled.");
      state = MAIN_MENU;
      return; }

    if (!number(input)) {
      errorMsg("Invalid Gold Amount.");
      state = MAIN_MENU;
      return; }

    try

    {
      int num = Integer.parseInt(input);
      if ((num < 0) || (num > 1000000)) throw new Exception("Invalid Gold Amount.");
      MOBILE.setGold(num);
      goodMsg("Gold amount set.");
      state = MAIN_MENU;
      return;
    }

    catch (Exception e) { errorMsg("Invalid Gold Amount.");  state = MAIN_MENU; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processChangeStat(String input)

  {
    if (input.length() <= 0) {
      state = MAIN_MENU;
      return; }

    if (!number(input)) {
      errorMsg("Not a valid number.");
      state = MENU_STATS;
      return; }

    try

    {
      int num = Integer.parseInt(input);

      if ((num < 0) || (num > 999999))
        throw new Exception("Invalid Number.");

      if (state == SET_LVL) MOBILE.setLevel(num);
      else if (state == SET_STR) MOBILE.setSTR(num);
      else if (state == SET_DEX) MOBILE.setDEX(num);
      else if (state == SET_CON) MOBILE.setCON(num);
      else if (state == SET_INT) MOBILE.setINT(num);
      else if (state == SET_WIS) MOBILE.setWIS(num);
      else if (state == SET_HR) MOBILE.setHR(num);
      else if (state == SET_DR) MOBILE.setDR(num);
      else if (state == SET_AC) MOBILE.setAC(num);
      else if (state == SET_MR) MOBILE.setMR(num);
      else if (state == SET_HP) MOBILE.setMaxHP(num);
      else if (state == SET_MN) MOBILE.setMaxMN(num);
      else if (state == SET_MV) MOBILE.setMaxMV(num);

      goodMsg("Mobile Stat set.");
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid Number."); }
    catch (Exception e)               { errorMsg("Invalid Number."); }

    state = MENU_STATS;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processStatMenu(String input)

  {
    if (input.length() <= 0) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1"))                         state = SET_LVL;
    else if (input.equals("2"))                    state = SET_STR;
    else if (input.equals("3"))                    state = SET_DEX;
    else if (input.equals("4"))                    state = SET_CON;
    else if (input.equals("5"))                    state = SET_INT;
    else if (input.equals("6"))                    state = SET_WIS;
    else if (input.equals("7"))                    state = SET_HR;
    else if (input.equals("8"))                    state = SET_DR;
    else if (input.equals("9"))                    state = SET_AC;
    else if (input.equals("10"))                   state = SET_MR;
    else if (input.equals("11"))                   state = SET_HP;
    else if (input.equals("12"))                   state = SET_MN;
    else if (input.equals("13"))                   state = SET_MV;
    else if (input.equals("15"))                   state = MAIN_MENU;

    else if (((input.equals("14")) || (match(input, "aut", "autostats"))))

    {
      MOBILE.invokeMobTemplate(MOBILE.getLevel());
      goodMsg("Mobile Stats set as Level " + MOBILE.getLevel() + " template.");
    }

    else if (match(input, "lev", "level"))         state = SET_LVL;
    else if (match(input, "str", "strength"))      state = SET_STR;
    else if (match(input, "dex", "dexterity"))     state = SET_DEX;
    else if (match(input, "con", "constitution"))  state = SET_CON;
    else if (match(input, "int", "intelligence"))  state = SET_INT;
    else if (match(input, "wis", "wisdom"))        state = SET_WIS;
    else if (match(input, "hitr", "hitroll"))      state = SET_HR;
    else if (match(input, "dam", "damroll"))       state = SET_DR;
    else if (match(input, "arm", "armorclass"))    state = SET_AC;
    else if (match(input, "res", "resistance"))    state = SET_MR;
    else if (match(input, "hitp", "hitpoints"))    state = SET_HP;
    else if (match(input, "mana", "mana"))         state = SET_MN;
    else if (match(input, "move", "move"))         state = SET_MV;
    else if (input.equalsIgnoreCase("HR"))         state = SET_HR;
    else if (input.equalsIgnoreCase("DR"))         state = SET_DR;
    else if (input.equalsIgnoreCase("AC"))         state = SET_AC;
    else if (input.equalsIgnoreCase("MR"))         state = SET_MR;
    else if (input.equalsIgnoreCase("HP"))         state = SET_HP;
    else if (input.equalsIgnoreCase("MN"))         state = SET_MN;
    else if (input.equalsIgnoreCase("MV"))         state = SET_MV;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processGender(String input)

  {
    if (input.length() <= 0) {
      errorMsg("Set Gender cancelled.");
      state = MAIN_MENU;
      return; }

    String gender;

    if (input.equalsIgnoreCase("male"))         gender = "male";
    else if (input.equalsIgnoreCase("female"))  gender = "female";
    else return;

    MOBILE.setGender(gender);
    goodMsg("Gender set to " + gender + "#g.");
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

    MOBILE.setBlockerDir(choice);
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
      MOBILE.setBlockerType(Blocker.BLOCKER_NORMAL);
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

    MOBILE.setBlockerType(Blocker.BLOCKER_IMMORTAL);
    MOBILE.setBlockerParam(input);
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

    MOBILE.setBlockerType(Blocker.BLOCKER_LEVEL);
    MOBILE.setBlockerParam(input);
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

    MOBILE.setBlockerType(Blocker.BLOCKER_CLASS);
    MOBILE.setBlockerParam(input);
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

      MOBILE.setBlockerType(Blocker.BLOCKER_CLAN);
      MOBILE.setBlockerParam(input);
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

      MOBILE.setBlockerType(Blocker.BLOCKER_RELIGION);
      MOBILE.setBlockerParam(input);
      goodMsg("Religion Blocker set for " + r.getName() + "#g.");
      state = MENU_BLOCKER;
    }

    catch (Exception e) { errorMsg("Invalid Religion."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processFlags(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    if (!MOBILE.toggleFlag(input))
      errorMsg("That flag does not exist.");
    else goodMsg("Flag toggled.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processMobMenu(String input)

  {
    if (input.length() <= 0)               { exit();                               }
    else if (input.equals("1"))            { state = SET_NAME;                     }
    else if (input.equals("2"))            { state = SET_KEYWORDS;                 }
    else if (input.equals("3"))            { state = SET_TITLE;                    }
    else if (input.equals("4"))            { writeDesc(Writable.FORMAT_LDESC, 65); }
    else if (input.equals("5"))            { writeDesc(Writable.FORMAT_EXACT, 80); }
    else if (input.equals("6"))            { state = MENU_STATS;                   }
    else if (input.equals("7"))            { state = SET_FLAGS;                    }
    else if (input.equals("8"))            { state = SET_GOLD;                     }
    else if (input.equals("9"))            { state = SET_EXP;                      }
    else if (input.equalsIgnoreCase("A"))  { state = SET_GENDER;                   }
    else if (input.equalsIgnoreCase("B"))  { state = SET_ALIGN;                    }
    else if (input.equalsIgnoreCase("C"))  { state = SET_SPEED;                    }
    else if (input.equalsIgnoreCase("D"))  { state = MENU_LOAD;                    }
    else if (input.equalsIgnoreCase("E"))  { state = MENU_WEAR;                    }
    else if (input.equalsIgnoreCase("F"))  { trySellList();                        }
    else if (input.equalsIgnoreCase("G"))  { trySetShop();                         }
    else if (input.equalsIgnoreCase("I"))  { trySetBlocker();                      }
    else if (input.equalsIgnoreCase("M"))  { state = MENU_ACTION;                  }
    else if (input.equalsIgnoreCase("K"))  { state = MENU_SKILL;                   }
    else if (input.equalsIgnoreCase("L"))  { state = MENU_SPELL;                   }
    else if (input.equalsIgnoreCase("X"))  { tryGlobalDelete();                    }
    else if (input.equalsIgnoreCase("Q"))  { exit();                               }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void trySellList()

  {
    if (!MOBILE.getFlag(Mobile.FLAG_MERCHANT)) {
      errorMsg("Mobile is not flagged as a merchant.");
      state = MAIN_MENU;
      return; }

    state = MENU_SELL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void trySetShop()

  {
    if (!MOBILE.getFlag(Mobile.FLAG_MERCHANT)) {
      errorMsg("Mobile is not flagged as a merchant.");
      state = MAIN_MENU;
      return; }

    state = SET_SHOP;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void trySetBlocker()

  {
    if (!MOBILE.getFlag(Mobile.FLAG_BLOCKER)) {
      errorMsg("Mobile is not flagged as a blocker.");
      state = MAIN_MENU;
      return; }

    state = MENU_BLOCKER;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void writeDesc(int format, int width)

  {
    Writable w = MOBILE.getDescWrite();
    w.setFormat(format);
    Composer newComposer = new Composer(myClient.getCharInfo(), w);
    newComposer.setLineWidth(width);
    newComposer.displayInfo();
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processNameChange(String input)

  {
    if (input.equals("")) {
      errorMsg("Name change cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > MAX_MOB_NAME_LENGTH) {
      errorMsg("Name exceeds maximum length.");
      state = MAIN_MENU;
      return; }

    MOBILE.setName(input);
    goodMsg("Mob name set to: " + input + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processKeywordsChange(String input)

  {
    if (input.equals("")) {
      errorMsg("Keywords change cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > MAX_MOB_KEY_LENGTH) {
      errorMsg("Keywords string exceeds maximum length.");
      state = MAIN_MENU;
      return; }

    MOBILE.setKeywords(input);
    goodMsg("Mob keywords set to: " + input + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processTitleChange(String input)

  {
    if (input.equals("")) {
      errorMsg("Title change cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > MAX_MTITLE_LENGTH) {
      errorMsg("Title exceeds maximum length.");
      state = MAIN_MENU;
      return; }

    MOBILE.setTitle(input);
    goodMsg("Mob title set to: " + input + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getMobMenu()

  {
    ArrayList menu = new ArrayList();

    String exact = "Off";
    if (MOBILE.getDescWrite().getFormat() == Writable.FORMAT_EXACT) exact = "On";

    String X_Color = condColor("#b", MOBILE_PROTOTYPE);
    String F_Color = condColor("#c", MOBILE.getFlag(Mobile.FLAG_MERCHANT));
    String G_Color = condColor("#c", MOBILE.getFlag(Mobile.FLAG_MERCHANT));
    String I_Color = condColor("#c", MOBILE.getFlag(Mobile.FLAG_BLOCKER));

    menu.add("center");
    menu.add("#YMobile Editor");
    menu.add("-");

    menu.add("Name:    " + MOBILE.getName());
    menu.add("Keys:    " + MOBILE.getKeywords());
    menu.add("MobID:   " + MOBILE.getID());
    menu.add("Zone:    " + World.getZone(MOBILE.getZoneID()).getName());
    menu.add("Exact:   " + exact);
    menu.add("Level:   " + MOBILE.getLevel());
    menu.add("Gender:  " + MOBILE.getGender());
    menu.add("Gold:    " + expCommas(String.valueOf(MOBILE.getGold())));
    menu.add("Exp:     " + expCommas(String.valueOf(MOBILE.getExperience())));
    menu.add("Speed:   " + MOBILE.getSpeed());
    menu.add("Align:   " + MOBILE.getAlignString());
    menu.add("-");

    menu.add("#g1.#N) Change Name.                    #cA.#N) Set Gender.        ");
    menu.add("#g2.#N) Change Keywords.                #cB.#N) Set Alignment.     ");
    menu.add("#g3.#N) Change Title.                   #cC.#N) Set Speed.         ");
    menu.add("#g4.#N) Write Look Description.         #cD.#N) Set Load Items.    ");
    menu.add("#g5.#N) Write Exact Look Description.   #cE.#N) Set Worn Items.    ");
    menu.add("#g6.#N) Modify Mob Level and Stats.     " + F_Color + "F.#N) Set Sell Items.    ");
    menu.add("#g7.#N) Add/Remove Flags.               " + G_Color + "G.#N) Set Shop Name.     ");
    menu.add("#g8.#N) Change Gold.                    " + I_Color + "I.#N) Set Blocker Type.  ");
    menu.add("#g9.#N) Change Exp.                                            ");
    menu.add("                                                           ");
    menu.add("#cK.#N) Assign Skills.                  #yH.#N) Help!              ");
    menu.add("#cL.#N) Assign Spells.                  " + X_Color + "X.#N) Delete Mob.        ");
    menu.add("#cM.#N) Assign Actions.                 #rQ.#N) Save and Exit.     ");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getWearMenu()

  {
    ArrayList menu = new ArrayList();
    ArrayList wlist = MOBILE.getWearList();
    ArrayList flist = MOBILE.getWearFreq();
    int size = wlist.size();

    menu.add("center");
    menu.add("               #nMobile Item Wear List               ");
    menu.add("-");

    if (size == 0) menu.add("#gThis mobile pops with no wear items.#N");

    else for (int i=0; i<size; i++)

    {
      String temp = "";
      double frq = ((Double)flist.get(i)).doubleValue();
      int num = ((Integer)wlist.get(i)).intValue();
      Item ITEM = World.getItem(num);

      temp += "#n(#g" + String.format("%1$5.5s", frq) + "#n) ";
      temp += "[#R" + String.format("%1$5s", num) + "#n]#N ";
      temp += ITEM.getName() + "#N";
      menu.add(temp);
    }

    menu.add("-");
    menu.add("#n[#c1#n]#N Add Wear Item");
    menu.add("#n[#c2#n]#N Remove Wear Item");
    menu.add("#n[#c3#n]#N Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getLoadMenu()

  {
    ArrayList menu = new ArrayList();
    ArrayList slist = MOBILE.getIpopList();
    ArrayList flist = MOBILE.getIpopFreq();
    int size = slist.size();

    menu.add("center");
    menu.add("               #nMobile Item Load List               ");
    menu.add("-");

    if (size == 0) menu.add("#gThis mobile pops with no items.#N");

    else for (int i=0; i<size; i++)

    {
      String temp = "";
      double frq = ((Double)flist.get(i)).doubleValue();
      int num = ((Integer)slist.get(i)).intValue();
      Item ITEM = World.getItem(num);

      temp += "#n(#g" + String.format("%1$5.5s", frq) + "#n) ";
      temp += "[#R" + String.format("%1$5.5s", num) + "#n]#N ";
      temp += ITEM.getName() + "#N";
      menu.add(temp);
    }

    menu.add("-");
    menu.add("#n[#c1#n]#N Add Load Item");
    menu.add("#n[#c2#n]#N Remove Load Item");
    menu.add("#n[#c3#n]#N Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getSellMenu()

  {
    ArrayList menu = new ArrayList();
    ArrayList slist = MOBILE.getSellList();
    int size = slist.size();

    menu.add("center");
    menu.add("               #nMerchant Sell List               ");
    menu.add("-");

    if (size == 0) menu.add("#gThis mobile has no items for sale.#N");

    else for (int i=0; i<size; i++)

    {
      String temp = "";
      int num = ((Integer)slist.get(i)).intValue();
      Item ITEM = World.getItem(num);

      temp += "#n[#R" + String.format("%1$5s", num) + "#n]#N ";
      temp += ITEM.getName() + "#N";

      menu.add(temp);
    }

    menu.add("-");
    menu.add("#n[#c1#n]#N Add Item for Sale.");
    menu.add("#n[#c2#n]#N Remove Item for Sale.");
    menu.add("#n[#c3#n]#N Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getStatMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("       #nMobile Statistics:       ");
    menu.add("-");
    menu.add("#n[#c 1#n]#N Level         #R:#N  " + MOBILE.getLevel());
    menu.add("#n[#c 2#n]#N Strength      #R:#N  " + MOBILE.getSTR());
    menu.add("#n[#c 3#n]#N Dexterity     #R:#N  " + MOBILE.getDEX());
    menu.add("#n[#c 4#n]#N Constitution  #R:#N  " + MOBILE.getCON());
    menu.add("#n[#c 5#n]#N Intelligence  #R:#N  " + MOBILE.getINT());
    menu.add("#n[#c 6#n]#N Wisdom        #R:#N  " + MOBILE.getWIS());
    menu.add("#n[#c 7#n]#N Hitroll       #R:#N  " + MOBILE.getHR());
    menu.add("#n[#c 8#n]#N Damroll       #R:#N  " + MOBILE.getDR());
    menu.add("#n[#c 9#n]#N Armorclass    #R:#N  " + MOBILE.getAC());
    menu.add("#n[#c10#n]#N Resistance    #R:#N  " + MOBILE.getMR());
    menu.add("#n[#c11#n]#N Hitpoints     #R:#N  " + MOBILE.getNaturalHP());
    menu.add("#n[#c12#n]#N Manapoints    #R:#N  " + MOBILE.getNaturalMN());
    menu.add("#n[#c13#n]#N Movepoints    #R:#N  " + MOBILE.getNaturalMV());
    menu.add(" ");
    menu.add("#n[#c14#n]#N #gAuto-Stats.#N");
    menu.add(" ");
    menu.add("#n[#c15#n]#N Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getBlockerMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("          #nMobile Blocker Settings:          ");
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

  public String getBlockerClassInfo()

  {
    String temp = "\r\n"

    + "Enter all professions that may pass.\r\n"
    + "Example:  Assassin Shadowblade Dark Cleric Paladin\r\n\r\n";

    return temp;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getFlagMenu()

  {
    ArrayList menu = new ArrayList();

    String temp = "";
    Flag[] flagList = MOBILE.getFlags();
    int numFlags = flagList.length;
    int numRows  = numFlags / 4;
    int rowCount = 0;

    menu.add("center");
    menu.add("#nFlags#N");
    menu.add("-");

    for (int i=0; i<numFlags; i++)

    {
      rowCount++;

      temp += getBooleanColor(flagList[i].isEnabled());
      temp += String.format("%1$-13s ", flagList[i].getName() + " ");

      if (rowCount == 4)

      {
        rowCount = 0;
        menu.add(temp.trim());
        temp = "";
      }
    }

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nEnter Name Of Flag To Toggle: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static void enterMobBuilder(Client myClient, String input)

  {
    boolean prototype = false;
    Entity ENT = myClient.getCharInfo();

    if (ENT.getPlayerState() == PSTATE_FIGHTING) {
      ENT.echo("You can't do that while fighting.");
      return; }

    int mID = -1;
    Entity m = null;
    boolean notInteger = false;

    try

    {
      mID = Integer.parseInt(input);
      if (mID < 0) throw new Exception();
      if (mID >= World.MAX_MOBS) throw new Exception();
      m = World.getMob(mID);
      if (m == null) throw new Exception();
      prototype = true;
    }

    catch (NumberFormatException nfe)

    {
      m = ENT.getRoom().findEntity(ENT, input);

      if (m == null) {
        ENT.echo("Mob not found.");
        return; }

      if (m.isPlayer()) {
        ENT.echo("You can't edit characters.");
        return; }

      notInteger = true;
    }

    catch (Exception e)

    {
      ENT.echo("Mob not found.");
      return;
    }

    if (!notInteger)

    {
      Zone Z = World.getZone(m.castMob().getZoneID());

      if (Z.getBusy()) {
        myClient.getCharInfo().echo(Z.getName() + " is busy.");
        return; }

      if ((ENT.getLevel() < 110) && (!ENT.getName().equalsIgnoreCase(Z.getOwner()))) {
        ENT.echo("You are not the owner of " + Z.getName() + "#N.");
        return; }

      Z.setBusy(true);
    }

    if (m.getPlayerState() == PSTATE_FIGHTING) {
      ENT.echo("You can't edit " + m.getName() + " while " + m.he() + " is fighting.");
      return; }

    BuilderMob BM = new BuilderMob(myClient, m.castMob(), prototype);
    myClient.pushInterface(BM);
    if (notInteger) BM.inRoom();
    myClient.getCharInfo().blindEmote("starts editing a mobile.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void exit()

  {
    MOBILE.setClient(null);
    myClient.popInterface();
    myClient.setClientState(CSTATE_NORMAL);
    myClient.getCharInfo().blindEmote("has finished editing a mobile.");

    if (GLOBAL_DELETE)

    {
      World.globalMobDelete(MOBILE.getID());
      World.save();
      myClient.getCharInfo().look("#rMobile " + MOBILE.getName() + "#r globally deleted.#N");
    }

    else if (!inRoom)

    {
      int znum = MOBILE.castMob().getZoneID();
      Zone Z = World.getZone(znum);
      myClient.getCharInfo().look(Z.getName() + " saved.");
      WriteThread.addObject(Z);
    }

    else myClient.getCharInfo().look("");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String getActionMenu()

  {
    ArrayList menu = new ArrayList();
    int size = MOBILE.getActionInts().size();

    menu.add("center");
    menu.add("                 #nMobile Actions                 ");
    menu.add("-");

    if (size == 0) menu.add("#gThere are no actions assigned to this mobile.#N");

    else for (int i=0; i<size; i++)

    {
      String temp = "";
      int AID = ((Integer)MOBILE.getActionInts().get(i)).intValue();
      Action A = World.getAction(AID);
      temp += "(#r" + (i+1) + "#N) ";
      temp += "[#c" + String.format("%1$-5s", AID) + "#N] ";
      temp += A.getName();
      menu.add(temp);
    }

    menu.add("-");
    menu.add("[#c1#N] Add Action.");
    menu.add("[#c2#N] Remove Action.");
    menu.add("[#c3#N] Edit Aggression.");
    menu.add("[#c4#N] Copy Aggression.");
    menu.add("[#c5#N] Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String getSkillMenu()

  {
    ArrayList menu = new ArrayList();
    ArrayList skills = MOBILE.getSkills();
    int size = skills.size();

    menu.add("center");
    menu.add("               #nMobile Skill List               ");
    menu.add("-");


    if (size == 0) menu.add("#gThere are no skills assigned to this mobile.#N");

    else for (int i=0; i<size; i++)

    {
      String temp = "";
      String skillName = ((Skill)skills.get(i)).getName();
      int level = ((Skill)skills.get(i)).getLevel();
      temp += "(#r" + String.format("%1$2s", i+1) + "#N) ";
      temp += "[#g" + String.format("%1$3s", level) + "#N] " + skillName;
      menu.add(temp);
    }

    menu.add("-");
    menu.add("[#c1#N] Add Skill.");
    menu.add("[#c2#N] Remove Skill.");
    menu.add("[#c3#N] Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String getSpellMenu()

  {
    ArrayList menu = new ArrayList();
    ArrayList spells = MOBILE.getSpells();
    int size = spells.size();

    menu.add("center");
    menu.add("               #nMobile Spell List               ");
    menu.add("-");

    if (size == 0) menu.add("#gThere are no spells assigned to this mobile.#N");

    else for (int i=0; i<size; i++)

    {
      String temp = "";
      String spellName = ((Spell)spells.get(i)).getName();
      int level   = ((Spell)spells.get(i)).getLevel();
      temp += "(#r" + String.format("%1$2s", i+1) + "#N) ";
      temp += "[#g" + String.format("%1$3s", level) + "#N] " + spellName;
      menu.add(temp);
    }

    menu.add("-");
    menu.add("[#c1#N] Add Spell.");
    menu.add("[#c2#N] Remove Spell.");
    menu.add("[#c3#N] Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}