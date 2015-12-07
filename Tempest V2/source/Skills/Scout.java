import java.io.*;
import java.util.*;

class Scout extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private int depth;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Scout()

  {
    super();

    id = SKILL_SCOUT;
    name = "Scout";
    desc = "Scout the surrounding area for signs of life.";
    prof = "All";
    importance = 5;
    learnRate = 5;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateDepth()

  {
    depth = ((level / 16) + 1);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    String tempstr = "You scout the surrounding area:\r\n";
    int north = 0, south = 0, east = 0, west = 0, up = 0, down = 0;
    int nsize = 0, ssize = 0, esize = 0, wsize = 0, usize = 0, dsize = 0;
    Room location;

    updateDepth();

    location = owner.getRoom();
    for (int i=1; i<=depth; i++)
    if (location.getN() != 0) {
      north++;
      location = World.getRoom(location.getN());
      nsize += location.getSize(); }

    location = owner.getRoom();
    for (int i=1; i<=depth; i++)
    if (location.getS() != 0) {
      south++;
      location = World.getRoom(location.getS());
      ssize += location.getSize(); }

    location = owner.getRoom();
    for (int i=1; i<=depth; i++)
    if (location.getE() != 0) {
      east++;
      location = World.getRoom(location.getE());
      esize += location.getSize(); }

    location = owner.getRoom();
    for (int i=1; i<=depth; i++)
    if (location.getW() != 0) {
      west++;
      location = World.getRoom(location.getW());
      wsize += location.getSize(); }

    location = owner.getRoom();
    for (int i=1; i<=depth; i++)
    if (location.getU() != 0) {
      up++;
      location = World.getRoom(location.getU());
      usize += location.getSize(); }

    location = owner.getRoom();
    for (int i=1; i<=depth; i++)
    if (location.getD() != 0) {
      down++;
      location = World.getRoom(location.getD());
      dsize += location.getSize(); }

    if (nsize > 0)
    for (int i=1; i<=north; i++)

    {
      location = owner.getRoom();

      for (int j=1; j<=i; j++)
        location = World.getRoom(location.getN());

      tempstr += location.getScoutNames(i, "N");
    }

    if (ssize > 0)
    for (int i=1; i<=south; i++)

    {
      location = owner.getRoom();

      for (int j=1; j<=i; j++)
        location = World.getRoom(location.getS());

      tempstr += location.getScoutNames(i, "S");
    }

    if (esize > 0)
    for (int i=1; i<=east; i++)

    {
      location = owner.getRoom();

      for (int j=1; j<=i; j++)
        location = World.getRoom(location.getE());

      tempstr += location.getScoutNames(i, "E");
    }

    if (wsize > 0)
    for (int i=1; i<=west; i++)

    {
      location = owner.getRoom();

      for (int j=1; j<=i; j++)
        location = World.getRoom(location.getW());

      tempstr += location.getScoutNames(i, "W");
    }

    if (usize > 0)
    for (int i=1; i<=up; i++)

    {
      location = owner.getRoom();

      for (int j=1; j<=i; j++)
        location = World.getRoom(location.getU());

      tempstr += location.getScoutNames(i, "U");
    }

    if (dsize > 0)
    for (int i=1; i<=down; i++)

    {
      location = owner.getRoom();

      for (int j=1; j<=i; j++)
        location = World.getRoom(location.getD());

      tempstr += location.getScoutNames(i, "D");
    }

    boolean failed = false;

    if ((nsize == 0) && (ssize == 0) && (esize == 0)
    && (wsize == 0) && (usize == 0) && (dsize == 0))

    {
      tempstr += "There are no signs of life nearby.";
      failed = true;
    }

    owner.echo(tempstr);
    owner.getRoom().xecho(owner, owner.getPName() + " scouts the surrounding area.");

    if (!failed) trainPeaceful();

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}