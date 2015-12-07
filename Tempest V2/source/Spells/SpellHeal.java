abstract class SpellHeal extends Spell

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 2000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private boolean fullHeal;
  private boolean selfHeal;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public SpellHeal()

  {
    super();
    spellType = SPELL_TYPE_HEAL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  abstract void updateOutput();
  abstract int getAmount();

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean cast(String rem)

  {
    boolean OK = false;

    target = caster.getRoom().findEntity(caster, rem);

    if (rem.length() <= 0)
      caster.echo("Who do you want to cast it on?");
    else if (target == null)
      caster.echo("There's nobody around by that name.");
    else if (caster.getRoom().getFlag(Room.FLAG_NO_MAGIC))
      caster.echo("Your magic doesn't seem to work here.");
    else if (caster.getCurrentMN() < manacost)
      caster.echo("You don't have enough mana to cast " + name + ".");
    else if (target.getCurrentHP() <= 0)
      caster.echo(target.His() + " wounds are beyond healing.");
    else OK = true;

    if (!OK) return false;

    int amount = getAmount();
    caster.setCurrentMN(caster.getCurrentMN()-manacost);
    target.setCurrentHP(target.getCurrentHP()+amount);

    tn = target.getName();
    Tn = target.getPName();
    ty = Utility.possessive(tn);
    Ty = Utility.possessive(Tn);

    selfHeal = (target == caster);
    fullHeal = (target.getCurrentHP() >= target.getMaxHP());

    updateOutput();
    appendHealOutput(selfHeal, fullHeal, true);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean itemCast(String rem, Item I, boolean first)

  {
    boolean OK = false;

    target = caster.getRoom().findEntity(caster, rem);

    if (rem.length() <= 0)
      caster.echo("Who do you want to cast it on?");
    else if (target == null)
      caster.echo("There's nobody around by that name.");
    else if (caster.getRoom().getFlag(Room.FLAG_NO_MAGIC))
      caster.echo("It doesn't seem to work here.");
    else if (target.getCurrentHP() <= 0)
      caster.echo(target.His() + " wounds are beyond healing.");
    else OK = true;

    if (!OK) return false;

    int amount = getAmount();
    target.setCurrentHP(target.getCurrentHP()+amount);

    tn = target.getName();
    Tn = target.getPName();
    ty = Utility.possessive(tn);
    Ty = Utility.possessive(Tn);

    selfHeal = (target == caster);
    fullHeal = (target.getCurrentHP() >= target.getMaxHP());

    if (first) tryItemUse(I);
    appendHealOutput(selfHeal, fullHeal, false);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void appendHealOutput(boolean self, boolean full, boolean trainSpell)

  {
    if ((self) && (full))

    {
      selfOut = "#NYou have completely healed yourself.#N";
      targOut = "#NYou have completely healed yourself.#N";
      grupOut = "#N" + Mn + " has completely healed " + target.himself() + ".#N";
      obsvOut = "#N" + Mn + " has completely healed " + target.himself() + ".#N";
    }

    else if ((self) && (!full))

    {
      selfOut = "#NYou heal some of your wounds.#N";
      targOut = "#NYou heal some of your wounds.#N";
      grupOut = "#N" + Mn + " has healed some of " + target.his() + " wounds.#N";
      obsvOut = "#N" + Mn + " has healed some of " + target.his() + " wounds.#N";
    }

    else if ((!self) && (full))

    {
      selfOut = "#NYou have completely healed " + tn + ".#N";
      targOut = "#NYou have been completely healed by " + mn + ".#N";
      grupOut = "#N" + Mn + " has completely healed " + tn + ".#N";
      obsvOut = "#N" + Mn + " has completely healed " + tn + ".#N";
    }

    else if ((!self) && (!full))

    {
      selfOut = "#NYou heal some of " + ty + " wounds.#N";
      targOut = "#N" + Mn + " has healed some of your wounds.#N";
      grupOut = "#N" + Mn + " has healed some of " + ty + " wounds.#N";
      obsvOut = "#N" + Mn + " has healed some of " + ty + " wounds.#N";
    }

    spellOutput(false, trainSpell);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}