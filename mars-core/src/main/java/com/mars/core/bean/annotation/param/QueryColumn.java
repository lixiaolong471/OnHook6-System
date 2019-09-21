package com.mars.core.bean.annotation.param;

import java.lang.annotation.*;

/**
 * Created by lixl on 2016/12/20.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface QueryColumn {
    /**
     *
     * 查询条件字段名称，默认为当前字段名称
     * @return
     */
    String name() default "";

    /**
     * or 查询 中的多个条件组合
     * 例如 （a = 1 or b = 1 or c = 1）
     * @return
     */
    String[] names() default {};

    /**
     * 查询条件操作方式 默认 ‘=’
     * @return
     */
    OptionType option() default OptionType.EQ;


}
