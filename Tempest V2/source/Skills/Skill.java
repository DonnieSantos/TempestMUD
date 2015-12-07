import java.io.*;
import java.util.*;

abstract class Skill extends Ability implements Externalizable

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int TOTAL_NUM_SKILLS       =  13;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int SKILL_SCOUT            =  0;
  public static final int SKILL_TRACK            =  1;
  public static final int SKILL_ASSASSIN_STRIKE  =  2;
  public static final int SKILL_STAB_AND_TWIST   =  3;
  public static final int SKILL_HEART_ATTACK     =  4;
  public static final int SKILL_DEATH_BLOSSOM    =  5;
  public static final int SKILL_CRITICAL_HIT     =  6;
  public static final int SKILL_TIGER_STRIKE     =  7;
  public static final int SKILL_BACKSTAB         =  8;
  public static final int SKILL_CIRCLE_AROUND    =  9;
  public static final int SKILL_DISARM           =  10;
  public static final int SKILL_POISON_STRIKE    =  11;
  public static final int SKILL_MARTIAL_ARTS     =  12;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  protected Entity owner;
  protected Entity target;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void setOwner(Entity e)  { owner = e;               }
  static int getNumSkills()       { return TOTAL_NUM_SKILLS; }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  abstract boolean execute(String rem);

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean isSkill() { return true;  }
  public boolean isSpell() { return false; }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
  
  public Skill replicate()
  
  {
    Skill S = null;
    try { S = (Skill)clone(); }
    catch (Exception e) { System.out.println(e); }
    return S;
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Skill()

  {
    super();

    owner = null;
    target = null;
    level = 0;
    timesUsed = 0;

    prereqs = new ArrayList();
    levreqs = new ArrayList();
    reqSTR = 0;
    reqDEX = 0;
    reqCON = 0;
    reqINT = 0;
    reqWIS = 0;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getSkillName(int i)

  {
    switch(i)

    {
      case SKILL_SCOUT:            return "Scout";
      case SKILL_TRACK:            return "Track";
      case SKILL_ASSASSIN_STRIKE:  return "Assassin Strike";
      case SKILL_STAB_AND_TWIST:   return "Stab and Twist";
      case SKILL_HEART_ATTACK:     return "Heart Attack";
      case SKILL_DEATH_BLOSSOM:    return "Death Blossom";
      case SKILL_CRITICAL_HIT:     return "Critical Hit";
      case SKILL_TIGER_STRIKE:     return "Tiger Strike";
      case SKILL_BACKSTAB:         return "Backstab";
      case SKILL_CIRCLE_AROUND:    return "Circle Around";
      case SKILL_DISARM:           return "Disarm";
      case SKILL_POISON_STRIKE:    return "Poison Strike";
      case SKILL_MARTIAL_ARTS:     return "Martial Arts";
    }

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void init(Entity Owner, int Level)

  {
    owner = Owner;
    level = Level;

    if (Owner != null)

    {
      mn = owner.getName();
      Mn = owner.getPName();
      my = Utility.possessive(mn);
      My = Utility.possessive(Mn);
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void addRequirement(String rName, int rLevel)

  {
    prereqs.add(new String(rName));
    levreqs.add(new Integer(rLevel));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void skillOutput(boolean passive, boolean failed, boolean peaceful)

  {
    owner.getRoom().setSkillRedirect(true);

    if (passive) owner.passiveOutput(target, selfOut, targOut, grupOut, obsvOut);
    else owner.combatOutput(target, selfOut, targOut, grupOut, obsvOut);

    if (!failed)

    {
      if (peaceful) trainPeaceful();
      else train();
    }

    owner.getRoom().setSkillRedirect(false);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean train()

  {
    if (level >= 100) return false;

    int max = learnRate * ((level / 20) + 1);

    timesUsed++;

    if ((timesUsed % max) != 0) return false;

    level++;
    owner.fightEcho(name + " has improved!");

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean trainPeaceful()

  {
    if (level >= 100) return false;

    int max = learnRate * ((level / 20) + 1);

    timesUsed++;

    if ((timesUsed % max) != 0) return false;

    level++;
    owner.echo(name + " has improved!");

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void trainPassive(Skill Sk)

  {
    owner.getRoom().setSkillRedirect(true);
    Sk.train();
    owner.getRoom().setSkillRedirect(false);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean tryDamageSkill(String rem, String cut, String output)

  {
    if (cut.length() > 0)
    if (rem.indexOf(cut) == 0) {
      rem = rem.substring(cut.length(), rem.length());
      rem = Utility.clearWhiteSpace(rem); }

    target = owner.getRoom().findEntity(owner, rem);

    if ((target == null) && (owner.getPlayerState() == Utility.PSTATE_FIGHTING))
      target = owner.getTarget();

    if (target == null) {
      owner.echo(output);
      return false; }

    if (!Combat.canAttack(owner, target, false, true)) return false;

    tn = target.getName();
    Tn = target.getPName();
    ty = Utility.possessive(tn);
    Ty = Utility.possessive(Tn);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static Skill createSkill(String sName, Entity ENT, int lev)

  {
    Ability A = null;
    Class C = null;

    sName = Utility.getProperClassName(sName);

    try

    {
      C = Class.forName(sName);
      A = (Ability) C.newInstance();
    }

    catch (Exception e) { return null; }
    catch (Throwable t) { return null; }


    try

    {
      Skill Sk = (Skill) A;
      Sk.init(ENT, lev);
      return Sk;
    }

    catch (Exception e) { return null; }
    catch (Throwable t) { return null; }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("LEVEL");     out.writeObject(new Integer(level));
      out.writeObject("TIMESUSED"); out.writeObject(new Integer(timesUsed));
      out.writeObject("SKILL END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("SKILL END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("LEVEL"))           level = ((Integer)in.readObject()).intValue();
        else if (tag.equals("TIMESUSED"))  timesUsed = ((Integer)in.readObject()).intValue();
        else if (!tag.equals("SKILL END")) in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}