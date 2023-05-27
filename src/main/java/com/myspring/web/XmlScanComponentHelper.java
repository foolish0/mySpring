package com.myspring.web;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Gabriel
 * @since 2023-05-24 23:29
 */
public class XmlScanComponentHelper {
    public static List<String> getNodeValue(URL xmlPath) {
        List<String> packages = new ArrayList<>();
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(xmlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        Iterator iterator = root.elementIterator();
        while (iterator.hasNext()) {
            Element el = (Element) iterator.next();
            packages.add(el.attributeValue("base-package"));
        }
        return packages;
    }
    
    
}
