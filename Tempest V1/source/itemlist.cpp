#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

itemlist::itemlist()

{
  set_size();

  for (int i=0; i<size; i++)
    list[i] = NULL;

  last_ms = 0;
  ms_match_count = 0;
}

itemlist::~itemlist()

{
  delete [] list;
}

item* itemlist::get_item(int n)

{
  return list[n];
}

item* itemlist::get_item(string s)

{
  if (number(s))

  {
    int index = stringconvert(s);

    if ((index < 0) || (index >= size))
      return NULL;

    return list[index];
  }

  s = remove_colors(s);
  int target_num = clip_number(&s);
  if (s.size() < 3) return NULL;

  for (int i=0; i<size; i++)
  if (list[i] != NULL)

  {
    if (abbreviation(s, lowercase(remove_colors(list[i]->name))))

    {
      target_num--;

      if (!target_num)
        return list[i];
    }
  }

  return NULL;
}

item* itemlist::replicate(int n, string how_created)

{
  item* new_item = new item();
  int position, length;
  string bname;

  if (list[n] == NULL) return NULL;

  if (list[n]->find_type(ITEM_BLOCKER))

  {
    delete new_item;
    new_item = new blocker();
    DC_Blocker(new_item)->set_bdir(DC_Blocker(list[n])->get_bdir());
    DC_Blocker(new_item)->set_btype(DC_Blocker(list[n])->get_btype());
  }

  new_item->board_name       = list[n]->board_name;
  new_item->board_pointer    = World->get_boardlist()->get_board(list[n]->board_name);
  new_item->amount           = list[n]->amount;
  new_item->id               = list[n]->id;
  new_item->type_list        = list[n]->type_list;
  new_item->num_types        = list[n]->num_types;
  new_item->name             = list[n]->name;
  new_item->lname            = list[n]->lname;
  new_item->look_desc        = list[n]->look_desc;
  new_item->ground_desc      = list[n]->ground_desc;
  new_item->flag_list        = list[n]->flag_list;
  new_item->num_flags        = list[n]->num_flags;
  new_item->worth            = list[n]->worth;
  new_item->level            = list[n]->level;
  new_item->classes          = list[n]->classes;
  new_item->places           = list[n]->places;
  new_item->hitroll          = list[n]->hitroll;
  new_item->damroll          = list[n]->damroll;
  new_item->armorclass       = list[n]->armorclass;
  new_item->resistance       = list[n]->resistance;
  new_item->HP               = list[n]->HP;
  new_item->MN               = list[n]->MN;
  new_item->MV               = list[n]->MV;
  new_item->STR              = list[n]->STR;
  new_item->DEX              = list[n]->DEX;
  new_item->CON              = list[n]->CON;
  new_item->INT              = list[n]->INT;
  new_item->WIS              = list[n]->WIS;
  new_item->all_spells       = list[n]->all_spells;
  new_item->all_reduce       = list[n]->all_reduce;
  new_item->all_power        = list[n]->all_power;
  new_item->mana_list        = list[n]->mana_list;
  new_item->power_list       = list[n]->power_list;
  new_item->enhance_list     = list[n]->enhance_list;
  new_item->num_enhancements = list[n]->num_enhancements;
  new_item->max_items        = list[n]->max_items;
  new_item->all_items        = list[n]->all_items;
  new_item->num_items        = list[n]->num_items;
  new_item->max_level        = list[n]->max_level;
  new_item->Item_List        = new item*[new_item->max_items];
  new_item->liquid_type      = list[n]->liquid_type;
  new_item->uses             = list[n]->uses;
  new_item->max_uses         = list[n]->max_uses;
  new_item->num_spells       = list[n]->num_spells;
  new_item->spell_list       = list[n]->spell_list;
  new_item->decay_time       = -1;
  new_item->num_innate       = list[n]->num_innate;
  new_item->innate_list      = list[n]->innate_list;
  new_item->max_charges      = list[n]->max_charges;
  new_item->charges_left     = list[n]->charges_left;
  new_item->num_charged      = list[n]->num_charged;
  new_item->charged_list     = list[n]->charged_list;
  new_item->PLAYABLE         = list[n]->PLAYABLE;
  new_item->PULLABLE         = list[n]->PULLABLE;

  for (int i=0; i<NUM_ITEM_ACTIONS; i++)

  {
    new_item->work_room[i] = list[n]->work_room[i];
    new_item->action_conditions[i] = list[n]->action_conditions[i];
    new_item->mana_cost[i] = list[n]->mana_cost[i];
    new_item->action_classes[i] = list[n]->action_classes[i];
    new_item->num_fail[i] = list[n]->num_fail[i];
    new_item->num_succ[i] = list[n]->num_succ[i];

    for (int j=0; j<list[n]->num_commands[i]; j++) {
      new_item->action_commands[i][j]  = list[n]->action_commands[i][j];
      new_item->command_location[i][j] = list[n]->command_location[i][j]; }

    for (int j=0; j<list[n]->num_fail[i]; j++) {
      new_item->fail_commands[i][j]    = list[n]->fail_commands[i][j];
      new_item->fail_locations[i][j]   = list[n]->fail_locations[i][j]; }

    for (int j=0; j<list[n]->num_succ[i]; j++) {
      new_item->succ_commands[i][j]    = list[n]->succ_commands[i][j];
      new_item->succ_locations[i][j]   = list[n]->succ_locations[i][j]; }

    new_item->num_commands[i] = list[n]->num_commands[i];
  }

  SYSTEMTIME timestamp;
  GetLocalTime(&timestamp);
  string time_stamp = "";
  Sleep(1);

  if (last_ms == timestamp.wMilliseconds) {
    ms_match_count++;
    timestamp.wMilliseconds += ms_match_count; }

  else {
    ms_match_count = 0;
    last_ms = timestamp.wMilliseconds; }

  time_stamp += intconvert(timestamp.wMonth) + "/";
  time_stamp += intconvert(timestamp.wDay) + "/";
  time_stamp += intconvert(timestamp.wYear) + ". ";
  time_stamp += intconvert(timestamp.wHour) + ":";
  time_stamp += intconvert(timestamp.wMinute) + ":";
  time_stamp += intconvert(timestamp.wSecond) + ":";
  time_stamp += intconvert(timestamp.wMilliseconds) + ".";

  new_item->TIME_STAMP = time_stamp;
  new_item->TYPE_STAMP = how_created;

  return new_item;
}

void itemlist::load()

{
  int done, num, number_of_items;
  string filename, XXX, str, type;
  char c, s[500], *thefile;
  stringstream ss;
  FILE *fp;

  string input, temp;

  for (int f=0; f<=World->get_maxzone(); f++)

  {
    ss.clear();
    ss.str("");

    ss << f;
    XXX = ss.str();

    if (f < 10)
      XXX = "00" + XXX;

    else if ((f >= 10) && (f <= 99))
      XXX = "0" + XXX;

    filename = ".\\zones\\zone" + XXX + ".itm";

    thefile = (char*)filename.c_str();
    fp = fopen(thefile, "r");

    if (fp == NULL) { /* Item file "f" does not exist! */ }

    else

    {
      cout << "Loading Item File " << f << "." << endl;

      fgets(s, sizeof(s), fp);

      for (int i=1; i<=8; i++)
        getc(fp);

      fscanf(fp, "%d", &number_of_items);

      fgets(s, sizeof(s), fp);
      fgets(s, sizeof(s), fp);

      int INUM;

      while (!feof(fp))

      {
        str = ReadLine(fp);

        if (first(str) == "ITEM:")

        {
          INUM = stringconvert(last(str));
          list[INUM] = new item();

          str = ReadLine(fp);
          str = last(str);

          if (str == "BLOCKER") {
            delete list[INUM];
            list[INUM] = new blocker();
            list[INUM]->add_type(ITEM_BLOCKER); }

          else if (str == "GENERIC")        { list[INUM]->add_type(ITEM_GENERIC);                                              }
          else if (str == "EQUIPMENT")      { list[INUM]->add_type(ITEM_EQUIPMENT);                                            }
          else if (str == "CONTAINEREQ")    { list[INUM]->add_type(ITEM_EQUIPMENT); list[INUM]->add_type(ITEM_CONTAINER);      }
          else if (str == "CONTAINER")      { list[INUM]->add_type(ITEM_CONTAINER);                                            }
          else if (str == "BOARD")          { list[INUM]->add_type(ITEM_BOARD);                                                }
          else if (str == "CORPSE")         { list[INUM]->add_type(ITEM_CORPSE); list[INUM]->add_type(ITEM_CONTAINER);         }
          else if (str == "GOLD")           { list[INUM]->add_type(ITEM_GOLD);                                                 }
          else if (str == "FOOD")           { list[INUM]->add_type(ITEM_FOOD);                                                 }
          else if (str == "DRINK")          { list[INUM]->add_type(ITEM_DRINK); list[INUM]->add_type(ITEM_DRINKABLE);          }
          else if (str == "DRINKABLE")      { list[INUM]->add_type(ITEM_DRINKABLE);                                            }
          else if (str == "DRINKCONTAINER") { list[INUM]->add_type(ITEM_DRINKCONTAINER); list[INUM]->add_type(ITEM_DRINKABLE); }
          else if (str == "POTION")         { list[INUM]->add_type(ITEM_POTION); list[INUM]->add_type(ITEM_DRINKABLE);         }
          else if (str == "SCROLL")         { list[INUM]->add_type(ITEM_SCROLL);                                               }
          else if (str == "FOUNTAIN")       { list[INUM]->add_type(ITEM_FOUNTAIN); list[INUM]->add_type(ITEM_DRINKABLE);       }

          list[INUM]->id = INUM;
          list[INUM]->zone_id = f;
        }

        if (first(str) == "BTYPE:") {
          DC_Blocker(list[INUM])->set_bdir(last(str)[0]);
          DC_Blocker(list[INUM])->set_btype(last(str).substr(2, last(str).size()-2)); }

        if (first(str) == "LTYPE:")       list[INUM]->liquid_type = last(str);
        if (first(str) == "USES:")        list[INUM]->uses = stringconvert(last(str));
        if (first(str) == "MAXUSES:")     list[INUM]->max_uses = stringconvert(last(str));
        if (first(str) == "CSPELL:")      list[INUM]->add_cspell(last(str));
        if (first(str) == "BOARDNAME:")   list[INUM]->board_name = last(str);
        if (first(str) == "NAME:")        list[INUM]->name = last(str);
        if (first(str) == "LNAME:")       list[INUM]->lname = last(str);
        if (first(str) == "GDESC:")       list[INUM]->ground_desc = last(str);
        if (first(str) == "LEVEL:")       list[INUM]->level = stringconvert(last(str));
        if (first(str) == "FLAG:")        list[INUM]->add_flag(last(str));
        if (first(str) == "WORTH:")       list[INUM]->worth = stringconvert(last(str));
        if (first(str) == "CLASS:")       list[INUM]->classes = last(str);
        if (first(str) == "PLACE:")       list[INUM]->places = last(str);
        if (first(str) == "MAXITEMS:")    list[INUM]->max_items = stringconvert(last(str));
        if (first(str) == "ENHANCEMENT:") list[INUM]->add_enhancement(last(str));
        if (first(str) == "INNATE:")      list[INUM]->add_innate(last(str));
        if (first(str) == "CHARGED:")     list[INUM]->add_charged(last(str));
        if (first(str) == "CHARGES:")

        {
          list[INUM]->charges_left = stringconvert(last(str));
          list[INUM]->max_charges = list[INUM]->charges_left;
        }

        if (first(str) == "STATS:")

        {
          str = last(str);
          list[INUM]->hitroll = stringconvert(first(str)); str = last(str);
          list[INUM]->damroll = stringconvert(first(str)); str = last(str);
          list[INUM]->armorclass = stringconvert(first(str)); str = last(str);
          list[INUM]->resistance = stringconvert(first(str)); str = last(str);
          list[INUM]->HP = stringconvert(first(str)); str = last(str);
          list[INUM]->MN = stringconvert(first(str)); str = last(str);
          list[INUM]->MV = stringconvert(first(str)); str= last(str);
        }

        if (first(str) == "SREQS:")

        {
          str = last(str);
          list[INUM]->STR = stringconvert(first(str)); str = last(str);
          list[INUM]->DEX = stringconvert(first(str)); str = last(str);
          list[INUM]->CON = stringconvert(first(str)); str = last(str);
          list[INUM]->INT = stringconvert(first(str)); str = last(str);
          list[INUM]->WIS = stringconvert(first(str)); str = last(str);
        }

        if (first(str) == "ACTION")

        {
          int action_type;
          string action_name = last(str);

          if (action_name == "PLAY")      action_type = ITEM_ACTION_PLAY;
          else if (action_name == "PULL") action_type = ITEM_ACTION_PULL;

          if (action_type == ITEM_ACTION_PLAY)      list[INUM]->PLAYABLE = 1;
          else if (action_type == ITEM_ACTION_PULL) list[INUM]->PULLABLE = 1;

          list[INUM]->action_classes[action_type] = last(ReadLine(fp));
          list[INUM]->mana_cost[action_type] = stringconvert(last(ReadLine(fp)));
          list[INUM]->work_room[action_type] = stringconvert(last(ReadLine(fp)));
          list[INUM]->action_conditions[action_type] = last(ReadLine(fp));

          input = ReadLine(fp);
          int count = 0;
          int fail_count = 0;
          int succ_count = 0;

          if (input != "BEGIN")

          {
            while ((input != "END ACTION") && (list[INUM]->num_commands[action_type] < NUM_ACTION_COMMANDS))

            {
              if (first(input) == "SUCC")

              {
                input = last(input);
                list[INUM]->succ_locations[action_type][succ_count] = stringconvert(first(input));
                list[INUM]->succ_commands[action_type][succ_count++] = last(input);
              }

              else if (first(input) == "FAIL")

              {
                input = last(input);
                list[INUM]->fail_locations[action_type][fail_count] = stringconvert(first(input));
                list[INUM]->fail_commands[action_type][fail_count++] = last(input);
              }

              else if (first(input) == "PREC")

              {
                input = last(input);
                list[INUM]->command_location[action_type][count] = stringconvert(first(input));
                list[INUM]->action_commands[action_type][count++] = last(input);
              }

              input = ReadLine(fp);
            }

            list[INUM]->num_commands[action_type] = count;
            list[INUM]->num_succ[action_type] = succ_count;
            list[INUM]->num_fail[action_type] = fail_count;
          }

          else str = input;
        }

        if (first(str) == "BEGIN")

        {
          if (last(str) != "EXACT")

          {
            temp = ReadLine(fp);

            while (temp != "END") {
              list[INUM]->look_desc += temp + " ";
              temp = ReadLine(fp); }

            if ((int)list[INUM]->look_desc.size() > 0)
              list[INUM]->look_desc = list[INUM]->look_desc.substr(0, list[INUM]->look_desc.size()-1);

            list[INUM]->look_desc = rm_allign(list[INUM]->look_desc);
          }

          else

          {
            temp = ReadLine(fp);

            while (temp != "END") {
              list[INUM]->look_desc += temp + "\r\n";
              temp = ReadLine(fp); }

            if ((int)list[INUM]->look_desc.size() >= 2)
              list[INUM]->look_desc = list[INUM]->look_desc.substr(0, list[INUM]->look_desc.size()-2);

            else exit_error("Error reading exact room description.");
          }
        }
      }

      if (fclose(fp) != 0)
        cout << "Error closing Item file!\n" << endl;
    }
  }
}