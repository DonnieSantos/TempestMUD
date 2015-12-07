import java.util.*;
import java.io.*;

public class Clan extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 9;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int clanID;
  private String clanName;
  private Writable clanInfo;
  private ArrayList members;
  private ArrayList maleRankNames;
  private ArrayList femaleRankNames;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getName()            { return clanName;                           }
  public int getID()                 { return clanID;                             }
  public int getNumRanks()           { return maleRankNames.size()-2;             }
  public String getMaleRank(int i)   { return (String) maleRankNames.get(i);      }
  public String getFemaleRank(int i) { return (String) femaleRankNames.get(i);    }
  public ClanMember getMember(int i) { return (ClanMember) members.get(i);        }
  public int getRank(int i)          { return getMember(i).getRank();             }
  public String getCharClass(int i)  { return getMember(i).getCharClass();        }
  public String getListName(int i)   { return properName(getMember(i).getName()); }
  public static Clan getClan(int i)  { return World.getClan(i);                   }
  public int getNumMembers()         { return members.size();                     }
  public void setName(String s)      { clanName = s;                              }
  public Writable getClanInfo()      { return clanInfo;                           }
  public ArrayList getMaleRanks()    { return maleRankNames;                      }
  public ArrayList getFemaleRanks()  { return femaleRankNames;                    }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Clan()

  {
    clanID = 0;
    clanName = "Generic Clan";
    clanInfo = new Writable("Clan Info", Writable.FORMAT_CDESC);
    members = new ArrayList();
    maleRankNames = new ArrayList();
    femaleRankNames = new ArrayList();

    maleRankNames.add(new String("Generic Applicant"));
    maleRankNames.add(new String("Generic Member"));
    maleRankNames.add(new String("Generic Council"));
    maleRankNames.add(new String("Generic Leader"));
    maleRankNames.add(new String("Generic Immortal"));

    femaleRankNames.add(new String("Generic Applicant"));
    femaleRankNames.add(new String("Generic Member"));
    femaleRankNames.add(new String("Generic Council"));
    femaleRankNames.add(new String("Generic Leader"));
    femaleRankNames.add(new String("Generic Immortal"));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Clan(int ID, String name)

  {
    this();
    clanID = ID;
    clanName = name;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void validateMembers()

  {
    for (int i=members.size()-1; i>=0; i--)
    if (!Char.fileExists(getListName(i)))
    members.remove(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void saveClan()

  {
    FileManager.saveClan(this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearClan()

  {
    members = new ArrayList();
    FileManager.saveClan(this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void xecho(Entity ENT1, Entity ENT2, String msg)

  {
    for (int i=0; i<ClientList.getSize(); i++)
    if (ClientList.getClient(i).getCharInfo().getClanNum() == clanID)
    if (ClientList.getClient(i).getCharInfo() != ENT1)
    if (ClientList.getClient(i).getCharInfo() != ENT2)
      ClientList.getClient(i).addOutput(msg);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addMember(String name, int rank, String profession)

  {
    ClanMember CM = new ClanMember(name, rank, profession);
    members.add(CM);
    saveClan();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addMember(Entity ENT)

  {
    ClanMember CM = new ClanMember(ENT.getName(), 0, ENT.getCharClass());
    members.add(CM);
    saveClan();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeMember(String name)

  {
    int pos = searchMember(name);
    if (pos == -1) return;
    members.remove(pos);
    saveClan();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int searchMember(String name)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getName().equalsIgnoreCase(name)) return i;
    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getMemberRank(String name)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getName().equalsIgnoreCase(name))
    return getMember(i).getRank();
    return -1;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getMemberRankName(String name, String gender)

  {
    int rankNum = getMemberRank(name);
    if (rankNum == -1) return "";
    if (name.equalsIgnoreCase("male")) return getMaleRank(rankNum);
    return getFemaleRank(rankNum);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getMemberClass(String name)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getName().equalsIgnoreCase(name))
    return getMember(i).getCharClass();
    return "NotInClan";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setMemberRank(String name, int rank)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getName().equalsIgnoreCase(name))
    getMember(i).setRank(rank);
    saveClan();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setMemberName(String name, String newName)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getName().equalsIgnoreCase(name))
    getMember(i).setName(newName);
    saveClan();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setMemberClass(String name, String profession)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getName().equalsIgnoreCase(name))
    getMember(i).setClass(profession);
    saveClan();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void updateMember(String name, Entity ENT, boolean save)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getName().equalsIgnoreCase(name))

    {
      getMember(i).setName(ENT.getName());
      getMember(i).setClass(ENT.getCharClass());
    }

    if (save) saveClan();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setRankName(int rank, String male, String female)

  {
    if (rank >= getNumRanks()) return;
    maleRankNames.set(rank, male);
    femaleRankNames.set(rank, female);
    saveClan();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getNumApplicants()

  {
    int count = 0;
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getRank() == 0) count++;
    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getNumCouncil()

  {
    int count = 0;
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getRank() == getNumRanks()-1) count++;
    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getNumLeaders()

  {
    int count = 0;
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getRank() == getNumRanks()) count++;
    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getNumImms()

  {
    int count = 0;
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getRank() == getNumRanks()+1) count++;
    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void apply(Entity SRC, String target)

  {
    String s = SRC.getName();
    int myClan = SRC.getClanNum();

    if (myClan >= 0) {
      Clan C = World.getClan(myClan);
      if (C.getMemberRank(s) > 0) SRC.echo("You are already in a clan.");
      else SRC.echo("You are already applying to a clan.");
      return; }

    if (target.length() <= 0) {
      SRC.echo("Which clan do you want to apply to?");
      return; }

    Clan C = World.getClan(target);

    if (C == null) {
      SRC.echo("That clan does not exist.");
      return; }

    String c = C.getName();

    C.addMember(SRC.getLName(), 0, SRC.getCharClass());
    SRC.setClan(C.getID());
    SRC.castChar().save();

    SRC.echo("You are applying for membership in the clan " + c + "#N.");
    C.xecho(SRC, SRC, s + " is applying for membership in " + c + "#N.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void enlist(Entity SRC, String target)

  {
    int nr = 0;
    Clan C = null;

    int myClan = SRC.getClanNum();

    if (myClan != -1) {
      C = getClan(myClan);
      nr = C.getNumRanks(); }

    Entity ENT = ClientList.findCharacter(target);

    try {

    if (C == null)
      throw ex("You are not in a clan to enlist anybody.");
    if (C.getMemberRank(SRC.getName()) == 0)
      throw ex("Only accepted clan members can enlist applicants.");
    if (C.getMemberRank(SRC.getName()) < nr-1)
      throw ex("You must be at least clanrank " + (nr-1) + " to enlist members.");
    if (target.length() <= 0)
      throw ex("Who do you want to enlist into your clan?");
    if (ENT == null)
      throw ex("There is nobody around by that name.");
    if (ENT == SRC)
      throw ex("Enlist yourself into the clan that you are already in?  Okay...");
    if (ENT.getClanNum() != myClan)
      throw ex(ENT.getName() + " is not applying to your clan.");
    if (C.getMemberRank(ENT.getName()) > 0)
      throw ex(ENT.getName() + " is already in your clan!");
    }

    catch (Exception e) { SRC.echo(e.getMessage());  return; }

    int entrance_rank = 1;
    if (ENT.getLevel() >= 100) entrance_rank = nr+1;
    C.setMemberRank(ENT.getName(), entrance_rank);

    String s = SRC.getName();
    String t = ENT.getName();
    String c = C.getName();
    SRC.echo("You accept " + ENT.getName() + " into " + C.getName() + "#N.");
    ENT.echo(s + " has accepted you into the clan " + c + "#N.");
    C.xecho(SRC, ENT, s + " has accepted " + t + " into " + c + "#N.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void expel(Entity SRC, String target)

  {
    int nr = 0;
    Clan C = null;

    int myClan = SRC.getClanNum();

    if (myClan != -1) {
      C = getClan(myClan);
      nr = C.getNumRanks(); }

    Entity ENT = World.findCharacter(target);

    try {

    if (C == null)
      throw ex("You are not in a clan to expel anybody.");
    if (C.getMemberRank(SRC.getName()) == 0)
      throw ex("Only accepted clan members can expel others.");
    if (C.getMemberRank(SRC.getName()) < nr-1)
      throw ex("You must be at least clanrank " + (nr-1) + " to expel members.");
    if (target.length() <= 0)
      throw ex("Who do you want to expel from your clan?");
    if (target.equalsIgnoreCase(SRC.getName()))
      throw ex("Expel yourself?  It's a little easier to just leave the clan.");
    if (ENT == null)
      throw ex("There is no such person.");
    if (ENT.getClanNum() != myClan)
      throw ex(ENT.getName() + " is not a member of your clan.");
    if (C.getMemberRank(ENT.getName()) >= C.getMemberRank(SRC.getName()))
      throw ex("You cannot expel those of equal or higher rank than you.");

    String s = SRC.getName();
    String t = properName(target);
    String c = C.getName();

    C.removeMember(ENT.getName());
    ENT.setClan(-1);
    ENT.castChar().save();

    SRC.echo("You have expelled " + t + " from " + c + "#N.");
    ENT.echo(s + " has expelled you from the clan " + c + "#N.");
    C.xecho(SRC, SRC, s + " has expelled " + t + " from " + c + "#N.");

    } // End try.

    catch (Exception e) { SRC.echo(e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void raise(Entity SRC, String target)

  {
    int nr = 0;
    Clan C = null;

    int myClan = SRC.getClanNum();

    if (myClan != -1) {
      C = getClan(myClan);
      nr = C.getNumRanks(); }

    Entity ENT = World.findCharacter(target);

    try {

    if (C == null)
      throw ex("You are not in a clan to promote anybody.");
    if (C.getMemberRank(SRC.getName()) == 0)
      throw ex("Only accepted clan members can promote others.");
    if (C.getMemberRank(SRC.getName()) < nr-1)
      throw ex("You must be at least clanrank " + (nr-1) + " to promote members.");
    if (target.length() <= 0)
      throw ex("Who do you want to promote?");
    if (target.equalsIgnoreCase(SRC.getName()))
      throw ex("Promoting yourself, eh?  Nice try.");
    if (ENT == null)
      throw ex("There is no such person.");
    if (ENT.getClanNum() != myClan)
      throw ex(ENT.getName() + " is not a member of your clan.");
    if (C.getMemberRank(target) >= C.getMemberRank(SRC.getName()))
      throw ex("You cannot promote those of equal or higher rank than you.");
    if (C.getMemberRank(target) == 0)
      throw ex(ENT.getName() + " has not been enlisted into the clan yet.");
    if (C.getMemberRank(target) >= nr)
      throw ex(ENT.getName() + " has already achieved the highest clanrank.");

    String s = SRC.getName();
    String t = properName(target);
    String c = C.getName();

    int new_rank = C.getMemberRank(target) + 1;
    C.setMemberRank(target, new_rank);

    String nrank = c + " #N" + C.getMemberRankName(ENT.getName(), ENT.getGender());
    SRC.echo("You have raised " + t + " to the rank of " + nrank + ".");
    ENT.echo(s + " has raised you to the rank of " + nrank + ".");
    C.xecho(SRC, ENT, s + " has raised " + t + " to the rank of " + nrank + ".");

    } // End try.

    catch (Exception e) { SRC.echo(e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void demote(Entity SRC, String target)

  {
    int nr = 0;
    Clan C = null;

    int myClan = SRC.getClanNum();

    if (myClan != -1) {
      C = getClan(myClan);
      nr = C.getNumRanks(); }

    Entity ENT = World.findCharacter(target);

    try {

    if (C == null)
      throw ex("You are not in a clan to demote anybody.");
    if (C.getMemberRank(SRC.getName()) == 0)
      throw ex("Only accepted clan members can demote others.");
    if (C.getMemberRank(SRC.getName()) < nr-1)
      throw ex("You must be at least clanrank " + (nr-1) + " to demote members.");
    if (target.length() <= 0)
      throw ex("Who do you want to demote?");
    if (target.equalsIgnoreCase(SRC.getName()))
      throw ex("You cannot demote yourself.");
    if (ENT == null)
      throw ex("There is no such person.");
    if (ENT.getClanNum() != myClan)
      throw ex(ENT.getName() + " is not a member of your clan.");
    if (C.getMemberRank(target) >= C.getMemberRank(SRC.getName()))
      throw ex("You cannot demote those of equal or higher rank than you.");
    if (C.getMemberRank(target) == 0)
      throw ex(ENT.getName() + " has not been enlisted into the clan yet.");

    String s = SRC.getName();
    String t = properName(target);
    String c = C.getName();

    int new_rank = C.getMemberRank(target) - 1;
    C.setMemberRank(target, new_rank);

    String nrank = c + " #N" + C.getMemberRankName(ENT.getName(), ENT.getGender());
    SRC.echo("You have lowered " + t + " to the rank of " + nrank + ".");
    ENT.echo(s + " has lowered you to the rank of " + nrank + ".");
    C.xecho(SRC, ENT, s + " has lowered " + t + " to the rank of " + nrank + ".");

    } // End try.

    catch (Exception e) { SRC.echo(e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void resign(Entity SRC, String target)

  {
    int myClan = SRC.getClanNum();

    if (myClan == -1) {
      SRC.echo("You aren't even in a clan!");
      return; }

    if (target.indexOf("yes") == -1) {
      String temp = "";
      temp += "To resign from your clan type 'clan resign YES'.\r\n\r\n";
      temp += "WARNING: You will permanently lose your membership and rank.";
      SRC.echo(temp);
      return; }

    Clan C = getClan(myClan);
    C.removeMember(SRC.getName());
    SRC.setClan(-1);
    SRC.castChar().save();

    SRC.echo("You have resigned from the clan " + C.getName() + "#N.");
    C.xecho(SRC, SRC, SRC.getName() + " has resigned from " + C.getName() + "#N.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String displayClanlist()

  {
    String temp = "";
    int width = 14;

    for (int i=0; i<World.getClanSize(); i++)
    if ((getClan(i) != null) && (visibleSize(getClan(i).getName()) > width))
      width = visibleSize(getClan(i).getName());

    String border = "  #n+-" + xchars(width,"-") + "-+#N";
    temp += border + "\r\n  #n|#N Current Clans#n:#N";
    temp += xchars(width-14," ") + " #n|#N\r\n" + border + "\r\n";

    for (int i=0; i<World.getClanSize(); i++)
    if (getClan(i) != null) {
      int buf = width - visibleSize(getClan(i).getName());
      temp += "  #n|#N " + getClan(i).getName() + xchars(buf," ") + " #n|#N\r\n"; }

    temp += border;

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String displayInfo()

  {
    String name = clanName;
    String leader_string = "";
    String council_string = "";
    int width = getWidth(clanInfo.getString());
    int top_width = (19 + visibleSize(name));
    if (top_width > width) width = top_width;
    int sm_width = width-9;
    int name_spaces = width - visibleSize(name) - 19;

    String border = "  #n+-" + xchars(width,"-") + "-+#N";
    String cborder = "  #n|#N " + xchars(width," ") + " #n|#N";

    String temp = border + "\r\n" + "  #n|#N Clan Information#n:#N  ";
    temp += name + xchars(name_spaces," ") + " #n|#N\r\n" + border + "\r\n";

    if (getNumLeaders() <= 1) temp += "  #n|#N Leader#R:#N  ";
    else if (getNumLeaders() > 1) temp += "  #n|#N Leaders#R:#N ";

    for (int i=0; i<members.size(); i++)
    if (getRank(i) == getNumRanks()) {
      if (leader_string.length() > 0) leader_string += " ";
      leader_string += "#G" + getListName(i); }

    if (leader_string.length() <= 0) leader_string = "None";

    leader_string = variableAlign(leader_string, sm_width);
    boolean first_time = true;

    while (leader_string.length() > 0)

    {
      if (!first_time) temp += "  #n|#N          ";
      first_time = false;
      String str = getLine(leader_string);
      leader_string = removeLine(leader_string);
      temp += str + xchars(sm_width-visibleSize(str)," ") + " #n|#N\r\n";
    }

    temp += border + "\r\n" + "  #n|#N Council#R:#N ";

    for (int i=0; i<members.size(); i++)
    if (getRank(i) == getNumRanks()-1) {
      if (council_string.length() > 0) council_string += " ";
      council_string += "#C" + getListName(i); }

    if (council_string.length() <= 0) council_string = "None";

    council_string = variableAlign(council_string, sm_width);
    first_time = true;

    while (council_string.length() > 0)

    {
      if (!first_time) temp += "  #n|#N          ";
      first_time = false;
      String str = getLine(council_string);
      council_string = removeLine(council_string);
      temp += str + xchars(sm_width-visibleSize(str)," ") + " #n|#N\r\n";
    }

    temp += border + "\r\n" + cborder + "\r\n";
    String cinfo = clanInfo.getString();

    while (cinfo.length() > 0)

    {
      String str = getLine(cinfo);
      cinfo = removeLine(cinfo);
      temp += "  #n|#N " + str + xchars(width-visibleSize(str)," ") + " #n|#N\r\n";
    }

    temp += cborder + "\r\n" + border;

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String displayRoster()

  {
    String name = clanName;
    int size = members.size();
    int count = 0, mcount = 0, rc = 0;
    int width = visibleSize(name) + 14;
    int w1 = 0, w2 = 0, w3 = 0, w4 = 0;
    String Names[] = new String [size];
    String Class[] = new String [size];
    int Ranks[] = new int [size];

    for (int i=0; i<size; i++)
    if (getRank(i) > 0)

    {
      Names[mcount] = getListName(i);
      Class[mcount] = getCharClass(i);
      Ranks[mcount] = getRank(i);
      mcount++;
    }

    for (int i=0; i<mcount; i++)

    {
      rc++;
      if (((rc%4) == 1) && (visibleSize(Names[i]) > w1)) w1 = visibleSize(Names[i]);
      if (((rc%4) == 2) && (visibleSize(Names[i]) > w2)) w2 = visibleSize(Names[i]);
      if (((rc%4) == 3) && (visibleSize(Names[i]) > w3)) w3 = visibleSize(Names[i]);
      if (((rc%4) == 0) && (visibleSize(Names[i]) > w4)) w4 = visibleSize(Names[i]);
    }

    if ((rc == 1) && ((w1+6) > width))                width = w1+6;
    else if ((rc == 2) && ((w1+w2+13) > width))       width = w1+w2+13;
    else if ((rc == 3) && ((w1+w2+w3+20) > width))    width = w1+w2+w3+20;
    else if ((rc >= 4) && ((w1+w2+w3+w4+26) > width)) width = w1+w2+w3+w4+26;

    int num_spaces = width - visibleSize(name) - 14;

    String border = "  #n+-" + xchars(width,"-") + "-+#N";
    String temp = border + "\r\n" + "  #n|#N Clan Roster#n:#N  " + name;
    temp += xchars(num_spaces," ") + " #n|#N\r\n" + border + "\r\n";

    for (int i=0; i<mcount; i++)

    {
      count++;

      if ((count%4) == 1) temp += "  #n|#N";
      temp += " [" + ClassMethods.shorten(Class[i], true) + "] " + Names[i] + " ";

      if ((count%4) == 1) temp += xchars(w1-visibleSize(Names[i])," ");
      if ((count%4) == 2) temp += xchars(w2-visibleSize(Names[i])," ");
      if ((count%4) == 3) temp += xchars(w3-visibleSize(Names[i])," ");
      if ((count%4) == 0) temp += xchars(w4-visibleSize(Names[i])," ");
      if ((count%4) == 0) temp += "#n|#N\r\n";

      if ((count == rc) && ((count%4) == 1))
        temp += xchars(width-w1-5," ") + "#n|#N\r\n";
      if ((count == rc) && ((count%4) == 2))
        temp += xchars(width-w1-w2-12," ") + "#n|#N\r\n";
      if ((count == rc) && ((count%4) == 3))
        temp += xchars(width-w1-w2-w3-19," ") + "#n|#N\r\n";
    }

    temp += border;

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String displayRankNames()

  {
    String name = clanName;
    int mwidth = 0, fwidth = 0;
    String mline = "", fline = "", mspace = "", fspace = "", temp = "";

    for (int i=1; i<=getNumRanks()+1; i++)

    {
      if (visibleSize(getMaleRank(i)) > mwidth)
        mwidth = visibleSize(getMaleRank(i));
      if (visibleSize(getFemaleRank(i)) > fwidth)
        fwidth = visibleSize(getFemaleRank(i));
    }

    mwidth += 11;
    fwidth += 11;

    for (int i=1; i<=mwidth; i++) mline += "-";
    for (int i=1; i<=fwidth; i++) fline += "-";
    for (int i=1; i<=mwidth; i++) mspace += " ";
    for (int i=1; i<=fwidth; i++) fspace += " ";

    temp += "  #n+-" + mline + "---" + fline + "-+#N\r\n";
    temp += "  #n|#N " + name + " #NClanrank Titles#n:#N";

    int num_spaces = mwidth + fwidth - visibleSize(name) - 14;
    temp += xchars(num_spaces," ") + " #n|#N\r\n";

    temp += "  #n+-" + mline + "-+-" + fline + "-+#N\r\n";
    temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";
    temp += "  #n|#N Male Ranks#R:#N" + xchars(mwidth-11," ");
    temp += " #n|#N Female Ranks#R:#N" + xchars(fwidth-13," ") + " #n|#N\r\n";
    temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";

    for (int i=1; i<=getNumRanks(); i++)

    {
      String is = "" + i;
      if (i < 10) is += " ";
      int num_male_spaces = mwidth - visibleSize(getMaleRank(i)) - 11;
      int num_female_spaces = fwidth - visibleSize(getFemaleRank(i)) - 11;
      temp += "  #n|#Y Rank " + is + "  #n-#N " + getMaleRank(i);
      temp += xchars(num_male_spaces," ") + " #n|#Y Rank " + is + "  #n-#N ";
      temp += getFemaleRank(i) + xchars(num_female_spaces," ") + " #n|#N\r\n";
    }

    String maleImm = getMaleRank(getNumRanks()+1);
    String femaleImm = getFemaleRank(getNumRanks()+1);

    temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";
    temp += "  #n|#C Immortal #n-#N " + maleImm;
    temp += xchars(mwidth-visibleSize(maleImm)-11," ");
    temp += " #n|#C Immortal #n-#N " + femaleImm;
    temp += xchars(fwidth-visibleSize(femaleImm)-11," ") + " #n|#N\r\n";
    temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";
    temp += "  #n+-" + mline + "-+-" + fline + "-+#N";

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void clanCommand(Entity ENT, String str)

  {
    String key = first(str).toLowerCase();
    String Rem = last(str);
    String rem = Rem.toLowerCase();
    String frem = first(rem);

    if (match(key, "l", "listing"))
      ENT.echo(Clan.displayClanlist());

    else if (match(key, "i", "information"))

    {
      if (rem.length() > 0)

      {
        Clan C = World.getClan(rem);

        if (C == null) {
          ENT.echo("There is no clan by that name.");
          return; }

        ENT.echo(C.displayInfo());
      }

      else ENT.echo(Clan.displayClanlist());
    }

    else if (match(key, "r", "roster"))

    {
      if (rem.length() > 0)

      {
        Clan C = World.getClan(rem);

        if (C == null) {
          ENT.echo("There is no clan by that name.");
          return; }

        ENT.echo(C.displayRoster());
      }

      else ENT.echo("Which clan do you wish to view the roster of?");
    }

    else if (match(key, "r", "ranks"))

    {
      if (rem.length() > 0)

      {
        Clan C = World.getClan(rem);

        if (C == null) {
          ENT.echo("There is no clan by that name.");
          return; }

        ENT.echo(C.displayRankNames());
      }

      else ENT.echo("Which clan do you wish to view the ranks of?");
    }

    else if (match(key, "ap", "apply"))   apply(ENT,rem);
    else if (match(key, "jo", "join"))    apply(ENT,rem);
    else if (match(key, "en", "enlist"))  enlist(ENT,frem);
    else if (match(key, "ac", "accept"))  enlist(ENT,frem);
    else if (match(key, "ex", "expel"))   expel(ENT,frem);
    else if (match(key, "re", "remove"))  expel(ENT,frem);
    else if (match(key, "ra", "raise"))   raise(ENT,frem);
    else if (match(key, "pr", "promote")) raise(ENT,frem);
    else if (match(key, "de", "demote"))  demote(ENT,frem);
    else if (match(key, "lo", "lower"))   demote(ENT,frem);
    else if (match(key, "re", "resign"))  resign(ENT,Rem);
    else if (match(key, "qu", "quit"))    resign(ENT,Rem);

    else ENT.echo(HelpFile.getClanCommands());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("CLANID");   out.writeObject(new Integer(clanID));
      out.writeObject("CNAME");    out.writeObject(clanName);
      out.writeObject("CINFO");    out.writeObject(clanInfo);
      out.writeObject("CMEMBERS"); out.writeObject(members);
      out.writeObject("CMRANKS");  out.writeObject(maleRankNames);
      out.writeObject("CFRANKS");  out.writeObject(femaleRankNames);
      out.writeObject("CLAN END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try {

    String tag = "";

    while (!tag.equals("CLAN END"))

    {
      tag = (String)in.readObject();

      if (tag.equals("CLANID"))
        clanID = ((Integer)in.readObject()).intValue();
      else if (tag.equals("CNAME"))
        clanName = (String)in.readObject();
      else if (tag.equals("CINFO"))
        clanInfo = (Writable)in.readObject();
      else if (tag.equals("CMEMBERS"))
        members = (ArrayList)in.readObject();
      else if (tag.equals("CMRANKS"))
        maleRankNames = (ArrayList)in.readObject();
      else if (tag.equals("CFRANKS"))
        femaleRankNames = (ArrayList)in.readObject();
      else if (!tag.equals("CLAN END"))
        in.readObject();
    }

    } // End try.

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}