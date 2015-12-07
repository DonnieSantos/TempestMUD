#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

mobile::mobile(mobdesc *m, room *r, zone *z)

{
  info = m;
  rm = r;
  my_zone = z;

  current_hp = info->HP;

  name = info->name;
  gold = info->gold;

  gender = info->gender;
  genderset();

  HR = 0;
  DR = 0;
  AC = 0;
  MR = 0;

  EQ_HP = 0;
  EQ_MN = 0;
  EQ_MV = 0;

  num_targets = 0;
  num_followers = 0;
  num_items = 0;
  num_items_equipped = 0;
  num_items_container = 0;

  bank_gold = 0;
  bank_num_items = 0;
  bank_item_list = new item* [200];

  following = this;
  tracking = NULL;
  tracking_dir = "";

  my_group = new group(this);
  combat_target = NULL;

  my_effects = new effects(this);
  intermittent_skill = 0;

  my_student = "";
  my_student_request = "";

  my_client = NULL;
  PLAYER = 0;

  //////////////////////
  //////////////////////

  Aliases = NULL;
  Calendar = NULL;
  Legend = NULL;

  //////////////////////
  //////////////////////

  string_stack = "";
  playerstate = 1;

  for (int i=0; i<info->num_skills; i++)
    skill_list.learn(info->skill[i], 100, 0);

  for (int i=0; i<info->num_spells; i++)
    spell_list.learn(info->spell[i], 100, 0);

  if (info->bdir != 'X') {
    Mob_Blocker = new blocker(this);
    Mob_Blocker->set_btype(info->btype);
    Mob_Blocker->set_bdir(info->bdir); }
  else Mob_Blocker = NULL;

  Right_Hand = NULL;
  Left_Hand = NULL;
  Body = NULL;
  Head = NULL;
  Feet = NULL;
  Back = NULL;
  Hands = NULL;
  Waist = NULL;
  Legs = NULL;
  Right_Ear = NULL;
  Left_Ear = NULL;
  Right_Wrist = NULL;
  Left_Wrist = NULL;
  Right_Finger = NULL;
  Left_Finger = NULL;
  Neck = NULL;

  ITEMS_EQUIPPED[0]  = &Right_Hand;
  ITEMS_EQUIPPED[1]  = &Left_Hand;
  ITEMS_EQUIPPED[2]  = &Body;
  ITEMS_EQUIPPED[3]  = &Head;
  ITEMS_EQUIPPED[4]  = &Feet;
  ITEMS_EQUIPPED[5]  = &Back;
  ITEMS_EQUIPPED[6]  = &Hands;
  ITEMS_EQUIPPED[7]  = &Waist;
  ITEMS_EQUIPPED[8]  = &Legs;
  ITEMS_EQUIPPED[9]  = &Right_Ear;
  ITEMS_EQUIPPED[10] = &Left_Ear;
  ITEMS_EQUIPPED[11] = &Right_Wrist;
  ITEMS_EQUIPPED[12] = &Left_Wrist;
  ITEMS_EQUIPPED[13] = &Right_Finger;
  ITEMS_EQUIPPED[14] = &Left_Finger;
  ITEMS_EQUIPPED[15] = &Neck;
}

int mobile::get_type()                    { return ENTITY_MOBILE;      }
string mobile::get_lname()                { return info->target[0];    }
string mobile::get_prename()              { return "";                 }
string mobile::get_title()                { return "";                 }
string mobile::get_class()                { return "Mobile";           }
string mobile::get_sclass()               { return "#YMb";             }
string mobile::get_ssclass()              { return "Mb";               }
string mobile::get_clan()                 { return "None";             }
string mobile::get_religion()             { return "None";             }
string mobile::get_password()             { return "";                 }
string mobile::get_hometown()             { return "";                 }
int mobile::get_max_hp()                  { return (info->HP + EQ_HP); }
int mobile::get_natural_hp()              { return (info->HP);         }
int mobile::get_last_experience()         { return -1;                 }
int mobile::get_current_mana()            { return 0;                  }
int mobile::get_current_move()            { return 0;                  }
int mobile::get_max_mana()                { return 0;                  }
int mobile::get_max_move()                { return 0;                  }
int mobile::get_natural_mana()            { return 0;                  }
int mobile::get_natural_move()            { return 0;                  }
int mobile::get_experience()              { return 0;                  }
int mobile::get_exp_needed()              { return 0;                  }
int mobile::get_dpoints()                 { return 0;                  }
void mobile::set_desc(string)             { return;                    }
void mobile::set_plan(string)             { return;                    }
void mobile::set_title(string, int)       { return;                    }
void mobile::set_prename(string, int)     { return;                    }
void mobile::level_up()                   { return;                    }
void mobile::level_up(int)                { return;                    }
void mobile::add(string)                  { return;                    }
void mobile::set_level(int)               { return;                    }
void mobile::set_max_hp(int)              { return;                    }
void mobile::set_current_mana(int)        { return;                    }
void mobile::set_max_mana(int)            { return;                    }
void mobile::set_current_move(int)        { return;                    }
void mobile::set_max_move(int)            { return;                    }
void mobile::set_experience(int)          { return;                    }
void mobile::set_password(string)         { return;                    }
void mobile::set_hometown(string)         { return;                    }
void mobile::set_class(string)            { return;                    }
void mobile::set_clan(string)             { return;                    }
void mobile::set_religion(string)         { return;                    }
void mobile::set_exp_needed()             { return;                    }
void mobile::set_STR(int)                 { return;                    }
void mobile::set_DEX(int)                 { return;                    }
void mobile::set_CON(int)                 { return;                    }
void mobile::set_INT(int)                 { return;                    }
void mobile::set_WIS(int)                 { return;                    }
void mobile::set_dpoints(int)             { return;                    }
void mobile::toggle_follow()              { return;                    }
void mobile::toggle_assist()              { return;                    }
void mobile::set_followable(int)          { return;                    }
void mobile::set_autoassist(int)          { return;                    }
void mobile::receive_experience(entity*)  { return;                    }
void mobile::gain_experience(int)         { return;                    }
void mobile::no_experience()              { return;                    }
int mobile::get_STR()                     { return info->STR;          }
int mobile::get_DEX()                     { return info->DEX;          }
int mobile::get_CON()                     { return info->CON;          }
int mobile::get_INT()                     { return info->INT;          }
int mobile::get_WIS()                     { return info->WIS;          }
int mobile::get_HR()                      { return (info->HR + HR);    }
int mobile::get_DR()                      { return (info->DR + DR);    }
int mobile::get_AC()                      { return (info->AC + AC);    }
int mobile::get_MR()                      { return (info->MR + MR);    }
int mobile::get_gold()                    { return gold;               }
mobdesc* mobile::get_info()               { return info;               }
int mobile::get_level()                   { return info->level;        }
string mobile::get_desc()                 { return info->desc;         }
string mobile::get_plan()                 { return "";                 }
int mobile::get_followable()              { return 1;                  }
int mobile::get_autoassist()              { return 1;                  }
void mobile::set_name(string s)           { name = s;                  }
int mobile::get_playerstate()             { return playerstate;        }
void mobile::set_playerstate(int i)       { playerstate = i;           }
void mobile::restore()                    { current_hp = info->HP;     }
void mobile::set_gold(int i)              { gold = i;                  }

void mobile::set_current_hp(int i)

{
  current_hp = i;
  if (current_hp < 0) current_hp = 0;
  if (current_hp > info->HP) current_hp = info->HP;
}

void mobile::die(int make_corpse)

{
  if (my_client != NULL)
    unpossess(0);

  if ((my_group->get_size() > 1) && (my_group->get_leader() == this))
    my_group->remove_all(this);

  for (int i=0; i<num_followers; i++)
    followers[i]->stop_following();

  stop_following();

  Death(make_corpse);

  my_zone->kill_mob(this);
}

int mobile::get_sanc()

{
  if (info->SANCD == 1) return 1;
  if (my_effects->get_ticks("Sanctuary") > 0) return 1;

  return 0;
}

blocker* mobile::get_blocker()

{
  return Mob_Blocker;
}

void mobile::regen()

{
  float max, gain;
  int intgain;

  max = (float)get_max_hp();
  gain = ((0.10) * max);

  intgain = (int)gain;

  current_hp += intgain;

  if (current_hp > info->HP)
    current_hp = info->HP;
}

int mobile::check_targets(string trg)

{
  for (int i=0; i<info->num_targets; i++)

  {
    if (info->target[i] == trg)
      return 1;
  }

  return 0;
}

string mobile::stat()

{
  return ("Insert Mob Stats Here.");
}

int mobile::random_move()

{
  int n = random_int(0, 60000);

  if (info->speed >= n)

  {
    if (rm->get_num_mobexits() > 0)

    {
      int m = random_int(0, rm->get_num_mobexits()-1);
      int target = rm->get_mobexit(m);

      if (target == rm->get_N()) move("N");
      else if (target == rm->get_S()) move("S");
      else if (target == rm->get_E()) move("E");
      else if (target == rm->get_W()) move("W");
      else if (target == rm->get_U()) move("U");
      else if (target == rm->get_D()) move("D");

      return 1;
    }
  }

  return 0;
}

void mobile::consider_action()

{
  int moved = 0;

  if (playerstate != PSTATE_FIGHTING)

  {

    if ((info->MOBILE) && (following == this) && (my_client == NULL))
      moved = random_move();

    if ((!moved) && (my_client == NULL) && (rm->occupied()))

    {
      int acted = 0;
      int n = random_int(0, 60000);

      for (int i=0; i<info->num_actions; i++)

      {
        if (!acted)

        {
          if (info->ACTF[i] >= n) {
            interpret_command(info->ACT[i]);
            acted = 1; }
        }
      }
    }
  }
}

void mobile::pop_equipment()

{
  itemlist *itmlst = World->get_itemlist();

  for (int i=0; i<info->num_items; i++)

  {
    int n = random_int(1,10000);

    if (n <= info->Item_Freq[i]) {
      item *the_item = itmlst->replicate(info->Items[i], "Popped by " + name + ".");
      add_item_inventory(the_item); }
  }

  for (int i=0; i<info->num_wears; i++)

  {
    int n = random_int(1,10000);

    if (n <= info->Wear_Freq[i]) {
      item *the_item = itmlst->replicate(info->Wears[i], "Popped by " + name + ".");
      add_item_inventory(the_item);
      equip(the_item, 0); }
  }
}

int find_word(string s, string str)

{
  string quote = "x";
  quote[0] = 34;

  if (str.find(" " + s + " ", 0) != string::npos) return 1;
  if (str.find(" " + s + ".", 0) != string::npos) return 1;
  if (str.find(" " + s + "!", 0) != string::npos) return 1;
  if (str.find(" " + s + "?", 0) != string::npos) return 1;
  if (str.find(" " + s + ",", 0) != string::npos) return 1;
  if (str.find("'" + s + " ", 0) != string::npos) return 1;
  if (str.find("'" + s + ".", 0) != string::npos) return 1;
  if (str.find("'" + s + "!", 0) != string::npos) return 1;
  if (str.find("'" + s + "?", 0) != string::npos) return 1;
  if (str.find("'" + s + ",", 0) != string::npos) return 1;

  if (str.find(" " + s + "'", 0) != string::npos) return 1;
  if (str.find("'" + s + "'", 0) != string::npos) return 1;
  if (str.find(" " + s + "#n'", 0) != string::npos) return 1;
  if (str.find("'" + s + "#n'", 0) != string::npos) return 1;
  if (str.find(quote + s + quote, 0) != string::npos) return 1;

  return 0;
}

string item_after(string str, string s)

{
  int pos = str.find(s);
  str = str.substr(pos+s.size()+1, str.size()-pos-s.size()+1);

  str = remove_bsr(str);
  str = remove_bsn(str);
  str = remove_colors(str);

  if ((str.find("a ") == 0) && (str.size() >= 2)) str.erase(0,2);
  if ((str.find("an ") == 0) && (str.size() >= 3)) str.erase(0,3);
  if ((str.find("my ") == 0) && (str.size() >= 3)) str.erase(0,3);
  if ((str.find("the ") == 0) && (str.size() >= 4)) str.erase(0,4);
  if ((str.find("this ") == 0) && (str.size() >= 5)) str.erase(0,5);
  if ((str.find("that ") == 0) && (str.size() >= 5)) str.erase(0,5);
  while (str.find(".") != string::npos) str.erase(str.find("."), 1);
  while (str.find(",") != string::npos) str.erase(str.find(","), 1);
  while (str.find("!") != string::npos) str.erase(str.find("!"), 1);
  while (str.find("?") != string::npos) str.erase(str.find("?"), 1);
  while (str.find("'") != string::npos) str.erase(str.find("'"), 1);
  while (str.find("please") != string::npos) str.erase(str.find("please"), 6);
  while (str.find("Please") != string::npos) str.erase(str.find("Please"), 6);
  while (str.find("thanks") != string::npos) str.erase(str.find("thanks"), 6);
  while (str.find("Thanks") != string::npos) str.erase(str.find("Thanks"), 6);
  while (str.find("thank you") != string::npos) str.erase(str.find("thank you"), 9);
  while (str.find("Thank you") != string::npos) str.erase(str.find("Thank you"), 9);
  if (str.find("my ") == 0) str.erase(0, 3);
  if (str.find("the ") == 0) str.erase(0, 4);
  if (str.find("this ") == 0) str.erase(0, 5);

  str = clear_whitespace(str);
  str = key(str);

  return str;
}

void mobile::examine_say(string str)

{
  string target = "";
  int count = 0, found = 0, talking_to_me = 0;

  while (str[count] != ',') {
    target += str[count];
    count++; }

  target.erase(0,2);
  target.erase(target.size()-5, 5);
  str = lowercase(str);

  entity *SRC = rm->find_character(target);
  if (SRC == NULL) return;

  for (int i=0; i<info->num_targets; i++)
    if (str.find(info->target[i], 0) != string::npos)
      talking_to_me = 1;

  if (talking_to_me)

  {
    if ((info->NPC) && (!info->BARD) && (SAID("change my plan") || SAID("change my description")))
      interpret_command("respond I can't help you with that. Try asking a Bard.");

    else if ((info->NPC) && (!info->BARD) && (SAID("bard") || SAID("bards") || SAID("plan") || SAID("description")))
      interpret_command("respond Bards can help you change your plan and description. I, however, am no Bard.");

    else if ((info->BARD) && SAID("change my description"))
      SRC->write_other(2);

    else if ((info->BARD) && SAID("change my plan"))
      SRC->write_other(3);

    else if ((info->BARD) && (SAID("bard") || SAID("bards") || SAID("plan") || SAID("description")))
      interpret_command("respond I am a Bard. I can help you change your plan or description. If that is your wish, just say so.");

    else if ((info->NPC) && (SAID("teach") || SAID("learn"))) {
      string temp  = "respond I cannot teach you any skills or spells. To learn new abilities, you must ";
             temp += "seek the assistance of one who already knows the skill or spell that you wish ";
             temp += "to learn, and they can teach you.";
      interpret_command(temp); }

    else if ((info->NPC) && (!info->SKILLMASTER) && (SAID("skill") || SAID("skills")))
      interpret_command("respond I don't know much about skills. Try asking a Skill Master.");

    else if ((info->NPC) && (!info->MERCHANT) && (SAID("list") || SAID("buy") || SAID("sell")))
      interpret_command("respond I'm sorry, I am not a merchant.  I do not buy or sell items.");

    else if ((info->MERCHANT) && SAID("buy"))
      interpret_command("respond Soon I will provide you with information about the items you want to buy.");

    else if ((info->MERCHANT) && SAID("sell"))
      interpret_command("respond Soon I will provide you with information about the items you have to sell.");

    else if ((info->MERCHANT) && SAID("list"))
      SRC->echo(merchant_item_list());

    else if ((info->NPC) && (!info->BANKER) && (SAID("account") || SAID("deposit") || SAID("withdraw")))
      interpret_command("respond Does this look like a bank to you?");

    else if ((info->BANKER) && SAID("account"))
      SRC->display_bank_account();

    else if ((info->BANKER) && SAID("deposit"))
      interpret_command("respond " + SRC->deposit(lowercase(item_after(str, "deposit"))));

    else if ((info->BANKER) && SAID("withdraw"))
      interpret_command("respond " + SRC->withdraw(lowercase(item_after(str, "withdraw"))));

    else if ((info->SKILLMASTER) && SAID("skill"))

    {
      for (int i=1; i<=skill_list.size(); i++)
      if (SAID(lowercase(skill_list[i].name))
      && ((skill_list[i].classes.find(SRC->get_ssclass()) != string::npos)
      || (skill_list[i].classes.find("All") != string::npos)))

      {
        found = 1;
        string tempstr = "respond " + skill_list[i].desc + " To learn it requires ";
        if (skill_list[i].STR > 0) tempstr += intconvert(skill_list[i].STR) + " Strength, ";
        if (skill_list[i].DEX > 0) tempstr += intconvert(skill_list[i].DEX) + " Dexterity, ";
        if (skill_list[i].CON > 0) tempstr += intconvert(skill_list[i].CON) + " Constitution, ";
        if (skill_list[i].INT > 0) tempstr += intconvert(skill_list[i].INT) + " Intelligence, ";
        if (skill_list[i].WIS > 0) tempstr += intconvert(skill_list[i].WIS) + " Wisdom, ";
        tempstr.erase(tempstr.length()-2, 2);
        tempstr += ".";
        interpret_command(tempstr);
      }

      if (!found) interpret_command("respond I'm sorry, there are no skills like that you can learn.");
    }

    else if ((info->SKILLMASTER) && SAID("skills"))
      SRC->display_all_skills();

    else if ((info->NPC) && (!info->SPELLMASTER) && (SAID("spell") || SAID("spells")))
      interpret_command("respond I don't know much about spells. Try asking a Spell Master.");

    else if ((info->SPELLMASTER) && SAID("spell"))

    {
      for (int i=1; i<=spell_list.size(); i++)
      if (SAID(lowercase(spell_list[i].name))
      && ((spell_list[i].classes.find(SRC->get_ssclass()) != string::npos)
      || (spell_list[i].classes.find("All") != string::npos)))

      {
        found = 1;
        string tempstr = "respond " + spell_list[i].desc + " To learn it requires ";
        if (spell_list[i].STR > 0) tempstr += intconvert(spell_list[i].STR) + " Strength, ";
        if (spell_list[i].DEX > 0) tempstr += intconvert(spell_list[i].DEX) + " Dexterity, ";
        if (spell_list[i].CON > 0) tempstr += intconvert(spell_list[i].CON) + " Constitution, ";
        if (spell_list[i].INT > 0) tempstr += intconvert(spell_list[i].INT) + " Intelligence, ";
        if (spell_list[i].WIS > 0) tempstr += intconvert(spell_list[i].WIS) + " Wisdom, ";
        tempstr.erase(tempstr.length()-2, 2);
        tempstr += ".";
        interpret_command(tempstr);
      }

      if (!found) interpret_command("respond I'm sorry, there are no spells like that you can learn.");
    }

    else if ((info->SPELLMASTER) && SAID("spells"))
      SRC->display_all_spells();

    else if ((info->NPC) && SAID("how are you"))
      interpret_command("respond I'm fine, thanks. How are you?");

    else if ((info->NPC) && (SAID("whats up") || SAID("what's up")))
      interpret_command("respond Not much, " + target + ". What's up with you?");

    else if ((info->NPC) && (SAID("hi") || SAID("hello") || SAID("hiya") || SAID("hey") || SAID("heya")
         || SAID("yo") || SAID("greetings")))
      interpret_command("respond Hello there, " + target + ".");

    else if ((info->NPC) && SAID("hail"))
      interpret_command("respond Hail to you, " + target + ".");

    else if ((info->NPC) && (SAID("thanks") || SAID("thank you")))
      interpret_command("respond You're welcome, " + target + ".");
  }
}