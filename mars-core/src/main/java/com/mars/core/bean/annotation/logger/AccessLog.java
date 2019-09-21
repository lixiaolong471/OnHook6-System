package com.mars.core.bean.annotation.logger;

import java.lang.annotation.*;

/**
 * Created by lixl on 2017/3/22.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLog {

    public String value();

    public String phoneParamName() default "";

    public AccessLog.ClientType client();

    public static enum ClientType{
        PC,MOBILE
    }

}
