package tempest.interfaces.gameinterface.commands;

import tempest.clients.Client;
import tempest.data.Data;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudString;
import tempest.server.PlayerList;

public class SayCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public SayCommand()

  {
    super();
    full.set("say");
    abbreviation.set("say");
    level.set(0);
    help.set("Send a message to the room.");
    shortcuts.set("'");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)

  {
    Data entities = entity.parent();
    MudString s = new MudString();

    for (Data current : entities)
    if (entity != current)

    {
      MudString name = current.get("attributes").get("name").value();
      Client C = PlayerList.get(name);
      s.append(entity.name());
      s.append(" says, '");
      s.append(parameter);
      s.append("'");
      C.echo(s);
    }

    s = new MudString();
    s.append("You say, '");
    s.append(parameter);
    s.append("'");

    return s;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}