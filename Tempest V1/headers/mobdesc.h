#ifndef MOBDESC_H
#define MOBDESC_H

class mobdesc

{
  public:
    mobdesc() { }
    mobdesc(int);
    ~mobdesc() { }
    void add_flag(string);
    void add_target(string);
    void add_skill(string, int);
    void add_spell(string, int);
    void add_action(string, int);
    void add_item(int, int);
    void add_wear(int, int);
    void add_item_sale(int);
    void normalize_frequency();
    int check_targets(string);

    int vnum;
    int zone_id;
    int level, HP, MN, AC, MR, HR, DR;
    int STR, DEX, CON, INT, WIS;
    int exp, gold, allign, speed;
    int MOBILE, AGGRO, BARD, NPC, SANCD, MERCHANT, BANKER;
    int SKILLMASTER, SPELLMASTER;
    int num_targets;
    int num_actions, *ACTF;
    int num_skills, *skillf;
    int num_spells, *spellf;
    int num_items, num_wears;
    int *Items, *Item_Freq;
    int *Wears, *Wear_Freq;
    int merchant_list[100];
    int merchant_listsize;
    string *skill, *spell;
    string store_name;
    string gender;
    string name;
    string title;
    string desc;
    string *target;
    string *ACT;
    string btype;
    char bdir;
};

#endif