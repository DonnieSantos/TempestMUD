#ifndef LIQUIDS_H
#define LIQUIDS_H

class liquids

{
  public:
    liquids() { }
    void drink_liquid(entity*, string, string);

  private:
    room* RM;
    entity* ENT;
    string cname;
    string lname;

    void standard_echo(string);
    void standard_echo(string, string);
    void standard_room_echo();
    void drink_unknown();
    void drink_water();
    void drink_curatio();
    void drink_panacea();
};

#endif