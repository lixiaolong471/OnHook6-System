package com.mars.core.bean.annotation.authority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleSecurity {
	/**
	 * 和T_AUTHORITY表里AUTHORITYMARK相同    表示具有某功能权限
	 * @return
	 */
	public String[] roleMark()default {};
	/**
	 * url中包含用户id的变量名
	 * @return
	 */
	public String ownerId()default "";
}
