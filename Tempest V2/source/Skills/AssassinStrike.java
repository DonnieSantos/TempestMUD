import java.io.*;
import java.util.*;

class AssassinStrike extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int damage;
  private boolean failed;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public AssassinStrike()

  {
    super();

    id = SKILL_ASSASSIN_STRIKE;
    name = "Assassin Strike";
    desc = "Massive damage against a single enemy.";
    prof = "As Sb";
    importance = 230;
    learnRate = 5;
    reqSTR = 140;
    reqDEX = 190;
    reqCON = 90;

    addRequirement("Death Blossom", 60);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateDamage()

  {
    damage = 0;

    double percent = (0.5) + (level * 0.005);

    for (int i=1; i<=6; i++)
      damage += (int)(((float)Combat.testDamage(owner, target)) * 0.55 * percent);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    if (!tryDamageSkill(rem, "strike", "Assassinate Who?"))
      return false;

    Item I = owner.getEquipment(Equipment.RIGHT_HAND);

    if ((I == null) || (!I.getDType().equalsIgnoreCase("Stab"))) {
      owner.echo("You can't use that skill without a stabbing weapon.");
      return false; }

    Combat.engage(owner, target, false);

    if (Combat.testHit(owner, target) != 1)

    {
      failed  = true;
      selfOut = "You completely miss " + tn + " with your strike.#N";
      targOut = Mn + " strikes at you, but misses completely.#N";
      grupOut = Mn + " strikes at " + tn + ", but misses completely.#N";
      obsvOut = Mn + " strikes at " + tn + ", but misses completely.#N";
    }

    else

    {
      failed = false;
      updateDamage();
      target.setCurrentHP(target.getCurrentHP()-damage);

      if (!target.isDead()) {
        selfOut = "#rYou strike " + tn + "!#N";
        targOut = "#r" + Mn + " strikes you!#N";
        grupOut = "#r" + Mn + " strikes " + tn + "!#N";
        obsvOut = "#N" + Mn + " strikes " + tn + "!#N"; }

      else {
        selfOut = "#rYou strike " + tn + ", spraying BLOOD everywhere!#N";
        targOut = "#r" + Mn + " strikes you, spilling your BLOOD!#N";
        grupOut = "#r" + Mn + " strikes " + tn + ", spraying BLOOD everywhere!#N";
        obsvOut = "#N" + Mn + " strikes " + tn + ", spraying BLOOD everywhere!#N"; }

      selfOut += "\r\nYour " + name + " hits " + tn + " for #R" + damage + "#N damage.";
      targOut += "\r\n" + My + " " + name + " hits you for #R" + damage + "#N damage.";
      grupOut += "\r\n" + My + " " + name + " hits " + tn + " for #R" + damage + "#N damage.";
      obsvOut += "\r\n" + My + " " + name + " hits " + tn + " for #N" + damage + "#N damage.";
    }

    skillOutput(false, failed, false);

    owner.lockCommands(6500);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}