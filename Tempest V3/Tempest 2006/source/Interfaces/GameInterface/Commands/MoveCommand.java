public class MoveCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MoveCommand()

  {
    full = "move";
    abbreviation = "move";
    level = 0;
    help = "Entity Move.";
    shortcuts = "";
    tablePriority = 0;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Message execute(Data entity, String direction)

  {
    Message message = tryMove(entity, direction);
    if (message == null) move(entity, direction);
    return message;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Message tryMove(Data entity, String direction)

  {
    if (entity.parent().parent().get("exits").get(direction) == null)
      return new Message("There is no exit in that direction.");

    return null;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void move(Data entity, String direction)

  {
    Data thisRoom = entity.parent().parent();
    Data nextRoom = Rooms.get(thisRoom.get("exits").get(direction).value());

    String name = Utility.properName(entity.name());
    String incoming = getIncoming(direction);

    Data observers = Data.Difference(thisRoom.get("entities"), entity);
    Data targetobs = nextRoom.get("entities");

    physicalMove(entity, thisRoom, nextRoom);
    Echo.toEntity(entity, "You walk " + direction + ".");
    Echo.toList(observers, name + " walks " + direction + ".");
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

  private String getIncoming(String direction)

  {
    char dir = direction.charAt(0);
    if (dir=='N') return new String("in from the south");
    if (dir=='S') return new String("in from the north");
    if (dir=='E') return new String("in from the west");
    if (dir=='W') return new String("in from the east");
    if (dir=='U') return new String("up from below");
    if (dir=='D') return new String("down from above");
    return new String("");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}