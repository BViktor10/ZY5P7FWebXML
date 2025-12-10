package zy5p7f.domparse.hu;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ZY5P7FDomQuery {

    public static void main(String[] args) {
        try {
            // 1. XML fájl betöltése és előkészítése (parse)
            File xmlFile = new File("ZY5P7F_XML.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("--- Páciensek és Diagnózisok Lekérdezése ---");

            // 2. Az összes "Patient" nevű elem lekérése egy listába
            // Ez a NodeList tartalmazza az összes pácienst a dokumentumban
            NodeList patientList = doc.getElementsByTagName("Patient");

            // 3. Végigiterálás a páciensek listáján
            for (int i = 0; i < patientList.getLength(); i++) {
                Node node = patientList.item(i);

                // Ellenőrzés, hogy valóban elemről van-e szó
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element patientElement = (Element) node;

                    // 4. A páciens nevének kinyerése
                    // A "Name" tag tartalmát kérjük le
                    String name = patientElement.getElementsByTagName("Name").item(0).getTextContent();
                    
                    // 5. A páciens SSN (TAJ) számának kinyerése az attribútumból
                    String ssn = patientElement.getAttribute("ssn");

                    // 6. A MedicalRecord és a diagnózis keresése a páciensen belül
                    Element recordElement = (Element) patientElement.getElementsByTagName("MedicalRecord").item(0);
                    String diagnosis = "Nincs adat";
                    
                    // Ha van MedicalRecord, akkor kivesszük a MainDiagnosis tartalmát
                    if (recordElement != null) {
                        diagnosis = recordElement.getElementsByTagName("MainDiagnosis").item(0).getTextContent();
                    }

                    // 7. Az eredmények formázott kiírása a konzolra
                    System.out.println("Páciens: " + name + " (SSN: " + ssn + ")");
                    System.out.println("  -> Diagnózis: " + diagnosis);
                    System.out.println("-----------------------------------");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}