#ifndef CLIENTLIST_H
#define CLIENTLIST_H

class clientlist

{
  public:
    clientlist();
    ~clientlist();
    client* insert();
    int remove(client*);
    client* get(int);
    client* get_client(int);
    client* find_client(string);
    client* find_client(string, client*);
    entity* find_character(string);
    entity* find_global_entity(string);
    void gecho(client*, string);
    void xgecho(client*, string);
    void clan_xecho(client*, string, string);
    void clan_xecho(client*, client*, string, string);
    void syslog_gecho(string, int);
    void syslog_gecho(string, int, string);
    void update_command_locks(double);
    void mud_timer();
    void spin();
    int size();
    void tick();
    string who();
    string userlist();

  private:
    int SIZE;
    client* *list;
    int tick_count;
    clock_t prev, updated;
    double mobtimer, mobtime;
    double ticktimer, ticktime;
    double roundtimer, roundtime;
    double locktimer, locktime;
};

#endif