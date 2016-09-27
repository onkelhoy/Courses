package Helper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;

/**
 * Created by henry on 2016-09-20.
 */
public class FileHandler {
    //private variables
    private Document xmlDoc = null;
    private XPath xpath = null;
    private String path = "";

    public Document getDoc(){ return xmlDoc; }

    public FileHandler(String fileName) {
        path = "content/"+fileName+"DB.xml";

        XPathFactory xFactory = XPathFactory.newInstance();
        xpath = xFactory.newXPath();

        try {
            DocumentBuilderFactory Bfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = Bfactory.newDocumentBuilder();

            File file = new File(path);
            if(file.exists()){
                xmlDoc = builder.parse(file); // file has been parsed into xml format document
                xmlDoc.getDocumentElement().normalize(); // normalizes the document
            }
            else {
                //file not found.. create one instead
                xmlDoc = builder.newDocument();
                Element root = xmlDoc.createElement(fileName+"s");
                xmlDoc.appendChild(root);
                Save();
            }
        }
        catch (Exception e){
            System.out.println("Cant load database: "+ fileName);
        }
    }

    public void Append(Node node){
        xmlDoc.appendChild(node);
    }
    public NodeList Search(String expression) {
        try {
            return (NodeList)xpath.compile(expression).evaluate(xmlDoc, XPathConstants.NODESET);
        } catch (Exception e) {
            return null;
        }
    }

    public void Save() {
        try {
            TransformerFactory transformerfactory = TransformerFactory.newInstance();
            Transformer transformer = transformerfactory.newTransformer();
            DOMSource source = new DOMSource(xmlDoc);

            File f = new File(path);
            if(!f.exists()){
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            StreamResult result = new StreamResult(new File(path));

            transformer.transform(source, result);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("database could not be saved: " + path);
        }
    }

}
