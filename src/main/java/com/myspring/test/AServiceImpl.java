package com.myspring.test;

import com.myspring.test.AService;

public class AServiceImpl implements AService {
    @Override
    public void sayHello() {
        System.out.println("a service say hello");
    }
}
