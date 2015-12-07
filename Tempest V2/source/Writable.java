import java.io.*;

public class Writable extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 3;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int FORMAT_PLAN  = 0;
  public static final int FORMAT_DESC  = 1;
  public static final int FORMAT_RDESC = 2;
  public static final int FORMAT_EXACT = 3;
  public static final int FORMAT_IDESC = 4;
  public static final int FORMAT_LDESC = 5;
  public static final int FORMAT_CDESC = 6;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int formatType;
  private String formattedString;
  private String rawString;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getFormat()                   { return formatType;      }
  public void setFormat(int i)             { formatType = i;         }
  public void setFormattedString(String s) { formattedString = s;    }
  public void setRawString(String s)       { rawString = s;          }
  public String getRawString()             { return rawString;       }
  public String getString()                { return formattedString; }
  
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Writable(int format)

  {
    formatType = format;
    formattedString = "";
    rawString = "";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Writable(String s, int format)

  {
    formatType = format;
    write(s);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Writable()

  {
    this(FORMAT_EXACT);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void write(String s)

  {
    rawString = s;

    if (formatType == FORMAT_DESC)       formattedString = formatDesc(s);
    else if (formatType == FORMAT_RDESC) formattedString = roomAlign("   " + s);
    else if (formatType == FORMAT_LDESC) formattedString = roomAlign(s);
    else if (formatType == FORMAT_CDESC) formattedString = variableAlign("     " + s, INFO_WIDTH);
    else if (formatType == FORMAT_EXACT) formattedString = s;
    else formattedString = variableAlign(s, 65);

    if (!formattedString.endsWith("#N")) formattedString += "#N";
  }
  
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
  
  public Writable replicate()
  
  {
    Writable W = new Writable(formatType);
    W.setFormattedString(new String(formattedString));
    W.setRawString(new String(rawString));
    
    return W;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("WRITABLE END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("FORMAT"))       formatType      = ((Integer)in.readObject()).intValue();
        else if (tag.equals("FSTRING")) formattedString = (String)in.readObject();
        else if (tag.equals("RSTRING")) rawString       = (String)in.readObject();
        else if (!tag.equals("WRITABLE END")) in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("FORMAT");  out.writeObject(new Integer(formatType));
      out.writeObject("FSTRING"); out.writeObject(formattedString);
      out.writeObject("RSTRING"); out.writeObject(rawString);
      out.writeObject("WRITABLE END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}