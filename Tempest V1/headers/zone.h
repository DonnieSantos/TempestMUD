#ifndef ZONE_H
#define ZONE_H

class zone

{
  public:
    zone(int);
    ~zone() { }
    void set_name(string);
    void set_size(int);
    void set_rate(int);
    void set_popsize(int);
    void add_mob(int, int, int);
    mobile* add_loaded_mob(int, int);
    void kill_mob(mobile*);
    mobile* get_mob(int);
    int get_popsize();
    string get_name();
    int get_number();
    void repopulate();
    void clear();

  private:
    string name;
    int lastroom;
    int size, rate;
    int number, ID;
    int multiplier;
    int original_size;
    int population_size;
    mobile* *population;
    int *population_rooms;
    int *population_mnums;
};

#endif