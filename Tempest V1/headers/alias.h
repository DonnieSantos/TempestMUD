#ifndef ALIAS_H
#define ALIAS_H

class alias

{
  public:
    alias() { }
    string name;
    string output;
};

class alias_list

{
  public:
    alias_list(entity*);
    void add_alias(string);
    alias* search_alias(string);
    alias* get_alias(int);
    void add_alias(alias*);
    void auto_alias(string, string);
    void remove_alias(alias*, int);
    void apply_aliasing(string*);
    void display_all();
    int get_size();

  private:
    entity* me;
    alias** list;
    int size;
};

#endif