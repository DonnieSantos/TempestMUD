#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

int clientlist::size()                 { return SIZE;    }
client* clientlist::get(int n)         { return list[n]; }
client* clientlist::get_client(int n)  { return list[n]; }

clientlist::clientlist()

{
  SIZE = 0;
  list = new client* [1];

  mobtimer = 0;
  ticktimer = 0;
  roundtimer = 0;
  locktimer = 0;

  mobtime = ANIMATE_TICK;
  ticktime = random_int(MINIMUM_TICK, MAXIMUM_TICK);
  roundtime = FIGHT_TICK_UPDATE;
  locktime = COMMAND_LOCK_UPDATE;

  tick_count = 1;
  prev = clock();
}

clientlist::~clientlist()

{
  delete [] list;
}

client* clientlist::insert()

{
  SIZE++;

  client* *temp = new client* [SIZE];

  for (int i=0; i<SIZE-1; i++)
    temp[i] = list[i];

  temp[SIZE-1] = new client();

  delete [] list;
  list = temp;

  return temp[SIZE-1];
}

int clientlist::remove(client *aclient)

{
  SIZE--;

  if (SIZE < 0) { SIZE = 0;  return 0; }

  client* *temp = new client* [SIZE];
  int found = 0;

  for (int i=0; i<=SIZE; i++)

  {
    if (list[i] != aclient)
      temp[i-found] = list[i];

    else found = 1;
  }

  if (found == 1)

  {
    delete [] list;
    list = temp;
    return 1;
  }

  return 0;
}

client* clientlist::find_client(string name)

{
  for (int i=0; i<SIZE; i++)
    if (list[i]->get_char()->get_name() == name)
      return list[i];

  return NULL;
}

client* clientlist::find_client(string name, client *aclient)

{
  for (int i=0; i<SIZE; i++)
    if ((list[i]->get_char()->get_name() == name) && (list[i] != aclient))
      return list[i];

  return NULL;
}

entity* clientlist::find_character(string name)

{
  name = proper_name(name);

  for (int i=0; i<SIZE; i++)
  if (list[i]->get_char()->get_name() == name)
  if ((list[i]->get_state() != CSTATE_LOGIN) && (list[i]->get_state() != CSTATE_POSSESSION))
  if (list[i]->get_char()->PLAYER)
    return list[i]->get_char();

  return NULL;
}

void clientlist::gecho(client *aclient, string str)

{
  aclient->put_output(str);
  xgecho(aclient, str);
}

void clientlist::xgecho(client *aclient, string str)

{
  for (int i=0; i<SIZE; i++)
    if (list[i] != aclient)
      list[i]->put_output(str);
}

void clientlist::clan_xecho(client *aclient, string cname, string str)

{
  for (int i=0; i<SIZE; i++)
  if (list[i] != aclient)

  {
    entity *ENT = list[i]->get_char();

    if (ENT->get_clan() == cname)
      if (World->get_clanlist()->get_clan(cname)->get_rank(ENT->get_name()) > 0)
        list[i]->put_output(str);
  }
}

void clientlist::clan_xecho(client *aclient, client *bclient, string cname, string str)

{
  for (int i=0; i<SIZE; i++)
  if ((list[i] != aclient) && (list[i] != bclient))

  {
    entity *ENT = list[i]->get_char();

    if (ENT->get_clan() == cname)
      if (World->get_clanlist()->get_clan(cname)->get_rank(ENT->get_name()) > 0)
        list[i]->put_output(str);
  }
}

string clientlist::who()

{
  string tempstring = "", xs;
  int count = 0;

  for (int i=0; i<SIZE; i++)
  if ((list[i]->get_state() != CSTATE_LOGIN) && (list[i]->get_char()->PLAYER))

  {
    xs = "";
    if (list[i]->get_char()->get_level() < 100) xs += " ";
    if (list[i]->get_char()->get_level() < 10) xs += " ";
    tempstring += "[#Y " + xs + intconvert(list[i]->get_char()->get_level()) + " ";
    tempstring += list[i]->get_char()->get_sclass() + " ] " + list[i]->get_char()->get_prename() + "#C";
    tempstring += list[i]->get_char()->get_name() + "#N " + list[i]->get_char()->get_title() + "#N\n\r";
    count++;
  }

  tempstring += "\r\nNumber of players: " + intconvert(count);

  return tempstring;
}

entity* clientlist::find_global_entity(string target)

{
  int target_num = clip_number(&target);

  for (int i=0; i<SIZE; i++)

  {
    if ((short_for(target, list[i]->get_char()->get_lname()))
    && (list[i]->get_state() != CSTATE_LOGIN)
    && (list[i]->get_state() != CSTATE_POSSESSION))
      target_num--;

    if (!target_num)
      return list[i]->get_char();
  }

  for (int i=0; i<=World->get_maxzone(); i++)
  if  (World->get_zone(i) != NULL)
  for (int j=0; j<World->get_zone(i)->get_popsize(); j++)

  {
    if (World->get_zone(i)->get_mob(j) != NULL)
    if (World->get_zone(i)->get_mob(j)->check_targets(target))
      target_num--;

    if (!target_num)
      return World->get_zone(i)->get_mob(j);
  }

  return NULL;
}

void clientlist::tick()

{
  for (int i=0; i<SIZE; i++)

  {
    list[i]->update_idle();

    if (list[i]->get_ld() > 0)
      list[i]->set_ld(list[i]->get_ld() + 1);

    if (list[i]->get_state() == CSTATE_NORMAL)
      list[i]->get_char()->regen();

    if (list[i]->get_state() != CSTATE_LOGIN)
      if (tick_count == 3)
        if (list[i]->get_char()->PLAYER)
          File->save_profile(list[i]);

    if (list[i]->get_state() != CSTATE_LOGIN) {
      string lost_spells = list[i]->get_char()->get_effects()->decrement();
      if (lost_spells != "") list[i]->put_output(lost_spells); }

    World->update_decaylist();
  }
}

void clientlist::mud_timer()

{
  updated = clock();
  double elapsed = ((double)(updated - prev) / CLOCKS_PER_SEC);
  prev = updated;

  mobtimer  += elapsed;
  ticktimer += elapsed;
  roundtimer += elapsed;
  locktimer += elapsed;

  if (mobtimer >= mobtime) {
    World->animate();
    mobtimer = 0; }

  if (ticktimer >= ticktime) {
    tick();
    tick_count++;
    if (tick_count == 4) tick_count = 1;
    ticktimer = 0;
    ticktime = random_int(MINIMUM_TICK, MAXIMUM_TICK); }

  if (locktimer >= locktime) {
    update_command_locks(locktimer);
    locktimer = 0; }

  if (roundtimer >= roundtime) {
    World->update_fights(roundtimer);
    roundtimer = 0; }
}

void clientlist::update_command_locks(double elapsed)

{
  for (int i=0; i<SIZE; i++)
    if (list[i]->get_command_lock() != 0)
      list[i]->update_command_lock(elapsed);
}

void clientlist::spin()

{
  string datarecvd;
  int OK;

  mud_timer();

  for (int i=0; i<SIZE; i++)
  if ((list[i]->get_state() != CSTATE_LOGIN) && (list[i]->get_state() != CSTATE_WRITING))

  {
    OK = 0;
    datarecvd = "";

    if (!list[i]->get_ld())

    {
      datarecvd = list[i]->get_commandstring();

      while (!OK)

      {
        list[i]->getbufferdata();
        if (datarecvd == list[i]->get_commandstring()) OK = 1;
        else datarecvd = list[i]->get_commandstring();
      }

      if ((list[i]->get_Qsize() > 0) && (list[i]->get_command_lock() <= 0))

      {
        datarecvd = list[i]->get_command();
        if (datarecvd != "") list[i]->get_char()->interpret_command(datarecvd);
        else if (!list[i]->get_ANSI_MODE()) list[i]->msg(list[i]->prompt());
      }

      if (i < SIZE) list[i]->flush_output();
    }

    else list[i]->update_ld_state();
  }
}

string clientlist::userlist()

{
  string tempstring = "", xs;
  string temp;

  for (int i=0; i<SIZE; i++)

  {
    xs = "";
    int lev;

    if (list[i]->get_state() == CSTATE_POSSESSION)
      lev = list[i]->get_backup()->get_level();
    else lev = list[i]->get_char()->get_level();

    if (lev < 100) xs += " ";
    if (lev < 10) xs += " ";

    if (list[i]->get_state() == CSTATE_POSSESSION)
      tempstring += "[#Y " + xs + intconvert(list[i]->get_backup()->get_level()) + " ";
    else tempstring += "[#Y " + xs + intconvert(list[i]->get_char()->get_level()) + " ";

    if (list[i]->get_state() == CSTATE_POSSESSION)
      tempstring += list[i]->get_backup()->get_sclass() + "#N ] [#c ";
    else tempstring += list[i]->get_char()->get_sclass() + "#N ] [#c ";

    if (list[i]->get_state() == CSTATE_POSSESSION)
      temp = list[i]->get_backup()->get_name();
    else temp = list[i]->get_char()->get_name();

    for (int j=0; j<12; j++)
      if (j < (signed)temp.size()) tempstring += temp[j];
      else tempstring += " ";

    tempstring += "#N ] [#b ";
    temp = list[i]->get_ip();
    for (int j=0; j<15; j++)
      if (j < (signed)temp.size()) tempstring += temp[j];
      else tempstring += " ";  

    tempstring += "#N ] [";
    if (list[i]->get_state()      == CSTATE_NORMAL)     tempstring += "#g ONLINE  ";
    else if (list[i]->get_state() == CSTATE_WRITING)    tempstring += "#G WRITING ";
    else if (list[i]->get_state() == CSTATE_LINKDEAD)   tempstring += "#R LINKLESS";
    else if (list[i]->get_state() == CSTATE_POSSESSION) tempstring += "#m POSSESS ";
    else if (list[i]->get_creation())                   tempstring += "#y CREATION";
    else if (list[i]->get_state() == CSTATE_LOGIN)      tempstring += "#r LOGIN   ";

    tempstring += "#N ] [#N ";
    temp = list[i]->get_idle_time();
    for (int j=0; j<2; j++)
      if (j < (signed)temp.size()) tempstring += temp[j];
      else tempstring += " "; 
      
    tempstring += "#N ] [#N ";
    temp = list[i]->get_last_login();
    for (int j=0; j<8; j++)
      if (j < (signed)temp.size()) tempstring += temp[j];
      else tempstring += " "; 
      
    tempstring += " #N]\r\n";
  }

  string border = "#n+------------------------------------------------------------------------------+#N";
  string header = "#n[ Level  ] [ Player Name  ] [ IP Address      ] [ Status   ] [Idle] [ Login    ]#N";
  string clrbrd = "+------------------------------------------------------------------------------+";

  tempstring = border + "\r\n" + header + "\r\n" + border + "\r\n" + tempstring + clrbrd;

  return tempstring;
}

void clientlist::syslog_gecho(string output, int level)

{
  output = "#G[ " + output + " ]#N";

  for (int i=0; i<SIZE; i++)
  if (list[i]->get_char() != NULL)
  if (list[i]->get_char()->get_level() >= level)
  if (list[i]->get_state() != CSTATE_LOGIN)
  if (list[i]->get_state() != CSTATE_WRITING)
    list[i]->put_output(output);
}

void clientlist::syslog_gecho(string output, int level, string ip_address)

{
  output = "#G[ " + output + " (" + ip_address + ") ]#N";

  for (int i=0; i<SIZE; i++)
  if (list[i]->get_char() != NULL)
  if (list[i]->get_char()->get_level() >= level)
  if (list[i]->get_state() != CSTATE_LOGIN)
  if (list[i]->get_state() != CSTATE_WRITING)
    list[i]->put_output(output);
}