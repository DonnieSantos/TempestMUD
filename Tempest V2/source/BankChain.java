import java.util.*;
import java.io.*;

class BankChain implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 80;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int id;
  private String name;
  private ArrayList branches;
  private ArrayList memberNames;
  private ArrayList accounts;
  private ArrayList transactionList;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getID()                        { return id;                           }
  public String getName()                   { return name;                         }
  public BankBranch getBranch(int i)        { return (BankBranch) branches.get(i); }
  public String getMemberName(int i)        { return (String) memberNames.get(i);  }
  public void addOneMember(String s)        { memberNames.add(s);                  }
  public void addOneAccount(BankAccount BA) { accounts.add(BA);                    }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public BankChain()

  {
    id = -1;
    name = "Generic Bank";
    branches = new ArrayList();
    memberNames = new ArrayList();
    accounts = new ArrayList();
    transactionList = new ArrayList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public BankChain(String chainName, int ID)

  {
    id = ID;
    name = chainName;
    branches = new ArrayList();
    memberNames = new ArrayList();
    accounts = new ArrayList();
    transactionList = new ArrayList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void save(String accountName)

  {
    addBusy(accountName);
    WriteThread.addObject(getAllAccounts(accountName));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addBranch(BankBranch BB)

  {
    branches.add(BB);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeBranch(BankBranch BB)

  {
    for (int i=branches.size()-1; i>=0; i--)
    if (getBranch(i) == BB)
      branches.remove(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean openAccount(String ownerName)

  {
    for (int i=0; i<memberNames.size(); i++)
    if (getMemberName(i).equalsIgnoreCase(ownerName))
      return false;

    BankAccount BA = new BankAccount(ownerName, -1, id);

    memberNames.add(ownerName);
    accounts.add(BA);

    for (int i=0; i<branches.size(); i++)
      getBranch(i).openAccount(ownerName);

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void tryCloseAccount(String ownerName)

  {
    boolean empty = true;

    BankAccount primary = getAccount(ownerName);

    if ((primary != null) && (!primary.isEmpty())) empty = false;

    if (empty)

    {
      for (int i=0; i<branches.size(); i++)

      {
        BankBranch BB = getBranch(i);
        BankAccount BA = BB.getAccount(ownerName);
        if (!BA.isEmpty()) empty = false;
      }
    }

    if (empty) closeAccount(ownerName);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void closeAccount(String ownerName)

  {
    for (int i=0; i<branches.size(); i++)
      getBranch(i).closeAccount(ownerName);

    for (int i=0; i<memberNames.size(); i++)
    if (getMemberName(i).equalsIgnoreCase(ownerName)) {
      memberNames.remove(i);
      accounts.remove(i); }

    WriteThread.msg("deletebankacct " + ownerName, this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void updateAccounts(BankBranch BB)

  {
    for (int i=0; i<memberNames.size(); i++)
      BB.openAccount(getMemberName(i));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public BankAccount getAccount(String ownerName)

  {
    for (int i=0; i<accounts.size(); i++)

    {
      BankAccount BA = (BankAccount) accounts.get(i);
      if (BA.getName().equalsIgnoreCase(ownerName))
        return BA;
    }

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ArrayList getAllAccounts(String ownerName)

  {
    ArrayList A = new ArrayList();

    BankAccount BA = getAccount(ownerName);

    if (BA == null) {
      openAccount(ownerName);
      BA = getAccount(ownerName); }

    A.add(BA);   

    for (int i=0; i<branches.size(); i++)
      A.add(getBranch(i).getAccount(ownerName));

    return A;    
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void distributeAccounts(ArrayList allAccounts)

  {
    String ownerName = ((BankAccount)allAccounts.get(0)).getName();

    for (int i=0; i<allAccounts.size(); i++)

    {
      BankAccount BA = (BankAccount) allAccounts.get(i);

      int bid = BA.getBankID();
      int rid = BA.getRoomID();

      if (bid != -1)

      {
        addOneMember(BA.getName());
        addOneAccount(BA);
      }

      else if (rid != -1)

      {
        BankBranch BB = null;
        Room R = World.getRoom(rid);
        if (R != null) BB = R.getBankBranch();

        if ((BB != null) && (BB.getChainID() == id))
          BB.addOneAccount(BA);
      }
    }

    ensureAccounts(ownerName);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void ensureAccounts(String ownerName)

  {
    for (int i=0; i<branches.size(); i++)

    {
      BankBranch BB = getBranch(i);
      BankAccount BA = BB.getAccount(ownerName);
      if (BA == null) BB.openAccount(ownerName);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean busy(String name)

  {
    synchronized(transactionList)
   
    {
      for (int i=0; i<transactionList.size(); i++)
      if (name.equalsIgnoreCase((String)transactionList.get(i)))
        return true;

      return false;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addBusy(String name)

  {
    synchronized(transactionList)

    {
      boolean found = false;

      for (int i=0; i<transactionList.size(); i++)
      if (name.equalsIgnoreCase((String)transactionList.get(i)))
        found = true;

      if (!found) transactionList.add(name);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeBusy(String name)

  {
    synchronized(transactionList)

    {
      for (int i=transactionList.size()-1; i>=0; i--)
      if (name.equalsIgnoreCase((String)transactionList.get(i)))
        transactionList.remove(i);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ID");   out.writeObject(new Integer(id));
      out.writeObject("NAME"); out.writeObject(name);
      out.writeObject("BANKCHAIN END");
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

      while (!tag.equals("BANKCHAIN END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("ID"))            id = ((Integer)in.readObject()).intValue();
        else if (tag.equals("NAME"))     name = (String)in.readObject();
        else if (!tag.equals("BANKCHAIN END")) in.readObject();
      }
    }

    catch (Exception e) { System.out.println(e); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}