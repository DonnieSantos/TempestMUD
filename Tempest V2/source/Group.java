import java.io.*;
import java.util.*;

class Group extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ArrayList members;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getSize()                   { return members.size();              }
  public Entity getMember(int i)         { return (Entity) members.get(i);     }
  public Entity getLeader()              { return getMember(0).getFollowing(); }
  public void addMemberSilent(Entity E)  { members.add(E);                     }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public Group(Entity me)

  {
    members = new ArrayList();
    members.add(me);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public int getPlayerSize()

  {
    int count = 0;

    for (int i=0; i<members.size(); i++)
      if (getMember(i).isPlayer())
        count++;

    return count;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public boolean inGroup(Entity ENT)

  {
    for (int i=0; i<members.size(); i++)
      if (getMember(i) == ENT)
        return true;

    return false;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addMember(Entity SRC, Entity ENT)

  {
    Entity leader = getLeader();

    if (SRC != leader)
      SRC.echo("Only the group leader can do that.");
    else if (ENT == null)
      leader.echo("Nobody around by that name.");
    else if (ENT == leader)
      leader.echo("You are already in your own group.");
    else if (ENT.getFollowing() != leader)
      leader.echo(ENT.getName() + " is not following you.");
    else if (ENT.getRoom() != leader.getRoom())
      leader.echo(ENT.getName() + " is not here right now.");

    else

    {
      leader.echo(ENT.getName() + " is joining this group.");
      groupMessage(leader, null, null, ENT.getName() + " is joining this group.");
      ENT.echo("You join " + possessive(leader.getName()) + " group.");

      for (int i=0; i<members.size(); i++)
      if (getMember(i) != ENT) {
        ENT.getGroup().addMemberSilent(getMember(i));
        getMember(i).getGroup().addMemberSilent(ENT); }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeMember(Entity SRC, Entity ENT)

  {
    Entity leader = getLeader();

    if (SRC != leader)
      SRC.echo("Only the group leader can do that.");
    else if (ENT == null)
      leader.echo("Nobody around by that name.");
    else if (ENT == leader)
      leader.echo("You can't remove yourself from your own group.");
    else if (!inGroup(ENT))
      leader.echo(ENT.getName() + " is not in your group.");

    else

    {
      groupMessage(leader, ENT, null, ENT.getName() + " is leaving this group.");
      ENT.echo(leader.getName() + " has removed you from the group.");
      leader.echo(ENT.getName() + " is leaving this group.");

      for (int i=0; i<members.size(); i++)
        if (getMember(i) != ENT)
          getMember(i).getGroup().removeMemberSilent(ENT);

      ENT.getGroup().destroyGroup();
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeMemberSilent(Entity E)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i) == E)
      members.remove(i);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void addAll(Entity SRC)

  {
    if (getLeader() != SRC) {
      SRC.echo("Only the group leader can do that.");
      return; }

    if (SRC.getNumFollowers() <= 0) {
      SRC.echo("Nobody is following you.");
      return; }

    ArrayList namesAdded = SRC.groupAll();

    int n = namesAdded.size();

    if (n <= 0) SRC.echo("Nobody has been added to the group.");

    else if (n == 1)

    {
      String name_added = (String) namesAdded.get(0);
      SRC.echo(name_added + " is joining this group.");
      Entity E = ClientList.findCharacter(name_added);

      if (E != null) {
        E.echo("You join " + possessive(SRC.getName()) + " group.");
        groupMessage(SRC, E, null, E.getName() + " is joining this group."); }
    }

    else

    {
      for (int i=0; i<n; i++)
        groupMessage((String)namesAdded.get(i) + " is joining this group.");
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void removeAll(Entity SRC)

  {
    if (members.size() <= 0) {
      getLeader().echo("The group is empty.");
      return; }

    if (SRC != getLeader()) {
      SRC.echo("Only the group leader can do that.");
      return; }

    groupMessage("Group disbanded.");

    while (members.size() > 1)
      getMember(1).stopFollowing();

    destroyGroup();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void destroyGroup()

  {
    Entity me = getMember(0);

    for (int i=1; i<members.size(); i++)
      getMember(i).getGroup().removeMemberSilent(me);

    members = new ArrayList();
    members.add(me);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  Entity getRandomTarget(Entity ENT, Entity TANK)

  {
    int size = members.size();
    Entity list[] = new Entity [size];
    int percentages[] = new int [size];
    int count = 0, last = 0, randint;
    float average;

    for (int i=0; i<size; i++)
    if (((getMember(i).targetingGroup(ENT.getGroup()))
    && (getMember(i).getRoom() == ENT.getRoom()))
    || (ENT.getTarget() == getMember(i)))
      list[count++] = getMember(i);

    average = (70 / count);

    for (int i=0; i<count; i++)
      percentages[i] = (int)average;

    for (int i=0; i<count; i++)

    {
      if (list[i] == getLeader())
        percentages[i] += 10;
      if (list[i] == TANK)
        percentages[i] += 20;
    }

    randint = getRandomInt(1, 100);

    for (int i=0; i<count; i++)

    {
      percentages[i] += last;

      if ((randint > last) && (randint <= percentages[i]))
        return list[i];

      else last = percentages[i];
    }

    return TANK;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void groupMessage(String str)

  {
    for (int i=0; i<members.size(); i++)
      getMember(i).echo(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void groupMessage(Entity s1, Entity s2, Entity s3, String str)

  {
    for (int i=0; i<members.size(); i++)
    if ((getMember(i) != s1) && (getMember(i) != s2) && (getMember(i) != s3))
      getMember(i).echo(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void groupRoomMessage(Room loc, String str)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getRoom() == loc)
      getMember(i).echo(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void groupRoomMessage(Room loc, Entity silent, String str)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getRoom() == loc)
    if (getMember(i) != silent)
      getMember(i).echo(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void groupDistMessage(Room loc, String str)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getRoom() != loc)
      getMember(i).echo(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void groupDistMessage(Room loc, Entity silent, String str)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getRoom() != loc)
    if (getMember(i) != silent)
      getMember(i).echo(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void combatMessage(Entity SRC, String str)

  {
    for (int i=0; i<members.size(); i++)
    if (getMember(i).getRoom() == SRC.getRoom())
    if (getMember(i) != SRC)
      getMember(i).awakeFightEcho(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public String getDisplayInfo(Entity ENT)

  {
    String str = "";

    str += "#N[ ";
    str += tenSpace(ENT.getLevel(),2);
    str += "#Y" + ENT.getLevel() + " ";
    str += ENT.getSClassColor() + " ]  #c< ";
    str += tenSpace(ENT.getCurrentHP(),3);
    str += "#g" + ENT.getCurrentHP() + "#Ghp ";
    str += tenSpace(ENT.getCurrentMN(),3);
    str += "#m" + ENT.getCurrentMN() + "#Mmn ";
    str += tenSpace(ENT.getCurrentMV(),3);
    str += "#b" + ENT.getCurrentMV() + "#Bmv";
    str += " #c>#N  " + ENT.getName();

    if (ENT.getGroup().getLeader() == ENT) str += " the leader.";
    else str += ".";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void displayGroup(Entity ENT)

  {
    Entity leader = getLeader();
    String str = "";
    int maxsize = 0;

    for (int i=0; i<members.size(); i++)

    {
      int size = visibleSize(getDisplayInfo(getMember(i)));
      if (size > maxsize) maxsize = size;
    }

    str += "#n+--" + xchars(maxsize,"-") + "--+#N\r\n#n|  ";
    str += "#gGroup Information#n:" + xchars(maxsize-16," ");
    str += "#n|#N\r\n#n+--" + xchars(maxsize,"-");

    int lsize = maxsize - visibleSize(getDisplayInfo(leader));
    str += "--+#N\r\n#n|  " + getDisplayInfo(leader);
    str += xchars(lsize," ") + "  #n|#N\r\n";

    if (members.size() > 1)
    for (int i=0; i<members.size(); i++)
    if (getMember(i) != leader)

    {
      int tsize = maxsize - visibleSize(getDisplayInfo(getMember(i)));
      str += "#n|  " + getDisplayInfo(getMember(i));
      str += xchars(tsize," ") + "  #n|#N\r\n";
    }

    str += "#n+--" + xchars(maxsize,"-") + "--+#N";

    ENT.echo(str);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void groupAssist(Entity ENT)

  {
    if (ENT == null) return;
    if (ENT.getPlayerState() != PSTATE_FIGHTING) return;

    for (int i=0; i<members.size(); i++)

    {
      Entity E = getMember(i);

      if ((E.getPlayerState() != PSTATE_FIGHTING) && (E.getAutoassist()))
      if ((E != ENT) && (E.getRoom() == ENT.getRoom()))

      {
        E.setTarget(ENT.getTarget());
        E.setPlayerState(PSTATE_FIGHTING);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void divideExperience(Room loc, int exp)

  {
    int max_level = 0;

    for (int i=0; i<members.size(); i++)

    {
      Entity E = getMember(i);

      if ((E.isPlayer()) && (E.getLevel() > max_level) && (E.getRoom() == loc))
        max_level = E.getLevel();
    }

    if (max_level == 0) return;

    for (int i=0; i<members.size(); i++)
    if ((getMember(i).isPlayer()) && (getMember(i).getRoom() == loc))

    {
      int LD = max_level - getMember(i).getLevel();
      int percentage = getExpDeficit(LD);
      int total = ((exp * percentage) / 100);
      getMember(i).gainExperience(total);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void group(Entity SRC, String target)

  {
    if (target.length() <= 0) { SRC.getGroup().displayGroup(SRC);  return; }
    if (target.equals("all")) { SRC.getGroup().addAll(SRC);        return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == null) {
      SRC.echo("Nobody around by that name.");
      return; }

    SRC.getGroup().addMember(SRC, ENT);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void ungroup(Entity SRC, String target)

  {
    if (target.length() <= 0) { SRC.echo("Who do you want to ungroup?");  return; }
    if (target.equals("all")) { SRC.getGroup().removeAll(SRC);            return; }

    Entity ENT = SRC.getRoom().findEntity(SRC, target);

    if (ENT == null) {
      SRC.echo("Nobody around by that name.");
      return; }

    SRC.getGroup().removeMember(SRC, ENT);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}