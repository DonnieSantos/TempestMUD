import java.io.*;
import java.util.*;

public class ItemSlot extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int STACK_SIZE = 20;
  public static final long serialVersionUID = 101;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private ArrayList itemList;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ItemSlot()               { itemList = new ArrayList();   }
  public Item getItem(int i)      { return (Item)itemList.get(i); }
  public int getNumItems()        { return itemList.size();       }
  public void add(Item I)         { itemList.add(I);              }
  public boolean contains(Item I) { return itemList.contains(I);  }
  public ArrayList getItems()     { return itemList;              }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean canAdd(Item I, int type)

  {
    if (itemList.isEmpty()) return true;
    Item itemInside = getItem(0);

    if (type != ItemList.TYPE_ROOM)

    {
      if (!itemInside.getFlag(Item.FLAG_STACKABLE)) return false;
      if (itemList.size() >= STACK_SIZE) return false;
    }

    if (itemInside.getName().equals(I.getName()))
    if (itemInside.getID() == I.getID())
      return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void remove(Item I)

  {
    int index = itemList.indexOf(I);
    if (index == -1) return;
    itemList.remove(index);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getDisplay(int type)

  {
    if (type == ItemList.TYPE_INVENTORY) return getInventoryDisplay();
    if (type == ItemList.TYPE_ROOM) return getRoomDisplay();
    if (type == ItemList.TYPE_CONTAINER) return getContainerDisplay();

    return "Unknown Type.";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private String getRoomDisplay()

  {
    if (itemList.size() == 0) return "";

    Item I = (Item)itemList.get(0);
    StringBuffer s = new StringBuffer();

    if (itemList.size() > 1)
      s.append(String.format("#N[%1$2s] #M", itemList.size()));

    if (I.findType(Item.ITEM_BOARD) != -1) s.append("#G");
    else s.append("#M");

    s.append(I.getGDesc());
    s.append("#N");

    return s.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private String getContainerDisplay()

  {
    return getInventoryDisplay();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private String getInventoryDisplay()

  {
    if (itemList.size() == 0) return "|                             ";

    Item I = (Item)itemList.get(0);
    int xs = 0;
    int len = removeColors(I.getName()).length();
    int clen = I.getName().length();
    StringBuffer s = new StringBuffer();

    s.append("| ");

    if (itemList.size() > 1)

    {
      xs = 5;
      s.append("(");
      s.append(String.format("%1$2s", itemList.size()));
      s.append(") ");
    }

    int finalLength = 27 - xs + clen - len;
    String format = "%1$-" + finalLength + "." + finalLength + "s";
    s.append(String.format(format, I.getName()));
    s.append(" ");

    return s.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ItemSlot replicate()

  {
    ItemSlot S = new ItemSlot();
    for (int i=0; i<itemList.size(); i++) S.add((Item)itemList.get(i));
    return S;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ILIST"); out.writeObject(itemList);
      out.writeObject("END ILIST");
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

      while (!tag.equals("END ILIST"))

      {
        tag = (String)in.readObject();
        if (tag.equals("ILIST")) itemList = (ArrayList)in.readObject();
        else if (!tag.equals("END ILIST")) in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}
