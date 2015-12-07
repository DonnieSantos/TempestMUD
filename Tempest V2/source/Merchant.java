import java.io.*;
import java.util.*;

public class Merchant extends Utility

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int MERCHANT_SOLD_OUT        =  0;
  public static final int MERCHANT_NEED_GOLD       =  1;
  public static final int MERCHANT_NEED_LEVEL      =  2;
  public static final int MERCHANT_CANT_CARRY      =  3;
  public static final int MERCHANT_ITEM_BOUGHT     =  4;
  public static final int MERCHANT_SELL_WORTHLESS  =  5;
  public static final int MERCHANT_ITEM_SOLD       =  6;
  public static final int MERCHANT_ITEM_NODROP     =  7;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static Item findItemFromSay(Mobile MERCHANT, String str)

  {
    ArrayList MERCHANT_LIST = convertList(MERCHANT);
    int size = MERCHANT_LIST.size();
    String itemName;

    str = removeColors(str.toLowerCase());

    for (int i=0; i<size; i++)

    {
      Item I = ((Item)MERCHANT_LIST.get(i));
      itemName = removeColors(I.getName()).toLowerCase();
      if (str.indexOf(itemName) != -1) return I;
    }

    return null;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static Item findItemExact(Entity CUSTOMER, String str)

  {
    ArrayList INVENTORY = CUSTOMER.getInventory().getList();
    int size = INVENTORY.size();
    String itemName;

    str = removeColors(str.toLowerCase());

    for (int i=0; i<size; i++)

    {
      Item I = ((Item)INVENTORY.get(i));
      itemName = removeColors(I.getName()).toLowerCase();
      if (str.indexOf(itemName) != -1) return I;
    }

    return null;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static int sell(Mobile merchant, Entity customer, Item I)

  {
    int error = Destroy.item(I, customer, ISELL);

    switch (error)

    {
      case ER_INOVALUE: return MERCHANT_SELL_WORTHLESS;
      case ER_INODROP:  return MERCHANT_ITEM_NODROP;
    }

    String msg = customer.getName() + " gives " + merchant.getName() + " " + I.getLName() + ".";
    customer.setGold(customer.getGold() + I.getWorth());
    customer.getRoom().xecho(merchant, customer, msg, ECHO_AWAKE);
    customer.echo("You give " + I.getLName() + " to " + merchant.getName() + ".");

    return MERCHANT_ITEM_SOLD;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static int buy(Mobile merchant, Entity customer, Item I)

  {
    if (merchant.getSellList().size() == 0) return MERCHANT_SOLD_OUT;

    int error = Create.item(I, customer, IBUY);

    switch (error)

    {
      case ER_EINVFULL:       return MERCHANT_CANT_CARRY;
      case ER_ILEVEL:         return MERCHANT_NEED_LEVEL;
      case ER_INOTENOUGHGOLD: return MERCHANT_NEED_GOLD;
    }

    String msg = merchant.getName() + " gives " + customer.getName() + " " + I.getLName() + ".";
    customer.setGold(customer.getGold() - I.getWorth());
    customer.echo(merchant.getName() + " gives you " + I.getLName() + ".");
    merchant.getRoom().xecho(merchant, customer, msg, ECHO_AWAKE);

    return MERCHANT_ITEM_BOUGHT;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static ArrayList convertList(Mobile MERCHANT)

  {
    int size = MERCHANT.getNumSells();
    ArrayList newList = new ArrayList(size);

    for (int i=0; i<size; i++)

    {
      Item ITEM = World.getItem(MERCHANT.getSellItem(i));
      ITEM = ITEM.replicate("Sold by " + MERCHANT.getName() + ".");
      newList.add(ITEM);
    }

    return newList;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String findDepositString(Entity ENT, String str)

  {
    int position = str.toLowerCase().indexOf("deposit");
    String strInput = last(str.substring(position)).toLowerCase();
    String backup = new String(strInput);

    while (backup.length() > 0) {
      if (number(first(backup))) return first(backup);
      backup = last(backup); }

    Item theItem = null;
    ArrayList inventory = ENT.getInventory().getList();

    for (int i=0; i<inventory.size(); i++)

    {
      Item I = (Item) inventory.get(i);
      String S = Utility.removeColors(I.getName().toLowerCase());
      if (strInput.indexOf(S) != -1) theItem = I;
    }

    if (theItem == null) return "";
    return removeColors(theItem.getName());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static String findWithdrawString(BankAccount A, String str)

  {
    if (A == null) return "";

    int position = str.toLowerCase().indexOf("withdraw");
    String strInput = last(str.substring(position)).toLowerCase();
    String backup = new String(strInput);

    while (backup.length() > 0) {
      if (number(first(backup))) return first(backup);
      backup = last(backup); }

    Item theItem = null;
    ArrayList items = A.getAllItems();

    for (int i=0; i<items.size(); i++)

    {
      Item I = (Item) items.get(i);
      String S = Utility.removeColors(I.getName().toLowerCase());
      if (strInput.indexOf(S) != -1) theItem = I;
    }

    if (theItem == null) return "";
    return removeColors(theItem.getName());
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static int getVisibleWidth(String[] SS, int sSize)

  {
    int maxSize = 0;

    for (int i=0; i<sSize; i++)

    {
      int vs = visibleSize(SS[i]);
      if (vs > maxSize) maxSize = vs;
    }

    return maxSize;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}