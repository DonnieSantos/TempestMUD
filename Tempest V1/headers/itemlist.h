#ifndef ITEMLIST_H
#define ITEMLIST_H

class itemlist

{
  public:
    itemlist();
    ~itemlist();
    void load();
    item* get_item(int);
    item* get_item(string);
    item* replicate(int, string);
    void set_size() { size = ITEMLIST_SIZE; }
    int get_size()  { return size;          }

  private:
    int size;
    int last_ms;
    int ms_match_count;
    item *list[ITEMLIST_SIZE];
};

#endif