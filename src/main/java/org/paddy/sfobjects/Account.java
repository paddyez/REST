package org.paddy.sfobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends SfObject {
    @JsonProperty("Contacts")
    private Contacts contacts;
    @JsonProperty("ShippingAddress")
    private Address shippingAddress;

    public String getJSON() {
        return "{\"attributes\":{\"type\":\"Account\"},\"Name\":\"" +
                getName() +
                "\",\"ShippingStreet\":\"Willy-Brandt-Platz 1-3\",\"ShippingStreet\":\"Willy-Brandt-Platz 1-3\",\"ShippingState\":\"\",\"ShippingPostalCode\":\"68161\",\"ShippingLongitude\":\"\",\"ShippingLatitude\":\"\",\"ShippingGeocodeAccuracy\":\"\",\"ShippingCountry\":\"Deutschland\",\"ShippingCity\":\"Mannheim\"}";
    }
}
