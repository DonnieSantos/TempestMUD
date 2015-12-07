#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

mobcounter::mobcounter(moblist *ml)

{
  size = 0;
  MOB_LIST = ml;

  for (int i=0; i<100; i++)
  for (int j=0; j<2; j++)
    vnums[i][j] = 0;
}

void mobcounter::add(int num)

{
  count = 0;
  done = 0;

  while (!done)

  {
    if (count == size) {
      vnums[size][0] = num;
      vnums[size][1]++;
      size++;
      done = 1; }

    else if (vnums[count][0] == num) {
      vnums[count][1]++;
      count++;
      done = 1; }

    else count++;
  }
}

void mobcounter::clear()

{
  for (int i=size-1; i>=0; i--) {
    vnums[i][0] = 0;
    vnums[i][1] = 0; }

  size = 0;
}

string mobcounter::stack()

{
  string str = "";

  for (int i=0; i<size; i++)

  {
    if (vnums[i][1] == 1)
      str = str + "\r\n" + MOB_LIST->get_mobdesc(vnums[i][0])->title;

    else if ((vnums[i][1] > 1) && (vnums[i][1] < 10))
      str = str + "\r\n" + "[ " + stringint(vnums[i][1]) + "] " + MOB_LIST->get_mobdesc(vnums[i][0])->title;

    else if ((vnums[i][1] > 9) && (vnums[i][1] < 100))
      str = str + "\r\n" + "[" + stringint(vnums[i][1]) + "] " + MOB_LIST->get_mobdesc(vnums[i][0])->title;
  }

  clear();

  return str;
}

string mobcounter::short_stack(int n)

{
  string str = "";
  string index = intconvert(n);

  for (int i=0; i<size; i++)

  {
    if (vnums[i][1] == 1)
      str = str + "\r\n    " + index + " - " + MOB_LIST->get_mobdesc(vnums[i][0])->name;

    else if ((vnums[i][1] > 1) && (vnums[i][1] < 10)) {
      str  = str + "\r\n    " + index + " - " + "[ " + stringint(vnums[i][1]) + "] ";
      str += MOB_LIST->get_mobdesc(vnums[i][0])->name; }

    else if ((vnums[i][1] > 9) && (vnums[i][1] < 100)) {
      str  = str + "\r\n    " + index + " - " + "[" + stringint(vnums[i][1]) + "] ";
      str += MOB_LIST->get_mobdesc(vnums[i][0])->name; }
  }

  clear();

  return str;
}

string mobcounter::stringint(int i)

{
  stringstream ss;   
  string s1;
 
  ss.clear();
  ss.str("");

  ss << i;
  s1 = ss.str();
  
  return s1;
}