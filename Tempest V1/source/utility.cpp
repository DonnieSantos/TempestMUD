#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

int random_int(int min, int max)

{
  max++;
  int d = max-min;
  int n = (int)(d * (rand() / (double)RAND_MAX));

  return (n+min);
}

int get_exp_percent(int num_group_members)

{
  if (num_group_members <= 0) return 0;
  else if (num_group_members == 1) return 100;
  else if (num_group_members == 2) return 75;
  else if (num_group_members == 3) return 60;
  else if (num_group_members == 4) return 50;
  else if (num_group_members == 5) return 40;
  else if (num_group_members == 6) return 35;
  else if (num_group_members == 7) return 30;
  else if (num_group_members == 8) return 27;
  else if (num_group_members == 9) return 25;
  else return 23;
}

int get_exp_deficit(int level_deficit)

{
  if (level_deficit <= 0) return 100;
  else if (level_deficit == 1) return 95;
  else if (level_deficit == 2) return 90;
  else if (level_deficit == 3) return 85;
  else if (level_deficit == 4) return 80;
  else if (level_deficit == 5) return 75;
  else if (level_deficit == 6) return 70;
  else if (level_deficit == 7) return 65;
  else if (level_deficit == 8) return 60;
  else if (level_deficit == 9) return 55;
  else if (level_deficit == 10) return 50;
  else if (level_deficit == 11) return 40;
  else if (level_deficit == 12) return 30;
  else if (level_deficit == 13) return 20;
  else if (level_deficit == 14) return 10;
  else if (level_deficit == 15) return 5;
  else if (level_deficit == 16) return 4;
  else if (level_deficit == 17) return 3;
  else if (level_deficit == 18) return 2;
  else if (level_deficit == 19) return 1;
  else return 0;
}

int get_exp_need(int lev)

{
  if (lev == 1) return 50000;
  else if (lev == 2) return 95000;
  else if (lev == 3) return 140000;
  else if (lev == 4) return 185000;
  else if (lev == 5) return 230000;
  else if (lev == 6) return 275000;
  else if (lev == 7) return 320000;
  else if (lev == 8) return 400000;
  else if (lev == 9) return 500000;
  else if (lev == 10) return 650000;
  else if (lev == 11) return 800000;
  else if (lev == 12) return 950000;
  else if (lev == 13) return 1100000;
  else if (lev == 14) return 1250000;
  else if (lev == 15) return 1400000;
  else if (lev == 16) return 1550000;
  else if (lev == 17) return 1700000;
  else if (lev == 18) return 1850000;
  else if (lev == 19) return 2000000;
  else if (lev == 20) return 2400000;
  else if (lev == 21) return 2800000;
  else if (lev == 22) return 3200000;
  else if (lev == 23) return 3600000;
  else if (lev == 24) return 4000000;
  else if (lev == 25) return 4400000;
  else if (lev == 26) return 4800000;
  else if (lev == 27) return 5200000;
  else if (lev == 28) return 5600000;
  else if (lev == 29) return 6000000;
  else if (lev == 30) return 7400000;
  else if (lev == 31) return 8800000;
  else if (lev == 32) return 10200000;
  else if (lev == 33) return 11600000;
  else if (lev == 34) return 13000000;
  else if (lev == 35) return 14400000;
  else if (lev == 36) return 15800000;
  else if (lev == 37) return 17200000;
  else if (lev == 38) return 18600000;
  else if (lev == 39) return 20000000;
  else if (lev == 40) return 23000000;
  else if (lev == 41) return 26000000;
  else if (lev == 42) return 29000000;
  else if (lev == 43) return 32000000;
  else if (lev == 44) return 35000000;
  else if (lev == 45) return 38000000;
  else if (lev == 46) return 41000000;
  else if (lev == 47) return 44000000;
  else if (lev == 48) return 47000000;
  else if (lev == 49) return 50000000;
  else if (lev == 50) return 52500000;
  else if (lev == 51) return 55000000;
  else if (lev == 52) return 57500000;
  else if (lev == 53) return 60000000;
  else if (lev == 54) return 62500000;
  else if (lev == 55) return 65000000;
  else if (lev == 56) return 67500000;
  else if (lev == 57) return 70000000;
  else if (lev == 58) return 72500000;
  else if (lev == 59) return 75000000;
  else if (lev == 60) return 77500000;
  else if (lev == 61) return 80000000;
  else if (lev == 62) return 82500000;
  else if (lev == 63) return 85000000;
  else if (lev == 64) return 87500000;
  else if (lev == 65) return 90000000;
  else if (lev == 66) return 92500000;
  else if (lev == 67) return 95000000;
  else if (lev == 68) return 97500000;
  else if (lev == 69) return 100000000;
  else if (lev == 70) return 110000000;
  else if (lev == 71) return 120000000;
  else if (lev == 72) return 130000000;
  else if (lev == 73) return 140000000;
  else if (lev == 74) return 150000000;
  else if (lev == 75) return 160000000;
  else if (lev == 76) return 170000000;
  else if (lev == 77) return 180000000;
  else if (lev == 78) return 190000000;
  else if (lev == 79) return 200000000;
  else if (lev == 80) return 230000000;
  else if (lev == 81) return 260000000;
  else if (lev == 82) return 290000000;
  else if (lev == 83) return 320000000;
  else if (lev == 84) return 350000000;
  else if (lev == 85) return 380000000;
  else if (lev == 86) return 410000000;
  else if (lev == 87) return 440000000;
  else if (lev == 88) return 470000000;
  else if (lev == 89) return 500000000;
  else if (lev == 90) return 550000000;
  else if (lev == 91) return 600000000;
  else if (lev == 92) return 650000000;
  else if (lev == 93) return 700000000;
  else if (lev == 94) return 800000000;
  else if (lev == 95) return 850000000;
  else if (lev == 96) return 900000000;
  else if (lev == 97) return 950000000;
  else if (lev == 98) return 999999999;

  return 0;
}

string sclassify(string s)

{
  string sclass = "";

  if (s == "Warrior")            sclass = "#BWa#N";
  else if (s == "Knight")        sclass = "#CKn#N";
  else if (s == "Berzerker")     sclass = "#RBr#N";
  else if (s == "Paladin")       sclass = "#bPa#N";
  else if (s == "Death Knight")  sclass = "#BDk#N";
  else if (s == "Crusader")      sclass = "#cCr#N";
  else if (s == "Anti Paladin")  sclass = "#yAp#N";
  else if (s == "Thief")         sclass = "#YTh#N";
  else if (s == "Assassin")      sclass = "#rAs#N";
  else if (s == "Rogue")         sclass = "#YRg#N";
  else if (s == "Ranger")        sclass = "#GRa#N";
  else if (s == "Shadowblade")   sclass = "#bSb#N";
  else if (s == "Merchant")      sclass = "#YMe#N";
  else if (s == "Mercenary")     sclass = "#CMc#N";
  else if (s == "Cleric")        sclass = "#GCl#N";
  else if (s == "Druid")         sclass = "#gDr#N";
  else if (s == "Monk")          sclass = "#GMo#N";
  else if (s == "Priest")        sclass = "#nPr#N";
  else if (s == "Dark Cleric")   sclass = "#BDc#N";
  else if (s == "Healer")        sclass = "#bHl#N";
  else if (s == "Alchemist")     sclass = "#gAl#N";
  else if (s == "Mage")          sclass = "#MMg#N";
  else if (s == "Wizard")        sclass = "#mWi#N";
  else if (s == "Illusionist")   sclass = "#yIl#N";
  else if (s == "Warlock")       sclass = "#MWl#N";
  else if (s == "Sorcerer")      sclass = "#gSc#N";
  else if (s == "Summoner")      sclass = "#cSm#N";
  else if (s == "Shapeshifter")  sclass = "#RSh#N";

  return sclass;
}

string color(string str, char c, int i)

{
  string temp;

  str = str.erase(0, 2);

  if (i == 1)

  {
    if (c == 'R')
      temp = RED;
    else if (c == 'r')
      temp = L_RED;
    else if (c == 'B')
      temp = BLUE;
    else if (c == 'b')
      temp = L_BLUE;
    else if (c == 'G')
      temp = GREEN;
    else if (c == 'g')
      temp = L_GREEN;
    else if (c == 'Y')
      temp = BROWN;
    else if (c == 'y')
      temp = YELLOW;
    else if (c == 'M')
      temp = PURPLE;
    else if (c == 'm')
      temp = L_PURPLE;
    else if (c == 'C')
      temp = CYAN;
    else if (c == 'c')
      temp = L_CYAN;
    else if (c == 'N')
      temp = GRAY;
    else if (c == 'n')
      temp = WHITE;
  }

  return temp + str;
}

string colorize(string s, int j)

{
  int i = 0;
  string temp, temp2;
  char c;

  while (i != (signed)s.length())

  {
    if (s[i] == '#')

    {
      temp2 = s.substr(0, i);
      temp = s.substr(i, s.length());
      c = s[i+1];

      if (c == 'r' || c == 'R' || c == 'b' || c == 'B' || c == 'G' || c == 'g' || c == 'Y' ||
          c == 'y' || c == 'M' || c == 'm' || c == 'C' || c == 'c' || c == 'N' || c == 'n')

      {
        temp = color(temp, c, j);
        i = i-2;
      }

      s = temp2 + temp;
    }

    i++;
  }

  if (j == 1)
    s = s + "\33[0;37m";

  return s;
}

int letter(char c)

{
  if (((c >= 97) && (c <= 122))
  || ((c >= 65) && (c <= 90)))
    return 1;

  return 0;
}

int number(char c)

{
  if ((c >= 48) && (c <= 57))
    return 1;

  return 0;
}

int number(string s)

{
  int size = s.size();

  for (int i=0; i<size; i++)
    if (!number(s[i]))
      return 0;

  return 1;
}

int nonzero_number(char c)

{
  if ((c >= 49) && (c <= 57))
    return 1;

  return 0;
}

string intconvert(int i)

{
  stringstream ss;

  ss.clear();
  ss.str("");
  ss << i;

  return ss.str();
}

int stringconvert(string s1)

{
  istringstream iss;
  int temp;

  iss.clear();
  iss.str("");

  iss.str(s1);
  iss >> temp;

  return temp;
}

string lowercase(string s)

{
  for (int i=0; i<=(int)s.size()-1; i++)

  {
    if ((s[i] >= 65) && (s[i] <= 90))
      s[i] = s[i] + 32;
  }

  return s;
}

string uppercase(string s)

{
  for (int i=0; i<=(int)s.size()-1; i++)

  {
    if ((s[i] >= 97) && (s[i] <= 122))
      s[i] = s[i] - 32;
  }

  return s;
}

int short_for(string srt, string full)

{
  int size = full.size();
  int ssize = srt.size();
  int count = 0;

  if ( (ssize < 2) || (ssize > size) )
    return 0;

  while (count < ssize)

  {
    if (srt[count] != full[count])
      return 0;

    count++;
  }

  return 1;
}

int clip_number(string *key)

{
  int num, size = key->size();
  string str;
  char sstr[1], cstr[2], ckey[20];

  if (size > 3)

  {
    str = *key;

    for (int i=0; i<size; i++)
      ckey[i] = str[i];

    if ((ckey[0] >= 49) && (ckey[0] <=57))

    {
      if ((ckey[1] >= 48) && (ckey[1] <=57) && (ckey[2] == '.'))

      {
        cstr[0] = ckey[0];
        cstr[1] = ckey[1];
        num = atoi(cstr);
        key->erase(0,3);
        return num;
      }

      else if (ckey[1] == '.')

      {
        sstr[0] = ckey[0];
        num = atoi(sstr);
        key->erase(0,2);
        return num;
      }
    }
  }

  return 1;
}

int color_character(char c)

{
  if ((c == 'R') || (c == 'r') || (c == 'B') || (c == 'b')
   || (c == 'G') || (c == 'g') || (c == 'Y') || (c == 'y')
   || (c == 'M') || (c == 'm') || (c == 'C') || (c == 'c')
   || (c == 'N') || (c == 'n')) return 1;

  return 0;
}

int visible_size(string s)

{
  int size = s.size();
  int vsize = s.size();

  for (int i=size-1; i>0; i--)

  {
    if ((s[i-1] == '#') && (color_character(s[i])))
      vsize = vsize -2;
  }

  return vsize;
}

string variable_allign(string str, int vline_length)

{
  int index = vline_length;
  int cut, drop, count;
  int last = 1;

  if ((int)str.size() > vline_length)

  {
    while (index < (int)str.size())

    {
      for (int i=last; i<index; i++)
        if ((str[i-1] == '#') && (color_character(str[i])))
          index = index+2;

      if (index < (int)str.size()) {

      cut = index-1;
      drop = index;

      if ((str[cut] != ' ') && (str[drop] == ' ')) {
        str.insert(index, "\r\n");
        str.erase(drop+2, 1); }

      else if ((str[cut] != ' ') && (str[drop] != ' '))

      {
        count = 0;
        while ((str[cut] != ' ') && (str[cut] != '\n')) { count++; cut--; }
        while ((str[drop] != ' ') && (drop < (int)str.size())) { count++; drop++; }
        cut = index-1;

        if (count <= vline_length)

        {
          while (str[cut] != ' ')
            cut--;

          for (int i=1; i<=(index-1-cut); i++)
            str.insert(cut, " ");

          str.insert(index, "\r\n");
        }

        else str.insert(index, "\r\n");
      }

      else str.insert(index, "\r\n");

      last = index;
      index = index + vline_length+2; }
    }
  }

  while (str.find(" \r\n") != string::npos) {
    int x = str.find(" \r\n");
    str.erase(x, 1); }

  return str;
}

string allign(string str)

{
  return variable_allign(str, LINE_LENGTH);
}

string rm_allign(string str)

{
  return variable_allign(str, RLINE_LENGTH);
}

int direction(string str)

{
  if ((str == "north") || (str == "nort") || (str == "nor") || (str == "no") || (str == "n")
  || (str == "south") || (str == "sout") || (str == "sou") || (str == "so") || (str == "s")
  || (str == "east") || (str == "eas") || (str == "ea") || (str == "e")
  || (str == "west") || (str == "wes") || (str == "we") || (str == "w")
  || (str == "down") || (str == "dow") || (str == "do") || (str == "d")
  || (str == "up") || (str == "u")) return 1;

  return 0;
}

string clear_whitespace(string s)

{
  int size = s.size(), temp;
  string tempstr = "X";

  if (size < 1) {
    s = "Invalid_String";
  }

  else if (size == 1) {
    if (s[0] == ' ')
      s = "Invalid_String";
  }

  else

  {
    while (s[size-1] == ' ') {
      s = s.substr(0, size-1);
      size--; }

    temp = s.find("  ");

    while (temp >= 0) {
      tempstr = s.substr(0, temp+1) + s.substr(temp+2, size-temp+2);
      s = tempstr;
      size--;
      temp = s.find("  "); }

    while (s[0] == ' ') {
      s =  s.substr(1, size-1);
      size--; }
  }

  return s;
}

string clip_end(string* str)

{
  string word = *str;

  while (last(word) != "") word = last(word);

  *str = (*str).substr(0, (*str).size()-word.size());

  if ((*str)[(*str).size()-1] == ' ') (*str).erase((*str).size()-1, 1);

  return word;
}

string first(string s)

{
  string temp;
  int i = s.find(" ");

  if (i == -1)
    return s;

  temp = s.substr(0, i);
  return temp;
}

string last(string s)

{
  if (s == "") return "";
  if (s.find(" ") == string::npos) return "";

  int i = s.find(" ");
  if (i < 0) return "";

  return s.substr(i+1, s.size()-i-1);
}

int legal_desc(string s)

{
  int size = s.size(), count = 0;

  for (int i=0; i<size; i++) {
    if (count >= 60) return 0;
    if (s[i] == ' ') count = 0;
    else count++; }

  return 1;
}

string exp_commas(string str)

{
  int size = str.size();

  if ((size >= 7) && (size <= 9)) {
    str.insert(size-6, ",");
    str.insert(size-2, ","); }

  else if ((size >= 4) && (size <= 6))
    str.insert(size-3, ",");

  return str;
}

string remove_dashes(string str)

{
  int size = str.length();

  for (int i=0; i<size; i++)
    if (str[i] == '-')
      str[i] = ' ';

  return str;
}

string full_day(string str)

{
  if (str == "Sun") return "Sunday";
  else if (str == "Mon") return "Monday";
  else if (str == "Tue") return "Tuesday";
  else if (str == "Wed") return "Wednesday";
  else if (str == "Thu") return "Thursday";
  else if (str == "Fri") return "Friday";
  else if (str == "Sat") return "Saturday";
  else return "X";
}

string full_date(string str)

{
  string temp = "", fdate;

  fdate = "";
  temp += str[0];
  temp += str[1];

  if (temp == "01") fdate = "January ";
  else if (temp == "02") fdate = "February ";
  else if (temp == "03") fdate = "March ";
  else if (temp == "04") fdate = "April ";
  else if (temp == "05") fdate = "May ";
  else if (temp == "06") fdate = "June ";
  else if (temp == "07") fdate = "July ";
  else if (temp == "08") fdate = "August ";
  else if (temp == "09") fdate = "September ";
  else if (temp == "10") fdate = "October ";
  else if (temp == "11") fdate = "November ";
  else if (temp == "12") fdate = "December ";

  temp = "";
  temp += str[3];
  temp += str[4];

  if (temp == "01") fdate += "1st, 20";
  else if (temp == "02") fdate += "2nd, 20";
  else if (temp == "03") fdate += "3rd, 20";
  else if (temp == "04") fdate += "4th, 20";
  else if (temp == "05") fdate += "5th, 20";
  else if (temp == "06") fdate += "6th, 20";
  else if (temp == "07") fdate += "7th, 20";
  else if (temp == "08") fdate += "8th, 20";
  else if (temp == "09") fdate += "9th, 20";
  else if (temp == "10") fdate += "10th, 20";
  else if (temp == "11") fdate += "11th, 20";
  else if (temp == "12") fdate += "12th, 20";
  else if (temp == "13") fdate += "13th, 20";
  else if (temp == "14") fdate += "14th, 20";
  else if (temp == "15") fdate += "15th, 20";
  else if (temp == "16") fdate += "16th, 20";
  else if (temp == "17") fdate += "17th, 20";
  else if (temp == "18") fdate += "18th, 20";
  else if (temp == "19") fdate += "19th, 20";
  else if (temp == "20") fdate += "20th, 20";
  else if (temp == "21") fdate += "21st, 20";
  else if (temp == "22") fdate += "22nd, 20";
  else if (temp == "23") fdate += "23rd, 20";
  else if (temp == "24") fdate += "24th, 20";
  else if (temp == "25") fdate += "25th, 20";
  else if (temp == "26") fdate += "26th, 20";
  else if (temp == "27") fdate += "27th, 20";
  else if (temp == "28") fdate += "28th, 20";
  else if (temp == "29") fdate += "29th, 20";
  else if (temp == "30") fdate += "30th, 20";
  else if (temp == "31") fdate += "31st, 20";

  temp = "";
  temp += str[6];
  temp += str[7];

  fdate += temp;

  return fdate;
}

string full_time(string str)

{
  string ftime = "", ampm = "AM", tempstr = "";
  int temp;

  if (str[0] != '0')
    ftime += str[0];

  ftime += str[1];
  temp = stringconvert(ftime);

  if (temp > 12) {
    temp = temp - 12;
    ampm = "PM"; }

  ftime  = intconvert(temp);
  tempstr += str[3];
  tempstr += str[4];

  ftime += ":" + tempstr + " " + ampm;

  return ftime;
}

int blank(string str)

{
  int x;

  if (str == "") return 1;

  x = str.find("\r", 0);

  while (x != (int)string::npos) {
    str.erase(x, 1);
    x = str.find("\r", 0); }

  x = str.find("\n", 0);

  while (x != (int)string::npos) {
    str.erase(x, 1);
    x = str.find("\n", 0); }

  x = str.find(" ", 0);

  while (x != (int)string::npos) {
    str.erase(x, 1);
    x = str.find(" ", 0); }

  if (visible_size(str) <= 0)
    return 1;

  return 0;
}

string format_plan(string str)

{
  int count = 1;
  int begin = 0, end = 0, last = str.size()-1;
  string temp = "", xs;

  if (str == "") return "";

  while ((end <= last) && (count <= 15))

  {
    while (str[end] != '\n')
      end++;

    xs = "";
    for (int i=1; i<=57-visible_size(str.substr(begin, end-begin)); i++) xs += " ";

    temp += "  #n|#N " + str.substr(begin, end-begin) + xs + " #n|#N\r\n";

    begin = end+1;
    end = end+1;

    count++;
  }

  temp = temp.substr(0, temp.size()-2);

  if (blank(str)) return "";

  return temp;
}

string format_desc(string str)

{
  int x;

  if (str == "") return "DEFAULT";
  if (blank(str)) return "DEFAULT";

  str = clear_whitespace(str);

  x = str.find("\r", 0);

  while (x != (int)string::npos) {
    str.erase(x, 1);
    x = str.find("\r", 0); }

  x = str.find("\n", 0);

  while (x != (int)string::npos) {
    str.erase(x, 1);
    x = str.find("\n", 0); }

  str = rm_allign(str);

  if (blank(str)) return "DEFAULT";

  return str;
}

string key(string s)

{
  return lowercase(first(s));
}

string possessive(string str)

{
  int size = str.size();

  if ((str[size-1] == 's') || (str[size-1] == 'S'))
    return (str + "'");

  else return (str + "'s");
}

string class_plural(string str)

{
  if (str == "Thief") return "Thieves";
  if (str == "Mercenary") return "Mercenaries";

  return (str + "s");
}

string string_stacker(string str)

{
  stringarray string_stack;
  int size = str.size();
  string temp = "";
  int more;

  for (int i=0; i<size; i++)

  {
    if (str[i] == '\n') {
      string_stack.put(temp);
      temp = "";
      more = 0; }

    else if (str[i] != '\r') {
      temp += str[i];
      more = 1; }
  }

  if (more) string_stack.put(temp);

  temp = "";
  size = string_stack.size();

  for (int i=0; i<size; i++)

  {
    int seen_before = 0;

    for (int j=0; j<i; j++)
      if (string_stack.get(j) == string_stack.get(i))
        seen_before = 1;

    if (!seen_before)

    {
      int count = string_stack.count(string_stack.get(i));

      if (count == 1)
        temp += string_stack.get(i) + "\r\n";

      else if (count > 1)
        temp += string_stack.get(i) + " (" + intconvert(count) + ")\r\n";
    }
  }

  if (size > 0) temp.erase(temp.size()-2, 2);

  return temp;
}

string format_identify(string str)

{
  stringarray string_stack;
  int more, maxsize = 0;
  string temp;

  for (int i=0; i<(int)str.size(); i++)

  {
    if (str[i] == '\n') {
      string_stack.put(temp);
      temp = "";
      more = 0; }

    else if (str[i] != '\r') {
      temp += str[i];
      more = 1; }
  }

  if (more) string_stack.put(temp);

  for (int i=0; i<string_stack.size(); i++)
    if (visible_size(string_stack.get(i)) > maxsize)
      maxsize = visible_size(string_stack.get(i));

  string border = "#n+-";  for (int i=1; i<=maxsize; i++) border += "-";  border += "-+#N";
  temp = border + "\r\n";

  for (int i=0; i<string_stack.size(); i++)

  {
    string xtra = "";
    int tsize = maxsize - visible_size(string_stack.get(i));
    for (int j=1; j<=tsize; j++) xtra += " ";

    if (string_stack.get(i).find("Flags:") != string::npos) temp += border + "\r\n";
    temp += "#n| #N" + string_stack.get(i) + xtra + " #n|#N\r\n";
  }

  temp += border;

  return temp;
}

string rainbow_string(string str, string valid)

{
  int count = 0;
  int position = 0;

  int allok = 0;
  if ((valid == "all") || (valid == "All")) allok = 1;

  while (position < (int)str.size())
  if ((allok) || (valid.find(str.substr(position,1)) != string::npos))

  {
    count++;

    switch(count) {
      case 1: str.insert(position, "#r"); break;
      case 2: str.insert(position, "#y"); break;
      case 3: str.insert(position, "#g"); break;
      case 4: str.insert(position, "#c"); break;
      case 5: str.insert(position, "#b"); break;
      case 6: str.insert(position, "#m"); break; }

    position = position + 3;
    if (count > 5) count = 0;
  }

  else position++;

  return str;
}

int speedwalk_legal(string key)

{
  int size = key.size();
  int count = 0;

  for (int i=0; i<size; i++)

  {
    if (nonzero_number(key[i])) {
      if (i == (size-1)) return 0;
      if (!direction(key.substr(i+1, 1))) return 0; }

    else if (!direction(key.substr(i, 1))) return 0;
  }

  for (int j=0; j<size; j++)

  {
    if (count > SPEEDWALK_LIMITER) return 0;

     if (nonzero_number(key[j])) {
      count = count + key[j] - 48;
      j++; }

    else count++;
  }

  if (count > SPEEDWALK_LIMITER) return 0;

  return 1;
}

int determine_gold_string(string str)

{
  string temp1 = key(str);
  string temp2 = key(last(str));

  if (number(temp1))
    if (lowercase(temp2).find("gold") != string::npos)
      return 1;

  return 0;
}

int match(string str, string s1, string s2)

{
  if (str[0] != s1[0]) return 0;
  if ((str == s1) || (str == s2)) return 1;

  s2 = s2.substr(s1.size(), s2.size()-s1.size());

  while (s2.size() > 0) {
    s1 = s1 + s2.substr(0, 1);
    s2 = s2.substr(1, s2.size()-1);
    if (str == s1) return 1; }

  return 0;
}

string remove_colors(string str)

{
  while (visible_size(str) < (int)str.size())
    str.erase(str.find("#"), 2);

  return str;
}

string proper_name(string str)

{
  str = lowercase(str);

  if ((str[0] >= 97) && (str[0] <= 122))
    str[0] = str[0] - 32;

  return str;
}

int get_width(string str)

{
  int count = 0;
  int maxcount = 0;

  while (str.size() > 0)

  {
    if ((str[0] != '\r') && (str[0] != '\n')) {
      count++;
      if (count > maxcount) maxcount = count; }

    else count = 0;

    str.erase(0,1);
  }

  return maxcount;
}

string strip_string(string *str)

{
  int count = 0;
  string temp = "";
  string str_copy = *str;

  while ((str_copy[count] != '\r') && (str_copy[count] != '\n') && ((int)str->size() > count)) {
    temp += str_copy[count];
    count++; }

  str->erase(0, temp.size());
  str_copy = *str;

  while (((str_copy[0] == '\r') || (str_copy[0] == '\n')) && (str->size() > 0)) {
    str->erase(0, 1);
    str_copy = *str; }

  return temp;
}

string remove_bsr(string str)

{
  while ((str.find("\r") != string::npos) && (str.size() > 0))
    str.erase(str.find("\r"), 1);

  return str;
}

string remove_bsn(string str)

{
  while ((str.find("\n") != string::npos) && (str.size() > 0))
    str.erase(str.find("\n"), 1);

  return str;
}

int ncount(string str)

{
  int count = 0;

  for (int i=0; i<(int)str.size(); i++)
    if (str[i] == '\n') count++;

  return count;
}

void format_ansi_output(string *s)

{
  string temp = *s;
  int reached_begin = 0, reached_end = 0;

  for (int i=0; i<(int)temp.size(); i++) {
    if (reached_begin) break;
    if (((temp[i] == '#') && (color_character(temp[i+1]))) && (i <= (int)temp.size()-2)) i++;
    else if ((temp[i] == '\r') || (temp[i] == '\n')) { temp.erase(i,1);  i--; }
    else reached_begin = 1; }

  for (int i=(int)temp.size()-1; i>=0; i--) {
    if (reached_end) break;
    if (((temp[i-1] == '#') && (color_character(temp[i]))) && (i >= 1)) i--;
    else if ((temp[i] == '\r') || (temp[i] == '\n')) temp.erase(i,1);
    else reached_end = 1; }

  *s = "\r\n\n" + temp;
}

string ReadLine(FILE* fp)

{
  char s[500];
  string str;

  fgets(s, sizeof(s), fp);
  str = s;
  str = str.substr(0, str.length()-1);

  return str;
}

string all_word_replace(string str, string old_word, string new_word)

{
  int pos;

  while (str.find(old_word) != string::npos)

  {
    pos = str.find(old_word);
    str.erase(pos, old_word.size());
    str.insert(pos, new_word);
  }

  return str;
}

int abbreviation(string a, string s)

{
  a = lowercase(a);
  s = lowercase(s);

  if (a == s) return 1;

  if (a.size() < 3) return 0;
  if (s.size() < 3) return 0;
  if (s.find(a) == string::npos) return 0;

  int pos = s.find(a);

  if (pos == 0) return 1;

  if (s[pos-1] == ' ') return 1;

  return 0;
}

string capital_lname(string lname)

{
  string temp = lname;

  if ((int)lname.length() < 1) return temp;

  if ((temp[0] >= 97) && (temp[0] <= 122))
    temp[0] = temp[0] - 32;

  return temp;
}

string last_command(string str)

{
  if (str == "") return str;

  while (str.find(";;") != string::npos)

  {
    int pos = str.find(";;");
    str.erase(0,pos+2);
  }

  return str;
}

void exit_error(string msg)

{
  cout << msg << endl;
  exit(1);
}