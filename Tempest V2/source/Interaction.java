import java.io.*;
import java.util.*;

public class Interaction extends Utility

{
  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryChangePlanError(Mobile mob)

  {
    if (mob.getFlag(Mobile.FLAG_BARD)) return false;

    boolean cond1 = mob.HEARD("change my plan");
    boolean cond2 = mob.HEARD("change my description");

    if ((!cond1) && (!cond2)) return false;

    mob.act("respond I can't help you with that. Try asking a Bard.");
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryNotBardError(Mobile mob)

  {
    if (mob.getFlag(Mobile.FLAG_BARD)) return false;

    boolean cond1 = mob.heard("bard");
    boolean cond2 = mob.heard("bards");
    boolean cond3 = mob.heard("plan");
    boolean cond4 = mob.heard("description");

    if ((!cond1) && (!cond2) && (!cond3) && (!cond4)) return false;

    String response  = "Bards can help you change your plan and description. ";
           response += "I, however, am no Bard.";

    mob.act("respond " + response);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryChangeDescription(Mobile mob, Entity SRC)

  {
    if (!mob.getFlag(Mobile.FLAG_BARD)) return false;
    if (!mob.HEARD("change my description")) return false;
    if (SRC.getClient() == null) return false;
    if (SRC.isPlayer() == false) return false;

    Writable W = SRC.getDescWrite();
    Composer newComposer = new Composer(SRC, W);
    newComposer.setLineWidth(WRITING_WIDTH);
    newComposer.displayInfo();

    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryChangePlan(Mobile mob, Entity SRC)

  {
    if (!mob.getFlag(Mobile.FLAG_BARD)) return false;
    if (!mob.HEARD("change my plan")) return false;
    if (SRC.getClient() == null) return false;
    if (SRC.isPlayer() == false) return false;

    Writable W = SRC.castChar().getPlanWrite();
    W.setFormat(Writable.FORMAT_EXACT);
    Composer newComposer = new Composer(SRC, W);
    newComposer.setLineWidth(PLAN_WIDTH);
    newComposer.displayInfo();

    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryAmBard(Mobile mob)

  {
    if (!mob.getFlag(Mobile.FLAG_BARD)) return false;

    boolean cond1 = mob.heard("bard");
    boolean cond2 = mob.heard("bards");
    boolean cond3 = mob.heard("plan");
    boolean cond4 = mob.heard("description");

    if ((!cond1) && (!cond2) && (!cond3) && (!cond4)) return false;

    String response  = "I am a Bard. ";
           response += "I can help you change your plan or description. ";
           response += "If that is your wish, just say so.";

    mob.act("respond " + response);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryCannotTeach(Mobile mob)

  {
    if ((!mob.heard("teach")) & (!mob.heard("learn"))) return false;

    String response  = "I cannot teach you any skills or spells. ";
           response += "To learn new abilities, you must seek the assistance";
           response += "of one who already knows the skill or spell that you";
           response += "wish to learn, and they can teach you.";

    mob.act("respond " + response);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryNotSkillmaster(Mobile mob)

  {
    if (mob.getFlag(Mobile.FLAG_SKILLMASTER)) return false;
    if ((!mob.heard("skill")) && (!mob.heard("learn"))) return false;

    String response  = "I don't know much about skills. ";
           response += "Try asking a Skill Master.";

    mob.act("respond " + response);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryNotMerchant(Mobile mob)

  {
    if (mob.getFlag(Mobile.FLAG_MERCHANT)) return false;

    boolean cond1 = mob.heard("buy");
    boolean cond2 = mob.heard("sell");
    boolean cond3 = mob.heard("list");

    if ((!cond1) && (!cond2) && (!cond3)) return false;

    String response  = "I'm sorry, I am not a merchant. ";
           response += "I do not buy or sell items.";

    mob.act("respond " + response);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryMentorLeave(Mobile mob, Entity SRC)

  {
    if ((!mob.getFlag(Mobile.FLAG_SKILLMASTER))
       && (!mob.getFlag(Mobile.FLAG_SPELLMASTER)))
         return false;

    if (!mob.HEARD("leave my mentor")) return false;
    if (SRC.isPlayer() == false) return false;

    boolean passed = false;
    String response = "";

    if (SRC.castChar().getMentor().length() <= 0)
      response = "You have no mentor to leave, " + SRC.getName() + ".";
    else passed = true;

    if (passed)

    {
      Question Q = new Question(mob, SRC, Question.LEAVE_MENTOR);

      if (mob.addQuestion(Q)) {
        response  = "Leaving your mentor is a very serious matter, " + SRC.getName();
        response += ". If you do this, it will take some time for you to recover ";
        response += "emotionally, and you will be unable to take on a new teacher ";
        response += "for at least a season or two. Are you certain you want to do it?"; }

      else response = Q.getFailedMessage(SRC.getName());
    }

    mob.act("respond " + response);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryMentorRequest(Mobile mob, Entity SRC, String s)

  {
    if ((!mob.getFlag(Mobile.FLAG_SKILLMASTER))
      && (!mob.getFlag(Mobile.FLAG_SPELLMASTER)))
        return false;

    if (SRC.isPlayer() == false) return false;

    boolean c1 = (mob.HEARD("wish to mentor"));
    boolean c2 = (mob.HEARD("like to mentor"));
    boolean c3 = (mob.HEARD("want to mentor"));
    boolean c4 = (mob.HEARD("going to mentor"));
    boolean c5 = (mob.HEARD("will mentor"));
    boolean c6 = (mob.HEARD("shall mentor"));

    if (c1) s = s.substring(makePlain(s).indexOf("wish to mentor")+14, s.length());
    else if (c2) s = s.substring(makePlain(s).indexOf("like to mentor")+14, s.length());
    else if (c3) s = s.substring(makePlain(s).indexOf("want to mentor")+14, s.length());
    else if (c4) s = s.substring(makePlain(s).indexOf("going to mentor")+15, s.length());
    else if (c5) s = s.substring(makePlain(s).indexOf("will mentor")+11, s.length());
    else if (c6) s = s.substring(makePlain(s).indexOf("shall mentor")+12, s.length());
    else return false;

    String target = first(clearWhiteSpace(removePunctuation(makePlain(s))));
    Entity Target = mob.getRoom().findEntity(mob, target);
    boolean passed = false;
    String response = "";

    if (SRC.getLevel() < 10)
      response = "You must be at least level 10 to mentor somebody.";
    else if (Target == null)
      response = "Bring your new student to me and I shall make it official.";
    else if (Target == SRC)
      response = "You want to be your own mentor? You are very strange...";
    else if (!Target.isPlayer())
      response = "No, I don't think that's a very good idea.";
    else if (Target.getLevel() > SRC.getLevel())
      response = Target.getName() + " is already more experienced than you are.";
    else if (!ClassMethods.validMentor(Target, SRC))
      response = "There's not much you could teach a " + Target.getCharClass() + ".";
    else if (Target.castChar().getMentor().length() > 0)
      response = Target.He() + " already has a mentor.";
    else if (SRC.castChar().getMentor().equals(Target.getName()))
      response = "You can't be your teacher's teacher. Isn't that obvious?";
    else if (!Target.checkSchedule(Schedule.LEAVE_MENTOR))
      response = Target.He() + " not ready to take on another teacher yet.";
    else passed = true;

    if (passed)

    {
      Question Q = new Question(mob, Target, Question.ACCEPT_MENTOR);
      Q.addAnswerParameter(SRC.getName());

      if (mob.addQuestion(Q)) {
        response  = Target.getName() + ", " + SRC.getName() + " has offered ";
        response += "to be your teacher and mentor. Take care in this decision, ";
        response += "for it will be etched into your legend forever as a sign of ";
        response += "loyalty, and may to a great extent dictate your success. ";
        response += "Do you accept this offer?"; }

      else response = Q.getFailedMessage(SRC.getName());
    }

    mob.act("respond " + response);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryMentorInfo(Mobile mob)

  {
    return false;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryShopList(Mobile mob, Entity SRC)

  {
    if (!mob.getFlag(Mobile.FLAG_MERCHANT)) return false;
    if (!mob.heard("list")) return false;
    SRC.echo(mob.getShopList());
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryBuyRequest(Mobile mob, Entity SRC, String s)

  {
    if (!mob.getFlag(Mobile.FLAG_MERCHANT)) return false;
    if (!mob.heard("buy"))  return false;
    if (!SRC.isPlayer()) return false;

    Item I = Merchant.findItemFromSay(mob, s);
    String nm = SRC.getName();

    if (I == null)

    {
      String response  = "I'm sorry, " + nm + ". I don't seem ";
             response += "to have anything like that in stock right now.";

      mob.act("respond " + response);
      return true;
    }

    int result = Merchant.buy(mob, SRC, I);

    if (result == Merchant.MERCHANT_SOLD_OUT)
      mob.act("respond I'm sorry, we are currently sold out.");
    else if (result == Merchant.MERCHANT_NEED_GOLD)
      mob.act("respond You don't have enough money to buy that, " + nm + ".");
    else if (result == Merchant.MERCHANT_NEED_LEVEL)
      mob.act("respond You are not powerful enough to use that, " + nm + ".");
    else if (result == Merchant.MERCHANT_CANT_CARRY)
      mob.act("respond You are already carrying too many items, " + nm + ".");
    else if (result == Merchant.MERCHANT_ITEM_BOUGHT)
      mob.act("respond Thank you for your business, " + nm + ".");

    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean trySellRequest(Mobile mob, Entity SRC, String s)

  {
    if (!mob.getFlag(Mobile.FLAG_MERCHANT)) return false;
    if (!mob.heard("sell")) return false;
    if (!SRC.isPlayer()) return false;

    Item I = Merchant.findItemExact(SRC, s);
    String nm = SRC.getName();

    if (I == null)

    {
      String response  = "I'm sorry, " + nm + ". You don't seem ";
             response += "to have anything like that to sell me.";

      mob.act("respond " + response);
      return true;
    }

    int result = Merchant.sell(mob, SRC, I);

    if (result == Merchant.MERCHANT_SELL_WORTHLESS)
      mob.act("respond I don't want that piece of junk.");
    else if (result == Merchant.MERCHANT_ITEM_SOLD)
      mob.act("respond Thank you for your business, " + nm + ".");
    else if (result == Merchant.MERCHANT_ITEM_NODROP)
      mob.act("respond I'm sorry, I don't buy items that can't be dropped.");

    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryDepositRequest(Mobile mob, Entity SRC, String s)

  {
    if (!mob.getFlag(Mobile.FLAG_BANKER)) return false;
    if (!mob.getRoom().hasBankBranch()) return false;
    if (!mob.heard("deposit")) return false;
    if (!SRC.isPlayer()) return false;

    BankBranch BB = mob.getRoom().getBankBranch();

    String depositString = Merchant.findDepositString(SRC, s);
    String response = BB.deposit(SRC, mob, depositString);

    mob.act("respond " + response);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryWithdrawRequest(Mobile mob, Entity SRC, String s)

  {
    if (!mob.getFlag(Mobile.FLAG_BANKER)) return false;
    if (!mob.getRoom().hasBankBranch()) return false;
    if (!mob.heard("withdraw")) return false;
    if (!SRC.isPlayer()) return false;

    BankBranch BB = mob.getRoom().getBankBranch();
    BankAccount BA = BB.getAccount(SRC.getName());

    String withdrawString = Merchant.findWithdrawString(BA, s);
    String response = BB.withdraw(SRC, mob, withdrawString);

    mob.act("respond " + response);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryAccountRequest(Mobile mob, Entity SRC)

  {
    if (!mob.getFlag(Mobile.FLAG_BANKER)) return false;
    if (!mob.getRoom().hasBankBranch()) return false;
    if (!SRC.isPlayer()) return false;
    if ((!mob.heard("account")) && (!mob.heard("balance"))) return false;

    BankBranch BB = mob.getRoom().getBankBranch();
    String info = BB.getAccountInfo(SRC.getName());

    if (info.length() > 0)

    {
      SRC.echo(info);
      return true;
    }

    mob.act("respond You don't have an account open here.");
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean trySingleSkillInfo(Mobile mob, Entity SRC, boolean spell)

  {
    if ((spell) && (!mob.getFlag(Mobile.FLAG_SPELLMASTER))) return false;
    if ((!spell) && (!mob.getFlag(Mobile.FLAG_SKILLMASTER))) return false;
    if ((spell) && (!mob.heard("spell"))) return false;
    if ((!spell) && (!mob.heard("skill"))) return false;

    int found = -1;

    int size = World.getNumSkills();
    if (spell) size = World.getNumSpells();

    for (int i=0; i<size; i++)

    {
      Ability S;
      if (spell) S = World.getSpell(i);
      else S = World.getSkill(i);

      if (mob.HEARD(S.getName()))

      {
        if ((S.getClasses().indexOf(SRC.getSClass()) != -1)
        || (S.getClasses().equalsIgnoreCase("All")))
          found = i;
      }
    }

    if (found == -1)

    {
      String s1 = "skill";   if (spell) s1 = "spell";
      String s2 = "skills";  if (spell) s2 = "spells";

      String resp  = "I'm sorry " + SRC.getName();
             resp += ", there are no " + s2 + " like that you can learn. ";
             resp += "Please ask me about " + s2 + " in general if you are not ";
             resp += "sure of the " + s1 + " name you are interested in.";

      mob.act("respond " + resp);
      return true;
    }

    Ability S;
    if (spell) S = World.getSpell(found);
    else S = World.getSkill(found);

    String response  = S.getDesc() + " To learn it requires ";
    String noreqs = S.getDesc() + " There are no requirements to learn it.";
    int count = 0;

    if (S.getSTR() > 0) { response += S.getSTR() + " Strength, ";      count++; }
    if (S.getDEX() > 0) { response += S.getDEX() + " Dexterity, ";     count++; }
    if (S.getCON() > 0) { response += S.getCON() + " Constitution, ";  count++; }
    if (S.getINT() > 0) { response += S.getINT() + " Intelligence, ";  count++; }
    if (S.getWIS() > 0) { response += S.getWIS() + " Wisdom, ";        count++; }

    if (count == 0) mob.act("respond " + noreqs);

    else

    {
      response = response.substring(0, response.length()-2);
      response += ".";
      mob.act("respond " + response);
    }

    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryAllSkillInfo(Mobile mob, Entity SRC, boolean spell)

  {
    if ((spell) && (!mob.getFlag(Mobile.FLAG_SPELLMASTER))) return false;
    if ((!spell) && (!mob.getFlag(Mobile.FLAG_SKILLMASTER))) return false;
    if ((spell) && (!mob.heard("spells"))) return false;
    if ((!spell) && (!mob.heard("skills"))) return false;

    String list = SRC.displayAllSkills(spell);
    String classp = classPlural(SRC.getCharClass());

    String s2 = "skills";       if (spell) s2 = "spells";
    String s3 = "Skillmaster";  if (spell) s3 = "Spellmaster";

    if (list.length() <= 0)

    {
      String resp   = "I'm sorry " + SRC.getName() + ", but " + classp;
             resp  += " cannot learn any of the currently known " + s2 + ". ";
             resp  += "However, you may do well to visit a " + s3 + ". ";
             resp  += "I am certain that you may learn a variety of " + s2 + ".";

      mob.act("respond " + resp);
      return true;
    }

    String response  = SRC.getName() + ", ";
           response += "Here are the " + s2 + " that " + classp + " can learn.";

    mob.act("respond " + response);
    SRC.echo(list);
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryHowAreYou(Mobile mob, Entity SRC)

  {
    if (!mob.HEARD("how are you")) return false;

    mob.act("respond I'm fine, thanks. How are you?");
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryWhatsUp(Mobile mob, Entity SRC)

  {
    boolean cond1 = (mob.HEARD("whats up"));
    boolean cond2 = (mob.HEARD("what's up"));

    if ((!cond1) && (!cond2)) return false;

    mob.act("respond Not much, " + SRC.getName() + ". What's up with you?");
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryHello(Mobile mob, Entity SRC)

  {
    boolean c1 = (mob.heard("hi"));
    boolean c2 = (mob.heard("hello"));
    boolean c3 = (mob.heard("hiya"));
    boolean c4 = (mob.heard("hey"));
    boolean c5 = (mob.heard("heya"));
    boolean c6 = (mob.heard("yo"));
    boolean c7 = (mob.heard("greetings"));

    if ((!c1) && (!c2) && (!c3) && (!c4) && (!c5) && (!c6) && (!c7))
      return false;

    mob.act("respond Hello there, " + SRC.getName() + ".");
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryHail(Mobile mob, Entity SRC)

  {
    if (!mob.heard("hail")) return false;

    mob.act("respond Hail to you, " + SRC.getName() + ".");
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean tryThanks(Mobile mob, Entity SRC)

  {
    boolean cond1 = (mob.heard("thanks"));
    boolean cond2 = (mob.HEARD("thank you"));

    if ((!cond1) && (!cond2)) return false;

    mob.act("respond You're welcome, " + SRC.getName() + ".");
    return true;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////
}