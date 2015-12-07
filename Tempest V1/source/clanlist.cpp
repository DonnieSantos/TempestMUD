#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

clanlist::clanlist()

{
  int num, num2, pos, numranks;
  string filename, XXX, str, str2;
  char c, s[500], *thefile;
  stringstream ss;
  FILE *fp;

  filename = ".\\clans\\clanlist.dat";

  thefile = (char*)filename.c_str();
  fp = fopen(thefile, "r");

  if (fp == NULL) { cout << "ERROR!!!  Clanlist File Does Not Exist!" << endl; }

  else

  {
    cout << "Loading Clans..." << endl;

    for (int j=1; j<=18; j++)
      getc(fp);

    fscanf(fp, "%d", &size);

    if (fclose(fp) != 0)
      cout << "Error closing Clanlist File!!!" << endl;
  }

  clan_list = new clan* [size];

  for (int i=1; i<=size; i++)

  {
    ss.clear();
    ss.str("");

    ss << i;
    XXX = ss.str();

    if (i < 10)
      XXX = "00" + XXX;

    else if ((i >= 10) && (i <= 99))
      XXX = "0" + XXX;

    filename = ".\\clans\\clan" + XXX + ".dat";

    thefile = (char*)filename.c_str();
    fp = fopen(thefile, "r");

    if (fp == NULL) { cout << "ERROR!!! Clan File " << i << " Does Not Exist!" << endl; }

    else

    {
      cout << "Loading Clan File " << i << "." << endl;

      pos = i-1;

      for (int j=1; j<=7; j++)
        getc(fp);

      fgets(s, sizeof(s), fp);

      str = s;
      str = str.substr(0, str.length()-1);

      for (int j=1; j<=10; j++)
        getc(fp);

      fscanf(fp, "%d", &num);
      fgets(s, sizeof(s), fp);

      for (int j=1; j<=8; j++)
        getc(fp);

      fscanf(fp, "%d", &numranks);
      fgets(s, sizeof(s), fp);
      fgets(s, sizeof(s), fp);

      clan_list[pos] = new clan(i, str, numranks);

      for (int j=1; j<=numranks+2; j++)

      {
        getc(fp);
        fscanf(fp, "%d", &num2);
        getc(fp); getc(fp); getc(fp); c = getc(fp);
        str = "";  str2 = "";

        while (c != ']') { str = str + c;  c = getc(fp); }
        getc(fp); getc(fp); c = getc(fp);
        while (c != ']') { str2 = str2 + c; c = getc(fp); }

        clan_list[pos]->set_rankname(num2, str, str2);

        fgets(s, sizeof(s), fp);
      }

      fgets(s, sizeof(s), fp);
      fgets(s, sizeof(s), fp);

      str = s;
      str = str.substr(0, str.length()-1);

      string clan_info = "";

      while (str != "[END]")

      {
        fgets(s, sizeof(s), fp);
        str = s;
        str = str.substr(0, str.length()-1);

        if (str != "[END]") clan_info = clan_info + " " + str;
      }

      clan_info.erase(0,1);
      clan_list[pos]->set_clan_info(clan_info);

      fgets(s, sizeof(s), fp);

      for (int j=1; j<=num; j++)

      {
        str = "";
        c = getc(fp);

        while (c != ' ') {
          str = str + c;
          c = getc(fp); }

        fscanf(fp, "%d", &num2);

        getc(fp);
        fgets(s, sizeof(s), fp);

        string prof = s;
        prof = prof.substr(0, prof.length()-1);

        clan_list[pos]->add_member(str, num2, prof);
      }

      if (fclose(fp) != 0)
        cout << "Error closing Clan File!\n" << endl;
    }
  }

  verify_all_members();
}

string clanlist::display_clanlist()

{
  string temp = "";
  int width = 14;

  for (int i=0; i<size; i++)
    if (visible_size(clan_list[i]->get_name()) > width)
      width = visible_size(clan_list[i]->get_name());

  string border = "  #n+-";
  for (int i=1; i<=width; i++) border += "-";
  border += "-+#N";

  temp += border + "\r\n  #n|#N Current Clans#n:#N";
  for (int i=1; i<=width-14; i++) temp += " ";
  temp += " #n|#N\r\n" + border + "\r\n";

  for (int i=0; i<size; i++) {
    temp += "  #n|#N " + clan_list[i]->get_name();
    for (int j=1; j<=width-visible_size(clan_list[i]->get_name()); j++) temp += " ";
    temp += " #n|#N\r\n"; }

  temp += border;

  return temp;
}

clan* clanlist::get_clan(string cname)

{
  cname = remove_colors(lowercase(cname));

  for (int i=0; i<size; i++)
    if (remove_colors(lowercase(clan_list[i]->get_name())) == cname)
      return clan_list[i];

  return NULL;
}

void clanlist::verify_all_members()

{
  for (int i=0; i<size; i++)
    clan_list[i]->verify_members();
}