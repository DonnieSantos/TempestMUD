#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

void entity::Quit()

{
  string s1 = "has left the game.";
  string s2 = name + " has left the game.";
  string s3 = name + " stops following you.";
  string s4 = "You stop following " + name + ".";
  string s5 = name + " is leaving this group.";
  int group_size = my_group->get_size();

  clist->syslog_gecho(get_name() + " has quit the game." , 100, my_client->get_ip());

  stop_fighting();
  stop_tracking();

  if (following != this)

  {
    if (group_size == 1)

    {
      string leader_here = s2 + "\r\n" + s3;
      string leader_away = s3;
      string obsv_here = s1 + "\r\n" + name + " stops following " + following->get_name() + ".";

      vdist_echo(following, leader_here, leader_away);
      xblind_emote(following, obsv_here);
      stop_following();
    }

    else

    {
      string leader_here = s2 + "\r\n" + s3 + "\r\n" + s5;
      string leader_away = s3 + "\r\n" + s5;
      string group_here = s2 + "\r\n" + name + " stops following " + following->get_name() + ".\r\n" + s5;
      string group_away = s5;
      string obsv_here = s2 + "\r\n" + name + " stops following " + following->get_name() + ".";

      entity *leader = following;
      stop_following();

      vdist_echo(leader, leader_here, leader_away);
      leader->get_group()->group_local_message(rm, leader, group_here, group_away);
      rm->gblind_echo(leader, leader, obsv_here);
    }
  }

  else if (num_followers > 0)

  {
    for (int i=0; i<num_followers; i++)
      if (my_group->in_group(followers[i]) == 0)
        vdist_echo(followers[i], s2 + "\r\n" + s4, s4);
      else
        vdist_echo(followers[i], s2 + "\r\nGroup disbanded.", "Group disbanded.");

    rm->pblind_echo(this, s2);

    while (num_followers > 0)
      followers[0]->stop_following();

    stop_following();
  }

  else blind_emote("has quit the game.");

  World->get_log()->add_log(NULL, my_client->get_ip() + " " + get_name() + " quit the game.");
  rm->loss(this);
  File->save_profile(my_client);
  my_client->disconnect(0);
}

void entity::Drop()

{
  string s1 = "has dropped from the game.";
  string s2 = name + " has dropped from the game.";
  string s3 = name + " stops following you.";
  string s4 = "You stop following " + name + ".";
  string s5 = name + " is leaving this group.";
  int group_size = my_group->get_size();

  if (my_client != NULL)
    my_client->flush_commands();

  clist->syslog_gecho(get_name() + " has dropped from the game.", 100, my_client->get_ip());

  stop_fighting();
  stop_tracking();

  if (following != this)

  {
    if (group_size == 1)

    {
      string leader_here = s2 + "\r\n" + s3;
      string leader_away = s3;
      string obsv_here = s1 + "\r\n" + name + " stops following " + following->get_name() + ".";

      vdist_echo(following, leader_here, leader_away);
      xblind_emote(following, obsv_here);
      stop_following();
    }

    else

    {
      string leader_here = s2 + "\r\n" + s3 + "\r\n" + s5;
      string leader_away = s3 + "\r\n" + s5;
      string group_here = s2 + "\r\n" + name + " stops following " + following->get_name() + ".\r\n" + s5;
      string group_away = s5;
      string obsv_here = s2 + "\r\n" + name + " stops following " + following->get_name() + ".";

      entity *leader = following;
      stop_following();

      vdist_echo(leader, leader_here, leader_away);
      leader->get_group()->group_local_message(rm, leader, group_here, group_away);
      rm->gblind_echo(leader, leader, obsv_here);
    }
  }

  else if (num_followers > 0)

  {
    for (int i=0; i<num_followers; i++)
      if (my_group->in_group(followers[i]) == 0)
        vdist_echo(followers[i], s2 + "\r\n" + s4, s4);
      else
        vdist_echo(followers[i], s2 + "\r\nGroup disbanded.", "Group disbanded.");

    rm->pblind_echo(this, s2);

    while (num_followers > 0)
      followers[0]->stop_following();

    stop_following();
  }

  else blind_emote("has dropped from the game.");

  World->get_log()->add_log(NULL, my_client->get_ip() + " " + get_name() + " dropped from the game.");
  rm->loss(this);
  if (PLAYER) File->save_profile(my_client);
}

void entity::Purge(entity *SRC)

{
  string s1 = "has been purged by the Gods.";
  string s2 = name + " has been purged by the Gods.";
  string s3 = name + " stops following you.";
  string s4 = "You stop following " + name + ".";
  string s5 = name + " is leaving this group.";
  int group_size = my_group->get_size();

  int vis = SRC->get_level();
  string sname = SRC->get_name();

  clist->syslog_gecho(sname + " has purged " + get_name() + ".", vis, my_client->get_ip());

  stop_fighting();
  stop_tracking();

  if (following != this)

  {
    if (group_size == 1)

    {
      string leader_here = s2 + "\r\n" + s3;
      string leader_away = s3;
      string obsv_here = s1 + "\r\n" + name + " stops following " + following->get_name() + ".";

      vdist_echo(following, leader_here, leader_away);
      xblind_emote(following, obsv_here);
      stop_following();
    }

    else

    {
      string leader_here = s2 + "\r\n" + s3 + "\r\n" + s5;
      string leader_away = s3 + "\r\n" + s5;
      string group_here = s2 + "\r\n" + name + " stops following " + following->get_name() + ".\r\n" + s5;
      string group_away = s5;
      string obsv_here = s2 + "\r\n" + name + " stops following " + following->get_name() + ".";

      entity *leader = following;
      stop_following();

      vdist_echo(leader, leader_here, leader_away);
      leader->get_group()->group_local_message(rm, leader, group_here, group_away);
      rm->gblind_echo(leader, leader, obsv_here);
    }
  }

  else if (num_followers > 0)

  {
    for (int i=0; i<num_followers; i++)
      if (my_group->in_group(followers[i]) == 0)
        vdist_echo(followers[i], s2 + "\r\n" + s4, s4);
      else
        vdist_echo(followers[i], s2 + "\r\nGroup disbanded.", "Group disbanded.");

    rm->pblind_echo(this, s2);

    while (num_followers > 0)
      followers[0]->stop_following();

    stop_following();
  }

  else blind_emote("has been purged by the Gods.");

  World->get_log()->add_log(NULL, my_client->get_ip() + " " + get_name() + " purged by " + sname + ".");
  rm->loss(this);
  File->save_profile(my_client);
  my_client->disconnect(0);
}

void entity::Death(int make_corpse)

{
  if (my_client != NULL)
    my_client->flush_commands();

  stop_fighting();
  stop_tracking();

  if (make_corpse)
    create_corpse();

  rm->loss(this);
}