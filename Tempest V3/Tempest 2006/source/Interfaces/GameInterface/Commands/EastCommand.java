public class EastCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public EastCommand()

  {
    full = "east";
    abbreviation = "e";
    level = 0;
    help = "Entity Move East.";
    shortcuts = "";
    tablePriority = 0;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Message execute(Data entity, String parameter)

  {
    return CommandTable.getInstance().get("move").execute(entity, "E");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}