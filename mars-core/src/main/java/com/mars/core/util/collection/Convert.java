package com.mars.core.util.collection;

/**
 * 数据类型转换
 * @param <T>
 * @param <E>
 */
public interface Convert< T, E > {
	E convert(T t);
}
