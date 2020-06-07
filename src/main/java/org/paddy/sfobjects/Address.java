package org.paddy.sfobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Address {
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("geocodeAccuracy")
    private String geocodeAccuracy;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("state")
    private String state;
    @JsonProperty("street")
    private String street;
}
