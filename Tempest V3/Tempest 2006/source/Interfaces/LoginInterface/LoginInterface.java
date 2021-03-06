import java.lang.StringBuffer;

public class LoginInterface extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private boolean nullCommand;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public LoginInterface(PlayerClient client, MudInterface previous)

  {
    super(client, previous);
    nullCommand = false;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (commandQueue.size() == 0) return;
    String command = commandQueue.removeFirst();

    if (command.length() == 0) {
      nullCommand = true;
      return; }

    Data entity = Mud.get("characters").get(command);

    if (entity == null) {
      outputQueue.add("#nInvalid character.#N");
      return; }

    if (ClientList.findClient(entity.name()) != null) {
      outputQueue.add("#nThat player is already in the game.#N");
      return; }

    entity.parent().remove(entity);
    Data room = Mud.get("zones").get("odessa").get("rooms").get("1");
    room.get("entities").add(entity);
    ClientList.putClient(entity.name(), client);
    client.setEntityData(entity);
    MudInterface newInterface = new GameInterface(client, this);
    newInterface.echo("#cWelcome to the Tempest!#N");
    client.pushInterface(newInterface);
    newInterface.focusGained();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void handleOutput()

  {
    if (nullCommand)

    {
      client.msg(getPrompt());
      nullCommand = false;
      return;
    }

    if (outputQueue.size() == 0) return;

    StringBuffer output = new StringBuffer();

    for (int i=0; i<outputQueue.size(); i++)
      output.append(outputQueue.get(i));

    output.append("\r\n");
    output.append(getPrompt());
    client.msg(output.toString());
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

  public void onDisconnect()

  {

  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    return "Enter your name: ";
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void focusGained()

  {

  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void exit()

  {

  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}