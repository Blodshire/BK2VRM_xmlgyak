/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domread;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author siran
 */
public class DomRead {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                File xmlFile = new File("usersBK2VRM.xml");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
                Document doc = null;
        try {
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
        } catch (SAXException ex) {
            Logger.getLogger(DomRead.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DomRead.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DomRead.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		doc.getDocumentElement().normalize();
		
		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		
		NodeList nList = doc.getElementsByTagName("user");
		
        listUsers(nList);

	}

    public static void listUsers(NodeList nList) throws DOMException {
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            
            System.out.println("\nCurrent Element: " + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element)nNode;
                
                String uid = elem.getAttribute("id");
                
                Node node1  = elem.getElementsByTagName("firstname").item(0);
                String fname = node1.getTextContent();
                
                Node node2 = elem.getElementsByTagName("lastname").item(0);
                String lname = node2.getTextContent();
                
                Node node3 = elem.getElementsByTagName("profession").item(0);
                String pname = node3.getTextContent();
                
                System.out.printf("id: %s%n", uid);
                System.out.printf("First name: %s%n", fname);
                System.out.printf("Last name: %s%n", lname);
                System.out.printf("Profession: %s%n", pname);
            }
        }
    }

}