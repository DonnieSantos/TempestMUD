#ifndef ITEM_H
#define ITEM_H

class decay

{
  public:
    decay() {}
    virtual void decay_object(int) = 0;
    int decay_time;
};

class item : public decay

{
  public:
    item();
    virtual void decay_object(int);

    // *************************************************************** //
    // ******************** General Item Functions ******************* //
    // *************************************************************** //

    void add_type(int);
    int find_type(int);
    void add_flag(string);
    void rem_flag(string);
    int find_flag(string);

    // *************************************************************** //
    // *********************** Liquid Functions ********************** //
    // *************************************************************** //

    void drink(entity*);
    void quaff(entity*);
    void refill(string);
    void empty(entity*);

    // *************************************************************** //
    // ********************* Consumable Functions ******************** //
    // *************************************************************** //

    void add_cspell(string);
    void eat(entity*);
    void recite(string*);
    void use(string*);
    void recharge();

    // *************************************************************** //
    // ********************* Container Functions ********************* //
    // *************************************************************** //

    void update();
    void contents_search(item**, int);
    void remove_item(item*);
    void add_item(item*);
    void force_add_item(item*);
    int can_add(item*);
    item* find_item(string);

    // *************************************************************** //
    // ******************** Enhancement Functions ******************** //
    // *************************************************************** //

    void cast(string, int*, int*);
    void add_enhancement(string);
    void add_innate(string);
    void add_charged(string);
    void equipped();
    void unequipped();

    // *************************************************************** //
    // ************************ Item Actions ************************* //
    // *************************************************************** //

    void play(entity*);
    void pull(entity*);

// *************************************************************** //
// *************************** Variables ************************* //
// *************************************************************** //

    // General Item Information
    int id, zone_id, num_types;
    int level, worth, num_flags;
    int* type_list;
    string name, lname;
    string look_desc, ground_desc;
    string TIME_STAMP, TYPE_STAMP;
    string* flag_list;
    entity* ENT; room* rm;

    // Equipment Information
    int hitroll, damroll;
    int armorclass, resistance;
    int HP, MN, MV, STR;
    int DEX, CON, INT, WIS;
    string classes, places;

    // Player Spell/Physical Improvements
    int num_enhancements, player_corpse, all_spells;
    int all_reduce, all_power;
    int* mana_list;
    int* power_list;
    string* enhance_list;
    string* innate_list;
    int num_innate;
    int max_charges;
    int charges_left;
    string* charged_list;
    int num_charged;

    // Message Board Information
    string board_name;
    board* board_pointer;

    // Gold Pile Information
    int amount;

    // Liquid Information
    string liquid_type;
    int uses, max_uses;

    // Consumable Item Information
    int num_spells;
    string* spell_list;

    // Container Information
    item* *Item_List;
    int num_items, all_items;
    int max_items, max_level;
    item* parent;

    // Actions
    int PLAYABLE, PULLABLE;
    int mana_cost            [NUM_ITEM_ACTIONS];
    int work_room            [NUM_ITEM_ACTIONS];
    int num_commands         [NUM_ITEM_ACTIONS];
    int command_location     [NUM_ITEM_ACTIONS][NUM_ACTION_COMMANDS];
    string action_classes    [NUM_ITEM_ACTIONS];
    string action_conditions [NUM_ITEM_ACTIONS];
    string action_commands   [NUM_ITEM_ACTIONS][NUM_ACTION_COMMANDS];

    int num_fail             [NUM_ITEM_ACTIONS];
    int fail_locations       [NUM_ITEM_ACTIONS][NUM_ACTION_COMMANDS];
    string fail_commands     [NUM_ITEM_ACTIONS][NUM_ACTION_COMMANDS];

    int num_succ             [NUM_ITEM_ACTIONS];
    int succ_locations       [NUM_ITEM_ACTIONS][NUM_ACTION_COMMANDS];
    string succ_commands     [NUM_ITEM_ACTIONS][NUM_ACTION_COMMANDS];
};

#endif