package com.myspring.web.servlet;

import com.myspring.web.RequestMapping;
import com.myspring.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author Gabriel
 * @since 2023-05-28 11:17
 */
public class RequestMappingHandlerMapping implements HandlerMapping{
    WebApplicationContext webApplicationContext;
    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
        initMapping();
    }

    private void initMapping() {
        Class<?> clazz = null;
        Object obj = null;
        String[] controllerNames = webApplicationContext.getBeanDefinitionNames();
        for (String controllerName : controllerNames) {
            try {
                clazz = Class.forName(controllerName);
                obj = this.webApplicationContext.getBean(controllerName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Method[] methods = clazz.getDeclaredMethods();
            if (methods != null) {
                for (Method method : methods) {
                    boolean hasRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (hasRequestMapping) {
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                        this.mappingRegistry.getMappingMethods().put(urlMapping, method);
                        this.mappingRegistry.getMappingObjs().put(urlMapping, obj);
                    }
                }
            }
        }
    }

    @Override
    public HandlerMethod getHandler(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        if (!this.mappingRegistry.getUrlMappingNames().contains(servletPath)) {
            return null;
        }
        Method method = this.mappingRegistry.getMappingMethods().get(servletPath);
        Object obj = this.mappingRegistry.getMappingObjs().get(servletPath);
        HandlerMethod handlerMethod = new HandlerMethod(method, obj);

        return handlerMethod;
    }
}
