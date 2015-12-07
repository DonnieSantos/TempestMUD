#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

world::world()

{
  set_size();
  set_maxzone();

  for (int i=0; i<world_size; i++)
    All_Rooms[i] = NULL;

  for (int i=0; i<=max_zone; i++)
    All_Zones[i] = NULL;

  MOB_LIST = new moblist();
  ITEM_LIST = new itemlist();
  LIQUID_LIST = new liquids();
  CLAN_LIST = new clanlist();
  RELIGION_LIST = new religionlist();
  BOARD_LIST = new boardlist();
  LOG = new systemlog();
}

room* world::get_room(int id)

{
  if ((id >= 0) && (id < world_size))
    return All_Rooms[id];

  return NULL;
}

zone* world::get_zone(int id)

{
  if ((id >= 0) && (id <= max_zone))
    return All_Zones[id];

  return NULL;
}

void world::create_room(int id)

{
  All_Rooms[id] = new room(id);

  All_Rooms[id]->add_look("north", "You see nothing out of the ordinary to the north.");
  All_Rooms[id]->add_look("south", "You see nothing out of the ordinary to the south.");
  All_Rooms[id]->add_look("east", "You see nothing out of the ordinary to the east.");
  All_Rooms[id]->add_look("west", "You see nothing out of the ordinary to the west.");
  All_Rooms[id]->add_look("down", "You see nothing out of the ordinary below you.");
  All_Rooms[id]->add_look("up", "You see nothing out ordinary above you.");
}

void world::load()

{
  string str, str2, last, DESC, ltarget, filename, XXX;
  int done, space = 0, num, num2, size, znum, exact;
  int room_id = 0, target = 0, pop_counter;
  char exit, c, s[500], *thefile;
  stringstream ss;
  FILE *fp;

  describe();

  cout << "Loading Zones..." << endl;

  for (int i=0; i<=max_zone; i++)

  {
    ss.clear();
    ss.str("");

    ss << i;
    XXX = ss.str();

    if (i < 10)
      XXX = "00" + XXX;

    else if ((i >= 10) && (i <= 99))
      XXX = "0" + XXX;

    filename = ".\\zones\\zone" + XXX + ".dat";

    thefile = (char*)filename.c_str();
    fp = fopen(thefile, "r");

    if (fp == NULL) { /* Zone file "i" does not exist! */ }

    else

    {
      cout << "Loading Zone " << i << "." << endl;

      while (c != '=') c = getc(fp);
      c = getc(fp);

      str = ReadLine(fp);

      while (c != '=') c = getc(fp);
      c = getc(fp);

      fscanf(fp, "%d", &znum);

      All_Zones[znum] = new zone(znum);
      pop_counter = 0;

      All_Zones[znum]->set_name(str);

      fgets(s, sizeof(s), fp);

      while (c != '=') c = getc(fp);
      c = getc(fp);

      fscanf(fp, "%d", &size);

      All_Zones[znum]->set_size(size);

      fgets(s, sizeof(s), fp);

      while (c != '=') c = getc(fp);
      c = getc(fp);

      fscanf(fp, "%d", &num2);

      All_Zones[znum]->set_rate(num2);

      fgets(s, sizeof(s), fp);

      while (c != '=') c = getc(fp);
      c = getc(fp);

      fscanf(fp, "%d", &num2);

      All_Zones[znum]->set_popsize(num2);

      fgets(s, sizeof(s), fp);
      fgets(s, sizeof(s), fp);

      // READ TERRAIN TYPE HERE
      fgets(s, sizeof(s), fp);

      for (int i=0; i<size; i++)

      {
        c = 'X';
        while (c != ' ') c = getc(fp);
        fscanf(fp, "%d", &room_id);
        fgets(s, sizeof(s), fp);

        create_room(room_id);

        str = ReadLine(fp);

        while (str.find("FLAG") == 0)

        {
          str.erase(0,5);

          All_Rooms[room_id]->set_flag(str);

          str = ReadLine(fp);
        }

        while (str.find("TERRAIN:") == 0)

        {
          str.erase(0,9);

          // SET TERRAIN.

          str = ReadLine(fp);
        }

        if (str.find("BEGIN") == string::npos)
          exit_error("Missing BEGIN in zone file.");

        if (str.find("EXACT") != string::npos) exact = 1;
        else exact = 0;

        str = ReadLine(fp);

        All_Rooms[room_id]->set_title(str);

        str = ReadLine(fp);

        str2 = "";

        while (str.find("END") != 0)

        {
          if (exact) str2 = str2 + str + "\r\n";
          else str2 = str2 + str + " ";

          str = ReadLine(fp);
        }

        if (exact) str2 = str2.substr(0, str2.size()-2);
        else str2 = str2.substr(0, str2.size()-1);

        All_Rooms[room_id]->set_desc(str2, exact);

        exit = getc(fp);

        while (exit == 'L')

        {
          while (c != '_') c = getc(fp);
          c = getc(fp);

          if (c == 'M')

          {
            while (c != ' ')
              c = getc(fp);

            fscanf(fp, "%d", &num);
            fgets(s, sizeof(s), fp);

            All_Zones[znum]->add_mob(pop_counter, num, room_id);
            pop_counter++;

            exit = getc(fp);
          }

          else if (c == 'I')

          {
            while (c != ' ')
              c = getc(fp);

            fscanf(fp, "%d", &num);
            fgets(s, sizeof(s), fp);

            item *new_item = ITEM_LIST->replicate(num, "Booted In Room " + intconvert(num) + ".");

            All_Rooms[room_id]->add_item_room(new_item);

            exit = getc(fp);
          }
        }

        while (exit == 'R')

        {
          string evnt = "";
          int efrq;

          for (int j=1; j<=11; j++)
            getc(fp);

          c = getc(fp);

          while (c != ']')

          {
            evnt += c;
            c = getc(fp);
          }

          getc(fp);

          fscanf(fp, "%d", &efrq);
          fgets(s, sizeof(s), fp);

          All_Rooms[room_id]->add_event(evnt, efrq);

          exit = getc(fp);
        }

        All_Rooms[room_id]->normalize_frequency();

        while ((exit == 'N') || (exit == 'E') || (exit == 'S') ||
               (exit == 'W') || (exit == 'U') || (exit == 'D'))

        {
          if (exit == 'N') space = 5;
          if (exit == 'E') space = 4;
          if (exit == 'S') space = 5;
          if (exit == 'W') space = 4;
          if (exit == 'U') space = 2;
          if (exit == 'D') space = 4;

          for (int j=1; j<=space; j++)
            getc(fp);

          fscanf(fp, "%d", &target);

          All_Rooms[room_id]->add_exit(exit, target);

          fgets(s, sizeof(s), fp);
          exit = getc(fp);
        }

        c = getc(fp);

        while (c == 'L')

        {
          cmdqueue targs;
          int num_targs = 0;

          for (int j=1; j<=4; j++)
            getc(fp);

          fscanf(fp, "%d", &target);

          getc(fp);

          fgets(s, sizeof(s), fp);

          ltarget = s;
          ltarget = ltarget.substr(0, ltarget.size()-1);

          while (ltarget.find(' ') != string::npos)

          {
            int cut = ltarget.find(' ');

            num_targs++;
            targs.put(ltarget.substr(0, cut));
            ltarget = ltarget.substr(cut+1, ltarget.size()-cut-1);
          }

          num_targs++;
          targs.put(ltarget);

          fgets(s, sizeof(s), fp);
          fgets(s, sizeof(s), fp);

          last = s;
          last = last.substr(0, last.length()-1);
          DESC = s;
          DESC = DESC.substr(0, DESC.length()-1);

          done = 0;

          if ((last[0] == 'E') && (last[1] == 'N') && (last[2] == 'D'))
            done = 1;

          while (!done)

          {
            fgets(s, sizeof(s), fp);

            last = s;
            last = last.substr(0, last.length()-1);

            DESC = DESC + " " + last;

            if ((last[0] == 'E') && (last[1] == 'N') && (last[2] == 'D'))
              done = 1;
          }

          str = DESC;
          str = str.substr(0, str.length());

          for (int j=0; j<num_targs; j++)
            All_Rooms[room_id]->add_look(targs.get(), str);

          getc(fp);
          c = getc(fp);
        }
      }

      if (fclose(fp) != 0)
        cout << "Error closing file!\n" << endl;
    }
  }

  cout << "Attaching Zones..." << endl;

  attach_zones();
  set_mobexits();
}

void world::describe()

{
  cout << "Loading Mobiles..." << endl;

  MOB_LIST->load();

  cout << "Loading Items..." << endl;

  ITEM_LIST->load();

  cout << "Loading Boards..." << endl;

  BOARD_LIST->load();
}

void world::attach_zones()

{
  int zone_num;

  for (int i=0; i<world_size; i++)

  {
    if (All_Rooms[i] != NULL)

    {
      zone_num = (All_Rooms[i]->get_id() / 100);

      while (All_Zones[zone_num] == NULL)
        zone_num--;

      All_Rooms[i]->set_zone(All_Zones[zone_num]);
    }
  }
}

void world::set_mobexits()

{
  for (int i=0; i<world_size; i++)

  {
    if (All_Rooms[i] != NULL)
      All_Rooms[i]->set_mobexits();
  }
}

void world::animate()

{
  for (int i=0; i<=max_zone; i++)

  {
    if (All_Zones[i] != NULL)

    {
      for (int j=0; j<All_Zones[i]->get_popsize(); j++)

      {
        if (All_Zones[i]->get_mob(j) != NULL)
        All_Zones[i]->get_mob(j)->consider_action();
      }
    }
  }

  for (int i=0; i<world_size; i++)

  {
    if (All_Rooms[i] != NULL)
      All_Rooms[i]->consider_event();
  }
}

void world::add_decaylist(decay* dp)

{
  decay* *temp = new decay* [decaysize+1];

  for (int i=0; i<decaysize; i++)
    temp[i] = decaylist[i];

  temp[decaysize] = dp;

  delete [] decaylist;
  decaylist = temp;
  decaysize++;
}

void world::remove_decaylist(decay* dp)

{
  int found = 0;
  decay* *temp = new decay* [decaysize-1];

  for (int i=0; i<decaysize; i++)
    if (decaylist[i] != dp)
      temp[i-found] = decaylist[i];
    else found = 1;

  delete [] decaylist;
  decaylist = temp;
  decaysize--;
}

int world::find_decaylist(decay* dp)

{
  for (int i=0; i<decaysize; i++)
    if (decaylist[i] == dp)
      return 1;

  return 0;
}

void world::update_decaylist()

{
  for (int i=0; i<decaysize; i++)

  {
    decaylist[i]->decay_time--;
    if (decaylist[i]->decay_time == 0) {
      decaylist[i]->decay_object(1);
      decaylist[i] = NULL; }
  }

  clean_decaylist();
}

void world::clean_decaylist()

{
  int count = 0;

  for (int i=0; i<decaysize; i++)
    if (decaylist[i] == NULL)
      count++;

  decay* *temp = new decay* [decaysize-count];
  int found = 0;

  for (int i=0; i<decaysize; i++)
    if (decaylist[i] != NULL)
      temp[i-found] = decaylist[i];
    else found++;

  delete [] decaylist;
  decaylist = temp;
  decaysize = decaysize - count;
}