#ifndef EFFECTS_H
#define EFFECTS_H

class effects

{
  public:
    effects(entity*);
    ~effects() { }
    void renew(string);
    void renew(string, int);
    string decrement();
    string remove_effect(string);
    int get_ticks(int);
    int get_ticks(string);
    int get_num_effects();
    string get_pfile_info();
    string get_stat_info();

  private:
    entity *self;
    int SANC, SHIELD;
};

#endif