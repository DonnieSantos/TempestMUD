#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

void entity::play(string str)

{
  item* ip = find_item_equipment(str);

  if (ip == NULL)
    ip = find_item_inventory(str);

  if (ip == NULL) {
    echo("You can't find that item anywhere.");
    return; }

  if (!ip->PLAYABLE) {
    echo("You can't play " + ip->lname + ".");
    return; }

  if (ip->action_classes[ITEM_ACTION_PLAY].find("All") == string::npos)
  if (ip->action_classes[ITEM_ACTION_PLAY].find(remove_colors(sclassify(get_class()))) == string::npos) {
    echo("Your class can't play " + ip->lname + ".");
    return; }

  if (get_current_mana() < ip->mana_cost[ITEM_ACTION_PLAY]) {
    echo("You don't have enough mana to play " + ip->lname + ".");
    return; }

  ip->play(this);
}

void entity::pull(string str)

{
  item* ip = rm->find_item_room(str);

  if (ip == NULL) {
    echo("You can't find that item anywhere.");
    return; }

  if (!ip->PULLABLE) {
    echo("You can't pull " + ip->lname + ".");
    return; }

  if (ip->action_classes[ITEM_ACTION_PULL].find("All") == string::npos)
  if (ip->action_classes[ITEM_ACTION_PULL].find(remove_colors(sclassify(get_class()))) == string::npos) {
    echo("Your class can't pull " + ip->lname + ".");
    return; }

  if (get_current_mana() < ip->mana_cost[ITEM_ACTION_PULL]) {
    echo("You don't have enough mana to pull " + ip->lname + ".");
    return; }

  ip->pull(this);
}

void entity::recite(string str)

{
  string targ_name;
  string item_name = "";
  string test = first(str);
  string holder = str;

  while (find_item_inventory(test) != NULL) {
    item_name = test;
    holder = last(holder);
    test = item_name + " " + first(holder); }

  targ_name = holder;

  item* ip = find_item_inventory(item_name);

  if (ip == NULL) {
    echo("You don't see that item in your inventory.");
    return; }

  ip->recite(&targ_name);
}

void entity::use(string str)

{
  string targ_name;
  string item_name = "";

  targ_name = clip_end(&str);
  item_name = str;

  if (item_name == "") {
    item_name = targ_name;
    targ_name = ""; }

  item* ip = find_item_equipment(item_name);

  if (ip == NULL) {
    echo("You can't use what you don't have.");
    return; }

  ip->use(&targ_name);
}

void entity::eat(string str)

{
  item* ip = find_item_inventory(str);

  if (ip == NULL) {
    echo("You don't see that item in your inventory.");
    return; }

  ip->eat(this);
}

void entity::drink(string str)

{
  item* ip = find_item_inventory(str);

  if (ip == NULL)

  {
    ip = rm->find_item_room(str);

    if (ip == NULL) {
      echo("You don't see that item in your inventory.");
      return; }
    if (!ip->find_type(ITEM_FOUNTAIN)) {
      echo("That must be in your inventory to drink.");
      return; }
  }

  ip->drink(this);
}

void entity::empty(string str)

{
  item* ip = find_item_inventory(str);

  if (ip == NULL) {
    echo("You don't see that item in your inventory.");
    return; }

  ip->empty(this);
}

void entity::refill(string str)

{
  string targ_name;
  string item_name = "";
  string test = first(str);
  string holder = str;

  while (find_item_inventory(test) != NULL) {
    item_name = test;
    holder = last(holder);
    test = item_name + " " + first(holder); }

  item* ip = find_item_inventory(item_name);

  if (ip == NULL) {
    echo("You don't see that item in your inventory.");
    return; }

  ip->refill(holder);
}

item* entity::find_container(string cname)

{
  item* *ilist = new item* [rm->get_num_items() + num_items];
  int target_num = clip_number(&cname);
  int count = 0;

  for (int i=0; i<num_items; i++)
    if (abbreviation(cname, remove_colors(Item_List[i]->name)))
      if (Item_List[i]->find_type(ITEM_CONTAINER))
        ilist[count++] = Item_List[i];

  for (int i=0; i<rm->get_num_items(); i++)
    if (abbreviation(cname, remove_colors(rm->get_item(i)->name)))
      if (rm->get_item(i)->find_type(ITEM_CONTAINER))
        ilist[count++] = rm->get_item(i);

  if (count >= target_num)
    return ilist[target_num-1];

  return NULL;
}

void entity::put(string str)

{
  string cname = clip_end(&str);
  item* cp = find_container(cname);
  item* *ilist = new item* [num_items];
  int count = 0;

  if (cp == NULL) {
    echo("You can't find that container anywhere.");
    return; }

  if (num_items < 0) {
    echo("You don't have any items in your inventory.");
    return; }

  if (cp->find_type(ITEM_CORPSE)) {
    echo("You cannot put items inside a corpse.");
    return; }

  string backup = str;
  if (clip_end(&backup) == "in")
    clip_end(&str);

  if (str == "all")

  {
    for (int i=0; i<num_items; i++)
      if (Item_List[i] != cp)
        ilist[count++] = Item_List[i];
  }

  else if (str.substr(0, 4) == "all.")

  {
    str = str.substr(4, str.size()-4);

    for (int i=0; i<num_items; i++)
      if (abbreviation(str, remove_colors(Item_List[i]->name)))
        ilist[count++] = Item_List[i];
  }

  else

  {
    ilist[0] = find_item_inventory(str);
    if (ilist[0] != NULL) count = 1;
  }

  if (count == 0) {
    echo("You don't have that item.");
    return; }

  int rv;
  string output_me = "";
  string output_you = "";

  for (int i=0; i<count; i++)

  {
    rv = cp->can_add(ilist[i]);

    if (rv == SUCCESS) {
      remove_item_inventory(ilist[i]);
      cp->add_item(ilist[i]); }

    create_container_put_output(rv, ilist[i], cp, &output_me, &output_you);

    if (rv == FULL)
      break;
  }

  output_me.erase(0, 2); output_you.erase(0, 2);
  if (output_you == "") echo(string_stacker(output_me));
  else echo(string_stacker(output_me), string_stacker(output_you));
}

void entity::create_container_put_output(int rv, item* ip, item* cp, string* output_me, string* output_you)

{
  if (rv == INSIDE_SELF)
    *output_me += "\r\nYou can't put an item inside itself.";
  else if (rv == SUCCESS) {
    *output_me  += "\r\nYou put " + ip->lname + " into " + cp->lname + ".";
    *output_you += "\r\n" + get_name() + " puts " + ip->lname + " into " + cp->lname + "."; }
  else if (rv == FULL)
    *output_me += "\r\n" + cp->name + " is full.";
  else if (rv == CANNOT_FIT)
    *output_me += "\r\n" + ip->name + " contains too many items to put into " + cp->lname + ".";
  else if (rv == CORPSE)
    *output_me += "\r\nYou can't put a corpse inside " + cp->lname + ".";
}

void entity::get_all_container(string str)

{
  item* *c_list = new item* [rm->get_num_items() + num_items];
  int count = 0;

  string cname = clip_end(&str);
  cname.erase(0, 4);

  for (int i=0; i<num_items; i++)
    if (abbreviation(cname, remove_colors(Item_List[i]->name)))
      if (Item_List[i]->find_type(ITEM_CONTAINER))
        c_list[count++] = Item_List[i];

  for (int i=0; i<rm->get_num_items(); i++)
    if (abbreviation(cname, remove_colors(rm->get_item(i)->name)))
      if (rm->get_item(i)->find_type(ITEM_CONTAINER))
        c_list[count++] = rm->get_item(i);

  string backup = str;
  if (clip_end(&backup) == "from")
    clip_end(&str);

  int rv;
  int items_got = 0;
  string output_me = "";
  string output_you = "";

  for (int i=0; i<count; i++)

  {
    int item_count = 0;
    item* *ilist = new item* [c_list[i]->num_items];

    if (str == "all")
    for (int j=0; j<c_list[i]->num_items; j++)
      ilist[item_count++] = c_list[i]->Item_List[j];

    else if (str.substr(0, 4) == "all.")

    {
      string item_name = str.substr(4, str.size()-4);
      for (int j=0; j<c_list[i]->num_items; j++)
        if (abbreviation(item_name, remove_colors(c_list[i]->Item_List[j]->name)))
          ilist[item_count++] = c_list[i]->Item_List[j];
    }

    else

    {
      ilist[0] = c_list[i]->find_item(str);
      if (ilist[0] != NULL) item_count = 1;
    }

    items_got += item_count;

    for (int j=0; j<item_count; j++)

    {
      rv = can_add_inventory(ilist[j]);

      if (rv == SUCCESS) {
        c_list[i]->remove_item(ilist[j]);
        add_item_inventory(ilist[j]); }

      create_container_get_output(rv, ilist[j], c_list[i], &output_me, &output_you);

      if (rv == FULL) break;
    }
  }

  if (items_got == 0) {
    echo("You can't find that in any container.");
    return; }

  output_me.erase(0, 2);
  output_you.erase(0, 2);
  if (output_you == "") echo(string_stacker(output_me));
  else echo(string_stacker(output_me), string_stacker(output_you));
}

int entity::refers_room(string str)

{
  if (str == "all")
    return 1;

  if (str.size() > 4)
  if (str.substr(0, 4) == "all.")

  {
    str.erase(0, 4);

    for (int i=0; i<rm->get_num_items(); i++)
      if (abbreviation(str, remove_colors(rm->get_item(i)->name)))
        return 1;
  }

  if (rm->find_item_room(str))
    return 1;

  return 0;
}

void entity::get(string str)

{
  if (refers_room(str)) {
    rm->get_room(this, str);
    return; }

  string backup = str;
  string last_word = clip_end(&backup);
  if (last_word.size() > 4)
  if (last_word.substr(0, 4) == "all.") {
    get_all_container(str);
    return; }

  item* container = find_container(last_word);

  if (container == NULL) {
    echo("You can't find that anywhere.");
    return; }

  item* *ilist = new item* [container->num_items];
  int count = 0;

  if (backup == "all")
  for (int i=0; i<container->num_items; i++)
    ilist[count++] = container->Item_List[i];
  else if (backup.substr(0, 4) == "all.")

  {
    backup.erase(0, 4);
    for (int i=0; i<container->num_items; i++)
      if (abbreviation(backup, remove_colors(container->Item_List[i]->name)))
        ilist[count++] = container->Item_List[i];
  }

  else

  {
    ilist[count] = container->find_item(backup);
    if (ilist[0] != NULL) count++;
  }

  if (count == 0) {
    echo("You don't see anything like that in " + container->lname + ".");
    return; }

  int rv;
  string output_me = "";
  string output_you = "";

  for (int i=0; i<count; i++)

  {
    rv = can_add_inventory(ilist[i]);

    if (rv == SUCCESS) {
      container->remove_item(ilist[i]);
      add_item_inventory(ilist[i]); }

    create_container_get_output(rv, ilist[i], container, &output_me, &output_you);

    if (rv == FULL) break;
  }

  output_me.erase(0, 2);
  output_you.erase(0, 2);
  if (output_you == "") echo(string_stacker(output_me));
  else echo(string_stacker(output_me), string_stacker(output_you));
}

int entity::can_add_inventory(item* ip)

{
  if (ip->find_type(ITEM_CONTAINER))

  {
    if (get_total_items() + ip->all_items + 1 > MAX_NUM_ITEMS)
      return CANNOT_FIT;

    if (ip->max_level > get_level())
      return CONTAINS_HIGH_LEVEL;
  }

  if (ip->find_type(ITEM_CORPSE))
    if (get_level() < 100)
      return CORPSE;

  if (get_level() < 100)
    if (ip->find_flag(FLAG_NOGET))
      return NO_GET;

  if (ip->level > get_level())
    return HIGH_LEVEL;

  if (num_items == MAX_NUM_ITEMS)
    return FULL;

  return SUCCESS;
}

void entity::create_container_get_output(int rv, item* ip, item* cp, string* output_me, string* output_you)

{
  if (rv == SUCCESS) {
    *output_me  += "\r\nYou get " + ip->lname + " from " + cp->lname + ".";
    *output_you += "\r\n" + get_name() + " gets " + ip->lname + " from " + cp->lname + "."; }
  else if (rv == HIGH_LEVEL)
    *output_me += "\r\n" + ip->name + " is too high of a level for you.";
  else if (rv == CONTAINS_HIGH_LEVEL)
    *output_me += "\r\n" + ip->name + " contains too high of a level item for you.";
  else if (rv == CANNOT_FIT)
    *output_me += "\r\n" + ip->name + " contains too many items for you to get.";
  else if (rv == FULL)
    *output_me += "\r\nYour inventory is full.";
}

void room::get_room(entity* target, string str)

{
  item* *ilist = new item* [num_items];
  int count = 0;
  int get_all = 0;

  if (str == "all")

  {
    get_all = 1;

    for (int i=0; i<num_items; i++)
      ilist[count++] = Item_List[i];
  }

  else if (str.substr(0, 4) == "all.")

  {
    str.erase(0, 4);

    for (int i=0; i<num_items; i++)
      if (abbreviation(str, remove_colors(Item_List[i]->name)))
        ilist[count++] = Item_List[i];
  }

  else

  {
    ilist[0] = find_item_room(str);
    if (ilist[0] != NULL) count = 1;
  }

  int new_count = 0;
  item* *list = new item* [count];

  for (int i=0; i<count; i++)
    if (!ilist[i]->find_flag(FLAG_UNTOUCHABLE))
      list[new_count++] = ilist[i];

  if (new_count == 0) {
    target->echo("You don't see that item anywhere.");
    return; }

  int rv;
  string output_me = "";
  string output_you = "";

  for (int i=0; i<new_count; i++)

  {
    rv = target->can_add_inventory(list[i]);

    if (rv == SUCCESS) {
      if (get_all) target->insert_item_inventory(list[i], i);
      else target->add_item_inventory(list[i]);
      remove_item_room(list[i]); }

    create_room_get_output(rv, list[i], target, &output_me, &output_you);

    if (rv == FULL) break;
  }

  output_me.erase(0, 2);
  output_you.erase(0, 2);
  if (output_you == "") target->echo(string_stacker(output_me));
  else target->echo(string_stacker(output_me), string_stacker(output_you));
}

void room::create_room_get_output(int rv, item* ip, entity* target, string* output_me, string* output_you)

{
  if (rv == SUCCESS) {
    *output_me  += "\r\nYou get " + ip->lname + ".";
    *output_you += "\r\n" + target->get_name() + " gets " + ip->lname + "."; }
  else if (rv == HIGH_LEVEL)
    *output_me += "\r\n" + proper_name(ip->name) + " is too high of a level for you.";
  else if (rv == CONTAINS_HIGH_LEVEL)
    *output_me += "\r\n" + proper_name(ip->name) + " contains too high of a level item for you.";
  else if (rv == CANNOT_FIT)
    *output_me += "\r\n" + proper_name(ip->name) + " contains too many items for you to get.";
  else if (rv == CORPSE)
    *output_me += "\r\nYou can't pick up a corpse.";
  else if (rv == NO_GET)
    *output_me += "\r\nYou can't pick " + ip->lname + " up.";
  else if (rv == FULL)
    *output_me += "\r\nYour inventory is full.";
}

void entity::drop(string str)

{
  item* *ilist;
  int count = 0;

  if (determine_gold_string(str)) {
    gold_drop(str);
    return; }

  str = key(str);

  if (num_items <= 0) {
    echo("You have nothing to drop.");
    return; }

  ilist = new item* [num_items];

  if (str == "all")
  for (int i=0; i<num_items; i++)
    ilist[count++] = Item_List[i];

  else if (str.substr(0, 4) == "all.")

  {
    str.erase(0, 4);

    for (int i=0; i<num_items; i++)
      if (abbreviation(str, remove_colors(Item_List[i]->name)))
        ilist[count++] = Item_List[i];
  }

  else

  {
    ilist[0] = find_item_inventory(str);
    if (ilist[0] != NULL) count = 1;
  }

  if (count == 0) {
    echo("You don't have that item to drop.");
    return; }

  int rv;
  string output_me  = "";
  string output_you = "";

  for (int i=0; i<count; i++)

  {
    rv = can_drop_room(ilist[i]);

    if (rv == SUCCESS) {
      remove_item_inventory(ilist[i]);
      rm->add_item_room(ilist[i]); }

    create_room_drop_output(rv, ilist[i], &output_me, &output_you);
  }

  output_me.erase(0, 2);
  output_you.erase(0, 2);
  if (output_you == "") echo(string_stacker(output_me));
  else echo(string_stacker(output_me), string_stacker(output_you));
}

int entity::can_drop_room(item* ip)

{
  if (get_level() < 100)
    if (ip->find_flag(FLAG_NODROP))
      return CANT_DROP;

  return SUCCESS;
}

void entity::create_room_drop_output(int rv, item* ip, string* output_me, string* output_you)

{
  if (rv == SUCCESS) {
    *output_me  += "\r\nYou drop " + ip->lname + ".";
    *output_you += "\r\n" + get_name() + " drops " + ip->lname + "."; }
  if (rv == CANT_DROP)
    *output_me += "\r\nYou can't seem to drop " + ip->lname + ".";
}

void entity::give(string str)

{
  string item_name;
  string targ_name;
  entity* target;
  item* *ilist = new item* [num_items];
  int count = 0;

  if (determine_gold_string(str)) {
    gold_give(str);
    return; }

  item_name = str;
  targ_name = clip_end(&item_name);

  string backup = item_name;
  if (clip_end(&backup) == "to")
    clip_end(&item_name);

  target = rm->find_entity(this, targ_name);

  if (target == NULL) {
    echo("You don't see that person.");
    return; }

  if (target == this) {
    echo("You can't give yourself an item.");
    return; }

  if (item_name == "all")
    for (count=0; count<num_items; count++)
      ilist[count] = Item_List[count];

  else if (item_name.size() > 4)
  if (item_name.substr(0, 4) == "all.")

  {
    item_name.erase(0, 4);
    for (int i=0; i<num_items; i++)
      if (abbreviation(item_name, remove_colors(Item_List[i]->name)))
        ilist[count++] = Item_List[i];
  }

  if (count == 0) {
    ilist[0] = find_item_inventory(item_name);
    count = 1; }

  if ((count == 0) || (ilist[0] == NULL)) {
    echo("You don't have that item in your inventory.");
    return; }

  int rv;
  string output_me = "";
  string output_you = "";
  string output_rm = "";

  for (int i=0; i<count; i++)

  {
    rv = target->can_add_inventory_get(ilist[i]);

    if (rv == SUCCESS) {
      remove_item_inventory(ilist[i]);
      target->add_item_inventory(ilist[i]); }

    create_give_output(rv, ilist[i], target, &output_me, &output_you, &output_rm);

    if (rv == FULL) break;
  }

  output_me.erase(0, 2);
  output_you.erase(0, 2);
  output_rm.erase(0, 2);

  if (output_you == "")
    echo(string_stacker(output_me));

  else

  {
    echo(string_stacker(output_me));
    target->echo(string_stacker(output_you));
    rm->xecho(target, this, string_stacker(output_rm));
  }
}

int entity::can_add_inventory_get(item* ip)

{
  if (ip->find_type(ITEM_CONTAINER))

  {
    if (get_total_items() + ip->all_items + 1 > MAX_NUM_ITEMS)
      return CANNOT_FIT;

    if (ip->max_level > get_level())
      return CONTAINS_HIGH_LEVEL;
  }

  if (ip->find_type(ITEM_CORPSE))
    if (get_level() < 100)
      return CORPSE;

  if (ip->find_flag(FLAG_NODROP))
    if (get_level() < 100)
      return CANT_DROP;

  if (ip->level > get_level())
    return HIGH_LEVEL;

  if (num_items == MAX_NUM_ITEMS)
    return FULL;

  return SUCCESS;
}

void entity::create_give_output(int rv, item* ip, entity* target, string* output_me, string* output_you, string* output_rm)

{
  if (rv == SUCCESS) {
    *output_me += "\r\nYou give " + target->get_name() + " " + ip->lname + ".";
    *output_you += "\r\n" + get_name() + " gives you " + ip->lname + ".";
    *output_rm += "\r\n" + get_name() + " gives " + target->get_name() + " " + ip->lname + "."; }
  else if (rv == FULL)
    *output_me += "\r\n" + possessive(target->get_name()) + " inventory is full.";
  else if (rv == CANNOT_FIT)
    *output_me += "\r\n" + possessive(target->get_name()) + " inventory does not have the capacity for that item.";
  else if (rv == CONTAINS_HIGH_LEVEL)
    *output_me += "\r\n" + ip->lname + " contains an item too high of a level for " + target->get_name() + ".";
  else if (rv == HIGH_LEVEL)
    *output_me += "\r\n" + ip->lname + " is too high of a level for " + target->get_name() + ".";
  else if (rv == CANT_DROP)
    *output_me += "\r\nYou can't let go of " + ip->lname + ".";
}

void entity::sacrifice_item(string str)

{
  item* *ilist = new item* [num_items + rm->get_num_items()];
  int count = 0;
  int in_room = 0;

  if (str == "all") {
    echo("You can't sacrifice all items in the room or your inventory.");
    return; }

  if (str.size() > 4)
  if (str.substr(0, 4) == "all.")

  {
    str.erase(0, 4);
    for (int i=0; i<num_items; i++)
      if (abbreviation(str, remove_colors(Item_List[i]->name)))
        ilist[count++] = Item_List[i];

    if (!count)

    {
      in_room = 1;
      for (int i=0; i<rm->get_num_items(); i++)
        if (abbreviation(str, remove_colors(rm->get_item(i)->name)))
          ilist[count++] = rm->get_item(i);
    }
  }

  if (!count)

  {
    ilist[0] = find_item_inventory(str);

    if (ilist[0] == NULL) {
      ilist[0] = rm->find_item_room(str);
      in_room = 1; }

    if (ilist[0] != NULL)
      count++;
  }

  int new_count = 0;
  item* *list = new item* [count];

  for (int i=0; i<count; i++)
    if (!ilist[i]->find_flag(FLAG_UNTOUCHABLE))
      list[new_count++] = ilist[i];

  if (!new_count) {
    echo("You can't find that item anywhere.");
    return; }

  int rv;
  string output_me = "";
  string output_you = "";

  for (int i=0; i<new_count; i++)

  {
    rv = sacrifice_item(list[i]);

    if (rv == SUCCESS) {
      if (in_room) rm->remove_item_room(list[i]); else remove_item_inventory(list[i]);
      if (World->find_decaylist(DC_Decay(list[i]))) World->remove_decaylist(DC_Decay(list[i]));
      output_me  += "\r\nYou sacrifice " + list[i]->lname + " to Genevieve.";
      output_you += "\r\n" +  get_name() + " sacrifices " + list[i]->lname + " to Genevieve."; }
    else if (rv == BOARD)
      output_me += "\r\nYou cannot sacrifice a message board.";
    else if (rv == CONTAINS_ITEMS)
      output_me += "\r\nYou cannot sacrifice a container with items inside.";
    else if (rv == PLAYER_CORPSE)
      output_me += "\r\nYou cannot sacrifice a player's corpse.";
    else if (rv == CANNOT_SACRIFICE)
      output_me += "\r\nYou cannot sacrifice that.";
  }

  output_me.erase(0, 2); output_you.erase(0, 2);
  if (output_you == "") echo(string_stacker(output_me));
  else echo(string_stacker(output_me), string_stacker(output_you));
}

int entity::sacrifice_item(item* ip)

{
  if (ip->find_flag(FLAG_NOSACRIFICE))
    return CANNOT_SACRIFICE;

  if (ip->find_type(ITEM_BOARD))
    if (get_level() < 100)
      return BOARD;

  if (ip->find_type(ITEM_CONTAINER))
    if (ip->player_corpse)
      if (ip->num_items != 0)
        return CONTAINS_ITEMS;

  if (ip->find_type(ITEM_CORPSE))
  if (ip->player_corpse)
    return PLAYER_CORPSE;

  return SUCCESS;
}

void entity::display_inventory()

{
  string str;

  str = "Your inventory contains:";

  if (num_items == 0) str += "\r\n  Nothing.";
  else str += stack_items(Item_List, num_items, 1);

  echo(str);
}

item* entity::find_item_all(string str)

{
  item* *ilist = new item* [rm->get_num_items() + num_items];
  int target_num = clip_number(&str);
  int count = 0;

  for (int i=0; i<num_items; i++)
    if (abbreviation(str, remove_colors(Item_List[i]->name)))
      ilist[count++] = Item_List[i];

  for (int i=0; i<rm->get_num_items(); i++)
    if (abbreviation(str, remove_colors(rm->get_item(i)->name)))
      ilist[count++] = rm->get_item(i);

  if (count >= target_num)
    return ilist[target_num-1];

  return NULL;
}

void entity::display_item_container(string str)

{
  string output;

  item* cp = find_item_all(str);

  if (cp == NULL) {
    echo("You can't find that anywhere.");
    return; }

  if (cp->find_type(ITEM_DRINKCONTAINER)) {
    echo(get_liquid_amount(cp));
    return; }

  if (cp->find_type(ITEM_FOUNTAIN)) {
    echo(capital_lname(cp->lname) + " is flowing with " + look_liquid(cp->liquid_type) + ".");
    return; }

  if (!cp->find_type(ITEM_CONTAINER)) {
    echo("You can't look inside that.");
    return; }

  output = cp->lname + " contains:";
  if (cp->num_items == 0) output += "\r\n  Nothing.";
  else output += stack_items(cp->Item_List, cp->num_items, 1);

  echo(output);
}

string room::display_item_list()

{
  string str;
  int count = 0;
  item* *list = new item* [num_items];

  for (int i=0; i<num_items; i++)
    if (!Item_List[i]->find_flag(FLAG_UNTOUCHABLE))
      list[count++] = Item_List[i];

  if (num_items == 0) return "";
  else str = stack_items(list, count);

  return str;
}

void entity::add_item_inventory(item* ip)

{
  if (ip->find_type(ITEM_GOLD)) {
    set_gold(get_gold() + ip->amount);
    return; }

  if (get_level() >= 100)
    if (World->find_decaylist(DC_Decay(ip)))
      World->remove_decaylist(DC_Decay(ip));

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

  ip->ENT = this;
  num_items++;
}

void entity::insert_item_inventory(item* ip, int position)

{
  if (ip->find_type(ITEM_GOLD)) {
    set_gold(get_gold() + ip->amount);
    return; }

  if (get_level() >= 100)
    if (World->find_decaylist(DC_Decay(ip)))
      World->remove_decaylist(DC_Decay(ip));

  for (int i=num_items; i>position; i--)
    Item_List[i] = Item_List[i-1];

  Item_List[position] = ip;

  ip->ENT = this;
  num_items++;
}

void entity::append_item_inventory(item* ip)

{
  if (ip->find_type(ITEM_GOLD)) {
    set_gold(get_gold() + ip->amount);
    return; }

  if (get_level() >= 100)
    if (World->find_decaylist(DC_Decay(ip)))
      World->remove_decaylist(DC_Decay(ip));

  ip->ENT = this;
  Item_List[num_items++] = ip;
}

void entity::remove_item_inventory(item* item_pointer)

{
  int position;

  for (int i=0; i<num_items; i++)
  if (Item_List[i] == item_pointer) {
    position = i;
    break; }

  num_items--;

  for (int i=position; i<num_items; i++)
    Item_List[i] = Item_List[i+1];

  item_pointer->ENT = NULL;
}

item* entity::find_item_inventory(string str)

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

int room::add_item_room(item *item_pointer)

{
  int found = 0;
  int position = -1;

  for (int i=0; i<num_items; i++)
  if (Item_List[i]->id == item_pointer->id) {
    position = i;
    i = num_items; }

  num_items++;
  item* *temp = new item* [num_items];

  if (position != -1)

  {
    for (int i=0; i<num_items; i++) {
      if (i == position) found = 1;
      else temp[i] = Item_List[i-found]; }

    temp[position] = item_pointer;
  }

  else

  {
    for (int i=0; i<num_items-1; i++)
      temp[i] = Item_List[i];

    temp[num_items-1] = item_pointer;
  }

  delete [] Item_List;
  Item_List = temp;

  item_pointer->rm = this;

  if (item_pointer->find_type(ITEM_CORPSE))
    if (!World->find_decaylist(item_pointer))
      World->add_decaylist(item_pointer);

  return SUCCESS;
}

void room::remove_item_room(item *item_pointer)

{
  int found = 0;
  item* *temp = new item* [num_items-1];

  for (int i=0; i<num_items; i++)
    if (Item_List[i] != item_pointer)
      temp[i-found] = Item_List[i];
    else found = 1;

  delete [] Item_List;
  Item_List = temp;
  num_items--;

  item_pointer->rm = NULL;
}

item* room::find_item_room(string str)

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

string room::stack_items(item* *list, int size)

{
  string str = "";
  int* count;
  int tempsize, position;
  item* *temp2;
  item* *temp = new item*[size];

  for (int i=0; i<size; i++)
    temp[i] = list[i];

  while (size > 0)

  {
    count = new int [size];
    tempsize = 0;
    position = 0;

    for (int i=0; i<size; i++) count[i] = 0;

    for (int i=0; i<size; i++)
    if (temp[0]->name == temp[i]->name) {
      count[i]++;
      tempsize++; }

    if (size-tempsize > 0)

    {
      temp2 = new item* [size-tempsize];

      for (int i=0; i<size; i++)
      if (count[i] == 0) {
        temp2[position] = temp[i];
        position++; }
    }

    if (tempsize > 1) {
      if (tempsize < 10) str += "\r\n#N[ #Y" + intconvert(tempsize) + "#N] #M";
      else str += "\r\n#N[#Y" + intconvert(tempsize) + "#N] #M";
      str += temp[0]->ground_desc + "#N"; }

    else str += "\r\n#M" + temp[0]->ground_desc + "#N";

    size = size - tempsize;
    temp = temp2;
  }

  return str;
}

string entity::stack_items(item* *list, int size, int capital)

{
  string str = "";
  int* count;
  int tempsize, position;
  item* *temp2;
  item* *temp = new item*[size];

  for (int i=0; i<size; i++)
    temp[i] = list[i];

  while (size > 0)

  {
    count = new int [size];
    tempsize = 0;
    position = 0;

    for (int i=0; i<size; i++)
      count[i] = 0;

    for (int i=0; i<size; i++)
    if (temp[0]->name == temp[i]->name) {
      count[i]++;
      tempsize++; }

    if (size-tempsize > 0)

    {
      temp2 = new item* [size-tempsize];

      for (int i=0; i<size; i++)
      if (count[i] == 0) {
        temp2[position] = temp[i];
        position++; }
     }

    if (tempsize > 1) {
      if (tempsize < 10) str += "\r\n  #N[#Y " + intconvert(tempsize) + "#N] ";
      else str += "\r\n  #N[#Y" + intconvert(tempsize) + "#N] ";
      if (capital) str += temp[0]->name + "#N";
      else str += temp[0]->lname + "#N"; }

    else {
    if (capital) str += "\r\n  #N" + temp[0]->name + "#N";
    else str += "\r\n  #N" + temp[0]->lname + "#N"; }

    size = size - tempsize;
    temp = temp2;
  }

  return str;
}

void entity::num_item_info()

{
  echo("You have " + intconvert(get_total_items()) + " items.");
}

int entity::get_total_items()

{
  int total_items = 0;
  item* temp;

  for (int i=0; i<16; i++)

  {
    temp = *ITEMS_EQUIPPED[i];

    if (temp != NULL)
      if (temp->find_type(ITEM_CONTAINER))
        total_items += temp->all_items;
  }

  for (int i=0; i<num_items; i++)
    if (Item_List[i]->find_type(ITEM_CONTAINER))
      total_items += Item_List[i]->all_items;

  return num_items + num_items_equipped + total_items;
}

void entity::create_corpse()

{
  itemlist *itmlst = World->get_itemlist();
  item* cor = itmlst->replicate(666, possessive(name) + " corpse.");

  cor->name          =   "#R" + possessive(name) + " corpse#N";
  cor->lname         =   "#R" + possessive(name) + " corpse#N";
  cor->look_desc     =   "#NThe corpse of " + name + ".#N";
  cor->ground_desc   =   "#R" + possessive(name) + " corpse lies here on the ground.#N";

  if (PLAYER) { cor->decay_time = 15;               cor->player_corpse = 1; }
  else        { cor->decay_time = random_int(3, 6); cor->player_corpse = 0; }

  World->add_decaylist(cor);

  if (info != NULL)
  if (info->NPC) {
    rm->add_item_room(cor);
    return; }

  if (get_gold() > 0) {
    cor->force_add_item(create_gold_pile(get_gold()));
    set_gold(0); }

  if (PLAYER)

  {
    item* *plist = new item* [get_total_items()];
    int count = 0;
    perish_itemlist(Item_List, num_items, plist, &count);
    perish_itemlist(*ITEMS_EQUIPPED, num_items_equipped, plist, &count);

    for (int i=0; i<count; i++)
      plist[i]->decay_object(0);
  }

  for (int i=0; i<num_items; i++)
    cor->add_item(Item_List[i]);

  while (num_items > 0)
    remove_item_inventory(Item_List[0]);

  item* temp;

  for (int i=0; i<16; i++)
  if ((*ITEMS_EQUIPPED[i]) != NULL)

  {
    temp = (*ITEMS_EQUIPPED[i]);

    if (temp != NULL) {
      unequip(temp, 0);
      remove_item_inventory(temp);
      cor->force_add_item(temp); }
  }

  rm->add_item_room(cor);
}

void entity::perish_itemlist(item* *ilist, int size, item* *plist, int* count)

{
  for (int i=0; i<size; i++)

  {
    if (ilist[i] != NULL)

    {
      if (ilist[i]->find_flag(FLAG_PERISH))
        plist[(*count)++] = ilist[i];

      if (ilist[i]->find_type(ITEM_CONTAINER))
        perish_itemlist(ilist[i]->Item_List, ilist[i]->num_items, plist, count);
    }
  }
}

item* entity::create_gold_pile(int amount)

{
  itemlist *itmlst = World->get_itemlist();
  item* gp = itmlst->replicate(1, "Dropped by " + name + ".");
  gp->amount = amount;

  if (amount >= 2) {
    gp->name = "some #ygold coins#N";
    gp->lname = "some #ygold coins#N";
    gp->ground_desc = "A few shiny gold coins have been left here.";
    gp->look_desc = rm_allign("A few shiny gold coins."); }

  if (amount >= 100) {
    gp->name = "a small #ypile of gold#N";
    gp->lname = "a small #ypile of gold#N";
    gp->look_desc = rm_allign("A small pile of gold coins.");
    gp->ground_desc = "A small pile of gold coins has been left here."; }

  if (amount >= 1000) {
    gp->name = "a #ypile of gold#N";
    gp->lname = "a #ypile of gold#N";
    gp->look_desc = rm_allign("A pile of gold!");
    gp->ground_desc = "A pile of gold coins has been left here."; }

  if (amount >= 10000) {
    gp->name = "a large #ypile of gold#N";
    gp->lname = "a large #ypile of gold#N";
    gp->look_desc = rm_allign("A large pile of gold!");
    gp->ground_desc = " A large pile of gold coins has been left here."; }

  if (amount >= 100000) {
    gp->name = "a huge #ypile of gold#N";
    gp->lname = "a huge #ypile of gold#N";
    gp->look_desc = rm_allign("A pile of gold!");;
    gp->ground_desc = "A huge pile of gold coins has been left here."; }

  if (amount >= 1000000) {
    gp->name = "a #ymountain of gold#N";
    gp->lname = "a #ymountain of gold#N";
    gp->look_desc = rm_allign("A towering mountain of gold coins!");
    gp->ground_desc = "A mountain of gold coins has been left here."; }

  return gp;
}

void entity::gold_drop(string str)

{
  int amount = stringconvert(key(str));

  if (amount > get_gold()) {
    echo("You don't have that much gold to drop.");
    return; }

  rm->add_item_room(create_gold_pile(amount));
  set_gold(get_gold() - amount);

  echo("You drop " + intconvert(amount) + " gold.", get_name() + " drops " + intconvert(amount) + " gold.");
}

void entity::gold_give(string str)

{
  entity* target = rm->find_entity(clip_end(&str));
  int amount = stringconvert(key(str));

  if (target == this) {
    echo("That would do you alot of good.");
    return; }

  if (target == NULL) {
    echo("You don't see that person anywhere.");
    return; }

  if (amount > get_gold()) {
    echo("You don't have that much gold.");
    return; }

  if (amount == 0) {
    echo("You can't give zero gold.");
    return; }

  target->set_gold(target->get_gold() + amount);
  set_gold(get_gold() - amount);
  echo("You give " + target->get_name() + " " + intconvert(amount) + " gold.");
  target->echo("\r\n" + get_name() + " gives you " + intconvert(amount) + " gold.");
  rm->xecho(target, this, get_name() + " gives " + target->get_name() + " " + intconvert(amount) + " gold.");
}

void entity::pfile_inventory_traverse(item* *ilist, int size, string* str, int level, int EQ)

{
  for (int i=0; i<size; i++)

  {
    *str += "\r\nIDNUM: " + intconvert(ilist[i]->id) + " " + intconvert(EQ) + " " + intconvert(level);

    if (!EQ) *str += "\r\nPLACE: None";
    else if (level > 0) *str += "\r\nPLACE: None";
    else *str += "\r\nPLACE: " + get_places(ilist[i]);

    *str += "\r\nSTAMP: " + ilist[i]->TIME_STAMP;
    *str += "\r\nCTYPE: " + ilist[i]->TYPE_STAMP;
    *str += "\r\nCHRGS: " + intconvert(ilist[i]->charges_left);
    *str += "\r\nLTYPE: " + ilist[i]->liquid_type;
    *str += "\r\nLUSES: " + intconvert(ilist[i]->uses);
    *str += "\r\n";

    if (ilist[i]->find_type(ITEM_CONTAINER))
      pfile_inventory_traverse(ilist[i]->Item_List, ilist[i]->num_items, str, level+1, EQ);
  }
}

string entity::pfile_item_information()

{
  string str = "";

  pfile_inventory_traverse(Item_List, num_items, &str, 0, 0);

  return str;
}