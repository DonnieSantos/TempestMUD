import java.util.*;
import java.io.*;

class BankAccount implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 82;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int roomID;
  private int bankID;
  private String owner;
  private ArrayList items;
  private int gold;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public BankAccount()

  {
    owner = "Generic_Owner";
    items = new ArrayList();
    gold  = 0;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public BankAccount(String ownerName, int RID, int BID)

  {
    roomID = RID;
    bankID = BID;
    owner = ownerName;
    items = new ArrayList();
    gold  = 0;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void deposit(int amount)   { gold = gold + amount;          }
  public void withdraw(int amount)  { gold = gold - amount;          }
  public void deposit(Item I)       { ItemUse.addItemBack(items, I); }
  public Item getItem(int i)        { return (Item) items.get(i);    }
  public Item removeItem(int i)     { return (Item) items.remove(i); }
  public String getName()           { return owner;                  }
  public int getGold()              { return gold;                   }
  public ArrayList getAllItems()    { return items;                  }
  public int getBankID()            { return bankID;                 }
  public int getRoomID()            { return roomID;                 }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Item withdraw(String itemName)

  {
    for (int i=0; i<items.size(); i++)
    if (Utility.abbreviation(itemName, Utility.removeColors(getItem(i).getName())))
      return removeItem(i);

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean isEmpty()

  {
    if ((gold <= 0) && (items.size() <= 0)) return true;
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getAccountGold()

  {
    return "#y" + Utility.expCommas(""+gold) + "#N Gold.";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getAccountItems()

  {
    Stacker stacker = new Stacker();

    for (int i=0; i<items.size(); i++)
      stacker.add(getItem(i).getName());

    if (stacker.getSize() == 0) return "None";

    return stacker.itemStack();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean canFit(Item I)

  {
    // !!! GAH
    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ROOMID"); out.writeObject(new Integer(roomID));
      out.writeObject("BANKID"); out.writeObject(new Integer(bankID));
      out.writeObject("OWNER");  out.writeObject(owner);
      out.writeObject("ITEMS");  out.writeObject(items);
      out.writeObject("GOLD");   out.writeObject(new Integer(gold));
      out.writeObject("BANKACCOUNT END");
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

      while (!tag.equals("BANKACCOUNT END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("ROOMID"))        roomID = ((Integer)in.readObject()).intValue();
        else if (tag.equals("BANKID"))   bankID = ((Integer)in.readObject()).intValue();
        else if (tag.equals("OWNER"))    owner  = (String)in.readObject();
        else if (tag.equals("ITEMS"))    items  = (ArrayList)in.readObject();
        else if (tag.equals("GOLD"))     gold   = ((Integer)in.readObject()).intValue();
        else if (!tag.equals("BANKACCOUNT END")) in.readObject();
      }
    }

    catch (Exception e) { System.out.println(e); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}