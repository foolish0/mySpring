package com.myspring.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel
 * @Description 一个简单的BeanFactory实现类
 * @since 2023-03-15 23:30
 */
public class SimpleBeanFactory implements BeanFactory{
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    public SimpleBeanFactory() {
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿实例
        Object singleton = singletons.get(beanName);
        if (singleton == null) {
            int i = beanNames.indexOf(beanName);
            if (i == -1) {
                throw new BeansException();
            } else {
                BeanDefinition beanDefinition = beanDefinitions.get(i);
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                singletons.put(beanDefinition.getId(), singleton);
            }
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
