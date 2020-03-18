package org.paddy.rest;

import org.paddy.sfObjects.Account;
import org.paddy.utils.NotifyingThread;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetAccount extends NotifyingThread {
    private Map<String, String> accountsM;
    private final String baseURI;

    public Map<String, String> getAccountsM() {
        return accountsM;
    }

    public GetAccount(String baseURI) {
        this.baseURI = baseURI;
    }

    private void createAccountMap() {
        accountsM = Collections.synchronizedMap(new LinkedHashMap<>());
        String requestURI = baseURI + "?sObj=Account&q=all";
        RestTemplate restTemplate = new RestTemplate();
        Account[] accounts = restTemplate.getForObject(requestURI, Account[].class);
        if (accounts != null && accounts.length > 0) {
            for (Account acc : accounts) {
                //System.out.println("Id: " + acc.getId() + " " + acc.getName());
                accountsM.put(acc.getId(), acc.getName());
            }
        }
    }

    public void doRun() {
        createAccountMap();
    }
}
