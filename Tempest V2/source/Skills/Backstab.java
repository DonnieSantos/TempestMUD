import java.io.*;
import java.util.*;

class Backstab extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int damage;
  private boolean failed;
  private boolean fromCircle;
  private Skill stab;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Backstab()

  {
    super();

    id = SKILL_BACKSTAB;
    name = "Backstab";
    desc = "Bury a dagger deep into the back of your enemy.";
    prof = "As Rg Th Sb";
    importance = 90;
    learnRate = 5;
    reqSTR = 15;
    reqDEX = 35;
    reqCON = 10;
    reqINT = 5;

    addRequirement("Stab and Twist", 20);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateDamage()

  {
    float percent  = (float) ((0.5) + (0.005 * stab.getLevel()));
    float testDam  = (float) Combat.testDamage(owner, target);
    float userDex  = (float) owner.getDEX();
    float modifier = ((float)(100.0 + (float)level) / (float)100.0);

    damage = (int) (percent * ((testDam * 0.4) + (userDex * 0.54)));
    damage = (int) ((float)damage * (float)modifier);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    if (owner.getIntermittentSkill() > 0) {
      owner.setIntermittentSkill(0);
      fromCircle = true; }

    else fromCircle = false;

    stab = owner.findSkill(SKILL_STAB_AND_TWIST);

    if (stab == null) {
      owner.echo("You can't backstab if you don't even know how to stab!");
      return false; }

    if (!tryDamageSkill(rem, "around", "Backstab Who?"))
      return false;

    Item I = owner.getEquipment(Equipment.RIGHT_HAND);

    if ((I == null) || (!I.getDType().equalsIgnoreCase("Stab"))) {
      owner.echo("You can't backstab without a stabbing weapon.");
      return false; }

    String dag = I.getLName();

    if ((!fromCircle) && (owner.getPlayerState() == Utility.PSTATE_FIGHTING)) {
      owner.echo("You're in the middle of a fight!");
      return false; }

    Combat.engage(owner, target, false);

    if (Combat.testHit(owner, target) != 1)

    {
      failed  = true;
      selfOut = "You completely miss " + tn + " with your misplaced stab.#N";
      targOut = Mn + " tries to stab you in the back, but misses completely.#N";
      grupOut = Mn + " tries to stab " + tn + " in the back, but misses completely.#N";
      obsvOut = Mn + " tries to stab " + tn + " in the back, but misses completely.#N";
    }

    else

    {
      failed = false;
      updateDamage();
      target.setCurrentHP(target.getCurrentHP()-damage);

      if (!target.isDead()) {
        selfOut = "#rYou drive " + dag + "#r deep into " + ty + " back!#N";
        targOut = "#r" + Mn + " drives " + dag + "#r deep into your back!#N";
        grupOut = "#r" + Mn + " drives " + dag + "#r deep into " + ty + " back!#N";
        obsvOut = "#N" + Mn + " drives " + dag + "#N deep into " + ty + " back!#N"; }

      else {
        selfOut = "#rYou plunge " + dag + "#r into " + ty + " back, spilling " + target.his() + " blood and life.#N";
        targOut = "#r" + Mn + " plunges " + dag + "#r into your back, spilling your blood and life.#N";
        grupOut = "#r" + Mn + " plunges " + dag + "#r into " + ty + " back, spilling " + target.his() + " blood and life.#N";
        obsvOut = "#N" + Mn + " plunges " + dag + "#N into " + ty + " back, spilling " + target.his() + " blood and life.#N"; }

      selfOut += "\r\nYour " + name + " hits " + tn + " for #R" + damage + "#N damage.";
      targOut += "\r\n" + My + " " + name + " hits you for #R" + damage + "#N damage.";
      grupOut += "\r\n" + My + " " + name + " hits " + tn + " for #R" + damage + "#N damage.";
      obsvOut += "\r\n" + My + " " + name + " hits " + tn + " for #N" + damage + "#N damage.";
    }

    if ((fromCircle) && (failed == false))

    {
      skillOutput(false, true, false);
      trainPassive(owner.findSkill(SKILL_CIRCLE_AROUND));
    }

    else skillOutput(false, failed, false);

    if (!fromCircle) owner.lockCommands(6500);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}