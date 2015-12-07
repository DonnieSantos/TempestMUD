using System;
using System.Collections;

namespace Tempest_Builder

{
  public class mob

  {
    public string id, name, keywords, rdesc, ldesc, level,
                  hitpoints, mana, experience, gold, allign,
                  speed, strength, wisdom, intelligence,
                  dexerity, constitution, armor_class,
                  magic_resistance, hitroll, damroll, gender,
                  shop_name, btype, bdir;

    public bool NPC, merchant, banker, bard, mobile, guard,
                skillmaster, spellmaster, blocker, aggro,
                undead, sancd, flying, clergy, teacher,
                herbalist;

    public ArrayList load_items_freq,
                     skills, spells,
                     wear_items_freq,
                     actions;

    public itemlist litems,
                    sitems,
                    witems;

    public string create_record()

    {
      string output;

      output = id + " " + name + "\r\n";
      output += keywords + "\r\n";
      output += rdesc + "\r\n";
      output += "BEGIN\r\n";
      output += variable_allign(ldesc, 80) + "\r\n";
      output += "END\r\n";
      output += "Level " + level + "\r\n";
      output += "HP/MN = " + hitpoints + " " + mana + "\r\n";
      output += "Stats = " + strength + " " + dexerity + " " + constitution + " " + intelligence + " " + wisdom + "\r\n";
      output += "WStats = " + hitroll + " " + damroll + " " + armor_class + " " + magic_resistance + "\r\n";
      output += "Exp = " + experience + "\r\n";
      output += "Gold = " + gold + "\r\n";
      output += "Gender = " + gender.ToLower() + "\r\n";
      output += "Allign = " + allign + "\r\n";
      output += "Speed = " + speed + "\r\n";

      if (NPC) output += "FLAG NPC\r\n";
      if (merchant) output += "FLAG MERCHANT " + shop_name + "\r\n";
      if (banker) output += "FLAG BANKER\r\n";
      if (bard) output += "FLAG BARD\r\n";
      if (mobile) output += "FLAG MOBILE\r\n";
      if (guard) output += "FLAG GUARD\r\n";
      if (skillmaster) output += "FLAG SKILLMASTER\r\n";
      if (spellmaster) output += "FLAG SKILLMASTER\r\n";
      if (aggro) output += "FLAG AGGRO\r\n";
      if (undead) output += "FLAG UNDEAD\r\n";
      if (sancd) output += "FLAG SANCD\r\n";
      if (flying) output += "FLAG FLYING\r\n";
      if (clergy) output += "FLAG CLERGY\r\n";
      if (teacher) output += "FLAG TEACHER\r\n";
      if (herbalist) output += "FLAG HERBALIST\r\n";
      if (blocker) output += "BLOCKER " + bdir + " " + btype + "\r\n";

      if (sitems.get_size() > 0) 

      {
        output += "SELL";
        for (int i=0; i<sitems.get_size(); i++)
          output += " " + sitems.get_item(i).id;
        output += "\r\n";
      }

      for (int i=0; i<skills.Count; i++)
        output += "SKILL [" + last((string)skills[i]) + "] " + first((string)skills[i]) + "\r\n";

      for (int i=0; i<spells.Count; i++)
        output += "SKILL [" + last((string)spells[i]) + "] " + first((string)spells[i]) + "\r\n";

      for (int i=0; i<actions.Count; i++)
        output += "ACT [" + last((string)actions[i]) + "] " + first((string)actions[i]) + "\r\n";
      
      for (int i=0; i<litems.get_size(); i++)
        output += "ITEM " + litems.get_item(i).id + " " + (string)load_items_freq[i] + "\r\n";

      for (int i=0; i<witems.get_size(); i++)
        output += "WEAR " + witems.get_item(i).id + " " + (string)wear_items_freq[i] + "\r\n";

      output += "[End]\r\n";

      return output;
    }

    public mob()

    {
      load_items_freq = new ArrayList();
      wear_items_freq = new ArrayList();
      skills = new ArrayList();
      spells = new ArrayList();
      actions = new ArrayList();

      litems = new itemlist();
      sitems = new itemlist();
      witems = new itemlist();

      id = "";
      name = "Empty";
      keywords = "Empty";
      rdesc = "Empty";
      ldesc = "Empty";
      level = "0";
      hitpoints = "0";
      mana = "0";
      experience = "0";
      gold = "0";
      allign = "0";
      speed = "0";
      strength = "0";
      wisdom = "0";
      intelligence = "0";
      dexerity = "0";
      constitution = "0";
      armor_class = "0";
      magic_resistance = "0";
      hitroll = "0";
      damroll = "0";
      gender = "Male";
      shop_name = "";
      btype = "";
      bdir = "";
    }
    
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

          if (index < str.Length) 
          {

            cut = index-1;
            drop = index;

            if ((str[cut] != ' ') && (str[drop] == ' ')) 
            {
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

      while (str.IndexOf(" \r\n") != -1) 
      {
        int x = str.IndexOf(" \r\n");
        str = str.Remove(x, 1); }

      return str;
    }
    private string first(string s)

    {
      string temp;
      int i = s.IndexOf(" ");

      if (i == -1)
        return s;

      temp = s.Substring(0, i);
      return temp;
    }

    private string last(string s)

    {
      if (s == "") return "";
      if (s.IndexOf(" ") == -1) return "";

      int i = s.IndexOf(" ");
      if (i < 0) return "";

      return s.Substring(i+1, s.Length-i-1);
    }
  }
}
