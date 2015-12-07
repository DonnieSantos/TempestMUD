import java.lang.Iterable;
import java.util.Iterator;

abstract class Data implements Iterable <Data>

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  protected static final int VARIABLE   = 1;
  protected static final int OBJECT     = 2;
  protected static final int COLLECTION = 3;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  protected String name;
  protected Data parent;
  protected int dataID;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void add(Data D)           { D.setParent(this);             }
  public void remove(Data D)        { D.setParent(null);             }
  public void setParent(Data D)     { parent = D;                    }
  public Data parent()              { return parent;                 }
  public String name()              { return name;                   }
  public boolean isVariable()       { return (dataID == VARIABLE);   }
  public boolean isObject()         { return (dataID == OBJECT);     }
  public boolean isCollection()     { return (dataID == COLLECTION); }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public abstract String value();
  public abstract void setValue(String s);
  public abstract Iterator <Data> iterator();
  public abstract Data get(String objectName);
  public abstract int size();

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static Data Intersect(Data set1, Data set2)

  {
    return Data.Difference(set1, Difference(set1, set2));
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static Data Difference(Data set1, Data set2)

  {
    Data difference = new DataCollection("");

    if (set1.isObject()) difference.add(set1);
    else if (set1.isCollection())
    for (Data object : set1)
    difference.add(object);

    if (set2.isObject()) difference.remove(set2);
    else if (set2.isCollection())
    for (Data object : set2)
    difference.remove(object);

    return difference;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static Data Union(Data set1, Data set2)

  {
    Data union = new DataCollection("");

    if (set1.isObject()) union.add(set1);
    else if (set1.isCollection())
    for (Data object : set1)
    union.add(object);

    if (set2.isObject()) union.add(set2);
    else if (set2.isCollection())
    for (Data object : set2)
    union.add(object);

    return union;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}