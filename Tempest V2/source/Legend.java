import java.io.*;
import java.util.*;

class Legend extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 14;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private Entity owner;
  private ArrayList list;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int MAX_LEGEND_SIZE = 100;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public LegendMark getMark(int i)  { return (LegendMark) list.get(i); }
  public void setOwner(Entity E)    { owner = E;                       }
  public void setList(ArrayList A)  { list = A;                        }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Legend()

  {
    owner = null;
    list = new ArrayList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Legend(Entity Owner)

  {
    owner = Owner;
    list = new ArrayList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
  
  public Legend replicate()
  
  {
    Legend L = new Legend();
    ArrayList A = new ArrayList(list.size());
    
    for (int i=0; i<list.size(); i++)
      A.add(((LegendMark)list.get(i)).replicate());
    
    L.setList(A);
    
    return L;
  }
  
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean addMark(String mark)

  {
    String date = "Vallum " + SystemTime.getGamedate("vallum") + ".";
    return addMark(mark, date);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean addMark(String mark, String date)

  {
    if (list.size() >= MAX_LEGEND_SIZE) return false;

    for (int i=0; i<list.size(); i++)

    {
      if (mark.equalsIgnoreCase(getMark(i).mark))
        return updateMark(mark, i, date);

      if (mark.toLowerCase().indexOf("mentored by") == 0)
      if (getMark(i).mark.toLowerCase().indexOf("mentored by") == 0)
        return overwriteMark(mark, i, date);
    }

    list.add(new LegendMark(mark, date, 1));
    owner.castChar().save();
    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean removeMark(String mark)

  {
    mark = Utility.makePlain(mark);

    for (int i=0; i<list.size(); i++)

    {
      LegendMark LM = getMark(i);
      String temp = Utility.makePlain(LM.mark);

      if (temp.equals(mark))

      {
        list.remove(i);
        return true;
      }
    }

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean updateMark(String newMark, int position, String date)

  {
    LegendMark updated_mark = getMark(position);

    updated_mark.mark = newMark;
    updated_mark.date = date;

    if (updated_mark.times < 999)
      updated_mark.times++;

    int last = list.size()-1;

    for (int i=position; i<last; i++)
      list.set(i, getMark(i+1));

    list.set(last, updated_mark);
    owner.castChar().save();
    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean overwriteMark(String newMark, int position, String date)

  {
    LegendMark updated_mark = getMark(position);

    updated_mark.mark = newMark;
    updated_mark.date = date;

    if (updated_mark.times < 999)
      updated_mark.times++;

    int last = list.size()-1;

    for (int i=position; i<last; i++)
      list.set(i, getMark(i+1));

    list.set(last, updated_mark);
    owner.castChar().save();
    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getLegend()

  {
    int left_width = 0;
    int right_width = 0;
    int left_size;
    int right_size;
    int bottom_width;
    int width;

    for (int i=0; i<list.size(); i++)

    {
      LegendMark L = getMark(i);
      left_size = visibleSize(L.mark);
      if (L.times > 1) left_size += 4;
      if (L.times > 9) left_size += 1;
      if (L.times > 99) left_size += 1;

      right_size = visibleSize(L.date);

      if (left_size > left_width) left_width = left_size;
      if (right_size > right_width) right_width = right_size;
    }

    String clan_name;
    if (owner.getClanNum() == -1) clan_name = "None";
    else clan_name = owner.castChar().getClan().getName();

    int name_size = visibleSize(owner.getName());
    int clan_size = visibleSize(clan_name);
    if (clan_name.equals("None")) clan_size = 0;
    else clan_size = clan_size + 3;
    int title_width = name_size + clan_size + 1;

    width = left_width + right_width + 6;
    bottom_width = width;
    if (title_width > width) width = title_width;

    String border = "  #n+-" + xchars(width,"-") + "-+#N";
    String temp = border + "\r\n";

    temp += "  #n|#N " + owner.getName() + ".";
    if (clan_size > 0) temp += " (" + clan_name + "#N)";
    temp += xchars(width-title_width," ") + " #n|#N\r\n" + border + "\r\n";

    int lspaces;
    int rspaces;

    for (int i=0; i<list.size(); i++)

    {
      LegendMark L = getMark(i);

      lspaces = left_width - visibleSize(L.mark);
      rspaces = right_width - visibleSize(L.date);

      if (L.times > 1) lspaces -= 4;
      if (L.times > 9) lspaces -= 1;
      if (L.times > 99) lspaces -= 1;

      temp += "  #n|#N ";
      temp += L.mark;

      if (L.times > 1) temp += " #N(" + L.times + ")";

      temp += xchars(lspaces," ") + "  #N--  " + L.date;

      temp += xchars(title_width-bottom_width," ");
      temp += xchars(rspaces," ") +" #n|#N\r\n";
    }

    temp += border;

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("LIST");   out.writeObject(list);
      out.writeObject("LEGEND END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("LEGEND END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("LIST")) list = (ArrayList)in.readObject();
        else if (!tag.equals("LEGEND END"))  in.readObject();
      }

    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}