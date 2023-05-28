package com.myspring.web;

import com.myspring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author Gabriel
 * @since 2023-05-28 10:36
 */
public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
