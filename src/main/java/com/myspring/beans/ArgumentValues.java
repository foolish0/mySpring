package com.myspring.beans;

import java.util.*;

/**
 * @author Gabriel
 * @since 2023-03-16 22:34
 */
public class ArgumentValues {
    private final Map<Integer, ArgumentValue> indexedArgumentValues = new HashMap<>(0);
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();

    public ArgumentValues() {
    }

    private void addArgumentValue(Integer key, ArgumentValue newValue) {
        this.indexedArgumentValues.put(key, newValue);
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public boolean hasIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.genericArgumentValues.add(new ArgumentValue(value, type));
    }

    public void addGenericArgumentValue(ArgumentValue newValue) {
        if (newValue.getName() != null) {
            for (Iterator<ArgumentValue> it = this.genericArgumentValues.iterator(); it.hasNext(); ) {
                ArgumentValue currentValue = it.next();
                if (newValue.getName().equals(currentValue.getName())) {
                    it.remove();
                }
            }
        }
        this.genericArgumentValues.add(newValue);
    }

    public ArgumentValue getGenericArgumentValue(String requiredName) {
        for (ArgumentValue valueHolder : this.genericArgumentValues) {
            if (valueHolder.getValue() != null && (requiredName == null || !valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount() {
        return this.genericArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }
}
