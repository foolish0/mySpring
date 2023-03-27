package com.myspring.context;

import com.myspring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.myspring.beans.factory.config.BeanFactoryPostProcessor;
import com.myspring.beans.factory.config.ConfigurableListableBeanFactory;
import com.myspring.beans.factory.support.DefaultListableBeanFactory;
import com.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import com.myspring.core.ClassPathXmlResource;
import com.myspring.core.Resource;

import java.util.ArrayList;
import java.util.List;

/**ppp
 * @author Gabriel
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    DefaultListableBeanFactory beanFactory;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

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
            try {
                refresh();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    void registerListener() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    }

    void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }
}
