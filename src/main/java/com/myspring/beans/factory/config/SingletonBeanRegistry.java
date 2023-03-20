package com.myspring.beans.factory.config;

/**
 * @author Gabriel
 * @Description
 * @since 2023-03-16 20:30
 */
public interface SingletonBeanRegistry {
    /**
     * 注册单例Bean
     *
     * @param beanName 名称
     * @param singletonObject 单例对象
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取单例
     *
     * @param beanName 名称
     * @return 对象
     */
    Object getSingleton(String beanName);

    /**
     * 是否包含对象
     *
     * @param beanName 名称
     * @return boolean
     */
    boolean containsSingleton(String beanName);

    /**
     * 获取所有单例bean
     *
     * @return String[]
     */
    String[] getSingletonNames();
}
