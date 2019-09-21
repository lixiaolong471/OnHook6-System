package com.mars.core.bean.annotation.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lixl on 2017/2/28.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Excel {

    /** 列名 */
    String name() default "";

    /**
     * 二级列标识 ，如果要获取子对象，用户自定义对象属性
     * @return
     */
    String column() default "";

    /** 宽度 */
    int width() default 20;

    /** 忽略导出该字段 */
    boolean skipExport() default false;

    /** 忽略导入该字段 */
    boolean skipImport() default false;

    /** 是否可以为空 */
    boolean required() default false;

}
