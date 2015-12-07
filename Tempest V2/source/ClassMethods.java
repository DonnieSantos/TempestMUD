import java.io.*;
import java.util.*;

public class ClassMethods

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean isWarrior(Entity E)  { return isWarrior(E.getCharClass()); }
  public static boolean isThief(Entity E)    { return isThief(E.getCharClass());   }
  public static boolean isCleric(Entity E)   { return isCleric(E.getCharClass());  }
  public static boolean isMage(Entity E)     { return isMage(E.getCharClass());    }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean isWarrior(String s)

  {
    if (s.equalsIgnoreCase("Warrior"))       return true;
    if (s.equalsIgnoreCase("Knight"))        return true;
    if (s.equalsIgnoreCase("Berzerker"))     return true;
    if (s.equalsIgnoreCase("Paladin"))       return true;
    if (s.equalsIgnoreCase("Anti-Paladin"))  return true;
    if (s.equalsIgnoreCase("Crusader"))      return true;
    if (s.equalsIgnoreCase("Death Knight"))  return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean isThief(String s)

  {
    if (s.equalsIgnoreCase("Thief"))        return true;
    if (s.equalsIgnoreCase("Assassin"))     return true;
    if (s.equalsIgnoreCase("Rogue"))        return true;
    if (s.equalsIgnoreCase("Ranger"))       return true;
    if (s.equalsIgnoreCase("Shadowblade"))  return true;
    if (s.equalsIgnoreCase("Merchant"))     return true;
    if (s.equalsIgnoreCase("Mercenary"))    return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean isCleric(String s)

  {
    if (s.equalsIgnoreCase("Cleric"))       return true;
    if (s.equalsIgnoreCase("Druid"))        return true;
    if (s.equalsIgnoreCase("Monk"))         return true;
    if (s.equalsIgnoreCase("Priest"))       return true;
    if (s.equalsIgnoreCase("Dark Cleric"))  return true;
    if (s.equalsIgnoreCase("Healer"))       return true;
    if (s.equalsIgnoreCase("Alchemist"))    return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean isMage(String s)

  {
    if (s.equalsIgnoreCase("Mage"))          return true;
    if (s.equalsIgnoreCase("Wizard"))        return true;
    if (s.equalsIgnoreCase("Illusionist"))   return true;
    if (s.equalsIgnoreCase("Sorcerer"))      return true;
    if (s.equalsIgnoreCase("Warlock"))       return true;
    if (s.equalsIgnoreCase("Summoner"))      return true;
    if (s.equalsIgnoreCase("Shapeshifter"))  return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean validClass(String s)

  {
    if (isWarrior(s))  return true;
    if (isThief(s))    return true;
    if (isCleric(s))   return true;
    if (isMage(s))     return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String shorten(String s, boolean withColor)

  {
    String sclass = "";

    if (withColor)

    {
      if (s.equalsIgnoreCase("Warrior"))            sclass = "#BWa#N";
      else if (s.equalsIgnoreCase("Knight"))        sclass = "#CKn#N";
      else if (s.equalsIgnoreCase("Berzerker"))     sclass = "#RBr#N";
      else if (s.equalsIgnoreCase("Paladin"))       sclass = "#bPa#N";
      else if (s.equalsIgnoreCase("Death Knight"))  sclass = "#BDk#N";
      else if (s.equalsIgnoreCase("Crusader"))      sclass = "#cCr#N";
      else if (s.equalsIgnoreCase("Anti Paladin"))  sclass = "#yAp#N";
      else if (s.equalsIgnoreCase("Thief"))         sclass = "#YTh#N";
      else if (s.equalsIgnoreCase("Assassin"))      sclass = "#rAs#N";
      else if (s.equalsIgnoreCase("Rogue"))         sclass = "#YRg#N";
      else if (s.equalsIgnoreCase("Ranger"))        sclass = "#GRa#N";
      else if (s.equalsIgnoreCase("Shadowblade"))   sclass = "#bSb#N";
      else if (s.equalsIgnoreCase("Merchant"))      sclass = "#YMe#N";
      else if (s.equalsIgnoreCase("Mercenary"))     sclass = "#CMc#N";
      else if (s.equalsIgnoreCase("Cleric"))        sclass = "#GCl#N";
      else if (s.equalsIgnoreCase("Druid"))         sclass = "#gDr#N";
      else if (s.equalsIgnoreCase("Monk"))          sclass = "#GMo#N";
      else if (s.equalsIgnoreCase("Priest"))        sclass = "#nPr#N";
      else if (s.equalsIgnoreCase("Dark Cleric"))   sclass = "#BDc#N";
      else if (s.equalsIgnoreCase("Healer"))        sclass = "#bHl#N";
      else if (s.equalsIgnoreCase("Alchemist"))     sclass = "#gAl#N";
      else if (s.equalsIgnoreCase("Mage"))          sclass = "#MMg#N";
      else if (s.equalsIgnoreCase("Wizard"))        sclass = "#mWi#N";
      else if (s.equalsIgnoreCase("Illusionist"))   sclass = "#yIl#N";
      else if (s.equalsIgnoreCase("Warlock"))       sclass = "#MWl#N";
      else if (s.equalsIgnoreCase("Sorcerer"))      sclass = "#gSc#N";
      else if (s.equalsIgnoreCase("Summoner"))      sclass = "#cSm#N";
      else if (s.equalsIgnoreCase("Shapeshifter"))  sclass = "#RSh#N";
    }

    else

    {
      if (s.equalsIgnoreCase("Warrior"))            sclass = "Wa";
      else if (s.equalsIgnoreCase("Knight"))        sclass = "Kn";
      else if (s.equalsIgnoreCase("Berzerker"))     sclass = "Br";
      else if (s.equalsIgnoreCase("Paladin"))       sclass = "Pa";
      else if (s.equalsIgnoreCase("Death Knight"))  sclass = "Dk";
      else if (s.equalsIgnoreCase("Crusader"))      sclass = "Cr";
      else if (s.equalsIgnoreCase("Anti Paladin"))  sclass = "Ap";
      else if (s.equalsIgnoreCase("Thief"))         sclass = "Th";
      else if (s.equalsIgnoreCase("Assassin"))      sclass = "As";
      else if (s.equalsIgnoreCase("Rogue"))         sclass = "Rg";
      else if (s.equalsIgnoreCase("Ranger"))        sclass = "Ra";
      else if (s.equalsIgnoreCase("Shadowblade"))   sclass = "Sb";
      else if (s.equalsIgnoreCase("Merchant"))      sclass = "Me";
      else if (s.equalsIgnoreCase("Mercenary"))     sclass = "Mc";
      else if (s.equalsIgnoreCase("Cleric"))        sclass = "Cl";
      else if (s.equalsIgnoreCase("Druid"))         sclass = "Dr";
      else if (s.equalsIgnoreCase("Monk"))          sclass = "Mo";
      else if (s.equalsIgnoreCase("Priest"))        sclass = "Pr";
      else if (s.equalsIgnoreCase("Dark Cleric"))   sclass = "Dc";
      else if (s.equalsIgnoreCase("Healer"))        sclass = "Hl";
      else if (s.equalsIgnoreCase("Alchemist"))     sclass = "Al";
      else if (s.equalsIgnoreCase("Mage"))          sclass = "Mg";
      else if (s.equalsIgnoreCase("Wizard"))        sclass = "Wi";
      else if (s.equalsIgnoreCase("Illusionist"))   sclass = "Il";
      else if (s.equalsIgnoreCase("Warlock"))       sclass = "Wl";
      else if (s.equalsIgnoreCase("Sorcerer"))      sclass = "Sc";
      else if (s.equalsIgnoreCase("Summoner"))      sclass = "Sm";
      else if (s.equalsIgnoreCase("Shapeshifter"))  sclass = "Sh";
    }

    return sclass;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String addColor(String s, boolean withColor)

  {
    if (!withColor) return s;

    if (s.equalsIgnoreCase("Warrior"))            return "#B" + s + "#N";
    else if (s.equalsIgnoreCase("Knight"))        return "#C" + s + "#N";
    else if (s.equalsIgnoreCase("Berzerker"))     return "#R" + s + "#N";
    else if (s.equalsIgnoreCase("Paladin"))       return "#b" + s + "#N";
    else if (s.equalsIgnoreCase("Death Knight"))  return "#B" + s + "#N";
    else if (s.equalsIgnoreCase("Crusader"))      return "#c" + s + "#N";
    else if (s.equalsIgnoreCase("Anti Paladin"))  return "#y" + s + "#N";
    else if (s.equalsIgnoreCase("Thief"))         return "#Y" + s + "#N";
    else if (s.equalsIgnoreCase("Assassin"))      return "#r" + s + "#N";
    else if (s.equalsIgnoreCase("Rogue"))         return "#Y" + s + "#N";
    else if (s.equalsIgnoreCase("Ranger"))        return "#G" + s + "#N";
    else if (s.equalsIgnoreCase("Shadowblade"))   return "#b" + s + "#N";
    else if (s.equalsIgnoreCase("Merchant"))      return "#Y" + s + "#N";
    else if (s.equalsIgnoreCase("Mercenary"))     return "#C" + s + "#N";
    else if (s.equalsIgnoreCase("Cleric"))        return "#G" + s + "#N";
    else if (s.equalsIgnoreCase("Druid"))         return "#g" + s + "#N";
    else if (s.equalsIgnoreCase("Monk"))          return "#G" + s + "#N";
    else if (s.equalsIgnoreCase("Priest"))        return "#n" + s + "#N";
    else if (s.equalsIgnoreCase("Dark Cleric"))   return "#B" + s + "#N";
    else if (s.equalsIgnoreCase("Healer"))        return "#b" + s + "#N";
    else if (s.equalsIgnoreCase("Alchemist"))     return "#g" + s + "#N";
    else if (s.equalsIgnoreCase("Mage"))          return "#M" + s + "#N";
    else if (s.equalsIgnoreCase("Wizard"))        return "#m" + s + "#N";
    else if (s.equalsIgnoreCase("Illusionist"))   return "#y" + s + "#N";
    else if (s.equalsIgnoreCase("Warlock"))       return "#M" + s + "#N";
    else if (s.equalsIgnoreCase("Sorcerer"))      return "#g" + s + "#N";
    else if (s.equalsIgnoreCase("Summoner"))      return "#c" + s + "#N";
    else if (s.equalsIgnoreCase("Shapeshifter"))  return "#R" + s + "#N";

    return s;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean validMentor(Entity Student, Entity Mentor)

  {
    if (Student.getLevel() > Mentor.getLevel()) return false;

    String S = Student.getCharClass();
    String M = Mentor.getCharClass();

    if ((isWarrior(S)) && (isWarrior(M)))  return true;
    if ((isThief(S))   && (isThief(M)))    return true;
    if ((isCleric(S))  && (isCleric(M)))   return true;
    if ((isMage(S))    && (isMage(M)))     return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}