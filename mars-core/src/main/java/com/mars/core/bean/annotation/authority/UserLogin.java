package com.mars.core.bean.annotation.authority;

import java.lang.annotation.*;

/**
 * 
 * @author tantx
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserLogin {

	boolean refreshSession() default false;
}
