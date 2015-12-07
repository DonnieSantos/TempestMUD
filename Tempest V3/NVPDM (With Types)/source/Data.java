import java.util.ArrayList;

abstract class Data

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  protected static final int COLLECTION = 1;
  protected static final int ARRAY      = 2;
  protected static final int OBJECT     = 3;
  protected static final int VARIABLE   = 4;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  protected String name;
  protected Data parent;
  protected int dataID;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public String name()              { return name;                   }
  public boolean hasName()          { return (name().length() > 0);  }
  public boolean isCollection()     { return (dataID == COLLECTION); }
  public boolean isArray()          { return (dataID == ARRAY);      }
  public boolean isObject()         { return (dataID == OBJECT);     }
  public boolean isVariable()       { return (dataID == VARIABLE);   }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  abstract String value();
  abstract void setValue(String val);
  abstract void add(Data D);
  abstract Data get(String objectName);
  abstract Data get(int index);
  abstract int size();
  abstract ArrayList toList();

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}