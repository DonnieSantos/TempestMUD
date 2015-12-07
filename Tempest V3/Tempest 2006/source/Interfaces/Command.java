class Command

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static void execute(Data entity, String command)

  {
    String commandName = Utility.first(command);
    String parameters = Utility.last(command);
    CommandTable table = CommandTable.getInstance();
    Message message = table.get(commandName).execute(entity, parameters);
    Client client = ClientList.findClient(entity.name());
    if ((message != null) && (client != null)) client.echo(message.toString());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}