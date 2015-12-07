#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

string intconvert(int);

void room::set_timer(double i) { timer = i; }

void world::add_room_fightlist(room* rm)

{
  room* *temp = new room* [fightsize+1];

  for (int i=0; i<fightsize; i++)
    temp[i] = fightlist[i];

  temp[fightsize] = rm;
  fightsize++;
  fightlist = temp;
  rm->set_timer(0);
}

void world::remove_room_fightlist(room* rm)

{
  int found = 0;

  fightsize--;
  room* *temp = new room* [fightsize];

  for (int i=0; i<=fightsize; i++)
    if (rm != fightlist[i])
      temp[i-found] = fightlist[i];
    else found = 1;

  fightlist = temp;
}

int world::find_room_fightlist(room* rm)

{
  for (int i=0; i<fightsize; i++)
    if (fightlist[i] == rm)
      return 1;

  return 0;
}

void world::update_fights(double elapsed)

{
  room* *temp = new room* [fightsize];
  int tempint = fightsize;

  for (int i=0; i<fightsize; i++)
    temp[i] = fightlist[i];

  for (int i=0; i<tempint; i++)
    temp[i]->update_combat(elapsed);
}

void room::update_combat(double elapsed)

{
  cslock Lock(&CriticalSection);

  timer += elapsed;

  if (timer >= 3)

  {
    all_assist();
    update_targets();
    fight();
    destroy_dead();
    update_targets();

    timer = 0;
  }
}

void room::destroy_dead()

{
  cslock Lock(&CriticalSection);

  int diesize = size;
  entity* *dielist = new entity* [diesize];

  for (int i=0; i<diesize; i++)
    dielist[i] = ENT_LIST[i];

  for (int i=0; i<diesize; i++)
  if (dielist[i]->get_current_hp() <= 0)

  {
    for (int j=0; j<size; j++)
      dielist[j]->remove_target(dielist[i]);

    dielist[i]->die(1);
  }
}

void room::all_assist()

{
  cslock Lock(&CriticalSection);

  for (int i=0; i<size; i++)
    if (ENT_LIST[i]->get_playerstate() == PSTATE_FIGHTING)
      ENT_LIST[i]->get_group()->group_assist(ENT_LIST[i]);
}

void room::update_targets()

{
  cslock Lock(&CriticalSection);

  for (int i=0; i<size; i++)
  for (int j=0; j<size; j++)
  if ((ENT_LIST[i]->targeting_group(ENT_LIST[j]->get_group()) == 1)
  && (ENT_LIST[j]->get_playerstate() == PSTATE_FIGHTING))
    ENT_LIST[j]->set_target(ENT_LIST[i]);

  for (int i=0; i<size; i++)
  for (int j=0; j<size; j++)
    if (ENT_LIST[i]->get_playerstate() != PSTATE_FIGHTING)
      ENT_LIST[j]->remove_target(ENT_LIST[i]);

  for (int i=0; i<size; i++)
    if (ENT_LIST[i]->get_target() == NULL)
      ENT_LIST[i]->set_playerstate(PSTATE_STANDING);

  check_fight();
}

void room::fight()

{
  cslock Lock(&CriticalSection);

  for (int i=0; i<size; i++)

  {
    attackinfo attackdata;
    entity* target;

    if (ENT_LIST[i]->get_target() != NULL)

    {
      target = ENT_LIST[i]->get_target();

      if (ENT_LIST[i]->get_client() == NULL)
        target = target->get_group()->get_random_target(ENT_LIST[i], target);

      attackdata = ENT_LIST[i]->attack(target);

      if (attackdata.succeeded) {
        distribute_foutput(attackdata, ENT_LIST[i], target);
        distribute_exp_output(ENT_LIST[i]); }
    }
  }

  add_meters();
}

void room::distribute_foutput(attackinfo attackdata, entity* ENT, entity* TARGET)

{
  cslock Lock(&CriticalSection);

  group *group1 = ENT->get_group();
  group *group2 = TARGET->get_group();


  for (int i=0; i<size; i++)

  {
    if (ENT_LIST[i]->get_client() != NULL)

    {
      int alive = 0, fighting = 0;

      if (ENT_LIST[i]->get_playerstate() == PSTATE_FIGHTING)
        fighting = 1;

      if (ENT_LIST[i]->get_current_hp() > 0)
        alive = 1;

      if ((ENT_LIST[i] == ENT) && ((alive) || (attackdata.self_output.find("You are dead!") != string::npos)))
        ENT_LIST[i]->get_client()->put_foutput(attackdata.self_output);

      else if ((ENT_LIST[i] == TARGET) && ((alive) || (attackdata.targ_output.find("You are dead!") != string::npos)))
        ENT_LIST[i]->get_client()->put_foutput(attackdata.targ_output);

      else if (((fighting) || (group1->in_group(ENT_LIST[i]) == 1) || (group2->in_group(ENT_LIST[i]) == 1))
        && ((alive) || (attackdata.grup_output.find("You are dead!") != string::npos)))
        ENT_LIST[i]->get_client()->put_foutput(attackdata.grup_output);

      else if ((alive) || (attackdata.obsv_output.find("You are dead!") != string::npos))
        ENT_LIST[i]->get_client()->put_foutput(attackdata.obsv_output);
    }
  }
}

void room::distribute_exp_output(entity *ENT)

{
  cslock Lock(&CriticalSection);

  group *the_group = ENT->get_group();

  for (int i=0; i<size; i++)
  if (the_group->in_group(ENT_LIST[i]) == 1)
  if (ENT_LIST[i]->get_last_experience() >= 0)

  {
    string bonus;

    if ((ENT_LIST[i]->get_last_experience() > 0) && (ENT_LIST[i]->get_experience() == 0))
      bonus = "#gYour level has increased!#N";
    else
      bonus = "#yYou receive " + intconvert(ENT_LIST[i]->get_last_experience()) + " experience!#N";

    ENT_LIST[i]->get_client()->put_foutput(bonus);
    ENT_LIST[i]->no_experience();
  }
}

void room::add_meters()

{
  cslock Lock(&CriticalSection);

  for (int i=0; i<size; i++)
    if (ENT_LIST[i]->get_playerstate() == PSTATE_FIGHTING)
      if ((ENT_LIST[i]->get_target()->get_current_hp() > 0) && (ENT_LIST[i]->get_current_hp() > 0))
        if (ENT_LIST[i]->get_client() != NULL)
          ENT_LIST[i]->get_client()->put_foutput(ENT_LIST[i]->hitpoint_meter());
}

void room::check_fight()

{
  cslock Lock(&CriticalSection);

  int count = 0;

  for (int i=0; i<size; i++)
    if (ENT_LIST[i]->get_playerstate() == PSTATE_FIGHTING)
      count++;

  if (count < 2)
  if (World->find_room_fightlist(this)) {
    World->remove_room_fightlist(this);
    timer = 0; }
}

void entity::engage(entity *target, int i)

{
  if (target == NULL) {
    echo("There's nobody here by that name.");
    return; }

  if (target == this) {
    echo("Attacking yourself is really not such a good idea.");
    return; }

  if (rm->get_LAWFULL() == 1) {
    echo("You cannot attack others in this place.");
    return; }

  if ((rm->get_PKOK() == 0) && (target->PLAYER)) {
    echo("You may not attack " + target->get_him() + " here.");
    return; }

  if (my_group->in_group(target) == 1) {
    echo("You may not attack group members.");
    return; }

  if (target->get_client() != NULL)
  if ((target->get_client()->get_state() == CSTATE_LINKDEAD) || (target->get_client()->get_state() == CSTATE_WRITING)) {
    echo("You can't attack " + target->get_him() + " right now.");
    return; }

  if (!World->find_room_fightlist(rm))
    World->add_room_fightlist(rm);

  set_target(target);
  set_playerstate(PSTATE_FIGHTING);

  if (target->get_playerstate() != PSTATE_FIGHTING) {
    target->set_target(this);
    target->set_playerstate(PSTATE_FIGHTING); }
  else target->set_target(this);

  if (i) quickattack(target);
}

void entity::quickattack(entity* TARGET)

{
  attackinfo attackdata = attack(TARGET);

  if (attackdata.succeeded)

  {
    attackdata.targ_output = attackdata.targ_output;
    attackdata.grup_output = attackdata.grup_output;
    attackdata.obsv_output = attackdata.obsv_output;

    rm->distribute_foutput(attackdata, this, TARGET);
    rm->distribute_exp_output(this);
    rm->add_meters();
    rm->destroy_dead();
    rm->update_targets();
  }
}

void entity::manual_assist(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if ((playerstate == PSTATE_FIGHTING) || (combat_target != NULL)) {
    echo("You are already fighting!");
    return; }

  if (ENT == NULL) {
    echo("There is nobody around by that name.");
    return; }

  if ((ENT->get_playerstate() != PSTATE_FIGHTING) || (ENT->get_target() == NULL)) {
    echo(ENT->get_name() + " isn't even fighting anybody!");
    return; }

  playerstate = PSTATE_FIGHTING;
  combat_target = ENT->get_target();
  quickattack(combat_target);
}

int entity::can_attack(entity *target)

{
  if ((rm == target->get_room()) && (rm->get_LAWFULL() == 0))
  if ((!target->PLAYER) || (rm->get_PKOK() == 1))
  if (my_group->in_group(target) == 0)
  if (target->get_current_hp() > 0)

  {
    if (target->get_client() == NULL) return 1;
    if (target->get_client()->get_state() == CSTATE_NORMAL) return 1;
    if (target->get_client()->get_state() == CSTATE_POSSESSION) return 1;
  }

  return 0;
}

void entity::stop_fighting()

{
  playerstate = PSTATE_STANDING;
  clear_targets();
  rm->update_targets();
}

void entity::flee()

{
  int exits[6] = {0, 0, 0, 0, 0, 0};
  int found = 0, num_exits = 0;
  int index;

  if (playerstate != PSTATE_FIGHTING) {
    echo("You're not even in a fight!");
    return; }

  if (rm->get_N()) { exits[0] = 1;  num_exits++; }
  if (rm->get_S()) { exits[1] = 1;  num_exits++; }
  if (rm->get_W()) { exits[2] = 1;  num_exits++; }
  if (rm->get_E()) { exits[3] = 1;  num_exits++; }
  if (rm->get_U()) { exits[4] = 1;  num_exits++; }
  if (rm->get_D()) { exits[5] = 1;  num_exits++; }

  if (num_exits == 0) {
    echo("There's nowhere to run to!");
    return; }

  while (!found)

  {
    index = rand() % 5;
    if (exits[index] != 0)
      found = 1;
  }

  string move_attempt, move_dir;

  switch (index)

  {
    case 0: move_attempt = try_move("N");  move_dir = "north";  break;
    case 1: move_attempt = try_move("S");  move_dir = "south";  break;
    case 2: move_attempt = try_move("W");  move_dir = "west";   break;
    case 3: move_attempt = try_move("E");  move_dir = "east";   break;
    case 4: move_attempt = try_move("U");  move_dir = "up";     break;
    case 5: move_attempt = try_move("D");  move_dir = "down";   break;
  }

  if ((move_attempt == "OK") || (move_attempt == "You're in the middle of a fight!"))

  {
    rm->search_looks("X");
    stop_fighting();

    echo("You flee the battle!", name + " flees from the battle!");

    switch (index)

    {
      case 0: move("N"); break;
      case 1: move("S"); break;
      case 2: move("W"); break;
      case 3: move("E"); break;
      case 4: move("U"); break;
      case 5: move("D"); break;
    }
  }

  else echo("You try to flee " + move_dir + ", but you can't manage to break through!");
}