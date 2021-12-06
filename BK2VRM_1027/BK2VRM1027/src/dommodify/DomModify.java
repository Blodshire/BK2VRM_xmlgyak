/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dommodify;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author siran
 */
public class DomModify {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DomModify.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		Document doc = builder.newDocument();
		
		Element root = doc.createElementNS("mydomBK2VRM", "users");
		doc.appendChild(root);
		
		root.appendChild(createUser(doc, "1", "Eszter", "Winch", "lead dev"));
		root.appendChild(createUser(doc, "2", "Elek", "Mekk", "junior dev"));
		root.appendChild(createUser(doc, "3", "Tivy", "Tiv", "devops engineer"));
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transf = null;
        try {
            transf = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(DomModify.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transf.setOutputProperty(OutputKeys.INDENT, "yes");
		transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amunt", "2");
		
		DOMSource source = new DOMSource(doc);
		
		File myFile = new File("myusersBK2VRM.xml");
		
		StreamResult console = new StreamResult(System.out);
		StreamResult file = new StreamResult(myFile);
		
        try {
            transf.transform(source, console);
            transf.transform(source, file);
        } catch (TransformerException ex) {
            Logger.getLogger(DomModify.class.getName()).log(Level.SEVERE, null, ex);
        }
		
	}
	
	private static Node createUserElement(Document doc, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		
		return node;
	}
	
	private static Node createUser(Document doc, String id, String lastname, String firstname, String profession) {
		Element user = doc.createElement("user");
		
		user.setAttribute("id", id);
		user.appendChild(createUserElement(doc, "firstname", firstname));
		user.appendChild(createUserElement(doc, "lastname", lastname));
		user.appendChild(createUserElement(doc, "profession", profession));
		
		return user;
	}

}

