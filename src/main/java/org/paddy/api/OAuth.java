package org.paddy.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.paddy.utils.ResponseStatusCodes;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class OAuth {
    private static final Logger log = LogManager.getLogger(OAuth.class);
    public static final String LOG_ERROR_MESSAGE = "### {}";
    private HttpURLConnection connection;
    private final Map<String, String> configMap;
    //private static final Set<String> servicesS = new HashSet<>(Arrays.asList("authorize", "token", "revoke"));
    private final String charset = StandardCharsets.UTF_8.name();
    private static final String SERVICES_OAUTH_2 = "/services/oauth2/";
    private String bearer;
    private static final boolean useCompression = true;

    public OAuth(Map<String, String> configMap) {
        this.configMap = configMap;
    }

    public void login() {
        String response;
        int responseCode = connect();
        if (responseCode == 200) {
            response = response();
            if (response != null) {
                setBearer(response);
                log.info("bearer: {}", bearer);
            }
        } else {
            if(responseCode != -1) {
                log.error("REST POST to default Salesforce API failed with Error code {}: {}", responseCode, ResponseStatusCodes.getPossibleCause("POST", responseCode));
            }
        }
    }

    private int connect() {
        /* TODO if other than login */
        String lengthS = String.valueOf(loginCredentials().length());
        int responseCode = -1;
        try {
            String uri = "https://" + configMap.get("my_domain") + SERVICES_OAUTH_2 + "token";
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("charset", charset);
            connection.addRequestProperty("Content-Length", lengthS);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            //connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //connection.addRequestProperty( "Accept", "*/*" );
            if (useCompression) {
                connection.addRequestProperty("Accept-Encoding", "gzip, deflate, compress");
            }
            printToOut();
            connection.connect();
            responseCode = connection.getResponseCode();
        } catch (IOException ioe) {
            log.error(LOG_ERROR_MESSAGE, ioe.getMessage());
        }
        if (log.isDebugEnabled()) {
            printHeader();
        }
        return responseCode;
    }

    private String loginCredentials() {
        return "grant_type=password" +
                "&username=" +
                configMap.get("username") +
                "&password=" +
                configMap.get("password") +
                configMap.get("security_token") +
                "&client_id=" +
                configMap.get("client_id") +
                "&client_secret=" +
                configMap.get("client_secret");
    }

    private void printToOut() {
        /* TODO print other than login */
        String credentials = loginCredentials();
        try {
            PrintStream printStream = new PrintStream(connection.getOutputStream());
            printStream.print(credentials);
            printStream.close();
        } catch (IOException ioe) {
            log.error(LOG_ERROR_MESSAGE, ioe.getMessage());
        }
    }

    private String response() {
        String response = null;
        BufferedReader bufferedReader;
        InputStream inputStream;
        try {
            if (useCompression) {
                inputStream = new GZIPInputStream(connection.getInputStream());
            } else {
                inputStream = connection.getInputStream();
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringWriter stringWriter = new StringWriter(connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
            while ((line = bufferedReader.readLine()) != null) {
                stringWriter.append(line);
            }
            bufferedReader.close();
            response = stringWriter.toString();
        } catch (IOException ioe) {
            log.error(LOG_ERROR_MESSAGE, ioe.getMessage());
        }
        return response;
    }

    private void setBearer(String response) {
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonMap = jsonParser.parseMap(response);
        if (!jsonMap.isEmpty()) {
            jsonMap.forEach((k, v) -> {
                if (k.equals("access_token")) {
                    bearer = String.valueOf(v);
                }
            });
        }
    }

    private void printHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n----\n");
        connection.getHeaderFields().forEach((key, values) -> {
            sb.append(key).append(": ");
            String joined = String.join("|", values);
            joined += "\n";
            sb.append(joined);
        });
        sb.append("----");
        if (log.isInfoEnabled()) {
            log.info(sb.toString());
        }
    }
}
