#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

effects::effects(entity *Self)

{
  self = Self;

  SANC   = 0;
  SHIELD = 0;
}

void effects::renew(string str)

{
  if (str == "Sanctuary")

  {
    if (SANC == 0) {
      self->set_AC(self->get_AC()+100);
      self->set_MR(self->get_MR()+20); }

    if (SANC != -1)
      SANC = 5;
  }

  else if (str == "Shield")

  {
    if (SHIELD == 0)
      self->set_AC(self->get_AC()+30);

    if (SHIELD != -1)
      SHIELD = 10;
  }
}

void effects::renew(string str, int duration)

{
  if (str == "Sanctuary")

  {
    if ((SANC == 0) && (duration > 0)) {
      self->set_AC(self->get_AC()+100);
      self->set_MR(self->get_MR()+20); }

    SANC = duration;
  }

  else if (str == "Shield")

  {
    if ((SHIELD == 0) && (duration > 0))
      self->set_AC(self->get_AC()+30);

    SHIELD = duration;
  }
}

string effects::decrement()

{
  string tempstr = "";

  if (SANC > 0)

  {
    SANC--;

    if (SANC == 0)
      tempstr += "\r\n" + remove_effect("Sanctuary");
  }

  if (SHIELD > 0)

  {
    SHIELD--;

    if (SHIELD == 0)
      tempstr += "\r\n" + remove_effect("Shield");
  }

  if (tempstr.length() > 0) tempstr.erase(0,2);

  return tempstr;
}

string effects::remove_effect(string spell_name)

{
  if (spell_name == "Sanctuary") {
    SANC = 0;
    self->set_AC(self->get_AC()-100);
    self->set_MR(self->get_MR()-20);
    return "You feel vulnerable."; }

  if (spell_name == "Shield") {
    SHIELD = 0;
    self->set_AC(self->get_AC()-30);
    return "You feel less armored."; }

  return "";
}

int effects::get_ticks(int n)

{
  if (n == 0)  return SANC;
  if (n == 1)  return SHIELD;

  return 0;
}

int effects::get_ticks(string str)

{
  if (str == "Sanctuary")  return SANC;
  if (str == "Shield")     return SHIELD;

  return 0;
}

int effects::get_num_effects()

{
  int count = 0;

  if (SANC != 0)    count++;
  if (SHIELD != 0)  count++;

  return count;
}

string effects::get_pfile_info()

{
  string tempstr = "";

  if (SANC > 1)    tempstr  +=  "AFFECTED_BY: Sanctuary "  +  intconvert(SANC-1)    +  "\n";
  if (SHIELD > 1)  tempstr  +=  "AFFECTED_BY: Shield "     +  intconvert(SHIELD-1)  +  "\n";

  return tempstr;
}

string effects::get_stat_info()

{
  int n = get_num_effects();
  string num = intconvert(n);

  if (n < 10) num = "0" + num;
  if (n == 0) num = "00";

  if ((SANC > 0) && (SANC < 10))            num += "[#yS#N]   " + intconvert(SANC);
  else if (SANC == -1)                      num += "[#yS#N]   A";
  else if ((SANC > 0) && (SANC >= 10))      num += "[#yS#N]  "  + intconvert(SANC);

  if ((SHIELD > 0) && (SHIELD < 10))        num += "[#CH#N]   " + intconvert(SHIELD);
  if (SHIELD == -1)                         num += "[#CH#N]   A";
  else if ((SHIELD > 0) && (SHIELD >= 10))  num += "[#CH#N]  "  + intconvert(SHIELD);

  int checksize = (int)num.size();

  if (checksize != ((11*n)+2)) return "00";

  cout << num << endl;

  return num;
}