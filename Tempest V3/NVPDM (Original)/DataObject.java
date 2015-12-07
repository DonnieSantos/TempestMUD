import java.io.*;
import java.util.*;

class DataObject extends Data

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  ArrayList vars = new ArrayList();

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public String value()             { return "";     }
  public void setValue(String val)  { return;        }
  public void add(Data var)         { vars.add(var); }
  public Data get(int index)        { return null;   }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public DataObject(String className)

  {
    name = className;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Data get(String objectName)

  {
    // Replace this with Hash Table Lookup later...

    for (int i=0; i<vars.size(); i++)
    if (((DataVariable)vars.get(i)).name().equalsIgnoreCase(objectName))
    return (Data) vars.get(i);

    return null;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}