package org.paddy.sfObjects;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Account extends sfObject {
    @JsonProperty("Contacts")
    private Contacts contacts;
    @JsonProperty("ShippingAddress")
    private Address shippingAddress;
    public Contacts getContacts() {return contacts;}
    public Account() {}
    public Account(String name) {
        setName(name);
    }
    public Account(Address shippingAddress,
                   Attributes attributes,
                   Contacts contacts,
                   String id,
                   String name) {
        this.shippingAddress = shippingAddress;
        setAttributes(attributes);
        this.contacts = contacts;
        setId(id);
        setName(name);
    }
    public String getJSON() {
        String toString = "";
        toString += "{\"Name\":\"" + getName() + "\"}";
        return toString;
    }
    @Override
    public String toString() {
        String type = getAttributes().getType();
        assert type.equals("Account");
        String toString = "== " + type + " " + getAttributes().getUrl() + " ==\n\n";
        toString += "Id: " + getId() + " Name: " + getName() + "\n";
        toString += "totalSize: " + getContacts().getTotalSize() + "\n";
        toString += "done: " + getContacts().getDone() + "\n";
        if(shippingAddress != null) {
            toString += shippingAddress;
        }
        toString += "\n=== Contacts ===\n\n";
        for(Contact contact : getContacts().getContacts()) {
            toString += contact + "\n";
        }
        return toString;
    }
}
