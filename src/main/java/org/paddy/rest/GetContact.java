package org.paddy.rest;

import org.paddy.sfobjects.Account;
import org.paddy.sfobjects.Contact;
import org.paddy.utils.NotifyingThread;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

public class GetContact extends NotifyingThread {
    private Object[][] obj = null;
    private String requestURI;
    private String accountName;

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
        final RestTemplate restTemplate = new RestTemplate();
        final Account[] accounts = restTemplate.getForObject(requestURI, Account[].class);
        Assert.notNull(accounts, "Accounts are null");
        for (Account acc : accounts) {
            if (acc.getContacts() != null) {
                this.obj = new Object[acc.getContacts().getTotalSize()][9];
                int i = 0;
                for (Contact contact : acc.getContacts().getContactsA()) {
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
