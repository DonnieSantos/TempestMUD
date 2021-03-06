abstract class Data

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  protected String name;
  protected Data parent;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public String name() { return name; }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  abstract String value();
  abstract void setValue(String val);
  abstract void add(Data D);
  abstract Data get(String objectName);
  abstract Data get(int index);

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}