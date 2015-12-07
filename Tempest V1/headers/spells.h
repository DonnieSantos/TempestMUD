#ifndef SPELLS_H
#define SPELLS_H
#define NUM_SPELLS 9
#include "spell.h"

class spells

{
  public:
    spells();
    int get_position(int);
    void learn(string, int, int);
    void learn(int, int, int);
    void unlearn(int);
    int train(int);
    int size();

    spell &operator[](int n)         { return list[get_position(n)]; }
    spell* get_by_importance(int n)  { return &list[n]; }
    spell* get(string);

  private:
    spell list[NUM_SPELLS+1];
    int SIZE;
};

// ******************************************************************************* //
// ********************************* SPELL LIST ********************************** //
// ******************************************************************************* //

spells::spells()

{
  SIZE = NUM_SPELLS;

  for (int i=1; i<=SIZE; i++) {
    list[i].id = i;
    list[i].level = 0;
    list[i].learned = 0;
    list[i].times_used = 0;
    list[i].prereq = "";
    list[i].prereq_lev = 0;
    list[i].STR = 0;
    list[i].DEX = 0;
    list[i].CON = 0;
    list[i].INT = 0;
    list[i].WIS = 0;
    list[i].type = 0; }

  list[1].name = "Gem Missile";
  list[1].desc = "Assail your enemy with a magical crystalline shard.";
  list[1].classes = "Mg Wi Sc Wl";
  list[1].type = SPELL_DAMAGE;
  list[1].manacost = 10;
  list[1].targeted = 1;
  list[1].learn_rate = 5;
  list[1].importance = 5;
  list[1].DEX = 5;
  list[1].INT = 10;

  list[2].name = "Cure Light";
  list[2].desc = "Calls upon the holy light to restore vitality.";
  list[2].classes = "Cl Dr Mo Pr Hl Kn Ra Sc";
  list[2].type = SPELL_SUPPORT;
  list[2].manacost = 50;
  list[2].targeted = 1;
  list[2].learn_rate = 5;
  list[2].importance = 15;
  list[2].INT = 50;
  list[2].WIS = 50;

  list[3].name = "Flash";
  list[3].desc = "Blinding light damages all nearby enemies.";
  list[3].classes = "Mg Wi Il Sc Dm Wl Sh";
  list[3].type = SPELL_AREA_DAMAGE;
  list[3].manacost = 30;
  list[3].targeted = 0;
  list[3].learn_rate = 5;
  list[3].importance = 10;
  list[3].INT = 75;
  list[3].WIS = 40;

  list[4].name = "Sanctuary";
  list[4].desc = "Divine protection against all damage.";
  list[4].classes = "Kn Pa Cl Mo Dr Pr Sc";
  list[4].type = SPELL_SUPPORT;
  list[4].manacost = 50;
  list[4].targeted = 1;
  list[4].learn_rate = 5;
  list[4].importance = 25;
  list[4].DEX = 40;
  list[4].INT = 85;
  list[4].WIS = 85;

  list[5].name = "Shield";
  list[5].desc = "A mystical shield protects from physical damage.";
  list[5].classes = "Kn Pa Ap Cl Dr Mo Pr Dc Sc";
  list[5].type = SPELL_SUPPORT;
  list[5].manacost = 20;
  list[5].targeted = 1;
  list[5].learn_rate = 5;
  list[5].importance = 20;
  list[5].STR = 30;
  list[5].DEX = 20;
  list[5].INT = 45;
  list[5].WIS = 45;

  list[6].name = "Identify";
  list[6].desc = "Identify an item.";
  list[6].classes = "All";
  list[6].type = SPELL_UNIQUE;
  list[6].manacost = 50;
  list[6].targeted = 2;
  list[6].learn_rate = 5;
  list[6].importance = 100;
  list[6].DEX = 50;
  list[6].INT = 50;
  list[6].WIS = 50;

  list[7].name = "Fireball";
  list[7].desc = "Hurl a deadly ball of fire to incinerate your enemy.";
  list[7].classes = "Mg Wi Sc Wl";
  list[7].type = SPELL_DAMAGE;
  list[7].manacost = 75;
  list[7].targeted = 1;
  list[7].learn_rate = 5;
  list[7].importance = 55;
  list[7].INT = 0;
  list[7].WIS = 0;

  list[8].name = "Lightning Bolt";
  list[8].desc = "Strike your enemy with a writhing bolt of electricity.";
  list[8].classes = "Mg Wi Sc Wl";
  list[8].type = SPELL_DAMAGE;
  list[8].manacost = 40;
  list[8].targeted = 1;
  list[8].learn_rate = 5;
  list[8].importance = 50;
  list[8].INT = 0;
  list[8].WIS = 0;

  list[9].name = "Burst";
  list[9].desc = "Unleash a barrage of energy bombs to devastate the area.";
  list[9].classes = "Wi Sc Wl";
  list[9].type = SPELL_AREA_DAMAGE;
  list[9].manacost = 100;
  list[9].targeted = 0;
  list[9].learn_rate = 5;
  list[9].importance = 150;
  list[9].INT = 0;
  list[9].WIS = 0;
}

// ******************************************************************************* //
// ********************************* SPELL LIST ********************************** //
// ******************************************************************************* //

string lowercase(string);

void spells::learn(string Name, int LEVEL, int TU)

{
  for (int i=1; i<=SIZE; i++)
  if (lowercase(list[i].name) == lowercase(Name)) {
    list[i].learned = 1;
    list[i].level = LEVEL;
    list[i].times_used = TU;
    break; }
}

void spells::learn(int ID, int LEVEL, int TU)

{
  ID = get_position(ID);

  if (ID) {
    list[ID].learned = 1;
    list[ID].level = LEVEL;
    list[ID].times_used = TU; }
}

void spells::unlearn(int ID)

{
  ID = get_position(ID);

  if (ID) {
    list[ID].learned = 0;
    list[ID].level = 0;
    list[ID].times_used = 0; }
}

int spells::train(int ID)

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

spell* spells::get(string name)

{
  for (int i=1; i<=SIZE; i++)
    if (lowercase(list[i].name) == lowercase(name))
      return &list[i];

  return NULL;
}

int spells::get_position(int id)

{
  for (int i=1; i<=SIZE; i++)
    if (list[i].id == id)
      return i;

  return 0;
}

int spells::size()

{
  return SIZE;
}

#endif