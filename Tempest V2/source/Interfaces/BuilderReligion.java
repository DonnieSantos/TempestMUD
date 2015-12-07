import java.io.*;
import java.util.*;

public class BuilderReligion extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int MAIN_MENU        = 0;
  public static final int SET_NAME         = 1;
  public static final int RENAME_SELECT    = 2;
  public static final int RENAME_MNAME     = 3;
  public static final int RENAME_FNAME     = 4;
  public static final int DIETY_NAME       = 5;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private int state;
  private int intHolder;
  private String strHolder;
  private Religion RELIGION;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public int viewMode()                { return NO_OUTPUT;    }
  public void echo(String s)           { utilityQueue.add(s); }
  public void addOutput(String s)      { return;              }
  public void addFightOutput(String s) { return;              }
  public void focusGained()            { return;              }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public BuilderReligion(Client c, Religion religion)

  {
    super(c);
    state = MAIN_MENU;
    RELIGION = religion;
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
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    myClient.popInterface();
    FileManager.saveReligion(RELIGION);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    if (state == MAIN_MENU)              return getReligionMenu();
    else if (state == SET_NAME)          return "Enter New Religion Name: ";
    else if (state == DIETY_NAME)        return "Enter the Name of this Religion's #cPatron Diety#R:#N ";

    else if (state == RENAME_SELECT)     return getRanksMenu();
    else if (state == RENAME_MNAME)      return "Enter New #bMale#N Degree Name   #R:#N ";
    else if (state == RENAME_FNAME)      return "Enter New #yFemale#N Degree Name #R:#N ";

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;

    String input = clearWhiteSpace(myClient.getCommand());

    if (state == MAIN_MENU)             processReligionMenu(input);
    else if (state == SET_NAME)         processName(input);
    else if (state == RENAME_SELECT)    processRenameSelect(input);
    else if (state == RENAME_MNAME)     processRenameMale(input);
    else if (state == RENAME_FNAME)     processRenameFemale(input);
    else if (state == DIETY_NAME)       processDietyName(input);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processDietyName(String input)

  {
    if (input.length() == 0) {
      errorMsg("Set Diety Name cancelled.");
      state = MAIN_MENU;
      return; }

      if (!Char.exists(input))

    {
      errorMsg("There is no character by that name.");
      state = MAIN_MENU;
      return;
    }

    Entity DIETY = World.findCharacter(input);

    if (DIETY == null)

    {
      errorMsg("Error loading character.");
      state = MAIN_MENU;
      return;
    }

    if (DIETY.getLevel() < 100)

    {
      errorMsg("That character is not a God.");
      state = MAIN_MENU;
      return;
    }

    RELIGION.setDietyName(properName(input));
    goodMsg("Patron Diety Name set.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRenameFemale(String input)

  {
    if (input.length() == 0) {
      errorMsg("Degree Rename Cancelled.");
      state = RENAME_SELECT;
      return; }

    ArrayList mRanks = RELIGION.getMaleRanks();
    ArrayList fRanks = RELIGION.getFemaleRanks();

    mRanks.set(intHolder, strHolder);
    fRanks.set(intHolder, input);

    goodMsg("Degree renamed.");
    state = RENAME_SELECT;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRenameMale(String input)

  {
    if (input.length() == 0) {
      errorMsg("Degree Rename Cancelled.");
      state = RENAME_SELECT;
      return; }

    strHolder = input;
    state = RENAME_FNAME;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRenameSelect(String input)

  {
    if (input.length() == 0) {
      menuMsg = "";
      state = MAIN_MENU;
      return; }

    try

    {
      int num = Integer.parseInt(input);

      if (num < 0)
        throw new Exception("Invalid degree number.");
      if (num > RELIGION.getNumRanks())
        throw new Exception("Invalid degree number.");

      intHolder = num;
      state = RENAME_MNAME;
    }

    catch (NumberFormatException nfe)  { errorMsg("Invalid degree number."); }
    catch (Exception e)                { errorMsg(e.getMessage());           }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processReligionMenu(String input)

  {
    if (input.length() <= 0)               { exit();                }
    else if (input.equals("1"))            { state = SET_NAME;      }
    else if (input.equals("2"))            { state = RENAME_SELECT; }
    else if (input.equals("3"))            { state = DIETY_NAME;    }
    else if (input.equals("4"))            { writeDesc();           }
    else if (input.equalsIgnoreCase("Q"))  { exit();                }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void writeDesc()

  {
    getCharInfo().getClient().msg(Ansi.clearScreen);
    Composer newComposer = new Composer(myClient.getCharInfo(), RELIGION.getReligionInfo());
    newComposer.setLineWidth(INFO_WIDTH);
    newComposer.displayInfo();
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processName(String input)

  {
    if (input.length() == 0) {
      errorMsg("Name setting cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > 80) {
      errorMsg("Name is too long. Must be 80 characters or less.");
      state = MAIN_MENU;
      return; }

    RELIGION.setName(input);
    goodMsg("Religion Name Set To: " + input + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getReligionMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#yReligion Editor");
    menu.add("-");

    menu.add("Name:     " + RELIGION.getName());
    menu.add("IDNum:    " + RELIGION.getID());
    menu.add("Diety:    " + RELIGION.getDietyName());
    menu.add("Members:  " + RELIGION.getNumMembers());
    menu.add("-");

    menu.add("#g1.#N) Rename Religion.");
    menu.add("#g2.#N) Rename Degree.");
    menu.add("#g3.#N) Set Diety Name.                     #yH.#N) Help!          ");
    menu.add("#g4.#N) Write Religion Info.                #rQ.#N) Quit.          ");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getRanksMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("      #n[#bMale#n]                        [#yFemale#n]#N");
    menu.add("-");

    for (int i=0; i<=RELIGION.getNumRanks(); i++)

    {
      String temp = "";
      temp += "#n[#c" + String.format("%1$3d", i) + "#n] #N";
      temp += String.format("%1$-30s", RELIGION.getMaleRank(i)) + "#n] #N";
      temp += xchars(RELIGION.getMaleRank(i).length()-visibleSize(RELIGION.getMaleRank(i))," ");
      temp += RELIGION.getFemaleRank(i);
      menu.add(temp);
    }

    String box = "#N\r\n\n" + boxify(menu,0) + "\r\n\n";

    return Ansi.clearScreen + "\r\n" + menuMsg + box + "Select #rDegree No.#N To Rename  #R:#N ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static void enterBuilder(Client myClient, String input)

  {
    if (myClient.getCharInfo().getPlayerState() == PSTATE_FIGHTING) {
      myClient.getCharInfo().echo("You can't do that while fighting.");
      return; }

    Religion religion = null;
    int num = 0;

    try

    {
      num = Integer.parseInt(input);
      religion = World.getReligion(num);
    }

    catch (NumberFormatException nfe)

    {
      religion = World.getReligion(input);
    }

    if (religion == null) {
      myClient.getCharInfo().echo("That religion does not exist.");
      return; }

    myClient.pushInterface(new BuilderReligion(myClient, religion));
    myClient.getCharInfo().blindEmote("starts editing a religion.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void exit()

  {
    myClient.popInterface();
    FileManager.saveReligion(RELIGION);
    myClient.setClientState(CSTATE_NORMAL);
    myClient.getCharInfo().look(RELIGION.getName() + " saved.");
    myClient.getCharInfo().blindEmote("has finished editing a religion.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}