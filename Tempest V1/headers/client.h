#ifndef CLIENT_H
#define CLIENT_H
#include "cmdqueue.h"

class client

{
  public:
    client();
    ~client() { }
    void msg(string);
    string put_command(string);
    string put_output(string);
    string put_foutput(string);
    string force_command(string);
    string get_command()                   { return CQ.get();        }
    string get_output()                    { return OQ.get();        }
    string get_foutput()                   { return FQ.get();        }
    int get_Qsize()                        { return CQ.get_size();   }
    int get_Osize()                        { return OQ.get_size();   }
    int get_Fsize()                        { return FQ.get_size();   }
    cmdqueue* get_Oqueue()                 { return &OQ;             }
    void set_clientsocket(SOCKET s)        { clientsocket = s;       }
    SOCKET get_clientsocket()              { return clientsocket;    }
    void set_ld(int i)                     { ld = i;                 }
    int get_ld()                           { return ld;              }
    int get_state()                        { return state;           }
    void set_state(int i)                  { state = i;              }
    int get_screen_size()                  { return screen_size;     }
    int get_ansicolor()                    { return ansicolor;       }
    int get_ANSI_MODE()                    { return ANSI_MODE;       }
    void set_screen_size(int i)            { screen_size = i;        }
    void set_ansicolor(int i)              { ansicolor = i;          }
    void set_ANSI_MODE(int i)              { ANSI_MODE = i;          }
    clientlist* get_clist()                { return clist;           }
    entity* get_char()                     { return charinfo;        }
    clientlist* get_clientlist()           { return clist;           }
    string get_commandstring()             { return commandstring;   }
    double get_command_lock()              { return command_lock;    }
    string get_last_command()              { return last_command;    }
    string get_ip()                        { return ip_a;            }
    int get_creation()                     { return creation;        }
    entity* get_backup()                   { return charinfo_backup; }
    string get_last_login()                { return last_login;      }
    string get_ansibar();
    void screen_resize(string);
    void clear_screen(int);
    void clear_ansibar();
    void draw_ansibar();
    void set_ip(char*);
    void set_command_lock(double);
    void update_command_lock(double);
    void flush_commands();
    void flush_output();
    void flush_normal();
    void flush_fight();
    void enter_ld_state();
    void update_ld_state();
    void update_idle();
    string prompt_private();
    void disconnect(int);
    void reconnect(client*);
    void getbufferdata();
    void set_charinfo(entity*);
    void charinfo_save();
    void charinfo_restore();
    string get_day();
    string get_date();
    string get_time();
    void display_date();
    void toggle(string);
    void write(string);
    string composer();
    string prompt();
    void login();
    void create_character(string);
    int attempt_password(string);
    int attempt_create(string);
    int valid_name(string);
    string get_idle_time();

  private:
    SOCKET clientsocket;
    cmdqueue CQ, OQ, FQ;
    entity *charinfo;
    entity *charinfo_backup;
    int ld, state, creation;
    int ANSI_MODE, ansicolor;
    string commandstring;
    string last_command;
    string ip_a;
    clock_t ld_time_prev;
    double ld_time;
    double command_lock_timer;
    double command_lock;
    int max_screen_size;
    int screen_size;
    int active, idle;
    double time_connected;
    double time_lastcommand;
    string last_login;
};

#endif