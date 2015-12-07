#ifndef GROUP_H
#define GROUP_H

class group

{
  public:
    group(entity*);
    ~group();
    void add_member(entity*, entity*);
    void add_member_silent(entity*);
    void remove_member(entity*, entity*);
    void remove_member_silent(entity*);
    entity* get_random_target(entity*, entity*);
    void add_all(entity*);
    void remove_all(entity*);
    void destroy_group();
    entity* get_leader();
    int in_group(entity*);
    string get_display_info(entity*);
    void display_group(entity*);
    entity* get_member(int);
    int get_size();
    int get_csize();
    void group_assist(entity*);
    void divide_experience(room*, int);
    void no_experience();

    void group_message(string);
    void group_message(entity*, string);
    void group_message(entity*, entity*, string);
    void group_message(entity*, entity*, entity*, string);
    void group_local_message(room*, string);
    void group_local_message(room*, string, string);
    void group_local_message(room*, entity*, string);
    void group_local_message(room*, entity*, string, string);

  private:
    int size;
    entity* *member;
};

#endif