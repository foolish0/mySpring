package com.myspring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel
 * @since 2023-03-16 22:58
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    public List<PropertyValue> getPropertyValueList() {
        return this.propertyValueList;
    }

    public int size() {
        return this.propertyValueList.size();
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    public void addPropertyValue(String propertyType, String propertyName, Object property, boolean isRef) {
        addPropertyValue(new PropertyValue(propertyType, propertyName, property, isRef));
    }

    public void removePropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.remove(propertyValue);
    }

    public void removePropertyValue(String propertyName) {
        this.propertyValueList.remove(getPropertyValue(propertyName));
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[this.propertyValueList.size()]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

    public Object get(String propertyName) {
        PropertyValue pv = getPropertyValue(propertyName);
        return pv != null ? pv.getValue() : null;
    }

    public boolean contains(String propertyName) {
        return getPropertyValue(propertyName) != null;
    }

    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }
}
