public abstract class Client extends Utility

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  protected Data entityData;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Data getEntityData() { return entityData; }
  public void setEntityData(Data d) { entityData = d; }
  public abstract void echo(String s);

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}