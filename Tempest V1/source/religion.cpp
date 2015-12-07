#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

religion::religion(int ID, string NAME)

{
  id = ID;
  size = 0;
  name = NAME;
  religion_info = "";

  members = new string [1];
  classes = new string [1];
  ranks = new int [1];
}

void religion::set_rankname(int rank, string male, string female)

{
  male_ranknames[rank] = male;
  female_ranknames[rank] = female;
}

void religion::set_religion_info(string str)

{
  str = "     " + str;
  religion_info = variable_allign(str, INFO_WIDTH);
}

void religion::set_diety_name(string dname)

{
  diety_name = dname;
}

void religion::set_diety_title(string dtitle)

{
  diety_title = dtitle;
}

string religion::get_name()

{
  return name;
}

int religion::get_num_leaders()

{
  int count = 0;

  for (int i=0; i<size; i++)
    if (ranks[i] == 4)
      count++;

  return count;
}

int religion::get_size()

{
  int count = 0;

  for (int i=0; i<size; i++)
    if (ranks[i] > 0)
      count++;

  return count;
}

void religion::add_member(string mname, int rank, string profession)

{
  size++;

  string *temp_names = new string [size];
  string *temp_class = new string [size];
  int *temp_ranks = new int [size];

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
  classes = temp_class;
  ranks = temp_ranks;
}

void religion::remove_member(string Name)

{
  size--;

  string *temp_names = new string [size];
  string *temp_class = new string [size];
  int *temp_ranks = new int [size];
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
  classes = temp_class;
  ranks = temp_ranks;
}

int religion::get_rank(string Name)

{
  for (int i=0; i<size; i++)
    if (members[i] == Name)
      return ranks[i];

  return -1;
}

void religion::set_rank(string Name, int num)

{
  for (int i=0; i<size; i++)
    if (members[i] == Name)
      ranks[i] = num;
}

string religion::get_class(string Name)

{
  for (int i=0; i<size; i++)
    if (members[i] == Name)
      return classes[i];

  return "";
}

void religion::set_class(string Name, string profession)

{
  for (int i=0; i<size; i++)
    if (members[i] == Name)
      classes[i] = profession;
}

string religion::get_rankname(string Name, string Gender)

{
  for (int i=0; i<size; i++)
  if (members[i] == Name)

  {
    if (Gender == "male") return male_ranknames[ranks[i]];
    if (Gender == "female") return female_ranknames[ranks[i]];
  }

  return "";
}

string religion::get_diety_name()

{
  return diety_name;
}

string religion::get_diety_title()

{
  return diety_title;
}

void religion::save_religionfile()

{
  FILE *religionfile;
  string s1, ID;

  ID = intconvert(id);
  if (id < 100) ID = "0" + ID;
  if (id < 10) ID = "0" + ID;

  s1 = ".\\religions\\religion" + ID + ".dat";
  religionfile = fopen((char*)s1.c_str(), "w");

  s1 = "Religion = " + name + "\n";
  fprintf(religionfile, (char*)s1.c_str());

  s1 = "Diety = " + diety_name + "\n";
  fprintf(religionfile, (char*)s1.c_str());

  s1 = "Title = " + diety_title + "\n";
  fprintf(religionfile, (char*)s1.c_str());

  s1 = "Members = " + intconvert(size) + "\n\n";
  fprintf(religionfile, (char*)s1.c_str());

  for (int i=1; i<=4; i++)

  {
    s1 = "[0";
    s1 += intconvert(i) + "] [" + male_ranknames[i] + "] [" + female_ranknames[i] + "]\n";
    if (i == 4) s1 += "\n";

    fprintf(religionfile, (char*)s1.c_str());
  }

  string info_output = religion_info;
  info_output.erase(0,5);

  s1 = "[BEGIN]\n" + info_output + "\n[END]\n\n";
  fprintf(religionfile, (char*)s1.c_str());

  for (int i=0; i<size; i++) {
    s1 = members[i] + " " + intconvert(ranks[i]) + " " + classes[i] + "\n";
    fprintf(religionfile, (char*)s1.c_str()); }

  s1 = "\n-End- ";
  fprintf(religionfile, (char*)s1.c_str());

  fclose(religionfile);
}

void religion::verify_members()

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

      while (temp != "RELIGION: ")

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

  save_religionfile();
}

void entity::religion_apply(string cname)

{
  if (!PLAYER) return;
  religionlist *RL = World->get_religionlist();
  religion *the_religion = RL->get_religion(cname);

  if (get_level() > 99) {
    echo("You are too Godly to worship anybody but yourself.");
    return; }

  if (get_religion() != "None") {
    if (RL->get_religion(get_religion())->get_rank(name) > 0) echo("You are already in a religion.");
    else echo("You are already applying to a religion.");
    return; }

  if (cname == "") {
    echo("Which religion do you want to apply to?");
    return; }

  if (the_religion == NULL) {
    echo("That religion does not exist.");
    return; }

  the_religion->add_member(name, 0, get_class());
  the_religion->save_religionfile();

  set_religion(the_religion->get_name());
  File->save_profile(my_client);

  echo("You are now applying to the religious order of " + the_religion->get_name() + ".");
}

void entity::religion_admit(string str)

{
  bool absolute_power = false;
  religion *my_religion = World->get_religionlist()->get_religion(get_religion());

  if (get_level() > 99) {
    my_religion = World->get_religionlist()->get_religion_diety(name);
    if (my_religion != NULL) absolute_power = true; }

  if (my_religion == NULL) {
    echo("You are not in a religion to admit anybody.");
    return; }

  if (absolute_power == false)
  if (my_religion->get_rank(name) < 3) {
    echo("Only ordained Priests can admit worshippers.");
    return; }

  if (str == "") {
    echo("Who do you want to admit as a worshipper?");
    return; }

  entity *ENT = clist->find_character(str);

  if (ENT == NULL) {
    echo("There is nobody around by that name.");
    return; }

  religion *their_religion = World->get_religionlist()->get_religion(ENT->get_religion());

  if (ENT == this) {
    echo("Admit yourself into the religion that you are already in?  Okay...");
    return; }

  if (my_religion != their_religion) {
    echo(ENT->get_name() + " is not applying to your religion.");
    return; }

  if (my_religion->get_rank(ENT->get_name()) > 0) {
    echo(ENT->get_name() + " is already a member of your religion!");
    return; }

  if (ENT->get_level() > 99) {
    echo(ENT->get_name() + " is much too Godly to worship anybody else.");
    return; }

  my_religion->set_rank(ENT->get_name(), 1);
  my_religion->save_religionfile();

  string forreligion = name + " has initiated " + ENT->get_name() + " into " + ENT->get_religion() + ".";
  echo("You accept " + ENT->get_name() + " into " + ENT->get_religion() + ".");
  ENT->echo("\r\n" + name + " has accepted you into the religion " + ENT->get_religion() + ".");
  rm->xecho(this, ENT, forreligion);
}

void entity::religion_excommunicate(string str)

{
  bool absolute_power = false;
  religion *my_religion = World->get_religionlist()->get_religion(get_religion());

  if (get_level() > 99) {
    my_religion = World->get_religionlist()->get_religion_diety(name);
    if (my_religion != NULL) absolute_power = true; }

  if (my_religion == NULL) {
    echo("You are not in a religion to excommunicate anybody.");
    return; }

  if (absolute_power == false)
  if (my_religion->get_rank(name) < 3) {
    echo("Only ordained Priests can excommunicate others.");
    return; }

  if (str == "") {
    echo("Who do you want to excommunicate from your religion?");
    return; }

  if (str == get_lname()) {
    echo("Excommunicate yourself?  It's a little easier to just leave the religion.");
    return; }

  entity *ENT = clist->find_character(str);

  if (ENT == NULL)

  {
    string *pfc = File->get_pfile_copy(str);
    int pfsize = stringconvert(pfc[0]);
    int in_religion = 0;

    if (pfsize == 0) {
      echo("There is nobody in your religion by that name.");
      delete pfc;
      return; }

    for (int i=1; i<=pfsize; i++)
    if (pfc[i].size() >= 10)
    if (pfc[i].substr(0, 10) == "RELIGION: ")
    if (pfc[i].substr(10, pfc[i].size()-10) == my_religion->get_name()) {
      in_religion = i;
      break; }

    if (in_religion == 0) {
      echo("There is nobody in your religion by that name.");
      delete pfc;
      return; }

    if (absolute_power == false)
    if (my_religion->get_rank(proper_name(str)) >= my_religion->get_rank(name)) {
      echo("You cannot excommunicate those of equal or higher degree than you.");
      delete pfc;
      return; }

    pfc[in_religion] = "RELIGION: None";
    File->set_pfile_copy(proper_name(str), pfc);

    my_religion->remove_member(proper_name(str));
    my_religion->save_religionfile();

    string forthereligion = name + " has excommunicated " + proper_name(str) + " from " + my_religion->get_name() + ".";
    echo("You have excommunicated " + proper_name(str) + " from " + my_religion->get_name() + ".");
    rm->xecho(this, forthereligion);

    return;
  }

  religion *their_religion = World->get_religionlist()->get_religion(ENT->get_religion());

  if (my_religion != their_religion) {
    echo(ENT->get_name() + " is not a member of your religion.");
    return; }

  if (absolute_power == false)
  if (my_religion->get_rank(ENT->get_name()) >= my_religion->get_rank(name)) {
    echo("You cannot excommunicate those of equal or higher degree than you.");
    return; }

  my_religion->remove_member(ENT->get_name());
  my_religion->save_religionfile();

  ENT->set_religion("None");
  File->save_profile(ENT->get_client());

  string forreligion = name + " has excommunicated " + ENT->get_name() + " from " + my_religion->get_name() + ".";
  echo("You have excommunicated " + ENT->get_name() + " from " + my_religion->get_name() + ".");
  ENT->echo("\r\n" + name + " has excommunicated you from " + my_religion->get_name() + ".");
  rm->xecho(this, ENT, forreligion);
}

void entity::religion_raise(string str)

{
  int new_rank;
  bool absolute_power = false;
  religion *my_religion = World->get_religionlist()->get_religion(get_religion());

  if (get_level() > 99) {
    my_religion = World->get_religionlist()->get_religion_diety(name);
    if (my_religion != NULL) absolute_power = true; }

  if (my_religion == NULL) {
    echo("You are not in a religion to promote anybody.");
    return; }

  if (absolute_power == false)
  if (my_religion->get_rank(name) < 3) {
    echo("Only ordained Priests can promote others.");
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
      echo("There is nobody in your religion by that name.");
      delete pfc;
      return; }

    for (int i=1; i<=pfsize; i++)
    if (pfc[i].size() >= 8)
    if (pfc[i].substr(0, 8) == "GENDER: ") {
      tgender = pfc[i].substr(8, pfc[i].size()-8);
      break; }

    if (my_religion->get_rank(proper_name(str)) == -1) {
      echo("There is nobody in your religion by that name.");
      delete pfc;
      return; }

    if (absolute_power == false)
    if (my_religion->get_rank(proper_name(str)) >= my_religion->get_rank(name)) {
      echo("You cannot promote those of equal or higher degree than you.");
      delete pfc;
      return; }

    if (my_religion->get_rank(proper_name(str)) == 0) {
      echo(proper_name(str) + " has not been admitted into the religion yet.");
      delete pfc;
      return; }

    if (my_religion->get_rank(proper_name(str)) >= 4) {
      echo(proper_name(str) + " has already achieved the highest religious degree.");
      delete pfc;
      return; }

    if (absolute_power == false)
    if (my_religion->get_rank(proper_name(str)) == 3) {
      if (tgender == "male") echo("Only " + my_religion->get_diety_name() + " can ordain High Priests.");
      else echo("Only " + my_religion->get_diety_name() + " can ordain High Priests.");
      delete pfc;
      return; }

    new_rank = my_religion->get_rank(proper_name(str)) + 1;
    my_religion->set_rank(proper_name(str), new_rank);
    my_religion->save_religionfile();

    string tempstr = my_religion->get_rankname(proper_name(str), tgender);
    echo("You have raised " + proper_name(str) + " to the degree of " + tempstr + ".");

    delete pfc;
    return;
  }

  religion *their_religion = World->get_religionlist()->get_religion(ENT->get_religion());

  if (my_religion != their_religion) {
    echo(ENT->get_name() + " is not a member of your religion.");
    return; }

  if (absolute_power == false)
  if (my_religion->get_rank(ENT->get_name()) >= my_religion->get_rank(name)) {
    echo("You cannot promote those of equal or higher degree than you.");
    return; }

  if (my_religion->get_rank(ENT->get_name()) == 0) {
    echo(ENT->get_name() + " has not been admitted into the religion yet.");
    return; }

  if (my_religion->get_rank(ENT->get_name()) >= 4) {
    echo(ENT->get_name() + " has already achieved the highest religious degree.");
    return; }

  if (absolute_power == false)
  if (my_religion->get_rank(proper_name(str)) == 3) {
    if (ENT->get_gender() == "male") echo("Only " + my_religion->get_diety_name() + " can ordain High Priests.");
    else echo("Only " + my_religion->get_diety_name() + " can ordain High Priests.");
    return; }

  new_rank = my_religion->get_rank(ENT->get_name()) + 1;
  my_religion->set_rank(ENT->get_name(), new_rank);
  my_religion->save_religionfile();

  string temp = my_religion->get_rankname(ENT->get_name(), ENT->get_gender());
  string forreligion = name + " has raised " + ENT->get_name() + " to the degree of " + temp + ".";
  echo("You have raised " + ENT->get_name() + " to the degree of " + temp + ".");
  ENT->echo("\r\n" + name + " has raised you to the degree of " + temp + ".");
  rm->xecho(this, ENT, forreligion);
}

void entity::religion_demote(string str)

{
  int new_rank;
  bool absolute_power = false;
  religion *my_religion = World->get_religionlist()->get_religion(get_religion());

  if (get_level() > 99) {
    my_religion = World->get_religionlist()->get_religion_diety(name);
    if (my_religion != NULL) absolute_power = true; }

  if (my_religion == NULL) {
    echo("You are not in a religion to demote anybody.");
    return; }

  if (absolute_power == false)
  if (my_religion->get_rank(name) < 3) {
    echo("Only accepted religion members can demote others.");
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
      echo("There is nobody in your religion by that name.");
      delete pfc;
      return; }

    for (int i=1; i<=pfsize; i++)
    if (pfc[i].size() >= 8)
    if (pfc[i].substr(0, 8) == "GENDER: ") {
      tgender = pfc[i].substr(8, pfc[i].size()-8);
      break; }

    if (my_religion->get_rank(proper_name(str)) == -1) {
      echo("There is nobody in your religion by that name.");
      delete pfc;
      return; }

    if (absolute_power == false)
    if (my_religion->get_rank(proper_name(str)) >= my_religion->get_rank(name)) {
      echo("You cannot demote those of equal or higher degree than you.");
      delete pfc;
      return; }

    if (my_religion->get_rank(proper_name(str)) == 0) {
      echo(proper_name(str) + " has not been enlisted into the religion yet.");
      delete pfc;
      return; }

    new_rank = my_religion->get_rank(proper_name(str)) - 1;
    my_religion->set_rank(proper_name(str), new_rank);
    my_religion->save_religionfile();

    string tempstr = my_religion->get_rankname(proper_name(str), tgender);
    echo("You have lowered " + proper_name(str) + " to the degree of " + tempstr + ".");

    delete pfc;
    return;
  }

  religion *their_religion = World->get_religionlist()->get_religion(ENT->get_religion());

  if (my_religion != their_religion) {
    echo(ENT->get_name() + " is not a member of your religion.");
    return; }

  if (absolute_power == false)
  if (my_religion->get_rank(ENT->get_name()) >= my_religion->get_rank(name)) {
    echo("You cannot demote those of equal or higher degree than you.");
    return; }

  if (my_religion->get_rank(ENT->get_name()) == 0) {
    echo(ENT->get_name() + " has not been enlisted into the religion yet.");
    return; }

  new_rank = my_religion->get_rank(ENT->get_name()) - 1;
  my_religion->set_rank(ENT->get_name(), new_rank);
  my_religion->save_religionfile();

  string temp = my_religion->get_rankname(ENT->get_name(), ENT->get_gender());
  string forreligion = name + " has lowered " + ENT->get_name() + " to the degree of " + temp + ".";
  echo("You have lowered " + ENT->get_name() + " to the degree of " + temp + ".");
  ENT->echo("\r\n" + name + " has lower you to the degree of " + temp + ".");
  rm->xecho(this, ENT, forreligion);
}

void entity::religion_forsake(string str)

{
  string temp = "";
  religion *my_religion = World->get_religionlist()->get_religion(get_religion());

  if (my_religion == NULL) {
    echo("You aren't even a member of a religious order!");
    return; }

  if (str != "YES") {
    temp += "To forsake your current religion type 'religion forsake YES'.\r\n\r\n";
    temp += "WARNING: You will permanently lose your membership and degree.";
    echo(temp);
    return; }

  my_religion->remove_member(name);
  my_religion->save_religionfile();

  set_religion("None");
  File->save_profile(my_client);

  echo("You have forsaken " + my_religion->get_name() + ".");
}

string religion::display_info()

{
  int width = get_width(religion_info);
  int top_width = (23 + visible_size(name));
  if (top_width > width) width = top_width;
  int sm_width = width-14;

  string border = "  #n+-";
  for (int i=1; i<=width; i++) border += "-";
  border += "-+#N";
  string cborder = "  #n|#N ";
  for (int i=1; i<=width; i++) cborder += " ";
  cborder += " #n|#N";

  string temp;

  temp += border + "\r\n";
  temp += "  #n|#N Religion Information#n:#N  " + name;
  int name_spaces = width - visible_size(name) - 23;
  for (int i=1; i<=name_spaces; i++) temp += " ";
  temp += " #n|#N\r\n";

  temp += border + "\r\n";
  temp += "  #n|#N Patron Diety#R:#N ";

  string patron_string;
  patron_string += "#c" + diety_name + "#N";

  if (visible_size(patron_string) <= 0) patron_string = "None";

  patron_string = variable_allign(patron_string, sm_width);
  int first_time = 1;

  while (patron_string.size() > 0)

  {
    if (!first_time) temp += "  #n|#N          ";
    first_time = 0;

    string str = strip_string(&patron_string);
    temp += str;
    for (int i=1; i<=sm_width-visible_size(str); i++) temp += " ";
    temp += " #n|#N\r\n";
  }

  temp += border + "\r\n";
  if (get_num_leaders() <= 1) temp += "  #n|#N High Priest#R:#N  ";
  else if (get_num_leaders() > 1) temp += "  #n|#N High Priests#R:#N ";

  string leader_string = "";

  for (int i=0; i<size; i++)
  if (ranks[i] == 4) {
    if (leader_string != "") leader_string += " ";
    leader_string += "#y" + members[i]; }

  if (leader_string == "") leader_string = "None";

  leader_string = variable_allign(leader_string, sm_width);
  first_time = 1;

  while (leader_string.size() > 0)

  {
    if (!first_time) temp += "  #n|#N          ";
    first_time = 0;

    string str = strip_string(&leader_string);
    temp += str;
    for (int i=1; i<=sm_width-visible_size(str); i++) temp += " ";
    temp += " #n|#N\r\n";
  }

  temp += border + "\r\n" + cborder + "\r\n";
  string cinfo = religion_info;

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

string religion::display_roster()

{
  int roster_count = 0, mcount = 0;
  int width = visible_size(name) + 19;
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

  temp += border + "\r\n" + "  #n|#N Religion Members#n:#N  " + name;
  int num_spaces = width - visible_size(name) - 19;
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

string religion::display_ranknames()

{
  int mwidth = 0, fwidth = 0;
  string mline = "", fline = "", mspace = "", fspace = "", temp = "";

  for (int i=1; i<=4; i++) {
    if (visible_size(male_ranknames[i]) > mwidth) mwidth = visible_size(male_ranknames[i]);
    if (visible_size(female_ranknames[i]) > fwidth) fwidth = visible_size(female_ranknames[i]); }

  for (int i=1; i<=mwidth; i++) mline += "-";
  for (int i=1; i<=fwidth; i++) fline += "-";
  for (int i=1; i<=mwidth; i++) mspace += " ";
  for (int i=1; i<=fwidth; i++) fspace += " ";

  temp += "  #n+-" + mline + "---" + fline + "-+#N\r\n";
  temp += "  #n|#N " + name + " #NReligious Degrees#n:#N";

  int num_spaces = mwidth + fwidth - visible_size(name) - 16;
  for (int i=1; i<=num_spaces; i++) temp += " ";
  temp += " #n|#N\r\n";

  temp += "  #n+-" + mline + "-+-" + fline + "-+#N\r\n";
  temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";

  temp += "  #n|#N Male Degrees#R:#N";
  for (int i=1; i<=mwidth-13; i++) temp += " ";
  temp += " #n|#N Female Degrees#R:#N";
  for (int i=1; i<=fwidth-15; i++) temp += " ";
  temp += " #n|#N\r\n";

  temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";

  for (int i=1; i<=4; i++)

  {
    temp += "  #n|#N " + male_ranknames[i];
    int num_male_spaces = mwidth - visible_size(male_ranknames[i]);
    for (int j=1; j<=num_male_spaces; j++) temp += " ";
    temp += " #n|#N " + female_ranknames[i];
    int num_female_spaces = fwidth - visible_size(female_ranknames[i]);
    for (int j=1; j<=num_female_spaces; j++) temp += " ";
    temp += " #n|#N\r\n";
  }

  temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";
  temp += "  #n+-" + mline + "-+-" + fline + "-+#N";

  return temp;
}

void entity::religion_command(string str)

{
  string key = lowercase(first(str));
  string rem = lowercase(last(str));
  string Rem = last(str);
  string frem = first(rem);

  if (match(key, "l", "listing"))
    echo(World->get_religionlist()->display_religionlist());

  else if (match(key, "i", "information"))

  {
    if (rem != "")

    {
      religion *the_religion = World->get_religionlist()->get_religion(rem);

      if (the_religion == NULL) {
        echo("There is no religion by that name.");
        return; }

      echo(the_religion->display_info());
    }

    else echo(World->get_religionlist()->display_religionlist());
  }

  else if (match(key, "m", "memberships"))

  {
    if (rem != "")

    {
      religion *the_religion = World->get_religionlist()->get_religion(rem);

      if (the_religion == NULL) {
        echo("There is no religion by that name.");
        return; }

      echo(the_religion->display_roster());
    }

    else echo("Which religion do you wish to view the members of?");
  }

  else if ((match(key, "d", "degrees")) || (match(key, "ra", "ranks")))

  {
    if (rem != "")

    {
      religion *the_religion = World->get_religionlist()->get_religion(rem);

      if (the_religion == NULL) {
        echo("There is no religion by that name.");
        return; }

      echo(the_religion->display_ranknames());
    }

    else echo("Which religion do you wish to view the degrees of?");
  }

  else if ((match(key, "ap", "apply")) || (match(key, "jo", "join")))     religion_apply(rem);
  else if ((match(key, "ad", "admit")) || (match(key, "ac", "accept")))   religion_admit(frem);
  else if ((match(key, "ra", "raise")) || (match(key, "pr", "promote")))  religion_raise(frem);
  else if ((match(key, "de", "demote")) || (match(key, "lo", "lower")))   religion_demote(frem);
  else if (match(key, "ex", "excommunicate")) religion_excommunicate(frem);
  else if (match(key, "for", "forsake")) religion_forsake(Rem);
  else echo(religion_commands());
}