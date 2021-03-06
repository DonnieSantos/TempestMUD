class GemMissile extends SpellDamage

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 2000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public GemMissile()

  {
    super();

    id = SPELL_GEM_MISSILE;
    name = "Gem Missile";
    desc = "Assail your enemy with a magical crystalline shard.";
    prof = "Mg Wi Sc Wl";
    manacost = 10;
    reqDEX = 5;
    reqINT = 10;
    importance = 5;
    learnRate = 5;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void updateOutput()

  {
    if (!target.isDead())

    {
      selfOut = "#gYou shoot a crystalline shard of energy at " + tn + "!#N";
      targOut = "#g" + Mn + " shoots a crystalline shard of energy at you!#N";
      grupOut = "#g" + Mn + " shoots a crystalline shard of energy at " + tn + "!#N";
      obsvOut = "#N" + Mn + " shoots a crystalline shard of energy at " + tn + "!#N";
    }

    else

    {
      selfOut = "#gYour gem missile pierces " + ty + " heart!#N";
      targOut = "#g" + My + " gem missile pierces your heart!#N";
      grupOut = "#g" + My + " gem missile pierces " + ty + " heart!#N";
      obsvOut = "#N" + My + " gem missile pierces " + ty + " heart!#N";
    }

    spellOutput(true, false);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public int getDamage()

  {
    int damage = (level / 5) + Utility.getRandomInt(20, 40);
    return damage;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}