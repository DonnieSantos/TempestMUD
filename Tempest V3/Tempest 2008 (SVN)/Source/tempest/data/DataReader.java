package tempest.data;

import java.io.File;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import tempest.primitives.*;

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
    Data data = new DataCollection(new MudString(dirName));
    File[] fileList = directory.listFiles();

    for (int i=0; i<fileList.length; i++)
    if (!fileList[i].isHidden())

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
      Document doc = docBuilder.parse(file);
      Node root = doc.getDocumentElement();
      D = Process(root);
    }

    catch (Exception e) { e.printStackTrace(); }

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
    Text text = (Text) variable.getFirstChild();
    String valu = "";
    String name = variable.getNodeName();

    if (text != null) valu = text.getWholeText().replaceAll("\n", "\r\n");
    
    DataVariable newVar = new DataVariable(new MudString(name));
    newVar.setValue(new MudString(valu));

    return newVar;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private Data ProcessObject(Element element)

  {
    String name = element.getAttribute("name");

    DataObject newObject = new DataObject(new MudString(name));
    NodeList vars = element.getChildNodes();

    for (int i=0; i<vars.getLength(); i++)
    if (vars.item(i).getNodeType() == Node.ELEMENT_NODE)
      newObject.add(ProcessVariable((Element)vars.item(i)));

    return newObject;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private Data ProcessCollection(Node root)

  {
    String name = ((Element)root).getAttribute("name");

    NodeList children = root.getChildNodes();
    DataCollection collection = new DataCollection(new MudString(name));

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