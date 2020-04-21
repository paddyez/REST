package org.paddy.sfObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account extends sfObject {
    @JsonProperty("Contacts")
    private Contacts contacts;
    @JsonProperty("ShippingAddress")
    private Address shippingAddress;

    public Contacts getContacts() {
        return contacts;
    }

    private Account() {
    }

    public Account(String name) {
        setName(name);
        Attributes attributes = new Attributes("Account");
        setAttributes(attributes);
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
        return "{\"attributes\":{\"type\":\"Account\"},\"Name\":\"" +
                getName() +
                "\",\"ShippingStreet\":\"Willy-Brandt-Platz 1-3\",\"ShippingStreet\":\"Willy-Brandt-Platz 1-3\",\"ShippingState\":\"\",\"ShippingPostalCode\":\"68161\",\"ShippingLongitude\":\"\",\"ShippingLatitude\":\"\",\"ShippingGeocodeAccuracy\":\"\",\"ShippingCountry\":\"Deutschland\",\"ShippingCity\":\"Mannheim\"}";
    }

    @Override
    public String toString() {
        String type = getAttributes().getType();
        assert type.equals("Account");
        StringBuilder sb = new StringBuilder();
        sb.append("== ")
                .append(type)
                .append(" ")
                .append(getAttributes().getUrl())
                .append(" ==\n\n")
                .append("Id: ")
                .append(getId())
                .append(" Name: ")
                .append(getName());
        if (getContacts() != null) {
            sb.append("\n")
                    .append("totalSize: ")
                    .append(getContacts().getTotalSize())
                    .append("\n")
                    .append("done: ")
                    .append(getContacts().getDone())
                    .append("\n");
            sb.append("\n=== Contacts ===\n\n");
            for (Contact contact : getContacts().getContactsA()) {
                sb.append(contact);
                sb.append("\n");
            }
        }
        if (shippingAddress != null) {
            sb.append(shippingAddress);
        }
        return sb.toString();
    }
}
