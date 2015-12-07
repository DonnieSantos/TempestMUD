abstract class SpellDamage extends Spell

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 2000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  protected int damage;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public SpellDamage()

  {
    super();
    spellType = SPELL_TYPE_DAMAGE;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  abstract void updateOutput();
  abstract int getDamage();

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean cast(String rem)

  {
    if ((rem.length() <= 0) && (caster.getPlayerState() == Utility.PSTATE_FIGHTING))
      target = caster.getTarget();
    else target = caster.getRoom().findEntity(caster, rem);

    if ((rem.length() <= 0) && (target == null)) {
      caster.echo("Who do you want to cast it on?");
      return false; }

    if (!Combat.canAttack(caster, target, true, true)) return false;

    if (caster.getCurrentMN() < manacost) {
      caster.echo("You don't have enough mana to cast " + name + ".");
      return false; }

    damage = target.resistMagic(getDamage());
    Combat.engage(caster, target, false);
    caster.setCurrentMN(caster.getCurrentMN()-manacost);
    target.setCurrentHP(target.getCurrentHP()-damage);

    tn = target.getName();
    Tn = target.getPName();
    ty = Utility.possessive(tn);
    Ty = Utility.possessive(Tn);

    updateOutput();
    appendDamageOutput(true);

    return true;
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
  
  public boolean itemCast(String rem, Item I, boolean first)
  
  {
    if ((rem.length() <= 0) && (caster.getPlayerState() == Utility.PSTATE_FIGHTING))
      target = caster.getTarget();
    else target = caster.getRoom().findEntity(caster, rem);

    if ((!first) && (target == null)) return false;

    if ((rem.length() <= 0) && (target == null)) {
      caster.echo("Who do you want to use it on?");
      return false; }

    if (!Combat.canAttack(caster, target, true, true)) return false;

    Combat.engage(caster, target, false);

    damage = target.resistMagic(getDamage());
    target.setCurrentHP(target.getCurrentHP()-damage);

    tn = target.getName();
    Tn = target.getPName();
    ty = Utility.possessive(tn);
    Ty = Utility.possessive(Tn);

    if (first) tryItemUse(I);
    appendDamageOutput(false);

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void appendDamageOutput(boolean trainSpell)

  {
    selfOut = "Your " + name + " hits " + tn + " for #R" + damage + "#N damage.";
    targOut = My + " " + name + " hits you for #R" + damage + "#N damage.";
    grupOut = My + " " + name + " hits " + tn + " for #R" + damage + "#N damage.";
    obsvOut = My + " " + name + " hits " + tn + " for #N" + damage + "#N damage.";
    spellOutput(false, trainSpell);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}