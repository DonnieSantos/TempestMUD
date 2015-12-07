public class Transfer

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int IDROP             = 0;
  public static final int IDISARM           = 1;
  public static final int IGETR             = 2;
  public static final int IGETC             = 3;
  public static final int ILOAD             = 4;
  public static final int IPOPE             = 5;
  public static final int IPOPR             = 6;
  public static final int IPOPC             = 7;
  public static final int IBUY              = 8;
  public static final int IWITHDRAW         = 9;
  public static final int IPUT              = 10;
  public static final int IPURGE            = 11;
  public static final int ISAC              = 12;
  public static final int IUSE              = 13;
  public static final int IEAT              = 14;
  public static final int IDECAYR           = 15;
  public static final int IDECAYE           = 16;
  public static final int IDECAYC           = 17;
  public static final int IDEPOSIT          = 18;
  public static final int IGIVE             = 19;
  public static final int ISELL             = 20;
  public static final int IENTERCORPSE      = 21;
  public static final int IREPLICATE        = 22;
  public static final int ICLEAR            = 23;
  public static final int ICREATECORPSE     = 24;
  public static final int IPERISH           = 25;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static final int ALL_OK            = 0;
  public static final int ER_INODROP        = 1;
  public static final int ER_ISTICKY        = 2;
  public static final int ER_IABSENT        = 3;
  public static final int ER_INOGET         = 4;
  public static final int ER_IUNTOUCH       = 5;
  public static final int ER_ILEVEL         = 6;
  public static final int ER_INOTCONT       = 7;
  public static final int ER_IFULL          = 8;
  public static final int ER_ICONT          = 9;
  public static final int ER_INOTFOOD       = 10;
  public static final int ER_INOSAC         = 11;
  public static final int ER_INOTENOUGHGOLD = 12;
  public static final int ER_IZEROGOLD      = 13;
  public static final int ER_IPLAYERCORPSE  = 14;
  public static final int ER_INOVALUE       = 15;
  public static final int ER_ICONTLEVEL     = 16;
  public static final int ER_RNODROP        = 17;
  public static final int ER_RNOGET         = 18;
  public static final int ER_EINVFULL       = 19;
  public static final int ER_ENOGET         = 20;
  public static final int ER_IHASITEMS      = 21;
  public static final int ER_BFULL          = 22;
  public static final int ER_IPERISH        = 23;
  public static final int ER_DISARMERROR    = 24;
  public static final int ER_IMAXLEVEL      = 25;
  public static final int ER_ICONTFULL      = 26;
  public static final int ER_INODEPOSIT     = 27;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int item(Item I, Object S, Object T, int type)

  {
    int error = -1;

    switch (type)

    {
      case IGIVE: error = canGive(I, (Entity)S, (Entity)T); break;
      case IDROP: error = canDrop(I, (Entity)S, (Room)T); break;
      case IPUT:  error = canPut(I, (Entity)S, (Item)T); break;
      case IGETR: error = canGetRoom(I, (Room)S, (Entity)T); break;
      case IGETC: error = canGetContainer(I, (Item)S, (Entity)T); break;
      case IDISARM: error = canDisarm(I, (Entity)S, (Room)T); break;
      case IDEPOSIT: error = canDeposit(I, (Entity)S, (BankAccount)T); break;
      case IWITHDRAW: error = canWithdraw(I, (BankAccount)S, (Entity)T); break;
      case IENTERCORPSE: error = canEnterCorpse(I, (Entity)S, (Item)T); break;
    }

    if (error == ALL_OK)

    {
      if (S instanceof Entity) ((Entity)S).itemOut(I, type);
      if (S instanceof Room)   ((Room)S).itemOut(I, type);
      if (S instanceof Item)   ((Item)S).itemOut(I, type);

      if (T instanceof Entity) ((Entity)T).itemIn(I, type);
      if (T instanceof Room)   ((Room)T).itemIn(I, type);
      if (T instanceof Item)   ((Item)T).itemIn(I, type);
    }

    return error;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int canEnterCorpse(Item I, Entity E, Item C)

  {
    if (I.getFlag(Item.FLAG_PERISH)) return ER_IPERISH;

    return ALL_OK;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int canGive(Item I, Entity E, Entity T)

  {
    if (I.getFlag(Item.FLAG_NO_DROP)) return ER_INODROP;
    if (I.getLevel() > T.getLevel()) return ER_ILEVEL;
    if (!T.canFit(I)) return ER_EINVFULL;

    return ALL_OK;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int canDeposit(Item I, Entity E, BankAccount A)

  {
    if (!I.isEmpty()) return ER_IHASITEMS;
    if (!A.canFit(I)) return ER_BFULL;
    if (I.getFlag(Item.FLAG_NO_DROP)) return ER_INODROP;
    if (I.getFlag(Item.FLAG_NO_DEPOSIT)) return ER_INODEPOSIT;

    return ALL_OK;  
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int canGetRoom(Item I, Room RM, Entity E)

  {
    if (!E.canFit(I)) return ER_EINVFULL;
    if (E.getLevel() < I.getLevel()) return ER_ILEVEL;
    if (E.getLevel() < I.getMaxLevel()) return ER_IMAXLEVEL;

    if (E.getLevel() < 100)

    {
      if (RM.getFlag(Room.FLAG_NO_GET)) return ER_RNOGET;
      if (I.getFlag(Item.FLAG_UNTOUCHABLE)) return ER_IUNTOUCH;
    }

    return ALL_OK;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int canGetContainer(Item I, Item C, Entity E)

  {
    if (!E.canFit(I)) return ER_EINVFULL;
    if (I.getLevel() > E.getLevel()) return ER_ILEVEL;

    return ALL_OK;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int canWithdraw(Item I, BankAccount A, Entity E)

  {
    if (!E.canFit(I)) return ER_EINVFULL;
    if (E.getLevel() < I.getLevel()) return ER_ILEVEL;
    if (E.getLevel() < I.getMaxLevel()) return ER_IMAXLEVEL;

    return ALL_OK;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int canDisarm(Item I, Entity E, Room RM)

  {
    boolean invFull = E.canFit(I);
    boolean roomNoDrop = RM.getFlag(Room.FLAG_NO_DROP);

    if (I.getFlag(Item.FLAG_STICKY)) return ER_ISTICKY;
    if ((invFull) && (roomNoDrop)) return ER_DISARMERROR;
    if ((roomNoDrop) && (!invFull)) return ER_RNODROP;

    return ALL_OK;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int canDrop(Item I, Entity E, Room RM)

  {
    if (I.isGold())

    {
      if (I.getWorth() <= 0) return ER_IZEROGOLD;
      if (E.getGold() < I.getWorth()) return ER_INOTENOUGHGOLD;
    }

    if (I.getFlag(Item.FLAG_NO_DROP)) return ER_INODROP;
    if (RM.getFlag(Room.FLAG_NO_DROP)) return ER_RNODROP;

    return ALL_OK;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static int canPut(Item I, Entity E, Item C)

  {
    if (I.getFlag(Item.FLAG_NO_DROP)) return ER_INODROP;
    if (I.isContainer()) return ER_ICONT;
    if (!C.canFit(I)) return ER_ICONTFULL;

    return ALL_OK;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}

