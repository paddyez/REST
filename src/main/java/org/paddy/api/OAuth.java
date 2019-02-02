package org.paddy.api;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
public class OAuth {
    Map<String, String> configMap;
    Set<String> servicesS = new HashSet<>(Arrays.asList("authorize", "token", "revoke"));
    private final String charset = StandardCharsets.UTF_8.name();
    private final String oauthServices = "/services/oauth2/";
    public OAuth(Map<String, String> configMap) {
        this.configMap = configMap;
    }
    public void login () {
        String uri = "https://" + configMap.get("my_domain") +  oauthServices + "token";
        try {
            URL url = new URL(uri);
            String content = "grant_type=password";
            content += "&username=" + configMap.get("username");
            content += "&password=" + configMap.get("password")+configMap.get("security_token");
            content += "&client_id=" + configMap.get("client_id");
            content += "&client_secret=" + configMap.get("client_secret");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches( false );
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("charset", charset);
            connection.addRequestProperty("Content-Length", String.valueOf(content.length()));
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            //connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //connection.addRequestProperty( "Accept", "*/*" );
            //connection.addRequestProperty( "Accept-Encoding", "gzip, deflate, compress" );
            PrintStream  printStream = new PrintStream(connection.getOutputStream());
            printStream.print(content);
            printStream.close();
            connection.connect();
            //printHeader(connection);
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
            String response = stringWriter.toString();
            System.out.println(response);
            bufferedReader.close();
        }
        catch (MalformedURLException mfe) {
            System.err.println("### " + mfe);
        }
        catch (IOException ioe) {
            System.err.println("### " + ioe);
        }
    }
    private static void printHeader(HttpURLConnection connection) {
        Map<String, List<String>> hdrs = connection.getHeaderFields();
        System.out.println("----");
        hdrs.forEach((key,  values) -> {
            System.out.print(key + ": ");
            String joined = values.stream().collect(Collectors.joining("|"));
            System.out.println(joined);
        });
        System.out.println("----");
    }
}
