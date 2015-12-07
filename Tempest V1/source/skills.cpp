#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

int entity::damage_skill(int ID, int damage, entity *target, attackinfo AM, attackinfo DM)

{
  if (target == this) {
    echo("Attacking yourself is really not such a good idea.");
    return 0; }

  if (rm->get_LAWFULL() == 1) {
    echo("You may not attack others in this place.");
    return 0; }

  if ((rm->get_PKOK() == 0) && (target->PLAYER)) {
    echo("You may not attack " + target->get_him() + " here.");
    return 0; }

  if (my_group->in_group(target) == 1) {
    echo("You may not attack group members.");
    return 0; }

  if (target->get_client() != NULL)
  if ((target->get_client()->get_state() == CSTATE_LINKDEAD) || (target->get_client()->get_state() == CSTATE_WRITING)) {
    echo("You can't attack " + target->get_him() + " right now.");
    return 0; }

  if (damage >= 0)

  {
    AM.self_output += "\r\nYour " + lowercase(skill_list[ID].name);
    AM.self_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
    AM.targ_output += "\r\n" + possessive(name) + " " + lowercase(skill_list[ID].name);
    AM.targ_output += " #rhits you#N for#r " + intconvert(damage) + " #Ndamage.";
    AM.grup_output += "\r\n" + possessive(name) + " " + lowercase(skill_list[ID].name);
    AM.grup_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
    AM.obsv_output += "\r\n" + possessive(name) + " " + lowercase(skill_list[ID].name);
    AM.obsv_output += " hits " + target->get_name() + ".";

    DM.self_output += "\r\nYour " + lowercase(skill_list[ID].name);
    DM.self_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
    DM.targ_output += "\r\n" + possessive(name) + " " + lowercase(skill_list[ID].name);
    DM.targ_output += " #rhits you#N for#r " + intconvert(damage) + " #Ndamage.";
    DM.grup_output += "\r\n" + possessive(name) + " " + lowercase(skill_list[ID].name);
    DM.grup_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
    DM.obsv_output += "\r\n" + possessive(name) + " " + lowercase(skill_list[ID].name);
    DM.obsv_output += " hits " + target->get_name() + ".";

    DM.self_output += "\r\n" + target->get_name() + " is dead!";
    DM.targ_output += "\r\nYou are dead!";
    DM.grup_output += "\r\n" + target->get_name() + " is dead!";
    DM.obsv_output += "\r\n" + target->get_name() + " is dead!";
  }

  engage(target, 0);

  if (damage > 0)
    target->set_current_hp(target->get_current_hp()-damage);

  attackinfo *SM = &AM;

  if ((target->get_current_hp() <= 0) && (damage > 0)) {
    receive_experience(target);
    SM = &DM; }

  if (target->get_current_hp() > 0)
    SM->self_output += "\r\n" + hitpoint_meter();

  if (damage > 0)
    if (skill_list.train(ID))
      SM->self_output += "\r\n" + skill_list[ID].name + " has improved!";

  rm->drop_spell_strings(this, target, *SM);
  rm->drop_exp_strings(this);
  rm->echo_strings();

  if (target->get_current_hp() <= 0)
    target->die(1);

  return 1;
}

void entity::death_blossom()

{
  int ID = skill_list.get("Death Blossom")->id;
  int room_size = rm->get_size();
  int blossom_size = 0;

  if (skill_list[ID].learned == 0) {
    echo("Invalid Command.");
    return; }

  if (my_client == NULL) return;

  if (!Calendar->check_event(SCHEDULE_DEATH_BLOSSOM)) {
    echo("You need to rest before attempting that again.");
    return; }

  if (rm->get_LAWFULL() == 1) {
    echo("You may not attack others in this place.");
    return; }

  for (int i=0; i<room_size; i++)
    if (can_attack(rm->get_ent(i)))
      blossom_size++;

  if (blossom_size == 0) {
    echo("It would be a waste to use it now.  There's nobody here it can hit.");
    return; }

  Calendar->schedule_event(SCHEDULE_DEATH_BLOSSOM, MINUTES_PER_PERIOD/6);

  string temp  = "********************************\r\n";
         temp += "*** #NDEATH BLOSSOM ATTACK !!! ***\r\n";
         temp += "********************************#N";
  string border  = "***";  for (int i=0; i<=41+visible_size(name); i++) border += "*";  border += "#N";
  string temp2 = border + "\r\n*** #N" + name + " Unleashes a Death Blossom Attack !!! ***\r\n" + border;

  echo(rainbow_string(temp, "*"));
  rm->xecho(this, rainbow_string(temp2, "*"));

  set_intermittent_skill(blossom_size);

  for (int i=0; i<blossom_size; i++)
    my_client->put_command("death blossom single");
}

void entity::death_blossom_single()

{
  attackinfo AM;
  int ID = skill_list.get("Death Blossom")->id;
  int room_size = rm->get_size();
  entity* *blossom_list = new entity* [room_size];
  int blossom_size = 0;

  for (int i=0; i<room_size; i++)
  if (can_attack(rm->get_ent(i))) {
    blossom_list[blossom_size] = rm->get_ent(i);
    blossom_size++; }

  set_intermittent_skill(get_intermittent_skill()-1);

  if (blossom_size == 0) return;

  int rn = random_int(0, blossom_size-1);
  int damage = test_damage(blossom_list[rn]);
  float multiplier = ((((float)skill_list.get("Death Blossom")->level) / (float)10) + 1);
  float total_damage = (multiplier * (float)damage);

  entity *target = blossom_list[rn];
  string tname = target->get_name();

  AM.self_output  = "#rYou slice the shit out of " + tname + "!#N";
  AM.targ_output  = "#r" + name + " slices the shit out of you!#N";
  AM.grup_output  = "#r" + name + " slices the shit out of " + tname + "!#N";
  AM.obsv_output  = "#N" + name + " slices the shit out of " + tname + "!#N";

  damage_skill(ID, total_damage, target, AM, AM);
  lock_commands(350);
}

void entity::stab_and_twist(string str)

{
  attackinfo AM, DM;
  int ID  = skill_list.get("Stab and Twist")->id;
  int ID2 = skill_list.get("Heart Attack")->id;

  if (skill_list[ID].learned == 0) {
    echo("Invalid Command.");
    return; }

  if ((str.find("and twist ") == 0) && (str.size() >= 10)) str.erase(0, 10);
  else if ((str.find("and twist") == 0) && (str.size() >= 9)) str.erase(0, 9);

  if ((str == "") && (playerstate != PSTATE_FIGHTING)) {
    echo("Stab who?");
    return; }

  entity *target = rm->find_entity(this, first(str));

  if ((playerstate == PSTATE_FIGHTING) && (first(str) == ""))
    target = combat_target;

  if (target == NULL) {
    echo("Nobody around by that name.");
    return; }

  string tname = target->get_name();
  float percent = (0.5) + (0.005 * skill_list[ID].level);
  int total_damage = percent * (((float)test_damage(target) * 0.4) + ((float)get_DEX() * 0.54));
  int r = random_int(0, (2000 - (skill_list[ID2].level * 15)));

  if (test_hit(target) != 1)

  {
    total_damage = -1;

    AM.self_output  = "#NYou completely miss " + tname + " with your wild stab.#N";
    AM.targ_output  = "#N" + name + " stabs at you, but misses completely.#N";
    AM.grup_output  = "#N" + name + " stabs at " + tname + ", but misses completely.#N";
    AM.obsv_output  = "#N" + name + " stabs at " + tname + ", but misses completely.#N";
  }

  else if ((r < 100) && (skill_list[ID2].learned == 1))

  {
    ID = ID2;

    for (int i=1; i<=6; i++)
      total_damage += percent * (((float)test_damage(target) * 0.4) + ((float)get_DEX() * 0.54));

    AM.self_output  = "#rYou stab " + tname + " directly in the heart!#N";
    AM.targ_output  = "#r" + name + " stabs you directly in the heart!#N";
    AM.grup_output  = "#r" + name + " stabs " + tname + " directly in the heart!#N";
    AM.obsv_output  = "#N" + name + " stabs " + tname + " directly in the heart!#N";

    DM.self_output  = "#rBLOOD sprays everywhere as you stab " + tname + " deep in the heart!#N";
    DM.targ_output  = "#rBLOOD sprays everywhere as " + name + " stabs you deep in the heart!#N";
    DM.grup_output  = "#rBLOOD sprays everywhere as " + name + " stabs " + tname + " deep in the heart!#N";
    DM.obsv_output  = "#NBLOOD sprays everywhere as " + name + " stabs " + tname + " deep in the heart!#N";
  }

  else

  {
    AM.self_output  = "#rYou stab " + tname + "!#N";
    AM.targ_output  = "#r" + name + " stabs you!#N";
    AM.grup_output  = "#r" + name + " stabs " + tname + "!#N";
    AM.obsv_output  = "#N" + name + " stabs " + tname + "!#N";

    DM.self_output  = "#rYou stab " + tname + ", spilling " + target->get_his() + " blood!#N";
    DM.targ_output  = "#r" + name + " stabs you, spilling your blood!#N";
    DM.grup_output  = "#r" + name + " stabs " + tname + ", spilling " + target->get_his() + " blood!#N";
    DM.obsv_output  = "#N" + name + " stabs " + tname + ", spilling " + target->get_his() + " blood!#N";
  }

  if (damage_skill(ID, total_damage, target, AM, DM)) lock_commands(6500);
}

void entity::assassin_strike(string str)

{
  attackinfo AM, DM;
  int ID  = skill_list.get("Assassin Strike")->id;

  if (skill_list[ID].learned == 0) {
    echo("Invalid Command.");
    return; }

  if ((str.find("strike ") == 0) && (str.size() >= 7)) str.erase(0, 7);
  else if ((str.find("strike") == 0) && (str.size() >= 6)) str.erase(0, 6);

  if ((str == "") && (playerstate != PSTATE_FIGHTING)) {
    echo("Assassinate who?");
    return; }

  entity *target = rm->find_entity(this, first(str));

  if ((playerstate == PSTATE_FIGHTING) && (first(str) == ""))
    target = combat_target;

  if (target == NULL) {
    echo("Nobody around by that name.");
    return; }

  string tname = target->get_name();

  int damage = 0;

  if (test_hit(target) != 1)

  {
    damage = -1;

    AM.self_output  = "#NYou completely miss " + tname + " with your strike.#N";
    AM.targ_output  = "#N" + name + " strikes at, but misses completely.#N";
    AM.grup_output  = "#N" + name + " strikes at " + tname + ", but misses completely.#N";
    AM.obsv_output  = "#N" + name + " strikes at " + tname + ", but misses completely.#N";
  }

  else

  {
    float percent = (0.5) + (0.005 * skill_list[ID].level);

    for (int i=1; i<=6; i++)
      damage += (int)((float)test_damage(target) * 0.55 * percent);

    AM.self_output  = "#rYou strike " + tname + "!#N";
    AM.targ_output  = "#r" + name + " strikes you!#N";
    AM.grup_output  = "#r" + name + " strikes " + tname + "!#N";
    AM.obsv_output  = "#N" + name + " strikes " + tname + "!#N";

    DM.self_output  = "#rYou strike " + tname + ", spraying BLOOD everywhere!#N";
    DM.targ_output  = "#r" + name + " strikes you, spilling your BLOOD!#N";
    DM.grup_output  = "#r" + name + " strikes " + tname + ", spraying BLOOD everywhere!#N";
    DM.obsv_output  = "#N" + name + " strikes " + tname + ", spraying BLOOD everywhere!#N";
  }

  if (damage_skill(ID, damage, target, AM, DM)) lock_commands(6500);
}