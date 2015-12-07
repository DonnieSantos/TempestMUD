#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

client::client()

{
  charinfo_backup = new character();
  charinfo = charinfo_backup;

  charinfo->set_client(this);

  ld = 0;
  ld_time = 0;
  state = 1;
  creation = 0;
  commandstring = "";
  last_command = "XXX";
  idle = 0;
  active = 0;
  ip_a = "";

  command_lock = 0;
  command_lock_timer = 0;
  time_connected = clock();
  time_lastcommand = clock();

  ANSI_MODE = 0;
  ansicolor = 1;
  screen_size = 25;
  max_screen_size = MAX_SCREEN_SIZE;

  last_login = get_time();
}

string client::put_command(string s)     

{
  time_lastcommand = clock(); 
  return CQ.put(s);     
}

string client::put_output(string s)      { return OQ.put(s);     }
string client::put_foutput(string s)     { return FQ.put(s);     }
string client::force_command(string s)   { return CQ.force(s);   }
void client::set_charinfo(entity *ENT)   { charinfo = ENT;       }
void client::set_command_lock(double d)  { command_lock = d;     }
void client::set_ip(char* str)           { ip_a = string(str);   }

string client::get_idle_time()

{
  string temp = intconvert(idle);
  if (idle < 10) temp = "0" + temp;
  return temp;
}

void client::charinfo_save()

{
  charinfo_backup = charinfo;
}

void client::charinfo_restore()

{
  charinfo = charinfo_backup;
  charinfo->set_client(this);
}

void client::flush_commands()

{
  if (charinfo != NULL)
    charinfo->set_intermittent_skill(0);

  CQ.flushbuffer();
}

void client::update_command_lock(double elapsed)

{
  command_lock_timer += elapsed;

  if (command_lock_timer >= command_lock) {
    command_lock = 0;
    command_lock_timer = 0; }
}

void client::update_idle()

{
  if (!active) idle++;
  else idle = 0;

  active = 0;
}

void client::getbufferdata()

{
  char* temp = new char;
  SOCKET returnvalue;
  int rv;

  returnvalue = recv(clientsocket, temp, 1, 0);

  if ((returnvalue != 0) && (returnvalue != -1))

  {
    if ((temp[0] == '\n') || (temp[0] == '\r'))

    {
      if (commandstring != "")
      if ((state == CSTATE_NORMAL) || (state == CSTATE_POSSESSION))
      if ((commandstring[0] != '!') && (commandstring[0] != 92))
        last_command = commandstring;

      idle = 0;
      active++;

      put_command(commandstring);
      World->get_log()->add_log(this, commandstring);
      commandstring = "";

      if (temp[0] == '\r') recv(clientsocket, temp, 1, 0);
    }

    else if (((int)temp[0] >= 32 && (int)temp[0] <= 122))
      commandstring += temp[0];
    else if ((int)temp[0] == 8 && commandstring.size() > 0)
      commandstring.erase(commandstring.size() - 1, 1);
  }

  else if (returnvalue == -1)

  {
    rv = WSAGetLastError();
    if (rv != WSAEWOULDBLOCK)
      enter_ld_state();
  }

  else if (returnvalue == 0)
    enter_ld_state();

  delete temp;
}

void client::screen_resize(string num_lines)

{
  if (!number(num_lines)) {
    put_output("#NResize Usage#n:#N #R[#Nresize num_lines#R]#N");
    return; }

  screen_size = stringconvert(num_lines);

  if (screen_size > MAX_SCREEN_SIZE)
    screen_size = MAX_SCREEN_SIZE;

  clear_screen(0);

  put_output("#NScreen size is now #n" + intconvert(screen_size) + "#N lines.#N");
}

void client::clear_screen(int forced)

{
  string cls = "\33[1;1H\33[0J";

  if ((ANSI_MODE) || (forced)) {
    char *temp = (char*)cls.c_str();
    send(clientsocket, temp, cls.size(), 0); }

  if (forced == 2) {
    if (ANSI_MODE) draw_ansibar();
    else msg(prompt()); }
}

void client::clear_ansibar()

{
  string home        =  "\33[" + intconvert(screen_size) + ";1H";
  string above       =  "\33[" + intconvert(screen_size-4) + ";1H";
  string sml_margin  =  "\33[1;" + intconvert(screen_size-4) + "r";
  string rst_origin  =  "\33[?6l";
  string cr_off      =  "\33[20l";
  string wrap        =  "\33[?7l";
  string cln         =  "\33[0J";

  string s = cr_off + wrap + rst_origin + sml_margin + above + cln;

  char *temp = (char*)s.c_str();
  send(clientsocket, temp, s.size(), 0);
}

string client::get_ansibar()

{
  int hp1, hp2, mn1 = 0, mn2, mv1 = 0, mv2;

  hp1 = (int)(((float)charinfo->get_current_hp() / (float)charinfo->get_max_hp()) * (float)10);

  if (charinfo->PLAYER) {
    mn1 = (int)(((float)charinfo->get_current_mana() / (float)charinfo->get_max_mana()) * (float)10);
    mv1 = (int)(((float)charinfo->get_current_move() / (float)charinfo->get_max_move()) * (float)10); }

  if (hp1 < 0) hp1 = 0;  if (hp1 > 10) hp1 = 10;  hp2 = 10 - hp1;
  if (mn1 < 0) mn1 = 0;  if (mn1 > 10) mn1 = 10;  mn2 = 10 - mn1;
  if (mv1 < 0) mv1 = 0;  if (mv1 > 10) mv1 = 10;  mv2 = 10 - mv1;

  string lifebar = "#n[";
  for (int i=1; i<=hp1; i++) lifebar += "#g=";
  for (int i=1; i<=hp2; i++) lifebar += "#N-";
  lifebar += "#n] #N";

  string manabar = "#n[";
  for (int i=1; i<=mn1; i++) manabar += "#m=";
  for (int i=1; i<=mn2; i++) manabar += "#N-";
  manabar += "#n] #N";

  string movebar = "#n[";
  for (int i=1; i<=mv1; i++) movebar += "#b=";
  for (int i=1; i<=mv2; i++) movebar += "#N-";
  movebar += "#n] #N";

  string bar  = "#N+----------------------------------------+--------------------------------+#N\r\n";
  bar += "#N| " + lifebar + manabar + movebar + "#N|                                #N|#N\r\n";
  bar += "#N+----------------------------------------+--------------------------------+#N";

  return colorize(bar, ansicolor);
}

void client::draw_ansibar()

{
  if (state == CSTATE_WRITING) return;

  string home        =  "\33[" + intconvert(screen_size) + ";1H";
  string corner      =  "\33[" + intconvert(screen_size-3) + ";1H";
  string big_margin  =  "\33[1;" + intconvert(screen_size+1) + "r";
  string sml_margin  =  "\33[1;" + intconvert(screen_size-4) + "r";
  string rst_origin  =  "\33[?6l";
  string cr_off      =  "\33[20l";
  string csave       =  "\33[7\33[025l";
  string cload       =  "\33[8\33[025h";
  string wrap        =  "\33[?7l";
  string cln         =  "\33[0J";

  string s = cr_off + wrap + rst_origin + big_margin + corner + get_ansibar() + sml_margin + home + cln;

  char *temp = (char*)s.c_str();
  send(clientsocket, temp, s.size(), 0);
}

void client::msg(string s)

{
  string home        =  "\33[" + intconvert(screen_size) + ";1H";
  string above       =  "\33[" + intconvert(screen_size-5) + ";1H";
  string sml_margin  =  "\33[1;" + intconvert(screen_size-5) + "r";
  string rst_origin  =  "\33[?6l";
  string cr_off      =  "\33[20l";
  string wrap        =  "\33[?7l";
  string cln         =  "\33[0J";

  if (ANSI_MODE) {
    format_ansi_output(&s);
    s = cr_off + wrap + rst_origin + sml_margin + above + colorize(s, ansicolor) + home + cln; }

  else s = colorize(s, ansicolor);

  while (s.length() > 2000)

  {
    string cut = s.substr(0, 2000);
    s.erase(0, 2000);
    send(clientsocket, (char*)cut.c_str(), cut.size(), 0);
    Sleep(50);
  }

  send(clientsocket, (char*)s.c_str(), s.size(), 0);

  if (ANSI_MODE) draw_ansibar();
}

void client::disconnect(int disc_type)

{
  if (disc_type == 1) shutdown(clientsocket, 1);

  if (disc_type == 0)

  {
    shutdown(clientsocket, 1);
    clist->remove(this);
  }
}

string client::prompt_private()

{
  int flag = 0;

  while (!flag)
  {
    Sleep(10);
    getbufferdata();

    if (ld == 1) return "CLIENT_ENTERED_LD_STATE";
    else if (get_Qsize() > 0) flag = 1;
    else if (get_Osize() > 0) get_output();
  }

  return CQ.get();
}

void client::enter_ld_state()

{
  if (state != CSTATE_LOGIN)

  {
    charinfo->unpossess(0);
    state = CSTATE_LINKDEAD;
    ld = 1;
    ld_time_prev = clock();
    clist->syslog_gecho("Closing link to " + charinfo->get_name() + ".", 100, get_ip());
    charinfo->get_room()->xecho(charinfo, charinfo->get_name() + " has lost " + charinfo->get_his() + " link.");
    string log_string = get_ip() + " " + get_char()->get_name() + " went linkdead in-game.";
    World->get_log()->add_log(NULL, log_string);
  }

  else if (creation == 1)

  {
    disconnect(0);
    File->delete_profile(charinfo->get_name());
    clist->syslog_gecho("CREATION: Closing link to " + charinfo->get_name() + ".", 100, get_ip());
    string log_string = get_ip() + " " + get_char()->get_name() + " went linkdead during creation.";
    World->get_log()->add_log(NULL, log_string);
    delete this;
    _endthread();
  }

  else

  {
    disconnect(0);
    clist->syslog_gecho("LOGIN: Closing link to " + charinfo->get_name() + ".", 100, get_ip());
    string log_string = get_ip() + " " + get_char()->get_name() + " went linkdead during login.";
    World->get_log()->add_log(NULL, log_string);
    delete this;
    _endthread();
  }
}

void client::update_ld_state()

{
  double elapsed;

  clock_t ld_time_new = clock();
  elapsed = (double)(ld_time_new - ld_time_prev) / CLOCKS_PER_SEC;
  ld_time_prev = ld_time_new;
  ld_time += elapsed;

  if (ld_time >= 120)  {
    charinfo->Drop();
    disconnect(0); }
}

int client::attempt_password(string name)

{
  char echo_off[3] = { IAC, WILL, ECHO };
  char echo_on[3]  = { IAC, WONT, ECHO };

  if (clist->find_client(name) == NULL)

  {
    clist->syslog_gecho(name + " connected to server.", 100, get_ip());
    charinfo->set_name(name);
    charinfo->set_level(File->get_level(name));
    charinfo->set_class(File->get_class(name));
  }

  string password = File->get_password(name);

  send(clientsocket, echo_off, 3, 0);
  msg(" Password: ");
  string attempt = prompt_private();
  send(clientsocket, echo_on, 3, 0);

  if (attempt == password)

  {
    charinfo->set_name(name);
    File->load_profile(this);
    return 0;
  }

  clist->syslog_gecho("Bad password on " + name + ".", 100, get_ip());

  disconnect(0);
  _endthread();

  return 1;
}

int client::attempt_create(string name)

{
  File->create_placeholder(name);
  creation = 1;
  charinfo->set_name(name);

  msg(" Unknown alias, create this character(Y/N)? ");
  string answer = prompt_private();

  if ((answer[0] == 'Y') || (answer[0] ==  'y')) {
    create_character(name);
    return 0; }

  creation = 0;
  charinfo->set_name("LOGGING IN");
  File->delete_profile(name);

  return 1;
}

int client::valid_name(string name)

{
  if (last(name) != "") {
    msg(" Invalid name.  Names must be one word.\r\n");
    return 0; }

  if (name.size() > 12) {
    msg(" Invalid name.  Names must be, at most, twelve letters.\r\n");
    return 0; }

  return 1;
}

void client::login()

{
  int rv = 1;
  string name;
  client* check;

  msg("\r\n" + title_screen() + "\r\n\r\n");

  while (rv)

  {
    msg(" Enter your name: ");
    name = proper_name(prompt_private());

    if (valid_name(name))

    {
      int exist_state = File->char_exist(name);

      if (exist_state == 1)
        rv = attempt_password(name);
      else if (exist_state == 2)
        msg(" That character is under creation.\r\n");
      else rv = attempt_create(name);
    }
  }

  check = clist->find_client(name, this);

  if (check == NULL)

  {
    clist->syslog_gecho(charinfo->get_name() + " entering game.", 100, get_ip());
    state = CSTATE_NORMAL;
    clear_screen(0);
    charinfo->get_room()->xecho(charinfo, charinfo->get_name() + " has entered the game.");
    charinfo->look("\r\n#cWelcome To The Tempest!");
  }

  else reconnect(check);
}

void client::create_character(string name)

{
  string datarecvd;

  clist->syslog_gecho("New character " + name + " connected to server.", 100, get_ip());

  File->create_placeholder(name);

  charinfo->set_name(name);
  charinfo->set_prename("", 0);
  charinfo->set_desc(charinfo->get_name() + " is standing here, looking for program bugs.");

  msg(" Enter a password for this character: ");
  datarecvd = prompt_private();
  charinfo->set_password(datarecvd);

  msg(" Do you want ansi color? ");
  datarecvd = prompt_private();
  if ((datarecvd[0] == 'Y') || (datarecvd[0] ==  'y')) ansicolor = 1;
  else ansicolor = 0;

  datarecvd = "X";

  while ((datarecvd[0] != 'f') && (datarecvd[0] != 'F') && (datarecvd[0] != 'm') && (datarecvd[0] != 'M'))

  {
    string gender_display = "\n #NChoose your gender:\r\n\n  (#bM#N) - Male\r\n  (#yF#N) - Female\r\n\n";
           gender_display += "  Select ---> ";

    msg(gender_display);
    datarecvd = prompt_private();
  }

  if ((datarecvd[0] == 'F') || (datarecvd[0] == 'f')) charinfo->set_gender("female");
  else charinfo->set_gender("male");

  datarecvd = "X";

  while ((datarecvd[0] != 'W') && (datarecvd[0] != 'w') && (datarecvd[0] != 'T') && (datarecvd[0] != 't')
  && (datarecvd[0] != 'C') && (datarecvd[0] != 'c') && (datarecvd[0] != 'M') && (datarecvd[0] != 'm')) {
    string startclass  = "\n #NChoose your starting profession:\r\n\n  (#BWa#N) - Warrior\r\n  (#YTh#N) - Thief\r\n  ";
           startclass += "(#GCl#N) - Cleric\r\n  (#MMg#N) - Mage\r\n\n  Select ---> ";
    msg(startclass);
    datarecvd = prompt_private(); }

  if ((datarecvd[0] == 'W') || (datarecvd[0] == 'w')) {
    charinfo->set_class("Warrior");
    charinfo->set_max_hp(210);
    charinfo->set_max_mana(45);
    charinfo->set_max_move(190);
    charinfo->restore();
    charinfo->set_STR(5);
    charinfo->set_DEX(2);
    charinfo->set_CON(3);
    charinfo->set_INT(0);
    charinfo->set_WIS(0); }
  else if ((datarecvd[0] == 'T') || (datarecvd[0] == 't')) {
    charinfo->set_class("Thief");
    charinfo->set_max_hp(140);
    charinfo->set_max_mana(70);
    charinfo->set_max_move(135);
    charinfo->restore();
    charinfo->set_STR(2);
    charinfo->set_DEX(5);
    charinfo->set_CON(2);
    charinfo->set_INT(1);
    charinfo->set_WIS(0); }
  else if ((datarecvd[0] == 'C') || (datarecvd[0] == 'c')) {
    charinfo->set_class("Cleric");
    charinfo->set_max_hp(120);
    charinfo->set_max_mana(110);
    charinfo->set_max_move(120);
    charinfo->restore();
    charinfo->set_STR(1);
    charinfo->set_DEX(1);
    charinfo->set_CON(1);
    charinfo->set_INT(2);
    charinfo->set_WIS(5); }
  else if ((datarecvd[0] == 'M') || (datarecvd[0] == 'm')) {
    charinfo->set_class("Mage");
    charinfo->set_max_hp(95);
    charinfo->set_max_mana(220);
    charinfo->set_max_move(100);
    charinfo->restore();
    charinfo->set_STR(0);
    charinfo->set_DEX(0);
    charinfo->set_CON(0);
    charinfo->set_INT(6);
    charinfo->set_WIS(4); }
  datarecvd[0] = 'X';
  while ((datarecvd[0] != '1') && (datarecvd[0] != '2') && (datarecvd[0] != '3') && (datarecvd[0] != '4')) {
    string longname  = "\r\n #NChoose your hometown:\r\n\n  (1) - #CHydecka#N   --  The Warrior City-State.\r\n  ";
           longname += "(2) - #nAlmeccia#N  --  The Holy Kingdom.\r\n  (3) - #BRavenna#N   --  The Shadowlands.\r\n  ";
           longname += "(4) - #mVolaris#N   --  Mage Country.\r\n\n  Select ---> ";
    msg(longname);
    datarecvd = prompt_private(); }

  if (datarecvd[0] == '1') charinfo->set_hometown("#CHydecka#N");
  else if (datarecvd[0] == '2') charinfo->set_hometown("#nAlmeccia#N");
  else if (datarecvd[0] == '3') charinfo->set_hometown("#BRavenna#N");
  else if (datarecvd[0] == '4') charinfo->set_hometown("#mVolaris#N");

  File->save_profile(this);

  creation = 0;
}

string client::prompt()

{
  string str;

  if (get_char()->PLAYER)

  str = "#c< #g" + intconvert(get_char()->get_current_hp())
        + "#Ghp #m"
        + intconvert(get_char()->get_current_mana())
        + "#Mmn #b"
        + intconvert(get_char()->get_current_move())
        + "#Bmv #c>#N ";

  else

  str = "#c< #n" + get_char()->get_name()
      + " #g" + intconvert(get_char()->get_current_hp())
      + "#Ghp #c>#N ";

  return str;
}

void client::reconnect(client* aclient)

{
  client* checker;

  checker = clist->find_client(charinfo->get_name(), this);

  if (checker != NULL)

  {
    clist->syslog_gecho(aclient->get_char()->get_name() + " has reconnected.", 100, get_ip());

    shutdown(aclient->get_clientsocket(), 1);
    aclient->set_clientsocket(clientsocket);
    aclient->set_ld(0);
    aclient->set_state(CSTATE_NORMAL);
    aclient->get_Oqueue()->flushbuffer();
    aclient->clear_screen(0);
    aclient->get_char()->look("#cYou reassume your body!#N");
    clist->remove(this);
    string log_string = get_ip() + " reconnected to " + aclient->get_ip() + " " + aclient->get_char()->get_name() + ".";
    World->get_log()->add_log(NULL, log_string);
    aclient->get_char()->blind_emote("has reconnected.");

    _endthread();
  }
}

void client::toggle(string str)

{
  string max_margin  =  "\33[r";
  string cr_on       =  "\33[20h";

  if (match(str, "a", "ansi"))

  {
    ANSI_MODE = 1 - ANSI_MODE;

    if (!ANSI_MODE) msg(cr_on);
    if (!ANSI_MODE) msg(max_margin);

    clear_screen(1);

    if (ANSI_MODE) put_output("ANSI Display is now enabled.");
    else put_output("ANSI Display is now disabled.");
  }

  else if (match(str, "c", "color")) {
    ansicolor = 1 - ansicolor;
    if (ansicolor) put_output("ANSI Color is now enabled.");
    else put_output("ANSI Color is now disabled."); }

  else if (match(str, "f", "follow")) {
    charinfo->toggle_follow();
    if (charinfo->get_followable()) put_output("You can now be followed.");
    else put_output("You can no longer be followed."); }

  else if (match(str, "a", "assist")) {
    charinfo->toggle_assist();
    if (charinfo->get_autoassist()) put_output("You will now automatically assist group members.");
    else put_output("You will no longer automatically assist group members."); }

  else

  {
    string toginfo;
    string enabled = "#gEnabled#N ";
    string disabled = "#rDisabled#N";

    toginfo  = "  #n+-------------------------+\r\n  |     #yToggle Settings#n     |\r\n";
    toginfo += "  | #N----------------------#n  |\r\n";

    toginfo += "  | #R[#Ntog ansi#R]#N     ";
      if (ANSI_MODE) toginfo += enabled;  else toginfo += disabled;

    toginfo += " #n|\r\n  | #R[#Ntog color#R]#N    ";
      if (ansicolor) toginfo += enabled;  else toginfo += disabled;

    toginfo += " #n|\r\n  | #R[#Ntog follow#R]#N   ";
      if (charinfo->get_followable()) toginfo += enabled;  else toginfo += disabled;

    toginfo += " #n|\r\n  | #R[#Ntog assist#R]#N   ";
      if (charinfo->get_autoassist()) toginfo += enabled;  else toginfo += disabled;

    toginfo += " #n|\r\n  +-------------------------+";

    put_output(toginfo);
  }
}

void client::flush_output()

{
  flush_fight();
  flush_normal();
}

void client::flush_normal()

{
  if (OQ.get_size() <= 0) return;

  string top_space = "\r\n\n";
  if (active) top_space = "\r\n";

  string normal_output = OQ.flush() + "\r\n\n";
  if (!ANSI_MODE) normal_output = top_space + normal_output;

  msg(normal_output);
  if (!ANSI_MODE) msg(prompt());

  active = 0;
}

void client::flush_fight()

{
  if (FQ.get_size() <= 0) return;

  string top_space = "\r\n\n";
  if (active) top_space = "\r\n";

  string fight_output = FQ.flush() + "\r\n\n";
  if (!ANSI_MODE) fight_output = top_space + fight_output;

  msg(fight_output);
  if (!ANSI_MODE) msg(prompt());

  active = 0;
}