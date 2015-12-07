package tempest.interfaces.gameinterface.commands;

import tempest.data.Data;
import tempest.interfaces.gameinterface.CommandTable;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudString;

public class NorthCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public NorthCommand()

  {
    super();
    full.set("North");
    abbreviation.set("n");
    level.set(0);
    help.set("Entity Move North.");
    shortcuts.set("");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)

  {
    MudString direction = new MudString("N");
    return CommandTable.getInstance().get("move").execute(entity, direction);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}