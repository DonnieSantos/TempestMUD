#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

string information()

{
  string temp = title_screen() + "\r\n\n";

  temp += " #R[#N Type 'HELP' for general information regarding gameplay and commands #R]#N";

  return temp;
}

string title_screen()

{
  string temp;

  temp += "#c   / ##         /\r\n";
  temp += "  / ###########/\r\n";
  temp += " /   #########\r\n";
  temp += " #   /  #                                                                  #\r\n";
  temp += "  ##/  ##                                                                 ##\r\n";
  temp += "   /  ###          /##  ### /### /###       /###      /##       /###      ##\r\n";
  temp += "  ##   ##         / ###  ##/ ###/ /##  /   / ###  /  / ###     / #### / #######\r\n";
  temp += "  ##   ##        /   ###  ##  ###/ ###/   /   ###/  /   ###   ##  ###/ ########\r\n";
  temp += "  ##   ##       ##    ### ##   ##   ##   ##    ##  ##    ### ####         ##\r\n";
  temp += "  ##   ##       ########  ##   ##   ##   ##    ##  ########    ###        ##\r\n";
  temp += "   ##  ##       #######   ##   ##   ##   ##    ##  #######       ###      ##\r\n";
  temp += "    ## #      / ##        ##   ##   ##   ##    ##  ##              ###    ##\r\n";
  temp += "     ###     /  ####    / ##   ##   ##   ##    ##  ####    /  /###  ##    ##\r\n";
  temp += "      ######/    ######/  ###  ###  ###  #######    ######/  / #### /     ##\r\n";
  temp += "        ###       #####    ###  ###  ### ######      #####      ###/       ##\r\n";
  temp += "                                         ##\r\n";
  temp += "                                         ##       #m__  __  _    _  _____   \r\n";
  temp += "    #n'A Multi-User Dimension#c              ##      #m|  \\/  || |  | ||  __ \\  \r\n";
  temp += "     #ndedicated to true roleplaying.'#c     ##      #m| \\  / || |  | || |  | | \r\n";
  temp += "                                                 #m| |\\/| || |  | || |  | | \r\n";
  temp += "     #yBrought to you by#n:                          #m| |  | || |__| || |__| | \r\n";
  temp += "     #rO#Rm#re#Rg#ra#Rf#rl#Ra#rr#Re #rSoftware                         #m|_|  |_| \\____/ |_____/  \r\n";
  temp += "\n";
  temp += " #n+----------------------------------------+-----------------------------------+\r\n";
  temp += " #n|                                        |                                   |\r\n";
  temp += " #n| #NAddress#R: #gtelnet://tempestmud.net:7000  #n| #YTempest is a strict roleplaying   #n|\r\n";
  temp += " #n| #NWebsite#R: #rhttp://omegaflare.com/tempest #n| #Ycommunity. Please keep all public #n|\r\n";
  temp += " #n| #NContact#R: #btempest@omegaflare.com        #n| #Yspeech and behavior in-character. #n|\r\n";
  temp += " #n|                                        |                                   |\r\n";
  temp += " #n+----------------------------------------+-----------------------------------+#N";

  return temp;
}

string colorlist()

{
  string temp;

  temp  = "  #n+-----------------------------------+#N\r\n  #n|#N Available Colors#n:#N                 #n|#N\r\n";
  temp += "  #n|#N                                   #n|\r\n  #n|#N n = #nWhite#N      N = #NGrey (Normal)#N  #n|#N\r\n";
  temp += "  #n|#N r = #rRed#N        R = #RDark Red#N       #n|#N\r\n  #n|#N g = #gGreen#N    ";
  temp += "  G = #GDark Green#N     #n|#N\r\n";
  temp += "  #n|#N b = #bBlue#N       B = #BDark Blue#N      #n|#N\r\n  #n|#N y = #yYellow#N   ";
  temp += "  Y = #YDark Yellow#N    #n|#N\r\n";
  temp += "  #n|#N c = #cCyan#N       C = #CDark Cyan#N      #n|#N\r\n  #n|#N m = #mMagenta#N  ";
  temp += "  M = #MPurple#N         #n|#N\r\n";
  temp += "  #n|#N                                   #n|#N\r\n  #n|#N Usage#n:#N    # + Color Letter + Text #n|#N\r\n";
  temp += "  #n|#N Example#n:#N  #xHello!                #n|#N\r\n  #n|#N                                   #n|#N\r\n";
  temp += "  #n|#N To Enable or Disable ANSI color#n:#N  #n|#N\r\n";
  temp += "  #n|#N #R[#Ntoggle ansi#R]#N or #R[#Ntoggle color#R]#N   #n|#N\r\n";
  temp += "  #n+-----------------------------------+#N";

  return temp;
}

string classlist()

{
  string temp = "";

  temp += "  #n+---------------------+---------------------+#N\r\n";
  temp += "  #n|#N (#BWa#N) - Warrior      #n|#N (#YTh#N) - Thief        #n|#N\r\n";
  temp += "  #n|#N (#CKn#N) - Knight       #n|#N (#rAs#N) - Assassin     #n|#N\r\n";
  temp += "  #n|#N (#RBr#N) - Berzerker    #n|#N (#YRg#N) - Rogue        #n|#N\r\n";
  temp += "  #n|#N (#bPa#N) - Paladin      #n|#N (#GRa#N) - Ranger       #n|#N\r\n";
  temp += "  #n|#N (#cCr#N) - Crusader     #n|#N (#YMe#N) - Merchant     #n|#N\r\n";
  temp += "  #n|#N (#BDk#N) - Death Knight #n|#N (#bSb#N) - Shadowblade  #n|#N\r\n";
  temp += "  #n|#N (#yAp#N) - Anti Paladin #n|#N (#CMc#N) - Mercenary    #n|#N\r\n";
  temp += "  #n+---------------------+---------------------+#N\r\n";
  temp += "  #n|#N (#GCl#N) - Cleric       #n|#N (#MMg#N) - Mage         #n|#N\r\n";
  temp += "  #n|#N (#gDr#N) - Druid        #n|#N (#mWi#N) - Wizard       #n|#N\r\n";
  temp += "  #n|#N (#GMo#N) - Monk         #n|#N (#yIl#N) - Illusionist  #n|#N\r\n";
  temp += "  #n|#N (#nPr#N) - Priest       #n|#N (#gSc#N) - Sorcerer     #n|#N\r\n";
  temp += "  #n|#N (#bHl#N) - Healer       #n|#N (#cSm#N) - Summoner     #n|#N\r\n";
  temp += "  #n|#N (#BDc#N) - Dark Cleric  #n|#N (#MWl#N) - Warlock      #n|#N\r\n";
  temp += "  #n|#N (#gAl#N) - Alchemist    #n|#N (#RSh#N) - Shapeshifter #n|#N\r\n";
  temp += "  #n+---------------------+---------------------+#N";

  return temp;
}

string immortal_commands()

{
  string temp;

  temp  = "  #n+---------------------------------------------------+\r\n";
  temp += "  |                 #cImmortal Commands                 #n|\r\n";
  temp += "  | ------------------------------------------------- |\r\n";
  temp += "  | #NAt                #R[#Nat target action#R]              #n|\r\n";
  temp += "  | #NGoto              #R[#Ngoto target#R]                   #n|\r\n";
  temp += "  | #NForce             #R[#Nforce target action#R]           #n|\r\n";
  temp += "  | #NTransport         #R[#Ntrans target#R]                  #n|\r\n";
  temp += "  | #NPossess           #R[#Npos mobname#R]                   #n|\r\n";
  temp += "  | #NUnpossess         #R[#Nunpos mobname#R]                 #n|\r\n";
  temp += "  | #NLoad              #R[#Nload mob/item id_num#R]          #n|\r\n";
  temp += "  | #NPurge             #R[#Npurge username/item_name#R]      #n|\r\n";
  temp += "  | #NSlay              #R[#Nslay mob_name#R]                 #n|\r\n";
  temp += "  | #NRestore           #R[#Nrestore target#R]                #n|\r\n";
  temp += "  | #NStat              #R[#Nstat target#R]                   #n|\r\n";
  temp += "  | #NUsers             #R[#Nusers#R]                         #n|\r\n";
  temp += "  | #NRoom List         #R[#Nrlist#R]                         #n|\r\n";
  temp += "  | #NZone List         #R[#Nzlist#R]                         #n|\r\n";
  temp += "  | #NItem List         #R[#Nilist#R]                         #n|\r\n";
  temp += "  | #NMobile List       #R[#Nmlist#R]                         #n|\r\n";
  temp += "  | #NZone Clear        #R[#Nzclear znum#R]                   #n|\r\n";
  temp += "  | #NZone Repopulate   #R[#Nzrepop znum#R]                   #n|\r\n";
  temp += "  | #NMark Legend       #R[#Nmark target legend_mark#R]       #n|\r\n";
  temp += "  | #NBestow            #R[#Nbestow skill_name upon target#R] #n|\r\n";
  temp += "  | #NSystem Time       #R[#Nsystime#R]                       #n|\r\n";
  temp += "  | #NFreeze            #R[#Nfreeze username#R]               #n|\r\n";
  temp += "  | #NUnfreeze          #R[#Nunfreeze username#R]             #n|\r\n";
  temp += "  | #NDisconnect        #R[#Ndc username#R]                   #n|\r\n";
  temp += "  | #NDelete            #R[#Ndelete username#R]               #n|\r\n";
  temp += "  | #NDisintegrate      #R[#Ndisintegrate username#R]         #n|\r\n";
  temp += "  +---------------------------------------------------+#N";

  return temp;
}

string clan_commands()

{
  string temp = "";

  temp += "  #n+------------------------------+#N\r\n";
  temp += "  #n|   #mAvailable Clan Commands#n:   |#N\r\n";
  temp += "  #n+------------------------------+#N\r\n";
  temp += "  #n| #R[#Nclan list#R]#N                  #n|#N\r\n";
  temp += "  #n| #R[#Nclan info clan_name#R]#N        #n|#N\r\n";
  temp += "  #n| #R[#Nclan ranks clan_name#R]#N       #n|#N\r\n";
  temp += "  #n| #R[#Nclan roster clan_name#R]#N      #n|#N\r\n";
  temp += "  #n| #R[#Nclan apply clan_name#R]#N       #n|#N\r\n";
  temp += "  #n| #R[#Nclan enlist player_name#R]#N    #n|#N\r\n";
  temp += "  #n| #R[#Nclan expel player_name#R]#N     #n|#N\r\n";
  temp += "  #n| #R[#Nclan raise player_name#R]#N     #n|#N\r\n";
  temp += "  #n| #R[#Nclan demote player_name#R]#N    #n|#N\r\n";
  temp += "  #n| #R[#Nclan resign YES#R]#N            #n|#N\r\n";
  temp += "  #n| #R[#Nctell message#R]#N              #n|#N\r\n";
  temp += "  #n+------------------------------+#N";

  return temp;
}

string religion_commands()

{
  string temp = "";

  temp += "  #n+--------------------------------------+#N\r\n";
  temp += "  #n|     #yAvailable Religion Commands#n:     |#N\r\n";
  temp += "  #n+--------------------------------------+#N\r\n";
  temp += "  #n| #R[#Nreligion list#R]#N                      #n|#N\r\n";
  temp += "  #n| #R[#Nreligion info religion_name#R]#N        #n|#N\r\n";
  temp += "  #n| #R[#Nreligion degrees religion_name#R]#N     #n|#N\r\n";
  temp += "  #n| #R[#Nreligion members religion_name#R]#N     #n|#N\r\n";
  temp += "  #n| #R[#Nreligion apply religion_name#R]#N       #n|#N\r\n";
  temp += "  #n| #R[#Nreligion admit player_name#R]#N         #n|#N\r\n";
  temp += "  #n| #R[#Nreligion excommunicate player_name#R]#N #n|#N\r\n";
  temp += "  #n| #R[#Nreligion raise player_name#R]#N         #n|#N\r\n";
  temp += "  #n| #R[#Nreligion lower player_name#R]#N         #n|#N\r\n";
  temp += "  #n| #R[#Nreligion forsake YES#R]#N               #n|#N\r\n";
  temp += "  #n+--------------------------------------+#N";

  return temp;
}

string immortal_lists()

{
  string temp = "";

  temp += "  #n+------------------------------------------------------------------+#N\r\n";
  temp += "  #n|                         #CImmortal Lists                           #n|#N\r\n";
  temp += "  #n| ---------------------------------------------------------------- |#N\r\n";
  temp += "  #n| #NGlobal#R:   #Nilist all        (Show all items)                      #n|#N\r\n";
  temp += "  #n| #NBy Name#R:  #Nilist sword      (Show all items named 'sword')        #n|#N\r\n";
  temp += "  #n| #NBy Zone#R:  #Nilist 10         (Show all items in Zone 10)           #n|#N\r\n";
  temp += "  #n| #NBy ID#R:    #Nilist 1000-2000  (Show all items in the given range)   #n|#N\r\n";
  temp += "  #n|                                                                  |#N\r\n";
  temp += "  #n| #NGlobal#R:   #Nmlist all        (Show all mobiles)                    #n|#N\r\n";
  temp += "  #n| #NBy Name#R:  #Nmlist troll      (Show all mobiles named 'troll')      #n|#N\r\n";
  temp += "  #n| #NBy Zone#R:  #Nmlist 10         (Show all mobiles in Zone 10)         #n|#N\r\n";
  temp += "  #n| #NBy ID#R:    #Nmlist 1000-2000  (Show all mobiles in the given range) #n|#N\r\n";
  temp += "  #n|                                                                  |#N\r\n";
  temp += "  #n| #NGlobal#R:   #Nrlist all        (Show all rooms)                      #n|#N\r\n";
  temp += "  #n| #NBy Name#R:  #Nrlist temple     (Show all rooms named 'temple')       #n|#N\r\n";
  temp += "  #n| #NBy Zone#R:  #Nrlist 10         (Show all rooms in Zone 10)           #n|#N\r\n";
  temp += "  #n| #NBy ID#R:    #Nrlist 1000-2000  (Show all rooms in the given range)   #n|#N\r\n";
  temp += "  #n|                                                                  |#N\r\n";
  temp += "  #n| #NGlobal#R:   #Nzlist all        (Show all zones)                      #n|#N\r\n";
  temp += "  #n| #NBy Name#R:  #Nzlist damascus   (Show all zones named 'damascus')     #n|#N\r\n";
  temp += "  #n| #NBy ID#R:    #Nzlist 20-50      (Show all zones in the given range)   #n|#N\r\n";
  temp += "  #n+------------------------------------------------------------------+#N";

  return temp;
}