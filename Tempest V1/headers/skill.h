#ifndef SKILL_H
#define SKILL_H

class skill

{
  public:
    string name;
    string desc;
    string prereq;
    string classes;
    int id;
    int level;
    int learned;
    int learn_rate;
    int times_used;
    int importance;
    int prereq_lev;
    int STR, DEX, CON, INT, WIS;
};

#endif