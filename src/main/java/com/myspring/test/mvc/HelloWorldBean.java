package com.myspring.test.mvc;

import com.myspring.web.RequestMapping;

/**
 * @author Gabriel
 * @since 2023-05-21 15:43
 */
public class HelloWorldBean {
    @RequestMapping(value = "/get")
    public String doGet() {
        return "hello get";
    }

    @RequestMapping(value = "/post")
    public String doPost() {
        return "hello post";
    }
}
