package com.mars.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by lixl on 2017/2/21.
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringUtils.context = context;
    }

    public static <T> T getBean(Class<T> t){
        while(context == null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return context.getBean(t);
    }

    public static <T> T getBean(String beanName,Class<T> t){
        return context.getBean(beanName,t);
    }

    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }

    public static String[] getBeanDefinitionNames(){
        return context.getBeanDefinitionNames();
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> t){
        return context.getBeansOfType(t);
    }

}
