import java.io.File;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class DataTypeReader

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public DataTypeReader() { }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public DataTypeList read(String path)

  {
    ArrayList dataTypeList = new ArrayList();

    File FILE = new File(path);
    File[] fileList = FILE.listFiles();

    for (int i=0; i<fileList.length; i++)

    {
      String filename = fileList[i].getName();
      DataType newDataType = read(path, filename);
      dataTypeList.add(newDataType);
    }

    return new DataTypeList(dataTypeList);
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public DataType read(String path, String filename)

  {
    DataType dataType = null;

    try

    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      Document doc = docBuilder.parse(new File(path + filename));
      Node root = doc.getDocumentElement();
      dataType = Process(root);
    }

    catch (Exception e) { return null; }

    return dataType;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private DataType Process(Node root)

  {
    if (root.getNodeName().equalsIgnoreCase("DataType"))
      return ProcessDataType(root);

    return null;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private DataType ProcessDataType(Node root)

  {
    NodeList children = root.getChildNodes();

    for (int i=0; i<children.getLength(); i++)

    {
      if (children.item(i).getNodeName().equalsIgnoreCase("Object"))
        return ProcessObject(children.item(i));
      if (children.item(i).getNodeName().equalsIgnoreCase("Collection"))
        return ProcessCollection(children.item(i));
    }

    return null;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private DataType ProcessObject(Node node)

  {
    String name = ((Element)node.getParentNode()).getAttribute("name");

    NodeList children = node.getChildNodes();
    DataType dataType = new DataType(name, false);

    for (int i=0; i<children.getLength(); i++)
    if (children.item(i).getNodeName().equalsIgnoreCase("Variable"))

    {
      String varName = ((Element)children.item(i)).getAttribute("name");
      dataType.addVariable(varName);
    }

    return dataType;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private DataType ProcessCollection(Node node)

  {
    String name = ((Element)node.getParentNode()).getAttribute("name");

    NodeList children = node.getChildNodes();
    DataType dataType = new DataType(name, true);

    for (int i=0; i<children.getLength(); i++)
    if (isValidCollectionItem(children.item(i).getNodeName()))

    {
      String varName = ((Element)children.item(i)).getAttribute("name");
      String typName = ((Element)children.item(i)).getAttribute("type");
      dataType.addCollectionItem(varName, typName);
    }

    return dataType;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private boolean isValidCollectionItem(String name)

  {
    if (name.equalsIgnoreCase("Object"))     return true;
    if (name.equalsIgnoreCase("Array"))      return true;
    if (name.equalsIgnoreCase("Collection")) return true;
    return false;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}