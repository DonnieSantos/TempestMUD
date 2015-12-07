#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

// ********************************************************************************************************* //
// ****************************************** COMMAND INTERPRETER ****************************************** //
// ********************************************************************************************************* //

void entity::interpret_command(string command)

{
  string key = "X", rem = "X", Rem = "X", frem = "X";
  int key_size, rem_size;
  int command_size;

  command = clear_whitespace(command);

  if (my_client != NULL) cout << name << ": [" << command << "]" << endl;

  if (command[0] == 39)       { command.erase(0,1); command = "say " + command;   }
  else if (command[0] == 34)  { command.erase(0,1); command = "tell " + command;  }
  else if (command[0] == 45)  { command.erase(0,1); command = "chat " + command;  }
  else if (command[0] == ';') { command.erase(0,1); command = "ctell " + command; }
  else if (command[0] == ':') { command.erase(0,1); command = "emote " + command; }

  command = clear_whitespace(command);

  if ((my_client != NULL) && ((command[0] == '!') || (command[0] == 92)))

  {
    string last_command = my_client->get_last_command();
    char indicator = clear_whitespace(last_command)[0];
    if ((indicator == '!') || (indicator == 92)) echo("Invalid Command.");
    else interpret_command(my_client->get_last_command());
  }

  else if ((letter(command[0])) || (number(command[0])))

  {
    lock_commands(200);

    if (Aliases != NULL)
      Aliases->apply_aliasing(&command);

    if (first(lowercase(command)) != "alias")
      process_multiple_commands(&command);

    command_size = command.size();

    key = first(command);
    key = lowercase(key);
    key_size = key.size();

    // *************************************************************************************************************** //
    // ********************************************* SINGLE KEY COMMANDS ********************************************* //
    // *************************************************************************************************************** //

    if (key_size == command_size)

    {
      if (match(key, "sa", "say")) echo("What do you want to say?");
      else if (match(key, "ch", "chat")) echo("What do you want to chat?");
      else if (match(key, "te", "tell")) echo("Who do you want to tell?");
      else if (match(key, "ct", "ctell")) ctell("");
      else if (match(key, "l", "look")) look("");
      else if (match(key, "use", "use")) use("What do you want to use?");
      else if (match(key, "we", "we")) speedwalk(key);
      else if (match(key, "n", "north")) move("N");
      else if (match(key, "s", "south")) move("S");
      else if (match(key, "e", "east")) move("E");
      else if (match(key, "w", "west")) move("W");
      else if (match(key, "d", "down")) move("D");
      else if (match(key, "u", "up")) move("U");
      else if (speedwalk_legal(key)) speedwalk(key);
      else if (match(key, "st", "stats")) stat(name);
      else if (match(key, "wh", "who")) echo(clist->who());
      else if (match(key, "me", "me")) echo("Emote Usage#n: #R[#Nemote action#R] [#Nme action#R] [#N:action#R]#N");
      else if (match(key, "em", "emote")) echo("Emote Usage#n: #R[#Nemote action#R] [#Nme action#R] [#N:action#R]#N");
      else if (match(key, "k", "kill")) echo("Who do you want to attack?");
      else if (match(key, "att", "attack")) echo("Who do you want to attack?");
      else if (match(key, "i", "inventory")) display_inventory();
      else if (match(key, "i", "information")) echo(information());
      else if (match(key, "c", "cast")) echo("What spell do yo want to cast?");
      else if (match(key, "gr", "group")) my_group->display_group(this);
      else if (match(key, "dis", "disband")) my_group->remove_all(this);
      else if (match(key, "un", "ungroup")) echo("Who do you want to ungroup?");
      else if (match(key, "a", "assist")) echo("Who do you want to assist?");
      else if (match(key, "f", "flee")) flee();
      else if (match(key, "sk", "skills")) display_skills();
      else if (match(key, "sp", "spells")) display_spells();
      else if (match(key, "sac", "sacrifice")) echo("What do you want to sacrifice?");
      else if (match(key, "fi", "finger")) echo("Whom do you want to finger?");
      else if (match(key, "fol", "follow")) echo("Who do you want to follow?");
      else if (match(key, "dit", "ditch")) echo("Who do you want to ditch?");
      else if (match(key, "ge", "get")) echo("What do you want to get?");
      else if (match(key, "co", "count")) num_item_info();
      else if (match(key, "re", "read")) echo("What do you want to read?");
      else if (match(key, "wr", "write")) echo("What do you want to write?");
      else if (match(key, "rem", "remove")) echo("What do you want to remove?");
      else if (match(key, "giv", "give")) echo("What do you want to give away?");
      else if (match(key, "dr", "drop")) echo("What do you want to drop?");
      else if (match(key, "bu", "buy")) buy_item("");
      else if (match(key, "se", "sell")) sell_item("");
      else if (match(key, "eq", "equipment")) echo(display_equipment(0));
      else if (match(key, "op", "open")) echo("What do you want to open?");
      else if (match(key, "clo", "close")) echo("What do you want to close?");
      else if (match(key, "pl", "play")) echo("What do you want to play?");
      else if (match(key, "pu", "pull")) echo("What do you want to pull?");
      else if (match(key, "cl", "clans")) clan_command("");
      else if (match(key, "re", "religions")) religion_command("");
      else if (match(key, "eat", "eat")) echo("What do you want to eat?");
      else if (match(key, "dr", "drink")) echo("What do you want to drink?");
      else if (match(key, "fi", "fill")) echo("What do you want to fill?");
      else if (match(key, "em", "empty")) echo("What do you want to empty?");
      else if (match(key, "qu", "quaff")) echo("What do you want to quaff?");
      else if (match(key, "rec", "recite")) echo("What do you want to recite?");
      else if ((PLAYER) && (match(key, "alias", "alias"))) Aliases->display_all();
      else if ((PLAYER) && (match(key, "le", "learn"))) learn("");
      else if ((PLAYER) && (match(key, "te", "teach"))) teach("", "");
      else if ((PLAYER) && (match(key, "un", "unlearn"))) unlearn("");
      else if ((PLAYER) && (match(key, "fo", "forget"))) unlearn("");
      else if ((PLAYER) && (match(key, "tit", "title"))) set_title("", 1);
      else if ((PLAYER) && (match(key, "pren", "prename"))) set_prename("", 1);
      else if ((PLAYER) && (match(key, "tog", "toggle"))) my_client->toggle("X");
      else if ((PLAYER) && (match(key, "res", "resize"))) my_client->screen_resize("X");
      else if ((PLAYER) && (match(key, "cle", "clear"))) my_client->clear_screen(2);
      else if ((PLAYER) && (match(key, "clr", "clr"))) my_client->clear_screen(2);
      else if ((PLAYER) && (match(key, "cls", "cls"))) my_client->clear_screen(2);
      else if ((PLAYER) && (match(key, "col", "colors"))) echo(colorlist());
      else if ((PLAYER) && (match(key, "cla", "classes"))) echo(classlist());
      else if ((PLAYER) && (match(key, "ad", "add"))) add("X");
      else if ((PLAYER) && (match(key, "date", "date"))) echo(get_gamedate("all"));
      else if ((PLAYER) && (match(key, "time", "time"))) echo(get_gamedate("all"));
      else if ((PLAYER) && (match(key, "sav", "save"))) save();
      else if ((PLAYER) && (match(key, "quit", "quit"))) quit();
      else if ((!PLAYER) && (match(key, "respond", "respond"))) respond(Rem);
      else if (match(key, "bl", "blink"))   blink();
      else if (match(key, "no", "nod"))     nod();
      else if (match(key, "la", "laugh"))   laugh();
      else if (match(key, "ca", "cackle"))  cackle();
      else if (match(key, "sh", "shake"))   shake();
      else if (match(key, "sm", "smile"))   smile();
      else if (match(key, "gr", "grin"))    grin();
      else if (match(key, "si", "sigh"))    sigh();
      else if (match(key, "sh", "shrug"))   shrug();
      else if (match(key, "pe", "peer"))    peer();
      else if (match(key, "fr", "frown"))   frown();
      else if (match(key, "gr", "growl"))   growl();
      else if (match(key, "sn", "snarl"))   snarl();
      else if (match(key, "ti", "tip"))     tip();
      else if (match(key, "gi", "giggle"))  giggle();

      else if (match(key, "sc", "scan")) scout();
      else if (match(key, "sc", "scout")) scout();
      else if (match(key, "tr", "track")) echo(track(""));
      else if (match(key, "st", "stab")) stab_and_twist("");
      else if (match(key, "as", "assassinate")) assassin_strike("");

      else if (key == "level") { level_up();  echo("Ok."); }

      else if ((key == "leg") || (key == "legend")) echo(Legend->get_legend());

      // ******************************************************************************************* //
      // ************************************ IMMORTAL COMMANDS ************************************ //
      // ******************************************************************************************* //

      else if ((match(key, "rl", "rlist")) && (IMM(100))) echo(immortal_lists());
      else if ((match(key, "ml", "mlist")) && (IMM(100))) echo(immortal_lists());
      else if ((match(key, "il", "ilist")) && (IMM(100))) echo(immortal_lists());
      else if ((match(key, "zl", "zlist")) && (IMM(100))) echo(immortal_lists());
      else if ((match(key, "at", "at")) && (IMM(100))) at("","");
      else if ((match(key, "res", "restore")) && (IMM(100))) imm_restore(name);
      else if ((match(key, "fo", "force")) && (IMM(100))) echo("Force Usage#n: #R[#Nforce target command#R]#N");
      else if ((match(key, "sl", "slay")) && (IMM(100))) echo("Slay Usage#n: #R[#Nslay target#R]#N");
      else if ((match(key, "zr", "zrepop")) && (IMM(100))) echo("Zone Repop Usage#n: #R[#Nzrepop znum#R]#N");
      else if ((match(key, "zc", "zclear")) && (IMM(100))) echo("Zone Clear Usage#n: #R[#Nzclear znum#R]#N");
      else if ((match(key, "lo", "load")) && (IMM(100))) load("");
      else if ((match(key, "go", "goto")) && (IMM(100))) echo("Which room do you want to go to?");
      else if ((match(key, "tr", "transport")) && (IMM(100))) echo("Who do you want to transport?");
      else if ((match(key, "pos", "possess")) && (IMM(100))) echo("Possess Usage#n: #R[#Npossess target_mob#R]#N");
      else if ((match(key, "im", "immortal")) && (IMM(100))) echo(immortal_commands());
      else if ((match(key, "sy", "systime")) && (IMM(100))) my_client->display_date();
      else if ((match(key, "ma", "mark")) && (IMM(100))) mark_legend("","");
      else if ((match(key, "bes", "bestow")) && (IMM(100))) bestow("");
      else if ((match(key, "pur", "purge")) && (IMM(100))) purge("");
      else if ((match(key, "us", "users")) && (IMM(100))) echo(clist->userlist());
      else if (match(key, "unp", "unpossess")) unpossess(1);

      else if ((match(key, "dc", "dc")) && (IMM(100))) dc("");
      else if ((match(key, "delete", "delete")) && (IMM(100))) delete_character("");
      else if ((match(key, "disintegrate", "disintegrate")) && (IMM(100))) disintegrate("");
      else if ((match(key, "freeze", "freeze")) && (IMM(100))) freeze("", 1);
      else if ((match(key, "unfreeze", "unfreeze")) && (IMM(100))) freeze("", 0);

      // ******************************************************************************************* //
      // ******************************************************************************************* //
      // ******************************************************************************************* //

      else echo("Invalid Command.");
    }

    // *************************************************************************************************************** //
    // ********************************************* DOUBLE KEY COMMANDS ********************************************* //
    // *************************************************************************************************************** //

    else

    {
      rem = command.substr(key_size+1, command_size-key_size-1);
      rem_size = rem.size();

      if (rem_size > SPEECH_LIMITER)
        rem = rem.substr(0, SPEECH_LIMITER);

      Rem = rem;
      rem = lowercase(rem);
      frem = first(rem);

      if (match(key, "sa", "say")) say(Rem);
      else if (match(key, "ch", "chat")) chat(Rem);
      else if (match(key, "te", "tell")) tell(frem, last(Rem));
      else if (match(key, "ct", "ctell")) ctell(Rem);
      else if (match(key, "l", "look")) look_at(rem);
      else if (match(key, "use", "use")) use(rem);
      else if (match(key, "we", "we")) speedwalk(key);
      else if (match(key, "n", "north")) move("N");
      else if (match(key, "s", "south")) move("S");
      else if (match(key, "e", "east")) move("E");
      else if (match(key, "w", "west")) move("W");
      else if (match(key, "d", "down")) move("D");
      else if (match(key, "u", "up")) move("U");
      else if (speedwalk_legal(key)) speedwalk(key);
      else if (match(key, "st", "stats")) stat(rem);
      else if (match(key, "me", "me")) emote(Rem);
      else if (match(key, "em", "emote")) emote(Rem);
      else if (match(key, "wh", "who")) echo(clist->who());
      else if (match(key, "i", "inventory")) display_inventory();
      else if (match(key, "i", "information")) echo(information());
      else if (match(key, "c", "cast")) cast(rem);
      else if (match(key, "k", "kill")) engage(rm->find_entity(this, frem), 1);
      else if (match(key, "att", "attack")) engage(rm->find_entity(this, frem), 1);
      else if (match(key, "dis", "disband")) my_group->remove_all(this);
      else if (match(key, "f", "flee")) flee();
      else if (match(key, "a", "assist")) manual_assist(frem);
      else if (match(key, "fin", "finger")) echo(File->get_plan(rem));
      else if (match(key, "re", "read")) read(frem);
      else if (match(key, "rem", "remove")) remove(rem);
      else if (match(key, "wr", "write")) write_note(Rem);
      else if (match(key, "fol", "follow")) follow(frem, 0);
      else if (match(key, "dit", "ditch")) ditch(frem);
      else if (match(key, "sk", "skills")) display_skills();
      else if (match(key, "sp", "spells")) display_spells();
      else if (match(key, "sac", "sacrifice")) sacrifice_item(frem);
      else if (match(key, "pu", "put")) put(rem);
      else if (match(key, "gi", "give")) give(rem);
      else if (match(key, "dr", "drop")) drop(rem);
      else if (match(key, "ge", "get")) get(rem);
      else if (match(key, "we", "wear")) equip(rem);
      else if (match(key, "bu", "buy")) buy_item(rem);
      else if (match(key, "se", "sell")) sell_item(rem);
      else if (match(key, "op", "open")) open_blocker(rem);
      else if (match(key, "clo", "close")) close_blocker(rem);
      else if (match(key, "pl", "play")) play(rem);
      else if (match(key, "pu", "pull")) pull(rem);
      else if (match(key, "cl", "clans")) clan_command(Rem);
      else if (match(key, "re", "religions")) religion_command(Rem);
      else if ((match(key, "eq", "equipment")) && (rem == "")) echo(display_equipment(0));
      else if (match(key, "eq", "equipment")) equip(frem);
      else if ((match(key, "gr", "group")) && (frem == "all")) my_group->add_all(this);
      else if (match(key, "gr", "group")) my_group->add_member(this, rm->find_entity(this, frem));
      else if ((match(key, "un", "ungroup")) && (frem == "all")) my_group->remove_all(this);
      else if (match(key, "un", "ungroup")) my_group->remove_member(this, rm->find_entity(this, frem));
      else if (match(key, "eat", "eat")) eat(rem);
      else if (match(key, "dr", "drink")) drink(rem);
      else if (match(key, "qu", "quaff")) drink(rem);
      else if (match(key, "fi", "fill")) refill(rem);
      else if (match(key, "em", "empty")) empty(rem);
      else if (match(key, "rec", "recite")) recite(rem);
      else if ((GHOST) && (key == "echo_room")) DC_Ghost(this)->echo_room(Rem);
      else if ((GHOST) && (key == "echo_owner")) DC_Ghost(this)->echo_owner(Rem);
      else if ((GHOST) && (key == "echo_room_exclude")) DC_Ghost(this)->echo_room_exclude(Rem);
      else if ((PLAYER) && (match(key, "alias", "alias"))) Aliases->add_alias(Rem);
      else if ((PLAYER) && (match(key, "le", "learn"))) learn(rem);
      else if ((PLAYER) && (match(key, "te", "teach"))) teach(frem, last(rem));
      else if ((PLAYER) && (match(key, "un", "unlearn"))) unlearn(rem);
      else if ((PLAYER) && (match(key, "fo", "forget"))) unlearn(rem);
      else if ((PLAYER) && (match(key, "tit", "title"))) set_title(Rem, 1);
      else if ((PLAYER) && (match(key, "pren", "prename"))) set_prename(Rem, 1);
      else if ((PLAYER) && (match(key, "tog", "toggle"))) my_client->toggle(frem);
      else if ((PLAYER) && (match(key, "res", "resize"))) my_client->screen_resize(frem);
      else if ((PLAYER) && (match(key, "cle", "clear"))) my_client->clear_screen(2);
      else if ((PLAYER) && (match(key, "clr", "clr"))) my_client->clear_screen(2);
      else if ((PLAYER) && (match(key, "cls", "cls"))) my_client->clear_screen(2);
      else if ((PLAYER) && (match(key, "col", "colors"))) echo(colorlist());
      else if ((PLAYER) && (match(key, "cla", "classes"))) echo(classlist());
      else if ((PLAYER) && (match(key, "ad", "add"))) add(frem);
      else if ((PLAYER) && (match(key, "date", "date"))) echo(get_gamedate("all"));
      else if ((PLAYER) && (match(key, "time", "time"))) echo(get_gamedate("all"));
      else if ((PLAYER) && (match(key, "sav", "save"))) save();
      else if ((PLAYER) && (match(key, "quit", "quit"))) echo("To quit the game, type 'quit'.");
      else if ((!PLAYER) && (match(key, "respond", "respond"))) respond(Rem);
      else if ((key == "death") && (rem == "blossom")) death_blossom();
      else if ((key == "death") && (rem == "blossom single") && (intermittent_skill > 0)) death_blossom_single();
      else if (match(key, "bl", "blink"))   blink(frem);
      else if (match(key, "no", "nod"))     nod(frem);
      else if (match(key, "la", "laugh"))   laugh(frem);
      else if (match(key, "ca", "cackle"))  cackle(frem);
      else if (match(key, "sh", "shake"))   shake(frem);
      else if (match(key, "sm", "smile"))   smile(frem);
      else if (match(key, "gr", "grin"))    grin(frem);
      else if (match(key, "si", "sigh"))    sigh(frem);
      else if (match(key, "sh", "shrug"))   shrug(frem);
      else if (match(key, "pe", "peer"))    peer(frem);
      else if (match(key, "fr", "frown"))   frown(frem);
      else if (match(key, "gr", "growl"))   growl(frem);
      else if (match(key, "sn", "snarl"))   snarl(frem);
      else if (match(key, "ti", "tip"))     tip(frem);
      else if (match(key, "gi", "giggle"))  giggle(frem);

      else if (match(key, "sc", "scan")) scout();
      else if (match(key, "sc", "scout")) scout();
      else if (match(key, "tr", "track")) echo(track(frem));
      else if (match(key, "st", "stab")) stab_and_twist(rem);
      else if (match(key, "as", "assassinate")) assassin_strike(rem);

      // ******************************************************************************************* //
      // ************************************ IMMORTAL COMMANDS ************************************ //
      // ******************************************************************************************* //

      else if ((match(key, "im", "immortal")) && (IMM(100))) echo(immortal_commands());
      else if ((match(key, "tr", "transport")) && (IMM(100))) trans(frem);
      else if ((match(key, "at", "at")) && (IMM(100))) at(frem, last(rem));
      else if ((match(key, "res", "restore")) && (IMM(100))) imm_restore(frem);
      else if ((match(key, "pos", "possess")) && (IMM(100))) possess(frem);
      else if ((match(key, "sl", "slay")) && (IMM(100))) slay(frem);
      else if ((match(key, "lo", "load")) && (IMM(100))) load(rem);
      else if ((match(key, "il", "ilist")) && (IMM(100))) ilist(rem);
      else if ((match(key, "rl", "rlist")) && (IMM(100))) rlist(rem);
      else if ((match(key, "ml", "mlist")) && (IMM(100))) mlist(rem);
      else if ((match(key, "zl", "zlist")) && (IMM(100))) zlist(rem);
      else if (((match(key, "go", "goto")) && (IMM(100))) && (number(frem))) (go_to(stringconvert(frem)));
      else if ((match(key, "go", "goto")) && (IMM(100))) go_to(frem);
      else if (((match(key, "fo", "force")) && (IMM(100))) && (last(rem) == "")) my_client->put_command("force");
      else if ((match(key, "fo", "force")) && (IMM(100))) force(frem, last(Rem));
      else if (((match(key, "zr", "zrepop")) && (IMM(100))) && (!number(frem))) my_client->put_command("zrepop");
      else if ((match(key, "zr", "zrepop")) && (IMM(100))) zrepop(stringconvert(frem));
      else if (((match(key, "zc", "zclear")) && (IMM(100))) && (!number(frem))) my_client->put_command("zclear");
      else if ((match(key, "zc", "zclear")) && (IMM(100))) zclear(stringconvert(frem));
      else if ((match(key, "sy", "systime")) && (IMM(100))) my_client->display_date();
      else if ((match(key, "ma", "mark")) && (IMM(100))) mark_legend(frem, last(Rem));
      else if ((match(key, "bes", "bestow")) && (IMM(100))) bestow(rem);
      else if ((match(key, "pur", "purge")) && (IMM(100))) purge(rem);
      else if ((match(key, "us", "users")) && (IMM(100))) echo(clist->userlist());
      else if (match(key, "unp", "unpossess")) unpossess(1);

      else if ((match(key, "dc", "dc")) && (IMM(100))) dc(rem);
      else if ((match(key, "delete", "delete")) && (IMM(100))) delete_character(rem);
      else if ((match(key, "disintegrate", "disintegrate")) && (IMM(100))) disintegrate(rem);
      else if ((match(key, "freeze", "freeze")) && (IMM(100))) freeze(rem, 1);
      else if ((match(key, "unfreeze", "unfreeze")) && (IMM(100))) freeze(rem, 0);

      // ******************************************************************************************* //
      // ******************************************************************************************* //
      // ******************************************************************************************* //

      else echo("Invalid Command.");
    }
  }

  else echo("Invalid Command.");
}

void entity::process_multiple_commands(string *command)

{
  if ((*command).find(";;") == string::npos) return;

  int pos = (*command).find(";;");

  (*command).erase(pos, 2);

  if (my_client != NULL)
    my_client->force_command((*command).substr(pos, (*command).size()-pos));

  (*command) = (*command).substr(0, pos);
}