package com.myspring.test;

import com.myspring.beans.factory.annotation.Autowired;

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
}
