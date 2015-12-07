import java.io.*;
import java.util.*;

public class Immortal extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void set(Entity SRC, String str)

  {
    ImmSet.set(SRC, str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveWorld(Entity SRC)

  {
    SRC.echo("Saving World.");
    World.save();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void report(Entity SRC, String str)

  {
    if (str.length() <= 0) {
      SRC.echo("Who do you want a report on?");
      return; }

    Entity Target = World.findCharacter(properName(first(str)));

    if (Target == null) {
      SRC.echo("There is no such person.");
      return; }

    if ((Target.getLevel() < SRC.getLevel()) || (Target == SRC))
      SRC.echo(Target.castChar().getReport(SRC.getLevel()));

    else SRC.echo("You can't do that.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void reportAccount(Entity SRC, String str)

  {
    String accountName = first(str);

    if (!Account.exists(accountName)) {
      SRC.echo("There is no such account.");
      return; }

    Account account = World.findAccount(accountName);
    SRC.echo(account.getAccountInfo(true));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void identify(Entity SRC, String target)

  {
    if (target.length() == 0) {
      SRC.echo("What do you want to identify?");
      return; }

    ArrayList ilist = new ArrayList(SRC.getInventory().getList());
    ilist.addAll(SRC.getRoom().getItemList().getList());
    ilist = ItemUse.parseListQuery(ilist, target);

    if (ilist.size() != 1) {
      SRC.echo("There's nothing like that around here.");
      return; }

    Item I = (Item) ilist.get(0);

    SRC.echo(I.getIdentify(SRC));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void restore(Entity SRC, String target)

  {
    if (target.length() <= 0) {
      SRC.echo("Who do you want to restore?");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.restore();
      SRC.echo("#gYou restore yourself to full health.#N"); }

    else if (ENT != null) {
      ENT.restore();
      ENT.echo("#g" + SRC.getName() + " has restored you to full health!#N");
      SRC.echo("#gYou restore " + ENT.getName() + " to full health.#N"); }

    else SRC.echo("There is nobody here by that name.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void mortalize(Entity SRC, String target)

  {
    String name = properName(first(target));

    Entity Target = World.findCharacter(name);

    if (Target == null) {
      SRC.echo("There is no such person.");
      return; }

    boolean queenBitch = SRC.getName().equalsIgnoreCase("Genevieve");
    boolean kingNothing = SRC.getName().equalsIgnoreCase("Aristal");

    if ((queenBitch) || (kingNothing))

    {
      Target.setLevel(1);
      SRC.echo(Target.getName() + " has been mortalized.");
      Target.castChar().save();
      return;
    }

    SRC.echo("You can't do that.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void goTo(Entity ENT, String str)

  {
    if (ENT.sleepCheck()) return;

    if (str.equals("")) {
      ENT.echo("Which room do you want to goto?");
      return; }

    int rnum = 0;
    Room targetRoom;

    try { rnum = Integer.parseInt(str); }
    catch (Exception e) { Immortal.goToEntity(ENT, str); return; }

    if (ENT.getClient() != null)
    if (ENT.getClient().getClientState() == CSTATE_IMM_AT) {
      ENT.echo("You can't do that.");
      return; }

    if (rnum < World.getSize())
      targetRoom = World.getRoom(rnum);
    else
      targetRoom = null;

    if (targetRoom != null)

    {
      ENT.stopFighting();
      ENT.stopTracking();
      ENT.getRoom().leave(ENT, LEAVE_GOTO);
      ENT.sendToAwake(ENT.getName() + " vanishes into thin air.");
      targetRoom.enter(ENT, ENTER_GOTO);
      ENT.sendToAwake(ENT.getName() + " appears from out of nowhere.");
      ENT.look("#gWoooooosh!#N");
    }

    else ENT.echo("There is no room by that number.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void goToEntity(Entity ENT, String target)

  {
    Entity E = World.findGlobalEntity(target);

    if (E == null) {
      ENT.echo("Nobody around by that name.");
      return; }

    Room targetRoom = E.getRoom();

    ENT.stopFighting();
    ENT.stopTracking();
    ENT.getRoom().leave(ENT, LEAVE_GOTO);
    ENT.sendToAwake(ENT.getName() + " vanishes into thin air.");
    targetRoom.enter(ENT, ENTER_GOTO);
    ENT.sendToAwake(ENT.getName() + " appears from out of nowhere.");
    ENT.look("#gWoooooosh!#N");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void trans(Entity ENT, String target)

  {
    if (ENT.sleepCheck()) return;

    Entity Target = World.findGlobalEntity(target);

    if (Target == ENT)
      ENT.echo("You are already here.  No need to transport.");

    else if (Target != null)

    {
      Target.stopFighting();
      Target.stopTracking();

      Target.getRoom().leave(Target, LEAVE_TRANS);
      Target.blindEmote("disappears in a puff of smoke.");
      ENT.getRoom().enter(Target, ENTER_TRANS);

      ENT.echo(Target.getName() + " appears in a burst of flames.");
      Target.xBlindEmote(ENT, "appears in a burst of flames.");

      if (Target.getPlayerState() != PSTATE_SLEEPING)
        Target.look("#r" + ENT.getName() + " has transported you!#N");
    }

    else ENT.echo("There is nobody around by that name.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void at(Entity ENT, String target, String action)

  {
    if (ENT.sleepCheck()) return;

    Room target_room;
    Room original_room = ENT.getRoom();

    if ((target.length() <= 0) || (action.length() <= 0)) {
      ENT.echo("At Usage#n:#N #R[#Nat <playername> <action>#R]#N");
      return; }

    if (ENT.getClient().getClientState() == CSTATE_IMM_AT) {
      ENT.echo("You can't do that.");
      return; }

    if (ENT.getPlayerState() == PSTATE_FIGHTING) {
      ENT.echo("You'd better finish this fight first.");
      return; }

    if (number(target))

    {
      int rnum = Integer.parseInt(target);
      target_room = World.getRoom(rnum);

      if (target_room == null) {
        ENT.echo("There is no room by that number.");
        return; }
    }

    else

    {
      Entity Target = World.findGlobalEntity(target);

      if (Target == null) {
        ENT.echo("There is nobody around by that name.");
        return; }

      if (Target == ENT) {
        ENT.echo("If you want to do something at yourself, just do it.");
        return; }

      target_room = Target.getRoom();
    }

    ENT.getClient().setClientState(CSTATE_IMM_AT);

    ENT.backupRoom();
    ENT.setRoom(target_room.getID());
    Interpreter.interpretCommand(ENT, action);
    ENT.setRoom(original_room.getID());

    if (ENT.getClient() != null)
    if (ENT.getClient().getClientState() != CSTATE_WRITING)
    if (ENT.getClient().getClientState() != CSTATE_BUILDING)
      ENT.getClient().setClientState(CSTATE_NORMAL);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void list(Entity SRC, String rem)

  {
    String usage = ""
    + "List Usage#n:       #R[#N list <item> <parameters>              #R]#N\r\n\n"
    + "Available Lists#n:  #R[#N characters, accounts, banks, boards   #R]#N\r\n"
    + "                  #R[#N rooms, zones, items, mobiles, actions #R]#N\r\n\n"
    + "Examples#n:#N  'list characters'\r\n"
    + "           'list characters online'\r\n"
    + "           'list rooms 200-500'\r\n"
    + "           'list mobs troll'\r\n"
    + "           'list zones all'";

    if (rem.length() == 0) { SRC.echo(usage);  return; }

    String s1 = first(rem);
    String s2 = last(rem);

    if (match(s1, "r", "rooms"))            rlist(SRC, s2);
    else if (match(s1, "z", "zones"))       zlist(SRC, s2);
    else if (match(s1, "m", "mobs"))        mlist(SRC, s2);
    else if (match(s1, "m", "mobiles"))     mlist(SRC, s2);
    else if (match(s1, "i", "items"))       ilist(SRC, s2);
    else if (match(s1, "a", "actions"))     alist(SRC, s2);
    else if (match(s1, "c", "characters"))  charList(SRC, s2);
    else if (match(s1, "a", "accounts"))    acctList(SRC, s2);
    else if (match(s1, "b", "banks"))       bankList(SRC, s2);
    else if (match(s1, "b", "boards"))      boardList(SRC, s2);
    else SRC.echo(usage);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void charList(Entity SRC, String rem)

  {
    String output = "";
    boolean first = true;
    boolean showAll = true;

    if (match(rem, "on", "online")) showAll = false;

    for (int i=0; i<World.getNumCharacters(); i++)
    if ((World.getCharacter(i).isOnline()) || (showAll))

    {
      if (!first) output += "\r\n";
      if (World.getCharacter(i).isOnline()) output += "#g";
      output += World.getCharacter(i).getName() + "#N";
      first = false;
    }

    PageBreak PB = new PageBreak(SRC.getClient(), output);
    PB.setHalting(false);
    SRC.getClient().pushInterface(PB);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void acctList(Entity SRC, String rem)

  {
    String output = "";
    boolean first = true;
    boolean showAll = true;

    if (match(rem, "on", "online")) showAll = false;

    for (int i=0; i<World.getNumAccounts(); i++)
    if ((World.getAccount(i).isOnline()) || (showAll))

    {
      if (!first) output += "\r\n";
      if (World.getAccount(i).isOnline()) output += "#g";
      output += World.getAccount(i).getUsername() + "#N";
      first = false;
    }

    PageBreak PB = new PageBreak(SRC.getClient(), output);
    PB.setHalting(false);
    SRC.getClient().pushInterface(PB);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void bankList(Entity SRC, String rem)

  {
    String output = "";
    boolean first = true;

    for (int i=0; i<World.getNumBanks(); i++)
    if (World.getBank(i) != null)

    {
      if (!first) output += "\r\n";
      output += World.getBank(i).getName();
      first = false;
    }

    PageBreak PB = new PageBreak(SRC.getClient(), output);
    PB.setHalting(false);
    SRC.getClient().pushInterface(PB);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void boardList(Entity SRC, String rem)

  {
    String output = "";
    boolean first = true;

    for (int i=0; i<World.getNumBoards(); i++)
    if (World.getBoard(i) != null)

    {
      if (!first) output += "\r\n";
      output += World.getBoard(i).getBoardName();
      first = false;
    }

    PageBreak PB = new PageBreak(SRC.getClient(), output);
    PB.setHalting(false);
    SRC.getClient().pushInterface(PB);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void zlist(Entity ENT, String rem)

  {
    if (rem.length() <= 0) {
      ENT.echo(HelpFile.getImmLists());
      return; }

    if (number(first(removeDashes(rem)))) {
      zlistNum(ENT, rem);
      return; }

    String str = "#nZone List:";
    int size = World.getNumZones();

    for (int i=0; i<size; i++)
    if (World.getZone(i) != null)

    {
      int zID = World.getZone(i).getID();
      String zName = World.getZone(i).getName();
      String zColor = World.getZone(i).getColor();
      String cName = removeColors(zName).toLowerCase();
      String xs = tenSpace(zID, 3);

      if ((abbreviation(rem, cName)) || (rem.equals("all")))
        str += "\r\n#N[#c" + zID + "#N]" + xs + zColor + zName + "#N";
    }

    ENT.getClient().pushPageBreak(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void zlistNum(Entity ENT, String rem)

  {
    String str = "#nZone List:";

    rem = removeDashes(rem);
    int start = Integer.parseInt(first(rem));

    if (last(rem).length() <= 0)

    {
      Zone Z = World.getZone(start);

      if (Z == null) {
        ENT.echo("There is no zone by that number.");
        return; }

      str += "\r\n#N[#c" + start + "#N]" + tenSpace(start, 3);
      str += Z.getColor() + Z.getName() + "#N";
      ENT.getClient().pushPageBreak(str);
      return;
    }

    if (!number(last(rem))) {
      ENT.echo(HelpFile.getImmLists());
      return; }

    int end = Integer.parseInt(last(rem));
    int max = World.getSize()-1;

    if (start < 0) start = 0;
    if (end > max) end = max;

    for (int i=start; i<=end; i++)

    {
      Zone Z = World.getZone(i);
      if (Z != null) str += "\r\n#N[#c" + i + "#N]" + tenSpace(i,5);
      if (Z != null) str += Z.getColor() + Z.getName() + "#N";
    }

    ENT.getClient().pushPageBreak(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void alist(Entity ENT, String rem)

  {
    if (rem.length() <= 0) {
      ENT.echo(HelpFile.getImmLists());
      return; }

    if (number(first(removeDashes(rem)))) {
      alistNum(ENT, rem);
      return; }

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

    ENT.getClient().pushPageBreak(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void alistNum(Entity ENT, String rem)

  {
    String str = "#nAction List:";

    rem = removeDashes(rem);
    int start = Integer.parseInt(first(rem));

    if (last(rem).length() <= 0)

    {
      Action A = World.getAction(start);

      if (A == null) {
        ENT.echo("There is no action by that number.");
        return; }

      str += "\r\n#N[#c" + start + "#N]" + tenSpace(start, 5);
      str += A.getName() + "#N";
      ENT.getClient().pushPageBreak(str);
      return;
    }

    if (!number(last(rem))) {
      ENT.echo(HelpFile.getImmLists());
      return; }

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

    ENT.getClient().pushPageBreak(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void rlist(Entity ENT, String rem)

  {
    if (rem.length() <= 0) {
      ENT.echo(HelpFile.getImmLists());
      return; }

    if (number(first(removeDashes(rem)))) {
      rlistNum(ENT, rem);
      return; }

    String str = "#nRoom List:";
    int size = World.getNumZones();

    for (int i=0; i<size; i++)
    if (World.getZone(i) != null)

    {
      Zone Z = World.getZone(i);
      int zsize = Z.getNumRooms();
      String rColor = Z.getColor();

      for (int j=0; j<zsize; j++)

      {
        int rID = Z.getRoom(j);
        String rName = Z.getRoomObj(j).getTitle();
        String cName = removeColors(rName).toLowerCase();
        String xs = tenSpace(rID, 5);

        if ((abbreviation(rem, cName)) || (rem.equals("all")))
          str += "\r\n#N[#c" + rID + "#N]" + xs + rColor + rName + "#N";
      }
    }

    ENT.getClient().pushPageBreak(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void rlistNum(Entity ENT, String rem)

  {
    String str = "#nRoom List:";

    rem = removeDashes(rem);
    int start = Integer.parseInt(first(rem));

    if (last(rem).length() <= 0)

    {
      Zone Z = World.getZone(start);

      if (Z == null) {
        ENT.echo("There is no Zone by that number.");
        return; }

      for (int j=0; j<Z.getNumRooms(); j++)

      {
        int rID = Z.getRoom(j);
        String rName = Z.getRoomObj(j).getTitle();
        String cName = removeColors(rName).toLowerCase();
        String xs = tenSpace(rID, 5);
        str += "\r\n#N[#c" + rID + "#N]" + xs + Z.getColor() + rName + "#N";
      }

      ENT.getClient().pushPageBreak(str);
      return;
    }

    if (!number(last(rem))) {
      ENT.echo(HelpFile.getImmLists());
      return; }

    int end = Integer.parseInt(last(rem));
    int max = World.getSize()-1;

    if (start < 0) start = 0;
    if (end > max) end = max;

    for (int i=start; i<=end; i++)

    {
      Room R = World.getRoom(i);
      if (R != null) str += "\r\n#N[#c" + i + "#N]" + tenSpace(i, 5);
      if (R != null) str += R.getZone().getColor() + R.getTitle() + "#N";
    }

    ENT.getClient().pushPageBreak(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void ilist(Entity ENT, String rem)

  {
    if (rem.length() <= 0) {
      ENT.echo(HelpFile.getImmLists());
      return; }

    if (number(first(removeDashes(rem)))) {
      ilistNum(ENT, rem);
      return; }

    String str = "#nItem List:";

    for (int i=0; i<World.getNumZones(); i++)
    if (World.getZone(i) != null)

    {
      Zone Z = World.getZone(i);
      int isize = Z.getNumItems();

      for (int j=0; j<isize; j++)

      {
        int iID = Z.getItem(j);
        String iName = Z.getItemObj(j).getName();
        String cName = removeColors(iName).toLowerCase();
        String xs = tenSpace(iID, 5);

        if ((abbreviation(rem, cName)) || (rem.equals("all")))
          str += "\r\n#N[#c" + iID + "#N]" + xs + iName + "#N";
      }
    }

    ENT.getClient().pushPageBreak(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void ilistNum(Entity ENT, String rem)

  {
    String str = "#nItem List:";

    rem = removeDashes(rem);
    int start = Integer.parseInt(first(rem));

    if (last(rem).length() <= 0)

    {
      Zone Z = World.getZone(start);

      if (Z == null) {
        ENT.echo("There is no Zone by that number.");
        return; }

      for (int j=0; j<Z.getNumItems(); j++)

      {
        int iID = Z.getItem(j);
        String iName = Z.getItemObj(j).getName();
        String cName = removeColors(iName).toLowerCase();
        String xs = tenSpace(iID, 5);
        str += "\r\n#N[#c" + iID + "#N]" + xs + iName + "#N";
      }

      ENT.getClient().pushPageBreak(str);
      return;
    }

    if (!number(last(rem))) {
      ENT.echo(HelpFile.getImmLists());
      return; }

    int end = Integer.parseInt(last(rem));
    int max = World.getItemSize()-1;

    if (start < 0) start = 0;
    if (end > max) end = max;

    for (int i=start; i<=end; i++)

    {
      Item I = World.getItem(i);
      if (I != null) str += "\r\n#N[#c" + i + "#N]" + tenSpace(i, 5);
      if (I != null) str += I.getName() + "#N";
    }

    ENT.getClient().pushPageBreak(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void mlist(Entity ENT, String rem)

  {
    if (rem.length() <= 0) {
      ENT.echo(HelpFile.getImmLists());
      return; }

    if (number(first(removeDashes(rem)))) {
      mlistNum(ENT, rem);
      return; }

    String str = "#nMobile List:";

    for (int i=0; i<World.getNumZones(); i++)
    if (World.getZone(i) != null)

    {
      Zone Z = World.getZone(i);
      int msize = Z.getNumMobs();

      for (int j=0; j<msize; j++)

      {
        int mID = Z.getMob(j);
        String mName = Z.getMobObj(j).getName();
        String cName = removeColors(mName).toLowerCase();
        String xs = tenSpace(mID, 5);

        if ((abbreviation(rem, cName)) || (rem.equals("all")))
          str += "\r\n#N[#c" + mID + "#N]" + xs + mName + "#N";
      }
    }

    ENT.getClient().pushPageBreak(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void mlistNum(Entity ENT, String rem)

  {
    String str = "#nMobile List:";

    rem = removeDashes(rem);
    int start = Integer.parseInt(first(rem));

    if (last(rem).length() <= 0)

    {
      Zone Z = World.getZone(start);

      if (Z == null) {
        ENT.echo("There is no Zone by that number.");
        return; }

      for (int j=0; j<Z.getNumMobs(); j++)

      {
        int mID = Z.getMob(j);
        String mName = Z.getMobObj(j).getName();
        String cName = removeColors(mName).toLowerCase();
        String xs = tenSpace(mID, 5);
        str += "\r\n#N[#c" + mID + "#N]" + xs + mName + "#N";
      }

      ENT.getClient().pushPageBreak(str);
      return;
    }

    if (!number(last(rem))) {
      ENT.echo(HelpFile.getImmLists());
      return; }

    int end = Integer.parseInt(last(rem));
    int max = World.getMobSize()-1;

    if (start < 0) start = 0;
    if (end > max) end = max;

    for (int i=start; i<=end; i++)

    {
      Mobile M = World.getMob(i);
      if (M != null) str += "\r\n#N[#c" + i + "#N]" + tenSpace(i, 5);
      if (M != null) str += M.getName() + "#N";
    }

    ENT.getClient().pushPageBreak(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void load(Entity ENT, String str)

  {
    if (ENT.sleepCheck()) return;

    String target = first(str);
    String tname = last(str);

    if ((match(target, "m", "mobile")) && (tname.length() > 0))

    {
      Mobile theMob = World.getMob(tname);

      if (theMob != null)

      {
        Mobile mob = theMob.replicate();
        Room R = ENT.getRoom();
        String n = ENT.getName();
        String m = "#N" + mob.getName() + "#g";
        String s = " has created " + m + " out of pure nothingness!#N";
        ENT.echo("#gWith a slight gesture of your hand, you create " + m + ".#N");
        R.xecho(ENT, "#g" + n + s, ECHO_AWAKE);
        R.enter(mob, ENTER_LOAD);
        return;
      }

      ENT.echo("That mobile does not exist.");
      return;
    }

    else if ((match(target, "i", "item")) && (tname.length() > 0))

    {
      Item theItem = World.getItem(tname);

      if (theItem != null)

      {
        Item newItem = theItem.replicate("Loaded by " + ENT.getName() + ".");
        int error = Create.item(newItem, ENT, ILOAD);

        if (error == ER_EINVFULL) {
          ENT.echo("You can't hold anymore items.");
          return; }

        if (error == ER_ILEVEL) {
          ENT.echo("You aren't powerful enough to create " + newItem.getLName() + ".");
          return; }

        String ln = newItem.getLName();
        String n = "#g" + ENT.getName() + " has created#N ";
        String g = "#gWith a slight gesture of your hand, ";
        ENT.echo(g + "you create#N " + ln + "#g.#N");
        ENT.sendToAwake(n + ln + "#g out of pure nothingness!#N");
        return;
      }

      ENT.echo("That item does not exist.");
      return;
    }

    ENT.echo("Load Usage#n: #R[#Nload mob <mnum>#R] [#Nload item <inum>#R]#N");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void makeZone(Entity ENT)

  {
    int zoneNum = World.createZone("Generic Zone");
    int roomNum = World.createRoom(World.getZone(zoneNum));

    ENT.echo("Zone " + zoneNum + " created.");
    ENT.echo("Room " + roomNum + " added.");

    World.getZone(zoneNum).setOwner(ENT.getName());
    World.getZone(zoneNum).save();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void makeClan(Entity ENT)

  {
    int clanNum = World.createClan();
    ENT.echo("Clan " + clanNum + " created.");
    FileManager.saveClan(World.getClan(clanNum));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void makeReligion(Entity ENT)

  {
    int religionNum = World.createReligion();
    ENT.echo("Religion " + religionNum + " created.");
    FileManager.saveReligion(World.getReligion(religionNum));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void makeMessageBoard(Entity ENT, String Rem)

  {
    if (Rem.length() == 0) {
      ENT.echo("Please provide a name for the new board.");
      return; }

    int boardNum = World.createBoard(Rem);
    ENT.echo(Rem + " message board created with ID: " + boardNum + ".");
    FileManager.saveBoard(World.getBoard(boardNum));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void makeBankChain(Entity ENT, String Rem)

  {
    if (Rem.length() == 0) {
      ENT.echo("Please provide a name for the new bank chain.");
      return; }

    int num = World.createBankChain(Rem);
    ENT.echo("Bank Chain " + Rem + " created with ID: " + num + ".");
    FileManager.createBankDirectory(removeColors(Rem));
    FileManager.saveBank(World.getBank(num));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void markLegend(Entity SRC, String target, String mark)

  {
    if ((target.length() <= 0) || (mark.length() <= 0)) {
      SRC.echo("Mark Usage#n: #R[#Nmark <player name> <legend mark>#R]#N");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == null) {
      SRC.echo("Nobody here by that name.");
      return; }

    if (!ENT.isPlayer()) {
      SRC.echo("Mobiles don't have legends.");
      return; }

    if (ENT == SRC) {
      SRC.castChar().getLegend().addMark(mark);
      SRC.echo("Ok.");
      return; }

    ENT.castChar().getLegend().addMark(mark);
    ENT.echo("#g" + SRC.getName() + " has etched a mark upon your legend!#N");
    SRC.echo("#gYou have etched a mark upon " + possessive(ENT.getName()) + " legend!#N");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void unmarkLegend(Entity SRC, String target, String mark)

  {
    if ((target.length() <= 0) || (mark.length() <= 0)) {
      SRC.echo("Unmark Usage#n: #R[#Nunmark <player name> <legend mark>#R]#N");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == null) {
      SRC.echo("Nobody here by that name.");
      return; }

    if (!ENT.isPlayer()) {
      SRC.echo("Mobiles don't have legends.");
      return; }

    if (ENT == SRC) {
      boolean removed = SRC.castChar().getLegend().removeMark(mark);
      if (removed) SRC.echo("Ok.");
      else SRC.echo("You don't have a legend mark like that.");
      return; }

    boolean removed = ENT.castChar().getLegend().removeMark(mark);

    if (!removed) {
      SRC.echo(ENT.He() + " doesn't have a legend mark like that.");
      return; }

    ENT.echo("#y" + SRC.getName() + " has erased a mark from your legend!");
    SRC.echo("#yYou have erased a mark from " + possessive(ENT.getName()) + " legend!");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void force(Entity SRC, String target, String command)

  {
    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC)
      SRC.echo("Do you really need to force yourself?");

    else if (ENT != null)

    {
      if (ENT.getLevel() >= SRC.getLevel()) {
        SRC.echo("Pick on somebody your own size.");
        return; }

      if (match(first(command), "k", "kill")) SRC.getClient().addFightOutput("Ok.");
        else SRC.echo("Ok.");

      Interpreter.interpretCommand(ENT, command);
    }

    else SRC.echo("There is nobody here by that name.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void slay(Entity SRC, String target)

  {
    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC)
      SRC.echo("You shouldn't Slay yourself.  It would probably hurt.");

    else if (ENT != null)

    {
      if (ENT.isPlayer()) SRC.echo("You can't Slay players. (yet)");

      else

      {
        String s1  = "#rYou savagely rip " + ENT.getName();
               s1 += " to shreds, completely annihilating " + ENT.him() + "!";
        String s2  = "#r" + SRC.getName() + " savagely rips " + ENT.getName();
               s2 +=" to shreds, completely annihilating " + ENT.him() + "!";

        ENT.die(false, false);

        SRC.echo(s1);
        SRC.getRoom().xecho(SRC, s2, ECHO_AWAKE);
      }
    }

    else SRC.echo("There's nobody here by that name.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void purge(Entity SRC, String str)

  {
    int inventory_item = 0;
    String e = "Purge Usage#n:#N #R[#Npurge <player name>#R] [#Npurge <item name>#R]#N";

    if (str.length() <= 0) { SRC.echo(e);  return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, str);

    if (ENT == SRC) {
      SRC.echo("You really shouldn't purge yourself.");
      return; }

    if (ENT != null)
    if (ENT.isPlayer())

    {
      if (ENT.getLevel() >= SRC.getLevel()) {
        SRC.echo("You aren't powerful enough to purge " + ENT.getName() + ".");
        return; }

      ENT.castChar().purged(SRC, PURGE_NORMAL);
      return;
    }

    ArrayList ilist = new ArrayList(SRC.getInventory().getList());
    ilist.addAll(SRC.getRoom().getItemList().getList());
    ilist = ItemUse.parseListQuery(ilist, str);

    if (ilist.size() != 1) {
      SRC.echo("There's nothing like that around here.");
      return; }

    Item I = (Item) ilist.get(0);
    String c = removeColors(I.getLName());
    String s = "#r" + SRC.getName() + " has purged " + c;
    int error = Destroy.item(I, SRC, IPURGE);

    SRC.echo("#rYou purge " + c + " #rwith a blast of fire!#N");
    SRC.getRoom().xecho(SRC, s + " #rwith a blast of fire!#N", ECHO_AWAKE);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void possess(Entity SRC, String target)

  {
    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (SRC.getClient().getClientState() == CSTATE_IMM_AT)
      SRC.restoreRoom();

    if (ENT == SRC)
      SRC.echo("Hopefully, you already possess your own soul.");

    else if (ENT != null)

    {
      if (ENT.isPlayer())
        SRC.echo("You can only possess mobiles, not people.");
      else if (SRC.getPlayerState() == PSTATE_FIGHTING)
        SRC.echo("You'd better finish this fight first.");
      else if ((!ENT.isPlayer()) && (ENT.getClient() != null))
        SRC.echo(ENT.getName() + " is already possessed.");

      else

      {
        Client MY_CLIENT = SRC.getClient();

        if ((SRC.getGroup().getSize() > 1) && (SRC.getGroup().getLeader() == SRC))
          SRC.getGroup().removeAll(SRC);

        SRC.stopFighting();
        SRC.stopFollowing();
        SRC.getRoom().leave(SRC, LEAVE_POSSESS);
        SRC.setClient(null);

        ENT.setClient(MY_CLIENT);
        MY_CLIENT.setCharInfo(ENT);
        MY_CLIENT.setClientState(CSTATE_POSSESS);

        String str = "";

        str += "#rYou leap into " + MY_CLIENT.getCharInfo().getName();
        str += "'s very soul, and possess " + MY_CLIENT.getCharInfo().him() + "!#N";

        MY_CLIENT.getCharInfo().echo(str);
      }
    }

    else SRC.echo("There is no mobile here by that name.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void unpossess(Client CLT, boolean active)

  {
    Entity mob = CLT.getCharInfo();
    String mobname = mob.getName();

    if (mob.isPlayer())

    {
      if (mob.getLevel() < 100) mob.echo("Invalid Command.");
      else mob.echo("You must first possess a mobile in order to unpossess one.");
      return;
    }

    if (active)
      mob.echo("#gYou return to your body!#N");

    Entity me = CLT.getCharInfoBackup();

    CLT.setCharInfo(me);
    CLT.setClientState(CSTATE_NORMAL);
    me.getRoom().enter(me, ENTER_UNPOS);

    mob.setClient(null);
    me.setClient(CLT);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void zrepop(Entity SRC, String rem)

  {
    if (rem.length() <= 0) {
      SRC.echo("Which zone do you want to repopulate?");
      return; }

    if (rem.equalsIgnoreCase("all"))

    {
      if (SRC.getLevel() < 110) {
        SRC.echo("You can't do that.");
        return; }

      World.repopMobs();
      SRC.echo("#gYou have repopulated the entire world!#N");
      return;
    }

    Zone Z = World.getZone(rem);

    if (Z == null) {
      SRC.echo("That zone does not exist.");
      return; }

    String n = removeColors(Z.getName());

    Z.repopMobs();
    SRC.echo("#gYou draw upon the powers of creation to repopulate " + n + "#g!#N");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void zclear(Entity SRC, String rem)

  {
    if (rem.length() <= 0) {
      SRC.echo("Which zone do you want to clear?");
      return; }

    if (rem.equalsIgnoreCase("all"))

    {
      if (SRC.getLevel() < 110) {
        SRC.echo("You can't do that.");
        return; }

      World.clearMobs();
      SRC.echo("#gYou have purged the entire world of mundane existence!#N");
      return;
    }

    Zone Z = World.getZone(rem);

    if (Z == null) {
      SRC.echo("That zone does not exist.");
      return; }

    String n = removeColors(Z.getName());

    Z.clearMobs();
    SRC.echo("#gYou have purged " + n + " #gof mundane existence!#N");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void clearMobPopulation(Entity SRC, String rem)

  {
    if (rem.length() <= 0) {
      SRC.echo("Which zone do you want to reset the population of?");
      return; }

    Zone Z = World.getZone(rem);

    if (Z == null) {
      SRC.echo("That zone does not exist.");
      return; }

    String n = removeColors(Z.getName());

    Z.clearMobPopulation();
    SRC.echo("#gYou have cleared the mobile population of " + n + "#g!#N");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void clearClan(Entity SRC, String rem)

  {
    if (rem.length() <= 0) {
      SRC.echo("Which clan do you want to reset?");
      return; }

    Clan C = World.getClan(rem);

    if (C == null) {
      SRC.echo("That clan does not exist.");
      return; }

    String n = removeColors(C.getName());

    C.clearClan();
    SRC.echo("#gYou have cleared the members of " + n + "#g!#N");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void bestow(Entity SRC, String rem)

  {
    int setLevel = 0;

    if (rem.length() <= 0) {
      SRC.echo("Bestow Usage#n:#N #R[#Nbestow <ability> upon <target> (level)#R]#N");
      return; }

    rem = rem.replaceAll(" upon ", " ");

    if (number(getLastWord(rem))) {
      setLevel = Integer.parseInt(getLastWord(rem));
      rem = clipLastWord(rem); }

    if ((setLevel < 0) || (setLevel > 100)) {
      SRC.echo("Skill and Spell levels must range between 0-100.");
      return; }

    String tName = getLastWord(rem);
    String sName = clipLastWord(rem);
    String fName = getProperClassName(sName);

    Entity Target = SRC.getRoom().findEntity(SRC, tName);

    if (Target == null) {
      SRC.echo("There's nobody here by that name.");
      return; }

    if ((SRC.getLevel() <= Target.getLevel()) && (Target != SRC)) {
      SRC.echo("You can't do that.");
      return; }

    Ability A = null;
    Class C = null;

    try

    {
      C = Class.forName(fName);
      A = (Ability) C.newInstance();
    }

    catch (Exception e) { SRC.echo("There is no such skill or spell.");  return; }
    catch (Throwable t) { SRC.echo("There is no such skill or spell.");  return; }

    try

    {
      if (A.isSkill())

      {
        Skill Sk = (Skill) A;
        Sk.init(Target, setLevel);

        String mn = SRC.getName();
        String tn = Target.getName();
        String sn = Sk.getName();

        Target.removeAbility(Sk.getName());
        Target.addSkill(Sk);

        if (SRC == Target) {
          SRC.echo("#cYou bestow upon yourself the art of " + sn + ".");
          return; }

        Target.echo("#c" + mn + " has bestowed upon you the art of " + sn + ".");
        SRC.echo("#cYou have bestowed upon " + tn + " the art of " + sn + ".");

        return;
      }

      if (A.isSpell())

      {
        Spell Sp = (Spell) A;
        Sp.init(Target, setLevel);

        String mn = SRC.getName();
        String tn = Target.getName();
        String sn = Sp.getName();

        Target.removeAbility(Sp.getName());
        Target.addSpell(Sp);

        if (SRC == Target) {
          SRC.echo("#cYou bestow upon yourself the art of " + sn + ".");
          return; }

        Target.echo("#c" + mn + " has bestowed upon you the art of " + sn + ".");
        SRC.echo("#cYou have bestowed upon " + tn + " the art of " + sn + ".");

        return;
      }
    }

    catch (Exception e) { SRC.echo("Something went wrong."); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void deprive(Entity SRC, String rem)

  {
    if (rem.length() <= 0) {
      SRC.echo("Deprive Usage#n:#N #R[#Ndeprive <target> of <ability>#R]#N");
      return; }

    rem = rem.replaceAll(" of ", " ");

    Entity Target = SRC.getRoom().findEntity(SRC, first(rem));

    if (Target == null) {
      SRC.echo("There's nobody around by that name.");
      return; }

    if ((SRC.getLevel() <= Target.getLevel()) && (Target != SRC)) {
      SRC.echo("You can't do that.");
      return; }

    Ability removed = Target.removeAbility(last(rem));

    if ((removed == null) && (Target == SRC)) {
      SRC.echo("You don't seem to have any ability by that name.");
      return; }

    if ((removed == null) && (Target != SRC)) {
      SRC.echo(Target.He() + " doesn't seem to have any ability by that name.");
      return; }

    String rn = removed.getName();
    String tn = Target.getName();
    String mn = SRC.getName();

    if (Target == SRC) {
      SRC.echo("#rYou have stripped yourself of the ability " + rn + "!");
      return; }

    SRC.echo("#rYou have stripped " + tn + " of the ability " + rn + "!");
    Target.echo("#r" + mn + " has stripped you of the ability " + rn + "!");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void snoop(Entity SRC, String rem)

  {
    Entity ENT = ClientList.findCharacter(rem);

    if (ENT == null) {
      SRC.echo("There's nobody around by that name.");
      return; }

    if (ENT == SRC)

    {
      if (SRC.castChar().getSnooping().length() == 0) {
        SRC.echo("Ok.");
        return; }

      String eName = SRC.castChar().getSnooping();
      SRC.castChar().setSnooping("");

      Entity Target = ClientList.findCharacter(eName);

      if (Target != null)

      {
        Target.getClient().removeSnooper(SRC.getClient());
        SRC.smsg(SRC.getName() + " stops snooping " + Target.getName() + ".");
        SRC.echo("You stop snooping " + Target.getName() + ".");
      }

      return;
    }

    if (ENT.getLevel() >= SRC.getLevel()) {
      SRC.echo("You can't do that.");
      return; }

    SRC.castChar().setSnooping(ENT.getName());
    ENT.getClient().addSnooper(SRC.getClient());
    SRC.smsg(SRC.getName() + " starts snooping " + ENT.getName() + ".");
    SRC.echo("You start snooping " + ENT.getName() + ".");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void ban(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Ban Usage#n:#N #R[#Nban <ip address> <days> <reason>#R]#N");
      return; }

    String ip = first(rem);
    rem = last(rem);

    if (!validIPString(ip)) {
      SRC.echo("Invalid IP address.");
      return; }

    int days = 0;
    if (number(first(rem))) days = Integer.parseInt(first(rem));
    if (days < 0) days = 0;

    String reason = last(rem);
    if (reason.length() == 0) reason = "Not specified.";

    String duration = "for " + days + " days.";
    if (days == 1) duration = "for 1 day.";
    if (days == 0) duration = "permanently.";

    AccessManager.manager.banSiteDays(ip, days, reason);
    SRC.echo(ip + " has been banned " + duration);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void unban(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Unban Usage#n:#N #R[#Nunban <ip address>#R]#N");
      return; }

    if (!AccessManager.manager.isSiteBanned(rem)) {
      SRC.echo(rem + " is not banned.");
      return; }

    AccessManager.manager.removeSiteBan(rem);
    SRC.echo(rem + " has been unbanned.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void block(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Block Usage#n:#N #R[#Nblock <account name> <days> <reason>#R]#N");
      return; }

    String accountName = first(rem);

    if (!Account.exists(accountName)) {
        SRC.echo("There is no such account.");
        return; }

    Account account = World.findAccount(accountName);

    rem = last(rem);

    int days = 0;
    if (number(first(rem))) days = Integer.parseInt(first(rem));
    if (days < 0) days = 0;

    String reason = last(rem);
    if (reason.length() == 0) reason = "Not specified.";

    String duration = "for " + days + " days.";
    if (days == 1) duration = "for 1 day.";
    if (days == 0) duration = "permanently.";

    AccessManager.manager.banAccountDays(account, days, reason);
    SRC.echo(accountName + " has been blocked " + duration);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void unblock(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Unblock Usage#n:#N #R[#Nblock <account name>#R]#N");
      return; }

    String accountName = first(rem);

    if (!Account.exists(accountName)) {
        SRC.echo("There is no such account.");
        return; }

    Account account = World.findAccount(accountName);

    if (!AccessManager.manager.isAccountBanned(account)) {
      SRC.echo(account.getUsername() + " is not blocked.");
      return; }

    AccessManager.manager.removeAccountBan(account);
    SRC.echo(account.getUsername() + " has been unblocked.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void freeze(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Freeze Usage#n:#N #R[#Nfreeze <character name> <days> <reason>#R]#N");
      return; }

    String charName = properName(first(rem));

    rem = last(rem);

    int days = 0;
    if (number(first(rem))) days = Integer.parseInt(first(rem));
    if (days < 0) days = 0;

    String reason = last(rem);
    if (reason.length() == 0) reason = "Not specified.";

    String duration = "for " + days + " days.";
    if (days == 1) duration = "for 1 day.";
    if (days == 0) duration = "permanently.";

    Entity E = World.findCharacter(charName);

    if (E == null) {
      SRC.echo("There is no such character.");
      return; }

    if (E.getLevel() >= SRC.getLevel()) {
      SRC.echo("You can't do that.");
      return; }

    E.castChar().purged(SRC, PURGE_FREEZE);
    AccessManager.manager.freezeCharDays(E.castChar(), days, reason);
    SRC.echo(charName + " has been frozen " + duration);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void unfreeze(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Unfreeze Usage#n:#N #R[#Nunfreeze <character name>#R]#N");
      return; }

    Entity C = World.findCharacter(rem);

    if (C == null) {
      SRC.echo("There is no such character.");
      return; }

    if (!AccessManager.manager.isCharFrozen(C.castChar())) {
      SRC.echo("That character is not frozen.");
      return; }

    AccessManager.manager.removeCharFreeze(C.castChar());
    SRC.echo(C.getName() + " has been unfrozen.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void banList(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Banlist Usage#n:#N #R[#Nbanlist <search string or 'all'>#R]#N");
      return; }

    String banList = AccessManager.manager.getBanList(rem);
    SRC.getClient().pushPageBreak(banList);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void blockList(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Blocklist Usage#n:#N #R[#Nblocklist <search string or 'all'>#R]#N");
      return; }

    String blockList = AccessManager.manager.getBlockList(rem);
    SRC.getClient().pushPageBreak(blockList);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void freezeList(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Freezelist Usage#n:#N #R[#Nfreezelist <search string or 'all'>#R]#N");
      return; }

    String freezeList = AccessManager.manager.getFreezeList(rem);
    SRC.getClient().pushPageBreak(freezeList);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void dc(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Disconnect Usage#n:#N #R[#Ndc <character name>#R]#N");
      return; }

    Entity C = ClientList.findCharacter(rem);

    if (C == null) {
      SRC.echo("There is no player by that name.");
      return; }

    if (C.getLevel() >= SRC.getLevel()) {
      SRC.echo("You can't do that.");
      return; }

    C.getClient().disconnect();
    C.getClient().enterLinkdeadState();
    SRC.echo(C.getName() + " has been disconnected.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void disintegrate(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Disintegrate Usage#n:#N #R[#Ndisintegrate <character name>#R]#N");
      return; }

    Entity C = World.findCharacter(rem);

    if (C == null) {
      SRC.echo("That character does not exist.");
      return; }

    if (C.getLevel() >= SRC.getLevel()) {
      SRC.echo("You can't do that.");
      return; }

    C.castChar().purged(SRC, PURGE_DISINTEGRATE);
    C.castChar().deleteCharacter();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void showRent(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Showrent Usage#n:#N #R[#Nshowrent <character name>#R]#N");
      return; }

    Entity C = World.findCharacter(rem);

    if (C == null) {
      SRC.echo("That character does not exist.");
      return; }

    if ((SRC != C) && (C.getLevel() >= SRC.getLevel())) {
      SRC.echo("You can't do that.");
      return; }

    ArrayList EQ  = new ArrayList();
    ArrayList INV = C.getInventory().getList();

    for (int i=0; i<Equipment.NUM_PLACES; i++)
    if (C.getEquipment(i) != null)
    if (!EQ.contains(C.getEquipment(i)))
      EQ.add(C.getEquipment(i));

    EQ  = ItemUse.expandItemList(EQ);
    INV = ItemUse.expandItemList(INV);
    String equipment = possessive(C.getName()) + " equipment:";
    String inventory = "\r\n" + possessive(C.getName()) + " inventory:";

    for (int i=0; i<EQ.size(); i++)

    {
      Item I = (Item)EQ.get(i);

      int difference = I.getName().length() - visibleSize(I.getName());
      int newLength = 25 + difference;
      String format = "%1$-" + newLength + "." + newLength + "s";

      equipment += "\r\n ";
      equipment += " #N[" + String.format(format, I.getName()) + "#N]";
      equipment += " #N[#n" + String.format("%1$-23.23s", I.getTimeStamp()) + "#N]";
      equipment += " #N[#n" + String.format("%1$-22.22s", I.getTypeStamp()) + "#N]";
    }

    if (EQ.size() == 0) equipment += "\r\n  Nothing.";

    for (int i=0; i<INV.size(); i++)

    {
      Item I = (Item)INV.get(i);

      int difference = I.getName().length() - visibleSize(I.getName());
      int newLength = 25 + difference;
      String format = "%1$-" + newLength + "." + newLength + "s";

      inventory += "\r\n ";
      inventory += " #N[" + String.format(format, I.getName()) + "#N]";
      inventory += " #N[#n" + String.format("%1$-23.23s", I.getTimeStamp()) + "#N]";
      inventory += " #N[#n" + String.format("%1$-22.22s", I.getTypeStamp()) + "#N]";
    }

    if (INV.size() == 0) inventory += "\r\n  Nothing.";

    SRC.echo(equipment);
    SRC.echo(inventory);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void deleteAccount(Entity SRC, String rem)

  {
    if (rem.length() == 0) {
      SRC.echo("Deleteaccount Usage#n:#N #R[#Ndeleteaccount <account name>#R]#N");
      return; }

    if (!Account.exists(rem)) {
      SRC.echo("That account does not exist.");
      return; }

    Account A = World.findAccount(rem);

    boolean canDelete = true;
    Entity activeChar = null;
    ArrayList charList = new ArrayList();

    for (int i=0; i<A.getSize(); i++)
    if (canDelete)

    {
      Entity C = World.findCharacter(A.getChar(i));
      activeChar = ClientList.findCharacter(A.getChar(i));

      if (C.getLevel() >= SRC.getLevel()) canDelete = false;
      else charList.add(C);
    }

    if (!canDelete) {
      SRC.echo("You can't do that.");
      return; }

    if (activeChar != null) activeChar.castChar().purged(SRC, PURGE_ACCOUNT);

    Client client = ClientList.findClientObject(A.getUsername());
    if (client != null) client.disconnect();

    for (int i=0; i<charList.size(); i++)
      ((Entity)charList.get(i)).castChar().deleteCharacter();

    World.removeAccount(A.getUsername());
    SRC.echo("You removed " + possessive(A.getUsername()) + " account successfully.");
    WriteThread.msg("deleteaccount " + A.getUsername());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void killGame(Entity SRC, String rem)

  {
    Server.shutdown = true;

    for (int i=0; i<ClientList.getSize(); i++)

    {
      Client C = ClientList.getClient(i);
      C.disconnect();
      C.enterLinkdeadState();
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void pave(Entity SRC, String rem)

  {
    int dir;
    boolean canPave;

    if (match(rem, "n", "north"))      dir = DIRECTION_N;
    else if (match(rem, "s", "south")) dir = DIRECTION_S;
    else if (match(rem, "e", "east"))  dir = DIRECTION_E;
    else if (match(rem, "w", "west"))  dir = DIRECTION_W;
    else if (match(rem, "u", "up"))    dir = DIRECTION_U;
    else if (match(rem, "d", "down"))  dir = DIRECTION_D;
    else dir = -1;

    if (dir == -1) {
      SRC.echo("Pave Usage#n: #R[#Npave <direction>#R]#N");
      return; }

    Zone Z = SRC.getZone();
    Room R = SRC.getRoom();

    if ((dir == DIRECTION_N) && (R.getN() == 0))      canPave = true;
    else if ((dir == DIRECTION_S) && (R.getS() == 0)) canPave = true;
    else if ((dir == DIRECTION_E) && (R.getE() == 0)) canPave = true;
    else if ((dir == DIRECTION_W) && (R.getW() == 0)) canPave = true;
    else if ((dir == DIRECTION_U) && (R.getU() == 0)) canPave = true;
    else if ((dir == DIRECTION_D) && (R.getD() == 0)) canPave = true;
    else canPave = false;

    if (!canPave) {
      SRC.echo("There's already a room in that direction.");
      return; }

    Room newRoom = World.getRoom(World.createRoom(Z));
    newRoom.setTitle("New Room");
    newRoom.setTerrain(Z.getDefaultTerrain());
    newRoom.setExit(oppositeDir(dir), R.getID());

    R.setExit(dir, newRoom.getID());
    SRC.move(dir);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}