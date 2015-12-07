#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

ghost::ghost(entity* ENT, item* ip)

{
  my_client = NULL;
  Aliases = NULL;
  PLAYER = 0;
  owner = ENT;
  rm = ENT->get_room();
  num_items = 0;
  outcome = "";
  source = ip;
}

void ghost::gat(int action_type)

{
  for (int i=0; i<source->num_commands[action_type]; i++)
    if (source->action_commands[action_type][i] == "TEST")

    {
      if (outcome.find(source->action_conditions[action_type]) == string::npos)
        for (int j=0; j<source->num_fail[action_type]; j++)
          gat(source->fail_locations[action_type][j], source->fail_commands[action_type][j]);

      else
        for (int i=0; i<source->num_succ[action_type]; i++)
          gat(source->succ_locations[action_type][i], source->succ_commands[action_type][i]);

      return;
    }

  else gat(source->command_location[action_type][i], source->action_commands[action_type][i]);
}

void ghost::gat(int rnum, string action)

{
  if (rnum == 0) rm = owner->get_room();
  else rm = World->get_room(rnum);

  if (rm == NULL) return;

  interpret_command(action);
}

void ghost::echo_room(string str)

{
  for (int i=0; i<rm->get_size(); i++)
    if (rm->get_ent(i)->get_client() != NULL)
      rm->get_ent(i)->add_string(str);
}

void ghost::echo_owner(string str)

{
  owner->add_string(str);
}

void ghost::echo_room_exclude(string str)

{
  for (int i=0; i<rm->get_size(); i++)
    if (rm->get_ent(i)->get_client() != NULL)
    if (rm->get_ent(i) != owner)
      rm->get_ent(i)->add_string(str);
}

void ghost::echo_output(int action_type)

{
  room* temp_room;
  int loc;

  for (int i=0; i<source->num_commands[action_type]; i++)

  {
    loc = source->command_location[action_type][i];

    if (loc == 0) temp_room = owner->get_room();
    else temp_room = World->get_room(loc);

    if (temp_room != NULL) temp_room->echo_strings();
  }

  for (int i=0; i<source->num_fail[action_type]; i++)

  {
    loc = source->fail_locations[action_type][i];

    if (loc == 0) temp_room = owner->get_room();
    else temp_room = World->get_room(loc);

    if (temp_room != NULL) temp_room->echo_strings();
  }

  for (int i=0; i<source->num_succ[action_type]; i++)

  {
    loc = source->succ_locations[action_type][i];

    if (loc == 0) temp_room = owner->get_room();
    else temp_room = World->get_room(loc);

    if (temp_room != NULL) temp_room->echo_strings();
  }
}

void ghost::echo(string s)

{
  outcome = s;
}

int ghost::get_type()                               { return ENTITY_GHOST; }
int ghost::get_level()                              { return 100;          }
int ghost::check_targets(string)                    { return 0;            }
int ghost::get_followable()                         { return 0;            }
int ghost::get_autoassist()                         { return 0;            }
int ghost::get_natural_hp()                         { return 0;            }
int ghost::get_natural_mana()                       { return 0;            }
int ghost::get_natural_move()                       { return 0;            }
int ghost::get_max_hp()                             { return 0;            }
int ghost::get_current_mana()                       { return 0;            }
int ghost::get_max_mana()                           { return 0;            }
int ghost::get_current_move()                       { return 0;            }
int ghost::get_max_move()                           { return 0;            }
int ghost::get_experience()                         { return 0;            }
int ghost::get_exp_needed()                         { return 0;            }
int ghost::get_last_experience()                    { return 0;            }
int ghost::get_STR()                                { return 0;            }
int ghost::get_DEX()                                { return 0;            }
int ghost::get_CON()                                { return 0;            }
int ghost::get_INT()                                { return 0;            }
int ghost::get_WIS()                                { return 0;            }
int ghost::get_HR()                                 { return 0;            }
int ghost::get_DR()                                 { return 0;            }
int ghost::get_AC()                                 { return 0;            }
int ghost::get_MR()                                 { return 0;            }
int ghost::get_gold()                               { return 0;            }
int ghost::get_dpoints()                            { return 0;            }
int ghost::get_sanc()                               { return 0;            }
string ghost::stat()                                { return "";           }
string ghost::get_lname()                           { return "";           }
string ghost::get_prename()                         { return "";           }
string ghost::get_title()                           { return "";           }
string ghost::get_class()                           { return "";           }
string ghost::get_sclass()                          { return "";           }
string ghost::get_ssclass()                         { return "";           }
string ghost::get_clan()                            { return "";           }
string ghost::get_religion()                        { return "";           }
string ghost::get_password()                        { return "";           }
string ghost::get_hometown()                        { return "";           }
string ghost::merchant_item_list()                  { return "";           }
string ghost::get_desc()                            { return "";           }
string ghost::get_plan()                            { return "";           }
void ghost::set_desc(string)                        { return;              }
void ghost::set_plan(string)                        { return;              }
void ghost::set_title(string, int)                  { return;              }
void ghost::set_prename(string, int)                { return;              }
void ghost::level_up()                              { return;              }
void ghost::level_up(int)                           { return;              }
void ghost::add(string)                             { return;              }
void ghost::set_name(string)                        { return;              }
void ghost::restore()                               { return;              }
void ghost::regen()                                 { return;              }
void ghost::set_level(int)                          { return;              }
void ghost::set_current_hp(int)                     { return;              }
void ghost::set_max_hp(int)                         { return;              }
void ghost::set_current_mana(int)                   { return;              }
void ghost::set_max_mana(int)                       { return;              }
void ghost::set_current_move(int)                   { return;              }
void ghost::set_max_move(int)                       { return;              }
void ghost::set_experience(int)                     { return;              }
void ghost::set_password(string)                    { return;              }
void ghost::set_hometown(string)                    { return;              }
void ghost::set_class(string)                       { return;              }
void ghost::set_clan(string)                        { return;              }
void ghost::set_religion(string)                    { return;              }
void ghost::set_exp_needed()                        { return;              }
void ghost::set_STR(int)                            { return;              }
void ghost::set_DEX(int)                            { return;              }
void ghost::set_CON(int)                            { return;              }
void ghost::set_INT(int)                            { return;              }
void ghost::set_WIS(int)                            { return;              }
void ghost::set_gold(int)                           { return;              }
void ghost::set_dpoints(int)                        { return;              }
void ghost::toggle_follow()                         { return;              }
void ghost::toggle_assist()                         { return;              }
void ghost::set_followable(int)                     { return;              }
void ghost::set_autoassist(int)                     { return;              }
void ghost::receive_experience(entity*)             { return;              }
void ghost::gain_experience(int)                    { return;              }
void ghost::no_experience()                         { return;              }
void ghost::die(int)                                { return;              }
void ghost::examine_say(string)                     { return;              }
void ghost::merchant_sell_item(item*, entity*)      { return;              }
void ghost::merchant_buy_item(item*, entity*)       { return;              }
void ghost::echo(string, string)                    { return;              }
void ghost::cecho(string)                           { return;              }
void ghost::vdist_echo(entity*, string, string)     { return;              }
void ghost::gecho(string)                           { return;              }
void ghost::gecho(string, string)                   { return;              }
void ghost::emote(string)                           { return;              }
void ghost::blind_emote(string)                     { return;              }
void ghost::xblind_emote(entity*, string)           { return;              }
void ghost::xblind_emote(entity*, entity*, string)  { return;              }
void ghost::pblind_emote(string)                    { return;              }
void ghost::add_string(string)                      { return;              }
void ghost::echo_strings()                          { return;              }
void ghost::flush_strings()                         { return;              }
mobdesc* ghost::get_info()                          { return NULL;         }
item* ghost::merchant_find_item(int, string)        { return NULL;         }
blocker* ghost::get_blocker()                       { return NULL;         }