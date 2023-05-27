package com.myspring.web;

import com.myspring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author Gabriel
 * @since 2023-05-27 17:16
 */
public class AnnotaionConfigWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;
    public AnnotaionConfigWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServlet() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
