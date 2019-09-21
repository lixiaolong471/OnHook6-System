package com.mars.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by lixl on 2017/8/22.
 */
public class AnnotationUtils {

    public static <T extends Annotation> T getAnnotation(Class<T> annclz,String classz, String methodName){
        Class clazz=null;
        try {
            clazz = Class.forName(classz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(clazz == null) return null;

        for(Method method : clazz.getDeclaredMethods()){
            if(methodName.equals(method.getName())){
               T t = method.getAnnotation(annclz);
                return t;
            }
        }
        return null;
    }
}
