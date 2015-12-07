import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import java.lang.reflect.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

class ObjectDeserializer

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

  public static void write(Object O, String path) throws Exception

  {
    createDirectory(path);

    Document Doc = createInfoFile(O);
    writeDocumentToFile(Doc, path);

    createObjectDirectories(O, path);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static void createObjectDirectories(Object O, String path) throws Exception

  {
    Class C = O.getClass();

    if (C.isArray() && !isPrimitive(C))

    {
      int size = Array.getLength(O);
      String fieldName = currentFieldName;

      for (int i=0; i<size; i++)
        write(Array.get(O, i), path + "\\" + fieldName + "." + i);
    }

    else if (C == ArrayList.class)

    {
      ArrayList A = (ArrayList) O;
      String fieldName = currentFieldName;

      for (int i=0; i<A.size(); i++)
        write(A.get(i), path + "\\" + fieldName + "." + i);
    }

    else

    {
      Field[] fields = getAllFields(O.getClass());

      for (int i=0; i<fields.length; i++)
      if (fields[i].getType().isArray() || !isPrimitive(fields[i].getType()))

      {
        currentFieldName = fields[i].getName();
        write(fields[i].get(O), path + "\\" + currentFieldName);
      }
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  public static Field[] getAllFields(Class C)

  {
    if (C == Object.class) return new Field[0];

    Field[] fields = C.getDeclaredFields();
    Field[] allFields = getAllFields(C.getSuperclass());
    Field[] combined = new Field[fields.length + allFields.length];

    for (int i=0; i<fields.length; i++)
      combined[i] = fields[i];

    for (int i=fields.length; i<combined.length; i++)
      combined[i] = allFields[i - fields.length];

    return combined;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static Document createInfoFile(Object O) throws Exception

  {
    Class C = O.getClass();
    Document Doc = DB.newDocument();

    if (C.isArray() && isPrimitive(C))
      createPrimitiveArrayInfo(Doc, O);
    else if (C.isArray())
      createObjectArrayInfo(Doc, O);
    else if (C == ArrayList.class)
      createArrayListInfo(Doc, O);
    else createObjectInfo(Doc, O);

    return Doc;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static void createPrimitiveArrayInfo(Document Doc, Object O) throws Exception

  {
    Class C = O.getClass();
    int size = Array.getLength(O);
    Element root = Doc.createElement("PrimitiveArray");

    root.setAttribute("type", C.getCanonicalName());
    root.setAttribute("size", Integer.toString(size));

    for (int i=0; i<size; i++)

    {
      Element E = Doc.createElement("VAR");
      E.setAttribute("index", Integer.toString(i));
      E.setAttribute("value", Array.get(O, i).toString());
      root.appendChild(E);
    }

    Doc.appendChild(root);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static void createObjectArrayInfo(Document Doc, Object O) throws Exception

  {
    Class C = O.getClass();
    int size = Array.getLength(O);
    Element root = Doc.createElement("ObjectArray");

    root.setAttribute("type", C.getCanonicalName());
    root.setAttribute("size", Integer.toString(size));

    for (int i=0; i<size; i++)

    {
      Element E = Doc.createElement("OBJ");
      E.setAttribute("index", Integer.toString(i));
      E.setAttribute("id", currentFieldName + "." + i);
      root.appendChild(E);
    }

    Doc.appendChild(root);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static void createArrayListInfo(Document Doc, Object O) throws Exception

  {
    ArrayList A = (ArrayList) O;
    Element root = Doc.createElement("java.util.ArrayList");

    root.setAttribute("size", Integer.toString(A.size()));

    for (int i=0; i<A.size(); i++)

    {
      Element E = Doc.createElement("OBJ");
      E.setAttribute("index", Integer.toString(i));
      E.setAttribute("type", A.get(i).getClass().getName());
      root.appendChild(E);
    }

    Doc.appendChild(root);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static void createObjectInfo(Document Doc, Object O) throws Exception

  {
    Class C = O.getClass();
    Element root = Doc.createElement(C.getName());
    Field[] fields = getAllFields(C);

    for (Field field : fields)
    if (isPrimitive(field.getType()) && !field.getType().isArray())

    {
      Element E = Doc.createElement("VAR");
      E.setAttribute("id", field.getName());
      E.setAttribute("type", field.getType().getCanonicalName());
      E.setAttribute("value", field.get(O).toString());
      root.appendChild(E);
    }

    else if (field.getType().isArray())

    {
      Element E = Doc.createElement("ARR");
      E.setAttribute("id", field.getName());
      E.setAttribute("type", field.getType().getCanonicalName());
      root.appendChild(E);
    }

    else

    {
      Element E = Doc.createElement("OBJ");
      E.setAttribute("id", field.getName());
      E.setAttribute("type", field.getType().getCanonicalName());
      root.appendChild(E);
    }

    Doc.appendChild(root);
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

  private static void createDirectory(String path)

  {
    File F = new File(path);
    F.mkdir();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////

  private static void writeDocumentToFile(Document Doc, String path)

  {
    try

    {
      File F = new File(path + "\\info.xml");
      DOMSource source = new DOMSource(Doc);
      StreamResult result = new StreamResult(F);
      T.transform(source, result);
    }

    catch (Exception e) { e.printStackTrace(); }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////
}