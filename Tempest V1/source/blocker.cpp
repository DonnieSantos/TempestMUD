#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

string blocker::error_msg()            { return error_message; }
void blocker::set_btype(string btype)  { blocker_type = btype; }
void blocker::set_bdir(char dir)       { direction = dir;      }
string blocker::get_btype()            { return blocker_type;  }
char blocker::get_bdir()               { return direction;     }

blocker::blocker()

{
  CL = NULL;
  direction = 'X';
  error_message = "";
  Owner_Mob = NULL;
  clan_name = "";
  open_gate = -1;
  INITIALIZED = 0;
}

blocker::blocker(mobile *owner)

{
  CL = NULL;
  direction = 'X';
  error_message = "";
  Owner_Mob = owner;
  clan_name = "";
  open_gate = -1;
  INITIALIZED = 0;
}

void blocker::initialize()

{
  INITIALIZED = 1;

  CL = World->get_clanlist();

  error_message = "Blocker Error Message.";

  if ((blocker_type.find("gate") != string::npos) || (blocker_type.find("door") != string::npos)) {
    error_message = name + " #Nis closed.";
    open_gate = 0; }

  if (blocker_type.find("clan") != string::npos)

  {
    int pos1 = blocker_type.find("clan");
    blocker_type.erase(pos1,6);
    int pos2 = blocker_type.find("]");
    clan_name = blocker_type.substr(pos1, pos2-pos1);
    blocker_type.erase(pos1, pos2-pos1);
    blocker_type = clear_whitespace(blocker_type);

    if (CL->get_clan(clan_name) != NULL)
      error_message = ("Only members of " + CL->get_clan(clan_name)->get_name() + " may pass through here.");
  }

  if (blocker_type.find("obstacle") != string::npos)

  {
    if (Owner_Mob != NULL) error_message = Owner_Mob->get_name() + " blocks your way.";
    else error_message = name + " blocks your way.";
  }

  if (blocker_type.find("immortal") != string::npos)
    error_message = "Mortals cannot pass through here.";
}

int blocker::try_pass(entity *ENT)

{
  if (!INITIALIZED) initialize();

  if (open_gate == 0) return 0;

  if (blocker_type.find("obstacle") != string::npos)
    return 0;

  if (blocker_type.find("immortal") != string::npos)
  if (ENT->get_level() < 100)
    return 0;

  if (clan_name != "")

  {
    if (CL->get_clan(clan_name) != NULL)
    if (CL->get_clan(clan_name)->get_rank(ENT->get_name()) <= 0) return 0;
  }

  return 1;
}

int blocker::try_open(entity *ENT)

{
  if (!INITIALIZED) initialize();

  if (open_gate == -1) {
    ENT->echo(name + " #Ncannot be opened or closed.");
    return 0; }

  if (open_gate == 1) {
    ENT->echo("It's already open!");
    return 0; }

  if (open_gate == 0)

  {
    open_gate = 1;
    ENT->echo("You open " + lname + "#N.");
    rm->xecho(ENT, ENT->get_name() + " opens " + lname + "#N.");
    open_descriptions();
    return 1;
  }

  return 0;
}

int blocker::try_close(entity *ENT)

{
  if (!INITIALIZED) initialize();

  if (open_gate == -1) {
    ENT->echo(name + " #Ncannot be closed or open.");
    return 0; }

  if (open_gate == 0) {
    ENT->echo("It's already closed!");
    return 0; }

  if (open_gate == 1)

  {
    open_gate = 0;
    ENT->echo("You close " + lname + "#N.");
    rm->xecho(ENT, ENT->get_name() + " closes " + lname + "#N.");
    close_descriptions();
    return 1;
  }

  return 0;
}

void blocker::open_descriptions()

{
  name = all_word_replace(name, "closed", "open");
  name = all_word_replace(name, "Closed", "Open");
  name = all_word_replace(name, "CLOSED", "OPEN");

  lname = all_word_replace(lname, "closed", "open");
  lname = all_word_replace(lname, "Closed", "Open");
  lname = all_word_replace(lname, "CLOSED", "OPEN");

  look_desc = all_word_replace(look_desc, "closed", "open");
  look_desc = all_word_replace(look_desc, "Closed", "Open");
  look_desc = all_word_replace(look_desc, "CLOSED", "OPEN");

  ground_desc = all_word_replace(ground_desc, "closed", "open");
  ground_desc = all_word_replace(ground_desc, "Closed", "Open");
  ground_desc = all_word_replace(ground_desc, "CLOSED", "OPEN");
}

void blocker::close_descriptions()

{
  name = all_word_replace(name, "open", "closed");
  name = all_word_replace(name, "Open", "Closed");
  name = all_word_replace(name, "OPEN", "CLOSED");

  lname = all_word_replace(lname, "open", "closed");
  lname = all_word_replace(lname, "Open", "Closed");
  lname = all_word_replace(lname, "OPEN", "CLOSED");

  look_desc = all_word_replace(look_desc, "open", "closed");
  look_desc = all_word_replace(look_desc, "Open", "Closed");
  look_desc = all_word_replace(look_desc, "OPEN", "CLOSED");

  ground_desc = all_word_replace(ground_desc, "open", "closed");
  ground_desc = all_word_replace(ground_desc, "Open", "Closed");
  ground_desc = all_word_replace(ground_desc, "OPEN", "CLOSED");
}