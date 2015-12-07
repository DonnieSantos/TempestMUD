#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

attackinfo entity::attack(entity *target)

{
  float damage;
  attackinfo atk;

  atk.succeeded = 0;
  atk.self_output = "Failed.";
  atk.targ_output = "Failed.";
  atk.obsv_output = "Failed.";

  if ((get_current_hp() <= 0) || (target->get_current_hp() <= 0))
    return atk;

  atk.succeeded = 1;

  int connected = test_hit(target);

  if (connected == 1) damage = test_damage(target);
  else damage = connected;

  string tname = target->get_name();
  string type = "stab";
  int critical = 0;

  if ((skill_list.get("Critical Hit")->learned) && (damage >= 0))

  {
    if (random_int(0, 10000) < ((get_DEX() * 2) + 250))

    {
      critical = 1;
      int lev = skill_list.get("Critical Hit")->level;
      float percentage = ((50 + (lev*0.5)) / 100);
      int nstats = (2*get_STR()) + (0.5*get_DEX());
      int rstats = random_int(0.9*nstats, 1.1*nstats);
      damage = (2*damage) + (percentage * rstats);
    }
  }

  if (damage > 0)
    target->set_current_hp(target->get_current_hp()-damage);

  if (damage == -1) {
    atk.self_output = "#NYou barely miss " + tname + " with your errant attack.";
    atk.targ_output = "#N" + name + " barely misses you with " + get_his() + " errant attack.";
    atk.grup_output = "#N" + name + " barely misses " + tname + " with " + get_his() + " errant attack.";
    atk.obsv_output = "#N" + name + " barely misses " + tname + " with " + get_his() + " errant attack."; }

  else if (damage == -2) {
    atk.self_output = "#NYou miss " + tname + " with your poorly-aimed attack.";
    atk.targ_output = "#N" + name + " misses you with " + get_his() + " poorly-aimed attack.";
    atk.grup_output = "#N" + name + " misses " + tname + " with " + get_his() + " poorly-aimed attack.";
    atk.obsv_output = "#N" + name + " misses " + tname + " with " + get_his() + " poorly-aimed attack."; }

  else if (damage == -3) {
    atk.self_output = "#NYou completely miss " + tname + " with your inept attack.";
    atk.targ_output = "#N" + name + " completely misses you with " + get_his() + " inept attack.";
    atk.grup_output = "#N" + name + " completely misses " + tname + " with " + get_his() + " inept attack.";
    atk.obsv_output = "#N" + name + " completely misses " + tname + " with " + get_his() + " inept attack."; }

  else if (critical)

  {
    string crit = "critically";
    if (target->get_current_hp() < 1) crit = "CRITICALLY";

    atk.self_output  = "#R" + tname + " screams out in pain as you " + crit + " hit " + target->get_him() + "!#N\r\n";
    atk.self_output += "#NYour critical hit hits " + tname + " for #R" + intconvert(damage) + " #Ndamage.";
    atk.targ_output  = "#rYou scream out in pain as " + name + " " + crit + " hits you!#N\r\n";
    atk.targ_output += "#N" + possessive(name) + " critical hit hits you for #r" + intconvert(damage) + " #Ndamage.";
    atk.grup_output  = "#R" + tname + " screams out in pain as " + name + " " + crit + " hits " + target->get_him() + ".\r\n";
    atk.grup_output += "#N" + possessive(name) + " critical hit hits " + tname + " for #R" + intconvert(damage) + " #Ndamage.";
    atk.obsv_output  = "#N" + tname + " screams out in pain as " + name + " " + crit + " hits " + target->get_him() + ".\r\n";
    atk.obsv_output += "#N" + possessive(name) + " critical hit hits " + tname + " for #N" + intconvert(damage) + " #Ndamage.";

    if (skill_list.train(skill_list.get("Critical Hit")->id))
      atk.self_output += "\r\nCritial Hit has improved!";
  }

  else

  {
    string dam_adj = adjective(damage, target->get_max_hp());

    atk.self_output  = "#NYour " + type + " #R" + dam_adj;
    atk.self_output += " #N" + tname + " #Nfor #R" + intconvert(damage) + " #Ndamage.";
    atk.targ_output  = possessive(name) + " " + type + " #r" + dam_adj;
    atk.targ_output += " #Nyou for #r" + intconvert(damage) + " #Ndamage.";
    atk.grup_output  = possessive(name) + " " + type + " #R" + dam_adj;
    atk.grup_output += " #N" + tname + " #Nfor #R" + intconvert(damage) + " #Ndamage.";
    atk.obsv_output  = possessive(name) + " " + type + " #N" + dam_adj;
    atk.obsv_output += " #N" + tname + " #Nfor #N" + intconvert(damage) + " #Ndamage.";
  }

  if ((target->get_current_hp() < 1) && (damage >= 0))

  {
    atk.self_output += "\r\n#N" + tname + " is dead!";
    atk.targ_output += "\r\n#NYou are dead!";
    atk.grup_output += "\r\n#N" + tname + " is dead!";
    atk.obsv_output += "\r\n#N" + tname + " is dead!";

    receive_experience(target);
  }

  return atk;
}

int entity::test_damage(entity *target)

{
  int STANDARD_AC = ((get_level() * 10) + 10);
  int STANDARD_DEX = (get_level() * 1.5);
  int STANDARD_ABSORB = (int)((float)((STANDARD_AC + (STANDARD_DEX)) * .3));

  float DAMAGE = (int)((float)(((19*get_DR()) + (2.375*get_STR())) * .312)) + STANDARD_ABSORB;
  float ABSORB = (int)((float)((target->get_AC() + (target->get_DEX())) * .3));
  float damage = DAMAGE - ABSORB;

  if (damage < (DAMAGE * .05)) damage = (DAMAGE * .05);

  int random_percent = random_int(80, 120);
  float percentage = ((float)random_percent / 100);
  damage = (int)((float)damage * percentage);

  return damage;
}

int entity::test_hit(entity *target)

{
  int STANDARD_HIT = ((int)((float)get_level() / 5) + 1);
  int STANDARD_DEX = ((int)((float)get_level() * 1.5) + 2);

  int HIT = (int)(((float)get_HR() / (float)STANDARD_HIT) * 100);
  int AIM = (int)(((((float)get_DEX() / (float)STANDARD_DEX) * 100) - 100) / 2);
  int DODGE = (int)(((((float)target->get_DEX() / (float)STANDARD_DEX) * 100) - 100) / 2);

  int hit_rate = HIT + AIM - DODGE;
  if (hit_rate < 5) hit_rate = 5;
  if (hit_rate > 95) hit_rate = 95;

  int dodge_rate = 100 - hit_rate;
  int n = random_int(0,100000);
  int x = dodge_rate * 1000;

  if (x < n) return 1;
  if ((dodge_rate >= 0) && (dodge_rate <= 10)) return -1;
  if ((dodge_rate >= 11) && (dodge_rate <= 25)) return -2;

  return -3;
}

string adjective(int damage, int maxhp)

{
  string str;
  float percent;

  percent = (((float)damage / (float)maxhp) * (float)100);

  if (percent < 1)                             str = "barely scratches";
  else if ((percent >= 1) && (percent < 2))    str = "scratches";
  else if ((percent >= 2) && (percent < 3))    str = "grazes";
  else if ((percent >= 3) && (percent < 4))    str = "injures";
  else if ((percent >= 4) && (percent < 5))    str = "severely injures";
  else if ((percent >= 5) && (percent < 6))    str = "wounds";
  else if ((percent >= 6) && (percent < 7))    str = "deeply wounds";
  else if ((percent >= 7) && (percent < 8))    str = "strikes";
  else if ((percent >= 8) && (percent < 10))   str = "viciously strikes";
  else if ((percent >= 10) && (percent < 12))  str = "disfigures";
  else if ((percent >= 12) && (percent < 14))  str = "mercilessly disfigures";
  else if ((percent >= 14) && (percent < 17))  str = "brutalizes";
  else if ((percent >= 17) && (percent < 20))  str = "savagely brutalizes";
  else if ((percent >= 20) && (percent < 23))  str = "ruthlessly maims";
  else if ((percent >= 23) && (percent < 26))  str = "SLAUGHTERS";
  else if ((percent >= 26) && (percent < 29))  str = "+++ OBLITERATES +++";
  else if ((percent >= 29) && (percent < 33))  str = "*** MASSACRES ***";
  else                                         str = ">>> ANNIHILATES <<";

  return str;
}