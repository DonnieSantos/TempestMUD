import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class DataWriter

{
  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private DocumentBuilder docBuilder;
  private DocumentBuilderFactory docBuilderFactory;
  private TransformerFactory transformerFactory;
  private Transformer transformer;
  private Document Doc;

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public DataWriter() { }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private void init()

  {
    try

    {
      docBuilderFactory = DocumentBuilderFactory.newInstance();
      docBuilder = docBuilderFactory.newDocumentBuilder();
      transformerFactory = TransformerFactory.newInstance();
      transformer = transformerFactory.newTransformer();
      Doc = docBuilder.newDocument();
    }

    catch (Exception ex) { ex.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void writeFile(Data data, String filename)

  {
    init();

    System.out.println("Writing File: " + filename);

    File file = new File(filename);

    try

    {
      Doc.appendChild(createElement(data));
      DOMSource source = new DOMSource(Doc);
      StreamResult result = new StreamResult(file);
      transformer.transform(source, result);
    }

    catch (Exception ex) { ex.printStackTrace(); }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  public void writeDirectory(Data data, String filename)

  {
    File file = new File(filename);
    file.mkdir();

    ArrayList list = data.toList();

    for (int i=0; i<list.size(); i++)
    {
      Data D = (Data) list.get(i);
      String name = D.name();
      if (name.length() == 0) name = "" + i;
      String newFileName = filename + "//" + name;
      if (D.isObject()) writeFile(D, (newFileName + ".xml"));
      else writeDirectory(D, newFileName);
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Element createElement(Data data)

  {
    if (data.isCollection()) return createCollectionElement(data);
    if (data.isArray()) return createArrayElement(data);
    if (data.isObject()) return createObjectElement(data);
    if (data.isVariable()) return createVariableElement(data);
    return null;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Element createCollectionElement(Data data)

  {
    Element E = Doc.createElement("Collection");

    if (data.hasName()) E.setAttribute("name", data.name());

    ArrayList list = data.toList();

    for (int i=0; i<list.size(); i++)

    {
      Data D = (Data) list.get(i);
      if (D.isCollection()) E.appendChild(createCollectionElement(D));
      else if (D.isArray()) E.appendChild(createArrayElement(D));
      else if (D.isObject()) E.appendChild(createObjectElement(D));
      else if (D.isVariable()) E.appendChild(createVariableElement(D));
    }

    return E;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Element createArrayElement(Data data)

  {
    Element E = Doc.createElement("Array");

    if (data.hasName()) E.setAttribute("name", data.name());

    for (int i=0; i<data.size(); i++)

    {
      Data D = data.get(i);
      if (D.isCollection()) E.appendChild(createCollectionElement(D));
      else if (D.isArray()) E.appendChild(createArrayElement(D));
      else if (D.isObject()) E.appendChild(createObjectElement(D));
      else if (D.isVariable()) E.appendChild(createVariableElement(D));
    }

    return E;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Element createObjectElement(Data data)

  {
    Element E = Doc.createElement("Object");

    if (data.hasName()) E.setAttribute("name", data.name());

    ArrayList list = data.toList();

    for (int i=0; i<list.size(); i++)
      E.appendChild(createVariableElement(((Data)list.get(i))));

    return E;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Element createVariableElement(Data data)

  {
    Element E = Doc.createElement("Variable");
    E.setAttribute("name", data.name());
    E.setAttribute("value", data.value());
    return E;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}