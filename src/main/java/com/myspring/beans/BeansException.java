package com.myspring.beans;

/**
 * @author Gabriel
 * @Description Beans异常处理类
 * @since 2023-03-15 22:35
 */
public class BeansException extends Exception {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException() {
        super();
    }
}
