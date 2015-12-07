class Move extends Command

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Message run(Data entity, MudString direction)

  {
    Message message = tryMove(entity, direction);
    if (!message) move(entity, direction);
    return message;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Message tryMove(Data entity, MudString direction)

  {
    if (PlayerState.soulless(entity))
      return new Message("Unposessed Immortal");
    if (PlayerState.fighting(entity))
      return new Message("You're in the middle of a fight!");
    if (PlayerState.sleeping(entity))
      return new Message("You can't move in your sleep!");
    if (PlayerState.resting(entity))
      return new Message("Try standing up first.");
    if (PlayerState.sitting(entity))
      return new Message("Try standing up first.");
    if (ClientState.immat(entity))
      return new Message("You can't do that.");
    if (!MUD.get("rooms").get(entity.parent().get(direction)))
      return new Message("There is no exit in that direction.");

    return null;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void move(Data entity, MudString direction)

  {
    Data thisRoom = entity.parent();
    Data nextRoom = thisRoom.get(direction);

    MudString name = entity.get("name").firstCap();
    MudString incoming = getIncoming(direction);

    Data observers = Data.Difference(thisRoom.get("entities"), entity);
    Data targetobs = nextRoom.get("entities");

    physicalMove(entity, thisRoom, nextRoom);
    Echo.toEntity(entity, "You walk " + direction + ".");
    Echo.toList(observers, name + " walks " + direction + ".");
    Echo.toList(targetobs, name + " walks " + incoming + ".");

    moveFollowers(entity, thisRoom, nextRoom);
    Data followers = entity.get("followers");
    Data whosInRoom = nextRoom.get("entities");
    Data follsWhoMoved = Data.Intersect(followers, whosInRoom);
    Echo.toList(follsWhoMoved, "You follow " + name + " " + direction + ".");
    makeFollowersLook(follsWhoMoved, direction);

    entity.look();
    entity.reTrack(direction);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void physicalMove(Data entity, Data thisRoom, Data nextRoom)

  {
    thisRoom.rem(entity);
    nextRoom.add(entity);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void moveFollowers(Data entity, Data thisRoom, Data nextRoom)

  {
    DataIterator follower = new DataIterator(entity.get("followers"));

    while (follower.next())

    {
      Message message = tryMove(follower, dir);

      if (!message)

      {
        physicalMove(follower, thisRoom, nextRoom);
        Echo.toEntity(entity, follower.get("name") + " follows you.");
      }
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void makeFollowersLook(Data followers, MudString direction)

  {
    DataIterator follower = new DataIterator(followers);

    while (follower.next())

    {
      follower.look();
      follower.reTrack(direction);
    }
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