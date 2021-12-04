/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.domparse.BK2VRM.READ;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author siran
 */
public class DomReadBK2VRM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//      Uj DocumentBuilder letrehozasa
//      Document letrehozasa XML fajlbol
        File xmlFile = new File("myxml.xml");
        Document doc = null;
        doc = introduceFile(doc, xmlFile);

        //ha sikertelen volt a doksi letrehozasa teszteljuk, egyebkent kiirjuk
        if (doc != null) {
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
        } else {
            System.out.println("A doc null ertek!");
        }
        NodeList myList = doc.getDocumentElement().getChildNodes();
        String indent = "";
        listData(myList, indent);

    }

    public static Document introduceFile(Document doc, File xmlFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DomReadBK2VRM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }

    public static void listData(NodeList thisList, String indent) {
        indent+="\t";
        if (thisList != null) {
            for (int i = 0; i < thisList.getLength(); i++) {
                Node thisNode = thisList.item(i);
                if (thisNode.getNodeType() == Node.ELEMENT_NODE && !thisNode.getTextContent().trim().equals("")) {
                    System.out.println(indent+"{"+thisNode.getNodeName()+"}:");

                    NodeList newList = thisNode.getChildNodes();
                    listData(newList,indent);
                } else if (thisNode instanceof Text) {
                    String value = thisNode.getNodeValue().trim();
                    if (value.equals("")) {
                        continue;
                    }
                    System.out.println(indent+"-"+thisNode.getTextContent()+"-");
                }
            }
        }
    }
}
