package com.mars.core.bean.annotation.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OpLog {

	public String value();
	
	public Type type();
	
	public String module();

	public boolean post() default true;

	public static enum Type {

		DELETE, ADD, MODIFY, QUERY, LOGIN,IMPORT,EXPORT;

		public String getName(){
			String name = "";
			switch (this){
				case ADD:name = "新增";break;
				case DELETE:name = "删除";break;
				case MODIFY:name = "修改";break;
				case QUERY:name = "查询";break;
				case LOGIN:name = "登录";break;
				case IMPORT:name = "导入";break;
				case EXPORT:name = "导出";break;
				default:name = "其他";break;
			}
			return name;
		}
	}
	
}
