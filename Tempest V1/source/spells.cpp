#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

void entity::cast(string str)

{
  int ID = 0, found = 0;

  for (int i=1; i<=spell_list.size(); i++)

  {
    string spellname = lowercase(spell_list[i].name);

    if (str.find(spellname) != string::npos)
    if (str.find(spellname) == 0)

    {
      str.erase(0, spellname.size());
      if (str.size() > 0) str.erase(0, 1);
      str = key(str);
      ID = spell_list[i].id;
      found = 1;
      break;
    }
  }

  if (!found) {
    echo("That spell does not exist.");
    return; }

  if (spell_list[ID].learned == 0) {
    echo("You do not know that spell.");
    return; }

  if ((combat_target == NULL) && (str == "") && (spell_list[ID].targeted == 1)) {
    echo("Who do you want to cast it on?");
    return; }

  if ((combat_target == NULL) && (str == "") && (spell_list[ID].targeted == 2)) {
    echo("Which item do you want to cast it on?");
    return; }

  if ((get_current_mana() < spell_list[ID].manacost) && (PLAYER)) {
    echo("You don't have enough mana to cast " + spell_list[ID].name + ".");
    return; }

  if ((combat_target != NULL) && (str == "") && (spell_list[ID].targeted == 1))
    str = combat_target->get_lname();

  entity *target = NULL;
  item *itarget = NULL;

  if (spell_list[ID].targeted == 1) {
    target = rm->find_entity(this, str);
    if (target == NULL) {
      echo("There is nobody here by that name.");
      return; }
  }

  else if (spell_list[ID].targeted == 2) {
    itarget = rm->find_item_room(str);
    if (itarget == NULL) itarget = find_item_inventory(str);
    if (itarget == NULL)  {
      echo("There is no item by that name here.");
      return; }
  }

  if (ID == spell_list.get("Gem Missile")->id)          gem_missile(target);
  else if (ID == spell_list.get("Cure Light")->id)      cure_light(target);
  else if (ID == spell_list.get("Flash")->id)           flash();
  else if (ID == spell_list.get("Sanctuary")->id)       sanctuary(target);
  else if (ID == spell_list.get("Shield")->id)          shield(target);
  else if (ID == spell_list.get("Identify")->id)        identify(itarget);
  else if (ID == spell_list.get("Lightning Bolt")->id)  lightning_bolt(target);
  else if (ID == spell_list.get("Fireball")->id)        fireball(target);
  else if (ID == spell_list.get("Burst")->id)           burst();

  rm->echo_strings();
}

void entity::item_cast(string spell_name, string target_name)

{
  target_name = lowercase(target_name);

  if (spell_list.get(spell_name) == NULL) {
    add_string("That spell does not exist.");
    return; }

  int ID = spell_list.get(spell_name)->id;

  if ((combat_target == NULL) && (target_name == "") && (spell_list[ID].targeted == 1)) {
    add_string("Who do you want to cast it on?");
    return; }

  if ((combat_target == NULL) && (target_name == "") && (spell_list[ID].targeted == 2)) {
    add_string("Which item do you want to cast it on?");
    return; }

  if ((combat_target != NULL) && (target_name == "") && (spell_list[ID].targeted == 1))
    target_name = combat_target->get_lname();

  entity* target = NULL;
  item* itarget  = NULL;

  if (spell_list[ID].targeted == 1) {
    target = rm->find_entity(this, target_name);
    if (target == NULL) {
      add_string("There is nobody here by that name.");
      return; }
  }

  else if (spell_list[ID].targeted == 2) {
    itarget = rm->find_item_room(target_name);
    if (itarget == NULL) itarget = find_item_inventory(target_name);
    if (itarget == NULL)  {
      add_string("There is no item by that name here.");
      return; }
  }

  if (ID == spell_list.get("Gem Missile")->id)          gem_missile(target);
  else if (ID == spell_list.get("Cure Light")->id)      cure_light(target);
  else if (ID == spell_list.get("Flash")->id)           flash();
  else if (ID == spell_list.get("Sanctuary")->id)       sanctuary(target);
  else if (ID == spell_list.get("Shield")->id)          shield(target);
  else if (ID == spell_list.get("Identify")->id)        identify(itarget);
  else if (ID == spell_list.get("Lightning Bolt")->id)  lightning_bolt(target);
  else if (ID == spell_list.get("Fireball")->id)        fireball(target);
  else if (ID == spell_list.get("Burst")->id)           burst();
}

void entity::damage_cast(int ID, int damage, entity *target, attackinfo AM, attackinfo DM)

{
  if (target == this) {
    echo("You cannot cast that on yourself.");
    return; }

  if (rm->get_LAWFULL() == 1) {
    echo("You may not attack others in this place.");
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

  int mana = spell_list[ID].manacost;
  if (Right_Hand != NULL) Right_Hand->cast(spell_list[ID].name, &mana, &damage);

  AM.self_output += "\r\nYour " + lowercase(spell_list[ID].name);
  AM.self_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
  AM.targ_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  AM.targ_output += " #rhits you#N for#r " + intconvert(damage) + " #Ndamage.";
  AM.grup_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  AM.grup_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
  AM.obsv_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  AM.obsv_output += " hits " + target->get_name() + ".";

  DM.self_output += "\r\nYour " + lowercase(spell_list[ID].name);
  DM.self_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
  DM.targ_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  DM.targ_output += " #rhits you#N for#r " + intconvert(damage) + " #Ndamage.";
  DM.grup_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  DM.grup_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
  DM.obsv_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  DM.obsv_output += " hits " + target->get_name() + ".";

  DM.self_output += "\r\n" + target->get_name() + " is dead!";
  DM.targ_output += "\r\nYou are dead!";
  DM.grup_output += "\r\n" + target->get_name() + " is dead!";
  DM.obsv_output += "\r\n" + target->get_name() + " is dead!";

  engage(target, 0);

  target->set_current_hp(target->get_current_hp()-damage);

  attackinfo *SM = &AM;

  if (target->get_current_hp() <= 0) {
    receive_experience(target);
    SM = &DM; }

  if (spell_list.train(ID))
    SM->self_output += "\r\n" + spell_list[ID].name + " has improved!";

  rm->drop_spell_strings(this, target, *SM);
  rm->drop_exp_strings(this);

  if (PLAYER)
    set_current_mana(get_current_mana() - mana);

  if (target->get_current_hp() <= 0)
    target->die(1);
}

void entity::support_cast(int ID, int amount, entity *target, attackinfo AM)

{
  int mana = spell_list[ID].manacost;
  if (Right_Hand != NULL) Right_Hand->cast(spell_list[ID].name, &mana, &amount);

  target->set_current_hp(target->get_current_hp()+amount);

  if ((amount > 0) && (target == this))

  {
    if (target->get_current_hp() != target->get_max_hp()) {
      AM.self_output += "#N\r\nYou heal some of your wounds.#N";
      AM.targ_output += "#N\r\nYou heal some of your wounds.#N";
      AM.grup_output += "#N\r\n" + get_name() + " has healed some of " + get_his() + " wounds.#N";
      AM.obsv_output += "#N\r\n" + get_name() + " has healed some of " + get_his() + " wounds.#N"; }

    else {
      AM.self_output += "#N\r\nYou have completely healed yourself.#N";
      AM.targ_output += "#N\r\nYou have completely healed yourself.#N";
      AM.grup_output += "#N\r\n" + get_name() + " has completely healed " + get_himself() + ".#N";
      AM.obsv_output += "#N\r\n" + get_name() + " has completely healed " + get_himself() + ".#N"; }
  }

  else if ((amount > 0) && (target != this))

  {
    if (target->get_current_hp() != target->get_max_hp()) {
      AM.self_output += "#N\r\nYou heal some of " + TARGETS + " wounds.#N";
      AM.targ_output += "#N\r\n" + get_name() + " has healed some of your wounds.#N";
      AM.grup_output += "#N\r\n" + get_name() + " has healed some of " + TARGETS + " wounds.#N";
      AM.obsv_output += "#N\r\n" + get_name() + " has healed some of " + TARGETS + " wounds.#N"; }

    else {
      AM.self_output += "#N\r\nYou have completely healed " + target->get_name() + ".#N";
      AM.targ_output += "#N\r\nYou have been completely healed by " + get_name() + ".#N";
      AM.grup_output += "#N\r\n" + get_name() + " has completely healed " + target->get_name() + ".#N";
      AM.obsv_output += "#N\r\n" + get_name() + " has completely healed " + target->get_name() + ".#N"; }
  }

  if (spell_list.train(ID))
    AM.self_output += "\r\n" + spell_list[ID].name + " has improved!";

  if (PLAYER)
    set_current_mana(get_current_mana() - mana);

  rm->drop_spell_strings(this, target, AM);
}

void entity::area_damage_cast(int ID, int damage, string s1, string s2)

{
  if (rm->get_LAWFULL() == 1) {
    echo("You may not attack others in this place.");
    return; }

  attackinfo AM;
  int mana = spell_list[ID].manacost;

  if (Right_Hand != NULL) Right_Hand->cast(spell_list[ID].name, &mana, &damage);

  attackinfo IM;
  IM.self_output = s1;  IM.targ_output = s2;
  IM.grup_output = s2;  IM.obsv_output = s2;

  rm->drop_spell_strings(this, this, IM);

  int found = 0;
  int fake_mana;

  for (int i=0; i<rm->get_size(); i++)
  if (can_attack(rm->get_ent(i)))

  {
    found = 1;
    entity *target = rm->get_ent(i);

    engage(target, 0);

    if (ID == spell_list.get("Flash")->id) damage = (spell_list[ID].level / 2) + random_int(100, 150);
    if (ID == spell_list.get("Burst")->id) damage = (spell_list[ID].level / 2) + random_int(300, 500);

    if (Right_Hand != NULL) Right_Hand->cast(spell_list[ID].name, &fake_mana, &damage);
    target->set_current_hp(target->get_current_hp()-damage);

    AM.self_output  = "Your " + lowercase(spell_list[ID].name);
    AM.self_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
    AM.targ_output  = possessive(get_name()) + " " + lowercase(spell_list[ID].name);
    AM.targ_output += " #rhits you#N for#r " + intconvert(damage) + " #Ndamage.";
    AM.grup_output  = possessive(get_name()) + " " + lowercase(spell_list[ID].name);
    AM.grup_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
    AM.obsv_output  = possessive(get_name()) + " " + lowercase(spell_list[ID].name);
    AM.obsv_output += " hits " + target->get_name() + ".";

    if (target->get_current_hp() <= 0)

    {
      AM.self_output += "\r\n" + target->get_name() + " is dead!";
      AM.targ_output += "\r\nYou are dead!";
      AM.grup_output += "\r\n" + target->get_name() + " is dead!";
      AM.obsv_output += "\r\n" + target->get_name() + " is dead!";

      receive_experience(target);
    }

    rm->drop_spell_strings(this, target, AM);
    rm->drop_exp_strings(this);
  }

  if (found)
  if (spell_list.train(ID))
    add_string(spell_list[ID].name + " has improved!");

  int diesize = rm->get_size();
  entity* *dielist = new entity* [diesize];

  for (int i=0; i<diesize; i++)
    dielist[i] = rm->get_ent(i);

  if (PLAYER)
    set_current_mana(get_current_mana() - mana);

  for (int i=0; i<diesize; i++)
    if (dielist[i]->get_current_hp() <= 0)
      dielist[i]->die(1);
}

void room::drop_spell_strings(entity *ENT, entity *TARGET, attackinfo attackdata)

{
  cslock Lock(&CriticalSection);

  group *group1 = ENT->get_group();
  group *group2 = TARGET->get_group();

  for (int i=0; i<size; i++)

  {
    int alive = 0, fighting = 0;

    if (ENT_LIST[i]->get_playerstate() == PSTATE_FIGHTING)
      fighting = 1;

    if (ENT_LIST[i]->get_current_hp() > 0)
      alive = 1;

    if ((ENT_LIST[i] == ENT) && ((alive) || (attackdata.self_output.find("You are dead!") != string::npos)))
      ENT_LIST[i]->add_string(attackdata.self_output);

    else if ((ENT_LIST[i] == TARGET) && ((alive) || (attackdata.targ_output.find("You are dead!") != string::npos)))
      ENT_LIST[i]->add_string(attackdata.targ_output);

    else if (((fighting) || (group1->in_group(ENT_LIST[i]) == 1) || (group2->in_group(ENT_LIST[i]) == 1))
    && ((alive) || (attackdata.grup_output.find("You are dead!") != string::npos)))
      ENT_LIST[i]->add_string(attackdata.grup_output);

    else if ((alive) || (attackdata.obsv_output.find("You are dead!") != string::npos))
      ENT_LIST[i]->add_string(attackdata.obsv_output);
  }
}

void room::drop_exp_strings(entity *ENT)

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

    ENT_LIST[i]->add_string(bonus);
    ENT_LIST[i]->no_experience();
  }
}

void room::echo_strings()

{
  cslock Lock(&CriticalSection);

  for (int i=0; i<size; i++)
    ENT_LIST[i]->echo_strings();
}

void entity::gem_missile(entity *target)

{
  int ID = spell_list.get("Gem Missile")->id;
  int damage = (spell_list[ID].level / 5) + random_int(20, 40);

  attackinfo AM, DM;

  if (!silent_cast)

  {
    AM.self_output = "#gYou shoot a crystalline shard of energy at " + target->get_name() + "!#N";
    AM.targ_output = "#g" + get_name() + " shoots a crystalline shard of energy at you!#N";
    AM.grup_output = "#g" + get_name() + " shoots a crystalline shard of energy at " + target->get_name() + "!#N";
    AM.obsv_output = "#N" + get_name() + " shoots a crystalline shard of energy at " + target->get_name() + "!#N";
  }

  DM.self_output = "#gYour gem missile pierces " + TARGETS + " heart!#N";
  DM.targ_output = "#g" + MY + " gem missile pierces your heart!#N";
  DM.grup_output = "#g" + MY + " gem missile pierces " + TARGETS + " heart!#N";
  DM.obsv_output = "#N" + MY + " gem missile pierces " + TARGETS + " heart!#N";

  if (!silent_cast) damage_cast(ID, damage, target, AM, DM);
  else item_damage_cast(ID, damage, target, AM, DM);
}

void entity::lightning_bolt(entity *target)

{
  int ID = spell_list.get("Lightning Bolt")->id;
  int damage = (spell_list[ID].level / 5) + random_int(50, 200);

  attackinfo AM, DM;

  if (!silent_cast)

  {
    AM.self_output = "#gYou shoot a bolt of lightning at " + target->get_name() + "!#N";
    AM.targ_output = "#g" + get_name() + " shoots a bolt of lightning at you!#N";
    AM.grup_output = "#g" + get_name() + " shoots a bolt of lightning at " + target->get_name() + "!#N";
    AM.obsv_output = "#N" + get_name() + " shoots a bolt of lightning at " + target->get_name() + "!#N";
  }

  DM.self_output = "#gYour lightning bolt shatters " + target->get_name() + " into a thousand pieces!#N";
  DM.targ_output = "#g" + MY + " lightning bolt shatters your body into a thousand pieces!#N";
  DM.grup_output = "#g" + MY + " lightning bolt shatters " + target->get_name() + " into a thousand pieces!#N";
  DM.obsv_output = "#N" + MY + " lightning bolt shatters " + target->get_name() + " into a thousand pieces!#N";

  if (!silent_cast) damage_cast(ID, damage, target, AM, DM);
  else item_damage_cast(ID, damage, target, AM, DM);
}

void entity::fireball(entity *target)

{
  int ID = spell_list.get("Fireball")->id;
  int damage = (spell_list[ID].level / 5) + random_int(150, 350);

  attackinfo AM, DM;

  if (!silent_cast)

  {
    AM.self_output = "#rYou throw a fireball at " + target->get_name() + "!#N";
    AM.targ_output = "#r" + get_name() + " throws a fireball you!#N";
    AM.grup_output = "#r" + get_name() + " throws a fireball at " + target->get_name() + "!#N";
    AM.obsv_output = "#N" + get_name() + " throws a fireball at " + target->get_name() + "!#N";
  }

  DM.self_output = "#rYour fireball completely incinerates " + target->get_name() + "!#N";
  DM.targ_output = "#r" + MY + " fireball completely incinerates your body!#N";
  DM.grup_output = "#r" + MY + " fireball completely incinerates " + target->get_name() + "!#N";
  DM.obsv_output = "#N" + MY + " fireball completely incinerates " + target->get_name() + "!#N";

  if (!silent_cast) damage_cast(ID, damage, target, AM, DM);
  else item_damage_cast(ID, damage, target, AM, DM);
}

void entity::cure_light(entity *target)

{
  int ID = spell_list.get("Cure Light")->id;
  int amount = (spell_list[ID].level) + random_int(80, 120);

  attackinfo AM;

  string tname = target->get_name(), oname = target->get_name();
  if (target == this) { tname = "your wounds";  oname = get_himself(); }

  if (!silent_cast)

  {
    AM.self_output = "#bYou call upon the light of heaven to heal " + tname + ".#N";
    AM.targ_output = "#b" + get_name() + " calls upon the light of heaven to heal your wounds.#N";
    AM.grup_output = "#b" + get_name() + " calls upon the light of heaven to heal " + oname + ".#N";
    AM.obsv_output = "#b" + get_name() + " calls upon the light of heaven to heal " + oname + ".#N";
  }

  if (!silent_cast) support_cast(ID, amount, target, AM);
  else item_support_cast(ID, amount, target, AM);
}

void entity::flash()

{
  int ID = spell_list.get("Flash")->id;
  int damage = (spell_list[ID].level / 2) + random_int(100, 150);
  string s1 = "";
  string s2 = "";

  if (!silent_cast)

  {
    s1 = "#yYou snap your fingers and create a blinding flash of light!#N";
    s2 = "#y" + get_name() + " snaps " + get_his() + " fingers and creates a blinding flash of light!#N";
  }

  if (!silent_cast) area_damage_cast(ID, damage, s1, s2);
  else item_area_damage_cast(ID, damage);
}

void entity::burst()

{
  int ID = spell_list.get("Burst")->id;
  int damage = (spell_list[ID].level / 2) + random_int(300, 500);
  string s1 = "";
  string s2 = "";

  if (!silent_cast)

  {
    s1 = "#gYou wave your hand, and the very air begins to pop and explode!#N";
    s2 = "#g" + get_name() + " waves " + get_his() + " hand, causing the very air to pop and explode!#N";
  }

  if (!silent_cast) area_damage_cast(ID, damage, s1, s2);
  else item_area_damage_cast(ID, damage);
}

void entity::sanctuary(entity *target)

{
  int ID = spell_list.get("Sanctuary")->id;
  int amount = 0;

  attackinfo AM;

  string tname = target->get_name(), oname = target->get_name();
  if (target == this) { tname = "you";  oname = get_himself(); }

  if (!silent_cast)

  {
    AM.self_output = "#yYour prayers manifest into a shiny golden aura that surrounds " + tname + ".#N\r\n";
    AM.targ_output = "#y" + MY + " prayers manifest into a shiny golden aura that surrounds you.#N\r\n";
    AM.grup_output = "#y" + MY + " prayers manifest into a shiny golden aura that surrounds " + oname + ".#N\r\n";
    AM.obsv_output = "#y" + MY + " prayers manifest into a shiny golden aura that surrounds " + oname + ".#N\r\n";
  }

  if (target == this) AM.self_output += "#NYou are protected by Sanctuary.#N";
  else AM.self_output += "#N" + target->get_name() + " is protected by Sanctuary.#N";
  AM.targ_output += "#NYou are protected by Sanctuary.#N";
  AM.grup_output += "#N" + target->get_name() + " is protected by Sanctuary.#N";
  AM.obsv_output += "#N" + target->get_name() + " is protected by Sanctuary.#N";

  target->get_effects()->renew("Sanctuary");

  if (!silent_cast) support_cast(ID, amount, target, AM);
  else item_support_cast(ID, amount, target, AM);
}

void entity::shield(entity *target)

{
  int ID = spell_list.get("Shield")->id;
  int amount = 0;

  attackinfo AM;

  string tname = target->get_name(), oname = target->get_name();

  if (target == this) { tname = "yourself";  oname = get_himself(); }

  if (!silent_cast)

  {
    AM.self_output = "#CYou summon forth a mighty shield to protect " + tname + ".#N\r\n";
    AM.targ_output = "#C" + name + " summons forth a mighty shield to protect you.#N\r\n";
    AM.grup_output = "#C" + name + " summons forth a mighty shield to protect " + oname + ".#N\r\n";
    AM.obsv_output = "#C" + name + " summons forth a mighty shield to protect " + oname + ".#N\r\n";
  }

  if (target == this) AM.self_output += "#NYou are protected by Shield.#N";
  else AM.self_output += "#N" + target->get_name() + " is protected by Shield.#N";
  AM.targ_output += "#NYou are protected by Shield.#N";
  AM.grup_output += "#N" + target->get_name() + " is protected by Shield.#N";
  AM.obsv_output += "#N" + target->get_name() + " is protected by Shield.#N";

  target->get_effects()->renew("Shield");

  if (!silent_cast) support_cast(ID, amount, target, AM);
  else item_support_cast(ID, amount, target, AM);
}

void entity::identify(item *the_item)

{
  int ID = spell_list.get("Identify")->id;
  string temp = the_item->name + "#N.";

  if (get_level() > 99) temp += " #n(#c" + intconvert(the_item->id) + "#n)#N";

  if (the_item->find_type(ITEM_EQUIPMENT))

  {
    temp += "\r\nLevel " + intconvert(the_item->level) + " (" + the_item->classes + ")";
    if (the_item->places.find("None") == string::npos) temp += "\r\nEquipable As: " + the_item->places + ".";

    if (the_item->STR > 0) temp += "\r\nRequired Strength: " + intconvert(the_item->STR) + ".";
    if (the_item->DEX > 0) temp += "\r\nRequired Dexterity: " + intconvert(the_item->DEX) + ".";
    if (the_item->CON > 0) temp += "\r\nRequired Constitution: " + intconvert(the_item->CON) + ".";
    if (the_item->INT > 0) temp += "\r\nRequired Intelligence: " + intconvert(the_item->INT) + ".";
    if (the_item->WIS > 0) temp += "\r\nRequired Wisdom: " + intconvert(the_item->WIS) + ".";

    if (the_item->hitroll > 0) temp += "\r\nIncreases Hitroll by " + intconvert(the_item->hitroll) + ".";
    else if (the_item->hitroll < 0) temp += "\r\nDecreases Hitroll by " + intconvert(the_item->hitroll) + ".";
    if (the_item->damroll > 0) temp += "\r\nIncreases Damroll by " + intconvert(the_item->damroll) + ".";
    else if (the_item->damroll < 0) temp += "\r\nDecreases Damroll by " + intconvert(the_item->damroll) + ".";
    if (the_item->armorclass > 0) temp += "\r\nIncreases Armorclass by " + intconvert(the_item->armorclass) + ".";
    else if (the_item->armorclass < 0) temp += "\r\nDecreases Armorclass by " + intconvert(the_item->armorclass) + ".";
    if (the_item->resistance > 0) temp += "\r\nIncreases Resistance by " + intconvert(the_item->resistance) + ".";
    else if (the_item->resistance < 0) temp += "\r\nDecreases Resistance by " + intconvert(the_item->resistance) + ".";

    if (the_item->HP > 0) temp += "\r\nRaises Health by " + intconvert(the_item->HP) + ".";
    else if (the_item->resistance < 0) temp += "\r\nReduces Health by " + intconvert(the_item->HP) + ".";
    if (the_item->MN > 0) temp += "\r\nRaises Mana by " + intconvert(the_item->MN) + ".";
    else if (the_item->resistance < 0) temp += "\r\nReduces Mana by " + intconvert(the_item->MN) + ".";
    if (the_item->MV > 0) temp += "\r\nRaises Movement by " + intconvert(the_item->MV) + ".";
    else if (the_item->resistance < 0) temp += "\r\nReduces Movement by " + intconvert(the_item->MV) + ".";
  }

  if (get_level() > 99)

  {
    //if (the_item->flags != "") temp += "\r\nFlags: " + the_item->flags;
    temp += "\r\n" + the_item->TIME_STAMP;
    temp += "\r\n" + the_item->TYPE_STAMP;
  }

  if (!silent_cast)

  {
    if (spell_list.train(ID))
      temp += "\r\n\n" + spell_list[ID].name + " has improved!";

    if (PLAYER)
      set_current_mana(get_current_mana() - spell_list[ID].manacost);

    echo(format_identify(temp));
  }

  else add_string(format_identify(temp));
}

void entity::item_support_cast(int ID, int amount, entity *target, attackinfo AM)

{
  if (ID == ID) {}
  target->set_current_hp(target->get_current_hp()+amount);

  if ((amount > 0) && (target == this))

  {
    if (target->get_current_hp() != target->get_max_hp()) {
      AM.self_output += "\r\n#NYou heal some of your wounds.#N";
      AM.targ_output += "\r\n#NYou heal some of your wounds.#N";
      AM.grup_output += "\r\n#N" + get_name() + " has healed some of " + get_his() + " wounds.#N";
      AM.obsv_output += "\r\n#N" + get_name() + " has healed some of " + get_his() + " wounds.#N"; }

    else {
      AM.self_output += "\r\n#NYou have completely healed yourself.#N";
      AM.targ_output += "\r\n#NYou have completely healed yourself.#N";
      AM.grup_output += "\r\n#N" + get_name() + " has completely healed " + get_himself() + ".#N";
      AM.obsv_output += "\r\n#N" + get_name() + " has completely healed " + get_himself() + ".#N"; }
  }

  else if ((amount > 0) && (target != this))

  {
    if (target->get_current_hp() != target->get_max_hp()) {
      AM.self_output += "\r\n#NYou heal some of " + TARGETS + " wounds.#N";
      AM.targ_output += "\r\n#N" + get_name() + " has healed some of your wounds.#N";
      AM.grup_output += "\r\n#N" + get_name() + " has healed some of " + TARGETS + " wounds.#N";
      AM.obsv_output += "\r\n#N" + get_name() + " has healed some of " + TARGETS + " wounds.#N"; }

    else {
      AM.self_output += "\r\n#NYou have completely healed " + target->get_name() + ".#N";
      AM.targ_output += "\r\n#NYou have been completely healed by " + get_name() + ".#N";
      AM.grup_output += "\r\n#N" + get_name() + " has completely healed " + target->get_name() + ".#N";
      AM.obsv_output += "\r\n#N" + get_name() + " has completely healed " + target->get_name() + ".#N"; }
  }

  rm->drop_spell_strings(this, target, AM);
}

void entity::item_damage_cast(int ID, int damage, entity *target, attackinfo AM, attackinfo DM)

{
  if (target == this) {
    add_string("You cannot cast that on yourself.");
    return; }

  if (rm->get_LAWFULL() == 1) {
    add_string("You may not attack others in this place.");
    return; }

  if ((rm->get_PKOK() == 0) && (target->PLAYER)) {
    add_string("You may not attack " + target->get_him() + " here.");
    return; }

  if (my_group->in_group(target) == 1) {
    add_string("You may not attack group members.");
    return; }

  if (target->get_client() != NULL)
  if ((target->get_client()->get_state() == CSTATE_LINKDEAD) || (target->get_client()->get_state() == CSTATE_WRITING)) {
    add_string("You can't attack " + target->get_him() + " right now.");
    return; }

  AM.self_output += "\r\nYour " + lowercase(spell_list[ID].name);
  AM.self_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
  AM.targ_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  AM.targ_output += " #rhits you#N for#r " + intconvert(damage) + " #Ndamage.";
  AM.grup_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  AM.grup_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
  AM.obsv_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  AM.obsv_output += " hits " + target->get_name() + ".";

  DM.self_output += "\r\nYour " + lowercase(spell_list[ID].name);
  DM.self_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
  DM.targ_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  DM.targ_output += " #rhits you#N for#r " + intconvert(damage) + " #Ndamage.";
  DM.grup_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  DM.grup_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
  DM.obsv_output += "\r\n" + possessive(name) + " " + lowercase(spell_list[ID].name);
  DM.obsv_output += " hits " + target->get_name() + ".";

  DM.self_output += "\r\n" + target->get_name() + " is dead!";
  DM.targ_output += "\r\nYou are dead!";
  DM.grup_output += "\r\n" + target->get_name() + " is dead!";
  DM.obsv_output += "\r\n" + target->get_name() + " is dead!";

  engage(target, 0);

  target->set_current_hp(target->get_current_hp()-damage);

  attackinfo *SM = &AM;

  if (target->get_current_hp() <= 0) {
    receive_experience(target);
    SM = &DM; }

  rm->drop_spell_strings(this, target, *SM);
  rm->drop_exp_strings(this);

  if (target->get_current_hp() <= 0)
    target->die(1);
}

void entity::item_area_damage_cast(int ID, int damage)

{
  if (rm->get_LAWFULL() == 1) {
    add_string("You may not attack others in this place.");
    return; }

  attackinfo AM;

  for (int i=0; i<rm->get_size(); i++)
  if (can_attack(rm->get_ent(i)))

  {
    entity *target = rm->get_ent(i);

    engage(target, 0);

    if (ID == spell_list.get("Flash")->id) damage = (spell_list[ID].level / 2) + random_int(100, 150);
    if (ID == spell_list.get("Burst")->id) damage = (spell_list[ID].level / 2) + random_int(300, 500);

    target->set_current_hp(target->get_current_hp()-damage);

    AM.self_output  = "Your " + lowercase(spell_list[ID].name);
    AM.self_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
    AM.targ_output  = possessive(get_name()) + " " + lowercase(spell_list[ID].name);
    AM.targ_output += " #rhits you#N for#r " + intconvert(damage) + " #Ndamage.";
    AM.grup_output  = possessive(get_name()) + " " + lowercase(spell_list[ID].name);
    AM.grup_output += " hits " + target->get_name() + " for#R " + intconvert(damage) + " #Ndamage.";
    AM.obsv_output  = possessive(get_name()) + " " + lowercase(spell_list[ID].name);
    AM.obsv_output += " hits " + target->get_name() + ".";

    if (target->get_current_hp() <= 0)

    {
      AM.self_output += "\r\n" + target->get_name() + " is dead!";
      AM.targ_output += "\r\nYou are dead!";
      AM.grup_output += "\r\n" + target->get_name() + " is dead!";
      AM.obsv_output += "\r\n" + target->get_name() + " is dead!";

      receive_experience(target);
    }

    rm->drop_spell_strings(this, target, AM);
    rm->drop_exp_strings(this);
  }

  int diesize = rm->get_size();
  entity* *dielist = new entity* [diesize];

  for (int i=0; i<diesize; i++)
    dielist[i] = rm->get_ent(i);

  for (int i=0; i<diesize; i++)
    if (dielist[i]->get_current_hp() <= 0)
      dielist[i]->die(1);
}

int entity::item_cast_check(string spell_name, string target_name)

{
  target_name = lowercase(target_name);

  if (spell_list.get(spell_name) == NULL) {
    add_string("That spell does not exist.");
    return 0; }

  int ID = spell_list.get(spell_name)->id;

  if ((combat_target == NULL) && (target_name == "") && (spell_list[ID].targeted == 1)) {
    add_string("Who do you want to cast it on?");
    return 0; }

  if ((combat_target == NULL) && (target_name == "") && (spell_list[ID].targeted == 2)) {
    add_string("Which item do you want to cast it on?");
    return 0; }

  if ((combat_target != NULL) && (target_name == "") && (spell_list[ID].targeted == 1))
    target_name = combat_target->get_lname();

  entity* target = NULL;
  item* itarget  = NULL;

  if (spell_list[ID].targeted == 1) {
    target = rm->find_entity(this, target_name);
    if (target == NULL) {
      add_string("There is nobody here by that name.");
      return 0; }
  }

  else if (spell_list[ID].targeted == 2) {
    itarget = rm->find_item_room(target_name);
    if (itarget == NULL) itarget = find_item_inventory(target_name);
    if (itarget == NULL)  {
      add_string("There is no item by that name here.");
      return 0; }
  }

  if (spell_list[ID].type == SPELL_DAMAGE)
    if (!item_damage_check(target))
      return 0;

  if (spell_list[ID].type == SPELL_AREA_DAMAGE)
    if (!item_area_damage_check())
      return 0;

  return 1;
}

int entity::item_damage_check(entity* target)

{
  if (target == this) {
    add_string("You cannot cast that on yourself.");
    return 0; }

  if (rm->get_LAWFULL() == 1) {
    add_string("You may not attack others in this place.");
    return 0; }

  if ((rm->get_PKOK() == 0) && (target->PLAYER)) {
    add_string("You may not attack " + target->get_him() + " here.");
    return 0; }

  if (my_group->in_group(target) == 1) {
    add_string("You may not attack group members.");
    return 0; }

  if (target->get_client() != NULL)
  if ((target->get_client()->get_state() == CSTATE_LINKDEAD) || (target->get_client()->get_state() == CSTATE_WRITING)) {
    add_string("You can't attack " + target->get_him() + " right now.");
    return 0; }

  return 1;
}

int entity::item_area_damage_check()

{
  if (rm->get_LAWFULL() == 1) {
    add_string("You may not attack others in this place.");
    return 0; }

  return 1;
}