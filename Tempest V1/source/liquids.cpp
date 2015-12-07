#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

void liquids::drink_liquid(entity *drinker, string liquid_name, string container_name)

{
  ENT = drinker;
  RM = drinker->get_room();
  cname = container_name + "#N";
  lname = lowercase(liquid_name);

  if (lname == "water") drink_water();
  else if (lname == "curatio") drink_curatio();
  else if (lname == "panacea") drink_panacea();
  else drink_unknown();
}

string look_liquid(string liquid_name)

{
  liquid_name = lowercase(liquid_name);

  if (liquid_name == "water") return "clear water";
  else if (liquid_name == "curatio") return "blue liquid";
  else if (liquid_name == "panacea") return "green liquid";

  return "liquid";
}

string get_liquid_amount(item *dc)

{
  float percentage;
  string lt = look_liquid(dc->liquid_type);
  string temp = capital_lname(dc->lname) + " is ";

  if (dc->max_uses == 0) return (temp + "empty.");

  percentage = ((float)((float)dc->uses / (float)dc->max_uses));
  percentage = percentage * 100;

  if (percentage <= 0) return (temp + "empty.");
  if (percentage == 100) return (temp + "full of " + lt + ".");

  if ((dc->uses == 1) && (dc->max_uses > 2)) return (temp + "nearly empty.");
  if ((dc->uses == dc->max_uses-1) && (dc->max_uses > 2)) return (temp + "nearly full of " + lt + ".");

  if ((percentage >= 0) && (percentage <= 20)) return temp + "nearly empty.";
  else if ((percentage >= 20) && (percentage < 40)) return (temp + "less than half full of " + lt + ".");
  else if ((percentage >= 40) && (percentage < 60)) return (temp + "about half full of " + lt + ".");
  else if ((percentage >= 60) && (percentage < 80)) return (temp + "more than half full of " + lt + ".");
  else if ((percentage >= 80) && (percentage < 100)) return (temp + "nearly full of " + lt + ".");

  return (temp + "empty.");
}

void liquids::standard_room_echo()

{
  RM->xecho(ENT, ENT->get_name() + " drinks from " + cname + ".");
}

void liquids::standard_echo(string dname)

{
  ENT->echo("You drink " + dname + " from " + cname + ".");
}

void liquids::standard_echo(string dname, string second_line)

{
  ENT->echo("You drink " + dname + " from " + cname + ".\r\n" + second_line);
}

void liquids::drink_unknown()

{
  standard_echo("something");
  standard_room_echo();
}

void liquids::drink_water()

{
  standard_echo("water", "Your thirst is quenched.");
  standard_room_echo();
}

void liquids::drink_curatio()

{
  standard_echo("a blue potion");
  ENT->restore();
  standard_room_echo();
}

void liquids::drink_panacea()

{
  standard_echo("a bright green potion");
  ENT->restore();
  standard_room_echo();
}