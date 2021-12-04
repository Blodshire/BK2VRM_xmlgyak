/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.domparse.BK2VRM.QUERY;

import hu.domparse.BK2VRM.READ.DomReadBK2VRM;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author siran
 */
public class DomQueryBK2VRM {

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
            // System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
        } else {
            System.out.println("A doc null ertek!");
        }
        NodeList myList = doc.getDocumentElement().getChildNodes();
        //NodeList modifyList = doc.getDocumentElement().getElementsByTagName("length");
        //modifyData(modifyList);
        String indent = "";

        //query  - matematikat hallgatok neve
        NodeList queryList = doc.getDocumentElement().getElementsByTagName("lesson");
        //listData(queryList, indent);
        String keresett = null;

        //Matematika targyak ID-je kikeresése
        for (int i = 0; i < queryList.getLength(); i++) {
            NodeList query = queryList.item(i).getChildNodes();
            for (int j = 0; j < query.getLength(); j++) {
                if (query.item(j).getNodeName() == "name" && query.item(j).getTextContent().equals("Matematika")) {
                    keresett = getParentAttribute(query.item(j), "O_ID");
                }
            }

        }

        //azon hallgato idk kigyűjtése akik hallgatnak matematikát
        ArrayList<String> hallgatoIdk = new ArrayList();
        NodeList queryList2 = doc.getDocumentElement().getElementsByTagName("credit");
        for (int i = 0; i < queryList2.getLength(); i++) {
            if (getAttribute(queryList2.item(i), "O_IDREF").equals(keresett)) {
                hallgatoIdk.add(getAttribute(queryList2.item(i), "D_IDREF"));

            }

        }

        //azon hallgatók adatainak kiírása akik ki lettek gyűjtve 
        queryList2 = doc.getDocumentElement().getElementsByTagName("student");
        for (int i = 0; i < queryList2.getLength(); i++) {
            for (int j = 0; j < hallgatoIdk.size(); j++) {
                //System.out.println(getAttribute(queryList2.item(i), "D_ID"));
                if (getAttribute(queryList2.item(i), "D_ID").equals(hallgatoIdk.get(j))) {
                    listData(queryList2.item(i).getChildNodes(), indent);
                }
            }

        }
        //indent = "";

        //listData(myList, indent);
    }

    public static String getAttribute(Node myNode, String ID) {
        NamedNodeMap thisMap = myNode.getAttributes();
        for (int i = 0; i < thisMap.getLength(); i++) {
            if (thisMap.item(i).getNodeName().equals(ID)) {
                return thisMap.item(i).getTextContent();
            }

        }
        return "";
    }

    public static String getParentAttribute(Node myNode, String ID) {
        NamedNodeMap thisMap = myNode.getParentNode().getAttributes();
        for (int i = 0; i < thisMap.getLength(); i++) {
            if (thisMap.item(i).getNodeName().equals(ID)) {
                return thisMap.item(i).getTextContent();
            }

        }
        return "";
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
        indent += "\t";
        if (thisList != null) {
            for (int i = 0; i < thisList.getLength(); i++) {
                Node thisNode = thisList.item(i);
                if (thisNode.getNodeType() == Node.ELEMENT_NODE && !thisNode.getTextContent().trim().equals("")) {
                    System.out.println(indent + "{" + thisNode.getNodeName() + "}:");

                    NodeList newList = thisNode.getChildNodes();
                    listData(newList, indent);
                } else if (thisNode instanceof Text) {
                    String value = thisNode.getNodeValue().trim();
                    if (value.equals("")) {
                        continue;
                    }
                    System.out.println(indent + "-" + thisNode.getTextContent() + "-");
                }
            }
        }
    }
    //orak roviditese 10 perccel
    /*    public static void modifyData(NodeList thisList){
    if(thisList!=null){
    for (int i = 0; i < thisList.getLength(); i++) {
    int length = Integer.parseInt(thisList.item(i).getTextContent());
    length-=10;
    Integer myLength = length;
    thisList.item(i).setTextContent(myLength.toString());
    }
    }
    }*/
}
