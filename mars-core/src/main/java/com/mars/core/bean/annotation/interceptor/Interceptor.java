package com.mars.core.bean.annotation.interceptor;

import java.lang.annotation.*;

/**
 * Created by lixl on 2017/3/24.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Interceptor {
}
