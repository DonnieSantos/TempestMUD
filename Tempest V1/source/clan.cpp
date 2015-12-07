#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

clan::clan(int ID, string NAME, int numranks)

{
  size = 0;
  clan_info = "";

  id = ID;
  name = NAME;

  members = new string [1];
  ranks = new int [1];
  classes = new string [1];

  num_ranknames = numranks;

  male_ranknames = new string [num_ranknames+2];
  female_ranknames = new string [num_ranknames+2];
}

void clan::set_rankname(int rank, string male, string female)

{
  male_ranknames[rank] = male;
  female_ranknames[rank] = female;
}

void clan::set_clan_info(string str)

{
  str = "     " + str;
  clan_info = variable_allign(str, INFO_WIDTH);
}

string clan::get_name()

{
  return name;
}

int clan::get_numranks()

{
  return num_ranknames;
}

void clan::add_member(string mname, int rank, string profession)

{
  size++;

  string *temp_names = new string [size];
  int *temp_ranks = new int [size];
  string *temp_class = new string [size];

  for (int i=0; i<size-1; i++) {
    temp_names[i] = members[i];
    temp_ranks[i] = ranks[i];
    temp_class[i] = classes[i]; }

  temp_names[size-1] = mname;
  temp_ranks[size-1] = rank;
  temp_class[size-1] = profession;

  delete [] members;
  delete [] ranks;
  delete [] classes;

  members = temp_names;
  ranks = temp_ranks;
  classes = temp_class;
}

void clan::remove_member(string Name)

{
  size--;

  string *temp_names = new string [size];
  int *temp_ranks = new int [size];
  string *temp_class = new string [size];
  int found = 0;

  for (int i=0; i<=size; i++)

  {
    if (members[i] != Name) {
      temp_names[i-found] = members[i];
      temp_ranks[i-found] = ranks[i];
      temp_class[i-found] = classes[i]; }

    else found = 1;
  }

  delete [] members;
  delete [] ranks;
  delete [] classes;

  members = temp_names;
  ranks = temp_ranks;
  classes = temp_class;
}

int clan::get_rank(string Name)

{
  for (int i=0; i<size; i++)
    if (members[i] == Name)
      return ranks[i];

  return -1;
}

void clan::set_rank(string Name, int num)

{
  for (int i=0; i<size; i++)
    if (members[i] == Name)
      ranks[i] = num;
}

string clan::get_class(string Name)

{
  for (int i=0; i<size; i++)
    if (members[i] == Name)
      return classes[i];

  return "";
}

void clan::set_class(string Name, string profession)

{
  for (int i=0; i<size; i++)
    if (members[i] == Name)
      classes[i] = profession;
}

int clan::get_num_leaders()

{
  int count = 0;

  for (int i=0; i<size; i++)
    if (ranks[i] == num_ranknames)
      count++;

  return count;
}

int clan::get_num_council()

{
  int count = 0;

  for (int i=0; i<size; i++)
    if (ranks[i] == num_ranknames-1)
      count++;

  return count;
}

int clan::get_num_immortals()

{
  int count = 0;

  for (int i=0; i<size; i++)
    if (ranks[i] == num_ranknames+1)
      count++;

  return count;
}

int clan::get_num_applicants()

{
  int count = 0;

  for (int i=0; i<size; i++)
    if (ranks[i] == 0)
      count++;

  return count;
}

string clan::get_rankname(string Name, string Gender)

{
  for (int i=0; i<size; i++)
  if (members[i] == Name)

  {
    if (Gender == "male") return male_ranknames[ranks[i]];
    if (Gender == "female") return female_ranknames[ranks[i]];
  }

  return "";
}

void clan::save_clanfile()

{
  FILE *clanfile;
  string s1, ID;

  ID = intconvert(id);
  if (id < 100) ID = "0" + ID;
  if (id < 10) ID = "0" + ID;

  s1 = ".\\clans\\clan" + ID + ".dat";
  clanfile = fopen((char*)s1.c_str(), "w");

  s1 = "Clan = " + name + "\n";
  fprintf(clanfile, (char*)s1.c_str());

  s1 = "Members = " + intconvert(size) + "\n";
  fprintf(clanfile, (char*)s1.c_str());

  s1 = "Ranks = " + intconvert(num_ranknames) + "\n\n";
  fprintf(clanfile, (char*)s1.c_str());

  for (int i=0; i<=num_ranknames+1; i++)

  {
    s1 = "[";
    if (i < 10) s1 += "0";
    s1 += intconvert(i) + "] [" + male_ranknames[i] + "] [" + female_ranknames[i] + "]\n";
    if (i == num_ranknames+1) s1 += "\n";

    fprintf(clanfile, (char*)s1.c_str());
  }

  string info_output = clan_info;
  info_output.erase(0,5);

  s1 = "[BEGIN]\n" + info_output + "\n[END]\n\n";
  fprintf(clanfile, (char*)s1.c_str());

  for (int i=0; i<size; i++) {
    s1 = members[i] + " " + intconvert(ranks[i]) + " " + classes[i] + "\n";
    fprintf(clanfile, (char*)s1.c_str()); }

  s1 = "\n-End- ";
  fprintf(clanfile, (char*)s1.c_str());

  fclose(clanfile);
}

void clan::verify_members()

{
  FILE *pfile;
  char *temp2, s1[500], c;
  string temp, s;

  for (int i=0; i<size; i++)

  {
    int valid_member = 0;
    string profession = "";

    s = ".\\pfiles\\" + members[i] + ".dat";
    temp2 = (char*)s.c_str();
    pfile = fopen(temp2, "r");

    if (pfile != NULL)

    {
      temp = "";

      while (temp != "CLAN: ")

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

        if (temp == "CLASS: ")
          profession = s;
      }

      if (s == name)
        valid_member = 1;
    }

    if (!valid_member)
      remove_member(members[i]);

    else set_class(members[i], profession);

    fclose(pfile);
  }

  save_clanfile();
}

void entity::clan_apply(string cname)

{
  if (!PLAYER) return;
  clanlist *CL = World->get_clanlist();
  clan *the_clan = CL->get_clan(cname);

  if (get_clan() != "None") {
    if (CL->get_clan(get_clan())->get_rank(name) > 0) echo("You are already in a clan.");
    else echo("You are already applying to a clan.");
    return; }

  if (cname == "") {
    echo("Which clan do you want to apply to?");
    return; }

  if (the_clan == NULL) {
    echo("That clan does not exist.");
    return; }

  the_clan->add_member(name, 0, get_class());
  the_clan->save_clanfile();

  set_clan(the_clan->get_name());
  File->save_profile(my_client);

  echo("You are applying for membership in the clan " + the_clan->get_name() + ".");
  clist->clan_xecho(my_client, get_clan(), name + " is applying for membership in " + the_clan->get_name() + ".");
}

void entity::clan_enlist(string str)

{
  clan *my_clan = World->get_clanlist()->get_clan(get_clan());

  if (my_clan == NULL) {
    echo("You are not in a clan to enlist anybody.");
    return; }

  if (my_clan->get_rank(name) == 0) {
    echo("Only accepted clan members can enlist applicants.");
    return; }

  if (my_clan->get_rank(name) < my_clan->get_numranks()-1) {
    echo("You must be at least clanrank " + intconvert(my_clan->get_numranks()-1) + " to enlist members.");
    return; }

  if (str == "") {
    echo("Who do you want to enlist into your clan?");
    return; }

  entity *ENT = clist->find_character(str);

  if (ENT == NULL) {
    echo("There is nobody around by that name.");
    return; }

  if (ENT == this) {
    echo("Enlist yourself into the clan that you are already in?  Okay...");
    return; }

  if (ENT->get_clan() != get_clan()) {
    echo(ENT->get_name() + " is not applying to your clan.");
    return; }

  if (my_clan->get_rank(ENT->get_name()) > 0) {
    echo(ENT->get_name() + " is already in your clan!");
    return; }

  int entrance_rank = 1;
  if (ENT->get_level() >= 100) entrance_rank = my_clan->get_numranks()+1;

  my_clan->set_rank(ENT->get_name(), entrance_rank);
  my_clan->save_clanfile();

  string forclan = name + " has accepted " + ENT->get_name() + " into " + get_clan() + ".";
  echo("You accept " + ENT->get_name() + " into " + get_clan() + ".");
  ENT->echo("\r\n" + name + " has accepted you into the clan " + get_clan() + ".");
  clist->clan_xecho(my_client, ENT->get_client(), get_clan(), forclan);
}

void entity::clan_expel(string str)

{
  clan *my_clan = World->get_clanlist()->get_clan(get_clan());

  if (my_clan == NULL) {
    echo("You are not in a clan to expel anybody.");
    return; }

  if (my_clan->get_rank(name) == 0) {
    echo("Only accepted clan members can expel others.");
    return; }

  if (my_clan->get_rank(name) < my_clan->get_numranks()-1) {
    echo("You must be at least clanrank " + intconvert(my_clan->get_numranks()-1) + " to expel members.");
    return; }

  if (str == "") {
    echo("Who do you want to expel from your clan?");
    return; }

  if (str == get_lname()) {
    echo("Expel yourself?  It's a little easier to just leave the clan.");
    return; }

  entity *ENT = clist->find_character(str);

  if (ENT == NULL)

  {
    string *pfc = File->get_pfile_copy(str);
    int pfsize = stringconvert(pfc[0]);
    int in_clan = 0;

    if (pfsize == 0) {
      echo("There is nobody in your clan by that name.");
      delete pfc;
      return; }

    for (int i=1; i<=pfsize; i++)
    if (pfc[i].size() >= 6)
    if (pfc[i].substr(0, 6) == "CLAN: ")
    if (pfc[i].substr(6, pfc[i].size()-6) == get_clan()) {
      in_clan = i;
      break; }

    if (in_clan == 0) {
      echo("There is nobody in your clan by that name.");
      delete pfc;
      return; }

    if (my_clan->get_rank(proper_name(str)) >= my_clan->get_rank(name)) {
      echo("You cannot expel those of equal or higher rank than you.");
      delete pfc;
      return; }

    pfc[in_clan] = "CLAN: None";
    File->set_pfile_copy(proper_name(str), pfc);

    my_clan->remove_member(proper_name(str));
    my_clan->save_clanfile();

    string fortheclan = name + " has expelled " + proper_name(str) + " from " + get_clan() + ".";
    echo("You have expelled " + proper_name(str) + " from " + get_clan() + ".");
    clist->clan_xecho(my_client, get_clan(), fortheclan);

    return;
  }

  if (ENT->get_clan() != get_clan()) {
    echo(ENT->get_name() + " is not a member of your clan.");
    return; }

  if (my_clan->get_rank(ENT->get_name()) >= my_clan->get_rank(name)) {
    echo("You cannot expel those of equal or higher rank than you.");
    return; }

  my_clan->remove_member(ENT->get_name());
  my_clan->save_clanfile();

  ENT->set_clan("None");
  File->save_profile(ENT->get_client());

  string forclan = name + " has expelled " + ENT->get_name() + " from " + get_clan() + ".";
  echo("You have expelled " + ENT->get_name() + " from " + get_clan() + ".");
  ENT->echo("\r\n" + name + " has expelled you from the clan " + get_clan() + ".");
  clist->clan_xecho(my_client, ENT->get_client(), get_clan(), forclan);
}

void entity::clan_raise(string str)

{
  int new_rank;
  clan *my_clan = World->get_clanlist()->get_clan(get_clan());

  if (my_clan == NULL) {
    echo("You are not in a clan to promote anybody.");
    return; }

  if (my_clan->get_rank(name) == 0) {
    echo("Only accepted clan members can promote others.");
    return; }

  if (my_clan->get_rank(name) < my_clan->get_numranks()-1) {
    echo("You must be at least clanrank " + intconvert(my_clan->get_numranks()-1) + " to promote members.");
    return; }

  if (str == "") {
    echo("Who do you want to promote?");
    return; }

  if (str == get_lname()) {
    echo("Promoting yourself, eh?  Nice try.");
    return; }

  entity *ENT = clist->find_character(str);

  if (ENT == NULL)

  {
    string *pfc = File->get_pfile_copy(str);
    int pfsize = stringconvert(pfc[0]);
    string tgender = "";

    if (pfsize == 0) {
      echo("There is nobody in your clan by that name.");
      delete pfc;
      return; }

    for (int i=1; i<=pfsize; i++)
    if (pfc[i].size() >= 8)
    if (pfc[i].substr(0, 8) == "GENDER: ") {
      tgender = pfc[i].substr(8, pfc[i].size()-8);
      break; }

    if (my_clan->get_rank(proper_name(str)) == -1) {
      echo("There is nobody in your clan by that name.");
      delete pfc;
      return; }

    if (my_clan->get_rank(proper_name(str)) >= my_clan->get_rank(name)) {
      echo("You cannot promote those of equal or higher rank than you.");
      delete pfc;
      return; }

    if (my_clan->get_rank(proper_name(str)) == 0) {
      echo(proper_name(str) + " has not been enlisted into the clan yet.");
      delete pfc;
      return; }

    if (my_clan->get_rank(proper_name(str)) >= my_clan->get_numranks()) {
      echo(proper_name(str) + " has already achieved the highest clanrank.");
      delete pfc;
      return; }

    new_rank = my_clan->get_rank(proper_name(str)) + 1;
    my_clan->set_rank(proper_name(str), new_rank);
    my_clan->save_clanfile();

    string tempstr = get_clan() + " " + my_clan->get_rankname(proper_name(str), tgender);
    echo("You have raised " + proper_name(str) + " to the rank of " + tempstr + ".");

    delete pfc;
    return;
  }

  if (ENT->get_clan() != get_clan()) {
    echo(ENT->get_name() + " is not a member of your clan.");
    return; }

  if (my_clan->get_rank(ENT->get_name()) >= my_clan->get_rank(name)) {
    echo("You cannot promote those of equal or higher rank than you.");
    return; }

  if (my_clan->get_rank(ENT->get_name()) == 0) {
    echo(ENT->get_name() + " has not been enlisted into the clan yet.");
    return; }

  if (my_clan->get_rank(ENT->get_name()) >= my_clan->get_numranks()) {
    echo(ENT->get_name() + " has already achieved the highest clanrank.");
    return; }

  new_rank = my_clan->get_rank(ENT->get_name()) + 1;
  my_clan->set_rank(ENT->get_name(), new_rank);
  my_clan->save_clanfile();

  string temp = get_clan() + " " + my_clan->get_rankname(ENT->get_name(), ENT->get_gender());
  string forclan = name + " has raised " + ENT->get_name() + " to the rank of " + temp + ".";
  echo("You have raised " + ENT->get_name() + " to the rank of " + temp + ".");
  ENT->echo("\r\n" + name + " has raised you to the rank of " + temp + ".");
  clist->clan_xecho(my_client, ENT->get_client(), get_clan(), forclan);
}

void entity::clan_demote(string str)

{
  int new_rank;
  clan *my_clan = World->get_clanlist()->get_clan(get_clan());

  if (my_clan == NULL) {
    echo("You are not in a clan to demote anybody.");
    return; }

  if (my_clan->get_rank(name) == 0) {
    echo("Only accepted clan members can demote others.");
    return; }

  if (my_clan->get_rank(name) < my_clan->get_numranks()-1) {
    echo("You must be at least clanrank " + intconvert(my_clan->get_numranks()-1) + " to demote members.");
    return; }

  if (str == "") {
    echo("Who do you want to demote?");
    return; }

  if (str == get_lname()) {
    echo("You cannot demote yourself.");
    return; }

  entity *ENT = clist->find_character(str);

  if (ENT == NULL)

  {
    string *pfc = File->get_pfile_copy(str);
    int pfsize = stringconvert(pfc[0]);
    string tgender = "";

    if (pfsize == 0) {
      echo("There is nobody in your clan by that name.");
      delete pfc;
      return; }

    for (int i=1; i<=pfsize; i++)
    if (pfc[i].size() >= 8)
    if (pfc[i].substr(0, 8) == "GENDER: ") {
      tgender = pfc[i].substr(8, pfc[i].size()-8);
      break; }

    if (my_clan->get_rank(proper_name(str)) == -1) {
      echo("There is nobody in your clan by that name.");
      delete pfc;
      return; }

    if (my_clan->get_rank(proper_name(str)) >= my_clan->get_rank(name)) {
      echo("You cannot demote those of equal or higher rank than you.");
      delete pfc;
      return; }

    if (my_clan->get_rank(proper_name(str)) == 0) {
      echo(proper_name(str) + " has not been enlisted into the clan yet.");
      delete pfc;
      return; }

    new_rank = my_clan->get_rank(proper_name(str)) - 1;
    my_clan->set_rank(proper_name(str), new_rank);
    my_clan->save_clanfile();

    string tempstr = get_clan() + " " + my_clan->get_rankname(proper_name(str), tgender);
    echo("You have demoted " + proper_name(str) + " to the rank of " + tempstr + ".");

    delete pfc;
    return;
  }

  if (ENT->get_clan() != get_clan()) {
    echo(ENT->get_name() + " is not a member of your clan.");
    return; }

  if (my_clan->get_rank(ENT->get_name()) >= my_clan->get_rank(name)) {
    echo("You cannot demote those of equal or higher rank than you.");
    return; }

  if (my_clan->get_rank(ENT->get_name()) == 0) {
    echo(ENT->get_name() + " has not been enlisted into the clan yet.");
    return; }

  new_rank = my_clan->get_rank(ENT->get_name()) - 1;
  my_clan->set_rank(ENT->get_name(), new_rank);
  my_clan->save_clanfile();

  string temp = get_clan() + " " + my_clan->get_rankname(ENT->get_name(), ENT->get_gender());
  string forclan = name + " has demoted " + ENT->get_name() + " to the rank of " + temp + ".";
  echo("You have demoted " + ENT->get_name() + " to the rank of " + temp + ".");
  ENT->echo("\r\n" + name + " has demoted you to the rank of " + temp + ".");
  clist->clan_xecho(my_client, ENT->get_client(), get_clan(), forclan);
}

void entity::clan_resign(string str)

{
  string temp = "";
  clan *my_clan = World->get_clanlist()->get_clan(get_clan());

  if (my_clan == NULL) {
    echo("You aren't even in a clan!");
    return; }

  if (str != "YES") {
    temp += "To resign from your clan type 'clan resign YES'.\r\n\r\n";
    temp += "WARNING: You will permanently lose your membership and rank.";
    echo(temp);
    return; }

  my_clan->remove_member(name);
  my_clan->save_clanfile();

  set_clan("None");
  File->save_profile(my_client);

  echo("You have resigned from the clan " + my_clan->get_name() + ".");
  clist->clan_xecho(my_client, my_clan->get_name(), name + " has resigned from " + my_clan->get_name() + ".");
}

string clan::display_info()

{
  int width = get_width(clan_info);
  int top_width = (19 + visible_size(name));
  if (top_width > width) width = top_width;
  int sm_width = width-9;

  string border = "  #n+-";
  for (int i=1; i<=width; i++) border += "-";
  border += "-+#N";
  string cborder = "  #n|#N ";
  for (int i=1; i<=width; i++) cborder += " ";
  cborder += " #n|#N";

  string temp = border + "\r\n";

  temp += "  #n|#N Clan Information#n:#N  " + name;
  int name_spaces = width - visible_size(name) - 19;
  for (int i=1; i<=name_spaces; i++) temp += " ";
  temp += " #n|#N\r\n" + border + "\r\n";

  if (get_num_leaders() <= 1) temp += "  #n|#N Leader#R:#N  ";
  else if (get_num_leaders() > 1) temp += "  #n|#N Leaders#R:#N ";

  string leader_string = "";

  for (int i=0; i<size; i++)
  if (ranks[i] == num_ranknames) {
    if (leader_string != "") leader_string += " ";
    leader_string += "#G" + members[i]; }

  if (leader_string == "") leader_string = "None";

  leader_string = variable_allign(leader_string, sm_width);
  int first_time = 1;

  while (leader_string.size() > 0)

  {
    if (!first_time) temp += "  #n|#N          ";
    first_time = 0;

    string str = strip_string(&leader_string);
    temp += str;
    for (int i=1; i<=sm_width-visible_size(str); i++) temp += " ";
    temp += " #n|#N\r\n";
  }

  temp += border + "\r\n";
  temp += "  #n|#N Council#R:#N ";

  string council_string = "";

  for (int i=0; i<size; i++)
  if (ranks[i] == num_ranknames-1) {
    if (council_string != "") council_string += " ";
    council_string += "#C" + members[i]; }

  if (council_string == "") council_string = "None";

  council_string = variable_allign(council_string, sm_width);
  first_time = 1;

  while (council_string.size() > 0)

  {
    if (!first_time) temp += "  #n|#N          ";
    first_time = 0;

    string str = strip_string(&council_string);
    temp += str;
    for (int i=1; i<=sm_width-visible_size(str); i++) temp += " ";
    temp += " #n|#N\r\n";
  }

  temp += border + "\r\n" + cborder + "\r\n";
  string cinfo = clan_info;

  while (cinfo.size() > 0)

  {
    string str = strip_string(&cinfo);
    temp += "  #n|#N " + str;
    for (int i=1; i<=width-visible_size(str); i++) temp += " ";
    temp += " #n|#N\r\n";
  }

  temp += cborder + "\r\n" + border;

  return temp;
}

string clan::display_roster()

{
  int roster_count = 0, mcount = 0;
  int width = visible_size(name) + 14;
  int w1 = 0, w2 = 0, w3 = 0, w4 = 0;
  string temp = "", border = "";

  string *Names = new string [size];
  string *Class = new string [size];
  int *Ranks = new int [size];

  for (int i=0; i<size; i++)
  if (ranks[i] > 0) {
    Names[mcount] = members[i];
    Class[mcount] = classes[i];
    Ranks[mcount] = ranks[i];
    mcount++; }

  for (int i=0; i<size; i++) {
    roster_count++;
    if (((roster_count%4) == 1) && (visible_size(Names[i]) > w1)) w1 = visible_size(Names[i]);
    if (((roster_count%4) == 2) && (visible_size(Names[i]) > w2)) w2 = visible_size(Names[i]);
    if (((roster_count%4) == 3) && (visible_size(Names[i]) > w3)) w3 = visible_size(Names[i]);
    if (((roster_count%4) == 0) && (visible_size(Names[i]) > w4)) w4 = visible_size(Names[i]); }

  if ((roster_count == 1) && ((w1+6) > width)) width = w1 + 6;
  else if ((roster_count == 2) &&  ((w1+w2+13) > width)) width = w1 + w2 + 13;
  else if ((roster_count == 3) &&  ((w1+w2+w3+20) > width)) width = w1 + w2 + w3 + 20;
  else if ((roster_count >= 4) &&  ((w1+w2+w3+w4+26) > width)) width = w1 + w2 + w3 + w4 + 26;

  border += "  #n+-";
  for (int i=1; i<=width; i++) border += "-";
  border += "-+#N";

  temp += border + "\r\n" + "  #n|#N Clan Roster#n:#N  " + name;
  int num_spaces = width - visible_size(name) - 14;
  for (int i=1; i<=num_spaces; i++) temp += " ";
  temp += " #n|#N\r\n" + border + "\r\n";

  int count = 0;

  for (int i=0; i<size; i++)

  {
    count++;

    if ((count%4) == 1) temp += "  #n|#N";
    temp += " [" + sclassify(Class[i]) + "] " + Names[i] + " ";
    if ((count%4) == 1) for (int j=1; j<=w1-visible_size(Names[i]); j++) temp += " ";
    if ((count%4) == 2) for (int j=1; j<=w2-visible_size(Names[i]); j++) temp += " ";
    if ((count%4) == 3) for (int j=1; j<=w3-visible_size(Names[i]); j++) temp += " ";
    if ((count%4) == 0) for (int j=1; j<=w4-visible_size(Names[i]); j++) temp += " ";
    if ((count%4) == 0) temp += "#n|#N\r\n";

    if ((count == roster_count) && ((count%4) == 1)) {
      for (int j=1; j<=(width-w1-5); j++) temp += " ";  temp += "#n|#N\r\n"; }
    if ((count == roster_count) && ((count%4) == 2)) {
      for (int j=1; j<=(width-w1-w2-12); j++) temp += " ";  temp += "#n|#N\r\n"; }
    if ((count == roster_count) && ((count%4) == 3)) {
      for (int j=1; j<=(width-w1-w2-w3-19); j++) temp += " ";  temp += "#n|#N\r\n"; }
  }

  temp += border;

  delete [] Names;
  delete [] Ranks;
  delete [] Class;

  return temp;
}

string clan::display_ranknames()

{
  int mwidth = 0, fwidth = 0;
  string mline = "", fline = "", mspace = "", fspace = "", temp = "";

  for (int i=1; i<=num_ranknames+1; i++) {
    if (visible_size(male_ranknames[i]) > mwidth) mwidth = visible_size(male_ranknames[i]);
    if (visible_size(female_ranknames[i]) > fwidth) fwidth = visible_size(female_ranknames[i]); }

  mwidth += 11;
  fwidth += 11;

  for (int i=1; i<=mwidth; i++) mline += "-";
  for (int i=1; i<=fwidth; i++) fline += "-";
  for (int i=1; i<=mwidth; i++) mspace += " ";
  for (int i=1; i<=fwidth; i++) fspace += " ";

  temp += "  #n+-" + mline + "---" + fline + "-+#N\r\n";
  temp += "  #n|#N " + name + " #NClanrank Titles#n:#N";

  int num_spaces = mwidth + fwidth - visible_size(name) - 14;
  for (int i=1; i<=num_spaces; i++) temp += " ";
  temp += " #n|#N\r\n";

  temp += "  #n+-" + mline + "-+-" + fline + "-+#N\r\n";
  temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";

  temp += "  #n|#N Male Ranks#R:#N";
  for (int i=1; i<=mwidth-11; i++) temp += " ";
  temp += " #n|#N Female Ranks#R:#N";
  for (int i=1; i<=fwidth-13; i++) temp += " ";
  temp += " #n|#N\r\n";

  temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";

  for (int i=1; i<=num_ranknames; i++)

  {
    temp += "  #n|#Y Rank " + intconvert(i);
    if (i < 10) temp += " ";
    temp += "  #n-#N " + male_ranknames[i];
    int num_male_spaces = mwidth - visible_size(male_ranknames[i]) - 11;
    for (int j=1; j<=num_male_spaces; j++) temp += " ";
    temp += " #n|#Y Rank " + intconvert(i);
    if (i < 10) temp += " ";
    temp += "  #n-#N " + female_ranknames[i];
    int num_female_spaces = fwidth - visible_size(female_ranknames[i]) - 11;
    for (int j=1; j<=num_female_spaces; j++) temp += " ";
    temp += " #n|#N\r\n";
  }

  temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";

  temp += "  #n|#C Immortal #n-#N " + male_ranknames[num_ranknames+1];
  int num_mi_spaces = mwidth - visible_size(male_ranknames[num_ranknames+1]) - 11;
  for (int i=1; i<=num_mi_spaces; i++) temp += " ";
  temp += " #n|#C Immortal #n-#N " + female_ranknames[num_ranknames+1];
  int num_fi_spaces = fwidth - visible_size(female_ranknames[num_ranknames+1]) - 11;
  for (int i=1; i<=num_fi_spaces; i++) temp += " ";
  temp += " #n|#N\r\n";

  temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";
  temp += "  #n+-" + mline + "-+-" + fline + "-+#N";

  return temp;
}

void entity::clan_command(string str)

{
  string key = lowercase(first(str));
  string rem = lowercase(last(str));
  string Rem = last(str);
  string frem = first(rem);

  if (match(key, "l", "listing"))
    echo(World->get_clanlist()->display_clanlist());

  else if (match(key, "i", "information"))

  {
    if (rem != "")

    {
      clan *the_clan = World->get_clanlist()->get_clan(rem);

      if (the_clan == NULL) {
        echo("There is no clan by that name.");
        return; }

      echo(the_clan->display_info());
    }

    else echo(World->get_clanlist()->display_clanlist());
  }

  else if (match(key, "r", "roster"))

  {
    if (rem != "")

    {
      clan *the_clan = World->get_clanlist()->get_clan(rem);

      if (the_clan == NULL) {
        echo("There is no clan by that name.");
        return; }

      echo(the_clan->display_roster());
    }

    else echo("Which clan do you wish to view the roster of?");
  }

  else if (match(key, "r", "ranks"))

  {
    if (rem != "")

    {
      clan *the_clan = World->get_clanlist()->get_clan(rem);

      if (the_clan == NULL) {
        echo("There is no clan by that name.");
        return; }

      echo(the_clan->display_ranknames());
    }

    else echo("Which clan do you wish to view the ranks of?");
  }

  else if ((match(key, "ap", "apply")) || (match(key, "jo", "join")))     clan_apply(rem);
  else if ((match(key, "en", "enlist")) || (match(key, "ac", "accept")))  clan_enlist(frem);
  else if ((match(key, "ex", "expel")) || (match(key, "re", "remove")))   clan_expel(frem);
  else if ((match(key, "ra", "raise")) || (match(key, "pr", "promote")))  clan_raise(frem);
  else if ((match(key, "de", "demote")) || (match(key, "lo", "lower")))   clan_demote(frem);
  else if ((match(key, "res", "resign")) || (match(key, "qu", "quit")))   clan_resign(Rem);
  else echo(clan_commands());
}