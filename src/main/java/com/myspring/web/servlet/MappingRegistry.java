package com.myspring.web.servlet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel
 * @since 2023-05-28 11:13
 */
public class MappingRegistry {
    // 自定义的@RequestMapping名称列表（URL）
    private List<String> urlMappingNames = new ArrayList<>();
    // URL和对象映射关系
    private Map<String, Object> mappingObjs = new HashMap<>();
    // URL和方法映射关系
    private Map<String, Method> mappingMethods = new HashMap<>();

    public List<String> getUrlMappingNames() {
        return urlMappingNames;
    }

    public void setUrlMappingNames(List<String> urlMappingNames) {
        this.urlMappingNames = urlMappingNames;
    }

    public Map<String, Object> getMappingObjs() {
        return mappingObjs;
    }

    public void setMappingObjs(Map<String, Object> mappingObjs) {
        this.mappingObjs = mappingObjs;
    }

    public Map<String, Method> getMappingMethods() {
        return mappingMethods;
    }

    public void setMappingMethods(Map<String, Method> mappingMethods) {
        this.mappingMethods = mappingMethods;
    }
}
