package com.myspring.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gabriel
 * @since 2023-05-28 11:07
 */
public interface HandlerMapping {
    HandlerMethod getHandler(HttpServletRequest request);
}
