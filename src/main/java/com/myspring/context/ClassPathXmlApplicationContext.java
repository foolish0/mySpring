package com.myspring.context;

import com.myspring.beans.BeanFactory;
import com.myspring.beans.BeansException;
import com.myspring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.myspring.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.myspring.beans.factory.config.BeanPostProcessor;
import com.myspring.beans.factory.support.DefaultListableBeanFactory;
import com.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.myspring.core.ClassPathXmlResource;
import com.myspring.core.Resource;

import java.util.ArrayList;
import java.util.List;

/**ppp
 * @author Gabriel
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    DefaultListableBeanFactory beanFactory;
    private final List<BeanPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    /**
     * context负责整合容器启动过程，读外部配置、解析Bean定义、创建BeanFactory
     *
     * @param fileName 配置文件名
     */
    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            refresh();
        }
    }

    public List<BeanPostProcessor> getBeanFactoryPostProcessors() {
        return this.beanFactoryPostProcessors;
    }

    public void addBeanFactoryPostProcessor(BeanPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    public void refresh() {
        registerBeanPostProcessors(this.beanFactory);
        onRefresh();
    }

    private void registerBeanPostProcessors(AbstractAutowireCapableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    private void onRefresh() {
        this.beanFactory.refresh();
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
    public Boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
