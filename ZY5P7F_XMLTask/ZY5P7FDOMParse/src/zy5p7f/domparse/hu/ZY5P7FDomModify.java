package zy5p7f.domparse.hu;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ZY5P7FDomModify {

    public static void main(String[] args) {
        try {
            // 1. XML fájl beolvasása memóriába módosításra
            File xmlFile = new File("ZY5P7F_XML.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // 2. A módosítandó elemek (Gyógyszerek) lekérése
            NodeList medList = doc.getElementsByTagName("Medication");

            // 3. Végigjárjuk a gyógyszereket, hogy megtaláljuk a megfelelőt
            for (int i = 0; i < medList.getLength(); i++) {
                Node node = medList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element medElement = (Element) node;

                    // 4. Feltétel: A "MED001" azonosítójú gyógyszert keressük
                    if ("MED001".equals(medElement.getAttribute("id"))) {
                        
                        // Megkeressük a BrandName (Márkanév) elemet
                        Node brandNameNode = medElement.getElementsByTagName("BrandName").item(0);
                        
                        System.out.println("Régi név: " + brandNameNode.getTextContent());
                        
                        // 5. MÓDOSÍTÁS: Átírjuk a szöveges tartalmat
                        brandNameNode.setTextContent("Xanax SR");
                        
                        System.out.println("Új név beállítva: Xanax SR");
                    }
                }
            }
            
            // 6. A módosított DOM fa kiírása (Mentés)
            // TransformerFactory és Transformer létrehozása
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Forrás (a módosított dokumentum a memóriában)
            DOMSource source = new DOMSource(doc);

            // A) Kiírás a konzolra (ellenőrzés végett)
            System.out.println("\n--- Módosított XML tartalom: ---");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            // B) Kiírás új fájlba (hogy az eredeti is megmaradjon)
            StreamResult fileResult = new StreamResult(new File("ZY5P7F_XML_Modified.xml"));
            transformer.transform(source, fileResult);
            
            System.out.println("\n\nSikeres mentés: ZY5P7F_XML_Modified.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}