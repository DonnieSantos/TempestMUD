package tempest.interfaces.gameinterface.commands;

import tempest.clients.PlayerClient;
import tempest.data.Data;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudString;
import tempest.server.Echo;
import tempest.server.Mud;
import tempest.server.PlayerList;

public class QuitCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public QuitCommand()

  {
    super();
    full.set("quit");
    abbreviation.set("quit");
    level.set(0);
    help.set("Leave the game.");
    shortcuts.set("");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)

  {
    if (entity.get("attributes").get("isPlayer") == null)
      return new MudString("Mobiles cannot quit.");

    Data thisRoom = entity.parent().parent();
    Data entities = thisRoom.get("entities");
    Data observers = Data.DifferenceSingle(entities, entity);

    entities.remove(entity);
    Mud.get("characters").add(entity);

    MudString name = entity.get("attributes").get("name").value();
    PlayerClient client = (PlayerClient) PlayerList.get(name);
    client.popInterface();
    client.echo("Bye.");
    
    Echo.toList(observers, name.toString() + " has left the game.");

    return null;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}