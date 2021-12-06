/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saxbk2vrm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author siran
 */
public class SaxBK2VRM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                public Map<String, Boolean> elementFlags = new HashMap<String, Boolean>();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    System.out.println(indentValue(elementFlags) + qName + formattedAttributeList(attributes) + " start");
                    AddEntry(elementFlags, qName, true);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    AddEntry(elementFlags, qName, false);
                    System.out.println(indentValue(elementFlags) + qName + " end");
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    System.out.println(indentValue(elementFlags) + new String(ch, start, length));
                }
            };

            saxParser.parse("macskakBK2VRM.xml", handler);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SaxBK2VRM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(SaxBK2VRM.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaxBK2VRM.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    public static String indentValue(Map<String, Boolean> map) {
        String indent = "";

        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            if (entry.getKey() != null && entry.getValue() == true) {
                indent += "  ";
            }
        }

        return indent;
    }

    public static void AddEntry(Map<String, Boolean> map, String key, Boolean value) {
        if (map.get(key) == null) {
            map.put(key, value);
        } else {
            map.replace(key, value);
        }
    }

    public static String formattedAttributeList(Attributes attributes) {
        if (attributes.getLength() == 0) {
            return "";
        }

        String attributeList = ", { ";
        for (int i = 0; i < attributes.getLength(); i++) {
            attributeList += attributes.getLocalName(i) + ":" + attributes.getValue(i) + " ";
        }
        return attributeList + "}";
    }
}
