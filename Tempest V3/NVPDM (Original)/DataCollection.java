import java.io.*;
import java.util.*;

class DataCollection extends Data

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  ArrayList collection = new ArrayList();

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public String value()             { return "";                  }
  public void setValue(String val)  { return;                     }
  public void add(Data dataObject)  { collection.add(dataObject); }
  public Data get(int index)        { return null;                }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public DataCollection(String collectionName)

  {
    name = collectionName;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Data get(String objectName)

  {
    // Replace this with Hash Table Lookup later...

    for (int i=0; i<collection.size(); i++)
    if (((Data)collection.get(i)).name().equalsIgnoreCase(objectName))
    return (Data) collection.get(i);

    return null;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}