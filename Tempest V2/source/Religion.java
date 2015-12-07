import java.util.*;
import java.io.*;

public class Religion extends Utility implements Externalizable

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 10;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private int religionID;
  private String dietyName;
  private String religionName;
  private Writable religionInfo;
  private ArrayList members;
  private ArrayList maleRankNames;
  private ArrayList femaleRankNames;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getName()              { return religionName;                    }
  public String getDietyName()         { return dietyName;                       }
  public int getID()                   { return religionID;                      }
  public int getNumRanks()             { return maleRankNames.size()-1;          }
  public String getMaleRank(int i)     { return (String) maleRankNames.get(i);   }
  public String getFemaleRank(int i)   { return (String) femaleRankNames.get(i); }
  public ClanMember getMember(int i)   { return (ClanMember) members.get(i);     }
  public int getRank(int i)            { return getMember(i).getRank();          }
  public String getCharClass(int i)    { return getMember(i).getCharClass();     }
  public String getNormName(int i)     { return getMember(i).getName();          }
  public String getListName(int i)     { return properName(getNormName(i));      }
  public static Religion getRel(int i) { return World.getReligion(i);            }
  public void setDietyName(String s)   { dietyName = s;                          }
  public int getNumMembers()           { return members.size();                  }
  public void setName(String s)        { religionName = s;                       }
  public Writable getReligionInfo()    { return religionInfo;                    }
  public ArrayList getMaleRanks()      { return maleRankNames;                   }
  public ArrayList getFemaleRanks()    { return femaleRankNames;                 }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Religion()

  {
    religionID = 0;
    religionName = "Generic Religion";
    religionInfo = new Writable("Religion Info", Writable.FORMAT_CDESC);
    members = new ArrayList();
    maleRankNames = new ArrayList();
    femaleRankNames = new ArrayList();

    dietyName = "Patron God";

    maleRankNames.add(new String("Generic Applicant"));
    maleRankNames.add(new String("Generic Worshipper"));
    maleRankNames.add(new String("Generic Acolyte"));
    maleRankNames.add(new String("Generic Priest"));
    maleRankNames.add(new String("Generic High Priest"));

    femaleRankNames.add(new String("Generic Applicant"));
    femaleRankNames.add(new String("Generic Worshipper"));
    femaleRankNames.add(new String("Generic Acolyte"));
    femaleRankNames.add(new String("Generic Priestess"));
    femaleRankNames.add(new String("Generic High Priestess"));
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Religion(int ID, String name)

  {
    this();
    religionID = ID;
    religionName = name;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void validateMembers()

  {
    if (!Char.fileExists(properName(dietyName)))
      dietyName = "None";

    for (int i=members.size()-1; i>=0; i--)
    if (!Char.fileExists(getListName(i)))
    members.remove(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void saveReligion()

  {
    FileManager.saveReligion(this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void clearReligion()

  {
    members = new ArrayList();
    FileManager.saveReligion(this);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void xecho(Entity ENT1, Entity ENT2, String msg)

  {
    for (int i=0; i<ClientList.getSize(); i++)
    if (ClientList.getClient(i).getCharInfo().getReligionNum() == religionID)
    if (ClientList.getClient(i).getCharInfo() != ENT1)
    if (ClientList.getClient(i).getCharInfo() != ENT2)
      ClientList.getClient(i).addOutput(msg);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addMember(String name, int rank, String profession)

  {
    ClanMember RM = new ClanMember(name, rank, profession);
    members.add(RM);
    saveReligion();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeMember(String name)

  {
    int pos = searchMember(name);
    if (pos == -1) return;
    members.remove(pos);
    saveReligion();
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
    return "NotInReligion";
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setMemberRank(String name, int rank)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getName().equalsIgnoreCase(name))
    getMember(i).setRank(rank);
    saveReligion();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setMemberName(String name, String newName)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getName().equalsIgnoreCase(name))
    getMember(i).setName(newName);
    saveReligion();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setMemberClass(String name, String profession)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getName().equalsIgnoreCase(name))
    getMember(i).setClass(profession);
    saveReligion();
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

    if (save) saveReligion();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void setRankName(int rank, String male, String female)

  {
    if (rank >= getNumRanks()) return;
    maleRankNames.set(rank, male);
    femaleRankNames.set(rank, female);
    saveReligion();
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

  public int getNumPriests()

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

  public static void apply(Entity SRC, String target)

  {
    String r = "";
    Religion R = null;
    int rnum = SRC.getReligionNum();

    if (rnum != -1) {
      R = getRel(rnum);
      r = R.getName(); }

    if (SRC.getLevel() >= 100) {
      SRC.echo("You are too Godly to worship anybody but yourself.");
      return; }

    String s = SRC.getName();

    if (R != null) {
      if (R.getMemberRank(s) > 0) SRC.echo("You are already in a religion.");
      else SRC.echo("You are already applying to a religion.");
      return; }

    if (target.length() <= 0) {
      SRC.echo("Which religion do you want to apply to?");
      return; }

    if (R == null) {
      SRC.echo("That religion does not exist.");
      return; }

    SRC.setReligion(rnum);
    R.addMember(SRC.getLName(), 0, SRC.getCharClass());
    SRC.castChar().save();

    SRC.echo("You are now applying to the religious order, " + r + "#N.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean checkAbsolutePower(Entity SRC, Entity ENT)

  {
    if (SRC == null) return false;
    if (ENT == null) return false;
    if (SRC.getLevel() < 100) return false;

    Religion R = null;
    int rnum = ENT.getReligionNum();

    if (rnum != -1) R = getRel(rnum);
    if (R == null) return false;
    if (R.getDietyName().equalsIgnoreCase(SRC.getName())) return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void admit(Entity SRC, String target)

  {
    int nr = 0;
    Religion R = null;
    int rnum = SRC.getReligionNum();

    if (rnum != -1) R = getRel(rnum);
    if (rnum != -1) nr = R.getNumRanks();

    Entity ENT = ClientList.findCharacter(target);
    boolean absolute_power = checkAbsolutePower(SRC, ENT);

    try {

    if ((!absolute_power) && (R == null))
      throw ex("You are not in a religion to admit anybody.");
    if ((!absolute_power) && (R.getMemberRank(SRC.getName()) == 0))
      throw ex("You have not been accepted into your religion yet.");
    if ((!absolute_power) && (R.getMemberRank(SRC.getName()) < nr-1))
      throw ex("Only ordained Priests can admit worshippers.");
    if (target.length() <= 0)
      throw ex("Who do you want to admit as a worshipper?");
    if (ENT == null)
      throw ex("There is nobody around by that name.");
    if (ENT == SRC)
      throw ex("Admit yourself into the religion that you are already in?  Okay...");
    if (ENT.getReligionNum() != rnum)
      throw ex(ENT.getName() + " is not applying to your religion.");
    if (R.getMemberRank(ENT.getName()) > 0)
      throw ex(ENT.getName() + " is already a member of your religion!");
    }

    catch (Exception e) { SRC.echo(e.getMessage());  return; }

    R.setMemberRank(ENT.getName(), 1);

    String s = SRC.getName();
    String t = ENT.getName();
    String r = R.getName();
    SRC.echo("You admit " + t + " into " + r + "#N.");
    ENT.echo(s + " has admitted you into the religious order, " + r + "#N.");
    SRC.getRoom().xecho(SRC, ENT, s + " has admitted " + t + " into " + r + "#N.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void excommunicate(Entity SRC, String target)

  {
    int nr = 0;
    Religion R = null;
    int rnum = SRC.getReligionNum();

    if (rnum != -1) R = getRel(rnum);
    if (rnum != -1) nr = R.getNumRanks();

    Entity ENT = World.findCharacter(target);
    boolean absolute_power = checkAbsolutePower(SRC, ENT);

    try {

    if ((!absolute_power) && (R == null))
      throw ex("You are not in a religion to excommunicate anybody.");
    if ((!absolute_power) && (R.getMemberRank(SRC.getName()) == 0))
      throw ex("You have not been accepted into your religion yet.");
    if ((!absolute_power) && (R.getMemberRank(SRC.getName()) < nr-1))
      throw ex("Only ordained Priests can excommunicate others.");
    if (target.length() <= 0)
      throw ex("Who do you want to excommunicate?");
    if (target.equalsIgnoreCase(SRC.getName()))
      throw ex("It's a little easier to just leave the religion...");
    if (ENT == null)
      throw ex("There is no such person.");
    if (ENT.getReligionNum() != rnum)
      throw ex(ENT.getName() + " is not a member of your religion.");
    if (!absolute_power)
    if (R.getMemberRank(ENT.getName()) >= R.getMemberRank(SRC.getName()))
      throw ex("You cannot excommunicate " + properName(target) + ".");

    String s = SRC.getName();
    String t = properName(target);
    String r = R.getName();

    R.removeMember(ENT.getName());
    ENT.setReligion(-1);
    ENT.castChar().save();

    SRC.echo("You have excommunicated " + t + " from " + r + "#N.");
    ENT.echo(s + " has excommunicated you from " + r + "#N.");
    R.xecho(SRC, SRC, t + " has been excommunicated by " + s + ".");

    } // End try.

    catch (Exception e) { SRC.echo(e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void raise(Entity SRC, String target)

  {
    int nr = 0;
    Religion R = null;
    int rnum = SRC.getReligionNum();

    if (rnum != -1) R = getRel(rnum);
    if (rnum != -1) nr = R.getNumRanks();

    Entity ENT = World.findCharacter(target);
    boolean absolute_power = checkAbsolutePower(SRC, ENT);

    try {

    if ((!absolute_power) && (R == null))
      throw ex("You are not in a religion to promote anybody.");
    if ((!absolute_power) && (R.getMemberRank(SRC.getName()) == 0))
      throw ex("You have not been accepted into your religion yet.");
    if ((!absolute_power) && (R.getMemberRank(SRC.getName()) < nr-1))
      throw ex("Only ordained Priests can promote others.");
    if (target.length() <= 0)
      throw ex("Who do you want to promote?");
    if (target.equalsIgnoreCase(SRC.getName()))
      throw ex("Promoting yourself, eh?  Nice try.");
    if (ENT == null)
      throw ex("There is no such person.");
    if (ENT.getReligionNum() != rnum)
      throw ex(ENT.getName() + " is not a member of your religion.");
    if (!absolute_power)
    if (R.getMemberRank(target) >= R.getMemberRank(SRC.getName()))
      throw ex("You cannot promote those of equal or higher rank than you.");
    if (R.getMemberRank(target) == 0)
      throw ex(ENT.getName() + " has not been accepted into your religion yet.");
    if (R.getMemberRank(target) >= nr)
      throw ex(ENT.getName() + " has already achieved the highest degree.");

    String s = SRC.getName();
    String t = properName(target);
    String r = R.getName();

    int new_rank = R.getMemberRank(target) + 1;
    R.setMemberRank(target, new_rank);

    String nrank = r + " #N" + R.getMemberRankName(ENT.getName(), ENT.getGender());
    SRC.echo("You have raised " + t + " to the degree of " + nrank + ".");
    ENT.echo(s + " has raised you to the degree of " + nrank + ".");
    Room rm = SRC.getRoom();
    rm.xecho(SRC, ENT, s + " has raised " + t + " to the degree of " + nrank + ".");

    } // End try.

    catch (Exception e) { SRC.echo(e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void demote(Entity SRC, String target)

  {
    int nr = 0;
    Religion R = null;
    int rnum = SRC.getReligionNum();

    if (rnum != -1) R = getRel(rnum);
    if (rnum != -1) nr = R.getNumRanks();

    Entity ENT = World.findCharacter(target);
    boolean absolute_power = checkAbsolutePower(SRC, ENT);

    try {

    if ((!absolute_power) && (R == null))
      throw ex("You are not in a religion to demote anybody.");
    if ((!absolute_power) && (R.getMemberRank(SRC.getName()) == 0))
      throw ex("You have not been accepted into your religion yet.");
    if ((!absolute_power) && (R.getMemberRank(SRC.getName()) < nr-1))
      throw ex("Only ordained Priests may demote others.");
    if (target.length() <= 0)
      throw ex("Who do you want to demote?");
    if (target.equalsIgnoreCase(SRC.getName()))
      throw ex("You cannot demote yourself.");
    if (ENT == null)
      throw ex("There is no such person.");
    if (ENT.getReligionNum() != rnum)
      throw ex(ENT.getName() + " is not a member of your religion.");
    if (!absolute_power)
    if (R.getMemberRank(target) >= R.getMemberRank(SRC.getName()))
      throw ex("You cannot demote those of equal or higher rank than you.");
    if (R.getMemberRank(target) == 0)
      throw ex(ENT.getName() + " has not been accepted into the religion yet.");

    String s = SRC.getName();
    String t = properName(target);
    String r = R.getName();

    int new_rank = R.getMemberRank(target) - 1;
    R.setMemberRank(target, new_rank);

    String nrank = r + " #N" + R.getMemberRankName(ENT.getName(), ENT.getGender());
    SRC.echo("You have lowered " + t + " to the degree of " + nrank + ".");
    ENT.echo(s + " has lowered you to the degree of " + nrank + ".");
    Room rm = SRC.getRoom();
    rm.xecho(SRC, ENT, s + " has lowered " + t + " to the degree of " + nrank + ".");

    } // End try.

    catch (Exception e) { SRC.echo(e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void forsake(Entity SRC, String target)

  {
    Religion R = null;
    int rnum = SRC.getReligionNum();

    if (rnum != -1) R = getRel(rnum);

    String temp = "";

    if (SRC.getLevel() >= 100) {
      SRC.echo("You can't forsake your own fellowship, they need you!");
      return; }

    if (R == null) {
      SRC.echo("You aren't even a member of a religion order!");
      return; }

    if (!target.equals("YES")) {
      temp += "To forsake your current religion type 'religion forsake YES'.\r\n\r\n";
      temp += "WARNING: You will permanently lose your membership and degree.";
      SRC.echo(temp);
      return; }

    R.removeMember(SRC.getName());
    SRC.setReligion(-1);
    SRC.castChar().save();

    SRC.echo("You have forsaken " + R.getName() + "#N.");
    Room rm = SRC.getRoom();
    rm.xecho(SRC, SRC, SRC.getName() + " has forsaken " + R.getName() + "#N.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static String displayReligionlist()

  {
    String temp = "";
    int width = 18;

    for (int i=0; i<World.getReligionSize(); i++)
    if ((getRel(i) != null) && (visibleSize(getRel(i).getName()) > width))
      width = visibleSize(getRel(i).getName());

    String border = "  #n+-" + xchars(width,"-") + "-+#N";
    temp += border + "\r\n  #n|#N Current Religions#n:#N";
    temp += xchars(width-18," ") + " #n|#N\r\n" + border + "\r\n";

    for (int i=0; i<World.getReligionSize(); i++)
    if (getRel(i) != null) {
      int buf = width - visibleSize(getRel(i).getName());
      temp += "  #n|#N " + getRel(i).getName() + xchars(buf," ") + " #n|#N\r\n"; }

    temp += border;

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String displayInfo()

  {
    String name = religionName;
    String leader_string = "";
    int width = getWidth(religionInfo.getString());
    int top_width = (23 + visibleSize(name));
    if (top_width > width) width = top_width;
    int sm_width = width-14;

    String border = "  #n+-" + xchars(width,"-") + "-+#N";
    String cborder = "  #n|#N " + xchars(width," ") + " #n|#N";
    String patron_string = "#c" + dietyName + "#N";
    if (visibleSize(patron_string) <= 0) patron_string = "None";
    patron_string = variableAlign(patron_string, sm_width);

    String temp = border + "\r\n  #n|#N Religion Information#n:#N  " + name;
    temp += xchars(width-visibleSize(name)-23," ") + " #n|#N\r\n";
    temp += border + "\r\n" + "  #n|#N Patron Diety#R:#N ";

    boolean first_time = true;

    while (patron_string.length() > 0)

    {
      if (!first_time) temp += "  #n|#N          ";
      first_time = false;
      String str = getLine(patron_string);
      patron_string = removeLine(patron_string);
      temp += str + xchars(sm_width-visibleSize(str)," ") + " #n|#N\r\n";
    }

    temp += border + "\r\n";
    if (getNumLeaders() <= 1) temp += "  #n|#N High Priest#R:#N  ";
    else if (getNumLeaders() > 1) temp += "  #n|#N High Priests#R:#N ";

    for (int i=0; i<members.size(); i++)
    if (getRank(i) == 4) {
      if (leader_string.length() > 0) leader_string += " ";
      leader_string += "#y" + getListName(i); }

    if (leader_string.length() <= 0) leader_string = "None";

    leader_string = variableAlign(leader_string, sm_width);
    first_time = true;

    while (leader_string.length() > 0)

    {
      if (!first_time) temp += "  #n|#N          ";
      first_time = false;
      String str = getLine(leader_string);
      leader_string = removeLine(leader_string);
      temp += str + xchars(sm_width-visibleSize(str)," ") + " #n|#N\r\n";
    }

    temp += border + "\r\n" + cborder + "\r\n";
    String cinfo = religionInfo.getString();

    while (cinfo.length() > 0)

    {
      String str = getLine(cinfo);
      cinfo = removeLine(cinfo);
      temp += "  #n|#N " + str + xchars(width-visibleSize(str)," ");
      temp += " #n|#N\r\n";
    }

    temp += cborder + "\r\n" + border;

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String displayRoster()

  {
    String name = religionName;
    int size = members.size();
    int count = 0, mcount = 0, rc = 0;
    int width = visibleSize(name) + 19;
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

    for (int i=0; i<size; i++)

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

    int num_spaces = width - visibleSize(name) - 19;

    String border = "  #n+-" + xchars(width,"-") + "-+#N";
    String temp = border + "\r\n" + "  #n|#N Religion Members#n:#N  " + name;
    temp += xchars(num_spaces," ") + " #n|#N\r\n" + border + "\r\n";

    for (int i=0; i<size; i++)

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
    String name = religionName;
    int mwidth = 0, fwidth = 0;
    String mline = "", fline = "", mspace = "", fspace = "", temp = "";

    for (int i=1; i<=getNumRanks(); i++)

    {
      if (visibleSize(getMaleRank(i)) > mwidth)
        mwidth = visibleSize(getMaleRank(i));
      if (visibleSize(getFemaleRank(i)) > fwidth)
        fwidth = visibleSize(getFemaleRank(i));
    }

    for (int i=1; i<=mwidth; i++) mline += "-";
    for (int i=1; i<=fwidth; i++) fline += "-";
    for (int i=1; i<=mwidth; i++) mspace += " ";
    for (int i=1; i<=fwidth; i++) fspace += " ";

    temp += "  #n+-" + mline + "---" + fline + "-+#N\r\n";
    temp += "  #n|#N " + name + " #NReligious Degrees#n:#N";
    temp += xchars(mwidth+fwidth-visibleSize(name)-16," ") + " #n|#N\r\n";
    temp += "  #n+-" + mline + "-+-" + fline + "-+#N\r\n";
    temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";
    temp += "  #n|#N Male Degrees#R:#N" + xchars(mwidth-13," ");
    temp += " #n|#N Female Degrees#R:#N" + xchars(fwidth-15," ") + " #n|#N\r\n";
    temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";

    for (int i=1; i<=getNumRanks(); i++)

    {
      String male = getMaleRank(i);
      String female = getFemaleRank(i);
      temp += "  #n|#N " + male + xchars(mwidth-visibleSize(male)," ");
      temp += " #n|#N " + female + xchars(fwidth-visibleSize(female)," ");
      temp += " #n|#N\r\n";
    }

    temp += "  #n|#N " + mspace + " #n|#N " + fspace + " #n|#N\r\n";
    temp += "  #n+-" + mline + "-+-" + fline + "-+#N";

    return temp;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void religionCommand(Entity ENT, String str)

  {
    String key = first(str).toLowerCase();
    String Rem = last(str);
    String rem = Rem.toLowerCase();
    String frem = first(rem);

    if (match(key, "l", "listing"))
      ENT.echo(Religion.displayReligionlist());

    else if (match(key, "i", "information"))

    {
      if (rem.length() > 0)

      {
        Religion R = World.getReligion(rem);

        if (R == null) {
          ENT.echo("There is no religion by that name.");
          return; }

        ENT.echo(R.displayInfo());
      }

      else ENT.echo(Religion.displayReligionlist());
    }

    else if (match(key, "m", "members"))

    {
      if (rem.length() > 0)

      {
        Religion R = World.getReligion(rem);

        if (R == null) {
          ENT.echo("There is no religion by that name.");
          return; }

        ENT.echo(R.displayRoster());
      }

      else ENT.echo("Which religion do you wish to view the roster of?");
    }

    else if ((match(key, "r", "ranks")) || (match(key, "d", "degrees")))

    {
      if (rem.length() > 0)

      {
        Religion R = World.getReligion(rem);

        if (R == null) {
          ENT.echo("There is no religion by that name.");
          return; }

        ENT.echo(R.displayRankNames());
      }

      else ENT.echo("Which religions do you wish to view the degrees of?");
    }

    else if (match(key, "ap", "apply"))          apply(ENT,rem);
    else if (match(key, "jo", "join"))           apply(ENT,rem);
    else if (match(key, "ad", "admit"))          admit(ENT,rem);
    else if (match(key, "ac", "accept"))         admit(ENT,rem);
    else if (match(key, "ra", "raise"))          raise(ENT,rem);
    else if (match(key, "pr", "promote"))        raise(ENT,rem);
    else if (match(key, "de", "demote"))         demote(ENT,rem);
    else if (match(key, "lo", "lower"))          demote(ENT,rem);
    else if (match(key, "ex", "excommunicate"))  excommunicate(ENT,frem);
    else if (match(key, "fo", "forsake"))        forsake(ENT,Rem);

    else ENT.echo(HelpFile.getReligionCommands());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void writeExternal(ObjectOutput out)

  {
    try

    {
      out.writeObject("RELIGIONID");   out.writeObject(new Integer(religionID));
      out.writeObject("RNAME");        out.writeObject(religionName);
      out.writeObject("RINFO");        out.writeObject(religionInfo);
      out.writeObject("RMEMBERS");     out.writeObject(members);
      out.writeObject("RMRANKS");      out.writeObject(maleRankNames);
      out.writeObject("RFRANKS");      out.writeObject(femaleRankNames);
      out.writeObject("RDIETY");       out.writeObject(dietyName);
      out.writeObject("RELIGION END");
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void readExternal(ObjectInput in)

  {
    try {

    String tag = "";

    while (!tag.equals("RELIGION END"))

    {
      tag = (String)in.readObject();

      if (tag.equals("RELIGIONID"))
        religionID = ((Integer)in.readObject()).intValue();
      else if (tag.equals("RNAME"))
        religionName = (String)in.readObject();
      else if (tag.equals("RINFO"))
        religionInfo = (Writable)in.readObject();
      else if (tag.equals("RMEMBERS"))
        members = (ArrayList)in.readObject();
      else if (tag.equals("RMRANKS"))
        maleRankNames = (ArrayList)in.readObject();
      else if (tag.equals("RFRANKS"))
        femaleRankNames = (ArrayList)in.readObject();
      else if (tag.equals("RDIETY"))
        dietyName = (String)in.readObject();
      else if (!tag.equals("RELIGION END"))
        in.readObject();
    }

    } // End try.

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}