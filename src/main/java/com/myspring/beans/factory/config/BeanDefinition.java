package com.myspring.beans.factory.config;

import com.myspring.beans.PropertyValues;

/**
 * @author Gabriel
 */
public class BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    private boolean lazyInit = true;
    private String[] dependsOn;
    private ConstructorArgumentValues constructorArgumentValues;
    private PropertyValues propertyValues;
    private String initMethodName;
    private volatile Object beanClass;
    private String id;
    private String className;
    private String scope = SCOPE_SINGLETON;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public boolean hasBeanClass() {
        return this.beanClass instanceof Class;
    }

    public String getClassName() {
        return className;
    }

    public String getId() {
        return id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public boolean hasConstructorArgumentValues() {
        return !this.constructorArgumentValues.isEmpty();
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String... dependsOn) {
        this.dependsOn = dependsOn;
    }

    public ConstructorArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    public void setConstructorArgumentValues(ConstructorArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues != null ? constructorArgumentValues : new ConstructorArgumentValues();
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public Object getBeanClass() {
        return (Class<?>) beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
