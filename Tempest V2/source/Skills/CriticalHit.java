import java.io.*;
import java.util.*;

class CriticalHit extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public CriticalHit()

  {
    super();

    id = SKILL_CRITICAL_HIT;
    name = "Critical Hit";
    desc = "Turn your attacks into deadly critical blows.";
    prof = "As Th Sb";
    importance = 110;
    learnRate = 5;
    reqSTR = 20;
    reqDEX = 50;
    reqCON = 20;

    addRequirement("Stab and Twist", 30);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    // Double damage plus stat bonuses.
    // Happens randomly in place of normal attacks.

    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean tryCrit()

  {
    if (Utility.getRandomInt(0, 10000) < ((owner.getDEX() * 2) + 250)) return true;
    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public int criticalDamage(int damage)

  {
    float percentage = (((int)(level*0.5)) + 50) / 100;
    int nstats = (2*owner.getSTR()) + (owner.getDEX() / 2);
    int rstats = Utility.getRandomInt(((int)(0.9*nstats)), ((int)(1.1*nstats)));
    damage = (2*damage) + ((int)(percentage * rstats));
    return damage;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}