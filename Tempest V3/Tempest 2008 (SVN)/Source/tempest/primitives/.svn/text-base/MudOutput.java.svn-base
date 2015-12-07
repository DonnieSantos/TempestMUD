package tempest.primitives;
import java.util.ArrayList;

public class MudOutput

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private ArrayList <MudString> output;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudOutput()           { output = new ArrayList <MudString> (); }
  public void add(MudString s) { output.add(s);                         }
  public void add(String s)    { output.add(new MudString(s));          }
  public MudString get(int i)  { return output.get(i);                  }
  public int count()           { return output.size();                  }
  public void clear()          { output.clear();                        }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void add(MudOutput s)

  {
    for (int i=0; i<s.count(); i++) output.add(s.get(i));
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString get()

  {
    MudString allOutput = new MudString();

    for (int i=0; i<output.size(); i++)

    {
      MudString s = (MudString) output.get(i);
      if (s.length() == 0) continue;
      if (i > 0) allOutput.appendLine();
      allOutput.append(s);
    }

    return allOutput;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString boxify(int indent)

  {
    if (output.size() == 0) return new MudString();

    int width = 0;

    for (MudString s : output)
    if (s.visibleSize() > width)
      width = s.visibleSize();

    MudString border = new MudString();
    border.padLeft(indent, " ");
    border.append("#n+-");
    border.padRight(width, "-");
    border.append("-+#N");

    MudString temp = new MudString();
    temp.appendLine(border);
    boolean center = false;

    for (MudString menuItem : output)

    {
      if (menuItem.equals("-")) temp.appendLine(border);

      else if (menuItem.equalsIgnoreCase("center")) center = true;

      else

      {
        MudString line = new MudString();

        if (!center)

        {
          line.padLeft(indent, " ");
          line.append("#n|#N ");
          line.append(menuItem);
          line.padRight(width - menuItem.visibleSize(), " ");
          line.append(" #n|#N\r\n");
        }

        else if (center)

        {
          int marginSpace = width - menuItem.visibleSize();
          int leftMargin = marginSpace / 2;
          int rightMargin = marginSpace / 2;
          if ((marginSpace%2)!=0) rightMargin++;

          line.padLeft(indent, " ");
          line.append("#n|#N ");
          line.padRight(leftMargin, " ");
          line.append(menuItem);
          line.padRight(rightMargin, " ");
          line.append(" #n|#N\r\n");

          center = false;
        }

        temp.append(line);
      }
    }

    temp.append(border);
    return temp;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}