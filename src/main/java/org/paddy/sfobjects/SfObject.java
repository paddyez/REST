package org.paddy.sfobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SfObject {
    @JsonProperty("attributes")
    private Attributes attributes;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Id")
    private String id;
}
