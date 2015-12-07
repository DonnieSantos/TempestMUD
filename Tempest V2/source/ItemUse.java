import java.io.*;
import java.util.*;

public class ItemUse extends Utility

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void addItemBack(ArrayList itemList, Item iItem)

  {
    boolean inList = false;
    int size = itemList.size();
    int pos = 0;

    for (int i=0; i<size; i++)
      if (((Item)itemList.get(i)).getName().equals(iItem.getName())) {
        inList = true; pos = i; }

    if (!inList) itemList.add(size, iItem);
    else itemList.add(pos, iItem);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void put(Entity ENT, String input)

  {
    String cName = getLastWord(input);
    int targetNum = clipNumber(cName);
    cName = clipString(cName);
    input = clipLastWord(input);

    ArrayList clist = new ArrayList(ENT.getInventory().getList());
    clist.addAll(ENT.getRoom().getItemList().getList());
    clist = restrictItemList(clist, cName);

    if (targetNum > clist.size()) {
      ENT.echo("You don't see that container.");
      return; }

    Item C = (Item)clist.get(targetNum-1);
    ArrayList ilist = parseListQuery(ENT.getInventory().getList(), input);
    if (ilist.contains(C)) ilist.remove(ilist.indexOf(C));

    if (ilist.isEmpty()) {
      ENT.echo("You don't have that item.");
      return; }

    ENT.getRoom().stackingOn();

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item)ilist.get(i);
      int error = Transfer.item(I, ENT, C, IPUT);

      switch (error)

      {
        case ER_INOTCONT: ENT.echo("You can't put items inside " + C.getLName() + ".");
                          ENT.getRoom().stackingOff();
                          return;
        case ER_IFULL:    ENT.echo(properName(I.getName()) + " is full."); break;
        case ER_INODROP:  ENT.echo("You can't put " + I.getLName() + " into " + C.getLName() + "."); break;
        case ER_ICONT:    ENT.echo("You can't put " + I.getLName() + " into " + C.getLName() + "."); break;
        case ALL_OK:      ENT.echo("You put " + I.getLName() + " into " + C.getLName() + ".");
                          ENT.sendToAwake(ENT.getName() + " puts " + I.getLName() + " into " + C.getLName() + ".");
      }
    }

    ENT.getRoom().stackingOff();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void sacrifice(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to sacrifice?");
      return; }

    if (input.equals("all")) {
      ENT.echo("You can't sacrifice all items.");
      return; }

    ArrayList ilist;

    if (input.startsWith("all."))

    {
      input = input.replaceFirst("all.", "");
      if (!restrictItemList(ENT.getInventory().getList(), input).isEmpty())
        ilist = new ArrayList(ENT.getInventory().getList());
      else ilist = new ArrayList(ENT.getRoom().getItemList().getList());
      ilist = parseListQuery(ilist, "all."+input);
    }

    else

    {
      ilist = new ArrayList(ENT.getInventory().getList());
      ilist.addAll(ENT.getRoom().getItemList().getList());
      ilist = parseListQuery(ilist, input);
    }

    if (ilist.isEmpty()) {
      ENT.echo("You don't see that item anywhere.");
      return; }

    ENT.getRoom().stackingOn();

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item)ilist.get(i);
      int error = Destroy.item(I, ENT, ISAC);

      switch (error)

      {
        case ER_ILEVEL:        ENT.echo("You're not powerful enough to sacrifice " + I.getLName() + "."); break;
        case ER_IPLAYERCORPSE: ENT.echo("You can't sacrifice " + I.getLName() + ".");                     break;
        case ER_INOSAC:        ENT.echo("You can't sacrifice " + I.getLName() + ".");                     break;
        case ALL_OK:           ENT.echo("You sacrifice " + I.getLName() + " to Genevieve.");
                               ENT.sendToAwake(ENT.getName() + " sacrifices " + I.getLName() + " to Genevieve.");
      }
    }

    ENT.getRoom().stackingOff();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void give(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("Give what to who?");
      return; }

    String eName = getLastWord(input);
    input = clipLastWord(input);
    Entity TARG = ENT.getRoom().findEntity(ENT, eName);

    if (TARG == null) {
      ENT.echo("You don't see that person.");
      return; }

    ArrayList ilist = new ArrayList();

    if (!isGoldString(input))

    {
      ilist = new ArrayList(parseListQuery(ENT.getInventory().getList(), input));

      if (ilist.size()== 0) {
        ENT.echo("You do not have that item.");
        return; }
    }

    else ilist.add(createGoldPile(ENT, Integer.parseInt(first(input))));

    ENT.getRoom().stackingOn();

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item)ilist.get(i);
      int error = Transfer.item(I, ENT, TARG, IGIVE);

      switch (error)

      {
        case ER_INOTENOUGHGOLD: ENT.echo("You don't have that much gold.");       break;
        case ER_IZEROGOLD:      ENT.echo("What would be the point of that?");     break;
        case ER_ILEVEL:         ENT.echo(TARG.getName() + " is not powerful enough to hold " + I.getLName() + "."); break;
        case ER_EINVFULL:       ENT.echo(TARG.getName() + " can't hold anymore items.");
                                ENT.getRoom().stackingOff();
                                return;
        case ER_ENOGET:         ENT.echo(TARG.getName() + " can't be given items right now."); break;
        case ER_INODROP:        ENT.echo("You can't give " + I.getLName() + " to " + TARG.getName() + "."); break;
        case ALL_OK:            ENT.echo("You give " + I.getLName() + " to " + TARG.getName() + ".");
                                TARG.echo(ENT.getName() + " gives you " + I.getLName() + ".");
                                ENT.getRoom().xecho(ENT, TARG, ENT.getName() + " gives " + TARG.getName() + " " + I.getLName() + ".", ECHO_AWAKE);
      }
    }

    ENT.getRoom().stackingOff();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void drop(Entity ENT, String input)

  {
    ArrayList ilist = new ArrayList();

    if (!isGoldString(input))

    {
      ilist = parseListQuery(ENT.getInventory().getList(), input);

      if (ilist.size() == 0) {
        ENT.echo("You do not have that item.");
        return; }
    }

    else ilist.add(createGoldPile(ENT, Integer.parseInt(first(input))));

    ENT.getRoom().stackingOn();

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item)ilist.get(i);
      int error = Transfer.item(I, ENT, ENT.getRoom(), IDROP);

      switch (error)

      {
        case ER_INOTENOUGHGOLD: ENT.echo("You don't have that much gold.");       break;
        case ER_IZEROGOLD:      ENT.echo("What would be the point of that?");     break;
        case ER_INODROP:        ENT.echo("You can't drop " + I.getLName() + "."); break;
        case ER_RNODROP:        ENT.echo("You can't drop items here.");
                                ENT.getRoom().stackingOff();
                                return;
        case ALL_OK:            ENT.echo("You drop " + I.getLName() + ".");
                                ENT.sendToAwake(ENT.getName() + " drops " + I.getLName() + ".");
      }
    }

    ENT.getRoom().stackingOff();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void getItemRoom(Entity ENT, String input)

  {
    ArrayList ilist = parseListQuery(ENT.getRoom().getItemList().getList(), input);

    if (ilist.isEmpty()) {
      ENT.echo("You can't find that item.");
      return; }

    ENT.getRoom().stackingOn();

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item)ilist.get(i);
      int error = Transfer.item(I, ENT.getRoom(), ENT, IGETR);

      switch (error)

      {
        case ER_INOGET:     ENT.echo("You can't get " + I.getLName() + "."); break;
        case ER_RNOGET:     ENT.echo("You can't get " + I.getLName() + "."); break;
        case ER_ENOGET:     ENT.echo("You can't get items."); break;
        case ER_EINVFULL:   ENT.echo("You can't hold anymore items."); break;
        case ER_ILEVEL:     ENT.echo("You aren't powerful enough to get " + I.getLName() + "."); break;
        case ER_ICONTLEVEL: ENT.echo("You aren't powerful enough to get " + I.getLName() + "."); break;
        case ER_IUNTOUCH:   ENT.echo("You can't get " + I.getLName() + "."); break;
        case ALL_OK:        ENT.echo("You get " + I.getLName() + ".");
                            ENT.sendToAwake(ENT.getName() + " gets " + I.getLName() + ".");
      }
    }

    ENT.getRoom().stackingOff();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void getItemAllContainers(Entity ENT, String input)

  {
    String cName = getLastWord(input).replaceFirst("all.", "");
    input = clipLastWord(input);

    ArrayList clist = new ArrayList(ENT.getInventory().getList());
    clist.addAll(ENT.getRoom().getItemList().getList());
    clist = restrictItemList(clist, cName);

    for (int i=clist.size()-1; i>=0; i--)
    if (!((Item)clist.get(i)).isContainer())
      clist.remove(i);

    if (clist.isEmpty()) {
      ENT.echo("You didn't find any items with that name.");
      return; }

    boolean isOutput = false;

    ENT.getRoom().stackingOn();

    for (int i=0; i<clist.size(); i++)

    {
      Item C = (Item)clist.get(i);
      ArrayList ilist = parseListQuery(C.getItemList().getList(), input);

      for (int j=0; j<ilist.size(); j++)

      {
        Item I = (Item)ilist.get(j);
        int error = Transfer.item(I, C, ENT, IGETC);

        switch (error)

        {
          case ER_EINVFULL:   ENT.echo("You can't hold anymore items.");
                              ENT.getRoom().stackingOff();
                              return;
          case ER_ILEVEL:     ENT.echo("You aren't powerful enough to get " + I.getLName() + ".");
                              isOutput = true;
                              break;
          case ALL_OK:        ENT.echo("You get " + I.getLName() + " from " + C.getLName() + ".");
                              ENT.sendToAwake(ENT.getName() + " gets " + I.getLName() + " from " + C.getLName() + ".");
                              isOutput = true;
        }
      }
    }

    ENT.getRoom().stackingOff();

    if (!isOutput) ENT.echo("You did not find anything in those containers.");
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void get(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to get?");
      return; }

    String lastWord = getLastWord(input);

    if (parseListQuery(ENT.getRoom().getItemList().getList(), input).size() > 0) {
      getItemRoom(ENT, input);
      return; }

    if (lastWord.indexOf("all.") == 0) {
      getItemAllContainers(ENT, input);
      return; }

    int targetNum = clipNumber(lastWord);
    input = clipLastWord(input);
    lastWord = clipString(lastWord);
    ArrayList clist = new ArrayList(ENT.getInventory().getList());
    clist.addAll(ENT.getRoom().getItemList().getList());
    clist = restrictItemList(clist, lastWord);

    if (targetNum > clist.size()) {
      ENT.echo("You don't see that item anywhere.");
      return; }

    Item C = (Item)clist.get(targetNum-1);

    if (!C.isContainer()) {
      ENT.echo("There is nothing inside " + C.getLName() + ".");
      return; }

    ArrayList ilist = parseListQuery(C.getItemList().getList(), input);

    if (ilist.isEmpty()) {
      ENT.echo("That item is not inside " + C.getLName() + ".");
      return; }

    ENT.getRoom().stackingOn();

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item)ilist.get(i);
      int error = Transfer.item(I, C, ENT, IGETC);

      switch (error)

      {
        case ER_EINVFULL:   ENT.echo("You can't hold anymore items.");
                            ENT.getRoom().stackingOff();
                            return;
        case ER_ILEVEL:     ENT.echo("You aren't powerful enough to get " + I.getLName() + "."); break;
        case ALL_OK:        ENT.echo("You get " + I.getLName() + " from " + C.getLName() + ".");
                            ENT.sendToAwake(ENT.getName() + " gets " + I.getLName() + " from " + C.getLName() + ".");
      }
    }

    ENT.getRoom().stackingOff();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList parseListQuery(ArrayList ilist, String searchName)

  {
    ArrayList newList;

    if (searchName.equals("all")) return new ArrayList(ilist);

    if (searchName.indexOf("all.") == 0) {
      newList = restrictItemList(ilist, searchName.replaceFirst("all.", ""));
      if (newList.isEmpty()) return new ArrayList();
      else return newList; }

    int targetNum = clipNumber(searchName);
    searchName = clipString(searchName);
    newList = restrictItemList(ilist, searchName);

    if (newList.isEmpty()) return newList;
    if (targetNum > newList.size()) return new ArrayList();

    ArrayList iList = new ArrayList();
    iList.add(newList.get(targetNum-1));

    return iList;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList restrictItemList(ArrayList ilist, String searchName)

  {
    int size = ilist.size();
    ArrayList newList = new ArrayList();

    for (int i=0; i<size; i++)
    if (abbreviation(searchName, removeColors(((Item)ilist.get(i)).getName())))
      newList.add(ilist.get(i));

    return newList;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void displayInventory(Entity ENT)

  {
    ENT.echo(ENT.getInventory().getDisplay());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void displayItemListContainer(Entity ENT, String input)

  {
    if (input.equals("")) {
      ENT.echo("What do you want to look in?");
      return; }

    int targetNum = clipNumber(input);
    input = clipString(input);

    ArrayList clist = new ArrayList(ENT.getInventory().getList());
    clist.addAll(ENT.getRoom().getItemList().getList());
    clist = restrictItemList(clist, input);

    if (targetNum > clist.size()) {
      ENT.echo("You don't see that anywhere.");
      return; }

    Item container = (Item)clist.get(targetNum-1);

    if ((container.isDrinkCont()) || (container.isFountain())) {
      ENT.echo(Liquids.getAmount(container));
      return; }

    if (!container.isContainer()) {
      ENT.echo(container.getName() + " is not a container.");
      return; }

    ENT.echo(container.getItemList().getDisplay());
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static boolean findItemLook(Entity ENT, String input)

  {
    int targetNum = clipNumber(input);
    input = clipString(input);

    ArrayList ilist = new ArrayList(ENT.getInventory().getList());
    ilist.addAll(ENT.getRoom().getItemList().getList());
    ilist = restrictItemList(ilist, input);

    if (targetNum > ilist.size()) return false;
    Item item = (Item)ilist.get(targetNum-1);
    ENT.echo(item.getLDesc());

    return true;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Item makeCorpse(Entity e)

  {
    Item corpse = new Item();

    corpse.addType(Item.ITEM_CONTAINER);
    corpse.setName(possessive(e.getName()) + " corpse");
    corpse.setLName(possessive(e.getName()) + " corpse");
    corpse.setGDesc("#RThe bloody corpse of " + e.getName() + " lies here.#N");
    corpse.setMaxItems(100);
    corpse.toggleFlag("NO_GET");
    corpse.toggleFlag("INV_DECAY");

    if (e.isPlayer())

    {
      corpse.startDecay(3000);
      corpse.toggleFlag("NO_SACRIFICE");
      corpse.addType(Item.ITEM_CORPSE_PLAYER);
    }

    else

    {
      corpse.addType(Item.ITEM_CORPSE);
      corpse.startDecay(1);
      if (e.castMob().getFlag(Mobile.FLAG_NPC)) return corpse;
    }

    ArrayList newList = e.getAllTopItems();

    for (int i=0; i<newList.size(); i++)
      Transfer.item((Item)newList.get(i), e, corpse, IENTERCORPSE);

    return corpse;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void updateDecayItemList(ArrayList ilist)

  {
    ArrayList copyList = new ArrayList(ilist);
    int size = copyList.size();

    for (int i=0; i<size; i++)

    {
      Item ITEM = (Item)copyList.get(i);

      if ((ITEM != null) && (ITEM.getNumItems() > 0))

      {
        updateDecayItemList(ITEM.getItemList().getList());
        ITEM.updateDecay();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Item createGoldPile(Entity ENT, int amount)

  {
    Item goldPile = new Item();

    goldPile.addType(Item.ITEM_GOLD);
    goldPile.setTypeStamp("Dropped by " + ENT.getName() + ".");
    goldPile.setTimeStamp(SystemTime.getItemTimeStamp());
    goldPile.setWorth(amount);
    goldPile.setName("A Pile of Gold");
    goldPile.setLName("a pile of gold");
    goldPile.setGDesc("A small pile of gold has been left here.");
    goldPile.setLDesc("A small pile of coins.");

    return goldPile;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void use(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to use?");
      return; }

    String targName = getLastWord(input);
    input = clipLastWord(input);

    if (input.length() == 0) {
      input = targName;
      targName = ""; }

    ArrayList eqlist = new ArrayList(Arrays.asList(ENT.getEquipment()));

    while (eqlist.contains(null))
      eqlist.remove(eqlist.indexOf(null));

    ArrayList elist = new ArrayList();

    for (int i=0; i<eqlist.size(); i++)
    if (!elist.contains(eqlist.get(i)))
      elist.add(eqlist.get(i));

    ArrayList ilist = new ArrayList(elist);
    ilist.addAll(ENT.getInventory().getList());
    ilist = parseListQuery(ilist, input);

    if (ilist.isEmpty() || ilist.size() > 1) {
      ENT.echo("You don't have that item.");
      return; }

    ((Item)ilist.get(0)).use(targName);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void quaff(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to quaff?");
      return; }

    ArrayList ilist = new ArrayList(ENT.getInventory().getList());
    ilist = parseListQuery(ilist, input);

    if (ilist.isEmpty() || ilist.size() > 1) {
      ENT.echo("You don't have that item.");
      return; }

    Item ITEM = (Item)ilist.get(0);

    if (!ITEM.isDrinkCont()) {
      ENT.echo("You can't drink from " + ITEM.getLName() + ".");
      return; }

    ITEM.drink();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void drink(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to drink?");
      return; }

    boolean inRoom = false;

    ArrayList ilist = new ArrayList(ENT.getInventory().getList());
    ilist = parseListQuery(ilist, input);

    if (ilist.size() > 1) {
      ENT.echo("You don't have that item.");
      return; }

    if (ilist.isEmpty())

    {
      ilist = parseListQuery(ENT.getRoom().getItemList().getList(), input);
      inRoom = true;

      if (ilist.isEmpty()) {
        ENT.echo("You don't have that item.");
        return; }
    }

    Item ITEM = (Item)ilist.get(0);

    if (inRoom && !ITEM.isFountain()) {
      ENT.echo("You don't have that item.");
      return; }

    if (ITEM.isFountain()) {
      Liquids.drink(ENT, ITEM.getLiquidType(), ITEM.getLName());
      return; }

    if (!ITEM.isDrinkCont()) {
      ENT.echo("You can't drink from " + ITEM.getLName() + ".");
      return; }

    ITEM.drink();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void fill(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to fill?");
      return; }

    String liquidPool = getLastWord(input);
    String container  = clipLastWord(input);
    int index = input.indexOf(" from ");

    if (index != -1) {
      liquidPool = input.substring(index+6, input.length()-1);
      container = input.substring(0, index); }
    else if (countWords(input) == 1) {
      container = liquidPool;
      liquidPool = ""; }

    ArrayList ilist = new ArrayList(ENT.getInventory().getList());
    ilist = parseListQuery(ilist, container);

    ArrayList rlist = new ArrayList(ENT.getRoom().getItemList().getList());
    rlist = parseListQuery(rlist, liquidPool);

    if (ilist.isEmpty() || ilist.size() > 1) {
      ENT.echo("You don't have that item.");
      return; }

    Item ITEM = (Item)ilist.get(0);

    if (liquidPool.length() == 0) {
      ENT.echo("What do you want to fill " + ITEM.getLName() + " with?");
      return; }

    if (rlist.isEmpty() || rlist.size() > 1) {
      ENT.echo("There is no " + liquidPool + " here.");
      return; }

    Item POOL = (Item)rlist.get(0);

    if (!ITEM.isDrinkCont()) {
      ENT.echo("You can't fill " + ITEM.getLName() + ".");
      return; }

    if (!POOL.isFountain()) {
      ENT.echo("You can't fill " + ITEM.getLName() + " from " + POOL.getLName() + ".");
      return; }

    ITEM.fill(POOL);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void empty(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to empty?");
      return; }

    ArrayList ilist = new ArrayList(ENT.getInventory().getList());
    ilist = parseListQuery(ilist, input);

    if (ilist.isEmpty() || ilist.size() > 1) {
      ENT.echo("You don't have that item.");
      return; }

    ((Item)ilist.get(0)).empty();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void recite(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to recite?");
      return; }

    String targName = getLastWord(input);
    input = clipLastWord(input);

    if (input.length() == 0) {
      input = targName;
      targName = ""; }

    ArrayList ilist = new ArrayList(ENT.getInventory().getList());
    ilist = parseListQuery(ilist, input);

    if (ilist.isEmpty() || ilist.size() > 1) {
      ENT.echo("You don't have that item.");
      return; }

    Item ITEM = (Item)ilist.get(0);

    if (!ITEM.isScroll()) {
      ENT.echo(properName(ITEM.getLName()) + " is not a scroll.");
      return; }

    ITEM.use(targName);
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void eat(Entity ENT, String input)

  {
    if (input.length() == 0) {
      ENT.echo("What do you want to eat?");
      return; }

    ArrayList ilist = new ArrayList(ENT.getInventory().getList());
    ilist = parseListQuery(ilist, input);

    if (ilist.isEmpty() || ilist.size() > 1) {
      ENT.echo("You don't have that item.");
      return; }

    Item I = (Item)ilist.get(0);
    int error = Destroy.item(I, ENT, IEAT);

    switch (error)

    {
      case ER_INOTFOOD: ENT.echo("You can't eat " + I.getLName() + "."); break;
      case ALL_OK:      ENT.echo("You eat " + I.getLName() + ".");
                        ENT.sendToAwake(ENT.getName() + " eats " + I.getLName() + ".");
                        I.castUseSpells();
                        I.destroy(IEAT);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList expandItemList(ArrayList ilist)

  {
    ArrayList newList = new ArrayList();

    for (int i=0; i<ilist.size(); i++)

    {
      Item I = (Item)ilist.get(i);

      newList.add(I);

      if (I.getNumItems() > 0)
        newList.addAll(I.getItemList().getList());
    }

    return newList;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}
