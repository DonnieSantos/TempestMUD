import java.io.*;
import java.util.*;

abstract class Ansi extends Utility

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String clearScreen = "\33[1;1H\33[0J";
  public static String resetOrigin = "\33[?6l";
  public static String wrapAround = "\33[?7l";
  public static String clearLine = "\33[0J";
  public static String maxMargin = resetOrigin + "\33[1;" + (MAX_SCREEN_SIZE) + "r";

  public static String home;
  public static String above;
  public static String corner;
  public static String lowMargin;
  public static String highMargin;
  public static String preData;
  public static String postData;

  public static int hp;
  public static int mn;
  public static int mv;
  public static String bar;
  public static String lifebar;
  public static String manabar;
  public static String movebar;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static void setMessageMargins(int screenSize)

  {
    home        =  "\33[" + screenSize + ";1H";
    above       =  "\33[" + (screenSize-5) + ";1H";
    highMargin  =  "\33[1;" + (screenSize-5) + "r";

    preData = wrapAround + resetOrigin + highMargin + above;
    postData = home + clearLine;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static void setBarMargins(int screenSize)

  {
    home        =  "\33[" + screenSize + ";1H";
    corner      =  "\33[" + (screenSize-3) + ";1H";
    lowMargin   =  "\33[1;" + (screenSize+1) + "r";
    highMargin  =  "\33[1;" + (screenSize-4) + "r";

    preData = wrapAround + resetOrigin + lowMargin + corner;
    postData = highMargin + home + clearLine;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getAnsiBar(Entity E, int screenSize)

  {
    int hp1, hp2, mn1 = 0, mn2, mv1 = 0, mv2;

    hp = (int)(((float)E.getCurrentHP() / (float)E.getMaxHP()) * (float)10);
    mn = (int)(((float)E.getCurrentMN() / (float)E.getMaxMN()) * (float)10);
    mv = (int)(((float)E.getCurrentMV() / (float)E.getMaxMV()) * (float)10);

    hp = bound(hp, 0, 10);
    mn = bound(mn, 0, 10);
    mv = bound(mv, 0, 10);

    lifebar = "#n[#g" + xchars(hp,"=") + "#N" + xchars(10-hp,"-") + "#n] #N";
    manabar = "#n[#m" + xchars(mn,"=") + "#N" + xchars(10-mn,"-") + "#n] #N";
    movebar = "#n[#b" + xchars(mv,"=") + "#N" + xchars(10-mv,"-") + "#n] #N";

    bar  = "#N+----------------------------------------+--------------------------------+#N\r\n";
    bar += "#N| " + lifebar + manabar + movebar + "#N|                                #N|#N\r\n";
    bar += "#N+----------------------------------------+--------------------------------+#N";

    setBarMargins(screenSize);

    return preData + bar + postData;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String drawAboveBar(String s, int screenSize)

  {
    setMessageMargins(screenSize);
    return preData + s + postData;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}