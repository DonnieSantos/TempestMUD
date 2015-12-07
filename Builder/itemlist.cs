using System;
using System.Collections;

namespace Tempest_Builder

{
  public class itemlist : ICloneable

  {
    private item[] LIST;
    private int size;

    public itemlist()

    {
      size = 0;
    }

    public object Clone()

    {
      return this.MemberwiseClone();
    }

    public void flush()

    {
      item[] temp = new item[0];
      LIST = temp;
      size = 0;
    }

    public void add_item(item item_pointer)

    {
      item[] temp = new item[size+1];

      if (LIST != null)
        Array.Copy(LIST, temp, size);

      temp[size] = item_pointer;

      LIST = temp;
      size++;
    }

    public void remove_item(item item_pointer)

    {
      if (size > 1)

      {
        int found = 0;
        item[] temp = new item[size-1];

        for (int i=0; i<size; i++)
          if (LIST[i] != item_pointer)
            temp[i-found] = LIST[i];
          else found = 1;

        LIST = temp;
        size--;
      }

      else

      {
        item[] temp = new item[0];
        LIST = temp;
        size = 0;
      }
    }

    public void remove_item(int index)

    {
      if (size > 1)

      {
        int found = 0;
        item[] temp = new item[size-1];

        for (int i=0; i<size; i++)
          if (i != index)
            temp[i-found] = LIST[i];
          else found = 1;

        LIST = temp;
        size--;
      }

      else

      {
        item[] temp = new item[0];
        LIST = temp;
        size = 0;
      }
    }

    public item find_item(string ID)

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

    public item get_item(int i)

    {
      return LIST[i];
    }

    public void sort()

    {
      int i,j;
      item t;

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
