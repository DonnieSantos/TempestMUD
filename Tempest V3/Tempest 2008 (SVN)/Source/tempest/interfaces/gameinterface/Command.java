package tempest.interfaces.gameinterface;

import tempest.clients.Client;
import tempest.data.Data;
import tempest.primitives.MudString;
import tempest.server.PlayerList;

public class Command

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static void execute(Data entity, String command)

  {
    execute(entity, new MudString(command));
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static void execute(Data entity, MudString command)

  {
    MudString commandName = command.first();
    MudString parameters = command.last();
    CommandTable table = CommandTable.getInstance();
    MudString message = table.get(commandName).execute(entity, parameters);
    MudString name = entity.get("attributes").get("name").value();
    Client client = PlayerList.get(name);
    if ((message != null) && (client != null)) client.echo(message);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}