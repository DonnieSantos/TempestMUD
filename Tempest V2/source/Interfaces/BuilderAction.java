import java.io.*;
import java.util.*;

public class BuilderAction extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int MAIN_MENU    = 0;
  public static final int SET_NAME     = 1;

  public static final int MENU_COMMAND = 2;
  public static final int INSERT_INDEX = 3;
  public static final int DELAY_MIN    = 4;
  public static final int DELAY_MAX    = 5;
  public static final int ADD_COMMAND  = 6;
  public static final int REM_COMMAND  = 7;
  public static final int DELETE_ACT   = 8;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private int state;
  private int commandIndex;
  private int delayMinimum;
  private int delayMaximum;
  private static Action ACTION;
  private boolean GLOBAL_DELETE;
  private boolean PROTOTYPE_ACTION;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public int viewMode()                { return NO_OUTPUT;    }
  public void echo(String s)           { utilityQueue.add(s); }
  public void addOutput(String s)      { return;              }
  public void addFightOutput(String s) { return;              }
  public void focusGained()            { return;              }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public BuilderAction(Client c, Action A, boolean prototype)

  {
    super(c);
    ACTION = A;
    PROTOTYPE_ACTION = prototype;
    GLOBAL_DELETE = false;
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
    active = false;
    menuMsg = "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    if (ACTION.getID() != -1) FileManager.saveActions();
    myClient.popInterface();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    if (state == MAIN_MENU)         return getActionMenu();
    else if (state == SET_NAME)     return "Enter A #rNew Name#n           : #N";
    else if (state == MENU_COMMAND) return getCommandMenu();
    else if (state == INSERT_INDEX) return "Select #rCommand Index#n       : #N";
    else if (state == DELAY_MIN)    return "Enter The #rMinimum Delay#n    : #N";
    else if (state == DELAY_MAX)    return "Enter The #rMaximum Delay#n    : #N";
    else if (state == ADD_COMMAND)  return "Enter The #rCommand#n          : #N";
    else if (state == REM_COMMAND)  return "Selected Command To #rRemove#N : #N";
    else if (state == DELETE_ACT)   return "Type 'delete' if you are sure: ";

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void tryGlobalDelete()

  {
    if (!PROTOTYPE_ACTION) {
      errorMsg("You can only delete actions from the global list.");
      state = MAIN_MENU;
      return; }

    state = DELETE_ACT;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processDeleteAction(String input)

  {
    if (!input.equalsIgnoreCase("delete")) {
      state = MAIN_MENU;
      return; }

    GLOBAL_DELETE = true;
    exit();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;

    String input = clearWhiteSpace(myClient.getCommand());

    if (state == MAIN_MENU)         processActionMenu(input);
    else if (state == SET_NAME)     processSetName(input);

    else if (state == MENU_COMMAND) processCommandMenu(input);
    else if (state == INSERT_INDEX) processInsertCommand(input);
    else if (state == DELAY_MIN)    processDelayMin(input);
    else if (state == DELAY_MAX)    processDelayMax(input);
    else if (state == ADD_COMMAND)  processAddCommand(input);
    else if (state == REM_COMMAND)  processRemCommand(input);
    else if (state == DELETE_ACT)   processDeleteAction(input);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processRemCommand(String input)

  {
    if (input.length() == 0) {
      errorMsg("Remove command cancelled.");
      state = MENU_COMMAND;
      return; }

    try

    {
      int index = Integer.parseInt(input)-1;

      if (index < 0) throw new Exception("Invalid index.");
      if (index >= ACTION.getNumCommands()) throw new Exception("That command does not exist.");

      ACTION.removeCommand(index);
      goodMsg("Command removed.");
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid index."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_COMMAND;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processAddCommand(String input)

  {
    if (input.length() == 0) {
      errorMsg("Add command cancelled.");
      state = MENU_COMMAND;
      return; }

    if (ACTION.getNumCommands() == commandIndex) ACTION.addCommand(input, delayMinimum, delayMaximum);
    else ACTION.insertCommand(input, delayMinimum, delayMaximum, commandIndex);

    state = MENU_COMMAND;
    goodMsg("Command added.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processDelayMin(String input)

  {
    if (input.length() == 0) {
      errorMsg("Add command cancelled.");
      state = MENU_COMMAND;
      return; }

    try

    {
      delayMinimum = Integer.parseInt(input);

      if (delayMinimum < 0)
        throw new Exception("The minimum delay must be zero or greater.");

      if (delayMinimum > 10000)
        throw new Exception("The minimum delay must be less or equal to 10,000.");

      state = DELAY_MAX;
      return;
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid minimum delay."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_COMMAND;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processDelayMax(String input)

  {
    if (input.length() == 0) {
      errorMsg("Add command cancelled.");
      state = MENU_COMMAND;
      return; }

    try

    {
      delayMaximum = Integer.parseInt(input);

      if (delayMaximum < 0)
        throw new Exception("Invalid maximum delay.");

      if (delayMaximum > 10000)
        throw new Exception("The maximum delay must be less than or equal to 10,000.");

      if (delayMaximum < delayMinimum)
        throw new Exception("The maximum delay cannot be less than the minimum delay.");

      state = ADD_COMMAND;
      return;
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid maximum delay."); }
    catch (Exception e) { errorMsg(e.getMessage()); }

    state = MENU_COMMAND;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processInsertCommand(String input)

  {
    if (input.length() == 0) {
      errorMsg("Add command cancelled.");
      state = MENU_COMMAND;
      return; }

    try

    {
      commandIndex = Integer.parseInt(input)-1;

      if (commandIndex < 0) throw new Exception();
      if (commandIndex > ACTION.getNumCommands()) throw new Exception();

      state = DELAY_MIN;
      return;
    }

    catch (Exception e) { errorMsg("Invalid index."); }

    state = MENU_COMMAND;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processCommandMenu(String input)

  {
    if ((input.length() == 0) || (input.equals("3"))) {
      state = MAIN_MENU;
      return; }

    if (input.equals("1"))  state = INSERT_INDEX;
    if (input.equals("2"))

    {
      if (ACTION.getNumCommands() == 0)
        errorMsg("There are no commands to remove.");
      else state = REM_COMMAND;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processActionMenu(String input)

  {
    if (input.length() <= 0) exit();
    else if (input.equals("1")) state = SET_NAME;
    else if (input.equals("2")) ACTION.setHostile(!ACTION.getHostile());
    else if (input.equals("3")) ACTION.setRepeats(!ACTION.getRepeats());
    else if (input.equals("4")) ACTION.setRandom(!ACTION.getRandom());
    else if (input.equals("5")) state = MENU_COMMAND;
    else if (input.equalsIgnoreCase("Q")) exit();

    // Add support for X to delete an action from the global list.
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processSetName(String input)

  {
    if (input.length() == 0) {
      errorMsg("Name change cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > ACTION_NAME_LENGTH) {
      errorMsg("Name exceeds the maximum length.");
      state = MAIN_MENU;
      return; }

    ACTION.setName(input);

    state = MAIN_MENU;
    goodMsg("Action name set to: " + input + "#g.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void exit()

  {
    if (GLOBAL_DELETE)

    {
      myClient.popInterface();
      myClient.setClientState(CSTATE_NORMAL);
      myClient.getCharInfo().blindEmote("has finished editing an action.");

      World.globalActionDelete(ACTION.getID());
      FileManager.saveActions();
      myClient.getCharInfo().look("#rItem " + ACTION.getID() + " globally deleted.#N");

      return;
    }

    myClient.popInterface();
    myClient.setClientState(CSTATE_NORMAL);
    if (ACTION.isInfinite()) ACTION.setRepeats(false);
    if (ACTION.getID() != -1) FileManager.saveActions();
    myClient.getCharInfo().look(ACTION.getName() + " saved.");
    myClient.getCharInfo().blindEmote("has finished editing an action.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static void enterActionBuilder(Client myClient, String input)

  {
    boolean prototype_action = true;

    if (myClient.getCharInfo().getPlayerState() == PSTATE_FIGHTING) {
      myClient.getCharInfo().echo("You can't do that while fighting.");
      return; }

    if (input.length() == 0) {
      BuilderGlobalAction.enterGlobalActionBuilder(myClient);
      return; }

    Action A = null;
    int num = 0;

    try {
      num = Integer.parseInt(input);
      A = World.getAction(num); }

    catch (NumberFormatException nfe) {
      myClient.getCharInfo().echo("Action not found.");
      return; }

    if (A == null) {
      myClient.getCharInfo().echo("That action does not exist.");
      return; }

    if (A.getID() == -1) prototype_action = false;

    myClient.pushInterface(new BuilderAction(myClient, A, prototype_action));
    myClient.getCharInfo().blindEmote("starts editing an action.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static void enterActionBuilder(Client myClient, Action A)

  {
    boolean prototype_action = true;

    if (A.getID() == -1) prototype_action = false;

    myClient.pushInterface(new BuilderAction(myClient, A, prototype_action));
    myClient.getCharInfo().blindEmote("starts editing an action.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String getActionMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("                       #rAction Editor#N                       ");
    menu.add("-");
    menu.add("Action:    " + ACTION.getName());
    menu.add("ActionID:  " + ACTION.getID());
    menu.add("Hostile:   " + ACTION.getHostile());
    menu.add("Repeats:   " + ACTION.getRepeats());
    menu.add("Random:    " + ACTION.getRandom());
    menu.add("Commands:  " + ACTION.getNumCommands());
    menu.add("-");
    menu.add("#g1.#N) Rename Action.");
    menu.add("#g2.#N) Toggle Hostile.");
    menu.add("#g3.#N) Toggle Repeats.");
    menu.add("#g4.#N) Toggle Random.                  " + condColor("#b",PROTOTYPE_ACTION) + "X.#N) Delete Action.");
    menu.add("#g5.#N) Edit Commands.                  #rQ.#N) Save and Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String getCommandMenu()

  {
    ArrayList menu = new ArrayList();
    int size = ACTION.getNumCommands();
    String[] commands = new String [size];
    int[] minDelays   = new int [size];
    int[] maxDelays   = new int [size];

    menu.add("center");
    menu.add("#nCommands#N");

    if (size > 0) menu.add("-");
    if (size > 0) menu.add("#nIndex   Min     Max   Command#N");

    menu.add("-");

    for (int i=0; i<size; i++)

    {
      commands[i]  = ACTION.getCommand(i);
      minDelays[i] = ACTION.getDelayMin(i);
      maxDelays[i] = ACTION.getDelayMax(i);
    }

    for (int i=0; i<size; i++)

    {
      String temp = "";
      temp += "#n(#r" + String.format("%1$3d", i+1) + "#n) #N";
      temp += "#n[#g" + String.format("%1$5d", minDelays[i]) + "#n] #N";
      temp += "#n[#g" + String.format("%1$5d", maxDelays[i]) + "#n] #N";
      temp += commands[i];

      menu.add(temp);
    }

    if (size == 0) menu.add("#gThere are no commands for this action.");

    menu.add("-");
    menu.add("[#c1#N] Add Command.");
    menu.add("[#c2#N] Remove Command.");
    menu.add("[#c3#N] Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}