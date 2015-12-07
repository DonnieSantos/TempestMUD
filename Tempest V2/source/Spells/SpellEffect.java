abstract class SpellEffect extends Spell

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 2000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  protected Effect effect;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public SpellEffect()

  {
    super();
    spellType = SPELL_TYPE_EFFECT_HELPFUL;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  abstract void createEffect();
  abstract void initEffect();
  abstract void updateOutput(boolean self);
  abstract void appendOutput(boolean self, boolean trainSpell);

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void makeHelpful() { spellType = SPELL_TYPE_EFFECT_HELPFUL; }
  public void makeHarmful() { spellType = SPELL_TYPE_EFFECT_HARMFUL; }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean cast(String rem)

  {
    createEffect();

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
    else if ((effect.getHarmful()) && (!Combat.canAttack(caster, target, true, true)))
      OK = false;
    else OK = true;

    if (!OK) return false;

    initEffect();

    if (effect.getHarmful()) Combat.engage(caster, target, false);
    caster.setCurrentMN(caster.getCurrentMN()-manacost);

    tn = target.getName();
    Tn = target.getPName();
    ty = Utility.possessive(tn);
    Ty = Utility.possessive(Tn);

    boolean self = (target == caster);

    updateOutput(self);
    appendOutput(self, true);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean itemCast(String rem, Item I, boolean first)

  {
    createEffect();

    boolean OK = false;

    target = caster.getRoom().findEntity(caster, rem);

    if (rem.length() <= 0)
      caster.echo("Who do you want to use it on?");
    else if (target == null)
      caster.echo("There's nobody around by that name.");
    else if (caster.getRoom().getFlag(Room.FLAG_NO_MAGIC))
      caster.echo("Your magic doesn't seem to work here.");
    else OK = true;

    if (target != caster)

    {
      if ((first) && ((effect.getHarmful()) && (!Combat.canAttack(caster, target, true, true))))
        return false;

      if ((!first) && ((effect.getHarmful()) && (!Combat.canAttackSilent(caster, target, true, true))))
        return false;
    }

    if (!OK) return false;

    initEffect();

    tn = target.getName();
    Tn = target.getPName();
    ty = Utility.possessive(tn);
    Ty = Utility.possessive(Tn);

    boolean self = (target == caster);

    if (first) tryItemUse(I);
    appendOutput(self, false);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}