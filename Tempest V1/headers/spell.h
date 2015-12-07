#ifndef SPELL_H
#define SPELL_H

class spell

{
  public:
    string name;
    string desc;
    string prereq;
    string classes;
    int manacost;
    int targeted;
    int id;
    int level;
    int learned;
    int learn_rate;
    int importance;
    int times_used;
    int prereq_lev;
    int type;
    int STR, DEX, CON, INT, WIS;
};

#endif