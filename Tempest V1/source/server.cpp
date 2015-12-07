#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

filemanager *File;
clientlist *clist;
world *World;

void loginthread(void *voidclient)

{
  client *aclient = (client*) voidclient;
  aclient->get_char()->set_room(World->get_room(100));
  aclient->login();

  aclient->get_char()->update_claninfo();
  aclient->get_char()->update_religioninfo();
  aclient->charinfo_save();
  aclient->set_state(CSTATE_NORMAL);

  int room_logged = aclient->get_char()->get_room()->get_id();
  World->get_room(room_logged)->gain(aclient->get_char());
  World->get_log()->add_log(aclient, "Entered the world.");

  _endthread();
}

// ********************************************************************************************* //
// ********************************************************************************************* //
// ********************************************************************************************* //

void main()

{
  SOCKET        mastersocket, subsocket;
  WSADATA       wsadat;
  SOCKADDR_IN   sockaddr;
  SOCKADDR_IN   name;
  client        *aclient;
  u_long        argp;
  int           namelen;
  char*         ip_address;

  srand(time(0));

  World = new world();
  World->load();

  World->get_log()->add_log(NULL, "Tempest Server Started.");
  cout << "Tempest MUD Server Program Running." << endl;

  clist = new clientlist();
  File = new filemanager();

  argp       =  (unsigned)(long)1;
  namelen    =  16;

  WSAStartup(2, &wsadat);

  subsocket                       =   SOCKET_ERROR;
  mastersocket                    =   socket(AF_INET, SOCK_STREAM, 0);
  sockaddr.sin_port               =   htons(GAME_PORT);
  sockaddr.sin_family             =   AF_INET;
  sockaddr.sin_addr.S_un.S_addr   =   INADDR_ANY;

  bind(mastersocket, (SOCKADDR*)(&sockaddr), sizeof(sockaddr));
  listen(mastersocket, 1);
  ioctlsocket(mastersocket, FIONBIO, &argp);

  while (1)

  {
    while (subsocket == SOCKET_ERROR)

    {
      Sleep(50);
      subsocket = accept(mastersocket, NULL, NULL);
      clist->spin();
    }

    ioctlsocket(subsocket, FIONBIO, &argp);
    getpeername(subsocket, (SOCKADDR*)(&name), &namelen);
    ip_address = inet_ntoa(name.sin_addr);

    World->get_log()->add_log(NULL, string(ip_address) + " connected to server.");
    cout << ip_address << " connected to server." << endl;

    aclient = clist->insert();
    aclient->set_ip(ip_address);
    aclient->set_clientsocket(subsocket);

    _beginthread(loginthread, 0, (void*)aclient);

    subsocket = SOCKET_ERROR;
  }
}