#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

systemlog::systemlog()

{
  logdate.wMonth = 0;
  logdate.wDay   = 0;
  logdate.wYear  = 0;
}

FILE* systemlog::open_log_file()

{
  string date;
  SYSTEMTIME temp;
  FILE* log_pointer;

  GetLocalTime(&temp);

  date = ".\\logs\\";
  date += intconvert(temp.wMonth) + "-";
  date += intconvert(temp.wDay) + "-";
  date += intconvert(temp.wYear) + ".log";

  log_pointer = fopen((char*)date.c_str(), "a");
  logdate = temp;

  return log_pointer;
}

void systemlog::add_log(client* aclient, string str)

{
  char* header;
  char* entry;
  string str_header = "";
  string str_entry  = "";
  FILE* log_pointer = open_log_file();

  str_header = intconvert(logdate.wHour) + ":" + intconvert(logdate.wMinute) + ":" + intconvert(logdate.wSecond);

  if (aclient != NULL)
    if (aclient->get_state() != CSTATE_LOGIN)

    {
      str_header += " " + aclient->get_ip() + " " + aclient->get_char()->get_name();
      str_entry  += "(" + intconvert(aclient->get_char()->get_room()->get_id()) + ") " + str;
    }

    else

    {
      if (aclient->get_creation() == 0)
        str_header += " " + aclient->get_ip() + " LOGIN";
      else
        str_header += " " + aclient->get_ip() + " CREATION";

      str_entry  += "(VOID) " + str;
    }

  else

  {
    str_header += " SYSTEM";
    str_entry  += str;
  }

  header = (char*)str_header.c_str();
  entry  = (char*)str_entry.c_str();

  fprintf(log_pointer, "[%-33.33s] %s\n", header, entry);

  fclose(log_pointer);
}