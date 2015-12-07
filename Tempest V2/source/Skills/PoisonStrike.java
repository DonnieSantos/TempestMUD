import java.io.*;
import java.util.*;

class PoisonStrike extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;
  private static final int STRIKE_MISSED     = 1;
  private static final int STRIKE_RESIST     = 2;
  private static final int STRIKE_LANDED     = 3;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int strength;
  private int duration;
  private boolean failed;
  private int result;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public PoisonStrike()

  {
    super();

    id = SKILL_POISON_STRIKE;
    name = "Poison Strike";
    desc = "A mystic strike that unleashes venom upon your foe.";
    prof = "As Mo";
    importance = 190;
    learnRate = 5;
    reqSTR = 20;
    reqDEX = 70;
    reqCON = 20;
    reqINT = 20;
    reqWIS = 20;

    addRequirement("Martial Arts", 30);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateDamage()

  {
    duration = 5;
    strength = (int)((((double)level / 100.0) * 15.0) + 5.0);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int tryStrike()

  {
    if (Combat.testHit(owner, target) != 1) return STRIKE_MISSED;
    if (!Combat.testMagicHit(target)) return STRIKE_RESIST;
    return STRIKE_LANDED;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    if (!tryDamageSkill(rem, "strike", "Poison Who?"))
      return false;

    Combat.engage(owner, target, false);

    result = tryStrike();

    if (result == STRIKE_MISSED)

    {
      failed  = true;
      selfOut = "You completely miss " + tn + " with your strike.#N";
      targOut = Mn + " strikes at you, but misses completely.#N";
      grupOut = Mn + " strikes at " + tn + ", but misses completely.#N";
      obsvOut = Mn + " strikes at " + tn + ", but misses completely.#N";
    }

    else if (result == STRIKE_RESIST)

    {
      failed  = true;
      selfOut = "#GYou strike " + tn + ".#N";
      targOut = "#G" + Mn + " strikes you.#N";
      grupOut = "#G" + Mn + " strikes " + tn + ".#N";
      obsvOut = "#N" + Mn + " strikes " + tn + ".#N";

      selfOut += "\r\n" + Tn + " is not affected by the poison.#N";
      targOut += "\r\n" + "You are not affected by the poison.#N";
      grupOut += "\r\n" + Tn + " is not affected by the poison.#N";
      obsvOut += "\r\n" + Tn + " is not affected by the poison.#N";
    }

    else

    {
      failed = false;
      selfOut = "#gYou strike " + tn + "!#N";
      targOut = "#g" + Mn + " strikes you!#N";
      grupOut = "#g" + Mn + " strikes " + tn + "!#N";
      obsvOut = "#N" + Mn + " strikes " + tn + "!#N";

      selfOut += "\r\n" + Tn + " has been poisoned!#N";
      targOut += "\r\n" + "You have been poisoned!#N";
      grupOut += "\r\n" + Tn + " has been poisoned!#N";
      obsvOut += "\r\n" + Tn + " has been poisoned!#N";

      updateDamage();
      Effect poison = new EffectPoison();
      poison.init(target, strength, duration);
    }

    skillOutput(true, failed, false);

    owner.lockCommands(6500);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}