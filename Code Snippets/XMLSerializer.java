import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.lang.reflect.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class XMLSerializer

{
  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static DocumentBuilder DB;
  private static DocumentBuilderFactory DBF;
  private static TransformerFactory TF;
  private static Transformer T;
  private static HashTable seenObjects;
  private static int referenceIncrementor;
  private static Document Doc;

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void init()

  {
    try

    {
      System.out.print("Initializing XML Serializer... ");

      DBF = DocumentBuilderFactory.newInstance();
      DB = DBF.newDocumentBuilder();
      TF = TransformerFactory.newInstance();
      T = TF.newTransformer();
      seenObjects = new HashTable(101);
      referenceIncrementor = 0;
      Doc = null;

      System.out.println("Done.");
    }

    catch (Exception e)

    {
      System.out.println("error: " + e);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Document newDocument()

  {
    return DB.newDocument();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void write(String filename)

  {
    try

    {
      File F = new File(filename);
      DOMSource source = new DOMSource(Doc);
      StreamResult result = new StreamResult(F);
      T.transform(source, result);
    }

    catch (Exception e)

    {
      e.printStackTrace();
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void startXMLWrite(Object O, String filename)

  {
    try

    {
      Doc = XMLSerializer.newDocument();
      Doc.appendChild(createXMLEntry(O));

      write(filename);

      referenceIncrementor = 0;
      seenObjects.clear();
      Doc = null;
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  private static boolean primitive(Class C)

  {
    return C == Integer.class ||
           C == Boolean.class ||
           C == Byte.class ||
           C == Short.class ||
           C == Long.class ||
           C == Float.class ||
           C == Double.class ||
           C == String.class;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Element createXMLEntry(Object O) throws Exception

  {
    Class C = O.getClass();
    Field[] fields = getClassFields(C);
    //seenObjects.add(O, referenceIncrementor);
    Element root = Doc.createElement("TempestMudObjects");
    createXML(O, root, "");
    //Element root = createObjectXML(O, "");

    for (int i=0; i<fields.length; i++)

    {
      fields[i].setAccessible(true);
      createXML(fields[i].get(O), root, fields[i].getName());
    }

    return root;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static void createXML(Object O, Element root, String fieldName) throws Exception

  {
    if (O == null) {
      root.appendChild(createObjectXML(O, fieldName));
      return; }

    if (seenObjects.contains(O)) {
      root.appendChild(createObjectReference(O, fieldName));
      return; }

    Class C = O.getClass();

    if (primitive(C) && !C.isArray())
      root.appendChild(createPrimitiveXML(O, fieldName));

    else if (C.isArray())

    {
      seenObjects.add(O, referenceIncrementor);
      Element subtree = createArrayXML(O, fieldName);
      int size = Array.getLength(O);

      for (int j=0; j<size; j++)
        createXML(Array.get(O, j), subtree, "");

      root.appendChild(subtree);
    }

    else if (C == ArrayList.class)

    {
      seenObjects.add(O, referenceIncrementor);
      Element subtree = createObjectXML(O, fieldName);
      ArrayList A = (ArrayList)O;

      for (int j=0; j<A.size(); j++)
        createXML(A.get(j), subtree, "");

      root.appendChild(subtree);
    }

    else

    {
      seenObjects.add(O, referenceIncrementor);
      Element subtree = createObjectXML(O, fieldName);
      root.appendChild(subtree);

      Field[] fields = getClassFields(C);
      for (int i=0; i<fields.length; i++)

      {
        fields[i].setAccessible(true);
        createXML(fields[i].get(O), subtree, fields[i].getName());
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Field[] getClassFields(Class C) throws Exception

  {
    ArrayList list = getFields(C);
    Object[] listObjects = list.toArray();
    Field[] fields = new Field[listObjects.length];

    for (int i=0; i<listObjects.length; i++)
      fields[i] = (Field)listObjects[i];

    return fields;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Element createObjectReference(Object O, String fieldName)

  {
    Element E = Doc.createElement("OBJ");
    if (fieldName.length() > 0) E.setAttribute("id", fieldName);
    E.setAttribute("type", O.getClass().getCanonicalName());
    E.setAttribute("reference", seenObjects.getValue(O)+"");
    return E;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Element createArrayXML(Object O, String fieldName)

  {
    Element E = Doc.createElement("ARR");
    E.setAttribute("id", fieldName);
    E.setAttribute("size", Integer.toString(Array.getLength(O)));
    E.setAttribute("type", O.getClass().getCanonicalName());
    E.setAttribute("uniqueID", referenceIncrementor + "");
    referenceIncrementor++;
    return E;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Element createObjectXML(Object O, String fieldName)

  {
    Element E = Doc.createElement("OBJ");

    if (fieldName.length() > 0)
      E.setAttribute("id", fieldName);

    if (O != null)

    {
      E.setAttribute("type", O.getClass().getCanonicalName());
      E.setAttribute("uniqueID", referenceIncrementor + "");
      referenceIncrementor++;
    }

    else E.setAttribute("type", "null");

    return E;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Element createListXML(Object O, String fieldName)

  {
    Element E = Doc.createElement("OBJ");
    E.setAttribute("id", fieldName);
    E.setAttribute("type", O.getClass().getCanonicalName());
    E.setAttribute("uniqueID", referenceIncrementor + "");
    referenceIncrementor++;
    return E;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static Element createPrimitiveXML(Object O, String fieldName)

  {
    Element E = Doc.createElement("VAR");
    E.setAttribute("id", fieldName);
    E.setAttribute("type", O.getClass().getCanonicalName());
    E.setAttribute("value", O.toString());
    return E;
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////

  public static ArrayList getFields(Class C) throws Exception

  {
    if (C == Legend.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("list"));
      A.add(C.getDeclaredField("owner"));
      return A;
    }

    else if (C == LegendMark.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("mark"));
      A.add(C.getDeclaredField("date"));
      A.add(C.getDeclaredField("times"));
      return A;
    }

    else if (C == Zone.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("name"));
      A.add(C.getDeclaredField("zoneColor"));
      A.add(C.getDeclaredField("zoneOwner"));
      A.add(C.getDeclaredField("id"));
      A.add(C.getDeclaredField("repopRate"));
      A.add(C.getDeclaredField("defaultTerrain"));
      A.add(C.getDeclaredField("roomList"));
      A.add(C.getDeclaredField("itemList"));
      A.add(C.getDeclaredField("mobList"));
      return A;
    }

    else if (C == Room.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("id"));
      A.add(C.getDeclaredField("zoneID"));
      A.add(C.getDeclaredField("bankID"));
      A.add(C.getDeclaredField("N"));
      A.add(C.getDeclaredField("E"));
      A.add(C.getDeclaredField("S"));
      A.add(C.getDeclaredField("W"));
      A.add(C.getDeclaredField("U"));
      A.add(C.getDeclaredField("D"));
      A.add(C.getDeclaredField("terrain"));
      A.add(C.getDeclaredField("title"));
      A.add(C.getDeclaredField("lookList"));
      A.add(C.getDeclaredField("lookTargs"));
      A.add(C.getDeclaredField("actionList"));
      A.add(C.getDeclaredField("mobPopulation"));
      A.add(C.getDeclaredField("itemPopulation"));
      A.add(C.getDeclaredField("flagList"));
      A.add(C.getDeclaredField("desc"));
      A.add(C.getDeclaredField("bankbranch"));
      A.add(C.getDeclaredField("responseList"));
      return A;
    }

    else if (C == Account.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField(""));
      return A;
    }

    else if (C == BankChain.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("id"));
      A.add(C.getDeclaredField("name"));
      A.add(C.getDeclaredField("branches"));
      A.add(C.getDeclaredField("memberNames"));
      A.add(C.getDeclaredField("accounts"));
      return A;
    }

    else if (C == BankBranch.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("id"));
      A.add(C.getDeclaredField("roomID"));
      A.add(C.getDeclaredField("accounts"));
      return A;
    }

    else if (C == BankAccount.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("roomID"));
      A.add(C.getDeclaredField("bankID"));
      A.add(C.getDeclaredField("owner"));
      A.add(C.getDeclaredField("items"));
      A.add(C.getDeclaredField("gold"));
      return A;
    }

    else if (C == Account.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("username"));
      A.add(C.getDeclaredField("password"));
      A.add(C.getDeclaredField("emailAddress"));
      A.add(C.getDeclaredField("creationStamp"));
      A.add(C.getDeclaredField("registerStamp"));
      A.add(C.getDeclaredField("lastLoginDate"));
      A.add(C.getDeclaredField("lastLoginIP"));
      A.add(C.getDeclaredField("characterNames"));
      A.add(C.getDeclaredField("registered"));
      A.add(C.getDeclaredField("numChars"));
      A.add(C.getDeclaredField("badPasswords"));
      return A;
    }

    else if (C == Flag.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("enabled"));
      A.add(C.getDeclaredField("name"));
      return A;
    }

    else if (C == ItemSlot.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("itemList"));
      return A;
    }

    else if (C == MessageBoard.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("noteList"));
      A.add(C.getDeclaredField("boardName"));
      A.add(C.getDeclaredField("ID"));
      return A;
    }

    else if (C == Note.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("level"));
      A.add(C.getDeclaredField("clan"));
      A.add(C.getDeclaredField("clanRank"));
      A.add(C.getDeclaredField("religion"));
      A.add(C.getDeclaredField("religionRank"));
      A.add(C.getDeclaredField("author"));
      A.add(C.getDeclaredField("date"));
      A.add(C.getDeclaredField("title"));
      A.add(C.getDeclaredField("timeStamp"));
      A.add(C.getDeclaredField("body"));
      return A;
    }

    else if (C == ClanMember.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("name"));
      A.add(C.getDeclaredField("profession"));
      A.add(C.getDeclaredField("rank"));
      return A;
    }

    else if (C == Clan.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("clanID"));
      A.add(C.getDeclaredField("clanName"));
      A.add(C.getDeclaredField("clanInfo"));
      A.add(C.getDeclaredField("members"));
      A.add(C.getDeclaredField("maleRankNames"));
      A.add(C.getDeclaredField("femaleRankNames"));
      return A;
    }

    else if (C == Religion.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("religionID"));
      A.add(C.getDeclaredField("dietyName"));
      A.add(C.getDeclaredField("religionName"));
      A.add(C.getDeclaredField("religionInfo"));
      A.add(C.getDeclaredField("members"));
      A.add(C.getDeclaredField("maleRankNames"));
      A.add(C.getDeclaredField("femaleRankNames"));
      return A;
    }

    else if (C == Schedule.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("events"));
      return A;
    }

    else if (C == Writable.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("formatType"));
      A.add(C.getDeclaredField("formattedString"));
      A.add(C.getDeclaredField("rawString"));
      return A;
    }

    else if (C == PopMember.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("idNumbers"));
      A.add(C.getDeclaredField("frequencies"));
      A.add(C.getDeclaredField("isMob"));
      return A;
    }

    else if (C == Char.class)

    {
      ArrayList A = new ArrayList();
      A.addAll(getFields(C.getSuperclass()));
      A.add(C.getDeclaredField("experience"));
      A.add(C.getDeclaredField("dPoints"));
      A.add(C.getDeclaredField("clanID"));
      A.add(C.getDeclaredField("religionID"));
      A.add(C.getDeclaredField("screenSize"));
      A.add(C.getDeclaredField("accountName"));
      A.add(C.getDeclaredField("creationDate"));
      A.add(C.getDeclaredField("lastLogin"));
      A.add(C.getDeclaredField("fullname"));
      A.add(C.getDeclaredField("prename"));
      A.add(C.getDeclaredField("hometown"));
      A.add(C.getDeclaredField("religionName"));
      A.add(C.getDeclaredField("myMentor"));
      A.add(C.getDeclaredField("students"));
      A.add(C.getDeclaredField("dietyTitle"));
      A.add(C.getDeclaredField("myPrompt"));
      A.add(C.getDeclaredField("myLegend"));
      A.add(C.getDeclaredField("ansiMode"));
      A.add(C.getDeclaredField("colorMode"));
      A.add(C.getDeclaredField("plan"));
      A.add(C.getDeclaredField("myAliases"));
      return A;
    }

    else if (C == Mobile.class)

    {
      ArrayList A = new ArrayList();
      A.addAll(getFields(C.getSuperclass()));
      A.add(C.getDeclaredField("ID"));
      A.add(C.getDeclaredField("zoneID"));
      A.add(C.getDeclaredField("speed"));
      A.add(C.getDeclaredField("experience"));
      A.add(C.getDeclaredField("flagList"));
      A.add(C.getDeclaredField("keywords"));
      A.add(C.getDeclaredField("storeName"));
      A.add(C.getDeclaredField("blockType"));
      A.add(C.getDeclaredField("blockDir"));
      A.add(C.getDeclaredField("ipopList"));
      A.add(C.getDeclaredField("ipopFreq"));
      A.add(C.getDeclaredField("wearList"));
      A.add(C.getDeclaredField("wearFreq"));
      A.add(C.getDeclaredField("sellList"));
      A.add(C.getDeclaredField("questions"));
      A.add(C.getDeclaredField("actions"));
      A.add(C.getDeclaredField("actionInts"));
      return A;
    }

    else if (C == Cleric.class)

    {
      ArrayList A = new ArrayList();
      A.addAll(getFields(C.getSuperclass()));
      return A;
    }

    else if (C == Alias.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("name"));
      A.add(C.getDeclaredField("output"));
      return A;
    }

    else if (C == AliasList.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("list"));
      return A;
    }

    else if (C == Entity.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("currentHP"));
      A.add(C.getDeclaredField("currentMN"));
      A.add(C.getDeclaredField("currentMV"));
      A.add(C.getDeclaredField("maxHP"));
      A.add(C.getDeclaredField("maxMN"));
      A.add(C.getDeclaredField("maxMV"));
      A.add(C.getDeclaredField("level"));
      A.add(C.getDeclaredField("myRoom"));
      A.add(C.getDeclaredField("alignment"));
      A.add(C.getDeclaredField("blockerType"));
      A.add(C.getDeclaredField("blockerDir"));
      A.add(C.getDeclaredField("blockerParam"));
      A.add(C.getDeclaredField("HR"));
      A.add(C.getDeclaredField("DR"));
      A.add(C.getDeclaredField("AC"));
      A.add(C.getDeclaredField("MR"));
      A.add(C.getDeclaredField("STR"));
      A.add(C.getDeclaredField("DEX"));
      A.add(C.getDeclaredField("CON"));
      A.add(C.getDeclaredField("INT"));
      A.add(C.getDeclaredField("WIS"));
      A.add(C.getDeclaredField("gold"));
      A.add(C.getDeclaredField("EQ_HP"));
      A.add(C.getDeclaredField("EQ_MN"));
      A.add(C.getDeclaredField("EQ_MV"));
      A.add(C.getDeclaredField("name"));
      A.add(C.getDeclaredField("gender"));
      A.add(C.getDeclaredField("title"));
      A.add(C.getDeclaredField("poisoned"));
      A.add(C.getDeclaredField("SANCD"));
      A.add(C.getDeclaredField("spellList"));
      A.add(C.getDeclaredField("skillList"));
      A.add(C.getDeclaredField("effectList"));
      A.add(C.getDeclaredField("equipment"));
      A.add(C.getDeclaredField("description"));
      A.add(C.getDeclaredField("schedule"));
      A.add(C.getDeclaredField("followable"));
      A.add(C.getDeclaredField("autoassist"));
      A.add(C.getDeclaredField("inventory"));
      return A;
    }


    else if (C == Item.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("id"));
      A.add(C.getDeclaredField("zoneID"));
      A.add(C.getDeclaredField("level"));
      A.add(C.getDeclaredField("worth"));
      A.add(C.getDeclaredField("hitRoll"));
      A.add(C.getDeclaredField("damRoll"));
      A.add(C.getDeclaredField("armorClass"));
      A.add(C.getDeclaredField("resistance"));
      A.add(C.getDeclaredField("hitPoints"));
      A.add(C.getDeclaredField("manaPoints"));
      A.add(C.getDeclaredField("movePoints"));
      A.add(C.getDeclaredField("strength"));
      A.add(C.getDeclaredField("dexterity"));
      A.add(C.getDeclaredField("intelligence"));
      A.add(C.getDeclaredField("wisdom"));
      A.add(C.getDeclaredField("constitution"));
      A.add(C.getDeclaredField("maxItems"));
      A.add(C.getDeclaredField("maxUses"));
      A.add(C.getDeclaredField("numUses"));
      A.add(C.getDeclaredField("liquidType"));
      A.add(C.getDeclaredField("liquidAmount"));
      A.add(C.getDeclaredField("liquidMax"));
      A.add(C.getDeclaredField("blockerType"));
      A.add(C.getDeclaredField("blockerDir"));
      A.add(C.getDeclaredField("boardID"));
      A.add(C.getDeclaredField("decayTime"));
      A.add(C.getDeclaredField("name"));
      A.add(C.getDeclaredField("lname"));
      A.add(C.getDeclaredField("groundDesc"));
      A.add(C.getDeclaredField("timeStamp"));
      A.add(C.getDeclaredField("typeStamp"));
      A.add(C.getDeclaredField("editStamp"));
      A.add(C.getDeclaredField("markStamp"));
      A.add(C.getDeclaredField("classes"));
      A.add(C.getDeclaredField("places"));
      A.add(C.getDeclaredField("dtype"));
      A.add(C.getDeclaredField("blockerParam"));
      A.add(C.getDeclaredField("flagList"));
      A.add(C.getDeclaredField("isDecaying"));
      A.add(C.getDeclaredField("lookDesc"));
      A.add(C.getDeclaredField("responseList"));
      A.add(C.getDeclaredField("loadList"));
      A.add(C.getDeclaredField("wearSpellList"));
      A.add(C.getDeclaredField("wearListLevels"));
      A.add(C.getDeclaredField("useSpellList"));
      A.add(C.getDeclaredField("useListLevels"));
      A.add(C.getDeclaredField("types"));
      A.add(C.getDeclaredField("popMember"));
      A.add(C.getDeclaredField("itemList"));
      return A;
    }

    else if (C == ItemList.class)

    {
      ArrayList A = new ArrayList();
      A.add(C.getDeclaredField("type"));
      A.add(C.getDeclaredField("slotList"));
      return A;
    }

    return new ArrayList();
  }

  //////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////
}