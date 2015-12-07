#ifndef BLOCKER_H
#define BLOCKER_H
#include "item.h"

class blocker : public item

{
  public:
    blocker();
    blocker(mobile*);
    void initialize();
    int try_pass(entity*);
    int try_open(entity*);
    int try_close(entity*);
    string error_msg();
    char get_bdir();
    string get_btype();
    void set_bdir(char);
    void set_btype(string);
    void open_descriptions();
    void close_descriptions();

  private:
    clanlist *CL;
    char direction;
    string error_message;
    string blocker_type;
    string clan_name;
    mobile* Owner_Mob;
    int open_gate;
    unsigned int INITIALIZED;
};

#endif