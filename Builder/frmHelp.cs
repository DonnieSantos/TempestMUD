using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Tempest_Builder

{
	public class frmHelp : System.Windows.Forms.Form
	
	{
	  private string output;
    private System.Windows.Forms.Button cmdOK;
    private System.Windows.Forms.TextBox txtHelp;

		private System.ComponentModel.Container components = null;

		public frmHelp()
		
		{
		  output = "";
			InitializeComponent();
		}
		
		public void set_output(string str)
		
		{		  
      if (str.Equals("Zone Form"))
        output = @"
                              Zone Information
                             __________________

 ID       -  Unique Zone ID number.
 Name     -  Name of your zone.
 Repop    -  Repopulation rate. (Percentage: 0-100)
 Scheme   -  Standard and automatic color of room titles.
 Terrain  -  Standard room terrain type.

 Link Zone        -  Automatically attach all room exits.
 Update Terrains  -  Automatically set all room terrains.
 Debug Exits      -  Generate error report regarding room exits.
 
 (*)---Please note that you need to know your Zone ID number
       before you start building. Unless you want to risk having
       to go back and change all the ID numbers, you should make
       sure to get a confirmed unique Zone ID number from the
       implementors when you are ready to build a zone. Zone ID
       numbers 0-35 are already taken by the capitals, the
       Tempest, and the first few clan areas. So the first zones
       made by the builders will be in the range 35-100.
 
 ";
      else if (str == "Room Form")
        output = @"
                              Room Information
                             __________________

   ID  -  Every room must have a unique ID number. This id will be
          intitialized and assigned automatically, but you may set
          it manually if you wish. You will be given a warning if
          your room number lies outside the boundaries of the zone.

 Name  -  Your room's name will automatically be colored to the same
          color as the zone's color scheme, but you may change it
          manually if you wish.

 Desc  -  The room description. This will be automatically formatted
          to a maximum of 15 lines, 65 characters per line, and no
          double-spaces or whitespace. However if you need to, you
          may select 'Exact' to type in your description manually
          which will NOT be modified in any way. This can be useful
          for drawing pictures (such as prison bars, if the room is
          a jail cell) or formatting special text.

 -------------------------------------------------------------------------------

 Room Actions  -  Your room may periodically echo a line of text, and
                  this is called a room event or action. To specify
                  an event, you must simply provide the line of text
                  to print out (with optional color) and how often
                  you want it to occur, which is indicated with the
                  frequency. Frequencies must be between 0-60000. See
                  the 'Frequency Help' menu option for information about
                  translating frequencies to real life time intervals.

 Look List     -  These are extensions of the room description. If you
                  add a look description, this description will echo
                  to the screen when somebody types 'look' followed by
                  one of the keywords. For example a room might have
                  flowers in the description, and you can see some of
                  them by adding a look description with the keywords
                  'flower' and 'flowers'. Then this look description
                  will echo if the user types 'look flower' or if they
                  type 'look flowers'.

 Item List     -  The item ID numbers you specify will auto-load in
                  this room upon restart.

 Mob List      -  The mobile ID numbers you sppecify will auto-load in
                  this room upon restart. If they die, they will
                  re-load upon zone repopulation.

 -------------------------------------------------------------------------------

 Room Exits    -  A room may have an exit in the 4 cardinal directions,
                  or up and down. You specify which room the exit leads
                  to with the destination room's ID number.

 Exit Linking  -  In most cases you will want the room that you make
                  an exit to to come back to this room in the opposite
                  direction. If you walk north, you want to be able to
                  walk south and get back to where you were. You can
                  use the auto-link buttons and 'Outgoing link all'.
                  In other cases, there might be a room out there that
                  already has an exit defined to the ID number of this
                  room you are making. For this, 'Incoming link all'
                  will automatically create exits that go to those
                  rooms.  'LINK ALL' will perform both incoming and
                  outgoing automatic exit links.

 -------------------------------------------------------------------------------

 Terrain Type  -  The terrain of a room will determine how many points
                  of movement it requires to navigate through, and
                  how fast you can move around. It can also indicate
                  an ability or non-ability to move at all based on
                  a characters movement skills.

 Room Flags    -  A Room Flag is a togglable option setting a certain
                  aspect of the room to true or false. It is false by
                  default, so flagging a room will set these true:

 ANIMATE      -  This room will periodically echo events/actions.
 INDOORS      -  This room is indoors.
 SILENT       -  This room is sound proof against outside noise.
 LAWFUL       -  No violence whatsoever is permitted in this room.
 PKOK         -  You may attack other players in this room.
 DONATION     -  Dropped items will save on the ground in this room.
 PRIVATE      -  Only two people at a time may stay in this room.
 DARK         -  You need a light source to see in this room.
 SHROUD       -  This room is shrouded from distant viewing.
 CHURCH       -  This room is on holy ground.
 TUNNEL       -  This room is a tunnel that you must crawl through.
 UNDERWATER   -  This room is under water.
 DAMAGE       -  Periodic damage will be inflicted upon you here.
 DEATHTRAP    -  If you walk into this room, you will die.
 GODROOM      -  Only immortals (Level 100+) may enter this room.
 REGEN_HP     -  Health regeneration is bolstered here.
 REGEN_MN     -  Mana regeneration is bolstered here.
 NO_TRACK     -  Entities in this room cannot be tracked.
 NO_SCOUT     -  You cannot scout while in this room.
 NO_DISPEL    -  Anti-spell measures will not work in this room.
 NO_TELEPORT  -  You may not teleport here, or summon entities here.
 NO_MOB       -  No mobiles may walk into this room.
 NO_QUIT      -  You may not quit the game in this room.
 NO_DROP      -  You may not drop items in this room.
 NO_MAGIC     -  Magic of any kind will not work in this room.
 NO_FLEE      -  You may not run away from battle in this room.
 NO_SPELL     -  You may not cast spells in this room.
 NO_SUMMON    -  Entities in this room may not be summoned.

                 (Note that NO_TELEPORT is the opposite of NO_SUMMON)

";
      else if (str == "Mobile Form")
        output = @"
                               Mobile Editor
                              _______________

 ID        -  Unique ID number for your mobile. This is set for you
              automatically, however, you may set it yourself. You
              will be given a warning if the ID number falls outside
              of the boundaries of your zone.

 Name      -  The mobile's name. You should NOT add color to this.

 Keywords  -  These are the keywords that are used in identifying the
              mobile for players. For example, if the keywords for a
              mobile are 'knight' and 'guard', a player may type
              'kill knight' or 'kill guard' to attack this mobile.

 RDesc     -  Description shown when a player looks in the room.
              This is equivalent to a player's title. However for
              mobiles, you should add color for this unless it's
              a special case and absolutely necessary.

 LDesc     -  Description shown when a player looks at the mobile.

 -------------------------------------------------------------------------------

 Consult the 'Balancing Mobile Stats' from the Help Menu for figuring
 out how to come up with suitable stats for the following section.

 Level      -  Numerical level.
 HitPoints  -  Number of hit points.
 Mana       -  Number of mana points.
 Gender     -  Male or Female gender.
 Exp        -  Experience points earned from killing this mobile.
 Gold       -  Amount of gold dropped when this mobile is killed.
 Align      -  Alignment of the mobile. Alignment ranges from 0 to 100.
               The closer to 0, the more evil aligned the mobile is.
               The closer to 100, the more good aligned the mobile is.
 Speed      -  This is the frequency at which the mobile moves if it
               is flagged as MOBILE. It's on a range from 0 to 60,000.
               See the 'Frequency Help' menu option for information about
               how to translate frequencies to real life time intervals.

 INNATE STATS: (independant of equipment)

 STR        -  Strength.
 INT        -  Intelligence.
 WIS        -  Wisdom.
 DEX        -  Dexterity.
 CON        -  Constitution.

 EQUIPMENT STATS: (additional stats from equipment)

 AC         -  Armor class is the amount of physical damage absorb.
 MR         -  Magic resistance is a percentage of magic deflection.
 HIT        -  How often the mobile hits a combat target.
 DAM        -  How much damage the mobile inflicts per hit.

 -------------------------------------------------------------------------------

 Mobile Flags  -  A Mob Flag is a togglable option setting a certain
                  aspect of the mob to true or false. It is false by
                  default, so flagging a mob will set these true:

 NPC           -  Non-Player Character. These are typically mobiles
                  that are not meant to be killed and exist for
                  roleplaying purposes. Items and gold on these
                  mobiles will not be dropped when they are killed.
 MERCHANT      -  Merchant mobiles sell items. When this flagged is
                  selected, the 'Sell Items' section on the form
                  becomes active. Clicking 'Add' will display an item
                  selector dialog box in which you can add items which
                  this mobile will sell. You may also specify the name
                  of the store in the 'Shop Name' field.
 BANKER        -  A player can deposit and withdraw items and gold
                  from mobiles that are flagged as bankers.
 BARD          -  Bard mobiles allow players to modify their plan
                  and description.
 MOBILE        -  This mobile will move around within its zone.
 GUARD         -  The mobile will patrol the area for lawlessness.
 SKILLMASTER   -  Players can learn about skills from a skill master.
 SPELLMASTER   -  Players can learn about spells from a spell master.
 AGGRO         -  Normally an AGGRO flagged mobile will attack anybody,
                  however, if you set the alignment to the extreme
                  value of 0 (absolute evil) or 100 (absolute good),
                  the mob will only attack opposite aligned players.
 BLOCKER       -  This mobile will block you from moving in a certain
                  direction, given a certain critera for blocking. If
                  you set this flag, the 'Blocker Type' will become
                  active, which specifies what kind of blocker this is.
                  It might be a gate, a clan blocker, a mortal blocker,
                  or really anything you can think of. The 'Blocker Dir'
                  will also become active, so you may specify which
                  direction this blocker will block. (NESWUD)
 CLERGY        -  This mobile is an attendant to the religious clergy.
 TEACHER       -  This mobile is able to teach and assist classes.
 HERBALIST     -  This mobile knows about herbal lore.
 SANCD         -  This mobile is affected by sanctuary.
 FLYING        -  This mobile is flying.
 UNDEAD        -  This mobile is undead.

 -------------------------------------------------------------------------------

 Load Items  -  The mobile will load these items at the specified
                frequency, and have them in their inventory.

 Wear Items  -  The mobile will load these items at the specified
                frequency, and automatically equip them.

 Actions     -  The mobile will echo this line of text at the
                specified frequency. Color is optional but should
                not be used much in this context.

 Skills      -  The mobile will know this skill, and will use it
                (usually only in combat, depending on the skill)
                at the specified frequency.

 Spells      -  The mobile will know this spell, and will use it
                (usually only in combat, depending on the spell)
                at the specified frequency.
 
 For actions, skills and spells, use a scale of 0-60,000.
 For Load Items and Wear Items, use a scale of 0-10,000.
 See the 'Frequency Help' menu option for information about
 how to translate frequencies to real life time intervals.
 
 ";

      else if (str == "Item Form")
        output = @"
                                 Item Editor
                                _____________

 ID     -  Unique Item ID Number. This will be automatically
           initialized and assigned, but you may change it manually
           if you wish. You will be given a warning if the ID number
           falls outside of the boundaries of your zone.

 Name   -  The normal name of the item, commonly in capital letters.
           Example: 'Red Rose' or 'A Red Rose'. Either is fine.
           Colors may be added if you wish.

 LName  -  The lowercase name of the item as would be grammatically
           appropriate for a sentence.  Example: 'a red rose'.
           In this case 'red rose' would not work, because a
           sentence would be, 'You pick up red rose.'
           Colors may be added if you wish.

 GDesc  -  What you see when you look in the room. This is equivalent
           to a player or mobile title.  Example: 'There is a red
           rose lying on the ground here.'  You should not use colors
           unless it's very necessary.

 LDesc  -  This is what you see when you look at the item directly.
           Like a room description, it is auto-formatted with a max
           of 15 lines and 65 characters per line. But you may again
           use 'Exact' mode, in order to skip over these formatting
           options. This can be useful for drawing pictures, for
           example if your item is a map. Keep in mind that the
           standard width for most terminals is 80 characters, and
           you can see the 80 character limit in exact mode as it
           is defined by the top horizontal bar.

 -------------------------------------------------------------------------------

 Worth         -  How much the item costs for you to buy. Selling this
                  item will yield ten percent of its pure worth.

 Bonuses       -  If the dominant item type is Equipment or Equipable,
                  these are the stats you will gain from wearing it.

 Requirements  -  These are the innate stat and level requirements
                  necessary to wear the item.

 (*) -- These three fields are very important. Please refer to the
        help sections on 'Balancing Item Stats' and 'Balancing
        Item Prices' for information on calculating balanced values.

 -------------------------------------------------------------------------------

 Dominant Item Type - YOU SHOULD SET THIS VALUE FIRST!!!
                      The dominant item type specifies what kind of
                      item this is, and will open up all available
                      options for that item type on the form.

 Normal      -  This is a basic item with no special attributes.

 Container   -  This item can contain other items. This will open up
                the option of selecting 'Max Items' which specifies
                how many items this container may hold. The universal
                maximum is 100, which you may not exceed. Also, the
                'Contained Items' field will open, where you specify
                the ID numbers of the items inside.

 Board       -  This item is a board that you can write messages on.
                This will open up the 'Board Name' option as well.
                Example: 'The Bloody Rose Clan Board'.

 Fountain    -  A fountain that you may drink from. Fountains never
                run out of liquid. Selecting this will open up the
                'Liquid' field, and you can specify the contents,
                such as water, poison, orange juice, blood, etc.

 Blocker     -  The item may block you from moving in a certain
                direction when it is dropped. This will open up the
                'Blocker Type' field, which specifies what kind of
                blocker it is, and 'Blocker Dir' which specifies
                which direction the mobile blocks. (NESWUD)

 Scroll      -  A scroll may be used once to cast a spell, and then
                the scroll will vanish. You specify the spell name
                under 'Consumable Spells'.

 Equipment   -  The item can be worn. In addition to equipment
                bonuses, a piece of equipment may have innate or
                charged spells, which both can be specified by name
                in the corresponding sections. An innate spell will
                stay with you while you wear the item, while a
                charged spell has a limited number of charges, and
                only casts the spell on command.

 Equipable   -  A combination of equipment and container. You may
 Container      wear this item, and it may also hold items. For
                example, you can wear a backpack on your back.

 Liquid      -  This item contains a liquid that you may drink. The
 Container      name of the liquid should correspond to a liquid type
                that exists in the game, or else it will become an
                unknown liquid and have no effect when you drink it.
                New liquid types may be suggested by builders at any
                time, and their effects can be anything. You must
                also specify the 'Liquid Uses' which means how many
                times can you drink from this container. Take not that
                liquid containers may contain ANY liquid, but what you
                specify is the liquid they load with.

 Consumable  -  This is the same as a Liquid Container, but the item
 Drink          will completely disappear after you use it once.

 Consumable  -  You can eat this item. It will disappear when you do.
 Food

 -------------------------------------------------------------------------------

 Places   -  If the dominant item type is equipment or equipable, you
             must specify which ares of the body it occupies while
             worn. Multiple selections are possible, but try to keep
             it so that it makes sense.

 Classes  -  If the dominant item type is equipment or equipable, this
             specifies which classes may equip the item.

             +---------------------+---------------------+
             | (Wa) - Warrior      | (Th) - Thief        |
             | (Kn) - Knight       | (As) - Assassin     |
             | (Br) - Berzerker    | (Rg) - Rogue        |
             | (Pa) - Paladin      | (Ra) - Ranger       |
             | (Cr) - Crusader     | (Me) - Merchant     |
             | (Dk) - Death Knight | (Sb) - Shadowblade  |
             | (Ap) - Anti Paladin | (Mc) - Mercenary    |
             +---------------------+---------------------+
             | (Cl) - Cleric       | (Mg) - Mage         |
             | (Dr) - Druid        | (Wi) - Wizard       |
             | (Mo) - Monk         | (Il) - Illusionist  |
             | (Pr) - Priest       | (Sc) - Sorcerer     |
             | (Hl) - Healer       | (Sm) - Summoner     |
             | (Dc) - Dark Cleric  | (Wl) - Warlock      |
             | (Al) - Alchemist    | (Sh) - Shapeshifter |
             +---------------------+---------------------+

 -------------------------------------------------------------------------------
 
 Item Flags    -  An Item Flag is a togglable option setting a certain
                  aspect of the item to true or false. It is false by
                  default, so flagging an item will set these true:
                  
 GLOWING       -  This item glows.
 HUMMING       -  This item hums.
 GOOD          -  This item can be equipped by the good.
 EVIL          -  This item can be equipped by the evil.
 NEUTRAL       -  This item can be equipped by the neutral.
 BURNING       -  This item flaming hot.
 FREEZING      -  This item is freezing cold.
 SHOCKING      -  This item surges with electricity.
 PERISH        -  This item will be destroyed upon death.
 INGREDIENT    -  This item is a potion ingredient.
 INVISIBLE     -  This item is invisible to the common eye.
 UNTOUCHABLE   -  This item is completely inaccessible by normal commands.
 NO_GET        -  You cannot pick up this item.
 NO_DROP       -  You cannot drop this item.
 NO_SACRIFICE  -  You cannot sacrifice this item.
 NO_RENT       -  You cannot keep this item after you quit the game.

 NOTE: By default, an item can be equipped by any alignment.
       If you specify an alignment flag, then only those who
       are aligned with the current flags can equip it. If
       you flag all three of the alignment flags, it's the
       same as the default setting.

";

      else if (str == "Colors")
        output =@"
                     Adding Colors To A Word Or String
                    ___________________________________
 
 To add color to a word, put a color mark before it, signified by a
 pound sign and the letter of the color. At the end of the word or
 sentence, change the color back to normal.

           n = White      N = Grey (Normal) 
           r = Red        R = Dark Red 
           g = Green      G = Dark Green 
           b = Blue       B = Dark Blue 
           y = Yellow     Y = Dark Yellow 
           c = Cyan       C = Dark Cyan 
           m = Magenta    M = Purple

 Usage:    A pound sign followed by the letter of the color. 
 Example:  This is a #rRed#N Rose.
 
 ";
 
      else if (str == "Frequencies")
        output = @"        
                            Event Frequencies
                           ___________________
                           
 On Tempest MUD, there is a periodic 'Animation' that occurs once
 every second. At each of these animation 'ticks', the world updates
 and the objects consider actions or events. Mobiles can consider
 moving, Rooms can consider an event, and so on. Anything in the
 game that relies on this animation will roll a random number every
 second between 0 and 60,000.  So you can control how often these
 events occur by assigning a minimum value that needs to be surpassed.
 If you set a frequency to 1000, then you have a 1/60 chance of your
 event taking place every second. By doing a little bit of simple
 math, you can see that the following frequencies will result in
 an approximate behavior of:
 
 60,000  -  The event will occur once per second.
  6,000  -  The event will occur once every 10 seconds.
  1,000  -  The event will occur once per minute.
    100  -  The event will occur once every 10 minutes.
     16  -  The event will occur once per hour.
      0  -  The event will never occur.

 The only exception to this is for mobiles loading items into their
 inventory or equipment. These use a scale from 0-10,000 which is
 completely linear. A frequency of 10,000 would load the item 100%
 of the time, 5,000 would load 50% of the time, and so on.
 
";      
                             
      else if (str == "Writing And Theme")
        output = @"
                        Writing Guidelines And Theme
                       ______________________________

 -------------------------------------------------------------------------------

 Staying In-Character:

 Tempest MUD is strictly roleplaying, therefore the zones and rooms must
 stay in-character and in-theme. This will be given a loose leniency when
 it comes to singular mobiles and rooms. For instance, if you wanted a
 mobile named 'Fenrir' which was a huge wolf, it would be fine. However,
 creating an entire zone in the theme of Norse mythology would not be
 acceptable. Within your own judgment, try and maintain the basic theme,
 and intermix any real life or mythological references that you deem
 appropriate. Any zone submitted is subject to change or non-use if the
 implementors disapprove. Please be aware that we reserve the right to
 change the placement or use of words at our own discretion.

 -------------------------------------------------------------------------------

 Maintaining Theme Relevance:

 There are four quadrants of the world. Each one is mainly, but not totally,
 in the theme of the patron God of that section. Pick one of the four areas,
 and write a zone for it. It can be a city, a dungeon, a cave, a mountain...
 Anything. Use your creativity to work out themes, events, history, people,
 monsters... Again, anything. The Tempest zone (the large storm in the center
 of the world), as well as the capital cities of the four nations, will be
 strictly written by the implementors, so please don't write anything for
 those. Aside from those 5 zones, anything goes. Keep in mind that natural as
 well as man-made borders prohibit access to the nations themselves. You
 can't write a zone that moves from one nation into the other. As said, pick
 one, and write in that theme.

 -------------------------------------------------------------------------------

 Hydecka - The Warrior City-State:

 A military state whose inhabitants worship Tarkus, the Warrior God. Hydecka
 is a heavily armored nation with many castles and cities. Many militant
 regimes, some in the service of the capital and others independent, exist
 across the land. Hydecka is in the Southeast quadrant of the world, and its
 capital city is Damascus, where the king (played by a mortal character whose
 name is subject to change) resides and commands the national army. It is
 surrounded by the man-made Wall of Jericho, which blockades all access save
 through the Diamond Gates to the West and North, as well as the Tempest to
 the Northwest.

 -------------------------------------------------------------------------------

 Almeccia - The Holy Kingdom:

 A deeply religious kingdom which honors the memory of the Goddess of Light,
 Elora. Elora is the patron Goddess of Clergy, and of those who practice the
 healing arts. There are many temples and shrines of ornate architecture
 across Almeccia. The villages that lie on the outskirts of the kingdom are
 modest and inhabited by humble, faithful peasants. The entire landscape of
 Almeccia is bright and sacred. Almeccia is in the Northeast quadrant of the
 world, and its capital city is Lourdes, where the High Clergy governs the
 nation. The Southern border is blockaded by the Wall of Jericho, while the
 Western Border is guarded by the impassable Diamante mountain range. The
 Tempest lies to the Southwest.

 -------------------------------------------------------------------------------

 Ravenna - The Shadowlands:

 A dangerous landscape of dark forests, murky swamps, and tall black
 mountains. Ravenna is the domain of Genevieve, the Goddess of Deception
 and Stealth. This is the dwelling place of the most talented Assassins and
 Thieves in the world. Ravenna is in the Northwest quadrant of the world,
 and its capital city is Odessa, a misty fort that serves as home to the
 Shadow Guild, an order of adept Rogues who rule the land from the shadows.
 The frightening Diamante mountains stand tall to the East, preventing access
 to the Holy Kingdom, while the thick, deep forest of Terracyon provides
 secure protection to the South. The Tempest lies to the Southeast.

 -------------------------------------------------------------------------------

 Volaris - Mage Country:

 Volaris is the homeland of Scholars and Mages. The students of Academics and
 Wizardry alike pay homage to Aristal, the Mage God. Though not as
 spectacularly stunning in appearance as the grandiose cities and castles of
 the East, Volaris has many large cities that serve mainly as centers for
 education and research. Volaris is a slightly dark, mysterious, exotic land.
 Many strange and curious things can be seen there, while some things there
 cannot be seen at all. Volaris is in the Southwest quadrant of the world,
 and its capital city is Scion, a huge Castle and Academy from where the
 Council of Elder Mages govern the land. To the North, Terracyon Forest
 prohibits access into Ravenna. To the East lies the Wall of Jericho,
 barring entry into Hydecka. The Tempest lies to the Northeast.

 -------------------------------------------------------------------------------

 Clan Writing:

 We will accept writing and ideas from anybody that wants to propose clan
 ideas, or if you want to start your own clan even. However be aware that we
 will judge this material slightly more critically than the zone writing, as
 we can only allow clans to be put in if all of the implementors agree that
 the theme is proper.

 -------------------------------------------------------------------------------

 Make Your Own Story:

 Tempest MUD will be a rich world with heavy themes of politics and religion,
 all roleplayed by human players. Kings, officials and even the Gods
 themselves will ALL be roleplayed by human players. Normal mortal characters
 may have the option to come out of character on certain private channels,
 but Gods will ALWAYS be in character, and never reveal who their human
 player is. As a builder, you may have the option to play a God character
 yourself. You can incorporate new ideas for your own Immortal into your
 zones and sub-themes.

 -------------------------------------------------------------------------------

 Scion Library:

 The Library of Scion will be an in and out of game collection of literature
 written in-character by the players of Tempest MUD. These will serve the
 purpose of expanding on the game theme, as well as providing the players
 with a medium for applying their talents to the game. Contests and Quests
 will be held periodically as an opportunity for players to use their
 artistic talents and publish their winning works in the library of Scion,
 in the game, and on the Tempest website. Please refer to the Tempest MUD
 website to see read the background story, and to browes the Library of
 Scion for the current selection of available literature.

 http://www.omegaflare.com/tempest

";
      else if (str == "Balancing Mobile Stats")
        output = @"
                          Balancing Mobile Stats
                         ________________________

 You can use the following formulas to calculate the complete average for
 every statistic a mobile might have at a given level. You can of course
 make mobiles much weaker or stronger than average. However using these
 numbers should give you a point of reference.

                  +--------------------------------------+
                  | Health        -  (Level * 100) + 100 |
                  | Mana          -  (Level * 100) + 100 |
                  |                                      |
                  | Strength      -  (Level * 2)   + 2   |
                  | Dexterity     -  (Level * 1.5) + 2   |
                  | Constitution  -  (Level * 1.5) + 2   |
                  | Intelligence  -  (Level * 1)   + 1   |
                  | Wisdom        -  (Level * 1)   + 1   |
                  |                                      |
                  | Hitroll       -  (Level / 5)  + 1    |
                  | Damroll       -  (Level / 4)  + 1    |
                  | Armorclass    -  (Level * 10) + 10   |
                  | Resistance    -  (Level / 3)         |
                  |                                      |
                  | Experience    -  (Level * 1000)      |
                  | Gold          -  (Level * 10) / 2    |
                  +--------------------------------------+

 One very important thing to know is that normally, equipment stats such
 as Hitroll, Damroll, Armorclass and Resistance are only added by putting
 on equipment. However, since the majority of combat effectiveness comes
 from equipment stats, mobiles need to have these stats available with
 no equipment on, so that they can still be tough. So when you set the
 values of HR, DR, AC and MR, keep in mind that these are assuming your
 mob is unarmed. If you give them equipment, you'll have to tone down
 your their innate equipment stats to compensate.
 
 ";
        
      else if (str == "Balancing Item Stats")
        output = @"
                             Balancing Item Stats
                            ______________________

 Health/Mana/Movement Chart:

 At the following levels, the different classes should (if they balanced their
 stats in a common fashion) end up with something around these numbers.  It is
 possible to get way less or way more, but that's not necessary to take into
 account for the purposes of making balanced equipment, since they will suffer
 other losses for their sacrifice.
  
      Lv       Warrior          Thief           Cleric            Mage
    +----+---------------+----------------+----------------+----------------+
    | 1  | 210  45  190  | 140  70   135  | 120  110  120  | 95   20   100  |
    | 5  | 300  65  280  | 200  110  220  | 170  185  190  | 135  340  170  |
    | 10 | 450  100 400  | 280  160  330  | 240  280  280  | 190  490  250  |
    | 20 | 700  150 650  | 460  270  560  | 400  500  475  | 300  800  430  |
    | 30 | 1000 200 900  | 650  380  800  | 550  730  650  | 440  1150 620  |
    | 40 | 1300 250 1100 | 850  490  1050 | 725  980  860  | 590  1500 805  |
    | 50 | 1700 300 1400 | 1100 600  1300 | 900  1260 1050 | 740  1850 980  |
    | 60 | 2100 350 1700 | 1300 700  1550 | 1090 1550 1250 | 890  2250 1180 |
    | 70 | 2400 400 2000 | 1600 850  1850 | 1300 1850 1450 | 1050 2650 1350 |
    | 80 | 2800 450 2300 | 1850 950  2100 | 1500 2200 1700 | 1190 3100 1560 |
    | 90 | 3200 550 2600 | 2100 1050 2400 | 1750 2550 1900 | 1350 3550 1750 |
    | 99 | 3600 600 2800 | 2300 1200 2600 | 1950 2900 2100 | 1480 3950 1900 |
    +----+---------------+----------------+----------------+----------------+

 These numbers represent NATURAL stats, meaning with no equipment modifiers.
 Equipment in a SPECIFIC Full Kit should not exceed 150 % of the natural stat.

 For example, a Level 99 Warrior in a hitpoint kit should not be able to get
 much more than (3600 * 1.5) which is 5400.  If equipment exists that allows
 such a character to attain well over this amount (in this case, well over
 5400 hp) then the equipment either needs some negative modifiers, or a
 reduction in the specified health bonus.
 
 -------------------------------------------------------------------------------
 
 Character Statistics Chart:

 The following chart very loosley represents what the AVERAGE BALANCED
 character might have for statistics. These are generalized for each class
 type, so they will vary if you have a special class.  For example, a Knight
 might have more armorclass but less damage than a Warrior, and a Death
 Knight would have more damage and less armorclass.

 These are a very general reference. You will have to adjust these numbers
 depending on which specific class you are of this class type, and also what
 type of equipment this particular character is outfitted in.

 +---------+ +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+
 | Warrior | | Level  | STR | DEX | CON | INT | WIS | Hit | Dam |  AC  | MR  |
 +---------+ +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+
             | Lv. 1  | 5   | 2   | 3   | 0   | 0   | 0   | 1   | 40   | 0   |
             | Lv. 25 | 50  | 25  | 40  | 7   | 5   | 6   | 6   | 320  | 15  |
             | Lv. 50 | 100 | 50  | 75  | 15  | 10  | 12  | 12  | 600  | 30  |
             | Lv. 75 | 150 | 75  | 115 | 20  | 20  | 18  | 18  | 900  | 45  |
             | Lv. 99 | 200 | 100 | 150 | 25  | 25  | 25  | 25  | 1200 | 65  |
             +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+

 +---------+ +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+
 | Thief   | | Level  | STR | DEX | CON | INT | WIS | Hit | Dam |  AC  | MR  |
 +---------+ +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+
             | Lv. 1  | 2   | 5   | 2   | 1   | 0   | 0   | 1   | 25   | 0   |
             | Lv. 25 | 40  | 50  | 25  | 7   | 5   | 5   | 5   | 210  | 10  |
             | Lv. 50 | 75  | 100 | 50  | 15  | 10  | 10  | 10  | 400  | 20  |
             | Lv. 75 | 115 | 150 | 75  | 20  | 20  | 15  | 15  | 600  | 30  |
             | Lv. 99 | 150 | 200 | 100 | 25  | 25  | 20  | 20  | 800  | 40  |
             +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+

 +---------+ +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+
 | Cleric  | | Level  | STR | DEX | CON | INT | WIS | Hit | Dam |  AC  | MR  |
 +---------+ +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+
             | Lv. 1  | 1   | 1   | 1   | 2   | 5   | 1   | 0   | 20   | 0   |
             | Lv. 25 | 6   | 20  | 25  | 25  | 50  | 5   | 4   | 140  | 15  |
             | Lv. 50 | 12  | 40  | 50  | 50  | 100 | 8   | 7   | 290  | 35  |
             | Lv. 75 | 18  | 60  | 75  | 75  | 150 | 11  | 11  | 440  | 50  |
             | Lv. 99 | 25  | 75  | 100 | 100 | 200 | 15  | 15  | 600  | 70  |
             +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+

 +---------+ +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+
 | Mage    | | Level  | STR | DEX | CON | INT | WIS | Hit | Dam |  AC  | MR  |
 +---------+ +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+
             | Lv. 1  | 0   | 0   | 0   | 6   | 4   | 1   | 0   | 15   | 0   |
             | Lv. 25 | 6   | 12  | 25  | 60  | 25  | 2   | 2   | 130  | 20  |
             | Lv. 50 | 12  | 25  | 50  | 125 | 50  | 4   | 4   | 250  | 40  |
             | Lv. 75 | 18  | 37  | 65  | 190 | 75  | 7   | 7   | 375  | 60  |
             | Lv. 99 | 25  | 50  | 75  | 250 | 100 | 9   | 9   | 500  | 80  |
             +--------+-----+-----+-----+-----+-----+-----+-----+------+-----+

 Remember to make adjustments for CLASS and KIT! A Shadowblade is more
 geared for damage than an Assassin, with less protection. They would most
 likely be interested, then, in doing more damage with their equipment. Thus
 you would want to keep in mind all possible outfits for a Shadowblade that
 add damage, and keep it balanced. This is the type of thing you must
 consider at all times when balancing equipment for each class.

 ------------------------------------------------------------------------------- 
 
 General Class Differences:

 Warrior       -  All-round combat class. Well-protected and great damage.
 Knight        -  More defense than a Warrior, less damage.
 Berzerker     -  More damage than a Warrior, less defense.
 Paladin       -  More defense than a Warrior, less damage. Great resistance.
 Crusader      -  Best defense, worst damage.
 Death Knight  -  Second-best damage.
 Anti Paladin  -  Best damage, worst defense.

 Thief         -  Skill class. Less protection than a warrior. Good damage.
 Assassin      -  More damage than a thief. No combat losses. Great damage.
 Rogue         -  Slightly better than a Thief in all combat areas.
 Ranger        -  Best-protected Thief class. Best bow-user. Good damage.
 Merchant      -  Cannot equip most weapons and armor. Non-combat class.
 Shadowblade   -  Better damage than an Assassin, less protection.
 Mercenary     -  Well protected and versatile in armament. Good damage.

 Cleric        -  Healing class. Weak physically.
 Druid         -  More health and protection than a Cleric, less mana.
 Monk          -  Most health and physical damage. Low mana and spell power.
 Priest        -  More mana than a Cleric, less health. Best healer.
 Healer        -  Same physical attributes as a Cleric. Uses potions.
 Dark Cleric   -  Favors spell power instead of mana. Good protection.
 Alchemist     -  Non-combat oriented. Weakest stats.

 Mage          -  Magic class. Great spell damage and resistance.
 Wizard        -  More mana and damage than a basic mage.
 Illusionist   -  Less mana, more health.
 Sorcerer      -  All-purpose mage. Versatile and balanced.
 Summoner      -  Weak stats, but can summon powerful familliars.
 Warlock       -  Most magic damage. Lower mana and physical stats.
 Shapeshifter  -  Weakest stats, but can change shape to a melee type entity.
 
 ";        
        
      else if (str == "Balancing Item Prices")
        output = @"
                            Balancing Item Prices
                           _______________________

 This chart represents how much gold characters should have at certan
 levels. The three columns represent whether they are poor, average, or
 rich. Keep in mind that the tendency on a MUD is to get way more gold
 than anyone anticipated possible, so keep gold rare, shop equipment
 expensive, and mobile popped or found equipment relatively low-worth
 in price, even if it's a very useful item. Keeping gold rare, and thus
 making economy an important aspect of the game, is a high priority
 of Tempest MUD.

                 +-------------------------------------------+
                 | Level     Minimum     Average     Loaded  |
                 +-------------------------------------------+
                 | 1         100         250         500     |
                 | 2         200         500         1000    |
                 | 5         500         1,000       2,000   |
                 | 10        750         1,500       3,000   |
                 | 20        1,500       3,000       6,000   |
                 | 30        3,000       5,000       10,000  |
                 | 40        7,000       15,000      30,000  |
                 | 50        12,500      25,000      50,000  |
                 | 60        25,000      50,000      100,000 |
                 | 70        50,000      100,000     200,000 |
                 | 80        100,000     200,000     400,000 |
                 | 90        175,000     350,000     700,000 |
                 | 99        250,000     500,000     1 Mil   |
                 +-------------------------------------------+

 -------------------------------------------------------------------------------

 Shop Item Prices:

 A sold weapon should cost between 50% and 200% of the Level Average.
 A sold piece of armor should cost between 25% and 200% of the Level Average.
 A sold non-equipable item should be priced based on its usefulness. 

 (refer to the above chart for the level averages)
 
 If this is not totally clear, take a look at the standard equipment lists.
 Take the standard Thief equipment sold at Odessa as an example. Many of the
 daggers are expensive, usually costing much more than the level average.
 The same goes for armor that is somewhat large or expensive, like jewelry
 or body armor. But items such as gloves or belts would be far less
 expensive, as low as 20-25% of the level average.

 Also keep in mind the very important PERISH flag. This is imporant in terms
 of MUD economy, because an expensive item that has the perish flag on it
 will be lost when death occurs, and you will have to buy another one. Try
 to make magical items, important items, or expensive items PERISH if
 possible. Most important items above level 50 should be PERISH.
 
 -------------------------------------------------------------------------------
 
 Mobile Item Values:

 Items that mobiles pop/load should be relatively low-worth.
 These formulas can help estimate values based on level:

 Worthless Item  -  (Mobile Level * 10) / 2
 Average Item    -  (Mobile Level * 10) * 2
 Good Item       -  (Mobile Level * 10) * 5
 Valuable Item   -  (Mobile Level * 10) * 20 
 Rare Item       -  (Mobile Level * 10) * 50
 
 Keep in mind that in MUDs, selling items is usually only
 truly profitable when selling to other people, who will not
 care about hard-coded worth. In this respect, it is fair to
 keep the worth's very, very low.
 
 ";
      else if (str == "Standard Weapons List")
        output = @"
 Thief Standard Weapons (STATS)

 +----+--------------------+----------------+------------------------+
 | LV |        NAME        |     BONUSES    |     REQUIRED STATS     |
 +----+--------------------+----------------+------------------------+
 | 1  | Razorblade         |  1 Dam         | 2    5    2    0    0  |
 | 2  | White Dagger       |  1 Hit   1 Dam | 2    5    2    0    0  |
 | 5  | Steel Dagger       |  2 Hit   1 Dam | 0    10   0    0    0  |
 | 8  | Sharp Steel Dagger |  2 Hit   2 Dam | 0    15   0    0    0  |
 | 10 | Stiletto           |  3 Hit   3 Dam | 10   20   10   0    0  |
 | 12 | Sharp Stiletto     |  3 Hit   4 Dam | 10   22   10   0    0  |
 | 15 | Serrated Knife     |  4 Hit   4 Dam | 15   25   10   0    0  |
 | 20 | Assassin's Knife   |  5 Hit   5 Dam | 20   40   20   0    0  |
 | 25 | Onyx Dagger        |  5 Hit   6 Dam | 25   50   30   0    0  |
 | 30 | Morning Glory      |  6 Hit   6 Dam | 20   60   30   0    0  |
 | 35 | Blood Dagger       |  6 Hit   7 Dam | 35   60   30   0    0  |
 | 40 | The Sidewinder     |  8 Hit   8 Dam | 20   80   50   10   0  |
 | 45 | Lotus Dagger       |  9 Hit   8 Dam | 20   95   40   5    5  |
 | 50 | Black Stiletto     | 10 Hit  10 Dam | 50   100  50   10   0  |
 | 65 | Ebony Kris         | 11 Hit  12 Dam | 75   120  50   15   5  |
 | 70 | Sun Dagger         | 12 Hit  12 Dam | 70   120  80   20   15 |
 | 80 | Blossom Dagger     | 14 Hit  14 Dam | 80   180  80   0    0  |
 | 90 | Royal Dagger       | 19 Hit  14 Dam | 90   200  90   0    0  |
 | 95 | Black Orchid       | 18 Hit  18 Dam | 150  150  100  0    0  |
 | 99 | The Heartseeker    | 21 Hit  17 Dam | 100  200  100  20   0  |
 +----+--------------------+----------------+------------------------+
 | 1  | Wooden Bow         |  1 Dam         | 2    5    2    0    0  |
 | 2  | Longbow            |  2 Dam         | 2    5    2    0    0  |
 | 5  | Iron Bow           |  1 Hit   1 Dam | 5    6    0    0    0  |
 | 8  | Leather Whip       |  2 Hit   2 Dam | 5    10   10   0    0  |
 | 10 | Crossbow           |  1 Hit   3 Dam | 10   20   5    0    0  |
 | 12 | Iron Crossbow      |  2 Hit   3 Dam | 15   20   5    0    0  |
 | 15 | Throwing Star      |  1 Hit   4 Dam | 0    25   0    0    0  |
 | 20 | Black Leather Whip |  4 Hit   4 Dam | 20   20   20   0    0  |
 | 25 | Throwing Knife     |  3 Hit   5 Dam | 0    50   0    0    0  |
 | 30 | Poison Darts       |  3 Hit   3 Dam | 0    60   0    0    0  |
 | 35 | Elven Longbow      |  4 Hit   6 Dam | 20   45   20   10   0  |
 | 40 | Morningstar        |  6 Hit   7 Dam | 40   40   40   0    0  |
 | 50 | Drow Longbow       |  3 Hit   9 Dam | 30   80   30   20   10 |
 | 55 | Triple Flail       |  8 Hit   9 Dam | 60   60   60   0    0  |
 | 60 | Robin Bow          |  6 Hit  10 Dam | 20   100  20   10   10 |
 | 70 | Mystic Bow         |  7 Hit  10 Dam | 10   120  10   20   20 |
 | 75 | Flame Whip         | 10 Hit  10 Dam | 90   90   90   0    0  |
 | 80 | Dream Bow          |  8 Hit  12 Dam | 80   100  100  0    50 |
 | 85 | Giant Scythe       | 13 Hit  13 Dam | 100  100  100  0    0  |
 | 95 | Seraphic Bow       | 12 Hit  16 Dam | 80   120  120  30   30 |
 | 99 | Valkyrie           |  9 Hit  17 Dam | 130  130  130  0    0  |
 +-------------------------+----------------+------------------------+

 Thief Standard Weapons (WORTH & CLASSES)

 +----+-----------------+---------+----------------------------------+
 | LV |      NAME       |  WORTH  |             CLASSES              |
 +----+-----------------+---------+----------------------------------+
 | 1  | Razorblade      |      60 | All Thief                        |
 | 2  | White Dagger    |     250 | All Thief                        |
 | 5  | Steel Dagger    |     400 | All Thief                        |
 | 8  | S. Steel Dagger |     600 | All Thief                        |
 | 10 | Stiletto        |     800 | Th As Rg Ra Sb Mc                |
 | 12 | Sharp Stiletto  |     885 | Th As Rg Ra Sb Mc                |
 | 15 | Serrated Knife  |     950 | Th As Rg Sb Mc                   |
 | 20 | As. Knife       |   1,500 | Th As Rg Sb Mc                   |
 | 25 | Onyx Dagger     |   2,500 | Th As Rg Sb Mc                   |
 | 30 | Morning Glory   |   4,000 | Th As Rg Ra Sb Mc                |
 | 35 | Blood Dagger    |   7,500 | Th As Rg Sb Mc                   |
 | 40 | The Sidewinder  |  10,000 | Th As Rg Ra Sb Mc                |
 | 45 | Lotus Dagger    |  20,000 | Th As Rg Ra Sb Mc                |
 | 50 | Black Stiletto  |  30,000 | Th As Ra Rg Sb Mc                |
 | 65 | Ebony Kris      |  68,000 | Th As Rg Sb Mc                   |
 | 70 | Sun Dagger      | 170,000 | Th As Ra Rg Sb Mc                |
 | 80 | Blossom Dagger  | 250,000 | Th As Ra Rg Sb Mc                |
 | 90 | Royal Dagger    | 600,000 | Th As Rg Sb                      |
 | 95 | Black Orchid    | 800,000 | Th As Sb                         |
 | 99 | The Heartseeker | 950,000 | Th As Sb                         |
 +----+-----------------+---------+----------------------------------+
 | 1  | Wooden Bow      |     100 | Th As Rg Ra Me Sb Mc Cl Hl Wa Pa |
 | 2  | Longbow         |     300 | Rg Ra Mc Wa                      |
 | 5  | Iron Bow        |     550 | Th As Rg Ra Me Sb Mc Cl Hl Wa Pa |
 | 8  | Leather Whip    |     800 | Th As Rg Sb Mc Wa                |
 | 10 | Crossbow        |     920 | Th As Rg Ra Me Sb Mc Cl Hl Wa Pa |
 | 12 | Iron Crossbow   |   1,000 | Th As Rg Ra Sb Mc Cl Hl Wa Pa    |
 | 15 | Throwing Star   |   1,200 | Th As Rg Sb                      |
 | 20 | B. Leather Whip |   2,000 | Th As Rg Sb Mc Wa                |
 | 25 | Throwing Knife  |   4,000 | Th As Rg Sb                      |
 | 30 | Poison Darts    |   7,500 | Th As Rg Sb                      |
 | 35 | Elven Longbow   |  10,000 | Th As Rg Ra Sb Mc Cl Hl Wa Pa    |
 | 40 | Morningstar     |  14,000 | Th As Rg Sb Mc Wa                |
 | 50 | Drow Longbow    |  45,000 | Th As Rg Ra Sb Mc Cl Hl Wa Pa    |
 | 55 | Triple Flail    |  80,000 | Th As Rg Sb Mc Wa                |
 | 60 | Robin Bow       | 100,000 | Th As Rg Ra Sb Mc Cl Hl Wa Pa    |
 | 70 | Mystic Bow      | 250,000 | Th As Rg Ra Mc Cl Hl             |
 | 75 | Flame Whip      | 350,000 | Th As Rg Sb Mc Wa                |
 | 80 | Dream Bow       | 400,000 | Th Rg Ra Mc Cl Hl                |
 | 85 | Giant Scythe    | 500,000 | Th As Rg Sb Mc Wa                |
 | 95 | Seraphic Bow    | 980,000 | Ra Cl                            |
 | 99 | Valkyrie        | 1.2 Mil | Ra As                            |
 +----------------------+---------+----------------------------------+
 
 ";
        
      else if (str == "Standard Armor List")
        output = @"
 Thief Standard Armor (STATS)

 +----+----------------------+----------------------+--------------------+
 | LV |        NAME          |       BONUSES        |   REQUIRED STATS   |
 +----+----------------------+----------------------+--------------------+
 | 10 | White Headband       |  15 AC   1 Hit       | 10  15  5   0   0  |
 | 25 | Green Headband       |  30 AC   1 Hit       | 30  40  20  0   0  |
 | 40 | Yellow Headband      |  40 AC   1 Hit       | 50  70  30  10  0  |
 | 65 | Orange Headband      |  50 AC  50 Hp        | 70  110 60  15  10 |
 | 80 | Blue Headband        |  65 AC   1 Hit       | 80  120 80  20  10 |
 | 90 | Red Headband         |  80 AC  90 Hp        | 100 130 100 15  20 |
 | 95 | Black Headband       |  50 AC   2 Hit       | 80  160 80  25  20 |
 | 96 | Sanguinary Circlet   |  10 AC   1 Dam       | 150 100 100 0   0  |
 +----+----------------------+----------------------+--------------------+
 | 1  | White Shirt          |  10 AC               | 0   0   0   0   0  |
 | 2  | Black Shirt          |  15 AC               | 3   4   2   1   0  |
 | 5  | Amber Vest           |  35 AC               | 5   5   5   0   0  |
 | 10 | Rogue Jacket         |  50 AC   50 Hp       | 15  20  10  3   2  |
 | 20 | Garnet Coat          |  80 AC   80 Hp       | 30  40  20  6   4  |
 | 30 | Crimson Mail         | 120 AC  100 Hp       | 45  60  30  5   5  |
 | 40 | Lupine Vestment      | 180 AC    2 Hit      | 60  80  40  10  10 |
 | 50 | Grand Vestcoat       | 220 AC  150 Hp       | 75  100 50  10  10 |
 | 65 | Full Metal Jacket    | 260 AC    1 Dam      | 100 120 60  10  10 |
 | 80 | Nightmare Garb       | 300 AC  200 Hp       | 120 160 80  20  10 |
 | 90 | Brigandine           | 450 AC  300 Hp       | 135 180 90  20  10 |
 +----+----------------------+----------------------+--------------------+
 | 1  | Sandals              | 3  AC                | 0   0   0   0   0  |
 | 2  | Boots                | 5  AC                | 3   4   2   1   0  |
 | 10 | Red Boots            | 8  AC   30 Hp  20 Mv | 10  15  10  1   1  |
 | 20 | Thief Boots          | 15 AC  100 Mv        | 25  35  15  2   2  |
 | 40 | Sturdy Boots         | 25 AC                | 70  70  40  5   5  |
 | 50 | Silent Shoes         | 20 AC   1 Hit  50 Mv | 70  95  50  5   5  |
 | 80 | Combat Boots         | 10 AC   1 Dam        | 135 135 80  15  15 |
 | 90 | Rogue Boots          | 30 AC  200 Mv        | 135 180 90  20  10 |
 +----+----------------------+----------------------+--------------------+
 | 1  | Cape                 | 7 AC                 | 3   4   2   1   0  |
 | 10 | Dark Cape            | 10 AC  10 Hp         | 10  15  10  1   1  |
 | 30 | Brown Cape           | 20 AC  15 Hp         | 30  50  30  5   5  |
 | 50 | Black Cape           | 35 AC  25 Hp         | 50  90  50  10  5  |
 | 60 | Rogue Cape           | 40 AC                | 60  110 60  10  10 |
 | 80 | Shadow Cloak         | 30 AC  45 Hp  5 MR   | 80  150 80  15  10 |
 | 85 | Assassin's Cloak     | 60 AC   5 MR         | 85  160 70  25  10 |
 | 90 | Red Cape             | 70 AC                | 90  170 70  20  20 |
 +----+----------------------+----------------------+--------------------+
 | 1  | Gloves               |  3 AC                | 0   0   0   0   0  |
 | 12 | White Gloves         |  8 AC   20 Hp  20 Mn | 15  20  10  2   1  |
 | 25 | Leather Gloves       | 10 AC    1 Hit       | 35  45  20  5   3  |
 | 50 | Rogue Gloves         | 15 AC   50 Hp        | 75  100 50  15  10 |
 | 65 | Thief Gloves         | 20 AC  100 Mv        | 70  120 65  10  10 |
 | 85 | Black Leather Gloves |  5 AC    1 Dam       | 90  130 80  5   5  |
 +----+----------------------+----------------------+--------------------+
 | 5  | Brown Belt           |  5 AC                | 5   5   5   0   0  |
 | 20 | Leather Belt         | 10 AC  20 Hp         | 15  25  10  5   5  |
 | 45 | Snakeskin Belt       | 15 AC  40 Hp  20 Mn  | 50  60  30  5   5  |
 | 80 | Black Belt           | 20 AC   5 MR         | 80  120 60  10  5  |
 | 90 | Red Belt             |  5 AC   8 MR         | 110 160 70  15  10 |
 +----+----------------------+----------------------+--------------------+
 | 1  | Black Pants          |   5 AC               | 3   4   2   1   0  |
 | 3  | Leather Pants        |   8 AC   15 Hp       | 3   4   2   1   0  |
 | 8  | Brown Corduroys      |  15 AC   20 Hp 50 Mv | 10  15  10  1   0  |
 | 18 | Black Corduroys      |  20 AC   40 Hp       | 25  35  15  2   2  |
 | 30 | Red Corduroys        |  35 AC               | 45  60  30  5   5  |
 | 50 | Camouflage Pants     |  50 AC   50 Hp       | 75  100 50  10  10 |
 | 66 | Assassin's Pants     |  10 AC    1 Dam      | 110 110 60  10  10 |
 | 88 | Black Rogue Pants    | 100 AC  100 Hp       | 125 170 100 20  10 |
 | 92 | Black Cargo Pants    | 120 AC               | 135 190 90  20  10 |
 +----+----------------------+----------------------+--------------------+
 | 3  | Leather Bracer       |  5 AC                | 5   5   5   1   0  |
 | 15 | Bronze Bracer        | 10 AC                | 15  15  10  1   0  |
 | 55 | Metal Bracer         | 15 AC  1 Hit         | 80  50  50  0   0  |
 | 75 | Spiked Wristband     |  5 AC  1 Dam         | 120 70  70  10  10 |
 | 85 | Steel Bracer         | 20 AC  1 Hit         | 120 80  80  15  10 |
 +----+----------------------+----------------------+--------------------+
 | 5  | Brass Ring           |   1 Hit              | 0   0   0   0   0  |
 | 20 | Emerald Ring         |  50 Hp   20 Mn       | 0   0   0   0   0  |
 | 35 | Ruby Ring            |  80 Hp               | 0   0   0   0   0  |
 | 50 | Sapphire Ring        |  50 Hp   1 Hit       | 0   0   0   0   0  |
 | 50 | Ring of Precision    |   2 Hit              | 0   0   0   0   0  |
 | 80 | Ring of Balance      | 100 Hp   1 Hit       | 0   0   0   0   0  |
 | 90 | Diamond Ring         |  50 Hp  50 Mn   5 MR | 0   0   0   0   0  |
 +----+----------------------+----------------------+--------------------+
 | 35 | Bronze Medal         |   5 MR               | 0   0   0   0   0  |
 | 60 | Silver Medal         |  10 MR               | 0   0   0   0   0  |
 | 80 | Gold Medal           |  15 MR               | 0   0   0   0   0  |
 | 80 | Lapis Amulet         | 100 Hp  100 Mn       | 0   0   0   0   0  |
 | 90 | Bloodstone Medallion | 200 Hp               | 0   0   0   0   0  |
 | 99 | Black Narcissus      |  20 MR               | 0   0   0   0   0  |
 +----+----------------------+----------------------+--------------------+
 | 20 | Jade Earring         |  1  Hit              | 0   0   0   0   0  |
 | 35 | Emerald Earring      |  50 Hp   20 Mn       | 0   0   0   0   0  |
 | 50 | Sapphire Earring     |  50 Hp    1 Hit      | 0   0   0   0   0  |
 | 75 | Ruby Earring         | 150 Hp               | 0   0   0   0   0  |
 | 90 | Diamond Earring      |  50 Hp   50 Mn  5 MR | 0   0   0   0   0  |
 +----+----------------------+----------------------+--------------------+

 Thief Standard Armor (WORTH & CLASSES)

 +----+----------------------+---------+-------------------+
 | LV |        NAME          |  WORTH  |     CLASSES       |
 +----+----------------------+---------+-------------------+
 | 10 | White Headband       |     200 | All Thief         |
 | 25 | Green Headband       |     400 | All Thief         |
 | 40 | Yellow Headband      |   1,500 | All Thief         |
 | 65 | Orange Headband      |   7,500 | All Thief         |
 | 80 | Blue Headband        |  20,000 | All Thief         |
 | 90 | Red Headband         |  43,000 | Th As Rg Ra Sb Mc |
 | 95 | Black Headband       |  50,000 | Th As Rg Ra Sb Mc |
 | 96 | Sanguinary Circlet   | 750,000 | Th As Sb Rg       |
 +----+----------------------+---------+-------------------+
 | 1  | White Shirt          |     220 | All Thief         |
 | 2  | Black Shirt          |     410 | All Thief         |
 | 5  | Amber Vest           |     900 | All Thief         |
 | 10 | Rogue Jacket         |   1,300 | All Thief         |
 | 20 | Garnet Coat          |   2,200 | All Thief         |
 | 30 | Crimson Mail         |   5,000 | All Thief         |
 | 40 | Lupine Vestment      |  15,000 | All Thief         |
 | 50 | Grand Vestcoat       |  25,000 | All Thief         |
 | 65 | Full Metal Jacket    |  75,000 | Th As Rg Ra Sb Mc |
 | 80 | Nightmare Garb       | 200,000 | Th As Rg Ra Sb Mc |
 | 90 | Brigandine           | 350,000 | Th As Rg Ra Sb Mc |
 +----+----------------------+---------+-------------------+
 | 1  | Sandals              |      60 | All               |
 | 2  | Boots                |     180 | All               |
 | 10 | Red Boots            |     500 | All Thief         |
 | 20 | Thief Boots          |   1,100 | All Thief         |
 | 40 | Sturdy Boots         |   5,000 | All Thief/Warrior |
 | 50 | Silent Shoes         |   5,500 | All Thief         |
 | 80 | Combat Boots         |  65,000 | Th As Rg Ra Sb Mc |
 | 90 | Rogue Boots          | 115,000 | Th As Rg Ra Sb Mc |
 +----+----------------------+---------+-------------------+
 | 1  | Cape                 |      90 | All               |
 | 10 | Dark Cape            |     500 | All Thief         |
 | 30 | Brown Cape           |   1,800 | All Thief         |
 | 50 | Black Cape           |  10,000 | All Thief         |
 | 60 | Rogue Cape           |  20,000 | All Thief         |
 | 80 | Shadow Cloak         |  80,000 | Th As Rg Ra Sb Mc |
 | 85 | Assassin's Cloak     | 110,000 | Th As Rg Sb Mc    |
 | 90 | Red Cape             | 140,000 | Th As Rg Ra Sb Mc |
 +----+----------------------+---------+-------------------+
 | 1  | Gloves               |      50 | All               |
 | 12 | White Gloves         |     400 | All               |
 | 25 | Leather Gloves       |     950 | All               |
 | 50 | Rogue Gloves         |   6,500 | All Thief         |
 | 65 | Thief Gloves         |  15,000 | Th As Rg Ra Sb Mc |
 | 85 | Black Leather Gloves |  60,000 | Th As Rg Ra Sb Mc |
 +----+----------------------+---------+-------------------+
 | 5  | Brown Belt           |     250 | All               |
 | 20 | Leather Belt         |   1,000 | All               |
 | 45 | Snakeskin Belt       |   6,000 | All Thief         |
 | 80 | Black Belt           |  65,000 | Th As Rg Ra Sb Mc |
 | 90 | Red Belt             |  90,000 | Th As Rg Ra Sb Mc |
 +----+----------------------+---------+-------------------+
 | 1  | Black Pants          |     100 | All Thief         |
 | 3  | Leather Pants        |     200 | All Thief         |
 | 8  | Brown Corduroys      |     600 | All Thief         |
 | 18 | Black Corduroys      |   1,200 | All Thief         |
 | 30 | Red Corduroys        |   2,300 | All Thief         |
 | 50 | Camouflage Pants     |  12,500 | Th As Rg Ra Sb Mc |
 | 66 | Assassin's Pants     |  37,500 | Th As Rg Sb Mc    |
 | 88 | Black Rogue Pants    | 165,000 | Th As Rg Ra Sb Mc |
 | 92 | Black Cargo Pants    | 180,000 | Th As Rg Ra Sb Mc |
 +----+----------------------+---------+-------------------+
 | 3  | Leather Bracer       |     150 | All               |
 | 15 | Bronze Bracer        |     550 | All Thief/Warrior |
 | 55 | Metal Bracer         |   9,500 | All Thief/Warrior |
 | 75 | Spiked Wristband     |  45,000 | All Thief/Warrior |
 | 85 | Steel Bracer         |  82,500 | All Thief/Warrior |
 +----+----------------------+---------+-------------------+
 | 5  | Brass Ring           |     250 | All               |
 | 20 | Emerald Ring         |   1,500 | All               |
 | 35 | Ruby Ring            |   9,000 | All               |
 | 50 | Sapphire Ring        |  20,000 | All               |
 | 50 | Ring of Precision    |  22,000 | All Thief         |
 | 80 | Ring of Balance      | 180,000 | All Thief         |
 | 90 | Diamond Ring         | 450,000 | All               |
 +----+----------------------+---------+-------------------+
 | 35 | Bronze Medal         |  10,000 | All               |
 | 60 | Silver Medal         | 100,000 | All               |
 | 80 | Gold Medal           | 350,000 | All               |
 | 80 | Lapis Amulet         | 200,000 | All Thief         |
 | 90 | Bloodstone Medallion | 750,000 | All Thief         |
 | 99 | Black Narcissus      |   2 Mil | All Thief         |
 +----+----------------------+---------+-------------------+
 | 20 | Jade Earring         |   1,200 | All               |
 | 35 | Emerald Earring      |   8,500 | All               |
 | 50 | Sapphire Earring     |  20,000 | All               |
 | 75 | Ruby Earring         | 150,000 | All               |
 | 90 | Diamond Earring      | 450,000 | All               |
 +----+----------------------+---------+-------------------+

";
    }

    protected override void OnPaint(PaintEventArgs e)
    
    {
      base.OnPaint (e);
      
      txtHelp.Text = output;
    }


		protected override void Dispose(bool disposing)
		
		{
			if(disposing)
				if(components != null)
					components.Dispose();
					
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
      this.cmdOK = new System.Windows.Forms.Button();
      this.txtHelp = new System.Windows.Forms.TextBox();
      this.SuspendLayout();
      // 
      // cmdOK
      // 
      this.cmdOK.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdOK.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdOK.ForeColor = System.Drawing.SystemColors.InfoText;
      this.cmdOK.Location = new System.Drawing.Point(275, 480);
      this.cmdOK.Name = "cmdOK";
      this.cmdOK.TabIndex = 7;
      this.cmdOK.Text = "OK";
      this.cmdOK.Click += new System.EventHandler(this.cmdOK_Click);
      // 
      // txtHelp
      // 
      this.txtHelp.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtHelp.Location = new System.Drawing.Point(24, 8);
      this.txtHelp.Multiline = true;
      this.txtHelp.Name = "txtHelp";
      this.txtHelp.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
      this.txtHelp.Size = new System.Drawing.Size(592, 464);
      this.txtHelp.TabIndex = 8;
      this.txtHelp.Text = "";
      // 
      // frmHelp
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(8, 14);
      this.AutoScroll = true;
      this.ClientSize = new System.Drawing.Size(624, 510);
      this.Controls.Add(this.txtHelp);
      this.Controls.Add(this.cmdOK);
      this.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.MaximizeBox = false;
      this.MinimizeBox = false;
      this.Name = "frmHelp";
      this.ShowInTaskbar = false;
      this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "Help";
      this.ResumeLayout(false);

    }
		#endregion

    private void cmdOK_Click(object sender, System.EventArgs e)
    
    {
      this.Hide();
    }
	}
}
