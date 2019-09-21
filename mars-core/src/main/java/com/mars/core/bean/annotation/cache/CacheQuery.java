package com.mars.core.bean.annotation.cache;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheQuery {

    /**
     * 缓存存储键
      * @return
     */
   String value();


    /**
     * 時間單位：分鐘
     * 缓存时间，0的时候，为永久存储
     * @return
     */
   long time() default 0L;

}
