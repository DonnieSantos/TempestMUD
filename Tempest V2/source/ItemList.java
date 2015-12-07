import java.io.*;
import java.util.*;

public class ItemList extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final long serialVersionUID = 100;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int TYPE_ROOM      = 0;
  public static final int TYPE_INVENTORY = 1;
  public static final int TYPE_CONTAINER = 2;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int type;
  private Room myRoom;
  private Entity myEntity;
  private Item myContainer;
  private ItemSlot[] slotList;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ItemList(int size, int t)

  {
    type = t;
    if (size % 2 == 1) size++;
    slotList = new ItemSlot[size];
    for (int i=0; i<size; i++) slotList[i] = new ItemSlot();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ItemList()                      {                  }
  public void setEntity(Entity E)        { myEntity = E;    }
  public void setRoom(Room R)            { myRoom = R;      }
  public void setContainer(Item I)       { myContainer = I; }
  public void setSlot(int i, ItemSlot S) { slotList[i] = S; }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getNumItems()

  {
    int count = 0;

    for (int i=0; i<slotList.length; i++)
      count += slotList[i].getNumItems();

    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean canFit(Item I)

  {
    for (int i=0; i<slotList.length; i++)
    if (slotList[i].canAdd(I, type))
      return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void add(Item I)

  {
    for (int i=0; i<slotList.length; i++)
    if (slotList[i].canAdd(I, type))

    {
      slotList[i].add(I);
      return;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void remove(Item I)

  {
    for (int i=0; i<slotList.length; i++)
    if (slotList[i].contains(I))
      slotList[i].remove(I);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addSlot()

  {
    ItemSlot[] temp = new ItemSlot[slotList.length+1];
    for (int i=0; i<slotList.length; i++) temp[i] = slotList[i];
    temp[slotList.length] = new ItemSlot();
    slotList = temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ArrayList resize(int numSlots)

  {
    if (numSlots % 2 == 1) numSlots++;
    while (slotList.length < numSlots) addSlot();
    if (numSlots == slotList.length) return null;

    ArrayList truncatedItems = new ArrayList();
    ItemSlot[] temp = new ItemSlot[numSlots];

    for (int i=0; i<numSlots; i++) temp[i] = slotList[i];

    for (int i=numSlots; i<slotList.length; i++)
      truncatedItems.add(slotList[i].getItems());

    slotList = temp;

    return truncatedItems;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean contains(Item I)

  {
    for (int i=0; i<slotList.length; i++)
    if (slotList[i].contains(I)) return true;
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getDisplay()

  {
    if (type == TYPE_ROOM) return getRoomDisplay();
    if (type == TYPE_INVENTORY) return getInventoryDisplay();
    if (type == TYPE_CONTAINER) return getContainerDisplay();

    return "Unknown Type.";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getRoomDisplay()

  {
    String[] list = new String[slotList.length];
    for (int i=0; i<slotList.length; i++) list[i] = slotList[i].getDisplay(type);

    StringBuffer s = new StringBuffer();

    for (int i=0; i<list.length; i++)
    if (list[i].length() > 0)
      s.append("\r\n" + list[i]);

    return s.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getContainerDisplay()

  {
    String[] list = new String[slotList.length];
    for (int i=0; i<slotList.length; i++) list[i] = slotList[i].getDisplay(type);

    String border  = "  +-----------------------------------------------------------+#N";
    String border2 = "  +-----------------------------+-----------------------------+#N";
    String header  = "  |" + centerAlign(58, "#M" + myContainer.getName()) + " #N|";

    StringBuffer s = new StringBuffer();

    s.append(border + "\r\n");
    s.append(header + "\r\n");
    s.append(border2);

    boolean odd = true;

    for (int i=0; i<list.length; i++)

    {
      if (odd) s.append("\r\n  " + list[i]);
      else s.append(list[i] + "|\r\n" + border2);
      odd = !odd;
    }

    return s.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getInventoryDisplay()

  {
    String[] list = new String[slotList.length];
    for (int i=0; i<slotList.length; i++) list[i] = slotList[i].getDisplay(type);

    String border  = "  +-----------------------------------------------------------+#N";
    String header  = "  |                         #YInventory#N                         |#N";
    String border2 = "  +-----------------------------+-----------------------------+#N";

    StringBuffer s = new StringBuffer();

    s.append(border + "\r\n");
    s.append(header + "\r\n");
    s.append(border2);

    boolean odd = true;

    for (int i=0; i<list.length; i++)

    {
      if (odd) s.append("\r\n  " + list[i]);
      else s.append(list[i] + "|\r\n" + border2);
      odd = !odd;
    }

    return s.toString();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ArrayList getList()

  {
    ArrayList list = new ArrayList();
    for (int i=0; i<slotList.length; i++) list.addAll(slotList[i].getItems());
    return list;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ItemList replicate()

  {
    ItemList I = new ItemList(slotList.length, type);
    for (int i=0; i<slotList.length; i++) I.setSlot(i, slotList[i].replicate());
    return I;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("TYPE"); out.writeObject(new Integer(type));
      out.writeObject("LIST"); out.writeObject(slotList);
      out.writeObject("END SLOTLIST");
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

      while (!tag.equals("END SLOTLIST"))

      {
        tag = (String)in.readObject();
        if (tag.equals("LIST")) slotList = (ItemSlot[])in.readObject();
        else if (tag.equals("TYPE")) type = ((Integer)in.readObject()).intValue();
        else if (!tag.equals("END SLOTLIST")) in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}
