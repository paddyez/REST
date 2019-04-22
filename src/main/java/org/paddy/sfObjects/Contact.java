package org.paddy.sfObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact extends sfObject {
    public static final String SEMICOLON_SEPARATOR = "\";\"";
    @JsonProperty("MailinngAddress")
    private Address mailingAddress;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("MobilePhone")
    private String mobilePhone;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("Salutation")
    private String salutation;
    @JsonProperty("Title")
    private String title;

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getPhone() {
        return phone;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getTitle() {
        return title;
    }

    public java.lang.Object[] getRow() {
        return new java.lang.Object[]{getId(), salutation, title, lastName, firstName, email, phone, mobilePhone};
    }

    public Contact() {
    }

    public Contact(Address mailingAddress,
                   Attributes attributes,
                   String email,
                   String firstName,
                   String id,
                   String lastName,
                   String mobilePhone,
                   String name,
                   String phone,
                   String salutation,
                   String title) {
        this.mailingAddress = mailingAddress;
        setAttributes(attributes);
        this.email = email;
        this.firstName = firstName;
        setId(id);
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        setName(name);
        this.phone = phone;
        this.salutation = salutation;
        this.title = title;
    }

    @Override
    public String toString() {
        String type = getAttributes().getType();
        assert type.equals("Contact");
        String toString;
        toString = "\"" + getId() + SEMICOLON_SEPARATOR + getName() + SEMICOLON_SEPARATOR + email + SEMICOLON_SEPARATOR + phone + "\"";
        return toString;
    }
}
