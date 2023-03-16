package com.myspring.beans;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gabriel
 * @Description 一个简单的BeanFactory实现类
 * @since 2023-03-15 23:30
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory{
    //    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
//    private List<String> beanNames = new ArrayList<>();
//    private Map<String, Object> singletons = new HashMap<>();
    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);
    public SimpleBeanFactory() {
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿实例
        Object singleton = this.getSingleton(beanName);
        // 没有这个bean的实例，则获取它的定义来创建实例
        if (singleton == null) {
            // 获取bean的定义
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (beanDefinition == null) {
                throw new BeansException("No bean.");
            }
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return singleton;
    }

    @Override
    public Boolean containsBean(String name) {
        return containsSingleton(name);
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }
}
