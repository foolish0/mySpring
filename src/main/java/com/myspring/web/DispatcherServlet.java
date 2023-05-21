package com.myspring.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriel
 * @since 2023-03-28 23:25
 */
public class DispatcherServlet extends HttpServlet {
    // URL对应的MappingValue
    private Map<String, MappingValue> mappingValues;
    // 对应的类
    private Map<String, Class<?>> mappingClz = new HashMap<>();
    // 对应的方法
    private Map<String, Object> mappingObjs = new HashMap<>();

    private String sContextConfigLocation;

    public DispatcherServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Resource resource = new ClassPathXmlResource(xmlPath);
        XmlConfigReader reader = new XmlConfigReader();
        mappingValues = reader.loadConfig(resource);

        refresh();

    }

    protected void refresh() {
        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
            String id = entry.getKey();
            String className = entry.getValue().getClz();
            Class<?> clz = null;
            Object obj = null;
            try {
                clz = Class.forName(className);
                obj = clz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            mappingClz.put(id, clz);
            mappingObjs.put(id, obj);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (mappingValues.get(servletPath) == null) {
            return;
        }

        Class<?> clz = this.mappingClz.get(servletPath);
        Object obj = this.mappingObjs.get(servletPath);
        String method = mappingValues.get(servletPath).getMethod();
        Object result = "";

        try {
            Method clzMethod = clz.getMethod(method);
            result = clzMethod.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.getWriter().append(result.toString());
    }
}
