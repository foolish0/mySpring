package com.myspring.beans.factory.support;

import com.myspring.beans.BeansException;
import com.myspring.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.myspring.beans.factory.config.BeanDefinition;
import com.myspring.beans.factory.config.ConfigurableBeanFactory;
import com.myspring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel
 * @since 2023-03-22 22:54
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory {
    private ConfigurableBeanFactory parentBeanFactory;
    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return (String[]) this.beanDefinitionNames.toArray();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (String beanName : this.beanDefinitionNames) {
            boolean matchFound = false;
            BeanDefinition mbd = this.getBeanDefinition(beanName);
            Class<?> classToMatch = mbd.getClass();
            if (type.isAssignableFrom(classToMatch)) {
                matchFound = true;
            } else {
                matchFound = false;
            }

            if (matchFound) {
                result.add(beanName);
            }
        }

        return (String[]) result.toArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);
        }
        return result;
    }

    public void setParentBeanFactory(ConfigurableBeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object result = super.getBean(beanName);
        if (result == null) {
            result = this.parentBeanFactory.getBean(beanName);
        }
        return result;
    }
}
