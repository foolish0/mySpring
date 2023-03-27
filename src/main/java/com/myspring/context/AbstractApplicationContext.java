package com.myspring.context;

import com.myspring.beans.BeansException;
import com.myspring.beans.factory.config.BeanFactoryPostProcessor;
import com.myspring.beans.factory.config.ConfigurableListableBeanFactory;
import com.myspring.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Gabriel
 * @since 2023-03-27 20:38
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private Environment environment;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
    private long startupDate;
    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return beanFactoryPostProcessors;
    }

    @Override
    public void refresh() throws IllegalStateException, BeansException {
        postProcessBeanFactory(getBeanFactory());
        registerBeanPostProcessors(getBeanFactory());
        initApplicationEventPublisher();
        onRefresh();
        registerListener();
        finishRefresh();
    }

    abstract void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);

    abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory);

    abstract void initApplicationEventPublisher();

    abstract void onRefresh();

    abstract void registerListener();

    abstract void finishRefresh();

    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return true;
    }
}
