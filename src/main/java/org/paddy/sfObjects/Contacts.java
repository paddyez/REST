package org.paddy.sfObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contacts {
    @JsonProperty("totalSize")
    private int totalSize;
    @JsonProperty("done")
    private boolean done;
    @JsonProperty("records")
    private Contact[] contactsA;

    public int getTotalSize() {
        return totalSize;
    }

    public boolean getDone() {
        return done;
    }

    public Contact[] getContactsA() {
        return contactsA;
    }

    public Contacts() {
    }

    public Contacts(int totalSize, boolean done, Contact[] contactsA) {
        this.totalSize = totalSize;
        this.done = done;
        this.contactsA = contactsA;
    }
}
