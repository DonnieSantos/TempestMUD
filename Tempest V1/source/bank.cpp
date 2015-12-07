#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

item* entity::find_item_bank(string str)

{
  int target_num = clip_number(&str);
  if (str.size() < 3) return NULL;

  for (int i=0; i<bank_num_items; i++)
  if (lowercase(remove_colors(bank_item_list[i]->name)).find(str) != string::npos)

  {
    target_num--;

    if (!target_num)
      return bank_item_list[i];
  }

  return NULL;
}

void entity::add_item_bank(item* item_pointer)

{
  bank_item_list[bank_num_items] = item_pointer;
  bank_num_items++;
}

void entity::remove_item_bank(item* item_pointer)

{
  int position;

  for (int i=0; i<bank_num_items; i++)
    if (bank_item_list[i] == item_pointer) {
      position = i;
      break; }

  bank_num_items--;

  for (int i=position; i<bank_num_items; i++)
    bank_item_list[i] = bank_item_list[i+1];
}

string entity::deposit(string str)

{
  if (determine_gold_string(str))
    return deposit_gold(str);

  item* item_pointer = find_item_inventory(str);

  if (item_pointer == NULL)
    return "You aren't carrying anything like that.";

  if (bank_num_items >= 200)
    return "Your bank account cannot hold any more items.";

  if (item_pointer->find_type(ITEM_CONTAINER))
    return "I'm sorry, we can't hold containers for you.";

  remove_item_inventory(item_pointer);
  add_item_bank(item_pointer);
  echo("\r\nYou deposit " + item_pointer->lname + ".");
  rm->xecho(this, name + " deposits an item.");
  return "";
}

string entity::withdraw(string str)

{
  if (determine_gold_string(str))
    return withdraw_gold(str);

  item* item_pointer = find_item_bank(str);

  if (item_pointer == NULL)
    return "You haven't got anything like that in your account.";

  if (get_total_items() >= 100)
    return "You cannot hold any more items.";

  remove_item_bank(item_pointer);
  add_item_inventory(item_pointer);
  echo("\r\nYou withdraw " + item_pointer->lname + ".");
  rm->xecho(this, name + " withdraws an item.");
  return "";
}

string entity::deposit_gold(string str)

{
  int amount = stringconvert(key(str));

  if (amount > get_gold())
    return "You do not have that much gold on you.";

  bank_gold += amount;
  set_gold(get_gold() - amount);

  echo("\r\nYou deposit " + key(str) + " gold.");
  rm->xecho(this, name + " deposits some gold.");
  return "";
}

string entity::withdraw_gold(string str)

{
  int amount = stringconvert(key(str));

  if (amount > bank_gold)
    return "Your account does not have that much in it.";

  bank_gold -= amount;
  set_gold(get_gold() + amount);

  echo("\r\nYou withdraw " + key(str) + " gold.");
  rm->xecho(this, name + " withdraws some gold.");
  return "";
}

void entity::display_bank_account()

{
  string str;

  str  = "\r\n" + possessive(get_name()) + " account:\r\n";
  str += "#YItems:#N";
  str += stack_items(bank_item_list, bank_num_items, 1);
  str += "\r\n#YGold#N: " + intconvert(bank_gold);

  echo(str);
}