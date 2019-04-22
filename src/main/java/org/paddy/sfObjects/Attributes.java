package org.paddy.sfObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

class Attributes {
    @JsonProperty("type")
    private String type;
    @JsonProperty("url")
    private String url;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public Attributes() {
    }

    public Attributes(String type, String url) {
        this.type = type;
        this.url = url;
    }
}
