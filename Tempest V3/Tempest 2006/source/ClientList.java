import java.util.Hashtable;
import java.util.LinkedList;

public class ClientList

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private static LinkedList <PlayerClient> list;
  private static LinkedList <PlayerClient> linkdeadList;
  private static LinkedList <PlayerClient> removeList;
  private static Hashtable <String, Client> clientTable;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  static void addClient(PlayerClient c)         { list.add(c);               }
  static boolean contains(PlayerClient c)       { return list.contains(c);   }
  static PlayerClient getClient(int i)          { return list.get(i);        }
  static int clientCount()                      { return list.size();        }
  static void addLinkdead(PlayerClient c)       { linkdeadList.add(c);       }
  static void queueClientRemove(PlayerClient c) { removeList.add(c);         }
  static Client findClient(String s)            { return clientTable.get(s); }
  static void putClient(String s, Client c)     { clientTable.put(s, c);     }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static void init()

  {
    list = new LinkedList <PlayerClient> ();
    linkdeadList = new LinkedList <PlayerClient> ();
    removeList = new LinkedList <PlayerClient> ();
    clientTable = new Hashtable <String, Client> ();

    clientTable.put("MobileClient", new MobileClient());
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static void processRemoveQueue()

  {
    for (Client C : removeList)

    {
      list.remove(C);
      linkdeadList.remove(C);
    }

    removeList.clear();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static void spin()

  {
    for (PlayerClient C : list)

    {
      C.processInput();
      C.handleInput();
      C.handleOutput();
    }

    processRemoveQueue();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}