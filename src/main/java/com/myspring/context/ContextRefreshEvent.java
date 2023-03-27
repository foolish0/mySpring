package com.myspring.context;

/**
 * @author Gabriel
 * @since 2023-03-27 20:18
 */
public class ContextRefreshEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshEvent(Object source) {
        super(source);
    }

    public String toString() {
        return this.msg;
    }
}
