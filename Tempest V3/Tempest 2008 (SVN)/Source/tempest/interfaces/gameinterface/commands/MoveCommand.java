package tempest.interfaces.gameinterface.commands;

import tempest.data.Data;
import tempest.data.DataSet;
import tempest.interfaces.gameinterface.Command;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudChar;
import tempest.primitives.MudString;
import tempest.server.Echo;
import tempest.server.Rooms;

public class MoveCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MoveCommand()

  {
    super();
    full.set("move");
    abbreviation.set("move");
    level.set(0);
    help.set("Entity Move.");
    shortcuts.set("");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString direction)

  {
    MudString message = tryMove(entity, direction);
    if (message == null) move(entity, direction);
    return message;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString tryMove(Data entity, MudString direction)

  {
    if (entity.parent().parent().get("exits").get(direction) == null)
      return new MudString("There is no exit in that direction.");

    return null;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void move(Data entity, MudString direction)

  {
    Data thisRoom = entity.parent().parent();
    Data nextRoom = Rooms.get(thisRoom.get("exits").get(direction).value());

    MudString name = entity.name().firstCap();
    MudString incoming = getIncoming(direction);
    MudString fullDirection = getFullDirection(direction);

    DataSet observers = Data.DifferenceSingle(thisRoom.get("entities"), entity);
    DataSet targetobs = Data.List(nextRoom.get("entities"));

    physicalMove(entity, thisRoom, nextRoom);
    Echo.toEntity(entity, "You walk " + fullDirection + ".");
    Echo.toList(observers, name + " walks " + fullDirection + ".");
    Echo.toList(targetobs, name + " walks " + incoming + ".");

    Command.execute(entity, "look");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void physicalMove(Data entity, Data thisRoom, Data nextRoom)

  {
    thisRoom.get("entities").remove(entity);
    nextRoom.get("entities").add(entity);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private MudString getFullDirection(MudString direction)

  {
    MudChar dir = direction.charAt(0);
    if (dir.equals('N')) return new MudString("north");
    if (dir.equals('S')) return new MudString("south");
    if (dir.equals('E')) return new MudString("east");
    if (dir.equals('W')) return new MudString("west");
    if (dir.equals('U')) return new MudString("up");
    if (dir.equals('D')) return new MudString("down");
    return new MudString("");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private MudString getIncoming(MudString direction)

  {
    MudChar dir = direction.charAt(0);
    if (dir.equals('N')) return new MudString("in from the south");
    if (dir.equals('S')) return new MudString("in from the north");
    if (dir.equals('E')) return new MudString("in from the west");
    if (dir.equals('W')) return new MudString("in from the east");
    if (dir.equals('U')) return new MudString("up from below");
    if (dir.equals('D')) return new MudString("down from above");
    return new MudString("");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}