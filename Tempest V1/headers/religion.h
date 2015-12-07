#ifndef RELIGION_H
#define RELIGION_H

class religion

{
  public:
    religion(int, string);
    void set_rankname(int, string, string);
    void add_member(string, int, string);
    void remove_member(string);
    string get_name();
    int get_size();
    int get_rank(string);
    int get_num_leaders();
    void set_rank(string, int);
    void set_class(string, string);
    void set_religion_info(string);
    void set_diety_name(string);
    void set_diety_title(string);
    string get_class(string);
    string get_rankname(string, string);
    string get_diety_name();
    string get_diety_title();
    string display_info();
    string display_roster();
    string display_ranknames();
    void save_religionfile();
    void verify_members();

  private:
    int id;
    int size;
    int *ranks;
    string name;
    string *members;
    string *classes;
    string religion_info;
    string male_ranknames[5];
    string female_ranknames[5];
    string diety_name;
    string diety_title;
};

#endif