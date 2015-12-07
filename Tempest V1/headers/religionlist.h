#ifndef RELIGIONLIST_H
#define RELIGIONLIST_H

class religionlist

{
  public:
    religionlist();
    religion* get_religion(string);
    religion* get_religion_diety(string);
    string display_religionlist();
    void verify_all_members();

  private:
    int size;
    religion* *religion_list;
};

#endif