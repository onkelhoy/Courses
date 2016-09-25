package Helper;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.sql.RowSetInternal;
import javax.sql.rowset.WebRowSet;
import javax.sql.rowset.spi.XmlWriter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by henry on 2016-09-20.
 */
public class FileHandler {
    //private variables
    private Document xmlDoc = null;
    private XPath xpath = null;
    private String path = "";

    public Document getDoc(){ return xmlDoc; }

    public FileHandler(String filePath) throws IOException, ParserConfigurationException, SAXException {
        path = filePath;
        File file = new File(filePath);
        DocumentBuilderFactory Bfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = Bfactory.newDocumentBuilder();

        xmlDoc = builder.parse(file); // file has been parsed into xml format document
        xmlDoc.getDocumentElement().normalize(); // normalizes the document

        XPathFactory xFactory = XPathFactory.newInstance();
        xpath = xFactory.newXPath();
    }

    public void Append(Node node){
        xmlDoc.appendChild(node);
    }
    public String Search(String query) throws XPathExpressionException {
        return xpath.evaluate(query, xmlDoc);
    }

    public void Save() throws TransformerException {
        TransformerFactory transformerfactory = TransformerFactory.newInstance();
        Transformer transformer = transformerfactory.newTransformer();
        DOMSource source = new DOMSource(xmlDoc);
        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
    }

}
