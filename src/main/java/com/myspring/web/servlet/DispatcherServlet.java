package com.myspring.web.servlet;

import com.myspring.web.AnnotaionConfigWebApplicationContext;
import com.myspring.web.WebApplicationContext;
import com.myspring.web.XmlScanComponentHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * @author Gabriel
 * @since 2023-05-28 10:49
 */
public class DispatcherServlet extends HttpServlet {
    // 需要扫描的package列表
    private List<String> packageNames = new ArrayList<>();
    // controller名称和对象映射关系
    private Map<String, Object> controllerObjs = new HashMap<>();
    // controller名称
    private List<String> controllerNames = new ArrayList<>();
    // controller名称和类映射关系
    private Map<String, Class<?>> controllerClasses = new HashMap<>();

    private String sContextConfigLocation;
    // DispatcherServlet启动的上下文
    private WebApplicationContext webApplicationContext;
    // Listener启动的上下文
    private WebApplicationContext parentApplicationContext;

    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";

    public DispatcherServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONFIG_ATTRIBUTE);

        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        this.webApplicationContext = new AnnotaionConfigWebApplicationContext(sContextConfigLocation, this.parentApplicationContext);

        refresh();

    }

    protected void refresh() {
        initController();
        initHandlerMapping();
        initHandlerAdapter();
    }

    private void initHandlerAdapter() {
        this.handlerAdapter = new RequestMappingHandlerAdapter(this.webApplicationContext);
    }

    private void initHandlerMapping() {
        this.handlerMapping = new RequestMappingHandlerMapping(this.webApplicationContext);
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
                obj = this.webApplicationContext.getBean(controllerName);
                this.controllerObjs.put(controllerName, obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);

        try {
            doDispatcher(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {
        HttpServletRequest processedRequest = null;
        processedRequest = req;
        HandlerMethod handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }
        HandlerAdapter ha = this.handlerAdapter;
        ha.handle(processedRequest, resp, handlerMethod);
    }
}
