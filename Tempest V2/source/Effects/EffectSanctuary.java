import java.io.*;
import java.util.*;

public class EffectSanctuary extends Effect

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 3000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void tick() { }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public EffectSanctuary()

  {
    super();

    id = EFFECT_SANCTUARY;
    name = "Sanctuary";
    desc = "A holy aura protects you from all damage.";
    dmsg = "You feel vulnerable.";
    wmsg = "You are protected by Sanctuary.";
    omsg = " is protected by Sanctuary.";
    sign = "#N[#yS#N]";
    harmful = false;
    active = false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void init(Object Owner, int Strength, int Duration)

  {
    initialize(Owner, Strength, Duration);
    owner = (Entity) Owner;

    if (getEntityOwner().addEffect(this))
      getEntityOwner().setSANC(true);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public void dissipate(boolean audible)

  {
    getEntityOwner().removeEffect(this);
    getEntityOwner().setSANC(false);
    if (audible) getEntityOwner().echo(dmsg);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}