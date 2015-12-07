public class LookCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Data me;
  private Data room;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public LookCommand()

  {
    full = "look";
    abbreviation = "l";
    level = 0;
    help = "Look at the room.";
    shortcuts = "";
    tablePriority = 0;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public Message execute(Data entity, String parameter)

  {
    setPointers(entity, entity.parent().parent());

    MudOutput output = new MudOutput();
    output.add(getNameAndDesc());
    output.add(getEntities());
    output.add(getExits());

    return new Message(output.toString());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void setPointers(Data entity, Data entityRoom)

  {
    me = entity;
    room = entityRoom;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private MudString getNameAndDesc()

  {
    MudString roomOutput = new MudString();
    roomOutput.append("#r");
    roomOutput.append(room.get("attributes").get("name").value());
    roomOutput.append("#N\r\n");
    roomOutput.append(room.get("attributes").get("description").value());
    return roomOutput;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private MudString getEntities()

  {
    MudString entityOutput = new MudString();
    Data entities = room.get("entities");

    for (Data entity : entities)
    if (entity != me)

    {
      entityOutput.append("\r\n#C");
      entityOutput.append(entity.name());
      entityOutput.append("#N is here.");
    }

    return entityOutput;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private MudString getExits()

  {
    MudString exitOutput = new MudString("");
    Data exits = room.get("exits");

    Data N = exits.get("N");
    Data S = exits.get("S");
    Data E = exits.get("E");
    Data W = exits.get("W");
    Data U = exits.get("U");
    Data D = exits.get("D");

    boolean noExit = true;

    exitOutput.append("\r\n");
    exitOutput.append("[Exits: ");

    if (N != null) { exitOutput.append("#Cn#N");  noExit = false; }
    if (S != null) { exitOutput.append("#Cs#N");  noExit = false; }
    if (E != null) { exitOutput.append("#Ce#N");  noExit = false; }
    if (W != null) { exitOutput.append("#Cw#N");  noExit = false; }
    if (U != null) { exitOutput.append("#Cu#N");  noExit = false; }
    if (D != null) { exitOutput.append("#Cd#N");  noExit = false; }

    if (noExit) exitOutput.append("#CNone#N");
    exitOutput.append("]");

    return exitOutput;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}