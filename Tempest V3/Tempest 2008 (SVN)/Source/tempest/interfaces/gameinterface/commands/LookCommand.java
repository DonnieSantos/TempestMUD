package tempest.interfaces.gameinterface.commands;

import tempest.data.Data;
import tempest.interfaces.gameinterface.EntityCommand;
import tempest.primitives.MudOutput;
import tempest.primitives.MudString;

public class LookCommand extends EntityCommand

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public LookCommand()

  {
    super();
    full.set("look");
    abbreviation.set("l");
    level.set(0);
    help.set("Look at the room.");
    shortcuts.set("");
    tablePriority.set(0);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString execute(Data entity, MudString parameter)

  {
    Data room = entity.parent().parent();

    MudOutput output = new MudOutput();
    output.add(getNameAndDesc(room));
    output.add(getEntities(entity, room));
    output.add(getExits(room));

    return output.get();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private MudString getNameAndDesc(Data room)

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

  private MudString getEntities(Data me, Data room)

  {
    MudString entityOutput = new MudString();
    Data entities = room.get("entities");

    for (Data entity : entities)
    if (entity != me)

    {
      MudString name = entity.get("attributes").get("name").value();
      entityOutput.append("#C");
      entityOutput.append(name);
      entityOutput.append("#N is here.");
    }

    return entityOutput;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private MudString getExits(Data room)

  {
    MudString exitOutput = new MudString("");
    Data exits = room.get("exits");
    boolean noExit = true;

    exitOutput.append("[Exits: ");

    if (exits.get("N") != null) { exitOutput.append("#Cn#N"); noExit = false; }
    if (exits.get("S") != null) { exitOutput.append("#Cs#N"); noExit = false; }
    if (exits.get("E") != null) { exitOutput.append("#Ce#N"); noExit = false; }
    if (exits.get("W") != null) { exitOutput.append("#Cw#N"); noExit = false; }
    if (exits.get("U") != null) { exitOutput.append("#Cu#N"); noExit = false; }
    if (exits.get("D") != null) { exitOutput.append("#Cd#N"); noExit = false; }

    if (noExit) exitOutput.append("#CNone#N");
    exitOutput.append("]");

    return exitOutput;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}