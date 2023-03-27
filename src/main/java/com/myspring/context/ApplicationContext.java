package com.myspring.context;

import com.myspring.beans.BeansException;
import com.myspring.beans.factory.ListableBeanFactory;
import com.myspring.beans.factory.config.BeanFactoryPostProcessor;
import com.myspring.beans.factory.config.ConfigurableBeanFactory;
import com.myspring.beans.factory.config.ConfigurableListableBeanFactory;
import com.myspring.core.env.Environment;
import com.myspring.core.env.EnvironmentCapable;

/**
 * @author Gabriel
 * @since 2023-03-27 20:25
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {
    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws IllegalStateException, BeansException;

    void close();

    boolean isActive();
}
