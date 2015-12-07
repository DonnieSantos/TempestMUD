#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

cmdqueue::cmdqueue()

{
  size = 0;
  str = new string [1];
  str[0] = "NOT USED";
}

cmdqueue::~cmdqueue()

{
  delete [] str;
}

void cmdqueue::flushbuffer()

{
  size = 0;
  delete [] str;
  str = new string [1];
  str[0] = "NOT USED";
}

string cmdqueue::put(string s)

{
  size++;

  string *temp = new string [size+1];

  temp[0] = "NOT USED";
  temp[1] = s;

  if (size > 1)
  for (int i=2; i<=size; i++)
    temp[i] = str[i-1];

  delete [] str;
  str = temp;

  return str[1];
}

string cmdqueue::force(string s)

{
  size++;

  string *temp = new string [size+1];

  for (int i=0; i<size; i++)
    temp[i] = str[i];

  temp[size] = s;

  delete [] str;
  str = temp;

  return str[size];
}

string cmdqueue::get()

{
  string *temp, removed;

  if (size > 0)

  { 
    size--;
    temp = new string [size+1];

    for (int i=0; i<=size; i++)
      temp[i] = str[i];

    removed = str[size+1];

    delete [] str;
    str = temp;

    return removed;
  }

  return "EMPTY";
}

int cmdqueue::search(string s)

{
  for (int i=0; i<=size; i++)

  {
    if (str[i] == s)
      return 1;
  }

  return 0;
}

int cmdqueue::get_size()

{
  return size;
}

string cmdqueue::flush()

{
  string output = "";

  while (size > 0)
    output = output + "\r\n" + get();

  if (output.length() > 0) output.erase(0,2);

  return output;
}