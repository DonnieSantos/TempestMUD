import java.io.*;
import java.util.*;

class MartialArts extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int HRDR;
  private int damage;
  private int connected;
  private boolean critical;
  private CriticalHit CritHit;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public MartialArts()

  {
    super();

    id = SKILL_MARTIAL_ARTS;
    name = "Martial Arts";
    desc = "The ability to fight with you bear hands.";
    prof = "As Mo";
    importance = 180;
    learnRate = 5;
    reqSTR = 10;
    reqDEX = 10;
    reqCON = 20;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    // Call attack() in place of normal attacks
    // when you are not wielding any weapons.

    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateHitAndDam()

  {
    HRDR = (int)(20.0 * (((double)owner.getLevel()+1.0)/100.0));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void attack(Entity Target)

  {
    target = Target;
    tn = target.getName();
    ty = Utility.possessive(tn);

    updateHitAndDam();

    connected = Combat.testHit(owner, target, HRDR);
    if (connected == 1) damage = Combat.testDamage(owner, target, HRDR);
    else damage = connected;

    CritHit = (CriticalHit) owner.findSkill(Skill.SKILL_CRITICAL_HIT);
    if (CritHit != null) critical = CritHit.tryCrit();
    else critical = false;

    if ((critical) && (damage >= 0))
      damage = CritHit.criticalDamage(damage);

    if (damage > 0)
      target.setCurrentHP((int)(target.getCurrentHP()-damage));

    if (damage == -1) {
      selfOut = "You barely miss " + tn + " with your errant attack.";
      targOut = Mn + " barely misses you with " + owner.his() + " errant attack.";
      grupOut = Mn + " barely misses " + tn + " with " + owner.his() + " errant attack.";
      obsvOut = Mn + " barely misses " + tn + " with " + owner.his() + " errant attack."; }

    else if (damage == -2) {
      selfOut = "You miss " + tn + " with your poorly-aimed attack.";
      targOut = Mn + " misses you with " + owner.his() + " poorly-aimed attack.";
      grupOut = Mn + " misses " + tn + " with " + owner.his() + " poorly-aimed attack.";
      obsvOut = Mn + " misses " + tn + " with " + owner.his() + " poorly-aimed attack."; }

    else if (damage == -3) {
      selfOut = "You completely miss " + tn + " with your inept attack.";
      targOut = Mn + " completely misses you with " + owner.his() + " inept attack.";
      grupOut = Mn + " completely misses " + tn + " with " + owner.his() + " inept attack.";
      obsvOut = Mn + " completely misses " + tn + " with " + owner.his() + " inept attack."; }

    else if (critical)

    {
      String CH = "critically hit";
      if (target.getCurrentHP() < 1) CH = "CRITICALLY hit";

      selfOut  = "#R" + Tn + " screams out in pain as you " + CH + " " + target.him() + "!#N\r\n";
      selfOut += "#NYour critical hit hits " + tn + " for #R" + damage + " #Ndamage.";
      targOut  = "#rYou scream out in pain as " + mn + " " + CH + "s you!#N\r\n";
      targOut += "#N" + My + " critical hit hits you for #r" + damage + " #Ndamage.";
      grupOut  = "#R" + Tn + " screams out in pain as " + mn + " " + CH + "s " + target.him() + ".\r\n";
      grupOut += "#N" + My + " critical hit hits " + tn + " for #R" + damage + " #Ndamage.";
      obsvOut  = "#N" + Tn + " screams out in pain as " + mn + " " + CH + "s " + target.him() + ".\r\n";
      obsvOut += "#N" + My + " critical hit hits " + tn + " for #N" + damage + " #Ndamage.";
    }

    else updateOutput();

    if (damage > 0) owner.combatOutput(target, selfOut, targOut, grupOut, obsvOut);
    else owner.passiveOutput(target, selfOut, targOut, grupOut, obsvOut);

    if (critical) CritHit.train();
    else if (damage > 0) this.train();

    if ((target.getCurrentHP() < 1) && (target.getClient() != null))
      target.getClient().freezeFightOutput();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateOutput()

  {
    int r = Utility.getRandomInt(0,9);

    switch(r)

    {
      case 0:  selfOut = "#RYou slam your fist right into " + ty + "#R face";                                  break;
      case 1:  selfOut = "#RYou punch " + tn + "#R hard in the stomach";                                       break;
      case 2:  selfOut = "#RYou karate chop " + tn + "#R on the side of " + target.his() + " neck";            break;
      case 3:  selfOut = "#RYou ram your palm into " + ty + "#R chest";                                        break;
      case 4:  selfOut = "#RYou kick " + tn + "#R right in the kidney";                                        break;
      case 5:  selfOut = "#RYou side kick " + tn + "#R in the guts";                                           break;
      case 6:  selfOut = "#RYou elbow " + tn + "#R in the side of the head";                                   break;
      case 7:  selfOut = "#RYou kick " + tn + "#R in the shoulder";                                            break;
      case 8:  selfOut = "#RYou kick " + tn + "#R hard in the legs";                                           break;
      case 9:  selfOut = "#RYou gouge at " + ty + "#R eyes";                                                   break;
    }

    switch(r)

    {
      case 0:  targOut = "#r" + Mn + "#r slams her fist right into your face";                                 break;
      case 1:  targOut = "#r" + Mn + "#r punches you hard in the stomach";                                     break;
      case 2:  targOut = "#r" + Mn + "#r karate chops you on the side of your neck";                           break;
      case 3:  targOut = "#r" + Mn + "#r rams her palm into your chest";                                       break;
      case 4:  targOut = "#r" + Mn + "#r kicks you right in the kidney";                                       break;
      case 5:  targOut = "#r" + Mn + "#r side kicks you in the guts";                                          break;
      case 6:  targOut = "#r" + Mn + "#r elbows you in the side of the head";                                  break;
      case 7:  targOut = "#r" + Mn + "#r kicks you in the shoulder";                                           break;
      case 8:  targOut = "#r" + Mn + "#r kicks you hard in the legs";                                          break;
      case 9:  targOut = "#r" + Mn + "#r gouges at your eyes";                                                 break;
    }

    switch(r)

    {
      case 0:  grupOut = "#R" + Mn + "#R slams her fist right into " + ty + "#R face";                         break;
      case 1:  grupOut = "#R" + Mn + "#R punches " + tn + "#R hard in the stomach";                            break;
      case 2:  grupOut = "#R" + Mn + "#R karate chops " + tn + "#R on the side of " + target.his() + " neck";  break;
      case 3:  grupOut = "#R" + Mn + "#R rams her palm into " + ty + "#R chest";                               break;
      case 4:  grupOut = "#R" + Mn + "#R kicks " + tn + "#R right in the kidney";                              break;
      case 5:  grupOut = "#R" + Mn + "#R side kicks " + tn + "#R in the guts";                                 break;
      case 6:  grupOut = "#R" + Mn + "#R elbows " + tn + "#R in the side of the head";                         break;
      case 7:  grupOut = "#R" + Mn + "#R kicks " + tn + "#R in the shoulder";                                  break;
      case 8:  grupOut = "#R" + Mn + "#R kicks " + tn + "#R hard in the legs";                                 break;
      case 9:  grupOut = "#R" + Mn + "#R gouges at " + ty + "#R eyes";                                         break;
    }

    switch(r)

    {
      case 0:  obsvOut = "#N" + Mn + "#N slams her fist right into " + ty + "#N face";                         break;
      case 1:  obsvOut = "#N" + Mn + "#N punches " + tn + "#N hard in the stomach";                            break;
      case 2:  obsvOut = "#N" + Mn + "#N karate chops " + tn + "#N on the side of " + target.his() + " neck";  break;
      case 3:  obsvOut = "#N" + Mn + "#N rams her palm into " + ty + "#N chest";                               break;
      case 4:  obsvOut = "#N" + Mn + "#N kicks " + tn + "#N right in the kidney";                              break;
      case 5:  obsvOut = "#N" + Mn + "#N side kicks " + tn + "#N in the guts";                                 break;
      case 6:  obsvOut = "#N" + Mn + "#N elbows " + tn + "#N in the side of the head";                         break;
      case 7:  obsvOut = "#N" + Mn + "#N kicks " + tn + "#N in the shoulder";                                  break;
      case 8:  obsvOut = "#N" + Mn + "#N kicks " + tn + "#N hard in the legs";                                 break;
      case 9:  obsvOut = "#N" + Mn + "#N gouges at " + ty + "#N eyes";                                         break;
    }

    selfOut += " for " + damage + " damage.#N";
    targOut += " for " + damage + " damage.#N";
    grupOut += " for " + damage + " damage.#N";
    obsvOut += " for " + damage + " damage.#N";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}