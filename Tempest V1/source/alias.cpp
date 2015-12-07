#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

alias_list::alias_list(entity* owner)

{
  me = owner;

  size = 0;
  list = new alias * [1];
  list[0] = new alias();
}

void alias_list::add_alias(string str)

{
  if (str == "") {
    display_all();
    return; }

  string a1 = clear_whitespace(remove_colors(lowercase(first(str))));
  string Rem = last(str);

  if (a1 == "alias") {
    if (Rem == "") me->echo("No such alias to remove.");
    else me->echo("Illegal alias definition.");
    return; }

  alias *A = search_alias(a1);

  if (Rem == "")

  {
    if (A == NULL) {
      me->echo("No such alias to remove.");
      return; }

    remove_alias(A,1);
    return;
  }

  string lc = lowercase(last_command(Rem));
  string lc2;

  for (int i=0; i<size; i++)

  {
    lc2 = lowercase(last_command(list[i]->output));

    if ((lowercase(list[i]->name) == lc) && (a1 == lc2)) {
      me->echo("Aliasing in loops is not allowed.");
      return; }
  }

  if (A != NULL) {
    remove_alias(A,0);
    delete A; }

  A = new alias();
  A->name = a1;
  A->output = Rem;

  add_alias(A);
}

void alias_list::add_alias(alias *a)

{
  if (size >= MAX_NUM_ALIASES) {
    me->echo("You have reached the maximum number of aliases.");
    return; }

  size++;

  alias** temp = new alias * [size];

  for (int i=0; i<size-1; i++)
    temp[i] = list[i];

  temp[size-1] = a;

  delete [] list;
  list = temp;

  me->echo("Ok. [" + a->name + "] will now output [" + a->output + "].");
}

void alias_list::auto_alias(string s1, string s2)

{
  size++;

  alias** temp = new alias * [size];

  for (int i=0; i<size-1; i++)
    temp[i] = list[i];

  alias* a = new alias();
  a->name = s1;
  a->output = s2;

  temp[size-1] = a;

  delete [] list;
  list = temp;
}

alias* alias_list::search_alias(string s)

{
  for (int i=0; i<size; i++)
    if (list[i]->name == s) return list[i];

  return NULL;
}

void alias_list::remove_alias(alias *a, int audible)

{
  size--;

  if (size < 0) { size = 0;  return; }

  alias** temp = new alias * [size];
  int found = 0;

  for (int i=0; i<=size; i++)

  {
    if (list[i] != a)
      temp[i-found] = list[i];

    else found = 1;
  }

  if (found == 1)

  {
    if (audible)
      me->echo("Alias [" + a->name + "] has been removed.");

    delete [] list;
    list = temp;
  }

  else me->echo("Attempted to remove alias but didn't find it.");
}

void alias_list::display_all()

{
  if (size == 0) {
    string hlp = "Type 'help alias' for information on using aliases.";
    me->echo("You have no aliases defined.\r\n" + hlp);
    return; }

  string temp = "#nCurrently Defined Aliases#R:#N";

  for (int i=0; i<size; i++)

  {
    temp += "\r\n" + list[i]->name + " = " + list[i]->output;
  }

  me->echo(temp);
}

void alias_list::apply_aliasing(string *str)

{
  string command = *str;
  string key = first(lowercase(command));
  int ksize = key.size();
  int found = 0;

  for (int i=0; i<size; i++)
  if (list[i]->name == key)

  {
    found = 1;
    key = list[i]->output;
    i = size;
  }

  if (!found) return;

  command.erase(0, ksize);
  while (command.find(" ") == 0) command.erase(0,1);

  while ((command.find("%") != string::npos) && (command.size() > 0))
    command.erase(command.find("%"), 1);

  if (key.find("%") != string::npos)

  {
    while (key.find("%") != string::npos)

    {
      int pos = key.find("%");
      key.erase(pos, 1);
      key.insert(pos, command);
    }

    command = key;
  }

  else if (command == "") command = key;

  else command = key + " " + command;

  *str = command;
}

alias* alias_list::get_alias(int i)

{
  if (i < size) return list[i];
  return NULL;
}

int alias_list::get_size()

{
  return size;
}