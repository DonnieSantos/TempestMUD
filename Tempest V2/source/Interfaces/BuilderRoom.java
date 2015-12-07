import java.io.*;
import java.util.*;

public class BuilderRoom extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int MAIN_MENU          = 0;
  public static final int SET_NAME           = 1;
  public static final int SET_FDESC          = 2;
  public static final int SET_EDESC          = 3;

  public static final int MENU_LOOK          = 4;
  public static final int ADD_LOOK_NAME      = 5;
  public static final int REMOVE_LOOK        = 7;

  public static final int MENU_EXIT_LOOK     = 8;
  public static final int EDIT_EXIT_LOOK     = 9;
  public static final int RESET_EXIT_LOOK    = 10;

  public static final int SELECT_EXIT        = 11;
  public static final int SET_EXIT           = 12;
  public static final int SET_FLAG           = 13;
  public static final int SET_ZONE           = 14;
  public static final int SET_BANK           = 15;

  public static final int DELETE_ROOM        = 16;
  public static final int SET_TERRAIN        = 17;

  public static final int MENU_ACTION        = 18;
  public static final int ADD_ACTION         = 19;
  public static final int REM_ACTION         = 20;
  public static final int EDIT_RESPONSE      = 21;
  public static final int COPY_RESPONSE      = 22;
  public static final int COPY_RESPONSE_ID   = 23;

  public static final int MENU_MOB_POP       = 24;
  public static final int ADD_POP_COUNT      = 25;
  public static final int ADD_POP_MOB        = 26;
  public static final int ADD_POP_FREQ       = 27;
  public static final int REM_POP            = 28;
  public static final int SORT_POP_ONE       = 29;
  public static final int SORT_POP_TWO       = 30;
  public static final int EDIT_POP           = 31;
  public static final int EDIT_POP_MENU      = 32;
  public static final int EDIT_POP_ADD       = 33;
  public static final int EDIT_POP_REM       = 34;
  public static final int EDIT_POP_MNUM_ONE  = 35;
  public static final int EDIT_POP_MNUM_TWO  = 36;
  public static final int EDIT_POP_FREQ_ONE  = 37;
  public static final int EDIT_POP_FREQ_TWO  = 38;

  public static final int MENU_ITEM_POP      = 39;
  public static final int ADD_IPOP_COUNT     = 40;
  public static final int ADD_IPOP_ITEM      = 41;
  public static final int ADD_IPOP_FREQ      = 42;
  public static final int REM_IPOP           = 43;
  public static final int SORT_IPOP_ONE      = 44;
  public static final int SORT_IPOP_TWO      = 45;
  public static final int EDIT_IPOP          = 46;
  public static final int EDIT_IPOP_MENU     = 47;
  public static final int EDIT_IPOP_ADD      = 48;
  public static final int EDIT_IPOP_REM      = 49;
  public static final int EDIT_IPOP_MNUM_ONE = 50;
  public static final int EDIT_IPOP_MNUM_TWO = 51;
  public static final int EDIT_IPOP_FREQ_ONE = 52;
  public static final int EDIT_IPOP_FREQ_TWO = 53;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public int viewMode()                { return NO_OUTPUT;    }
  public void echo(String s)           { utilityQueue.add(s); }
  public void addOutput(String s)      { return;              }
  public void addFightOutput(String s) { return;              }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private Room ROOM;
  private int state;
  private int holderInteger;
  private String holderString;

  private PopMember mobPOP;
  private PopMember editMobPOP;
  private int numMobPopTypes;
  private int currentMobPop;
  private int currentMobPopID;

  private PopMember itemPOP;
  private PopMember editItemPOP;
  private int numItemPopTypes;
  private int currentItemPop;
  private int currentItemPopID;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public BuilderRoom(Client c, Room r)

  {
    super(c);
    ROOM = r;
    state = MAIN_MENU;
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
    menuMsg = "";
    active = false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    if (state == MAIN_MENU)               return getRoomMenu();
    else if (state == SET_NAME)           return "Enter New Room Name: ";
    else if (state == MENU_LOOK)          return getARLookMenu();
    else if (state == ADD_LOOK_NAME)      return "Enter Look Keyword:  ";
    else if (state == REMOVE_LOOK)        return "Enter Look Number To Remove: ";
    else if (state == MENU_EXIT_LOOK)     return getARExitMenu();
    else if (state == EDIT_EXIT_LOOK)     return "Enter Direction to Change: ";
    else if (state == RESET_EXIT_LOOK)    return "Enter Direction to Reset: ";
    else if (state == SELECT_EXIT)        return getARLinkMenu();
    else if (state == SET_EXIT)           return "Enter Room Number To Link To: ";
    else if (state == DELETE_ROOM)        return "Type 'delete' if you want to delete this room: ";
    else if (state == SET_ZONE)           return "Enter Zone Number: ";
    else if (state == SET_BANK)           return getBankList();
    else if (state == SET_TERRAIN)        return getTerrainMenu();
    else if (state == SET_FLAG)           return getARFlagMenu();
    else if (state == MENU_ACTION)        return getActionMenu();
    else if (state == ADD_ACTION)         return "Enter Action ID to Add: ";
    else if (state == REM_ACTION)         return "Enter Action ID to Remove: ";
    else if (state == EDIT_RESPONSE)      return "Enter Response Action Index to Edit: ";
    else if (state == COPY_RESPONSE)      return "Enter Response Action Index: ";
    else if (state == COPY_RESPONSE_ID)   return "Enter Action ID: ";
    else if (state == MENU_MOB_POP)       return getMobPopMenu();
    else if (state == ADD_POP_COUNT)      return "Enter #rNumber of Mob Types     #n: ";
    else if (state == ADD_POP_MOB)        return "Enter #rPopulation Mob ID   #N(#n" + (currentMobPop+1) + "#N) #n: ";
    else if (state == ADD_POP_FREQ)       return "Enter #rPopulation Freqency #N(#n" + (currentMobPop+1) + "#N) #n: ";
    else if (state == REM_POP)            return "Enter Population ID Number to Remove: ";
    else if (state == SORT_POP_ONE)       return "Enter the first position to swap  : ";
    else if (state == SORT_POP_TWO)       return "Enter the second position to swap : ";
    else if (state == EDIT_POP)           return "Enter Population ID Number to Edit: ";
    else if (state == EDIT_POP_ADD)       return "Enter Mobile ID Number to add: ";
    else if (state == EDIT_POP_REM)       return "Enter index number to remove : ";
    else if (state == EDIT_POP_MNUM_ONE)  return "Enter index number to change : ";
    else if (state == EDIT_POP_MNUM_TWO)  return "Enter the new mobile ID num  : ";
    else if (state == EDIT_POP_FREQ_ONE)  return "Enter index number to change : ";
    else if (state == EDIT_POP_FREQ_TWO)  return "Enter the new pop frequency  : ";
    else if (state == EDIT_POP_MENU)      return getMobPopEditMenu();
    else if (state == MENU_ITEM_POP)      return getItemPopMenu();
    else if (state == ADD_IPOP_COUNT)     return "Enter #rNumber of Item Types     #n: ";
    else if (state == ADD_IPOP_ITEM)      return "Enter #rPopulation Item ID   #N(#n" + (currentMobPop+1) + "#N) #n: ";
    else if (state == ADD_IPOP_FREQ)      return "Enter #rPopulation Freqency #N(#n" + (currentMobPop+1) + "#N) #n: ";
    else if (state == REM_IPOP)           return "Enter Population ID Number to Remove: ";
    else if (state == SORT_IPOP_ONE)      return "Enter the first position to swap  : ";
    else if (state == SORT_IPOP_TWO)      return "Enter the second position to swap : ";
    else if (state == EDIT_IPOP)          return "Enter Population ID Number to Edit: ";
    else if (state == EDIT_IPOP_ADD)      return "Enter Item ID Number to add  : ";
    else if (state == EDIT_IPOP_REM)      return "Enter index number to remove : ";
    else if (state == EDIT_IPOP_MNUM_ONE) return "Enter index number to change : ";
    else if (state == EDIT_IPOP_MNUM_TWO) return "Enter the new item ID num    : ";
    else if (state == EDIT_IPOP_FREQ_ONE) return "Enter index number to change : ";
    else if (state == EDIT_IPOP_FREQ_TWO) return "Enter the new pop frequency  : ";
    else if (state == EDIT_IPOP_MENU)     return getItemPopEditMenu();

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;

    String input = clearWhiteSpace(myClient.getCommand());

    if (state == MAIN_MENU)               processRoomMenu(input);
    else if (state == SET_NAME)           processRename(input);
    else if (state == MENU_LOOK)          processLookMenu(input);
    else if (state == ADD_LOOK_NAME)      processAddLookName(input);
    else if (state == REMOVE_LOOK)        processRemoveLook(input);
    else if (state == MENU_EXIT_LOOK)     processExitLookMenu(input);
    else if (state == EDIT_EXIT_LOOK)     processEditExitLookMenu(input);
    else if (state == RESET_EXIT_LOOK)    processExitLookReset(input);
    else if (state == SELECT_EXIT)        processAddExitChoose(input);
    else if (state == SET_EXIT)           processAddExit(input);
    else if (state == SET_FLAG)           processFlagSelection(input);
    else if (state == DELETE_ROOM)        processRoomDelete(input);
    else if (state == SET_BANK)           processBankChange(input);
    else if (state == SET_ZONE)           processZoneChange(input);
    else if (state == SET_TERRAIN)        processTerrainChange(input);
    else if (state == MENU_ACTION)        processActionMenu(input);
    else if (state == ADD_ACTION)         processAddAction(input);
    else if (state == REM_ACTION)         processRemoveAction(input);
    else if (state == EDIT_RESPONSE)      processEditResponse(input);
    else if (state == COPY_RESPONSE)      processCopyResponse(input);
    else if (state == COPY_RESPONSE_ID)   processCopyActionID(input);
    else if (state == MENU_MOB_POP)       processMobPopMenu(input);
    else if (state == ADD_POP_COUNT)      processAddMobPopCount(input);
    else if (state == ADD_POP_MOB)        processAddMobPopMob(input);
    else if (state == ADD_POP_FREQ)       processAddMobPopFreq(input);
    else if (state == REM_POP)            processRemoveMobPop(input);
    else if (state == SORT_POP_ONE)       processSortMobPop1(input);
    else if (state == SORT_POP_TWO)       processSortMobPop2(input);
    else if (state == EDIT_POP)           processEditMobPop(input);
    else if (state == EDIT_POP_ADD)       processEditMobPopAdd(input);
    else if (state == EDIT_POP_REM)       processEditMobPopRem(input);
    else if (state == EDIT_POP_MNUM_ONE)  processEditMobPopMNum1(input);
    else if (state == EDIT_POP_MNUM_TWO)  processEditMobPopMNum2(input);
    else if (state == EDIT_POP_FREQ_ONE)  processEditMobPopFreq1(input);
    else if (state == EDIT_POP_FREQ_TWO)  processEditMobPopFreq2(input);
    else if (state == EDIT_POP_MENU)      processEditMobPopMenu(input);

    else if (state == MENU_ITEM_POP)      processItemPopMenu(input);
    else if (state == ADD_IPOP_COUNT)     processAddItemPopCount(input);
    else if (state == ADD_IPOP_ITEM)      processAddItemPopItem(input);
    else if (state == ADD_IPOP_FREQ)      processAddItemPopFreq(input);
    else if (state == REM_IPOP)           processRemoveItemPop(input);
    else if (state == SORT_IPOP_ONE)      processSortItemPop1(input);
    else if (state == SORT_IPOP_TWO)      processSortItemPop2(input);
    else if (state == EDIT_IPOP)          processEditItemPop(input);
    else if (state == EDIT_IPOP_ADD)      processEditItemPopAdd(input);
    else if (state == EDIT_IPOP_REM)      processEditItemPopRem(input);
    else if (state == EDIT_IPOP_MNUM_ONE) processEditItemPopMNum1(input);
    else if (state == EDIT_IPOP_MNUM_TWO) processEditItemPopMNum2(input);
    else if (state == EDIT_IPOP_FREQ_ONE) processEditItemPopFreq1(input);
    else if (state == EDIT_IPOP_FREQ_TWO) processEditItemPopFreq2(input);
    else if (state == EDIT_IPOP_MENU)     processEditItemPopMenu(input);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    myClient.popInterface();
    WriteThread.addObject(ROOM.getZone());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void focusGained() { return; }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processActionMenu(String input)

  {
    if ((input.length() == 0) || (input.equals("5"))) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1")) state = ADD_ACTION;
    if (input.equals("2")) state = REM_ACTION;
    if (input.equals("3")) state = EDIT_RESPONSE;
    if (input.equals("4")) state = COPY_RESPONSE;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processAddAction(String input)

  {
    if (input.length() == 0) {
      errorMsg("Action addition cancelled.");
      state = MENU_ACTION;
      return; }

    try

    {
      int num = Integer.parseInt(input);
      if (num < 0) throw new Exception("That action does not exist.");
      if (num > World.MAX_ACTIONS) throw new Exception("That action does not exist.");

      if (World.getAction(num) == null)
        throw new Exception("That action does not exist.");

      Action A = World.getAction(num).replicate();
      ROOM.addAction(A);
      goodMsg(A.getName() + " added successfully.");
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid Action ID."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_ACTION;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processRemoveAction(String input)

  {
    if (input.length() == 0) {
      errorMsg("Action removal cancelled.");
      state = MENU_ACTION;
      return; }

    try

    {
      int index = Integer.parseInt(input)-1;
      Action A = (Action)ROOM.getActionList().remove(index);
      goodMsg(A.getName() + " removed successfully.");
    }

    catch (Exception e) { errorMsg("Invalid action index."); }

    state = MENU_ACTION;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processEditResponse(String input)

  {
    if (input.length() == 0) {
      errorMsg("Action editing cancelled.");
      state = MENU_ACTION;
      return; }

    try

    {
      int num = Integer.parseInt(input)-1;
      Action A = ROOM.getResponse(num);
      BuilderAction BA = new BuilderAction(myClient, A, false);
      myClient.pushInterface(BA);
    }

    catch (Exception e) { errorMsg("Invalid action index."); }

    state = MENU_ACTION;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processCopyResponse(String input)

  {
    if (input.length() == 0) {
      state = MENU_ACTION;
      errorMsg("Action copying cancelled.");
      return; }

    try

    {
      int num = Integer.parseInt(input)-1;
      if (num < 0) throw new Exception();
      if (num >= Room.NUM_RESPONSES) throw new Exception();

      holderInteger = num;
      state = COPY_RESPONSE_ID;
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
      state = MENU_ACTION;
      errorMsg("Action copying cancelled.");
      return; }

    try

    {
      int num = Integer.parseInt(input);
      if (num < 0) throw new Exception("That action does not exist.");
      if (num > World.MAX_ACTIONS) throw new Exception("That action does not exist.");

      if (World.getAction(num) == null)
        throw new Exception("That action does not exist.");

      Action A = World.getAction(num).replicate();
      ROOM.setResponse(holderInteger, A);
      goodMsg("Action " + A.getName() + " copied successfully.");
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid Action ID."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_ACTION;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processZoneChange(String input)

  {
    if (input.length() == 0) {
      errorMsg("Zone Change cancelled.");
      state = MAIN_MENU;
      return; }

    if (ROOM.getZone().getNumRooms() == 1) {
      errorMsg("You cannot move the last room in a zone.");
      state = MAIN_MENU;
      return; }

    try

    {
      int zNum = Integer.parseInt(input);

      if (zNum < 0) {
        errorMsg("Invalid zone.");
        return; }

      try { World.getZone(zNum); }
      catch (Exception e) { errorMsg("Invalid zone."); return; }

      Zone oldZone = ROOM.getZone();
      Zone newZone = World.getZone(zNum);

      oldZone.globalRoomDelete(ROOM.getID());
      newZone.addRoom(ROOM.getID());
      ROOM.setZoneID(newZone.getID());

      WriteThread.addObject(oldZone);
      WriteThread.addObject(newZone);

      goodMsg("Room changed to zone: " + newZone.getName() + "#g.");
      state = MAIN_MENU;
    }

    catch (Exception e) { errorMsg("Invalid zone number."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void fragBranch(BankBranch BB)

  {
    if (BB == null) return;

    try

    {
      BankChain oldBank = World.getBank(BB.getChainID());
      oldBank.removeBranch(BB);
    }

    catch (Exception e) { System.out.println("Old Bank Not Found."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processBankChange(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    try

    {
      int bNum = Integer.parseInt(input) - 1;

      if (bNum < -1) {
        errorMsg("Invalid bank.");
        return; }

      if (bNum == -1)

      {
        fragBranch(ROOM.getBankBranch());
        ROOM.setBankBranch(null);
        WriteThread.addObject(ROOM.getZone());
        goodMsg("This room is no longer affiliated with a bank.");
        return;
      }

      try { World.getBank(bNum); }
      catch (Exception e) { errorMsg("Invalid bank."); return; }

      BankBranch oldBranch = ROOM.getBankBranch();
      BankChain newBank = World.getBank(bNum);
      BankBranch newBranch = new BankBranch(newBank.getID(), ROOM.getID());

      if ((oldBranch == null) || ((oldBranch.getChainID() != newBranch.getChainID())))

      {
        fragBranch(oldBranch);
        newBank.addBranch(newBranch);
        ROOM.setBankBranch(newBranch);
        WriteThread.addObject(ROOM.getZone());
      }

      goodMsg("This room is now a branch of:#N " + newBank.getName() + "#g.");
    }

    catch (Exception e) { errorMsg("Invalid bank number."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static void enterRoomBuilder(Client myClient)

  {
    Entity E = myClient.getCharInfo();
    Zone Z = E.getZone();

    if (E.getPlayerState() == PSTATE_FIGHTING) {
      E.echo("You can't do that while fighting.");
      return; }

    if (Z.getBusy()) {
      E.echo(Z.getName() + " is busy.");
      return; }

    if ((E.getLevel() < 110) && (!E.getName().equalsIgnoreCase(Z.getOwner()))) {
      E.echo("You are not the owner of " + Z.getName() + "#N.");
      return; }

    Z.setBusy(true);
    myClient.getCharInfo().blindEmote("starts building the room.");
    myClient.pushInterface(new BuilderRoom(myClient, myClient.getCharInfo().getRoom()));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void exit()

  {
    myClient.popInterface();
    myClient.setClientState(CSTATE_NORMAL);
    myClient.getCharInfo().blindEmote("has finished building the zone.");
    myClient.getCharInfo().look(ROOM.getTitle() + " saved.");
    WriteThread.addObject(ROOM.getZone());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRoomMenu(String input)

  {
    if (input.length() <= 0) exit();

    else if (input.equals("1"))            { state = SET_NAME;           }
    else if (input.equals("2"))            { processNormalDescription(); }
    else if (input.equals("3"))            { processExactDescription();  }
    else if (input.equals("4"))            { state = MENU_LOOK;          }
    else if (input.equals("5"))            { state = MENU_EXIT_LOOK;     }
    else if (input.equals("6"))            { state = SELECT_EXIT;        }
    else if (input.equals("7"))            { state = SET_FLAG;           }
    else if (input.equals("8"))            { state = MENU_MOB_POP;       }
    else if (input.equals("9"))            { state = MENU_ITEM_POP;      }
    else if (input.equalsIgnoreCase("A"))  { state = SET_TERRAIN;        }
    else if (input.equalsIgnoreCase("B"))  { state = SET_BANK;           }
    else if (input.equalsIgnoreCase("Z"))  { state = SET_ZONE;           }
    else if (input.equalsIgnoreCase("H"))  {                             }
    else if (input.equalsIgnoreCase("M"))  { state = MENU_ACTION;        }
    else if (input.equalsIgnoreCase("Q"))  { exit();                     }

    else if (input.equalsIgnoreCase("X"))

    {
      if (ROOM.getZone().getNumRooms() == 1)
        echo("#rYou can't delete the last room in a zone.#N\r\n");
      else state = DELETE_ROOM;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processNormalDescription()

  {
    Writable w = ROOM.getDescWrite();
    w.setFormat(Writable.FORMAT_RDESC);
    Composer newComposer = new Composer(myClient.getCharInfo(), w);
    newComposer.setLineWidth(ROOM_FORMATTED_WIDTH);
    newComposer.displayInfo();
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processExactDescription()

  {
    Writable w = ROOM.getDescWrite();
    w.setFormat(Writable.FORMAT_EXACT);
    Composer newComposer = new Composer(myClient.getCharInfo(), w);
    newComposer.setLineWidth(ROOM_EXACT_WIDTH);
    newComposer.displayInfo();
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processRename(String input)

  {
    if (input.length() == 0) {
      errorMsg("Room renaming cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > ROOM_TITLE_LENGTH) {
      errorMsg("Room title exceeds the maximum length.");
      state = MAIN_MENU;
      return; }

    ROOM.setTitle(input);
    goodMsg("Room name set to: " + input + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processLookMenu(String input)

  {
    if ((input.length() == 0) || (input.equals("3"))) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1"))      { state = ADD_LOOK_NAME; }
    else if (input.equals("2")) { state = REMOVE_LOOK;   }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processAddLookName(String input)

  {
    if (input.length() == 0) {
      state = MENU_LOOK;
      errorMsg("Look creation cancelled.");
      return; }

    if (visibleSize(input) > ROOM_LOOK_LENGTH) {
      state = MENU_LOOK;
      errorMsg("Look name exceeds the maximum length.");
      return; }

    Writable w = new Writable("Generic look.", Writable.FORMAT_LDESC);
    ROOM.addLook(input, w);
    Composer newComposer = new Composer(myClient.getCharInfo(), w);
    newComposer.setLineWidth(LOOK_FORMATTED_WIDTH);
    newComposer.displayInfo();
    goodMsg("Look added successfully.");
    state = MENU_LOOK;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processRemoveLook(String input)

  {
    if (input.length() == 0) {
      errorMsg("Look deletion cancelled.");
      state = MENU_LOOK;
      return; }

    try

    {
      int index = Integer.parseInt(input) - 1;

      if (index < 0) throw new Exception();
      if (index >= ROOM.getNumTargs()) throw new Exception();

      String target = ROOM.getLookTarg(index);
      ROOM.removeLook(target);
      goodMsg("Look removed.");
      state = MENU_LOOK;
    }

    catch (Exception e) { return; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processExitLookMenu(String input)

  {
    if ((input.length() == 0) || (input.equals("3"))) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1"))      state = EDIT_EXIT_LOOK;
    else if (input.equals("2")) state = RESET_EXIT_LOOK;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processEditExitLookMenu(String input)

  {
    if (input.length() == 0) {
      errorMsg("Exit look editting cancelled.");
      state = MENU_EXIT_LOOK;
      return; }

    try

    {
      int index = -1;
      String dir = Character.toString(input.charAt(0)).toLowerCase();

      if (dir.equals("n")) index = 0;
      if (dir.equals("s")) index = 1;
      if (dir.equals("e")) index = 2;
      if (dir.equals("w")) index = 3;
      if (dir.equals("u")) index = 4;
      if (dir.equals("d")) index = 5;

      if (index == -1) {
        errorMsg("Invalid direction.");
        state = MENU_EXIT_LOOK;
        return; }

      Writable w = ROOM.getLookWrit(index);
      Composer newComposer = new Composer(myClient.getCharInfo(), w);
      newComposer.setLineWidth(LOOK_FORMATTED_WIDTH);
      newComposer.displayInfo();
    }

    catch (Exception e) { errorMsg("Invalid direction."); }

    state = MENU_EXIT_LOOK;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processExitLookReset(String input)

  {
    if (input.length() == 0) {
      errorMsg("Exit look resetting cancelled.");
      state = MENU_EXIT_LOOK;
      return; }

    try

    {
      int index = -1;
      String dir = Character.toString(input.charAt(0)).toLowerCase();

      if (dir.equals("n")) index = 0;
      if (dir.equals("s")) index = 1;
      if (dir.equals("e")) index = 2;
      if (dir.equals("w")) index = 3;
      if (dir.equals("u")) index = 4;
      if (dir.equals("d")) index = 5;

      if (index == -1) {
        errorMsg("Invalid direction.");
        state = MENU_EXIT_LOOK;
        return; }

      if (index == 0) ROOM.getLookWrit(0).write("You see nothing out of the ordinary to the north.");
      if (index == 1) ROOM.getLookWrit(1).write("You see nothing out of the ordinary to the south.");
      if (index == 2) ROOM.getLookWrit(2).write("You see nothing out of the ordinary to the east.");
      if (index == 3) ROOM.getLookWrit(3).write("You see nothing out of the ordinary to the west.");
      if (index == 4) ROOM.getLookWrit(4).write("You see nothing out of the ordinary above you.");
      if (index == 5) ROOM.getLookWrit(5).write("You see nothing out of the ordinary below you.");

      goodMsg("Exit reset.");
    }

    catch (Exception e) { errorMsg("Invalid direction."); }

    state = MENU_EXIT_LOOK;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processAddExitChoose(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    input = Character.toString(input.charAt(0)).toLowerCase();

    if (input.equals("n") || input.equals("s") ||
        input.equals("e") || input.equals("w") ||
        input.equals("u") || input.equals("d"))

    {
      holderString = input;
      state = SET_EXIT;
    }

    else

    {
      state = SELECT_EXIT;
      errorMsg("No such direction: " + input);
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processAddExit(String input)

  {
    if (input.length() == 0) {
      errorMsg("Exit change cancelled.");
      state = SELECT_EXIT;
      return; }

    try

    {
      int roomExit = Integer.parseInt(input);

      if (roomExit < 0) throw new Exception();
      if (roomExit >= World.MAX_ROOMS) throw new Exception();

      if (World.getRoom(roomExit) != null)

      {
        ROOM.setExit(holderString, roomExit);
        goodMsg("Room exit set.");
        state = SELECT_EXIT;
      }

      else throw new Exception();
    }

    catch (Exception e)

    {
      errorMsg("That room does not exit.");
      state = SELECT_EXIT;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processFlagSelection(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    if (!ROOM.toggleFlag(input))
      errorMsg("That flag does not exist.");
    else goodMsg("Flag toggled.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processMobPopMenu(String input)

  {
    if ((input.length() == 0) || (input.equals("5"))) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1")) state = ADD_POP_COUNT;

    else if (input.equals("2"))

    {
      if (ROOM.getMobPopSize() == 0) {
        errorMsg("There are no mobiles to edit.");
        state = MENU_MOB_POP;
        return; }

      state = EDIT_POP;
    }

    else if (input.equals("3"))

    {
      if (ROOM.getMobPopSize() == 0) {
        errorMsg("There are no mobiles to remove.");
        state = MENU_MOB_POP;
        return; }

      state = REM_POP;
    }

    else if (input.equals("4"))

    {
      if (ROOM.getMobPopSize() < 2) {
        errorMsg("You need at least 2 mobiles to change the order.");
        state = MENU_MOB_POP;
        return; }

      state = SORT_POP_ONE;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processAddMobPopCount(String input)

  {
    try

    {
      if (input.length() == 0) {
        errorMsg("Add population member cancelled.");
        state = MENU_MOB_POP;
        return; }

      numMobPopTypes = Integer.parseInt(input);

      if ((numMobPopTypes < 1) || (numMobPopTypes > 9))
        throw new Exception("Input must be 1-9.");

      currentMobPop = 0;
      state = ADD_POP_MOB;
    }

    catch (NumberFormatException nfe)  { echo("#rInput must be 1-9.#N\r\n");      return; }
    catch (Exception e)                { echo("#r" + e.getMessage() + "#N\r\n");  return; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processAddMobPopMob(String input)

  {
    try

    {
      if (input.length() == 0) {
        errorMsg("Add population member cancelled.");
        state = MENU_MOB_POP;
        return; }

      if (number(input))

      {
        currentMobPopID = Integer.parseInt(input);
        if (currentMobPopID < 0) throw new Exception("Mob does not exist.");
        if (currentMobPopID > World.MAX_MOBS) throw new Exception("Mob does not exist.");
        if (World.getMob(currentMobPopID) == null) throw new Exception("Mob does not exist.");
      }

      else

      {
        Mobile tempMob = World.getMob(input);
        if (tempMob == null) throw new Exception("Mob does not exist.");
        currentMobPopID = tempMob.getID();
      }

      state = ADD_POP_FREQ;
    }

    catch (NumberFormatException nfe)  { echo("#rInvalid mobile id.#N\r\n");      return; }
    catch (Exception e)                { echo("#r" + e.getMessage() + "#N\r\n");  return; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processAddMobPopFreq(String input)

  {
    try

    {
      if (input.length() == 0) {
        errorMsg("Add population member cancelled.");
        state = MENU_MOB_POP;
        return; }

      double freq = Double.parseDouble(input);

      if ((freq < 0.0) || (freq > 100.0))
        throw new Exception("Frequency must be between 0 and 100.");

      if (mobPOP == null)
        mobPOP = new PopMember(ROOM, currentMobPopID, freq, true);
      else mobPOP.addType(currentMobPopID, freq);

      currentMobPop++;

      if ((currentMobPop >= numMobPopTypes) || (mobPOP.getTotalFrequency() >= 100.0))

      {
        ROOM.addMobPop(mobPOP);
        mobPOP = null;
        goodMsg("New mobile added to the population.");
        state = MENU_MOB_POP;
      }

      else state = ADD_POP_MOB;
    }

    catch (NumberFormatException nfe)  { echo("#rInvalid frequency.#N\r\n");      return; }
    catch (Exception e)                { echo("#r" + e.getMessage() + "#N\r\n");  return; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRemoveMobPop(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Remove population member cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > ROOM.getMobPopSize()))
        throw new Exception("Invalid population item.");

      p--;

      ROOM.removeMobPop(p);
      goodMsg("Mobile removed.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid population item."); }
    catch (Exception e)                { errorMsg(e.getMessage());             }

    state = MENU_MOB_POP;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processSortMobPop1(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Sort population cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > ROOM.getMobPopSize()))
        throw new Exception("Invalid population item.");

      p--;

      holderInteger = p;
      state = SORT_POP_TWO;
      return;
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid population item."); }
    catch (Exception e)                { errorMsg(e.getMessage());             }

    state = MENU_MOB_POP;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processSortMobPop2(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Sort population cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > ROOM.getMobPopSize()))
        throw new Exception("Invalid population item.");

      ROOM.mobPopSwap(holderInteger, p-1);
      goodMsg("Population re-ordered.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid population item."); }
    catch (Exception e)                { errorMsg(e.getMessage());             }

    state = MENU_MOB_POP;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditMobPop(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Edit population member cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > ROOM.getMobPopSize()))
        throw new Exception("Invalid population item.");

      p--;

      editMobPOP = ROOM.getMobPop(p);
      state = EDIT_POP_MENU;
      return;
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid population item."); }
    catch (Exception e)                { errorMsg(e.getMessage());             }

    state = MENU_MOB_POP;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditMobPopMenu(String input)

  {
    if ((input.length() == 0) || (input.equals("5"))) {
      state = MENU_MOB_POP;
      return; }

    if (input.equals("1"))

    {
      if (editMobPOP.getNumTypes() >= 9) {
        errorMsg("You can only have up to 9 mob types.");
        state = EDIT_POP_MENU;
        return; }

      if (editMobPOP.getTotalFrequency() >= 100.0) {
        errorMsg("Maximum total frequency of 100% already used.");
        state = EDIT_POP_MENU;
        return; }

      state = EDIT_POP_ADD;
    }

    else if (input.equals("2"))

    {
      if (editMobPOP.getNumTypes() == 1) {
        errorMsg("You cannot remove all mob types.");
        state = EDIT_POP_MENU;
        return; }

      state = EDIT_POP_REM;
    }

    else if (input.equals("3")) state = EDIT_POP_MNUM_ONE;
    else if (input.equals("4")) state = EDIT_POP_FREQ_ONE;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditMobPopAdd(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Add mobile type cancelled.");

      int mnum = Integer.parseInt(input);

      if ((mnum < 0) || (mnum > World.MAX_MOBS))
        throw new Exception("Mob does not exist.");

      if (World.getMob(mnum) == null) throw new Exception("Mob does not exist.");

      double freq = 100.0 - editMobPOP.getTotalFrequency();

      boolean added = editMobPOP.addType(mnum, freq);

      if (added) goodMsg("Mobile type added to population member.");
      else errorMsg("Unable to add mobile type to population member.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid Mobile ID number."); }
    catch (Exception e)                { errorMsg(e.getMessage());              }

    state = EDIT_POP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditMobPopRem(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Remove mobile type cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > editMobPOP.getNumTypes()))
        throw new Exception("Invalid index number.");

      boolean removed = editMobPOP.removeType(p-1);

      if (removed) goodMsg("Mobile type removed from population member.");
      else errorMsg("Unable to remove mobile type from population member.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid index number."); }
    catch (Exception e)                { errorMsg(e.getMessage());          }

    state = EDIT_POP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditMobPopMNum1(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Change Mobile Type cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > editMobPOP.getNumTypes()))
        throw new Exception("Invalid index number.");

      holderInteger = (p-1);
      state = EDIT_POP_MNUM_TWO;
      return;
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid index number."); }
    catch (Exception e)                { errorMsg(e.getMessage());          }

    state = EDIT_POP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditMobPopMNum2(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Change Mobile Type cancelled.");

      int mnum = Integer.parseInt(input);

      if ((mnum < 0) || (mnum > World.MAX_MOBS))
        throw new Exception("Mob does not exist.");

      if (World.getMob(mnum) == null) throw new Exception("Mob does not exist.");

      editMobPOP.setIDNum(holderInteger, mnum);
      goodMsg("Mobile type updated.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid Mobile ID."); }
    catch (Exception e)                { errorMsg(e.getMessage());       }

    state = EDIT_POP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditMobPopFreq1(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Change frequency cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > editMobPOP.getNumTypes()))
        throw new Exception("Invalid index number.");

      holderInteger = (p-1);
      state = EDIT_POP_FREQ_TWO;
      return;
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid index number."); }
    catch (Exception e)                { errorMsg(e.getMessage());          }

    state = EDIT_POP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditMobPopFreq2(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Change frequency cancelled.");

      double freq = Double.parseDouble(input);

      if ((freq < 0.0) || (freq > 100.0))
        throw new Exception("Frequency must be between 0 and 100.");

      editMobPOP.setFreq(holderInteger, freq);
      goodMsg("Frequency updated.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid frequency."); }
    catch (Exception e)                { errorMsg(e.getMessage());       }

    state = EDIT_POP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processItemPopMenu(String input)

  {
    if ((input.length() == 0) || (input.equals("5"))) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1")) state = ADD_IPOP_COUNT;

    else if (input.equals("2"))

    {
      if (ROOM.getItemPopSize() == 0) {
        errorMsg("There are no items to edit.");
        state = MENU_ITEM_POP;
        return; }

      state = EDIT_IPOP;
    }

    else if (input.equals("3"))

    {
      if (ROOM.getItemPopSize() == 0) {
        errorMsg("There are no items to remove.");
        state = MENU_ITEM_POP;
        return; }

      state = REM_IPOP;
    }

    else if (input.equals("4"))

    {
      if (ROOM.getItemPopSize() < 2) {
        errorMsg("You need at least 2 items to change the order.");
        state = MENU_ITEM_POP;
        return; }

      state = SORT_IPOP_ONE;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processAddItemPopCount(String input)

  {
    try

    {
      if (input.length() == 0) {
        errorMsg("Add population member cancelled.");
        state = MENU_ITEM_POP;
        return; }

      numItemPopTypes = Integer.parseInt(input);

      if ((numItemPopTypes < 1) || (numItemPopTypes > 9))
        throw new Exception("Input must be 1-9.");

      currentItemPop = 0;
      state = ADD_IPOP_ITEM;
    }

    catch (NumberFormatException nfe)  { echo("#rInput must be 1-9.#N\r\n");      return; }
    catch (Exception e)                { echo("#r" + e.getMessage() + "#N\r\n");  return; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processAddItemPopItem(String input)

  {
    try

    {
      if (input.length() == 0) {
        errorMsg("Add population member cancelled.");
        state = MENU_ITEM_POP;
        return; }

      if (number(input))

      {
        currentItemPopID = Integer.parseInt(input);
        if (currentItemPopID < 0) throw new Exception("Item does not exist.");
        if (currentItemPopID > World.MAX_ITEMS) throw new Exception("Item does not exist.");
        if (World.getItem(currentItemPopID) == null) throw new Exception("Item does not exist.");
      }

      else

      {
        Item tempItem = World.getItem(input);
        if (tempItem == null) throw new Exception("Item does not exist.");
        currentItemPopID = tempItem.getID();
      }

      state = ADD_IPOP_FREQ;
    }

    catch (NumberFormatException nfe)  { echo("#rInvalid item id.#N\r\n");        return; }
    catch (Exception e)                { echo("#r" + e.getMessage() + "#N\r\n");  return; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processAddItemPopFreq(String input)

  {
    try

    {
      if (input.length() == 0) {
        errorMsg("Add population member cancelled.");
        state = MENU_ITEM_POP;
        return; }

      double freq = Double.parseDouble(input);

      if ((freq < 0.0) || (freq > 100.0))
        throw new Exception("Frequency must be between 0 and 100.");

      if (itemPOP == null)
        itemPOP = new PopMember(ROOM, currentItemPopID, freq, false);
      else itemPOP.addType(currentItemPopID, freq);

      currentItemPop++;

      if ((currentItemPop >= numItemPopTypes) || (itemPOP.getTotalFrequency() >= 100.0))

      {
        ROOM.addItemPop(itemPOP);
        itemPOP = null;
        goodMsg("New item added to the population.");
        state = MENU_ITEM_POP;
      }

      else state = ADD_IPOP_ITEM;
    }

    catch (NumberFormatException nfe)  { echo("#rInvalid frequency.#N\r\n");      return; }
    catch (Exception e)                { echo("#r" + e.getMessage() + "#N\r\n");  return; }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRemoveItemPop(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Remove population member cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > ROOM.getItemPopSize()))
        throw new Exception("Invalid population item.");

      p--;

      ROOM.removeItemPop(p);
      goodMsg("Item removed.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid population item."); }
    catch (Exception e)                { errorMsg(e.getMessage());             }

    state = MENU_ITEM_POP;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processSortItemPop1(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Sort population cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > ROOM.getItemPopSize()))
        throw new Exception("Invalid population item.");

      p--;

      holderInteger = p;
      state = SORT_IPOP_TWO;
      return;
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid population item."); }
    catch (Exception e)                { errorMsg(e.getMessage());             }

    state = MENU_ITEM_POP;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processSortItemPop2(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Sort population cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > ROOM.getItemPopSize()))
        throw new Exception("Invalid population item.");

      ROOM.itemPopSwap(holderInteger, p-1);
      goodMsg("Population re-ordered.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid population item."); }
    catch (Exception e)                { errorMsg(e.getMessage());             }

    state = MENU_ITEM_POP;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditItemPop(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Edit population member cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > ROOM.getItemPopSize()))
        throw new Exception("Invalid population item.");

      p--;

      editItemPOP = ROOM.getItemPop(p);
      state = EDIT_IPOP_MENU;
      return;
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid population item."); }
    catch (Exception e)                { errorMsg(e.getMessage());             }

    state = MENU_ITEM_POP;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditItemPopMenu(String input)

  {
    if ((input.length() == 0) || (input.equals("5"))) {
      state = MENU_ITEM_POP;
      return; }

    if (input.equals("1"))

    {
      if (editItemPOP.getNumTypes() >= 9) {
        errorMsg("You can only have up to 9 item types.");
        state = EDIT_IPOP_MENU;
        return; }

      if (editItemPOP.getTotalFrequency() >= 100.0) {
        errorMsg("Maximum total frequency of 100% already used.");
        state = EDIT_IPOP_MENU;
        return; }

      state = EDIT_IPOP_ADD;
    }

    else if (input.equals("2"))

    {
      if (editItemPOP.getNumTypes() == 1) {
        errorMsg("You cannot remove all item types.");
        state = EDIT_IPOP_MENU;
        return; }

      state = EDIT_IPOP_REM;
    }

    else if (input.equals("3")) state = EDIT_IPOP_MNUM_ONE;
    else if (input.equals("4")) state = EDIT_IPOP_FREQ_ONE;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditItemPopAdd(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Add item type cancelled.");

      int inum = Integer.parseInt(input);

      if ((inum < 0) || (inum > World.MAX_ITEMS))
        throw new Exception("Item does not exist.");

      if (World.getItem(inum) == null) throw new Exception("Item does not exist.");

      double freq = 100.0 - editItemPOP.getTotalFrequency();

      boolean added = editItemPOP.addType(inum, freq);

      if (added) goodMsg("Item type added to population member.");
      else errorMsg("Unable to add item type to population member.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid Item ID number."); }
    catch (Exception e)                { errorMsg(e.getMessage());            }

    state = EDIT_IPOP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditItemPopRem(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Remove item type cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > editItemPOP.getNumTypes()))
        throw new Exception("Invalid index number.");

      boolean removed = editItemPOP.removeType(p-1);

      if (removed) goodMsg("Item type removed from population member.");
      else errorMsg("Unable to remove item type from population member.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid index number."); }
    catch (Exception e)                { errorMsg(e.getMessage());          }

    state = EDIT_IPOP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditItemPopMNum1(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Change Item Type cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > editItemPOP.getNumTypes()))
        throw new Exception("Invalid index number.");

      holderInteger = (p-1);
      state = EDIT_IPOP_MNUM_TWO;
      return;
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid index number."); }
    catch (Exception e)                { errorMsg(e.getMessage());          }

    state = EDIT_IPOP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditItemPopMNum2(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Change Item Type cancelled.");

      int inum = Integer.parseInt(input);

      if ((inum < 0) || (inum > World.MAX_ITEMS))
        throw new Exception("Item does not exist.");

      if (World.getItem(inum) == null) throw new Exception("Item does not exist.");

      editItemPOP.setIDNum(holderInteger, inum);
      goodMsg("Item type updated.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid Item ID."); }
    catch (Exception e)                { errorMsg(e.getMessage());     }

    state = EDIT_IPOP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditItemPopFreq1(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Change frequency cancelled.");

      int p = Integer.parseInt(input);

      if ((p < 1) || (p > editItemPOP.getNumTypes()))
        throw new Exception("Invalid index number.");

      holderInteger = (p-1);
      state = EDIT_IPOP_FREQ_TWO;
      return;
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid index number."); }
    catch (Exception e)                { errorMsg(e.getMessage());          }

    state = EDIT_IPOP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEditItemPopFreq2(String input)

  {
    try

    {
      if (input.length() == 0) throw new Exception("Change frequency cancelled.");

      double freq = Double.parseDouble(input);

      if ((freq < 0.0) || (freq > 100.0))
        throw new Exception("Frequency must be between 0 and 100.");

      editItemPOP.setFreq(holderInteger, freq);
      goodMsg("Frequency updated.");
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid frequency."); }
    catch (Exception e)                { errorMsg(e.getMessage());       }

    state = EDIT_IPOP_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processTerrainChange(String input)

  {
    boolean setOK = true;

    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    try

    {
      int selection = Integer.parseInt(input);

      switch (selection)

      {
        case 1:  ROOM.setTerrain(Room.TERRAIN_FLAT_NORMAL); break;
        case 2:  ROOM.setTerrain(Room.TERRAIN_FLAT_ICY); break;
        case 3:  ROOM.setTerrain(Room.TERRAIN_FLAT_MUDDY); break;
        case 4:  ROOM.setTerrain(Room.TERRAIN_FLAT_ROCKY); break;
        case 5:  ROOM.setTerrain(Room.TERRAIN_ROAD_DIRT); break;
        case 6:  ROOM.setTerrain(Room.TERRAIN_ROAD_STONE); break;
        case 7:  ROOM.setTerrain(Room.TERRAIN_MOUNTAIN_LEVEL); break;
        case 8:  ROOM.setTerrain(Room.TERRAIN_MOUNTAIN_HILLS); break;
        case 9:  ROOM.setTerrain(Room.TERRAIN_MOUNTAIN_ASCENDING); break;
        case 10: ROOM.setTerrain(Room.TERRAIN_MOUNTAIN_STEEP); break;
        case 11: ROOM.setTerrain(Room.TERRAIN_FOREST_BRUSH); break;
        case 12: ROOM.setTerrain(Room.TERRAIN_FOREST_SPARSE); break;
        case 13: ROOM.setTerrain(Room.TERRAIN_FOREST_DENSE); break;
        case 14: ROOM.setTerrain(Room.TERRAIN_SWAMP_LIGHT); break;
        case 15: ROOM.setTerrain(Room.TERRAIN_SWAMP_BOGGY); break;
        case 16: ROOM.setTerrain(Room.TERRAIN_WATER_SHALLOW); break;
        case 17: ROOM.setTerrain(Room.TERRAIN_WATER_DEEP); break;
        case 18: ROOM.setTerrain(Room.TERRAIN_WATER_SEA); break;
        case 19: ROOM.setTerrain(Room.TERRAIN_SPECIAL_AIRBORNE); break;
        case 20: ROOM.setTerrain(Room.TERRAIN_SPECIAL_SHIMMY); break;
        case 21: ROOM.setTerrain(Room.TERRAIN_SPECIAL_REPEL); break;
        case 22: ROOM.setTerrain(Room.TERRAIN_SPECIAL_CRAWL); break;
        case 23: state = MAIN_MENU; return;
        default: throw new Exception();
      }
    }

    catch (Exception e) { errorMsg("Invalid terrain type."); return; }

    goodMsg("Terrain set.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processRoomDelete(String input)

  {
    if (!input.equalsIgnoreCase("delete")) {
      echo("#rRoom deletion cancelled.#N\r\n");
      state = MAIN_MENU;
      return; }

    World.globalRoomDelete(ROOM.getID());
    World.save();

    myClient.popInterface();
    myClient.setClientState(CSTATE_NORMAL);
    myClient.getCharInfo().look("#r" + ROOM.getTitle() + "#r deleted.#N");
    WriteThread.addObject(ROOM.getZone());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getRoomMenu()

  {
    String exact = "Off";

    if (ROOM.getDescWrite().getFormat() == Writable.FORMAT_EXACT)
      exact = "On";

    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("                        #rRoom Editor#N                        ");
    menu.add("-");
    menu.add("Room#R:#N     " + ROOM.getTitle());
    menu.add("RoomID#R:#N   " + ROOM.getID());
    menu.add("Zone#R:#N     " + World.getZone(ROOM.getZoneID()).getName());
    menu.add("Exact#R:#N    " + exact);
    menu.add("Exits#R:#N    " + ROOM.getNumExits());
    menu.add("Looks#R:#N    " + ROOM.getNumTargs());
    menu.add("Flags#R:#N    " + Integer.toString(Flag.flagCount(ROOM.getFlags())));
    menu.add("Actions#R:#N  " + ROOM.getActionList().size());
    menu.add("Terrain#R:#N  " + terrainString(ROOM.getTerrain()));
    menu.add("-");
    menu.add("#g1.#N) Rename Room.                    #cA.#N) Change Terrain Type.");
    menu.add("#g2.#N) Write Description.              #cB.#N) Set Bank Branch.");
    menu.add("#g3.#N) Write Exact Description.        #cM.#N) Assign Actions.");
    menu.add("#g4.#N) Add/Remove Room Looks.          #cZ.#N) Change Zone.");
    menu.add("#g5.#N) Add/Remove Exit Looks.");
    menu.add("#g6.#N) Add/Remove Room Exit.");
    menu.add("#g7.#N) Add/Remove Room Flag.           #yH.#N) Help!");
    menu.add("#g8.#N) Edit Mobile Population.         #bX.#N) Delete Room.");
    menu.add("#g9.#N) Edit Item Population.           #rQ.#N) Save and Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String getActionMenu()

  {
    ArrayList menu = new ArrayList();
    ArrayList list = ROOM.getActionList();

    menu.add("center");
    menu.add("#n               Action Menu               #N");
    menu.add("-");
    menu.add("#nActions:#N");

    if (list.size() == 0) menu.add("  None.");

    for (int i=0; i<list.size(); i++)

    {
      String temp = "";
      String space = " ";
      Action A = (Action)list.get(i);

      if (i > 9) space = "";

      temp += "  (" + space + "#r" + (i+1) + "#N) ";
      temp += "[#c" + String.format("%1$5s", A.getID()) + "#N] ";
      temp += A.getName();
      menu.add(temp);
    }

    menu.add("-");
    menu.add("#nResponses:");
    menu.add("  #N(#r 1#N) Enter.");
    menu.add("  #N(#r 2#N) Exit.");
    menu.add("-");
    menu.add("#N[#c1#N] Add Action.");
    menu.add("#N[#c2#N] Remove Action.");
    menu.add("#N[#c3#N] Edit Response.");
    menu.add("#N[#c4#N] Copy Response.");
    menu.add("#N[#c5#N] Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getARFlagMenu()

  {
    ArrayList menu = new ArrayList();

    String temp = "";
    Flag[] flagList = ROOM.getFlags();
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

 public String getARLookMenu()

  {
    ArrayList menu = new ArrayList();
    int size = ROOM.getNumLooks();

    menu.add("center");
    menu.add("#nLooks#N");
    menu.add("-");

    for (int i=6; i<size; i++)
      menu.add("(" + (i-6+1) + ") " + ROOM.getLookTarg(i-6));

    if (size == 6) menu.add("There are no looks in this room.");

    menu.add("-");
    menu.add("#n[#c1#n]#N - Add Look");
    menu.add("#n[#c2#n]#N - Remove Look");
    menu.add("#n[#c3#n]#N - Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getBankList()

  {
    ArrayList menu = new ArrayList();
    String branch = "None";

    if (ROOM.getBankBranch() != null)
      branch = World.getBank(ROOM.getBankBranch().getChainID()).getName();

    menu.add("center");
    menu.add("#nBank Chain#N");
    menu.add("-");
    menu.add("#nCurrent Bank Chain:#N " + branch + "#N");
    menu.add("-");
    menu.add("#n[#c 0#n]#N - None");

    for (int i=0; i<World.getNumBanks(); i++)
    if (World.getBank(i) != null)

    {
      String temp = "";
      temp += "#n[#c" + tenSpace(i,1) + (i+1) + "#n]#N - ";
      temp += World.getBank(i).getName() + "#N";
      menu.add(temp);
    }

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nEnter Name Of The Bank Branch: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getARExitMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#nExit Looks#N");
    menu.add("-");
    menu.add("#nN#North    #nW#Nest");
    menu.add("#nS#Nouth    #nU#Np");
    menu.add("#nE#Nast     #nD#Nown");
    menu.add("-");
    menu.add("#n[#c1#n]#N - Change Exit Look");
    menu.add("#n[#c2#n]#N - Reset Exit Look");
    menu.add("#n[#c3#n]#N - Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getMobPopMenu()

  {
    ArrayList menu = new ArrayList();

    int size = ROOM.getMobPopSize();

    menu.add("center");
    menu.add("#nMobile Population#N");
    menu.add("-");

    if (size == 0) menu.add("There are no mobs populating this room.");
    else menu.add(" #n[ #RID #n]  [ #RRATE#n ]  [ #RMOBILE#n ]#N");

    menu.add("-");

    for (int i=0; i<size; i++)

    {
      String temp = "";
      PopMember PM = ROOM.getMobPop(i);
      int firstMob = PM.getIDNum(0);
      double frq = PM.getTotalFrequency();
      Mobile M = World.getMob(firstMob);

      temp += " #n[#c" + tenSpace((i+1),3) + (i+1) + "#n]#N ";
      temp += "#n(#g" + String.format("%1$5.5s", frq) + "#n)   ";

      if (!PM.isRandom())
        temp += "#n(#r" + firstMob + "#n)#N " + M.getName();
      else temp += "#RRandom#N";

      menu.add(temp);
    }

    if (size > 0) menu.add("-");
    menu.add("[#c1#N] Add Member");
    menu.add("[#c2#N] Edit Member");
    menu.add("[#c3#N] Remove Member");
    menu.add("[#c4#N] Change Pop Order");
    menu.add("[#c5#N] Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getMobPopEditMenu()

  {
    if (editMobPOP == null) return "";

    ArrayList menu = new ArrayList();

    int size = editMobPOP.getNumTypes();

    menu.add("center");
    menu.add("#MPopulation Member Editor#N");
    menu.add("-");

    if (size == 0) menu.add("There are no mobile types defined for this population member.");
    else menu.add(" #n[ #RID #n]  [ #RRATE#n ]  [ #RMOBILE#n ]#N");

    menu.add("-");

    for (int i=0; i<size; i++)

    {
      String temp = "";
      int mNum = editMobPOP.getIDNum(i);
      double frq = editMobPOP.getFreq(i);
      Mobile M = World.getMob(mNum);

      temp += " #n[#c" + tenSpace((i+1),3) + (i+1) + "#n]#N ";
      temp += "#n(#g" + String.format("%1$5.5s", frq) + "#n)   ";
      temp += "#n(#r" + mNum + "#n)#N " + M.getName();

      menu.add(temp);
    }

    if (size > 0) menu.add("-");
    menu.add("[#c1#N] Add a Mobile");
    menu.add("[#c2#N] Remove a Mobile");
    menu.add("[#c3#N] Change a Mobile ID");
    menu.add("[#c4#N] Change a Frequency");
    menu.add("[#c5#N] Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getItemPopMenu()

  {
    ArrayList menu = new ArrayList();

    int size = ROOM.getItemPopSize();

    menu.add("center");
    menu.add("#nItem Population#N");
    menu.add("-");

    if (size == 0) menu.add("There are no items populating this room.");
    else menu.add(" #n[ #RID #n]  [ #RRATE#n ]  [ #RITEM#n ]#N");

    menu.add("-");

    for (int i=0; i<size; i++)

    {
      String temp = "";
      PopMember PM = ROOM.getItemPop(i);
      int firstItem = PM.getIDNum(0);
      double frq = PM.getTotalFrequency();
      Item I = World.getItem(firstItem);

      temp += " #n[#c" + tenSpace((i+1),3) + (i+1) + "#n]#N ";
      temp += "#n(#g" + String.format("%1$5.5s", frq) + "#n)   ";

      if (!PM.isRandom())
        temp += "#n(#r" + firstItem + "#n)#N " + I.getName();
      else temp += "#RRandom#N";

      menu.add(temp);
    }

    if (size > 0) menu.add("-");
    menu.add("[#c1#N] Add Item");
    menu.add("[#c2#N] Edit Item");
    menu.add("[#c3#N] Remove Item");
    menu.add("[#c4#N] Change Pop Order");
    menu.add("[#c5#N] Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getItemPopEditMenu()

  {
    if (editItemPOP == null) return "";

    ArrayList menu = new ArrayList();

    int size = editItemPOP.getNumTypes();

    menu.add("center");
    menu.add("#MPopulation Member Item Editor#N");
    menu.add("-");

    if (size == 0) menu.add("There are no item types defined for this population member.");
    else menu.add(" #n[ #RID #n]  [ #RRATE#n ]  [ #RITEM#n ]#N");

    menu.add("-");

    for (int i=0; i<size; i++)

    {
      String temp = "";
      int iNum = editItemPOP.getIDNum(i);
      double frq = editItemPOP.getFreq(i);
      Item I = World.getItem(iNum);

      temp += " #n[#c" + tenSpace((i+1),3) + (i+1) + "#n]#N ";
      temp += "#n(#g" + String.format("%1$5.5s", frq) + "#n)   ";
      temp += "#n(#r" + iNum + "#n)#N " + I.getName();

      menu.add(temp);
    }

    if (size > 0) menu.add("-");
    menu.add("[#c1#N] Add Item");
    menu.add("[#c2#N] Remove Item");
    menu.add("[#c3#N] Change Item ID");
    menu.add("[#c4#N] Change Frequency");
    menu.add("[#c5#N] Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getARLinkMenu()

  {
    ArrayList menu = new ArrayList();

    String N = String.format("%1$5s", ROOM.getN());
    String S = String.format("%1$5s", ROOM.getS());
    String E = String.format("%1$5s", ROOM.getE());
    String W = String.format("%1$5s", ROOM.getW());
    String U = String.format("%1$5s", ROOM.getU());
    String D = String.format("%1$5s", ROOM.getD());

    if (ROOM.getN() == 0) N = "No Exit"; else N = "#n[#c" + N + "#n]#N " + World.getRoom(ROOM.getN()).getTitle();
    if (ROOM.getS() == 0) S = "No Exit"; else S = "#n[#c" + S + "#n]#N " + World.getRoom(ROOM.getS()).getTitle();
    if (ROOM.getE() == 0) E = "No Exit"; else E = "#n[#c" + E + "#n]#N " + World.getRoom(ROOM.getE()).getTitle();
    if (ROOM.getW() == 0) W = "No Exit"; else W = "#n[#c" + W + "#n]#N " + World.getRoom(ROOM.getW()).getTitle();
    if (ROOM.getU() == 0) U = "No Exit"; else U = "#n[#c" + U + "#n]#N " + World.getRoom(ROOM.getU()).getTitle();
    if (ROOM.getD() == 0) D = "No Exit"; else D = "#n[#c" + D + "#n]#N " + World.getRoom(ROOM.getD()).getTitle();

    menu.add("center");
    menu.add("#nExits#N");
    menu.add("-");
    menu.add("North: " + N);
    menu.add("South: " + S);
    menu.add("East:  " + E);
    menu.add("West:  " + W);
    menu.add("Up:    " + U);
    menu.add("Down:  " + D);

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nEnter Exit to Set: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getTerrainMenu()

  {
    int terrain = ROOM.getTerrain();
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#nTerrain Type#N");
    menu.add("-");
    menu.add("Current: #n" + terrainString(terrain));
    menu.add("-");
    menu.add("#n[#c 1#n]#N - Flat Normal");
    menu.add("#n[#c 2#n]#N - Flat Icy");
    menu.add("#n[#c 3#n]#N - Flat Muddy");
    menu.add("#n[#c 4#n]#N - Flat Rocky");
    menu.add("#n[#c 5#n]#N - Road Dirt");
    menu.add("#n[#c 6#n]#N - Road Stone");
    menu.add("#n[#c 7#n]#N - Mountain Level");
    menu.add("#n[#c 8#n]#N - Mountain Hills");
    menu.add("#n[#c 9#n]#N - Mountain Ascending");
    menu.add("#n[#c10#n]#N - Mountain Steep");
    menu.add("#n[#c11#n]#N - Forest Brush");
    menu.add("#n[#c12#n]#N - Forest Sparse");
    menu.add("#n[#c13#n]#N - Forest Dense");
    menu.add("#n[#c14#n]#N - Swamp Light");
    menu.add("#n[#c15#n]#N - Swamp Boggy");
    menu.add("#n[#c16#n]#N - Water Shallow");
    menu.add("#n[#c17#n]#N - Water Deep");
    menu.add("#n[#c18#n]#N - Water Sea");
    menu.add("#n[#c19#n]#N - Special Airborne");
    menu.add("#n[#c20#n]#N - Special Shimmy");
    menu.add("#n[#c21#n]#N - Special Repel");
    menu.add("#n[#c22#n]#N - Special Crawl");
    menu.add("#n[#c23#n]#N - Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String terrainString(int terrain)

  {
    if (terrain == Room.TERRAIN_FLAT_NORMAL       ) return "Flat Normal";
    if (terrain == Room.TERRAIN_FLAT_ICY          ) return "Flat Icy";
    if (terrain == Room.TERRAIN_FLAT_MUDDY        ) return "Flat Muddy";
    if (terrain == Room.TERRAIN_FLAT_ROCKY        ) return "Flat Rocky";
    if (terrain == Room.TERRAIN_ROAD_DIRT         ) return "Road Dirt";
    if (terrain == Room.TERRAIN_ROAD_STONE        ) return "Road Stone";
    if (terrain == Room.TERRAIN_MOUNTAIN_LEVEL    ) return "Mountain Level";
    if (terrain == Room.TERRAIN_MOUNTAIN_HILLS    ) return "Mountain Hills";
    if (terrain == Room.TERRAIN_MOUNTAIN_ASCENDING) return "Mountain Ascending";
    if (terrain == Room.TERRAIN_MOUNTAIN_STEEP    ) return "Mountain Steep";
    if (terrain == Room.TERRAIN_FOREST_BRUSH      ) return "Forest Brush";
    if (terrain == Room.TERRAIN_FOREST_SPARSE     ) return "Forest Sparse";
    if (terrain == Room.TERRAIN_FOREST_DENSE      ) return "Forest Dense";
    if (terrain == Room.TERRAIN_SWAMP_LIGHT       ) return "Swamp Light";
    if (terrain == Room.TERRAIN_SWAMP_BOGGY       ) return "Swamp Boggy";
    if (terrain == Room.TERRAIN_WATER_SHALLOW     ) return "Water Shallow";
    if (terrain == Room.TERRAIN_WATER_DEEP        ) return "Water Deep";
    if (terrain == Room.TERRAIN_WATER_SEA         ) return "Water Sea";
    if (terrain == Room.TERRAIN_SPECIAL_AIRBORNE  ) return "Special Airborne";
    if (terrain == Room.TERRAIN_SPECIAL_SHIMMY    ) return "Special Shimmy";
    if (terrain == Room.TERRAIN_SPECIAL_REPEL     ) return "Special Repel";
    if (terrain == Room.TERRAIN_SPECIAL_CRAWL     ) return "Special Crawl";

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}