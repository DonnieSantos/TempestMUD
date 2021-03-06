import java.io.*;
import java.util.*;
import java.lang.*;

public class WriteThread extends Thread

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static WriteThread writer;
  public static LinkedList queue;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public WriteThread() { }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void init()

  {
    writer = new WriteThread();
    queue = new LinkedList();
    writer.setDaemon(true);
    writer.start();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void addObject(Object O)

  {
    synchronized (queue)

    {
      if ((!(O instanceof String)) && (queue.contains(O))) return;
      queue.add(O);
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static Object getObject()

  {
    synchronized (queue)

    {
      if (queue.size() > 0) return queue.get(0);
      return null;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void removeObject()

  {
    synchronized (queue)

    {
      if (queue.size() > 0) queue.remove(0);
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static boolean isWriting()

  {
    synchronized (queue)

    {
      if (queue.size() > 0) return true;
      return false;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void run()

  {
    try

    {
      while (true)
      if (getObject() == null) Thread.sleep(100);
      else write(getObject());
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void write(Object O)

  {
    if (O instanceof String) FileManager.appendLog((String)O);
    if (O instanceof Zone) FileManager.saveZone((Zone)O);
    if (O instanceof Char) FileManager.saveCharacter((Entity)O);
    if (O instanceof ArrayList) FileManager.saveBankAccount((ArrayList)O);
    if (O instanceof Account) FileManager.saveAccount((Account)O);
    if (O instanceof WriteThreadMsg) interpretMessage((WriteThreadMsg)O);

    WriteThread.removeObject();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void interpretMessage(WriteThreadMsg msg)

  {
    String cmd = Utility.first(msg.msg);
    String parameter = Utility.last(msg.msg);

    if (cmd.equals("deleteaccount"))    FileManager.deleteAccount(parameter);
    if (cmd.equals("deletecharacter"))  FileManager.deleteCharacter(parameter);
    if (cmd.equals("deletezone"))       FileManager.deleteZone(msg.zone);
    if (cmd.equals("deletebankacct"))   FileManager.deleteBankAccount(msg.chain, parameter);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void msg(String s)

  {
    addObject(new WriteThreadMsg(s));
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void msg(String s, Zone Z)

  {
    WriteThreadMsg msg = new WriteThreadMsg();

    msg.msg = s;
    msg.zone = Z;

    addObject(msg);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public static void msg(String s, BankChain BC)

  {
    WriteThreadMsg msg = new WriteThreadMsg();

    msg.msg = s;
    msg.chain = BC;

    addObject(msg);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}
