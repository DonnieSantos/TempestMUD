#ifndef FILEMANAGER_H
#define FILEMANAGER_H
#include "windows.h"

class filemanager

{
  public:
    filemanager();
    ~filemanager();
    int load_profile(client*);
    void save_profile(client*);
    int delete_profile(string);
    void item_load(client*);
    string get_plan(string);
    string* get_pfile_copy(string);
    void set_pfile_copy(string, string*);
    int char_exist(string);
    string get_password(string);
    string get_class(string);
    int get_level(string);
    void create_placeholder(string);

  private:
    CRITICAL_SECTION CriticalSection;
};

#endif