package org.paddy;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.paddy.gui.RestJFrame;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

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
    }

    private static Map<String, Object> configFromFile(String resourceName) {
        Map<String, Object> jsonMap;
        InputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();
        String configJSON;
        if (resourceName == null) {
            inputStream = Main.class.getResourceAsStream("/config.json");
        } else {
            try {
                inputStream = new FileInputStream(resourceName);
            } catch (FileNotFoundException fnfe) {
                log.error("Config file could not be read => {}", fnfe.getMessage());
            }
        }
        assertThat(inputStream).as("Input stream is null! Check path?!").isNotNull();
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)){
            getFileContentsAsString(stringBuilder, inputStreamReader);
        } catch (IOException ioe) {
            log.error("InputStreamReader can not be invoked => {}", ioe.getMessage());
        }
        configJSON = stringBuilder.toString();
        JsonParser jsonParser = new BasicJsonParser();
        jsonMap = jsonParser.parseMap(configJSON);
        return jsonMap;
    }

    private static void getFileContentsAsString(StringBuilder stringBuilder, InputStreamReader inputStreamReader) {
        String line;
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException ioe) {
            log.error("BufferedReader can not be invoked => {}", ioe.getMessage());
        }
    }
}