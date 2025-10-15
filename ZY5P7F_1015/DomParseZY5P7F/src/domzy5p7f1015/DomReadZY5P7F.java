package domzy5p7f1015;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import java.io.File;

public class DomReadZY5P7F {

    public static void main(String[] args) {
        try {
            File inputFile = new File("orarendZY5P7F.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Gyökérelem kiírása
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            // Rekurzív fa-bejárás és blokkos kiírás
            printNode(doc.getDocumentElement(), 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, int indent) {
        // Kiírás beljebb húzással
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.print("<" + node.getNodeName());

        // Attribútumok kiírása
        if (node.hasAttributes()) {
            NamedNodeMap attributes = node.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attr = attributes.item(i);
                System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
            }
        }
        System.out.println(">");

        // Gyerekek kiírása
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node child = nodeList.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                printNode(child, indent + 1);
            } else if (child.getNodeType() == Node.TEXT_NODE && !child.getNodeValue().trim().isEmpty()) {
                for (int j = 0; j < indent + 1; j++) System.out.print("  ");
                System.out.println(child.getNodeValue().trim());
            }
        }

        // Záró tag
        for (int i = 0; i < indent; i++) System.out.print("  ");
        System.out.println("</" + node.getNodeName() + ">");
    }
}
