#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

board::board()

{
  InitializeCriticalSection(&CriticalSection);
}

board::~board()

{
  delete notelist;
  DeleteCriticalSection(&CriticalSection);
}

string board::get_name()        { return name; }
void board::set_name(string s)  { name = s;    }

string board::display_list()

{
  cslock Lock(&CriticalSection);

  string temp = "";
  string temp2;
  int i, whitesize;
  stringstream ss;
  string whitespace, numspace;

  temp = "The [" + name + "] message board:\r\n\r\n";

  if (size != 0)

  {
    for (i=0; i<size; i++)

    {
      ss.clear();
      ss.str("");
      ss << i+1;

      temp2 = ss.str();

      whitespace = "";
      whitesize = 15 - (int)notelist[i].sender.size();

      for (int i=0; i<whitesize; i++)
        whitespace += " ";

      numspace = " ";
      if ((i+1) < 10) numspace += " ";
      if ((i+1) < 100) numspace += " ";

      temp += "[#r" + temp2 + "#N]" + numspace + "[#C" + notelist[i].sender + "#N]" + whitespace;
      temp += notelist[i].title + "#N\r";

      if (i != size-1)
        temp += '\n';
    }

    return temp;
  }

  return temp + "There are no messages posted on this board.";
}

string board::remove_note(string s, string writer, int level)

{
  cslock Lock(&CriticalSection);

  char *temp;
  string temp2, temp3, temp4;
  note *templist;
  int i, index;
  istringstream iss;
  stringstream ss;
  FILE *nfile;

  iss.clear();
  iss.str("");
  iss.str(s);
  iss >> index;

  if ((index<=0) || (index>size))
    return "That message does not exist!";

  if ((notelist[index-1].sender == writer) || (level > 99))
  {
    templist = new note[size-1];
    size--;

    temp2 = ".\\boards\\" + name + "\\" + s + ".dat";
    temp = (char*)temp2.c_str();

    remove(temp);

    for (i=0; i<index-1; i++)
      templist[i] = notelist[i];

    for (i=index; i<=size; i++)
      templist[i-1] = notelist[i];

    delete [] notelist;
    notelist = templist;

    for (i=index; i<=size; i++)

    {
      temp4 = "";
      temp3 = "";

      ss.clear();
      ss.str("");
      ss << i;
      temp4 = ss.str();

      ss.clear();
      ss.str("");
      ss << i+1;
      temp3 = ss.str();

      temp2 = "rename boards\\" + name + "\\" + temp3 + ".dat " + temp4 + ".dat";
      temp = (char*)temp2.c_str();

      system(temp);
    }

    temp2 = ".\\boards\\" + name + "\\notelist.dat";
    temp = (char*)temp2.c_str();
    nfile = fopen(temp, "w");

    temp2 = "";
    ss.clear();
    ss.str("");
    ss << size;
    temp2 = ss.str();
    temp = (char*)temp2.c_str();
    fprintf(nfile, temp);
    fclose(nfile);

    return "Message removed.";
  }

  return "You cannot remove that message.";
}

void board::add_note(string sender, string title, string body)

{
  cslock Lock(&CriticalSection);

  FILE *nfile;
  string temp, temp3, temp4;
  char *temp2;
  char s[4];
  note *templist;
  istringstream iss;
  stringstream ss;
  int i;

  temp = ".\\boards\\" + name + "\\notelist.dat";
  temp2 = (char*)temp.c_str();
  nfile = fopen(temp2, "r");
  fgets(s, sizeof(s), nfile);
  fclose(nfile);

  temp = s;
  iss.clear();
  iss.str("");
  iss.str(temp);
  iss >> i;
  i++;
  ss.clear();
  ss.str("");
  ss << i;
  temp = ss.str();

  temp3 = ".\\boards\\" + name + "\\notelist.dat";
  temp2 = (char*)temp3.c_str();
  nfile = fopen(temp2, "w");
  temp2 = (char*)temp.c_str();
  fprintf(nfile, temp2);
  fclose(nfile);

  size++;

  for (i=size-1; i>=1; i--)
  {
    temp4 = "";
    temp3 = "";

    ss.clear();
    ss.str("");
    ss << i;
    temp = ss.str();

    ss.clear();
    ss.str("");
    ss << i+1;
    temp3 = ss.str();

    temp4 = "rename boards\\" + name + "\\" + temp + ".dat " + temp3 + ".dat";
    temp2 = (char*)temp4.c_str();

    system(temp2);
  }

  temp3 = ".\\boards\\" + name + "\\1.dat";
  temp2 = (char*)temp3.c_str();
  nfile = fopen(temp2, "w");

  temp3 = sender + '\n';
  temp2 = (char*)temp3.c_str();
  fprintf(nfile, temp2);

  temp3 = title + '\n';
  temp2 = (char*)temp3.c_str();
  fprintf(nfile, temp2);

  temp3 = body;
  temp2 = (char*)temp3.c_str();
  fprintf(nfile, temp2);

  fprintf(nfile, "-[END NOTE]- ");

  fclose(nfile);

  templist = new note[size];
  templist[0].sender = sender;
  templist[0].title = title;
  templist[0].body = body;

  for (i=1; i<size; i++)
    templist[i] = notelist[i-1];

  delete [] notelist;
  notelist = new note[size];
  notelist = templist;
}

string board::get_note(int i)

{
  cslock Lock(&CriticalSection);

  string temp;
  int j, x, z, q = 0;

  if ((i>0) && (i<=size))
  {
    temp =  "#n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n";

    temp += "#n|#C " + notelist[i-1].sender;
    for (j=(int)(61-notelist[i-1].sender.length()); j>0; j--)
      temp += " ";
    temp += " #n|\r\n";

    temp += "#n| ";

    j = 61 - visible_size(notelist[i-1].title);

    if (j%2==0)
    {
      for (x=0; x<(j/2); x++)
        temp += " ";
      temp += "#C" + notelist[i-1].title;
      for (x=0; x<(j/2); x++)
        temp += " ";
    }
    else
    {
      for (x=0; x<=(j/2); x++)
        temp += " ";
      temp += "#C" + notelist[i-1].title;
      for (x=0; x<(j/2); x++)
        temp += " ";
    }

    temp += " #n|\r\n";

    temp += "#n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n";
    temp += "#n|                                                               |#N\r\n";

    j = 0;

    while (j<(int)(notelist[i-1].body.length()))
    {
      temp += "#n|#N ";
      q = 0;

      while (notelist[i-1].body[j] != '\n')

      {
        temp += notelist[i-1].body[j];

        if (notelist[i-1].body[j] == '#')
          if (notelist[i-1].body[j+1] == 'R' ||
        notelist[i-1].body[j+1] == 'r' ||
        notelist[i-1].body[j+1] == 'B' ||
        notelist[i-1].body[j+1] == 'b' ||
        notelist[i-1].body[j+1] == 'G' ||
        notelist[i-1].body[j+1] == 'g' ||
        notelist[i-1].body[j+1] == 'Y' ||
        notelist[i-1].body[j+1] == 'y' ||
        notelist[i-1].body[j+1] == 'M' ||
        notelist[i-1].body[j+1] == 'm' ||
        notelist[i-1].body[j+1] == 'C' ||
        notelist[i-1].body[j+1] == 'c' ||
        notelist[i-1].body[j+1] == 'N' ||
        notelist[i-1].body[j+1] == 'n')
                q = q-2;

        q++;
        j++;
      }

      j++;

      x = (61-q);

      if (x%2==0)

      {
        for (z=0; z<x; z++)
          temp += " ";
        temp += " #n|\r\n";
      }

      else

      {
        for (z=0; z<x; z++)
          temp += " ";
        temp += " #n|\r\n";
      }
    }

    temp += "#n|                                                               |#N\r\n";
    temp += "#n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return temp;
  }

  return "That note does not exist!";
}

void board::load_board(string str)

{
  FILE *nfile;
  char *temp2;
  char s[200];
  string temp;

  cout << "Loading " + name + " board." << endl;
  temp = ".\\boards\\" + name + "\\notelist.dat";
  temp2 = (char*)temp.c_str();
  nfile = fopen(temp2, "r");
  fgets(s, sizeof(s), nfile);

  temp = s;
  size = stringconvert(temp);
  fclose(nfile);

  notelist = new note[size];

  for (int i=1; i<=size; i++)

  {
    temp = intconvert(i);
    temp = ".\\boards\\" + name + "\\" + temp + ".dat";
    temp2 = (char*)temp.c_str();
    nfile = fopen(temp2, "r");

    fgets(s, sizeof(s), nfile);
    temp = s;
    temp = temp.substr(0, temp.length()-1);
    notelist[i-1].sender = temp;

    fgets(s, sizeof(s), nfile);
    temp = s;
    temp = temp.substr(0, temp.length()-1);
    notelist[i-1].title  = temp;

    notelist[i-1].body = "";

    while (temp != "-[END NOTE]- ")

    {
      fgets(s, sizeof(s), nfile);
      temp = s;

      if (temp != "-[END NOTE]- ")
        notelist[i-1].body   += temp;
    }

    fclose(nfile);
  }
}