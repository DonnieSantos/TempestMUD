#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

long get_gametime()

{
  long Game_Minutes_Passed;
  long Total_Minutes_Passed;
  int real_year, real_month, real_day;
  int real_hour, real_minute, real_second;
  int NY = 0, LY = 0, extra_day = 0;
  int remainder, full_periods;
  int years_passed;

  SYSTEMTIME current_time;
  GetLocalTime(&current_time);
  real_year    =  current_time.wYear;
  real_month   =  current_time.wMonth;
  real_day     =  current_time.wDay;
  real_hour    =  current_time.wHour;
  real_minute  =  current_time.wMinute;
  real_second  =  current_time.wSecond;

  cout << real_year << endl;
  cout << real_month << endl;
  cout << real_day << endl;
  cout << real_hour << endl;
  cout << real_minute << endl;
  cout << real_second << endl;

  years_passed = real_year - 2004;

  if (years_passed > 0) {
    years_passed--;
    LY++; }

  if (years_passed > 0)

  {
    remainder = (years_passed % 4);
    full_periods = (years_passed / 4);

    while (full_periods > 0) {
      full_periods--;
      NY = NY + 3;
      LY = LY + 1; }

    if (remainder > 0) {
      remainder--;
      LY++; }

    if (remainder > 0)
      NY = NY + remainder;
  }

  Total_Minutes_Passed = ((NY*525600) + (LY*527040));

  if ((real_year % 4) == 0) extra_day = 1;

  if (real_month == 2) real_day = real_day + 31;
  else if (real_month == 3) real_day = real_day + extra_day + 59;
  else if (real_month == 4) real_day = real_day + extra_day + 90;
  else if (real_month == 5) real_day = real_day + extra_day + 120;
  else if (real_month == 6) real_day = real_day + extra_day + 151;
  else if (real_month == 7) real_day = real_day + extra_day + 181;
  else if (real_month == 8) real_day = real_day + extra_day + 212;
  else if (real_month == 9) real_day = real_day + extra_day + 243;
  else if (real_month == 10) real_day = real_day + extra_day + 273;
  else if (real_month == 11) real_day = real_day + extra_day + 304;
  else if (real_month == 12) real_day = real_day + extra_day + 334;

  Total_Minutes_Passed += ((real_day-1)*1440);
  Total_Minutes_Passed += (real_hour * 60);
  Total_Minutes_Passed += (real_minute);

  Game_Minutes_Passed = (Total_Minutes_Passed * 12);

  while (real_second >= 5) {
    Game_Minutes_Passed++;
    real_second -= 5; }

  //cout << Game_Minutes_Passed << endl;

  return Game_Minutes_Passed;
}

string get_gamedate(string format)

{
  int vallum = 1000, season = 1, day = 1, period = 1;
  string full_vallum, full_season, full_day, full_period;
  long gametime = get_gametime();

  while (gametime >= MINUTES_PER_VALLUM)  { gametime -= MINUTES_PER_VALLUM;  vallum++; }
  while (gametime >= MINUTES_PER_SEASON)  { gametime -= MINUTES_PER_SEASON;  season++; }
  while (gametime >= MINUTES_PER_DAY)     { gametime -= MINUTES_PER_DAY;     day++;    }
  if (gametime >= MINUTES_PER_PERIOD)     { gametime -= MINUTES_PER_PERIOD;  period++; }
  if (gametime >= MINUTES_PER_PERIOD)     { gametime -= MINUTES_PER_PERIOD;  period++; }
  if (gametime >= MINUTES_PER_PERIOD)     { gametime -= MINUTES_PER_PERIOD;  period++; }
  if (gametime >= MINUTES_PER_PERIOD+1)   { period++;                                  }

  full_vallum = intconvert(vallum);

  if (season == 1) full_season = "Spring";
  else if (season == 2) full_season = "Summer";
  else if (season == 3) full_season = "Autumn";
  else full_season = "Winter";

  full_day = intconvert(day);
  if (((day%10) == 1) && (day != 11)) full_day += "st";
  else if (((day%10) == 2) && (day != 12)) full_day += "nd";
  else if (((day%10) == 3) && (day != 13)) full_day += "rd";
  else full_day += "th";

  if (period == 1) full_period = "Night";
  else if (period == 2) full_period = "Morning";
  else if (period == 3) full_period = "Afternoon";
  else full_period = "Evening";

  if (format == "vallum")  return full_vallum;
  if (format == "season")  return full_season;
  if (format == "day")     return full_day;
  if (format == "period")  return full_period;

  return ("The " + full_period + " of the " + full_day + " day of " + full_season + ", Vallum " + full_vallum + ".");
}