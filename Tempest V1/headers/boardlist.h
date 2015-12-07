#ifndef BOARDLIST_H
#define BOARDLIST_H

class boardlist

{
  public:
    boardlist() { }
    ~boardlist() { }
    int get_size();
    board* get_board(string);
    void load();

  private:
    int size;
    board *list;
};

#endif