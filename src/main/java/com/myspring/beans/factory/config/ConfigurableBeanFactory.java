package com.myspring.beans.factory.config;

import com.myspring.beans.BeanFactory;

/**
 * @author Gabriel
 * @since 2023-03-22 22:14
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependBenaName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);

    boolean hasDependentBean(String beanName);
}
