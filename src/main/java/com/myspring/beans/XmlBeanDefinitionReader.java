package com.myspring.beans;

import com.myspring.core.Resource;
import org.dom4j.Element;

/**
 * @author Gabriel
 * @Description 解析好的Xml转换成BeanDefinition
 * @since 2023-03-15 23:01
 */
public class XmlBeanDefinitionReader {
    BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory factory) {
        this.beanFactory = factory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
