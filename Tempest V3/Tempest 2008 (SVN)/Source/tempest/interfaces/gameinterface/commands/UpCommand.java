package tempest.interfaces.gameinterface.commands;

import tempest.data.Data;
import tempest.interfaces.gameinterface.CommandTable;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudString;

public class UpCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public UpCommand()

  {
    super();
    full.set("Up");
    abbreviation.set("u");
    level.set(0);
    help.set("Entity Move Up.");
    shortcuts.set("");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)

  {
    MudString direction = new MudString("U");
    return CommandTable.getInstance().get("move").execute(entity, direction);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}