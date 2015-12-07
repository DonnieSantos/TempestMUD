package tempest.interfaces.gameinterface.commands;

import tempest.data.Data;
import tempest.interfaces.gameinterface.CommandTable;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudString;

public class SouthCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public SouthCommand()

  {
    super();
    full.set("South");
    abbreviation.set("s");
    level.set(0);
    help.set("Entity Move South.");
    shortcuts.set("");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)

  {
    MudString direction = new MudString("S");
    return CommandTable.getInstance().get("move").execute(entity, direction);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}