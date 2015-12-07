#ifndef MOBCOUNTER_H
#define MOBCOUNTER_H

class mobcounter

{
  public:
    mobcounter(moblist*);
    ~mobcounter() { }
    void add(int);
    void clear();
    string stack();
    string short_stack(int);
    string stringint(int);

  private:
    moblist* MOB_LIST;
    int size, vnums[100][2];
    int count, done;
};

#endif