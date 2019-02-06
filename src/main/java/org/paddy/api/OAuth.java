package org.paddy.api;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
public class OAuth {
    private HttpURLConnection connection;
    private Map<String, String> configMap;
    private final Set<String> servicesS = new HashSet<>(Arrays.asList("authorize", "token", "revoke"));
    private final String charset = StandardCharsets.UTF_8.name();
    private final String oauthServices = "/services/oauth2/";
    private String credentials;
    private  String bearer;
    public OAuth(Map<String, String> configMap) {
        this.configMap = configMap;
    }
    public void login () {
        String response;
        connect();
        if (connection == null) throw new AssertionError();
        try {
            printToOut();
            connection.connect();
            //printHeader();
            response = response();
            if(response != null) {
                setBearer(response);
            }
        }
        catch (IOException ioe) {
            System.err.println("### " + ioe);
        }
        //System.out.println(bearer);
    }
    private void connect() {
        /* TODO if other than login */
        String lengthS = String.valueOf(loginCredentials().length());
        try {
            String uri = "https://" + configMap.get("my_domain") + oauthServices + "token";
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
            //connection.addRequestProperty( "Accept-Encoding", "gzip, deflate, compress" );
        }
        catch (IOException ioe) {
            System.err.println("### " + ioe);
        }
    }
    private String loginCredentials() {
        String credentials = "grant_type=password";
        credentials += "&username=" + configMap.get("username");
        credentials += "&password=" + configMap.get("password")+configMap.get("security_token");
        credentials += "&client_id=" + configMap.get("client_id");
        credentials += "&client_secret=" + configMap.get("client_secret");
        return credentials;
    }
    private void printToOut() {
        /* TODO print other than login */
        credentials = loginCredentials();
        try {
            PrintStream printStream = new PrintStream(connection.getOutputStream());
            printStream.print(credentials);
            printStream.close();
        }
        catch (IOException ioe) {
            System.err.println("### " + ioe);
        }
    }
    private String response() {
        String response = null;
        try {
            int status = connection.getResponseCode();
            BufferedReader bufferedReader;
            if (status > 299) {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            String line;
            StringWriter stringWriter = new StringWriter(connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
            while ((line = bufferedReader.readLine()) != null) {
                stringWriter.append(line);
            }
            bufferedReader.close();
            response = stringWriter.toString();
        }
        catch (IOException ioe) {
            System.err.println("### " + ioe);
        }
        return response;
    }
    private void setBearer(String response) {
        JsonParser jsonParser = new BasicJsonParser();
        Map jsonMap = jsonParser.parseMap(response);
        jsonMap.forEach((k, v) -> {
            if (k.equals("access_token")) {
                bearer = String.valueOf(v);
            }
        });
    }
    private void printHeader() {
        System.out.println("----");
        connection.getHeaderFields().forEach((key,  values) -> {
            System.out.print(key + ": ");
            String joined = values.stream().collect(Collectors.joining("|"));
            System.out.println(joined);
        });
        System.out.println("----");
    }
}
