import java.util.*;
import java.io.*;
import java.lang.*;
import java.nio.channels.*;
import java.lang.reflect.Array;

public abstract class Utility extends Transfer

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int CSTATE_LOGIN    = 0;
  public static final int CSTATE_CREATION = 1;
  public static final int CSTATE_NORMAL   = 2;
  public static final int CSTATE_LINKDEAD = 3;
  public static final int CSTATE_IMM_AT   = 4;
  public static final int CSTATE_WRITING  = 5;
  public static final int CSTATE_POSSESS  = 6;
  public static final int CSTATE_BUILDING = 7;
  public static final int CSTATE_MENU     = 8;

  public static final int PSTATE_STANDING = 1;
  public static final int PSTATE_SITTING  = 2;
  public static final int PSTATE_RESTING  = 3;
  public static final int PSTATE_SLEEPING = 4;
  public static final int PSTATE_FIGHTING = 5;

  public static final int ALIGN_NEUTRAL   = 0;
  public static final int ALIGN_LIGHT     = 1;
  public static final int ALIGN_DARK      = 2;

  public static final int ECHO_ALL        = 0;
  public static final int ECHO_AWAKE      = 1;

  public static final int PROMPT_NONE     = 0;
  public static final int PROMPT_NORMAL   = 1;
  public static final int PROMPT_ANSI     = 2;
  public static final int PROMPT_WRITING  = 3;
  public static final int PROMPT_LOGIN    = 4;
  public static final int PROMPT_PASSWORD = 5;
  public static final int PROMPT_NEWCHAR  = 6;
  public static final int PROMPT_NEWPASS  = 7;
  public static final int PROMPT_CREATION = 8;

  public static final int CREATION_NULL   = 0;
  public static final int CREATION_GENDER = 1;
  public static final int CREATION_CITY   = 2;
  public static final int CREATION_CLASS  = 3;

  public static final int LD_TIMEOUT      = 5;

  public static final int CHARS_PER_ACCOUNT    = 10;
  public static final int MAX_ACCT_NAME_LENGTH = 12;
  public static final int MAX_CHAR_NAME_LENGTH = 12;
  public static final int MAX_PASSWORD_LENGTH  = 12;

  public static final int PLAN_WIDTH           = 57;
  public static final int NOTE_TITLE_LENGTH    = 45;
  public static final int LINE_LENGTH          = 75;
  public static final int RLINE_LENGTH         = 65;
  public static final int INFO_WIDTH           = 60;
  public static final int SPEECH_LIMITER       = 350;
  public static final int SPEEDWALK_LIMITER    = 45;
  public static final int MAX_NUM_ALIASES      = 100;
  public static final int MAX_SCREEN_SIZE      = 100;
  public static final int MAX_SAY_HISTORY      = 25;
  public static final int WRITING_WIDTH        = 61;
  public static final int ITEM_NAME_LENGTH     = 35;
  public static final int ITEM_FORMATTED_WIDTH = 65;
  public static final int ITEM_EXACT_WIDTH     = 80;
  public static final int ROOM_EXACT_WIDTH     = 80;
  public static final int ROOM_FORMATTED_WIDTH = 65;
  public static final int LOOK_FORMATTED_WIDTH = 65;
  public static final int ITEM_GDESC_LENGTH    = 75;
  public static final int ROOM_TITLE_LENGTH    = 50;
  public static final int ROOM_LOOK_LENGTH     = 70;
  public static final int ACTION_NAME_LENGTH   = 60;
  public static final int MAX_MOB_NAME_LENGTH  = 25;
  public static final int MAX_MOB_KEY_LENGTH   = 45;
  public static final int MAX_PRENAME_LENGTH   = 13;
  public static final int MAX_PTITLE_LENGTH    = 42;
  public static final int MAX_MTITLE_LENGTH    = 75;
  public static final int ZONE_NAME_LENGTH     = 25;

  public static final double RESISTANCE_CAP = 0.8;

  public static final int DIRECTION_N = 0;
  public static final int DIRECTION_S = 1;
  public static final int DIRECTION_E = 2;
  public static final int DIRECTION_W = 3;
  public static final int DIRECTION_U = 4;
  public static final int DIRECTION_D = 5;

  public static final int ENTER_LOGIN    = 0;
  public static final int ENTER_MOVE     = 1;
  public static final int ENTER_FOLLOW   = 2;
  public static final int ENTER_REBORN   = 3;
  public static final int ENTER_GOTO     = 4;
  public static final int ENTER_TRANS    = 5;
  public static final int ENTER_UNPOS    = 6;
  public static final int ENTER_NEWCLASS = 7;
  public static final int ENTER_TELEPORT = 8;
  public static final int ENTER_LOAD     = 9;
  public static final int ENTER_POP      = 10;

  public static final int LEAVE_QUIT     = 0;
  public static final int LEAVE_MOVE     = 1;
  public static final int LEAVE_FOLLOW   = 2;
  public static final int LEAVE_DIE      = 3;
  public static final int LEAVE_GOTO     = 4;
  public static final int LEAVE_TRANS    = 5;
  public static final int LEAVE_POSSESS  = 6;
  public static final int LEAVE_NEWCLASS = 7;
  public static final int LEAVE_TELEPORT = 8;
  public static final int LEAVE_PURGE    = 9;

  public static final int ENTER_DROP     = 0;
  public static final int ENTER_DECAY    = 1;
  public static final int ENTER_IPOP     = 2;
  public static final int ENTER_DISARM   = 3;
  public static final int ENTER_DIE      = 4;

  public static final int LEAVE_DESTROY  = 0;
  public static final int LEAVE_GET      = 1;

  public static final int PURGE_NORMAL       = 0;
  public static final int PURGE_FREEZE       = 1;
  public static final int PURGE_DISINTEGRATE = 2;
  public static final int PURGE_ACCOUNT      = 3;

  public static final int INV_START_SIZE = 20;

  public static final String BLACK    = "\33[0;30m";
  public static final String RED      = "\33[0;31m";
  public static final String GREEN    = "\33[0;32m";
  public static final String BROWN    = "\33[0;33m";
  public static final String BLUE     = "\33[0;34m";
  public static final String PURPLE   = "\33[0;35m";
  public static final String CYAN     = "\33[0;36m";
  public static final String GRAY     = "\33[0;37m";
  public static final String L_BLUE   = "\33[1;34m";
  public static final String L_GREEN  = "\33[1;32m";
  public static final String L_CYAN   = "\33[1;36m";
  public static final String L_RED    = "\33[1;31m";
  public static final String L_PURPLE = "\33[1;35m";
  public static final String YELLOW   = "\33[1;33m";
  public static final String WHITE    = "\33[1;37m";

  public static final Random random = new Random();

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int getRandomInt(int min, int max)

  {
    max++;
    if (min == max) return min;
    return min + random.nextInt(max - min);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static double getRandomDouble(double min, double max)

  {
    double range = max - min;
    return (min + (random.nextDouble() * range));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int bound(int val, int min, int max)

  {
    if (val < min) val = min;
    if (val > max) val = max;
    return val;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int getExpPercent(int numGroupMembers)

  {
    if (numGroupMembers <= 0) return 0;
    else if (numGroupMembers == 1) return 100;
    else if (numGroupMembers == 2) return 75;
    else if (numGroupMembers == 3) return 60;
    else if (numGroupMembers == 4) return 50;
    else if (numGroupMembers == 5) return 40;
    else if (numGroupMembers == 6) return 35;
    else if (numGroupMembers == 7) return 30;
    else if (numGroupMembers == 8) return 27;
    else if (numGroupMembers == 9) return 25;
    else return 23;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int getExpDeficit(int levelDeficit)

  {
    if (levelDeficit <= 0) return 100;
    else if (levelDeficit == 1) return 95;
    else if (levelDeficit == 2) return 90;
    else if (levelDeficit == 3) return 85;
    else if (levelDeficit == 4) return 80;
    else if (levelDeficit == 5) return 75;
    else if (levelDeficit == 6) return 70;
    else if (levelDeficit == 7) return 65;
    else if (levelDeficit == 8) return 60;
    else if (levelDeficit == 9) return 55;
    else if (levelDeficit == 10) return 50;
    else if (levelDeficit == 11) return 40;
    else if (levelDeficit == 12) return 30;
    else if (levelDeficit == 13) return 20;
    else if (levelDeficit == 14) return 10;
    else if (levelDeficit == 15) return 5;
    else if (levelDeficit == 16) return 4;
    else if (levelDeficit == 17) return 3;
    else if (levelDeficit == 18) return 2;
    else if (levelDeficit == 19) return 1;
    else return 0;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int getExpNeeded(int lev)

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

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String colorToName(String s)

  {
    if (s.equals("#r")) return "Red";
    else if (s.equals("#R")) return "Dark red";
    else if (s.equals("#g")) return "Green";
    else if (s.equals("#G")) return "Dark green";
    else if (s.equals("#b")) return "Blue";
    else if (s.equals("#B")) return "Dark blue";
    else if (s.equals("#y")) return "Yellow";
    else if (s.equals("#Y")) return "Dark yellow";
    else if (s.equals("#c")) return "Cyan";
    else if (s.equals("#C")) return "Dark cyan";
    else if (s.equals("#m")) return "Magenta";
    else if (s.equals("#M")) return "Purple";
    else if (s.equals("#N")) return "Grey";
    else if (s.equals("#n")) return "White";

    return "Invalid_Color";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Exception ex(String s)

  {
    return new Exception(s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean bool(int n)

  {
    if (n > 0) return true;
    else return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int getInt(ArrayList A, int n)

  {
    return ((Integer)A.get(n)).intValue();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static double getDouble(ArrayList A, int n)

  {
    return ((Double)A.get(n)).doubleValue();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String getString(ArrayList A, int n)

  {
    return ((String)A.get(n));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String xchars(int num, String s)

  {
    String str = "";
    for (int i=0; i<num; i++) str += s;
    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String colorize(String s)

  {
    s = s.replaceAll("#R", RED);
    s = s.replaceAll("#r", L_RED);
    s = s.replaceAll("#B", BLUE);
    s = s.replaceAll("#b", L_BLUE);
    s = s.replaceAll("#G", GREEN);
    s = s.replaceAll("#g", L_GREEN);
    s = s.replaceAll("#Y", BROWN);
    s = s.replaceAll("#y", YELLOW);
    s = s.replaceAll("#M", PURPLE);
    s = s.replaceAll("#m", L_PURPLE);
    s = s.replaceAll("#C", CYAN);
    s = s.replaceAll("#c", L_CYAN);
    s = s.replaceAll("#N", GRAY);
    s = s.replaceAll("#n", WHITE);
    s += GRAY;

    return s;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean match(String str, String s1, String s2)

  {
    if (str.length() <= 0) return false;
    if (str.charAt(0) != s1.charAt(0)) return false;
    if (str.length() < s1.length()) return false;

    String temp = new String(s1);

    for (int i=s1.length(); i<=s2.length(); i++)

    {
      if (str.equalsIgnoreCase(temp))
        return true;

      if (i != s2.length())
        temp += s2.charAt(i);
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String first(String s)

  {
    String temp;
    int i = s.indexOf(" ");

    if (i == -1)
      return s;

    temp = s.substring(0, i);
    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String last(String s)

  {
    if (s.equals("")) return "";
    if (s.indexOf(" ") == -1) return "";

    int i = s.indexOf(" ");
    if (i < 0) return "";

    return s.substring(i+1, s.length());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean letter(char c)

  {
    if (((c >= 97) && (c <= 122))
    || ((c >= 65) && (c <= 90)))
      return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean number(char c)

  {
    if ((c >= 48) && (c <= 57))
      return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean number(String s)

  {
    int size = s.length();

    if (size <= 0) return false;

    for (int i=0; i<size; i++)
      if (!number(s.charAt(i)))
        return false;

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean nonzeroNumber(char c)

  {
    if ((c >= 49) && (c <= 57))
      return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean shortFor(String srt, String full)

  {
    int size = full.length();
    int ssize = srt.length();
    int count = 0;

    if ((ssize < 3) || (ssize > size))
      return false;

    while (count < ssize)

    {
      if (srt.charAt(count) != full.charAt(count))
        return false;

      count++;
    }

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean direction(String s)

  {
    if ((s.equals("north")) || (s.equals("nort")) || (s.equals("nor"))
     || (s.equals("no"))    || (s.equals("n"))    || (s.equals("south"))
     || (s.equals("sout"))  || (s.equals("sou"))  || (s.equals("so"))
     || (s.equals("s"))     || (s.equals("east")) || (s.equals("eas"))
     || (s.equals("ea"))    || (s.equals("e"))    || (s.equals("west"))
     || (s.equals("wes"))   || (s.equals("we"))   || (s.equals("w"))
     || (s.equals("down"))  || (s.equals("dow"))  || (s.equals("do"))
     || (s.equals("d"))     || (s.equals("up"))   || (s.equals("u"))) return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean colorCharacter(char c)

  {
    if ((c == 'R') || (c == 'r') || (c == 'B') || (c == 'b')
     || (c == 'G') || (c == 'g') || (c == 'Y') || (c == 'y')
     || (c == 'M') || (c == 'm') || (c == 'C') || (c == 'c')
     || (c == 'N') || (c == 'n')) return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String getNumberString(String s)

  {
    int size = s.length();
    int position = s.indexOf(".");

    if (position == -1) return s;
    if (position == 0)  return s;
    if (size < 3)       return s;

    String numberString = "";

    for (int i=0; i<position; i++)
      if (number(s.charAt(i)))
        numberString += s.charAt(i);
      else return s;

    return numberString;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int clipNumber(String s)

  {
    String numberString = getNumberString(s);

    if (s == numberString) return 1;

    return Integer.parseInt(numberString);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String clipString(String s)

  {
    String numberString = getNumberString(s);

    if (s == numberString) return s;

    return s.substring(numberString.length()+1, s.length());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int visibleSize(String s)

  {
    int size = s.length();
    int vsize = s.length();

    for (int i=size-1; i>0; i--)
      if ((s.charAt(i-1) == '#') && (colorCharacter(s.charAt(i))))
        vsize = vsize - 2;

    return vsize;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String variableAlign(String str, int vline_length)

  {
    StringBuffer s = new StringBuffer(str);
    int index = vline_length;
    int cut, drop, count;
    int last = 1;

    if (s.length() > vline_length)

    {
      while (index < s.length())

      {
        for (int i=last; i<index; i++)
        if (i < s.length())
        if (colorCharacter(s.charAt(i)))
        if (s.charAt(i-1) == '#')
          index = index+2;

        if (index < s.length()) {

        cut = index-1;
        drop = index;

        if ((s.charAt(cut) != ' ') && (s.charAt(drop) == ' '))

        {
          s = s.insert(index, "\r\n");
          s = s.deleteCharAt(drop+2);
        }

        else if ((s.charAt(cut) != ' ') && (s.charAt(drop) != ' '))

        {
          count = 0;
          while ((s.charAt(cut) != ' ')  && (s.charAt(cut) != '\n')) { count++; cut--; }
          while ((drop < s.length()) && (s.charAt(drop) != ' '))  { count++; drop++; }
          cut = index-1;

          if (count <= vline_length)

          {
            while (s.charAt(cut) != ' ')
              cut--;

            for (int i=1; i<=(index-1-cut); i++)
              s = s.insert(cut, " ");

            s = s.insert(index, "\r\n");
          }

          else s = s.insert(index, "\r\n");
        }

        else s = s.insert(index, "\r\n");

        last = index;
        index = index + vline_length+2; }
      }
    }

    return s.toString().replaceAll(" \r\n", "\r\n");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String align(String str)

  {
    return variableAlign(str, LINE_LENGTH);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String roomAlign(String str)

  {
    return variableAlign(str, RLINE_LENGTH);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String clearWhiteSpace(String s)

  {
    while (s.indexOf("  ") != -1)
      s = s.replaceAll("  ", " ");

    return s.trim();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String whiteSpace(int n)

  {
    String s = "";
    for (int i=0; i<n; i++) s += " ";
    return s;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String tenSpace(int num, int places)

  {
    int mult = 1;
    String str = "";

    for (int i=0; i<places; i++) {
      mult = mult * 10;
      if (num < mult) str += " "; }

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String getLastWord(String s)

  {
    s = s.trim();

    while (!last(s).equals("")) s = last(s);

    return s;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String clipLastWord(String s)

  {
    String lastWord = getLastWord(s);
    String newString = s.substring(0, s.length()-lastWord.length());

    return newString.trim();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean legalDesc(String s)

  {
    int size = s.length();
    int count = 0;

    for (int i=0; i<size; i++) {
      if (count >= 60) return false;
      if (s.charAt(i) == ' ') count = 0;
      else count++; }

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String expCommas(String s)

  {
    StringBuffer str = new StringBuffer(s);
    int size = str.length();

    if ((size >= 7) && (size <= 9)) {
      str.insert(size-6, ",");
      str.insert(size-2, ","); }

    else if ((size >= 4) && (size <= 6))
      str.insert(size-3, ",");

    return str.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String removeDashes(String s)

  {
    StringBuffer str = new StringBuffer(s);

    int size = str.length();

    for (int i=1; i<size; i++)
      if (str.charAt(i) == '-')
        str.setCharAt(i, ' ');

    return str.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String fullDay(String str)

  {
    if (str.equals("Sun")) return "Sunday";
    else if (str.equals("Mon")) return "Monday";
    else if (str.equals("Tue")) return "Tuesday";
    else if (str.equals("Wed")) return "Wednesday";
    else if (str.equals("Thu")) return "Thursday";
    else if (str.equals("Fri")) return "Friday";
    else if (str.equals("Sat")) return "Saturday";
    else return "X";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String fullDate(String str)

  {
    String temp = "", fdate;

    fdate = "";
    temp += str.charAt(0);
    temp += str.charAt(1);

    if (temp.equals("01")) fdate = "January ";
    else if (temp.equals("02")) fdate = "February ";
    else if (temp.equals("03")) fdate = "March ";
    else if (temp.equals("04")) fdate = "April ";
    else if (temp.equals("05")) fdate = "May ";
    else if (temp.equals("06")) fdate = "June ";
    else if (temp.equals("07")) fdate = "July ";
    else if (temp.equals("08")) fdate = "August ";
    else if (temp.equals("09")) fdate = "September ";
    else if (temp.equals("10")) fdate = "October ";
    else if (temp.equals("11")) fdate = "November ";
    else if (temp.equals("12")) fdate = "December ";

    temp = "";
    temp += str.charAt(3);
    temp += str.charAt(4);

    if (temp.equals("01")) fdate += "1st, 20";
    else if (temp.equals("02")) fdate += "2nd, 20";
    else if (temp.equals("03")) fdate += "3rd, 20";
    else if (temp.equals("04")) fdate += "4th, 20";
    else if (temp.equals("05")) fdate += "5th, 20";
    else if (temp.equals("06")) fdate += "6th, 20";
    else if (temp.equals("07")) fdate += "7th, 20";
    else if (temp.equals("08")) fdate += "8th, 20";
    else if (temp.equals("09")) fdate += "9th, 20";
    else if (temp.equals("10")) fdate += "10th, 20";
    else if (temp.equals("11")) fdate += "11th, 20";
    else if (temp.equals("12")) fdate += "12th, 20";
    else if (temp.equals("13")) fdate += "13th, 20";
    else if (temp.equals("14")) fdate += "14th, 20";
    else if (temp.equals("15")) fdate += "15th, 20";
    else if (temp.equals("16")) fdate += "16th, 20";
    else if (temp.equals("17")) fdate += "17th, 20";
    else if (temp.equals("18")) fdate += "18th, 20";
    else if (temp.equals("19")) fdate += "19th, 20";
    else if (temp.equals("20")) fdate += "20th, 20";
    else if (temp.equals("21")) fdate += "21st, 20";
    else if (temp.equals("22")) fdate += "22nd, 20";
    else if (temp.equals("23")) fdate += "23rd, 20";
    else if (temp.equals("24")) fdate += "24th, 20";
    else if (temp.equals("25")) fdate += "25th, 20";
    else if (temp.equals("26")) fdate += "26th, 20";
    else if (temp.equals("27")) fdate += "27th, 20";
    else if (temp.equals("28")) fdate += "28th, 20";
    else if (temp.equals("29")) fdate += "29th, 20";
    else if (temp.equals("30")) fdate += "30th, 20";
    else if (temp.equals("31")) fdate += "31st, 20";

    temp = "";
    temp += str.charAt(6);
    temp += str.charAt(7);

    fdate += temp;

    return fdate;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String fullTime(String str)

  {
    String ftime = "", ampm = "AM", tempstr = "";
    int temp;

    if (str.charAt(0) != '0')
      ftime += str.charAt(0);

    ftime += str.charAt(1);
    temp = Integer.parseInt(ftime);

    if (temp > 12) {
      temp = temp - 12;
      ampm = "PM"; }

    ftime  = Integer.toString(temp);
    tempstr += str.charAt(3);
    tempstr += str.charAt(4);

    ftime += ":" + tempstr + " " + ampm;

    return ftime;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean blank(String s)

  {
    StringBuffer str = new StringBuffer(s);
    int x;

    if (s.equals("")) return true;

    x = str.indexOf("\r", 0);

    while (x != -1) {
      str.deleteCharAt(x);
      x = str.indexOf("\r", 0); }

    x = str.indexOf("\n", 0);

    while (x != -1) {
      str.deleteCharAt(x);
      x = str.indexOf("\n", 0); }

    x = str.indexOf(" ", 0);

    while (x != -1) {
      str.deleteCharAt(x);
      x = str.indexOf(" ", 0); }

    if (visibleSize(str.toString()) <= 0)
      return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String formatPlan(String str)

  {
    int count = 1;
    int begin = 0, end = 0, last = str.length()-1;
    String temp = "", xs;

    if (str.equals("")) return "";

    while ((end <= last) && (count <= 15))

    {
      while (str.charAt(end) != '\n')
        end++;

      xs = "";
      for (int i=1; i<=57-visibleSize(str.substring(begin, end-begin)); i++)
        xs += " ";

      temp += "  #n|#N " + str.substring(begin, end-begin) + xs + " #n|#N\r\n";

      begin = end+1;
      end = end+1;

      count++;
    }

    temp = temp.substring(0, temp.length()-2);

    if (blank(str)) return "";

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String formatDesc(String s)

  {
    int x;

    if (s.equals("")) return "DEFAULT";
    if (blank(s)) return "DEFAULT";

    s = s.replaceAll("\r\n", " ");
    s = clearWhiteSpace(s);
    s = roomAlign(s);

    if (blank(s)) return "DEFAULT";

    return s;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String key(String s)

  {
    return first(s).toLowerCase();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String classPlural(String str)

  {
    if (str.equals("Thief")) return "Thieves";
    if (str.equals("Mercenary")) return "Mercenaries";

    return (str + "s");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String stringStacker(String str)

  {
    ArrayList stringStack = new ArrayList();
    int size = str.length();
    String temp = "";
    boolean more = false;

    for (int i=0; i<size; i++)

    {
      if (str.charAt(i) == '\n') {
        stringStack.add(temp);
        temp = "";
        more = false; }

      else if (str.charAt(i) != '\r') {
        temp += str.charAt(i);
        more = true; }
    }

    if (more) stringStack.add(temp);

    temp = "";
    size = stringStack.size();

    for (int i=0; i<size; i++)

    {
     boolean seen_before = false;

      for (int j=0; j<i; j++)
        if ((String)stringStack.get(j) == (String)stringStack.get(i))
          seen_before = true;

      if (!seen_before)

      {
        int count = countStack(stringStack, (String)stringStack.get(i));

        if (count == 1)
          temp += (String)stringStack.get(i) + "\r\n";

        else if (count > 1)
          temp += (String)stringStack.get(i) + " (" + count + ")\r\n";
      }
    }

    if (size > 0) temp = temp.substring(0, temp.length()-2);

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int countStack(ArrayList stack, String str)

  {
    int count = 0;
    int size = stack.size();

    for (int i=0; i<size; i++)
      if ((String)stack.get(i) == str)
        count++;

    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String formatIdentify(String str)

  {
    ArrayList stringStack = new ArrayList();
    boolean more = false;
    int maxsize = 0;
    String temp = "";

    for (int i=0; i<str.length(); i++)

    {
      if (str.charAt(i) == '\n') {
        stringStack.add(temp);
        temp = "";
        more = false; }

      else if (str.charAt(i) != '\r') {
        temp += str.charAt(i);
        more = true; }
    }

    if (more) stringStack.add(temp);

    for (int i=0; i<stringStack.size(); i++)
      if (visibleSize((String)stringStack.get(i)) > maxsize)
        maxsize = visibleSize((String)stringStack.get(i));

    String border = "#n+-";  for (int i=1; i<=maxsize; i++) border += "-";
    border += "-+#N";
    temp = border + "\r\n";

    for (int i=0; i<stringStack.size(); i++)

    {
      String xtra = "";
      int tsize = maxsize - visibleSize((String)stringStack.get(i));
      for (int j=1; j<=tsize; j++) xtra += " ";

      if (((String)stringStack.get(i)).indexOf("Flags:") != -1)
        temp += border + "\r\n";

      temp += "#n| #N" + (String)stringStack.get(i) + xtra + " #n|#N\r\n";
    }

    temp += border;

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String rainbowString(String s, String valid)

  {
    int count = 0;
    int position = 0;
    StringBuffer str = new StringBuffer(s);

    boolean allok = false;
    if ((valid.equals("all")) || (valid.equals("All"))) allok = true;

    while (position < str.length())
    if ((allok) || (valid.indexOf(str.substring(position, position+1)) != -1))

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

    return str.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean speedwalkLegal(String key)

  {
    if (key.equalsIgnoreCase("use")) return false;

    int size = key.length();
    int count = 0;

    for (int i=0; i<size; i++)

    {
      if (nonzeroNumber(key.charAt(i)))

      {
        if (i == (size-1)) return false;
        if (!direction(key.substring(i+1, i+2))) return false;
      }

      else if (!direction(key.substring(i, i+1))) return false;
    }

    for (int j=0; j<size; j++)

    {
      if (count > SPEEDWALK_LIMITER) return false;

       if (nonzeroNumber(key.charAt(j)))

       {
         count = count + key.charAt(j) - 48;
         j++;
       }

      else count++;
    }

    if (count > SPEEDWALK_LIMITER) return false;

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean determineGoldString(String str)

  {
    String temp1 = key(str);
    String temp2 = key(last(str));

    if (number(temp1))
      if (temp2.toLowerCase().indexOf("gold") != -1)
        return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String removeColors(String s)

  {
    StringBuffer str = new StringBuffer(s);
    int position = 0;

    while (visibleSize(str.toString()) < str.length())

    {
      position = str.indexOf("#");
      str.delete(position, position+2);
    }

    return str.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String properName(String s)

  {
    if (s.length() <= 0) return s;

    StringBuffer str = new StringBuffer(s.toLowerCase());

    if ((str.charAt(0) >= 97) && (str.charAt(0) <= 122))
      str.setCharAt(0, (char)(str.charAt(0)-32));

    return str.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int getWidth(String s)

  {
    int count = 0;
    int maxcount = 0;
    StringBuffer str = new StringBuffer(s);

    while (str.length() > 0)

    {
      if ((str.charAt(0) != '\r') && (str.charAt(0) != '\n')) {
        count++;
        if (count > maxcount) maxcount = count; }

      else count = 0;

      str.deleteCharAt(0);
    }

    return maxcount;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String getLine(String str)

  {
    int count = 0;
    String temp = "";

    while ((str.length() > count)
    && (str.charAt(count) != '\n')
    && (str.charAt(count) != '\r')) {
      temp += str.charAt(count);
      count++; }

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String removeLine(String str)

  {
    int count = 0;
    String temp = "";
    StringBuffer s = new StringBuffer(str);

    while ((s.length() > count)
    && (s.charAt(count) != '\n')
    && (s.charAt(count) != '\r')) {
      temp += s.charAt(count);
      count++; }

    s = s.delete(0, temp.length());

    while ((s.length() > 0) && ((s.charAt(0) == '\r') || (s.charAt(0) == '\n')))
      s = s.delete(0, 1);

    return s.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String removeBsr(String str)

  {
    return str.replaceAll("\r", "");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String removeBsn(String str)

  {
    return str.replaceAll("\n", "");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int nCount(String str)

  {
    int count = 0;

    for (int i=0; i<str.length(); i++)
      if (str.charAt(i) == '\n') count++;

    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String formatAnsiOutput(String s)

  {
    StringBuffer temp = new StringBuffer(s);
    boolean reached_begin = false;
    boolean reached_end   = false;

    for (int i=0; i<temp.length(); i++) {
      if (reached_begin) break;
      if (((temp.charAt(i) == '#') && (colorCharacter(temp.charAt(i+1)))) && (i <= temp.length()-2)) i++;
      else if ((temp.charAt(i) == '\r') || (temp.charAt(i) == '\n')) { temp.deleteCharAt(i);  i--; }
      else reached_begin = true; }

    for (int i=temp.length()-1; i>=0; i--) {
      if (reached_end) break;
      if (((temp.charAt(i-1) == '#') && (colorCharacter(temp.charAt(i)))) && (i >= 1)) i--;
      else if ((temp.charAt(i) == '\r') || (temp.charAt(i) == '\n')) temp.deleteCharAt(i);
      else reached_end = true; }

    return "\r\n\n" + temp.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean abbreviation(String a, String s)

  {
    a = removeColors(a.toLowerCase());
    s = removeColors(s.toLowerCase());

    if (a.equals(s)) return true;

    if (a.length() < 3) return false;
    if (s.length() < 3) return false;
    if (s.indexOf(a) == -1) return false;

    int pos = s.indexOf(a);

    if (pos == 0) return true;

    if (s.charAt(pos-1) == ' ') return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean abbreviation(String a, String s, int precision)

  {
    a = removeColors(a.toLowerCase());
    s = removeColors(s.toLowerCase());

    if (a.equals(s)) return true;

    if (a.length() < precision) return false;
    if (s.length() < precision) return false;
    if (s.indexOf(a) == -1) return false;

    int pos = s.indexOf(a);

    if (pos == 0) return true;

    if (s.charAt(pos-1) == ' ') return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String capitalLname(String lname)

  {
    StringBuffer temp = new StringBuffer(lname);

    if (temp.length() < 1) return temp.toString();

    if ((temp.charAt(0) >= 97) && (temp.charAt(0) <= 122))
      temp.setCharAt(0, (char)(temp.charAt(0) - 32));

    return temp.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String lastCommand(String s)

  {
    if (s.equals("")) return s;

    StringBuffer str = new StringBuffer(s);

    while (str.indexOf(";;") != -1)

    {
      int pos = str.indexOf(";;");
      str.delete(0,pos+2);
    }

    return str.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String getBooleanColor(boolean b)

  {
    if (b) return "#g";
    else return "#R";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String possessive(String str)

  {
    int size = str.length();

    if ((str.charAt(size-1) == 's') || (str.charAt(size-1) == 'S'))
      return (str + "'");

    else return (str + "'s");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String onOff(boolean b)

  {
    if (b) return "On ";
    return "Off";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean searchArrayList(ArrayList A, String s)

  {
    for (int i=0; i<A.size(); i++)

    {
      String str = (String) A.get(i);
      if (str.equals(s)) return true;
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean searchArrayList(ArrayList A, int n)

  {
    int size = A.size();

    for (int i=0; i<size; i++)
      if (getInt(A,i) == n) return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int indexOf(ArrayList A, int n)

  {
    int size = A.size();

    for (int i=0; i<size; i++)
      if (getInt(A,i) == n) return i;

    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean punctuation(char c)

  {
    if (c == '.') return true;
    if (c == '!') return true;
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean findKeyword(String str, String keywords)

  {
    while (keywords.length() > 0)

    {
      String s = first(keywords);
      keywords = last(keywords);
      if (s.equalsIgnoreCase(str)) return true;
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int countWords(String str)

  {
    int count = 0;

    while (str.length() > 0) {
      str = last(str);
      count++; }

    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList copyIntList(ArrayList A)

  {
    ArrayList C = new ArrayList();

    for (int i=0; i<A.size(); i++)
      C.add(new Integer(getInt(A,i)));

    return C;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList copyDoubleList(ArrayList A)

  {
    ArrayList C = new ArrayList();

    for (int i=0; i<A.size(); i++)
      C.add(new Double(((Double)A.get(i)).doubleValue()));

    return C;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList copyStringList(ArrayList A)

  {
    ArrayList C = new ArrayList();

    for (int i=0; i<A.size(); i++)

    {
      String s = (String) A.get(i);
      C.add(new String(s));
    }

    return C;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean isGoldString(String str)

  {
    String temp1 = key(str);
    String temp2 = key(last(str));

    if (number(temp1))
      if (temp2.indexOf("gold") != -1)
        return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String getProperClassName(String str)

  {
    String temp = "";

    while (str.length() > 0)

    {
      temp += properName(first(str));
      str = last(str);
    }

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String makePlain(String str)

  {
    return removeColors(str.toLowerCase());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String removePunctuation(String str)

  {
    if (str.indexOf(".") != -1) str = clearWhiteSpace(str.replace('.',' '));
    if (str.indexOf(",") != -1) str = clearWhiteSpace(str.replace(',',' '));
    if (str.indexOf("!") != -1) str = clearWhiteSpace(str.replace('!',' '));
    if (str.indexOf("?") != -1) str = clearWhiteSpace(str.replace('?',' '));
    if (str.indexOf("'") != -1) str = clearWhiteSpace(str.replace((char)39,' '));

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList getAllWords(String str)

  {
    ArrayList words = new ArrayList();
    boolean adding = false;
    String currentWord = "";
    String s = str.toLowerCase();

    while (s.length() > 0)

    {
      if (letter(s.charAt(0))) {
        currentWord += s.charAt(0);
        adding = true; }

      else if (adding) {
        words.add(currentWord);
        currentWord = "";
        adding = false; }

      s = s.substring(1, s.length());
    }

    if (currentWord.length() > 0) words.add(currentWord);

    return words;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String properPlaceName(String str)

  {
    StringBuffer s = new StringBuffer(properName(str));

    for (int i=0; i<s.length(); i++)
    if ((s.charAt(i) == '_') && (i < s.length()-1))
      s.setCharAt(i+1, Character.toUpperCase(s.charAt(i+1)));

    return s.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean sameItem(Item I1, Item I2)

  {
    if (I1.getID() != I2.getID()) return false;
    if (!I1.getName().equals(I2.getName())) return false;
    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int containsItem(ArrayList A, int n)

  {
    for (int i=0; i<A.size(); i++)
    if (sameItem(World.getItem(n), (Item)A.get(i)))
      return i;

    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String boxify(ArrayList A, int indent)

  {
    if ((A == null) || (A.size() == 0)) return "";

    int width = 0;

    for (int i=0; i<A.size(); i++)
    if (visibleSize((String)A.get(i)) > width)
      width = visibleSize((String)A.get(i));

    String border = xchars(indent," ") + "#n+-" + xchars(width,"-") + "-+#N";
    String temp = border + "\r\n";
    boolean center = false;

    for (int i=0; i<A.size(); i++)

    {
      String menuItem = (String) A.get(i);

      if (menuItem.equals("-")) temp += border + "\r\n";

      else if (menuItem.equalsIgnoreCase("center")) center = true;

      else

      {
        if (!center)

        {
          temp += xchars(indent," ") + "#n|#N " + menuItem;
          temp += xchars(width-visibleSize(menuItem)," ") + " #n|#N\r\n";
        }

        else if (center)

        {
          int marginSpace = width - visibleSize(menuItem);
          int leftMargin = marginSpace / 2;
          int rightMargin = marginSpace / 2;
          if ((marginSpace%2)!=0) rightMargin++;

          temp += xchars(indent," ") + "#n|#N " + xchars(leftMargin," ");
          temp += menuItem + xchars(rightMargin," ") + " #n|#N\r\n";
        }

        center = false;
      }
    }

    temp += border;

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String condColor(String color, boolean cond)

  {
    if (cond) return color;
    return "#N";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean validIPString(String s)

  {
    s = s.replace('.', ' ');

    String[] components = new String[4];

    components[0] = first(s); s = last(s);
    components[1] = first(s); s = last(s);
    components[2] = first(s); s = last(s);
    components[3] = first(s); s = last(s);

    if (s.length() != 0) return false;

    for (int i=0; i<components.length; i++)

    {
      if (!number(components[i])) return false;
      int componentValue = Integer.parseInt(components[i]);
      if (componentValue < 0) return false;
      if (componentValue > 255) return false;
    }

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean lessThan(String s1, String s2)

  {
    int i = s1.compareToIgnoreCase(s2);
    if (i < 0) return true;
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void sortAlphabetical(ArrayList A)

  {
    for (int b=0; b<A.size()-1; b++)
    for (int i=0; i<A.size()-1; i++)
    if (lessThan(getString(A,i+1), getString(A,i)))

    {
      String s1 = (String) A.get(i);
      String s2 = (String) A.get(i+1);

      A.set(i,s2);
      A.set(i+1,s1);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String centerAlign(int width, String s)

  {
    String str = "";
    int j = width - visibleSize(s);
    if (j%2 == 0) for (int i=0; i<(j/2); i++) str += " ";
    else for (int i=0; i<=(j/2); i++) str += " ";
    str += s;
    for (int i=0; i<(j/2); i++) str += " ";
    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int oppositeDir(int dir)

  {
    if (dir == DIRECTION_N) return DIRECTION_S;
    if (dir == DIRECTION_S) return DIRECTION_N;
    if (dir == DIRECTION_E) return DIRECTION_W;
    if (dir == DIRECTION_W) return DIRECTION_E;
    if (dir == DIRECTION_U) return DIRECTION_D;
    if (dir == DIRECTION_D) return DIRECTION_U;

    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}