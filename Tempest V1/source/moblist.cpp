#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

moblist::moblist()

{
  set_size();

  for (int i=0; i<size; i++)
    list[i] = NULL;
}

mobdesc* moblist::get_mobdesc(int n)

{
  return list[n];
}

mobdesc* moblist::get_mobdesc(string s)

{
  if (number(s))

  {
    int index = stringconvert(s);

    if ((index < 0) || (index >= size))
      return NULL;

    return list[index];
  }

  s = remove_colors(s);
  int target_num = clip_number(&s);
  if (s.size() < 3) return NULL;

  for (int i=0; i<size; i++)
  if (list[i] != NULL)

  {
    if ((abbreviation(s, lowercase(remove_colors(list[i]->name)))) || (list[i]->check_targets(s)))

    {
      target_num--;

      if (!target_num)
        return list[i];
    }
  }

  return NULL;
}

void moblist::load()

{
  int done, num, num2, znum, size, count, mnum;
  string filename, XXX, str, str2;
  char c, s[500], *thefile;
  stringstream ss;
  FILE *fp;

  float HP_PERCENT  = ((float)MOBILE_HP_PERCENT / (float)100);
  float HR_PERCENT  = ((float)MOBILE_HR_PERCENT / (float)100);
  float DR_PERCENT  = ((float)MOBILE_DR_PERCENT / (float)100);
  float AC_PERCENT  = ((float)MOBILE_AC_PERCENT / (float)100);
  float MR_PERCENT  = ((float)MOBILE_MR_PERCENT / (float)100);
  float STR_PERCENT = ((float)MOBILE_STR_PERCENT / (float)100);
  float DEX_PERCENT = ((float)MOBILE_DEX_PERCENT / (float)100);
  float CON_PERCENT = ((float)MOBILE_CON_PERCENT / (float)100);
  float INT_PERCENT = ((float)MOBILE_INT_PERCENT / (float)100);
  float WIS_PERCENT = ((float)MOBILE_WIS_PERCENT / (float)100);

  for (int i=0; i<=World->get_maxzone(); i++)

  {
    ss.clear();
    ss.str("");

    ss << i;
    XXX = ss.str();

    if (i < 10)
      XXX = "00" + XXX;

    else if ((i >= 10) && (i <= 99))
      XXX = "0" + XXX;

    filename = ".\\zones\\zone" + XXX + ".mob";

    thefile = (char*)filename.c_str();
    fp = fopen(thefile, "r");

    if (fp == NULL) { /* Mob file "i" does not exist! */ }

    else

    {
      cout << "Loading Mob File " << i << "." << endl;

      for (int i=1; i<=5; i++)
        getc(fp);

      fscanf(fp, "%d", &znum);
      if (znum != i) cout << "Mob File Number Mismatch !!!" << endl;

      fgets(s, sizeof(s), fp);

      for (int i=1; i<=15; i++)
        getc(fp);

      fscanf(fp, "%d", &size);

      fgets(s, sizeof(s), fp);
      fgets(s, sizeof(s), fp);

      for (int i=1; i<=size; i++)

      {
        fscanf(fp, "%d", &num);
        mnum = num;

        list[mnum] = new mobdesc(mnum);

        list[mnum]->zone_id = znum;

        list[mnum]->btype = "";
        list[mnum]->bdir = 'X';

        getc(fp);

        fgets(s, sizeof(s), fp);

        str = s;
        str = str.substr(0, str.length()-1);

        list[mnum]->name = str;

        fgets(s, sizeof(s), fp);

        str = s;
        str = str.substr(0, str.length()-1);

        while (str.find(" ") != string::npos) {
          int pos = str.find(" ");
          list[mnum]->add_target(lowercase(str.substr(0, pos)));
          str = str.substr(pos+1, str.size()-pos); }

        list[mnum]->add_target(lowercase(str));

        fgets(s, sizeof(s), fp);

        str = s;
        str = str.substr(0, str.length()-1);

        list[mnum]->title = "#Y" + rm_allign(str) + "#N";

        fgets(s, sizeof(s), fp);

        done = 0;
        count = 0;
        str2 = "";

        while (!done)

        {
          count++;

          fgets(s, sizeof(s), fp);
          str = s;
          str = str.substr(0, str.length()-1);

          if ((str[0] == 'E') && (str[1] == 'N') && (str[2] == 'D'))
            done = 1;

          else {
            if (count == 1) str2 = str2 + str;
            else if (count > 1) str2 = str2 + " " + str; }
        }

        list[mnum]->desc = rm_allign(str2);

        for (int j=1; j<=6; j++)
          getc(fp);

        fscanf(fp, "%d", &num);
        fgets(s, sizeof(s), fp);
        list[mnum]->level = num;

        for (int j=1; j<=8; j++)
          getc(fp);

        fscanf(fp, "%d", &num);
        getc(fp);
        fscanf(fp, "%d", &num2);
        fgets(s, sizeof(s), fp);

        list[mnum]->HP = (int)((float)num * HP_PERCENT);
        list[mnum]->MN = num2;

        for (int j=1; j<=8; j++)
          getc(fp);

        fscanf(fp, "%d", &num);
        getc(fp);
        list[mnum]->STR = (int)((float)num * STR_PERCENT);

        fscanf(fp, "%d", &num);
        getc(fp);
        list[mnum]->DEX = (int)((float)num * DEX_PERCENT);

        fscanf(fp, "%d", &num);
        getc(fp);
        list[mnum]->CON = (int)((float)num * CON_PERCENT);

        fscanf(fp, "%d", &num);
        getc(fp);
        list[mnum]->INT = (int)((float)num * INT_PERCENT);

        fscanf(fp, "%d", &num);
        fgets(s, sizeof(s), fp);
        list[mnum]->WIS = (int)((float)num * WIS_PERCENT);

        for (int j=1; j<=9; j++)
          getc(fp);

        fscanf(fp, "%d", &num);
        getc(fp);
        list[mnum]->HR = (int)((float)num * HR_PERCENT);

        fscanf(fp, "%d", &num);
        getc(fp);
        list[mnum]->DR = (int)((float)num * DR_PERCENT);

        fscanf(fp, "%d", &num);
        getc(fp);
        list[mnum]->AC = (int)((float)num * AC_PERCENT);

        fscanf(fp, "%d", &num);
        fgets(s, sizeof(s), fp);
        list[mnum]->MR = (int)((float)num * MR_PERCENT);

        for (int j=1; j<=6; j++)
          getc(fp);

        fscanf(fp, "%d", &num);
        fgets(s, sizeof(s), fp);
        list[mnum]->exp = num;

        for (int j=1; j<=7; j++)
          getc(fp);

        fscanf(fp, "%d", &num);
        fgets(s, sizeof(s), fp);
        list[mnum]->gold = num;

        for (int j=1; j<=9; j++)
          getc(fp);

        fgets(s, sizeof(s), fp);

        str = s;
        str = str.substr(0, str.length()-1);

        list[mnum]->gender = str;

        for (int j=1; j<=9; j++)
          getc(fp);

        fscanf(fp, "%d", &num);
        fgets(s, sizeof(s), fp);
        list[mnum]->allign = num;

        for (int j=1; j<=8; j++)
          getc(fp);

        fscanf(fp, "%d", &num);
        fgets(s, sizeof(s), fp);
        list[mnum]->speed = num;

        done = 0;

        while (!done)

        {
          c = getc(fp);

          if (c == 'F') {
            fgets(s, sizeof(s), fp);
            str = s;
            str = str.substr(4, str.length()-5);
            list[mnum]->add_flag(str); }

          else if (c == 'B') {
            fgets(s, sizeof(s), fp);
            str = s;
            str = str.substr(7, str.length()-8);
            list[mnum]->bdir = str[0];
            str.erase(0,2);
            list[mnum]->btype = str; }

          else if (c == 'S')

          {
            c = getc(fp);

            if (c == 'K') {
              getc(fp); getc(fp);
              getc(fp); getc(fp);
              getc(fp); c = getc(fp);

              string sk_name = "";

              while (c != ']') {
                sk_name = sk_name + c;
                c = getc(fp); }

              getc(fp);
              fscanf(fp, "%d", &num2);
              fgets(s, sizeof(s), fp);
              list[mnum]->add_skill(sk_name, num2); }

            else if (c == 'P') {
              getc(fp); getc(fp);
              getc(fp); getc(fp);
              getc(fp); c = getc(fp);

              string sp_name = "";

              while (c != ']') {
                sp_name = sp_name + c;
                c = getc(fp); }

              getc(fp);
              fscanf(fp, "%d", &num2);
              fgets(s, sizeof(s), fp);
              list[mnum]->add_spell(sp_name, num2); }

            else if (c == 'E')

            {
              getc(fp); getc(fp);
              getc(fp);

              fgets(s, sizeof(s), fp);
              str = s;
              str = str.substr(0, str.length()-1);

              while (str.find(" ") != string::npos) {
                int pos = str.find(" ");
                list[mnum]->add_item_sale(stringconvert(str.substr(0, pos)));
                str = str.substr(pos+1, str.size()-pos); }

              list[mnum]->add_item_sale(stringconvert(str));
            }
          }

          else if (c == 'A') {
            getc(fp); getc(fp); getc(fp); getc(fp);
            str = "";
            c = getc(fp);
            while (c != ']') {
              str = str + c;
              c = getc(fp); }
            getc(fp);
            fscanf(fp, "%d", &num);
            fgets(s, sizeof(s), fp);
            list[mnum]->add_action(str, num); }

          else if (c == 'I')

          {
            getc(fp); getc(fp); getc(fp); getc(fp);
            fscanf(fp, "%d", &num);
            getc(fp);
            fscanf(fp, "%d", &num2);
            fgets(s, sizeof(s), fp);

            list[mnum]->add_item(num, num2);
          }

          else if (c == 'W')

          {
            getc(fp); getc(fp); getc(fp); getc(fp);
            fscanf(fp, "%d", &num);
            getc(fp);
            fscanf(fp, "%d", &num2);
            fgets(s, sizeof(s), fp);

            list[mnum]->add_wear(num, num2);
          }

          else done = 1;
        }

        list[mnum]->normalize_frequency();

        fgets(s, sizeof(s), fp);
        fgets(s, sizeof(s), fp);
      }

      if (fclose(fp) != 0)
        cout << "Error closing file!\n" << endl;
    }
  }
}