import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import java.lang.reflect.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

class ObjectSerializer

{
  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static DocumentBuilder DB;
  private static DocumentBuilderFactory DBF;
  private static TransformerFactory TF;
  private static Transformer T;
  private static String currentFieldName;

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static void init()

  {
    try

    {
      DBF = DocumentBuilderFactory.newInstance();
      DB = DBF.newDocumentBuilder();
      TF = TransformerFactory.newInstance();
      T = TF.newTransformer();
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Object load(String path) throws Exception

  {
    Document Doc = DB.parse(path + "\\info.xml");
    return parseDocument(Doc, path);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static Object parseDocument(Document Doc, String path) throws Exception

  {
    Element root = Doc.getDocumentElement();
    String tagName = root.getTagName();

    if (tagName.equals("PrimitiveArray"))
      return parsePrimitiveArray(root, path);
    else if (tagName.equals("ObjectArray"))
      return parseObjectArray(root, path);
    else if (tagName.equals("java.util.ArrayList"))
      return parseArrayList(root, path);
    else return parseObject(root, path);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static Object parsePrimitiveArray(Element root, String path) throws Exception

  {
    Class rootClass = null;
    String className = root.getAttribute("type");
    NodeList primitiveList = root.getChildNodes();
    int size = Integer.parseInt(root.getAttribute("size"));

    if (className.equals("int[]")) rootClass = int[].class;
    else if (className.equals("String[]")) rootClass = String[].class;
    else if (className.equals("double[]")) rootClass = double[].class;
    else if (className.equals("boolean[]")) rootClass = boolean[].class;
    else if (className.equals("short[]")) rootClass = short[].class;
    else if (className.equals("long[]")) rootClass = long[].class;
    else if (className.equals("byte[]")) rootClass = byte[].class;
    else if (className.equals("float[]")) rootClass = float[].class;

    Object newObject = Array.newInstance(rootClass.getComponentType(), size);

    for (int i=0; i<size; i++)

    {
      Element element = (Element) primitiveList.item(i);
      String value = element.getAttribute("value");

      if (rootClass == int[].class) Array.setInt(newObject, i, Integer.parseInt(value));
      else if (rootClass == boolean[].class) Array.setBoolean(newObject, i, Boolean.parseBoolean(value));
      else if (rootClass == byte[].class) Array.setByte(newObject, i, Byte.parseByte(value));
      else if (rootClass == long[].class) Array.setLong(newObject, i, Long.parseLong(value));
      else if (rootClass == float[].class) Array.setFloat(newObject, i, Float.parseFloat(value));
      else if (rootClass == short[].class) Array.setShort(newObject, i, Short.parseShort(value));
      else if (rootClass == String[].class) Array.set(newObject, i, value);
      else if (rootClass == double[].class) Array.setDouble(newObject, i, Double.parseDouble(value));
    }

    return newObject;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static Object parseObjectArray(Element root, String path) throws Exception

  {
    int size = Integer.parseInt(root.getAttribute("size"));
    String className = root.getAttribute("type");
    Class rootClass = Class.forName(className.substring(0, className.length()-2));
    Object newObject = Array.newInstance(rootClass, size);
    String subName = path.substring(path.lastIndexOf("\\") + 1);

    for (int i=0; i<size; i++)
      Array.set(newObject, i, load(path + "\\" + subName + "." + i));

    return newObject;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static Object parseArrayList(Element root, String path) throws Exception

  {
    ArrayList A = new ArrayList();
    int size = Integer.parseInt(root.getAttribute("size"));
    String subName = path.substring(path.lastIndexOf("\\") + 1);

    for (int i=0; i<size; i++)
      A.add(i, load(path + "\\" + subName + "." + i));

    return A;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static Object parseObject(Element root, String path) throws Exception

  {
    Class rootClass = Class.forName(root.getTagName());
    Object newObject = rootClass.newInstance();
    NodeList list = root.getChildNodes();

    for (int i=0; i<list.getLength(); i++)

    {
      Element element = (Element) list.item(i);
      String fieldName = element.getAttribute("id");
      String value = element.getAttribute("value");
      Field field = findField(rootClass, fieldName);

      if (field != null)
      if (element.getTagName().equals("VAR"))

      {
        if (field.getType() == int.class) field.setInt(newObject, Integer.parseInt(value));
        else if (field.getType() == boolean.class) field.setBoolean(newObject, Boolean.parseBoolean(value));
        else if (field.getType() == byte.class) field.setByte(newObject, Byte.parseByte(value));
        else if (field.getType() == long.class) field.setLong(newObject, Long.parseLong(value));
        else if (field.getType() == float.class) field.setFloat(newObject, Float.parseFloat(value));
        else if (field.getType() == short.class) field.setShort(newObject, Short.parseShort(value));
        else if (field.getType() == double.class) field.setDouble(newObject, Double.parseDouble(value));
        else if (field.getType() == String.class) field.set(newObject, value);
      }

      else field.set(newObject, load(path + "\\" + fieldName));
    }

    return newObject;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static Field findField(Class C, String id)

  {
    Field[] fields = ObjectDeserializer.getAllFields(C);

    for (Field field : fields)
      if (field.getName().equals(id))
        return field;

    return null;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static boolean isPrimitive(Class C)

  {
    return C == int.class       || C == boolean.class   || C == byte.class      ||
           C == short.class     || C == long.class      || C == float.class     ||
           C == double.class    || C == String.class    || C == Integer.class   ||
           C == Boolean.class   || C == Byte.class      || C == Short.class     ||
           C == Long.class      || C == Float.class     || C == Double.class    ||
           C == int[].class     || C == boolean[].class || C == byte[].class    ||
           C == short[].class   || C == long[].class    || C == float[].class   ||
           C == double[].class  || C == String[].class  || C == Integer[].class ||
           C == Boolean[].class || C == Byte[].class    || C == Short[].class   ||
           C == Long[].class    || C == Float[].class   || C == Double[].class;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
}