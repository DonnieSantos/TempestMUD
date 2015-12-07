using System;
using System.Collections;

namespace Tempest_Builder

{
  public class roomlist

  {
    private room[] LIST;
    private int size;

    public roomlist()

    {
      size = 0;
    }

    public void flush()

    {
      room[] temp = new room[0];
      LIST = temp;
      size = 0;
    }

    public void add_room(room room_pointer)

    {
      room[] temp = new room[size+1];

      if (LIST != null)
        Array.Copy(LIST, temp, size);

      temp[size] = room_pointer;

      LIST = temp;
      size++;
    }

    public void remove_room(room room_pointer)

    {
      if (size > 1)

      {
        int found = 0;
        room[] temp = new room[size-1];

        for (int i=0; i<size; i++)
          if (LIST[i] != room_pointer)
            temp[i-found] = LIST[i];
          else found = 1;

        LIST = temp;
        size--;
      }

      else

      {
        room[] temp = new room[0];
        LIST = temp;
        size = 0;
      }
    }

    public void remove_room(int index)

    {
      if (size > 1)

      {
        int found = 0;
        room[] temp = new room[size-1];

        for (int i=0; i<size; i++)
          if (i != index)
            temp[i-found] = LIST[i];
          else found = 1;

        LIST = temp;
        size--;
      }

      else

      {
        room[] temp = new room[0];
        LIST = temp;
        size = 0;
      }
    }

    public room find_room(string ID)

    {
      for (int i=0; i<size; i++)
        if (LIST[i].get_id() == ID)
          return LIST[i];

      return null;
    }

    public int get_size()

    {
      return size;
    }

    public room get_room(int i)

    {
      return LIST[i];
    }

    public void sort()

    {
      int i,j;
      room t;

      for(i=0; i<size; i++)
        for(j=0; j<size-i-1; j++)
          if(int.Parse(LIST[j].get_id()) > int.Parse(LIST[j+1].get_id()))

          {
            t         = LIST[j];
            LIST[j]   = LIST[j+1];
            LIST[j+1] = t;
          }
    }

    public int get_popsize()

    {
      int count = 0;

      for (int i=0; i<size; i++)
        count += LIST[i].get_mobs().get_size();

      return count;
    }

    public string get_population()

    {
      string output = "";

      for (int i=0; i<size; i++)
        for (int j=0; j<LIST[i].get_mobs().get_size(); j++)
          output += LIST[i].get_id() + " " + LIST[i].get_mobs().get_mob(j).id + "\r\n";

      return output;
    }

    public int link_rooms()
    
    {
      int count = 0;
      
      for (int i=0; i<size; i++)
        for (int j=0; j<size; j++)
          count += outgoing_link(i, j);
      
      return count;
    }
    
    public int outgoing_link(int source, int dest)
    
    {
      int count = 0;
      
      if (LIST[source].get_exit(0) == LIST[dest].get_id())
      if (LIST[dest].get_exit(1) != LIST[source].get_id())
      {
        count++;
        LIST[dest].set_exit(1, LIST[source].get_id());
      }
      
      if (LIST[source].get_exit(1) == LIST[dest].get_id())
      if (LIST[dest].get_exit(0) != LIST[source].get_id())
      {
        count++;
        LIST[dest].set_exit(0, LIST[source].get_id());
      }
              
      if (LIST[source].get_exit(2) == LIST[dest].get_id())
      if (LIST[dest].get_exit(3) != LIST[source].get_id())
      {
        count++;
        LIST[dest].set_exit(3, LIST[source].get_id());
      }
      
      if (LIST[source].get_exit(3) == LIST[dest].get_id())
      if (LIST[dest].get_exit(2) != LIST[source].get_id())
      {
        count++;
        LIST[dest].set_exit(2, LIST[source].get_id());
      }
      
      if (LIST[source].get_exit(4) == LIST[dest].get_id())
      if (LIST[dest].get_exit(5) != LIST[source].get_id())
      {
        count++;
        LIST[dest].set_exit(5, LIST[source].get_id());
      }
      
      if (LIST[source].get_exit(5) == LIST[dest].get_id())
      if (LIST[dest].get_exit(4) != LIST[source].get_id())
      {
        count++;
        LIST[dest].set_exit(4, LIST[source].get_id());         
      }
      
      return count;
    }      
    
    public void default_terrain()
    
    {
      for (int i=0; i<size; i++)
        LIST[i].terrain = frmMain.main_pointer.cboZoneTerrain.Text;
    }
    
    public bool room_in_zone(string id)
    
    {
      if (id == "")
        return false;
        
      for (int i=0; i<size; i++)
        if (LIST[i].get_id() == id)
          return true;
          
      return false;
    }
    
    public string debug_room(int source)
    
    {
      string output = "";
       
      if (LIST[source].get_exit(0) != null)
      if (!room_in_zone(LIST[source].get_exit(0)))
      
      {
        output += "ROOM " + LIST[source].get_id().ToString() + ": North exit (";
        output += LIST[source].get_exit(0).ToString() + ") links to a room not in the zone.\r\n";
      }
      
      if (LIST[source].get_exit(1) != null)
      if (!room_in_zone(LIST[source].get_exit(1)))
      
      {
        output += "ROOM " + LIST[source].get_id().ToString() + ": South exit (";
        output += LIST[source].get_exit(1).ToString() + ") links to a room not in the zone.\r\n";
      }
      
      if (LIST[source].get_exit(2) != null)
      if (!room_in_zone(LIST[source].get_exit(2)))
      
      {
        output += "ROOM " + LIST[source].get_id().ToString() + ": East exit (";
        output += LIST[source].get_exit(2).ToString() + ") links to a room not in the zone.\r\n";
      }
      
      if (LIST[source].get_exit(3) != null)
      if (!room_in_zone(LIST[source].get_exit(3)))
      
      {
        output += "ROOM " + LIST[source].get_id().ToString() + ": West exit (";
        output += LIST[source].get_exit(3).ToString() + ") links to a room not in the zone.\r\n";
      }
      
      if (LIST[source].get_exit(4) != null)
      if (!room_in_zone(LIST[source].get_exit(4)))
      
      {
        output += "ROOM " + LIST[source].get_id().ToString() + ": Up exit (";
        output += LIST[source].get_exit(4).ToString() + ") links to a room not in the zone.\r\n";
      }
      
      if (LIST[source].get_exit(5) != null)
      if (!room_in_zone(LIST[source].get_exit(5)))
      
      {
        output += "ROOM " + LIST[source].get_id().ToString() + ": Down exit (";
        output += LIST[source].get_exit(5).ToString() + ") links to a room not in the zone.\r\n";
      }
      
      return output;
    }
    
    public string debug_rooms()
    
    {
      string output = "";
      
      for (int i=0; i<size; i++)
        output += debug_room(i);
        
      if (output == "") output = "No erroneous room exits detected.";
      
      return output;
    }
  }
}