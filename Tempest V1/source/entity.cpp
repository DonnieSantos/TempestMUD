#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

void entity::set_HR(int i)                  { HR = i;                              }
void entity::set_DR(int i)                  { DR = i;                              }
void entity::set_AC(int i)                  { AC = i;                              }
void entity::set_MR(int i)                  { MR = i;                              }
void entity::set_room(room *r)              { rm = r;                              }
void entity::set_gender(string s)           { gender = s;                          }
void entity::set_room(int i)                { rm = World->get_room(i);             }
void entity::set_client(client *aclient)    { my_client = aclient;                 }
void entity::set_playerstate(int i)         { playerstate = i;                     }
void entity::set_tracking(entity *e)        { tracking = e;                        }
void entity::set_tracking_dir(string s)     { tracking_dir = s;                    }
void entity::stop_tracking()                { tracking = NULL;  tracking_dir = ""; }
void entity::set_intermittent_skill(int i)  { intermittent_skill = i;              }
void entity::set_silent_cast(int i)         { silent_cast = i;                     }
void entity::set_student(string s)          { my_student = s;                      }
void entity::set_student_request(string s)  { my_student_request = s;              }
entity* entity::get_follower(int n)         { return followers[n];                 }

void entity::save()

{
  if (!PLAYER) return;
  if (my_client == NULL) return;

  File->save_profile(my_client);
  echo("#nCharacter information saved.#N");
}

void entity::quit()

{
  if (!PLAYER) return;
  if (my_client == NULL) return;

  if (playerstate == PSTATE_FIGHTING) echo("You're in the middle of a fight!");
  else if (my_client->get_state() == CSTATE_IMM_AT) echo("You can't do that.");
  else Quit();
}

void entity::lock_commands(int milliseconds)

{
  double ms = (double)milliseconds;
  ms = (ms / 1000);

  if (my_client != NULL)
    my_client->set_command_lock(ms);
}

void entity::say(string str)

{
  string s1 = allign("#nYou say '" + str + "#n'#N");
  string s2 = allign("#n" + name + " says, '" + str + "#n'#N");
  echo(s1, s2);

  if (rm->mob_occupied())
    rm->examine_say(s2);
}

void entity::respond(string str)

{
  if (my_client != NULL) {
    echo("You can't do that.");
    return; }

  string s = allign("#n" + name + " says, '" + str + "#n'#N");
  
  rm->prompt_all();
  rm->xecho(this, s);
}

void entity::chat(string str)

{
  string s = allign("#c[#n" + name + "#c] #n-> #g" + str + "#N");
  clist->xgecho(my_client, s);
  echo(s);
}

void entity::ctell(string str)

{
  clan *the_clan = World->get_clanlist()->get_clan(get_clan());

  if (the_clan == NULL) {
    echo("You are not in a clan.");
    return; }

  if (the_clan->get_rank(name) == 0) {
    echo("You are not a member of the clan yet.");
    return; }

  if (str == "") {
    echo("What do you want to clantell?");
    return; }

  string s1 = allign("#mYou tell your clan '" + str + "#m'#N");
  string s2 = allign("#m" + name + " tells your clan, '" + str + "#m'#N");

  echo(s1);
  clist->clan_xecho(my_client, get_clan(), s2);
}

void entity::tell(string target, string message)

{
  string tempstr;

  if (message == "") {
    echo("What do you want to tell them?");
    return; }

  for (int i=0; i<clist->size(); i++)

  {
    if (target == clist->get_client(i)->get_char()->get_lname())

    {
      if (clist->get_client(i)->get_state() == CSTATE_LINKDEAD)
        echo(clist->get_client(i)->get_char()->get_name() + " is linkdead.");

      else if (clist->get_client(i)->get_state() == CSTATE_WRITING)
        echo(clist->get_client(i)->get_char()->get_name() + " is writing a message.");

      else

      {
        tempstr = "#CYou tell " + clist->get_client(i)->get_char()->get_name() + ", '" + message + "#C'#N";
        echo(allign(tempstr));

        tempstr = "#C" + get_name() + " tells you, '" + message + "#C'#N";
        clist->get_client(i)->put_output(allign(tempstr));
      }

      return;
    }
  }

  for (int i=0; i<clist->size(); i++)

  {
    if (short_for(target, clist->get_client(i)->get_char()->get_lname()))

    {
      if (clist->get_client(i)->get_state() == CSTATE_LINKDEAD)
        echo(clist->get_client(i)->get_char()->get_name() + " is linkdead.");

      else if (clist->get_client(i)->get_state() == CSTATE_WRITING)
        echo(clist->get_client(i)->get_char()->get_name() + " is writing a message.");

      else

      {
        tempstr = "#CYou tell " + clist->get_client(i)->get_char()->get_name() + ", '" + message + "#C'#N";
        echo(allign(tempstr));

        tempstr = "#C" + get_name() + " tells you, '" + message + "#C'#N";
        clist->get_client(i)->put_output(allign(tempstr));
      }

      return;
    }
  }

  echo("There is nobody around by that name.");
}

void entity::echo(string str)

{
  if (my_client != NULL)
    my_client->put_output(str);
}

void entity::echo(string s1, string s2)

{
  echo(s1);
  rm->xecho(this, s2);
}

void entity::vdist_echo(entity* ENT, string s1, string s2)

{
  if (ENT == NULL) return;

  if (ENT->get_room() == rm) ENT->echo(s1);
  else ENT->echo(s2);
}

void entity::gecho(string str)

{
  clist->gecho(my_client, str);
}

void entity::gecho(string s1, string s2)

{
  echo(s1);
  clist->xgecho(my_client, s2);
}

void entity::emote(string str)

{
  str = rm_allign(name + " " + str);
  echo(str);
  rm->xecho(this, str);
}

void entity::blind_emote(string str)

{
  str = rm_allign(name + " " + str);
  rm->xecho(this, str);
}

void entity::xblind_emote(entity *ENT, string str)

{
  str = rm_allign(name + " " + str);
  rm->xecho(this, ENT, str);
}

void entity::xblind_emote(entity *ENT, entity *ENT2, string str)

{
  str = rm_allign(name + " " + str);
  rm->xecho(this, ENT, ENT2, str);
}

void entity::pblind_emote(string str)

{
  str = rm_allign(name + " " + str);
  rm->pblind_echo(this, str);
}

void entity::add_string(string str)

{
  string_stack += "\r\n" + str;
}

void entity::echo_strings()

{
  if (string_stack.length() > 0) string_stack.erase(0,2);

  if ((my_client != NULL) && (string_stack != ""))
    my_client->put_output(string_stack);

  string_stack = "";
}

void entity::flush_strings()

{
  string_stack = "";
}

void entity::genderset()

{
  if (gender == "male") {
    He = "He";
    he = "he";
    His = "His";
    his = "his";
    Him = "Him";
    him = "him";
    Himself = "Himself";
    himself = "himself"; }

  else {
    He = "She";
    he = "she";
    His = "Her";
    his = "her";
    Him = "Her";
    him = "her";
    Himself = "Herself";
    himself = "herself"; }
}

void entity::set_target(entity *targ)

{
  if (combat_target == targ)
    return;

  if (combat_target == NULL)
    combat_target = targ;

  else

  {
    for (int i=0; i<num_targets; i++)
      if (target_list[i] == targ)
        return;

    num_targets++;
    entity* *temp = new entity* [num_targets];

    for (int i=0; i<num_targets-1; i++)
      temp[i] = target_list[i];

    temp[num_targets-1] = targ;

    target_list = temp;
  }
}

entity* entity::remove_target(entity *targ)

{
  int is_target = 0;

  if (combat_target == targ)
    is_target = 1;

  for (int i=0; i<num_targets; i++)
    if (target_list[i] == targ)
      is_target = 1;

  if (!is_target) return NULL;

  if ((combat_target == targ) && (num_targets == 0)) {
    combat_target = NULL;
    return NULL; }

  else if ((combat_target == targ) && (num_targets > 0)) {
    combat_target = remove_target(target_list[0]);
    return combat_target; }

  else

  {
    num_targets--;
    entity* *temp = new entity* [num_targets];
    int found = 0;

    for (int i=0; i<=num_targets; i++)

    {
      if (target_list[i] != targ)
        temp[i-found] = target_list[i];

      else found = 1;
    }

    target_list = temp;
  }

  return targ;
}

void entity::clear_targets()

{
  combat_target = NULL;

  while (num_targets > 0)
    remove_target(target_list[0]);
}

int entity::targeting_group(group *grp)

{
  if (combat_target != NULL)
  if (grp->in_group(combat_target))
    return 1;

  for (int i=0; i<num_targets; i++)
    if (grp->in_group(target_list[i]))
      return 1;

  return 0;
}

void entity::stat(string target)

{
  if (target == name) {
    echo(stat());
    return; }

  if (get_level() < 100) {
    echo("You can't do that.");
    return; }

  entity *ENT = rm->find_entity(target);
  if (ENT == NULL) ENT = clist->find_character(target);
  if (ENT == NULL) ENT = clist->find_global_entity(target);

  if (ENT == NULL) {
    echo("Nobody around by that name.");
    return; }

  echo(ENT->stat());
}

void entity::look(string pretext)

{
  int no_exit = 1;
  string tempstr = "", VN;

  tempstr += rm->display_item_list();
  tempstr += rm->get_look_titles(this);

  tempstr = tempstr + "\r\n" + "[Exits: ";
  if (rm->get_N()) { tempstr = tempstr + "#Cn#N"; no_exit = 0; }
  if (rm->get_E()) { tempstr = tempstr + "#Ce#N"; no_exit = 0; }
  if (rm->get_S()) { tempstr = tempstr + "#Cs#N"; no_exit = 0; }
  if (rm->get_W()) { tempstr = tempstr + "#Cw#N"; no_exit = 0; }
  if (rm->get_U()) { tempstr = tempstr + "#Cu#N"; no_exit = 0; }
  if (rm->get_D()) { tempstr = tempstr + "#Cd#N"; no_exit = 0; }
  if (no_exit) tempstr += "#CNone#N";
  tempstr = tempstr + "]";

  if (get_level() < 100) VN = "";
  else VN = "#n (#c" + intconvert(rm->get_id()) + "#n)#N";

  if (pretext.find("TRACKING", 0) != string::npos) {
    pretext.erase(0,8);
    tempstr += "\r\n\n" + track(tracking); }

  if (pretext != "") echo(pretext + "\r\n\n" + rm->get_title() + VN + "\r\n" + rm->get_desc() + tempstr);
  else echo(rm->get_title() + VN + "\r\n" + rm->get_desc() + tempstr);
}

void entity::look_at(string target)

{
  if ((key(target) == "i") || (key(target) == "in")) {
    display_item_container(last(target));
    return; }

  if (find_item_all(target) != NULL) {
    echo(find_item_all(target)->look_desc);
    return; }

  target = key(target);
  entity *ENT = rm->find_entity(this, target);

  if (target == "board") read("board");

  else if ((target == "n") || (target == "north"))   echo(rm->get_look(0));
  else if ((target == "s") || (target == "south"))   echo(rm->get_look(1));
  else if ((target == "e") || (target == "east"))    echo(rm->get_look(2));
  else if ((target == "w") || (target == "west"))    echo(rm->get_look(3));
  else if ((target == "d") || (target == "down"))    echo(rm->get_look(4));
  else if ((target == "u") || (target == "up"))      echo(rm->get_look(5));

  else if (ENT != NULL) {
    echo(ENT->get_desc() + "\r\n" + ENT->display_equipment(1));
    if (ENT != this) ENT->echo(name + " looks at you.");
    if (name != ENT->get_name()) rm->xecho(this, ENT, name + " looks at " + ENT->get_name() + "."); }

  else if ((rm->get_numlooks() > 6) && (rm->search_looks(target)))
    echo(rm->get_look(rm->search_looks(target)));

  else if ((target == "no") || (target == "nor") || (target == "nort"))   echo(rm->get_look(0));
  else if ((target == "so") || (target == "sou") || (target == "sout"))   echo(rm->get_look(1));
  else if ((target == "ea") || (target == "eas"))   echo(rm->get_look(2));
  else if ((target == "we") || (target == "wes"))   echo(rm->get_look(3));
  else if ((target == "do") || (target == "dow"))   echo(rm->get_look(4));

  else echo("There is no " + target + " here.");
}

void entity::read(string str)

{
  item* item_pointer = rm->find_item_room(str);

  if (number(str))

  {
    item_pointer = rm->find_item_room("board");

    if (item_pointer == NULL) {
      echo("There's no message board here.");
      return; }

    echo(item_pointer->board_pointer->get_note(stringconvert(str)));
    return;
  }

  if (item_pointer == NULL)

  {
    item_pointer = find_item_inventory(str);
    if (item_pointer == NULL) {
      echo("You don't see a message board here.");
      return; }
  }

  if (!item_pointer->find_type(ITEM_BOARD)) {
    echo("That doesn't look like a message board.");
    return; }

  echo(item_pointer->board_pointer->display_list());
}

void entity::remove(string str)

{
  if (number(str))

  {
    item* item_pointer = rm->find_item_room("board");

    if (item_pointer == NULL) {
      echo("There's no message board here.");
      return; }

   string temp = item_pointer->board_pointer->remove_note(str, get_name(), get_level());

   if (temp == "Message Removed.")
    echo(temp, name + " has removed message " + str + ".");
    else echo(temp);
  }

  else unequip(str);
}

string entity::try_move(string dir)

{
  string temp = rm->check_blockers(this, dir[0]);

  if (temp != "OK") return temp;

  else if ((PLAYER) && (my_client == NULL))
    return "Unposessed Immortal";

  else if (get_playerstate() == PSTATE_FIGHTING)
    return "You're in the middle of a fight!";

  else if (my_client != NULL)
    if (my_client->get_state() == CSTATE_IMM_AT)
      return "You can't do that.";

  return "OK";
}

int entity::move(string dir)

{
  int target;
  cmdqueue temp_queue;
  string direction, incoming;

  if (dir == "N")      { target = rm->get_N();  direction = "north";  incoming = "in from the south"; }
  else if (dir == "S") { target = rm->get_S();  direction = "south";  incoming = "in from the north"; }
  else if (dir == "E") { target = rm->get_E();  direction = "east";   incoming = "in from the west";  }
  else if (dir == "W") { target = rm->get_W();  direction = "west";   incoming = "in from the east";  }
  else if (dir == "U") { target = rm->get_U();  direction = "up";     incoming = "up from below";     }
  else if (dir == "D") { target = rm->get_D();  direction = "down";   incoming = "down from above";   }

  room* last = rm;
  room* current = World->get_room(target);
  string walks = name + " walks " + direction + ".";
  string walksin = name + " walks " + incoming + ".";
  string youwalk = "You walk " + direction + ".";
  string otherswalk;

  string leader_move = try_move(dir);

  if (target == 0) {
    echo("There is no exit in that direction.");
    return 0; }

  if (leader_move != "OK") {
    echo (leader_move);
    return 0; }

  for (int i=0; i<num_followers; i++)

  {
    string temp = followers[i]->try_move(dir);

    if ((followers[i]->get_room() == last) && (temp == "OK")) {
      walks += "\r\n" + followers[i]->get_name() + " walks " + direction + ".";
      walksin += "\r\n" + followers[i]->get_name() + " walks " + incoming + ".";
      youwalk += "\r\n" + followers[i]->get_name() + " follows you.";
      last->loss(followers[i]);
      followers[i]->set_room(current);
      current->gain(followers[i]);
      temp_queue.put(followers[i]->get_name()); }
  }

  rm->loss(this);
  rm = current;
  rm->gain(this);

  if (dir == tracking_dir) youwalk = "TRACKING" + youwalk;
  else stop_tracking();

  look(youwalk);
  last->pblind_echo(this, walks);
  current->pblind_echo(this, walksin);

  for (int i=0; i<num_followers; i++)

  {
    if (temp_queue.search(followers[i]->get_name()))

    {
      otherswalk = name + " walks " + direction + ".";

      for (int j=0; j<num_followers; j++)
        if ((followers[j]->get_name() != followers[i]->get_name()) && (temp_queue.search(followers[j]->get_name())))
          otherswalk += "\r\n" + followers[j]->get_name() + " walks " + direction + ".";

      if (dir == followers[i]->get_tracking_dir())
        followers[i]->look("TRACKING" + otherswalk + "\r\nYou follow " + name + " " + direction + ".");

      else {
        followers[i]->look(otherswalk + "\r\nYou follow " + name + " " + direction + ".");
        followers[i]->stop_tracking(); }
    }

    else if (followers[i]->get_room() == last) {
      string temp = followers[i]->try_move(dir);
      if (temp != "You're in the middle of a fight!")
      followers[i]->echo(walks + "\r\n" + temp);
      else followers[i]->echo(walks); }

    else if (followers[i]->get_room() == current)
      followers[i]->echo(walksin);
  }

  return target;
}

void entity::speedwalk(string str)

{
  if (my_client == NULL) return;

  if (playerstate == PSTATE_FIGHTING) {
    echo("You're in the middle of a fight!");
    return; }

  if (str.size() == 0) return;

  if (str.size() == 1) {
    my_client->put_command(str);
    return; }

  string s = str.substr(1, 1);
  string num = str.substr(0, 1);

  if (number(num)) {
    str[0] = intconvert((stringconvert(num)-1))[0];
    if (str[0] == '1') str.erase(0, 1);
    else if (str[0] == '0') str.erase(0, 2); }

  else {
    str = str.substr(1, str.size()-1);
    s = num; }

  move(uppercase(s));

  my_client->force_command(str);
}

string entity::hitpoint_meter()

{
  if (combat_target == NULL) return "";

  string str = possessive(combat_target->get_name()) + " health: #n[";
  float hpmeter = ((float)combat_target->get_current_hp() / (float)combat_target->get_max_hp());
  float inthpmeter = ((hpmeter * 100) / 10);

  for (int i=1; i<=10; i++)

  {
    if (i <= inthpmeter) {
      if (inthpmeter > 5) str += "#g=#N";
      else if (inthpmeter > 2) str += "#y=#N";
      else if (inthpmeter > 0) str += "#r=#N"; }

    else str += "#N-#N";
  }

  str += "#n]#N";

  return str;
}

void entity::update_claninfo()

{
  clan *the_clan = World->get_clanlist()->get_clan(get_clan());

  if (the_clan == NULL) {
    set_clan("None");
    return; }

  if (the_clan->get_rank(name) == -1) {
    set_clan("None");
    return; }

  the_clan->set_class(name, get_class());
}

void entity::update_religioninfo()

{
  religion *the_religion = World->get_religionlist()->get_religion(get_religion());

  if (the_religion == NULL) {
    set_religion("None");
    return; }

  if (the_religion->get_rank(name) == -1) {
    set_religion("None");
    return; }

  the_religion->set_class(name, get_class());
}

void entity::open_blocker(string str)

{
  item *the_item = rm->find_item_room(str);

  if (the_item == NULL) {
    echo("You don't see anything like that around here.");
    return; }

  if (DC_Blocker(the_item) == NULL) {
    echo(the_item->name + " #Ncannot be opened.");
    return; }

  DC_Blocker(the_item)->try_open(this);
}

void entity::close_blocker(string str)

{
  item *the_item = rm->find_item_room(str);

  if (the_item == NULL) {
    echo("You don't see anything like that around here.");
    return; }

  if (DC_Blocker(the_item) == NULL) {
    echo(the_item->name + " #Ncannot be closed.");
    return; }

  DC_Blocker(the_item)->try_close(this);
}