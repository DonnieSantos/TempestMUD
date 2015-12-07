public class Utility

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

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

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static String first(String s)

  {
    String temp;
    int i = s.indexOf(" ");

    if (i == -1)
      return s;

    temp = s.substring(0, i);
    return temp;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static String last(String s)

  {
    if (s.equals("")) return "";
    if (s.indexOf(" ") == -1) return "";

    int i = s.indexOf(" ");
    if (i < 0) return "";

    return s.substring(i+1, s.length());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static String[] createMatchings(String abbr, String full)

  {
    int numMatchings = full.length() - abbr.length() + 1;
    String[] matchings = new String[numMatchings];

    for (int i=0; i<numMatchings; i++)
      matchings[i] = full.substring(0, abbr.length() + i);

    return matchings;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

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

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static String properName(String s)

  {
    if (s.length() <= 0) return s;

    StringBuffer str = new StringBuffer(s.toLowerCase());

    if ((str.charAt(0) >= 97) && (str.charAt(0) <= 122))
      str.setCharAt(0, (char)(str.charAt(0)-32));

    return str.toString();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}