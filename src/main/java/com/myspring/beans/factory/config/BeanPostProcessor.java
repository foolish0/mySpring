package com.myspring.beans.factory.config;

import com.myspring.beans.BeansException;

/**
 * @author Gabriel
 * @since 2023-03-20 21:40
 */
public interface BeanPostProcessor {
    /**
     * Bean初始化之前
     *
     * @param bean bean对象
     * @param beanName 名称
     * @return 对象
     * @throws BeansException exception
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * Bean初始化之后
     *
     * @param bean bean对象
     * @param beanName 名称
     * @return 对象
     * @throws BeansException exception
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
