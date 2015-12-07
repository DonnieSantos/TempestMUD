package tempest.server;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import tempest.clients.ClientList;
import tempest.clients.PlayerClient;
import tempest.interfaces.logininterface.LoginInterface;
import tempest.primitives.MudString;
import tempest.data.Data;

public class Server

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static Server instance;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private boolean shutdown;
  private ServerSocketChannel masterChannel;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Server()       { this.shutdown = false; }
  public void shutdown() { shutdown = true;       }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static Server getInstance()

  {
    if (instance == null) instance = new Server();
    return instance;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void open(int port)

  {
    try

    {
      masterChannel = ServerSocketChannel.open();
      masterChannel.configureBlocking(false);
      masterChannel.socket().bind(new InetSocketAddress(port));
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

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

    catch (Exception e) { e.printStackTrace(); }

    System.out.println("Waiting for writing to finish.");
    System.out.println("Writing finished.");
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void connectClient(SocketChannel clientChannel)

  {
    try

    {
      clientChannel.socket().setSendBufferSize(50000);
      clientChannel.configureBlocking(false);

      PlayerClient client = new PlayerClient(clientChannel);
      client.pushInterface(new LoginInterface(client));
      Data titleData = Mud.get("configuration").get("mud").get("title");
      client.echo(titleData.value());
      ClientList.addClient(client);

      MudString ipAddress = client.getIPAddress();
      System.out.println(ipAddress.toString() + " connected to server.");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}