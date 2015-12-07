import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

class BankBranch implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 81;

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  private int id;
  private int roomID;
  private ArrayList accounts;

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public int getChainID()                  { return id;                               }
  public int getRoomID()                   { return roomID;                           }
  public BankAccount getAccount(int i)     { return (BankAccount) accounts.get(i);    }
  public BankAccount removeAccount(int i)  { return (BankAccount) accounts.remove(i); }
  public void addOneAccount(BankAccount b) { accounts.add(b);                         }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public BankBranch()

  {
    id = -1;
    accounts = new ArrayList();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public BankBranch(int chainID, int RoomID)

  {
    id = chainID;
    roomID = RoomID;
    accounts = new ArrayList();
    World.getBank(id).updateAccounts(this);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public boolean requestAccount(String ownerName)

  {
    return World.getBank(id).openAccount(ownerName);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public BankAccount openAccount(String ownerName)

  {
    BankAccount BA = new BankAccount(ownerName, roomID, -1);
    accounts.add(BA);
    return BA;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void closeAccount(String ownerName)

  {
    for (int i=accounts.size()-1; i>=0; i--)
    if (getAccount(i).getName().equalsIgnoreCase(ownerName))
      removeAccount(i);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public BankAccount getAccount(String ownerName)

  {
    for (int i=0; i<accounts.size(); i++)
    if (getAccount(i).getName().equalsIgnoreCase(ownerName))
      return getAccount(i);

    return null;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public String deposit(Entity ENT, Entity Banker, String str)

  {
    if (World.getBank(id).busy(ENT.getName()))
      return "We are still processing your last transaction, one moment please.";

    BankAccount A = null;

    if (str.length() <= 0)
      return "You don't have anything like that.";

    for (int i=0; i<accounts.size(); i++)
    if (getAccount(i).getName().equalsIgnoreCase(ENT.getName()))
      A = getAccount(i);

    if (Utility.number(str))

    {
      int amount = Integer.parseInt(str);

      if (amount > ENT.getGold())
        return "You don't have that much gold.";

      if (A == null) {
        World.getBank(id).openAccount(ENT.getName());
        A = getAccount(ENT.getName()); }

      A = World.getBank(id).getAccount(ENT.getName());
      A.deposit(amount);
      ENT.setGold(ENT.getGold() - amount);
      ENT.castChar().save();
      World.getBank(id).save(ENT.getName());

      ENT.echo("You give " + Banker.getName() + " some gold.");
      Banker.getRoom().xecho(Banker, ENT, ENT.getName() + " gives " +
      Banker.getName() + " some gold.", Utility.ECHO_AWAKE);

      return "You have deposited " + amount + " gold.";
    }

    Item I = ENT.removeItemInventory(str);

    if (I == null)
      return "You don't have anything like that.";

    if (A == null) {
      World.getBank(id).openAccount(ENT.getName());
      A = getAccount(ENT.getName()); }

    A.deposit(I);
    ENT.castChar().save();
    World.getBank(id).save(ENT.getName());

    ENT.echo("You give " + Banker.getName() + " " + I.getLName() + ".");
    Banker.getRoom().xecho(Banker, ENT, ENT.getName() + " gives " +
    Banker.getName() + " " + I.getLName() + ".", Utility.ECHO_AWAKE);

    return "You have deposited " + I.getLName() + "#N.";
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public String withdraw(Entity ENT, Entity Banker, String str)

  {
    if (World.getBank(id).busy(ENT.getName()))
      return "We are still processing your last transaction, one moment please.";

    BankAccount A = null;

    if (str.length() <= 0)
      return "You don't have anything like that deposited.";

    for (int i=0; i<accounts.size(); i++)
    if (getAccount(i).getName().equalsIgnoreCase(ENT.getName()))
      A = getAccount(i);

    if (A == null)
      return "You don't have an account open here.";

    if (Utility.number(str))

    {
      A = World.getBank(id).getAccount(ENT.getName());

      int amount = Integer.parseInt(str);

      if (amount > A.getGold())
        return "You don't have that much gold in your account.";

      A.withdraw(amount);
      ENT.setGold(ENT.getGold() + amount);
      World.getBank(id).tryCloseAccount(ENT.getName());
      ENT.castChar().save();
      World.getBank(id).save(ENT.getName());

      ENT.echo(Banker.getName() + " gives you some gold.");
      Banker.getRoom().xecho(Banker, ENT, Banker.getName() + " gives " +
      ENT.getName() + " some gold.", Utility.ECHO_AWAKE);

      return "You have withdrawn " + amount + " gold.";
    }

    Item I = A.withdraw(str);

    if (I == null)
      return "You don't have anything like that deposited.";

    Create.item(I, ENT, Transfer.IWITHDRAW);
    World.getBank(id).tryCloseAccount(ENT.getName());
    ENT.castChar().save();
    World.getBank(id).save(ENT.getName());

    ENT.echo(Banker.getName() + " gives you " + I.getLName() + ".");
    Banker.getRoom().xecho(Banker, ENT, Banker.getName() + " gives " +
    ENT.getName() + " " + I.getLName() + ".", Utility.ECHO_AWAKE);

    return "You have withdrawn " + I.getLName() + "#N.";
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public String getAccountInfo(String ownerName)

  {
    int width = 16;
    String info = "";
    BankAccount A = null;

    for (int i=0; i<accounts.size(); i++)
    if (getAccount(i).getName().equalsIgnoreCase(ownerName))
      A = getAccount(i);

    if (A == null) return "";

    BankAccount C = World.getBank(id).getAccount(ownerName);

    String bName = World.getBank(id).getName();
    String gInfo = C.getAccountGold();
    String iInfo = A.getAccountItems();

    String[] SS = iInfo.split("\r\n");
    int sSize = Array.getLength(SS);

    int iLen = Merchant.getVisibleWidth(SS, sSize) + 2;
    int bLen = Utility.visibleSize(bName);
    int gLen = Utility.visibleSize(gInfo) + 2;
    if (bLen > width) width = bLen;
    if (gLen > width) width = gLen;
    if (iLen > width) width = iLen;

    String border = "  #n+-" + Utility.xchars(width,"-") + "-+#N";
    String xs = Utility.xchars(width-16," ");

    info += "\r\n" + border + "\r\n  #n|#N " + bName + Utility.xchars(width-bLen," ");
    info += " #n|#N\r\n" + border + "\r\n  #n|#N Account Balance:" + xs;
    info += " #n|#N\r\n  #n|#N   " + gInfo + Utility.xchars(width-gLen," ");
    info += " #n|#N\r\n" + border + "\r\n  | Deposited Items:" + xs + " #n|#N\r\n";

    for (int i=0; i<sSize; i++)
    if (Utility.visibleSize(SS[i]) > 0)

    {
      int numSpaces = width - Utility.visibleSize(SS[i]) - 2;
      info += "  #n|#N   " + SS[i];
      info += Utility.xchars(numSpaces," ") + " #n|#N\r\n";
    }

    info += border;

    return info;
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ID");     out.writeObject(new Integer(id));
      out.writeObject("ROOMID"); out.writeObject(new Integer(roomID));
      out.writeObject("BANKBRANCH END");
    }

    catch (Exception e) { System.out.println(e); }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("BANKBRANCH END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("ID"))          id = ((Integer)in.readObject()).intValue();
        else if (tag.equals("ROOMID")) roomID = ((Integer)in.readObject()).intValue();
        else if (!tag.equals("BANKBRANCH END")) in.readObject();
      }
    }

    catch (Exception e) { System.out.println(e); }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////
}