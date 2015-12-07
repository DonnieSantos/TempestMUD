import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;

class DataCollection extends Data

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Hashtable collection;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public String value()             { return "";                             }
  public void setValue(String val)  { return;                                }
  public int size()                 { return collection.size();              }
  public void add(Data data)        { collection.put(data.name(), data);     }
  public Data get(int i)            { return null;                           }
  public Data get(String objName)   { return (Data) collection.get(objName); }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public DataCollection(String collectionName)

  {
    dataID = COLLECTION;
    name = collectionName;
    collection = new Hashtable(51);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public ArrayList toList()

  {
    ArrayList A = new ArrayList();
    Enumeration enumeration = collection.elements();
    for (int i=0; i<collection.size(); i++) A.add(enumeration.nextElement());
    return A;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}