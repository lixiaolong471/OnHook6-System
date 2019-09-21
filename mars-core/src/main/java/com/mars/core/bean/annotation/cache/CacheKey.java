package com.mars.core.bean.annotation.cache;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheKey {

    String value();
}
