package tempest.data;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import tempest.primitives.*;

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

    for (Data D : data)

    {
      MudString name = D.name();
      String newFileName = filename + "//" + name.toString();
      if (D.isObject()) writeFile(D, (newFileName + ".xml"));
      else writeDirectory(D, newFileName);
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Element createElement(Data data)

  {
    if (data.isVariable()) return createVariableElement(data);
    if (data.isObject()) return createObjectElement(data);
    if (data.isCollection()) return createCollectionElement(data);
    return null;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Element createVariableElement(Data data)

  {
    Element E = Doc.createElement(data.name().toString());
    E.appendChild(Doc.createTextNode(data.value().toString()));
    return E;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Element createObjectElement(Data data)

  {
    Element E = Doc.createElement("Object");
    E.setAttribute("name", data.name().toString());
    for (Data D : data) E.appendChild(createVariableElement(D));
    return E;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////

  private Element createCollectionElement(Data data)

  {
    Element E = Doc.createElement("Collection");
    E.setAttribute("name", data.name().toString());

    for (Data D : data)

    {
      if (D.isCollection()) E.appendChild(createCollectionElement(D));
      else if (D.isObject()) E.appendChild(createObjectElement(D));
      else if (D.isVariable()) E.appendChild(createVariableElement(D));
    }

    return E;
  }

  /////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////
}