package org.paddy.rest;
import org.paddy.sfObjects.Account;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class PutAccount extends NotifyingThread {
    static final String URI_BASE = "https://restful-developer-edition.eu12.force.com/services/apexrest/Rest";
    public static void insertAccount() {
        Account a  = new Account("TestAccount");
        //System.out.println("Accout: " + a.getName());
    }
    public static void insertAccounts() {
        Set<String> accountsS = new HashSet<>();
        Account a;
        for(int i = 0; i < 1000; i++) {
            a = new Account("TestAccount Tester" + i);
            accountsS.add(a.getJSON());
        }
        String newAccounts = "{\"accs\":[";
        newAccounts += String.join(",",accountsS);
        newAccounts += "]}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestBody = new HttpEntity<>(newAccounts, headers);
        //System.out.println("Test 1: " + requestBody.getBody());
        ResponseEntity<String> result;
        result = restTemplate.postForEntity(URI_BASE, requestBody, String.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            String s = result.getBody();
            System.out.println("(Client Side) Accounrs created: " + s);
        }
    }
    public void doRun(){
        insertAccounts();
    }
}
