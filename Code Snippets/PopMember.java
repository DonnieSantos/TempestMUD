import java.io.*;
import java.util.*;

class PopMember extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private Mobile member;
  private int roomNumber;
  private ArrayList idNumbers;
  private ArrayList frequencies;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Mobile getMob()              { return member;                    }
  public int getRNum()                { return roomNumber;                }
  public int getNumTypes()            { return idNumbers.size();          }
  public int getIDNum(int i)          { return getInt(idNumbers, i);      }
  public double getFreq(int i)        { return getDouble(frequencies, i); }
  public boolean isRandom()           { return (idNumbers.size() > 1);    }
  public void setIDNum(int i, int j)  { idNumbers.set(i, new Integer(j)); }
  public void setRNum(int i)          { roomNumber = i;                   }
  public void nullify()               { member = null;                    }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public PopMember(int rnum, int id, double freq)

  {
    member = null;
    roomNumber = rnum;
    idNumbers = new ArrayList();
    frequencies = new ArrayList();
    addType(id, freq);
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
    member = World.getMob(getIDNum(i)).replicate();
    member.setPopMember(this);
    member.setRoom(roomNumber);
    member.popEq();
    World.getRoom(roomNumber).gain(member);
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

  public boolean removeType(int i)

  {
    if (i >= getNumTypes()) return false;
    idNumbers.remove(i);
    frequencies.remove(i);
    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private double getTotalFrequency()

  {
    double total = 0.0;

    for (int i=0; i<getNumTypes(); i++)
      total += getFreq(i);

    return total;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}