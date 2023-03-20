package com.myspring.beans.factory.support;

import com.myspring.beans.BeanFactory;
import com.myspring.beans.BeansException;
import com.myspring.beans.PropertyValue;
import com.myspring.beans.PropertyValues;
import com.myspring.beans.factory.config.BeanDefinition;
import com.myspring.beans.factory.config.ConstructorArgumentValue;
import com.myspring.beans.factory.config.ConstructorArgumentValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gabriel
 * @since 2023-03-20 22:00
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory,BeanDefinitionRegistry {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames = new ArrayList<>();
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    public AbstractBeanFactory() {

    }

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿实例
        Object singleton = this.getSingleton(beanName);
        // 没有这个bean的实例，则获取它的定义来创建实例
        if (singleton == null) {
            // 没有实例，尝试从毛坯实例中获取
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) {
                // 毛坯也没有，创建bean实例并注册
                System.out.println("get bean null ---------- " + beanName);
                // 获取bean的定义
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                singleton = createBean(beanDefinition);
                this.registerSingleton(beanName, singleton);
                // 进行beanPostProcessor处理
                // step 1：before
                applyBeanPostProcessorBeforeInitialization(singleton, beanName);
                // step 2：init
                if (beanDefinition.getInitMethodName() != null && !"".equals(beanDefinition.getInitMethodName())) {
                    invokeInitMethod(beanDefinition, singleton);
                }
                // step 3：after
                applyBeanPostProcessorAfterInitialization(singleton, beanName);
            }
        }
        return singleton;
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        // 创建毛坯bean实例
        Object obj = doCreateBean(beanDefinition);
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 完善bean，主要是处理属性
        populateBean(beanDefinition, clz, obj);
        return obj;
    }

    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;

        try {
            clz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            if (constructorArgumentValues != null) {
                if (!constructorArgumentValues.isEmpty()) {
                    Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
                    Object[] paramValues = new Object[constructorArgumentValues.getArgumentCount()];
                    for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                        ConstructorArgumentValue constructorArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                        if ("String".equals(constructorArgumentValue.getType()) || "java.lang.String".equals(constructorArgumentValue.getType())) {
                            paramTypes[i] = String.class;
                            paramValues[i] = constructorArgumentValue.getValue();
                        } else if ("Integer".equals(constructorArgumentValue.getType()) || "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                            paramTypes[i] = Integer.class;
                            paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                        } else if ("int".equals(constructorArgumentValue.getType())) {
                            paramTypes[i] = int.class;
                            paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                        } else {
                            paramTypes[i] = String.class;
                            paramValues[i] = constructorArgumentValue.getValue();
                        }
                    }
                    try {
                        // 按照特性构造器创建实例
                        con = clz.getConstructor(paramTypes);
                        obj = con.newInstance(paramValues);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // 没有参数直接创建实例
                    obj = clz.newInstance();
                }
            } else {
                obj = clz.newInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(beanDefinition.getId() + " bean created. " + beanDefinition.getClassName() + " : " + obj);
        return obj;
    }

    private void populateBean(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        handleProperties(beanDefinition, clz, obj);
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pType = propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.isRef();
                Class<?>[] types = new Class<?>[1];
                Object[] values = new Object[1];

                if (!isRef) {
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        types[0] = String.class;
                    } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        types[0] = Integer.class;
                    } else if ("int".equals(pType)) {
                        types[0] = int.class;
                    } else {
                        types[0] = String.class;
                    }
                    values[0] = pValue;
                } else {
                    try {
                        types[0] = Class.forName(pType);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        values[0] = getBean((String) pValue);
                    } catch (BeansException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 按照setter的命名规范查找set方法，并调用
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = null;
                try {
                    method = clz.getMethod(methodName, types);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                try {
                    method.invoke(obj, values);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void invokeInitMethod(BeanDefinition beanDefinition, Object obj) {
        Class<?> clz = beanDefinition.getClass();
        Method method = null;
        try {
            method = clz.getMethod(beanDefinition.getInitMethodName());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    abstract public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    abstract public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }
}
