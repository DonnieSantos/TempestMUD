#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

void entity::scout()

{
  string tempstr = "You scout the surrounding area:\r\n";
  int north = 0, south = 0, east = 0, west = 0;
  int nsize = 0, ssize = 0, esize = 0, wsize = 0;
  room *location;

  int ID = skill_list.get("Scout")->id;

  if (skill_list[ID].learned == 0) {
    echo("You do not have the ability to scout.");
    return; }

  int depth = ((skill_list[ID].level / 16) + 1);

  location = rm;
  for (int i=1; i<=depth; i++)
  if (location->get_N()) {
    north++;
    location = World->get_room(location->get_N());
    nsize += location->get_size(); }

  location = rm;
  for (int i=1; i<=depth; i++)
  if (location->get_S()) {
    south++;
    location = World->get_room(location->get_S());
    ssize += location->get_size(); }

  location = rm;
  for (int i=1; i<=depth; i++)
  if (location->get_E()) {
    east++;
    location = World->get_room(location->get_E());
    esize += location->get_size(); }

  location = rm;
  for (int i=1; i<=depth; i++)
  if (location->get_W()) {
    west++;
    location = World->get_room(location->get_W());
    wsize += location->get_size(); }

  if (nsize)
  for (int i=1; i<=north; i++)

  {
    location = rm;

    for (int j=1; j<=i; j++)
      location = World->get_room(location->get_N());

    tempstr += location->get_scout_names(i, "N");
  }

  if (ssize)
  for (int i=1; i<=south; i++)

  {
    location = rm;

    for (int j=1; j<=i; j++)
      location = World->get_room(location->get_S());

    tempstr += location->get_scout_names(i, "S");
  }

  if (esize)
  for (int i=1; i<=east; i++)

  {
    location = rm;

    for (int j=1; j<=i; j++)
      location = World->get_room(location->get_E());

    tempstr += location->get_scout_names(i, "E");
  }

  if (wsize)
  for (int i=1; i<=west; i++)

  {
    location = rm;

    for (int j=1; j<=i; j++)
      location = World->get_room(location->get_W());

    tempstr += location->get_scout_names(i, "W");
  }

  if ((!nsize) && (!ssize) && (!esize) && (!wsize))
    tempstr += "There are no signs of life nearby.";
  else if (skill_list.train(ID))
    tempstr += "\r\n\nYour ability to scout has improved!";

  echo(tempstr, name + " scouts the surrounding area.");
}

string entity::track(string target)

{
  int target_num = clip_number(&target);
  entity *ENT = rm->find_entity(this, target);
  int index, size, new_rooms[10000];
  intarray *IRA = new intarray();
  int found = 1;

  int ID = skill_list.get("Track")->id;

  if (skill_list[ID].learned == 0) return "You do not have the ability to track.";
  if (target == "") return "What do you want to track?";

  int range = (skill_list[ID].level*2) + 50;

  if (ENT != NULL)

  {
    int num_ents = rm->find_num_entities(target);

    if ((target_num - num_ents) <= 0) {
      stop_tracking();
      return ENT->get_name() + " is right here!"; }
    else
      target_num = target_num - num_ents;
  }

  IRA->put(rm->get_id());

  for (int i=1; i<=range; i++)

  {
    if (!found) {
      stop_tracking();
      return "You can't seem to pick up the trail."; }

    found = 0;

    if (i < 3) index = i - 1;
    else index += new_rooms[i-2];

    size = IRA->size();
    new_rooms[i] = 0;

    for (int j=index; j<size; j++)

    {
      room *current_room = World->get_room(IRA->get(j));

      if (current_room->get_N())
      if (!IRA->search(current_room->get_N()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_N());

        entity *ent = World->get_room(current_room->get_N())->find_entity(target);

        if (ent != NULL) {
          int num_ents = World->get_room(current_room->get_N())->find_num_entities(target);
          if ((target_num - num_ents) <= 0) return track(ent);
          else target_num = target_num - num_ents; }
      }

      if (current_room->get_S())
      if (!IRA->search(current_room->get_S()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_S());

        entity *ent = World->get_room(current_room->get_S())->find_entity(target);

        if (ent != NULL) {
          int num_ents = World->get_room(current_room->get_S())->find_num_entities(target);
          if ((target_num - num_ents) <= 0) return track(ent);
          else target_num = target_num - num_ents; }
      }

      if (current_room->get_E())
      if (!IRA->search(current_room->get_E()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_E());

        entity *ent = World->get_room(current_room->get_E())->find_entity(target);

        if (ent != NULL) {
          int num_ents = World->get_room(current_room->get_E())->find_num_entities(target);
          if ((target_num - num_ents) <= 0) return track(ent);
          else target_num = target_num - num_ents; }
      }

      if (current_room->get_W())
      if (!IRA->search(current_room->get_W()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_W());

        entity *ent = World->get_room(current_room->get_W())->find_entity(target);

        if (ent != NULL) {
          int num_ents = World->get_room(current_room->get_W())->find_num_entities(target);
          if ((target_num - num_ents) <= 0) return track(ent);
          else target_num = target_num - num_ents; }
      }

      if (current_room->get_U())
      if (!IRA->search(current_room->get_U()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_U());

        entity *ent = World->get_room(current_room->get_U())->find_entity(target);

        if (ent != NULL) {
          int num_ents = World->get_room(current_room->get_U())->find_num_entities(target);
          if ((target_num - num_ents) <= 0) return track(ent);
          else target_num = target_num - num_ents; }
      }

      if (current_room->get_D())
      if (!IRA->search(current_room->get_D()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_D());

        entity *ent = World->get_room(current_room->get_D())->find_entity(target);

        if (ent != NULL) {
          int num_ents = World->get_room(current_room->get_D())->find_num_entities(target);
          if ((target_num - num_ents) <= 0) return track(ent);
          else target_num = target_num - num_ents; }
      }
    }
  }

  stop_tracking();
  return "You can't seem to pick up the trail.";
}

string entity::track(entity *target)

{
  int range = 1000000;
  int ID = skill_list.get("Track")->id;

  if (rm == target->get_room())

  {
    string trained = "";
    if (tracking != NULL)
    if (skill_list.train(ID)) trained += "\r\nYour tracking skill has improved!";

    stop_tracking();
    return target->get_name() + " is right here!" + trained;
  }

  int *dist_and_dir = rm->distance(target->get_room());

  if (dist_and_dir == NULL) {
    stop_tracking();
    return "You can't seem to pick up the trail."; }

  if (dist_and_dir[0] > range) {
    stop_tracking();
    return "You can't seem to pick up the trail."; }

  if (dist_and_dir[1] == 1) {
    tracking = target;  tracking_dir = "N";
    return "You sense a trail north of here!"; }

  if (dist_and_dir[1] == 2) {
    tracking = target;  tracking_dir = "S";
    return "You sense a trail south of here!"; }

  if (dist_and_dir[1] == 3) {
    tracking = target;  tracking_dir = "E";
    return "You sense a trail east of here!"; }

  if (dist_and_dir[1] == 4) {
    tracking = target;  tracking_dir = "W";
    return "You sense a trail west of here!"; }

  if (dist_and_dir[1] == 5) {
    tracking = target;  tracking_dir = "U";
    return "You sense a trail up from here!"; }

  if (dist_and_dir[1] == 6) {
    tracking = target;  tracking_dir = "D";
    return "You sense a trail down from here!"; }

  stop_tracking();
  return "CONTINUAL TRACK ERROR";
}