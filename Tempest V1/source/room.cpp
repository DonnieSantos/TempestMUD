#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

room::room()   { InitializeCriticalSection(&CriticalSection); }
room::~room()  { DeleteCriticalSection(&CriticalSection);     }

room::room(int ID)

{
  InitializeCriticalSection(&CriticalSection);

  id          =  ID;
  num_looks   =  0;
  num_people  =  0;
  num_mobs    =  0;
  num_events  =  0;
  num_items   =  0;
  size        =  0;

  look = new string [1];
  look[0] = "";

  ltarget = new string [1];
  ltarget[0] = "";

  LAWFULL    =  0;
  PKOK       =  0;
  NO_MAGIC   =  0;
  REGEN      =  0;
  INDOORS    =  0;
  NO_SUMMON  =  0;
  NO_QUIT    =  0;
  SILENT     =  0;
  PRIVATE    =  0;
  NO_DROP    =  0;
  NO_MOB     =  0;
  DONATION   =  0;

  N = 0;
  E = 0;
  S = 0;
  W = 0;
  U = 0;
  D = 0;

  num_mobexits = 0;

  MC = NULL;
  Item_List = NULL;

  // Instanciate Mob Counter.
  // Set My Zone?
}

void room::set_flag(string str)

{
  if (str == "LAWFULL")   LAWFULL    =  1;
  if (str == "PKOK")      PKOK       =  1;
  if (str == "NO_MAGIC")  NO_MAGIC   =  1;
  if (str == "REGEN")     REGEN      =  1;
  if (str == "INDOORS")   INDOORS    =  1;
  if (str == "NO_SUMMON") NO_SUMMON  =  1;
  if (str == "NO_QUIT")   NO_QUIT    =  1;
  if (str == "SILENT")    SILENT     =  1;
  if (str == "PRIVATE")   PRIVATE    =  1;
  if (str == "NO_DROP")   NO_DROP    =  1;
  if (str == "NO_MOB")    NO_MOB     =  1;
  if (str == "DONATION")  DONATION   =  1;
}

void room::set_title(string s)

{
  title = s;
}

void room::set_desc(string str, int exact)

{
  if (exact) desc = str;

  else {
    str.insert(0, "   ");
    desc = rm_allign(str); }
}

void room::add_exit(char ex, int target)

{
  if (ex == 'N') N = target;
  else if (ex == 'S') S = target;
  else if (ex == 'E') E = target;
  else if (ex == 'W') W = target;
  else if (ex == 'U') U = target;
  else if (ex == 'D') D = target;
}

void room::add_event(string event, int frequency)

{
  num_events++;
  string *temp1 = new string [num_events];
  int *temp2 = new int [num_events];

  for (int i=0; i<num_events-1; i++) {
    temp1[i] = events[i];
    temp2[i] = eventf[i]; }

  temp1[num_events-1] = event;
  temp2[num_events-1] = frequency;

  events = temp1;
  eventf = temp2;
}

void room::set_mobexits()

{
  num_mobexits = 0;

  if (N) if ((World->get_room(N)->get_zone() == my_zone)
  && (World->get_room(N)->get_NO_MOB() == 0)) {
    mobexit[num_mobexits] = N;
    num_mobexits++; }

  if (S) if ((World->get_room(S)->get_zone() == my_zone)
  && (World->get_room(S)->get_NO_MOB() == 0)) {
    mobexit[num_mobexits] = S;
    num_mobexits++; }

  if (E) if ((World->get_room(E)->get_zone() == my_zone)
  && (World->get_room(E)->get_NO_MOB() == 0)) {
    mobexit[num_mobexits] = E;
    num_mobexits++; }

  if (W) if ((World->get_room(W)->get_zone() == my_zone)
  && (World->get_room(W)->get_NO_MOB() == 0)) {
    mobexit[num_mobexits] = W;
    num_mobexits++; }

  if (U) if ((World->get_room(U)->get_zone() == my_zone)
  && (World->get_room(U)->get_NO_MOB() == 0)) {
    mobexit[num_mobexits] = U;
    num_mobexits++; }

  if (D) if ((World->get_room(D)->get_zone() == my_zone)
  && (World->get_room(D)->get_NO_MOB() == 0)) {
    mobexit[num_mobexits] = D;
    num_mobexits++; }
}

void room::add_look(string target, string str)

{
  if ((num_looks >= 6) && (direction(target)))

  {
    if (target[0] == 'n') {
      look[0] = rm_allign(str);
      look[0] = look[0].substr(0, look[0].length()-4); }

    else if (target[0] == 's') {
      look[1] = rm_allign(str);
      look[1] = look[1].substr(0, look[1].length()-4); }

    else if (target[0] == 'e') {
      look[2] = rm_allign(str);
      look[2] = look[2].substr(0, look[2].length()-4); }

    else if (target[0] == 'w') {
      look[3] = rm_allign(str);
      look[3] = look[3].substr(0, look[3].length()-4); }

    else if (target[0] == 'd') {
      look[4] = rm_allign(str);
      look[4] = look[4].substr(0, look[4].length()-4); }

    else if (target[0] == 'u') {
      look[5] = rm_allign(str);
      look[5] = look[5].substr(0, look[5].length()-4); }
  }

  else

  {
    num_looks++;

    string *temp1 = new string [num_looks];
    string *temp2 = new string [num_looks];

    for (int i=0; i<num_looks-1; i++) {
      temp1[i] = look[i];
      temp2[i] = ltarget[i]; }

    temp1[num_looks-1] = rm_allign(str);
    temp2[num_looks-1] = target;

    look = temp1;
    ltarget = temp2;

    if (num_looks > 6)
      look[num_looks-1] = look[num_looks-1].substr(0, look[num_looks-1].length()-4);
  }
}

int room::search_looks(string trg)

{
  for (int i=0; i<num_looks; i++)

  {
    if (ltarget[i] == trg)
      return i;
  }

  return 0;
}

void room::echo(string str)

{
  cslock Lock(&CriticalSection);

  for (int i=0; i<size; i++)
    ENT_LIST[i]->echo(str);
}

void room::xecho(entity *ENT, string str)

{
  cslock Lock(&CriticalSection);

  if (ENT->get_type() == ENTITY_GHOST) return;

  for (int i=0; i<size; i++)
    if (ENT_LIST[i] != ENT)
      ENT_LIST[i]->echo(str);
}

void room::xecho(entity *ENT, entity *ENT2, string str)

{
  cslock Lock(&CriticalSection);

  if (ENT->get_type() == ENTITY_GHOST) return;

  for (int i=0; i<size; i++)
    if ((ENT_LIST[i] != ENT) && (ENT_LIST[i] != ENT2))
      ENT_LIST[i]->echo(str);
}

void room::xecho(entity *ENT, entity *ENT2, entity *ENT3, string str)

{
  cslock Lock(&CriticalSection);

  if (ENT->get_type() == ENTITY_GHOST) return;

  for (int i=0; i<size; i++)
    if ((ENT_LIST[i] != ENT) && (ENT_LIST[i] != ENT2) && (ENT_LIST[i] != ENT3))
      ENT_LIST[i]->echo(str);
}

void room::pblind_echo(entity *ENT, string str)

{
  cslock Lock(&CriticalSection);

  if (ENT->get_type() == ENTITY_GHOST) return;

  for (int i=0; i<size; i++)
    if ((ENT_LIST[i] != ENT) && (ENT_LIST[i]->get_following() != ENT))
      ENT_LIST[i]->echo(str);
}

void room::gblind_echo(entity *ENT, string str)

{
  cslock Lock(&CriticalSection);

  if (ENT->get_type() == ENTITY_GHOST) return;

  for (int i=0; i<size; i++)
    if (ENT->get_group()->in_group(ENT_LIST[i]) == 0)
      ENT_LIST[i]->echo(str);
}

void room::gblind_echo(entity *ENT, entity *silent, string str)

{
  cslock Lock(&CriticalSection);

  if (ENT->get_type() == ENTITY_GHOST) return;

  for (int i=0; i<size; i++)
    if ((ENT->get_group()->in_group(ENT_LIST[i]) == 0) && (ENT_LIST[i] != silent))
      ENT_LIST[i]->echo(str);
}

void room::gblind_echo(entity *ENT, entity *s1, entity *s2, string str)

{
  cslock Lock(&CriticalSection);

  if (ENT->get_type() == ENTITY_GHOST) return;

  for (int i=0; i<size; i++)
    if ((ENT->get_group()->in_group(ENT_LIST[i]) == 0) && (ENT_LIST[i] != s1) && (ENT_LIST[i] != s2))
      ENT_LIST[i]->echo(str);
}

void room::gblind_echo(entity *ENT, entity *s1, entity *s2, entity *s3, string str)

{
  cslock Lock(&CriticalSection);

  if (ENT->get_type() == ENTITY_GHOST) return;

  for (int i=0; i<size; i++)
    if ((ENT->get_group()->in_group(ENT_LIST[i]) == 0) && (ENT_LIST[i] != s1)
    && (ENT_LIST[i] != s2) && (ENT_LIST[i] != s3))
      ENT_LIST[i]->echo(str);
}

void room::gain(entity *ENT)

{
  cslock Lock(&CriticalSection);

  entity* *temp = new entity* [size+1];

  size++;
  if (ENT->PLAYER) num_people++;
  else num_mobs++;

  for (int i=0; i<size-1; i++)
    temp[i] = ENT_LIST[i];

  temp[size-1] = ENT;

  ENT_LIST = temp;
}

void room::loss(entity *ENT)

{
  cslock Lock(&CriticalSection);

  entity* *temp = new entity* [size-1];
  int found = 0;

  size--;
  if (ENT->PLAYER) num_people--;
  else num_mobs--;

  for (int i=0; i<=size; i++)

  {
    if (ENT_LIST[i] != ENT)
      temp[i-found] = ENT_LIST[i];

    else found = 1;
  }

  ENT_LIST = temp;
}

entity* room::get_ent(int n)

{
  cslock Lock(&CriticalSection);

  if (n < size)
    return ENT_LIST[n];

  return NULL;
}

void room::normalize_frequency()

{
  int total = 0;

  for (int i=0; i<num_events-1; i++)
  for (int j=0; j<num_events-1; j++)
  if (eventf[j] > eventf[j+1])

  {
    int temp = eventf[j];
    eventf[j] = eventf[j+1];
    eventf[j+1] = temp;

    string temp2 = events[j];
    events[j] = events[j+1];
    events[j+1] = temp2;
  }

  for (int i=0; i<num_events; i++)

  {
    eventf[i] += total;
    total += eventf[i];
  }
}

void room::consider_event()

{
  if (occupied())

  {
    int acted = 0;
    int n = random_int(0, 60000);

    for (int i=0; i<num_events; i++)

    {
      if (!acted)

      {
        if (eventf[i] >= n) {
          echo(events[i]);
          acted = 1; }
      }
    }
  }
}

void room::examine_say(string str)

{
  cslock Lock(&CriticalSection);

  for (int i=0; i<size; i++)
    if ((!ENT_LIST[i]->PLAYER) && (ENT_LIST[i]->get_client() == NULL))
      ENT_LIST[i]->examine_say(str);
}

entity* room::find_entity(string target)

{
  cslock Lock(&CriticalSection);

  int target_num = clip_number(&target);

  for (int i=0; i<size; i++)

  {
    if (ENT_LIST[i]->PLAYER)

    {
      if ((ENT_LIST[i]->check_targets(target))
      && (ENT_LIST[i]->get_client()->get_state() != CSTATE_LOGIN))
        target_num--;

      if (!target_num) return ENT_LIST[i];
    }

    else

    {
      if (ENT_LIST[i]->check_targets(target))
        target_num--;

      if (!target_num) return ENT_LIST[i];
    }
  }

  return NULL;
}

entity* room::find_entity(entity *SRC, string target)

{
  cslock Lock(&CriticalSection);

  int target_num = clip_number(&target);

  if ((SRC->get_room() == this) && (SRC->check_targets(target))) {
    if (target_num == 1) return SRC;
    target_num--; }

  for (int i=0; i<size; i++)
  if (ENT_LIST[i]->PLAYER)

  {
    if ((ENT_LIST[i]->check_targets(target))
    && (ENT_LIST[i]->get_client()->get_state() != CSTATE_LOGIN)
    && (ENT_LIST[i] != SRC))
      target_num--;

    if (!target_num) return ENT_LIST[i];
  }

  for (int i=0; i<size; i++)
  if (!ENT_LIST[i]->PLAYER)

  {
    if ((ENT_LIST[i]->check_targets(target))
    && (ENT_LIST[i] != SRC))
      target_num--;

    if (!target_num) return ENT_LIST[i];
  }

  return NULL;
}

entity* room::find_character(string target)

{
  cslock Lock(&CriticalSection);

  for (int i=0; i<size; i++)
    if ((ENT_LIST[i]->get_name() == target) && (ENT_LIST[i]->PLAYER))
      return ENT_LIST[i];

  return NULL;
}

int room::find_num_entities(string target)

{
  cslock Lock(&CriticalSection);

  int num_ents = 0;

  for (int i=0; i<size; i++)

  {
    if (ENT_LIST[i]->PLAYER) {
      if ((ENT_LIST[i]->check_targets(target))
      && (ENT_LIST[i]->get_client()->get_state() != CSTATE_LOGIN))
        num_ents++; }

    else if (ENT_LIST[i]->check_targets(target))
      num_ents++;
  }

  return num_ents;
}

string room::get_look_titles(entity *SRC)

{
  cslock Lock(&CriticalSection);

  string tspace, tempstr = "", mob_fighters = "";

  if (MC == NULL)
    MC = new mobcounter(World->get_moblist());

  for (int i=0; i<size; i++)

  {
    if (ENT_LIST[i]->PLAYER) {
    if ((ENT_LIST[i] != SRC) && (ENT_LIST[i]->get_client()->get_state() != CSTATE_LOGIN))

    {
      tspace = " ";
      if (visible_size(ENT_LIST[i]->get_title()) < 1) tspace = "";

      tempstr += "\r\n" + ENT_LIST[i]->get_prename() + "#C" + ENT_LIST[i]->get_name();

      if (ENT_LIST[i]->get_playerstate() == PSTATE_FIGHTING)

      {
        entity *target = ENT_LIST[i]->get_target();
        string tname = "";
        if (target != NULL) tname = target->get_name();

        if (target == SRC) tname = "YOU";

        if (target != NULL) tempstr += "#N is here fighting " + tname + "#N!";
        else tempstr += "#N " + ENT_LIST[i]->get_title() + tspace + "#Nis standing here.";
      }

      else tempstr += "#N " + ENT_LIST[i]->get_title() + tspace + "#Nis standing here.";

      if (ENT_LIST[i]->get_client()->get_state() == CSTATE_LINKDEAD)
      tempstr += " #N(#Rlinkdead#N)";

      if (ENT_LIST[i]->get_client()->get_state() == CSTATE_WRITING)
      tempstr += " #N(#Gwriting#N)";
    } }

    else if (ENT_LIST[i] != SRC)

    {
      if (ENT_LIST[i]->get_playerstate() == PSTATE_FIGHTING)

      {
        entity *target = ENT_LIST[i]->get_target();
        string tname = "";
        if (target != NULL) tname = target->get_name();

        if (target == SRC) tname = "YOU";

        if (target != NULL) mob_fighters += "\r\n#Y" + ENT_LIST[i]->get_name() + "#Y is here #Nfighting " + tname + "#N!";
        else MC->add(ENT_LIST[i]->get_info()->vnum);
      }

      else MC->add(ENT_LIST[i]->get_info()->vnum);
    }
  }

  tempstr += mob_fighters;
  tempstr += MC->stack();

  return tempstr;
}

string room::get_scout_names(int dist, string dir)

{
  cslock Lock(&CriticalSection);

  string tempstr = "";

  if (MC == NULL)
    MC = new mobcounter(World->get_moblist());

  if (dist == 1) {
    if (dir == "N") tempstr += "\r\n  To the north:";
    else if (dir == "S") tempstr += "\r\n  To the south:";
    else if (dir == "E") tempstr += "\r\n  To the east:";
    else if (dir == "W") tempstr += "\r\n  To the west:"; }

  for (int i=0; i<size; i++)

  {
    if (ENT_LIST[i]->PLAYER) {
    if (ENT_LIST[i]->get_client()->get_state() != CSTATE_LOGIN)
      tempstr += "\r\n    " + intconvert(dist) + " - " + ENT_LIST[i]->get_name(); }

    else MC->add(ENT_LIST[i]->get_info()->vnum);
  }

  tempstr += MC->short_stack(dist);

  return tempstr;
}

string room::check_blockers(entity *ENT, char dir)

{
  cslock Lock(&CriticalSection);

  for (int i=0; i<num_items; i++)
  if (DC_Blocker(Item_List[i]) != NULL)
  if (DC_Blocker(Item_List[i])->get_bdir() == dir)

  {
    if (DC_Blocker(Item_List[i])->try_pass(ENT) == 0)
      return DC_Blocker(Item_List[i])->error_msg();
  }

  for (int i=0; i<size; i++)
  if (ENT_LIST[i]->get_blocker() != NULL)
  if (ENT_LIST[i]->get_blocker()->get_bdir() == dir)

  {
    if (ENT_LIST[i]->get_blocker()->try_pass(ENT) == 0)
      return ENT_LIST[i]->get_blocker()->error_msg();
  }

  return "OK";
}

int room::get_groupsize(group *the_group)

{
  cslock Lock(&CriticalSection);

  int count = 0;

  for (int i=0; i<size; i++)
    if (the_group->in_group(ENT_LIST[i]) == 1)
      count++;

  return count;
}

int* room::distance(room *loc)

{
  int index, size, new_rooms[10000];
  intarray *IRA = new intarray();
  intarray *DIR = new intarray();
  int found = 1, i = 0;

  int *dist_and_dir = new int [2];

  if (loc == this) return 0;

  IRA->put(id);
  DIR->put(0);

  while (1)

  {
    i++;
    dist_and_dir[0] = i;

    if (!found) return NULL;
    found = 0;

    if (i < 3) index = i - 1;
    else index += new_rooms[i-2];

    size = IRA->size();
    new_rooms[i] = 0;

    for (int j=index; j<size; j++)

    {
      room *current_room = World->get_room(IRA->get(j));

      if (current_room->get_N())
      if (!IRA->search(current_room->get_N()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_N());
        if (i == 1) DIR->put(1);
        else DIR->put(DIR->get(IRA->search(current_room->get_id())-1));
        if (World->get_room(current_room->get_N()) == loc) {
          dist_and_dir[1] = DIR->get(DIR->size()-1);
          return dist_and_dir; }
      }

      if (current_room->get_S())
      if (!IRA->search(current_room->get_S()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_S());
        if (i == 1) DIR->put(2);
        else DIR->put(DIR->get(IRA->search(current_room->get_id())-1));
        if (World->get_room(current_room->get_S()) == loc) {
          dist_and_dir[1] = DIR->get(DIR->size()-1);
          return dist_and_dir; }
      }

      if (current_room->get_E())
      if (!IRA->search(current_room->get_E()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_E());
        if (i == 1) DIR->put(3);
        else DIR->put(DIR->get(IRA->search(current_room->get_id())-1));
        if (World->get_room(current_room->get_E()) == loc) {
          dist_and_dir[1] = DIR->get(DIR->size()-1);
          return dist_and_dir; }
      }

      if (current_room->get_W())
      if (!IRA->search(current_room->get_W()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_W());
        if (i == 1) DIR->put(4);
        else DIR->put(DIR->get(IRA->search(current_room->get_id())-1));
        if (World->get_room(current_room->get_W()) == loc) {
          dist_and_dir[1] = DIR->get(DIR->size()-1);
          return dist_and_dir; }
      }

      if (current_room->get_U())
      if (!IRA->search(current_room->get_U()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_U());
        if (i == 1) DIR->put(5);
        else DIR->put(DIR->get(IRA->search(current_room->get_id())-1));
        if (World->get_room(current_room->get_U()) == loc) {
          dist_and_dir[1] = DIR->get(DIR->size()-1);
          return dist_and_dir; }
      }

      if (current_room->get_D())
      if (!IRA->search(current_room->get_D()))

      {
        found = 1;
        new_rooms[i]++;
        IRA->put(current_room->get_D());
        if (i == 1) DIR->put(6);
        else DIR->put(DIR->get(IRA->search(current_room->get_id())-1));
        if (World->get_room(current_room->get_D()) == loc) {
          dist_and_dir[1] = DIR->get(DIR->size()-1);
          return dist_and_dir; }
      }
    }
  }
}

void room::prompt_all()

{
  for (int i=0; i<size; i++)
  if (ENT_LIST[i]->get_client() != NULL)

  {
    string p = ENT_LIST[i]->get_client()->prompt();

    if (!ENT_LIST[i]->get_client()->get_ANSI_MODE())
      ENT_LIST[i]->echo("\r\n" + p + "\r\n");
    else ENT_LIST[i]->echo("");
  }
}