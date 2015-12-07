abstract class SpellAreaDamage extends Spell

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 2000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private boolean hit;
  private int damage;
  private int count;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public SpellAreaDamage()

  {
    super();
    spellType = SPELL_TYPE_AREADAMAGE;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  abstract void updateOutput();
  abstract int getDamage();

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean cast(String rem)

  {
    boolean OK = false;

    if (caster.getRoom().getFlag(Room.FLAG_LAWFULL))
      caster.echo("You may not attack others in this place.");
    else if (caster.getRoom().getFlag(Room.FLAG_NO_MAGIC))
      caster.echo("Your magic doesn't seem to work here.");
    else if (caster.getCurrentMN() < manacost)
      caster.echo("You don't have enough mana to cast " + name + ".");
    else OK = true;

    if (!OK) return false;

    caster.setCurrentMN(caster.getCurrentMN()-manacost);

    count = 0;
    hit = false;

    int size = caster.getRoom().getSize();

    for (int i=size-1; i>=0; i--)
    if (Combat.canAttackSilent(caster, caster.getRoom().getEnt(i), true, true))

    {
      target = caster.getRoom().getEnt(i);

      damage = target.resistMagic(getDamage());
      Combat.engage(caster, target, false);
      target.setCurrentHP(target.getCurrentHP()-damage);

      if (damage > 0) hit = true;

      tn = target.getName();
      Tn = target.getPName();
      ty = Utility.possessive(tn);
      Ty = Utility.possessive(Tn);

      if (count == 0) updateOutput();

      updateDamageOutput();

      count++;
    }

    target = caster;

    if (count == 0) updateOutput();

    if (hit) trainRedirect();

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean itemCast(String rem, Item I, boolean first)

  {
    boolean OK = false;

    if (caster.getRoom().getFlag(Room.FLAG_LAWFULL))
      caster.echo("You may not attack others in this place.");
    else if (caster.getRoom().getFlag(Room.FLAG_NO_MAGIC))
      caster.echo("It doesn't seem to work here.");
    else OK = true;

    if (!OK) return false;

    count = 0;

    int size = caster.getRoom().getSize();

    target = caster;

    if (first) tryItemUse(I);

    for (int i=size-1; i>=0; i--)
    if (Combat.canAttackSilent(caster, caster.getRoom().getEnt(i), true, true))

    {
      target = caster.getRoom().getEnt(i);

      damage = target.resistMagic(getDamage());
      Combat.engage(caster, target, false);
      target.setCurrentHP(target.getCurrentHP()-damage);

      tn = target.getName();
      Tn = target.getPName();
      ty = Utility.possessive(tn);
      Ty = Utility.possessive(Tn);

      updateDamageOutput();

      count++;
    }

    target = caster;

    if ((first) && (count == 0)) failedItemUse();

    return true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private void updateDamageOutput()

  {
    selfOut = "Your " + name + " hits " + tn + " for #R" + damage + "#N damage.";
    targOut = My + " " + name + " hits you for #R" + damage + "#N damage.";
    grupOut = My + " " + name + " hits " + tn + " for #R" + damage + "#N damage.";
    obsvOut = My + " " + name + " hits " + tn + " for #N" + damage + "#N damage.";

    spellOutput(false, false);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}