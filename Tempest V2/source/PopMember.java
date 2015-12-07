import java.io.*;
import java.util.*;

class PopMember extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final long serialVersionUID = 90;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private Object member;
  private Room popRoom;
  private boolean isMob;
  private ArrayList idNumbers;
  private ArrayList frequencies;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Mobile getMob()              { return (Mobile) member;            }
  public Item getItem()               { return (Item) member;              }
  public Room getRoom()               { return popRoom;                    }
  public int getNumTypes()            { return idNumbers.size();           }
  public int getIDNum(int i)          { return getInt(idNumbers, i);       }
  public double getFreq(int i)        { return getDouble(frequencies, i);  }
  public boolean isRandom()           { return (idNumbers.size() > 1);     }
  public void setIDNum(int i, int j)  { idNumbers.set(i, new Integer(j));  }
  public void setRoom(Room r)         { popRoom = r;                       }
  public void unlinkMob(Mobile M)     { if (getMob() == M) member = null;  }
  public void unlinkItem(Item I)      { if (getItem() == I) member = null; }
  public void unlinkMember()          { member = null;                     }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public PopMember()

  {
    isMob = true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public PopMember(Room popInRoom, int id, double freq, boolean isMobile)

  {
    member = null;
    popRoom = popInRoom;
    idNumbers = new ArrayList();
    frequencies = new ArrayList();
    addType(id, freq);
    isMob = isMobile;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void unlinkAll()

  {
    if (member != null)

    {
      if ((isMob) && (getMob().getPopMember() == this))
        getMob().setPopMember(null);
      if ((!isMob) && (getItem().getPopMember() == this))
        getItem().setPopMember(null);
    }

    member = null;
    popRoom = null;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void pop()

  {
    if (member != null) return;
    if (getNumTypes() <= 0) return;

    if (getNumTypes() > 1)

    {
      double low = 0.0;
      double high = getFreq(0);
      double rand = getRandomDouble(0.0, 100.0);
      boolean popped = false;

      if ((rand >= low) && (rand <= high)) { pop(0); popped = true; }

      for (int i=1; i<getNumTypes(); i++)
      if (!popped)

      {
        low += getFreq(i-1);
        high += getFreq(i);

        if ((rand >= low) && (rand <= high)) { pop(i); popped = true; }
      }
    }

    else if (getRandomDouble(0.0, 100.0) < getFreq(0)) pop(0);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private void pop(int i)

  {
    if (isMob)

    {
      Mobile M = World.getMob(getIDNum(i)).replicate();
      M.setPopMember(this);
      M.popEq();
      M.restore();
      popRoom.enter(M, ENTER_POP);
      member = (Mobile) M;
    }

    else

    {
      String stamp = "Popped in Room " + popRoom.getID() + ".";
      Item I = World.getItem(getIDNum(i)).replicate(stamp);
      I.setPopMember(this);
      Create.item(I, popRoom, IPOPR);
      member = (Item) I;
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean addType(int id, double freq)

  {
    double TOTAL = getTotalFrequency();

    if (TOTAL >= 100.0) return false;

    if ((TOTAL + freq) > 100.0)
      freq = 100.0 - TOTAL;

    idNumbers.add(new Integer(id));
    frequencies.add(new Double(freq));

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setFreq(int i, double freq)

  {
    if (i >= getNumTypes()) return;

    double TOTAL = getTotalFrequency();

    if ((TOTAL - getFreq(i) + freq) > 100.0)

    {
      double addAmount = 100.0 - TOTAL;
      double newFreq = getFreq(i) + addAmount;
      frequencies.set(i, new Double(newFreq));
    }

    else frequencies.set(i, new Double(freq));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean removeType(int i)

  {
    if (i >= getNumTypes()) return false;
    idNumbers.remove(i);
    frequencies.remove(i);
    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public double getTotalFrequency()

  {
    double total = 0.0;

    for (int i=0; i<getNumTypes(); i++)
      total += getFreq(i);

    return total;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("ISMOB");   out.writeObject(new Boolean(isMob));
      out.writeObject("IDS");     out.writeObject(idNumbers);
      out.writeObject("FREQS");   out.writeObject(frequencies);
      out.writeObject("PM END");
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

      while (!tag.equals("PM END"))

      {
        tag = (String)in.readObject();

        if (tag.equals("ISMOB"))         isMob = ((Boolean)in.readObject()).booleanValue();
        else if (tag.equals("IDS"))      idNumbers = (ArrayList)in.readObject();
        else if (tag.equals("FREQS"))    frequencies = (ArrayList)in.readObject();
        else if (!tag.equals("PM END"))  in.readObject();
      }
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}