#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

void entity::go_to(int rnum)

{
  room *target_room;
  int wsize = World->get_size();

  if (my_client != NULL)
  if (my_client->get_state() == CSTATE_IMM_AT) {
    echo("You can't do that.");
    return; }

  if (rnum < wsize)
    target_room = World->get_room(rnum);
  else
    target_room = NULL;

  if (target_room != NULL) {
    stop_fighting();
    stop_tracking();
    rm->loss(this);
    blind_emote("vanishes into thin air.");
    rm = target_room;
    rm->gain(this);
    blind_emote("appears from out of nowhere.");
    look("#gWoooooosh!#N"); }

  else echo("There is no room by that number.");
}

void entity::go_to(string target)

{
  entity *ENT = clist->find_global_entity(target);

  if (my_client != NULL)
  if (my_client->get_state() == CSTATE_IMM_AT) {
    echo("You can't do that.");
    return; }

  if (ENT == this)
    echo("It's kind of pointless to go to yourself.");

  else if (ENT != NULL) {
    stop_fighting();
    stop_tracking();
    rm->loss(this);
    blind_emote("vanishes into thin air.");
    rm = ENT->get_room();
    rm->gain(this);
    blind_emote("appears from out of nowhere.");
    look("#gWoooooosh!#N"); }

  else echo("There is nobody around by that name.");
}

void entity::trans(string target)

{
  entity *ENT = clist->find_global_entity(target);

  if (ENT == this)
    echo("You are already here.  No need to transport.");

  else if (ENT != NULL)

  {
    ENT->stop_fighting();
    ENT->stop_tracking();

    if (ENT->PLAYER) ENT->get_room()->loss(ENT);
    else ENT->get_room()->loss(ENT);

    ENT->blind_emote("disappears in a puff of smoke.");
    ENT->set_room(rm);

    if (ENT->PLAYER) rm->gain(ENT);
    else rm->gain(ENT);

    echo(ENT->get_name() + " appears in a burst of flames.");
    ENT->xblind_emote(this, "appears in a burst of flames.");
    ENT->look("#r" + name + " has transported you!#N");
  }

  else echo("There is nobody around by that name.");
}

void entity::at(string target, string action)

{
  room *target_room, *original_room = rm;

  if ((target == "") || (action == "")) {
    echo("At Usage#n:#N #R[#Nat playername action#R]#N");
    return; }

  if (my_client->get_state() == CSTATE_IMM_AT) {
    echo("You can't do that.");
    return; }

  if (playerstate == PSTATE_FIGHTING) {
    echo("You'd better finish this fight first.");
    return; }

  if (number(target))

  {
    target_room = World->get_room(stringconvert(target));

    if (target_room == NULL) {
      echo("There is no room by that number.");
      return; }
  }

  else

  {
    entity *ENT = clist->find_global_entity(target);

    if (ENT == NULL) {
      echo("There is nobody around by that name.");
      return; }

    if (ENT == this) {
      echo("If you want to do something at yourself, just do it.");
      return; }

    target_room = ENT->get_room();
  }

  my_client->set_state(CSTATE_IMM_AT);

  backup_room();
  rm = target_room;
  interpret_command(action);
  rm = original_room;

  if (my_client != NULL)
  my_client->set_state(CSTATE_NORMAL);
}

void entity::rlist(string str)

{
  str = remove_colors(str);

  if (number(str)) {
    rlist(stringconvert(str));
    return; }

  if (number(first(remove_dashes(str))) && number(last(remove_dashes(str)))) {
    rlist(stringconvert(first(remove_dashes(str))), stringconvert(last(remove_dashes(str))));
    return; }

  string temp = "#nRoom List:", whitespace;

  for (int i=0; i<World->get_size(); i++)

  {
    if (World->get_room(i) != NULL)
    if ((int)str.size() >= 3)
    if ((abbreviation(str, lowercase(remove_colors(World->get_room(i)->get_title())))) || (str == "all"))

    {
      whitespace = "";
      if (World->get_room(i)->get_id() < 100000) whitespace += " ";
      if (World->get_room(i)->get_id() < 10000) whitespace += " ";
      if (World->get_room(i)->get_id() < 1000) whitespace += " ";
      if (World->get_room(i)->get_id() < 100) whitespace += " ";
      if (World->get_room(i)->get_id() < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(World->get_room(i)->get_id()) + "#N]" + whitespace;
      temp += World->get_room(i)->get_title() + "#N";
    }
  }

  echo(temp);
}

void entity::rlist(int znum)

{
  string temp = "#nRoom List:", whitespace;

  for (int i=0; i<World->get_size(); i++)

  {
    if (World->get_room(i) != NULL)
    if (World->get_room(i)->get_zone()->get_number() == znum)

    {
      whitespace = "";
      if (World->get_room(i)->get_id() < 100000) whitespace += " ";
      if (World->get_room(i)->get_id() < 10000) whitespace += " ";
      if (World->get_room(i)->get_id() < 1000) whitespace += " ";
      if (World->get_room(i)->get_id() < 100) whitespace += " ";
      if (World->get_room(i)->get_id() < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(World->get_room(i)->get_id()) + "#N]" + whitespace;
      temp += World->get_room(i)->get_title() + "#N";
    }
  }

  echo(temp);
}

void entity::rlist(int start, int finish)

{
  string temp = "#nRoom List:", whitespace;

  if (start >= World->get_size()) start = World->get_size()-1;
  if (finish >= World->get_size()) finish = World->get_size()-1;

  for (int i=start; i<=finish; i++)

  {
    if (World->get_room(i) != NULL)

    {
      whitespace = "";
      if (World->get_room(i)->get_id() < 100000) whitespace += " ";
      if (World->get_room(i)->get_id() < 10000) whitespace += " ";
      if (World->get_room(i)->get_id() < 1000) whitespace += " ";
      if (World->get_room(i)->get_id() < 100) whitespace += " ";
      if (World->get_room(i)->get_id() < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(World->get_room(i)->get_id()) + "#N]" + whitespace;
      temp += World->get_room(i)->get_title() + "#N";
    }
  }

  echo(temp);
}

void entity::mlist(string str)

{
  str = remove_colors(str);

  if (number(str)) {
    mlist(stringconvert(str));
    return; }

  if (number(first(remove_dashes(str))) && number(last(remove_dashes(str)))) {
    mlist(stringconvert(first(remove_dashes(str))), stringconvert(last(remove_dashes(str))));
    return; }

  string temp = "#nMobile List:", whitespace;
  moblist *list = World->get_moblist();

  for (int i=0; i<list->get_size(); i++)

  {
    if (list->get_mobdesc(i) != NULL)
    if ((int)str.size() >= 3)
    if (((abbreviation(str, lowercase(remove_colors(list->get_mobdesc(i)->name))))
    || (str == "all")) || (list->get_mobdesc(i)->check_targets(str)))

    {
      whitespace = "";
      if (list->get_mobdesc(i)->vnum < 100000) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 10000) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 1000) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 100) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(list->get_mobdesc(i)->vnum) + "#N]" + whitespace;
      temp += list->get_mobdesc(i)->name + "#N";
    }
  }

  echo(temp);
}

void entity::mlist(int znum)

{
  string temp = "#nMobile List:", whitespace;
  moblist *list = World->get_moblist();

  for (int i=0; i<list->get_size(); i++)

  {
    if (list->get_mobdesc(i) != NULL)
    if (list->get_mobdesc(i)->zone_id == znum)

    {
      whitespace = "";
      if (list->get_mobdesc(i)->vnum < 100000) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 10000) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 1000) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 100) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(list->get_mobdesc(i)->vnum) + "#N]" + whitespace;
      temp += list->get_mobdesc(i)->name + "#N";
    }
  }

  echo(temp);
}

void entity::mlist(int start, int finish)

{
  string temp = "#nMobile List:", whitespace;
  moblist *list = World->get_moblist();

  if (start >= list->get_size()) start = list->get_size()-1;
  if (finish >= list->get_size()) finish = list->get_size()-1;

  for (int i=start; i<=finish; i++)

  {
    if (list->get_mobdesc(i) != NULL)

    {
      whitespace = "";
      if (list->get_mobdesc(i)->vnum < 100000) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 10000) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 1000) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 100) whitespace += " ";
      if (list->get_mobdesc(i)->vnum < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(list->get_mobdesc(i)->vnum) + "#N]" + whitespace;
      temp += list->get_mobdesc(i)->name + "#N";
    }
  }

  echo(temp);
}

void entity::ilist(string str)

{
  str = remove_colors(str);

  if (number(str)) {
    ilist(stringconvert(str));
    return; }

  if (number(first(remove_dashes(str))) && number(last(remove_dashes(str)))) {
    ilist(stringconvert(first(remove_dashes(str))), stringconvert(last(remove_dashes(str))));
    return; }

  string temp = "#nItem List:", whitespace;
  itemlist *list = World->get_itemlist();

  for (int i=0; i<list->get_size(); i++)

  {
    if (list->get_item(i) != NULL)
    if ((int)str.size() >= 3)
    if ((abbreviation(str, lowercase(remove_colors(list->get_item(i)->name)))) || (str == "all"))

    {
      whitespace = "";
      if (list->get_item(i)->id < 100000) whitespace += " ";
      if (list->get_item(i)->id < 10000) whitespace += " ";
      if (list->get_item(i)->id < 1000) whitespace += " ";
      if (list->get_item(i)->id < 100) whitespace += " ";
      if (list->get_item(i)->id < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(list->get_item(i)->id) + "#N]" + whitespace;
      temp += list->get_item(i)->name + "#N";
    }
  }

  echo(temp);
}

void entity::ilist(int znum)

{
  string temp = "#nItem List:", whitespace;
  itemlist *list = World->get_itemlist();

  for (int i=0; i<list->get_size(); i++)

  {
    if (list->get_item(i) != NULL)
    if (list->get_item(i)->zone_id == znum)

    {
      whitespace = "";
      if (list->get_item(i)->id < 100000) whitespace += " ";
      if (list->get_item(i)->id < 10000) whitespace += " ";
      if (list->get_item(i)->id < 1000) whitespace += " ";
      if (list->get_item(i)->id < 100) whitespace += " ";
      if (list->get_item(i)->id < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(list->get_item(i)->id) + "#N]" + whitespace;
      temp += list->get_item(i)->name + "#N";
    }
  }

  echo(temp);
}

void entity::ilist(int start, int finish)

{
  string temp = "#nItem List:", whitespace;
  itemlist *list = World->get_itemlist();

  if (start >= list->get_size()) start = list->get_size()-1;
  if (finish >= list->get_size()) finish = list->get_size()-1;

  for (int i=start; i<=finish; i++)

  {
    if (list->get_item(i) != NULL)

    {
      whitespace = "";
      if (list->get_item(i)->id < 100000) whitespace += " ";
      if (list->get_item(i)->id < 10000) whitespace += " ";
      if (list->get_item(i)->id < 1000) whitespace += " ";
      if (list->get_item(i)->id < 100) whitespace += " ";
      if (list->get_item(i)->id < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(list->get_item(i)->id) + "#N]" + whitespace;
      temp += list->get_item(i)->name + "#N";
    }
  }

  echo(temp);
}

void entity::zlist(string str)

{
  str = remove_colors(str);

  if (number(str)) {
    zlist(stringconvert(str));
    return; }

  if (number(first(remove_dashes(str))) && number(last(remove_dashes(str)))) {
    zlist(stringconvert(first(remove_dashes(str))), stringconvert(last(remove_dashes(str))));
    return; }

  string temp = "#nZone List:", whitespace;

  for (int i=0; i<=World->get_maxzone(); i++)

  {
    if (World->get_zone(i) != NULL)
    if ((int)str.size() >= 3)
    if ((abbreviation(str, lowercase(remove_colors(World->get_zone(i)->get_name())))) || (str == "all"))

    {
      whitespace = "";
      if (World->get_zone(i)->get_number() < 1000) whitespace += " ";
      if (World->get_zone(i)->get_number() < 100) whitespace += " ";
      if (World->get_zone(i)->get_number() < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(World->get_zone(i)->get_number()) + "#N]" + whitespace;
      temp += World->get_zone(i)->get_name() + "#N";
    }
  }

  echo(temp);
}

void entity::zlist(int num)

{
  string temp = "#nZone List:", whitespace;

  if (World->get_zone(num) != NULL)

  {
    whitespace = "";
    if (World->get_zone(num)->get_number() < 1000) whitespace += " ";
    if (World->get_zone(num)->get_number() < 100) whitespace += " ";
    if (World->get_zone(num)->get_number() < 10) whitespace += " ";

    temp += "\r\n#N[#c" + intconvert(World->get_zone(num)->get_number()) + "#N]" + whitespace;
    temp += World->get_zone(num)->get_name() + "#N";
  }

  echo(temp);
}

void entity::zlist(int start, int finish)

{
  string temp = "#nZone List:", whitespace;

  if (start >= World->get_maxzone()) start = World->get_maxzone();
  if (finish >= World->get_maxzone()) finish = World->get_maxzone();

  for (int i=start; i<=finish; i++)

  {
    if (World->get_zone(i) != NULL)

    {
      whitespace = "";
      if (World->get_zone(i)->get_number() < 1000) whitespace += " ";
      if (World->get_zone(i)->get_number() < 100) whitespace += " ";
      if (World->get_zone(i)->get_number() < 10) whitespace += " ";

      temp += "\r\n#N[#c" + intconvert(World->get_zone(i)->get_number()) + "#N]" + whitespace;
      temp += World->get_zone(i)->get_name() + "#N";
    }
  }

  echo(temp);
}

void entity::imm_restore(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    restore();
    echo("#gYou restore yourself to full health.#N"); }

  else if (ENT != NULL) {
    ENT->restore();
    ENT->echo("\r\n#g" + name + " has restored you to full health!#N");
    echo("#gYou restore " + ENT->get_name() + " to full health.#N"); }

  else echo("There is nobody here by that name.");
}

void entity::force(string target, string command)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this)
    echo("Do you really need to force yourself?");

  else if (ENT != NULL)

  {
    if (ENT->get_level() >= get_level()) {
      echo("Pick on somebody your own size.");
      return; }

    if (match(first(command), "k", "kill")) my_client->put_foutput("Ok.");
    else echo("Ok.");
    ENT->interpret_command(command);
  }

  else echo("There is nobody here by that name.");
}

void entity::possess(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (my_client->get_state() == CSTATE_IMM_AT)
    restore_room();

  if (ENT == this)
    echo("Hopefully, you already possess your own soul.");

  else if (ENT != NULL)

  {
    if (ENT->PLAYER)
      echo("You can only possess mobiles, not people.");

    else if (playerstate == PSTATE_FIGHTING)
      echo("You'd better finish this fight first.");

    else if ((!ENT->PLAYER) && (ENT->get_client() != NULL))
      echo(ENT->get_name() + " is already possessed.");

    else

    {
      client* MY_CLIENT = my_client;
      int extra_line = 0;

      if ((my_group->get_size() > 1) && (my_group->get_leader() == this)) {
        my_group->remove_all(this);
        extra_line = 1; }

      stop_fighting();
      stop_following();
      rm->loss(this);
      my_client = NULL;

      ENT->set_client(MY_CLIENT);
      MY_CLIENT->set_charinfo(ENT);
      MY_CLIENT->set_state(CSTATE_POSSESSION);

      string tempstr = "";
      if (extra_line == 1) tempstr += "\r\n";

      tempstr += "#rYou leap into " + MY_CLIENT->get_char()->get_name() + "'s very soul, and possess ";
      tempstr += MY_CLIENT->get_char()->get_him() + "!#N";

      MY_CLIENT->get_char()->echo(tempstr);
    }
  }

  else echo("There is no mobile here by that name.");
}

void entity::unpossess(int active)

{
  entity* ENT = my_client->get_char();
  string mobname = my_client->get_char()->get_name();

  if (PLAYER) {
    if (get_level() < 100) echo("Invalid Command.");
    else echo("You must first possess a mobile in order to unpossess one.");
    return; }

  if (active)
    echo("#rYou return to your body!#N");

  my_client->charinfo_restore();
  my_client->set_state(CSTATE_NORMAL);
  my_client->get_char()->get_room()->gain(my_client->get_char());

  ENT->set_client(NULL);
}

void entity::slay(string target)

{
  entity* ENT = rm->find_entity(this, target);

  if (ENT == this)
    echo("You shouldn't Slay yourself.  It would probably hurt.");

  else if (ENT != NULL)

  {
    if (ENT->PLAYER) echo("You can't Slay players. (yet)");

    else

    {
      string s1  = "#rYou savagely rip " + ENT->get_name();
             s1 += " to shreds, completely annihilating " + ENT->get_him() + "!";
      string s2  = "#r" + name + " savagely rips " + ENT->get_name();
             s2 +=" to shreds, completely annihilating " + ENT->get_him() + "!";

      ENT->stop_fighting();
      ENT->die(0);

      echo(s1);
      rm->xecho(this, s2);
    }
  }

  else echo("There's nobody here by that name.");
}

void entity::zrepop(int znum)

{
  if ((znum > World->get_maxzone()) || (znum < 0))
    echo("That zone does not exist.");

  else if (World->get_zone(znum) == NULL)
    echo("That zone does not exist.");

  else {
    World->get_zone(znum)->repopulate();
    echo("#gYou draw upon the powers of creation to repopulate " + World->get_zone(znum)->get_name() + "#g!#N"); }
}

void entity::zclear(int znum)

{
  if ((znum > World->get_maxzone()) || (znum < 0))
    echo("That zone does not exist.");

  else if (World->get_zone(znum) == NULL)
    echo("That zone does not exist.");

  else {
    World->get_zone(znum)->clear();
    echo("#gYou have purged " + World->get_zone(znum)->get_name() + " #gof mundane existence!#N"); }
}

void entity::load(string str)

{
  moblist *mblst = World->get_moblist();
  itemlist *itmlst = World->get_itemlist();
  string target = first(str);
  string tname = last(str);

  if ((match(target, "m", "mobile")) && (tname != ""))

  {
    mobdesc* the_mobdesc = mblst->get_mobdesc(tname);

    if (the_mobdesc != NULL)

    {
      mobile *mob = rm->get_zone()->add_loaded_mob(the_mobdesc->vnum, rm->get_id());
      echo("#gWith a slight gesture of your hand, you create " + mob->get_name() + ".#N");
      rm->xecho(this, "#g" + name + " has created " + mob->get_name() + " out of pure nothingness!#N");
      return;
    }

    echo("That mobile does not exist.");
    return;
  }

  if ((match(target, "i", "item")) && (tname != ""))

  {
    item *the_item = itmlst->get_item(tname);

    if (the_item != NULL)

    {
      item *new_item = itmlst->replicate(the_item->id, "Loaded by " + name + ".");
      echo("#gWith a slight gesture of your hand, you create#N " + new_item->lname + "#g.#N");
      rm->xecho(this, "#g" + name + " has created#N " + new_item->lname + " #gout of pure nothingness!#N");
      add_item_inventory(new_item);
      return;
    }

    echo("That item does not exist.");
    return;
  }

  echo("Load Usage#n: #R[#Nload mob mnum#R] [#Nload item inum#R]#N");
}

void entity::bestow(string str)

{
  string error_msg = "Bestow Usage#n:#N #R[#Nbestow ability_name upon player_name#R]#N";

  if ((str.find(" upon ") == string::npos) || (str.size() == 0)) {
    echo(error_msg);
    return; }

  int pos = str.find(" upon ");
  string aname = str.substr(0, pos);
  string pname = str.substr(pos+6, str.size()-pos-6);

  entity *target = rm->find_entity(this, pname);

  if (target == NULL) {
    echo("Nobody around by that name.");
    return; }

  skill *SK = skill_list.get(aname);
  spell *SP = spell_list.get(aname);

  if ((SK == NULL) && (SP == NULL)) {
    echo("There is no such skill or spell.");
    return; }

  int ID;
  string atype, lname;

  if (SK != NULL) { ID = SK->id;  atype = "skill";  lname = skill_list[ID].name; }
  if (SP != NULL) { ID = SP->id;  atype = "spell";  lname = spell_list[ID].name; }

  if ((target->skill_list[ID].learned == 1) && (atype == "skill")) {
    if (target == this) echo("You already know that skill.");
    else echo(target->get_name() + " already knows that skill.");
    return; }

  if ((target->spell_list[ID].learned == 1) && (atype == "spell")) {
    if (target == this) echo("You already know that spell.");
    else echo(target->get_name() + " already knows that spell.");
    return; }

  if (atype == "skill") target->skill_list.learn(ID, 0, 0);
  if (atype == "spell") target->spell_list.learn(ID, 0, 0);

  if (target == this) {
    echo("#cYou bestow the art of " + lname + " upon yourself.#N");
    return; }

  target->echo("\r\n#c" + name + " bestows upon you the art of " + lname + ".#N");
  echo("#cYou bestow upon " + target->get_name() + " the art of " + lname + ".#N");
}

void entity::purge(string str)

{
  int inventory_item = 0;
  string error_msg = "Purge Usage#n:#N #R[#Npurge player_name#R] [#Npurge item_name#R]#N";

  if (str == "") {
    echo(error_msg);
    return; }

  entity* ENT = rm->find_entity(str);

  if (ENT == this) {
    echo("You really shouldn't purge yourself.");
    return; }

  if (ENT != NULL)
  if (ENT->PLAYER)

  {
    if (ENT->get_level() >= get_level()) {
      echo("You aren't powerful enough to purge " + ENT->get_name() + ".");
      return; }

    ENT->Purge(this);
    return;
  }

  item *the_item = rm->find_item_room(str);

  if (the_item == NULL) {
    the_item = find_item_inventory(str);
    inventory_item = 1; }

  if (the_item != NULL)
  if (the_item->find_flag(FLAG_UNTOUCHABLE))
    the_item = NULL;

  if (the_item == NULL) {
    echo("There's no item like that around here.");
    return; }

  echo("#rYou purge " + the_item->lname + " #rwith a blast of fire!#N");
  rm->xecho(this, "#r" + name + " has purged " + the_item->lname + " #rwith a blast of fire!#N");

  if (inventory_item) remove_item_inventory(the_item);
  else rm->remove_item_room(the_item);
}

void entity::mark_legend(string target, string mark)

{
  if ((target == "") || (mark == "")) {
    echo("Mark Usage#n: #R[#Nmark player_name legend_mark#R]#N");
    return; }

  entity* ENT = rm->find_entity(this, target);

  if (ENT == NULL) {
    echo("Nobody here by that name.");
    return; }

  if (!ENT->PLAYER) {
    echo("Mobiles don't have legends.");
    return; }

  if (ENT == this) {
    Legend->add_mark(mark);
    echo("Ok.");
    return; }

  ENT->get_legend()->add_mark(mark);
  ENT->echo("#g" + name + " has etched a mark upon your legend!#N");
  echo("#gYou have etched a mark upon " + possessive(ENT->get_name()) + " legend!#N");
}

void entity::dc(string target)

{
  entity *ENT = clist->find_global_entity(target);

  if (target == "") {
    echo("Who do you want to disconnect?");
    return; }

  if (ENT == NULL) {
    echo("Nobody here by that name.");
    return; }

  if (!ENT->PLAYER)
  if (ENT->get_client() == NULL) {
    echo("Mobiles can't be disconnected.");
    return; }

  if (ENT == this) {
    echo("You can't disconnect yourself.");
    return; }

  if (ENT->get_client()->get_state() == CSTATE_LINKDEAD) {
    echo("They're already disconnected.");
    return; }

  if (ENT->get_level() >= get_level()) {
    echo("You aren't powerful enough to disconnect " + ENT->get_name() + ".");
    return; }

  ENT->get_client()->disconnect(1);
  ENT->get_client()->enter_ld_state();
}

void entity::delete_character(string target)

{
  client *the_client = clist->find_client(target);

  if (target == "") {
    echo("Who do you want to delete?");
    return; }

  if (the_client != NULL)

  {
    // Get rid of the client.
    return;
  }

  target = proper_name(target);
  int deleted = File->delete_profile(target);

  if (deleted) echo("You have deleted " + target + ".");
  else echo("There is no player file named " + target + ".");
}

void entity::freeze(string target, int cold)

{
  if (cold) echo("Freeze.");
  else echo("Unfreeze.");
}

void entity::disintegrate(string target)

{
  echo("Bam.");
}