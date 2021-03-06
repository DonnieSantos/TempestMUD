import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;

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

  private Server()

  {
    this.shutdown = false;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public static Server getInstance()

  {
    if (instance == null) instance = new Server();
    return instance;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void shutdown()

  {
    shutdown = true;
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

    catch (Exception e)

    {
      e.printStackTrace();
    }
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

    catch (Exception e)

    {
      e.printStackTrace();
    }

    try

    {
      System.out.println("Waiting for writing to finish.");
      System.out.println("Writing finished.");
    }

    catch (Exception e)

    {
      e.printStackTrace();
    }
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
      client.pushInterface(new LoginInterface(client, null));
      client.echo("#YTempest MUD.#N\r\n");
      ClientList.addClient(client);

      String ipAddress = client.getIPAddress();
      System.out.println(ipAddress + " connected to server.");
    }

    catch (Exception e)

    {
      e.printStackTrace();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}
