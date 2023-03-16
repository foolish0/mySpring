package com.myspring.context;

/**
 * 监控容器的启动状态，增加时间监听
 *
 * @author Gabriel
 * @since 2023-03-16 21:06
 */
public interface ApplicationEventPublisher {
    /**
     * 发布事件
     *
     * @param event 事件
     */
    void publishEvent(ApplicationEvent event);
}
