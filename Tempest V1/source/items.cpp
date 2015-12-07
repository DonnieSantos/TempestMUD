#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

// *************************************************************** //
// *********************** Item Constructor ********************** //
// *************************************************************** //

item::item()

{
  id = 0;
  zone_id = 0;
  type_list = NULL;
  num_types = 0;
  level = 1;
  worth = 0;
  name = "";
  lname = "";
  look_desc = "";
  ground_desc = "";
  flag_list = NULL;
  num_flags = 0;

  TIME_STAMP = "";
  TYPE_STAMP = "";

  rm = NULL;
  ENT = NULL;

  Item_List = NULL;
  parent = NULL;
  num_items = 0;
  all_items = 0;
  max_items = 0;
  max_level = 0;

  hitroll = 0;
  damroll = 0;
  armorclass = 0;
  resistance = 0;
  classes = "None";
  places = "None";
  worth = 0;
  HP = 0;
  MN = 0;
  MV = 0;
  STR = 0;
  DEX = 0;
  CON = 0;
  INT = 0;
  WIS = 0;

  num_enhancements = 0;
  enhance_list     = NULL;
  mana_list        = NULL;
  power_list       = NULL;
  all_spells       = 0;
  all_reduce       = 0;
  all_power        = 0;

  num_spells = 0;
  max_uses = 0;
  uses = 0;
  spell_list = new string[1];
  liquid_type = "None";

  max_charges = 0;
  charges_left = 0;
  num_charged = 0;
  charged_list = new string[1];

  num_innate = 0;
  innate_list = new string[1];

  board_name = "";
  board_pointer = NULL;

  decay_time = -1;
  player_corpse = 0;
  amount = 0;

  PLAYABLE = 0;
  PULLABLE = 0;

  for (int i=0; i<NUM_ITEM_ACTIONS; i++)

  {
    num_commands[i] = 0;
    work_room[i] = 0;
    mana_cost[i] = 0;
    num_fail[i] = 0;
    num_succ[i] = 0;
    action_conditions[i] = "";
    action_classes[i] = "";
  }

  for (int i=0; i<NUM_ITEM_ACTIONS; i++)
  for (int j=0; j<NUM_ACTION_COMMANDS; j++)

  {
    fail_commands[i][j] = "";
    fail_locations[i][j] = 0;
    succ_commands[i][j] = "";
    succ_locations[i][j] = 0;
    action_commands[i][j] = "";
    command_location[i][j] = 0;
  }
}

// *************************************************************** //
// ************************* Item Functions ********************** //
// *************************************************************** //

void item::add_type(int type)

{
  int* temp = new int[num_types+1];

  for (int i=0; i<num_types; i++)
    temp[i] = type_list[i];

  temp[num_types] = type;
  num_types++;

  type_list = temp;
}

int item::find_type(int type)

{
  for (int i=0; i<num_types; i++)
    if (type_list[i] == type)
      return 1;

  return 0;
}

void item::add_flag(string flag)

{
  string* temp = new string[num_flags+1];

  for (int i=0; i<num_flags; i++)
    temp[i] = flag_list[i];

  temp[num_flags] = flag;
  num_flags++;

  flag_list = temp;
}

void item::rem_flag(string flag)

{
  if (!find_flag(flag))
    return;

  int found = 0;
  string* temp = new string[num_flags-1];

  for (int i=0; i<num_flags; i++)
    if (flag_list[i] != flag)
      temp[i-found] = flag_list[i];
    else found = 1;

  delete [] flag_list;
  flag_list = temp;
  num_flags--;
}

int item::find_flag(string flag)

{
  for (int i=0; i<num_flags; i++)
    if (flag_list[i] == flag)
      return 1;

  return 0;
}

void item::add_innate(string spell_name)

{
  string* temp = new string [num_innate+1];

  for (int i=0; i<num_innate; i++)
    temp[i] = innate_list[i];

  temp[num_innate] = spell_name;
  num_innate++;

  innate_list = temp;
}

void item::add_charged(string spell_name)

{
  string* temp = new string [num_charged+1];

  for (int i=0;  i<num_charged; i++)
    temp[i] = charged_list[i];

  temp[num_charged] = spell_name;
  num_charged++;

  charged_list = temp;
}

// *************************************************************** //
// ************************** ITEM ACTIONS *********************** //
// *************************************************************** //

void item::play(entity* owner)

{
  room* RM = owner->get_room();

  for (int i=0; i<RM->get_size(); i++)
  if (RM->get_ent(i)->PLAYER)
    if (RM->get_ent(i) != owner)
      RM->get_ent(i)->add_string("\r\n" + owner->get_name() + " plays " + lname + ".");

  owner->add_string("You play " + lname + ".");

  int working_room = work_room[ITEM_ACTION_PLAY];

  if ((RM->get_id() == working_room) || (working_room == -1))

  {
    ghost* temp_ghost = new ghost(owner, this);
    temp_ghost->gat(ITEM_ACTION_PLAY);
    temp_ghost->echo_output(ITEM_ACTION_PLAY);
    delete temp_ghost;
  }

  owner->set_current_mana(owner->get_current_mana() - mana_cost[ITEM_ACTION_PLAY]);
  RM->echo_strings();
}

void item::pull(entity* owner)

{
  room* RM = owner->get_room();

  for (int i=0; i<RM->get_size(); i++)
  if (RM->get_ent(i)->PLAYER)
    if (RM->get_ent(i) != owner)
      RM->get_ent(i)->add_string("\r\n" + owner->get_name() + " pulls " + lname + ".");

  owner->add_string("You pull " + lname + ".");

  int working_room = work_room[ITEM_ACTION_PULL];

  if ((RM->get_id() == working_room) || (working_room == -1))

  {
    ghost* temp_ghost = new ghost(owner, this);
    temp_ghost->gat(ITEM_ACTION_PULL);
    temp_ghost->echo_output(ITEM_ACTION_PULL);
    delete temp_ghost;
  }

  owner->set_current_mana(owner->get_current_mana() - mana_cost[ITEM_ACTION_PULL]);
  RM->echo_strings();
}

// *************************************************************** //
// ********************* Enhancement Functions ******************* //
// *************************************************************** //

void item::equipped()

{
  ENT->set_silent_cast(1);

  for (int i=0; i<num_innate; i++)

  {
    ENT->item_cast(innate_list[i], ENT->get_name());
    ENT->get_effects()->renew(innate_list[i], -1);
  }

  ENT->set_silent_cast(0);
}

void item::unequipped()

{
  if (!num_innate) return;

  int* no_remove = new int[num_innate];

  for (int i=0; i<num_innate; i++)
    no_remove[i] = 0;

  for (int i=0; i<ENT->get_equip_size(); i++)

  {
    item* temp = ENT->get_equipped(i);

    if (temp != NULL)
    if (temp->num_innate)
    for (int j=0; j<num_innate; j++)
      for (int x=0; x<temp->num_innate; x++)
        if (innate_list[j] == temp->innate_list[x])
          no_remove[j] = 1;
  }

  for (int i=0; i<num_innate; i++)
    if (!no_remove[i])
      ENT->add_string(ENT->get_effects()->remove_effect(innate_list[i]));
}

void item::cast(string spell_name, int* mana, int* power)

{
  double temp;

  for (int i=0; i<num_enhancements; i++)
  if (spell_name == enhance_list[i])

  {
    temp = (double)*mana * ((double)mana_list[i] / (double)100);
    *mana = *mana - temp;

    temp = (double)*power * ((double)power_list[i] / (double)100);
    *power = *power + temp;
  }

  if (all_spells)

  {
    temp = (double)*mana * ((double)all_reduce / (double)100);
    *mana = *mana - temp;

    temp = (double)*power * ((double)all_power / (double)100);
    *power = *power + temp;
  }
}

void item::add_enhancement(string str)

{
  int power = stringconvert(clip_end(&str));
  int mana = stringconvert(clip_end(&str));

  if (str == "All Spells")

  {
    all_spells = 1;
    all_reduce = mana;
    all_power  = power;
  }

  else

  {
    string* temp_names = new string[num_enhancements+1];
    int* temp_powers = new int[num_enhancements+1];
    int* temp_manas  = new int[num_enhancements+1];

    for (int i=0; i<num_enhancements; i++)

    {
      temp_names[i]  = enhance_list[i];
      temp_powers[i] = power_list[i];
      temp_manas[i]  = mana_list[i];
    }

    temp_names[num_enhancements] = str;
    temp_powers[num_enhancements] = power;
    temp_manas[num_enhancements] = mana;

    num_enhancements++;

    enhance_list = temp_names;
    mana_list = temp_manas;
    power_list = temp_powers;
  }
}

// *************************************************************** //
// ********************** Consumable Functions ******************* //
// *************************************************************** //

void item::add_cspell(string str)

{
  string* temp = new string[num_spells+1];

  for (int i=0; i<num_spells; i++)
    temp[i] = spell_list[i];

  temp[num_spells++] = str;
  spell_list = temp;
}

void item::drink(entity* target)

{
  if (!find_type(ITEM_DRINKABLE)) {
    target->echo("You can't drink from " + lname + ".");
    return; }

  if (find_type(ITEM_DRINKCONTAINER))

  {
    if (uses <= 0) {
      target->echo(name + " is empty.");
      return; }

    uses--;

    liquids LL;
    LL.drink_liquid(target, liquid_type, lname);

    if (uses <= 0) liquid_type = "";

    return;
  }

  if (find_type(ITEM_FOUNTAIN))

  {
    liquids LL;
    LL.drink_liquid(target, liquid_type, lname);
    return;
  }

  target->echo("Error--Item is drinkable but not a liquid container or fountain.");
}

void item::eat(entity* target)

{
  if (!find_type(ITEM_FOOD)) {
    target->echo("You can't eat " + lname + ".");
    return; }

  target->remove_item_inventory(this);
  target->echo("You eat " + lname +  ".\r\nYou feel less hungry.");
  target->get_room()->xecho(target, target->get_name() + " eats " + lname + ".");
}

void item::quaff(entity* target)

{
  ////////////////////////////////////////////
  // DON'T DELETE THIS -- FIX IT LATER
  ////////////////////////////////////////////

  if (!find_type(ITEM_POTION)) {
    target->echo("You can't quaff " + lname + ".");
    return; }

  attackinfo AM;

  AM.self_output = "You quaff " + lname + ".";
  AM.targ_output = "\r\n" + target->get_name() + " quaffs " + lname + ".";
  AM.grup_output = "\r\n" + target->get_name() + " quaffs " + lname + ".";
  AM.obsv_output = "\r\n" + target->get_name() + " quaffs " + lname + ".";

  target->get_room()->drop_spell_strings(target, target, AM);
  target->set_silent_cast(1);

  for (int i=0; i<num_spells; i++)
    target->item_cast(spell_list[i], target->get_name());

  target->set_silent_cast(0);
  target->get_room()->echo_strings();
  target->remove_item_inventory(this);
}

void item::refill(string target)

{
  if (!find_type(ITEM_DRINKCONTAINER)) {
    ENT->echo("You can't fill " + lname + ".");
    return; }

  item* ip = ENT->get_room()->find_item_room(target);

  if (ip == NULL) {
    ENT->echo("What do you want to fill " + lname + " with?");
    return; }

  if (!ip->find_type(ITEM_FOUNTAIN)) {
    ENT->echo("You can't fill " + lname + " from " + ip->lname + ".");
    return; }

  if ((liquid_type != ip->liquid_type) && (uses == max_uses)) {
    ENT->echo(capital_lname(lname) + " is already full of " + look_liquid(liquid_type) + "#N.");
    return; }

  if ((liquid_type != ip->liquid_type) && (uses > 0)) {
    ENT->echo(capital_lname(lname) + " still has some " + look_liquid(liquid_type) + " #Nleft in it.");
    return; }

  if (uses == max_uses) {
    ENT->echo(capital_lname(lname) + " is already full.");
    return; }

  uses = max_uses;
  liquid_type = ip->liquid_type;
  ENT->echo("You fill " + lname + " from " + ip->lname + ".",
  ENT->get_name() + " fills " + lname + " from " + ip->lname + ".");
}

void item::empty(entity* target)

{
  if (!find_type(ITEM_DRINKCONTAINER)) {
    target->echo("You can't empty " + lname + ".");
    return; }

  if (uses <= 0) {
    target->echo(name + " is already empty.");
    return; }

  uses = 0;
  liquid_type = "";

  target->echo("You turn " + lname + " over spilling its contents everywhere!",
  target->get_name() + " turns " + lname + " over spilling its contents everywhere!");
}

void item::recite(string* target)

{
  if (!find_type(ITEM_SCROLL)) {
    ENT->echo("You can't recite " + lname + ".");
    return; }

  attackinfo AM;

  AM.self_output = "You recite " + lname + ".";
  AM.targ_output = "\r\n" + ENT->get_name() + " recites " + lname + ".";
  AM.grup_output = "\r\n" + ENT->get_name() + " recites " + lname + ".";
  AM.obsv_output = "\r\n" + ENT->get_name() + " recites " + lname + ".";

  if (num_spells)
  if (!ENT->item_cast_check(spell_list[0], *target)) {
    ENT->get_room()->echo_strings();
    return; }

  ENT->get_room()->drop_spell_strings(ENT, ENT, AM);
  ENT->set_silent_cast(1);

  for (int i=0; i<num_spells; i++)
    ENT->item_cast(spell_list[i], *target);

  ENT->set_silent_cast(0);
  ENT->get_room()->echo_strings();
  ENT->remove_item_inventory(this);
}

void item::use(string* target)

{
  if (!num_charged) {
    ENT->echo("You can't use " + lname + ".");
    return; }

  if (charges_left == 0) {
    ENT->echo("There are no charges left in " + lname + ".");
    return; }

  attackinfo AM;

  AM.self_output = "You uses " + lname + ".";
  AM.targ_output = "\r\n" + ENT->get_name() + " uses " + lname + ".";
  AM.grup_output = "\r\n" + ENT->get_name() + " uses " + lname + ".";
  AM.obsv_output = "\r\n" + ENT->get_name() + " uses " + lname + ".";

  if (!ENT->item_cast_check(charged_list[0], *target)) {
    ENT->get_room()->echo_strings();
    return; }

  ENT->get_room()->drop_spell_strings(ENT, ENT, AM);
  ENT->set_silent_cast(1);

  for (int i=0; i<num_charged; i++)
    ENT->item_cast(charged_list[i], *target);

  ENT->set_silent_cast(0);
  ENT->get_room()->echo_strings();
  charges_left--;
}

void item::recharge()

{
  charges_left = max_charges;
}

// *************************************************************** //
// ************************* Decay Function ********************** //
// *************************************************************** //

void item::decay_object(int audible)

{
  room* rp = NULL;
  string str = capital_lname(lname) + " crumbles into dust.";

  if (find_type(ITEM_EQUIPMENT))

  {
    room* rp = NULL;
    string str = capital_lname(lname) + " crumbles into dust.";

    if (ENT != NULL)

    {
      entity* ep = ENT;
      if (ep->find_item_equipment(name) != NULL)
        ep->unequip(this, 0);

      ep->remove_item_inventory(this);
      rp = ep->get_room();

      if (find_type(ITEM_CONTAINER))
        for (int i=0; i<num_items; i++)
          ep->add_item_inventory(Item_List[i]);
    }

    else if (rm != NULL)

    {
      rp = rm;
      rp->remove_item_room(this);

      if (find_type(ITEM_CONTAINER))
        for (int i=0; i<num_items; i++)
          rp->add_item_room(Item_List[i]);
    }

    else if (parent != NULL)

    {
      parent->remove_item(this);

      for (int i=0; i<num_items; i++)
        parent->add_item(Item_List[i]);
    }

    if (audible)
    if (rp != NULL) rp->echo(str);
    delete this;
  }

  else if (find_type(ITEM_CORPSE))

  {
    room* rp = NULL;

    if (rm != NULL)

    {
      rp = rm;
      rp->remove_item_room(this);

      if (player_corpse)
      for (int i=0; i<num_items; i++)
        rp->add_item_room(Item_List[i]);
    }

    if (audible)
    if (rp != NULL) rp->echo(capital_lname(lname) + " decays into dust.");
    delete this;
  }

  else

  {
    if (ENT != NULL)

    {
      entity* ep = ENT;
      rp = ep->get_room();
      ep->remove_item_inventory(this);

      if (find_type(ITEM_CONTAINER))
        for (int i=0; i<num_items; i++)
          ep->add_item_inventory(Item_List[i]);
    }

    else if (rm != NULL)

    {
      rp = rm;
      rp->remove_item_room(this);

      if (find_type(ITEM_CONTAINER))
        for (int i=0; i<num_items; i++)
          rp->add_item_room(Item_List[i]);
    }

    else if (parent != NULL)

    {
      parent->remove_item(this);

      for (int i=0; i<num_items; i++)
        parent->add_item(Item_List[i]);
    }

    if (audible)
    if (rp != NULL) rp->echo(str);
    delete this;
  }
}

// *************************************************************** //
// ********************** Container Functions ******************** //
// *************************************************************** //

void item::add_item(item* ip)

{
  int position = 0;

  for (int i=0; i<num_items; i++)
  if (Item_List[i]->id == ip->id) {
    position = i;
    i = num_items; }

  for (int i=num_items; i>=0; i--)

  {
    if (i == position) {
      Item_List[i] = ip;
      i = -1; }

    else Item_List[i] = Item_List[i-1];
  }

  num_items++;

  if (ip->find_type(ITEM_CONTAINER)) {
    all_items += ip->all_items + 1;
    ip->parent = this; }

  update();
}

void item::force_add_item(item* ip)

{
  int position = 0;

  for (int i=0; i<num_items; i++)
  if (Item_List[i]->id == ip->id) {
    position = i;
    i = num_items; }

  for (int i=num_items; i>=0; i--)

  {
    if (i == position) {
      Item_List[i] = ip;
      i = -1; }

    else Item_List[i] = Item_List[i-1];
  }

  num_items++;

  if (ip->find_type(ITEM_CONTAINER)) {
    all_items += ip->all_items + 1;
    ip->parent = this; }

  update();
}

void item::remove_item(item* ip)

{
  int position;

  for (int i=0; i<num_items; i++)
    if (Item_List[i] == ip) {
      position = i;
      break; }

  for (int i=position; i<num_items; i++)
    Item_List[i] = Item_List[i+1];

  num_items--;

  if (ip->find_type(ITEM_CONTAINER)) {
    all_items -= ip->all_items - 1;
    ip->parent = NULL; }

  update();
}

item* item::find_item(string str)

{
  int target_num = clip_number(&str);
  if (str.size() < 3) return NULL;

  for (int i=0; i<num_items; i++)

  {
    if (abbreviation(str, lowercase(remove_colors(Item_List[i]->name))))

    {
      target_num--;

      if (!target_num)
        return Item_List[i];
    }
  }

  return NULL;
}

void item::update()

{
  max_level = 0;
  all_items = 0;
  contents_search(Item_List, num_items);
}

void item::contents_search(item* *ilist, int size)

{
  for (int i=0; i<size; i++)

  {
    all_items++;

    if (ilist[i]->level > max_level)
      max_level = ilist[i]->level;

    if (ilist[i]->find_type(ITEM_CONTAINER))
      contents_search(ilist[i]->Item_List, ilist[i]->num_items);
  }
}

int item::can_add(item* ip)

{
  if (this == ip)
    return INSIDE_SELF;

  if (find_type(ITEM_CORPSE))
    return CORPSE;

  if (find_type(ITEM_CONTAINER))
  if ((all_items + ip->all_items + 1) > max_items)
    return CANNOT_FIT;

  if (num_items == max_items)
    return FULL;

  return SUCCESS;
}