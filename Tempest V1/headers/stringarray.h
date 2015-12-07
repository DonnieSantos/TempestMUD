#ifndef STRINGARRAY_H
#define STRINGARRAY_H

class stringarray

{
  public:
    stringarray();
    ~stringarray();
    void put(string);
    string get(int);
    void add(int, string);
    int search(string);
    int count(string);
    int size();

  private:
    string list[2000];
    int lsize;
};

stringarray::stringarray() 	{ lsize = 0;      }
stringarray::~stringarray() 	{                 }
string stringarray::get(int x)  { return list[x]; }
int stringarray::size()	        { return lsize;   }

void stringarray::put(string x)

{
  list[lsize] = x;
  lsize++;
}

void stringarray::add(int i, string s)

{
  list[i] += s;
}

int stringarray::search(string x)

{
  for (int i=0; i<lsize; i++)
    if (list[i] == x)
      return i+1;

  return 0;
}

int stringarray::count(string x)

{
  int Count = 0;

  for (int i=0; i<lsize; i++)
    if (list[i] == x)
      Count++;

  return Count;
}

#endif