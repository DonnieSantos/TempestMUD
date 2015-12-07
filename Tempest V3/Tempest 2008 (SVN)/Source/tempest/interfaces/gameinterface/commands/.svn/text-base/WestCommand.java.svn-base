package tempest.interfaces.gameinterface.commands;

import tempest.data.Data;
import tempest.interfaces.gameinterface.CommandTable;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudString;

public class WestCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public WestCommand()

  {
    super();
    full.set("West");
    abbreviation.set("w");
    level.set(0);
    help.set("Entity Move West.");
    shortcuts.set("");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)

  {
    MudString direction = new MudString("W");
    return CommandTable.getInstance().get("move").execute(entity, direction);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}