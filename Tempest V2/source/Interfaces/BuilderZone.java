import java.io.*;
import java.util.*;

public class BuilderZone extends MudInterface

{
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static final int MAIN_MENU          = 0;
  public static final int SET_NAME           = 1;
  public static final int NEW_ROOM           = 2;
  public static final int NEW_MOB            = 3;
  public static final int NEW_ITEM           = 4;
  public static final int SET_COLOR          = 5;
  public static final int SET_TERRAIN        = 6;
  public static final int SET_REPOP          = 7;
  public static final int SET_ZONE_OWNER     = 8;
  public static final int DELETE_ZONE        = 9;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private Zone ZONE;
  private int state;
  private int integerHolder;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public int viewMode()                { return NO_OUTPUT;    }
  public void echo(String s)           { utilityQueue.add(s); }
  public void addOutput(String s)      { return;              }
  public void addFightOutput(String s) { return;              }
  public void focusGained()            {                      }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public BuilderZone(Client c)

  {
    super(c);
    state = MAIN_MENU;
    c.setClientState(CSTATE_BUILDING);
    ZONE = c.getCharInfo().getZone();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void flushUtilityQueue()

  {
    output = "";

    while (!utilityQueue.isEmpty())
      output += utilityQueue.removeFirst().toString();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleOutput()

  {
    if (!active) return;

    flushUtilityQueue();
    myClient.msg(output + getPrompt());
    menuMsg = "";
    active = false;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getPrompt()

  {
    if (state == MAIN_MENU)                 return getZoneMenu();
    else if (state == SET_NAME)             return "Enter New Zone Name: ";
    else if (state == NEW_ROOM)             return getPavePrompt();
    else if (state == NEW_ITEM)             return "Enter New Item Name: ";
    else if (state == NEW_MOB)              return "Enter New Mob Name: ";
    else if (state == SET_COLOR)            return "Enter New Color: ";
    else if (state == SET_TERRAIN)          return getTerrainMenu();
    else if (state == SET_REPOP)            return "Enter New Repop Rate(0-100): ";
    else if (state == SET_ZONE_OWNER)       return "Enter owner name: ";
    else if (state == DELETE_ZONE)          return "Type 'delete' if you are sure: ";

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleInput()

  {
    if (!myClient.commandWaiting()) return;

    String input = clearWhiteSpace(myClient.getCommand());

    if (state == MAIN_MENU)                 processZoneMenu(input);
    else if (state == SET_NAME)             processZoneRename(input);
    else if (state == NEW_ROOM)             processNewRoom(input);
    else if (state == NEW_MOB)              processNewMob(input);
    else if (state == NEW_ITEM)             processNewItem(input);
    else if (state == SET_COLOR)            processZoneColor(input);
    else if (state == SET_TERRAIN)          processTerrain(input);
    else if (state == SET_REPOP)            processRepopRate(input);
    else if (state == SET_ZONE_OWNER)       processZoneOwner(input);
    else if (state == DELETE_ZONE)          processZoneDelete(input);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void onDisconnect()

  {
    myClient.popInterface();
    ZONE.save();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static void enterZoneBuilder(Client myClient)

  {
    Entity E = myClient.getCharInfo();
    Zone Z = E.getZone();

    if (E.getPlayerState() == PSTATE_FIGHTING) {
      myClient.getCharInfo().echo("You can't do that while fighting.");
      return; }

    if (Z.getBusy()) {
      E.echo(Z.getName() + " is busy.");
      return; }

    if ((E.getLevel() < 110) && (!E.getName().equalsIgnoreCase(Z.getOwner()))) {
      E.echo("You are not the owner of " + Z.getName() + "#N.");
      return; }

    Z.setBusy(true);
    myClient.pushInterface(new BuilderZone(myClient));
    myClient.getCharInfo().blindEmote("starts building the zone.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void exit()

  {
    myClient.popInterface();
    myClient.setClientState(CSTATE_NORMAL);
    myClient.getCharInfo().blindEmote("has finished building the zone.");
    myClient.getCharInfo().look(ZONE.getName() + " saved.");
    ZONE.save();
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void processZoneMenu(String input)

  {
    if (input.length() <= 0)               { exit();                  }
    else if (input.equals("1"))            { state = SET_NAME;        }
    else if (input.equals("2"))            { state = NEW_ROOM;        }
    else if (input.equals("3"))            { state = NEW_MOB;         }
    else if (input.equals("4"))            { state = NEW_ITEM;        }
    else if (input.equals("5"))            { state = SET_COLOR;       }
    else if (input.equals("6"))            { state = SET_TERRAIN;     }
    else if (input.equals("7"))            { state = SET_REPOP;       }
    else if (input.equals("8"))            { viewMobPopulation();     }
    else if (input.equals("9"))            { viewItemPopulation();    }
    else if (input.equalsIgnoreCase("A"))  { listAllRooms();          }
    else if (input.equalsIgnoreCase("B"))  { listAllMobs();           }
    else if (input.equalsIgnoreCase("C"))  { listAllItems();          }
    else if (input.equalsIgnoreCase("H"))  {                          }
    else if (input.equalsIgnoreCase("X"))  { state = DELETE_ZONE;     }
    else if (input.equalsIgnoreCase("Q"))  { exit();                  }

    else if (input.equalsIgnoreCase("O"))

    {
      if (myClient.getCharInfo().getLevel() < 110) {
        errorMsg("Only immortals of level 110 can set the zone owner.");
        return; }

      state = SET_ZONE_OWNER;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processZoneRename(String input)

  {
    if (input.length() == 0) {
      errorMsg("Zone renaming cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > ZONE_NAME_LENGTH) {
      errorMsg("Name exceeds the maximum length.");
      state = MAIN_MENU;
      return; }

    ZONE.setName(input);
    goodMsg("Zone name set to: " + input + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processNewRoom(String input)

  {
    if (input.length() == 0) {
      errorMsg("Room creation cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > ROOM_TITLE_LENGTH) {
      errorMsg("Name exceeds the maximum length.");
      state = MAIN_MENU;
      return; }

    Room r = World.getRoom(World.createRoom(ZONE));
    r.setTitle(input);
    r.setTerrain(ZONE.getDefaultTerrain());
    goodMsg("Room created with ID: " + r.getID() + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processNewMob(String input)

  {
    if (input.length() == 0) {
      errorMsg("Mob creation cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > MAX_MOB_NAME_LENGTH) {
      errorMsg("Name exceeds the maximum length.");
      state = MAIN_MENU;
      return; }

    Mobile M = World.getMob(World.createMobile(ZONE));
    M.setName(input);
    goodMsg("Mobile created with ID: " + M.getID() + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processNewItem(String input)

  {
    if (input.length() == 0) {
      errorMsg("Item creation cancelled.");
      state = MAIN_MENU;
      return; }

    if (visibleSize(input) > ITEM_NAME_LENGTH) {
      errorMsg("Name exceeds the maximum length.");
      state = MAIN_MENU;
      return; }

    Item i = World.getItem(World.createItem(ZONE));
    i.setName(input);
    goodMsg("Item created with ID: " + i.getID() + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processZoneColor(String input)

  {
    if (input.length() == 0) {
      errorMsg("Color selection cancelled.");
      state = MAIN_MENU;
      return; }

    String colorName = colorToName(input);

    if (colorName.equals("Invalid_Color")) {
      errorMsg("Invalid Color.");
      return; }

    else ZONE.setColor(input);

    goodMsg("Zone color set to: " + colorName + "#g.");
    state = MAIN_MENU;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processTerrain(String input)

  {
    if (input.length() == 0) {
      state = MAIN_MENU;
      return; }

    try

    {
      int selection = Integer.parseInt(input);

      switch (selection)

      {
        case 1:  ZONE.setDefaultTerrain(Room.TERRAIN_FLAT_NORMAL); break;
        case 2:  ZONE.setDefaultTerrain(Room.TERRAIN_FLAT_ICY); break;
        case 3:  ZONE.setDefaultTerrain(Room.TERRAIN_FLAT_MUDDY); break;
        case 4:  ZONE.setDefaultTerrain(Room.TERRAIN_FLAT_ROCKY); break;
        case 5:  ZONE.setDefaultTerrain(Room.TERRAIN_ROAD_DIRT); break;
        case 6:  ZONE.setDefaultTerrain(Room.TERRAIN_ROAD_STONE); break;
        case 7:  ZONE.setDefaultTerrain(Room.TERRAIN_MOUNTAIN_LEVEL); break;
        case 8:  ZONE.setDefaultTerrain(Room.TERRAIN_MOUNTAIN_HILLS); break;
        case 9:  ZONE.setDefaultTerrain(Room.TERRAIN_MOUNTAIN_ASCENDING); break;
        case 10: ZONE.setDefaultTerrain(Room.TERRAIN_MOUNTAIN_STEEP); break;
        case 11: ZONE.setDefaultTerrain(Room.TERRAIN_FOREST_BRUSH); break;
        case 12: ZONE.setDefaultTerrain(Room.TERRAIN_FOREST_SPARSE); break;
        case 13: ZONE.setDefaultTerrain(Room.TERRAIN_FOREST_DENSE); break;
        case 14: ZONE.setDefaultTerrain(Room.TERRAIN_SWAMP_LIGHT); break;
        case 15: ZONE.setDefaultTerrain(Room.TERRAIN_SWAMP_BOGGY); break;
        case 16: ZONE.setDefaultTerrain(Room.TERRAIN_WATER_SHALLOW); break;
        case 17: ZONE.setDefaultTerrain(Room.TERRAIN_WATER_DEEP); break;
        case 18: ZONE.setDefaultTerrain(Room.TERRAIN_WATER_SEA); break;
        case 19: ZONE.setDefaultTerrain(Room.TERRAIN_SPECIAL_AIRBORNE); break;
        case 20: ZONE.setDefaultTerrain(Room.TERRAIN_SPECIAL_SHIMMY); break;
        case 21: ZONE.setDefaultTerrain(Room.TERRAIN_SPECIAL_REPEL); break;
        case 22: ZONE.setDefaultTerrain(Room.TERRAIN_SPECIAL_CRAWL); break;
        case 23: state = MAIN_MENU; return;
        default: throw new Exception();
      }
    }

    catch (Exception e) { errorMsg("Invalid terrain type."); return; }

    goodMsg("Terrain set.");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processRepopRate(String input)

  {
    if (input.length() == 0) {
      errorMsg("Repop rate setting cancelled.");
      state = MAIN_MENU;
      return; }

    int rate = 0;

    try

    {
      rate = Integer.parseInt(input);
      if ((rate < 0) || (rate > 100))
        throw new Exception();

      ZONE.setRate(rate);
      goodMsg("Repop rate set to: " + rate + "#g.");
      state = MAIN_MENU;
    }

    catch (Exception e) { errorMsg("Invalid repop rate.  Must be between 0 and 100."); }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processZoneOwner(String input)

  {
    if (input.length() == 0) {
      errorMsg("Zone owner setting cancelled.");
      state = MAIN_MENU;
      return; }

    ZONE.setOwner(properName(input));
    state = MAIN_MENU;
    goodMsg("Zone owner set to: " + properName(input) + ".");
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void processZoneDelete(String input)

  {
    if (input.equalsIgnoreCase("delete"))

    {
      World.globalZoneDelete(ZONE.getID());
      World.save();
      myClient.popInterface();
      myClient.setClientState(CSTATE_NORMAL);
      myClient.getCharInfo().look("#r" + ZONE.getName() + " #rhas been deleted.#N");
    }

    else

    {
      errorMsg("Deletion cancelled.");
      state = MAIN_MENU;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getZoneMenu()

  {
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("                        #gZone Editor#N                        ");
    menu.add("-");
    menu.add("#NZone#R:#N     " + ZONE.getName());
    menu.add("#NZoneID#R:#N   " + ZONE.getID());
    menu.add("#NOwner#R:#N    " + ZONE.getOwner());
    menu.add("#NRooms#R:#N    " + ZONE.getNumRooms());
    menu.add("#NMobiles#R:#N  " + ZONE.getNumMobs());
    menu.add("#NItems#R:#N    " + ZONE.getNumItems());
    menu.add("#NColor#R:#N    " + colorToName(ZONE.getColor()));
    menu.add("#NTerrain#R:#N  " + terrainString(ZONE.getDefaultTerrain()));
    menu.add("#NRepop#R:#N    " + ZONE.getRepopRate());
    menu.add("-");
    menu.add("#g1.#N) Rename Zone.                    #cA.#N) List All Rooms.");
    menu.add("#g2.#N) Create New Room.                #cB.#N) List All Mobiles.");
    menu.add("#g3.#N) Create New Mobile Type.         #cC.#N) List All Items.");
    menu.add("#g4.#N) Create New Item Type.           #cO.#N) Set Owner.");
    menu.add("#g5.#N) Modify Zone Default Color.");
    menu.add("#g6.#N) Modify Zone Default Terrain.");
    menu.add("#g7.#N) Modify Zone Repop Rate.         #yH.#N) Help!");
    menu.add("#g8.#N) View Mobile Population.         #bX.#N) Delete Zone.");
    menu.add("#N8.#N) View Item Population.           #rQ.#N) Save and Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void listAllRooms()

  {
    int size = ZONE.getNumRooms();

    String str = "#nRoom List:#N";

    for (int i=0; i<size; i++)

    {
      int rID = ZONE.getRoomObj(i).getID();
      String rTitle = ZONE.getRoomObj(i).getTitle();
      str += "\r\n#N[#c" + rID + "#N] " + tenSpace(rID, 5) + rTitle;
    }

    PageBreak PB = new PageBreak(myClient, str);
    PB.setHalting(true);
    myClient.pushInterface(PB);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void listAllItems()

  {
    int size = ZONE.getNumItems();

    String str = "#nItem List:#N";

    for (int i=0; i<size; i++)

    {
      int iID = ZONE.getItemObj(i).getID();
      String iTitle = ZONE.getItemObj(i).getName();
      str += "\r\n#N[#c" + iID + "#N] " + tenSpace(iID, 5) + iTitle;
    }

    PageBreak PB = new PageBreak(myClient, str);
    PB.setHalting(true);
    myClient.pushInterface(PB);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void listAllMobs()

  {
    int size = ZONE.getNumMobs();
    String str = "#nMobile List:#N";

    for (int i=0; i<size; i++)
    if (ZONE.getMobObj(i) != null)

    {
      int mID = ZONE.getMobObj(i).getID();
      String mTitle = ZONE.getMobObj(i).getName();
      str += "\r\n#N[#c" + mID + "#N] " + tenSpace(mID, 5) + mTitle;
    }

    PageBreak PB = new PageBreak(myClient, str);
    PB.setHalting(true);
    myClient.pushInterface(PB);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void viewMobPopulation()

  {
    ArrayList list = new ArrayList();

    list.add("center");
    list.add("#MMobile Population#N");
    list.add("-");

    int size = 0;

    for (int i=0; i<ZONE.getNumRooms(); i++)
      size += ZONE.getRoomObj(i).getMobPopSize();

    if (size == 0) list.add("There are no mobs populating this zone.");
    else list.add(" #n[ #RID #n]  [ #RROOM #n]  [ #RRATE#n ]  [ #RMOBILE#n ]#N");

    if (size > 0) list.add("-");

    for (int i=0; i<ZONE.getNumRooms(); i++)

    {
      Room ROOM = ZONE.getRoomObj(i);

      for (int j=0; j<ROOM.getMobPopSize(); j++)

      {
        String temp = "";
        PopMember PM = ROOM.getMobPop(j);
        int firstMob = PM.getIDNum(0);
        double frq = PM.getTotalFrequency();
        Mobile M = World.getMob(firstMob);

        temp += " #n[#c" + tenSpace((j+1),3) + (j+1) + "#n]#N ";
        temp += "#r" + String.format("%1$6s    ", ROOM.getID()) + "#N";
        temp += "#n(#g" + String.format("%1$5.5s", frq) + "#n)   ";

        if (!PM.isRandom())
          temp += "#n(#r" + firstMob + "#n)#N " + M.getName();
        else temp += "#RRandom#N";

        list.add(temp);
      }
    }

    String str = Ansi.clearScreen + "\r\n" + boxify(list,0);

    PageBreak PB = new PageBreak(myClient, str);
    PB.setHalting(true);
    myClient.pushInterface(PB);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private void viewItemPopulation()

  {

  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getTerrainMenu()

  {
    int terrain = ZONE.getDefaultTerrain();
    ArrayList menu = new ArrayList();

    menu.add("center");
    menu.add("#nTerrain Type#N");
    menu.add("-");
    menu.add("Current: #n" + terrainString(terrain));
    menu.add("-");
    menu.add("#n[#c 1#n]#N - Flat Normal");
    menu.add("#n[#c 2#n]#N - Flat Icy");
    menu.add("#n[#c 3#n]#N - Flat Muddy");
    menu.add("#n[#c 4#n]#N - Flat Rocky");
    menu.add("#n[#c 5#n]#N - Road Dirt");
    menu.add("#n[#c 6#n]#N - Road Stone");
    menu.add("#n[#c 7#n]#N - Mountain Level");
    menu.add("#n[#c 8#n]#N - Mountain Hills");
    menu.add("#n[#c 9#n]#N - Mountain Ascending");
    menu.add("#n[#c10#n]#N - Mountain Steep");
    menu.add("#n[#c11#n]#N - Forest Brush");
    menu.add("#n[#c12#n]#N - Forest Sparse");
    menu.add("#n[#c13#n]#N - Forest Dense");
    menu.add("#n[#c14#n]#N - Swamp Light");
    menu.add("#n[#c15#n]#N - Swamp Boggy");
    menu.add("#n[#c16#n]#N - Water Shallow");
    menu.add("#n[#c17#n]#N - Water Deep");
    menu.add("#n[#c18#n]#N - Water Sea");
    menu.add("#n[#c19#n]#N - Special Airborne");
    menu.add("#n[#c20#n]#N - Special Shimmy");
    menu.add("#n[#c21#n]#N - Special Repel");
    menu.add("#n[#c22#n]#N - Special Crawl");
    menu.add("#n[#c23#n]#N - Exit");

    return Ansi.clearScreen + "\r\n" + menuMsg + "#N\r\n\n" + boxify(menu,0) + "\r\n\nSelect: ";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String terrainString(int terrain)

  {
    if (terrain == Room.TERRAIN_FLAT_NORMAL       ) return "Flat Normal";
    if (terrain == Room.TERRAIN_FLAT_ICY          ) return "Flat Icy";
    if (terrain == Room.TERRAIN_FLAT_MUDDY        ) return "Flat Muddy";
    if (terrain == Room.TERRAIN_FLAT_ROCKY        ) return "Flat Rocky";
    if (terrain == Room.TERRAIN_ROAD_DIRT         ) return "Road Dirt";
    if (terrain == Room.TERRAIN_ROAD_STONE        ) return "Road Stone";
    if (terrain == Room.TERRAIN_MOUNTAIN_LEVEL    ) return "Mountain Level";
    if (terrain == Room.TERRAIN_MOUNTAIN_HILLS    ) return "Mountain Hills";
    if (terrain == Room.TERRAIN_MOUNTAIN_ASCENDING) return "Mountain Ascending";
    if (terrain == Room.TERRAIN_MOUNTAIN_STEEP    ) return "Mountain Steep";
    if (terrain == Room.TERRAIN_FOREST_BRUSH      ) return "Forest Brush";
    if (terrain == Room.TERRAIN_FOREST_SPARSE     ) return "Forest Sparse";
    if (terrain == Room.TERRAIN_FOREST_DENSE      ) return "Forest Dense";
    if (terrain == Room.TERRAIN_SWAMP_LIGHT       ) return "Swamp Light";
    if (terrain == Room.TERRAIN_SWAMP_BOGGY       ) return "Swamp Boggy";
    if (terrain == Room.TERRAIN_WATER_SHALLOW     ) return "Water Shallow";
    if (terrain == Room.TERRAIN_WATER_DEEP        ) return "Water Deep";
    if (terrain == Room.TERRAIN_WATER_SEA         ) return "Water Sea";
    if (terrain == Room.TERRAIN_SPECIAL_AIRBORNE  ) return "Special Airborne";
    if (terrain == Room.TERRAIN_SPECIAL_SHIMMY    ) return "Special Shimmy";
    if (terrain == Room.TERRAIN_SPECIAL_REPEL     ) return "Special Repel";
    if (terrain == Room.TERRAIN_SPECIAL_CRAWL     ) return "Special Crawl";

    return "";
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String getPavePrompt()

  {
    String str = "";

    str += "\r\n#nNote#R: #NYou can also use the #n'#Rpave#n'#N command to make new rooms";
    str += "\r\ninstead of creating them manually in the Zone Editor.\r\n\n";
    str += "Enter New Room Name: ";

    return str;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}