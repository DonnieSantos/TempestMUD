class ExperienceManager

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static long[] expNeeded = new long [MAX_GOD_LEVEL+1];
  public static boolean arrayInitialized = false;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private Char owner;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public ExperienceManager(Char ownerChar)

  {
    owner = ownerChar

    if (!ExperienceManager.arrayInitialized)
      ExperienceManager.initializeExpNeeded();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public void receiveExperience(Entity target)

  {
    if (target.isPlayer()) return;
    Mobile mob = (Mobile) target;

    if (owner.getGroupsize() > 1)

    {
      int totalExp = mob.getExpWorth();
      int percentage = getExpPercent(owner.getGroupsize());
      int sharedExp = ((totalExp * percentage) / 100);
      owner.divideExperience(sharedExp);
    }

    else owner.gainExperience(mob.getExpWorth());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private void gainExperience(int amountGained)

  {
    if (owner.getLevel() < 99)

    {
      int totalExpNeeded = expNeeded[owner.getLevel()];

      if (amountGained > (totalExpNeeded / 100))
        amountGained = (totalExpNeeded / 100);

      owner.setExperience(owner.getExperience() + amountGained);

      if (owner.getExperience() >= totalExpNeeded) levelUp();
      else owner.combatEcho("#yYou receive " + amountGained + " experience!#N");
    }

    else owner.setExperience(0);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private void levelUp()

  {
    if (owner.getLevel() < 99)

    {
      owner.setLevel(owner.getLevel()+1);
      owner.setDPoints(owner.getDPoints()+5);

      int maxHP = owner.getMaxHP();
      int maxMN = owner.getMaxMN();
      int maxMV = owner.getMaxMV();

      int HP_MULT = ClassMethods.calculateHPMult(owner.getProfession());
      int MN_MULT = ClassMethods.calculateMNMult(owner.getProfession());
      int MV_MULT = ClassMethods.calculateMVMult(owner.getProfession());

      maxHP += (HP_MULT + (owner.getCON()/10));
      maxMN += (MN_MULT + (owner.getWIS()/10));
      maxMV += (MV_MULT + (owner.getCON()/20));

      owner.setMaxHP(maxHP);
      owner.setMaxMN(maxMN);
      owner.setMaxMV(maxMV);

      owner.combatEcho("#gYour level has increased!#N");
      owner.save();
    }

    else owner.setExperience(0);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static void initializeExpNeeded()

  {
    expNeeded[0] = 0;
    expNeeded[1] = 50000;
    expNeeded[2] = 95000;
    expNeeded[3] = 140000;
    expNeeded[4] = 185000;
    expNeeded[5] = 230000;
    expNeeded[6] = 275000;
    expNeeded[7] = 320000;
    expNeeded[8] = 400000;
    expNeeded[9] = 500000;
    expNeeded[10] = 650000;
    expNeeded[11] = 800000;
    expNeeded[12] = 950000;
    expNeeded[13] = 1100000;
    expNeeded[14] = 1250000;
    expNeeded[15] = 1400000;
    expNeeded[16] = 1550000;
    expNeeded[17] = 1700000;
    expNeeded[18] = 1850000;
    expNeeded[19] = 2000000;
    expNeeded[20] = 2400000;
    expNeeded[21] = 2800000;
    expNeeded[22] = 3200000;
    expNeeded[23] = 3600000;
    expNeeded[24] = 4000000;
    expNeeded[25] = 4400000;
    expNeeded[26] = 4800000;
    expNeeded[27] = 5200000;
    expNeeded[28] = 5600000;
    expNeeded[29] = 6000000;
    expNeeded[30] = 7400000;
    expNeeded[31] = 8800000;
    expNeeded[32] = 10200000;
    expNeeded[33] = 11600000;
    expNeeded[34] = 13000000;
    expNeeded[35] = 14400000;
    expNeeded[36] = 15800000;
    expNeeded[37] = 17200000;
    expNeeded[38] = 18600000;
    expNeeded[39] = 20000000;
    expNeeded[40] = 23000000;
    expNeeded[41] = 26000000;
    expNeeded[42] = 29000000;
    expNeeded[43] = 32000000;
    expNeeded[44] = 35000000;
    expNeeded[45] = 38000000;
    expNeeded[46] = 41000000;
    expNeeded[47] = 44000000;
    expNeeded[48] = 47000000;
    expNeeded[49] = 50000000;
    expNeeded[50] = 52500000;
    expNeeded[51] = 55000000;
    expNeeded[52] = 57500000;
    expNeeded[53] = 60000000;
    expNeeded[54] = 62500000;
    expNeeded[55] = 65000000;
    expNeeded[56] = 67500000;
    expNeeded[57] = 70000000;
    expNeeded[58] = 72500000;
    expNeeded[59] = 75000000;
    expNeeded[60] = 77500000;
    expNeeded[61] = 80000000;
    expNeeded[62] = 82500000;
    expNeeded[63] = 85000000;
    expNeeded[64] = 87500000;
    expNeeded[65] = 90000000;
    expNeeded[66] = 92500000;
    expNeeded[67] = 95000000;
    expNeeded[68] = 97500000;
    expNeeded[69] = 100000000;
    expNeeded[70] = 110000000;
    expNeeded[71] = 120000000;
    expNeeded[72] = 130000000;
    expNeeded[73] = 140000000;
    expNeeded[74] = 150000000;
    expNeeded[75] = 160000000;
    expNeeded[76] = 170000000;
    expNeeded[77] = 180000000;
    expNeeded[78] = 190000000;
    expNeeded[79] = 200000000;
    expNeeded[80] = 230000000;
    expNeeded[81] = 260000000;
    expNeeded[82] = 290000000;
    expNeeded[83] = 320000000;
    expNeeded[84] = 350000000;
    expNeeded[85] = 380000000;
    expNeeded[86] = 410000000;
    expNeeded[87] = 440000000;
    expNeeded[88] = 470000000;
    expNeeded[89] = 500000000;
    expNeeded[90] = 550000000;
    expNeeded[91] = 600000000;
    expNeeded[92] = 650000000;
    expNeeded[93] = 700000000;
    expNeeded[94] = 800000000;
    expNeeded[95] = 850000000;
    expNeeded[96] = 900000000;
    expNeeded[97] = 950000000;
    expNeeded[98] = 999999999;

    for (int i=99; i<=MAX_GOD_LEVEL; i++)
      expNeeded[i] = 0;

    arrayInitialized = true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}