import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.lang.reflect.Array;
import java.nio.channels.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class AccessManager extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final long serialVersionUID = 100;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static AccessManager manager;
  
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
  
  private int maxConnectionsFromIP;

  private LinkedList siteBanList;
  private LinkedList siteBanExpirations;
  private LinkedList siteBanOnDate;
  private LinkedList siteBanReason;

  private LinkedList accountBanList;
  private LinkedList accountBanExpirations;
  private LinkedList accountBanOnDate;
  private LinkedList accountBanReason;

  private LinkedList charFreezeList;
  private LinkedList charFreezeExpirations;
  private LinkedList charFreezeOnDate;
  private LinkedList charFreezeReason;
  
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public AccessManager()

  {
    maxConnectionsFromIP = 3;
    
    siteBanList           = new LinkedList();
    siteBanExpirations    = new LinkedList();
    siteBanOnDate         = new LinkedList();
    siteBanReason         = new LinkedList();

    accountBanList        = new LinkedList();
    accountBanExpirations = new LinkedList();
    accountBanOnDate      = new LinkedList();
    accountBanReason      = new LinkedList();

    charFreezeList        = new LinkedList();
    charFreezeExpirations = new LinkedList();
    charFreezeOnDate      = new LinkedList();
    charFreezeReason      = new LinkedList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void init()

  {
    manager = FileManager.loadAccessManager();
    
    if (manager != null) return;
    
    manager = new AccessManager();
    FileManager.saveAccessManager(manager);
  }
  
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
  
  public void setMaxConnectionsFromIP(int i) { maxConnectionsFromIP = i;    }
  public int getMaxConnectionsFromIP()       { return maxConnectionsFromIP; }
  
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
  
  public boolean tooManyConnections(String ipAddress)
  
  {
    int count = 0;
    
    for (int i=0; i<ClientList.getSize(); i++)
    if ((ClientList.getClient(i)).getIPAddress().equals(ipAddress))
      count++;
      
    if (count >= maxConnectionsFromIP) return true;
    return false;
  }
  
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean isSiteBanned(String ipAddress)

  {
    int index = findSiteBan(ipAddress);
    if (index == -1) return false;

    long gameTime   = SystemTime.CALENDAR.getTimeInMillis();
    long expireTime = ((Long)siteBanExpirations.get(index)).longValue();
    long issued     = ((Long)siteBanOnDate.get(index)).longValue();

    if (expireTime == issued) return true;
    if (gameTime <= expireTime) return true;
    removeSiteBan(ipAddress);
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean isAccountBanned(Account account)

  {
    int index = findAccountBan(account.getUsername());
    if (index == -1) return false;

    long gameTime   = SystemTime.CALENDAR.getTimeInMillis();
    long expireTime = ((Long)accountBanExpirations.get(index)).longValue();
    long issued     = ((Long)accountBanOnDate.get(index)).longValue();

    if (expireTime == issued) return true;
    if (gameTime <= expireTime) return true;
    removeAccountBan(account);
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean isCharFrozen(Char character)

  {
    int index = findCharFreeze(character.getName());
    if (index == -1) return false;

    long gameTime   = SystemTime.CALENDAR.getTimeInMillis();
    long expireTime = ((Long)charFreezeExpirations.get(index)).longValue();
    long issued     = ((Long)charFreezeOnDate.get(index)).longValue();

    if (expireTime == issued) return true;
    if (gameTime <= expireTime) return true;
    removeCharFreeze(character);
    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void freezeCharDays(Char character, int days, String reason)

  {
    removeCharFreeze(character);

    long hours = days * 24;
    long minutes = hours * 60;
    long seconds = minutes * 60;
    long milliseconds = seconds * 1000;
    long currentTime = SystemTime.CALENDAR.getTimeInMillis();

    Long issued = new Long(currentTime);
    Long expire = new Long(milliseconds + currentTime);

    charFreezeList.add(character.getName());
    charFreezeExpirations.add(expire);
    charFreezeOnDate.add(issued);
    charFreezeReason.add(reason);

    FileManager.saveAccessManager(manager);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void banAccountDays(Account account, int days, String reason)

  {
    removeAccountBan(account);

    long hours = days * 24;
    long minutes = hours * 60;
    long seconds = minutes * 60;
    long milliseconds = seconds * 1000;
    long currentTime = SystemTime.CALENDAR.getTimeInMillis();

    Long issued = new Long(currentTime);
    Long expire = new Long(milliseconds + currentTime);

    accountBanList.add(account.getUsername());
    accountBanExpirations.add(expire);
    accountBanOnDate.add(issued);
    accountBanReason.add(reason);

    FileManager.saveAccessManager(manager);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void banSiteDays(String ipAddress, int days, String reason)

  {
    removeSiteBan(ipAddress);

    long hours = days * 24;
    long minutes = hours * 60;
    long seconds = minutes * 60;
    long milliseconds = seconds * 1000;
    long currentTime = SystemTime.CALENDAR.getTimeInMillis();

    Long issued = new Long(currentTime);
    Long expire = new Long(milliseconds + currentTime);

    siteBanList.add(ipAddress);
    siteBanExpirations.add(expire);
    siteBanOnDate.add(issued);
    siteBanReason.add(reason);

    FileManager.saveAccessManager(manager);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean removeCharFreeze(Char character)

  {
    int index = findCharFreeze(character.getName());

    if (index == -1) return false;

    charFreezeList.remove(index);
    charFreezeExpirations.remove(index);
    charFreezeOnDate.remove(index);
    charFreezeReason.remove(index);

    FileManager.saveAccessManager(manager);

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean removeAccountBan(Account account)

  {
    int index = findAccountBan(account.getUsername());

    if (index == -1) return false;

    accountBanList.remove(index);
    accountBanExpirations.remove(index);
    accountBanOnDate.remove(index);
    accountBanReason.remove(index);

    FileManager.saveAccessManager(manager);

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean removeSiteBan(String ipAddress)

  {
    int index = findSiteBan(ipAddress);

    if (index == -1) return false;

    siteBanList.remove(index);
    siteBanExpirations.remove(index);
    siteBanOnDate.remove(index);
    siteBanReason.remove(index);

    FileManager.saveAccessManager(manager);

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int findCharFreeze(String name)

  {
    for (int i=0; i<charFreezeList.size(); i++)
    if (name.equals(charFreezeList.get(i)))
      return i;

    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int findAccountBan(String name)

  {
    for (int i=0; i<accountBanList.size(); i++)
    if (name.equals(accountBanList.get(i)))
      return i;

    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int findSiteBan(String ipAddress)

  {
    for (int i=0; i<siteBanList.size(); i++)
    if (ipAddress.equals(siteBanList.get(i)))
      return i;

    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getFreezeList(String rem)

  {
    boolean all = false;
    boolean found = false;
    ArrayList list = new ArrayList();

    if (rem.equals("all")) all = true;

    if (charFreezeList.size() > 0)

    {
      list.add("#n[ Character Name  ] [ Issued   ] [ Expires  ] [ Reason                     ]#N");
      list.add("-");
    }

    else list.add("There are no frozen characters.");

    for (int i=0; i<charFreezeList.size(); i++)
    if ((all) || (((String)charFreezeList.get(i)).toLowerCase().indexOf(rem) != -1))

    {
      String s;
      String name = (String)charFreezeList.get(i);
      String reason = (String)charFreezeReason.get(i);
      long issued = ((Long)charFreezeOnDate.get(i)).longValue();
      long expire = ((Long)charFreezeExpirations.get(i)).longValue();

      String s1 = SystemTime.SDATE.format(new Date(issued));
      String s2 = SystemTime.SDATE.format(new Date(expire));

      if (issued == expire) s2 = " ------ ";

      s  = "#N[#C " + String.format("%1$-15s", name) + " #N] [#n " + s1 + " #N] ";
      s += "[#n " + s2 + " #N] [ " + String.format("%1$-26.26s", reason) + " ]";

      list.add(s);
      found = true;
    }

    if ((!all) && (!found) && (charFreezeList.size() > 0)) list.add("No matches.");

    return boxify(list, 0);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getBlockList(String rem)

  {
    boolean all = false;
    boolean found = false;
    ArrayList list = new ArrayList();

    if (rem.equals("all")) all = true;

    if (accountBanList.size() > 0)

    {
      list.add("#n[ Account Name    ] [ Issued   ] [ Expires  ] [ Reason                     ]#N");
      list.add("-");
    }

    else list.add("There are no accounts that are blocked.");

    for (int i=0; i<accountBanList.size(); i++)
    if ((all) || (((String)accountBanList.get(i)).indexOf(rem) != -1))

    {
      String s;
      String name = (String)accountBanList.get(i);
      String reason = (String)accountBanReason.get(i);
      long issued = ((Long)accountBanOnDate.get(i)).longValue();
      long expire = ((Long)accountBanExpirations.get(i)).longValue();

      String s1 = SystemTime.SDATE.format(new Date(issued));
      String s2 = SystemTime.SDATE.format(new Date(expire));

      if (issued == expire) s2 = " ------ ";

      s  = "#N[#C " + String.format("%1$-15s", name) + " #N] [#n " + s1 + " #N] ";
      s += "[#n " + s2 + " #N] [ " + String.format("%1$-26.26s", reason) + " ]";

      list.add(s);
      found = true;
    }

    if ((!all) && (!found) && (accountBanList.size() > 0)) list.add("No matches.");

    return boxify(list, 0);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getBanList(String rem)

  {
    boolean all = false;
    boolean found = false;
    ArrayList list = new ArrayList();

    if (rem.equals("all")) all = true;

    if (siteBanList.size() > 0)

    {
      list.add("#n[ IP Address      ] [ Issued   ] [ Expires  ] [ Reason                     ]#N");
      list.add("-");
    }

    else list.add("There are no sites that are banned.");

    for (int i=0; i<siteBanList.size(); i++)
    if ((all) || (((String)siteBanList.get(i)).indexOf(rem) != -1))

    {
      String s;
      String ip = (String)siteBanList.get(i);
      String reason = (String)siteBanReason.get(i);
      long issued = ((Long)siteBanOnDate.get(i)).longValue();
      long expire = ((Long)siteBanExpirations.get(i)).longValue();

      String s1 = SystemTime.SDATE.format(new Date(issued));
      String s2 = SystemTime.SDATE.format(new Date(expire));

      if (issued == expire) s2 = " ------ ";

      s  = "#N[#C " + String.format("%1$-15s", ip) + " #N] [#n " + s1 + " #N] ";
      s += "[#n " + s2 + " #N] [ " + String.format("%1$-26.26s", reason) + " ]";

      list.add(s);
      found = true;
    }

    if ((!all) && (!found) && (siteBanList.size() > 0)) list.add("No matches.");

    return boxify(list, 0);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("SITEBANLIST");    out.writeObject(siteBanList);
      out.writeObject("SITEBANEXPIRES"); out.writeObject(siteBanExpirations);
      out.writeObject("SITEBANDATES");   out.writeObject(siteBanOnDate);
      out.writeObject("SITEBANREASONS"); out.writeObject(siteBanReason);
      out.writeObject("ACCBANLIST");     out.writeObject(accountBanList);
      out.writeObject("ACCBANEXPIRES");  out.writeObject(accountBanExpirations);
      out.writeObject("ACCBANDATES");    out.writeObject(accountBanOnDate);
      out.writeObject("ACCBANREASONS");  out.writeObject(accountBanReason);
      out.writeObject("CHRFRZLIST");     out.writeObject(charFreezeList);
      out.writeObject("CHRFRZEXPIRES");  out.writeObject(charFreezeExpirations);
      out.writeObject("CHRFRZDATES");    out.writeObject(charFreezeOnDate);
      out.writeObject("CHRFRZREASONS");  out.writeObject(charFreezeReason);
      out.writeObject("MAXCONFROMIP");   out.writeObject(new Integer(maxConnectionsFromIP));
      out.writeObject("ACCESSMANAGER END");
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

      while (!tag.equals("ACCESSMANAGER END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("SITEBANLIST"))         siteBanList = (LinkedList)in.readObject();
        else if (tag.equals("SITEBANEXPIRES")) siteBanExpirations = (LinkedList)in.readObject();
        else if (tag.equals("SITEBANDATES"))   siteBanOnDate = (LinkedList)in.readObject();
        else if (tag.equals("SITEBANREASONS")) siteBanReason = (LinkedList)in.readObject();
        else if (tag.equals("ACCBANLIST"))     accountBanList = (LinkedList)in.readObject();
        else if (tag.equals("ACCBANEXPIRES"))  accountBanExpirations = (LinkedList)in.readObject();
        else if (tag.equals("ACCBANDATES"))    accountBanOnDate = (LinkedList)in.readObject();
        else if (tag.equals("ACCBANREASONS"))  accountBanReason = (LinkedList)in.readObject();
        else if (tag.equals("CHRFRZLIST"))     charFreezeList = (LinkedList)in.readObject();
        else if (tag.equals("CHRFRZEXPIRES"))  charFreezeExpirations = (LinkedList)in.readObject();
        else if (tag.equals("CHRFRZDATES"))    charFreezeOnDate = (LinkedList)in.readObject();
        else if (tag.equals("CHRFRZREASONS"))  charFreezeReason = (LinkedList)in.readObject();
        else if (tag.equals("MAXCONFROMIP"))   maxConnectionsFromIP = ((Integer)in.readObject()).intValue();
        else if (!tag.equals("ACCESSMANAGER END")) in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}
