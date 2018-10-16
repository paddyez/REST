package org.paddy.sfObjects;
public interface Object {
    Attributes getAttributes();
    String getId();
    String getName();
    void setAttributes(Attributes attributes);
    void setId(String id);
    void setName(String name);
}
