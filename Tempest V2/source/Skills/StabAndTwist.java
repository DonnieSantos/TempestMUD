import java.io.*;
import java.util.*;

class StabAndTwist extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int damage;
  private boolean failed;
  private boolean heartAttack;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public StabAndTwist()

  {
    super();

    id = SKILL_STAB_AND_TWIST;
    name = "Stab and Twist";
    desc = "Quickly thrust your dagger into your enemy and turn it viciously, tearing their flesh.";
    prof = "As Rg Th Sb";
    importance = 100;
    learnRate = 5;
    reqSTR = 7;
    reqDEX = 5;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateDamage()

  {
    float percent = (float) ((0.5) + (0.005 * level));
    float testDam = (float) Combat.testDamage(owner, target);
    float userDex = (float) owner.getDEX();

    damage = (int) (percent * ((testDam * 0.4) + (userDex * 0.54)));

    if (heartAttack)

    {
      for (int i=1; i<=6; i++)
        damage += percent * (((float)Combat.testDamage(owner, target) * 0.4) + (userDex * 0.54));
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    if (!tryDamageSkill(rem, "and twist", "Stab Who?"))
      return false;

    Item I = owner.getEquipment(Equipment.RIGHT_HAND);

    if ((I == null) || (!I.getDType().equalsIgnoreCase("Stab"))) {
      owner.echo("You can't use that skill without a stabbing weapon.");
      return false; }

    heartAttack = false;
    Skill HA = owner.findSkill(SKILL_HEART_ATTACK);

    if (HA != null)

    {
      int r = Utility.getRandomInt(0, (2000 - (HA.getLevel() * 15)));
      if (r < 100) heartAttack = true;
    }

    failed = false;
    Combat.engage(owner, target, false);

    if (Combat.testHit(owner, target) != 1)

    {
      failed  = true;
      heartAttack = false;

      selfOut = "You completely miss " + tn + " with your wild stab.#N";
      targOut = Mn + " stabs at you, but misses completely.#N";
      grupOut = Mn + " stabs at " + tn + ", but misses completely.#N";
      obsvOut = Mn + " stabs at " + tn + ", but misses completely.#N";
    }

    else if (heartAttack)

    {
      failed = true;
      updateDamage();
      target.setCurrentHP(target.getCurrentHP()-damage);

      if (!target.isDead()) {
        selfOut = "#rYou stab " + tn + " directly in the heart!#N";
        targOut = "#r" + Mn + " stabs you directly in the heart!#N";
        grupOut = "#r" + Mn + " stabs " + tn + " directly in the heart!#N";
        obsvOut = "#N" + Mn + " stabs " + tn + " directly in the heart!#N"; }

      else {
        selfOut = "#rBLOOD sprays everywhere as you stab " + tn + " deep in the heart!#N";
        targOut = "#rBLOOD sprays everywhere as " + mn + " stabs you deep in the heart!#N";
        grupOut = "#rBLOOD sprays everywhere as " + mn + " stabs " + tn + " deep in the heart!#N";
        obsvOut = "#NBLOOD sprays everywhere as " + mn + " stabs " + tn + " deep in the heart!#N"; }

      selfOut += "\r\nYour " + HA.getName() + " hits " + tn + " for #R" + damage + "#N damage.";
      targOut += "\r\n" + My + HA.getName() + " hits you for #R" + damage + "#N damage.";
      grupOut += "\r\n" + My + HA.getName() + " hits " + tn + " for #R" + damage + "#N damage.";
      obsvOut += "\r\n" + My + HA.getName() + " hits " + tn + " for #N" + damage + "#N damage.";
    }

    else if (!heartAttack)

    {
      failed = false;
      updateDamage();
      target.setCurrentHP(target.getCurrentHP()-damage);

      if (!target.isDead()) {
        selfOut = "#rYou stab " + tn + "!#N";
        targOut = "#r" + Mn + " stabs you!#N";
        grupOut = "#r" + Mn + " stabs " + tn + "!#N";
        obsvOut = "#N" + Mn + " stabs " + tn + "!#N"; }

      else {
        selfOut = "#rYou stab " + tn + ", spilling " + target.his() + " blood!#N";
        targOut = "#r" + Mn + " stabs you, spilling your blood!#N";
        grupOut = "#r" + Mn + " stabs " + tn + ", spilling " + target.his() + " blood!#N";
        obsvOut = "#N" + Mn + " stabs " + tn + ", spilling " + target.his() + " blood!#N"; }

      selfOut += "\r\nYour " + name + " hits " + tn + " for #R" + damage + "#N damage.";
      targOut += "\r\n" + My + " " + name + " hits you for #R" + damage + "#N damage.";
      grupOut += "\r\n" + My + " " + name + " hits " + tn + " for #R" + damage + "#N damage.";
      obsvOut += "\r\n" + My + " " + name + " hits " + tn + " for #N" + damage + "#N damage.";
    }

    skillOutput(false, failed, false);

    if (heartAttack) trainPassive(HA);

    owner.lockCommands(6500);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}