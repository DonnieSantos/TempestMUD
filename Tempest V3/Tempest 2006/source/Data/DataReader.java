import java.io.File;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class DataReader

{
  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public DataReader() { }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Data read(String filename)

  {
    File file = new File(filename);
    if (file.isDirectory()) return readDirectory(file);
    return readFile(file);
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Data readDirectory(File directory)

  {
    String dirName = directory.getName().toLowerCase();
    Data data = new DataCollection(dirName);
    File[] fileList = directory.listFiles();

    for (int i=0; i<fileList.length; i++)

    {
      String filename = fileList[i].getName();
      File file = new File(directory + "//" + filename);
      if (file.isDirectory()) data.add(readDirectory(file));
      else data.add(readFile(file));
    }

    return data;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  public Data readFile(File file)

  {
    Data D = null;

    try

    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      Document doc = docBuilder.parse (file);
      Node root = doc.getDocumentElement();
      D = Process(root);
    }

    catch (Exception e) { System.out.println(e.getStackTrace()); }

    return D;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private Data Process(Node root)

  {
    String nodeName = root.getNodeName();
    if (nodeName.equalsIgnoreCase("Collection"))     return ProcessCollection(root);
    else if (nodeName.equalsIgnoreCase("Object"))    return ProcessObject((Element)root);
    else if (nodeName.equalsIgnoreCase("Variable"))  return ProcessVariable((Element)root);
    else return null;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private Data ProcessVariable(Element variable)

  {
    String name = variable.getAttribute("name");
    String valu = variable.getAttribute("value");

    DataVariable newVar = new DataVariable(name);
    newVar.setValue(valu);

    return newVar;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private Data ProcessObject(Element element)

  {
    String name = element.getAttribute("name");

    DataObject newObject = new DataObject(name);
    NodeList vars = element.getElementsByTagName("Variable");

    for (int i=0; i<vars.getLength(); i++)
      newObject.add(ProcessVariable((Element)vars.item(i)));

    return newObject;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private Data ProcessCollection(Node root)

  {
    String name = ((Element)root).getAttribute("name");

    NodeList children = root.getChildNodes();
    DataCollection collection = new DataCollection(name);

    for (int i=0; i<children.getLength(); i++)

    {
      Node child = children.item(i);
      String nodeName = child.getNodeName();
      if (nodeName.equalsIgnoreCase("Collection"))  collection.add(ProcessCollection(child));
      else if (nodeName.equalsIgnoreCase("Object")) collection.add(ProcessObject((Element)child));
    }

    return collection;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////
}