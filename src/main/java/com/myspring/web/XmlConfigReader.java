package com.myspring.web;

import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriel
 * @since 2023-03-28 23:19
 */
public class XmlConfigReader {
    public XmlConfigReader() {
    }

    public Map<String, MappingValue> loadConfig(Resource res) {
        Map<String, MappingValue> mappings = new HashMap<>();
        while (res.hasNext()) {
            Element next = (Element) res.next();
            String beanID = next.attributeValue("id");
            String beanClassName = next.attributeValue("class");
            String beanMethod = next.attributeValue("value");

            mappings.put(beanID, new MappingValue(beanID, beanClassName, beanMethod));
        }
        return mappings;
    }
}
