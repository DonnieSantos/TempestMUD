class LightningBolt extends SpellDamage

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 2000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public LightningBolt()

  {
    super();

    id = SPELL_LIGHTNING_BOLT;
    name = "Lightning Bolt";
    desc = "Strike your enemy with a writhing bolt of electricity.";
    prof = "Mg Wi Sc Wl";
    manacost = 40;
    importance = 50;
    learnRate = 5;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void updateOutput()

  {
    if (!target.isDead())

    {
      selfOut = "#gYou shoot a bolt of lightning at " + tn + "!#N";
      targOut = "#g" + Mn + " shoots a bolt of lightning at you!#N";
      grupOut = "#g" + Mn + " shoots a bolt of lightning at " + tn + "!#N";
      obsvOut = "#N" + Mn + " shoots a bolt of lightning at " + tn + "!#N";
    }

    else

    {
      selfOut = "#gYour lightning bolt shatters " + tn + " into a thousand pieces!#N";
      targOut = "#g" + My + " lightning bolt shatters your body into a thousand pieces!#N";
      grupOut = "#g" + My + " lightning bolt shatters " + tn + " into a thousand pieces!#N";
      obsvOut = "#N" + My + " lightning bolt shatters " + tn + " into a thousand pieces!#N";
    }

    spellOutput(true, false);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public int getDamage()

  {
    int damage = (level / 5) + Utility.getRandomInt(50, 200);
    return damage;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}