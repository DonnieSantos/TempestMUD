#ifndef CHARACTER_H
#define CHARACTER_H
#include "entity.h"

class character: public entity

{
  public:
    character();
    ~character() { }
    virtual int get_type();
    virtual int get_playerstate();
    virtual string get_lname();
    virtual string get_prename();
    virtual string get_title();
    virtual string get_class();
    virtual string get_sclass();
    virtual string get_ssclass();
    virtual string get_clan();
    virtual string get_religion();
    virtual int get_natural_hp();
    virtual int get_natural_mana();
    virtual int get_natural_move();
    virtual int get_max_hp();
    virtual int get_current_mana();
    virtual int get_max_mana();
    virtual int get_current_move();
    virtual int get_max_move();
    virtual int get_experience();
    virtual int get_exp_needed();
    virtual int get_last_experience();
    virtual string get_password();
    virtual string get_hometown();
    virtual int get_STR();
    virtual int get_DEX();
    virtual int get_CON();
    virtual int get_INT();
    virtual int get_WIS();
    virtual int get_HR();
    virtual int get_DR();
    virtual int get_AC();
    virtual int get_MR();
    virtual int get_gold();
    virtual int get_dpoints();
    virtual int get_sanc();
    virtual void set_playerstate(int);
    virtual void set_level(int);
    virtual void set_current_hp(int);
    virtual void set_max_hp(int);
    virtual void set_current_mana(int);
    virtual void set_max_mana(int);
    virtual void set_current_move(int);
    virtual void set_max_move(int);
    virtual void set_experience(int);
    virtual void set_password(string);
    virtual void set_hometown(string);
    virtual void set_class(string);
    virtual void set_clan(string);
    virtual void set_religion(string);
    virtual void set_exp_needed();
    virtual void set_STR(int);
    virtual void set_DEX(int);
    virtual void set_CON(int);
    virtual void set_INT(int);
    virtual void set_WIS(int);
    virtual void set_gold(int);
    virtual void set_dpoints(int);
    virtual void toggle_follow();
    virtual void toggle_assist();
    virtual void set_followable(int);
    virtual void set_autoassist(int);
    virtual void set_desc(string);
    virtual void set_plan(string);
    virtual void set_title(string, int);
    virtual void set_prename(string, int);
    virtual void level_up();
    virtual void level_up(int);
    virtual void add(string);
    virtual void set_name(string);
    virtual int check_targets(string);
    virtual int get_level();
    virtual string get_desc();
    virtual string get_plan();
    virtual int get_followable();
    virtual int get_autoassist();
    virtual string stat();
    virtual void restore();
    virtual void regen();
    virtual void die(int);
    virtual void receive_experience(entity*);
    virtual void gain_experience(int);
    virtual void no_experience();
    virtual mobdesc* get_info();
    virtual void examine_say(string);
    virtual item* merchant_find_item(int, string);
    virtual void merchant_sell_item(item*, entity*);
    virtual void merchant_buy_item(item*, entity*);
    virtual string merchant_item_list();
    virtual blocker* get_blocker();


  private:
    string lname, prename;
    string password, hometown;
    int level, D_Points;
    int max_hp;
    int current_mana, max_mana;
    int current_move, max_move;
    int experience, exp_needed;
    int last_experience;
    int STR, DEX, CON, INT, WIS;
    string profession, sclass;
    string title;
    string desc, plan;
    string clan_name;
    string religion_name;
    int HP_MULT, MN_MULT, MV_MULT;
    int FOLLOWABLE;
    int AUTOASSIST;
};

#endif