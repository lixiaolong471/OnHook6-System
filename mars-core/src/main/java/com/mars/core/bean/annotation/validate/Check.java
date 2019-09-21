package com.mars.core.bean.annotation.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Check {
	/**
	 * 列名称,用于提示消息中的列表显示。
	 * @return
	 */
	String columnName() default "";

	/**
	 *
	 * 字段允许输入的最大长度 默认20
	 *
	 * @return
	 */
	int max() default 20;

	/**
	 * 字段允许输入的最小长度 默认0
	 * @return
	 */
	int min() default 0;

	/**
	 * 字段类型验证，例如mail:代表输入内容只能为邮箱,tel：输入内容只能为电话号码，ip，输入内容只能为ip地址;
	 * @return
	 */
	CheckType type() default CheckType.DEFAULT;

	/**
	 * 字段是否 默认可以为空
	 * @return
	 */
	boolean nullable() default true;
	

	
}
