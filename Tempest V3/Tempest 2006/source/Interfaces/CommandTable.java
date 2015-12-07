import java.io.File;
import java.util.Hashtable;

public class CommandTable extends Utility

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

  public EntityCommand get(String commandName)

  {
    return commands.get(commandName);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void construct()

  {
    File classDirectory = new File("./bytecode/");
    File[] classFiles = classDirectory.listFiles();

    for (File F : classFiles)

    {
      String filename = F.getName().replace('.', ' ');
      String extension = last(filename);
      String className = first(filename);

      if (extension.equals("class"))

      {
        try

        {
          ClassLoader loader = ClassLoader.getSystemClassLoader();
          Class C = loader.loadClass(className);
          if (C.getSuperclass() == EntityCommand.class)
            insert((EntityCommand)C.newInstance());
        }

        catch (Exception e)

        {
          System.out.println(e);
        }
      }
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void insert(EntityCommand C)

  {
    String abbr = C.getAbbreviation();
    String full = C.getFull();
    String[] values = createMatchings(abbr, full);

    for (int i=0; i<values.length; i++)
    if (commands.containsKey(values[i]))

    {
      StringBuffer buffer = new StringBuffer();
      buffer.append("WARNING! Conflicting key ");
      buffer.append(values[i]);
      buffer.append(" for command ");
      buffer.append(full);
      buffer.append(".");

      System.out.println(buffer.toString());

      buffer = new StringBuffer();
      buffer.append(values[i]);
      buffer.append(" will not be mapped to ");
      buffer.append(full);
      buffer.append(" command.");

      System.out.println(buffer.toString());
    }

    else commands.put(values[i], C);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}