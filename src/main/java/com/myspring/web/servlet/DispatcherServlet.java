package com.myspring.web.servlet;

import com.myspring.beans.BeansException;
import com.myspring.beans.factory.annotation.Autowired;
import com.myspring.web.AnnotaionConfigWebApplicationContext;
import com.myspring.web.RequestMapping;
import com.myspring.web.WebApplicationContext;
import com.myspring.web.XmlScanComponentHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author Gabriel
 * @since 2023-05-28 10:49
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

    private String sContextConfigLocation;

    // DispatcherServlet启动的上下文
    private WebApplicationContext webApplicationContext;
    // Listener启动的上下文
    private WebApplicationContext parentWebApplicationContext;

    public DispatcherServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.parentWebApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONFIG_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        this.webApplicationContext = new AnnotaionConfigWebApplicationContext(sContextConfigLocation, this.parentWebApplicationContext);

        refresh();

    }

    protected void refresh() {
        // 初始化controller
        initController();
        // 初始化url映射
        initMapping();
    }

    private void initController() {
        this.controllerNames = Arrays.asList(this.webApplicationContext.getBeanDefinitionNames());
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
                // todo: 暂时粗暴处理，接口不能newInstance
                if (!clz.isInterface()) {
                    obj = clz.newInstance();
                    populateBean(obj);
                    this.controllerObjs.put(controllerName, obj);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void populateBean(Object bean) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    String fieldName = field.getName();
                    Object autowiredObj = this.webApplicationContext.getBean(fieldName);
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    private void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();
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
        if (!this.urlMappingNames.contains(servletPath)) {
            return;
        }

        Object obj = this.mappingObjs.get(servletPath);
        Object result = "";

        try {
            Method clzMethod = this.mappingMethods.get(servletPath);
            result = clzMethod.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.getWriter().append(result.toString());
    }
}
