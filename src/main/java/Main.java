import org.paddy.api.OAuth;
import org.paddy.gui.RestJFrame;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.util.Assert;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Object> jsonMap = new HashMap<>();
        Map<String, String> configMap = new HashMap<>();
        if (args.length > 1) {
            if (args[0].equals("-f")) {
                jsonMap = configFromFile(args[1]);
            }
        } else {
            jsonMap = configFromFile(null);
        }
        jsonMap.forEach((k, v) -> configMap.put(k, (String) v));
        //configMap.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
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

    private static Map<String, Object> configFromFile(String rewsourceName) {
        Map<String, Object> jsonMap;
        InputStream inputStream = Main.class.getResourceAsStream("../resources/config.json");
        InputStreamReader inputStreamReader;
        BufferedReader reader;
        StringBuilder stringBuilder = new StringBuilder();
        String line, configJSON;
        if (rewsourceName != null) {
            try {
                inputStream = new FileInputStream(rewsourceName);
            } catch (FileNotFoundException fnfe) {
                System.err.println("### " + fnfe);
            }
        }
        Assert.notNull(inputStream, "Input stream is null! Check path?!");
        inputStreamReader = new InputStreamReader(inputStream);
        reader = new BufferedReader(inputStreamReader);
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        configJSON = stringBuilder.toString();
        JsonParser jsonParser = new BasicJsonParser();
        jsonMap = jsonParser.parseMap(configJSON);
        return jsonMap;
    }
}
