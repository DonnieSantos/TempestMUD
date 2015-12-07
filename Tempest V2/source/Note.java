import java.lang.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

public class Note extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 11;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int level;
  private int clan;
  private int clanRank;
  private int religion;
  private int religionRank;
  private String author;
  private String date;
  private String title;
  private String timeStamp;
  private Writable body;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getClan()                  { return clan;         }
  public int getClanRank()              { return clanRank;     }
  public int getReligion()              { return religion;     }
  public int getReligionRank()          { return religionRank; }
  public String getAuthor()             { return author;       }
  public String getDate()               { return date;         }
  public String getTitle()              { return title;        }
  public String getTimeStamp()          { return timeStamp;    }
  public Writable getBody()             { return body;         }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Note() { }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Note(Entity ENT, String s)

  {
    int clanID = ENT.getClanNum();
    int religionID = ENT.getReligionNum();

    String day    = SystemTime.getGamedate("day");
    String season = SystemTime.getGamedate("season");
    String vallum = SystemTime.getGamedate("vallum");

    day = day.substring(0, day.length()-2);
    if (Integer.parseInt(day) < 10) day = "0" + day;

    date         =  season + " " + day + ", " + vallum + ".";
    title        =  s;
    author       =  ENT.getName();
    level        =  ENT.getLevel();
    timeStamp    =  SystemTime.getTimeStamp();
    body         =  new Writable(Writable.FORMAT_EXACT);
    clan         =  -1;
    clanRank     =  -1;
    religion     =  -1;
    religionRank =  -1;

    if ((clanID != -1) && (World.getClan(clanID) != null))

    {
      clan = clanID;
      clanRank = World.getClan(clanID).getMemberRank(author);
    }

    if ((religionID != -1) && (World.getReligion(religionID) != null))

    {
      religion = religionID;
      religionRank = World.getReligion(religionID).getMemberRank(author);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getNoteMessage()

  {
    String temp = "";

    int len = author.length();
    String authorName = String.format("%1$-" + len + "s", author);
    String postDate = String.format("%1$" + (61-len) + "s", date);

    temp  = "#n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n";
    temp += "#n|#C " + authorName + postDate + " #n|\r\n";
    temp += "#n| ";

    int j = 61 - visibleSize(title);

    if (j%2 == 0) for (int i=0; i<(j/2); i++) temp += " ";
    else for (int i=0; i<=(j/2); i++) temp += " ";

    temp += "#C" + title;
    for (int i=0; i<(j/2); i++) temp += " ";

    temp += " #n|\r\n";
    temp += "#n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n";
    temp += "#n|                                                               |#N\r\n";

    String[] noteBody = body.getString().split("\r\n");
    int numLines = Array.getLength(noteBody);

    for (int i=0; i<numLines; i++)

    {
      int invChars = noteBody[i].length() - visibleSize(noteBody[i]);
      String xs   = whiteSpace(invChars);
      String line = String.format("%1$-62s", noteBody[i]);
      temp += "#n|#N " + line + xs + "#n|#N\r\n";
    }

    temp += "#n|                                                               |#N\r\n";
    temp += "#n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("AUTHOR");    out.writeObject(author);
      out.writeObject("TITLE");     out.writeObject(title);
      out.writeObject("BODY");      out.writeObject(body);
      out.writeObject("DATE");      out.writeObject(date);
      out.writeObject("LEVEL");     out.writeObject(new Integer(level));
      out.writeObject("CLAN");      out.writeObject(new Integer(clan));
      out.writeObject("CLANRANK");  out.writeObject(new Integer(clanRank));
      out.writeObject("RELIGION");  out.writeObject(new Integer(religion));
      out.writeObject("RELRANK");   out.writeObject(new Integer(religionRank));
      out.writeObject("TIMESTAMP"); out.writeObject(timeStamp);
      out.writeObject("NOTE END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("NOTE END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("AUTHOR"))         author = (String)in.readObject();
        else if (tag.equals("TITLE"))     title  = (String)in.readObject();
        else if (tag.equals("BODY"))      body   = (Writable)in.readObject();
        else if (tag.equals("DATE"))      date   = (String)in.readObject();
        else if (tag.equals("LEVEL"))     level  = ((Integer)in.readObject()).intValue();
        else if (tag.equals("CLAN"))      clan   = ((Integer)in.readObject()).intValue();
        else if (tag.equals("CLANRANK"))  clanRank = ((Integer)in.readObject()).intValue();
        else if (tag.equals("RELIGION"))  religion = ((Integer)in.readObject()).intValue();
        else if (tag.equals("RELRANK"))   religionRank = ((Integer)in.readObject()).intValue();
        else if (tag.equals("TIMESTAMP")) timeStamp = (String)in.readObject();
        else if (!tag.equals("NOTE END")) in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}