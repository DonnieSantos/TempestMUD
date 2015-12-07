package tempest.data;
import tempest.primitives.*;

public class DataSet extends DataCollection

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public DataSet()                   { super(new MudString(""));          }
  public DataSet(String collName)    { super(new MudString(collName));    }
  public DataSet(MudString collName) { super(collName);                   }
  public void add(Data data)         { collection.add(data.name(), data); }
  public void remove(Data data)      { collection.remove(data.name());    }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}