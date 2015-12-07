import java.io.*;
import java.util.*;

class Disarm extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Disarm()

  {
    super();

    id = SKILL_DISARM;
    name = "Disarm";
    desc = "Send your enemy's weapon flying.";
    prof = "As Ra Rg Th Sb Wa Kn Cr Dk";
    importance = 60;
    learnRate = 5;
    reqSTR = 40;
    reqDEX = 50;
    reqCON = 20;
    reqINT = 10;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateDamage() { }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private boolean tryDisarm()

  {
    double percent  = (((double)owner.getDEX()/2.0) / (double)target.getDEX()) * 100.0;
    double decrease = (50.0 + (level/2.0)) / 100.0;

    if (percent > 50.0) percent = 50.0;

    percent = percent / 100.0;
    percent = percent * decrease;

    int r = Utility.getRandomInt(0, 100000);
    if (((double)r / 100000.0) > percent) return false;

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    if (!tryDamageSkill(rem, "disarm", "Disarm Who?"))
      return false;

    if ((owner.getPlayerState() != Utility.PSTATE_FIGHTING) || (owner.getTarget() != target)) {
      owner.echo("You're not even fighting " + target.him() + "!");
      return false; }

    Item weapon = target.getEquipment(Equipment.RIGHT_HAND);

    if (weapon == null) {
      owner.echo(target.He() + "'s not even wielding anything!");
      return false; }

    boolean failed = false;

    if (!tryDisarm())

    {
      failed = true;
      selfOut = "You try to disarm " + tn + ", but fail.";
      targOut = Mn + " tries to disarm you, but fails.";
      grupOut = Mn + " tries to disarm " + tn + ", but fails.";
      obsvOut = Mn + " tries to disarm " + tn + ", but fails.";
    }

    else

    {
      Transfer.item(weapon, target, target.getRoom(), Transfer.IDISARM);

      failed = false;
      selfOut = "You send " + ty + " weapon flying!";
      targOut = Mn + " sends your weapon flying!";
      grupOut = Mn + " sends " + ty + " weapon flying!";
      obsvOut = Mn + " sends " + ty + " weapon flying!";
    }

    skillOutput(true, failed, false);

    owner.lockCommands(6500);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}