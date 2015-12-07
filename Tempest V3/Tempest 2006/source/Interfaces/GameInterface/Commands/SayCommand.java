import java.util.ArrayList;
import java.lang.StringBuffer;

public class SayCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public SayCommand()

  {
    full = "say";
    abbreviation = "say";
    level = 0;
    help = "Send a message to the room.";
    shortcuts = "'";
    tablePriority = 0;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Message execute(Data entity, String parameter)

  {
    Data entities = entity.parent();
    StringBuffer s = new StringBuffer(parameter.length());

    for (Data current : entities)
    if (entity != current)

    {
      Client C = ClientList.findClient(current.name());
      s.append(entity.name());
      s.append(" says, '");
      s.append(parameter);
      s.append("'");
      C.echo(s.toString());
    }

    s = new StringBuffer(parameter.length());
    s.append("You say, '");
    s.append(parameter);
    s.append("'");

    return new Message(s.toString());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}