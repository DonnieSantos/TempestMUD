#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

zone::zone(int i)

{
  number = i;
  ID = number*100;
}

mobile* zone::get_mob(int i)    { return population[i];   }
int zone::get_popsize()         { return population_size; }
void zone::set_name(string s)   { name = s;               }
string zone::get_name()         { return name;            }
int zone::get_number()          { return number;          }
void zone::set_rate(int i)      { rate = i;               }

void zone::set_size(int i)

{
  size = i;

  if (size == 0) multiplier = 1;
  else multiplier = ((size-1) / 100) + 1;

  lastroom = ID + 99 + ((multiplier-1)*100);
}

void zone::set_popsize(int n)

{
  original_size = n;
  population_size = n;

  if (n > 0)

  {
    population = new mobile* [n];
    population_rooms = new int[n];
    population_mnums = new int[n];
  }
}

void zone::add_mob(int j, int mnum, int rnum)

{
  mobdesc *mobd = World->get_moblist()->get_mobdesc(mnum);
  room *mroom = World->get_room(rnum);

  population[j] = new mobile(mobd, mroom, this);
  population_rooms[j] = rnum;
  population_mnums[j] = mnum;

  population[j]->pop_equipment();

  World->get_room(rnum)->gain(population[j]);
}

mobile* zone::add_loaded_mob(int mnum, int rnum)

{
  mobdesc *mobd = World->get_moblist()->get_mobdesc(mnum);
  room *mroom = World->get_room(rnum);

  mobile* *temp = new mobile* [population_size+1];

  for (int i=0; i<population_size; i++)
    temp[i] = population[i];

  temp[population_size] = new mobile(mobd, mroom, this);

  population = temp;
  World->get_room(rnum)->gain(population[population_size]);
  population_size++;

  population[population_size-1]->pop_equipment();

  return population[population_size-1];
}

void zone::kill_mob(mobile *mob)

{
  for (int i=0; i<population_size; i++)

  {
    if (population[i] == mob) {
      delete population[i];
      population[i] = NULL; }
  }
}

void zone::repopulate()

{
  mobdesc *mobd;
  room *mroom;

  for (int i=0; i<original_size; i++)

  {
    if (population[i] == NULL) {
      mobd = World->get_moblist()->get_mobdesc(population_mnums[i]);
      mroom = World->get_room(population_rooms[i]);
      population[i] = new mobile(mobd, mroom, this);
      population[i]->pop_equipment();
      mroom->gain(population[i]); }
  }
}

void zone::clear()

{
  for (int i=0; i<=World->get_maxzone(); i++)
  if (World->get_zone(i) != NULL)
  for (int j=0; j<World->get_zone(i)->get_popsize(); j++)

  if (World->get_zone(i)->get_mob(j) != NULL)
  if ((World->get_zone(i)->get_mob(j)->get_room()->get_id() >= ID)
  &&  (World->get_zone(i)->get_mob(j)->get_room()->get_id() <= lastroom))

  {
    World->get_zone(i)->get_mob(j)->die(0);
  }
}