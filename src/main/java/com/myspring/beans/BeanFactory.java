package com.myspring.beans;

/**
 * @author Gabriel
 * @Description 基础容器，有两个特性：1、注册BeanDefinition，2、获取Bean
 * @since 2023-03-15 22:45
 */
public interface BeanFactory {
    /**
     * 获取Bean
     *
     * @param beanName Bean名称
     * @return 对象
     * @throws BeansException 异常
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 是否包含Bean
     *
     * @param name Bean名称
     * @return boolean
     */
    Boolean containsBean(String name);

    /**
     * 注册BeanDefinition
     *
     * @param beanName bean名称
     * @param obj 与beanName对应的Bean
     */
    void registerBean(String beanName, Object obj);
}
