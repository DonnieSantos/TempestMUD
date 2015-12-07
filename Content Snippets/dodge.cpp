#include <stdio.h>
#include <iostream.h>

void main()

{
  int Level;
  int My_DEX, Target_DEX, Hitroll;

  cout << "Your Level ---------> ";
  cin >> Level;

  cout << "Your Dexterity -----> ";
  cin >> My_DEX;

  cout << "Your Hitroll -------> ";
  cin >> Hitroll;

  cout << "Enemy Dexterity ----> ";
  cin >> Target_DEX;

  int STANDARD_HIT = ((int)((float)Level / 5) + 1);
  int STANDARD_DEX = ((int)((float)Level * 1.5) + 2);

  int HIT = (int)(((float)Hitroll / (float)STANDARD_HIT) * 100);
  int AIM = (int)(((((float)My_DEX / (float)STANDARD_DEX) * 100) - 100) / 2);
  int DODGE = (int)(((((float)Target_DEX / (float)STANDARD_DEX) * 100) - 100) / 2);

  int hit_rate = HIT + AIM - DODGE;
  if (hit_rate < 5) hit_rate = 5;
  if (hit_rate > 95) hit_rate = 95;

  cout << endl;
  cout << "HIT     : " << HIT << endl;
  cout << "AIM     : " << AIM << endl;
  cout << "DODGE   : " << DODGE << endl;
  cout << "HIT RATE: " << hit_rate << endl;
}