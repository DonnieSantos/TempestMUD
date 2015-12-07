using System;
using System.Collections;

namespace Tempest_Builder

{
  public class item

  {
    public string id, name, lname, lookatdesc, grounddesc,
                  level, reqstatStr, reqstatDex, reqstatWis, reqstatCon,
                  reqstatInt, hp, mana, move, hit, dam, ac, res,
                  board_name, max_items, worth, charges, btype, bdir,
                  uses, liquid, lightticks, exact_description;

    public bool Wa, Kn, Br, Pa, Dk, Cr, Ap, Th, As, Rg, Ra, Sb, Me,
                Mc, Cl, Dr, Mo, Pr, Dc, Hl, Al, Mg, Wi, Il,
                Wl, Sc, Sm, Sh;
    
    public bool weapon, shield, lhand, rhand, body, head, feet, back, 
                hands, waist, legs, lear, rear, lwrist, rwrist, lfinger, 
                rfinger, neck;
    
    public bool normal, container, board, scroll, equipment, 
                eq_container, consumable_drink, refillable_drink, 
                fountain, food, blocker, light;
    
    public bool perish, ingredient, no_get, no_sacrifice, no_drop, 
                glowing, humming, invisible, untouchable, neutral,
                good, evil, burning, freezing, shocking, no_rent;

    public bool stab, slash, pierce, bludgeon, chop, whip, arrow, star,
                fist, thrust, gouge, blast;
                
    public bool exact_look;

    public ArrayList potion_spells, loadIDs, innate_spells, charged_spells;

    public item()

    {
      id = "0";
      name = "Empty";
      lname = "Empty";
      lookatdesc = "Empty";
      grounddesc = "Empty";
      level = "0";
      reqstatStr = "0";
      reqstatDex = "0";
      reqstatWis = "0";
      reqstatCon = "0";
      reqstatInt = "0";
      hp = "0";
      mana = "0";
      move = "0";
      hit = "0";
      dam = "0";
      ac = "0";
      res = "0";
      board_name = "Empty";
      max_items = "0";
      worth = "0";
      charges = "0";
      uses = "0";
      liquid = "Empty";
      exact_description = "";

      Wa = Kn = Br = Pa = Dk = Cr = Ap = Th = As =
      Rg = Ra = Sb = Me = Mc = Cl = Dr = Mo = Pr =
      Dc = Hl = Al = Mg = Wi = Il = Wl = Sc = Sm =
      Sh = false;

      stab = slash = pierce = bludgeon = chop = whip = arrow = star =
      fist = thrust = gouge = blast = false;

      perish = ingredient = no_get = no_sacrifice = no_drop = 
      glowing = humming = invisible = untouchable = neutral = 
      good = evil = burning = freezing = shocking = no_rent = false;

      normal = container = board = scroll = light =
      equipment = eq_container = consumable_drink =
      refillable_drink = fountain = food = false;

      weapon = shield = lhand = rhand = body = head = feet = back = 
      hands = waist = legs = lear = rear = lwrist = rwrist = lfinger = 
      rfinger = neck = false;
      
      exact_look = false;
      
      potion_spells = new ArrayList();
      loadIDs = new ArrayList();
      innate_spells = new ArrayList();
      charged_spells = new ArrayList();
    }

    public string create_record()

    {
      string output;

      output = "ITEM: " + id + "\r\n";
      output += "TYPE: ";

      if (normal)                output += "GENERIC";
      else if (light)            output += "LIGHT";
      else if (blocker)          output += "BLOCKER";
      else if (container)        output += "CONTAINER";
      else if (board)            output += "BOARD";
      else if (scroll)           output += "SCROLL";
      else if (equipment)        output += "EQUIPMENT";
      else if (eq_container)     output += "CONTAINEREQ";
      else if (consumable_drink) output += "DRINK";
      else if (refillable_drink) output += "DRINKCONTAINER";
      else if (fountain)         output += "FOUNTAIN";
      else if (food)             output += "FOOD";

      output += "\r\n";

      output += "NAME: " + name + "\r\n";
      output += "LNAME: " + lname + "\r\n";
      output += "GDESC: " + grounddesc + "\r\n";
      output += "LEVEL: " + level + "\r\n";
      output += "WORTH: " + worth + "\r\n";

      if (equipment || eq_container || light)

      {

        output += "STATS: " + hit + " " + dam + " " + ac + " " + res + " " + hp + " " + mana + " " + move + "\r\n";
        output += "SREQS: " + reqstatStr + " " + reqstatDex + " " + reqstatCon + " " + reqstatInt + " " +reqstatWis + "\r\n";
        output += "CLASS:";

        if (Wa && Kn && Br && Pa && Dk && Cr && Ap && Th && As && Rg && Ra && Sb && Me && Mc 
          && Cl && Dr && Mo && Pr && Dc && Hl && Al && Mg && Wi && Il && Wl && Sc && Sm && Sh)
          output += " All";
          
        else if (!Wa && !Kn && !Br && !Pa && !Dk && !Cr && !Ap && !Th && !As && !Rg && !Ra && !Sb && !Me && !Mc 
          && !Cl && !Dr && !Mo && !Pr && !Dc && !Hl && !Al && !Mg && !Wi && !Il && !Wl && !Sc && !Sm && !Sh)
          output += " None";
          
        else

        {
          if (Wa) output += " Wa" ; if (Kn) output += " Kn" ; if (Br) output += " Br" ; if (Pa) output += " Pa" ;
          if (Dk) output += " Dk" ; if (Cr) output += " Cr" ; if (Ap) output += " Ap" ; if (Th) output += " Th" ;
          if (As) output += " As" ; if (Rg) output += " Rg" ; if (Ra) output += " Ra" ; if (Sb) output += " Sb" ;
          if (Me) output += " Me" ; if (Mc) output += " Mc" ; if (Cl) output += " Cl" ; if (Dr) output += " Dr" ;
          if (Mo) output += " Mo" ; if (Pr) output += " Pr" ; if (Dc) output += " Dc" ; if (Hl) output += " Hl" ;
          if (Al) output += " Al" ; if (Mg) output += " Mg" ; if (Wi) output += " Il" ; if (Il) output += " Il" ;
          if (Wl) output += " Wl" ; if (Sc) output += " Sc" ; if (Sm) output += " Sm" ; if (Sh) output += " Sh" ;
        }

        output += "\r\nPLACE:";

        if (lhand && rhand) output += " Both_Hands";
        else if (lear && rear) output += " Both_Ears";
        else if (lwrist && rwrist) output += " Both_Wrists";
        else if (lfinger && rfinger) output += " Both_Fingers";
        else

        {
          if (weapon) output += " Weapon"; 
          if (shield) output += " Shield";
          if (lhand) output += " Left_Hand"; 
          if (rhand) output += " Right_Hand";
          if (body) output += " Body"; 
          if (head) output += " Head";
          if (feet) output += " Feet"; 
          if (back) output += " Back"; 
          if (hands) output += " Hands";
          if (waist) output += " Waist"; 
          if (legs) output += " Legs"; 
          if (rear) output += " Right_Ear";
          if (lear) output += " Left_Ear"; 
          if (rwrist) output += " Right_Wrist";
          if (lwrist) output += " Left_Wrist";
          if (rfinger) output += " Right_Finger"; 
          if (lfinger) output += " Left_Finger"; 
          if (neck) output += " Neck";
        }

        if (!weapon && !shield && !lhand && !rhand && !body && !head && !feet && !back && !hands && !waist && !legs 
          && !lear && !rear && !lwrist && !rwrist && !lfinger && !rfinger && !neck)
          output += " None";

        output += "\r\n";

        for (int i=0; i<innate_spells.Count; i++)
          output += "INNATE: " + (string)innate_spells[i] + "\r\n";

        for (int i=0; i<charged_spells.Count; i++)
          output += "CHARGED: " + (string)charged_spells[i] + "\r\n";

        if (charged_spells.Count > 0)
          output += "CHARGES: " + charges + "\r\n";

        if (stab) output += "DTYPE: STAB\r\n";
        else if (slash) output += "DTYPE: SLASH\r\n";
        else if (pierce) output += "DTYPE: PIERCE\r\n";
        else if (bludgeon) output += "DTYPE: BLUDGEON\r\n";
        else if (chop) output += "DTYPE: CHOP\r\n";
        else if (whip) output += "DTYPE: WHIP\r\n";
        else if (arrow) output += "DTYPE: ARROW\r\n";
        else if (star) output += "DTYPE: STAR\r\n";
        else if (fist) output += "DTYPE: FIST\r\n";
        else if (thrust) output += "DTYPE: THRUST\r\n";
        else if (gouge) output += "DTYPE: GOUGE\r\n";
        else if (blast) output += "DTYPE: BLAST\r\n";
      }

      if (board) output += "BOARDNAME: " + board_name + "\r\n";
      if (blocker) output += "BTYPE: " + bdir + " " + btype + "\r\n";

      if (light) output += "LTICKS: " + lightticks + "\r\n";
      if ((refillable_drink) || (fountain) || (consumable_drink))
        
      {
        output += "LTYPE: " + liquid + "\r\n";
        output += "USES: " + uses + "\r\n";
        output += "MAXUSES: " + uses + "\r\n";
      }

      if (scroll)

      {
        for (int i=0; i<potion_spells.Count; i++)
          output += "CSPELL: " + (string)potion_spells[i] + "\r\n";
      }

      if (container || eq_container)

      {
        output += "MAXITEMS: " + max_items + "\r\n";
        
        for (int i=0; i<loadIDs.Count; i++)
          output += "LOADITEM: " + (string)loadIDs[i] + "\r\n";
      }

      if (perish)       output += "FLAG: PERISH\r\n";
      if (ingredient)   output += "FLAG: INGREDIENT\r\n";
      if (no_get)       output += "FLAG: NO_GET\r\n";
      if (no_sacrifice) output += "FLAG: NO_SACRIFICE\r\n";
      if (no_drop)      output += "FLAG: NO_DROP\r\n";
      if (glowing)      output += "FLAG: GLOWING\r\n";
      if (humming)      output += "FLAG: HUMMING\r\n";
      if (invisible)    output += "FLAG: INVISIBLE\r\n";
      if (untouchable)  output += "FLAG: UNTOUCHABLE\r\n";
      if (neutral)      output += "FLAG: NEUTRAL\r\n";
      if (good)         output += "FLAG: GOOD\r\n";
      if (evil)         output += "FLAG: EVIL\r\n";
      if (burning)      output += "FLAG: BURNING\r\n";
      if (freezing)     output += "FLAG: FREEZING\r\n";
      if (shocking)     output += "FLAG: SHOCKING\r\n";
      if (no_rent)      output += "FLAG: NO_RENT\r\n";
      
      if (exact_look)
      
      {
        output += "BEGIN EXACT\r\n";
        output += exact_description + "\r\n";
        output += "END\r\n";
      }
      
      else
      
      {
        output += "BEGIN\r\n";
        output += variable_allign(lookatdesc, 80) + "\r\n";
        output += "END\r\n";
      }

      return output;
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
  }
}
