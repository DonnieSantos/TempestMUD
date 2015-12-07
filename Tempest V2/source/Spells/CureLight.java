class CureLight extends SpellHeal

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 2000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public CureLight()

  {
    super();

    id = SPELL_CURE_LIGHT;
    name = "Cure Light";
    desc = "Calls upon the holy light to restore vitality.";
    prof = "Cl Dr Mo Pr Hl Kn Ra Sc";
    manacost = 50;
    reqINT = 50;
    reqWIS = 50;
    importance = 15;
    learnRate = 5;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void updateOutput()

  {
    selfOut = "#bYou call upon the light of heaven to heal " + tn + ".#N";
    targOut = "#b" + Mn + " calls upon the light of heaven to heal your wounds.#N";
    grupOut = "#b" + Mn + " calls upon the light of heaven to heal " + tn + ".#N";
    obsvOut = "#b" + Mn + " calls upon the light of heaven to heal " + tn + ".#N";

    spellOutput(true, false);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public int getAmount()

  {
    int amount = level + Utility.getRandomInt(80, 120);
    return amount;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}