package org.paddy.sfObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contacts {
    @JsonProperty("totalSize")
    private int totalSize;
    @JsonProperty("done")
    private boolean done;
    @JsonProperty("records")
    private Contact[] contacts;

    public int getTotalSize() {
        return totalSize;
    }

    public boolean getDone() {
        return done;
    }

    public Contact[] getContacts() {
        return contacts;
    }

    public Contacts() {
    }

    public Contacts(int totalSize, boolean done, Contact[] contacts) {
        this.totalSize = totalSize;
        this.done = done;
        this.contacts = contacts;
    }
}
