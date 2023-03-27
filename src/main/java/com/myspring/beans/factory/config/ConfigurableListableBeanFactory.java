package com.myspring.beans.factory.config;

import com.myspring.beans.factory.ListableBeanFactory;

/**
 * @author Gabriel
 * @since 2023-03-22 22:18
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
}
