#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

filemanager::filemanager()

{
  InitializeCriticalSection(&CriticalSection);
}

filemanager::~filemanager()

{
  DeleteCriticalSection(&CriticalSection);
}

void filemanager::save_profile(client *my_client)

{
  cslock Lock(&CriticalSection);

  FILE *pfile;
  entity *ME = my_client->get_char();
  string s1 = ME->get_name();
  char *temp;

  s1 = ".\\pfiles\\" + s1 + ".dat";
  temp = (char*)s1.c_str();

  pfile = fopen(temp, "w");

  s1 = "NAME: " + ME->get_name() + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "PASSWORD: " + ME->get_password() + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "GENDER: " + ME->get_gender() + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "CLASS: " + ME->get_class() + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "HOMETOWN: " + ME->get_hometown() + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "CLAN: " + ME->get_clan() + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "RELIGION: " + ME->get_religion() + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "TITLE: " + ME->get_title() + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "PRENAME: " + ME->get_prename() + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "LEVEL: " + intconvert(ME->get_level()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "ROOM_LOGGED: " + intconvert(ME->get_room()->get_id()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "SCREEN_SIZE: " + intconvert(my_client->get_screen_size()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "TOG_ANSI: " + intconvert(my_client->get_ANSI_MODE()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "TOG_COLOR: " + intconvert(my_client->get_ansicolor()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "TOG_FOLLOW: " + intconvert(ME->get_followable()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "TOG_ASSIST: " + intconvert(ME->get_autoassist()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "MAX_HP: " + intconvert(ME->get_natural_hp()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "CURRENT_HP: " + intconvert(ME->get_current_hp()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "MAX_MANA: " + intconvert(ME->get_natural_mana()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "CURRENT_MANA: " + intconvert(ME->get_current_mana()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "MAX_MOVE: " + intconvert(ME->get_natural_move()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "CURRENT_MOVE: " + intconvert(ME->get_current_move()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "EXPERIENCE: " + intconvert(ME->get_experience()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "STR: " + intconvert(ME->get_STR()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "DEX: " + intconvert(ME->get_DEX()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "CON: " + intconvert(ME->get_CON()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "INT: " + intconvert(ME->get_INT()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "WIS: " + intconvert(ME->get_WIS()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "GOLD: " + intconvert(ME->get_gold()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  s1 = "DPOINTS: " + intconvert(ME->get_dpoints()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  for (int i=1; i<=ME->skill_list.size(); i++)
  if (ME->skill_list[i].learned == 1)

  {
    s1  = "SKILL: ";
    s1 += intconvert(ME->skill_list[i].id) + " ";
    s1 += intconvert(ME->skill_list[i].level) + " ";
    s1 += intconvert(ME->skill_list[i].times_used) + '\n';
    temp = (char*)s1.c_str();
    fprintf(pfile, temp);
  }

  for (int i=1; i<=ME->spell_list.size(); i++)
  if (ME->spell_list[i].learned == 1)

  {
    s1  = "SPELL: ";
    s1 += intconvert(ME->spell_list[i].id) + " ";
    s1 += intconvert(ME->spell_list[i].level) + " ";
    s1 += intconvert(ME->spell_list[i].times_used) + '\n';
    temp = (char*)s1.c_str();
    fprintf(pfile, temp);
  }

  s1 = ME->get_effects()->get_pfile_info();

  if (s1 != "") {
    temp = (char*)s1.c_str();
    fprintf(pfile, temp); }

  fprintf(pfile, "-END- ");

  fprintf(pfile, "\n\n+DESCRIPTION+\n");

  s1 = remove_bsr(ME->get_desc()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  fprintf(pfile, "+END+\n\n+PLAN+\n");

  s1 = remove_bsr(ME->get_plan()) + '\n';
  temp = (char*)s1.c_str();
  fprintf(pfile, temp);

  fprintf(pfile, "+END+\n\n");

  fprintf(pfile, "Aliases = %d\n", ME->Aliases->get_size());

  for (int i=0; i<ME->Aliases->get_size(); i++)

  {
    s1  = "ALIAS: [" + ME->Aliases->get_alias(i)->name + "] ";
    s1 += "OUTPUT: [" + ME->Aliases->get_alias(i)->output + "]\n";
    fprintf(pfile, (char*)s1.c_str());
  }

  s1 = "\n" + ME->get_calendar()->get_schedule() + "\n";
  fprintf(pfile, (char*)s1.c_str());

  s1 = "\n" + ME->get_legend()->get_fileinfo();
  fprintf(pfile, (char*)s1.c_str());

  fprintf(pfile, "\n[END]");

  fclose(pfile);

  string filename = ".//pfiles//" + ME->get_name() + ".itm";
  FILE* item_file = fopen((char*)filename.c_str(),"w");

  if (item_file != NULL)

  {
    string item_output = "Num_items: " + intconvert(ME->get_total_items()) + "\r\n";
    item_output += ME->pfile_item_information();
    item_output += ME->pfile_equipment_information();
    item_output += "\r\n";
    fprintf(pfile, (char*)item_output.c_str());
  }

  fclose(item_file);
}

int filemanager::char_exist(string s)

{
  cslock Lock(&CriticalSection);

  s = ".\\pfiles\\" + s + ".dat";
  char* temp = (char*)s.c_str();
  FILE* pfile = fopen(temp, "r");

  if (pfile == NULL)
    return 0;

  if (first(ReadLine(pfile)) == "PLACEHOLDER")

  {
    fclose(pfile);
    return 2;
  }

  return 1;
}

string filemanager::get_password(string name)

{
  cslock Lock(&CriticalSection);

  string password = "";
  name = ".\\pfiles\\" + name + ".dat";
  FILE* pfile = fopen((char*)name.c_str(), "r");

  if (pfile != NULL)

  {
    password = ReadLine(pfile);
    password = ReadLine(pfile);
    password = last(password);
    fclose(pfile);
  }

  return password;
}

int filemanager::get_level(string name)

{
  cslock Lock(&CriticalSection);

  string level = "";
  name = ".\\pfiles\\" + name + ".dat";
  FILE* pfile = fopen((char*)name.c_str(), "r");

  level = ReadLine(pfile);

  while (first(level) != "LEVEL:")
    level = ReadLine(pfile);

  level = last(level);
  fclose(pfile);

  return stringconvert(level);
}

string filemanager::get_class(string name)

{
  cslock Lock(&CriticalSection);

  string clas = "";
  name = ".\\pfiles\\" + name + ".dat";
  FILE* pfile = fopen((char*)name.c_str(), "r");

  clas = ReadLine(pfile);

  while (first(clas) != "CLASS:")
    clas = ReadLine(pfile);

  clas = last(clas);
  fclose(pfile);

  return clas;
}

void filemanager::create_placeholder(string name)

{
  cslock Lock(&CriticalSection);

  name = ".\\pfiles\\" + name + ".dat";
  FILE* pfile = fopen((char*)name.c_str(), "w");

  fprintf(pfile, "PLACEHOLDER\r\n");
  fclose(pfile);
}

int filemanager::load_profile(client *my_client)

{
  cslock Lock(&CriticalSection);

  FILE *pfile;
  entity *ME = my_client->get_char();
  char *temp2, s1[500], c;
  string temp = "";
  string s = ME->get_name();
  int CURHP, CURMN, CURMV;
  int MAXHP, MAXMN, MAXMV;
  int colon;

  s = ".\\pfiles\\" + s + ".dat";
  temp2 = (char*)s.c_str();
  pfile = fopen(temp2, "r");

  if (pfile != NULL)

  {
    while (temp != "-END- ")

    {
      temp = "";
      c = 'x';

      while (c != ' ') {
        c = getc(pfile);
        temp = temp + c; }

      if (temp != "-END- ") {
        fgets(s1, sizeof(s1), pfile);
        s = string(s1);
        s = s.substr(0, s.length()-1); }

      if (temp == "NAME: ")    ME->set_name(s);
      else if (temp == "PASSWORD: ") ME->set_password(s);
      else if (temp == "GENDER: ") { ME->set_gender(s); ME->genderset(); }
      else if (temp == "CLASS: ") ME->set_class(s);
      else if (temp == "HOMETOWN: ") ME->set_hometown(s);
      else if (temp == "CLAN: ") ME->set_clan(s);
      else if (temp == "RELIGION: ") ME->set_religion(s);
      else if (temp == "TITLE: ") ME->set_title(s, 0);
      else if (temp == "PRENAME: ") ME->set_prename(s, 0);
      else if (temp == "LEVEL: ") { ME->set_level(stringconvert(s)); ME->set_exp_needed(); }
      else if (temp == "ROOM_LOGGED: ") ME->set_room(stringconvert(s));
      else if (temp == "SCREEN_SIZE: ") my_client->set_screen_size(stringconvert(s));
      else if (temp == "TOG_ANSI: ") my_client->set_ANSI_MODE(stringconvert(s));
      else if (temp == "TOG_COLOR: ") my_client->set_ansicolor(stringconvert(s));
      else if (temp == "TOG_FOLLOW: ") ME->set_followable(stringconvert(s));
      else if (temp == "TOG_ASSIST: ") ME->set_autoassist(stringconvert(s));
      else if (temp == "CURRENT_HP: ") CURHP = stringconvert(s);
      else if (temp == "CURRENT_MANA: ") CURMN = stringconvert(s);
      else if (temp == "CURRENT_MOVE: ") CURMV = stringconvert(s);
      else if (temp == "MAX_HP: ") MAXHP = stringconvert(s);
      else if (temp == "MAX_MANA: ") MAXMN = stringconvert(s);
      else if (temp == "MAX_MOVE: ") MAXMV = stringconvert(s);
      else if (temp == "EXPERIENCE: ") ME->set_experience(stringconvert(s));
      else if (temp == "STR: ") ME->set_STR(stringconvert(s));
      else if (temp == "DEX: ") ME->set_DEX(stringconvert(s));
      else if (temp == "CON: ") ME->set_CON(stringconvert(s));
      else if (temp == "INT: ") ME->set_INT(stringconvert(s));
      else if (temp == "WIS: ") ME->set_WIS(stringconvert(s));
      else if (temp == "GOLD: ") ME->set_gold(stringconvert(s));
      else if (temp == "DPOINTS: ") ME->set_dpoints(stringconvert(s));

      else if (temp == "SKILL: ")

      {
        int space = s.find(" ", 0);
        string num1 = s.substr(0, space);
        s.erase(0, space+1);

        space = s.find(" ", 0);
        string num2 = s.substr(0, space);
        s.erase(0, space+1);

        int skill_id = stringconvert(num1);
        int skill_lv = stringconvert(num2);
        int skill_tu = stringconvert(s);

        if (skill_id <= NUM_SKILLS) {
          ME->skill_list[skill_id].learned = 1;
          ME->skill_list[skill_id].level = skill_lv;
          ME->skill_list[skill_id].times_used = skill_tu; }
      }

      else if (temp == "SPELL: ")

      {
        int space = s.find(" ", 0);
        string num1 = s.substr(0, space);
        s.erase(0, space+1);

        space = s.find(" ", 0);
        string num2 = s.substr(0, space);
        s.erase(0, space+1);

        int spell_id = stringconvert(num1);
        int spell_lv = stringconvert(num2);
        int spell_tu = stringconvert(s);

        if (spell_id <= NUM_SPELLS) {
          ME->spell_list[spell_id].learned = 1;
          ME->spell_list[spell_id].level = spell_lv;
          ME->spell_list[spell_id].times_used = spell_tu; }
      }

      else if (temp == "AFFECTED_BY: ")

      {
        string aname = "";
        int duration = 0;

        while (s[0] != ' ') {
          aname = aname + s[0];
          s.erase(0,1); }

        s.erase(0,1);
        s.erase(s.size(), 1);

        duration = stringconvert(s);

        ME->get_effects()->renew(aname, duration);
      }
    }

    fgets(s1, sizeof(s1), pfile);
    fgets(s1, sizeof(s1), pfile);
    fgets(s1, sizeof(s1), pfile);

    string desc = "";
    int done = 0;

    while (!done)

    {
      fgets(s1, sizeof(s1), pfile);
      s = string(s1);
      s = s.substr(0, s.length()-1);

      if (s != "+END+") {
        if (desc == "") desc += s;
        else desc += "\r\n" + s; }

      else done = 1;
    }

    fgets(s1, sizeof(s1), pfile);
    fgets(s1, sizeof(s1), pfile);
    string plan = "";
    done = 0;

    while (!done)

    {
      fgets(s1, sizeof(s1), pfile);
      s = string(s1);
      s = s.substr(0, s.length()-1);

      if (s != "+END+") {
        if (plan == "") plan += s;
        else plan += "\r\n" + s; }

      else done = 1;
    }

    ME->set_desc(desc);
    ME->set_plan(plan);

    if (ME->get_room() == NULL)
      ME->set_room(World->get_room(100));


    int num_aliases, pos;
    string str = ReadLine(pfile);
    string aname, aoutput;

    c = 'X';
    while (c != '=') c = getc(pfile);
    getc(pfile);

    fscanf(pfile, "%d", &num_aliases);
    ReadLine(pfile);

    for (int i=0; i<num_aliases; i++)

    {
      str = ReadLine(pfile);
      str.erase(0,8);
      pos = str.find("OUTPUT:");
      aname = str.substr(0, pos-2);
      str.erase(0,pos+9);
      aoutput = str.substr(0, str.size()-1);

      ME->Aliases->auto_alias(aname, aoutput);
    }

    ReadLine(pfile);
    c = 'X';
    while (c != '=') c = getc(pfile);
    getc(pfile);

    int calendar_size, SID, deadline;

    fscanf(pfile, "%d", &calendar_size);
    ReadLine(pfile);

    for (int i=0; i<calendar_size; i++)

    {
      for (int j=1; j<=10; j++) getc(pfile);
      fscanf(pfile, "%d", &SID);
      getc(pfile);
      fscanf(pfile, "%d", &deadline);
      ReadLine(pfile);

      ME->get_calendar()->set_event(SID, deadline);
    }

    ReadLine(pfile);
    c = 'X';
    while (c != '=') c = getc(pfile);
    getc(pfile);

    int num_marks;
    int mark_times;
    string mark_date;

    fscanf(pfile, "%d", &num_marks);
    ReadLine(pfile);

    for (int i=0; i<num_marks; i++)

    {
      mark_date = "";

      for (int j=1; j<=8; j++) getc(pfile);
      fscanf(pfile, "%d", &mark_times);

      getc(pfile); c = getc(pfile);

      while (c != ']') {
        c = getc(pfile);
        if (c != ']') mark_date = mark_date + c; }

      getc(pfile);

      str = ReadLine(pfile);

      ME->get_legend()->auto_mark(str, mark_times, mark_date);
    }

    ME->set_max_hp(MAXHP);
    ME->set_max_mana(MAXMN);
    ME->set_max_move(MAXMV);
    ME->set_current_hp(CURHP);
    ME->set_current_mana(CURMN);
    ME->set_current_move(CURMV);

    fclose(pfile);

    item_load(my_client);

    return 1;
  }

  fclose(pfile);
  return 0;
}

int filemanager::delete_profile(string str)

{
  cslock Lock(&CriticalSection);

  string s1 = ".\\pfiles\\" + str + ".dat";
  string s2 = ".\\pfiles\\" + str + ".itm";

  char *f1 = (char*)s1.c_str();
  char *f2 = (char*)s2.c_str();

  FILE *fp = fopen(f1, "r");
  if (fp == NULL) return 0;
  fclose(fp);

  remove(f1);
  remove(f2);

  return 1;
}

string filemanager::get_plan(string name)

{
  cslock Lock(&CriticalSection);

  FILE *pfile;
  char *temp2, s1[500], c;
  string temp = "", fullplan = "", s;
  string full_name, title, level, cclass, clan_name, clanrank;
  string religious_rank, political_rank, gender, plan;
  religion* the_religion;
  clan* the_clan;

  name = ".\\pfiles\\" + name + ".dat";
  temp2 = (char*)name.c_str();
  pfile = fopen(temp2, "r");

  if (pfile == NULL) fullplan = "There is no player by that name.";

  else

  {
    while (temp != "-END- ")

    {
      temp = "";
      c = 'x';

      while (c != ' ') {
        c = getc(pfile);
        temp += c; }

      if (temp != "-END- ") {
        fgets(s1, sizeof(s1), pfile);
        s = string(s1);
        s = s.substr(0, s.length()-1); }

      if (temp == "NAME: ") full_name = s;
      else if (temp == "GENDER: ") gender = s;
      else if (temp == "TITLE: ") title = s;
      else if (temp == "LEVEL: ") level = s;
      else if (temp == "CLASS: ") cclass = s;

      else if (temp == "CLAN: ")

      {
        clan_name = s;

        the_clan = World->get_clanlist()->get_clan(s);
        if (the_clan == NULL) clan_name = "None";

        if (clan_name != "None")
          clanrank = the_clan->get_rankname(full_name, gender);
        else {
          clan_name = "";
          clanrank = ""; }
      }

      else if (temp == "RELIGION: ")

      {
        if (s != "None") {
          the_religion = World->get_religionlist()->get_religion(s);
          if (the_religion != NULL) religious_rank = the_religion->get_rankname(full_name, gender);
          else religious_rank = ""; }

        else {
          the_religion = World->get_religionlist()->get_religion_diety(full_name);
          if (the_religion != NULL) religious_rank = the_religion->get_diety_title();
          else religious_rank = ""; }
      }
    }

    fgets(s1, sizeof(s1), pfile);
    fgets(s1, sizeof(s1), pfile);
    fgets(s1, sizeof(s1), pfile);

    int done = 0;

    while (!done)

    {
      fgets(s1, sizeof(s1), pfile);
      s = string(s1);
      s = s.substr(0, s.length()-1);

      if (s == "+PLAN+") done = 1;
    }

    plan = "";
    done = 0;

    while (!done)

    {
      fgets(s1, sizeof(s1), pfile);
      s = string(s1);
      s = s.substr(0, s.length()-1);

      if (s != "+END+") {
        if (plan == "") plan += s;
        else plan += "\r\n" + s; }

      else done = 1;
    }

    ////////////////////////////////////
    political_rank = "[Political Rank]";
    ////////////////////////////////////

    string xs1 = "";
    for (int i=1; i<=58-visible_size(full_name)-visible_size(title)-1; i++) xs1 += " ";

    string xs2 = "";
    for (int i=1; i<=58-visible_size(cclass)-visible_size(level)-7; i++) xs2 += " ";

    string xs3 = "";
    for (int i=1; i<=58-visible_size(clan_name)-visible_size(clanrank)-17; i++) xs3 += " ";

    string xs4 = "";
    for (int i=1; i<=58-visible_size(political_rank)-16; i++) xs4 += " ";

    string xs5 = "";
    for (int i=1; i<=58-visible_size(religious_rank)-16; i++) xs5 += " ";

    fullplan  = "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n";
    fullplan += "  #n| #N" + full_name + " " + title + xs1 + "#n|#N\r\n";
    fullplan += "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n";
    fullplan += "  #n| #NLevel " + level + " " + cclass + xs2 + "#n|#N\r\n";

    if ((clan_name != "") || (clanrank != ""))
    fullplan += "  #n| #NClantitle Rank#n: #N" + clan_name + " " + clanrank + xs3 + "#n|#N\r\n";

    //////////////////////////////////////////////////////////////////////////////////
    //fullplan += "  #n| #NPolitical Rank#n: #N" + political_rank + xs4 + "#n|#N\r\n";
    //////////////////////////////////////////////////////////////////////////////////

    if (religious_rank != "")
    fullplan += "  #n| #NReligious Rank#n: #N" + religious_rank + xs5 + "#n|#N\r\n";

    fullplan += "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    if (plan != "")

    {
      fullplan += "\r\n  #n|                                                           |#N\r\n";
      fullplan += plan + "\r\n";
      fullplan += "  #n|                                                           |#N\r\n";
      fullplan += "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";
    }
  }

  fclose(pfile);

  return fullplan;
}

void filemanager::item_load(client *my_client)

{
  FILE* item_file;
  entity* ME = my_client->get_char();
  itemlist* IL = World->get_itemlist();
  string filename = ".//pfiles//" + ME->get_name() + ".itm";
  string input;
  int num_items;

  item_file = fopen((char*)filename.c_str(), "r");

  if (item_file == NULL) return;

  input = ReadLine(item_file);
  num_items = stringconvert(last(input));

  int*  dlist   = new int    [num_items];
  int*  elist   = new int    [num_items];
  string* plist = new string [num_items];
  item* *ilist  = new item*  [num_items];

  for (int i=0; i<num_items; i++)

  {
    input = ReadLine(item_file);

    input = last(ReadLine(item_file));
    int ID = stringconvert(first(input));
    int EQ = stringconvert(first(last(input)));
    int DP = stringconvert(last(last(input)));

    string PLACE = last(ReadLine(item_file));
    string STAMP = last(ReadLine(item_file));
    string CTYPE = last(ReadLine(item_file));
    int CHARGES = stringconvert(last(ReadLine(item_file)));
    string LTYPE = last(ReadLine(item_file));
    int LUSES = stringconvert(last(ReadLine(item_file)));

    ilist[i] = IL->replicate(ID, CTYPE);

    if (ilist[i] != NULL)

    {
      ilist[i]->TIME_STAMP = STAMP;
      ilist[i]->charges_left = CHARGES;
      ilist[i]->liquid_type = LTYPE;
      ilist[i]->uses = LUSES;
    }

    dlist[i] = DP;
    elist[i] = EQ;
    plist[i] = PLACE;
  }

  int current_depth;
  item* current_container;

  for (int i=0; i<num_items; i++)

  {
    if (dlist[i] > 0)
    for (int j=i; j>=0; j--)
      if (dlist[j] == dlist[i]-1) { current_container = ilist[j]; j = -1; }

    if (ilist[i] != NULL)

    {
      if (dlist[i] == 0) ME->append_item_inventory(ilist[i]);
      else if (current_container == NULL) ME->append_item_inventory(ilist[i]);
      else current_container->add_item(ilist[i]);
    }
  }

  for (int i=0; i<num_items; i++)
  if (ilist[i] != NULL)
    ilist[i]->update();

  for (int i=0; i<num_items; i++)
  if ((elist[i] == 1) && (dlist[i] == 0))
  if (ilist[i] != NULL)

  {
    ME->equip_direct(ilist[i], plist[i]);
    ME->remove_item_inventory(ilist[i]);
    ilist[i]->ENT = ME;
  }
}

string* filemanager::get_pfile_copy(string Name)

{
  cslock Lock(&CriticalSection);

  FILE *pfile;
  char s[500];
  string str;
  int count;

  string *filecopy = new string [1000];

  string filename = ".\\pfiles\\" + proper_name(Name) + ".dat";
  pfile = fopen((char*)filename.c_str(), "r");

  if (pfile == NULL)

  {
    filecopy[0] = "0";
    return filecopy;
  }

  else

  {
    count = 1;
    fgets(s, sizeof(s), pfile);
    str = s;
    str = str.substr(0, str.length()-1);
    filecopy[count] = str;

    while (filecopy[count] != "[END]")

    {
      count++;
      fgets(s, sizeof(s), pfile);
      str = s;
      if (str != "[END]") str = str.substr(0, str.length()-1);
      filecopy[count] = str;
    }

    filecopy[0] = intconvert(count);
  }

  fclose(pfile);

  return filecopy;
}

void filemanager::set_pfile_copy(string Name, string *pfc)

{
  cslock Lock(&CriticalSection);

  FILE *pfile;
  int psize = stringconvert(pfc[0]);
  string filename = ".\\pfiles\\" + Name + ".dat";
  pfile = fopen((char*)filename.c_str(), "w");

  for (int i=1; i<=psize; i++)

  {
    if (i != psize) pfc[i] += "\n";
    fprintf(pfile, (char*)pfc[i].c_str());
  }

  fclose(pfile);

  delete pfc;
}