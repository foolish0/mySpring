package com.myspring.beans.factory.config;

import com.myspring.beans.BeanFactory;
import com.myspring.beans.BeansException;

/**
 * @author Gabriel
 * @since 2023-03-27 20:33
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
