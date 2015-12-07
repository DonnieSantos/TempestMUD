#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

group::group(entity *self)

{
  size = 1;
  member = new entity* [1];
  member[0] = self;
}

group::~group()

{
  delete [] member;
}

entity* group::get_member(int n)  { return member[n]; }
int group::get_size()             { return size;      }

int group::get_csize()

{
  int count = 0;

  for (int i=0; i<size; i++)
    if (member[i]->PLAYER)
      count++;

  return count;
}

void group::add_member(entity *SRC, entity *new_member)

{
  entity *leader = get_leader();

  if (SRC != leader) {
    SRC->echo("Only the group leader can do that.");
    return; }

  else if (new_member == NULL) {
    leader->echo("Nobody around by that name.");
    return; }

  else if (new_member == leader) {
    leader->echo("You are already in your own group.");
    return; }

  else if (new_member->get_following() != leader) {
    leader->echo(new_member->get_name() + " is not following you.");
    return; }

  else if (new_member->get_room() != leader->get_room()) {
    leader->echo(new_member->get_name() + " is not here right now.");
    return; }

  else {
    leader->echo(new_member->get_name() + " is joining this group.");
    group_message(leader, new_member->get_name() + " is joining this group.");
    new_member->echo("You join " + possessive(leader->get_name()) + " group."); }

  for (int i=0; i<size; i++)
    if (new_member != member[i]) {
      new_member->get_group()->add_member_silent(member[i]);
      member[i]->get_group()->add_member_silent(new_member); }
}

void group::add_member_silent(entity *new_member)

{
  size++;
  entity* *temp = new entity* [size];

  for (int i=0; i<size-1; i++)
    temp[i] = member[i];

  temp[size-1] = new_member;

  delete [] member;
  member = temp;
}

void group::remove_member(entity *SRC, entity *old_member)

{
  entity *leader = get_leader();

  if (SRC != leader) {
    SRC->echo("Only the group leader can do that.");
    return; }

  else if (old_member == NULL) {
    leader->echo("Nobody around by that name.");
    return; }

  else if (old_member == leader) {
    leader->echo("You can't remove yourself from your own group.");
    return; }

  else if (!in_group(old_member)) {
    leader->echo(old_member->get_name() + " is not in your group.");
    return; }

  else {
    group_message(leader, old_member, old_member->get_name() + " is leaving this group.");
    old_member->echo(leader->get_name() + " has removed you from the group.");
    leader->echo(old_member->get_name() + " is leaving this group."); }

  for (int i=0; i<size; i++)
    if (old_member != member[i])
      member[i]->get_group()->remove_member_silent(old_member);

  old_member->get_group()->destroy_group();
}

void group::remove_member_silent(entity *old_member)

{
  int found = 0;

  for (int i=0; i<size; i++)
    if (member[i] == old_member)
      found = 1;

  if (!found) return;

  size--;
  entity* *temp = new entity* [size];

  temp[0] = member[0];

  found = 0;

  for (int i=1; i<=size; i++)

  {
    if (member[i] != old_member)
      temp[i-found] = member[i];

    else found = 1;
  }

  delete [] member;
  member = temp;
}

entity* entity::group_all(entity *new_entity, cmdqueue *names_added)

{
  for (int i=0; i<num_followers; i++)

  {
    if ((followers[i]->get_room() == rm)
    && (my_group->in_group(followers[i]) == 0))

    {
      for (int j=0; j<my_group->get_size(); j++)
      if (followers[i] != my_group->get_member(j))

      {
        if (names_added->search(followers[i]->get_name()) == 0)
          names_added->put(followers[i]->get_name());

        new_entity = followers[i];
        if (new_entity != new_entity) { cout << "The Universe has ended."; }

        followers[i]->get_group()->add_member_silent(my_group->get_member(j));
        my_group->get_member(j)->get_group()->add_member_silent(followers[i]);
      }
    }
  }

  return new_entity;
}

void group::add_all(entity *SRC)

{
  entity *leader = get_leader();
  entity *new_entity;
  cmdqueue names_added;
  string tempstr = "";

  if (get_leader() != SRC) {
    SRC->echo("Only the group leader can do that.");
    return; }

  if (SRC->get_num_followers() == 0) {
    SRC->echo("Nobody is following you.");
    return; }

  new_entity = leader->group_all(new_entity, &names_added);

  int n = names_added.get_size();

  if (n == 1)

  {
    string name_added = names_added.get();
    leader->echo(name_added + " is joining this group.");
    new_entity->echo("You join " + possessive(leader->get_name()) + " group.");
    group_message(leader, new_entity, new_entity->get_name() + " is joining this group.");
  }

  else

  {
    for (int i=1; i<=n; i++)
      tempstr += names_added.get() + " is joining this group.\r\n";

    if (n > 0) tempstr.erase(tempstr.size()-2, 2);

    if (tempstr == "")
      leader->echo("Nobody has been added to the group.");

    else {
      group_message(leader, tempstr);
      leader->echo(tempstr); }
  }
}

void group::remove_all(entity *SRC)

{
  if (size <= 1) {
    get_leader()->echo("The group is empty.");
    return; }

  if (get_leader() != SRC) {
    SRC->echo("Only the group leader can do that.");
    return; }

  group_message(get_leader(), "Group disbanded.");
  get_leader()->echo("Group disbanded.");

  while (size > 1)
    member[1]->stop_following();

  destroy_group();
}

void group::destroy_group()

{
  for (int i=1; i<size; i++)
    member[i]->get_group()->remove_member_silent(member[0]);

  size = 1;
  entity* *temp = new entity* [1];

  temp[0] = member[0];
  member = temp;
}

entity* group::get_random_target(entity* ENT, entity* TANK)

{
  entity* *list = new entity* [size];
  int *percentages = new int [size];
  int count = 0, last = 0, randint, average;

  for (int i=0; i<size; i++)
  if ((member[i]->targeting_group(ENT->get_group()))
  && (member[i]->get_room() == ENT->get_room())) {
    list[count] = member[i];
    count++; }

  average = (70 / count);

  for (int i=0; i<count; i++)
    percentages[i] = (int)average;

  for (int i=0; i<count; i++)

  {
    if (list[i] == get_leader())
      percentages[i] += 10;
    if (list[i] == TANK)
      percentages[i] += 20;
  }

  randint = random_int(1, 100);

  for (int i=0; i<count; i++)

  {
    percentages[i] += last;

    if ((randint > last) && (randint <= percentages[i]))
      return list[i];

    else last = percentages[i];
  }

  return TANK;
}

entity* group::get_leader()

{
  if (size > 1)
    return member[0]->get_following();

  return member[0];
}

int group::in_group(entity *ENT)

{
  for (int i=0; i<size; i++)
    if (member[i] == ENT)
      return 1;

  return 0;
}

void group::group_message(string str)

{
  for (int i=0; i<size; i++)
    if (member[i]->get_client() != NULL)
      member[i]->echo(str);
}

void group::group_message(entity *silent, string str)

{
  for (int i=0; i<size; i++)
    if ((member[i]->get_client() != NULL) && (member[i] != silent))
      member[i]->echo(str);
}

void group::group_message(entity *s1, entity *s2, string str)

{
  for (int i=0; i<size; i++)
    if ((member[i]->get_client() != NULL) && (member[i] != s1) && (member[i] != s2))
      member[i]->echo(str);
}

void group::group_message(entity *s1, entity *s2, entity *s3, string str)

{
  for (int i=0; i<size; i++)
    if ((member[i]->get_client() != NULL) && (member[i] != s1) && (member[i] != s2) && (member[i] != s3))
      member[i]->echo(str);
}

void group::group_local_message(room *loc, string str)

{
  for (int i=0; i<size; i++)
    if (member[i]->get_client() != NULL)
    if (member[i]->get_room() == loc)
      member[i]->echo(str);
}

void group::group_local_message(room *loc, string s1, string s2)

{
  for (int i=0; i<size; i++)
    if (member[i]->get_client() != NULL)
    if (member[i]->get_room() == loc)
      member[i]->echo(s1);
    else
      member[i]->echo(s2);
}

void group::group_local_message(room *loc, entity *silent, string str)

{
  for (int i=0; i<size; i++)
    if (member[i]->get_client() != NULL)
    if ((member[i]->get_room() == loc) && (member[i] != silent))
      member[i]->echo(str);
}

void group::group_local_message(room *loc, entity *silent, string s1, string s2)

{
  for (int i=0; i<size; i++)
  if ((member[i]->get_client() != NULL) && (member[i] != silent))

  {
    if (member[i]->get_room() == loc)
      member[i]->echo(s1);
    else
      member[i]->echo(s2);
  }
}

string group::get_display_info(entity *member)

{
  string tempstr;

  tempstr = "#N[ ";
  if (member->get_level() < 100) tempstr += " ";
  if (member->get_level() < 10) tempstr += " ";
  tempstr += "#Y" + intconvert(member->get_level()) + " ";
  tempstr += member->get_sclass() + " #N]  #c< ";
  if (member->get_current_hp() < 1000) tempstr += " ";
  if (member->get_current_hp() < 100) tempstr += " ";
  if (member->get_current_hp() < 10) tempstr += " ";
  tempstr += "#g" + intconvert(member->get_current_hp()) + "#Ghp ";
  if (member->get_current_mana() < 1000) tempstr += " ";
  if (member->get_current_mana() < 100) tempstr += " ";
  if (member->get_current_mana() < 10) tempstr += " ";
  tempstr += "#m" + intconvert(member->get_current_mana()) + "#Mmn ";
  if (member->get_current_move() < 1000) tempstr += " ";
  if (member->get_current_move() < 100) tempstr += " ";
  if (member->get_current_move() < 10) tempstr += " ";
  tempstr += "#b" + intconvert(member->get_current_move()) + "#Bmv";
  tempstr += " #c>#N  " + member->get_name();
  if (member->get_group()->get_leader() == member) tempstr += " the leader.";
  else tempstr += ".";

  return tempstr;
}

void group::display_group(entity *ENT)

{
  entity *leader = get_leader();
  string tempstr = "";
  int maxsize = 0;

  for (int i=0; i<size; i++)
    if (visible_size(get_display_info(member[i])) > maxsize)
      maxsize = visible_size(get_display_info(member[i]));

  tempstr += "#n+--";
  for (int i=1; i<=maxsize; i++) tempstr += "-";
  tempstr += "--+#N\r\n#n|  #gGroup Information#n:";
  for (int i=1; i<=maxsize-16; i++) tempstr += " ";
  tempstr += "#n|#N\r\n#n+--";
  for (int i=1; i<=maxsize; i++) tempstr += "-";

  int lsize = maxsize - visible_size(get_display_info(leader));
  tempstr += "--+#N\r\n#n|  " + get_display_info(leader);
  for (int i=1; i<=lsize; i++) tempstr += " ";
  tempstr += "  #n|#N\r\n";

  if (size > 1)
  for (int i=0; i<size; i++)
  if (member[i] != leader)

  {
    int tsize = maxsize - visible_size(get_display_info(member[i]));
    tempstr += "#n|  " + get_display_info(member[i]);
    for (int j=1; j<=tsize; j++) tempstr += " ";
    tempstr += "  #n|#N\r\n";
  }

  tempstr += "#n+--";
  for (int i=1; i<=maxsize; i++) tempstr += "-";
  tempstr += "--+#N";

  ENT->echo(tempstr);
}


void group::group_assist(entity *ENT)

{
  if (ENT == NULL) return;
  if (ENT->get_playerstate() != PSTATE_FIGHTING) return;

  for (int i=0; i<size; i++)

  {
    if (member[i] != ENT)
    if (member[i]->get_playerstate() != PSTATE_FIGHTING)
    if (member[i]->get_autoassist() == 1)
    if (member[i]->get_room() == ENT->get_room()) {
      member[i]->set_target(ENT->get_target());
      member[i]->set_playerstate(PSTATE_FIGHTING); }
  }
}

void group::divide_experience(room *loc, int exp)

{
  int max_level = 0;

  for (int i=0; i<size; i++)
    if ((member[i]->PLAYER) && (member[i]->get_level() > max_level) && (member[i]->get_room() == loc))
      max_level = member[i]->get_level();

  if (max_level == 0) return;

  for (int i=0; i<size; i++)
  if ((member[i]->PLAYER) && (member[i]->get_room() == loc))

  {
    int LD = max_level - member[i]->get_level();
    int percentage = get_exp_deficit(LD);
    int total = ((exp * percentage) / 100);
    member[i]->gain_experience(total);
  }
}

void group::no_experience()

{
  for (int i=0; i<size; i++)
    member[i]->no_experience();
}