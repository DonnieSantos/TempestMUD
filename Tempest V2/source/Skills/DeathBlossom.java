import java.io.*;
import java.util.*;

class DeathBlossom extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int damage;
  private boolean failed;
  private ArrayList BlossomList;
  private int commandsWaiting;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public DeathBlossom()

  {
    super();

    id = SKILL_DEATH_BLOSSOM;
    name = "Death Blossom";
    desc = "Death Blossom massive damage close-range attack against multiple targets.";
    prof = "As Sb";
    importance = 245;
    learnRate = 5;
    reqSTR = 35;
    reqDEX = 150;
    reqCON = 35;

    addRequirement("Critical Hit", 60);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateDamage()

  {
    damage = Combat.testDamage(owner, target);
    float multiplier = ((((float)level) / (float)10) + 1);
    float totalDamage = (multiplier * (float)damage);
    damage = (int)totalDamage;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    if (owner.getIntermittentSkill() > 0)
      return blossomSingle();

    return blossomRoom();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean blossomRoom()

  {
    if (owner.getClient() == null) return false;

    if (owner.checkSchedule(Schedule.DEATH_BLOSSOM) == false) {
      owner.echo("You need to rest before attempting that again.");
      return false; }

    int blossomSize = 0;
    Room Rm = owner.getRoom();

    for (int i=0; i<Rm.getSize(); i++)
    if (Combat.canAttackSilent(owner, Rm.getEnt(i), false, true))
      blossomSize++;

    if (blossomSize == 0) {
      owner.echo("It would be a waste to use it now. There's nobody here it can hit.");
      return false; }

    owner.scheduleEvent(Schedule.DEATH_BLOSSOM, SystemTime.ONE_MINUTE);

    String mine = "";
    String them = "";
    String bord = "***" + Utility.xchars(41+Utility.visibleSize(mn),"*") + "#N";

    mine += "********************************\r\n";
    mine += "*** #NDEATH BLOSSOM ATTACK !!! ***\r\n";
    mine += "********************************#N";

    them += bord + "\r\n*** #N" + mn + " Unleashes a Death Blossom Attack !!! ***\r\n" + bord;

    owner.echo(Utility.rainbowString(mine, "*"));
    Rm.xecho(owner, Utility.rainbowString(them, "*"));

    owner.setIntermittentSkill(blossomSize);

    for (int i=0; i<blossomSize; i++)
      owner.getClient().forceCommand("death blossom");

    commandsWaiting = blossomSize;

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean blossomSingle()

  {
    BlossomList = new ArrayList();
    Room Rm = owner.getRoom();
    commandsWaiting--;

    for (int i=0; i<Rm.getSize(); i++)
    if (Combat.canAttackSilent(owner, Rm.getEnt(i), false, true))
      BlossomList.add(Rm.getEnt(i));

    owner.setIntermittentSkill(owner.getIntermittentSkill()-1);

    if (owner.getIntermittentSkill() == 0) failed = false;
    else failed = true;

    if (BlossomList.size() <= 0) return false;

    // If there's only one target left in the room,
    // make this the last blossom. (To avoid cheating)

    if (BlossomList.size() == 1)
    for (int i=0; i<commandsWaiting; i++)
      owner.getClient().getCommand();

    int targetNum = Utility.getRandomInt(0, BlossomList.size()-1);
    target = (Entity) BlossomList.get(targetNum);
    tn = target.getName();

    updateDamage();
    Combat.engage(owner, target, false);
    target.setCurrentHP(target.getCurrentHP()-damage);

    selfOut = "#rYou slice the shit out of " + tn + "!#N";
    targOut = "#r" + Mn + " slices the shit out of you!#N";
    grupOut = "#r" + Mn + " slices the shit out of " + tn + "!#N";
    obsvOut = "#N" + Mn + " slices the shit out of " + tn + "!#N";

    selfOut += "\r\nYour " + name + " hits " + tn + " for #R" + damage + "#N damage.";
    targOut += "\r\n" + My + " " + name + " hits you for #R" + damage + "#N damage.";
    grupOut += "\r\n" + My + " " + name + " hits " + tn + " for #R" + damage + "#N damage.";
    obsvOut += "\r\n" + My + " " + name + " hits " + tn + " for #N" + damage + "#N damage.";

    skillOutput(false, failed, false);

    owner.lockCommands(350);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}