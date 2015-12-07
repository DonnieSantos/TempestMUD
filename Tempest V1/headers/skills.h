#ifndef SKILLS_H
#define SKILLS_H
#define NUM_SKILLS 26
#include "skill.h"

class skills

{
  public:
    skills();
    int get_position(int);
    void learn(string, int, int);
    void learn(int, int, int);
    void unlearn(int);
    int train(int);
    int size();

    skill &operator[](int n)         { return list[get_position(n)]; }
    skill* get_by_importance(int n)  { return &list[n]; }
    skill* get(string);

  private:
    skill list[NUM_SKILLS+1];
    int SIZE;
};

// ******************************************************************************* //
// ********************************* SKILL LIST ********************************** //
// ******************************************************************************* //

skills::skills()

{
  SIZE = NUM_SKILLS;

  for (int i=1; i<=SIZE; i++) {
    list[i].id = i;
    list[i].level = 0;
    list[i].learned = 0;
    list[i].times_used = 0;
    list[i].prereq_lev = 0;
    list[i].prereq = "None";
    list[i].STR = 0;
    list[i].DEX = 0;
    list[i].CON = 0;
    list[i].INT = 0;
    list[i].WIS = 0; }

  list[1].name = "Scout";
  list[1].desc = "Scout the surrounding area.";
  list[1].classes = "All";
  list[1].learn_rate = 5;
  list[1].importance = 10;
  list[1].STR = 5;
  list[1].DEX = 5;
  list[1].CON = 5;
  list[1].INT = 5;
  list[1].WIS = 5;

  list[2].name = "Track";
  list[2].desc = "Track down a target.";
  list[2].classes = "As Ra Rg Th Sb";
  list[2].learn_rate = 5;
  list[2].importance = 20;
  list[2].prereq = "Scout";
  list[2].prereq_lev = 20;
  list[2].STR = 10;
  list[2].DEX = 10;
  list[2].CON = 10;
  list[2].INT = 10;
  list[2].WIS = 10;

  list[3].name = "Stealth";
  list[3].desc = "Move without being seen.";
  list[3].classes = "Th Rg As Sb Ra";
  list[3].learn_rate = 5;
  list[3].importance = 40;
  list[3].prereq = "Scout";
  list[3].prereq_lev = 40;
  list[3].DEX = 40;
  list[3].INT = 15;
  list[3].WIS = 5;

  list[4].name = "Death Blossom";
  list[4].desc = "Death Blossom massive damage close-range attack against multiple targets.";
  list[4].classes = "As Sb";
  list[4].learn_rate = 5;
  list[4].importance = 245;
  list[4].prereq = "Critical Hit";
  list[4].prereq_lev = 60;
  list[4].STR = 35;
  list[4].DEX = 150;
  list[4].CON = 35;

  list[5].name = "Hide";
  list[5].desc = "Attempt to remain hidden from sight.";
  list[5].classes = "As Ra Rg Th Sb";
  list[5].learn_rate = 5;
  list[5].importance = 30;
  list[5].prereq = "Scout";
  list[5].prereq_lev = 30;
  list[5].DEX = 30;
  list[5].INT = 10;

  list[6].name = "Dodge";
  list[6].desc = "Evade physical attacks.";
  list[6].classes = "As Ra Rg Th Sb Wa Mo";
  list[6].learn_rate = 5;
  list[6].importance = 50;
  list[6].DEX = 50;

  list[7].name = "Disarm";
  list[7].desc = "Send your enemy's weapon flying.";
  list[7].classes = "As Ra Rg Th Sb Wa Kn Cr Dk";
  list[7].learn_rate = 5;
  list[7].importance = 60;
  list[7].STR = 40;
  list[7].DEX = 50;
  list[7].CON = 20;
  list[7].INT = 10;

  list[8].name = "Decoy";
  list[8].desc = "Bait and switch, tricking your enemy into attacking a log decoy.";
  list[8].classes = "As Sb";
  list[8].learn_rate = 5;
  list[8].importance = 70;
  list[8].prereq = "Dodge";
  list[8].prereq_lev = 50;
  list[8].DEX = 100;
  list[8].INT = 30;
  list[8].WIS = 20;

  list[9].name = "Smoke Bomb";
  list[9].desc = "Escape safely from a dangerous situation.";
  list[9].classes = "As Sb Rg";
  list[9].learn_rate = 5;
  list[9].importance = 80;
  list[9].prereq = "Stealth";
  list[9].prereq_lev = 50;
  list[9].DEX = 120;
  list[9].INT = 20;
  list[9].WIS = 20;

  list[10].name = "Backstab";
  list[10].desc = "Bury a dagger deep into the back of your enemy.";
  list[10].classes = "As Rg Th Sb";
  list[10].learn_rate = 5;
  list[10].importance = 90;
  list[10].prereq = "Stab and Twist";
  list[10].prereq_lev = 20;
  list[10].STR = 15;
  list[10].DEX = 35;
  list[10].CON = 10;
  list[10].INT = 5;

  list[11].name = "Stab and Twist";
  list[11].desc = "Quickly thrust your dagger into your enemy and turn it viciously, tearing their flesh.";
  list[11].classes = "As Rg Th Sb";
  list[11].learn_rate = 5;
  list[11].importance = 100;
  list[11].STR = 7;
  list[11].DEX = 15;

  list[12].name = "Circle Around";
  list[12].desc = "Circle around your enemy and backstab.";
  list[12].classes = "As Th Sb";
  list[12].learn_rate = 5;
  list[12].importance = 120;
  list[12].prereq = "Backstab";
  list[12].prereq_lev = 30;
  list[12].STR = 10;
  list[12].DEX = 80;
  list[12].CON = 10;
  list[12].INT = 10;

  list[13].name = "Critical Hit";
  list[13].desc = "Strike your enemy with deadly critical blows.";
  list[13].classes = "As Th Sb";
  list[13].learn_rate = 5;
  list[13].importance = 110;
  list[13].prereq = "Stab and Twist";
  list[13].prereq_lev = 30;
  list[13].STR = 20;
  list[13].DEX = 50;
  list[13].CON = 20;

  // DART LORE NEEDS A PREREQUISITE [THROW:30]

  list[14].name = "Dart Lore";
  list[14].desc = "The ability to identify and appraise throwing weapons.";
  list[14].classes = "As Sb Rg Th Me";
  list[14].learn_rate = 5;
  list[14].importance = 130;
  list[14].INT = 20;
  list[14].WIS = 10;

  // WHIP LORE NEEDS A PREREQUISITE [WHIPLASH:30]

  list[15].name = "Whip Lore";
  list[15].desc = "The ability to identify and appraise whips and chains.";
  list[15].classes = "As Sb Rg Th Me Wa Mc";
  list[15].learn_rate = 5;
  list[15].importance = 140;
  list[15].INT = 20;
  list[15].WIS = 10;

  // BOW LORE NEEDS A PREREQUISITE [AIM:30]

  list[16].name = "Bow Lore";
  list[16].desc = "The ability to identify and appraise bows.";
  list[16].classes = "As Sb Rg Th Ra Wa Pa Mc Hl Cl Me";
  list[16].learn_rate = 5;
  list[16].importance = 160;
  list[16].INT = 20;
  list[16].WIS = 10;

  list[17].name = "Knife Lore";
  list[17].desc = "The ability to identify and appraise daggers and knives.";
  list[17].classes = "As Sb Rg Th Ra Mc Me";
  list[17].learn_rate = 5;
  list[17].importance = 150;
  list[17].prereq = "Stab and Twist";
  list[17].prereq_lev = 50;
  list[17].INT = 20;
  list[17].WIS = 10;

  list[18].name = "Knife Smith";
  list[18].desc = "Forge, sharpen and repair daggers and knives.";
  list[18].classes = "As Sb Rg Th Me";
  list[18].learn_rate = 5;
  list[18].importance = 170;
  list[18].prereq = "Knife Lore";
  list[18].prereq_lev = 50;
  list[18].CON = 40;
  list[18].INT = 30;
  list[18].WIS = 20;

  list[19].name = "Martial Arts";
  list[19].desc = "The ability to fight without the use of weapons.";
  list[19].classes = "As Sb Mo";
  list[19].learn_rate = 5;
  list[19].importance = 180;
  list[19].STR = 25;
  list[19].DEX = 25;
  list[19].CON = 20;
  list[19].INT = 10;
  list[19].WIS = 10;

  list[20].name = "Poison Strike";
  list[20].desc = "A mystic strike that unleashes venom upon your foe.";
  list[20].classes = "As";
  list[20].learn_rate = 5;
  list[20].importance = 190;
  list[20].prereq = "Martial Arts";
  list[20].prereq_lev = 30;
  list[20].STR = 20;
  list[20].DEX = 70;
  list[20].CON = 20;
  list[20].INT = 20;
  list[20].WIS = 20;

  list[21].name = "Blinding Strike";
  list[21].desc = "Viciously strike the eyes of your enemy, leaving him blind.";
  list[21].classes = "As";
  list[21].learn_rate = 5;
  list[21].importance = 200;
  list[21].prereq = "Poison Strike";
  list[21].prereq_lev = 30;
  list[21].STR = 25;
  list[21].DEX = 80;
  list[21].CON = 25;
  list[21].INT = 25;
  list[21].WIS = 20;

  list[22].name = "Lightning Strike";
  list[22].desc = "Unleash built up energy to lash out with a fearsome bolt of electricity.";
  list[22].classes = "As";
  list[22].learn_rate = 5;
  list[22].importance = 210;
  list[22].prereq = "Blinding Strike";
  list[22].prereq_lev = 40;
  list[22].STR = 30;
  list[22].DEX = 90;
  list[22].CON = 30;
  list[22].INT = 30;
  list[22].WIS = 20;

  list[23].name = "Tiger Strike";
  list[23].desc = "Ravage your enemy with a brutal attack.";
  list[23].classes = "As";
  list[23].learn_rate = 5;
  list[23].importance = 220;
  list[23].prereq = "Lightning Strike";
  list[23].prereq_lev = 50;
  list[23].STR = 55;
  list[23].DEX = 90;
  list[23].CON = 30;
  list[23].INT = 30;
  list[23].WIS = 20;

  list[24].name = "Heart Attack";
  list[24].desc = "Deadly strike to the very heart of your enemy.";
  list[24].classes = "As Sb";
  list[24].learn_rate = 5;
  list[24].importance = 240;
  list[24].prereq = "Circle Around";
  list[24].prereq_lev = 90;
  list[24].STR = 50;
  list[24].DEX = 200;
  list[24].CON = 50;
  list[24].INT = 10;
  list[24].WIS = 10;

  list[25].name = "Murder";
  list[25].desc = "Attempt to slay your enemy in a single hit.";
  list[25].classes = "As Sb";
  list[25].learn_rate = 5;
  list[25].importance = 250;
  list[25].prereq = "Heart Attack";
  list[25].prereq_lev = 100;
  list[25].STR = 150;
  list[25].DEX = 200;
  list[25].CON = 100;

  list[26].name = "Assassin Strike";
  list[26].desc = "Massive damage against a single enemy.";
  list[26].classes = "As Sb";
  list[26].learn_rate = 5;
  list[26].importance = 230;
  list[26].prereq = "Death Blossom";
  list[26].prereq_lev = 60;
  list[26].STR = 140;
  list[26].DEX = 190;
  list[26].CON = 90;

  int tempint;
  string tempstr;

  for (int b=1; b<SIZE; b++)
  for (int i=1; i<SIZE; i++)
  if (list[i].importance > list[i+1].importance)

  {
    tempstr = list[i].name;        list[i].name = list[i+1].name;              list[i+1].name = tempstr;
    tempstr = list[i].desc;        list[i].desc = list[i+1].desc;              list[i+1].desc = tempstr;
    tempstr = list[i].prereq;      list[i].prereq = list[i+1].prereq;          list[i+1].prereq = tempstr;
    tempstr = list[i].classes;     list[i].classes = list[i+1].classes;        list[i+1].classes = tempstr;
    tempint = list[i].id;          list[i].id = list[i+1].id;                  list[i+1].id = tempint;
    tempint = list[i].STR;         list[i].STR = list[i+1].STR;                list[i+1].STR = tempint;
    tempint = list[i].DEX;         list[i].DEX = list[i+1].DEX;                list[i+1].DEX = tempint;
    tempint = list[i].CON;         list[i].CON = list[i+1].CON;                list[i+1].CON = tempint;
    tempint = list[i].INT;         list[i].INT = list[i+1].INT;                list[i+1].INT = tempint;
    tempint = list[i].WIS;         list[i].WIS = list[i+1].WIS;                list[i+1].WIS = tempint;
    tempint = list[i].learn_rate;  list[i].learn_rate = list[i+1].learn_rate;  list[i+1].learn_rate = tempint;
    tempint = list[i].prereq_lev;  list[i].prereq_lev = list[i+1].prereq_lev;  list[i+1].prereq_lev = tempint;
    tempint = list[i].importance;  list[i].importance = list[i+1].importance;  list[i+1].importance = tempint;
  }
}

// ******************************************************************************* //
// ******************************************************************************* //
// ******************************************************************************* //

string lowercase(string);

void skills::learn(string Name, int LEVEL, int TU)

{
  for (int i=1; i<=SIZE; i++)
  if (lowercase(list[i].name) == lowercase(Name)) {
    list[i].learned = 1;
    list[i].level = LEVEL;
    list[i].times_used = TU;
    break; }
}

void skills::learn(int ID, int LEVEL, int TU)

{
  ID = get_position(ID);

  if (ID) {
    list[ID].learned = 1;
    list[ID].level = LEVEL;
    list[ID].times_used = TU; }
}

void skills::unlearn(int ID)

{
  ID = get_position(ID);

  if (ID) {
    list[ID].learned = 0;
    list[ID].level = 0;
    list[ID].times_used = 0; }
}

int skills::train(int ID)

{
  ID = get_position(ID);

  if (((list[ID].learned == 1) && (list[ID].level < 100)) && (ID))

  {
    int max = list[ID].learn_rate * ((list[ID].level / 20) + 1);

    list[ID].times_used++;

    if ((list[ID].times_used % max) == 0) {
      list[ID].level++;
      return 1; }
  }

  return 0;
}

skill* skills::get(string name)

{
  for (int i=1; i<=SIZE; i++)
    if (lowercase(list[i].name) == lowercase(name))
      return &list[i];

  return NULL;
}

int skills::get_position(int id)

{
  for (int i=1; i<=SIZE; i++)
    if (list[i].id == id)
      return i;

  return 0;
}

int skills::size()

{
  return SIZE;
}

#endif