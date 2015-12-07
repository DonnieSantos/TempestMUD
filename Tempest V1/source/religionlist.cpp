#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

religionlist::religionlist()

{
  int num, num2, pos, numranks;
  string filename, XXX, str, str2, str3;
  char c, s[500], *thefile;
  stringstream ss;
  FILE *fp;

  filename = ".\\religions\\religionlist.dat";

  thefile = (char*)filename.c_str();
  fp = fopen(thefile, "r");

  if (fp == NULL) { cout << "ERROR!!!  religionlist File Does Not Exist!" << endl; }

  else

  {
    cout << "Loading Religions..." << endl;

    for (int j=1; j<=22; j++)
      getc(fp);

    fscanf(fp, "%d", &size);

    if (fclose(fp) != 0)
      cout << "Error closing Religion List File!!!" << endl;
  }

  religion_list = new religion* [size];

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

    filename = ".\\religions\\religion" + XXX + ".dat";

    thefile = (char*)filename.c_str();
    fp = fopen(thefile, "r");

    if (fp == NULL) { cout << "ERROR!!! Religion File " << i << " Does Not Exist!" << endl; }

    else

    {
      cout << "Loading Religion File " << i << "." << endl;

      pos = i-1;

      for (int j=1; j<=11; j++) getc(fp);
      fgets(s, sizeof(s), fp);
      str = s;
      str = str.substr(0, str.length()-1);

      for (int j=1; j<=8; j++) getc(fp);
      fgets(s, sizeof(s), fp);
      str2 = s;
      str2 = str2.substr(0, str2.length()-1);

      for (int j=1; j<=8; j++) getc(fp);
      fgets(s, sizeof(s), fp);
      str3 = s;
      str3 = str3.substr(0, str3.length()-1);

      for (int j=1; j<=10; j++)
        getc(fp);

      fscanf(fp, "%d", &num);
      fgets(s, sizeof(s), fp);
      fgets(s, sizeof(s), fp);

      religion_list[pos] = new religion(i, str);

      religion_list[pos]->set_diety_name(str2);
      religion_list[pos]->set_diety_title(str3);

      religion_list[pos]->set_rankname(0, str + " Applicant", str + " Applicant");

      for (int j=1; j<=4; j++)

      {
        str = "";  str2 = "";

        for (int k=1; k<=6; k++)
          getc(fp);

        c = getc(fp);

        while (c != ']') { str = str + c;  c = getc(fp); }
        getc(fp); getc(fp); c = getc(fp);

        while (c != ']') { str2 = str2 + c; c = getc(fp); }

        religion_list[pos]->set_rankname(j, str, str2);

        fgets(s, sizeof(s), fp);
      }

      fgets(s, sizeof(s), fp);
      fgets(s, sizeof(s), fp);

      str = s;
      str = str.substr(0, str.length()-1);

      string religion_info = "";

      while (str != "[END]")

      {
        fgets(s, sizeof(s), fp);
        str = s;
        str = str.substr(0, str.length()-1);

        if (str != "[END]") religion_info = religion_info + " " + str;
      }

      religion_info.erase(0,1);
      religion_list[pos]->set_religion_info(religion_info);

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

        religion_list[pos]->add_member(str, num2, prof);
      }

      if (fclose(fp) != 0)
        cout << "Error closing Religion File!\n" << endl;
    }
  }

  verify_all_members();
}

string religionlist::display_religionlist()

{
  string temp = "";
  int width = 18;

  for (int i=0; i<size; i++)
    if (visible_size(religion_list[i]->get_name()) > width)
      width = visible_size(religion_list[i]->get_name());

  string border = "  #n+-";
  for (int i=1; i<=width; i++) border += "-";
  border += "-+#N";

  temp += border + "\r\n  #n|#N Current religions#n:#N";
  for (int i=1; i<=width-18; i++) temp += " ";
  temp += " #n|#N\r\n" + border + "\r\n";

  for (int i=0; i<size; i++) {
    temp += "  #n|#N " + religion_list[i]->get_name();
    for (int j=1; j<=width-visible_size(religion_list[i]->get_name()); j++) temp += " ";
    temp += " #n|#N\r\n"; }

  temp += border;

  return temp;
}

religion* religionlist::get_religion(string cname)

{
  cname = remove_colors(lowercase(cname));

  for (int i=0; i<size; i++)
    if (remove_colors(lowercase(religion_list[i]->get_name())) == cname)
      return religion_list[i];

  return NULL;
}

religion* religionlist::get_religion_diety(string dname)

{
  for (int i=0; i<size; i++)
    if (religion_list[i]->get_diety_name() == dname)
      return religion_list[i];

  return NULL;
}

void religionlist::verify_all_members()

{
  for (int i=0; i<size; i++)
    religion_list[i]->verify_members();
}