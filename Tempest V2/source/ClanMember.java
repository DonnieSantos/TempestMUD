import java.util.*;
import java.io.*;

public class ClanMember implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 8;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private String name;
  private String profession;
  private int rank;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ClanMember() { }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getName()         { return name;       }
  public int getRank()            { return rank;       }
  public String getCharClass()    { return profession; }
  public void setName(String s)   { name = s;          }
  public void setRank(int i)      { rank = i;          }
  public void setClass(String s)  { profession = s;    }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ClanMember(String mname, int mrank, String mprofession)

  {
    this();
    name = mname;
    rank = mrank;
    profession = mprofession;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("NAME"); out.writeObject(name);
      out.writeObject("PROF"); out.writeObject(profession);
      out.writeObject("RANK"); out.writeObject(new Integer(rank));
      out.writeObject("CLANMEMBER END");
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

      while (!tag.equals("CLANMEMBER END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("NAME"))      name = (String)in.readObject();
        else if (tag.equals("PROF")) profession = (String)in.readObject();
        else if (tag.equals("RANK")) rank = ((Integer)in.readObject()).intValue();
        else if (!tag.equals("CLANMEMBER END")) in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}