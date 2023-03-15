package com.myspring.context;

import com.myspring.beans.*;
import com.myspring.core.ClassPathXmlResource;
import com.myspring.core.Resource;

/**
 * @author Gabriel
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    BeanFactory beanFactory;

    /**
     * context负责整合容器启动过程，读外部配置、解析Bean定义、创建BeanFactory
     *
     * @param fileName 配置文件名
     */
    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    /**
     * context对外提供getBean，实现就是调用BeanFactory相应方法
     *
     * @param beanName Bean名称
     * @return Bean对象
     */
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
