package com.myspring.context;

import java.util.EventObject;

/**
 * 监控容器的启动状态，增加时间监听
 * 监听应用事件
 *
 * @author Gabriel
 * @since 2023-03-16 21:11
 */
public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
