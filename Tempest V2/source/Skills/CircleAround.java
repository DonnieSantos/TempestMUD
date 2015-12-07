import java.io.*;
import java.util.*;

class CircleAround extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private Skill stab;
  private Skill backstab;
  private boolean failed;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public CircleAround()

  {
    super();

    id = SKILL_CIRCLE_AROUND;
    name = "Circle Around";
    desc = "Circle around your enemy and stab them in the back.";
    prof = "As Th Sb";
    importance = 120;
    learnRate = 5;
    reqSTR = 10;
    reqDEX = 80;
    reqCON = 10;
    reqINT = 10;

    addRequirement("Backstab", 30);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateDamage()

  {
    // Damage comes from backstab skill.
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private boolean tryCircle()

  {
    double percent  = ((double)owner.getDEX() / (double)target.getDEX()) * 100.0;
    double decrease = (50.0 + (level/2.0)) / 100.0;

    if (percent > 95.0) percent = 95.0;

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
    if (!tryDamageSkill(rem, "around", "Circle around who?"))
      return false;

    stab = owner.findSkill(SKILL_STAB_AND_TWIST);
    backstab = owner.findSkill(SKILL_BACKSTAB);

    if (stab == null) {
      owner.echo("You don't even know how to stab!");
      return false; }

    if (backstab == null) {
      owner.echo("You don't even know how to backstab!");
      return false; }

    Item I = owner.getEquipment(Equipment.RIGHT_HAND);

    if ((I == null) || (!I.getDType().equalsIgnoreCase("Stab"))) {
      owner.echo("You can't backstab without a stabbing weapon.");
      return false; }

    if (!tryCircle())

    {
      failed = true;
      selfOut = "You try to circle around " + tn + ", but fail.";
      targOut = Mn + " tries to circle around you, but fails.";
      grupOut = Mn + " tries to circle around " + tn + ", but fails.";
      obsvOut = Mn + " tries to circle around " + tn + ", but fails.";
    }

    else

    {
      failed = false;
      selfOut = "You easily circle around " + tn + ".";
      targOut = Mn + " makes a quick move and circles around you.";
      grupOut = Mn + " makes a quick move and circles around " + tn + ".";
      obsvOut = Mn + " makes a quick move and circles around " + tn + ".";
    }

    skillOutput(false, true, false);
    owner.setIntermittentSkill(1);
    if (!failed) backstab.execute(rem);
    owner.lockCommands(6500);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}