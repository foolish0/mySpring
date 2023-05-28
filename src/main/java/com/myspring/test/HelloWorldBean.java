package com.myspring.test;

import com.myspring.beans.factory.annotation.Autowired;
import com.myspring.test.service.AServiceImpl;
import com.myspring.test.service.BaseService;
import com.myspring.web.RequestMapping;

/**
 * @author Gabriel
 * @since 2023-05-21 15:43
 */
public class HelloWorldBean {
    @Autowired
    private BaseService baseservice;
    
    @Autowired
    private AServiceImpl aservice;
    
    @RequestMapping(value = "/get")
    public String doGet() {
        baseservice.sayHello();
        return "hello get";
    }

    @RequestMapping(value = "/post")
    public String doPost() {
        aservice.sayHello();
        return "hello post";
    }
}
