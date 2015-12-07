public class WestCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public WestCommand()

  {
    full = "west";
    abbreviation = "w";
    level = 0;
    help = "Entity Move West.";
    shortcuts = "";
    tablePriority = 0;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Message execute(Data entity, String parameter)

  {
    return CommandTable.getInstance().get("move").execute(entity, "W");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}