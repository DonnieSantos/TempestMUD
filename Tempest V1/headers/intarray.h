#ifndef INTARRAY_H
#define INTARRAY_H

class intarray

{
  public:
    intarray();
    ~intarray();
    void put(int);
    int get(int);
    int search(int);
    int size();

  private:
    int list[10000];
    int lsize;
};

intarray::intarray() 	     { lsize = 0;      }
intarray::~intarray() 	     {                 }
int intarray::get(int x)     { return list[x]; }
int intarray::size()	     { return lsize;   }

void intarray::put(int x)

{
  list[lsize] = x;
  lsize++;
}

int intarray::search(int x)

{
  for (int i=0; i<lsize; i++)
    if (list[i] == x)
      return i+1;

  return 0;
}

#endif