package com.myspring.test.service;

import com.myspring.test.service.AServiceImpl;

/**
 * @author Gabriel
 * @since 2023-03-19 19:13
 */
public class BaseBaseService {
    private AServiceImpl as;

    public AServiceImpl getAs() {
        return as;
    }

    public void setAs(AServiceImpl as) {
        this.as = as;
    }

    public BaseBaseService() {
    }

    public void sayHello() {
        System.out.println("Base Base Service says hello");

    }
}
