package tempest.interfaces.gameinterface;

import java.io.File;
import java.util.Hashtable;
import tempest.primitives.MudString;

public class CommandTable

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private static CommandTable instance;
  private Hashtable <String, EntityCommand> commands;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static CommandTable getInstance()

  {
    if (instance == null) instance = new CommandTable();
    return instance;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private CommandTable()

  {
    commands = new Hashtable <String, EntityCommand> (101);
    construct();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public EntityCommand get(MudString commandName)

  {
    return commands.get(commandName.toString());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public EntityCommand get(String commandName)

  {
    return commands.get(commandName);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void construct()

  {
    File classDirectory = new File("./Bytecode/tempest/interfaces/gameinterface/commands/");
    System.out.println(classDirectory.getPath());
    File[] classFiles = classDirectory.listFiles();

    for (File F : classFiles)

    {
      String filename = F.getName().replace('.', ' ');
      String extension = new MudString(filename).last().toString();
      String className = new MudString(filename).first().toString();

      if (extension.equals("class"))

      {
        try

        {
          ClassLoader loader = ClassLoader.getSystemClassLoader();
          Class<?> C = loader.loadClass("tempest.interfaces.gameinterface.commands." + className);

          if (C.getSuperclass() == EntityCommand.class)
            insert((EntityCommand)C.newInstance());
        }

        catch (Exception e)

        {
          e.printStackTrace();
        }
      }
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void insert(EntityCommand C)

  {
    MudString abbr = C.getAbbreviation();
    MudString full = C.getFull();
    MudString[] values = MudString.createMatchings(abbr, full);

    for (int i=0; i<values.length; i++)
    if (commands.containsKey(values[i].toString()))

    {
      StringBuffer buffer = new StringBuffer();
      buffer.append("WARNING! Conflicting key ");
      buffer.append(values[i].toString());
      buffer.append(" for command ");
      buffer.append(full);
      buffer.append(".");

      System.out.println(buffer.toString());

      buffer = new StringBuffer();
      buffer.append(values[i].toString());
      buffer.append(" will not be mapped to ");
      buffer.append(full);
      buffer.append(" command.");

      System.out.println(buffer.toString());
    }

    else commands.put(values[i].toLowerCase().toString(), C);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}