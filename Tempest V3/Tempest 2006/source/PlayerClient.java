import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.lang.StringBuffer;
import java.nio.channels.SocketChannel;

public class PlayerClient extends Client

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private String ipAddress;
  private StringBuffer inprogressCommand;
  private ByteBuffer receivedBytes;
  private SocketChannel clientSocket;
  private MudInterface currentInterface;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public PlayerClient(SocketChannel clientSocket)

  {
    try

    {
      this.clientSocket = clientSocket;

      ipAddress = "";
      inprogressCommand = new StringBuffer();
      receivedBytes = ByteBuffer.allocateDirect(10000);
    }

    catch (Exception e)

    {
      e.printStackTrace();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public String getIPAddress()

  {
    if (clientSocket.socket().getInetAddress() == null) return ipAddress;
    ipAddress = clientSocket.socket().getInetAddress().getHostAddress();
    return ipAddress;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void msg(String s)

  {
    try

    {
      s = colorize(s);
      byte[] message = s.getBytes();
      ByteBuffer wrappedMessage = ByteBuffer.wrap(message);
      clientSocket.write(wrappedMessage);
    }

    catch (Exception e)

    {
      System.out.println("Sending error.");
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void processInput()

  {
    // If not connected return
    try

    {
      receivedBytes.clear();

      if (clientSocket.read(receivedBytes) == -1)

      {
        // Can't read the socket.
        return;
      }

      receivedBytes.flip();

      while (receivedBytes.hasRemaining())

      {
        char temp = (char)receivedBytes.get();

        if ((temp == '\r') || (temp == '\n'))

        {
          if ((temp == '\r') && (receivedBytes.hasRemaining())) receivedBytes.get();
          currentInterface.receiveCommand(inprogressCommand.toString());
          inprogressCommand = new StringBuffer();
        }

        else if ((int)temp >= 32 && (int)temp <= 126 && inprogressCommand.length() < 500)
          inprogressCommand.append(temp);
        else if (((int)temp == 127 || (int)temp == 8) && inprogressCommand.length() > 0)
          inprogressCommand.deleteCharAt(inprogressCommand.length() - 1);
      }
    }

    catch (Exception e)

    {
      // Something bad happened.  Make them go linkdead.
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void disconnect()

  {
    try

    {
      clientSocket.socket().close();
    }

    catch (Exception e)

    {
      e.printStackTrace();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void pushInterface(MudInterface nextInterface)

  {
    currentInterface = nextInterface;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    currentInterface.handleInput();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void handleOutput()

  {
    currentInterface.handleOutput();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void echo(String s)

  {
    currentInterface.echo(s);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}