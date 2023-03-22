package com.myspring.beans.factory.support;

import com.myspring.beans.BeansException;
import com.myspring.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.myspring.beans.factory.config.BeanPostProcessor;
import com.myspring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Map;

/**
 * @author Gabriel
 * @since 2023-03-22 22:54
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {
    @Override
    public int getBeanDefinitionCount() {
        return 0;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return new String[0];
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return null;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {

    }

    @Override
    public void registerDependentBean(String beanName, String dependBenaName) {

    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return new String[0];
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return new String[0];
    }
}
