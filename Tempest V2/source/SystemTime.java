import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

class SystemTime extends Utility

{
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Calendar CALENDAR = new GregorianCalendar(TimeZone.getDefault());

  public static SimpleDateFormat CLOCK = new SimpleDateFormat("HH:mm:ss");
  public static SimpleDateFormat STAMP = new SimpleDateFormat("MM/dd/yy - HH:mm:ss:SSS");
  public static SimpleDateFormat SDATE = new SimpleDateFormat("MM/dd/yy");

  public static final int MINUTES_PER_PERIOD = 365;
  public static final int MINUTES_PER_DAY    = 1461;
  public static final int MINUTES_PER_SEASON = 131490;
  public static final int MINUTES_PER_VALLUM = 525960;

  public static final int ONE_MONTH    = MINUTES_PER_VALLUM;
  public static final int ONE_WEEK     = MINUTES_PER_SEASON;
  public static final int ONE_DAY      = ONE_WEEK / 7;
  public static final int ONE_HOUR     = ONE_DAY / 24;
  public static final int ONE_MINUTE   = ONE_HOUR / 60;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static final int FIRST_YEAR = 2005;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static String thisTimeStamp = "";
  private static String lastTimeStamp = "";
  private static long stampCount = 0;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void update()          { CALENDAR.setTime(new Date());    }
  public static String getTimeStamp()  { return STAMP.format(new Date()); }
  public static String getTime()       { return CLOCK.format(new Date()); }
  public static String getDate()       { return SDATE.format(new Date()); }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static String getItemTimeStamp()

  {
    thisTimeStamp = STAMP.format(new Date());

    if (thisTimeStamp.equals(lastTimeStamp))

    {
      stampCount++;
      lastTimeStamp = thisTimeStamp;
      thisTimeStamp += " (" + stampCount + ")";
    }

    else

    {
      stampCount = 0;
      lastTimeStamp = thisTimeStamp;
    }

    return thisTimeStamp;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static String displayDate()

  {
    String theDay = "";
    String theDate = "";
    String theTime = "";

    update();

    int day    =  CALENDAR.get(Calendar.DAY_OF_WEEK);
    int date   =  CALENDAR.get(Calendar.DAY_OF_MONTH);
    int month  =  CALENDAR.get(Calendar.MONTH);
    int year   =  CALENDAR.get(Calendar.YEAR);
    int hour   =  CALENDAR.get(Calendar.HOUR);
    int min    =  CALENDAR.get(Calendar.MINUTE);
    int AMPM   =  CALENDAR.get(Calendar.AM_PM);

    if (day == CALENDAR.SUNDAY)          theDay = "Sunday";
    else if (day == CALENDAR.MONDAY)     theDay = "Monday";
    else if (day == CALENDAR.TUESDAY)    theDay = "Tuesday";
    else if (day == CALENDAR.WEDNESDAY)  theDay = "Wednesday";
    else if (day == CALENDAR.THURSDAY)   theDay = "Thursday";
    else if (day == CALENDAR.FRIDAY)     theDay = "Friday";
    else if (day == CALENDAR.SATURDAY)   theDay = "Saturday";

    if (month == CALENDAR.JANUARY)         theDate = "January";
    else if (month == CALENDAR.FEBRUARY)   theDate = "February";
    else if (month == CALENDAR.MARCH)      theDate = "March";
    else if (month == CALENDAR.APRIL)      theDate = "April";
    else if (month == CALENDAR.MAY)        theDate = "May";
    else if (month == CALENDAR.JUNE)       theDate = "June";
    else if (month == CALENDAR.JULY)       theDate = "July";
    else if (month == CALENDAR.AUGUST)     theDate = "August";
    else if (month == CALENDAR.SEPTEMBER)  theDate = "September";
    else if (month == CALENDAR.OCTOBER)    theDate = "October";
    else if (month == CALENDAR.NOVEMBER)   theDate = "November";
    else if (month == CALENDAR.DECEMBER)   theDate = "December";

    if (date == 1) theDate += " 1st, ";
    else if (date == 2) theDate += " 2nd, ";
    else if (date == 3) theDate += " 3rd, ";
    else if (date == 4) theDate += " 4th, ";
    else if (date == 5) theDate += " 5th, ";
    else if (date == 6) theDate += " 6th, ";
    else if (date == 7) theDate += " 7th, ";
    else if (date == 8) theDate += " 8th, ";
    else if (date == 9) theDate += " 9th, ";
    else if (date == 10) theDate += " 10th, ";
    else if (date == 11) theDate += " 11th, ";
    else if (date == 12) theDate += " 12th, ";
    else if (date == 13) theDate += " 13th, ";
    else if (date == 14) theDate += " 14th, ";
    else if (date == 15) theDate += " 15th, ";
    else if (date == 16) theDate += " 16th, ";
    else if (date == 17) theDate += " 17th, ";
    else if (date == 18) theDate += " 18th, ";
    else if (date == 19) theDate += " 19th, ";
    else if (date == 20) theDate += " 20th, ";
    else if (date == 21) theDate += " 21st, ";
    else if (date == 22) theDate += " 22nd, ";
    else if (date == 23) theDate += " 23rd, ";
    else if (date == 24) theDate += " 24th, ";
    else if (date == 25) theDate += " 25th, ";
    else if (date == 26) theDate += " 26th, ";
    else if (date == 27) theDate += " 27th, ";
    else if (date == 28) theDate += " 28th, ";
    else if (date == 29) theDate += " 29th, ";
    else if (date == 30) theDate += " 30th, ";
    else if (date == 31) theDate += " 31st, ";

    theDate += "" + year;

    theTime = hour + ":";
    if (min < 10) theTime += "0";
    theTime += min + " ";

    if (AMPM == CALENDAR.AM) theTime += "AM";
    else if (AMPM == CALENDAR.PM) theTime += "PM";

    String output = theDay + ", " + theDate + " -- " + theTime + ".";

    return output;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static long getGametime()

  {
    long Game_Minutes_Passed;
    long Total_Minutes_Passed;
    int NY = 0, LY = 0, extra_day = 0;
    int remainder, full_periods;
    int years_passed;

    update();

    int real_year    =  CALENDAR.get(Calendar.YEAR);
    int real_month   =  CALENDAR.get(Calendar.MONTH)+1;
    int real_day     =  CALENDAR.get(Calendar.DAY_OF_MONTH);
    int real_hour    =  CALENDAR.get(Calendar.HOUR);
    int real_minute  =  CALENDAR.get(Calendar.MINUTE);
    int real_second  =  CALENDAR.get(Calendar.SECOND);
    int AMPM         =  CALENDAR.get(Calendar.AM_PM);

    if (AMPM == CALENDAR.PM) real_hour += 12;

    years_passed = real_year - FIRST_YEAR;

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

    return Game_Minutes_Passed;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static String getGamedate(String format)

  {
    int vallum = 1000, season = 1, day = 1, period = 1;
    String full_vallum, full_season, full_day, full_period;
    long gametime = getGametime();

    while (gametime >= MINUTES_PER_VALLUM)  { gametime -= MINUTES_PER_VALLUM;  vallum++; }
    while (gametime >= MINUTES_PER_SEASON)  { gametime -= MINUTES_PER_SEASON;  season++; }
    while (gametime >= MINUTES_PER_DAY)     { gametime -= MINUTES_PER_DAY;     day++;    }
    if (gametime >= MINUTES_PER_PERIOD)     { gametime -= MINUTES_PER_PERIOD;  period++; }
    if (gametime >= MINUTES_PER_PERIOD)     { gametime -= MINUTES_PER_PERIOD;  period++; }
    if (gametime >= MINUTES_PER_PERIOD)     { gametime -= MINUTES_PER_PERIOD;  period++; }
    if (gametime >= MINUTES_PER_PERIOD+1)   { period++;                                  }

    full_vallum = "" + vallum;

    if (season == 1) full_season = "Spring";
    else if (season == 2) full_season = "Summer";
    else if (season == 3) full_season = "Autumn";
    else full_season = "Winter";

    full_day = "" + day;
    if (((day%10) == 1) && (day != 11)) full_day += "st";
    else if (((day%10) == 2) && (day != 12)) full_day += "nd";
    else if (((day%10) == 3) && (day != 13)) full_day += "rd";
    else full_day += "th";

    if (period == 1) full_period = "Night";
    else if (period == 2) full_period = "Morning";
    else if (period == 3) full_period = "Afternoon";
    else full_period = "Evening";

    if (format.equalsIgnoreCase("vallum"))  return full_vallum;
    if (format.equalsIgnoreCase("season"))  return full_season;
    if (format.equalsIgnoreCase("day"))     return full_day;
    if (format.equalsIgnoreCase("period"))  return full_period;

    String ftime = "The ";
    ftime += full_period + " of the " + full_day + " day of ";
    ftime += full_season + ", Vallum " + full_vallum + ".";

    return ftime;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
}