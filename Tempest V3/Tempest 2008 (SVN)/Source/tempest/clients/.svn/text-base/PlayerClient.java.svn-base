package tempest.clients;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.nio.channels.SocketChannel;
import tempest.primitives.*;
import tempest.interfaces.*;

public class PlayerClient extends Client

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private MudString uniqueId;
  private long linkdeadTime;
  private long lastProcessInput;
  private MudString ipAddress;
  private boolean isLinkdead;
  private ByteBuffer receivedBytes;
  private SocketChannel clientSocket;
  private MudInterface currentInterface;
  private MudString inprogressCommand;
  private LinkedList <MudInterface> interfaceStack;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public boolean isLinkdead()            { return isLinkdead;               }
  public void handleInput()              { currentInterface.handleInput();  }
  public void handleOutput()             { currentInterface.handleOutput(); }
  public void echo(String s)             { currentInterface.echo(s);        }
  public void echo(MudString s)          { currentInterface.echo(s);        }
  public void setSocket(SocketChannel s) { clientSocket = s;                }
  public MudString uniqueId()            { return uniqueId;                 }
  public MudInterface getInterface()     { return currentInterface;         }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public PlayerClient(SocketChannel clientSocket)

  {
    try

    {
      this.uniqueId = ClientList.generateId();
      this.clientSocket = clientSocket;
      this.ipAddress = new MudString("");
      this.inprogressCommand = new MudString(500);
      this.receivedBytes = ByteBuffer.allocateDirect(10000);
      this.isLinkdead = false;
      this.interfaceStack = new LinkedList <MudInterface> ();
    }

    catch (Exception e)

    {
      e.printStackTrace();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public MudString getIPAddress()

  {
    if (clientSocket.socket().getInetAddress() == null) return ipAddress;
    ipAddress.set(clientSocket.socket().getInetAddress().getHostAddress());
    return ipAddress;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void msg(MudString s)

  {
    try

    {
      byte[] message = s.colorize().getBytes();
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

  private void clientTimer()

  {
    long current = System.currentTimeMillis();
    long elapsed = current - lastProcessInput;
    lastProcessInput = current;
    if (isLinkdead) linkdeadTime += elapsed;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void disconnect()

  {
    try { clientSocket.socket().close(); }
    catch (Exception e) { e.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void popInterface()

  {
    interfaceStack.removeFirst();
    currentInterface.onPop();
    currentInterface = interfaceStack.getFirst();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void pushInterface(MudInterface nextInterface)

  {
    interfaceStack.addFirst(nextInterface);
    currentInterface = interfaceStack.getFirst();
    currentInterface.onPush();
    currentInterface.focusGained();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void reconnect(PlayerClient oldClient)

  {
    ClientList.queueClientRemove(this);
    oldClient.disconnect();
    oldClient.setSocket(clientSocket);
    oldClient.exitLinkdead();

    MudInterface current = oldClient.getInterface();
    current.onReconnect();
    current.focusGained();
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void enterLinkdead()

  {
    currentInterface.onDisconnect();
    isLinkdead = true;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void exitLinkdead()

  {
    isLinkdead = false;
    linkdeadTime = 0;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void processInput()

  {
    try

    {
      clientTimer();
      if (isLinkdead) return;

      receivedBytes.clear();

      if (clientSocket.read(receivedBytes) == -1)
        throw new Exception("Can't read the socket.");

      receivedBytes.flip();

      while (receivedBytes.hasRemaining())

      {
        char temp = (char)receivedBytes.get();

        if ((temp == '\r') || (temp == '\n'))

        {
          if ((temp == '\r') && (receivedBytes.hasRemaining()))
            receivedBytes.get();

          currentInterface.receiveCommand(inprogressCommand.toString());
          inprogressCommand = new MudString(500);
        }

        else if ((int)temp >= 32 && (int)temp <= 126)

        {
          if (inprogressCommand.length() < 500)
            inprogressCommand.append(temp);
        }

        else if (((int)temp == 127 || (int)temp == 8))

        {
          if (inprogressCommand.length() > 0)
            inprogressCommand.deleteCharAt(inprogressCommand.length() - 1);
        }
      }
    }

    catch (Exception e) { enterLinkdead(); }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}