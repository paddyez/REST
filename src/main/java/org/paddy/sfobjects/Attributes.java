package org.paddy.sfobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class Attributes {
    @JsonProperty("type")
    private String type;
    @JsonProperty("url")
    private String url;
}
