#include <stdio.h>
#include <iostream.h>

void main()

{
  int my_level;
  int my_str, my_dam;
  int targ_ac, targ_dex;

  cout << "Your Level ---------> ";
  cin >> my_level;

  cout << "Your Strength ------> ";
  cin >> my_str;

  cout << "Your Damroll -------> ";
  cin >> my_dam;

  cout << "Enemy Armorclass ---> ";
  cin >> targ_ac;

  cout << "Enemy Dexterity ----> ";
  cin >> targ_dex;

  int STANDARD_AC = ((my_level * 10) + 10);
  int STANDARD_DEX = (my_level * 1.5);
  int STANDARD_ABSORB = (int)((float)((STANDARD_AC + (2*STANDARD_DEX)) * .3));

  int ABSORB = (int)((float)((targ_ac + (2*targ_dex)) * .3));
  int DAMAGE = (int)((float)(((30*my_dam) + my_str) * .312)) + STANDARD_ABSORB;
  int TOTAL_DAMAGE = DAMAGE - ABSORB;

  if (TOTAL_DAMAGE < (DAMAGE * .05)) TOTAL_DAMAGE = (DAMAGE * .05);

  cout << endl;
  cout << "Damage: " << DAMAGE << endl;
  cout << "Absorb: " << ABSORB << endl;
  cout << "DTOTAL: " << TOTAL_DAMAGE << endl;
}