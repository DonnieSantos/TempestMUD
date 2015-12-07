import java.io.*;
import java.util.*;

public class BuilderClan extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int MAIN_MENU        = 0;
  public static final int SET_NAME         = 1;
  public static final int RENAME_SELECT    = 2;
  public static final int RENAME_MNAME     = 3;
  public static final int RENAME_FNAME     = 4;

  public static final int MENU_RANKS       = 5;
  public static final int ADD_RANK         = 6;
  public static final int REM_RANK         = 7;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private Clan CLAN;
  private int state;
  private int intHolder;
  private String strHolder;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public int viewMode()                { return NO_OUTPUT;    }
  public void echo(String s)           { utilityQueue.add(s); }
  public void addOutput(String s)      { return;              }
  public void addFightOutput(String s) { return;              }
  public void focusGained()            { return;              }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public BuilderClan(Client c, Clan clan)

  {
    super(c);
    CLAN = clan;
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
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    FileManager.saveClan(CLAN);
    myClient.popInterface();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    if (state == MAIN_MENU)              return getClanMenu();
    else if (state == SET_NAME)          return "Enter New Clan Name: ";

    else if (state == RENAME_SELECT)     return getRanksMenu();
    else if (state == RENAME_MNAME)      return "Enter New #bMale#N Rank Name   #R:#N ";
    else if (state == RENAME_FNAME)      return "Enter New #yFemale#N Rank Name #R:#N ";

    else if (state == MENU_RANKS)        return getAddRemoveRanksMenu();
    else if (state == ADD_RANK)          return "Select #rRank No.#N To Add    #R:#N ";
    else if (state == REM_RANK)          return "Select #rRank No.#N To Remove #R:#N ";

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;

    String input = clearWhiteSpace(myClient.getCommand());

    if (state == MAIN_MENU)             processClanMenu(input);
    else if (state == SET_NAME)         processName(input);
    else if (state == RENAME_SELECT)    processRenameSelect(input);
    else if (state == RENAME_MNAME)     processRenameMale(input);
    else if (state == RENAME_FNAME)     processRenameFemale(input);

    else if (state == MENU_RANKS)       processRanksMenu(input);
    else if (state == ADD_RANK)         processRanksAdd(input);
    else if (state == REM_RANK)         processRanksRem(input);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRanksRem(String input)

  {
    if (input.length() == 0) {
      errorMsg("Rank removal cancelled.");
      state = MENU_RANKS;
      return; }

    try

    {
      int num = Integer.parseInt(input);
      if (num < 0)
        throw new Exception("That rank does not exist.");
      if (num == 0)
        throw new Exception("Can't remove the applicant rank.");
      if (num == CLAN.getNumRanks() + 1)
        throw new Exception("Can't remove the immortal rank.");
      if (num > CLAN.getNumRanks() + 1)
        throw new Exception("Rank does not exist.");

      CLAN.getMaleRanks().remove(num);
      CLAN.getFemaleRanks().remove(num);

      state = MENU_RANKS;
      goodMsg("Rank removed.");
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid rank number."); }
    catch (Exception e)               { errorMsg(e.getMessage());         }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRanksAdd(String input)

  {
    if (input.length() == 0) {
      errorMsg("Rank addition cancelled.");
      state = MENU_RANKS;
      return; }

    try

    {
      int num = Integer.parseInt(input);

      if (num < 1)
        throw new Exception("Can't insert a rank before the applicant rank.");
      if (num > CLAN.getNumRanks() + 1)
        throw new Exception("Can't insert a rank after the immortal rank.");

      CLAN.getMaleRanks().add(num, "Generic Male Rank");
      CLAN.getFemaleRanks().add(num, "Generic Female Rank");

      state = MENU_RANKS;
      goodMsg("Rank added.");
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid rank number."); }
    catch (Exception e)               { errorMsg(e.getMessage());         }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRanksMenu(String input)

  {
    if ((input.length() == 0) || input.equals("3")) {
      menuMsg = "";
      state = MAIN_MENU;
      return; }

    if (input.equals("1")) state = ADD_RANK;
    else if (input.equals("2")) state = REM_RANK;
    else menuMsg = "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRenameFemale(String input)

  {
    if (input.length() == 0) {
      errorMsg("Rename Rank cancelled.");
      state = RENAME_SELECT;
      return; }

    ArrayList mRanks = CLAN.getMaleRanks();
    ArrayList fRanks = CLAN.getFemaleRanks();

    mRanks.set(intHolder, strHolder);
    fRanks.set(intHolder, input);

    goodMsg("Rank renamed.");
    state = RENAME_SELECT;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRenameMale(String input)

  {
    if (input.length() == 0) {
      errorMsg("Rename Rank cancelled.");
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
        throw new Exception("Invalid rank number.");
      if (num >= CLAN.getNumRanks() + 2)
        throw new Exception("Invalid rank number.");

      intHolder = num;
      state = RENAME_MNAME;
    }

    catch (NumberFormatException nfe) { errorMsg("Invalid rank number."); }
    catch (Exception e)               { errorMsg(e.getMessage());         }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processClanMenu(String input)

  {
    if (input.length() <= 0)               { exit();                }
    else if (input.equals("1"))            { state = SET_NAME;      }
    else if (input.equals("2"))            { state = RENAME_SELECT; }
    else if (input.equals("3"))            { state = MENU_RANKS;    }
    else if (input.equals("4"))            { writeDesc();           }
    else if (input.equalsIgnoreCase("Q"))  { exit();                }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void writeDesc()

  {
    getCharInfo().getClient().msg(Ansi.clearScreen);
    Composer newComposer = new Composer(myClient.getCharInfo(), CLAN.getClanInfo());
    newComposer.setLineWidth(INFO_WIDTH);
    newComposer.displayInfo();
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processName(String input)

  {
    if (input.length() == 0) {
      errorMsg("Modify Name cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > 80) {
      errorMsg("Name is too long. Must be 80 characters or less.");
      state = MAIN_MENU;
      return; }

    CLAN.setName(input);
    goodMsg("Clan name set to: " + input + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getClanMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#mClan Editor");
    menu.add("-");

    menu.add("Name:     " + CLAN.getName());
    menu.add("ClanID:   " + CLAN.getID());
    menu.add("Members:  " + CLAN.getNumMembers());
    menu.add("Ranks:    " + CLAN.getNumRanks());
    menu.add("-");

    menu.add("#g1.#N) Rename Clan.");
    menu.add("#g2.#N) Rename Rank.");
    menu.add("#g3.#N) Add/Remove Rank.                    #yH.#N) Help!          ");
    menu.add("#g4.#N) Write Clan Info.                    #rQ.#N) Quit.          ");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getAddRemoveRanksMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("      #n[#bMale#n]                  [#yFemale#n]#N");
    menu.add("-");

    int numRanks = CLAN.getNumRanks() + 2;

    for (int i=0; i<numRanks; i++)

    {
      String temp = "";
      temp += "#n(#r" + String.format("%1$3d", i) + "#n) #N"; 
      temp += String.format("%1$-24s", CLAN.getMaleRank(i));
      temp += CLAN.getFemaleRank(i);
      menu.add(temp);
    }

    String box = boxify(menu,0) + "\r\n\n";

    box += "#n[#c1#n]#N Add Rank\r\n";
    box += "#n[#c2#n]#N Remove Rank\r\n";
    box += "#n[#c3#n]#N Exit\r\n\n";

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + box + "Rank Menu Selection       #R:#N ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getRanksMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("      #n[#bMale#n]                        [#yFemale#n]#N");
    menu.add("-");

    int numRanks = CLAN.getNumRanks() + 2;

    for (int i=0; i<numRanks; i++)

    {
      String temp = "";
      temp += "#n[#c" + String.format("%1$3d", i) + "#n] #N";
      temp += String.format("%1$-30s", CLAN.getMaleRank(i));
      temp += CLAN.getFemaleRank(i);
      menu.add(temp);
    }

    String box = "#N\r\n\n" + boxify(menu,0) + "\r\n\n";

    return Ansi.clearScreen + "\r\n" + menuMsg + box + "Select #rRank No.#N To Rename  #R:#N ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static void enterClanBuilder(Client myClient, String input)

  {
    if (myClient.getCharInfo().getPlayerState() == PSTATE_FIGHTING) {
      myClient.getCharInfo().echo("You can't do that while fighting.");
      return; }

    Clan clan = null;
    int num = 0;

    try

    {
      num = Integer.parseInt(input);
      clan = World.getClan(num);
    }

    catch (NumberFormatException nfe)

    {
      clan = World.getClan(input);
    }

    if (clan == null) {
      myClient.getCharInfo().echo("That clan does not exist.");
      return; }

    myClient.pushInterface(new BuilderClan(myClient, clan));
    myClient.getCharInfo().blindEmote("starts editing a clan.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void exit()

  {
    FileManager.saveClan(CLAN);
    myClient.popInterface();
    myClient.setClientState(CSTATE_NORMAL);
    myClient.getCharInfo().look(CLAN.getName() + " saved.");
    myClient.getCharInfo().blindEmote("has finished editing a clan.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}