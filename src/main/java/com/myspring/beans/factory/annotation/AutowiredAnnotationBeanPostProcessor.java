package com.myspring.beans.factory.annotation;

import com.myspring.beans.BeansException;
import com.myspring.beans.factory.config.BeanPostProcessor;

/**
 * @author Gabriel
 * @since 2023-03-20 21:48
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
