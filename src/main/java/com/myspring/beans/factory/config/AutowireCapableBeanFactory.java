package com.myspring.beans.factory.config;

import com.myspring.beans.BeanFactory;
import com.myspring.beans.BeansException;

/**
 * @author Gabriel
 * @since 2023-03-22 22:20
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
