import java.io.*;
import java.util.*;

public class ImmSet extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void set(Entity SRC, String str)

  {
    String er = "";
    String weak = "You can't do that.";
    boolean failed = false;

    er += "Set Usage#n:   #R[#N set <name> <attribute> <value>           #R]#N\r\n\n";
    er += "Attributes#n:  #R[#N name, fullname, level, gender, class     #R]#N\r\n";
    er += "             #R[#N hometown, clan, religion, prename, title #R]#N\r\n";
    er += "             #R[#N room, screensize, ANSI, color, follow    #R]#N\r\n";
    er += "             #R[#N assist, exp, gold, bank, dpoints         #R]#N\r\n";
    er += "             #R[#N STR, DEX, CON, INT, WIS, HR, DR, AC, MR  #R]#N\r\n";
    er += "             #R[#N HP, maxHP, MN, maxMN, MV, maxMV          #R]#N\r\n";
    er += "             #R[#N clanrank, religionrank, dietytitle       #R]#N\r\n";
    er += "             #R[#N alignment, mentor, politicalrank         #R]#N";

    if (SRC.getLevel() == 110)
    if (first(str).equalsIgnoreCase("account")) {
      setAccount(SRC, last(str));
      return; }

    try {

    String name = first(str).toLowerCase();
    String attr = first(last(str)).toLowerCase();
    String val = last(last(str));

    Entity Target = World.findCharacter(name);

    if (str.length() <= 0) throw ex(er);
    if (name.length() <= 0) throw ex(er);
    if (attr.length() <= 0) throw ex(er);
    if (val.length() <= 0) throw ex(er);
    if (Target == null) throw ex("There is no such person.");
    if ((Target.getLevel() >= SRC.getLevel()) && (Target != SRC)) throw ex(weak);

    String oldName = Target.getName();

    if (attr.equalsIgnoreCase("name"))

    {
      WriteThread.msg("deletecharacter " + Target.getName());
      Target.setName(properName(first(val)));
      Target.castChar().updateMemberships(oldName);
      SRC.echo(possessive(oldName) + " name has been set to " + Target.getName() + ".");
    }

    else if (attr.equalsIgnoreCase("fullname"))

    {
      Target.setFullName(val);
      SRC.echo(possessive(oldName) + " name has been set to " + Target.getName() + ".");
    }

    else if (attr.equalsIgnoreCase("title"))

    {
      Target.setTitle(val);
      SRC.echo(possessive(Target.getName()) + " title has been set.");
    }

    else if (attr.equalsIgnoreCase("dietytitle"))

    {
      if (Target.getLevel() < 100) throw ex("You can only set that for Gods!");
      Target.castChar().setDietyTitle(val);
      SRC.echo(possessive(Target.getName()) + " diety title has been set.");
    }

    else if (attr.equalsIgnoreCase("class"))

    {
      if (!ClassMethods.validClass(val)) throw ex("Invalid Class Specified.");
      Target = Target.castChar().setClass(properName(val));
      if (Target == null) throw ex("Invalid Class Specified.");
      SRC.echo(possessive(Target.getName()) + " class has been set.");
      World.setCharacter(Target);
    }

    else if (attr.equalsIgnoreCase("level"))

    {
      if (!number(val)) throw ex("That's not a valid level.");
      int newlevel = Integer.parseInt(val);
      if ((SRC.getLevel() < 110) && (newlevel > 99)) throw ex(weak);
      if (newlevel < 1) throw ex(weak);
      Target.setLevel(newlevel);
      SRC.echo(possessive(Target.getName()) + " level has been set.");
    }

    else if (attr.equalsIgnoreCase("alignment"))

    {
      if (val.equalsIgnoreCase("neutral")) Target.setAlignment(ALIGN_NEUTRAL);
      else if (val.equalsIgnoreCase("light")) Target.setAlignment(ALIGN_LIGHT);
      else if (val.equalsIgnoreCase("dark")) Target.setAlignment(ALIGN_DARK);
      else throw ex("Alignment must be neutral, light or dark.");
      SRC.echo(possessive(Target.getName()) + " alignment has been set.");
    }

    else if (attr.equalsIgnoreCase("gender"))

    {
      if ((!val.equalsIgnoreCase("male")) && (!val.equalsIgnoreCase("female")))
      throw ex("Invalid Gender.  Must be 'male' or 'female'.");
      Target.setGender(val);
      SRC.echo(possessive(Target.getName()) + " gender has been set.");
    }

    else if (attr.equalsIgnoreCase("hometown"))

    {
      Target.castChar().setHometown(val);
      SRC.echo(possessive(Target.getName()) + " hometown has been set.");
    }

    else if (attr.equalsIgnoreCase("prename"))

    {
      Target.castChar().setPrename(val);
      SRC.echo(possessive(Target.getName()) + " prename has been set.");
    }

    else if (attr.equalsIgnoreCase("room"))

    {
      if (!number(val)) throw ex("That's not a valid room number.");
      Room R = World.getRoom(Integer.parseInt(val));
      if (R == null) throw ex("There is no such room.");
      Entity E = ClientList.findCharacter(Target.getName());
      if (Target.castChar().isOnline()) Target.getRoom().leave(Target, LEAVE_TRANS);
      if (Target.castChar().isOnline()) R.enter(Target, ENTER_TRANS);
      if (!Target.castChar().isOnline()) Target.setRoom(R.getID());
      SRC.echo(possessive(Target.getName()) + " room has been set.");
    }

    else if (attr.equalsIgnoreCase("screensize"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      if (!Target.isPlayer()) throw ex("You can't do that.");
      Target.castChar().setScreenSize(val, true);
      SRC.echo(possessive(Target.getName()) + " screensize has been set.");
    }

    else if (attr.equalsIgnoreCase("ansi"))

    {
      String s = possessive(Target.getName()) + " ANSI mode has been set.";
      if ((!val.equalsIgnoreCase("on")) && (!val.equalsIgnoreCase("off")))
      throw ex("Invalid ANSI Mode.  Must be 'on' or 'off'.");
      if (!Target.isPlayer()) throw ex("You can't do that.");
      if (val.equalsIgnoreCase("on")) Target.castChar().setAnsiMode(true);
      if (val.equalsIgnoreCase("off")) Target.castChar().setAnsiMode(false);
      if (SRC != Target) SRC.echo(s);
    }

    else if (attr.equalsIgnoreCase("color"))

    {
      if ((!val.equalsIgnoreCase("on")) && (!val.equalsIgnoreCase("off")))
      throw ex("Invalid Color Mode.  Must be 'on' or 'off'.");
      if (!Target.isPlayer()) throw ex("You can't do that.");
      if (val.equalsIgnoreCase("on")) Target.castChar().setColorMode(true);
      if (val.equalsIgnoreCase("off")) Target.castChar().setColorMode(false);
      SRC.echo(possessive(Target.getName()) + " color mode has been set.");
    }

    else if (attr.equalsIgnoreCase("follow"))

    {
      if ((!val.equalsIgnoreCase("on")) && (!val.equalsIgnoreCase("off")))
      throw ex("Invalid Follow Mode.  Must be 'on' or 'off'.");
      if (val.equalsIgnoreCase("on")) Target.setFollowable(true);
      if (val.equalsIgnoreCase("off")) Target.setFollowable(false);
      SRC.echo(possessive(Target.getName()) + " follow mode has been set.");
    }

    else if (attr.equalsIgnoreCase("assist"))

    {
      if ((!val.equalsIgnoreCase("on")) && (!val.equalsIgnoreCase("off")))
      throw ex("Invalid Autoassist Mode.  Must be 'on' or 'off'.");
      if (val.equalsIgnoreCase("on")) Target.setAutoassist(true);
      if (val.equalsIgnoreCase("off")) Target.setAutoassist(false);
      SRC.echo(possessive(Target.getName()) + " autoassist has been set.");
    }

    else if (attr.equalsIgnoreCase("STR"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setSTR(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " strength has been set.");
    }

    else if (attr.equalsIgnoreCase("DEX"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setDEX(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " dexterity has been set.");
    }

    else if (attr.equalsIgnoreCase("CON"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setCON(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " constitution has been set.");
    }

    else if (attr.equalsIgnoreCase("INT"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setINT(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " intelligence has been set.");
    }

    else if (attr.equalsIgnoreCase("WIS"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setWIS(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " wisdom has been set.");
    }

    else if (attr.equalsIgnoreCase("HR"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setHR(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " hitroll has been set.");
    }

    else if (attr.equalsIgnoreCase("DR"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setDR(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " damroll has been set.");
    }

    else if (attr.equalsIgnoreCase("AC"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setAC(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " armorclass has been set.");
    }

    else if (attr.equalsIgnoreCase("MR"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setMR(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " resistance has been set.");
    }

    else if (attr.equalsIgnoreCase("dpoints"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.castChar().setDPoints(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " distribution points have been set.");
    }

    else if (attr.equalsIgnoreCase("gold"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setGold(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " gold has been set.");
    }

    else if (attr.equalsIgnoreCase("exp"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.castChar().setExp(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " experience has been set.");
    }

    else if (attr.equalsIgnoreCase("HP"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setCurrentHP(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " hitpoints have been set.");
    }

    else if (attr.equalsIgnoreCase("MN"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setCurrentMN(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " mana has been set.");
    }

    else if (attr.equalsIgnoreCase("MV"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setCurrentMV(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " movement has been set.");
    }

    else if (attr.equalsIgnoreCase("maxHP"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setMaxHP(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " maximum hitpoints have been set.");
    }

    else if (attr.equalsIgnoreCase("maxMN"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setMaxMN(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " maximum mana has been set.");
    }

    else if (attr.equalsIgnoreCase("maxMV"))

    {
      if (!number(val)) throw ex("That's not a valid number.");
      Target.setMaxMV(Integer.parseInt(val));
      SRC.echo(possessive(Target.getName()) + " maximum movement has been set.");
    }

    else if (attr.equalsIgnoreCase("clan"))

    {
      int newClan;
      if (val.equalsIgnoreCase("None")) newClan = -1;
      else if (World.getClan(val) == null) throw ex("There is no such clan.");
      else newClan = World.getClan(val).getID();
      int currentClan = Target.getClanNum();
      if (currentClan != -1) World.getClan(currentClan).removeMember(Target.getName());
      if (newClan != -1) World.getClan(newClan).addMember(Target);
      Target.setClan(newClan);
      SRC.echo(possessive(Target.getName()) + " clan has been set.");
    }

    else if (attr.equalsIgnoreCase("clanrank"))

    {
      int cnum = Target.getClanNum();
      if (cnum == -1) throw ex(Target.getName() + " isn't even in a clan.");
      if (!number(val)) throw ex("That's not a valid number.");
      int newRank = Integer.parseInt(val);
      int nr = World.getClan(cnum).getNumRanks();
      if ((Target.getLevel() > 99) && ((newRank != 0) && (newRank != (nr+1))))
      throw ex("Immortals can only be rank 0 or " + (nr+1) + ".");
      if ((Target.getLevel() <= 99) && ((newRank < 0) || (newRank > nr)))
      throw ex("That's not a valid rank for that clan.");
      World.getClan(cnum).setMemberRank(Target.getName(), newRank);
      SRC.echo(possessive(Target.getName()) + " clanrank has been set.");
    }

    else { SRC.echo(er);  failed = true; }

    if (!failed) Target.castChar().save();

    } // End try.

    catch (Exception e) { SRC.echo(e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void setAccount(Entity SRC, String str)

  {
    String er = "";
    String weak = "You can't do that.";
    boolean failed = false;

    er += "Set Account#n: #R[#N set account <account> <attribute> <value> #R]#N\r\n\n";
    er += "Attributes#n:  #R[#N username, password, registered            #R]#N";

    try {

    String name = first(str).toLowerCase();
    String attr = first(last(str)).toLowerCase();
    String val = last(last(str));
    String oldName = name;
    String aname = "Account " + name;

    if (str.length() <= 0) throw ex(er);
    if (name.length() <= 0) throw ex(er);
    if (attr.length() <= 0) throw ex(er);
    if (val.length() <= 0) throw ex(er);

    Account account = World.findAccount(name);

    if (account == null)
      throw ex("There is no such account.");

    if (attr.equalsIgnoreCase("username"))

    {
      if (!Account.validAccountName(val)) throw ex("Invalid Account Name.");
      if (!Account.usableAccountName(val)) throw ex("Account Name Unavailable.");
      val = properName(val);
      account.setUsername(val);
      account.changedUsername();
      WriteThread.msg("deleteaccount " + name);
      SRC.echo(aname + " username set to " + val + ".");
    }

    else if (attr.equalsIgnoreCase("password"))

    {
      String newPass = first(val);
      if (!Account.validPassword(newPass)) throw ex("Invalid Password.");
      account.setPassword(newPass);
      SRC.echo(aname + " password set to " + newPass + ".");
    }

    else if (attr.equalsIgnoreCase("registered"))

    {
      if (val.equalsIgnoreCase("true")) account.setRegistered(true);
      else if (val.equalsIgnoreCase("false")) account.setRegistered(false);
      else throw ex("You can only set registered to 'true' or 'false'.");
      SRC.echo(aname + " registered set to " + val.toLowerCase() + ".");
    }

    else { SRC.echo(er);  failed = true; }

    if (!failed) account.save();

    } // End try.

    catch (Exception e) { SRC.echo(e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}