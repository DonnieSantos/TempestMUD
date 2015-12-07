using System;
using System.Collections;

namespace Tempest_Builder

{
  public class moblist : ICloneable

  {
    private mob[] LIST;
    private int size;

    public moblist()

    {
      size = 0;
    }

    public object Clone()

    {
      return this.MemberwiseClone();
    }

    public void flush()

    {
      mob[] temp = new mob[0];
      LIST = temp;
      size = 0;
    }

    public void add_mob(mob mob_pointer)

    {
      mob[] temp = new mob[size+1];

      if (LIST != null)
        Array.Copy(LIST, temp, size);

      temp[size] = mob_pointer;

      LIST = temp;
      size++;
    }

    public void remove_mob(mob mob_pointer)

    {
      if (size > 1)

      {
        int found = 0;
        mob[] temp = new mob[size-1];

        for (int i=0; i<size; i++)
          if (LIST[i] != mob_pointer)
            temp[i-found] = LIST[i];
          else found = 1;

        LIST = temp;
        size--;
      }

      else

      {
        mob[] temp = new mob[0];
        LIST = temp;
        size = 0;
      }
    }

    public void remove_mob(int index)

    {
      if (size > 1)

      {
        int found = 0;
        mob[] temp = new mob[size-1];

        for (int i=0; i<size; i++)
          if (i != index)
            temp[i-found] = LIST[i];
          else found = 1;

        LIST = temp;
        size--;
      }

      else

      {
        mob[] temp = new mob[0];
        LIST = temp;
        size = 0;
      }
    }

    public mob find_mob(string ID)

    {
      for (int i=0; i<size; i++)
        if (LIST[i].id == ID)
          return LIST[i];

      return null;
    }

    public int get_size()

    {
      return size;
    }

    public mob get_mob(int i)

    {
      return LIST[i];
    }

    public void sort()

    {
      int i,j;
      mob t;

      for(i=0; i<size; i++)
        for(j=0; j<size-i-1; j++)
          if(int.Parse(LIST[j].id) > int.Parse(LIST[j+1].id))

          {
            t         = LIST[j];
            LIST[j]   = LIST[j+1];
            LIST[j+1] = t;
          }
    }
  }
}