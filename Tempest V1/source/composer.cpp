#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

typedef struct
{
  client* my_client;
  string note_name;
  item* board_pointer;
  int save_to;
} writeinfo;

string format_plan(string);
string format_desc(string);
string full_day(string);
string full_date(string);
string full_time(string);

void write(void* voiddata)

{
  writeinfo *writedata = (writeinfo*)voiddata;
  string datarecvd, body = "";
  string CR_ON = "\33[20h";
  string complete, cancel;
  int quit = 0, true_ansi;

  true_ansi = writedata->my_client->get_ANSI_MODE();

  if (true_ansi) {
    writedata->my_client->clear_ansibar();
    writedata->my_client->set_ANSI_MODE(0);
    writedata->my_client->msg(CR_ON);
    complete = "\r\n\nWriting completed.";
    cancel = "\r\n\nWriting canceled."; }

  else {
    complete = "Writing completed.";
    cancel = "Writing canceled."; }

  string name = writedata->my_client->get_char()->get_name();

  string temp  = "To complete your writing type '::save' and to exit type '::exit'.\r\n";
         temp += "These commands must be on a separate line to work.\n\r";
         temp += "Please write within the boundaries specified.\n\n\r";
         temp += "] --------------------------------------------------------- [";

  writedata->my_client->msg("\r\n" + temp + "\r\n");

  while ((quit == 0)  && (writedata->my_client->get_state() == CSTATE_WRITING))

  {
    writedata->my_client->msg("#N] ");

    datarecvd = writedata->my_client->prompt_private();

    if (datarecvd.length() > 61)
      writedata->my_client->msg("] #rLine exceeds max length.  Will not be added.\r\n");

    else if ((datarecvd != "::save") && (datarecvd != "::exit"))

    {
      string added_text = datarecvd + '\n';
      if (writedata->save_to == 2) added_text += " ";

      body += added_text;
    }

    else quit = 1;
  }

  if (writedata->my_client->get_state() == CSTATE_WRITING)
  if (datarecvd == "::save")

  {
   if (writedata->save_to == 1)
     writedata->board_pointer->board_pointer->add_note(name, writedata->note_name, body);

    if (writedata->save_to == 2)
      writedata->my_client->get_char()->set_desc(format_desc(body));

    else if (writedata->save_to == 3)
      writedata->my_client->get_char()->set_plan(format_plan(body));

    writedata->my_client->put_output(complete);
  }

  else writedata->my_client->put_output(cancel);

  if (!writedata->my_client->get_char()->PLAYER) writedata->my_client->set_state(CSTATE_POSSESSION);

  else

  {
    writedata->my_client->set_state(CSTATE_NORMAL);
    File->save_profile(writedata->my_client);
  }

  if (true_ansi)
    writedata->my_client->set_ANSI_MODE(1);

  delete writedata;
  _endthread();
}

// ************************************************************************************************************ //
// ************************************************************************************************************ //
// ************************************************************************************************************ //

void entity::write_note(string str)

{
  writeinfo *writedata = new writeinfo;

  if (my_client == NULL)
    return;

  if (playerstate == PSTATE_FIGHTING) {
    echo("Error: Cannot write while in combat.");
    return; }

  if (my_client->get_state() == CSTATE_IMM_AT)
    restore_room();

  writedata->my_client = my_client;
  writedata->note_name = str;
  writedata->board_pointer = rm->find_item_room("board");
  writedata->save_to = 1;

  if (writedata->board_pointer == NULL) {
    echo("Error: Board not found.");
    return; }

  my_client->set_state(CSTATE_WRITING);
  my_client->get_Oqueue()->flushbuffer();

  _beginthread(write, 0, (void*)writedata);
  blind_emote("starts writing a note.");
}

void entity::write_other(int save_to)

{
  writeinfo *writedata = new writeinfo;

  if (my_client == NULL)
    return;

  if (get_playerstate() == PSTATE_FIGHTING) {
    echo("Error: Cannot write in combat.");
    return; }

  if (my_client->get_state() == CSTATE_IMM_AT)
    restore_room();

  writedata->my_client = my_client;
  writedata->note_name = "";
  writedata->board_pointer = NULL;
  writedata->save_to = save_to;

  my_client->set_state(CSTATE_WRITING);
  my_client->get_Oqueue()->flushbuffer();

  _beginthread(write, 0, (void*)writedata);
  blind_emote("starts writing.");
}

// ************************************************************************************************************ //
// ************************************************************************************************************ //
// ************************************************************************************************************ //

string client::get_day()

{
  string str;
  time_t aclock;
  struct tm *temp;

  time(&aclock);
  temp = localtime(&aclock);

  str = asctime(temp);
  str = str.substr(0,3);

  return str;
}

string client::get_date()

{
  char temp[128];
  _strdate(temp);
  return string(temp);
}

string client::get_time()

{
  char temp[128];
  _strtime(temp);
  return string(temp);
}

void client::display_date()

{
  string s_day = full_day(get_day());
  string s_date = full_date(get_date());
  string s_time = full_time(get_time());
  put_output(s_day + ", " + s_date + " -- " + s_time + ".");
}