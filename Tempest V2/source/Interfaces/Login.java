import java.io.*;
import java.util.*;

public class Login extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final int NO_PROMPT          = -1;
  private static final int ENTER_NAME         =  0;
  private static final int ENTER_PASSWORD     =  1;
  private static final int CONFIRM_CREATE     =  2;
  private static final int ENTER_NEW_PASSWORD =  3;
  private static final int ACCOUNT_MENU       =  4;

  private static final int NEWCHAR_NAME       =  5;
  private static final int NEWCHAR_CONFIRM    =  6;
  private static final int CREATION_GENDER    =  7;
  private static final int CREATION_CLASS     =  8;
  private static final int CREATION_CITY      =  9;
  private static final int DELETION_NAME      =  10;
  private static final int DELETION_CONFIRM   =  11;
  private static final int DELETION_POSITIVE  =  12;
  private static final int PASSWORD_OLD       =  13;
  private static final int PASSWORD_NEW       =  14;
  private static final int PASSWORD_CONFIRM   =  15;
  private static final int VIEW_STATS         =  16;
  private static final int EMAIL_ADDRESS      =  17;
  private static final int DELETE_ACCOUNT     =  18;
  private static final int DEL_ACCT_CONFIRM   =  19;
  private static final int REGISTER           =  20;
  private static final int CREDITS            =  21;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int state;
  private Account account;
  private Char charInfo;
  private String passwordHolder;
  private String menuMsg;
  private boolean disconnect;
  private boolean deletePlaceholder;
  private int numBadPasswords;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public int viewMode()                { return NO_OUTPUT;    }
  public void echo(String s)           { utilityQueue.add(s); }
  public void addOutput(String s)      { return;              }
  public void addFightOutput(String s) { return;              }
  public void focusGained()            {                      }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Login(Client c)

  {
    super(c);
    menuMsg = "";
    state = ENTER_NAME;
    disconnect = false;
    deletePlaceholder = false;
    account = new Account();
    myClient.setAccount(account);
    myClient.setClientState(CSTATE_LOGIN);
    numBadPasswords = 0;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    if (state == ENTER_NAME)              return " Enter Account Username: ";
    else if (state == ENTER_PASSWORD)     return " Enter Account Password: ";
    else if (state == CONFIRM_CREATE)     return " Create a new account by this name? ";
    else if (state == ENTER_NEW_PASSWORD) return " Enter a password for this account: ";
    else if (state == NEWCHAR_NAME)       return " Enter the new character name: ";
    else if (state == NEWCHAR_CONFIRM)    return " Create " + charInfo.getName() + "? ";
    else if (state == CREATION_GENDER)    return "   Select: ";
    else if (state == CREATION_CLASS)     return "   Select: ";
    else if (state == CREATION_CITY)      return "   Select: ";
    else if (state == DELETION_NAME)      return " Enter the name of the character to delete: ";
    else if (state == DELETION_CONFIRM)   return " You wish to delete " + charInfo.getName() + "? ";
    else if (state == DELETION_POSITIVE)  return " Type 'delete this character' if you are sure: ";
    else if (state == PASSWORD_OLD)       return " Enter current password: ";
    else if (state == PASSWORD_NEW)       return " Enter a new password: ";
    else if (state == PASSWORD_CONFIRM)   return " Confirm new password: ";
    else if (state == EMAIL_ADDRESS)      return " Enter your E-Mail Address: ";
    else if (state == DELETE_ACCOUNT)     return " Delete this account and all of its characters? ";
    else if (state == DEL_ACCT_CONFIRM)   return " Type 'delete this account' if you are sure: ";

    else if (state == REGISTER)           return getRegInfo();
    else if (state == CREDITS)            return getCredits();
    else if (state == VIEW_STATS)         return account.getAccountInfo(false);
    else if (state == ACCOUNT_MENU)       return account.getAccountMenu(menuMsg);

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private String getRegInfo()

  {
    String info = Ansi.clearScreen + "\r\n" + HelpFile.helpRegister() + "\r\n\n";
    info += "  [Press #nEnter#N to Continue] : ";
    return info;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private String getCredits()

  {
    String info = Ansi.clearScreen + "\r\n" + HelpFile.helpCredits() + "\r\n\n";
    info += "  [Press #nEnter#N to Continue] : ";
    return info;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void exit() { }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleOutput()

  {
    if (!active) return;
    if (disconnect) return;

    flushUtilityQueue();
    if (output.length() > 0) output += "\r\n" + getPrompt();
    else output += getPrompt();
    myClient.msg(output);
    menuMsg = "";
    active = false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;

    String input = clearWhiteSpace(myClient.getCommand());

    if (state == ENTER_NAME)              processName(input);
    else if (state == ENTER_PASSWORD)     processPassword(input);
    else if (state == CONFIRM_CREATE)     processConfirmCreation(input);
    else if (state == ENTER_NEW_PASSWORD) processNewPassword(input);
    else if (state == ACCOUNT_MENU)       processAccountMenu(input);
    else if (state == NEWCHAR_NAME)       processNewcharName(input);
    else if (state == NEWCHAR_CONFIRM)    processNewcharConfirm(input);
    else if (state == CREATION_GENDER)    processGender(input);
    else if (state == CREATION_CLASS)     processClass(input);
    else if (state == CREATION_CITY)      processCity(input);
    else if (state == DELETION_NAME)      processDeletionName(input);
    else if (state == DELETION_CONFIRM)   processDeletionConfirm(input);
    else if (state == DELETION_POSITIVE)  processDeletionPositive(input);
    else if (state == PASSWORD_OLD)       processPasswordOld(input);
    else if (state == PASSWORD_NEW)       processPasswordNew(input);
    else if (state == PASSWORD_CONFIRM)   processPasswordConfirm(input);
    else if (state == VIEW_STATS)         processViewStats(input);
    else if (state == EMAIL_ADDRESS)      processEmailAddress(input);
    else if (state == DELETE_ACCOUNT)     processDeleteAccount(input);
    else if (state == DEL_ACCT_CONFIRM)   processDeleteAccountConfirm(input);
    else if (state == REGISTER)           processRegister(input);
    else if (state == CREDITS)            processCredits(input);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void flushUtilityQueue()

  {
    output = "";

    while (!utilityQueue.isEmpty())
      output += utilityQueue.removeFirst().toString();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void quitGame()

  {
    account.save();
    disconnect = true;
    myClient.disconnect();
    ClientList.queueClientRemove(myClient);
    SystemLog.doubleAccountLog(myClient, "has quit the game.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    String nm = account.getUsername();
    String ip = myClient.getIPAddress();

    myClient.disconnect();
    ClientList.queueClientRemove(myClient);
    ClientList.accountMessage("LOGIN: Account " + nm + " has gone linkdead.", myClient);
    SystemLog.addLog(ip + " Account " + nm + " went linkdead during login.");

    if (deletePlaceholder) FileManager.deletePlaceHolder(charInfo.getName());
    if (account.isPlaceHolder()) FileManager.deleteAccountHolder(account.getUsername());
    if ((account != null) && (state == ACCOUNT_MENU)) account.save();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void loginCharacter(int index)

  {
    if (AccessManager.manager.isAccountBanned(account)) {
      menuMsg = " #rThis account is blocked.#N";
      return; }

    String name = account.getChar(index);

    if (name.length() == 0) {
      menuMsg = " #rThat character slot is empty.#N";
      return; }

    Char E = (Char) World.findCharacter(name);

    if (E == null) {
      menuMsg = " #rCharacter loading error.#N";
      return; }

    if (AccessManager.manager.isCharFrozen(E)) {
      menuMsg = " #r" + E.getName() + " is frozen.#N";
      return; }

    E.setClient(myClient);
    myClient.setCharInfo(E);
    myClient.setCharInfoBackup(E);
    myClient.setClientState(CSTATE_NORMAL);
    E.enterGame();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processName(String input)

  {
    if (input.length() <= 0) return;

    while ((input.length() > 1) && (!letter(input.charAt(0))))
      input = input.substring(1, input.length());

    input = properName(input);

    if (!Account.validAccountName(input)) {
      echo(" #rInvalid account name.#N");
      return; }

    if (!Account.exists(input))

    {
      FileManager.createAccountHolder(input);
      account.setUsername(input);
      account.setPlaceHolder(true);
      account.loginStamp(myClient.getIPAddress());
      state = CONFIRM_CREATE;
      return;
    }

    account = (Account) World.findAccount(input);

    if (account.isPlaceHolder()) {
      echo(" #rThat account is currently under creation.#N");
      return; }

    myClient.setAccount(account);
    account.loginStamp(myClient.getIPAddress());
    state = ENTER_PASSWORD;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processPassword(String input)

  {
    String ip = myClient.getIPAddress();
    String nm = account.getUsername();

    if (!account.getPassword().equals(input))

    {
      SystemLog.addLog(ip + " Bad password on " + nm + ".");
      ClientList.accountMessage("Bad password on " + nm + ".", myClient);
      echo(" #rIncorrect Password.#N");

      numBadPasswords++;
      account.setBadPasswords(account.getBadPasswords()+1);
      account.save();

      if (numBadPasswords >= 3)

      {
        state = NO_PROMPT;
        disconnect = true;
        myClient.disconnect();
        ClientList.queueClientRemove(myClient);
        ClientList.accountMessage("Closing link to " + nm + ".", myClient);
        SystemLog.addLog(ip + " Closing link to " + nm + ".");
      }
    }

    else

    {
      SystemLog.doubleAccountLog(myClient, "connected to server.");

      Client C = ClientList.findClientExclude(myClient, account.getUsername());

      int bpw = account.getBadPasswords();
      account.setBadPasswords(0);

      if (C == null)

      {
        state = ACCOUNT_MENU;
        myClient.setClientState(CSTATE_MENU);
        account.updateCharacters();

        if (bpw > 0)
          menuMsg = " #rYOU'VE HAD #n" + bpw + " #rBAD PASSWORD ATTEMPTS SINCE YOUR LAST LOGIN!#N";

        return;
      }

      if (C.getClientState() == CSTATE_LOGIN)

      {
        C.disconnect();
        C.enterLinkdeadState();
        state = ACCOUNT_MENU;
        myClient.setClientState(CSTATE_MENU);
        account.updateCharacters();
        return;
      }

      state = NO_PROMPT;
      myClient.reconnect(C);
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processConfirmCreation(String input)

  {
    input = input.toLowerCase();

    if ((input.length() == 0) || (!input.substring(0,1).equalsIgnoreCase("y")))

    {
      String name = account.getUsername();
      FileManager.deleteAccountHolder(name);
      account = new Account();
      account.setUsername(name);
      myClient.setAccount(account);
      state = ENTER_NAME;
      return;
    }

    String ip = myClient.getIPAddress();
    String nm = account.getUsername();
    SystemLog.addLog(ip + " New account " + nm + " connected to server.");
    ClientList.accountMessage("New account " + nm + " connected to server.", myClient);
    state = ENTER_NEW_PASSWORD;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processNewPassword(String input)

  {
    if ((input.length() == 0) || (!Account.validAccountName(input))) {
      echo(" #rInvalid Password.#N");
      return; }

    account.setPlaceHolder(false);
    account.setPassword(input);
    account.save();
    World.setAccount(account);
    state = ACCOUNT_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processAccountMenu(String input)

  {
    if (input.equals("1")) loginCharacter(0);
    else if (input.equals("2")) loginCharacter(1);
    else if (input.equals("3")) loginCharacter(2);
    else if (input.equals("4")) loginCharacter(3);
    else if (input.equals("5")) loginCharacter(4);
    else if (input.equals("6")) loginCharacter(5);
    else if (input.equals("7")) loginCharacter(6);
    else if (input.equals("8")) loginCharacter(7);
    else if (input.equals("9")) loginCharacter(8);
    else if (input.equals("10")) loginCharacter(9);
    else if (input.equalsIgnoreCase("N")) state = NEWCHAR_NAME;
    else if (input.equalsIgnoreCase("D")) state = DELETION_NAME;
    else if (input.equalsIgnoreCase("P")) state = PASSWORD_OLD;
    else if (input.equalsIgnoreCase("E")) state = EMAIL_ADDRESS;
    else if (input.equalsIgnoreCase("S")) state = VIEW_STATS;
    else if (input.equalsIgnoreCase("R")) state = REGISTER;
    else if (input.equalsIgnoreCase("C")) state = CREDITS;
    else if (input.equalsIgnoreCase("X")) state = DELETE_ACCOUNT;
    else if (input.equalsIgnoreCase("Q")) quitGame();

    if (input.equalsIgnoreCase("N")) myClient.setClientState(CSTATE_CREATION);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processViewStats(String input)

  {
    state = ACCOUNT_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processRegister(String input)

  {
    state = ACCOUNT_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processCredits(String input)

  {
    state = ACCOUNT_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processEmailAddress(String input)

  {
    if (!Account.validEmailAddress(input)) {
      menuMsg = " #rInvalid E-Mail Address.#N";
      state = ACCOUNT_MENU;
      return; }

    account.setEmailAddress(input);
    account.save();
    menuMsg = " #gE-Mail Address Updated.#N";
    state = ACCOUNT_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processNewcharName(String input)

  {
    if (input.length() == 0)

    {
      menuMsg = " #rCharacter creation cancelled.#N";
      myClient.setClientState(CSTATE_MENU);
      state = ACCOUNT_MENU;
      return;
    }

    while ((input.length() > 1) && (!letter(input.charAt(0))))
      input = input.substring(1, input.length());

    input = properName(input);

    if (!Account.validCharacterName(input)) {
      echo(" #rInvalid character name.#N");
      return; }

    if (!Account.usableCharacterName(input))

    {
      echo(" #rThat name is already taken or unusable.#N");
      return;
    }

    FileManager.createPlaceHolder(input);
    deletePlaceholder = true;
    charInfo = new Warrior();
    charInfo.setName(input);
    charInfo.setClient(myClient);
    state = NEWCHAR_CONFIRM;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processNewcharConfirm(String input)

  {
    if ((input.length() == 0) || (!input.substring(0,1).equalsIgnoreCase("y")))

    {
      FileManager.deletePlaceHolder(charInfo.getName());
      menuMsg = " #rCharacter creation cancelled.#N";
      myClient.setClientState(CSTATE_MENU);
      state = ACCOUNT_MENU;
      return;
    }

    if (account.canAddCharacter(charInfo.getName())) {
      echo(getGenderMenu());
      state = CREATION_GENDER;
      return; }

    FileManager.deletePlaceHolder(charInfo.getName());
    menuMsg = " #r" + account.getAddCharacterError(charInfo.getName()) + "#N";
    myClient.setClientState(CSTATE_MENU);
    state = ACCOUNT_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processGender(String input)

  {
    if (input.length() < 1) return;

    if ((input.charAt(0) != 'f') && (input.charAt(0) != 'F') &&
        (input.charAt(0) != 'm') && (input.charAt(0) != 'M')) return;

    if (input.charAt(0) == 'f' || input.charAt(0) == 'F')
      charInfo.setGender("female");
    else charInfo.setGender("male");

    state = CREATION_CLASS;
    echo(getClassMenu());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processClass(String input)

  {
    if (input.length() < 1) return;

    if ((input.charAt(0) != 'w') && (input.charAt(0) != 'W') &&
        (input.charAt(0) != 't') && (input.charAt(0) != 'T') &&
        (input.charAt(0) != 'c') && (input.charAt(0) != 'C') &&
        (input.charAt(0) != 'm') && (input.charAt(0) != 'M')) return;

    if ((input.charAt(0) == 'w') || (input.charAt(0) == 'W'))      charInfo = charInfo.setClass("Warrior");
    else if ((input.charAt(0) == 't') || (input.charAt(0) == 'T')) charInfo = charInfo.setClass("Thief");
    else if ((input.charAt(0) == 'c') || (input.charAt(0) == 'C')) charInfo = charInfo.setClass("Cleric");
    else if ((input.charAt(0) == 'm') || (input.charAt(0) == 'M')) charInfo = charInfo.setClass("Mage");

    state = CREATION_CITY;
    echo(getCityMenu());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processCity(String input)

  {
    if (input.length() < 1) return;
    if ((input.charAt(0) < '1') || (input.charAt(0) > '4')) return;

    if (input.charAt(0) == '1')      charInfo.setHometown("Hydecka");
    else if (input.charAt(0) == '2') charInfo.setHometown("Almeccia");
    else if (input.charAt(0) == '3') charInfo.setHometown("Ravenna");
    else if (input.charAt(0) == '4') charInfo.setHometown("Volaris");

    FileManager.deletePlaceHolder(charInfo.getName());
    deletePlaceholder = false;
    charInfo.setAccountName(account.getUsername());
    charInfo.setRoom(World.findHometown(charInfo).getID());
    World.insertCharacter(charInfo);
    account.addCharacter(charInfo.getName());
    account.updateCharacters();
    account.save();
    charInfo.creationStamp();
    charInfo.addLegendMark("Human " + charInfo.getCharClass());
    charInfo.castChar().save();
    menuMsg = "#g" + charInfo.getName() + "#g has been added to your account.#N";
    myClient.setClientState(CSTATE_MENU);
    state = ACCOUNT_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processDeletionName(String input)

  {
    if (input.length() == 0)

    {
      menuMsg = " #rCharacter deletion cancelled.#N";
      state = ACCOUNT_MENU;
      return;
    }

    boolean found = false;

    for (int i=0; i<account.getSize(); i++)
    if (account.getChar(i).equalsIgnoreCase(input))
      found = true;

    if (!found)

    {
      menuMsg = " #rThere is no character by that name in your account.#N";
      state = ACCOUNT_MENU;
      return;
    }

    charInfo = (Char) World.findCharacter(properName(input));

    if (charInfo == null)

    {
      account.updateCharacters();
      menuMsg = " #rThat character file is missing. Account updated.#N";
      state = ACCOUNT_MENU;
      return;
    }

    state = DELETION_CONFIRM;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processDeletionConfirm(String input)

  {
    if ((input.length() == 0) || (!input.substring(0,1).equalsIgnoreCase("y")))

    {
      menuMsg = " #rCharacter deletion cancelled.#N";
      state = ACCOUNT_MENU;
      return;
    }

    state = DELETION_POSITIVE;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processDeletionPositive(String input)

  {
    if ((input.length() == 0) || (!input.equalsIgnoreCase("delete this character")))

    {
      menuMsg = " #rCharacter deletion cancelled.#N";
      state = ACCOUNT_MENU;
      return;
    }

    if (charInfo == null)

    {
      account.updateCharacters();
      menuMsg = " #rThat character file is missing. Account updated.#N";
      state = ACCOUNT_MENU;
      return;
    }

    String nm = charInfo.getName();
    charInfo.setClient(null);
    charInfo.deleteCharacter();
    account.updateCharacters();
    menuMsg = " #g" + nm + "#g has been deleted.#N";
    state = ACCOUNT_MENU;
    return;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processDeleteAccount(String input)

  {
    if ((input.length() == 0) || (!input.substring(0,1).equalsIgnoreCase("y")))

    {
      menuMsg = " #rAccount deletion cancelled.#N";
      state = ACCOUNT_MENU;
      return;
    }

    state = DEL_ACCT_CONFIRM;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processDeleteAccountConfirm(String input)

  {
    if ((input.length() == 0) || (!input.equalsIgnoreCase("delete this account")))

    {
      menuMsg = " #rAccount deletion cancelled.#N";
      state = ACCOUNT_MENU;
      return;
    }

    account.deleteAllCharacters();
    World.removeAccount(account.getUsername());
    WriteThread.msg("deleteaccount " + account.getUsername());

    disconnect = true;
    myClient.disconnect();
    ClientList.queueClientRemove(myClient);
    SystemLog.doubleAccountLog(myClient, "self-deleted and exited the game.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processPasswordOld(String input)

  {
    if (!input.equals(account.getPassword()))

    {
      menuMsg = " #rIncorrect Password.#N";
      state = ACCOUNT_MENU;
      return;
    }

    state = PASSWORD_NEW;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processPasswordNew(String input)

  {
    if ((input.length() == 0) || (!Account.validAccountName(input))) {
      echo(" #rInvalid Password.#N");
      return; }

    passwordHolder = input;
    state = PASSWORD_CONFIRM;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void processPasswordConfirm(String input)

  {
    if (!input.equals(passwordHolder))

    {
      menuMsg = " #rPasswords do not match. No change made.#N";
      state = ACCOUNT_MENU;
      return;
    }

    account.setPassword(input);
    account.save();
    menuMsg = " #gPassword successfully changed.#N";
    state = ACCOUNT_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public String getGenderMenu()

  {
    return "\r\n #NChoose your gender:\r\n\n   (#bM#N) - Male\r\n   (#yF#N) - Female\r\n";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public String getClassMenu()

  {
    String startClass  = "\r\n #NChoose your starting profession:\r\n\n   ";
           startClass += "(#BWa#N) - Warrior\r\n   (#YTh#N) - Thief\r\n   ";
           startClass += "(#GCl#N) - Cleric\r\n   (#MMg#N) - Mage\r\n";

    return startClass;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public String getCityMenu()

  {
    String longName  = "\r\n #NChoose your hometown:\r\n\n   ";
           longName += "(1) - #CHydecka#N   --  The Warrior City-State.\r\n   ";
           longName += "(2) - #nAlmeccia#N  --  The Holy Kingdom.\r\n   ";
           longName += "(3) - #BRavenna#N   --  The Shadowlands.\r\n   ";
           longName += "(4) - #mVolaris#N   --  Mage Country.\r\n";

    return longName;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}