package com.myspring.test;

import com.myspring.beans.factory.annotation.Autowired;
import com.myspring.web.RequestMapping;

/**
 * @author Gabriel
 * @since 2023-05-21 15:43
 */
public class HelloWorldBean {
    @Autowired
    private AService aservice;
    
    @RequestMapping(value = "/get")
    public String doGet() {
        aservice.sayHello();
        return "hello get";
    }

    @RequestMapping(value = "/post")
    public String doPost() {
        return "hello post";
    }
}
