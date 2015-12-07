#ifndef BOARD_H
#define BOARD_H
#include "windows.h"

class board
{
  public:
    board();
    ~board();
    void load_board(string);
    void add_note(string, string, string);
    void set_name(string);
    string remove_note(string, string, int);
    string get_name();
    string get_note(int);
    string display_list();

  private:
    int size;
    string name;
    note *notelist;
    CRITICAL_SECTION CriticalSection;
};

#endif