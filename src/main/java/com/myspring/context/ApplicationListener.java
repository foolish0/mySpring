package com.myspring.context;

import java.util.EventListener;

/**
 * @author Gabriel
 * @since 2023-03-27 20:15
 */
public class ApplicationListener implements EventListener {
    void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }
}
