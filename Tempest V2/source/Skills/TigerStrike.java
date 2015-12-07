import java.io.*;
import java.util.*;

class TigerStrike extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int HRDR;
  private int damage;
  private boolean failed;
  private int strikeNum;
  private int hitCount;
  private int delayRemainder;
  private boolean giveup;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public TigerStrike()

  {
    super();

    id = SKILL_TIGER_STRIKE;
    name = "Tiger Strike";
    desc = "Ravage your enemy with a brutal attack.";
    prof = "As Mo";
    importance = 220;
    learnRate = 5;
    reqSTR = 50;
    reqDEX = 100;
    reqCON = 50;
    reqINT = 30;
    reqWIS = 20;

    addRequirement("Lightning Strike", 50);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateDamage()

  {
    HRDR = (int)(20.0 * (((double)owner.getLevel()+1.0)/100.0));
    damage = Combat.testDamage(owner, target, HRDR);
    float pctAdd = ((float)level*40) / 10000;
    int add = (int)((float)damage * pctAdd);
    int sub = (int)((float)damage * 0.40);
    damage = damage + add - sub;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    if (owner.getIntermittentSkill() > 0)
      return strike();

    return initiate(rem);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean initiate(String rem)

  {
    if (!tryDamageSkill(rem, "strike", "Strike Who?"))
      return false;

    giveup = false;
    hitCount = 0;
    strikeNum = 0;
    delayRemainder = 6500;

    int maxStrikes = 1;
    int r = Utility.getRandomInt(0, 25000+(level*750));
    if (r > 5000) maxStrikes = 2;
    if (r > 25000) maxStrikes = 3;

    owner.setIntermittentSkill(maxStrikes);

    for (int i=0; i<maxStrikes; i++)
      owner.getClient().forceCommand("tiger strike");

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean strike()

  {
    owner.setIntermittentSkill(owner.getIntermittentSkill()-1);

    if (target == null) giveup = true;
    if (target.isDead()) giveup = true;
    if (target.getRoom() != owner.getRoom()) giveup = true;
    if (!Combat.canAttackSilent(owner, target, false, true)) giveup = true;

    if (giveup) return false;

    updateDamage();
    boolean connected = (Combat.testHit(owner, target, HRDR) == 1);
    Combat.engage(owner, target, false);
    strikeNum++;

    if (!connected)

    {
      giveup = true;

      selfOut = "You completely miss " + tn + " with your strike.#N";
      targOut = Mn + " strikes at you, but misses completely.#N";
      grupOut = Mn + " strikes at " + tn + ", but misses completely.#N";
      obsvOut = Mn + " strikes at " + tn + ", but misses completely.#N";
    }

    else

    {
      hitCount++;
      target.setCurrentHP(target.getCurrentHP()-damage);

      if (target.isDead()) {
        selfOut = "#y####r You completely rip " + tn + " to shreds!!! #y####N";
        targOut = "#y####r " + Mn + " completely rips you to shreds!!! #y####N";
        grupOut = "#y####r " + Mn + " completely rips " + tn + " to shreds!!! #y####N";
        obsvOut = "#N### " + Mn + " completely rips " + tn + " to shreds!!! ####N"; }

      else if (strikeNum == 1) {
        selfOut = "#y///#r You claw " + tn + " right across the face! #y///#N";
        targOut = "#y///#r " + Mn + " claws you right across the face! #y///#N";
        grupOut = "#y///#r " + Mn + " claws " + tn + " right across the face! #y///#N";
        obsvOut = "#N/// " + Mn + " claws " + tn + " right across the face! ///#N"; }

      else if (strikeNum == 2) {
        selfOut = "#y\\\\\\#r You savagely claw " + tn + " in the torso! #y\\\\\\#N";
        targOut = "#y\\\\\\#r " + Mn + " savagely claws you in the torso! #y\\\\\\#N";
        grupOut = "#y\\\\\\#r " + Mn + " savagely claws " + tn + " in the torso! #y\\\\\\#N";
        obsvOut = "#N\\\\\\ " + Mn + " savagely claws " + tn + " in the torso! \\\\\\#N"; }

      else {
        selfOut = "#y<<<#r You brutally maul " + tn + " with a snarl! #y<<<#N";
        targOut = "#y<<<#r " + Mn + " brutally mauls you with a snarl! #y<<<#N";
        grupOut = "#y<<<#r " + Mn + " brutally mauls " + tn + " with a snarl! #y<<<#N";
        obsvOut = "#N<<< " + Mn + " brutally mauls " + tn + " with a snarl! <<<#N"; }

      selfOut += "\r\nYour " + name + " hits " + tn + " for #R" + damage + "#N damage.";
      targOut += "\r\n" + My + " " + name + " hits you for #R" + damage + "#N damage.";
      grupOut += "\r\n" + My + " " + name + " hits " + tn + " for #R" + damage + "#N damage.";
      obsvOut += "\r\n" + My + " " + name + " hits " + tn + " for #N" + damage + "#N damage.";
    }

    if ((owner.getIntermittentSkill() == 0) && (connected)) failed = false;
    else failed = true;

    skillOutput(false, failed, false);

    if ((owner.getIntermittentSkill() > 0) && (!giveup)) {
       delayRemainder = delayRemainder - 300;
       owner.lockCommands(300); }

    else owner.lockCommands(delayRemainder);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}