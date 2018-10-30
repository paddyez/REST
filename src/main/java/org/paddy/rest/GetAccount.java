package org.paddy.rest;
import org.paddy.sfObjects.Account;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
public class GetAccount extends NotifyingThread {
    Map<String, String> accountsM;
    static final String URI_BASE = "https://restful-developer-edition.eu12.force.com/services/apexrest/Rest";
    public Map<String, String> getAccountsM() {
        return accountsM;
    }
    private void createAccountMap() {
        accountsM = Collections.synchronizedMap(new LinkedHashMap<>());
        String requestURI = URI_BASE + "?sObj=Account&q=all";
        RestTemplate restTemplate = new RestTemplate();
        Account[] accounts = restTemplate.getForObject(requestURI, Account[].class);
        for(Account acc : accounts) {
            //System.out.println("Id: " + acc.getId() + " " + acc.getName());
            accountsM.put(acc.getId(), acc.getName());
        }
    }
    public void doRun(){
        createAccountMap();
    }
}
