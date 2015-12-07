#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

void entity::equip(string str)

{
  item *the_item = find_item_inventory(str);

  if (str == "all") {
    equip_all(str);
    return; }

  if (str.size() > 4)
  if (str.substr(0, 4) == "all.") {
    equip_all(str.substr(4, str.size()-4));
    return; }

  if (the_item == NULL) {
    echo("You do not have that item in your inventory.");
    return; }

  equip(the_item, 1);
}

int entity::equip(item *target_item, int audible)

{
  if (!target_item->find_type(ITEM_EQUIPMENT)) {
    if (audible) echo("You cannot equip " + target_item->lname + "#N.");
    return 0; }

  string place = target_item->places;

  if (PLAYER)
  if ((target_item->classes.find(get_ssclass()) == string::npos) && (target_item->classes != "All")) {
    if (audible) echo("That item cannot be worn by your profession.");
    return 0; }

  if ((PLAYER) && (get_STR() < target_item->STR)) {
    if (audible) echo("You require more Strength to use that item.");
    return 0; }

  if ((PLAYER) && (get_DEX() < target_item->DEX)) {
    if (audible) echo("You require more Dexterity to use that item.");
    return 0; }

  if ((PLAYER) && (get_CON() < target_item->CON)) {
    if (audible) echo("You require more Constitution to use that item.");
    return 0; }

  if ((PLAYER) && (get_INT() < target_item->INT)) {
    if (audible) echo("You require more Intelligence to use that item.");
    return 0; }

  if ((PLAYER) && (get_WIS() < target_item->WIS)) {
    if (audible) echo("You require more Wisdom to use that item.");
    return 0; }

  string main_place;

  if (place.find(" ") == string::npos)
    main_place = place;

  else

  {
    int count = 0;

    while (place[count] != ' ') {
      main_place += place[count];
      count++; }
  }

  int can_wear = 1;
  string taken = "";

  if ((place.find("Weapon") != string::npos)       && (Right_Hand != NULL))   { can_wear = 0;  taken = "weapon hand";  }
  if ((place.find("Shield") != string::npos)       && (Left_Hand != NULL))    { can_wear = 0;  taken = "shield hand";  }
  if ((place.find("Right_Hand") != string::npos)   && (Right_Hand != NULL))   { can_wear = 0;  taken = "right hand";   }
  if ((place.find("Left_Hand") != string::npos)    && (Left_Hand != NULL))    { can_wear = 0;  taken = "left hand";    }
  if ((place.find("Body") != string::npos)         && (Body != NULL))         { can_wear = 0;  taken = "body";         }
  if ((place.find("Head") != string::npos)         && (Head != NULL))         { can_wear = 0;  taken = "head";         }
  if ((place.find("Feet") != string::npos)         && (Feet != NULL))         { can_wear = 0;  taken = "feet";         }
  if ((place.find("Back") != string::npos)         && (Back != NULL))         { can_wear = 0;  taken = "back";         }
  if ((place.find("Hands") != string::npos)        && (Hands != NULL))        { can_wear = 0;  taken = "hands";        }
  if ((place.find("Waist") != string::npos)        && (Waist != NULL))        { can_wear = 0;  taken = "waist";        }
  if ((place.find("Legs") != string::npos)         && (Legs != NULL))         { can_wear = 0;  taken = "legs";         }
  if ((place.find("Right_Ear") != string::npos)    && (Right_Ear != NULL))    { can_wear = 0;  taken = "right ear";    }
  if ((place.find("Left_Ear") != string::npos)     && (Left_Ear != NULL))     { can_wear = 0;  taken = "left ear";     }
  if ((place.find("Right_Wrist") != string::npos)  && (Right_Wrist != NULL))  { can_wear = 0;  taken = "right wrist";  }
  if ((place.find("Left_Wrist") != string::npos)   && (Left_Wrist != NULL))   { can_wear = 0;  taken = "left wrist";   }
  if ((place.find("Right_Finger") != string::npos) && (Right_Finger != NULL)) { can_wear = 0;  taken = "right finger"; }
  if ((place.find("Left_Finger") != string::npos)  && (Left_Finger != NULL))  { can_wear = 0;  taken = "left finger";  }
  if ((place.find("Neck") != string::npos)         && (Neck != NULL))         { can_wear = 0;  taken = "neck";         }

  if (place.find("Both_Hands") != string::npos)
  if ((Right_Hand == NULL) || (Left_Hand == NULL)) can_wear = 1;
  else { can_wear = 0;  taken = "hands"; }

  if (place.find("Both_Ears") != string::npos)
  if ((Right_Ear == NULL) || (Left_Ear == NULL)) can_wear = 1;
  else { can_wear = 0;  taken = "ears"; }

  if (place.find("Both_Wrists") != string::npos)
  if ((Right_Wrist == NULL) || (Left_Wrist == NULL)) can_wear = 1;
  else { can_wear = 0;  taken = "wrists"; }

  if (place.find("Both_Fingers") != string::npos)
  if ((Right_Finger == NULL) || (Left_Finger == NULL)) can_wear = 1;
  else { can_wear = 0;  taken = "fingers"; }

  if (can_wear == 0) {
    if (audible) echo("You are already wearing something on your " + taken + ".");
    return 0; }

  if (place.find("Weapon") != string::npos)        Right_Hand    =  target_item;
  if (place.find("Shield") != string::npos)        Left_Hand     =  target_item;
  if (place.find("Right_Hand") != string::npos)    Right_Hand    =  target_item;
  if (place.find("Left_Hand") != string::npos)     Left_Hand     =  target_item;
  if (place.find("Body") != string::npos)          Body          =  target_item;
  if (place.find("Head") != string::npos)          Head          =  target_item;
  if (place.find("Feet") != string::npos)          Feet          =  target_item;
  if (place.find("Back") != string::npos)          Back          =  target_item;
  if (place.find("Hands") != string::npos)         Hands         =  target_item;
  if (place.find("Waist") != string::npos)         Waist         =  target_item;
  if (place.find("Legs") != string::npos)          Legs          =  target_item;
  if (place.find("Right_Ear") != string::npos)     Right_Ear     =  target_item;
  if (place.find("Left_Ear") != string::npos)      Left_Ear      =  target_item;
  if (place.find("Right_Wrist") != string::npos)   Right_Wrist   =  target_item;
  if (place.find("Left_Wrist") != string::npos)    Left_Wrist    =  target_item;
  if (place.find("Right_Finger") != string::npos)  Right_Finger  =  target_item;
  if (place.find("Left_Finger") != string::npos)   Left_Finger   =  target_item;
  if (place.find("Neck") != string::npos)          Neck          =  target_item;

  if (place.find("Both_Hands") != string::npos) {
    if (Right_Hand == NULL) Right_Hand = target_item;
    else Left_Hand = target_item; }

  if (place.find("Both_Ears") != string::npos) {
    if (Right_Ear == NULL) Right_Ear = target_item;
    else Left_Ear = target_item; }

  if (place.find("Both_Wrists") != string::npos) {
    if (Right_Wrist == NULL) Right_Wrist = target_item;
    else Left_Wrist = target_item; }

  if (place.find("Both_Fingers") != string::npos) {
    if (Right_Finger == NULL) Right_Finger = target_item;
    else Left_Finger = target_item; }

  if (audible)

  {
    string output_me;
    string output_you;

    if (main_place == "Weapon")            output_me = "You now wield " + target_item->lname + "#N.";
    else if (main_place == "Shield")       output_me = "You equip " + target_item->lname + "#N.";
    else if (main_place == "Right_Hand")   output_me = "You grab " + target_item->lname + "#N.";
    else if (main_place == "Left_Hand")    output_me = "You grab " + target_item->lname + "#N.";
    else if (main_place == "Both_Hands")   output_me = "You grab " + target_item->lname + "#N.";
    else if (main_place == "Right_Finger") output_me = "You slide on " + target_item->lname + "#N.";
    else if (main_place == "Left_Finger")  output_me = "You slide on " + target_item->lname + "#N.";
    else if (main_place == "Both_Fingers") output_me = "You slide on " + target_item->lname + "#N.";
    else output_me = "You put on " + target_item->lname + "#N.";

    if (main_place == "Weapon")            output_you = name + " wields " + target_item->lname + "#N.";
    else if (main_place == "Shield")       output_you = name + " equips " + target_item->lname + "#N.";
    else if (main_place == "Right_Hand")   output_you = name + " grabs " + target_item->lname + "#N.";
    else if (main_place == "Left_Hand")    output_you = name + " grabs " + target_item->lname + "#N.";
    else if (main_place == "Both_Hands")   output_you = name + " grabs " + target_item->lname + "#N.";
    else if (main_place == "Right_Finger") output_you = name + " slides on " + target_item->lname + "#N.";
    else if (main_place == "Left_Finger")  output_you = name + " slides on " + target_item->lname + "#N.";
    else if (main_place == "Both_Fingers") output_you = name + " slides on " + target_item->lname + "#N.";
    else output_you = name + " puts on " + target_item->lname + "#N.";

    for (int i=0; i<rm->get_size(); i++)
      if (rm->get_ent(i) != this)
        rm->get_ent(i)->add_string(output_you);

    add_string(output_me);
    target_item->equipped();

    rm->echo_strings();
  }

  HR += target_item->hitroll;
  DR += target_item->damroll;
  AC += target_item->armorclass;
  MR += target_item->resistance;

  EQ_HP += target_item->HP;
  EQ_MN += target_item->MN;
  EQ_MV += target_item->MV;

  remove_item_inventory(target_item);
  target_item->ENT = this;
  num_items_equipped++;

  return 1;
}

void entity::equip_all(string str)

{
  int count = 0;
  int allok = 0;
  string forme = "";
  string forothers = "";

  if (num_items == 0) {
    echo("You have nothing to equip.");
    return; }

  int n = num_items;
  item* *invlist = new item* [num_items];

  for (int i=0; i<n; i++)
    invlist[i] = Item_List[i];

  if (str == "all") allok = 1;
  if (str.size() < 3) str = "XXXXXXXXX";

  for (int i=0; i<n; i++)
  if ((allok) || (lowercase(invlist[i]->lname).find(str) != string::npos))
  if (equip(invlist[i], 0))

  {
    count++;
    forme += "You";
    forothers += name;

    if (invlist[i]->places.find("Weapon") != string::npos)       { forme += " now wield ";  forothers += " wields ";    }
    else if (invlist[i]->places.find("Shield") != string::npos)  { forme += " equip ";      forothers += " equips ";    }
    else if (invlist[i]->places.find("Hand") != string::npos)    { forme += " grab ";       forothers += " grabs ";     }
    else if (invlist[i]->places.find("Finger") != string::npos)  { forme += " slide on ";   forothers += " slides on "; }
    else                                                         { forme += " put on ";     forothers += " puts on ";   }

    forme += invlist[i]->lname + "#N.";
    forothers += invlist[i]->lname + "#N.";

    for (int i=0; i<rm->get_size(); i++)
      if (rm->get_ent(i) != this)
        rm->get_ent(i)->add_string(forothers);

    add_string(forme);

    invlist[i]->equipped();

    forme = "";
    forothers = "";
  }

  if (count == 0) {
    if (allok) echo("You have nothing that you can equip.");
    else echo("You don't have anything like that you can equip.");
    return; }

  rm->echo_strings();
}

void entity::equip_direct(item *the_item, string place)

{
  if (place.find("Right_Hand") != string::npos)    Right_Hand    =  the_item;
  if (place.find("Left_Hand") != string::npos)     Left_Hand     =  the_item;
  if (place.find("Body") != string::npos)          Body          =  the_item;
  if (place.find("Head") != string::npos)          Head          =  the_item;
  if (place.find("Feet") != string::npos)          Feet          =  the_item;
  if (place.find("Back") != string::npos)          Back          =  the_item;
  if (place.find("Hands") != string::npos)         Hands         =  the_item;
  if (place.find("Waist") != string::npos)         Waist         =  the_item;
  if (place.find("Legs") != string::npos)          Legs          =  the_item;
  if (place.find("Right_Ear") != string::npos)     Right_Ear     =  the_item;
  if (place.find("Left_Ear") != string::npos)      Left_Ear      =  the_item;
  if (place.find("Right_Wrist") != string::npos)   Right_Wrist   =  the_item;
  if (place.find("Left_Wrist") != string::npos)    Left_Wrist    =  the_item;
  if (place.find("Right_Finger") != string::npos)  Right_Finger  =  the_item;
  if (place.find("Left_Finger") != string::npos)   Left_Finger   =  the_item;
  if (place.find("Neck") != string::npos)          Neck          =  the_item;

  the_item->equipped();

  for (int i=0; i<rm->get_size(); i++)
    rm->get_ent(i)->flush_strings();

  HR += the_item->hitroll;
  DR += the_item->damroll;
  AC += the_item->armorclass;
  MR += the_item->resistance;

  EQ_HP += the_item->HP;
  EQ_MN += the_item->MN;
  EQ_MV += the_item->MV;

  num_items_equipped++;
  the_item->ENT = this;
}

void entity::unequip(string str)

{
  item *the_item = find_item_equipment(str);

  if (str == "all") {
    unequip_all(str);
    return; }

  if (str.size() > 4)
  if (str.substr(0, 4) == "all.") {
    unequip_all(str.substr(4, str.size()-4));
    return; }

  if (the_item == NULL) {
    echo("You are not wearing anything like that.");
    return; }

  unequip(the_item, 1);
}

int entity::unequip(item *the_item, int audible)

{
  for (int i=0; i<16; i++)
    if (the_item == (*ITEMS_EQUIPPED[i]))
      (*ITEMS_EQUIPPED[i]) = NULL;

  string place = the_item->places;
  string main_place;

  if (place.find(" ") == string::npos)
    main_place = place;

  else

  {
    int count = 0;

    while (place[count] != ' ') {
      main_place += place[count];
      count++; }
  }

  if (audible)

  {
    string output_me;
    string output_you;

    if (main_place == "Weapon")            output_me = "You stop wielding " + the_item->lname + "#N.";
    else if (main_place == "Shield")       output_me = "You stop using " + the_item->lname + "#N.";
    else if (main_place == "Right_Hand")   output_me = "You stop using " + the_item->lname + "#N.";
    else if (main_place == "Left_Hand")    output_me = "You stop using " + the_item->lname + "#N.";
    else output_me = "You take off " + the_item->lname + "#N.";

    if (main_place == "Weapon")            output_you = name + " stops wielding " + the_item->lname + "#N.";
    else if (main_place == "Shield")       output_you = name + " stops using " + the_item->lname + "#N.";
    else if (main_place == "Right_Hand")   output_you = name + " stops using " + the_item->lname + "#N.";
    else if (main_place == "Left_Hand")    output_you = name + " stops using " + the_item->lname + "#N.";
    else output_you = name + " takes off " + the_item->lname + "#N.";

    for (int i=0; i<rm->get_size(); i++)
      if (rm->get_ent(i) != this)
        rm->get_ent(i)->add_string(output_you);

    add_string(output_me);

    the_item->unequipped();
    rm->echo_strings();
  }

  HR -= the_item->hitroll;
  DR -= the_item->damroll;
  AC -= the_item->armorclass;
  MR -= the_item->resistance;

  EQ_HP -= the_item->HP;
  EQ_MN -= the_item->MN;
  EQ_MV -= the_item->MV;

  set_current_hp(get_current_hp());
  set_current_mana(get_current_mana());
  set_current_move(get_current_move());

  the_item->ENT = NULL;
  add_item_inventory(the_item);
  num_items_equipped--;

  return 1;
}

void entity::unequip_all(string str)

{
  int allok = 0;
  int ilsize = 0;
  item* item_list[16];
  string forme = "", forothers = "";

  if (num_items_equipped == 0) {
    echo("You are not wearing any equipment.");
    return; }

  if (str == "all") allok = 1;
  if (str.size() < 3) str = "XXXXXXXXX";

  for (int i=0; i<16; i++)
  if ((*ITEMS_EQUIPPED[i]) != NULL)
  if ((allok) || (lowercase((*ITEMS_EQUIPPED[i])->lname).find(str) != string::npos))

  {
    int found = 0;

    for (int j=0; j<ilsize; j++)
      if (item_list[j] == (*ITEMS_EQUIPPED[i]))
        found = 0;

    if (!found) {
      item_list[ilsize] = (*ITEMS_EQUIPPED[i]);
      ilsize++; }
  }

  for (int i=0; i<16; i++)
    (*ITEMS_EQUIPPED[i]) = NULL;

  for (int i=0; i<ilsize; i++)

  {
    item *the_item = item_list[i];
    string place = the_item->places;
    string main_place;

    if (place.find(" ") == string::npos)
      main_place = place;

    else

    {
      int count = 0;

      while (place[count] != ' ') {
        main_place += place[count];
        count++; }
    }

    if (main_place == "Weapon")            forme += "You stop wielding " + the_item->lname + "#N.";
    else if (main_place == "Shield")       forme += "You stop using " + the_item->lname + "#N.";
    else if (main_place == "Right_Hand")   forme += "You stop using " + the_item->lname + "#N.";
    else if (main_place == "Left_Hand")    forme += "You stop using " + the_item->lname + "#N.";
    else                                   forme += "You take off " + the_item->lname + "#N.";

    if (main_place == "Weapon")            forothers += name + " stops wielding " + the_item->lname + "#N.";
    else if (main_place == "Shield")       forothers += name + " stops using " + the_item->lname + "#N.";
    else if (main_place == "Right_Hand")   forothers += name + " stops using " + the_item->lname + "#N.";
    else if (main_place == "Left_Hand")    forothers += name + " stops using " + the_item->lname + "#N.";
    else                                   forothers += name + " takes off " + the_item->lname + "#N.";

    for (int i=0; i<rm->get_size(); i++)
      if (rm->get_ent(i) != this)
        rm->get_ent(i)->add_string(forothers);

    add_string(forme);
    the_item->unequipped();

    unequip(the_item, 0);

    forme = "";
    forothers = "";
  }

  rm->echo_strings();
}

string entity::display_equipment(int external)

{
  string tempstr = "";
  int maxsize = 0, maxsize2 = 0;
  int top = 0, mid = 0, bot = 0;

  if (Right_Hand != NULL) {
    top = 1;
    if (10 > maxsize) maxsize = 10;
    if (visible_size(Right_Hand->name) > maxsize2) maxsize2 = visible_size(Right_Hand->name); }

  if (Left_Hand != NULL) {
    top = 1;
    if (9 > maxsize) maxsize = 9;
    if (visible_size(Left_Hand->name) > maxsize2) maxsize2 = visible_size(Left_Hand->name); }

  if (Head != NULL) {
    mid = 1;
    if (4 > maxsize) maxsize = 4;
    if (visible_size(Head->name) > maxsize2) maxsize2 = visible_size(Head->name); }

  if (Body != NULL) {
    mid = 1;
    if (4 > maxsize) maxsize = 4;
    if (visible_size(Body->name) > maxsize2) maxsize2 = visible_size(Body->name); }

  if (Back != NULL) {
    mid = 1;
    if (4 > maxsize) maxsize = 4;
    if (visible_size(Back->name) > maxsize2) maxsize2 = visible_size(Back->name); }

  if (Hands != NULL) {
    mid = 1;
    if (5 > maxsize) maxsize = 5;
    if (visible_size(Hands->name) > maxsize2) maxsize2 = visible_size(Hands->name); }

  if (Waist != NULL) {
    mid = 1;
    if (5 > maxsize) maxsize = 5;
    if (visible_size(Waist->name) > maxsize2) maxsize2 = visible_size(Waist->name); }

  if (Legs != NULL) {
    mid = 1;
    if (4 > maxsize) maxsize = 4;
    if (visible_size(Legs->name) > maxsize2) maxsize2 = visible_size(Legs->name); }

  if (Feet != NULL) {
    mid = 1;
    if (4 > maxsize) maxsize = 4;
    if (visible_size(Feet->name) > maxsize2) maxsize2 = visible_size(Feet->name); }

  if (Neck != NULL) {
    bot = 1;
    if (4 > maxsize) maxsize = 4;
    if (visible_size(Neck->name) > maxsize2) maxsize2 = visible_size(Neck->name); }

  if (Right_Wrist != NULL) {
    bot = 1;
    if (11 > maxsize) maxsize = 11;
    if (visible_size(Right_Wrist->name) > maxsize2) maxsize2 = visible_size(Right_Wrist->name); }

  if (Left_Wrist != NULL) {
    bot = 1;
    if (10 > maxsize) maxsize = 10;
    if (visible_size(Left_Wrist->name) > maxsize2) maxsize2 = visible_size(Left_Wrist->name); }

  if (Right_Finger != NULL) {
    bot = 1;
    if (12 > maxsize) maxsize = 12;
    if (visible_size(Right_Finger->name) > maxsize2) maxsize2 = visible_size(Right_Finger->name); }

  if (Left_Finger != NULL) {
    bot = 1;
    if (11 > maxsize) maxsize = 11;
    if (visible_size(Left_Finger->name) > maxsize2) maxsize2 = visible_size(Left_Finger->name); }

  if (Right_Ear != NULL) {
    bot = 1;
    if (9 > maxsize) maxsize = 9;
    if (visible_size(Right_Ear->name) > maxsize2) maxsize2 = visible_size(Right_Ear->name); }

  if (Left_Ear != NULL) {
    bot = 1;
    if (8 > maxsize) maxsize = 8;
    if (visible_size(Left_Ear->name) > maxsize2) maxsize2 = visible_size(Left_Ear->name); }

  if (external)

  {
    string condition = "perfect";
    if (get_current_hp() < get_max_hp()) condition = "good";
    if (get_current_hp() < ((get_max_hp() * 75) / 100)) condition = "fair";
    if (get_current_hp() < ((get_max_hp() * 50) / 100)) condition = "bad";
    if (get_current_hp() < ((get_max_hp() * 25) / 100)) condition = "dire";

    tempstr += "#N" + name + " is in " + condition + " condition.\r\n";

    if (maxsize == 0) {
      tempstr += "#N" + name + " is not wearing any equipment.";
      return tempstr; }
  }

  else

  {
    if (num_items_equipped <= 0) {
      tempstr += "You are not wearing any equipment.";
      return tempstr; }

    tempstr += "#NYou are wearing #n" + intconvert(num_items_equipped) + " #Nitem";
    if (num_items_equipped > 1) tempstr += "s";
    tempstr += ".\r\n";
  }

  string border = "\r\n  #n+-";
  for (int i=1; i<=maxsize; i++) border += "-";
  border += "-+-";
  for (int i=1; i<=maxsize2; i++) border += "-";
  border += "-+#N";

  tempstr += border;

  if (top)

  {
    if (Right_Hand != NULL) {
      tempstr += "\r\n  #n|#N Right Hand";
      for (int i=1; i<=maxsize-10; i++) tempstr += " ";
      tempstr += " #n|#N " + Right_Hand->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Right_Hand->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Left_Hand != NULL) {
      tempstr += "\r\n  #n|#N Left Hand";
      for (int i=1; i<=maxsize-9; i++) tempstr += " ";
      tempstr += " #n|#N " + Left_Hand->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Left_Hand->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    tempstr += border;
  }

  if (mid)

  {
    if (Head != NULL) {
      tempstr += "\r\n  #n|#N Head";
      for (int i=1; i<=maxsize-4; i++) tempstr += " ";
      tempstr += " #n|#N " + Head->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Head->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Body != NULL) {
      tempstr += "\r\n  #n|#N Body";
      for (int i=1; i<=maxsize-4; i++) tempstr += " ";
      tempstr += " #n|#N " + Body->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Body->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Back != NULL) {
      tempstr += "\r\n  #n|#N Back";
      for (int i=1; i<=maxsize-4; i++) tempstr += " ";
      tempstr += " #n|#N " + Back->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Back->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Hands != NULL) {
      tempstr += "\r\n  #n|#N Hands";
      for (int i=1; i<=maxsize-5; i++) tempstr += " ";
      tempstr += " #n|#N " + Hands->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Hands->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Waist != NULL) {
      tempstr += "\r\n  #n|#N Waist";
      for (int i=1; i<=maxsize-5; i++) tempstr += " ";
      tempstr += " #n|#N " + Waist->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Waist->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Legs != NULL) {
      tempstr += "\r\n  #n|#N Legs";
      for (int i=1; i<=maxsize-4; i++) tempstr += " ";
      tempstr += " #n|#N " + Legs->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Legs->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Feet != NULL) {
      tempstr += "\r\n  #n|#N Feet";
      for (int i=1; i<=maxsize-4; i++) tempstr += " ";
      tempstr += " #n|#N " + Feet->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Feet->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    tempstr += border;
  }

  if (bot)

  {
    if (Neck != NULL) {
      tempstr += "\r\n  #n|#N Neck";
      for (int i=1; i<=maxsize-4; i++) tempstr += " ";
      tempstr += " #n|#N " + Neck->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Neck->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Right_Wrist != NULL) {
      tempstr += "\r\n  #n|#N Right Wrist";
      for (int i=1; i<=maxsize-11; i++) tempstr += " ";
      tempstr += " #n|#N " + Right_Wrist->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Right_Wrist->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Left_Wrist != NULL) {
      tempstr += "\r\n  #n|#N Left Wrist";
      for (int i=1; i<=maxsize-10; i++) tempstr += " ";
      tempstr += " #n|#N " + Left_Wrist->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Left_Wrist->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Right_Finger != NULL) {
      tempstr += "\r\n  #n|#N Right Finger";
      for (int i=1; i<=maxsize-12; i++) tempstr += " ";
      tempstr += " #n|#N " + Right_Finger->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Right_Finger->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Left_Finger != NULL) {
      tempstr += "\r\n  #n|#N Left Finger";
      for (int i=1; i<=maxsize-11; i++) tempstr += " ";
      tempstr += " #n|#N " + Left_Finger->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Left_Finger->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Right_Ear != NULL) {
      tempstr += "\r\n  #n|#N Right Ear";
      for (int i=1; i<=maxsize-9; i++) tempstr += " ";
      tempstr += " #n|#N " + Right_Ear->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Right_Ear->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    if (Left_Ear != NULL) {
      tempstr += "\r\n  #n|#N Left Ear";
      for (int i=1; i<=maxsize-8; i++) tempstr += " ";
      tempstr += " #n|#N " + Left_Ear->name + "#N";
      for (int i=1; i<=maxsize2-visible_size(Left_Ear->name); i++) tempstr += " ";
      tempstr += " #n|#N"; }

    tempstr += border;
  }

  return tempstr;
}

item* entity::find_item_equipment(string str)

{
  int target_num = clip_number(&str);
  if (str.size() < 3) return NULL;

  for (int i=0; i<16; i++)
  if ((*ITEMS_EQUIPPED[i]) != NULL)

  {
    if (abbreviation(str, remove_colors((*ITEMS_EQUIPPED[i])->name)))

    {
      target_num--;
      if (!target_num) return (*ITEMS_EQUIPPED[i]);
    }
  }

  return NULL;
}

string entity::pfile_equipment_information()

{
  string str = "";
  item* *temp = new item* [num_items_equipped];
  int count = 0;

  for (int i=0; i<16; i++)

  {
    if ((*ITEMS_EQUIPPED[i]) != NULL) {
      temp[count] = (*ITEMS_EQUIPPED[i]);
      count++; }
  }

  pfile_inventory_traverse(temp, count, &str, 0, 1);

  return str;
}

string entity::get_places(item *eq)

{
  string temp = "";

  if (Right_Hand == eq)    temp += "Right_Hand ";
  if (Left_Hand == eq)     temp += "Left_Hand ";
  if (Body == eq)          temp += "Body ";
  if (Head == eq)          temp += "Head ";
  if (Feet == eq)          temp += "Feet ";
  if (Back == eq)          temp += "Back ";
  if (Hands == eq)         temp += "Hands ";
  if (Waist == eq)         temp += "Waist ";
  if (Legs == eq)          temp += "Legs ";
  if (Right_Ear == eq)     temp += "Right_Ear ";
  if (Left_Ear == eq)      temp += "Left_Ear ";
  if (Right_Wrist == eq)   temp += "Right_Wrist ";
  if (Left_Wrist == eq)    temp += "Left_Wrist ";
  if (Right_Finger == eq)  temp += "Right_Finger ";
  if (Left_Finger == eq)   temp += "Left_Finger ";
  if (Neck == eq)          temp += "Neck ";

  if (temp == "") return "None";

  temp.erase(temp.size()-1, 1);

  return temp;
}