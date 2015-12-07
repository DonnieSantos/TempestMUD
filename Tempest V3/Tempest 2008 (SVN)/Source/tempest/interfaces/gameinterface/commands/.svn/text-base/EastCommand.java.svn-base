package tempest.interfaces.gameinterface.commands;

import tempest.data.Data;
import tempest.interfaces.gameinterface.CommandTable;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudString;

public class EastCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public EastCommand()

  {
    super();
    full.set("East");
    abbreviation.set("e");
    level.set(0);
    help.set("Entity Move East.");
    shortcuts.set("");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)

  {
    MudString direction = new MudString("E");
    return CommandTable.getInstance().get("move").execute(entity, direction);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}