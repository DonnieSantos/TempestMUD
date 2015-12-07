import java.util.*;
import java.io.*;

class Interpreter extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static String key;
  private static String rem;
  private static String Rem;
  private static String frem;
  private static boolean CLIENT;
  private static boolean PLAYER;
  private static boolean GHOST;
  private static boolean SINGLE;
  private static Entity currentUser;
  private static Client CLT;
  private static Char CHR;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Interpreter() { }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static boolean exact(String str)

  {
    String whole = key + " " + rem;
    str = str.toLowerCase();
    if (whole.indexOf(str) == 0) return true;
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static boolean IMM(int lev)

  {
    if (currentUser.getLevel() < lev) return false;
    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static String firstReplace(String str, String rep)

  {
    str = str.substring(1);
    str = rep + " " + str;
    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static String processMultipleCommands(Entity ENT, String command)

  {
    StringBuffer s = new StringBuffer(command);
    if (s.indexOf(";;") == -1) return command;
    if (s.indexOf(";;!") != -1) return command;
    if (s.indexOf(";;\\") != -1) return command;
    int pos = s.indexOf(";;");
    s = s.delete(pos, pos+2);
    if (CLIENT) ENT.getClient().forceCommand(s.substring(pos, s.length()));
    s = new StringBuffer(s.substring(0, pos));
    return s.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void interpretCommand(Entity ENT, String command)

  {
    if (ENT.getClient() != null) CLIENT = true;
    else CLIENT = false;

    if (ENT.isGhost())  GHOST  = true;    else GHOST  = false;
    if (ENT.isPlayer()) PLAYER = true;    else PLAYER = false;
    if (ENT.isPlayer()) CHR = (Char)ENT;  else CHR = null;

    command = clearWhiteSpace(command);

    if (command.length() <= 0) {
      if (CLIENT) ENT.getClient().drawPrompt();
      return; }

    if (CLIENT) System.out.println(ENT.getName() + ": [" + command + "]");
    if (CLIENT) SystemLog.addLog(ENT.getClient(), command);

    if (command.charAt(0) == 39)        command = firstReplace(command, "say");
    else if (command.charAt(0) == 34)   command = firstReplace(command, "tell");
    else if (command.charAt(0) == 45)   command = firstReplace(command, "chat");
    else if (command.charAt(0) == ';')  command = firstReplace(command, "ctell");
    else if (command.charAt(0) == ':')  command = firstReplace(command, "emote");

    command = clearWhiteSpace(command);

    if ((CLIENT) && ((command.charAt(0) == '!') || (command.charAt(0) == 92)))

    {
      String last_command = ENT.getClient().getLastCommand();
      last_command = clearWhiteSpace(last_command);
      if (last_command.length() <= 0) last_command = " ";
      char indicator = last_command.charAt(0);
      if ((indicator == '!') || (indicator == 92)) ENT.echo("Invalid Command.");
      else interpretCommand(ENT, ENT.getClient().getLastCommand());
    }

    else if ((letter(command.charAt(0))) || (number(command.charAt(0))))

    {
      if (CLIENT) ENT.getClient().lockCommands(200);
      if ((CLIENT) && (PLAYER)) command = ((Char)ENT).getAliases().applyAliasing(command);

      if (!first(command.toLowerCase()).equals("alias"))
        command = processMultipleCommands(ENT, command);

      key = first(command).toLowerCase();

      if (key.length() == command.length()) Rem = "";
      else Rem = command.substring(key.length()+1, command.length());

      if (Rem.length() > SPEECH_LIMITER) Rem = Rem.substring(0, SPEECH_LIMITER);
      rem = Rem.toLowerCase();
      frem = first(rem);

      if (rem.equals("")) SINGLE = true;
        else SINGLE = false;

      currentUser = ENT;
      CLT = ENT.getClient();

      interpret(ENT);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static void interpret(Entity ENT)

  {
    // *************************************************************************************************** //
    // ****************************************** Communication ****************************************** //
    // *************************************************************************************************** //

    if ((SINGLE) && (match(key, "l", "look")))  ENT.look("");
    else if (match(key, "l", "look"))           ENT.lookAt(rem);
    else if (match(key, "sa", "say"))           ENT.say(Rem);
    else if (match(key, "ch", "chat"))          ENT.chat(Rem);
    else if (match(key, "te", "tell"))          ENT.tell(frem, last(Rem), true);
    //else if (match(key, "ct", "ctell"))         ENT.ctell(Rem);

    // *************************************************************************************************** //
    // *************************************** General Information *************************************** //
    // *************************************************************************************************** //

    else if (match(key, "he", "help"))          ENT.echo(HelpFile.help(rem));
    else if (match(key, "com", "commands"))     ENT.echo(HelpFile.help(rem));
    else if (match(key, "pol", "policy"))       ENT.echo(HelpFile.helpPolicy());
    else if (match(key, "reg", "register"))     ENT.echo(HelpFile.helpRegister());
    else if (match(key, "reg", "registration")) ENT.echo(HelpFile.helpRegister());
    else if (match(key, "credit", "credits"))   ENT.echo(HelpFile.helpCredits());
    else if (match(key, "info", "information")) ENT.echo(HelpFile.getTitleScreen());
    else if (match(key, "st", "stats"))         ENT.stat();
    else if (match(key, "sc", "score"))         ENT.stat();
    else if (match(key, "me", "me"))            ENT.emote(Rem);
    else if (match(key, "em", "emote"))         ENT.emote(Rem);
    else if (match(key, "sk", "skills"))        ENT.displaySkills(false);
    else if (match(key, "sp", "spells"))        ENT.displaySkills(true);
    else if (match(key, "wh", "who"))           ENT.echo(ClientList.whoList());
    else if (match(key, "fin", "finger"))       ENT.echo(Char.findPlan(rem));
    else if (match(key, "cl", "clans"))         Clan.clanCommand(ENT,rem);
    else if (match(key, "re", "religions"))     Religion.religionCommand(ENT,Rem);

    // *************************************************************************************************** //
    // ******************************************** Movement ********************************************* //
    // *************************************************************************************************** //

    else if (match(key, "we", "we"))            ENT.speedwalk(key);
    else if (match(key, "n", "north"))          ENT.move(DIRECTION_N);
    else if (match(key, "s", "south"))          ENT.move(DIRECTION_S);
    else if (match(key, "e", "east"))           ENT.move(DIRECTION_E);
    else if (match(key, "w", "west"))           ENT.move(DIRECTION_W);
    else if (match(key, "d", "down"))           ENT.move(DIRECTION_D);
    else if (match(key, "u", "up"))             ENT.move(DIRECTION_U);
    else if (speedwalkLegal(key))               ENT.speedwalk(key);
    else if (match(key, "sit", "sit"))          ENT.sit(rem);
    else if (match(key, "st", "stand"))         ENT.stand(rem);
    else if (match(key, "rest", "rest"))        ENT.rest(rem);
    else if (match(key, "sl", "sleep"))         ENT.sleep(rem);

    // *************************************************************************************************** //
    // ***************************************** Combat Commands ***************************************** //
    // *************************************************************************************************** //

    else if (match(key, "k", "kill"))           Combat.engage(ENT, frem);
    else if (match(key, "att", "attack"))       Combat.engage(ENT, frem);
    else if (match(key, "c", "cast"))           ENT.cast(rem);
    else if (match(key, "a", "assist"))         Combat.manualAssist(ENT, frem);
    else if (match(key, "f", "flee"))           Combat.flee(ENT);

    // *************************************************************************************************** //
    // ***************************************** Group Commands ****************************************** //
    // *************************************************************************************************** //

    else if (match(key, "gr", "group"))         Group.group(ENT,frem);
    else if (match(key, "un", "ungroup"))       Group.ungroup(ENT,frem);
    else if (match(key, "dis", "disband"))      Group.ungroup(ENT,"all");
    else if (match(key, "fol", "follow"))       ENT.follow(frem);
    else if (match(key, "dit", "ditch"))        ENT.ditch(frem);

    // *************************************************************************************************** //
    // ***************************************** Skill Commands ****************************************** //
    // *************************************************************************************************** //

    else if (match(key, "tr", "track"))            ENT.executeSkill("track", rem);
    else if (match(key, "sc", "scan"))             ENT.executeSkill("scout", rem);
    else if (match(key, "sc", "scout"))            ENT.executeSkill("scout", rem);
    else if (match(key, "di", "disarm"))           ENT.executeSkill("disarm", rem);
    else if (match(key, "po", "poison"))           ENT.executeSkill("poison strike", rem);
    else if (match(key, "ti", "tiger"))            ENT.executeSkill("tiger strike", rem);
    else if (match(key, "as", "assassinate"))      ENT.executeSkill("assassin strike", rem);
    else if (match(key, "st", "stab"))             ENT.executeSkill("stab and twist", rem);
    else if (match(key, "ci", "circle"))           ENT.executeSkill("circle around", rem);
    else if (match(key, "ba", "backstab"))         ENT.executeSkill("backstab", rem);
    else if (match(key, "blossom", "blossom"))     ENT.executeSkill("death blossom", rem);
    else if (exact("death blossom"))               ENT.executeSkill("death blossom", rem);

    // *************************************************************************************************** //
    // ******************************************** Item Uses ******************************************** //
    // *************************************************************************************************** //

    else if (match(key, "write", "write"))      MessageBoard.writeNote(ENT, Rem);
    else if (match(key, "re", "read"))          ENT.read(rem);
    else if (match(key, "rem", "remove"))       ENT.remove(rem);
    else if (match(key, "eq", "equipment"))     Equipment.equip(ENT, rem);
    else if (match(key, "we", "wear"))          Equipment.equip(ENT, rem);
    else if (match(key, "wi", "wield"))         Equipment.wield(ENT, rem);
    else if (match(key, "use", "use"))          ItemUse.use(ENT,rem);
    else if (match(key, "i", "inventory"))      ItemUse.displayInventory(ENT);
    else if (match(key, "ge", "get"))           ItemUse.get(ENT,rem);
    else if (match(key, "gi", "give"))          ItemUse.give(ENT,rem);
    else if (match(key, "pu", "put"))           ItemUse.put(ENT,rem);
    else if (match(key, "dr", "drop"))          ItemUse.drop(ENT,rem);
    else if (match(key, "sac", "sacrifice"))    ItemUse.sacrifice(ENT,rem);
    //else if (match(key, "op", "open"))          ItemUse.openBlocker(ENT,rem);
    //else if (match(key, "clo", "close"))        ItemUse.closeBlocker(ENT,rem);
    //else if (match(key, "pu", "pull"))          ItemUse.pull(ENT,rem);
    //else if (match(key, "pl", "play"))          ItemUse.play(ENT,rem);
    else if (match(key, "eat", "eat"))          ItemUse.eat(ENT,rem);
    else if (match(key, "dr", "drink"))         ItemUse.drink(ENT,rem);
    else if (match(key, "qu", "quaff"))         ItemUse.quaff(ENT,rem);
    else if (match(key, "fi", "fill"))          ItemUse.fill(ENT,rem);
    else if (match(key, "em", "empty"))         ItemUse.empty(ENT,rem);
    else if (match(key, "rec", "recite"))       ItemUse.recite(ENT,rem);

    // *************************************************************************************************** //
    // ********************************************* Socials ********************************************* //
    // *************************************************************************************************** //

    else if (match(key, "bl", "blink"))         Socials.blink(ENT,frem);
    else if (match(key, "no", "nod"))           Socials.nod(ENT,frem);
    else if (match(key, "la", "laugh"))         Socials.laugh(ENT,frem);
    else if (match(key, "ca", "cackle"))        Socials.cackle(ENT,frem);
    else if (match(key, "sh", "shake"))         Socials.shake(ENT,frem);
    else if (match(key, "sm", "smile"))         Socials.smile(ENT,frem);
    else if (match(key, "gr", "grin"))          Socials.grin(ENT,frem);
    else if (match(key, "si", "sigh"))          Socials.sigh(ENT,frem);
    else if (match(key, "sh", "shrug"))         Socials.shrug(ENT,frem);
    else if (match(key, "pe", "peer"))          Socials.peer(ENT,frem);
    else if (match(key, "fr", "frown"))         Socials.frown(ENT,frem);
    else if (match(key, "gr", "growl"))         Socials.growl(ENT,frem);
    else if (match(key, "sn", "snarl"))         Socials.snarl(ENT,frem);
    else if (match(key, "ti", "tip"))           Socials.tip(ENT,frem);
    else if (match(key, "gi", "giggle"))        Socials.giggle(ENT,frem);

    // *************************************************************************************************** //
    // **************************************** Immortal Commands **************************************** //
    // *************************************************************************************************** //

    else if ((IMM(100)) && (match(key, "im", "immortal")))             ENT.echo(HelpFile.getImmCommands());
    else if ((IMM(100)) && (match(key, "us", "users")))                ENT.echo(ClientList.userList());
    else if ((IMM(100)) && (match(key, "sy", "systime")))              ENT.echo(SystemTime.displayDate());
    else if ((IMM(100)) && (match(key, "at", "at")))                   Immortal.at(ENT,frem,last(Rem));
    else if ((IMM(100)) && (match(key, "go", "goto")))                 Immortal.goTo(ENT,frem);
    else if ((IMM(100)) && (match(key, "tr", "transport")))            Immortal.trans(ENT,frem);
    else if ((IMM(100)) && (match(key, "id", "identify")))             Immortal.identify(ENT,rem);
    else if ((IMM(100)) && (match(key, "il", "ilist")))                Immortal.ilist(ENT,rem);
    else if ((IMM(100)) && (match(key, "rl", "rlist")))                Immortal.rlist(ENT,rem);
    else if ((IMM(100)) && (match(key, "ml", "mlist")))                Immortal.mlist(ENT,rem);
    else if ((IMM(100)) && (match(key, "zl", "zlist")))                Immortal.zlist(ENT,rem);
    else if ((IMM(100)) && (match(key, "al", "alist")))                Immortal.alist(ENT,rem);
    else if ((IMM(100)) && (match(key, "zr", "zrepop")))               Immortal.zrepop(ENT,rem);
    else if ((IMM(100)) && (match(key, "zc", "zclear")))               Immortal.zclear(ENT,rem);
    else if ((IMM(100)) && (match(key, "sl", "slay")))                 Immortal.slay(ENT,frem);
    else if ((IMM(100)) && (match(key, "res", "restore")))             Immortal.restore(ENT,frem);
    else if ((IMM(100)) && (match(key, "lo", "load")))                 Immortal.load(ENT,rem);
    else if ((IMM(100)) && (match(key, "pur", "purge")))               Immortal.purge(ENT,rem);
    else if ((IMM(100)) && (match(key, "pos", "possess")))             Immortal.possess(ENT,frem);
    else if ((IMM(1)) && (match(key, "unp", "unpossess")))             Immortal.unpossess(CLT,true);
    else if ((IMM(100)) && (match(key, "fo", "force")))                Immortal.force(ENT,frem,last(Rem));
    else if ((IMM(100)) && (match(key, "ma", "mark")))                 Immortal.markLegend(ENT,frem,last(Rem));
    else if ((IMM(100)) && (match(key, "unm", "unmark")))              Immortal.unmarkLegend(ENT,frem,last(rem));
    else if ((IMM(100)) && (match(key, "bes", "bestow")))              Immortal.bestow(ENT,rem);
    else if ((IMM(100)) && (match(key, "dep", "deprive")))             Immortal.deprive(ENT,rem);
    else if ((IMM(100)) && (match(key, "str", "strip")))               Immortal.deprive(ENT,rem);
    else if ((IMM(100)) && (match(key, "snoo", "snoop")))              Immortal.snoop(ENT, rem);
    else if ((IMM(100)) && (match(key, "dc", "dc")))                   Immortal.dc(ENT,rem);
    else if ((IMM(100)) && (match(key, "disconnect", "disconnect")))   Immortal.dc(ENT, rem);
    else if ((IMM(100)) && (match(key, "se", "set")))                  Immortal.set(ENT, Rem);
    else if ((IMM(100)) && (match(key, "re", "report")))               Immortal.report(ENT, Rem);
    else if ((IMM(100)) && (match(key, "zedit", "zedit")))             BuilderZone.enterZoneBuilder(CLT);
    else if ((IMM(100)) && (match(key, "redit", "redit")))             BuilderRoom.enterRoomBuilder(CLT);
    else if ((IMM(100)) && (match(key, "iedit", "iedit")))             BuilderItem.enterItemBuilder(CLT, rem);
    else if ((IMM(100)) && (match(key, "medit", "medit")))             BuilderMob.enterMobBuilder(CLT, rem);
    else if ((IMM(100)) && (match(key, "aedit", "aedit")))             BuilderAction.enterActionBuilder(CLT, rem);
    else if ((IMM(100)) && (match(key, "banl", "banlist")))            Immortal.banList(ENT, rem);
    else if ((IMM(100)) && (match(key, "blockl", "blocklist")))        Immortal.blockList(ENT, rem);
    else if ((IMM(100)) && (match(key, "freezel", "freezelist")))      Immortal.freezeList(ENT, rem);
    else if ((IMM(100)) && (match(key, "showr", "showrent")))          Immortal.showRent(ENT, rem);
    else if ((IMM(100)) && (match(key, "pav", "pave")))                Immortal.pave(ENT, rem);
    else if ((IMM(100)) && (match(key, "li", "list")))                 Immortal.list(ENT, rem);

    // 110 Only Commands.

    else if ((IMM(110)) && (match(key, "clanedit", "clanedit")))       BuilderClan.enterClanBuilder(CLT, rem);
    else if ((IMM(110)) && (match(key, "reledit", "reledit")))         BuilderReligion.enterBuilder(CLT, rem);
    else if ((IMM(110)) && (match(key, "dis", "disintegrate")))        Immortal.disintegrate(ENT,rem);
    else if ((IMM(110)) && (match(key, "account", "account")))         Immortal.reportAccount(ENT, Rem);
    else if ((IMM(110)) && (match(key, "ban", "ban")))                 Immortal.ban(ENT, Rem);
    else if ((IMM(110)) && (match(key, "unban", "unban")))             Immortal.unban(ENT, Rem);
    else if ((IMM(110)) && (match(key, "block", "block")))             Immortal.block(ENT, Rem);
    else if ((IMM(110)) && (match(key, "unblock", "unblock")))         Immortal.unblock(ENT, Rem);
    else if ((IMM(110)) && (match(key, "freeze", "freeze")))           Immortal.freeze(ENT, Rem);
    else if ((IMM(110)) && (match(key, "unfreeze", "unfreeze")))       Immortal.unfreeze(ENT, Rem);
    else if ((IMM(110)) && (match(key, "deleteacc", "deleteaccount"))) Immortal.deleteAccount(ENT, rem);

    // Creation Commands:

    else if ((IMM(110)) && (match(key, "makez", "makezone")))          Immortal.makeZone(ENT);
    else if ((IMM(110)) && (match(key, "makec", "makeclan")))          Immortal.makeClan(ENT);
    else if ((IMM(110)) && (match(key, "maker", "makereligion")))      Immortal.makeReligion(ENT);
    else if ((IMM(110)) && (match(key, "makeb", "makeboard")))         Immortal.makeMessageBoard(ENT, Rem);
    else if ((IMM(110)) && (match(key, "makeb", "makebank")))          Immortal.makeBankChain(ENT, Rem);

    // Dangerous Commands:

    else if ((IMM(110)) && (match(key, "mortal", "mortalize")))        Immortal.mortalize(ENT, Rem);
    else if ((IMM(110)) && (match(key, "killgame", "killgame")))       Immortal.killGame(ENT, rem);
    else if ((IMM(110)) && (match(key, "saveworld", "saveworld")))     Immortal.saveWorld(ENT);
    else if ((IMM(110)) && (match(key, "clearmobpop", "clearmobpop"))) Immortal.clearMobPopulation(ENT, Rem);
    else if ((IMM(110)) && (match(key, "clearcl", "clearclan")))       Immortal.clearClan(ENT, Rem);
    else if ((IMM(110)) && (match(key, "renumber", "renumber")))       World.globalRenumber(ENT, rem);
    else if ((IMM(110)) && (match(key, "fragmobs", "fragmobs")))       World.completeMobReset(ENT, rem);

    // *************************************************************************************************** //
    // ***************************************** Player Commands ***************************************** //
    // *************************************************************************************************** //

    else if ((PLAYER) && (match(key, "alias", "alias")))          CHR.getAliases().addAlias(Rem);
    else if ((PLAYER) && (match(key, "le", "learn")))             ENT.echo(Learning.learn(ENT,rem));
    else if ((PLAYER) && (match(key, "un", "unlearn")))           ENT.echo(Learning.unlearn(ENT,Rem));
    else if ((PLAYER) && (match(key, "fo", "forget")))            ENT.echo(Learning.unlearn(ENT,Rem));
    else if ((PLAYER) && (match(key, "te", "teach")))             ENT.echo(Learning.teach(ENT,rem));
    else if ((PLAYER) && (match(key, "tit", "title")))            ENT.title(Rem);
    else if ((PLAYER) && (match(key, "pren", "prename")))         ENT.prename(Rem);
    else if ((PLAYER) && (match(key, "prom", "prompt")))          CHR.setPrompt(Rem);
    else if ((PLAYER) && (match(key, "tog", "toggle")))           CHR.toggle(frem);
    else if ((PLAYER) && (match(key, "res", "resize")))           CHR.setScreenSize(frem, true);
    else if ((PLAYER) && (match(key, "cle", "clear")))            CLT.clearScreen(true);
    else if ((PLAYER) && (match(key, "clr", "clr")))              CLT.clearScreen(true);
    else if ((PLAYER) && (match(key, "cls", "cls")))              CLT.clearScreen(true);
    else if ((PLAYER) && (match(key, "col", "colors")))           ENT.echo(HelpFile.getColorList());
    else if ((PLAYER) && (match(key, "cla", "classes")))          ENT.echo(HelpFile.getClassList());
    else if ((PLAYER) && (match(key, "ali", "alignment")))        ENT.echo(HelpFile.helpAlignment());
    else if ((PLAYER) && (match(key, "ad", "add")))               CHR.addStat(ENT, frem);
    else if ((PLAYER) && (match(key, "date", "date")))            ENT.echo(SystemTime.getGamedate("all"));
    else if ((PLAYER) && (match(key, "time", "time")))            ENT.echo(SystemTime.getGamedate("all"));
    else if ((PLAYER) && (match(key, "sav", "save")))             ENT.castChar().saveCharacter();
    else if ((PLAYER) && (match(key, "quit", "quit")))            ENT.castChar().quit();

    // *************************************************************************************************** //
    // ***************************************** Mobile Commands ***************************************** //
    // *************************************************************************************************** //

    else if ((!PLAYER) && (match(key, "respond", "respond")))     ENT.castMob().respond(Rem);
    else if ((!PLAYER) && (match(key, "mobwalk", "mobwalk")))     ENT.castMob().moveWithinZone();
    else if ((!PLAYER) && (match(key, "mobroam", "mobroam")))     ENT.castMob().moveAcrossZone();
    else if ((!PLAYER) && (match(key, "mobseek", "mobseek")))     ENT.castMob().seekRoom(Rem);
    else if ((!PLAYER) && (match(key, "attackr", "attackrand")))  ENT.castMob().attackRand(rem);
    else if ((!PLAYER) && (match(key, "evaluate", "evaluate")))   Evaluator.evaluate(ENT, rem);

    // *************************************************************************************************** //
    // ***************************************** Ghost Commands ****************************************** //
    // *************************************************************************************************** //

    else if ((GHOST) && (match(key, "echo", "echo")))             ENT.castGhost().ghostEcho(Rem);
    else if ((GHOST) && (match(key, "move", "move")))             ENT.castGhost().ghostMove(rem);
    else if ((GHOST) && (match(key, "load", "load")))             ENT.castGhost().ghostLoad(rem);
    else if ((GHOST) && (match(key, "damageroom", "damageroom"))) ENT.castGhost().giveDamage(rem);
    else if ((GHOST) && (match(key, "damagerand", "damagerand"))) ENT.castGhost().giveDamageRandom(Rem);
    else if ((GHOST) && (match(key, "targetecho", "targetecho"))) ENT.castGhost().echoToTargets(Rem);
    else if ((GHOST) && (match(key, "othersecho", "othersecho"))) ENT.castGhost().echoToOthers(Rem);

    // *************************************************************************************************** //
    // *************************************************************************************************** //
    // *************************************************************************************************** //

    // Temporary Commands.
    else if (match(key, "lev", "level")) { ENT.castChar().levelUp();  ENT.echo("Ok."); }

    else if (match(key, "leg", "legend")) {
      Entity E = ENT.getRoom().findEntity(ENT, rem);
      if ((E != null) && (!E.isPlayer())) return;
      if (E != null) ENT.echo(E.castChar().getLegend().getLegend());
      else ENT.echo(ENT.castChar().getLegend().getLegend()); }

    // *************************************************************************************************** //
    // *************************************************************************************************** //
    // *************************************************************************************************** //

    else ENT.echo("Invalid Command.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}