#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

board* boardlist::get_board(string str)

{
  for (int i=0; i<size; i++)
    if (list[i].get_name().find(str) != string::npos)
      return &list[i];

  return NULL;
}

void boardlist::load()

{
  int num_boards;
  string filename, input;
  FILE* fp;
  char s[500];

  filename = ".\\boards\\boardlist.dat";
  fp = fopen((char*)filename.c_str(), "r");

  fscanf(fp, "%d", &num_boards);
  fgets(s, sizeof(s), fp);
  list = new board [num_boards];

  for (int i=0; i<num_boards; i++)
  {
    fgets(s, sizeof(s), fp);
    input = s;
    input = input.substr(0, input.length()-1);

    list[i].set_name(input);
    list[i].load_board(list[i].get_name());
  }

  fclose(fp);
}

int boardlist::get_size()

{
  return size;
}