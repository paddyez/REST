package org.paddy.rest;

import org.paddy.sfObjects.Account;
import org.paddy.sfObjects.Contact;
import org.paddy.utils.NotifyingThread;
import org.springframework.web.client.RestTemplate;

public class GetContact extends NotifyingThread {
    private Object[][] obj = null;
    private String requestURI, accountName;

    public Object[][] getObj() {
        return obj;
    }

    public String getAccountName() {
        return accountName;
    }

    public GetContact(String baseURI, String accountId, String accountName) {
        this.accountName = accountName;
        requestURI = baseURI + "?sObj=Account&Id=" + accountId;
    }

    public static void getContactsObjectByAccountName() {
        //String requestURI = URI_BASE + "?sObj=Account&q=" + searchString;
    }

    private synchronized void getContactsObjects() {
        RestTemplate restTemplate = new RestTemplate();
        Account[] accounts = restTemplate.getForObject(requestURI, Account[].class);
        for (Account acc : accounts) {
            if (acc.getContacts() != null) {
                this.obj = new Object[acc.getContacts().getTotalSize()][9];
                int i = 0;
                for (Contact contact : acc.getContacts().getContacts()) {
                    this.obj[i] = contact.getRow();
                    i++;
                }
            }
        }
    }

    public void doRun() {
        getContactsObjects();
    }
}
