#ifndef ROOM_H
#define ROOM_H
#include "windows.h"

class room

{
  public:
    room();
    ~room();
    room(int);
    void set_flag(string);
    void set_title(string);
    void set_desc(string, int);
    void add_exit(char, int);
    void add_look(string, string);
    void add_event(string, int);
    int search_looks(string);
    void set_mobexits();
    void consider_event();
    void normalize_frequency();
    int get_size()                              { return size;         }
    int get_id()                                { return id;           }
    string get_title()                          { return title;        }
    string get_desc()                           { return desc;         }
    int get_numlooks()                          { return num_looks;    }
    string get_look(int n)                      { return look[n];      }
    int get_N()                                 { return N;            }
    int get_S()                                 { return S;            }
    int get_E()                                 { return E;            }
    int get_W()                                 { return W;            }
    int get_U()                                 { return U;            }
    int get_D()                                 { return D;            }
    int occupied()                              { return num_people;   }
    int mob_occupied()                          { return num_mobs;     }
    void set_zone(zone* z)                      { my_zone = z;         }
    zone* get_zone()                            { return my_zone;      }
    int get_num_mobexits()                      { return num_mobexits; }
    int get_mobexit(int n)                      { return mobexit[n];   }
    int get_LAWFULL()                           { return LAWFULL;      }
    int get_NO_MOB()                            { return NO_MOB;       }
    int get_PKOK()                              { return PKOK;         }
    int get_num_items()                         { return num_items;    }
    item* get_item(int i)                       { return Item_List[i]; }
    void gain(entity*);
    void loss(entity*);
    entity* get_ent(int n);
    void examine_say(string);
    entity* find_entity(string);
    entity* find_entity(entity*, string);
    entity* find_character(string);
    int find_num_entities(string);
    string get_look_titles(entity*);
    string get_scout_names(int, string);
    string check_blockers(entity*, char);
    int get_groupsize(group*);
    int* distance(room*);
    void get_room(entity*, string);
    int add_item_room(item*);
    void remove_item_room(item*);
    void create_room_get_output(int, item*, entity*, string*, string*);
    item* find_item_room(string);
    string stack_items(item**, int);
    string display_item_list();
    int num_merchants();
    entity* get_merchant(int);
    item* find_item_merchant(string, entity**);


// ***************************************************************************** //
// ***************************** COMBAT AND SPELLS ***************************** //
// ***************************************************************************** //

    void update_combat(double);
    void update_targets();
    void all_assist();
    void fight();
    void destroy_dead();
    void check_fight();
    void add_meters();
    void distribute_foutput(attackinfo, entity*, entity*);
    void distribute_exp_output(entity*);
    void set_timer(double);
    int item_area_damage(entity*, int, int);
    void drop_spell_strings(entity*, entity*, attackinfo);
    void drop_exp_strings(entity*);
    void echo_strings();

// ***************************************************************************** //
// **************************** MESSAGING FUNCTIONS **************************** //
// ***************************************************************************** //

    void echo(string);
    void xecho(entity*, string);
    void xecho(entity*, entity*, string);
    void xecho(entity*, entity*, entity*, string);
    void pblind_echo(entity*, string);
    void gblind_echo(entity*, string);
    void gblind_echo(entity*, entity*, string);
    void gblind_echo(entity*, entity*, entity*, string);
    void gblind_echo(entity*, entity*, entity*, entity*, string);
    void prompt_all();

// ***************************************************************************** //
// ***************************************************************************** //
// ***************************************************************************** //

  private:
    zone *my_zone;
    int id, num_looks, num_mobexits;
    string title, desc, *look, *ltarget;
    int LAWFULL, PKOK, NO_MAGIC, REGEN;
    int INDOORS, NO_SUMMON, NO_QUIT, SILENT;
    int PRIVATE, NO_DROP, NO_MOB, DONATION;
    int N, E, S, W, U, D, mobexit[6];
    entity* *ENT_LIST;
    int num_people;
    int num_mobs;
    int size;
    mobcounter *MC;
    string *events;
    int num_events, *eventf;
    item* *Item_List;
    int num_items;
    double timer;
    CRITICAL_SECTION CriticalSection;
};

#endif