#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

int room::num_merchants()

{
  int count = 0;

  for (int i=0; i<size; i++)
    if (!(ENT_LIST[i]->PLAYER))
    if (ENT_LIST[i]->get_info()->MERCHANT)
      count++;

  return count;
}

entity* room::get_merchant(int target)

{
  for (int i=0; i<size; i++)
    if (!(ENT_LIST[i]->PLAYER))
    if (ENT_LIST[i]->get_info()->MERCHANT)
      if (target == 1) return ENT_LIST[i];
      else target--;

  return NULL;
}

item* room::find_item_merchant(string str, entity* *mp)

{
  int merchant_count = num_merchants();
  int target_num = clip_number(&str);
  int found_count = 0;
  int prefix = 1;

  item* ip;

  if (str.size() < 3) return NULL;

  for (int i=1; i<=merchant_count; i++)

  {
    *mp = get_merchant(i);

    for (int j=0; j<(*mp)->get_info()->merchant_listsize; j++)

    {
      ip = (*mp)->merchant_find_item(prefix, str);

      if (ip != NULL)
        found_count++;

      if (found_count == target_num)
        return ip;

      prefix++;
    }

    prefix = 1;
  }

  return NULL;
}

item* mobile::merchant_find_item(int target_num, string str)

{
  itemlist* ilist = World->get_itemlist();

  for (int i=0; i<info->merchant_listsize; i++)

  {
    if (lowercase(remove_colors(ilist->get_item(info->merchant_list[i])->name)).find(str) != string::npos)
      target_num--;

    if (!target_num)
      return ilist->get_item(info->merchant_list[i]);
  }

  return NULL;
}

void mobile::merchant_sell_item(item* item_pointer, entity* target)

{
  itemlist* ilist = World->get_itemlist();

  target->add_item_inventory(ilist->replicate(item_pointer->id, "Sold by " + get_name() + "."));
  target->set_gold(target->get_gold() - item_pointer->worth);
  target->echo("You buy " + item_pointer->lname + " for " + intconvert(item_pointer->worth) + " gold.");
  target->blind_emote("buys " + item_pointer->lname + " for " + intconvert(item_pointer->worth) + " gold.");
}

void mobile::merchant_buy_item(item* item_pointer, entity* target)

{
  target->remove_item_inventory(item_pointer);
  target->set_gold(target->get_gold() + item_pointer->worth);
  target->echo("You sell " + item_pointer->lname + " for " + intconvert(item_pointer->worth) + " gold.");
  target->blind_emote("sells " + item_pointer->lname + " for " + intconvert(item_pointer->worth) + " gold.");
}

void entity::buy_item(string str)

{
  item* item_pointer;
  entity* mp;

  str = remove_colors(str);

  if (rm->num_merchants() == 0) {
    echo("There is nobody here to buy from.");
    return; }

  if (str == "") {
    echo("What do you want to buy?");
    return; }

  item_pointer = rm->find_item_merchant(str, &mp);

  if (item_pointer == NULL) {
    echo("#n" + rm->get_merchant(1)->get_name() + " #nsays, 'Sorry, I don't have that item in stock.'#N");
    return; }

  string iname = item_pointer->lname;

  if (item_pointer->worth > get_gold()) {
    echo("#n" + mp->get_name() + " #nsays, 'You don't have enough gold for " + iname + ".'#N");
    return; }

  if (get_total_items()+1 > 100) {
    echo("#n" + mp->get_name() + " #nsays, 'You don't have enough inventory room for " + iname + ".'#N");
    return; }

  if (item_pointer->level > get_level()) {
    echo("#n" + mp->get_name() + " #nsays, 'Your level is too low for " + iname + ".'#N");
    return; }

  mp->merchant_sell_item(item_pointer, this);
}

void entity::sell_item(string str)

{
  entity* merchant_pointer = rm->get_merchant(1);
  item* item_pointer = find_item_inventory(str);

  if (rm->num_merchants() == 0) {
    echo("There is nobody here to sell to.");
    return; }

  if (str == "") {
    echo("What do you want to sell?");
    return; }

  if (item_pointer == NULL) {
    echo("#n" + merchant_pointer->get_name() + " #nsays, 'Hmm, you don't seem to have that item.'#N");
    return; }


  if (item_pointer->find_type(ITEM_CONTAINER))
    if (item_pointer->num_items > 0) {
      echo("#n" + merchant_pointer->get_name() + " #nsays, 'I don't buy containers with items inside, sorry.'#N");
      return; }

  merchant_pointer->merchant_buy_item(item_pointer, this);
}

string mobile::merchant_item_list()

{
  string temp = "";
  string wares[500], prices[500];
  itemlist *itmlst = World->get_itemlist();
  int count = 0, max1 = 0, max2 = 0, top = 0, bottom = 0, width = 0;

  for (int i=0; i<info->merchant_listsize; i++) {
    wares[count] = itmlst->get_item(info->merchant_list[i])->name;
    prices[count] = exp_commas(intconvert(itmlst->get_item(info->merchant_list[i])->worth));
    count++; }

  for (int i=0; i<count; i++) {
    if (visible_size(wares[i]) > max1) max1 = visible_size(wares[i]);
    if (visible_size(prices[i]) > max2) max2 = visible_size(prices[i]); }

  top = visible_size(info->store_name) + 1;
  if (count == 0) bottom = 9;
  else bottom = max1 + max2 + 11;
  if (top >= bottom) width = top;
  else width = bottom;

  string border = "#n+-";
  for (int i=1; i<=width; i++) border += "-";
  border += "-+#N";

  temp  = "\r\n  " + border + "\r\n";
  temp += "  #n|#N " + info->store_name + "#N:";
  for (int i=1; i<width-visible_size(info->store_name); i++) temp += " ";
  temp += " #n|#N\r\n";
  temp += "  " + border + "\r\n";

  if (count == 0)

  {
    temp += "  #n| #NSold Out!";
    for (int j=1; j<=width-9; j++) temp += " ";
    temp += " #n|#N\r\n";
    temp += "  " + border;

    return temp;
  }

  for (int i=0; i<count; i++)

  {
    temp += "  #n|#N " + wares[i];
    for (int j=1; j<=max1-visible_size(wares[i]); j++) temp += " ";
    temp += "  #N--  ";
    for (int j=1; j<=max2-visible_size(prices[i]); j++) temp += " ";
    temp += "#N" + prices[i] + " #NGold #n|#N\r\n";
  }

  temp += "  " + border;

  return temp;
}

void mobdesc::add_item_sale(int id)

{
  merchant_list[merchant_listsize] = id;
  merchant_listsize++;
}