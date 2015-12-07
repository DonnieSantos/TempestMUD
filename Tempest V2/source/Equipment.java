import java.lang.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

public class Equipment extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int NUM_PLACES   = 16;
  public static final int RIGHT_HAND   = 0;
  public static final int LEFT_HAND    = 1;
  public static final int BODY         = 2;
  public static final int HEAD         = 3;
  public static final int FEET         = 4;
  public static final int BACK         = 5;
  public static final int HANDS        = 6;
  public static final int WAIST        = 7;
  public static final int LEGS         = 8;
  public static final int RIGHT_EAR    = 9;
  public static final int LEFT_EAR     = 10;
  public static final int RIGHT_WRIST  = 11;
  public static final int LEFT_WRIST   = 12;
  public static final int RIGHT_FINGER = 13;
  public static final int LEFT_FINGER  = 14;
  public static final int NECK         = 15;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean wield(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to wield?");
      return false; }

    if (ENT.sleepCheck()) return false;

    ArrayList ilist = ItemUse.parseListQuery(ENT.getInventory().getList(), input);

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item) ilist.get(i);
      if (I.getPlaces().indexOf("Weapon") != -1)
        return equip(ENT, I, true);

      ENT.echo("You can't wield " + I.getLName() + ".");
      return false;
    }

    ENT.echo("You do not have that item in your inventory.");
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean hold(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to hold?");
      return false; }

    if (ENT.sleepCheck()) return false;

    ArrayList ilist = ItemUse.parseListQuery(ENT.getInventory().getList(), input);

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item) ilist.get(i);
      if (I.getPlaces().indexOf("Hold") != -1)
        return equip(ENT, I, true);

      ENT.echo("You can't hold " + I.getLName() + ".");
      return false;
    }

    ENT.echo("You do not have that item in your inventory.");
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void equip(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo(displayEquipment(ENT, false));
      return; }

    if (ENT.sleepCheck()) return;

    if (input.equals("all"))       { equipAll(ENT, input);  return; }
    if (input.startsWith("all."))  { equipAll(ENT, input);  return; }

    ArrayList ilist = ItemUse.parseListQuery(ENT.getInventory().getList(), input);

    if (ilist.size() <= 0) {
      ENT.echo("You do not have that item in your inventory.");
      return; }

    for (int i=0; i<ilist.size(); i++)
      equip(ENT, (Item)ilist.get(i), true);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean equip(Entity ENT, Item ITEM, boolean audible)

  {
    if (ITEM.findType(Item.ITEM_EQUIPMENT) == -1) {
    if (audible) ENT.echo("You cannot equip " + ITEM.getLName() + "#N.");
    return false; }

    boolean PLAYER = ENT.isPlayer();
    String places = ITEM.getPlaces();
    String classes = ITEM.getClasses();

    if (PLAYER)
    if ((classes.indexOf(ENT.getSClass()) == -1) && (!classes.equals("All"))) {
      if (audible) ENT.echo("That item cannot be worn by your profession.");
      return false; }

    if ((PLAYER) && (ENT.getSTR() < ITEM.getSTR())) {
      if (audible) ENT.echo("You require more Strength to use that item.");
      return false; }

    if ((PLAYER) && (ENT.getDEX() < ITEM.getDEX())) {
      if (audible) ENT.echo("You require more Dexterity to use that item.");
      return false; }

    if ((PLAYER) && (ENT.getCON() < ITEM.getCON())) {
      if (audible) ENT.echo("You require more Constitution to use that item.");
      return false; }

    if ((PLAYER) && (ENT.getINT() < ITEM.getINT())) {
      if (audible) ENT.echo("You require more Intelligence to use that item.");
      return false; }

    if ((PLAYER) && (ENT.getWIS() < ITEM.getWIS())) {
      if (audible) ENT.echo("You require more Wisdom to use that item.");
      return false; }

    boolean canWear = true;
    String taken = "";
    String mainPlace = first(places);
    Item[] equipment = ENT.getEquipment();

    if ((places.indexOf("Weapon") != -1)       && (equipment[RIGHT_HAND] != null))   { canWear = false;  taken = "right hand";   }
    if ((places.indexOf("Hold") != -1)         && (equipment[LEFT_HAND] != null))    { canWear = false;  taken = "left hand";    }
    if ((places.indexOf("Body") != -1)         && (equipment[BODY] != null))         { canWear = false;  taken = "body";         }
    if ((places.indexOf("Head") != -1)         && (equipment[HEAD] != null))         { canWear = false;  taken = "head";         }
    if ((places.indexOf("Feet") != -1)         && (equipment[FEET] != null))         { canWear = false;  taken = "feet";         }
    if ((places.indexOf("Back") != -1)         && (equipment[BACK] != null))         { canWear = false;  taken = "back";         }
    if ((places.indexOf("Hands") != -1)        && (equipment[HANDS] != null))        { canWear = false;  taken = "hands";        }
    if ((places.indexOf("Waist") != -1)        && (equipment[WAIST] != null))        { canWear = false;  taken = "waist";        }
    if ((places.indexOf("Legs") != -1)         && (equipment[LEGS] != null))         { canWear = false;  taken = "legs";         }
    if ((places.indexOf("Right_Ear") != -1)    && (equipment[RIGHT_EAR] != null))    { canWear = false;  taken = "right ear";    }
    if ((places.indexOf("Left_Ear") != -1)     && (equipment[LEFT_EAR] != null))     { canWear = false;  taken = "left ear";     }
    if ((places.indexOf("Right_Wrist") != -1)  && (equipment[RIGHT_WRIST] != null))  { canWear = false;  taken = "right wrist";  }
    if ((places.indexOf("Left_Wrist") != -1)   && (equipment[LEFT_WRIST] != null))   { canWear = false;  taken = "left wrist";   }
    if ((places.indexOf("Right_Finger") != -1) && (equipment[RIGHT_FINGER] != null)) { canWear = false;  taken = "right finger"; }
    if ((places.indexOf("Left_Finger") != -1)  && (equipment[LEFT_FINGER] != null))  { canWear = false;  taken = "left finger";  }
    if ((places.indexOf("Neck") != -1)         && (equipment[NECK] != null))         { canWear = false;  taken = "neck";         }

    if (places.indexOf("Either_Ear") != -1)
    if ((equipment[RIGHT_EAR] == null) || (equipment[LEFT_EAR] == null)) canWear = true;
    else { canWear = false;  taken = "ears"; }

    if (places.indexOf("Either_Wrist") != -1)
    if ((equipment[RIGHT_WRIST] == null) || (equipment[LEFT_WRIST] == null)) canWear = true;
    else { canWear = false;  taken = "wrists"; }

    if (places.indexOf("Either_Finger") != -1)
    if ((equipment[RIGHT_FINGER] == null) || (equipment[LEFT_FINGER] == null)) canWear = true;
    else { canWear = false;  taken = "fingers"; }

    if (!canWear)

    {
      // Fix this so right hand and left hand change depending on the character.

      if (audible) ENT.echo("You are already wearing something on your " + taken + ".");
      return false;
    }

    if (places.indexOf("Weapon") != -1)        equipment[RIGHT_HAND]    =  ITEM;
    if (places.indexOf("Hold") != -1)          equipment[LEFT_HAND]     =  ITEM;
    if (places.indexOf("Body") != -1)          equipment[BODY]          =  ITEM;
    if (places.indexOf("Head") != -1)          equipment[HEAD]          =  ITEM;
    if (places.indexOf("Feet") != -1)          equipment[FEET]          =  ITEM;
    if (places.indexOf("Back") != -1)          equipment[BACK]          =  ITEM;
    if (places.indexOf("Hands") != -1)         equipment[HANDS]         =  ITEM;
    if (places.indexOf("Waist") != -1)         equipment[WAIST]         =  ITEM;
    if (places.indexOf("Legs") != -1)          equipment[LEGS]          =  ITEM;
    if (places.indexOf("Right_Ear") != -1)     equipment[RIGHT_EAR]     =  ITEM;
    if (places.indexOf("Left_Ear") != -1)      equipment[LEFT_EAR]      =  ITEM;
    if (places.indexOf("Right_Wrist") != -1)   equipment[RIGHT_WRIST]   =  ITEM;
    if (places.indexOf("Left_Wrist") != -1)    equipment[LEFT_WRIST]    =  ITEM;
    if (places.indexOf("Right_Finger") != -1)  equipment[RIGHT_FINGER]  =  ITEM;
    if (places.indexOf("Left_Finger") != -1)   equipment[LEFT_FINGER]   =  ITEM;
    if (places.indexOf("Neck") != -1)          equipment[NECK]          =  ITEM;

    if (places.indexOf("Either_Ear") != -1) {
      if (equipment[RIGHT_EAR] == null) equipment[RIGHT_EAR] = ITEM;
      else equipment[LEFT_EAR] = ITEM; }

    if (places.indexOf("Either_Wrist") != -1) {
      if (equipment[RIGHT_WRIST] == null) equipment[RIGHT_WRIST] = ITEM;
      else equipment[LEFT_WRIST] = ITEM; }

    if (places.indexOf("Either_Finger") != -1) {
      if (equipment[RIGHT_FINGER] == null) equipment[RIGHT_FINGER] = ITEM;
      else equipment[LEFT_FINGER] = ITEM; }

    if (audible)

    {
      if (mainPlace.equals("Weapon"))             ENT.echo("You now wield " + ITEM.getLName() + "#N.");
      else if (mainPlace.equals("Hold"))          ENT.echo("You grab " + ITEM.getLName() + "#N.");
      else if (mainPlace.equals("Right_Finger"))  ENT.echo("You slide on " + ITEM.getLName() + "#N.");
      else if (mainPlace.equals("Left_Finger"))   ENT.echo("You slide on " + ITEM.getLName() + "#N.");
      else if (mainPlace.equals("Either_Finger")) ENT.echo("You slide on " + ITEM.getLName() + "#N.");
      else ENT.echo("You put on " + ITEM.getLName() + "#N.");

      if (mainPlace.equals("Weapon"))             ENT.sendToAwake(ENT.getName() + " wields " + ITEM.getLName() + "#N.");
      else if (mainPlace.equals("Hold"))          ENT.sendToAwake(ENT.getName() + " grabs " + ITEM.getLName() + "#N.");
      else if (mainPlace.equals("Right_Finger"))  ENT.sendToAwake(ENT.getName() + " slides on " + ITEM.getLName() + "#N.");
      else if (mainPlace.equals("Left_Finger"))   ENT.sendToAwake(ENT.getName() + " slides on " + ITEM.getLName() + "#N.");
      else if (mainPlace.equals("Either_Finger")) ENT.sendToAwake(ENT.getName() + " slides on " + ITEM.getLName() + "#N.");
      else ENT.sendToAwake(ENT.getName() + " puts on " + ITEM.getLName() + "#N.");

      ITEM.action(Item.RESPONSE_EQUIP);
    }

    ENT.setHR(ENT.getHR() + ITEM.getHR());
    ENT.setDR(ENT.getDR() + ITEM.getDR());
    ENT.setAC(ENT.getAC() + ITEM.getAC());
    ENT.setMR(ENT.getMR() + ITEM.getMR());

    ENT.setEQHP(ENT.getEQHP() + ITEM.getHP());
    ENT.setEQMN(ENT.getEQMN() + ITEM.getMN());
    ENT.setEQMV(ENT.getEQMV() + ITEM.getMV());

    ENT.removeItemInventory(ITEM);

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void equipAll(Entity ENT, String input)

  {
    ArrayList ilist = ItemUse.parseListQuery(ENT.getInventory().getList(), input);

    if (ilist.isEmpty()) {
      ENT.echo("You have nothing to equip.");
      return; }

    boolean allOK = false;
    int count = 0;
    int size = ilist.size();

    if (input.equals("all")) allOK = true;

    for (int i=0; i<size; i++)
    if (equip(ENT, (Item)ilist.get(i), false))

    {
      count++;
      Item ITEM = (Item)ilist.get(i);
      String forme = "You";
      String forothers = ENT.getName();
      String places = ITEM.getPlaces();

      if (places.indexOf("Weapon") != -1)       { forme += " now wield ";  forothers += " wields ";    }
      else if (places.indexOf("Hold") != -1)    { forme += " grab ";       forothers += " grabs ";     }
      else if (places.indexOf("Finger") != -1)  { forme += " slide on ";   forothers += " slides on "; }
      else                                      { forme += " put on ";     forothers += " puts on ";   }

      forme += ITEM.getLName() + "#N.";
      forothers += ITEM.getLName() + "#N.";

      ENT.echo(forme);
      ENT.sendToAwake(forothers);
      ITEM.action(Item.RESPONSE_EQUIP);
    }

    if (count == 0) {
      if (allOK) ENT.echo("You have nothing that you can equip.");
      else ENT.echo("You don't have anything like that you can equip.");
      return; }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void unequip(Entity ENT, String input)

  {
    if (input.equals("all"))       { unequipAll(ENT, input);  return; }
    if (input.startsWith("all."))  { unequipAll(ENT, input);  return; }

    ArrayList equipment = new ArrayList(Arrays.asList(ENT.getEquipment()));

    while (equipment.contains(null))
      equipment.remove(equipment.indexOf(null));

    ArrayList eqlist = ItemUse.parseListQuery(new ArrayList(equipment), input);

    if (eqlist.isEmpty()) {
      ENT.echo("You are not wearing anything like that.");
      return; }

    int size = eqlist.size();

    for (int i=0; i<size; i++)
      unequip(ENT, (Item)eqlist.get(i), true);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean unequip(Entity ENT, Item ITEM, boolean audible)

  {
    Item[] equipment = ENT.getEquipment();

    for (int i=0; i<NUM_PLACES; i++)
      if (equipment[i] == ITEM)
        equipment[i] = null;

    String places = ITEM.getPlaces();
    String mainPlace = first(places);

    if (mainPlace.indexOf("Finger") != -1) mainPlace = "Finger";
    if (mainPlace.indexOf("Hold")   != -1) mainPlace = "Hold";
    if (mainPlace.indexOf("Weapon") != -1) mainPlace = "Weapon";

    if (audible)

    {
      String output_me;
      String output_you;

      if (mainPlace.equals("Weapon"))       output_me = "You stop wielding " + ITEM.getLName() + "#N.";
      else if (mainPlace.equals("Hold"))    output_me = "You stop using " + ITEM.getLName() + "#N.";
      else if (mainPlace.equals("Finger"))  output_me = "You slide off " + ITEM.getLName() + "#N.";
      else output_me = "You take off " + ITEM.getLName() + "#N.";

      if (mainPlace.equals("Weapon"))       output_you = ENT.getName() + " stops wielding " + ITEM.getLName() + "#N.";
      else if (mainPlace.equals("Hold"))    output_you = ENT.getName() + " stops using " + ITEM.getLName() + "#N.";
      else if (mainPlace.equals("Finger"))  output_you = ENT.getName() + " slides off " + ITEM.getLName() + "#N.";
      else output_you = ENT.getName() + " takes off " + ITEM.getLName() + "#N.";

      ENT.echo(output_me);
      ENT.sendToAwake(output_you);
      ITEM.action(Item.RESPONSE_UNEQUIP);
    }

    ENT.setHR(ENT.getHR() - ITEM.getHR());
    ENT.setDR(ENT.getDR() - ITEM.getDR());
    ENT.setAC(ENT.getAC() - ITEM.getAC());
    ENT.setMR(ENT.getMR() - ITEM.getMR());

    ENT.setEQHP(ENT.getEQHP() - ITEM.getHP());
    ENT.setEQMN(ENT.getEQMN() - ITEM.getMN());
    ENT.setEQMV(ENT.getEQMV() - ITEM.getMV());

    ENT.setCurrentHP(ENT.getCurrentHP());
    ENT.setCurrentMN(ENT.getCurrentMN());
    ENT.setCurrentMV(ENT.getCurrentMV());

    ENT.addItemInventory(ITEM);

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void dereference(Entity ENT, Item ITEM)

  {
    Item[] equipment = ENT.getEquipment();

    for (int i=0; i<NUM_PLACES; i++)
      if (equipment[i] == ITEM)
        equipment[i] = null;

    ENT.setHR(ENT.getHR() - ITEM.getHR());
    ENT.setDR(ENT.getDR() - ITEM.getDR());
    ENT.setAC(ENT.getAC() - ITEM.getAC());
    ENT.setMR(ENT.getMR() - ITEM.getMR());

    ENT.setEQHP(ENT.getEQHP() - ITEM.getHP());
    ENT.setEQMN(ENT.getEQMN() - ITEM.getMN());
    ENT.setEQMV(ENT.getEQMV() - ITEM.getMV());

    ENT.setCurrentHP(ENT.getCurrentHP());
    ENT.setCurrentMN(ENT.getCurrentMN());
    ENT.setCurrentMV(ENT.getCurrentMV());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void unequipAll(Entity ENT, String input)

  {
    Item[] equipment = ENT.getEquipment();
    ArrayList eqlist = new ArrayList();

    for (int i=0; i<equipment.length; i++)
    if (equipment[i] != null)
    if (!eqlist.contains(equipment[i]))
      eqlist.add(equipment[i]);

    if (eqlist.isEmpty()) {
      ENT.echo("You are not wearing any equipment.");
      return; }

    ArrayList ilist = new ArrayList();

    if (!input.equals("all"))

    {
      input = input.replaceFirst("all.", "");

      for (int i=0; i<eqlist.size(); i++)
      if (!ilist.contains(eqlist.get(i)))

      {
        Item ITEM = (Item)eqlist.get(i);

        if (abbreviation(input, removeColors(ITEM.getName())))
          ilist.add(ITEM);
      }

      if (ilist.size() == 0) {
        ENT.echo("You are not wearing any equipment by that name.");
        return; }
    }

    else ilist.addAll(eqlist);

    int ilsize = ilist.size();

    for (int i=0; i<ilsize; i++)

    {
      Item the_item = (Item)ilist.get(i);
      String places = the_item.getPlaces();
      String mainPlace = first(places);
      String forme = "";
      String forothers = "";

      if (mainPlace.indexOf("Finger") != -1) mainPlace = "Finger";
      if (mainPlace.indexOf("Hold")   != -1) mainPlace = "Hold";
      if (mainPlace.indexOf("Weapon") != -1) mainPlace = "Weapon";

      if (mainPlace.equals("Weapon"))       forme += "You stop wielding " + the_item.getLName() + "#N.";
      else if (mainPlace.equals("Hold"))    forme += "You stop using " + the_item.getLName() + "#N.";
      else if (mainPlace.equals("Finger"))  forme += "You slide off " + the_item.getLName() + "#N.";
      else                                  forme += "You take off " + the_item.getLName() + "#N.";

      if (mainPlace.equals("Weapon"))       forothers += ENT.getName() + " stops wielding " + the_item.getLName() + "#N.";
      else if (mainPlace.equals("Hold"))    forothers += ENT.getName() + " stops using " + the_item.getLName() + "#N.";
      else if (mainPlace.equals("Finger"))  forothers += ENT.getName() + " slides off " + the_item.getLName() + "#N.";
      else                                  forothers += ENT.getName() + " takes off " + the_item.getLName() + "#N.";

      unequip(ENT, the_item, false);

      ENT.echo(forme);
      ENT.sendToAwake(forothers);
      the_item.action(Item.RESPONSE_UNEQUIP);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String displayEquipment(Entity ENT, boolean external)

  {
    String tempstr = "";
    Item[] equipment = ENT.getEquipment();
    int maxsize = 0, maxsize2 = 0;
    boolean top = false;
    boolean mid = false;
    boolean bot = false;

    if (equipment[RIGHT_HAND] != null) {
      top = true;
      if (10 > maxsize) maxsize = 10;
      if (visibleSize(equipment[RIGHT_HAND].getName()) > maxsize2) maxsize2 = visibleSize(equipment[RIGHT_HAND].getName()); }

    if (equipment[LEFT_HAND] != null) {
      top = true;
      if (9 > maxsize) maxsize = 9;
      if (visibleSize(equipment[LEFT_HAND].getName()) > maxsize2) maxsize2 = visibleSize(equipment[LEFT_HAND].getName()); }

    if (equipment[HEAD] != null) {
      mid = true;
      if (4 > maxsize) maxsize = 4;
      if (visibleSize(equipment[HEAD].getName()) > maxsize2) maxsize2 = visibleSize(equipment[HEAD].getName()); }

    if (equipment[BODY] != null) {
      mid = true;
      if (4 > maxsize) maxsize = 4;
      if (visibleSize(equipment[BODY].getName()) > maxsize2) maxsize2 = visibleSize(equipment[BODY].getName()); }

    if (equipment[BACK] != null) {
      mid = true;
      if (4 > maxsize) maxsize = 4;
      if (visibleSize(equipment[BACK].getName()) > maxsize2) maxsize2 = visibleSize(equipment[BACK].getName()); }

    if (equipment[HANDS] != null) {
      mid = true;
      if (5 > maxsize) maxsize = 5;
      if (visibleSize(equipment[HANDS].getName()) > maxsize2) maxsize2 = visibleSize(equipment[HANDS].getName()); }

    if (equipment[WAIST] != null) {
      mid = true;
      if (5 > maxsize) maxsize = 5;
      if (visibleSize(equipment[WAIST].getName()) > maxsize2) maxsize2 = visibleSize(equipment[WAIST].getName()); }

    if (equipment[LEGS] != null) {
      mid = true;
      if (4 > maxsize) maxsize = 4;
      if (visibleSize(equipment[LEGS].getName()) > maxsize2) maxsize2 = visibleSize(equipment[LEGS].getName()); }

    if (equipment[FEET] != null) {
      mid = true;
      if (4 > maxsize) maxsize = 4;
      if (visibleSize(equipment[FEET].getName()) > maxsize2) maxsize2 = visibleSize(equipment[FEET].getName()); }

    if (equipment[NECK] != null) {
      bot = true;
      if (4 > maxsize) maxsize = 4;
      if (visibleSize(equipment[NECK].getName()) > maxsize2) maxsize2 = visibleSize(equipment[NECK].getName()); }

    if (equipment[RIGHT_WRIST] != null) {
      bot = true;
      if (11 > maxsize) maxsize = 11;
      if (visibleSize(equipment[RIGHT_WRIST].getName()) > maxsize2) maxsize2 = visibleSize(equipment[RIGHT_WRIST].getName()); }

    if (equipment[LEFT_WRIST] != null) {
      bot = true;
      if (10 > maxsize) maxsize = 10;
      if (visibleSize(equipment[LEFT_WRIST].getName()) > maxsize2) maxsize2 = visibleSize(equipment[LEFT_WRIST].getName()); }

    if (equipment[RIGHT_FINGER] != null) {
      bot = true;
      if (12 > maxsize) maxsize = 12;
      if (visibleSize(equipment[RIGHT_FINGER].getName()) > maxsize2) maxsize2 = visibleSize(equipment[RIGHT_FINGER].getName()); }

    if (equipment[LEFT_FINGER] != null) {
      bot = true;
      if (11 > maxsize) maxsize = 11;
      if (visibleSize(equipment[LEFT_FINGER].getName()) > maxsize2) maxsize2 = visibleSize(equipment[LEFT_FINGER].getName()); }

    if (equipment[RIGHT_EAR] != null) {
      bot = true;
      if (9 > maxsize) maxsize = 9;
      if (visibleSize(equipment[RIGHT_EAR].getName()) > maxsize2) maxsize2 = visibleSize(equipment[RIGHT_EAR].getName()); }

    if (equipment[LEFT_EAR] != null) {
      bot = true;
      if (8 > maxsize) maxsize = 8;
      if (visibleSize(equipment[LEFT_EAR].getName()) > maxsize2) maxsize2 = visibleSize(equipment[LEFT_EAR].getName()); }

    if (external)

    {
      String condition = "perfect";
      if (ENT.getCurrentHP() < ENT.getMaxHP()) condition = "good";
      if (ENT.getCurrentHP() < ((ENT.getMaxHP() * 75) / 100)) condition = "fair";
      if (ENT.getCurrentHP() < ((ENT.getMaxHP() * 50) / 100)) condition = "bad";
      if (ENT.getCurrentHP() < ((ENT.getMaxHP() * 25) / 100)) condition = "dire";

      tempstr += "#N" + ENT.getPName() + " is in " + condition + " condition.\r\n";

      if (maxsize == 0) {
        tempstr += "#N" + ENT.getPName() + " is not wearing any equipment.";
        return tempstr; }
    }

    else

    {
      int numEQ = numEquipped(ENT);

      if (numEQ <= 0) {
        tempstr += "You are not wearing any equipment.";
        return tempstr; }

      tempstr += "#NYou are wearing #n" + numEQ + " #Nitem";
      if (numEQ > 1) tempstr += "s";
      tempstr += ".\r\n";
    }

    String border = "\r\n  #n+-";
    for (int i=1; i<=maxsize; i++) border += "-";
    border += "-+-";
    for (int i=1; i<=maxsize2; i++) border += "-";
    border += "-+#N";

    tempstr += border;

    if (top)

    {
      if (equipment[RIGHT_HAND] != null) {
        tempstr += "\r\n  #n|#N Right Hand";
        for (int i=1; i<=maxsize-10; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[RIGHT_HAND].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[RIGHT_HAND].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[LEFT_HAND] != null) {
        tempstr += "\r\n  #n|#N Left Hand";
        for (int i=1; i<=maxsize-9; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[LEFT_HAND].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[LEFT_HAND].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      tempstr += border;
    }

    if (mid)

    {
      if (equipment[HEAD] != null) {
        tempstr += "\r\n  #n|#N Head";
        for (int i=1; i<=maxsize-4; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[HEAD].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[HEAD].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[BODY] != null) {
        tempstr += "\r\n  #n|#N Body";
        for (int i=1; i<=maxsize-4; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[BODY].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[BODY].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[BACK] != null) {
        tempstr += "\r\n  #n|#N Back";
        for (int i=1; i<=maxsize-4; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[BACK].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[BACK].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[HANDS] != null) {
        tempstr += "\r\n  #n|#N Hands";
        for (int i=1; i<=maxsize-5; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[HANDS].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[HANDS].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[WAIST] != null) {
        tempstr += "\r\n  #n|#N Waist";
        for (int i=1; i<=maxsize-5; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[WAIST].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[WAIST].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[LEGS] != null) {
        tempstr += "\r\n  #n|#N Legs";
        for (int i=1; i<=maxsize-4; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[LEGS].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[LEGS].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[FEET] != null) {
        tempstr += "\r\n  #n|#N Feet";
        for (int i=1; i<=maxsize-4; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[FEET].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[FEET].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      tempstr += border;
    }

    if (bot)

    {
      if (equipment[NECK] != null) {
        tempstr += "\r\n  #n|#N Neck";
        for (int i=1; i<=maxsize-4; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[NECK].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[NECK].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[RIGHT_WRIST] != null) {
        tempstr += "\r\n  #n|#N Right Wrist";
        for (int i=1; i<=maxsize-11; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[RIGHT_WRIST].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[RIGHT_WRIST].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[LEFT_WRIST] != null) {
        tempstr += "\r\n  #n|#N Left Wrist";
        for (int i=1; i<=maxsize-10; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[LEFT_WRIST].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[LEFT_WRIST].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[RIGHT_FINGER] != null) {
        tempstr += "\r\n  #n|#N Right Finger";
        for (int i=1; i<=maxsize-12; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[RIGHT_FINGER].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[RIGHT_FINGER].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[LEFT_FINGER] != null) {
        tempstr += "\r\n  #n|#N Left Finger";
        for (int i=1; i<=maxsize-11; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[LEFT_FINGER].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[LEFT_FINGER].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[RIGHT_EAR] != null) {
        tempstr += "\r\n  #n|#N Right Ear";
        for (int i=1; i<=maxsize-9; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[RIGHT_EAR].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[RIGHT_EAR].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      if (equipment[LEFT_EAR] != null) {
        tempstr += "\r\n  #n|#N Left Ear";
        for (int i=1; i<=maxsize-8; i++) tempstr += " ";
        tempstr += " #n|#N " + equipment[LEFT_EAR].getName() + "#N";
        for (int i=1; i<=maxsize2-visibleSize(equipment[LEFT_EAR].getName()); i++) tempstr += " ";
        tempstr += " #n|#N"; }

      tempstr += border;
    }

    return tempstr;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int numEquipped(Entity ENT)

  {
    ArrayList temp = new ArrayList();
    Item[] equipment = ENT.getEquipment();
    int count = 0;

    for (int i=0; i<NUM_PLACES; i++)
    if (equipment[i] != null)
    if (!temp.contains(equipment[i]))

    {
      temp.add(equipment[i]);
      count++;
    }

    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean shouldRemoveEffect(Entity ENT, String spellName)

  {
    ArrayList temp = new ArrayList();
    Item[] equipment = ENT.getEquipment();
    int count = 0;

    for (int i=0; i<NUM_PLACES; i++)
    if (equipment[i] != null)
    if (!temp.contains(equipment[i]))

    {
      temp.add(equipment[i]);
      if (equipment[i].hasInnateSpell(spellName))
        count++;
    }

    if (count >= 1) return false;

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean isEquipped(Entity ENT, Item I)

  {
    Item[] EQ = ENT.getEquipment();

    for (int i=0; i<EQ.length; i++)
    if (EQ[i] == I) return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}
