package com.myspring.beans.factory.xml;

import com.myspring.beans.PropertyValue;
import com.myspring.beans.PropertyValues;
import com.myspring.beans.factory.config.BeanDefinition;
import com.myspring.beans.factory.config.ConstructorArgumentValue;
import com.myspring.beans.factory.config.ConstructorArgumentValues;
import com.myspring.beans.factory.support.AutowireCapableBeanFactory;
import com.myspring.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel
 * @Description 解析好的Xml转换成BeanDefinition
 * @since 2023-03-15 23:01
 */
public class XmlBeanDefinitionReader {
    AutowireCapableBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            // 处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pv = "";
                boolean isRef = false;
                if (pValue != null && !"".equals(pValue)) {
                    pv = pValue;
                } else if (pRef != null && !"".equals(pRef)) {
                    isRef = true;
                    pv = pRef;
                    refs.add(pRef);
                }
                PVS.addPropertyValue(new PropertyValue(pType, pName, pv, isRef));
            }
            beanDefinition.setPropertyValues(PVS);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);

            // 处理构造函数
            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues AVS = new ConstructorArgumentValues();
            int i = 0;
            for (Element e : constructorElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                AVS.addGenericArgumentValue(new ConstructorArgumentValue(aValue, aType, aName));
                // todo：spring自己是如何添加有序列的参数对象的？
                AVS.addArgumentValue(i++, new ConstructorArgumentValue(aValue, aType, aName));
            }
            beanDefinition.setConstructorArgumentValues(AVS);

            this.beanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }
}
