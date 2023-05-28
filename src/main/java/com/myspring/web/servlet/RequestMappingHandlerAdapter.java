package com.myspring.web.servlet;

import com.myspring.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Gabriel
 * @since 2023-05-28 11:17
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter{
    WebApplicationContext webApplicationContext;

    public RequestMappingHandlerAdapter(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        handleInternal(request, response, (HandlerMethod) handler);
    }

    private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        Method method = handler.getMethod();
        Object bean = handler.getBean();
        Object result = null;
        try {
            result = method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            response.getWriter().append(Objects.requireNonNull(result).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
