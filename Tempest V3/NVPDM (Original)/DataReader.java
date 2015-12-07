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
    Data D = null;

    try

    {
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
      Document doc = docBuilder.parse (new File(filename));
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
    else if (nodeName.equalsIgnoreCase("Array"))     return ProcessArray(root);
    else if (nodeName.equalsIgnoreCase("Object"))    return ProcessObject((Element)root);
    else if (nodeName.equalsIgnoreCase("Variable"))  return ProcessVariable((Element)root);
    else return null;
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
      else if (nodeName.equalsIgnoreCase("Array"))  collection.add(ProcessArray(child));
      else if (nodeName.equalsIgnoreCase("Object")) collection.add(ProcessObject((Element)child));
    }

    return collection;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private Data ProcessArray(Node root)

  {
    String name = ((Element)root).getAttribute("name");
    NodeList children = root.getChildNodes();
    DataArray collection = new DataArray(name);

    for (int i=0; i<children.getLength(); i++)

    {
      Node child = children.item(i);
      String nodeName = child.getNodeName();
      if (nodeName.equalsIgnoreCase("Collection"))  collection.add(ProcessCollection(child));
      else if (nodeName.equalsIgnoreCase("Array"))  collection.add(ProcessArray(child));
      else if (nodeName.equalsIgnoreCase("Object")) collection.add(ProcessObject((Element)child));
    }

    return collection;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////

  private Data ProcessObject(Element element)

  {
    DataObject newObject = new DataObject(element.getAttribute("name"));
    NodeList vars = element.getElementsByTagName("Variable");

    for (int i=0; i<vars.getLength(); i++)
      newObject.add(ProcessVariable((Element)vars.item(i)));

    return newObject;
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
}