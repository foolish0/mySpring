package com.myspring.beans;

/**
 * @author Gabriel
 * @since 2023-03-16 22:27
 */
public class PropertyValue {
    private String type;
    private String name;
    private Object value;

    public PropertyValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
