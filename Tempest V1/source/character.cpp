#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

character::character()

{
  level = 1;
  name = "LOGGING IN";
  lname = "LOGGING IN";
  current_hp = 1;
  max_hp = 1;
  current_mana = 1;
  max_mana = 1;
  current_move = 1;
  max_move = 1;
  title = "the newbie.";
  experience = 0;

  profession = "Warrior";
  sclass = "#BWa#N";

  gender = "male";
  genderset();

  STR = 0;
  DEX = 0;
  CON = 0;
  INT = 0;
  WIS = 0;

  HR = 0;
  DR = 0;
  AC = 0;
  MR = 0;
  gold = 5000;

  EQ_HP = 0;
  EQ_MN = 0;
  EQ_MV = 0;

  D_Points = 0;
  num_followers = 0;
  num_targets = 0;
  num_items = 0;
  num_items_equipped = 0;
  num_items_container = 0;

  bank_gold = 0;
  bank_num_items = 0;
  bank_item_list = new item* [200];

  set_exp_needed();

  following = this;
  FOLLOWABLE = 1;
  AUTOASSIST = 1;

  tracking = NULL;
  tracking_dir = "";

  my_group = new group(this);
  combat_target = NULL;

  my_effects = new effects(this);
  intermittent_skill = 0;

  my_student = "";
  my_student_request = "";

  info = NULL;
  PLAYER = 1;

  Aliases = new alias_list(this);
  Legend = new legend(this);
  Calendar = new schedule();

  plan = "";
  clan_name = "None";
  religion_name = "None";

  last_experience = -1;
  string_stack = "";
  playerstate = 1;

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

int character::get_type()                { return ENTITY_CHAR;          }
string character::get_lname()            { return lname;                }
string character::get_prename()          { return prename;              }
string character::get_title()            { return title;                }
string character::get_class()            { return profession;           }
string character::get_sclass()           { return sclass;               }
string character::get_clan()             { return clan_name;            }
string character::get_religion()         { return religion_name;        }
int character::get_max_hp()              { return (max_hp + EQ_HP);     }
int character::get_natural_hp()          { return max_hp;               }
int character::get_current_mana()        { return current_mana;         }
int character::get_max_mana()            { return (max_mana + EQ_MN);   }
int character::get_natural_mana()        { return max_mana;             }
int character::get_current_move()        { return current_move;         }
int character::get_max_move()            { return (max_move + EQ_MV);   }
int character::get_natural_move()        { return max_move;             }
int character::get_experience()          { return experience;           }
int character::get_exp_needed()          { return exp_needed;           }
int character::get_last_experience()     { return last_experience;      }
string character::get_password()         { return password;             }
string character::get_hometown()         { return hometown;             }
int character::get_STR()                 { return STR;                  }
int character::get_DEX()                 { return DEX;                  }
int character::get_CON()                 { return CON;                  }
int character::get_INT()                 { return INT;                  }
int character::get_WIS()                 { return WIS;                  }
int character::get_HR()                  { return HR;                   }
int character::get_DR()                  { return DR;                   }
int character::get_AC()                  { return AC;                   }
int character::get_MR()                  { return MR;                   }
int character::get_gold()                { return gold;                 }
int character::get_dpoints()             { return D_Points;             }
void character::set_level(int i)         { level = i;                   }
void character::set_max_hp(int i)        { max_hp = i;                  }
void character::set_max_mana(int i)      { max_mana = i;                }
void character::set_max_move(int i)      { max_move = i;                }
void character::set_experience(int i)    { experience = i;              }
void character::set_desc(string s)       { desc = s;                    }
void character::set_plan(string s)       { plan = s;                    }
void character::set_clan(string s)       { clan_name = s;               }
void character::set_religion(string s)   { religion_name = s;           }
void character::set_password(string s)   { password = s;                }
void character::set_hometown(string s)   { hometown = s;                }
void character::set_followable(int i)    { FOLLOWABLE = i;              }
void character::set_autoassist(int i)    { AUTOASSIST = i;              }
void character::set_STR(int x)           { STR = x;                     }
void character::set_DEX(int x)           { DEX = x;                     }
void character::set_CON(int x)           { CON = x;                     }
void character::set_INT(int x)           { INT = x;                     }
void character::set_WIS(int x)           { WIS = x;                     }
void character::set_gold(int x)          { gold = x;                    }
void character::set_dpoints(int x)       { D_Points = x;                }
void character::toggle_follow()          { FOLLOWABLE = 1 - FOLLOWABLE; }
void character::toggle_assist()          { AUTOASSIST = 1 - AUTOASSIST; }
int character::get_level()               { return level;                }
int character::get_followable()          { return FOLLOWABLE;           }
int character::get_autoassist()          { return AUTOASSIST;           }
string character::get_desc()             { return desc;                 }
string character::get_plan()             { return plan;                 }
mobdesc* character::get_info()           { return NULL;                 }
void character::examine_say(string)      { return;                      }
int character::get_playerstate()         { return playerstate;          }
void character::set_playerstate(int i)   { playerstate = i;             }
void character::no_experience()          { last_experience = -1;        }

item* character::merchant_find_item(int, string)    { return NULL;      }
void character::merchant_sell_item(item*, entity*)  { return;           }
void character::merchant_buy_item(item*, entity*)   { return;           }
string character::merchant_item_list()              { return "";        }
blocker* character::get_blocker()                   { return NULL;      }

void character::set_current_hp(int i)

{
  current_hp = i;
  if (current_hp < 0) current_hp = 0;
  if (current_hp > get_max_hp()) current_hp = get_max_hp();
}

void character::set_current_mana(int i)

{
  current_mana = i;
  if (current_mana < 0) current_mana = 0;
  if (current_mana > get_max_mana()) current_mana = get_max_mana();
}

void character::set_current_move(int i)

{
  current_move = i;
  if (current_move < 0) current_move = 0;
  if (current_move > get_max_move()) current_move = get_max_move();
}

void character::set_prename(string s, int audible)

{
  if (visible_size(s) > 15) {
    if (audible) echo("Prenames may not exceed 15 characters.");
    return; }

  if (s == "") prename = s;
  else if (audible) prename = s + " #N";
  else prename = s;

  if (audible) {
    if (s == "") echo("You now have no prename.");
    else echo("Your prename is now: " + s + "#N."); }

  if (my_client->get_state() != CSTATE_LOGIN)
    File->save_profile(my_client);
}

void character::set_title(string s, int audible)

{
  if (visible_size(s) > 40) {
    if (audible) echo("Titles must be 40 characters or less.");
    return; }

  if (s == "") title = s;
  if (audible) title = s + "#N";
  else title = s;

  if (audible) {
    if (s == "") echo("You now have no title.");
    else echo("Your title is now: " + name + " " + s + "#N."); }

  if (my_client->get_state() != CSTATE_LOGIN)
    File->save_profile(my_client);
}

void character::die(int make_corpse)

{
  clist->syslog_gecho(get_name() + " was killed.", 100, my_client->get_ip());

  Death(make_corpse);

  set_room(World->get_room(100));
  rm->gain(this);

  look("\r\n#cBy the grace of Genevieve, you are revived and transported to safety!#N");
  set_current_hp(1);

  File->save_profile(my_client);
}

int character::get_sanc()

{
  if (my_effects->get_ticks("Sanctuary") > 0)
    return 1;

  return 0;
}

void character::set_exp_needed()

{
  exp_needed = get_exp_need(level);
}

string character::get_ssclass()

{
  string nocolor_sclass = get_sclass();
  nocolor_sclass.erase(0,2);
  nocolor_sclass.erase(2,2);

  return nocolor_sclass;
}

void character::set_class(string s)

{
  if ((s == "Warrior") || (s == "Knight") || (s == "Berzerker") || (s == "Paladin")
  || (s == "Death Knight") || (s == "Crusader") || (s == "Antipaladin")) {
    HP_MULT = 25;
    MN_MULT = 5;
    MV_MULT = 23; }

  else if ((s == "Thief") || (s == "Assassin") || (s == "Rogue") || (s == "Ranger")
  || (s == "Shadowblade") || (s == "Merchant") || (s == "Mercenary")) {
    HP_MULT = 15;
    MN_MULT = 10;
    MV_MULT = 22; }

  else if ((s == "Cleric") || (s == "Druid") || (s == "Monk") || (s == "Priest")
  || (s == "Dark Cleric") || (s == "Healer") || (s == "Alchemist")) {
    HP_MULT = 13;
    MN_MULT = 18;
    MV_MULT = 18; }

  else if ((s == "Mage") || (s == "Wizard") || (s == "Illusionist") || (s == "Warlock")
  || (s == "Sorcerer") || (s == "Summoner") || (s == "Shapeshifter")) {
    HP_MULT = 10;
    MN_MULT = 29;
    MV_MULT = 17; }

  profession = s;
  sclass = sclassify(s);

  update_claninfo();
  update_religioninfo();
}

void character::level_up()

{
  if (level < 99)

  {
    level++;
    experience = 0;
    set_exp_needed();
    D_Points += 5;

    max_hp += (HP_MULT + (CON/10));
    max_mana += (MN_MULT + (WIS/10));
    max_move += (MV_MULT + (CON/20));

    File->save_profile(my_client);
  }

  else

  {
    experience = 0;
    exp_needed = 0;
  }
}

void character::level_up(int x)

{
  for (int i=1; i<=x; i++)
    level_up();
}

void character::receive_experience(entity *target)

{
  if (target->PLAYER) return;

  int total_exp = target->get_info()->exp;
  int percentage = get_exp_percent(rm->get_groupsize(my_group));
  int shared_exp = ((total_exp * percentage) / 100);

  my_group->divide_experience(rm, shared_exp);
}

void character::gain_experience(int num)

{
  if (level < 99)

  {
    if (num > (exp_needed / 100))
      num = (exp_needed / 100);

    experience += num;
    last_experience = num;

    if (experience >= exp_needed)
      level_up();
  }

  else

  {
    experience = 0;
    last_experience = -1;
  }
}

void character::regen()

{
  int CON_MAX = CON;  if (CON > 200) CON_MAX = 200;
  int WIS_MAX = WIS;  if (WIS > 200) WIS_MAX = 200;

  float h_percent = (0.10) + ((float)CON_MAX/2000);
  float m_percent = (0.10) + ((float)WIS_MAX/2000);
  float v_percent = (0.10) + ((float)CON_MAX/2000);

  int h = (int)((float)(get_max_hp()+10) * h_percent);
  int m = (int)((float)(get_max_mana()+10) * m_percent);
  int v = (int)((float)(get_max_move()+10) * v_percent);

  current_hp += h;
  current_mana += m;
  current_move += v;

  if (current_hp > get_max_hp()) current_hp = get_max_hp();
  if (current_mana > get_max_mana()) current_mana = get_max_mana();
  if (current_move > get_max_move()) current_move = get_max_move();
}

void character::restore()

{
  current_hp   = get_max_hp();
  current_mana = get_max_mana();
  current_move = get_max_move();
}

void character::set_name(string s)

{
  name = s;
  lname = s;

  int size = s.size();

  for (int i=0; i<=size-1; i++)

  {
    if ((name[i] >= 65) && (name[i] <= 90))
      lname[i] = name[i] + 32;
  }
}

int character::check_targets(string str)

{
  if (short_for(str, lname))
    return 1;

  return 0;
}

void character::add(string str)

{
  if (str == "str") {
    if (D_Points > 0) {
      D_Points--; STR++;
      echo("#gYou feel stronger!#N"); }
    else echo("You do not have any stat points to spend."); }

  else if (str == "dex") {
    if (D_Points > 0) {
      D_Points--; DEX++;
      echo("#gYour dexterity has improved!#N"); }
    else echo("You do not have any stat points to spend."); }

  else if (str == "con") {
    if (D_Points > 0) {
      D_Points--; CON++;
      echo("#gA warm rush of vitality fills you!#N"); }
    else echo("You do not have any stat points to spend."); }

  else if (str == "int") {
    if (D_Points > 0) {
      D_Points--; INT++;
      echo("#gArcane power surges through you!#N"); }
    else echo("You do not have any stat points to spend."); }

  else if (str == "wis") {
    if (D_Points > 0) {
      D_Points--; WIS++;
      echo("#gYou are blessed with understanding and insight!#N"); }
    else echo("You do not have any stat points to spend."); }

  else {
    string tempstr = "Add Usage#n:#N #R[#Nadd str#R] [#Nadd dex#R] [#Nadd con#R] [#Nadd int#R] [#Nadd wis#R]#N";
    echo(tempstr); }
}

void fx_rig(string *temp, int *num_fx, int *underlined, string *FXINFO)

{
  *num_fx = *num_fx - 1;

  if (*num_fx < 0)

  {
    if (*underlined == 0) {
      *temp += "#n---------|";
      *underlined = 1; }

    else *temp += "#n         |";
  }

  else {
    *temp += " " + FXINFO->substr(0,11) + " #n|";
    FXINFO->erase(0,11); }
}

string character::stat()

{
  string temp;
  string exp, expn, gld;
  string xs = "", ys = "", zs = "", es = "", ns = "", gs = "";
  string STR_st = "", DEX_st = "", CON_st = "", INT_st = "", WIS_st = "";
  string HR_st = "", DR_st = "", AC_st = "", MR_st = "";
  string first_st = "", second_st = "", third_st = "";
  int x = 1, y = 1, z = 1, e, n, g;

  for (int i=1; i<=30-(int)name.size(); i++)
    first_st += " ";

  for (int i=1; i<=20-(int)profession.size(); i++)
    second_st += " ";

  for (int i=1; i<=20-visible_size(hometown); i++)
    third_st += " ";

  if (level < 100) second_st += " ";
  if (level < 10) second_st += " ";

  if (AC < 1000) AC_st += " ";
  if (STR < 100) STR_st += " ";  if (DEX < 100) DEX_st += " ";  if (CON < 100) CON_st += " ";
  if (INT < 100) INT_st += " ";  if (WIS < 100) WIS_st += " ";  if (HR < 100) HR_st += " ";
  if (DR < 100) DR_st += " ";    if (AC < 100) AC_st += " ";    if (MR < 100) MR_st += " ";
  if (STR < 10) STR_st += " ";   if (DEX < 10) DEX_st += " ";   if (CON < 10) CON_st += " ";
  if (INT < 10) INT_st += " ";   if (WIS < 10) WIS_st += " ";   if (HR < 10) HR_st += " ";
  if (DR < 10) DR_st += " ";     if (AC < 10) AC_st += " ";     if (MR < 10) MR_st += " ";

  if (current_hp < 1000) x++;      if (current_hp < 100) x++;      if (current_hp < 10) x++;
  if (get_max_hp() < 1000) x++;    if (get_max_hp() < 100) x++;    if (get_max_hp() < 10) x++;
  if (current_mana < 1000) y++;    if (current_mana < 100) y++;    if (current_mana < 10) y++;
  if (get_max_mana() < 1000) y++;  if (get_max_mana() < 100) y++;  if (get_max_mana() < 10) y++;
  if (current_move < 1000) z++;    if (current_move < 100) z++;    if (current_move < 10) z++;
  if (get_max_move() < 1000) z++;  if (get_max_move() < 100) z++;  if (get_max_move() < 10) z++;

  for (int i=1; i<=x; i++) xs += " ";
  for (int i=1; i<=y; i++) ys += " ";
  for (int i=1; i<=z; i++) zs += " ";

  exp  = exp_commas(intconvert(experience));
  expn = exp_commas(intconvert(exp_needed));
  gld  = exp_commas(intconvert(gold));

  e = 11 - exp.size();
  n = 11 - expn.size();
  g = 11 - gld.size();

  for (int i=1; i<=e; i++) es += " ";
  for (int i=1; i<=n; i++) ns += " ";
  for (int i=1; i<=g; i++) gs += " ";

  string FXINFO = my_effects->get_stat_info();
  string firsttwo = FXINFO.substr(0,2);
  if (firsttwo[0] == 0) firsttwo.erase(0,1);
  int num_fx = stringconvert(firsttwo);
  int overlined = 0;
  int underlined = 0;
  FXINFO.erase(0,2);

  temp  = "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+";
  if (num_fx > 0) { temp += "-=-=-=-=-+";  overlined = 1; }
  temp += "#n\r\n  |  #C" + name + "#N";
  temp += first_st + "#n+-------------------+  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "\r\n  #n|  #NLevel " + intconvert(level) + " " + profession;
  temp += second_st + "#n[#N     STR - " + STR_st + intconvert(STR) + "     #n]  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |  #NHometown#n:#N ";
  temp += hometown + third_st + "#n[     #NDEX - " + DEX_st + intconvert(DEX) + "     #n]  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |                                [     #NCON - " + CON_st + intconvert(CON) + "     #n]  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |     +--------------------+     [     #NINT - " + INT_st + intconvert(INT) + "     #n]  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |     [#N Health = #g" + intconvert(current_hp) + "#N/#G" + intconvert(get_max_hp()) + xs;
  temp += "#n]     [     #NWIS - " + WIS_st + intconvert(WIS) + "     #n]  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |     [   #NMana = #m";
  temp += intconvert(current_mana) + "#N/#M" + intconvert(get_max_mana()) + ys;
  temp += "#n]     +-------------------+  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |     [   #NMove = #b";
  temp += intconvert(current_move) + "#N/#B" + intconvert(get_max_move()) + zs;
  temp += "#n]     [    #NHitroll#n:#N  " + HR_st + intconvert(HR) + "  #n]  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |     +--------------------+     [    #NDamroll#n:#N  " + DR_st + intconvert(DR) + "  #n]  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |                                [ #NArmorclass#n:#N " + AC_st + intconvert(AC) + "  #n]  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |  +--------------------------+  [ #NResistance#n:#N  " + MR_st + intconvert(MR) + "  #n]  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |  [ #NExperience = #r" + es + exp + " #n]  +-------------------+  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |  [ #NNext Level = #R";
  temp += ns + expn + " #n]  [ #NGold#n: #y" + gs + gld + " #n]  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  |  +--------------------------+  +-------------------+  |#N";
  if (overlined) fx_rig(&temp, &num_fx, &underlined, &FXINFO);
  temp += "#n\r\n  +-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";
  if (overlined) temp += "#n-=-=-=-=-+#N";

  if (D_Points > 0)

  {
    string extra = "";
    if (D_Points < 100) extra += " ";
    if (D_Points < 10) extra += " ";

    temp += "\r\n  #n+-------------------------------------------------------+\r\n";
    temp += "  | #NYou have #r" + intconvert(D_Points) + " #Nstat points to spend. ";
    temp += extra + "#N(Usage: #R[#Nadd STR#R]#N) #n|\r\n  +-------------------------------------------------------+#N";
  }

  return temp;
}