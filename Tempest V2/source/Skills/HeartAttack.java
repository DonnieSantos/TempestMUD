import java.io.*;
import java.util.*;

class HeartAttack extends Skill

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private static final long serialVersionUID = 1000;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public HeartAttack()

  {
    super();

    id = SKILL_HEART_ATTACK;
    name = "Heart Attack";
    desc = "Deadly strike to the very heart of your enemy.";
    prof = "As Sb";
    importance = 240;
    learnRate = 5;
    reqSTR = 50;
    reqDEX = 200;
    reqCON = 50;
    reqINT = 10;
    reqWIS = 10;

    addRequirement("Circle Around", 90);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean execute(String rem)

  {
    // Seven times the damage of Stab and Twist.
    // Passive skill, happens randomly when you execute stab.

    return false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}