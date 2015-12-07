#ifndef MOBLIST_H
#define MOBLIST_H

class moblist

{
  public:
    moblist();
    ~moblist() { }
    mobdesc* get_mobdesc(int);
    mobdesc* get_mobdesc(string);
    void set_size() { size = MOBLIST_SIZE; }
    int get_size() { return size; }
    void load();

  private:
    int size;
    mobdesc *list[MOBLIST_SIZE];
};

#endif