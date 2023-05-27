package com.myspring.web;

import com.myspring.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author Gabriel
 * @since 2023-05-27 17:01
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONFIG_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";
    ServletContext getServlet();

    void setServletContext(ServletContext servletContext);
}
