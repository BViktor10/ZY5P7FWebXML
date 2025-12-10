package zy5p7f.domparse.hu;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ZY5P7FDomRead {

    public static void main(String[] args) {
        try {
            // 1. Az XML fájl helyének meghatározása
            File xmlFile = new File("ZY5P7F_XML.xml");

            // 2. Dokumentum építő gyár (Factory) és építő (Builder) létrehozása
            // Ez szükséges az XML struktúra felépítéséhez a memóriában
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();

            // 3. A teljes dokumentum beolvasása (parse) és DOM fává alakítása
            Document doc = dBuilder.parse(xmlFile);

            // 4. A dokumentum normalizálása
            // (Eltávolítja a felesleges üres helyeket és rendezi a csomópontokat)
            doc.getDocumentElement().normalize();

            // 5. A gyökérelem nevének kiírása ellenőrzésképpen
            System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");

            // 6. A fa bejárásának indítása a gyökérelemtől
            printNode(doc.getDocumentElement(), "");

        } catch (Exception e) {
            // Hiba esetén a hibaüzenet kiírása (pl. ha nem találja a fájlt)
            e.printStackTrace();
        }
    }

    // Rekurzív metódus a fa csomópontjainak bejárására és kiírására
    private static void printNode(Node node, String indent) {
        
        // Csak akkor foglalkozunk a csomóponttal, ha az "Elem" típusú (Node.ELEMENT_NODE)
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            
            // Az elem nevének kiírása, behúzással (indent)
            System.out.print(indent + "Elem: " + node.getNodeName());

            // Attribútumok vizsgálata és kiírása, ha léteznek
            if (node.hasAttributes()) {
                NamedNodeMap nodeMap = node.getAttributes();
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    Node tempNode = nodeMap.item(i);
                    // Attribútum név=érték párok kiírása
                    System.out.print(" | " + tempNode.getNodeName() + "=" + tempNode.getNodeValue());
                }
            }
            
            // Szöveges tartalom vizsgálata
            // Ha az elemnek van gyereke, és az szöveges típusú, akkor kiírjuk
            if (node.hasChildNodes() && node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                 String text = node.getFirstChild().getNodeValue().trim();
                 if (!text.isEmpty()) {
                     System.out.print(" : " + text);
                 }
            }
            
            System.out.println(); // Sortörés a következő elemhez

            // Gyerekelemek lekérése listába
            NodeList nodeList = node.getChildNodes();
            
            // Végigiterálás a gyerekeken, és a metódus meghívása újra (rekurzió)
            // A behúzást (indent) növeljük szóközökkel
            for (int i = 0; i < nodeList.getLength(); i++) {
                printNode(nodeList.item(i), indent + "  ");
            }
        }
    }
}