#ifndef CMDQUEUE_H
#define CMDQUEUE_H

class cmdqueue

{
  public:
    cmdqueue();
    ~cmdqueue();
    void flushbuffer();
    string put(string);
    string force(string);
    string get();
    int search(string);
    int get_size();
    string flush();

  private:
    string *str;
    int size;
};

#endif