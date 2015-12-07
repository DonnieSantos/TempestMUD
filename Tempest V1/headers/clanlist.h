#ifndef CLANLIST_H
#define CLANLIST_H

class clanlist

{
  public:
    clanlist();
    clan* get_clan(string);
    string display_clanlist();
    void verify_all_members();

  private:
    int size;
    clan* *clan_list;
};

#endif