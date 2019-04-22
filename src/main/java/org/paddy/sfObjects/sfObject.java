package org.paddy.sfObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class sfObject implements Object {
    @JsonProperty("attributes")
    private Attributes attributes;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Id")
    private String id;

    public Attributes getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
