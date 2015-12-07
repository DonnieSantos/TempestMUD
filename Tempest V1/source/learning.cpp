#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

void entity::learn(string str)

{
  string error_msg = "Learn Usage#n:#N #R[#Nlearn ability_name from teacher_name#R]#N";

  if ((str.find(" from ") == string::npos) || (str.size() == 0)) {
    echo(error_msg);
    return; }

  int pos = str.find(" from ");
  string ability = str.substr(0, pos);
  string tname = str.substr(pos+6, str.size()-pos-6);

  entity *teacher = rm->find_entity(this, tname);

  if (teacher == NULL) {
    echo("Nobody around by that name.");
    return; }

  if (teacher == this) {
    echo("You cannot teach yourself.");
    return; }

  if (!teacher->PLAYER) {
    echo(teacher->get_He() + " cannot teach you anything.");
    return; }

  int found = 0;
  string atype = "";
  int num_skills = skill_list.size();
  int num_spells = spell_list.size();

  for (int i=1; i<=num_skills; i++)
    if (lowercase(skill_list[i].name) == ability) {
      atype = "skill";
      found = i; }

  for (int i=1; i<=num_spells; i++)
    if (lowercase(spell_list[i].name) == ability) {
      atype = "spell";
      found = i; }

  if (!found) {
    echo("There is no ability by that name.");
    return; }

  if ((atype == "skill") && (skill_list[found].learned == 1)) {
    echo("You already know that skill.");
    return; }

  if ((atype == "spell") && (spell_list[found].learned == 1)) {
    echo("You already know that spell.");
    return; }

  if ((atype == "skill")
  && ((skill_list[found].classes.find(get_ssclass()) == string::npos)
  && (skill_list[found].classes.find("All") == string::npos))) {
    echo("There is no skill by that name which you can learn.");
    return; }

  if ((atype == "spell")
  && ((spell_list[found].classes.find(get_ssclass()) == string::npos)
  && (spell_list[found].classes.find("All") == string::npos))) {
    echo("There is no spell by that name which you can learn.");
    return; }

  if ((atype == "skill")
  && ((skill_list[found].classes.find(teacher->get_ssclass()) == string::npos)
  && (skill_list[found].classes.find("All") == string::npos))) {
    echo(skill_list[found].name + " cannot be taught by " + class_plural(teacher->get_class()) + ".");
    return; }

  if ((atype == "spell")
  && ((spell_list[found].classes.find(teacher->get_ssclass()) == string::npos)
  && (spell_list[found].classes.find("All") == string::npos))) {
    echo(spell_list[found].name + " cannot be taught by " + class_plural(teacher->get_class()) + ".");
    return; }

  string aname;
  int STR_REQ = 0, DEX_REQ = 0, CON_REQ = 0, INT_REQ = 0, WIS_REQ = 0;

  if (atype == "skill") aname = skill_list[found].name;   else aname = spell_list[found].name;
  if (atype == "skill") STR_REQ = skill_list[found].STR;  else STR_REQ = spell_list[found].STR;
  if (atype == "skill") DEX_REQ = skill_list[found].DEX;  else DEX_REQ = spell_list[found].DEX;
  if (atype == "skill") CON_REQ = skill_list[found].CON;  else CON_REQ = spell_list[found].CON;
  if (atype == "skill") INT_REQ = skill_list[found].INT;  else INT_REQ = spell_list[found].INT;
  if (atype == "skill") WIS_REQ = skill_list[found].WIS;  else WIS_REQ = spell_list[found].WIS;

  if (get_STR() < STR_REQ) {
    echo("You lack the strength to learn " + aname + ".");
    return; }

  if (get_DEX() < DEX_REQ) {
    echo("You lack the dexterity to learn " + aname + ".");
    return; }

  if (get_CON() < CON_REQ) {
    echo("You lack the constitution to learn " + aname + ".");
    return; }

  if (get_INT() < INT_REQ) {
    echo("You lack the intelligence to learn " + aname + ".");
    return; }

  if (get_WIS() < WIS_REQ) {
    echo("You lack the wisdom to learn " + aname + ".");
    return; }

  int plevel;
  string prerequisite;
  if (atype == "skill") { prerequisite = skill_list[found].prereq;  plevel = skill_list[found].prereq_lev; }
  if (atype == "spell") { prerequisite = spell_list[found].prereq;  plevel = spell_list[found].prereq_lev; }

  int ID = 0;

  if (prerequisite != "None") {
    if (atype == "skill") ID = skill_list.get(prerequisite)->id;
    else ID = spell_list.get(prerequisite)->id; }

  if ((ID != 0) && (atype == "skill"))
  if (skill_list[ID].level < plevel) {
    echo("To learn this skill requires " + skill_list[ID].name + " level " + intconvert(plevel) + ".");
    return; }

  if ((ID != 0) && (atype == "spell"))
  if (spell_list[ID].level < plevel) {
    echo("To learn this spell requires " + skill_list[ID].name + " level " + intconvert(plevel) + ".");
    return; }

  teacher->set_student(name);
  teacher->set_student_request(aname);
  teacher->echo("\r\n" + name + " asks you to teach " + get_him() + " the art of " + aname + ".");
  echo("You ask " + teacher->get_name() + " to teach you the art of " + aname + ".");
}

void entity::teach(string target, string ability)

{
  string error_msg = "Teach Usage#n:#N #R[#Nteach student_name ability_name#R]#N";
  entity *student = rm->find_entity(this, target);

  if ((target == "") || (ability == "")) {
    echo(error_msg);
    return; }

  if (student == NULL) {
    echo("Nobody around by that name.");
    return; }

  if (student == this) {
    echo("You cannot teach yourself.");
    return; }

  if (student->get_name() != my_student) {
    echo(student->get_name() + " has not asked you to teach " + student->get_him() + " anything.");
    return; }

  if (ability != lowercase(my_student_request)) {
    echo(student->get_name() + " would rather learn " + my_student_request + ".");
    return; }

  int found = 0;
  string atype = "";
  int num_skills = skill_list.size();
  int num_spells = spell_list.size();

  for (int i=1; i<=num_skills; i++)
    if ((lowercase(skill_list[i].name) == ability) && (skill_list[i].learned == 1)) {
      atype = "skill";
      found = i; }

  for (int i=1; i<=num_spells; i++)
    if ((lowercase(spell_list[i].name) == ability) && (spell_list[i].learned == 1)) {
      atype = "spell";
      found = i; }

  if (!found) {
    echo("You do not possess an ability by that name.");
    return; }

  if (atype == "skill") {
    my_student = "";
    my_student_request = "";
    student->skill_list.learn(found, 0, 0);
    student->echo("\r\n#g" + name + " teaches you the art of " + skill_list[found].name + "!#N");
    echo("#gYou teach " + student->get_name() + " the art of " + skill_list[found].name + "!#N"); }

  else if (atype == "spell") {
    my_student = "";
    my_student_request = "";
    student->spell_list.learn(found, 0, 0);
    student->echo("\r\n#g" + name + " teaches you the art of " + spell_list[found].name + "!#N");
    echo("#gYou teach " + student->get_name() + " the art of " + spell_list[found].name + "!#N"); }
}

void entity::unlearn(string ability)

{
  int are_you_sure = 1;

  if (ability.find("YES") == string::npos)
    are_you_sure = 0;

  if (!are_you_sure) {
    string temp = "Unlearn Usage#n:#R [#Nunlearn ability_name YES#R]#N\r\n\n";
    temp += "WARNING: You will lose current ability level permanently!";
    echo(temp);
    return; }

  int pos = ability.find("YES");
  ability.erase(pos, 3);
  ability = clear_whitespace(ability);

  int found = 0;
  string atype = "";
  int num_skills = skill_list.size();
  int num_spells = spell_list.size();

  for (int i=1; i<=num_skills; i++)
    if ((lowercase(skill_list[i].name) == ability) && (skill_list[i].learned == 1)) {
      atype = "skill";
      found = i; }

  for (int i=1; i<=num_spells; i++)
    if ((lowercase(spell_list[i].name) == ability) && (spell_list[i].learned == 1)) {
      atype = "spell";
      found = i; }

  if (!found) {
    echo("You do not possess an ability by that name.");
    return; }

  if (atype == "skill") {
    skill_list.unlearn(found);
    echo("#rYou have forgotten the " + skill_list[found].name + " ability!#N"); }

  else if (atype == "spell") {
    spell_list.unlearn(found);
    echo("#rYou have forgotten the " + spell_list[found].name + " ability!#N"); }
}

void entity::display_skills()

{
  int x, lsize = 0, rsize = 0, count = 0;
  string tempstr = "";

  for (int i=1; i<=skill_list.size(); i++)
  if (skill_list.get_by_importance(i)->learned == 1)

  {
    count++;
    x = skill_list.get_by_importance(i)->name.length();
    if (((count % 2) == 1) && (x > lsize)) lsize = x;
    if (((count % 2) == 0) && (x > rsize)) rsize = x;
  }

  if (lsize == 0) {
    tempstr += "  #n+-------------------+\r\n";
    tempstr += "  | #NNo Skills Learned #n|\r\n";
    tempstr += "  +-------------------+#N"; }

  else

  {
    count = 0;
    string border = "  #n+";
    for (int i=1; i<=lsize+12; i++) border += "-";
    border += "+#N";

    if (rsize > 0) {
    border += "#n";
    for (int i=1; i<=rsize+12; i++) border += "-";
    border += "+#N"; }

    tempstr += border + "\r\n";

    for (int i=1; i<=skill_list.size(); i++)
    if (skill_list.get_by_importance(i)->learned == 1)

    {
      count++;
      if ((count % 2) == 0) x = rsize+1;
      else x = lsize+1;
      if (count % 2) tempstr += "  #n| #Y";
      else tempstr += " #Y";
      tempstr += skill_list.get_by_importance(i)->name;
      for (int j=1; j<=x-(int)skill_list.get_by_importance(i)->name.length(); j++) tempstr += " ";
      tempstr += "#n[#N";
      if (skill_list.get_by_importance(i)->level < 100) tempstr += " ";
      if (skill_list.get_by_importance(i)->level < 10) tempstr += " ";
      tempstr += intconvert(skill_list.get_by_importance(i)->level);
      tempstr += "#n/#N100#n] #n|";
      if ((count % 2) == 0) tempstr += "\r\n";
    }

    if ((count % 2) == 1) {
      if (count > 1) for (int i=1; i<=12+rsize; i++) tempstr += " ";
      if (count > 1) tempstr += "#n|";
      tempstr += "\r\n"; }

    tempstr += border;
  }

  echo(tempstr);
}

void entity::display_spells()

{
  int x, lsize = 0, rsize = 0, count = 0;
  string tempstr = "";

  for (int i=1; i<=spell_list.size(); i++)
  if (spell_list.get_by_importance(i)->learned == 1)

  {
    count++;
    x = spell_list.get_by_importance(i)->name.length();
    if (((count % 2) == 1) && (x > lsize)) lsize = x;
    if (((count % 2) == 0) && (x > rsize)) rsize = x;
  }

  if (lsize == 0) {
    tempstr += "  #n+-------------------+\r\n";
    tempstr += "  | #NNo Spells Learned #n|\r\n";
    tempstr += "  +-------------------+#N"; }

  else

  {
    count = 0;
    string border = "  #n+";
    for (int i=1; i<=lsize+12; i++) border += "-";
    border += "+#N";

    if (rsize > 0) {
    border += "#n";
    for (int i=1; i<=rsize+12; i++) border += "-";
    border += "+#N"; }

    tempstr += border + "\r\n";

    for (int i=1; i<=spell_list.size(); i++)
    if (spell_list.get_by_importance(i)->learned == 1)

    {
      count++;
      if ((count % 2) == 0) x = rsize+1;
      else x = lsize+1;
      if (count % 2) tempstr += "  #n| #Y";
      else tempstr += " #Y";
      tempstr += spell_list.get_by_importance(i)->name;
      for (int j=1; j<=x-(int)spell_list.get_by_importance(i)->name.length(); j++) tempstr += " ";
      tempstr += "#n[#N";
      if (spell_list.get_by_importance(i)->level < 100) tempstr += " ";
      if (spell_list.get_by_importance(i)->level < 10) tempstr += " ";
      tempstr += intconvert(spell_list.get_by_importance(i)->level);
      tempstr += "#n/#N100#n] #n|";
      if ((count % 2) == 0) tempstr += "\r\n";
    }

    if ((count % 2) == 1) {
      if (count > 1) for (int i=1; i<=12+rsize; i++) tempstr += " ";
      if (count > 1) tempstr += "#n|";
      tempstr += "\r\n"; }

    tempstr += border;
  }

  echo(tempstr);
}

void entity::display_all_skills()

{
  int count = 0;
  string tempstr, border;

  border = "#n+";
  for (int i=1; i<=31; i++) border += "-";
  border += "+";
  for (int i=1; i<=31; i++) border += "-";
  border += "+#N";
  tempstr = "\r\n" + border + "\r\n";

  for (int i=1; i<=skill_list.size(); i++)

  {
    skill *the_skill = skill_list.get_by_importance(i);

    if (((the_skill->classes.find(get_ssclass()) != string::npos)
    || (the_skill->classes.find("All") != string::npos))
    && (the_skill->learned == 0))

    {
      count++;
      if (count % 2) tempstr += "#n| #Y";
      else tempstr += " #Y";
      tempstr += the_skill->name;
      for (int j=1; j<=30-(int)the_skill->name.length(); j++) tempstr += " ";
      tempstr += "#n|";
      if ((count % 2) == 0) tempstr += "\r\n";
    }
  }

  if ((count % 2) == 1) {
    for (int i=1; i<=31; i++) tempstr += " ";
    tempstr += "#n|";
    tempstr += "\r\n"; }

  tempstr += border;

  if (count > 0)
    echo(tempstr);

  else echo("\r\nThere are no more skills that you can learn.");
}

void entity::display_all_spells()

{
  int count = 0;
  string tempstr, border;

  border = "#n+";
  for (int i=1; i<=31; i++) border += "-";
  border += "+";
  for (int i=1; i<=31; i++) border += "-";
  border += "+#N";
  tempstr = "\r\n" + border + "\r\n";

  for (int i=1; i<=spell_list.size(); i++)

  {
    spell *the_spell = spell_list.get_by_importance(i);

    if (((the_spell->classes.find(get_ssclass()) != string::npos)
    || (the_spell->classes.find("All") != string::npos))
    && (the_spell->learned == 0))

    {
      count++;
      if (count % 2) tempstr += "#n| #Y";
      else tempstr += " #Y";
      tempstr += the_spell->name;
      for (int j=1; j<=30-(int)the_spell->name.length(); j++) tempstr += " ";
      tempstr += "#n|";
      if ((count % 2) == 0) tempstr += "\r\n";
    }
  }

  if ((count % 2) == 1) {
    for (int i=1; i<=31; i++) tempstr += " ";
    tempstr += "#n|";
    tempstr += "\r\n"; }

  tempstr += border;

  if (count > 0)
    echo(tempstr);

  else echo("\r\nThere are no more spells that you can learn.");
}