import java.io.*;
import java.util.*;

public class EffectPoison extends Effect

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 3000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private Entity E;
  private float percent;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public EffectPoison()

  {
    super();

    id = EFFECT_POISON;
    name = "Poison";
    desc = "Poisons your blood and makes you suffer.";
    dmsg = "You feel much better.";
    wmsg = "You have been poisoned!";
    omsg = " has been poisoned!";
    sign = "#N[#gP#N]";
    harmful = true;
    active = true;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void init(Object Owner, int Strength, int Duration)

  {
    initialize(Owner, Strength, Duration);
    owner = (Entity) Owner;

    if (getEntityOwner().addEffect(this))
      getEntityOwner().setPoisoned(true);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void dissipate(boolean audible)

  {
    getEntityOwner().removeEffect(this);
    getEntityOwner().setPoisoned(false);
    if (audible) getEntityOwner().echo(dmsg);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void tick()

  {
    E = getEntityOwner();
    percent = (float)strength / (float)100;

    if (E.isPlayer())

    {
      E.setCurrentHP(E.getCurrentHP()-(int)(E.getMaxHP()*(float)percent));
      E.setCurrentMN(E.getCurrentMN()+(int)(E.getMaxMN()*(float)0.05));
      E.setCurrentMV(E.getCurrentMV()+(int)(E.getMaxMV()*(float)0.05));
      if (E.getCurrentHP() <= 0) E.setCurrentHP(1);
      E.echo("#RThe poison courses through your veins, making you suffer.#N");
      return;
    }

    E.setCurrentMN((int)((E.getMaxMN()+10) * 0.2));
    E.setCurrentMV((int)((E.getMaxMV()+10) * 0.2));
    if ((float)((float)E.getCurrentHP() / (float)E.getMaxHP()) <= (float)0.5) return;
    E.setCurrentHP(E.getCurrentHP()-(int)(E.getMaxHP()*(float)percent));

    if ((float)((float)E.getCurrentHP() / (float)E.getMaxHP()) <= (float)0.5)
      E.setCurrentHP(E.getMaxHP() / 2);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}