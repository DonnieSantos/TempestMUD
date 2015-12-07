import java.io.*;
import java.util.*;

public class SystemLog extends Utility

{
  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static boolean enabled = true;

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static void doubleLog(Entity ENT, String msg)

  {
    String ip = "";

    if (ENT.getClient() != null)
      ip = ENT.getClient().getIPAddress();

    addLog(ip + " " + ENT.getName() + " " + msg);
    ENT.smsg(ENT.getName() + " " + msg);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static void doubleAccountLog(Client CLT, String msg)

  {
    String name = CLT.getAccount().getUsername();

    ClientList.accountMessage("Account " + name + " " + msg, CLT);
    addLog(CLT.getIPAddress() + " Account " + name + " " + msg);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static void addLog(String text)

  {
    addLog(null, text);
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public static synchronized void addLog(Client logClient, String text)

  {
    if (!enabled) return;

    String logEntry  = "";
    String logHeader = "";
    Entity E = null;

    if (logClient != null) E = logClient.getCharInfo();

    try

    {
      SystemTime.update();
      logHeader = SystemTime.getTime();

      if (logClient != null)

      {
        if (logClient.getClientState() != CSTATE_LOGIN)

        {
          logHeader += " " + logClient.getIPAddress();
          logHeader += " " + logClient.getCharInfo().getName();

          if (E != null) logEntry  += "(" + E.getRoom().getID() + ") "  + text;
          else logEntry  += "(NULL) "  + text;
        }

        else

        {
          if (logClient.getClientState() == CSTATE_LOGIN)
            logHeader += " " + logClient.getIPAddress() + " LOGIN";

          logEntry += "(VOID) " + text;
        }
      }

      else

      {
        logHeader += " SYSTEM";
        logEntry  += text;
      }

      logEntry = String.format("[%1$-33.33s] %2$s\r\n", logHeader, logEntry);
      WriteThread.addObject(logEntry);
    }

    catch (Exception e) { System.out.println(e); }
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////
}