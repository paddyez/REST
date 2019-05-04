package org.paddy;

import org.apache.log4j.Logger;
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
    private static final Logger log = Logger.getLogger(Main.class);

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
        /*
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RestJFrame(configMap);
            }
        });
        */
        SwingUtilities.invokeLater(() -> new RestJFrame(configMap));
        OAuth oAuth = new OAuth(configMap);
        oAuth.login();
    }

    private static Map<String, Object> configFromFile(String resourceName) {
        Map<String, Object> jsonMap;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        String configJSON;
        if (resourceName == null) {
            inputStream = Main.class.getResourceAsStream("../../../resources/config.json");
        } else {
            try {
                inputStream = new FileInputStream(resourceName);
            } catch (FileNotFoundException fnfe) {
                log.error("Config file could not be read: " + fnfe.getMessage());
            }
        }
        Assert.notNull(inputStream, "Input stream is null! Check path?!");
        try {
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException ioe) {
            log.error("Buffered reader can not be invoked => " + ioe.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                inputStream.close();
            } catch (IOException ioe) {
                log.error("Can not close Buffered reader => " + ioe.getMessage());
            }

        }
        configJSON = stringBuilder.toString();
        JsonParser jsonParser = new BasicJsonParser();
        jsonMap = jsonParser.parseMap(configJSON);
        return jsonMap;
    }
}
