package com.myspring.beans.factory.annotation;

import com.myspring.beans.BeansException;
import com.myspring.beans.factory.config.BeanPostProcessor;
import com.myspring.beans.factory.support.AutowireCapableBeanFactory;

import java.lang.reflect.Field;

/**
 * @author Gabriel
 * @since 2023-03-20 21:48
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private AutowireCapableBeanFactory beanFactory;

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;

        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            // 对每个属性进行判断，如果有@Autowired注解则进行处理
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    // 根据属性名查找同名bean
                    String fieldName = field.getName();
                    // todo: spring怎么做的，我这里属性名必须和注册的bean名称相同
                    Object autowiredObj = this.getBeanFactory().getBean(fieldName);
                    // 设置属性值，完成注入
                    try {
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
                        System.out.println("autowire " + fieldName + " for bean " + beanName);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
