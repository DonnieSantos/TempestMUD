import java.io.*;
import java.util.*;

public class BuilderGlobalAction extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int MAIN_MENU     = 0;
  public static final int SET_NAME      = 1;
  public static final int EDIT_ACTION   = 2;
  public static final int REMOVE_ACTION = 3;
  public static final int LIST_ACTIONS  = 4;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private int state;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public int viewMode()                { return NO_OUTPUT;        }
  public void onDisconnect()           { myClient.popInterface(); }
  public void echo(String s)           { utilityQueue.add(s);     }
  public void addOutput(String s)      { return;                  }
  public void addFightOutput(String s) { return;                  }
  public void focusGained()            { return;                  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public BuilderGlobalAction(Client c)

  {
    super(c);
    state = MAIN_MENU;
    c.setClientState(CSTATE_BUILDING);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    switch (state)

    {
      case MAIN_MENU:     return getGlobalActionMenu();
      case SET_NAME:      return "Enter Action Name: ";
      case EDIT_ACTION:   return "Enter Action ID to Edit: ";
      case REMOVE_ACTION: return "Enter Action ID to Remove: ";
      case LIST_ACTIONS:  return "Enter Action List Query: ";
      default:            return "";
    }
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

    myClient.setClientState(CSTATE_BUILDING);
    flushUtilityQueue();
    myClient.msg(output + getPrompt());
    active = false;
    menuMsg = "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;

    String input = clearWhiteSpace(myClient.getCommand());

    if (state == MAIN_MENU)          processGlobalActionMenu(input);
    else if (state == SET_NAME)      processSetName(input);
    else if (state == EDIT_ACTION)   processEditAction(input);
    else if (state == REMOVE_ACTION) processRemoveAction(input);
    else if (state == LIST_ACTIONS)  processListActions(input);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processGlobalActionMenu(String input)

  {
    if (input.length() <= 0) exit();

    else if (input.equals("1")) state = SET_NAME;
    else if (input.equals("2")) state = EDIT_ACTION;
    else if (input.equals("3")) state = REMOVE_ACTION;
    else if (input.equals("4")) state = LIST_ACTIONS;
    else if (input.equalsIgnoreCase("Q")) exit();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processListActions(String input)

  {
    PageBreak PB = new PageBreak(myClient, alist(input));
    PB.setHalting(true);
    myClient.pushInterface(PB);
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processRemoveAction(String input)

  {
    if (input.length() == 0) {
      errorMsg("Action removal cancelled.");
      state = MAIN_MENU;
      return; }

    try

    {
      int num;

      if (input.length() == 0) throw new Exception();

      num = Integer.parseInt(input);
      Action A = World.getAction(num);

      if (A == null) throw new Exception();

      World.removeAction(num);
      FileManager.saveActions();
      goodMsg("Action " + A.getName() + " removed.");
    }

    catch (Exception e) { errorMsg("Action not found."); }

    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processEditAction(String input)

  {
    if (input.length() == 0) {
      errorMsg("Action editing cancelled.");
      state = MAIN_MENU;
      return; }

    try

    {
      int num;

      num = Integer.parseInt(input);
      Action A = World.getAction(num);

      if (A == null) throw new Exception();

      BuilderAction.enterActionBuilder(myClient, A);
      goodMsg("Action editing complete.");
    }

    catch (Exception e) { errorMsg("Action not found."); }

    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processSetName(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      errorMsg("Action addition cancelled.");
      return; }

    if (visibleSize(input) > ACTION_NAME_LENGTH) {
      state = MAIN_MENU;
      errorMsg("Name exceeds the maximum length.");
      return; }

    int num = World.createAction(input);;

    if (num == -1) {
      errorMsg("Action list is full.");
      return; }

    Action A = World.getAction(num);
    FileManager.saveActions();
    state = MAIN_MENU;
    goodMsg("Action #r" + A.getName() + "#g created with ID: #n" + num + "#g.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void exit()

  {
    myClient.popInterface();
    myClient.setClientState(CSTATE_NORMAL);
    myClient.getCharInfo().look("Global Action Editor Exited.");
    myClient.getCharInfo().blindEmote("has left the actions builder.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static void enterGlobalActionBuilder(Client myClient)

  {
    if (myClient.getCharInfo().getPlayerState() == PSTATE_FIGHTING) {
      myClient.getCharInfo().echo("You can't do that while fighting.");
      return; }

    myClient.pushInterface(new BuilderGlobalAction(myClient));
    myClient.getCharInfo().blindEmote("enters the actions builder.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String getGlobalActionMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("                   #gGlobal Action Editor#N                    ");
    menu.add("-");
    menu.add("#g1.#N) Add Action.");
    menu.add("#g2.#N) Edit Action.");
    menu.add("#g3.#N) Remove Action.");
    menu.add("#g4.#N) List Actions.               #rQ.#N) Exit.");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String alist(String rem)

  {
    if (rem.length() == 0) rem = "all";

    if (number(first(removeDashes(rem))))
      return alistNum(rem);

    String str = "#nAction List:";
    int size = World.getActionSize();

    for (int i=0; i<size; i++)
    if (World.getAction(i) != null)

    {
      int aID = World.getAction(i).getID();
      String aName = World.getAction(i).getName();
      String cName = removeColors(aName).toLowerCase();
      String xs = tenSpace(aID, 5);

      if ((abbreviation(rem, cName)) || (rem.equals("all")))
        str += "\r\n#N[#c" + aID + "#N]" + xs + aName + "#N";
    }

    return str;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String alistNum(String rem)

  {
    String str = "#nAction List:";

    try

    {
      rem = removeDashes(rem);
      int start = Integer.parseInt(first(rem));

      if (last(rem).length() <= 0)

      {
        Action A = World.getAction(start);

        if (A == null) return "There is no action by that number.";

        str += "\r\n#N[#c" + start + "#N]" + tenSpace(start, 5);
        str += A.getName() + "#N";
        return str;
      }

      int end = Integer.parseInt(last(rem));
      int max = World.getActionSize()-1;

      if (start < 0) start = 0;
      if (end > max) end = max;

      for (int i=start; i<=end; i++)

      {
        Action A = World.getAction(i);
        if (A != null) str += "\r\n#N[#c" + i + "#N]" + tenSpace(i,5);
        if (A != null) str += A.getName() + "#N";
      }
    }

    catch (Exception e) { str = alist("all"); }

    return str;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}