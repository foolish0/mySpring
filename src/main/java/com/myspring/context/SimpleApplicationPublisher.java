package com.myspring.context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel
 * @since 2023-03-27 20:23
 */
public class SimpleApplicationPublisher implements ApplicationEventPublisher{
    List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }
}
