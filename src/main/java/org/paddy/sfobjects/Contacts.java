package org.paddy.sfobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Contacts {
    @JsonProperty("totalSize")
    private int totalSize;
    @JsonProperty("done")
    private boolean done;
    @JsonProperty("records")
    private Contact[] contactsA;
}
