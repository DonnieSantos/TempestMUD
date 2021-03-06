import java.io.*;
import java.lang.*;
import java.util.*;

public class World implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private Room roomList[10000];
  private ArrayList zoneList;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out) { }
  public void readExternal(ObjectInput in)    { }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Room getRoom(int i) { return roomList[i];            }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public World()

  {
    System.out.println("World object creation started.");

    for (int i=0; i<10000; i++) roomList[i] = null;

    zoneList = new ArrayList();

    System.out.println("World object creation ended.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Zone getZone(int ID)

  {
    for (int i=0; i<zoneList.size(); i++)

    {
      Zone Z = (Zone) zoneList.get(i);
      if (Z.getID() == ID) return Z;
    }

    return null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void createRoom(int ID, int zoneID)

  {
    roomList[ID] = new Room(ID);
    roomList[ID].setTitle("New Room");
    roomList[ID].setDesc("Blank Description", false);
    getZone(zoneID).addRoom(roomList[ID]);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}