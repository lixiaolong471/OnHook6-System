package com.mars.core.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixl on 2017/9/7.
 */
public interface CacheService {

    /**
     * 保存一个键值对
     * @param key
     * @param value
     * @return 如果此key已经存在value，则返回此value
     */
    Object put(String key, Object value);

    /**
     * 保存一个键值对,在unit为单位的time时长后被清除
     * @param key
     * @param value
     * @param unit 时间单位
     * @param time 时间值
     * @return 如果此key已经存在value，则返回此value
     */
    Object put(String key, Object value, TimeUnit unit, long time);

    /**
     * 移除缓存中的值
     * @param key
     * @return 返回此key对应的value
     */
    Object remove(String key);

    /**
     * 查询缓存中是否保存此key
     * @param key
     * @return 保存此key时返回<code>true<code>
     */
    boolean existKey(String key);

    /**
     * 获取指定key的值
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 入队
     * @param key
     * @param value
     * @return
     */
	Long in(String key, Object value);

	/**
	 * 出队
	 * @param key
	 * @return
	 */
	Object out(String key);

	/**
	 * 范围检索
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	List<Object> range(String key, int start, int end);

}
