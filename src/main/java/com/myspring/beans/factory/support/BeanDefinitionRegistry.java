package com.myspring.beans.factory.support;

import com.myspring.beans.factory.config.BeanDefinition;

/**
 * @author Gabriel
 * @since 2023-03-16 23:23
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);
}
