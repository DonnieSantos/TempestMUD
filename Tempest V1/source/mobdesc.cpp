#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

mobdesc::mobdesc(int vn)

{
  vnum = vn;

  zone_id = 0;

  num_targets  =  0;
  num_skills   =  0;
  num_spells   =  0;
  num_actions  =  0;
  num_items    =  0;
  num_wears    =  0;

  MOBILE       =  0;
  AGGRO        =  0;
  NPC          =  0;
  BARD         =  0;
  SKILLMASTER  =  0;
  SPELLMASTER  =  0;
  SANCD        =  0;
  MERCHANT     =  0;
  BANKER       =  0;

  merchant_listsize = 0;
}

void mobdesc::add_flag(string flag)

{
  store_name = "";

  if (flag.size() > 9)
  if (flag.substr(0,8) == "MERCHANT") {
    store_name = flag.substr(9, flag.size()-9);
    flag = "MERCHANT"; }

  if (flag == "MOBILE")            MOBILE       =  1;
  else if (flag == "AGGRO")        AGGRO        =  1;
  else if (flag == "BARD")         BARD         =  1;
  else if (flag == "NPC")          NPC          =  1;
  else if (flag == "SKILLMASTER")  SKILLMASTER  =  1;
  else if (flag == "SPELLMASTER")  SPELLMASTER  =  1;
  else if (flag == "SANC")         SANCD        =  1;
  else if (flag == "MERCHANT")     MERCHANT     =  1;
  else if (flag == "BANKER")       BANKER       =  1;

  if (store_name == "")
    store_name = possessive(name) + " Shop";
}

void mobdesc::add_target(string str)

{
  num_targets++;
  string *temp = new string [num_targets];

  for (int i=0; i<num_targets-1; i++)
    temp[i] = target[i];

  temp[num_targets-1] = str;

  target = temp;
}

void mobdesc::add_skill(string skl, int frq)

{
  num_skills++;
  string *temp1 = new string [num_skills];
  int *temp2 = new int [num_skills];

  for (int i=0; i<num_skills-1; i++) {
    temp1[i] = skill[i];
    temp2[i] = skillf[i]; }

  temp1[num_skills-1] = skl;
  temp2[num_skills-1] = frq;

  skill = temp1;
  skillf = temp2;
}

void mobdesc::add_spell(string spl, int frq)

{
  num_spells++;
  string *temp1 = new string [num_spells];
  int *temp2 = new int [num_spells];

  for (int i=0; i<num_spells-1; i++) {
    temp1[i] = spell[i];
    temp2[i] = spellf[i]; }

  temp1[num_spells-1] = spl;
  temp2[num_spells-1] = frq;

  spell = temp1;
  spellf = temp2;
}

void mobdesc::add_action(string akt, int frq)

{
  num_actions++;
  string *temp1 = new string [num_actions];
  int *temp2 = new int [num_actions];

  for (int i=0; i<num_actions-1; i++) {
    temp1[i] = ACT[i];
    temp2[i] = ACTF[i]; }

  temp1[num_actions-1] = akt;
  temp2[num_actions-1] = frq;

  ACT = temp1;
  ACTF = temp2;
}

void mobdesc::add_item(int itm, int frq)

{
  num_items++;
  int *temp1 = new int [num_items];
  int *temp2 = new int [num_items];

  for (int i=0; i<num_items-1; i++) {
    temp1[i] = Items[i];
    temp2[i] = Item_Freq[i]; }

  temp1[num_items-1] = itm;
  temp2[num_items-1] = frq;

  Items = temp1;
  Item_Freq = temp2;
}

void mobdesc::add_wear(int itm, int frq)

{
  num_wears++;
  int *temp1 = new int [num_wears];
  int *temp2 = new int [num_wears];

  for (int i=0; i<num_wears-1; i++) {
    temp1[i] = Wears[i];
    temp2[i] = Wear_Freq[i]; }

  temp1[num_wears-1] = itm;
  temp2[num_wears-1] = frq;

  Wears = temp1;
  Wear_Freq = temp2;
}

void mobdesc::normalize_frequency()

{
  int total = 0;

  for (int i=0; i<num_actions-1; i++)
  for (int j=0; j<num_actions-1; j++)
  if (ACTF[j] > ACTF[j+1])

  {
    int temp = ACTF[j];
    ACTF[j] = ACTF[j+1];
    ACTF[j+1] = temp;

    string temp2 = ACT[j];
    ACT[j] = ACT[j+1];
    ACT[j+1] = temp2;
  }

  for (int i=0; i<num_actions; i++)

  {
    ACTF[i] += total;
    total += ACTF[i];
  }
}

int mobdesc::check_targets(string str)

{
  for (int i=0; i<num_targets; i++)
    if (target[i] == str)
      return 1;

  return 0;
}