public class InvalidCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public InvalidCommand()

  {
    full = "invalid";
    abbreviation = "invalid";
    level = 0;
    help = "Invalid message.";
    shortcuts = "";
    tablePriority = 0;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Message execute(Data entity, String parameter)

  {
    return new Message("Invalid Command.");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}