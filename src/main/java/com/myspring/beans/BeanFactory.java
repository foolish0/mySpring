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

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    /**
     * 获取Bean的类型
     *
     * @param name
     * @return
     */
    Class<?> getType(String name);
}
