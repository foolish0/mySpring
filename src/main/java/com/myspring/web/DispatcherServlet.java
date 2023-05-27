package com.myspring.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * @author Gabriel
 * @since 2023-03-28 23:25
 */
public class DispatcherServlet extends HttpServlet {
    /**
     * 扩展MVC，直接扫描包
     * */
    // 需要扫描的package列表
    private List<String> packageNames = new ArrayList<>();
    // controller名称和对象映射关系
    private Map<String, Object> controllerObjs = new HashMap<>();
    // controller名称
    private List<String> controllerNames = new ArrayList<>();
    // controller名称和类映射关系
    private Map<String, Class<?>> controllerClasses = new HashMap<>();
    // 自定义的@RequestMapping名称列表（URL）
    private List<String> urlMappingNames = new ArrayList<>();
    // URL和对象映射关系
    private Map<String, Object> mappingObjs = new HashMap<>();
    // URL和方法映射关系
    private Map<String, Method> mappingMethods = new HashMap<>();
    
    /**
     * 原始MVC
     * */
    // URL对应的MappingValue
//    private Map<String, MappingValue> mappingValues;
    // 对应的类
//    private Map<String, Class<?>> mappingClz = new HashMap<>();
    // 对应的方法
//    private Map<String, Object> mappingObjs = new HashMap<>();

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

        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
//        Resource resource = new ClassPathXmlResource(xmlPath);
//        XmlConfigReader reader = new XmlConfigReader();
//        mappingValues = reader.loadConfig(resource);

        refresh();

    }

    protected void refresh() {
//        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
//            String id = entry.getKey();
//            String className = entry.getValue().getClz();
//            Class<?> clz = null;
//            Object obj = null;
//            try {
//                clz = Class.forName(className);
//                obj = clz.newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            mappingClz.put(id, clz);
//            mappingObjs.put(id, obj);
//        }
        
        // 初始化controller
        initController();
        // 初始化url映射
        initMapping();
    }

    private void initController() {
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : this.controllerNames) {
            // 类
            Class<?> clz = null;
            // 对象
            Object obj = null;
            try {
                clz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName, clz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                obj = clz.newInstance();
                this.controllerObjs.put(controllerName, obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packageNames) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URI uri = null;
        try {
            uri = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File dir = new File(uri);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            } 
        }
        return tempControllerNames;
    }

    private void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clazz.getMethods();
            if (methods != null) {
                for (Method method : methods) {
                    boolean hasRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (hasRequestMapping) {
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        this.urlMappingNames.add(urlMapping);
                        this.mappingMethods.put(urlMapping, method);
                        this.mappingObjs.put(urlMapping, obj);
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
//        if (mappingValues.get(servletPath) == null) {
//            return;
//        }
        if (!this.urlMappingNames.contains(servletPath)) {
            return;
        }
        
//        Class<?> clz = this.mappingClz.get(servletPath);
        Object obj = this.mappingObjs.get(servletPath);
//        String method = mappingValues.get(servletPath).getMethod();
        Object result = "";

        try {
//            Method clzMethod = clz.getMethod(method);
            Method clzMethod = this.mappingMethods.get(servletPath);
            result = clzMethod.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.getWriter().append(result.toString());
    }
}
