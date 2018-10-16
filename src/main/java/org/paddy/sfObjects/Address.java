package org.paddy.sfObjects;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getGeocodeAccuracy() { return geocodeAccuracy; }
    public String getLatitude() { return latitude; }
    public String getLongitude() { return longitude; }
    public String getPostalCode() { return postalCode; }
    public String getState() { return state; }
    public String getStreet() { return street; }
    public Address() {}
    public Address(String city,
                           String country,
                           String geocodeAccuracy,
                           String latitude,
                           String longitude,
                           String postalCode,
                           String state,
                           String street) {
        this.city = city;
        this.country = country;
        this.geocodeAccuracy = geocodeAccuracy;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postalCode = postalCode;
        this.state = state;
        this.street = street;
    }
    @Override
    public String toString() {
        String toString = "";
        toString += "Street: " + street + "\n";
        toString += "Postcode City: " + postalCode + " " + city + "\n";
        toString += "State: " + state + "\n";
        toString += "Country: " + country + "\n";
        toString += "Latitude: " + latitude + "\n";
        toString += "Longitude: " + longitude + "\n";
        toString += "Accuracy: " + geocodeAccuracy + "\n";
        return toString;
    }
}
