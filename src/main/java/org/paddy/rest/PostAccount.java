package org.paddy.rest;
import org.paddy.utils.ConsoleColors;
import org.paddy.sfObjects.Account;
import org.paddy.utils.NotifyingThread;
import org.paddy.utils.ResponseStatusCodes;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.LinkedHashSet;
import java.util.Set;
public class PostAccount extends NotifyingThread {
    String baseURI;
    public PostAccount(String baseURI) {
        this.baseURI = baseURI;
    }
    void insertAccount() {
        Account a  = new Account("TestAccount");
    }
    void insertAccounts() {
        Set<String> accountsS = new LinkedHashSet<>();
        Account a;
        for(int i = 0; i < 1000; i++) {
            a = new Account("TestAccount Tester" + i);
            accountsS.add(a.getJSON());
        }
        String newAccounts = "{\"sObjects\":[";
        newAccounts += String.join(",",accountsS);
        newAccounts += "],\"info\":\"Useless information\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestBody = new HttpEntity<>(newAccounts, headers);
        ResponseEntity<String> result;
        try {
            result = restTemplate.postForEntity(this.baseURI, requestBody, String.class);
            HttpStatus statusHS = result.getStatusCode();
            int statusi = statusHS.value();
            String response = getResponse(result);
            if (statusHS == HttpStatus.OK) {
                System.out.println("Accounts created: server status code: " + statusi + "\n" + response);
            } else {
                System.err.println("Failed to create Accounts server status code: " + statusi + "\n" + response);
            }
        }
        catch (HttpServerErrorException hsee) {
            int code = Integer.valueOf(hsee.getMessage().substring(0, 3)).intValue();
            System.err.println(hsee.getMessage() + ":\n" + ResponseStatusCodes.getPossibleCause("POST", code));
        }
    }
    private String getResponse(ResponseEntity<String> result) {
        String resultBody = result.getBody();
        HttpHeaders httpHeaders = result.getHeaders();
        Set<String> headersS = new LinkedHashSet<>();
        String resultHeadersS;
        for (String key : httpHeaders.keySet()) {
            headersS.add(key + ": " + httpHeaders.get(key));
        }
        resultHeadersS = String.join("\n", headersS);
        return "Headers:\n" + ConsoleColors.BLUE + resultHeadersS + ConsoleColors.WHITE + "\nBody:\n" + resultBody;
    }
    public void doRun(){
        insertAccounts();
    }
}
