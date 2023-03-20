package com.myspring.beans.factory.config;

import java.util.*;

/**
 * @author Gabriel
 * @since 2023-03-16 22:34
 */
public class ConstructorArgumentValues {
    private final Map<Integer, ConstructorArgumentValue> indexedArgumentValues = new HashMap<>(0);
    private final List<ConstructorArgumentValue> genericConstructorArgumentValues = new LinkedList<>();

    public ConstructorArgumentValues() {
    }

    public void addArgumentValue(Integer key, ConstructorArgumentValue newValue) {
        this.indexedArgumentValues.put(key, newValue);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public boolean hasIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.genericConstructorArgumentValues.add(new ConstructorArgumentValue(value, type));
    }

    public void addGenericArgumentValue(ConstructorArgumentValue newValue) {
        if (newValue.getName() != null) {
            for (Iterator<ConstructorArgumentValue> it = this.genericConstructorArgumentValues.iterator(); it.hasNext(); ) {
                ConstructorArgumentValue currentValue = it.next();
                if (newValue.getName().equals(currentValue.getName())) {
                    it.remove();
                }
            }
        }
        this.genericConstructorArgumentValues.add(newValue);
    }

    public ConstructorArgumentValue getGenericArgumentValue(String requiredName) {
        for (ConstructorArgumentValue valueHolder : this.genericConstructorArgumentValues) {
            if (valueHolder.getValue() != null && (requiredName == null || !valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount() {
        return this.genericConstructorArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericConstructorArgumentValues.isEmpty();
    }
}
