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

  public static void nod(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You nod solemnly.");
      SRC.blindEmote("nods solemnly.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You nod thoughtfully to yourself.");
      SRC.blindEmote("nods " + his + " head thoughtfully " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You nod in recognition to " + ENT.getName() + ".");
      ENT.awakeEcho(name + " nods in recognition to you.");
      SRC.xBlindEmote(ENT, "nods in recognition to " + ENT.getName() + ".");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void laugh(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You hold your stomach with laughter.");
      SRC.blindEmote("laughs.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You laugh at your own foolish behavior.");
      SRC.blindEmote("laughs at " + his + " own foolish behavior.");
      return; }

    if (ENT != null) {
      SRC.echo("You laugh loudly at " + ENT.getName() + ".");
      ENT.awakeEcho(name + " laughs loudly at you.");
      SRC.xBlindEmote(ENT, "laughs loudly at " + ENT.getName() + ".");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void shrug(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You shrug helplessly.");
      SRC.blindEmote("shrugs helplessly.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You shrug to yourself.");
      SRC.blindEmote("shrugs to " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You look at " + ENT.getName() + " and shrug.");
      ENT.awakeEcho(name + " looks at you and shrugs.");
      SRC.xBlindEmote(ENT, "looks at " + ENT.getName() + " and shrugs.");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void sigh(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You sigh sadly.");
      SRC.blindEmote("sighs.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You sigh sadly to yourself.");
      SRC.blindEmote("sighs sadly to " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You look at " + ENT.getName() + " and sigh.");
      ENT.awakeEcho(name + " looks at you and sighs.");
      SRC.xBlindEmote(ENT, "looks at " + ENT.getName() + " and sighs.");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void smile(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You smile brightly.");
      SRC.blindEmote("smiles brightly.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You smile happily to yourself.");
      SRC.blindEmote("smiles happily to " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You smile brightly at " + ENT.getName() + ".");
      ENT.awakeEcho(name + " smiles brightly at you.");
      SRC.xBlindEmote(ENT, "smiles brightly at " + ENT.getName() + ".");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void grin(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You grin deviously.");
      SRC.blindEmote("grins deviously.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You grin deviously to yourself.");
      SRC.blindEmote("grins deviously to " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You grin deviously at " + ENT.getName() + ".");
      ENT.awakeEcho(name + " grins deviously at you.");
      SRC.xBlindEmote(ENT, "grins deviously at " + ENT.getName() + ".");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void shake(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You shake your head.");
      SRC.blindEmote("shakes " + his + " head.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You shake your head to yourself in disgust.");
      SRC.blindEmote("shakes " + his + " head to " + hsf + " in disgust.");
      return; }

    if (ENT != null) {
      SRC.echo("You shake " + possessive(ENT.getName()) + " hand.");
      ENT.awakeEcho(name + " shakes your hand.");
      SRC.xBlindEmote(ENT, "shakes " + possessive(ENT.getName()) + " hand.");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void giggle(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You giggle.");
      SRC.blindEmote("giggles.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You giggle softly to yourself.");
      SRC.blindEmote("giggles softly to " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You giggle merrily at " + ENT.getName() + ".");
      ENT.awakeEcho(name + " giggles merrily at you.");
      SRC.xBlindEmote(ENT, "giggles merrily at " + ENT.getName() + ".");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void frown(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You frown.");
      SRC.blindEmote("frowns.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You frown to yourself.");
      SRC.blindEmote("frowns to " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You look at " + ENT.getName() + " and frown.");
      ENT.awakeEcho(name + " looks at you and frowns.");
      SRC.xBlindEmote(ENT, "looks at " + ENT.getName() + " and frowns.");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void peer(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You peer curiously.");
      SRC.blindEmote("peers curiously.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You lower your head and peer curiously.");
      SRC.blindEmote("lowers " + his + " head and peers curiously.");
      return; }

    if (ENT != null) {
      SRC.echo("You peer curiously at " + ENT.getName() + ".");
      ENT.awakeEcho(name + " peers curiously at you.");
      SRC.xBlindEmote(ENT, "peers curiously at " + ENT.getName() + ".");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void cackle(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You cackle with a touch of insanity.");
      SRC.blindEmote("throws " + his + " head back and cackles with insane glee!");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You cackle insanely, in marvel of your own genius.");
      SRC.blindEmote("cackles insanely, in marvel of " + his + " own genius.");
      return; }

    if (ENT != null) {
      SRC.echo("You cackle insanely at " + ENT.getName() + ".");
      ENT.awakeEcho(name + " cackles insanely at you.");
      SRC.xBlindEmote(ENT, "cackles insanely at " + ENT.getName() + ".");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void growl(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("Grrrrrrrr...");
      SRC.blindEmote("growls angrily.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You growl angrily at yourself.");
      SRC.blindEmote("growls angrily at " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You growl angrily at " + ENT.getName() + ".");
      ENT.awakeEcho(name + " growls angrily at you.");
      SRC.xBlindEmote(ENT, "growls angrily at " + ENT.getName() + ".");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void snarl(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You snarl like a vicious animal.");
      SRC.blindEmote("snarls like a vicious animal.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You snarl viciously at yourself.");
      SRC.blindEmote("snarls viciously at " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You snarl viciously at " + ENT.getName() + ".");
      ENT.awakeEcho(name + " snarls viciously at you.");
      SRC.xBlindEmote(ENT, "snarls viciously at " + ENT.getName() + ".");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void tip(Entity SRC, String target)

  {
    if (!sleepFilter(SRC)) return;

    if (target.length() <= 0) {
      SRC.echo("You tip your hat gracefully.");
      SRC.blindEmote("tips " + his + " hat gracefully.");
      return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == SRC) {
      SRC.echo("You proudly tip your hat to yourself.");
      SRC.blindEmote("proudly tips " + his + " hat to " + hsf + ".");
      return; }

    if (ENT != null) {
      SRC.echo("You gracefully tip your hat to " + ENT.getName() + ".");
      ENT.awakeEcho(name + " gracefully tips " + his + " hat to you.");
      SRC.xBlindEmote(ENT, "gracefully tips " + his + " hat to " + ENT.getName() + ".");
      return; }

    SRC.echo("There's nobody here by that name.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}