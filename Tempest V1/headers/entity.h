#ifndef ENTITY_H
#define ENTITY_H
#include "skills.h"
#include "spells.h"

class entity

{
  public:
    entity() { }
    ~entity() { }

    // *************************************************************** //
    // ********************** Public Variables *********************** //
    // *************************************************************** //

    int PLAYER;
    alias_list *Aliases;
    skills skill_list;
    spells spell_list;

    // *************************************************************** //
    // ********************** Public Functions *********************** //
    // *************************************************************** //

    void stat(string);
    void set_HR(int);
    void set_DR(int);
    void set_AC(int);
    void set_MR(int);
    void set_room(int);
    void set_room(room*);
    void set_client(client*);
    void set_gender(string);
    void set_playerstate(int);
    void lock_commands(int);
    void save();
    void quit();
    room* get_room()                   { return rm;                 }
    void backup_room()                 { broom = rm;                }
    void restore_room()                { rm = broom;                }
    client* get_client()               { return my_client;          }
    string get_name()                  { return name;               }
    int get_current_hp()               { return current_hp;         }
    string get_gender()                { return gender;             }
    string get_He()                    { return He;                 }
    string get_he()                    { return he;                 }
    string get_His()                   { return His;                }
    string get_his()                   { return his;                }
    string get_Him()                   { return Him;                }
    string get_him()                   { return him;                }
    string get_Himself()               { return Himself;            }
    string get_himself()               { return himself;            }
    effects* get_effects()             { return my_effects;         }
    schedule* get_calendar()           { return Calendar;           }
    legend* get_legend()               { return Legend;             }
    int get_playerstate()              { return playerstate;        }
    int get_num_followers()            { return num_followers;      }
    entity* get_following()            { return following;          }
    entity* get_tracking()             { return tracking;           }
    string get_tracking_dir()          { return tracking_dir;       }
    entity* get_target()               { return combat_target;      }
    group* get_group()                 { return my_group;           }
    int get_inventory_size()           { return num_items;          }
    int get_equip_size()               { return num_items_equipped; }
    int get_max_items()                { return MAX_NUM_ITEMS;      }
    int get_intermittent_skill()       { return intermittent_skill; }
    int get_silent_cast()              { return silent_cast;        }
    item* get_equipped(int i)          { return *ITEMS_EQUIPPED[i]; }
    void set_silent_cast(int);
    void set_intermittent_skill(int);
    void set_student(string);
    void set_student_request(string);
    void write_note(string);
    void write_other(int);
    void set_target(entity*);
    entity* remove_target(entity*);
    int targeting_group(group*);
    void clear_targets();
    void set_tracking(entity*);
    void set_tracking_dir(string);
    void stop_tracking();
    entity* get_follower(int);
    void add_follower(entity*);
    void remove_follower(entity*);
    void remove_follower(int);
    void follow(string, int);
    void stop_following();
    void ditch(string);
    void genderset();
    void look(string);
    void look_at(string);
    void read(string);
    void remove(string);
    void say(string);
    void respond(string);
    void chat(string);
    void tell(string, string);
    void ctell(string);
    string hitpoint_meter();
    string try_move(string);
    int move(string);
    void speedwalk(string);
    void display_skills();
    void display_spells();
    void display_all_skills();
    void display_all_spells();
    void teach(string, string);
    void learn(string);
    void unlearn(string);
    entity* group_all(entity*, cmdqueue*);
    void engage(entity*, int);
    int can_attack(entity*);
    int test_damage(entity*);
    int test_hit(entity*);
    void flee();
    void quickattack(entity*);
    void manual_assist(string);
    void stop_fighting();
    void open_blocker(string);
    void close_blocker(string);
    void ghost_action(string);

    // *************************************************************** //
    // ********************** ITEMS & EQUIPMENT ********************** //
    // *************************************************************** //

    void equip(string);
    void unequip(string);
    int equip(item*, int);
    int unequip(item*, int);
    void equip_all(string);
    void unequip_all(string);
    void equip_direct(item*, string);
    string get_places(item*);
    string display_equipment(int);
    item* find_item_equipment(string);

    void use(string);
    void empty(string);
    void refill(string);
    void eat(string);
    void drink(string);
    void recite(string);
    void put(string);
    void get(string);
    int refers_room(string);
    void get_all_container(string);
    void drop(string);
    void give(string);
    void buy_item(string);
    void sell_item(string);
    void sacrifice_item(string);
    int sacrifice_item(item*);

    void play(string);
    void pull(string);

    item* create_gold_pile(int);
    void gold_give(string);
    void gold_drop(string);
    void display_inventory();
    void display_item_container(string);
    int can_add_inventory(item*);
    int can_drop_room(item*);
    int can_add_inventory_get(item*);
    void create_give_output(int, item*, entity*, string*, string*, string*);
    void create_room_drop_output(int, item*, string*, string*);
    void create_container_put_output(int, item*, item*, string*, string*);
    void create_container_get_output(int, item*, item*, string*, string*);
    void add_item_inventory(item*);
    void remove_item_inventory(item*);
    item* find_item_inventory(string);
    void append_item_inventory(item*);
    void insert_item_inventory(item*, int);
    string stack_items(item**, int, int);
    int get_total_items();
    void num_item_info();
    void create_corpse();
    void pfile_inventory_traverse(item**, int, string*, int, int);
    string pfile_item_information();
    string pfile_equipment_information();
    string deposit(string);
    string withdraw(string);
    string deposit_gold(string);
    string withdraw_gold(string);
    item* find_item_bank(string);
    void add_item_bank(item*);
    void remove_item_bank(item*);
    void display_bank_account();
    item* extensive_search(string*);
    item* find_container(string);
    item* find_item_all(string);
    void perish_itemlist(item**, int, item**, int*);

    // *************************************************************** //
    // ********************* COMMAND INTERPRETER ********************* //
    // *************************************************************** //

    void interpret_command(string);
    void process_multiple_commands(string*);

    // *************************************************************** //
    // ******************** TERMINATION FUNCTIONS ******************** //
    // *************************************************************** //

    void Quit();
    void Drop();
    void Purge(entity*);
    void Death(int);

    // *************************************************************** //
    // ********************** VIRTUAL FUNCTIONS ********************** //
    // *************************************************************** //

    virtual int get_type() = 0;
    virtual void examine_say(string) = 0;
    virtual mobdesc* get_info() = 0;
    virtual string get_lname() = 0;
    virtual string get_prename() = 0;
    virtual string get_title() = 0;
    virtual string get_class() = 0;
    virtual string get_sclass() = 0;
    virtual string get_ssclass() = 0;
    virtual string get_clan() = 0;
    virtual string get_religion() = 0;
    virtual int get_natural_hp() = 0;
    virtual int get_natural_mana() = 0;
    virtual int get_natural_move() = 0;
    virtual int get_max_hp() = 0;
    virtual int get_current_mana() = 0;
    virtual int get_max_mana() = 0;
    virtual int get_current_move() = 0;
    virtual int get_max_move() = 0;
    virtual int get_experience() = 0;
    virtual int get_exp_needed() = 0;
    virtual int get_last_experience() = 0;
    virtual string get_password() = 0;
    virtual string get_hometown() = 0;
    virtual int get_STR() = 0;
    virtual int get_DEX() = 0;
    virtual int get_CON() = 0;
    virtual int get_INT() = 0;
    virtual int get_WIS() = 0;
    virtual int get_HR() = 0;
    virtual int get_DR() = 0;
    virtual int get_AC() = 0;
    virtual int get_MR() = 0;
    virtual int get_gold() = 0;
    virtual int get_dpoints() = 0;
    virtual int get_sanc() = 0;
    virtual void set_desc(string) = 0;
    virtual void set_plan(string) = 0;
    virtual void set_title(string, int) = 0;
    virtual void set_prename(string, int) = 0;
    virtual void level_up() = 0;
    virtual void level_up(int) = 0;
    virtual void add(string) = 0;
    virtual void set_name(string) = 0;
    virtual int check_targets(string) = 0;
    virtual int get_level() = 0;
    virtual string get_desc() = 0;
    virtual string get_plan() = 0;
    virtual int get_followable() = 0;
    virtual int get_autoassist() = 0;
    virtual string stat() = 0;
    virtual void restore() = 0;
    virtual void regen() = 0;
    virtual void set_level(int) = 0;
    virtual void set_current_hp(int) = 0;
    virtual void set_max_hp(int) = 0;
    virtual void set_current_mana(int) = 0;
    virtual void set_max_mana(int) = 0;
    virtual void set_current_move(int) = 0;
    virtual void set_max_move(int) = 0;
    virtual void set_experience(int) = 0;
    virtual void set_password(string) = 0;
    virtual void set_hometown(string) = 0;
    virtual void set_class(string) = 0;
    virtual void set_clan(string) = 0;
    virtual void set_religion(string) = 0;
    virtual void set_exp_needed() = 0;
    virtual void set_STR(int) = 0;
    virtual void set_DEX(int) = 0;
    virtual void set_CON(int) = 0;
    virtual void set_INT(int) = 0;
    virtual void set_WIS(int) = 0;
    virtual void set_gold(int) = 0;
    virtual void set_dpoints(int) = 0;
    virtual void toggle_follow() = 0;
    virtual void toggle_assist() = 0;
    virtual void set_followable(int) = 0;
    virtual void set_autoassist(int) = 0;
    virtual void receive_experience(entity*) = 0;
    virtual void gain_experience(int) = 0;
    virtual void no_experience() = 0;
    virtual void die(int) = 0;
    virtual item* merchant_find_item(int, string) = 0;
    virtual void merchant_sell_item(item*, entity*) = 0;
    virtual void merchant_buy_item(item*, entity*) = 0;
    virtual string merchant_item_list() = 0;
    virtual blocker* get_blocker() = 0;

    // *************************************************************** //
    // ********************* MESSAGING FUNCTIONS ********************* //
    // *************************************************************** //

    void echo(string);
    void echo(string, string);
    void vdist_echo(entity*, string, string);
    void gecho(string);
    void gecho(string, string);
    void emote(string);
    void blind_emote(string);
    void xblind_emote(entity*, string);
    void xblind_emote(entity*, entity*, string);
    void pblind_emote(string);
    void add_string(string);
    void echo_strings();
    void flush_strings();

    // *************************************************************** //
    // *************************** SOCIALS *************************** //
    // *************************************************************** //

    void blink();   void blink(string);
    void nod();     void nod(string);
    void laugh();   void laugh(string);
    void cackle();  void cackle(string);
    void shake();   void shake(string);
    void smile();   void smile(string);
    void grin();    void grin(string);
    void sigh();    void sigh(string);
    void shrug();   void shrug(string);
    void peer();    void peer(string);
    void frown();   void frown(string);
    void growl();   void growl(string);
    void snarl();   void snarl(string);
    void tip();     void tip(string);
    void giggle();  void giggle(string);

    // *************************************************************** //
    // **************************** CLANS **************************** //
    // *************************************************************** //

    void update_claninfo();
    void clan_command(string);
    void clan_apply(string);
    void clan_enlist(string);
    void clan_expel(string);
    void clan_raise(string);
    void clan_demote(string);
    void clan_resign(string);

    // *************************************************************** //
    // *************************** RELIGION ************************** //
    // *************************************************************** //

    void update_religioninfo();
    void religion_command(string);
    void religion_apply(string);
    void religion_admit(string);
    void religion_excommunicate(string);
    void religion_raise(string);
    void religion_demote(string);
    void religion_forsake(string);

    // *************************************************************** //
    // ********************** IMMORTAL COMMANDS ********************** //
    // *************************************************************** //

    void go_to(int);
    void go_to(string);
    void trans(string);
    void at(string, string);
    void rlist(string);
    void rlist(int);
    void rlist(int, int);
    void mlist(string);
    void mlist(int);
    void mlist(int, int);
    void ilist(string);
    void ilist(int);
    void ilist(int, int);
    void zlist(string);
    void zlist(int);
    void zlist(int, int);
    void imm_restore(string);
    void force(string, string);
    void possess(string);
    void unpossess(int);
    void slay(string);
    void zrepop(int);
    void zclear(int);
    void load(string);
    void bestow(string);
    void purge(string);
    void mark_legend(string, string);
    void delete_character(string);
    void disintegrate(string);
    void freeze(string, int);
    void dc(string);

    // *************************************************************** //
    // *************************** SKILLS **************************** //
    // *************************************************************** //

    int damage_skill(int, int, entity*, attackinfo, attackinfo);
    attackinfo attack(entity*);
    string track(string);
    string track(entity*);
    void scout();
    void death_blossom();
    void death_blossom_single();
    void stab_and_twist(string);
    void assassin_strike(string);

    // *************************************************************** //
    // *************************** SPELLS **************************** //
    // *************************************************************** //

    void cast(string);
    void damage_cast(int, int, entity*, attackinfo, attackinfo);
    void area_damage_cast(int, int, string, string);
    void support_cast(int, int, entity*, attackinfo);

    void item_cast(string, string);
    void item_support_cast(int, int, entity*, attackinfo);
    void item_damage_cast(int, int, entity*, attackinfo, attackinfo);
    void item_area_damage_cast(int, int);
    int item_cast_check(string, string);
    int item_damage_check(entity*);
    int item_area_damage_check();

    void gem_missile(entity*);
    void lightning_bolt(entity*);
    void fireball(entity*);
    void flash();
    void burst();
    void cure_light(entity*);
    void sanctuary(entity*);
    void shield(entity*);
    void identify(item*);

    // *************************************************************** //
    // *************************************************************** //
    // *************************************************************** //

  protected:
    string name;
    mobdesc *info;
    room *rm, *broom;
    client *my_client;
    string he, his, himself, him;
    string He, His, Himself, Him;
    string tracking_dir;
    entity *tracking;
    entity* *followers;
    entity *following;
    entity *combat_target;
    entity* *target_list;
    int num_targets;
    int num_followers;
    string gender;
    int current_hp;
    string string_stack;
    group *my_group;
    effects *my_effects;
    schedule *Calendar;
    legend *Legend;
    int playerstate;
    int EQ_HP, EQ_MN, EQ_MV;
    int HR, DR, AC, MR, gold;
    int intermittent_skill;
    int silent_cast;
    string my_student;
    string my_student_request;

    int num_items;
    int num_items_equipped;
    int num_items_container;
    item* Item_List[MAX_NUM_ITEMS];

    int bank_gold;
    int bank_num_items;
    item* *bank_item_list;

    item **ITEMS_EQUIPPED[16];
    item *Right_Hand;
    item *Left_Hand;
    item *Body;
    item *Head;
    item *Feet;
    item *Back;
    item *Hands;
    item *Waist;
    item *Legs;
    item *Right_Ear;
    item *Left_Ear;
    item *Right_Wrist;
    item *Left_Wrist;
    item *Right_Finger;
    item *Left_Finger;
    item *Neck;
};

#endif