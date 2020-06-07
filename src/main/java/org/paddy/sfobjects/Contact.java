package org.paddy.sfobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Contact extends SfObject {
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

    public java.lang.Object[] getRow() {
        return new java.lang.Object[]{getId(), salutation, title, lastName, firstName, email, phone, mobilePhone};
    }
}
