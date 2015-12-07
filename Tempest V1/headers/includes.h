#ifndef INCLUDES_H
#define INCLUDES_H

// ******************************************************************* //
// ************************** Game Settings ************************** //
// ******************************************************************* //

#define GAME_PORT          14321

#define ZONE_SIZE             50
#define WORLD_SIZE         10000
#define ITEMLIST_SIZE      10000
#define MOBLIST_SIZE       10000

#define LINE_LENGTH           75
#define RLINE_LENGTH          65
#define INFO_WIDTH            60
#define SPEEDWALK_LIMITER     45
#define SPEECH_LIMITER       350
#define MAX_SCREEN_SIZE      100
#define MAX_LEGEND_SIZE      100
#define MAX_NUM_ITEMS        100
#define MAX_NUM_ALIASES       50

#define MOBILE_HP_PERCENT    100
#define MOBILE_HR_PERCENT    100
#define MOBILE_DR_PERCENT    100
#define MOBILE_AC_PERCENT    100
#define MOBILE_MR_PERCENT    100
#define MOBILE_STR_PERCENT   100
#define MOBILE_DEX_PERCENT   100
#define MOBILE_CON_PERCENT   100
#define MOBILE_INT_PERCENT   100
#define MOBILE_WIS_PERCENT   100

#define ANIMATE_TICK           1
#define MINIMUM_TICK          50
#define MAXIMUM_TICK          70
#define FIGHT_TICK_UPDATE    .25
#define COMMAND_LOCK_UPDATE  .10

#define MINUTES_PER_PERIOD   365
#define MINUTES_PER_DAY      1461
#define MINUTES_PER_SEASON   131490
#define MINUTES_PER_VALLUM   525960

// ******************************************************************* //
// **************************** State Macros ************************* //
// ******************************************************************* //

// CLIENT STATES:

#define CSTATE_READY            0
#define CSTATE_LOGIN            1
#define CSTATE_NORMAL           2
#define CSTATE_LINKDEAD         3
#define CSTATE_WRITING          4
#define CSTATE_POSSESSION       5
#define CSTATE_IMM_AT           6

// PLAYER STATES:

#define PSTATE_STANDING         1
#define PSTATE_FIGHTING         2
#define PSTATE_SITTING          3
#define PSTATE_SLEEPING         4
#define PSTATE_RESTING          5

// ITEM TYPES:

#define ITEM_GENERIC            1
#define ITEM_EQUIPMENT          2
#define ITEM_CONTAINER          3
#define ITEM_BOARD              4
#define ITEM_CORPSE             5
#define ITEM_GOLD               6
#define ITEM_BLOCKER            7
#define ITEM_FOOD               8
#define ITEM_DRINK              9
#define ITEM_DRINKABLE         10
#define ITEM_DRINKCONTAINER    11
#define ITEM_POTION            12
#define ITEM_SCROLL            13
#define ITEM_FOUNTAIN          14

// ITEM FLAGS:

#define FLAG_NOGET       "NO_GET"
#define FLAG_NOSACRIFICE "NO_SACRIFICE"
#define FLAG_NODROP      "NO_DROP"
#define FLAG_GLOWING     "GLOW"
#define FLAG_HUMMING     "HUMMING"
#define FLAG_GOOD        "GOOD"
#define FLAG_EVIL        "EVIL"
#define FLAG_NEUTRAL     "NEUTRAL"
#define FLAG_BURNING     "BURNING"
#define FLAG_FREEZING    "FREEZING"
#define FLAG_SHOCKING    "SHOCKING"
#define FLAG_UNTOUCHABLE "UNTOUCHABLE"
#define FLAG_PERISH      "PERISH"

// ITEM LIST ADD ERRORS:

#define SUCCESS                 0
#define CANNOT_FIT              1
#define FULL                    2
#define CONTAINS_HIGH_LEVEL     3
#define HIGH_LEVEL              4
#define CORPSE                  5
#define NO_GET                  6
#define INSIDE_SELF             7
#define CANT_DROP               8
#define UNTOUCHABLE             9

// SACRIFICE ERRORS:

#define BOARD                   1
#define CONTAINS_ITEMS          2
#define PLAYER_CORPSE           3
#define CANNOT_SACRIFICE        4

// SPELL TYPES:

#define SPELL_SUPPORT           1
#define SPELL_DAMAGE            2
#define SPELL_AREA_DAMAGE       3
#define SPELL_UNIQUE            4

// SCHEDULE EVENTS

#define SCHEDULE_SIZE           4
#define SCHEDULE_DEATH_BLOSSOM  0
#define SCHEDULE_TIGER_STRIKE   1
#define SCHEDULE_FROZEN         2
#define SCHEDULE_MUTE           3

// ENTITY TYPES

#define ENTITY_GHOST            1
#define ENTITY_CHAR             2
#define ENTITY_MOBILE           3

// ITEM ACTIONS

#define NUM_ACTION_COMMANDS    10

#define NUM_ITEM_ACTIONS        2
#define ITEM_ACTION_PLAY        0
#define ITEM_ACTION_PULL        1

// ******************************************************************* //
// ************************** Global Definitions ********************* //
// ******************************************************************* //

#define BLACK    "\33[0;30m"
#define RED      "\33[0;31m"
#define GREEN    "\33[0;32m"
#define BROWN    "\33[0;33m"
#define BLUE     "\33[0;34m"
#define PURPLE   "\33[0;35m"
#define CYAN     "\33[0;36m"
#define GRAY     "\33[0;37m"
#define L_BLUE   "\33[1;34m"
#define L_GREEN  "\33[1;32m"
#define L_CYAN   "\33[1;36m"
#define L_RED    "\33[1;31m"
#define L_PURPLE "\33[1;35m"
#define YELLOW   "\33[1;33m"
#define WHITE    "\33[1;37m"

#define ECHO         1
#define MODE         1
#define EDIT         1
#define SLC          3
#define SLC_RP      13
#define LINEMODE    34
#define SE         240
#define SB         250
#define WILL       251
#define WONT       252
#define DO         253
#define DONT       254
#define IAC        255

#define DC_Decay(x) dynamic_cast<decay*>(x)
#define DC_Blocker(x) dynamic_cast<blocker*>(x)
#define DC_Ghost(x) dynamic_cast<ghost*>(x)

#define MY possessive(name)
#define TARGETS possessive(target->get_name())
#define SAID(x) ((find_word(x, str)))
#define IMM(x) ((get_level() >= x) && (get_level() >= 100))
#define GHOST (get_type() == ENTITY_GHOST)
#define INVALID_COMMAND "Invalid Command."

// ********************************************************************************************** //
// *************************************** Class Declarations *********************************** //
// ********************************************************************************************** //

class alias;
class alias_list;
class attackinfo;
class blocker;
class board;
class boardlist;
class character;
class clan;
class clanlist;
class client;
class clientlist;
class cmdqueue;
class consumable;
class cslock;
class drinkable;
class drinkable_container;
class effects;
class entity;
class filemanager;
class food;
class fountain;
class ghost;
class group;
class includes;
class intarray;
class item;
class itemlist;
class legend;
class legend_mark;
class liquids;
class mobcounter;
class mobdesc;
class mobile;
class moblist;
class note;
class potion;
class religion;
class religionlist;
class room;
class schedule;
class scroll;
class skill;
class skills;
class sorter;
class spell;
class spells;
class stringarray;
class systemlog;
class world;
class zone;

// ******************************************************************* //
// ************************ Global Variables ************************* //
// ******************************************************************* //

extern filemanager *File;
extern clientlist *clist;
extern world *World;

// ******************************************************************* //
// *********************** Function Prototypes *********************** //
// ******************************************************************* //

int random_int(int, int);
int get_exp_percent(int);
int get_exp_deficit(int);
int get_exp_need(int);
string sclassify(string);
string color(string, char, int);
string colorize(string, int);
int letter(char);
int number(char);
int number(string);
int nonzero_number(char);
string intconvert(int);
int stringconvert(string);
string lowercase(string);
string uppercase(string);
int short_for(string, string);
int clip_number(string*);
int color_character(char);
int visible_size(string);
string variable_allign(string, int);
string allign(string);
string rm_allign(string);
int direction(string);
string clear_whitespace(string);
string clip_end(string*);
string first(string);
string last(string);
int legal_desc(string);
string exp_commas(string);
string remove_dashes(string);
string full_day(string);
string full_date(string);
string full_time(string);
int blank(string);
string format_plan(string);
string format_desc(string);
string key(string);
string possessive(string);
string class_plural(string);
string string_stacker(string);
string format_identify(string);
string rainbow_string(string, string);
int speedwalk_legal(string);
int determine_gold_string(string);
int match(string, string, string);
string remove_colors(string);
string proper_name(string);
int get_width(string);
string strip_string(string*);
string remove_bsr(string);
string remove_bsn(string);
int ncount(string);
void format_ansi_output(string*);
string ReadLine(FILE*);
string adjective(int, int);
string all_word_replace(string, string, string);
int abbreviation(string, string);
string capital_lname(string);
string get_liquid_amount(item*);
string look_liquid(string);
string last_command(string);
void exit_error(string);
string get_gamedate(string);
long get_gametime();

// ******************************************************************* //
// *********************** Lists And Displays ************************ //
// ******************************************************************* //

string colorlist();
string classlist();
string clan_commands();
string religion_commands();
string immortal_lists();
string immortal_commands();
string title_screen();
string information();

// ******************************************************************* //
// **************************** Includes ***************************** //
// ******************************************************************* //

#include <stdio.h>
#include <iostream.h>
#include <winsock2.h>
#include <stdlib.h>
#include <string.h>
#include <sstream.h>
#include <process.h>
#include <time.h>
#include "windows.h"
#include "alias.h"
#include "ArrayList.h"
#include "attackinfo.h"
#include "blocker.h"
#include "board.h"
#include "boardlist.h"
#include "character.h"
#include "clan.h"
#include "clanlist.h"
#include "client.h"
#include "clientlist.h"
#include "cmdqueue.h"
#include "cslock.h"
#include "effects.h"
#include "entity.h"
#include "filemanager.h"
#include "ghost.h"
#include "group.h"
#include "includes.h"
#include "intarray.h"
#include "item.h"
#include "itemlist.h"
#include "legend.h"
#include "liquids.h"
#include "mobcounter.h"
#include "mobdesc.h"
#include "mobile.h"
#include "moblist.h"
#include "note.h"
#include "religion.h"
#include "religionlist.h"
#include "room.h"
#include "schedule.h"
#include "skill.h"
#include "skills.h"
#include "sorter.h"
#include "spell.h"
#include "spells.h"
#include "stringarray.h"
#include "systemlog.h"
#include "world.h"
#include "zone.h"

#endif