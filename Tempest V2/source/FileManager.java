import java.io.*;
import java.util.*;
import java.lang.reflect.Array;
import java.nio.channels.*;

public class FileManager extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int FM_CREATION      = 0;
  public static final int FM_EXISTS        = 1;
  public static final int FM_NOTEXISTS     = 2;
  public static final String FM_EXT        = ".dat";
  public static final String FM_PATH       = ".//pfiles//";
  public static final String ACCT_PATH     = ".//accounts//";
  public static final String WORLD_PATH    = ".//world//";
  public static final String CLAN_PATH     = ".//clans//";
  public static final String RELIGION_PATH = ".//religions//";
  public static final String BOARD_PATH    = ".//boards//";
  public static final String BANK_PATH     = ".//banks//";
  public static final String ACTION_PATH   = ".//actions//";
  public static final String ACCESS_PATH   = ".//access//";
  public static final String LOG_PATH      = ".//logs//";
  public static final String HELP_PATH     = ".//helpfiles//";

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ObjectOutput openOutputFile(String name) throws Exception

  {
    File FILE = new File(name);
    FileOutputStream FOS = new FileOutputStream(FILE);
    BufferedOutputStream BOS = new BufferedOutputStream(FOS);
    return new ObjectOutputStream(BOS);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ObjectInput openInputFile(String name) throws Exception

  {
    File FILE = new File(name);
    FileInputStream FIS = new FileInputStream(FILE);
    BufferedInputStream BIS = new BufferedInputStream(FIS);
    return new ObjectInputStream(BIS);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadAllAccounts()

  {
    File temp = new File(ACCT_PATH);
    File[] fileList = temp.listFiles();
    int numFiles = fileList.length;
    String sp = xchars(tenSpace(numFiles,5).length(),".") + " ";

    System.out.print("Loading " + numFiles + " Accounts......." + sp);

    for (int i=0; i<numFiles; i++)

    {
      String accountName = fileList[i].getName().replaceAll(FM_EXT,"");
      World.insertAccount(loadAccountFile(accountName));
    }

    System.out.println("Done.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList getAllPlayerNames()

  {
    ArrayList allPlayerNames = new ArrayList();

    File temp = new File(FM_PATH);
    File[] fileList = temp.listFiles();
    int numFiles = fileList.length;

    for (int i=0; i<numFiles; i++)

    {
      String charName = fileList[i].getName().replaceAll(FM_EXT,"");
      allPlayerNames.add(charName);
    }

    return allPlayerNames;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadAllPlayers()

  {
    File temp = new File(FM_PATH);
    File[] fileList = temp.listFiles();
    int numFiles = fileList.length;
    String sp = xchars(tenSpace(numFiles,5).length(),".") + " ";

    System.out.print("Loading " + numFiles + " Characters....." + sp);

    for (int i=0; i<numFiles; i++)

    {
      String charName = fileList[i].getName().replaceAll(FM_EXT,"");
      World.insertCharacter(loadCharacterFile(charName));
    }

    System.out.println("Done.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadActions()

  {
    try

    {
      ObjectInput in = openInputFile(ACTION_PATH + "actions.dat");
      ArrayList A = (ArrayList)in.readObject();
      in.close();

      for (int i=0; i<A.size(); i++) World.setAction((Action)A.get(i));
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveActions()

  {
    try

    {
      ArrayList A = new ArrayList();

      for (int i=0; i<World.getActionSize(); i++)
      if (World.getAction(i) != null)
        A.add(World.getAction(i));

      ObjectOutput out = openOutputFile(ACTION_PATH + "actions.dat");
      out.writeObject(A);
      out.close();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static synchronized void saveCharacter(Entity saveChar)

  {
    if (Server.shutdown)

    {
      SystemLog.addLog("Writing " + saveChar.getName() + ".");
      System.out.println("Writing " + saveChar.getName() + ".");
    }

    try

    {
      ObjectOutput out = openOutputFile(FM_PATH + saveChar.getName() + FM_EXT);
      out.writeObject(saveChar);
      out.close();
    }

    catch (Exception e) { System.out.println(e); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveAccount(Account A)

  {
    try

    {
      if (A.getUsername().equals("VOID"))
        throw new Exception("!!!VOID ACCOUNT DETECTED!!!");

      ObjectOutput out = openOutputFile(ACCT_PATH + A.getUsername() + FM_EXT);
      A.writeExternal(out);
      out.close();
    }

    catch (Exception e)

    {
      SystemLog.addLog(e.toString());
      e.printStackTrace();
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean accountExists(String acctName)

  {
    File afile = new File(ACCT_PATH + acctName + FM_EXT);
    if (!afile.exists()) return false;
    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Entity loadCharacterFile(String charName)

  {
    try

    {
      ObjectInput in = openInputFile(FM_PATH + charName + FM_EXT);
      Entity E = (Entity)in.readObject();
      in.close();
      return E;
    }

    catch (Exception e) { System.out.println(e); }

    return new Warrior();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Account loadAccountFile(String acctName)

  {
    Account newAcct = new Account();

    try

    {
      ObjectInput in = openInputFile(ACCT_PATH + acctName + FM_EXT);
      newAcct.readExternal(in);
      in.close();
    }

    catch (Exception e) { System.out.println(e); }

    return newAcct;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void createPlaceHolder(String charName)

  {
    Entity placeHolder = new Warrior();
    placeHolder.setName(charName);
    placeHolder.setRoom(100);
    World.insertCharacter(placeHolder);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void createAccountHolder(String acctName)

  {
    Account placeHolder = new Account();
    placeHolder.setUsername(acctName);
    placeHolder.setPlaceHolder(true);
    World.insertAccount(placeHolder);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean deleteCharacter(Entity ENT)

  {
    try

    {
      File pfile = new File(FM_PATH + ENT.getName() + FM_EXT);
      if (pfile.exists()) pfile.delete();
      return true;
    }

    catch (Exception e) { System.out.println(e); return false; }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean deleteCharacter(String fname)

  {
    try

    {
      File pfile = new File(FM_PATH + properName(fname) + FM_EXT);
      if (pfile.exists()) pfile.delete();
      return true;
    }

    catch (Exception e) { System.out.println(e); return false; }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean deleteAccount(String fname)

  {
    try

    {
      File afile = new File(ACCT_PATH + fname + FM_EXT);
      if (afile.exists()) afile.delete();
      return true;
    }

    catch (Exception e) { System.out.println(e); return false; }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void deletePlaceHolder(String charName)

  {
    World.removeCharacter(charName);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void deleteAccountHolder(String acctName)

  {
    World.removeAccount(acctName);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadBankAccounts()

  {
    for(int i=0; i<World.getNumBanks(); i++)
    if (World.getBank(i) != null)

    {
      BankChain BC = World.getBank(i);
      String bankDir = BANK_PATH + "//" + removeColors(BC.getName()) + "//";

      File temp = new File(bankDir);
      File[] accountList = temp.listFiles();
      int numFiles = Array.getLength(accountList);

      for (int j=0; j<numFiles; j++)
        loadBankAccount(BC, accountList[j]);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadBankAccount(BankChain BC, File accountFile)

  {
    try

    {
      FileInputStream FIS = new FileInputStream(accountFile);
      BufferedInputStream BIS = new BufferedInputStream(FIS);
      ObjectInputStream inputStream = new ObjectInputStream(BIS);
      ArrayList A = (ArrayList)inputStream.readObject();
      BC.distributeAccounts(A);
      inputStream.close();
    }

    catch(Exception e) { System.out.println(e); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void createBankDirectory(String dirName)

  {
    try

    {
      File temp = new File(BANK_PATH + dirName + "//");
      temp.mkdir();
    }

    catch(Exception e) { System.out.println(e); }
  }


  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveBankAccount(ArrayList A)

  {
    BankAccount BA = (BankAccount)A.get(0);
    BankChain BC = World.getBank(BA.getBankID());
    String chainName = removeColors(BC.getName());
    String ownerName = BA.getName();

    try

    {
      if (Server.shutdown)

      {
        System.out.println("Writing " + possessive(ownerName) + " account in " + chainName + ".");
        SystemLog.addLog("Writing " + possessive(ownerName) + " account in " + chainName + ".");
      }

      File temp = new File(BANK_PATH + "//" + chainName + "//" + ownerName + ".dat");
      FileOutputStream FOS = new FileOutputStream(temp);
      BufferedOutputStream BOS = new BufferedOutputStream(FOS);
      ObjectOutputStream outputStream = new ObjectOutputStream(BOS);
      outputStream.writeObject(A);
      outputStream.close();

      BC.removeBusy(ownerName);
    }

    catch (Exception e) { System.out.println(e); }
  }

   //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void deleteBankAccount(BankChain BC, String name)

  {
    try

    {
      String chainName = removeColors(BC.getName());
      File temp = new File(BANK_PATH + "//" + chainName + "//" + name + ".dat");
      if (!temp.exists()) return;
      if (!temp.delete()) throw new Exception("Could not delete file.");
    }

    catch (Exception e) { System.out.println(e); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadBanks()

  {
    try

    {
      File temp = new File(BANK_PATH);
      File[] fileList = temp.listFiles();
      int numFiles = Array.getLength(fileList);
      String sp = xchars(tenSpace(numFiles,5).length(),".") + " ";

      System.out.print("Loading " + (numFiles/2) + " Banks.........." + sp);

      for (int i=0; i<numFiles; i++)
        loadBank(fileList[i]);

      System.out.println("Done.");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadBank(File bfile)

  {
    if (bfile.isDirectory()) return;

    try

    {
      FileInputStream FIS = new FileInputStream(bfile);
      BufferedInputStream BIS = new BufferedInputStream(FIS);
      ObjectInputStream inputStream = new ObjectInputStream(BIS);
      BankChain BC = (BankChain)inputStream.readObject();
      World.setBank(BC);
      inputStream.close();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveBank(BankChain BC)

  {
    try

    {
      ObjectOutput out = openOutputFile(BANK_PATH + "bank" + String.format("%1$03d", BC.getID()));
      out.writeObject(BC);
      out.close();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveZones(Zone[] zoneList)

  {
    for (int i=0; i<ClientList.getSize(); i++)

    {
      // Fix this so it only throws people out if they are doing
      // building that actually conflicts with saving all the zones.

      Client C = ClientList.getClient(i);
      if ((C.getClientState() == CSTATE_BUILDING)
      || (C.getClientState() == CSTATE_WRITING))
        C.exitBuilder();
    }

    for (int i=0; i<World.MAX_ZONES; i++)
    if (zoneList[i] != null)
      zoneList[i].save();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static synchronized void saveZone(Zone z)

  {
    if (Server.shutdown)

    {
      SystemLog.addLog("Writing " + z.getName() + ".");
      System.out.println("Writing " + z.getName() + ".");
    }

    try

    {
      ObjectOutput out = openOutputFile(WORLD_PATH + "zone" + String.format("%1$03d", z.getID()));
      out.writeObject(z);
      out.close();
      z.setBusy(false);
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadZone(File zfile)

  {
    try

    {
      FileInputStream FIS = new FileInputStream(zfile);
      BufferedInputStream BIS = new BufferedInputStream(FIS);
      ObjectInputStream inputStream = new ObjectInputStream(BIS);
      Zone z = (Zone)inputStream.readObject();
      World.setZone(z);
      inputStream.close();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadZones()

  {
    try

    {
      File temp = new File(WORLD_PATH);
      File[] fileList = temp.listFiles();
      int numFiles = Array.getLength(fileList);
      String sp = xchars(tenSpace(numFiles,5).length(),".") + " ";

      System.out.print("Loading " + numFiles + " Zones.........." + sp);

      for (int i=0; i<numFiles; i++)
        loadZone(fileList[i]);

      System.out.println("Done.");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void deleteZone(Zone z)

  {
    try

    {
      System.out.println("Deleting " + z.getName() + ".");
      File zfile = new File(WORLD_PATH + "zone" + String.format("%1$03d", z.getID()));
      if (zfile.exists()) zfile.delete();
    }

    catch (Exception e) { System.out.println(e); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveBoard(MessageBoard b)

  {
    try

    {
      ObjectOutput out = openOutputFile(BOARD_PATH + "board" + String.format("%1$03d", b.getID()));
      out.writeObject(b);
      out.close();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static MessageBoard loadBoard(File bfile)

  {
    MessageBoard b = new MessageBoard();

    try

    {
      FileInputStream FIS = new FileInputStream(bfile);
      BufferedInputStream BIS = new BufferedInputStream(FIS);
      ObjectInputStream inputStream = new ObjectInputStream(BIS);
      b = (MessageBoard)inputStream.readObject();
      inputStream.close();
    }

    catch (Exception e) { e.printStackTrace(); }

    return b;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadBoards()

  {
    try

    {
      MessageBoard b;
      File temp = new File(BOARD_PATH);
      File[] fileList = temp.listFiles();
      int numFiles = Array.getLength(fileList);
      String sp = xchars(tenSpace(numFiles,5).length(),".") + " ";

      System.out.print("Loading " + numFiles + " Boards........." + sp);

      for (int i=0; i<numFiles; i++) {
        b = loadBoard(fileList[i]);
        World.setBoard(b); }

      System.out.println("Done.");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveClans()

  {
    for (int i=0; i<World.getClanSize(); i++)
    if (World.getClan(i) != null)
      saveClan(World.getClan(i));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveClan(Clan saveClan)

  {
    try

    {
      ObjectOutput out = openOutputFile(CLAN_PATH + "clan" + String.format("%1$03d", saveClan.getID()));
      out.writeObject(saveClan);
      out.close();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Clan loadClan(File cfile)

  {
    Clan c = new Clan();

    try

    {
      FileInputStream FIS = new FileInputStream(cfile);
      BufferedInputStream BIS = new BufferedInputStream(FIS);
      ObjectInputStream inputStream = new ObjectInputStream(BIS);
      c = (Clan)inputStream.readObject();
      inputStream.close();
    }

    catch (Exception e) { e.printStackTrace(); }

    return c;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadClans()

  {
    try

    {
      Clan c;
      File temp = new File(CLAN_PATH);
      File[] fileList = temp.listFiles();
      int numFiles = Array.getLength(fileList);
      String sp = xchars(tenSpace(numFiles,5).length(),".") + " ";

      System.out.print("Loading " + numFiles + " Clans.........." + sp);

      for (int i=0; i<numFiles; i++)

      {
        c = loadClan(fileList[i]);
        c.validateMembers();
        World.setClan(c);
      }

      System.out.println("Done.");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveReligions()

  {
    for (int i=0; i<World.getReligionSize(); i++)
    if (World.getReligion(i) != null)
      saveReligion(World.getReligion(i));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveReligion(Religion R)

  {
    try

    {
      ObjectOutput out = openOutputFile(RELIGION_PATH + "religion" + String.format("%1$03d", R.getID()));
      out.writeObject(R);
      out.close();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Religion loadReligion(File cfile)

  {
    Religion R = new Religion();

    try

    {
      FileInputStream FIS = new FileInputStream(cfile);
      BufferedInputStream BIS = new BufferedInputStream(FIS);
      ObjectInputStream inputStream = new ObjectInputStream(BIS);
      R = (Religion)inputStream.readObject();
      inputStream.close();
    }

    catch (Exception e) { e.printStackTrace(); }

    return R;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void loadReligions()

  {
    try

    {
      Religion R;
      File temp = new File(RELIGION_PATH);
      File[] fileList = temp.listFiles();
      int numFiles = Array.getLength(fileList);
      String sp = xchars(tenSpace(numFiles,5).length(),".") + " ";

      System.out.print("Loading " + numFiles + " Religions......" + sp);

      for (int i=0; i<numFiles; i++) {
        R = loadReligion(fileList[i]);
        R.validateMembers();
        World.setReligion(R); }

      System.out.println("Done.");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static AccessManager loadAccessManager()

  {
    try

    {
      System.out.print("Loading Access Manager........ ");

      File FILE = new File (ACCESS_PATH + "manager.dat");

      if (!FILE.exists()) {
        System.out.println("Does Not Exist!");
        return null; }

      FileInputStream FIS = new FileInputStream(FILE);
      BufferedInputStream BIS = new BufferedInputStream(FIS);
      ObjectInputStream objectInput = new ObjectInputStream(BIS);
      AccessManager AM = (AccessManager)objectInput.readObject();
      objectInput.close();

      System.out.println("Done.");

      return AM;
    }

    catch (Exception e)

    {
      System.out.println("Error!");
      e.printStackTrace();
      return null;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void saveAccessManager(AccessManager AM)

  {
    try

    {
      ObjectOutput out = openOutputFile(ACCESS_PATH + "manager.dat");
      out.writeObject(AM);
      out.close();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void appendLog(String s)

  {
    try

    {
      String fileName  = LOG_PATH;
             fileName += SystemTime.CALENDAR.get(SystemTime.CALENDAR.MONTH)+1 + "-";
             fileName += SystemTime.CALENDAR.get(SystemTime.CALENDAR.DAY_OF_MONTH) + "-";
             fileName += SystemTime.CALENDAR.get(SystemTime.CALENDAR.YEAR) + ".log";

      FileWriter logOutput = new FileWriter(fileName, true);

      logOutput.write(s);
      logOutput.close();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean charFileExists(String charName)

  {
    File F = new File(FM_PATH + charName + FM_EXT);
    return F.exists();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String fileToString(String filename)

  {
    String input = "";
    String content = "";

    try

    {
      BufferedReader reader = new BufferedReader(new FileReader(filename));

      while (input != null)

      {
        content += input;
        input = reader.readLine();
        if (input != null) content += "\r\n";
      }

      reader.close();
    }

    catch (Exception e)

    {
      System.out.println("Helpfile to string input error.");
      System.exit(0);
    }

    return content;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int countFiles(String dirName, boolean countDirectories)

  {
    try

    {
      File temp = new File(dirName);
      File[] fileList = temp.listFiles();
      int numFiles = Array.getLength(fileList);

      if (countDirectories) return numFiles;

      int count = 0;

      for (int i=0; i<numFiles; i++)
      if (fileList[i].getName().indexOf(".") != -1)
        count++;

      return count;
    }

    catch (Exception e) { return -1; }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}