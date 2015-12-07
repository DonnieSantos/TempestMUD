import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class Evaluator extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList targets  = new ArrayList();
  public static boolean checkHostile = false;
  public static Room RM;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int getNumTargets()     { return targets.size();          }
  public static Entity getTarget(int i) { return (Entity) targets.get(i); }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int ALL          = 0;
  public static final int DARK         = 1;
  public static final int LIGHT        = 2;
  public static final int WARRIOR      = 3;
  public static final int KNIGHT       = 4;
  public static final int BERZERKER    = 5;
  public static final int ANTI_PALADIN = 6;
  public static final int CRUSADER     = 7;
  public static final int DEATH_KNIGHT = 8;
  public static final int THIEF        = 9;
  public static final int ASSASSIN     = 10;
  public static final int ROGUE        = 11;
  public static final int RANGER       = 12;
  public static final int SHADOWBLADE  = 13;
  public static final int MERCHANT     = 14;
  public static final int MERCENARY    = 15;
  public static final int CLERIC       = 16;
  public static final int DRUID        = 17;
  public static final int MONK         = 18;
  public static final int PRIEST       = 19;
  public static final int DARK_CLERIC  = 20;
  public static final int HEALER       = 21;
  public static final int ALCHEMIST    = 22;
  public static final int MAGE         = 23;
  public static final int WIZARD       = 24;
  public static final int ILLUSIONIST  = 25;
  public static final int SORCERER     = 26;
  public static final int WARLOCK      = 27;
  public static final int SUMMONER     = 28;
  public static final int SHAPESHIFTER = 29;
  public static final int SANCTUARY    = 30;
  public static final int SHIELD       = 31;
  public static final int POISON       = 32;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void evaluate(Entity E, String rem)

  {
    targets.clear();
    RM = E.getRoom();
    if (RM == null) return;

    while (first(rem).length() > 0)

    {
      boolean inverse = false;
      StringBuffer evalString = new StringBuffer(first(rem).toUpperCase());
      rem = last(rem);

      if (evalString.charAt(0) == '!') {
        evalString.deleteCharAt(0);
        inverse = true; }

      try

      {
        Field evalField = Evaluator.class.getDeclaredField(evalString.toString());
        int evalType = evalField.getInt(evalField);
        Evaluator.eval(evalType, inverse);
      }

      catch (Exception e) { System.out.println(e); }
    }

    for (int i=targets.size()-1; i>=0; i--)
      if (getTarget(i).getNoHassle()) targets.remove(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static void eval(int condition, boolean inverse)

  {
    switch (condition)

    {
      case ALL          : all(inverse); break;
      case DARK         : dark(inverse); break;
      case LIGHT        : light(inverse); break;
      case WARRIOR      : evalClass("Warrior", inverse); break;
      case KNIGHT       : evalClass("Knight", inverse); break;
      case BERZERKER    : evalClass("Berzerker", inverse); break;
      case ANTI_PALADIN : evalClass("Anti-Paladin", inverse); break;
      case CRUSADER     : evalClass("Crusader", inverse); break;
      case DEATH_KNIGHT : evalClass("Death Knight", inverse); break;
      case THIEF        : evalClass("Thief", inverse); break;
      case ASSASSIN     : evalClass("Assassin", inverse); break;
      case ROGUE        : evalClass("Rogue", inverse); break;
      case RANGER       : evalClass("Ranger", inverse); break;
      case SHADOWBLADE  : evalClass("Shadowblade", inverse); break;
      case MERCHANT     : evalClass("Merchant", inverse); break;
      case MERCENARY    : evalClass("Mercenary", inverse); break;
      case CLERIC       : evalClass("Cleric", inverse); break;
      case DRUID        : evalClass("Druid", inverse); break;
      case MONK         : evalClass("Monk", inverse); break;
      case PRIEST       : evalClass("Priest", inverse); break;
      case DARK_CLERIC  : evalClass("Dark Cleric", inverse); break;
      case HEALER       : evalClass("Healer", inverse); break;
      case ALCHEMIST    : evalClass("Alchemist", inverse); break;
      case MAGE         : evalClass("Mage", inverse); break;
      case WIZARD       : evalClass("Wizard", inverse); break;
      case ILLUSIONIST  : evalClass("Illusionist", inverse); break;
      case SORCERER     : evalClass("Sorcerer", inverse); break;
      case WARLOCK      : evalClass("Warlock", inverse); break;
      case SUMMONER     : evalClass("Summoner", inverse); break;
      case SHAPESHIFTER : evalClass("Shapeshifter", inverse); break;
      case SANCTUARY    : evalEffect(Effect.EFFECT_SANCTUARY, inverse); break;
      case SHIELD       : evalEffect(Effect.EFFECT_SHIELD, inverse); break;
      case POISON       : evalEffect(Effect.EFFECT_POISON, inverse); break;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static void all(boolean inverse)

  {
    if (!inverse)

    {
      for (int i=0; i<RM.getNumEnts(); i++)
      if (RM.getEnt(i).isPlayer())
        targets.add(RM.getEnt(i));
    }

    else targets.clear();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static void dark(boolean inverse)

  {
    if (inverse) { light(false); return; }

    if (targets.size() > 0)

    {
      for (int i=targets.size()-1; i>=0; i--)
        if (getTarget(i).getAlignment() != ALIGN_DARK)
          targets.remove(i);
    }

    else

    {
      for (int i=0; i<RM.getNumEnts(); i++)

      {
        Entity E = RM.getEnt(i);
        if (E.getAlignment() == ALIGN_DARK)
        if (!targets.contains(E)) targets.add(E);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static void light(boolean inverse)

  {
    if (inverse) { dark(false); return; }

    if (targets.size() > 0)
    for (int i=targets.size()-1; i>=0; i--)

    {
      Entity E = getTarget(i);
      if (E.getAlignment() != ALIGN_LIGHT) targets.remove(i);
    }

    else
    for (int i=0; i<RM.getNumEnts(); i++)

    {
      Entity E = RM.getEnt(i);
      if (E.getAlignment() == ALIGN_LIGHT)
      if (!targets.contains(E)) targets.add(E);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static void evalClass(String cname, boolean inverse)

  {
    if (inverse)
    for (int i=0; i<RM.getNumEnts(); i++)

    {
      Entity E = RM.getEnt(i);
      if (!E.getCharClass().equalsIgnoreCase(cname))
      if (!targets.contains(E)) targets.add(E);
    }

    else
    for (int i=0; i<RM.getNumEnts(); i++)

    {
      Entity E = RM.getEnt(i);
      if (E.getCharClass().equalsIgnoreCase(cname))
      if (!targets.contains(E)) targets.add(E);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static void evalEffect(int eID, boolean inverse)

  {
    if (inverse)

    {
      for (int i=targets.size()-1; i>=0; i--)
      if (getTarget(i).findEffect(eID) != -1)
        targets.remove(i);
    }

    else

    {
      for (int i=targets.size()-1; i>=0; i--)
      if (getTarget(i).findEffect(eID) == -1)
        targets.remove(i);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}