import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class HallgatoTransformZY5P7F {

    public static void main(String[] args) {
        String xmlFile   = "hallgatoZY5P7F.xml";
        String xslFile   = "hallgatoZY5P7F.xsl";
        String outXml    = "hallgatoZY5P7F.out.xml";
        String outHtml   = "hallgatoZY5P7F.html";      

        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(
                    new StreamSource(new File(xslFile)));

            transformer.transform(
                    new StreamSource(new File(xmlFile)),
                    new StreamResult(new File(outXml)));

            transformer.transform(
                    new StreamSource(new File(xmlFile)),
                    new StreamResult(new File(outHtml)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

