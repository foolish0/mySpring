package com.myspring.test.service;

import com.myspring.beans.factory.annotation.Autowired;
import com.myspring.test.service.BaseBaseService;

/**
 * @author Gabriel
 * @since 2023-03-19 16:37
 */
public class BaseService {
    @Autowired
    private BaseBaseService basebaseservice;

    public BaseService() {

    }

    public BaseBaseService getBasebaseservice() {
        return basebaseservice;
    }

    public void setBasebaseservice(BaseBaseService basebaseservice) {
        this.basebaseservice = basebaseservice;
    }
    public void sayHello() {
        System.out.print("Base Service says hello");
        basebaseservice.sayHello();
    }
    public String getHello() {
        return "Base Service get Hello.";
    }
}
