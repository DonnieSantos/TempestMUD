import java.io.*;
import java.util.*;

public class AliasList extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 7;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private Entity me;
  private ArrayList list;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getSize()             { return list.size();         }
  public Alias getAlias(int i)     { return (Alias) list.get(i); }
  public void setOwner(Entity e)   { me = e;                     }
  public void setList(ArrayList A) { list = A;                   }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public AliasList() { }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public AliasList(Entity owner)

  {
    me = owner;
    list = new ArrayList();
  }
  
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
  
  public AliasList replicate()
  
  {
    AliasList AL = new AliasList(null);
    ArrayList A = new ArrayList();
    
    for (int i=0; i<list.size(); i++)
      A.add(((Alias)list.get(i)).replicate());
    
    AL.setList(A);
    
    return AL;    
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addAlias(String str)

  {
    if (str.equals("")) {
      displayAll();
      return; }

    String a1 = clearWhiteSpace(removeColors(first(str).toLowerCase()));
    String Rem = last(str);

    if (a1.equals("alias")) {
      if (Rem.equals("")) me.echo("No such alias to remove.");
      else me.echo("Illegal alias definition.");
      return; }

    Alias A = searchAlias(a1);

    if (Rem.equals(""))

    {
      if (A == null) {
        me.echo("No such alias to remove.");
        return; }

      removeAlias(A, true);
      return;
    }

    String lc = lastCommand(Rem).toLowerCase();
    String lc2;

    for (int i=0; i<list.size(); i++)

    {
      Alias Als = getAlias(i);

      lc2 = lastCommand(Als.output).toLowerCase();

      if ((Als.name.toLowerCase().equals(lc)) && (a1.equals(lc2))) {
        me.echo("Aliasing in loops is not allowed.");
        return; }
    }

    if (A != null) removeAlias(A, false);
    A = new Alias();
    A.name = a1;
    A.output = Rem;
    addAlias(A);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addAlias(Alias A)

  {
    if (list.size() >= MAX_NUM_ALIASES) {
      me.echo("You have reached the maximum number of aliases.");
      return; }

    list.add(A);

    me.echo("Ok. [" + A.name + "] will now output [" + A.output + "].");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Alias searchAlias(String s)

  {
    for (int i=0; i<list.size(); i++)

    {
      Alias A = getAlias(i);
      if (A.name.equals(s)) return A;
    }

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int searchPosition(String s)

  {
    for (int i=0; i<list.size(); i++)

    {
      Alias A = getAlias(i);
      if (A.name.equals(s)) return i;
    }

    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeAlias(Alias A, boolean audible)

  {
    int pos = searchPosition(A.name);

    if (pos != -1) {
      list.remove(pos);
      if (audible) me.echo("Alias [" + A.name + "] has been removed.");
      return; }

    me.echo("Attempted to remove alias but didn't find it.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void displayAll()

  {
    if (list.size() <= 0) {
      String hlp = "Type 'help alias' for information on using aliases.";
      me.echo("You have no aliases defined.\r\n" + hlp);
      return; }

    String temp = "#nCurrently Defined Aliases#R:#N";

    for (int i=0; i<list.size(); i++)

    {
      Alias A = getAlias(i);
      temp += "\r\n" + A.name + " = " + A.output;
    }

    me.echo(temp);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String applyAliasing(String str)

  {
    String KEY = first(str.toLowerCase());
    StringBuffer command = new StringBuffer(str);
    StringBuffer key = new StringBuffer(KEY);
    int ksize = key.length();
    boolean found = false;

    for (int i=0; i<list.size(); i++)

    {
      String nm = getAlias(i).name;

      if (nm.equals(key.toString()))

      {
        found = true;
        key = new StringBuffer(getAlias(i).output);
        i = list.size();
      }
    }

    if (!found) return str;

    command = command.delete(0, ksize);
    while (command.indexOf(" ") == 0) command = command.delete(0,1);

    while ((command.indexOf("%") != -1) && (command.length() > 0))
      command = command.delete(command.indexOf("%"), command.indexOf("%")+1);

    if (key.indexOf("%") != -1)

    {
      while (key.indexOf("%") != -1)

      {
        int pos = key.indexOf("%");
        key = key.delete(pos, pos+1);
        key = key.insert(pos, command);
      }

      return key.toString();
    }

    String cmd = command.toString();
    if (cmd.equals("")) return key.toString();
    return (key.toString() + " " + command.toString());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ALIST"); out.writeObject(list);
      out.writeObject("ALIASLIST END");
    }

    catch (Exception e) { System.out.println(e); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("ALIASLIST END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("ALIST")) list = (ArrayList)in.readObject();
        else if (!tag.equals("ALIASLIST END")) in.readObject();
      }
    }

    catch (Exception e) { System.out.println(e); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}