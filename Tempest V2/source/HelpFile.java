import java.util.*;
import java.io.*;

public class HelpFile

{
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String dq = Character.toString((char)34);

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String help(String rem)

  {
    if (rem.length() <= 0) return getHelpMenu();

    if (Utility.match(rem, "reg", "register"))      return helpRegister();
    if (Utility.match(rem, "reg", "registration"))  return helpRegister();
    if (Utility.match(rem, "cre", "credits"))       return helpCredits();
    if (Utility.match(rem, "pol", "policy"))        return helpPolicy();
    if (Utility.match(rem, "rol", "roleplaying"))   return helpRoleplaying();
    if (Utility.match(rem, "rp", "rp"))             return helpRoleplaying();
    if (Utility.match(rem, "her", "heresy"))        return helpRoleplaying();
    if (Utility.match(rem, "col", "colors"))        return helpColor();
    if (Utility.match(rem, "cla", "classes"))       return helpClass();
    if (Utility.match(rem, "wa", "warriors"))       return helpWarrior();
    if (Utility.match(rem, "th", "thiefs"))         return helpThief();
    if (Utility.match(rem, "th", "thieves"))        return helpThief();
    if (Utility.match(rem, "cl", "clerics"))        return helpCleric();
    if (Utility.match(rem, "ma", "mages"))          return helpMage();
    if (Utility.match(rem, "kn", "knights"))        return helpKnight();
    if (Utility.match(rem, "be", "berzerkers"))     return helpBerzerker();
    if (Utility.match(rem, "br", "br"))             return helpBerzerker();
    if (Utility.match(rem, "as", "assassins"))      return helpAssassin();
    if (Utility.match(rem, "ro", "rogues"))         return helpRogue();
    if (Utility.match(rem, "rg", "rg"))             return helpRogue();
    if (Utility.match(rem, "dr", "druids"))         return helpDruid();
    if (Utility.match(rem, "mo", "monks"))          return helpMonk();
    if (Utility.match(rem, "wi", "wizards"))        return helpWizard();
    if (Utility.match(rem, "il", "illusionists"))   return helpIllusionist();
    if (Utility.match(rem, "ali", "alignment"))     return helpAlignment();
    if (Utility.match(rem, "int", "interaction"))   return helpInteraction();
    if (Utility.match(rem, "tal", "talking"))       return helpInteraction();
    if (Utility.match(rem, "sk", "skills"))         return helpSkills();
    if (Utility.match(rem, "sp", "spells"))         return helpSpells();
    if (Utility.match(rem, "gr", "group"))          return helpGroup();
    if (Utility.match(rem, "fol", "follow"))        return helpFollow();

    return getHelpMenu();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getTitleNote()

  {
    String str = ""

    + "\r\n"
    + "+--------------------------------------------------------------------------+\r\n"
    + "|  #rVERY IMPORTANT NOTE !!!#N                                                 |\r\n"
    + "|                                                                          |\r\n"
    + "|  Tempest MUD was originally coded in C++, in February of 2003.           |\r\n"
    + "|  It is currently in the process of being recoded in Java, so the         |\r\n"
    + "|  MUD that you see now will be incomplete for a few weeks.  To see        |\r\n"
    + "|  a list of most of the commands (which probably half of them are         |\r\n"
    + "|  still being re-coded so are not back in yet) type '#rHELP#N'.               |\r\n"
    + "|                                                                          |\r\n"
    + "|  Beta testing has not even begun yet!  We don't have a playerbase        |\r\n"
    + "|  yet, because the game is still in alpha testing.                        |\r\n"
    + "|                                                                          |\r\n"
    + "|  We are currently looking for #rWRITERS#N, #rBUILDERS#N, and #rCODERS#N.             |\r\n"
    + "|  If you are only interested in mudding, then please come back            |\r\n"
    + "|  for beta testing starting January 1, 2005.                              |\r\n"
    + "|                                                                          |\r\n"
    + "|  E-mail #ctempest@omegaflare.com#N with all questions and comments,          |\r\n"
    + "|  And please visit our website at #bhttp://www.tempestmud.net#N!              |\r\n"
    + "|                                                                          |\r\n"
    + "|  Thank you,                                                              |\r\n"
    + "|                                                                          |\r\n"
    + "|  -#gGenevieve#N                                                              |\r\n"
    + "+--------------------------------------------------------------------------+\r\n";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getTitleScreen()

  {
    String str = ""

    + "#c   / ##         /\r\n"
    + "  / ###########/\r\n"
    + " /   #########\r\n"
    + " #   /  #                                                                  #\r\n"
    + "  ##/  ##                                                                 ##\r\n"
    + "   /  ###          /##  ### /### /###       /###      /##       /###      ##\r\n"
    + "  ##   ##         / ###  ##/ ###/ /##  /   / ###  /  / ###     / #### / #######\r\n"
    + "  ##   ##        /   ###  ##  ###/ ###/   /   ###/  /   ###   ##  ###/ ########\r\n"
    + "  ##   ##       ##    ### ##   ##   ##   ##    ##  ##    ### ####         ##\r\n"
    + "  ##   ##       ########  ##   ##   ##   ##    ##  ########    ###        ##\r\n"
    + "   ##  ##       #######   ##   ##   ##   ##    ##  #######       ###      ##\r\n"
    + "    ## #      / ##        ##   ##   ##   ##    ##  ##              ###    ##\r\n"
    + "     ###     /  ####    / ##   ##   ##   ##    ##  ####    /  /###  ##    ##\r\n"
    + "      ######/    ######/  ###  ###  ###  #######    ######/  / #### /     ##\r\n"
    + "        ###       #####    ###  ###  ### ######      #####      ###/       ##\r\n"
    + "                                         ##\r\n"
    + "                                         ##       #m__  __  _    _  _____   \r\n"
    + "    #n" + dq + "A Multi-User Dimension#c              ##      #m|  \\/  || |  | ||  __ \\  \r\n"
    + "     #ndedicated to true roleplaying." + dq + "#c     ##      #m| \\  / || |  | || |  | | \r\n"
    + "                                                 #m| |\\/| || |  | || |  | | \r\n"
    + "     #yBrought to you by#n:                          #m| |  | || |__| || |__| | \r\n"
    + "     #rO#Rm#re#Rg#ra#Rf#rl#Ra#rr#Re #rSoftware                         #m|_|  |_| \\____/ |_____/  \r\n"
    + "\n"
    + " #n+----------------------------------------+-----------------------------------+\r\n"
    + " #n|                                        |                                   |\r\n"
    + " #n| #NAddress#R: #gtelnet://tempestmud.net:7000  #n| #YTempest is a strict roleplaying   #n|\r\n"
    + " #n| #NWebsite#R: #rhttp://omegaflare.com/tempest #n| #Ycommunity. Please keep all public #n|\r\n"
    + " #n| #NContact#R: #btempest@omegaflare.com        #n| #Yspeech and behavior in-character. #n|\r\n"
    + " #n|                                        |                                   |\r\n"
    + " #n+----------------------------------------+-----------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getHelpMenu()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                #yHelp Menu#n                                |\r\n"
    + "  #n|                               -----------                               #n|\r\n"
    + "  #n|  #NType #n'help #R<#ntopic#R>#n'#N for detailed information on any command or topic.  #n|\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n| #Ysay#N       #n(#C'#n) #R:#N say <text>         #n|#Y Movement    #R:#N n e s w u d          #n|\r\n"
    + "  #n| #Yemote#N     #n(#C:#n) #R:#N me <text>          #n|#Y Stats       #R:#N stat / score         #n|\r\n"
    + "  #n| #Ytell#N      #n(#C" + dq + "#n) #R:#N tell <name> <text> #n|#Y Look        #R:#N look / look <target> #n|\r\n"
    + "  #n| #Ywhisper#N   #n(#C_#n) #R:#N whis <name> <text> #n|#Y Player List #R:#N who                  #n|\r\n"
    + "  #n| #Yyell#N      #n(#C+#n) #R:#N yell <text>        #n|#Y Player Info #R:#N finger <name>        #n|\r\n"
    + "  #n| #Yclantell#N  #n(#C;#n) #R:#N ct <text>          #n|#Y Game Info   #R:#N info                 #n|\r\n"
    + "  #n| #Ygrouptell#N #n(#C=#n) #R:#N gt <text>          #n|#Y Credits     #R:#N credits              #n|\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|    #rVARIOUS CHARACTER COMMANDS#R:#n     |         #rITEMS & EQUIPMENT#R:#n         |\r\n"
    + "  #n|   -----------------------------    |        --------------------        |\r\n"
    + "  #n|#N Kill, Attack, Assist, Flee, Wimp   #n|#N Equipment, Inventory, Wear, Remove #n|\r\n"
    + "  #n|#N Sit, Stand, Rest, Sleep, Consider  #n|#N Wield, Hold, Grab, Use, Count, Get #n|\r\n"
    + "  #n|#N Group, Ungroup, Follow, Ditch      #n|#N Give, Put, Drop, Sacrifice, Recite #n|\r\n"
    + "  #n|#N Learn, Unlearn, Teach, Exits       #n|#N Read, Write, Buy, Sell, Pull, Play #n|\r\n"
    + "  #n|#N Weather, Title, Prename, Auction   #n|#N Open, Close, Eat, Drink, Quaff     #n|\r\n"
    + "  #n|                                    #n|#N Fill, Empty, Pour, Examine, Donate #n|\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|   #rOther Suggested Help Topics#R:#n     | #YBuilt in Aliases  #R:#N alias <a1>     #n|\r\n"
    + "  #n|  ------------------------------    |#Y Multiple Commands #R:#N cmd1;;cmd2     #n|\r\n"
    + "  #n|#N   CLASSES, SKILLS, SPELLS          #n|#Y Repeat Command    #R:#N (!) or (\\)     #n|\r\n"
    + "  #n|#N   LEGENDS, QUESTS, INTERACTION     #n|#Y Current Game Date #R:#N time / date    #n|\r\n"
    + "  #n|#N   CLANS, RELIGIONS, IMMORTALS      #n|#Y Clear Screen      #R:#N clear          #n|\r\n"
    + "  #n|#N   LEVELS, STATS, GROUPING          #n|#Y Resize Screen     #R:#N resize <size>  #n|\r\n"
    + "  #n|#N   ALIGNMENT, COLORS, SOCIALS       #n|#Y Toggle Options    #R:#N toggle         #n|\r\n"
    + "  #n|#N   PLANS, DESCRIPTIONS, AUCTION     #n|#Y Save Character    #R:#N save           #n|\r\n"
    + "  #n|#N   POLICY, ROLEPLAYING, REGISTER    #n|#Y Quit Game         #R:#N quit           #n|\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpClass()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n"
    + "  #n|                       #gClasses#N                       #n|#N\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n"
    + "  #n|#N Your class determines which skills and spells you   #n|#N\r\n"
    + "  #n|#N may receive, and the type of equipment you can use. #n|#N\r\n"
    + "  #n|#N It also determines your natural health and mana.    #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N To view a full list of classes, type #n'#rclasses#n'#N.     #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N For information on one class, type #n'#rhelp #n<#Rclass#n>'#N.  #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N You must start as a #BWarrior#N, #YThief#N, #GCleric#N or #MMage#N. #n|#N\r\n"
    + "  #n|#N Upon reaching the 10th level, you have the option   #n|#N\r\n"
    + "  #n|#N to choose a specialized class, by talking to one    #n|#N\r\n"
    + "  #n|#N who is already of that class, and will sponsor you. #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N In addition to this, upon reaching the 50th level,  #n|#N\r\n"
    + "  #n|#N you have the option of changing your class to one   #n|#N\r\n"
    + "  #n|#N of the special classes reserved for a certain       #n|#N\r\n"
    + "  #n|#N alignment. All class changes are optional.          #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N For detailed information, type #n'#rhelp alignment#n'     |#N\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpAlignment()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n"
    + "  #n|                      #mAlignment#n                      |#N\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n"
    + "  #n|#N You are rated as aligned on a scale of 0-100, for   #n|#N\r\n"
    + "  #n|#N which lower values indicate a propensity for the    #n|#N\r\n"
    + "  #n|#N dark side, and higher values for the light. A value #n|#N\r\n"
    + "  #n|#N of 50 would be perfectly neutral.                   #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N Those who are level 50 or higher and of definitive  #n|#N\r\n"
    + "  #n|#N alignment (non-neutral) may optionally change their #n|#N\r\n"
    + "  #n|#N class to one of the special aligned classes. For    #n|#N\r\n"
    + "  #n|#N example, a Knight may shift to the Paladin or       #n|#N\r\n"
    + "  #n|#N Anti-Paladin classes. Like any class change, this   #n|#N\r\n"
    + "  #n|#N is optional, and requires that somebody of the new  #n|#N\r\n"
    + "  #n|#N class must sponsor and mentor you.                  #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N     Class          Light       Dark                 #n|#N\r\n"
    + "  #n|#N     -----------    --------    ------------         #n|#N\r\n"
    + "  #n|     #CKnight         #bPaladin     #yAnti-Paladin         #n|#N\r\n"
    + "  #n|     #RBerserker      #cCrusader    #BDeath Knight         #n|#N\r\n"
    + "  #n|     #rAssassin       #GRanger      #bShadowblade          #n|#N\r\n"
    + "  #n|     #YRogue          #YMerchant    #CMercenary            #n|#N\r\n"
    + "  #n|     #gDruid          #nPriest      #BDark Cleric          #n|#N\r\n"
    + "  #n|     #GMonk           #bHealer      #gAlchemist            #n|#N\r\n"
    + "  #n|     #mWizard         #gSorcerer    #MWarlock              #n|#N\r\n"
    + "  #n|     #yIllusionist    #cSummoner    #RShapeshifter         #n|#N\r\n"
    + "  #n+-----------------------------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpSkills()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n"
    + "  #n|                       #gSkills#n                        |#N\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n"
    + "  #n|#N The skills you may learn are, for the most part,    #n|#N\r\n"
    + "  #n|#N dicated by your class. It may be possible in some   #n|#N\r\n"
    + "  #n|#N cases to learn skills not meant for your class, but #n|#N\r\n"
    + "  #n|#N that would require either divine intervention, or   #n|#N\r\n"
    + "  #n|#N a severely determined student of multiple arts.     #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N The best way to find out which skills you can learn #n|#N\r\n"
    + "  #n|#N and what their requirements are is to talk to a     #n|#N\r\n"
    + "  #n|#N Skillmaster NPC.  Every major city has one, so find #n|#N\r\n"
    + "  #n|#N them and speak with them about skills.              #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N The only way to find out about the skills of other  #n|#N\r\n"
    + "  #n|#N classes is to speak with a human player of that     #n|#N\r\n"
    + "  #n|#N class, and they can tell you about it.              #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N To list your current skills, type #n'#rskills#n'#N.         #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N To use a skill, just type in the name of the skill. #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N If nothing happens, it's most likely a passive      #n|#N\r\n"
    + "  #n|#N skill, which means it works automatically. Consult  #n|#N\r\n"
    + "  #n|#N a Skillmaster or player if you are unsure of how    #n|#N\r\n"
    + "  #n|#N a particular skill works.                           #n|#N\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpSpells()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n"
    + "  #n|                       #mSpells#n                        |#N\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n"
    + "  #n|#N The spells you may learn are, for the most part,    #n|#N\r\n"
    + "  #n|#N dicated by your class. It may be possible in some   #n|#N\r\n"
    + "  #n|#N cases to learn spells not meant for your class, but #n|#N\r\n"
    + "  #n|#N that would require either divine intervention, or   #n|#N\r\n"
    + "  #n|#N a severely determined student of multiple arts.     #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N The best way to find out which spells you can learn #n|#N\r\n"
    + "  #n|#N and what their requirements are is to talk to a     #n|#N\r\n"
    + "  #n|#N Spellmaster NPC.  Every major city has one, so find #n|#N\r\n"
    + "  #n|#N them and speak with them about spells.              #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N The only way to find out about the spells of other  #n|#N\r\n"
    + "  #n|#N classes is to speak with a human player of that     #n|#N\r\n"
    + "  #n|#N class, and they can tell you about it.              #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N To list your current spells, type #n'#rspells#n'#N.         #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N To use a spell, type #n'#rcast #n<#Rspell name#n>'#N.           #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N There are different types of spells, so not all     #n|#N\r\n"
    + "  #n|#N spells can be cast on all things. It's usually      #n|#N\r\n"
    + "  #n|#N obvious by the name whether the spell requires a    #n|#N\r\n"
    + "  #n|#N target, but if you are not sure, ask a Spellmaster  #n|#N\r\n"
    + "  #n|#N or player about how to use that particular spell.   #n|#N\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getClassList()

  {
    String str = ""

    + "  #n+---------------------+---------------------+#N\r\n"
    + "  #n|#N (#BWa#N) - Warrior      #n|#N (#YTh#N) - Thief        #n|#N\r\n"
    + "  #n|#N (#CKn#N) - Knight       #n|#N (#rAs#N) - Assassin     #n|#N\r\n"
    + "  #n|#N (#RBr#N) - Berzerker    #n|#N (#YRg#N) - Rogue        #n|#N\r\n"
    + "  #n|#N (#bPa#N) - Paladin      #n|#N (#GRa#N) - Ranger       #n|#N\r\n"
    + "  #n|#N (#cCr#N) - Crusader     #n|#N (#YMe#N) - Merchant     #n|#N\r\n"
    + "  #n|#N (#BDk#N) - Death Knight #n|#N (#bSb#N) - Shadowblade  #n|#N\r\n"
    + "  #n|#N (#yAp#N) - Anti Paladin #n|#N (#CMc#N) - Mercenary    #n|#N\r\n"
    + "  #n+---------------------+---------------------+#N\r\n"
    + "  #n|#N (#GCl#N) - Cleric       #n|#N (#MMg#N) - Mage         #n|#N\r\n"
    + "  #n|#N (#gDr#N) - Druid        #n|#N (#mWi#N) - Wizard       #n|#N\r\n"
    + "  #n|#N (#GMo#N) - Monk         #n|#N (#yIl#N) - Illusionist  #n|#N\r\n"
    + "  #n|#N (#nPr#N) - Priest       #n|#N (#gSc#N) - Sorcerer     #n|#N\r\n"
    + "  #n|#N (#bHl#N) - Healer       #n|#N (#cSm#N) - Summoner     #n|#N\r\n"
    + "  #n|#N (#BDc#N) - Dark Cleric  #n|#N (#MWl#N) - Warlock      #n|#N\r\n"
    + "  #n|#N (#gAl#N) - Alchemist    #n|#N (#RSh#N) - Shapeshifter #n|#N\r\n"
    + "  #n+---------------------+---------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpWarrior()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                            #BWarrior#n                          |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Warriors are masters of close combat who specialize in all  #n|\r\n"
    + "  #n|#N forms of weaponry and armor. They are durable and strong.   #n|\r\n"
    + "  #n|#N While they rely mainly on brute force to get them through   #n|\r\n"
    + "  #n|#N rough situations, not all Warriors are without skill. Some  #n|\r\n"
    + "  #n|#N experienced Warriors can learn a variety of attack skills,  #n|\r\n"
    + "  #n|#N and in some cases even beneficial spells to enhance their   #n|\r\n"
    + "  #n|#N protection and perception.                                  #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N At level 10, a Warrior may become a #CKnight#N or #RBerzerker#N.    #n|\r\n"
    + "  #n|#N Type #n'#rhelp classes#n'#N for information on changing classes.    #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Warriors who stay pure to their profession, and never       #n|\r\n"
    + "  #n|#N deviate into the study of other arts, may receive special   #n|\r\n"
    + "  #n|#N abilities and the unique title of Master Warrior. While     #n|\r\n"
    + "  #n|#N there are, of course, huge advantages to the specialized    #n|\r\n"
    + "  #n|#N Warrior classes, and the long journey to level 99 as just   #n|\r\n"
    + "  #n|#N a Warrior is certainly more difficult, there may be some    #n|\r\n"
    + "  #n|#N subtle or yet unknown rewards for accomplishing this.       #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpThief()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                            #YThief#n                            |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Thieves are somewhat strong physically, but are quite weak  #n|\r\n"
    + "  #n|#N in comparison to Warriors. The true power of all            #n|\r\n"
    + "  #n|#N Thief-based classes lies in their skills and specialized    #n|\r\n"
    + "  #n|#N abilities. When focused on one thing, the Thief can excel   #n|\r\n"
    + "  #n|#N like no other. A master of stealth and speed, Thieves rely  #n|\r\n"
    + "  #n|#N on their wits and agility to lead them to victory, no       #n|\r\n"
    + "  #n|#N matter what their purposes are. The skills that they learn, #n|\r\n"
    + "  #n|#N though, rely heavily upon their choice of profession. For a #n|\r\n"
    + "  #n|#N Thief more than any other class, one's profession can make  #n|\r\n"
    + "  #n|#N a world of difference in what you can and cannot do. They   #n|\r\n"
    + "  #n|#N are very specialized, so choose carefully.                  #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N At Level 10, a Thief may become an #rAssassin#N or a #YRogue#N.     #n|\r\n"
    + "  #n|#N Type #n'#rhelp classes#n'#N for information on changing classes.    #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Thieves who stay pure to their profession, and never        #n|\r\n"
    + "  #n|#N deviate into the study of other arts, may receive special   #n|\r\n"
    + "  #n|#N abilities and the unique title of Master Thief. While       #n|\r\n"
    + "  #n|#N there are, of course, huge advantages to the specialized    #n|\r\n"
    + "  #n|#N Thief classes, and the long journey to level 99 as just     #n|\r\n"
    + "  #n|#N a Thief is certainly more difficult, there may be some      #n|\r\n"
    + "  #n|#N subtle or yet unknown rewards for accomplishing this.       #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpCleric()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                            #GCleric#n                           |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Clerics are altruistic magic users, who are concerned with  #n|\r\n"
    + "  #n|#N protecting themselves and those around them. Their skills   #n|\r\n"
    + "  #n|#N are primarily focused upon the healing arts, but also delve #n|\r\n"
    + "  #n|#N into areas of enchantment and mysticism. The presense of a  #n|\r\n"
    + "  #n|#N Cleric is essential to the success of a hunting party, as   #n|\r\n"
    + "  #n|#N their ability to restore the vitality of injured or fallen  #n|\r\n"
    + "  #n|#N warriors is unsurpassed. Both wise and talented, the Cleric #n|\r\n"
    + "  #n|#N is the foundation upon which an effective group is formed.  #n|\r\n"
    + "  #n|#N Clerics are often very spiritual and usually have           #n|\r\n"
    + "  #n|#N affiliation with organized religion. The Cleric has many    #n|\r\n"
    + "  #n|#N duties in society that are unrelated to combat. They are    #n|\r\n"
    + "  #n|#N spiritual leaders in society.                               #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N At level 10, a Cleric may become a #gDruid#N or a #GMonk#N.         #n|\r\n"
    + "  #n|#N Type #n'#rhelp classes#n'#N for information on changing classes.    #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Clerics who stay pure to their profession, and never        #n|\r\n"
    + "  #n|#N deviate into the study of other arts, may receive special   #n|\r\n"
    + "  #n|#N abilities and the unique title of High Cleric. While        #n|\r\n"
    + "  #n|#N there are, of course, huge advantages to the specialized    #n|\r\n"
    + "  #n|#N Cleric classes, and the long journey to level 99 as just    #n|\r\n"
    + "  #n|#N a Cleric is certainly more difficult, there may be some     #n|\r\n"
    + "  #n|#N subtle or yet unknown rewards for accomplishing this.       #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpMage()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                            #MMage#n                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Mages are scholars and powerful attack magic users. They    #n|\r\n"
    + "  #n|#N are fiercly intelligent, although very weak physically. The #n|\r\n"
    + "  #n|#N adept magic user dedicates his life to the study of the     #n|\r\n"
    + "  #n|#N arcane, and believes in mind over matter. A vast array of   #n|\r\n"
    + "  #n|#N knowledge and an arsenal of attack spells are the weapons   #n|\r\n"
    + "  #n|#N of the Mage, and his philosophy is that a good offense is   #n|\r\n"
    + "  #n|#N the best defense. Mages cannot absorb damage well--They     #n|\r\n"
    + "  #n|#N will more likely try and destroy the source before it has   #n|\r\n"
    + "  #n|#N the chance to attack. Some Mages that do not care for       #n|\r\n"
    + "  #n|#N combat might lead a life of study and solitude, but more    #n|\r\n"
    + "  #n|#N often than not they are formidable adversaries when         #n|\r\n"
    + "  #n|#N conflict arises.                                            #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N At level 10, a Mage may become a #mWizard#N or an #yIllusionist#N.  #n|\r\n"
    + "  #n|#N Type #n'#rhelp classes#n'#N for information on changing classes.    #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Mages who stay pure to their profession, and never          #n|\r\n"
    + "  #n|#N deviate into the study of other arts, may receive special   #n|\r\n"
    + "  #n|#N abilities and the unique title of Arch Mage. While          #n|\r\n"
    + "  #n|#N there are, of course, huge advantages to the specialized    #n|\r\n"
    + "  #n|#N Mage classes, and the long journey to level 99 as just      #n|\r\n"
    + "  #n|#N a Mage is certainly more difficult, there may be some       #n|\r\n"
    + "  #n|#N subtle or yet unknown rewards for accomplishing this.       #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpKnight()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                            #CKnight#n                           |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Knights are more chivalrous and less barbaric than common   #n|\r\n"
    + "  #n|#N Warriors. They abide by a code of conduct, and loyally      #n|\r\n"
    + "  #n|#N serve their masters when wartime arises. Knights have more  #n|\r\n"
    + "  #n|#N expertise in the area of weaponry and armor than Warriors,  #n|\r\n"
    + "  #n|#N but are not nearly as vicious or aggressive. Their superior #n|\r\n"
    + "  #n|#N equipment as well as the addition of some beneficial spells #n|\r\n"
    + "  #n|#N do, however, make the Knight class far more versatile. A    #n|\r\n"
    + "  #n|#N Knight who is aligned with the Light may become a #bPaladin#N.  #n|\r\n"
    + "  #n|#N Paladins are Holy Knights who possess magical healing       #n|\r\n"
    + "  #n|#N abilities and extra armored protection at the cost of       #n|\r\n"
    + "  #n|#N damage capabilities. A Dark aligned Knight may become an    #n|\r\n"
    + "  #n|#N #yAnti-Paladin#N, who also possesses some magical abilities,    #n|\r\n"
    + "  #n|#N but receives true gain in the area of raw damage and the    #n|\r\n"
    + "  #n|#N ability to kill quickly and efficiently. In general, all    #n|\r\n"
    + "  #n|#N forms of Knighthood are well-rounded and well-protected     #n|\r\n"
    + "  #n|#N close combat professions.                                   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Type #n'#rhelp alignment#n'#N for information on aligned classes.   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Also #n'#rhelp paladin#n'#N or #n'#rhelp anti-paladin#n'#N.                 #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpBerzerker()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                          #RBerzerker#n                          |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Berzerkers have taken the aggressive philosophy of a        #n|\r\n"
    + "  #n|#N Warrior to a new level. Not very concerned with magic or    #n|\r\n"
    + "  #n|#N skills, Berzerkers strive to increase damage and physical   #n|\r\n"
    + "  #n|#N protection over all other things. They are Warriors who     #n|\r\n"
    + "  #n|#N have become overzealous with the concept of warfare, and    #n|\r\n"
    + "  #n|#N can be completely overtaken by their battle-driven emotions #n|\r\n"
    + "  #n|#N at any time. Those who have been drawn towards the Light    #n|\r\n"
    + "  #n|#N may find themselves a #cCrusader#N, who also has great          #n|\r\n"
    + "  #n|#N close-combat abilities, but specializes mostly in defense,  #n|\r\n"
    + "  #n|#N and does not have quite the ferocity of a Berzerker's       #n|\r\n"
    + "  #n|#N attacks. Drawn to the Dark from a path that is already      #n|\r\n"
    + "  #n|#N dangerous, a #BDeath Knight#N is a fearsome combination of      #n|\r\n"
    + "  #n|#N combat prowess and evil intent. They are the ultimate war   #n|\r\n"
    + "  #n|#N machines; their only desire is to destroy. In any case, all #n|\r\n"
    + "  #n|#N Berzerker types are strictly within the realm of pure       #n|\r\n"
    + "  #n|#N physicality and overpowering strength, and do not have much #n|\r\n"
    + "  #n|#N in the area of skills and spells.                           #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Type #n'#rhelp alignment#n'#N for information on aligned classes.   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Also #n'#rhelp crusader#n'#N or #n'#rhelp death knight#n'#N.                #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpAssassin()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                          #rAssassin#n                           |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Assassins are killers. They are the most adept and skillful #n|\r\n"
    + "  #n|#N of all professions. They use these skills to execute        #n|\r\n"
    + "  #n|#N specialized attacks with knives and projectiles, often      #n|\r\n"
    + "  #n|#N dealing staggering critical damage. While not as versatile  #n|\r\n"
    + "  #n|#N as a common Thief, the Assassin is by far a more effective  #n|\r\n"
    + "  #n|#N combat profession. They are relatively weak defensively,    #n|\r\n"
    + "  #n|#N but more often than not, the opponent of an Assassin may    #n|\r\n"
    + "  #n|#N not even see their attacker until a knife is in their back. #n|\r\n"
    + "  #n|#N This way of life, ironically, stands on the edge of a       #n|\r\n"
    + "  #n|#N knife--Always on the verge of falling into the Light or     #n|\r\n"
    + "  #n|#N Dark. Assassins who become overly fond of killing to the    #n|\r\n"
    + "  #n|#N point where they do it out of enjoyment instead of          #n|\r\n"
    + "  #n|#N necessity are known as #bShadowblade#N. The Shadowblade are a   #n|\r\n"
    + "  #n|#N fearsome variation of the Assassin, even more adept at      #n|\r\n"
    + "  #n|#N stalking through the shadows and killing silently. So much  #n|\r\n"
    + "  #n|#N is the case, that they lose efficiency in most of the other #n|\r\n"
    + "  #n|#N areas of combat. On the other hand, some Assassins come to  #n|\r\n"
    + "  #n|#N the realization that their skills can be used for good, and #n|\r\n"
    + "  #n|#N protection of others. They become a #GRanger#N, a master of     #n|\r\n"
    + "  #n|#N projectile weapons. They excel in tracking and scouting.    #n|\r\n"
    + "  #n|#N Their marksmanship has no equal. All Assassins can kill     #n|\r\n"
    + "  #n|#N quickly, but when and how they choose to do so depends on   #n|\r\n"
    + "  #n|#N the individual.                                             #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Type #n'#rhelp alignment#n'#N for information on aligned classes.   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Also #n'#rhelp ranger#n'#N or #n'#rhelp shadowblade#n'#N.                   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpRogue()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                           #YRogue#n                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Rogues answer to no one. They live by their own rules, and  #n|\r\n"
    + "  #n|#N do whatever they please. A Rogue is not so combat-minded,   #n|\r\n"
    + "  #n|#N but more concerned with their own prosperity, and usually   #n|\r\n"
    + "  #n|#N monetary gain. They do have many useful combat skills,      #n|\r\n"
    + "  #n|#N though, to rely upon when they must fight. In fact, a Rogue #n|\r\n"
    + "  #n|#N is a bit more robust defensively than the common Thief or   #n|\r\n"
    + "  #n|#N even an Assassin. However, their attacks are not nearly as  #n|\r\n"
    + "  #n|#N effective. The true power of the Rogue lies in their        #n|\r\n"
    + "  #n|#N non-combat skills. Rogues have mysterious abilities in the  #n|\r\n"
    + "  #n|#N realm of weapons and item lore. Most shopkeepers are Rogues #n|\r\n"
    + "  #n|#N who abandoned combat completely have become a #YMerchant#N. The #n|\r\n"
    + "  #n|#N Merchant class is essential to the prosperity of others.    #n|\r\n"
    + "  #n|#N Rogues and Merchants will often be asked for assistance in  #n|\r\n"
    + "  #n|#N various areas from other classes who lack the skills to     #n|\r\n"
    + "  #n|#N accomplish their goals. However, some Rogues decide that    #n|\r\n"
    + "  #n|#N the best way to earn their gold is to fight. They become    #n|\r\n"
    + "  #n|#N a #CMercenary#N, and will wage war against anybody that their   #n|\r\n"
    + "  #n|#N employer commands, regardless of morality. Mercenaries are  #n|\r\n"
    + "  #n|#N far more formidable in combat than Rogues, but they lose    #n|\r\n"
    + "  #n|#N many of their non-combat skills. All Rogues, though, have a #n|\r\n"
    + "  #n|#N vast and indescribable array of skills that differ          #n|\r\n"
    + "  #n|#N depending on the inclinations of the Rogue in question.     #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Type #n'#rhelp alignment#n'#N for information on aligned classes.   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Also #n'#rhelp merchant#n'#N or #n'#rhelp mercenary#n'#N.                   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpDruid()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                           #gDruid#n                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Druids are those who have dedicated themselves to achieving #n|\r\n"
    + "  #n|#N a higher level of combat effectiveness. While not           #n|\r\n"
    + "  #n|#N necessarily aligned with the dark or light, Druids are      #n|\r\n"
    + "  #n|#N often of a neutral inclination, and are primarily concerned #n|\r\n"
    + "  #n|#N with their ability to use magic for healing and protection. #n|\r\n"
    + "  #n|#N To this end, they are more effective than the common        #n|\r\n"
    + "  #n|#N Cleric. The deeply religious Druid with firm beliefs may    #n|\r\n"
    + "  #n|#N become a #nPriest#N. The Priest above all other classes are     #n|\r\n"
    + "  #n|#N leaders in the church, and set a shining example for all    #n|\r\n"
    + "  #n|#N those of their faith. They are also the greatest healers in #n|\r\n"
    + "  #n|#N the land, although are not quite as well-rounded in a       #n|\r\n"
    + "  #n|#N combat sense as other Clerics may be. Not all religions are #n|\r\n"
    + "  #n|#N of the light, and not all Cleric magic is laced with good   #n|\r\n"
    + "  #n|#N intent. The Druid who is equally as faithfull as a Priest,  #n|\r\n"
    + "  #n|#N yet feels himself compelled to the darkness, becomes the    #n|\r\n"
    + "  #n|#N feared #BDark Cleric#N. Not everything is known about the Dark  #n|\r\n"
    + "  #n|#N Cleric, but it is said that they hold black masses, learn   #n|\r\n"
    + "  #n|#N dark attack spells, and wish curses upon the rest of the    #n|\r\n"
    + "  #n|#N world. They use their magical powers for the malcontent of  #n|\r\n"
    + "  #n|#N society, or for their own selfish purposes. They do possess #n|\r\n"
    + "  #n|#N healing powers, but are not concerned with the welfare of   #n|\r\n"
    + "  #n|#N others. They may, if anything, use such powers to heal      #n|\r\n"
    + "  #n|#N those who would kill their enemies.                         #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Type #n'#rhelp alignment#n'#N for information on aligned classes.   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Also #n'#rhelp priest#n'#N or #n'#rhelp dark cleric#n'#N.                   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpMonk()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                           #GMonk#n                              |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Monks are drawn towards the realm of physicality, in slight #n|\r\n"
    + "  #n|#N abandon of their magical abilities. The Monk can fight      #n|\r\n"
    + "  #n|#N using their bare hands with good results. They still        #n|\r\n"
    + "  #n|#N possess some of the magical healing powers that their       #n|\r\n"
    + "  #n|#N Cleric background taught them, but they now focus on        #n|\r\n"
    + "  #n|#N non-magic skills as well. In addition, Monks will often be  #n|\r\n"
    + "  #n|#N very wise in the areas of lore. Their knowledge of herbs    #n|\r\n"
    + "  #n|#N and potions is vast. It is this that often leads them to a  #n|\r\n"
    + "  #n|#N new path. A Monk who decides that herbalism and potion      #n|\r\n"
    + "  #n|#N making are more interesting than fighting may become a      #n|\r\n"
    + "  #n|#N #bHealer#N--A master of herbal lore. A Healer dislikes combat   #n|\r\n"
    + "  #n|#N in general, but may gladly lend their assistance to those   #n|\r\n"
    + "  #n|#N who need potions to heal various ailments. Often, a Healer  #n|\r\n"
    + "  #n|#N will be a simple merchant, spending their days preparing    #n|\r\n"
    + "  #n|#N and selling their creations. Like all things, the lore of   #n|\r\n"
    + "  #n|#N concocting potions has a dark side. Those who study long    #n|\r\n"
    + "  #n|#N and hard to conceive and create liquid poisions and bottled #n|\r\n"
    + "  #n|#N curses become an #gAlchemist#N. Famous for trying to create     #n|\r\n"
    + "  #n|#N gold of out lead, the Alchemist is selfish and mischievous; #n|\r\n"
    + "  #n|#N Not to be trusted. The like-minded may find their skills    #n|\r\n"
    + "  #n|#N invaluable, but beware of their treachery, and do not drink #n|\r\n"
    + "  #n|#N anything they offer if it looks suspicious.                 #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Type #n'#rhelp alignment#n'#N for information on aligned classes.   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Also #n'#rhelp healer#n'#N or #n'#rhelp alchemist#n'#N.                     #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpWizard()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                          #mWizard#n                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Wizards take destructive magic to a new level. They are     #n|\r\n"
    + "  #n|#N masters of the elements and wielders of immense arcane      #n|\r\n"
    + "  #n|#N power. Their power is so great that they may consume a      #n|\r\n"
    + "  #n|#N great host of foes with a single spell. A Wizard, like a    #n|\r\n"
    + "  #n|#N Mage, may have varying degrees of versatility; while some   #n|\r\n"
    + "  #n|#N Wizards may only concentrate on destruction, others may be  #n|\r\n"
    + "  #n|#N balanced with a wide variety of magical abilities. There is #n|\r\n"
    + "  #n|#N no telling just what kinds of things a Wizard can do, in    #n|\r\n"
    + "  #n|#N and out of a combat situation. Those that are decidedly     #n|\r\n"
    + "  #n|#N bent on destruction and nothing else may become a #MWarlock#N.  #n|\r\n"
    + "  #n|#N The Warlock is the ultimate destroyer, and wields terrible  #n|\r\n"
    + "  #n|#N explosive magicks. They suffer in various other aspects of  #n|\r\n"
    + "  #n|#N combat, and may often be anathematized from society, but    #n|\r\n"
    + "  #n|#N the quality of their assistance in times of conflict is     #n|\r\n"
    + "  #n|#N never questioned. Not all Wizards are hell-bent on          #n|\r\n"
    + "  #n|#N destruction. Some of them become a #gSorcerer#N: A very         #n|\r\n"
    + "  #n|#N balanced and versatile spell caster. While still possessing #n|\r\n"
    + "  #n|#N great destructive power, the Sorcerer has many other useful #n|\r\n"
    + "  #n|#N spells that can be used in combat as well as in other       #n|\r\n"
    + "  #n|#N situations. Sorcerers are looked up to and respected in     #n|\r\n"
    + "  #n|#N society; Like a Mage, they represent a scholar and teacher, #n|\r\n"
    + "  #n|#N with an immense breadth of knowledge.                       #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Type #n'#rhelp alignment#n'#N for information on aligned classes.   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Also #n'#rhelp sorcerer#n'#N or #n'#rhelp warlock#n'#N.                     #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpIllusionist()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                        #yIllusionist#n                          |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Illusionists are weavers of deception. You may not believe  #n|\r\n"
    + "  #n|#N it, but some things that are not even real can hurt you.    #n|\r\n"
    + "  #n|#N The Illusionist uses forgotten magicks and deceptive        #n|\r\n"
    + "  #n|#N techniques to damage and confuse their foe. Inherently      #n|\r\n"
    + "  #n|#N Mages, they rely mostly on magic to do their work, and      #n|\r\n"
    + "  #n|#N craft their illusions out of mana and energy. Not           #n|\r\n"
    + "  #n|#N necessarily light or dark, you never know what lies in the  #n|\r\n"
    + "  #n|#N mind of an Illusionist. Their phase shifting powers can     #n|\r\n"
    + "  #n|#N lead them to two very different paths. The light aligned    #n|\r\n"
    + "  #n|#N Illusionist realizes his potential for summoning Familliars #n|\r\n"
    + "  #n|#N from another plane or dimension, and calls upon them for    #n|\r\n"
    + "  #n|#N assistance in times of need. He becomes a #cSummoner#N.         #n|\r\n"
    + "  #n|#N However, those that realize the dark arts of phase          #n|\r\n"
    + "  #n|#N manipulation upon one's own body can become a #RShapeshifter#N. #n|\r\n"
    + "  #n|#N These abominations can change their own physical form to    #n|\r\n"
    + "  #n|#N that of a monstrous beast. Of all Mages, the Shapeshifter   #n|\r\n"
    + "  #n|#N is the only one who can act as a melee fighter at times.    #n|\r\n"
    + "  #n|#N The cost of this ability is dire indeed; some say that      #n|\r\n"
    + "  #n|#N those who delve too deeply into this practice lose grip     #n|\r\n"
    + "  #n|#N upon their very own soul.                                   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Type #n'#rhelp alignment#n'#N for information on aligned classes.   #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n|#N Also #n'#rhelp summoner#n'#N or #n'#rhelp shapeshifter#n'#N.                #n|\r\n"
    + "  #n|                                                             |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpInteraction()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n"
    + "  #n|               #YNPC & Mobile Interaction#n              |#N\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N\r\n"
    + "  #n|#N You can talk to Mobiles that are considered NPCs.   #n|#N\r\n"
    + "  #n|#N To do so, just speak aloud (with say) a question    #n|#N\r\n"
    + "  #n|#N or statement, and include the NPC's name.           #n|#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N Example#n:  #R[#Nsay Harlan, tell me about skills.#R]#n       |#N\r\n"
    + "  #n|                                                     |#N\r\n"
    + "  #n|#N There are many different types of NPCs, such as     #n|#N\r\n"
    + "  #n|#N Bards, Skillmasters, Spellmasters, etc.  Each type  #n|#N\r\n"
    + "  #n|#N of NPC can help you learn about different aspects   #n|#N\r\n"
    + "  #n|#N of the game.  Some may even send you on quests and  #n|#N\r\n"
    + "  #n|#N help you attain legend marks.  Try talking to as    #n|#N\r\n"
    + "  #n|#N many NPCs as possible to learn about new things!    #n|#N\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpColor()

  {
    return HelpFile.getColorList();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getColorList()

  {
    String str = ""

    + "  #n+-----------------------------------+#N\r\n  #n|#N Available Colors#n:#N                 #n|#N\r\n"
    + "  #n|#N                                   #n|\r\n  #n|#N n = #nWhite#N      N = #NGrey (Normal)#N  #n|#N\r\n"
    + "  #n|#N r = #rRed#N        R = #RDark Red#N       #n|#N\r\n  #n|#N g = #gGreen#N    "
    + "  G = #GDark Green#N     #n|#N\r\n"
    + "  #n|#N b = #bBlue#N       B = #BDark Blue#N      #n|#N\r\n  #n|#N y = #yYellow#N   "
    + "  Y = #YDark Yellow#N    #n|#N\r\n"
    + "  #n|#N c = #cCyan#N       C = #CDark Cyan#N      #n|#N\r\n  #n|#N m = #mMagenta#N  "
    + "  M = #MPurple#N         #n|#N\r\n"
    + "  #n|#N                                   #n|#N\r\n  #n|#N Usage#n:#N    # + Color Letter + Text #n|#N\r\n"
    + "  #n|#N Example#n:#N  #xHello!                #n|#N\r\n  #n|#N                                   #n|#N\r\n"
    + "  #n|#N To Enable or Disable Color Mode#n:#N  #n|#N\r\n"
    + "  #n|#N Type#n:#N #R[#Ntoggle color#R]#N              #n|#N\r\n"
    + "  #n+-----------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getImmCommands()

  {
    String str = ""

    + "  #n+---------------------------------------------------------------+\r\n"
    + "  |                      #cImmortal Commands                        #n|\r\n"
    + "  | ------------------------------------------------------------- |\r\n"
    + "  | #NSet               #R[#Nset <username> <attribute> <value>#R]        #n|\r\n"
    + "  | #NReport            #R[#Nrep <username>#R]                            #n|\r\n"
    + "  | #NAt                #R[#Nat <target> <action>#R]                      #n|\r\n"
    + "  | #NGoto              #R[#Ngoto <target>#R]                             #n|\r\n"
    + "  | #NForce             #R[#Nforce <target> <action>#R]                   #n|\r\n"
    + "  | #NTransport         #R[#Ntrans <target>#R]                            #n|\r\n"
    + "  | #NPossess           #R[#Npossess/unpossess <mobname>#R]               #n|\r\n"
    + "  | #NLoad              #R[#Nload <mob or item> <ID num>#R]               #n|\r\n"
    + "  | #NPurge             #R[#Npurge <target>#R]                            #n|\r\n"
    + "  | #NSlay              #R[#Nslay <target>#R]                             #n|\r\n"
    + "  | #NRestore           #R[#Nrestore <target>#R]                          #n|\r\n"
    + "  | #NStat              #R[#Nstat <target>#R]                             #n|\r\n"
    + "  | #NSnoop             #R[#Nsnoop <target>#R]                            #n|\r\n"
    + "  | #NIdentify          #R[#Nidentify <target>#R]                         #n|\r\n"
    + "  | #NUsers             #R[#Nusers#R]                                     #n|\r\n"
    + "  | #NSystem Time       #R[#Nsystime#R]                                   #n|\r\n"
    + "  | #NRoom List         #R[#Nrlist <options>#R]  [#Nredit = room editor#R]    #n|\r\n"
    + "  | #NZone List         #R[#Nzlist <options>#R]  [#Nzedit = zone editor#R]    #n|\r\n"
    + "  | #NItem List         #R[#Nilist <options>#R]  [#Niedit = item editor#R]    #n|\r\n"
    + "  | #NMobile List       #R[#Nmlist <options>#R]  [#Nmedit = mlist editor#R]   #n|\r\n"
    + "  | #NAction List       #R[#Nalist <options>#R]  [#Naedit = action editor#R]  #n|\r\n"
    + "  | #NZone Repopulate   #R[#Nzclear/zrepop <zone name or ID>#R]           #n|\r\n"
    + "  | #NBestow            #R[#Nbestow <skill> upon <target> (level)#R]      #n|\r\n"
    + "  | #NDeprive           #R[#Nstrip/deprive <target> of <skill>#R]         #n|\r\n"
    + "  | #NMark Legend       #R[#Nmark/unmark <target> <legend mark>#R]        #n|\r\n"
    + "  | #NFreeze            #R[#Nfreeze/unfreeze <username>#R]                #n|\r\n"
    + "  | #NDisconnect        #R[#Ndc <username>#R]                             #n|\r\n"
    + "  | #NDelete            #R[#Ndelete <username>#R]                         #n|\r\n"
    + "  | #NDisintegrate      #R[#Ndisintegrate <username>#R]                   #n|\r\n"
    + "  +---------------------------------------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getImmLists()

  {
    String str = ""

    + "  #n+------------------------------------------------------------------+#N\r\n"
    + "  #n|                         #CImmortal Lists                           #n|#N\r\n"
    + "  #n| ---------------------------------------------------------------- |#N\r\n"
    + "  #n| #NGlobal#R:   #Nilist all        (Show all items)                      #n|#N\r\n"
    + "  #n| #NBy Name#R:  #Nilist sword      (Show all items named 'sword')        #n|#N\r\n"
    + "  #n| #NBy Zone#R:  #Nilist 10         (Show all items in Zone 10)           #n|#N\r\n"
    + "  #n| #NBy ID#R:    #Nilist 1000-2000  (Show all items in the given range)   #n|#N\r\n"
    + "  #n|                                                                  |#N\r\n"
    + "  #n| #NGlobal#R:   #Nmlist all        (Show all mobiles)                    #n|#N\r\n"
    + "  #n| #NBy Name#R:  #Nmlist troll      (Show all mobiles named 'troll')      #n|#N\r\n"
    + "  #n| #NBy Zone#R:  #Nmlist 10         (Show all mobiles in Zone 10)         #n|#N\r\n"
    + "  #n| #NBy ID#R:    #Nmlist 1000-2000  (Show all mobiles in the given range) #n|#N\r\n"
    + "  #n|                                                                  |#N\r\n"
    + "  #n| #NGlobal#R:   #Nrlist all        (Show all rooms)                      #n|#N\r\n"
    + "  #n| #NBy Name#R:  #Nrlist temple     (Show all rooms named 'temple')       #n|#N\r\n"
    + "  #n| #NBy Zone#R:  #Nrlist 10         (Show all rooms in Zone 10)           #n|#N\r\n"
    + "  #n| #NBy ID#R:    #Nrlist 1000-2000  (Show all rooms in the given range)   #n|#N\r\n"
    + "  #n|                                                                  |#N\r\n"
    + "  #n| #NGlobal#R:   #Nzlist all        (Show all zones)                      #n|#N\r\n"
    + "  #n| #NBy Name#R:  #Nzlist damascus   (Show all zones named 'damascus')     #n|#N\r\n"
    + "  #n| #NBy ID#R:    #Nzlist 20-50      (Show all zones in the given range)   #n|#N\r\n"
    + "  #n|                                                                  |#N\r\n"
    + "  #n| #NGlobal#R:   #Nalist all        (Show all actions)                    #n|#N\r\n"
    + "  #n| #NBy Name#R:  #Nalist patrol     (Show all actions named 'patrol')     #n|#N\r\n"
    + "  #n| #NBy ID#R:    #Nalist 20-50      (Show all actions in the given range) #n|#N\r\n"
    + "  #n+------------------------------------------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getClanCommands()

  {
    String str = ""

    + "  #n+------------------------------+#N\r\n"
    + "  #n|   #mAvailable Clan Commands#n:   |#N\r\n"
    + "  #n+------------------------------+#N\r\n"
    + "  #n| #R[#Nclan list#R]#N                  #n|#N\r\n"
    + "  #n| #R[#Nclan info clan_name#R]#N        #n|#N\r\n"
    + "  #n| #R[#Nclan ranks clan_name#R]#N       #n|#N\r\n"
    + "  #n| #R[#Nclan roster clan_name#R]#N      #n|#N\r\n"
    + "  #n| #R[#Nclan apply clan_name#R]#N       #n|#N\r\n"
    + "  #n| #R[#Nclan enlist player_name#R]#N    #n|#N\r\n"
    + "  #n| #R[#Nclan expel player_name#R]#N     #n|#N\r\n"
    + "  #n| #R[#Nclan raise player_name#R]#N     #n|#N\r\n"
    + "  #n| #R[#Nclan demote player_name#R]#N    #n|#N\r\n"
    + "  #n| #R[#Nclan resign YES#R]#N            #n|#N\r\n"
    + "  #n| #R[#Nctell message#R]#N              #n|#N\r\n"
    + "  #n+------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String getReligionCommands()

  {
    String str = ""

    + "  #n+--------------------------------------+#N\r\n"
    + "  #n|     #yAvailable Religion Commands#n:     |#N\r\n"
    + "  #n+--------------------------------------+#N\r\n"
    + "  #n| #R[#Nreligion list#R]#N                      #n|#N\r\n"
    + "  #n| #R[#Nreligion info religion_name#R]#N        #n|#N\r\n"
    + "  #n| #R[#Nreligion degrees religion_name#R]#N     #n|#N\r\n"
    + "  #n| #R[#Nreligion members religion_name#R]#N     #n|#N\r\n"
    + "  #n| #R[#Nreligion apply religion_name#R]#N       #n|#N\r\n"
    + "  #n| #R[#Nreligion admit player_name#R]#N         #n|#N\r\n"
    + "  #n| #R[#Nreligion excommunicate player_name#R]#N #n|#N\r\n"
    + "  #n| #R[#Nreligion raise player_name#R]#N         #n|#N\r\n"
    + "  #n| #R[#Nreligion lower player_name#R]#N         #n|#N\r\n"
    + "  #n| #R[#Nreligion forsake YES#R]#N               #n|#N\r\n"
    + "  #n+--------------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpRegister()

  {
    String str = ""

    + "  #n+------------------------------------------------------------------------+#N\r\n"
    + "  #n|                        #rRegistration Information#n                        |#N\r\n"
    + "  #n+------------------------------------------------------------------------+#N\r\n"
    + "  #n|#N To actively participate in most of the game aspects of Tempest MUD,    #n|#N\r\n"
    + "  #n|#N you must have a registered account.  Once your account is registered,  #n|#N\r\n"
    + "  #n|#N all of your characters will have full privileges in the game. Accounts #n|#N\r\n"
    + "  #n|#N that are not registered will be severely limited as to the amount of   #n|#N\r\n"
    + "  #n|#N things your characters can do.  Also, one human being is limited to    #n|#N\r\n"
    + "  #n|#N one account, to ensure that there is no one person using multiple      #n|#N\r\n"
    + "  #n|#N characters simultaneously.  Those found to have multiple accounts      #n|#N\r\n"
    + "  #n|#N without explicit written permission are subject to account deletion.   #n|#N\r\n"
    + "  #n|#N If you need an extra account, please submit a formal written request.  #n|#N\r\n"
    + "  #n|                                                                        |#N\r\n"
    + "  #n|#N To achieve a registered account, we require a very brief biography of  #n|#N\r\n"
    + "  #n|#N your primary character.  It does not need to be excruciatingly         #n|#N\r\n"
    + "  #n|#N detailed or long.  The purpose of this is to ensure that our players   #n|#N\r\n"
    + "  #n|#N are literate, creative, and serious about roleplaying.  You need not   #n|#N\r\n"
    + "  #n|#N be completely devoted to roleplaying, as Tempest has quite enough to   #n|#N\r\n"
    + "  #n|#N satisfy those who enjoy pure hack and slash.  However, you must at     #n|#N\r\n"
    + "  #n|#N least be capable of blending in with our society, and have some        #n|#N\r\n"
    + "  #n|#N semblance of interest in your character's theme.  A valid e-mail       #n|#N\r\n"
    + "  #n|#N address should be provided in your account information.  Please e-mail #n|#N\r\n"
    + "  #n|#N us your biography from the address indicated, to avoid delays.         #n|#N\r\n"
    + "  #n|                                                                        |#N\r\n"
    + "  #n|#N Please e-mail all applications to#n:  #gtempest@omegaflare.com             #n|#N\r\n"
    + "  #n+------------------------------------------------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpCredits()

  {
    String str = ""

    + "  #n+------------------------------------------------------------------------+#N\r\n"
    + "  #n|                         #cTempest MUD Credits#n                            |#N\r\n"
    + "  #n+--------------------+---------------------------------------------------+#N\r\n"
    + "  #n|#N Donnie Santos      #n|#N Implementor / Game Director                       #n|#N\r\n"
    + "  #n|#N Jonathan Mis       #n|#N Implementor / Assistant Game Director             #n|#N\r\n"
    + "  #n|#N Michael Murray     #n|#N Builder  / Assistant Designer                     #n|#N\r\n"
    + "  #n+--------------------+---------------------------------------------------+#N\r\n"
    + "  #n|#N Tempest MUD is a completely original Java codebase.  It was originally #n|#N\r\n"
    + "  #n|#N written in C++ in February of 2003, and then moved over to Java to     #n|#N\r\n"
    + "  #n|#N take advantage of superior data persistence support.  All code was     #n|#N\r\n"
    + "  #n|#N written by Donnie Santos and Jon Mis.  The gameplay was designed by    #n|#N\r\n"
    + "  #n|#N Donnie, largely inspired by 'Dark Ages: Online Roleplaying' by Nexon,  #n|#N\r\n"
    + "  #n|#N 'EliteMUD' a heavily modified CircleMUD, and 'Diablo' by Blizzard.     #n|#N\r\n"
    + "  #n|#N With these inspiring games as models, Tempest MUD was developed to be  #n|#N\r\n"
    + "  #n|#N the best online roleplaying experience available, accepting only those #n|#N\r\n"
    + "  #n|#N that are serious about and helpful to our ambitions.  We want this to  #n|#N\r\n"
    + "  #n|#N be a place that is devoid of the mediocrity that rampages through our  #n|#N\r\n"
    + "  #n|#N online world.  The quality of our players will be highly moderated, on #n|#N\r\n"
    + "  #n|#N the assumption that there are great roleplayers out there, just        #n|#N\r\n"
    + "  #n|#N waiting for a place that isn't full of immature kids and morons.       #n|#N\r\n"
    + "  #n|                                                                        |#N\r\n"
    + "  #n|#N We are always looking for builders, and to a lesser extent, coders.    #n|#N\r\n"
    + "  #n|#N To apply to be a builder and/or Immortal, you may send a formal letter #n|#N\r\n"
    + "  #n|#N explaining your qualifications.                                        #n|#N\r\n"
    + "  #n|                                                                        |#N\r\n"
    + "  #n|#N All requests can be e-mailed to#n:  #gtempest@omegaflare.com               #n|#N\r\n"
    + "  #n+------------------------------------------------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpPolicy()

  {
    String str = ""

    + "  #n+--------------------------------------------------------------------------+\r\n"
    + "  #n|                            #rTempest MUD Policy#n                            |\r\n"
    + "  #n+--------------------------------------------------------------------------+\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n|#N              " + dq + "He who attempts to deceive the judicious is                #n|\r\n"
    + "  #n|#N               already baring his own back for the scourge." + dq + "              #n|\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n|#N                                               -C.S. Lewis.               #n|\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n+--------------------------------------------------------------------------+\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n|#N #n1.) #rLanguage and Heresy#n:  #NWe consider language to be of the utmost       #n|\r\n"
    + "  #n|#N     importance.  Words are the fabric which make up this universe, and   #n|\r\n"
    + "  #n|#N     since this is our universe, we must insist that every single word    #n|\r\n"
    + "  #n|#N     meets our personal approval.  We reserve the right to enforce any    #n|\r\n"
    + "  #n|#N     punishment we deem appropriate for merely the use of words which     #n|\r\n"
    + "  #n|#N     do not suit our palate.  This does not necessarily imply swearing,   #n|\r\n"
    + "  #n|#N     but moreso the excessive use of language that either:                #n|\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n|#N     #nA.)#N Refers to non-game related things. (Type #n<#rhelp heresy#n>#N for info) #n|\r\n"
    + "  #n|#N     #nB.)#N Contains unnecessary, non-character related bad grammar.         #n|\r\n"
    + "  #n|#N     #nC.)#N Constitutes unsolicited harassment of players or immortals.      #n|\r\n"
    + "  #n|#N     #nD.)#N Makes you sound like a moron and annoys people.                  #n|\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n|#N #n2.) #rMultiple Accounts and Cheating#n:  #NOne human being is allowed one      #n|\r\n"
    + "  #n|#N     account, and no more without our written permission, period.  The    #n|\r\n"
    + "  #n|#N     characters in the account can be used by that person, and nobody     #n|\r\n"
    + "  #n|#N     else.  Character sharing, password sharing, or any kind of account   #n|\r\n"
    + "  #n|#N     information sharing is considered cheating.  Also, the intentional   #n|\r\n"
    + "  #n|#N     exploitation of program bugs, writing scripts to play for you while  #n|\r\n"
    + "  #n|#N     you are not at the keyboard, or any other type of activity that      #n|\r\n"
    + "  #n|#N     gives you an unfair advantage in the game.  We will not hesitate to  #n|\r\n"
    + "  #n|#N     block and delete accounts with impunity, so please try to play fair. #n|\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n+--------------------------------------------------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpRoleplaying()

  {
    String str = ""

    + "  #n+--------------------------------------------------------------------------+\r\n"
    + "  #n|                          #rRoleplaying vs. Heresy#n                          |\r\n"
    + "  #n+--------------------------------------------------------------------------+\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n|#N #rExamples of Good Roleplaying#n:#N  Develop a personality for your character, #n|\r\n"
    + "  #n|#N and speak in a manner that is proper for them.  If you choose to play    #n|\r\n"
    + "  #n|#N a character that is exactly like your real self, that is fine too, but   #n|\r\n"
    + "  #n|#N refrain from heresy at all times.  This is a roleplaying MUD, so we      #n|\r\n"
    + "  #n|#N strongly encourage creativity and diversity in styles of characters.     #n|\r\n"
    + "  #n|#N Go into as much detail as you can, including your history, family,       #n|\r\n"
    + "  #n|#N moral and religious disposition, etc.  The more detail you put into your #n|\r\n"
    + "  #n|#N character, the richer the roleplaying experience will be for everyone.   #n|\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n|#N #rExamples of Heresy#n:                                                      #n|\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n|#N   #n1.) Stupid Names:        #N(e.g. 'xKillahx', 'iceDawg', 'ipwnu', etc.)   #n|\r\n"
    + "  #n|#N   #n2.) Internet Shorthand:  #N(e.g. 'lol', 'rofl', 'afk', 'how r u', etc.)  #n|\r\n"
    + "  #n|#N   #n3.) Ugly Abbreviations:  #N(e.g. 'xp' instead of 'experience', etc.)     #n|\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n|#N   #n4.) OOC Language:  #NOut Of Character (OOC) language is illegal.  You    #n|\r\n"
    + "  #n|#N       must speak as if you were your character, with no knowledge of the #n|\r\n"
    + "  #n|#N       real world.  You may not talk about computers, or when you have to #n|\r\n"
    + "  #n|#N       go to school, or intimate in any way that you are playing a game.  #n|\r\n"
    + "  #n|#N       You may not refer to yourself as a " + dq + "character" + dq + " or say that you     #n|\r\n"
    + "  #n|#N       have other characters.  If you absolutely must say something out   #n|\r\n"
    + "  #n|#N       of character, then you MUST use the OOC channel. <type #n'#rOOC text#n'#N> #n|\r\n"
    + "  #n|                                                                          |\r\n"
    + "  #n+--------------------------------------------------------------------------+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpGroup()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                #gGrouping#n                                |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                                        |\r\n"
    + "  #n|#N It is very difficult to hunt alone;  It's much safer to go in a group. #n|\r\n"
    + "  #n|#N You would be wise to bring along a diverse hunting party, so that you  #n|\r\n"
    + "  #n|#N have Warriors to take the brunt of the damage, Thieves to provide      #n|\r\n"
    + "  #n|#N invaluable skills, Clerics to keep your party healed, and Mages to     #n|\r\n"
    + "  #n|#N provide long-range and area-effect spell damage.                       #n|\r\n"
    + "  #n|#N                                                                        #n|\r\n"
    + "  #n|#N Experience is divided up among your group members, but higher level    #n|\r\n"
    + "  #n|#N players will take up more of the experience, so try to hunt with       #n|\r\n"
    + "  #n|#N people near your level if you want a good amount of experience.        #n|\r\n"
    + "  #n|                                                                        |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|#N To view your current group, type    #n:#N  group                           #n|\r\n"
    + "  #n|#N To add somebody to you group, type  #n:#N  group player_name               #n|\r\n"
    + "  #n|#N To ungroup somebody, type           #n:#N  ungroup player_name             #n|\r\n"
    + "  #n|#N To group all followers, type        #n:#N  group all                       #n|\r\n"
    + "  #n|#N To ungroup all group members, type  #n:#N  ungroup all                     #n|\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static String helpFollow()

  {
    String str = ""

    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                        #yFollowing#n                        |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|                                                         |\r\n"
    + "  #n|#N If you are following somebody, you will attempt to go   #n|\r\n"
    + "  #n|#N wherever they go.  If you sit down, or are blocked from #n|\r\n"
    + "  #n|#N moving somehow, they will go on without you.  Also, you #n|\r\n"
    + "  #n|#N need to follow somebody first if you wish to join their #n|\r\n"
    + "  #n|#N group.  Type #R<#nhelp group#R>#N for more information.         #n|\r\n"
    + "  #n|#N                                                         #n|\r\n"
    + "  #n|#N You may also set your character so that you may not be  #n|\r\n"
    + "  #n|#N followed. To toggle this setting, type: #R<#ntoggle follow#R> #n|\r\n"
    + "  #n|                                                         |\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+\r\n"
    + "  #n|#N To follow somebody, type  #n:#N  follow player_name         #n|\r\n"
    + "  #n|#N To ditch somebody, type   #n:#N  ditch player_name          #n|\r\n"
    + "  #n+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+#N";

    return str;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}