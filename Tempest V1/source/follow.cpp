#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

void entity::follow(string target, int from_ditch)

{
  int was_grouped = -1;
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this)

  {
    if (following == this) echo("You are already following yourself.");

    else

    {
      entity *last = following;
      string for_self;
      string for_lead;
      string for_obsv = name + " stops following " + following->get_name() + ".";
      string for_grup = name + " stops following " + following->get_name() + ".";
      string for_dist = name + " is leaving this group.";

      if (from_ditch) for_self = "You stop following " + following->get_name() + ".";
      else for_self = "You stop following " + following->get_name() + ".";

      if (from_ditch) for_lead = name + " stops following you.";
      else for_lead = name + " stops following you.";

      int grouped = following->get_group()->in_group(this);

      stop_following();

      if (grouped)

      {
        for_self += "\r\nYou leave " + possessive(last->get_name()) + " group.";
        for_lead += "\r\n" + name + " is leaving this group.";
        for_grup += "\r\n" + name + " is leaving this group.";
      }

      echo(for_self);
      last->echo(for_lead);
      last->get_group()->group_local_message(rm, last, for_grup, for_dist);
      rm->gblind_echo(last, this, last, for_obsv);
    }
  }

  else if (ENT != NULL)

  {
    string wname = ENT->get_name();
    string fname = ENT->get_following()->get_name();
    string fname_him = ENT->get_following()->get_him();

    if (following == ENT)
      echo("You are already following " + wname + ".");

    else if (ENT->get_following() == this)
      echo("You cannot follow " + wname + ".  " + ENT->get_He() + " is already following you.");

    else if (num_followers > 0)
      echo("You are already being followed.");

    else if (ENT->get_following() != ENT)
      echo(wname + " is following " + fname + ".  Try following " + fname_him + " instead.");

    else if (ENT->get_followable() == 0)
      echo(wname + " does not want to be followed right now.");

    else

    {
      entity *stop = following;
      string forme = "You start following " + wname + ".";
      string forfol = name + " starts following you.";
      string forstop = name + " stops following you.";
      string forothers = name + " starts following " + wname + ".";
      string forgroup = name + " starts following " + wname + ".";
      string distant_group = name + " is leaving this group.";

      if (following != this)

      {
        int grouped = following->get_group()->in_group(this);
        was_grouped = grouped;

        if (grouped) forstop += "\r\n" + name + " is leaving this group.";
        if (following->get_room() == rm) forstop += "\r\n" + name + " starts following " + wname + ".";

        if (grouped) forme.insert(0, "You leave " + possessive(following->get_name()) + " group.\r\n");
        forme.insert(0, "You stop following " + following->get_name() + ".\r\n");

        forfol.erase(0,2);
        forfol.insert(0, "\r\n" + name + " stops following " + following->get_name() + ".\r\n");

        forothers.insert(0, name + " stops following " + following->get_name() + ".\r\n");

        if (grouped) forgroup.insert(0, name + " is leaving this group.\r\n");
        forgroup.insert(0, name + " stops following " + following->get_name() + ".\r\n");

        stop_following();
      }

      ENT->add_follower(this);
      following = ENT;

      echo(forme);
      following->echo(forfol);
      if (stop != this) stop->echo(forstop);

      if (was_grouped != 1)
        rm->xecho(this, stop, following, forothers);
      else if (was_grouped == 1) {
        rm->gblind_echo(stop, this, stop, following, forothers);
        stop->get_group()->group_local_message(rm, stop, forgroup, distant_group); }
    }
  }

  else echo("There's nobody here by that name.");
}

void entity::ditch(string target)

{
  string *trg = new string();
  *trg = target;
  int target_num = clip_number(trg);

  if (num_followers == 0) {
    echo("There is nobody following you.");
    return; }

  for (int i=0; i<num_followers; i++)

  {
    if (followers[i]->check_targets(*trg))
      target_num--;

    if (!target_num)

    {
      string fname = lowercase(followers[i]->get_name());
      followers[i]->follow(fname, 1);
      return;
    }
  }

  echo("Nobody by that name is following you.");
}

void entity::add_follower(entity* FLWR)

{
  num_followers++;

  entity* *temp = new entity* [num_followers];

  for (int i=0; i<num_followers-1; i++)
    temp[i] = followers[i];

  temp[num_followers-1] = FLWR;

  followers = temp;
}

void entity::remove_follower(entity* FLWR)

{
  for (int i=0; i<num_followers; i++)
    if (followers[i] == FLWR) {
      remove_follower(i);
      return; }
}

void entity::remove_follower(int fnum)

{
  if (num_followers > 0)

  {
    num_followers--;

    entity* *temp = new entity* [num_followers];

    for (int i=0; i<=num_followers; i++)

    {
      if (i < fnum)
        temp[i] = followers[i];
      else if (i > fnum)
        temp[i-1] = followers[i];
    }

    followers = temp;
  }
}

void entity::stop_following()

{
  if (following != this) {
    following->remove_follower(this);
    following = this; }

  my_group->destroy_group();
}