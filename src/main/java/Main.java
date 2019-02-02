import org.paddy.api.OAuth;
import org.paddy.gui.*;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.util.Assert;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
public class Main {
    public static void main(String[] args) {
        Map<String, String> configMap = new HashMap<>();
        if(args.length > 0) {
            System.out.println(args[0]);
            configMap.put("a", "b");
        }
        else {
            Map<String, Object> jsonMap;
            jsonMap = configFromFile();
            jsonMap.forEach((k, v) -> configMap.put(k, (String)v));
            //configMap.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
        }
        //SwingUtilities.invokeLater(RestJFrame::new);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RestJFrame(configMap);
            }
        });
        /*
        SwingUtilities.invokeLater(() -> new RestJFrame(configMap));
        */
        OAuth oAuth = new OAuth(configMap);
            oAuth.login();
    }
    private static Map<String, Object> configFromFile() {
        Map<String, Object> jsonMap;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader reader;
        StringBuilder stringBuilder = new StringBuilder();
        String line, configJSON;
        inputStream = Main.class.getResourceAsStream("../resources/config.json");
        Assert.notNull(inputStream, "Input stream is null! Check path?!");
        inputStreamReader = new InputStreamReader(inputStream);
        reader = new BufferedReader(inputStreamReader);
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        }
        catch (IOException ioe) {
            System.err.println(ioe);
        }
        configJSON  = stringBuilder.toString();
        JsonParser jsonParser = new BasicJsonParser();
        jsonMap = jsonParser.parseMap(configJSON);
        return jsonMap;
    }
}
