package Helper;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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


    public FileHandler(String filePath) throws IOException, ParserConfigurationException, SAXException {
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
}
