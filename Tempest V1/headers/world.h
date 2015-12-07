#ifndef WORLD_H
#define WORLD_H

class world

{
  public:
    world();
    ~world() { }
    void create_room(int);
    room* get_room(int);
    zone* get_zone(int);
    void attach_zones();
    void set_mobexits();
    void load();
    void describe();
    void animate();
    void set_size()                    { world_size = WORLD_SIZE; }
    int get_size()                     { return world_size;       }
    void set_maxzone()                 { max_zone = ZONE_SIZE;    }
    int get_maxzone()                  { return max_zone;         }
    moblist* get_moblist()             { return MOB_LIST;         }
    itemlist* get_itemlist()           { return ITEM_LIST;        }
    boardlist* get_boardlist()         { return BOARD_LIST;       }
    liquids* get_liquidlist()          { return LIQUID_LIST;      }
    clanlist* get_clanlist()           { return CLAN_LIST;        }
    religionlist* get_religionlist()   { return RELIGION_LIST;    }
    int get_fightsize()                { return fightsize;        }
    systemlog* get_log()               { return LOG;              }

    void add_room_fightlist(room*);
    void remove_room_fightlist(room*);
    int find_room_fightlist(room*);
    void update_fights(double);

    void add_decaylist(decay*);
    void remove_decaylist(decay*);
    void update_decaylist();
    void clean_decaylist();
    int find_decaylist(decay*);


  private:
    int world_size, max_zone;
    zone *All_Zones[ZONE_SIZE+1];
    room *All_Rooms[WORLD_SIZE];
    moblist *MOB_LIST;
    itemlist *ITEM_LIST;
    boardlist *BOARD_LIST;
    liquids *LIQUID_LIST;
    clanlist *CLAN_LIST;
    religionlist *RELIGION_LIST;
    room* *fightlist;
    decay* *decaylist;
    systemlog* LOG;
    int fightsize;
    int decaysize;
};

#endif