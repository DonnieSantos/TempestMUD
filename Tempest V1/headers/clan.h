#ifndef CLAN_H
#define CLAH_H

class clan

{
  public:
    clan(int, string, int);
    void set_rankname(int, string, string);
    void add_member(string, int, string);
    void remove_member(string);
    string get_name();
    int get_numranks();
    int get_num_leaders();
    int get_num_council();
    int get_num_immortals();
    int get_num_applicants();
    int get_rank(string);
    void set_rank(string, int);
    void set_class(string, string);
    void set_clan_info(string);
    string get_class(string);
    string get_rankname(string, string);
    string display_info();
    string display_roster();
    string display_ranknames();
    void save_clanfile();
    void verify_members();

  private:
    int id;
    int size;
    int *ranks;
    string name;
    string *members;
    string *classes;
    string clan_info;
    int num_ranknames;
    string *male_ranknames;
    string *female_ranknames;
};

#endif