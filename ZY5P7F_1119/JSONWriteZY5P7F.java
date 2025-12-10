import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SuppressWarnings("unchecked")

public class JSONWriteZY5P7F {

    public static void main(String[] args) {

        try (FileReader reader = new FileReader("orarendZY5P7F.json")) {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONObject root = (JSONObject) jsonObject.get("ZY5P7F_orarend");
            JSONArray lessons = (JSONArray) root.get("ora");

            System.out.println("ZY5P7F Órarend 2025 ősz:\n");

            for (int i = 0; i < lessons.size(); i++) {
                JSONObject lesson = (JSONObject) lessons.get(i);
                JSONObject time = (JSONObject) lesson.get("idopont");

                System.out.println("Tárgy: " + lesson.get("targy"));
                System.out.println("Időpont: " + time.get("nap") + " " + time.get("tol") + "-" + time.get("ig"));
                System.out.println("Helyszín: " + lesson.get("helyszin"));
                System.out.println("Oktató: " + lesson.get("oktato"));
                System.out.println("Szak: " + lesson.get("szak"));
                System.out.println();
            }

            JSONObject ujJson = new JSONObject();
            ujJson.put("ZY5P7F_orarend", root);

            FileWriter writer = new FileWriter("orarendZY5P7F1.json");
            writer.write(ujJson.toJSONString());
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
