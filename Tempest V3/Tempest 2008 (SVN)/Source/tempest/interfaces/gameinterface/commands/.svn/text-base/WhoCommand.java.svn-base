package tempest.interfaces.gameinterface.commands;

import tempest.clients.Client;
import tempest.data.Data;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudString;
import tempest.server.PlayerList;

public class WhoCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public WhoCommand()

  {
    super();
    full.set("who");
    abbreviation.set("who");
    level.set(0);
    help.set("List the current players.");
    shortcuts.set(".");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)

  {
    int size = PlayerList.numPlayers();
    MudString s = new MudString();

    for (int i=0; i<size; i++)

    {
      Client c = PlayerList.get(i);
      Data d = c.getEntityData();
      Data attributes = d.get("attributes");
      MudString name = attributes.get("name").value();
      MudString level = attributes.get("level").value();

      if (i > 0) s.appendLine();
      s.append("[ #Y");
      s.append(level.toString());
      s.append("#N ] #C");
      s.append(name.toString());
      s.append("#N");
    }

    return s;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}