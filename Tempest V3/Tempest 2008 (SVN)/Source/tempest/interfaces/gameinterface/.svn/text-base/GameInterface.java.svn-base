package tempest.interfaces.gameinterface;

import tempest.clients.PlayerClient;
import tempest.data.Data;
import tempest.interfaces.MudInterface;
import tempest.primitives.MudString;
import tempest.server.Echo;
import tempest.server.Mud;
import tempest.server.PlayerList;

public class GameInterface extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private boolean nullCommand;
  private boolean commandEntered;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public GameInterface(PlayerClient client)

  {
    super(client);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (commandQueue.size() == 0) return;
    commandEntered = true;
    String command = commandQueue.removeFirst();

    if (command.length() != 0)

    {
      CommandTable table = CommandTable.getInstance();

      MudString temp = new MudString(command);
      EntityCommand C = table.get(temp.first().toLowerCase().toString());
      if (C == null) C = table.get("invalid");
      MudString message = C.execute(client.getEntityData(), temp.last());
      if (message != null) echo(message);
    }

    else nullCommand = true;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void handleOutput()

  {
    if (client.isLinkdead()) return;

    if (nullCommand)

    {
      client.msg(getPrompt());
      nullCommand = false;
      return;
    }

    if (outputQueue.size() == 0) return;

    MudString output = new MudString();

    if (!commandEntered) output.append("\r\n");

    commandEntered = false;

    for (int i=0; i<outputQueue.size(); i++)

    {
      output.append("\r\n");
      output.append(outputQueue.get(i));
    }

    output.append("\r\n\r\n");
    output.append(getPrompt());
    client.msg(output);
    outputQueue.clear();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void receiveCommand(String commandString) throws Exception

  {
    commandString = commandString.trim();

    if (commandString.length() > 0)
      commandQueue.add(commandString);
    else nullCommand = true;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void echo(String s)

  {
    outputQueue.add(s);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void echo(MudString s)

  {
    outputQueue.add(s.toString());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void onPush()

  {
    Data entity = client.getEntityData();
    Data room = Mud.get("zones").get("odessa").get("rooms").get("1");
    room.get("entities").add(entity);
    PlayerList.add(client);
    echo("#cWelcome to the Tempest!#N");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void onPop()

  {
    PlayerList.remove(client);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    Data entity = client.getEntityData();
    Data entityList = entity.parent();
    Data targets = Data.DifferenceSingle(entityList, entity);
    MudString name = entity.get("attributes").get("name").value();

    MudString output = new MudString();
    output.append(name);
    output.append(" has been disconnected.");

    Echo.toList(targets, output);

    outputQueue.clear();
    commandQueue.clear();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void onReconnect()

  {
    Data entity = client.getEntityData();
    Data entityList = entity.parent();
    Data targets = Data.DifferenceSingle(entityList, entity);
    MudString name = entity.get("attributes").get("name").value();

    MudString output = new MudString();
    output.append(name);
    output.append(" has reconnected.");

    Echo.toList(targets, output);
    Echo.toEntity(entity, "#cYou reassume your body!#N");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString getPrompt()

  {
    return new MudString("--> ");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void focusGained()

  {
    commandEntered = true;
    CommandTable table = CommandTable.getInstance();
    EntityCommand C = table.get("look");

    Data entity = client.getEntityData();
    MudString output = C.execute(entity, new MudString(""));
    Echo.toEntity(entity, output);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void exit()

  {

  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}