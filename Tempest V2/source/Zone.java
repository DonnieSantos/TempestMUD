import java.util.*;
import java.io.*;

public class Zone extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 2;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private String name;
  private String zoneColor;
  private String zoneOwner;
  private int id;
  private int repopRate;
  private int defaultTerrain;
  private ArrayList roomList;
  private ArrayList itemList;
  private ArrayList mobList;
  private boolean busy;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Zone()                         { this(0);                              }
  public int getID()                    { return id;                            }
  public String getName()               { return name;                          }
  public String getOwner()              { return zoneOwner;                     }
  public Mobile getMobObj(int i)        { return World.getMob(getMob(i));       }
  public Room getRoomObj(int i)         { return World.getRoom(getRoom(i));     }
  public Item getItemObj(int i)         { return World.getItem(getItem(i));     }
  public int getRoom(int i)             { return getInt(roomList,i);            }
  public int getItem(int i)             { return getInt(itemList,i);            }
  public int getMob(int i)              { return getInt(mobList,i);             }
  public int getRepopRate()             { return repopRate;                     }
  public int getNumRooms()              { return roomList.size();               }
  public int getNumMobs()               { return mobList.size();                }
  public int getNumItems()              { return itemList.size();               }
  public void setName(String s)         { name = s;                             }
  public void setRate(int i)            { repopRate = i;                        }
  public void setColor(String s)        { zoneColor = s;                        }
  public String getColor()              { return zoneColor;                     }
  public void setRoomList(ArrayList a)  { roomList = a;                         }
  public void clearMoblist()            { mobList = new ArrayList();            }
  public void setBusy(boolean b)        { busy = b;                             }
  public boolean getBusy()              { return busy;                          }
  public void setDefaultTerrain(int i)  { defaultTerrain = i;                   }
  public int getDefaultTerrain()        { return defaultTerrain;                }
  public void setOwner(String s)        { zoneOwner = s;                        }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Zone(int ID)

  {
    id          =  ID;
    name        =  "New Zone";
    zoneColor   =  "#N";
    repopRate   =  100;
    busy        =  false;
    zoneOwner   =  "";
    roomList    =  new ArrayList();
    itemList    =  new ArrayList();
    mobList     =  new ArrayList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void save()

  {
    WriteThread.addObject(this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearMobPopulation()

  {
    for (int i=0; i<roomList.size(); i++)
      getRoomObj(i).clearMobPopulation();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addRoom(int rnum)

  {
    Integer rn = new Integer(rnum);
    int i = roomList.indexOf(rn);
    if (i != -1) return;
    roomList.add(new Integer(rnum));
    sortRooms();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addItem(int inum)

  {
    Integer in = new Integer(inum);
    int i = itemList.indexOf(in);
    if (i != -1) return;
    itemList.add(new Integer(inum));
    sortItems();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addMob(int mnum)

  {
    Integer m = new Integer(mnum);
    int i = mobList.indexOf(m);
    if (i != -1) return;
    mobList.add(new Integer(mnum));
    sortMobs();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void movePlayersOut()

  {
    for (int i=0; i<roomList.size(); i++)
      getRoomObj(i).movePlayersOut();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void repopMobs()

  {
    for (int i=0; i<roomList.size(); i++)
      getRoomObj(i).repopMobs();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void repopItems()

  {
    for (int i=0; i<roomList.size(); i++)
      getRoomObj(i).repopItems();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearMobs()

  {
    for (int i=0; i<roomList.size(); i++)
      World.getRoom(getRoom(i)).clearMobs();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearItems()

  {
    for (int i=0; i<roomList.size(); i++)
      World.getRoom(getRoom(i)).clearItems();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean roomInZone(int rID)

  {
    int size = roomList.size();

    for (int i=0; i<size; i++)
      if (getInt(roomList,i) == rID)
        return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void sortRooms()

  {
    int size = roomList.size();

    for (int b=0; b<size-1; b++)
    for (int i=0; i<size-1; i++)

    {
      int r1 = getInt(roomList, i);
      int r2 = getInt(roomList, i+1);

      if (r1 > r2)

      {
        roomList.set(i, new Integer(r2));
        roomList.set(i+1, new Integer(r1));
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void sortItems()

  {
    int size = itemList.size();

    for (int b=0; b<size-1; b++)
    for (int i=0; i<size-1; i++)

    {
      int r1 = getInt(itemList, i);
      int r2 = getInt(itemList, i+1);

      if (r1 > r2)

      {
        itemList.set(i, new Integer(r2));
        itemList.set(i+1, new Integer(r1));
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void sortMobs()

  {
    int size = mobList.size();

    for (int b=0; b<size-1; b++)
    for (int i=0; i<size-1; i++)

    {
      int r1 = getInt(mobList, i);
      int r2 = getInt(mobList, i+1);

      if (r1 > r2)

      {
        mobList.set(i, new Integer(r2));
        mobList.set(i+1, new Integer(r1));
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void globalDelete()

  {
    for (int i=itemList.size()-1; i>=0; i--)
      World.globalItemDelete(getInt(itemList,i));
    for (int i=mobList.size()-1; i>=0; i--)
      World.globalMobDelete(getInt(mobList,i));
    for (int i=roomList.size()-1; i>=0; i--)
      World.globalRoomDelete(getInt(roomList,i));

    WriteThread.msg("deletezone", this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void globalRoomDelete(int rnum)

  {
    for (int i=roomList.size()-1; i>=0; i--)
    if (getInt(roomList, i) == rnum)
      roomList.remove(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void globalMobDelete(int mnum)

  {
    for (int i=mobList.size()-1; i>=0; i--)
    if (getInt(mobList,i) == mnum)
      mobList.remove(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void globalItemDelete(int inum)

  {
    for (int i=itemList.size()-1; i>=0; i--)
    if (getInt(itemList,i) == inum)
      itemList.remove(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void globalRoomRenumber(int[] indexList)

  {
    for (int i=0; i<roomList.size(); i++)
      roomList.set(i, new Integer(indexList[getInt(roomList,i)]));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void globalItemRenumber(int[] indexList)

  {
    for (int i=0; i<itemList.size(); i++)
      itemList.set(i, new Integer(indexList[getInt(itemList,i)]));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void globalMobRenumber(int[] indexList)

  {
    for (int i=mobList.size()-1; i>=0; i--)
      mobList.set(i, new Integer(indexList[getInt(mobList,i)]));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("NAME");        out.writeObject(name);
      out.writeObject("ZONECOLOR");   out.writeObject(zoneColor);
      out.writeObject("ID");          out.writeObject(new Integer(id));
      out.writeObject("REPOPRATE");   out.writeObject(new Integer(repopRate));
      out.writeObject("OWNER");       out.writeObject(zoneOwner);
      out.writeObject("ROOMLIST");    out.writeObject(roomList);
      out.writeObject("ITEMLIST");    out.writeObject(itemList);
      out.writeObject("MOBLIST");     out.writeObject(mobList);

      for (int i=0; i<roomList.size(); i++) {
        out.writeObject("ROOM");
        out.writeObject(World.getRoom(getInt(roomList, i))); }

      for (int i=0; i<itemList.size(); i++) {
        out.writeObject("ITEM");
        out.writeObject(World.getItem(getInt(itemList, i))); }

      for (int i=0; i<mobList.size(); i++) {
        out.writeObject("MOB");
        Entity M = World.getMob(getInt(mobList,i));
        M.writeExternal(out); }

      out.writeObject("ZONE END");
    }

    catch (Exception e) { e.printStackTrace(); }

  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try

    {
      String tag = "";

      while (!tag.equals("ZONE END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("NAME"))             name       = (String)in.readObject();
        else if (tag.equals("ZONECOLOR"))   zoneColor  = (String)in.readObject();
        else if (tag.equals("ID"))          id         = ((Integer)in.readObject()).intValue();
        else if (tag.equals("REPOPRATE"))   repopRate  = ((Integer)in.readObject()).intValue();
        else if (tag.equals("OWNER"))       zoneOwner  = (String)in.readObject();
        else if (tag.equals("ROOMLIST"))    roomList   = (ArrayList)in.readObject();
        else if (tag.equals("ITEMLIST"))    itemList   = (ArrayList)in.readObject();
        else if (tag.equals("MOBLIST"))     mobList    = (ArrayList)in.readObject();
        else if (tag.equals("ROOM"))        World.setRoom((Room)in.readObject());
        else if (tag.equals("ITEM"))        World.setItem((Item)in.readObject());

        else if (tag.equals("MOB"))

        {
          Entity M = new Mobile();
          M.readExternal(in);
          World.setMob(M.castMob());
        }

        else if (!tag.equals("ZONE END")) in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}
