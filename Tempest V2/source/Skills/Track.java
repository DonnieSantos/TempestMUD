import java.io.*;
import java.util.*;

class Track extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int range;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Track()

  {
    super();

    id = SKILL_TRACK;
    name = "Track";
    desc = "Track down a target.";
    prof = "As Ra Rg Th Sb";
    importance = 0;
    learnRate = 5;
    reqSTR = 10;
    reqDEX = 10;
    reqCON = 10;
    reqINT = 10;
    reqWIS = 10;

    addRequirement("Scout", 20);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateRange()

  {
    range = (level*2) + 50;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private boolean tmsg(String s, boolean r)

  {
    owner.echo(s);
    owner.stopTracking();
    return r;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    int target_num = Utility.clipNumber(rem);
    rem = Utility.clipString(rem);

    if (rem.length() <= 0)
      return tmsg("What do you want to track?", false);

    return track(rem, target_num);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private boolean track(String target, int target_num)

  {
    Entity ENT = owner.getRoom().findEntity(owner, target);
    int new_rooms[] = new int [World.getSize()];
    IntArray IRA = new IntArray();
    boolean found = true;
    int index = 0, size;

    updateRange();

    if (ENT != null)

    {
      int num_ents = owner.getRoom().findNumEnts(target);

      if ((target_num - num_ents) <= 0)
        return tmsg(ENT.getName() + " is right here!", false);

      else target_num = target_num - num_ents;
    }

    IRA.put(owner.getRoom().getID());

    for (int i=1; i<=range; i++)

    {
      if (!found)
        return tmsg("You can't seem to pick up the trail.", false);

      found = false;

      if (i < 3) index = i - 1;
      else index += new_rooms[i-2];

      size = IRA.size();
      new_rooms[i] = 0;

      for (int j=index; j<size; j++)

      {
        Room current_room = World.getRoom(IRA.get(j));

        if (current_room.getN() != 0)
        if (IRA.search(current_room.getN()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getN());

          Entity ent = World.getRoom(current_room.getN()).findEntity(owner, target);

          if (ent != null) {
            int num_ents = World.getRoom(current_room.getN()).findNumEnts(target);
            if ((target_num - num_ents) <= 0) return track(ent);
            else target_num = target_num - num_ents; }
        }

        if (current_room.getS() != 0)
        if (IRA.search(current_room.getS()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getS());

          Entity ent = World.getRoom(current_room.getS()).findEntity(owner, target);

          if (ent != null) {
            int num_ents = World.getRoom(current_room.getS()).findNumEnts(target);
            if ((target_num - num_ents) <= 0) return track(ent);
            else target_num = target_num - num_ents; }
        }

        if (current_room.getE() != 0)
        if (IRA.search(current_room.getE()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getE());

          Entity ent = World.getRoom(current_room.getE()).findEntity(owner, target);

          if (ent != null) {
            int num_ents = World.getRoom(current_room.getE()).findNumEnts(target);
            if ((target_num - num_ents) <= 0) return track(ent);
            else target_num = target_num - num_ents; }
        }

        if (current_room.getW() != 0)
        if (IRA.search(current_room.getW()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getW());

          Entity ent = World.getRoom(current_room.getW()).findEntity(owner, target);

          if (ent != null) {
            int num_ents = World.getRoom(current_room.getW()).findNumEnts(target);
            if ((target_num - num_ents) <= 0) return track(ent);
            else target_num = target_num - num_ents; }
        }

        if (current_room.getU() != 0)
        if (IRA.search(current_room.getU()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getU());

          Entity ent = World.getRoom(current_room.getU()).findEntity(owner, target);

          if (ent != null) {
            int num_ents = World.getRoom(current_room.getU()).findNumEnts(target);
            if ((target_num - num_ents) <= 0) return track(ent);
            else target_num = target_num - num_ents; }
        }

        if (current_room.getD() != 0)
        if (IRA.search(current_room.getD()) == -1)

        {
          found = true;
          new_rooms[i]++;
          IRA.put(current_room.getD());

          Entity ent = World.getRoom(current_room.getD()).findEntity(owner, target);

          if (ent != null) {
            int num_ents = World.getRoom(current_room.getD()).findNumEnts(target);
            if ((target_num - num_ents) <= 0) return track(ent);
            else target_num = target_num - num_ents; }
        }
      }
    }

    return tmsg("You can't seem to pick up the trail.", false);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean track(Entity target)

  {
    updateRange();

    if (owner.getRoom() == target.getRoom())

    {
      owner.echo(target.getPName() + " is right here!");
      if (owner.getTracking() != null) trainPeaceful();
      owner.stopTracking();
      return false;
    }

    int dist_and_dir[] = owner.getRoom().roomDistance(target.getRoom());

    if (dist_and_dir == null) {
      owner.stopTracking();
      owner.echo("You can't seem to pick up the trail.");
      return false; }

    int dist = dist_and_dir[0];
    int dir  = dist_and_dir[1];

    if (dist > range) {
      owner.stopTracking();
      owner.echo("You can't seem to pick up the trail.");
      return false; }

    switch(dir)

    {
      case 1:   owner.setTrackingDir(Utility.DIRECTION_N); owner.echo("You sense a trail north of here!");  break;
      case 2:   owner.setTrackingDir(Utility.DIRECTION_S); owner.echo("You sense a trail south of here!");  break;
      case 3:   owner.setTrackingDir(Utility.DIRECTION_E); owner.echo("You sense a trail east of here!");   break;
      case 4:   owner.setTrackingDir(Utility.DIRECTION_W); owner.echo("You sense a trail west of here!");   break;
      case 5:   owner.setTrackingDir(Utility.DIRECTION_U); owner.echo("You sense a trail up from here!");   break;
      case 6:   owner.setTrackingDir(Utility.DIRECTION_D); owner.echo("You sense a trail down from here!"); break;
      default:  owner.stopTracking(); return false;
    }

    owner.setTracking(target);
    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}