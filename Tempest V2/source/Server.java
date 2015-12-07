import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.lang.reflect.Array;
import java.nio.channels.*;

public class Server

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void main(String args[])

  {
    System.out.println("");

    int numArgs = Array.getLength(args);

    int port = 7000;

    if (numArgs > 0)

    {
      try                 { port = Integer.parseInt(args[0]);                     }
      catch (Exception e) { System.out.println("Invalid Command Line Argument."); }
    }

    WriteThread.init();
    Server mudServer = new Server(port);
    World.loadWorld();
    AccessManager.init();
    SystemLog.addLog("Tempest Server Started on port " + port + ".");
    System.out.println("Tempest Server Running on port " + port + ".\r\n");
    mudServer.acceptingState();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean shutdown;
  private ServerSocketChannel masterChannel;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Server(int port)

  {
    try

    {
      shutdown = false;
      masterChannel = ServerSocketChannel.open();
      masterChannel.configureBlocking(false);
      masterChannel.socket().bind(new InetSocketAddress(port));
    }

    catch (Exception e) { System.out.println(e); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void acceptingState()

  {
    SocketChannel clientChannel = null;

    try

    {
      while (!shutdown)

      {
        clientChannel = masterChannel.accept();
        if (clientChannel == null) ClientList.spin();
        else connectClient(clientChannel);

        Thread.sleep(5);
      }
    }

    catch (Exception e)

    {
      shutdown = true;
      e.printStackTrace();

      SystemLog.addLog(e.toString());
      StackTraceElement[] stackTrace = e.getStackTrace();

      for (int i=0; i<stackTrace.length; i++)
        SystemLog.addLog(stackTrace[i].toString());
    }

    try

    {
      SystemLog.addLog("Waiting for writing to finish.");
      System.out.println("Waiting for writing to finish.");
      while (WriteThread.isWriting()) Thread.sleep(10);
      System.out.println("Writing finished.");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void connectClient(SocketChannel clientChannel)

  {
    String ipAddress = clientChannel.socket().getInetAddress().getHostAddress();
    System.out.println(ipAddress + " connected to server.");

    try

    {
      clientChannel.socket().setSendBufferSize(50000);
      clientChannel.configureBlocking(false);
      Client newClient = new Client(clientChannel);

      if (AccessManager.manager.isSiteBanned(ipAddress))

      {
        newClient.msg(ipAddress + " is banned.\r\n");
        newClient.disconnect();
        System.out.println(ipAddress + " is banned.");
        return;
      }

      if (AccessManager.manager.tooManyConnections(ipAddress))

      {
        newClient.msg("Too many connections from " + ipAddress + "\r\n");
        newClient.disconnect();
        System.out.println("Too many connections from " + ipAddress);
        return;
      }

      ClientList.addClient(newClient);
      SystemLog.addLog(newClient, ipAddress + " connected to server.");
      newClient.pushInterface(new Login(newClient));
      newClient.msg(HelpFile.getTitleScreen() + "\r\n\n");
      newClient.drawPrompt();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}
