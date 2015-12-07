import java.util.*;
import java.io.*;

class Socials extends Utility

{
  private static String he;
  private static String He;
  private static String his;
  private static String His;
  private static String hsf;
  private static String Hsf;
  private static String name;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean sleepFilter(Entity SRC)

  {
    if (SRC.sleepCheck()) return false;

    he   = SRC.he();
    He   = SRC.He();
    his  = SRC.his();
    His  = SRC.His();
    hsf  = SRC.himself();
    Hsf  = SRC.Himself();
    name = SRC.getName();

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void blink(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You blink twice to clear your eyes.");
      SRC.blindEmote("blinks innocently.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You lower your head and blink to yourself.");
      SRC.blindEmote("lowers " + his + " head and blinks to " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You look at " + ENT.getName() + " and blink in wonder.");
      ENT.awakeEcho(name + " looks at you and blinks in wonder.");
      SRC.xBlindEmote(ENT, "looks at " + ENT.getName() + " and blinks in wonder.");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}