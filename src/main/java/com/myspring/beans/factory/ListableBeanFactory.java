package com.myspring.beans.factory;

import com.myspring.beans.BeanFactory;
import com.myspring.beans.BeansException;

import java.util.Map;

/**
 * @author Gabriel
 * @since 2023-03-22 22:13
 */
public interface ListableBeanFactory extends BeanFactory {
    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
