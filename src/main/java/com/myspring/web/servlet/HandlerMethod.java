package com.myspring.web.servlet;

import java.lang.reflect.Method;

/**
 * @author Gabriel
 * @since 2023-05-28 11:08
 */
public class HandlerMethod {
    private Method method;
    private Object bean;

    public HandlerMethod(Method method, Object bean) {
        this.method = method;
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
