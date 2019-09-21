package com.mars.core.bean.annotation.tree;

import java.lang.annotation.*;

/**
 * Created by lixl on 2017/7/17.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Node {
    String name() default "";
    boolean key() default false;
}
