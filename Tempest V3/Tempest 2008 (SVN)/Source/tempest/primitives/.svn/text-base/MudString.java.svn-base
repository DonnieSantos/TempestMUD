package tempest.primitives;
import java.lang.StringBuffer;

public class MudString

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  protected StringBuffer buffer;

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
  
  public static final String B_BLACK  = "\33[0;40m";
  public static final String B_RED    = "\33[0;41m";
  public static final String B_GREEN  = "\33[0;42m";
  public static final String B_YELLOW = "\33[0;43m";
  public static final String B_BLUE   = "\33[0;44m";
  public static final String B_PURPLE = "\33[0;45m";
  public static final String B_CYAN   = "\33[0;46m";
  public static final String B_WHITE  = "\33[0;47m";

  public static final String b_BLACK  = "\33[1;40m";
  public static final String b_RED    = "\33[1;41m";
  public static final String b_GREEN  = "\33[1;42m";
  public static final String b_YELLOW = "\33[1;43m";
  public static final String b_BLUE   = "\33[1;44m";
  public static final String b_PURPLE = "\33[1;45m";
  public static final String b_CYAN   = "\33[1;46m";
  public static final String b_WHITE  = "\33[1;47m";

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString()                  { buffer = new StringBuffer(""); }
  public MudString(int i)             { buffer = new StringBuffer(i);  }
  public MudString(String s)          { buffer = new StringBuffer(s);  }
  public MudString(StringBuffer s)    { buffer = s;                    }
  public MudString(MudString s)       { this(s.toString());            }
  public void set(String s)           { buffer = new StringBuffer(s);  }
  public void append(char c)          { buffer.append(c);              }
  public void append(String s)        { buffer.append(s);              }
  public void append(MudString s)     { buffer.append(s.toString());   }
  public void appendLine()            { buffer.append("\r\n");         }
  public void appendLine(String s)    { append(s); append("\r\n");     }
  public void appendLine(MudString s) { appendLine(s.toString());      }
  public StringBuffer getBuffer()     { return buffer;                 }
  public String toString()            { return buffer.toString();      }
  public int length()                 { return buffer.length();        }
  public int hashCode()               { return toString().hashCode();  }
  public boolean equals(MudString s)  { return s.equals(toString());   }
  public boolean equals(String s)     { return s.equals(toString());   }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void deleteCharAt(int i)

  {
    buffer.deleteCharAt(i);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public byte[] getBytes()

  {
    return buffer.toString().getBytes();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public boolean equalsIgnoreCase(MudString s)

  {
    return s.equalsIgnoreCase(toString());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public boolean equalsIgnoreCase(String s)

  {
    return s.equalsIgnoreCase(toString());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString toLowerCase()

  {
    MudString newString = new MudString();

    for (int i=0; i<buffer.length(); i++)
      newString.append(Character.toLowerCase(buffer.charAt(i)));

    return newString;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString substring(int start, int end)

  {
    return new MudString(buffer.substring(start, end));
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudChar charAt(int i)

  {
    return new MudChar(buffer.toString().charAt(i));
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString first()

  {
    int i = buffer.indexOf(" ");
    if (i == -1) return this;
    return new MudString(buffer.substring(0, i));
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString last()

  {
    if (buffer.equals("")) return this;
    int i = buffer.indexOf(" ");
    if (i < 0) return new MudString("");
    return new MudString(buffer.substring(i+1, buffer.length()));
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString firstCap()

  {
    if (buffer.length() <= 0) return this;

    // Fix this after we implement toLowerCase...

    StringBuffer str = new StringBuffer(buffer.toString().toLowerCase());

    if ((str.charAt(0) >= 97) && (str.charAt(0) <= 122))
      str.setCharAt(0, (char)(str.charAt(0)-32));

    return new MudString(str);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString colorize()

  {
    // Fix this after we implement replaceAll...

    String str = buffer.toString();

    str = str.replaceAll("#R", RED);
    str = str.toString().replaceAll("#r", L_RED);
    str = str.toString().replaceAll("#B", BLUE);
    str = str.toString().replaceAll("#b", L_BLUE);
    str = str.toString().replaceAll("#G", GREEN);
    str = str.toString().replaceAll("#g", L_GREEN);
    str = str.replaceAll("#Y", BROWN);
    str = str.replaceAll("#y", YELLOW);
    str = str.replaceAll("#M", PURPLE);
    str = str.replaceAll("#m", L_PURPLE);
    str = str.replaceAll("#C", CYAN);
    str = str.replaceAll("#c", L_CYAN);
    str = str.replaceAll("#N", GRAY);
    str = str.replaceAll("#n", WHITE);

    str = str.replaceAll("#:R", B_RED);
    str = str.replaceAll("#:B", B_BLUE);
    str = str.replaceAll("#:Y", B_YELLOW);
    str = str.replaceAll("#:G", B_GREEN);
    str = str.replaceAll("#:M", B_PURPLE);
    str = str.replaceAll("#:C", B_CYAN);
    str = str.replaceAll("#:N", B_WHITE);
    str = str.replaceAll("#:X", B_BLACK);

    str = str.replaceAll("#:r", b_RED);
    str = str.replaceAll("#:b", b_BLUE);
    str = str.replaceAll("#:y", b_YELLOW);
    str = str.replaceAll("#:g", b_GREEN);
    str = str.replaceAll("#:m", b_PURPLE);
    str = str.replaceAll("#:c", b_CYAN);
    str = str.replaceAll("#:n", b_WHITE);
    str = str.replaceAll("#:x", b_BLACK);

    str += GRAY;

    return new MudString(str);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static MudString[] createMatchings(MudString abbr, MudString full)

  {
    int numMatchings = full.length() - abbr.length() + 1;
    MudString[] matchings = new MudString [numMatchings];

    for (int i=0; i<numMatchings; i++)
      matchings[i] = full.substring(0, abbr.length() + i);

    return matchings;
 }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void padRight(int num, String s)

  {
    for (int i=0; i<num; i++) buffer.append(s);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void padLeft(int num, String s)

  {
    for (int i=0; i<num; i++) buffer.insert(0, s);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public int visibleSize()

  {
    int size = buffer.length();
    int vsize = buffer.length();

    for (int i=size-1; i>0; i--)
    if ((buffer.charAt(i-1) == '#') && (colorCharacter(buffer.charAt(i))))
      vsize = vsize - 2;

    return vsize;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static boolean colorCharacter(char c)

  {
    return ((c == 'R') || (c == 'r') || (c == 'B') || (c == 'b')
     || (c == 'G') || (c == 'g') || (c == 'Y') || (c == 'y')
     || (c == 'M') || (c == 'm') || (c == 'C') || (c == 'c')
     || (c == 'N') || (c == 'n'));
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}