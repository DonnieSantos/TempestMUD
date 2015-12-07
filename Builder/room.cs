using System;
using System.Collections;

namespace Tempest_Builder

{
  public class room

  {
    private string name,
      id,
      desc;

    private string[] room_exits;

    private ArrayList look_keys,
      look_descs,
      exit_keys,
      exit_descs,
      actions;

    private moblist mlist;
    private itemlist ilist;

    public string terrain, exact_description = "";
    public bool exact = false;
    
    private bool PKOK, LAWFULL, NO_MAGIC;
    private bool INDOORS, NO_SUMMON;
    private bool NO_QUIT, SILENT, PRIVATE;
    private bool NO_DROP, NO_MOB, DONATION;
    public bool DARK, CHURCH, TUNNEL, DAMAGE;
    public bool DEATHTRAP, UNDERWATER, GODROOM;
    public bool SHROUD, NO_TRACK, NO_SCOUT;
    public bool NO_DISPEL, NO_TELEPORT, NO_FLEE;
    public bool NO_SPELL, ANIMATE;
    public bool REGEN_HP, REGEN_MN;

    public room()

    {
      id   = "";
      name = "";
      desc = "";

      room_exits = new string[6];
      look_keys  = new ArrayList();
      look_descs = new ArrayList();
      exit_keys  = new ArrayList();
      exit_descs = new ArrayList();
      actions = new ArrayList();
      mlist = new moblist();
      ilist = new itemlist();

      for (int i=0; i<6; i++)
        room_exits[i] = null;
        
      terrain = frmMain.main_pointer.cboZoneTerrain.Text;
    }

    public string create_record(string scheme)

    {
      string output;

      output = "ROOM " + id + "\r\n";

      if (PKOK)      output += "FLAG PKOK\r\n";
      if (LAWFULL)   output += "FLAG LAWFULL\r\n";
      if (NO_MAGIC)  output += "FLAG NO_MAGIC\r\n";
      if (INDOORS)   output += "FLAG INDOORS\r\n";
      if (NO_SUMMON) output += "FLAG NO_SUMMON\r\n";
      if (NO_QUIT)   output += "FLAG NO_QUIT\r\n";
      if (SILENT)    output += "FLAG SILENT\r\n";
      if (PRIVATE)   output += "FLAG PRIVATE\r\n";
      if (NO_DROP)   output += "FLAG NO_DROP\r\n";
      if (NO_MOB)    output += "FLAG NO_MOB\r\n";
      if (DONATION)  output += "FLAG DONATION\r\n";
      if (DARK)      output += "FLAG DARK\r\n";
      if (CHURCH)    output += "FLAG CHURCH\r\n";
      if (TUNNEL)    output += "FLAG TUNNEL\r\n";
      if (DAMAGE)    output += "FLAG DAMAGE\r\n";
      if (DEATHTRAP) output += "FLAG DEATHTRAP\r\n";
      if (UNDERWATER) output += "FLAG UNDERWATER\r\n";
      if (GODROOM)   output += "FLAG GODROOM\r\n";
      if (SHROUD)    output += "FLAG SHROUD\r\n";
      if (NO_TRACK)  output += "FLAG NO_TRACK\r\n";
      if (NO_SCOUT)  output += "FLAG NO_SCOUT\r\n";
      if (NO_DISPEL) output += "FLAG NO_DISPEL\r\n";
      if (NO_TELEPORT) output += "FLAG NO_TELEPORT\r\n";
      if (NO_FLEE)   output += "FLAG NO_FLEE\r\n";
      if (NO_SPELL)  output += "FLAG NO_SPELL\r\n";
      if (ANIMATE)   output += "FLAG ANIMATE\r\n";
      if (REGEN_HP)  output += "FLAG REGEN_HP\r\n";
      if (REGEN_MN)  output += "FLAG REGEN_MN\r\n";
      
      output += "TERRAIN: " + terrain + "\r\n";
      
      if (exact)
      
      {
        output += "BEGIN EXACT\r\n";
        output += scheme + name + "#N\r\n";
        output += exact_description + "\r\n";
        output += "END\r\n";
      }
      
      else
      
      {
        output += "BEGIN\r\n";
        output += scheme + name + "#N\r\n";
        output += variable_allign(Utility.clear_whitespace(desc), 80) + "\r\n";
        output += "END\r\n";
      }

      for (int i=0; i<mlist.get_size(); i++)
        output += "LOAD_MOB " + mlist.get_mob(i).id + "\r\n";

      for (int i=0; i<ilist.get_size(); i++)
        output += "LOAD_ITEM " + ilist.get_item(i).id + "\r\n";

      if (room_exits[0] != null) output += "NORTH " + room_exits[0] + "\r\n";
      if (room_exits[1] != null) output += "SOUTH " + room_exits[1] + "\r\n";
      if (room_exits[2] != null) output += "EAST " + room_exits[2] + "\r\n";
      if (room_exits[3] != null) output += "WEST " + room_exits[3] + "\r\n";
      if (room_exits[4] != null) output += "UP " + room_exits[4] + "\r\n";
      if (room_exits[5] != null) output += "DOWN " + room_exits[5] + "\r\n";

      for (int i=0; i<exit_keys.Count; i++)

      {
        output += "\r\nLOOK " + id + " " + (string)exit_keys[i] + "\r\n";
        output += "BEGIN\r\n";
        output += variable_allign((string)exit_descs[i], 80) + "\r\n";
        output += "END\r\n";
      }

      for (int i=0; i<look_keys.Count; i++)

      {
        output += "\r\nLOOK " + id + " " + (string)look_keys[i] + "\r\n";
        output += "BEGIN\r\n";
        output += variable_allign((string)look_descs[i], 80) + "\r\n";
        output += "END\r\n";
      }

      return output;
    }

    #region Get and Set functions

    public moblist get_mobs()

    {
      return mlist;
    }

    public void set_mobs(moblist ml)

    {
      mlist = ml;
    }

    public ArrayList get_actions()

    {
      return actions;
    }

    public void set_actions(ArrayList al)

    {
      actions = al;
    }

    public itemlist get_ilist()

    {
      return ilist;
    }

    public void set_ilist(itemlist il)

    {
      ilist = il;
    }

    public void set_keys(ArrayList AL)

    {
      look_keys = AL;
    }

    public void set_descs(ArrayList AL)

    {
      look_descs = AL;
    }

    public void set_exitkeys(ArrayList AL)

    {
      exit_keys = AL;
    }

    public void set_exitdescs(ArrayList AL)

    {
      exit_descs = AL;
    }

    public ArrayList get_exitkeys()

    {
      return exit_keys;
    }

    public ArrayList get_exitdescs()

    {
      return exit_descs;
    }

    public ArrayList get_keys()

    {
      return look_keys;
    }

    public ArrayList get_descs()

    {
      return look_descs;
    }

    public void set_exit(int i, string str)

    {
      room_exits[i] = str;
    }

    public string get_exit(int i)

    {
      return room_exits[i];
    }

    public void set_donation(bool val)

    {
      DONATION = val;
    }

    public void set_nomob(bool val)

    {
      NO_MOB = val;
    }

    public void set_nodrop(bool val)

    {
      NO_DROP = val;
    }

    public void set_private(bool val)

    {
      PRIVATE = val;
    }

    public void set_silent(bool val)

    {
      SILENT = val;
    }

    public void set_noquit(bool val)

    {
      NO_QUIT = val;
    }

    public void set_nosummon(bool val)

    {
      NO_SUMMON = val;
    }

    public void set_indoors(bool val)

    {
      INDOORS = val;
    }

    public void set_nomagic(bool val)

    {
      NO_MAGIC = val;
    }

    public void set_lawfull(bool val)

    {
      LAWFULL = val;
    }

    public void set_pkok(bool val)

    {
      PKOK = val;
    }

    public void set_id(string str)

    {
      id = str;
    }

    public void set_name(string str)

    {
      name = str;
    }

    public void set_desc(string str)

    {
      desc = str;
    }

    public bool get_donation()

    {
      return DONATION;
    }

    public bool get_nomob()

    {
      return NO_MOB;
    }

    public bool get_nodrop()

    {
      return NO_DROP;
    }

    public bool get_private()

    {
      return PRIVATE;
    }

    public bool get_silent()

    {
      return SILENT;
    }

    public bool get_noquit()

    {
      return NO_QUIT;
    }

    public bool get_nosummon()

    {
      return NO_SUMMON;
    }

    public bool get_indoors()

    {
      return INDOORS;
    }

    public bool get_nomagic()

    {
      return NO_MAGIC;
    }

    public bool get_lawfull()

    {
      return LAWFULL;
    }

    public bool get_pkok()

    {
      return PKOK;
    }

    public string get_id()

    {
      return id;
    }

    public string get_name()

    {
      return name;
    }

    public string get_desc()

    {
      return desc;
    }
    #endregion

    private bool color_character(char c)

    {
      if ((c == 'R') || (c == 'r') || (c == 'B') || (c == 'b')
        || (c == 'G') || (c == 'g') || (c == 'Y') || (c == 'y')
        || (c == 'M') || (c == 'm') || (c == 'C') || (c == 'c')
        || (c == 'N') || (c == 'n')) return true;

      return false;
    }


    private string variable_allign(string str, int vline_length)

    {
      int index = vline_length;
      int cut, drop, count;
      int last = 1;

      if (str.Length > vline_length)

      {
        while (index < str.Length)

        {
          for (int i=last; i<index; i++)
            if ((str[i-1] == '#') && (color_character(str[i])))
              index = index+2;

          if (index < str.Length) {

          cut = index-1;
          drop = index;

          if ((str[cut] != ' ') && (str[drop] == ' ')) {
            str = str.Insert(index, "\r\n");
            str = str.Remove(drop+2, 1); }

          else if ((str[cut] != ' ') && (str[drop] != ' '))

          {
            count = 0;
            while ((str[cut] != ' ') && (str[cut] != '\n')) { count++; cut--; }
            while ((str[drop-1] != ' ') && (drop < str.Length)) { count++; drop++; }
            cut = index-1;

            if (count <= vline_length)

            {
              while (str[cut] != ' ')
                cut--;

              for (int i=1; i<=(index-1-cut); i++)
                str = str.Insert(cut, " ");

              str = str.Insert(index, "\r\n");
            }

            else str = str.Insert(index, "\r\n");
          }

          else str = str.Insert(index, "\r\n");

          last = index;
          index = index + vline_length+2; }
        }
      }

      while (str.IndexOf(" \r\n") != -1) {
        int x = str.IndexOf(" \r\n");
        str = str.Remove(x, 1); }

      return str;
    }
  }
}