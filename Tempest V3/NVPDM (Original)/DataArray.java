import java.io.*;
import java.util.*;

class DataArray extends Data

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  ArrayList array = new ArrayList();

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public String value()                { return "";                        }
  public void setValue(String val)     { return;                           }
  public void add(Data dataObject)     { array.add(dataObject);            }
  public Data get(int index)           { return (Data) array.get(index-1); }
  public Data get(String objectIndex)  { return null;                      }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public DataArray(String arrayName)

  {
    name = arrayName;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}