import java.io.*;
import java.util.*;

class Combat extends Utility

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static int damage;
  private static int connected;
  private static String s1;
  private static String s2;
  private static String s3;
  private static String s4;
  private static String mn;
  private static String tn;
  private static String ps;
  private static String Tn;
  private static String type;
  private static String meter;
  private static boolean critical;
  private static boolean passed;

  private static int STANDARD_AC;
  private static int STANDARD_DEX;
  private static int STANDARD_HIT;
  private static int STANDARD_ABSORB;
  private static int HIT;
  private static int AIM;
  private static int DODGE;

  private static double DAMAGE;
  private static double ABSORB;
  private static double totalDamage;
  private static double percentage;
  private static int randomPercent;
  private static int hitRate;
  private static int dodgeRate;
  private static int exitDir;

  private static MartialArts MA;
  private static CriticalHit CritHit;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static int testDamage(Entity SRC, Entity target)

  {
    STANDARD_AC = ((SRC.getLevel() * 10) + 10);
    STANDARD_DEX = (int)((double)SRC.getLevel() * 1.5);
    STANDARD_ABSORB = (int)((double)((STANDARD_AC + (STANDARD_DEX)) * 0.3));

    DAMAGE = ((double)((19*SRC.getDR()) + (2.375*(double)SRC.getSTR())) * 0.312);
    ABSORB = ((double)(target.getAC() + target.getDEX()) * 0.3);

    totalDamage = DAMAGE + STANDARD_ABSORB - ABSORB;

    if (totalDamage < (DAMAGE * .05)) totalDamage = (DAMAGE * .05);

    randomPercent = getRandomInt(80, 120);
    percentage = ((double)randomPercent / 100.0);
    totalDamage = totalDamage * percentage;

    return (int)totalDamage;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static int testDamage(Entity SRC, Entity target, int damRoll)

  {
    STANDARD_AC = ((SRC.getLevel() * 10) + 10);
    STANDARD_DEX = (int)((double)SRC.getLevel() * 1.5);
    STANDARD_ABSORB = (int)((double)((STANDARD_AC + (STANDARD_DEX)) * 0.3));

    DAMAGE = ((double)((19*damRoll) + (2.375*(double)SRC.getSTR())) * 0.312);
    ABSORB = ((double)(target.getAC() + target.getDEX()) * 0.3);

    totalDamage = DAMAGE + STANDARD_ABSORB - ABSORB;

    if (totalDamage < (DAMAGE * .05)) totalDamage = (DAMAGE * .05);

    randomPercent = getRandomInt(80, 120);
    percentage = ((double)randomPercent / 100.0);
    totalDamage = totalDamage * percentage;

    return (int)totalDamage;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static int testHit(Entity SRC, Entity target)

  {
    STANDARD_HIT = (int)(((double)SRC.getLevel() / 5.0) + 1.0);
    STANDARD_DEX = (int)(((double)SRC.getLevel() * 1.5) + 2.0);

    HIT = (int)(((double)SRC.getHR() / (double)STANDARD_HIT) * 100.0);
    AIM = (int)(((((double)SRC.getDEX() / (double)STANDARD_DEX) * 100.0) - 100.0) / 2.0);
    DODGE = (int)(((((double)target.getDEX() / (double)STANDARD_DEX) * 100.0) - 100.0) / 2.0);

    hitRate = HIT + AIM - DODGE;
    if (hitRate < 5) hitRate = 5;
    if (hitRate > 95) hitRate = 95;

    dodgeRate = 100 - hitRate;

    if ((dodgeRate*1000) < getRandomInt(0,100000)) return 1;
    if ((dodgeRate >= 0) && (dodgeRate <= 10)) return -1;
    if ((dodgeRate >= 11) && (dodgeRate <= 25)) return -2;
    return -3;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static int testHit(Entity SRC, Entity target, int hitRoll)

  {
    STANDARD_HIT = (int)(((double)SRC.getLevel() / 5.0) + 1.0);
    STANDARD_DEX = (int)(((double)SRC.getLevel() * 1.5) + 2.0);

    HIT = (int)(((double)hitRoll / (double)STANDARD_HIT) * 100.0);
    AIM = (int)(((((double)SRC.getDEX() / (double)STANDARD_DEX) * 100.0) - 100.0) / 2.0);
    DODGE = (int)(((((double)target.getDEX() / (double)STANDARD_DEX) * 100.0) - 100.0) / 2.0);

    hitRate = HIT + AIM - DODGE;
    if (hitRate < 5) hitRate = 5;
    if (hitRate > 95) hitRate = 95;

    dodgeRate = 100 - hitRate;

    if ((dodgeRate*1000) < getRandomInt(0,100000)) return 1;
    if ((dodgeRate >= 0) && (dodgeRate <= 10)) return -1;
    if ((dodgeRate >= 11) && (dodgeRate <= 25)) return -2;
    return -3;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean testMagicHit(Entity target)

  {
    if ((getRandomInt(0, 10000) / 100) > target.getTrueResistance()) return true;
    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean PvP(Entity E1, Entity E2)

  {
    if ((E1.isPlayer()) && (E2.isPlayer())) return true;
    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean canAttack(Entity SRC, Entity target, boolean magic, boolean ability)

  {
    passed = false;

    if (target == SRC)
      SRC.echo("Attacking yourself is really not such a good idea.");

    else if (target == null)
      SRC.echo("There's nobody around by that name.");

    else if (target.getRoom().getFlag(Room.FLAG_LAWFULL))
      SRC.echo("You may not attack others in this place.");

    else if ((magic) && (SRC.getRoom().getFlag(Room.FLAG_NO_MAGIC)))
      SRC.echo("Your magic doesn't seem to work here.");

    else if (PvP(SRC,target) && (!target.getRoom().getFlag(Room.FLAG_PKOK)))
      SRC.echo("You may not attack " + target.him() + " here.");

    else if ((target != SRC) && (SRC.getGroup().inGroup(target)))
      SRC.echo("You may not attack group members.");

    else if ((target.getClient() != null)
    && ((target.getClient().getClientState() == Utility.CSTATE_LINKDEAD)
    ||  (target.getClient().getClientState() == Utility.CSTATE_WRITING)
    ||  (target.getClient().getClientState() == Utility.CSTATE_BUILDING)))
      SRC.echo("You can't attack " + target.him() + " right now.");

    else if ((!ability) && (SRC.getPlayerState() == PSTATE_FIGHTING))
      SRC.echo("You are already fighting.");

    else passed = true;

    return passed;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean canAttackSilent(Entity SRC, Entity target, boolean magic, boolean ability)

  {
    if (target == SRC) return false;
    else if (target == null) return false;
    else if (target.getRoom().getFlag(Room.FLAG_LAWFULL)) return false;
    else if ((magic) && (SRC.getRoom().getFlag(Room.FLAG_NO_MAGIC))) return false;
    else if (PvP(SRC,target) && (!target.getRoom().getFlag(Room.FLAG_PKOK))) return false;
    else if ((target != SRC) && (SRC.getGroup().inGroup(target))) return false;

    else if ((target.getClient() != null)
    && ((target.getClient().getClientState() == Utility.CSTATE_LINKDEAD)
    ||  (target.getClient().getClientState() == Utility.CSTATE_WRITING)
    ||  (target.getClient().getClientState() == Utility.CSTATE_BUILDING)))
      return false;

    else if ((!ability) && (SRC.getPlayerState() == PSTATE_FIGHTING))
      return false;

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void attack(Entity SRC, Entity target)

  {
    if ((SRC.getCurrentHP() <= 0) || (target.getCurrentHP() <= 0)) return;

    if (SRC.getEquipment(Equipment.RIGHT_HAND) == null)
      MA = (MartialArts) SRC.findSkill(Skill.SKILL_MARTIAL_ARTS);
    else MA = null;

    if (MA != null) {
      MA.attack(target);
      return; }

    connected = testHit(SRC, target);
    if (connected == 1) damage = testDamage(SRC, target);
    else damage = connected;

    if (SRC.getEquipment(Equipment.RIGHT_HAND) != null)
      type = SRC.getEquipment(Equipment.RIGHT_HAND).getDType().toLowerCase();
    else type = "punch";

    CritHit = (CriticalHit) SRC.findSkill(Skill.SKILL_CRITICAL_HIT);
    if (CritHit != null) critical = CritHit.tryCrit();
    else critical = false;

    if ((critical) && (damage >= 0))
      damage = CritHit.criticalDamage(damage);

    if (damage > 0)
      target.setCurrentHP((int)(target.getCurrentHP()-damage));

    if (damage == -1)

    {
      tn = target.getName();
      mn = properName(SRC.getName());
      s1 = "You barely miss " + tn + " with your errant attack.";
      s2 = mn + " barely misses you with " + SRC.his() + " errant attack.";
      s3 = mn + " barely misses " + tn + " with " + SRC.his() + " errant attack.";
      s4 = mn + " barely misses " + tn + " with " + SRC.his() + " errant attack.";
    }

    else if (damage == -2)

    {
      tn = target.getName();
      mn = properName(SRC.getName());
      s1 = "You miss " + tn + " with your poorly-aimed attack.";
      s2 = mn + " misses you with " + SRC.his() + " poorly-aimed attack.";
      s3 = mn + " misses " + tn + " with " + SRC.his() + " poorly-aimed attack.";
      s4 = mn + " misses " + tn + " with " + SRC.his() + " poorly-aimed attack.";
    }

    else if (damage == -3)

    {
      tn = target.getName();
      mn = properName(SRC.getName());
      s1 = "You completely miss " + tn + " with your inept attack.";
      s2 = mn + " completely misses you with " + SRC.his() + " inept attack.";
      s3 = mn + " completely misses " + tn + " with " + SRC.his() + " inept attack.";
      s4 = mn + " completely misses " + tn + " with " + SRC.his() + " inept attack.";
    }

    else if (critical)

    {
      String CH = "critically hit";
      if (target.getCurrentHP() < 1) CH = "CRITICALLY hit";

      mn = SRC.getName();
      tn = target.getName();
      Tn = target.getPName();
      ps = possessive(SRC.getPName());
      s1  = "#R" + Tn + " screams out in pain as you " + CH + " " + target.him() + "!#N\r\n";
      s1 += "#NYour critical hit hits " + tn + " for #R" + damage + " #Ndamage.";
      s2  = "#rYou scream out in pain as " + mn + " " + CH + "s you!#N\r\n";
      s2 += "#N" + ps + " critical hit hits you for #r" + damage + " #Ndamage.";
      s3  = "#R" + Tn + " screams out in pain as " + mn + " " + CH + "s " + target.him() + ".\r\n";
      s3 += "#N" + ps + " critical hit hits " + tn + " for #R" + damage + " #Ndamage.";
      s4  = "#N" + Tn + " screams out in pain as " + mn + " " + CH + "s " + target.him() + ".\r\n";
      s4 += "#N" + ps + " critical hit hits " + tn + " for #N" + damage + " #Ndamage.";
    }

    else

    {
      String adj = adjective(damage, target.getMaxHP());

      tn = target.getName();
      ps = possessive(SRC.getPName());
      s1 = "#NYour " + type + " #R" + adj + " #N" + tn + " #Nfor #R" + damage + " #Ndamage.";
      s2 = ps + " " + type + " #r" + adj + " #Nyou for #r" + damage + " #Ndamage.";
      s3 = ps + " " + type + " #R" + adj + " #N" + tn + " #Nfor #R" + damage + " #Ndamage.";
      s4 = ps + " " + type + " #N" + adj + " #N" + tn + " #Nfor #N" + damage + " #Ndamage.";
    }

    if (damage > 0) SRC.combatOutput(target, s1, s2, s3, s4);
    else SRC.passiveOutput(target, s1, s2, s3, s4);

    if (critical) CritHit.train();

    if ((target.getCurrentHP() < 1) && (target.getClient() != null))
      target.getClient().freezeFightOutput();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void quickAttack(Entity SRC, Entity target)

  {
    attack(SRC, target);
    SRC.getRoom().addMeters();
    SRC.getRoom().destroyDead();
    SRC.getRoom().updateTargets();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void engage(Entity SRC, String str)

  {
    if (str.length() <= 0) {
      SRC.echo("Who you do you want to kill?");
      return; }

    Entity target = SRC.getRoom().findEntity(SRC, str);

    if (!canAttack(SRC, target, false, false)) return;

    engage(SRC, target, true);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void engage(Entity SRC, Entity target, boolean quickAttack)

  {
    SRC.getRoom().addFighter(SRC);
    SRC.getRoom().addFighter(target);
    World.addFightList(SRC.getRoom());

    SRC.setTarget(target);
    SRC.setPlayerState(PSTATE_FIGHTING);

    if (target.getPlayerState() != PSTATE_FIGHTING)

    {
      target.setTarget(SRC);
      target.setPlayerState(PSTATE_FIGHTING);
    }

    else target.setTarget(SRC);

    if (quickAttack) quickAttack(SRC, target);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void manualAssist(Entity SRC, String Targ)

  {
    Entity target = SRC.getRoom().findEntity(SRC, Targ);

    if ((SRC.getPlayerState() == PSTATE_FIGHTING) || (SRC.getTarget() != null)) {
      SRC.echo("You are already fighting!");
      return; }

    if (target == null) {
      SRC.echo("There is nobody around by that name.");
      return; }

    if ((target.getPlayerState() != PSTATE_FIGHTING) || (target.getTarget() == null)) {
      SRC.echo(target.getName() + " is not fighting anyone.");
      return; }

    SRC.setPlayerState(PSTATE_FIGHTING);
    SRC.setTarget(target.getTarget());
    quickAttack(SRC, SRC.getTarget());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void flee(Entity SRC)

  {
    if (SRC.getPlayerState() != PSTATE_FIGHTING) {
      SRC.echo("You're not even in a fight!");
      return; }

    boolean found = false;
    int numExits = 0;

    if (SRC.getRoom().getN() != 0) numExits++;
    if (SRC.getRoom().getS() != 0) numExits++;
    if (SRC.getRoom().getW() != 0) numExits++;
    if (SRC.getRoom().getE() != 0) numExits++;
    if (SRC.getRoom().getU() != 0) numExits++;
    if (SRC.getRoom().getD() != 0) numExits++;

    if (numExits <= 0) {
      SRC.echo("There's nowhere to run to!");
      return; }

    while (!found)

    {
      exitDir = getRandomInt(0, 1000) % 6;

      switch(exitDir)

      {
        case DIRECTION_N:  if (SRC.getRoom().getN() != 0)  found = true;  break;
        case DIRECTION_S:  if (SRC.getRoom().getS() != 0)  found = true;  break;
        case DIRECTION_E:  if (SRC.getRoom().getE() != 0)  found = true;  break;
        case DIRECTION_W:  if (SRC.getRoom().getW() != 0)  found = true;  break;
        case DIRECTION_U:  if (SRC.getRoom().getU() != 0)  found = true;  break;
        case DIRECTION_D:  if (SRC.getRoom().getD() != 0)  found = true;  break;
      }
    }

    String moveAttempt = "";
    String moveDir = "";

    switch (exitDir)

    {
      case DIRECTION_N:  moveAttempt = SRC.tryMove(DIRECTION_N);  moveDir = "north";  break;
      case DIRECTION_S:  moveAttempt = SRC.tryMove(DIRECTION_S);  moveDir = "south";  break;
      case DIRECTION_E:  moveAttempt = SRC.tryMove(DIRECTION_E);  moveDir = "east";   break;
      case DIRECTION_W:  moveAttempt = SRC.tryMove(DIRECTION_W);  moveDir = "west";   break;
      case DIRECTION_U:  moveAttempt = SRC.tryMove(DIRECTION_U);  moveDir = "up";     break;
      case DIRECTION_D:  moveAttempt = SRC.tryMove(DIRECTION_D);  moveDir = "down";   break;
    }

    if ((moveAttempt.equals("OK"))
    || (moveAttempt.equals("You're in the middle of a fight!")))

    {
      SRC.stopFighting();
      SRC.echo("You flee the battle!");
      SRC.sendToAwake(SRC.getName() + " flees from the battle!");

      switch (exitDir)

      {
        case DIRECTION_N:  SRC.move(DIRECTION_N);  break;
        case DIRECTION_S:  SRC.move(DIRECTION_S);  break;
        case DIRECTION_E:  SRC.move(DIRECTION_E);  break;
        case DIRECTION_W:  SRC.move(DIRECTION_W);  break;
        case DIRECTION_U:  SRC.move(DIRECTION_U);  break;
        case DIRECTION_D:  SRC.move(DIRECTION_D);  break;
      }

      return;
    }

    SRC.echo(moveAttempt);

    // We need to implement this so sometimes you fail fleeing for no reason.
    // SRC.echo("You try to flee " + moveDir + ", but you can't manage to break through!");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String adjective(int Damage, int MaxHP)

  {
    percentage = (((double)Damage / (double)MaxHP) * 100.0);

    if (percentage < 1)                                return "barely scratches";
    else if ((percentage >= 1) && (percentage < 2))    return "scratches";
    else if ((percentage >= 2) && (percentage < 3))    return "grazes";
    else if ((percentage >= 3) && (percentage < 4))    return "injures";
    else if ((percentage >= 4) && (percentage < 5))    return "severely injures";
    else if ((percentage >= 5) && (percentage < 6))    return "wounds";
    else if ((percentage >= 6) && (percentage < 7))    return "deeply wounds";
    else if ((percentage >= 7) && (percentage < 8))    return "strikes";
    else if ((percentage >= 8) && (percentage < 10))   return "viciously strikes";
    else if ((percentage >= 10) && (percentage < 12))  return "disfigures";
    else if ((percentage >= 12) && (percentage < 14))  return "mercilessly disfigures";
    else if ((percentage >= 14) && (percentage < 17))  return "brutalizes";
    else if ((percentage >= 17) && (percentage < 20))  return "savagely brutalizes";
    else if ((percentage >= 20) && (percentage < 23))  return "ruthlessly maims";
    else if ((percentage >= 23) && (percentage < 26))  return "SLAUGHTERS";
    else if ((percentage >= 26) && (percentage < 29))  return "+++ OBLITERATES +++";
    else if ((percentage >= 29) && (percentage < 33))  return "*** MASSACRES ***";
    else                                               return ">>> ANNIHILATES <<";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String hitpointMeter(Entity SRC)

  {
    if (SRC.getTarget() == null) return "";

    meter = possessive(SRC.getTarget().getPName()) + " health: #n[";
    float hpmeter = ((float)SRC.getTarget().getCurrentHP() / (float)SRC.getTarget().getMaxHP());
    float inthpmeter = ((hpmeter * 100) / 10);

    for (int i=1; i<=10; i++)

    {
      if (i <= inthpmeter) {
        if (inthpmeter > 5) meter += "#g=#N";
        else if (inthpmeter > 2) meter += "#y=#N";
        else if (inthpmeter > 0) meter += "#r=#N"; }

      else meter += "#N-#N";
    }

    meter += "#n]#N";

    return meter;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}