package tempest.interfaces.gameinterface.commands;

import tempest.clients.Client;
import tempest.clients.MobileClient;
import tempest.clients.PlayerClient;
import tempest.data.Data;
import tempest.interfaces.MudInterface;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.interfaces.menuinterface.MenuInterface;
import tempest.primitives.MudString;
import tempest.server.Mud;
import tempest.server.PlayerList;

public class EditCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public EditCommand()

  {
    super();
    full.set("edit");
    abbreviation.set("edit");
    level.set(100);
    help.set("Edit stuff.");
    shortcuts.set("");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)

  {
    MudString name = entity.get("attributes").get("name").value();
    Client c = PlayerList.get(name);

    if (c instanceof MobileClient)
      return new MudString("Mobiles can't edit data.");

    PlayerClient player = (PlayerClient) c;

    if (!parameter.equalsIgnoreCase("room"))
      return new MudString("You can't edit that!");

    return EditRoom(player, entity, parameter.last());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private MudString EditRoom(PlayerClient c, Data entity, MudString parameter)

  {
    Data room = entity.parent().parent();
    Data menu = Mud.get("builders").get("room").get("main_menu");
    MudInterface builder = new MenuInterface(c, menu, room);
    c.pushInterface(builder);
    return new MudString();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}