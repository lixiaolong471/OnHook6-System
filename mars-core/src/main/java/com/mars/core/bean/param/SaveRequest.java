package com.mars.core.bean.param;

/**
 *  修改及新增参数bean
 * Created by lixl on 2017/7/20.
 */
public interface SaveRequest<T>{
    /**
     * 根据参数获取新建对象时的对象数据实体类
     * @return
     */
    public  T getSave();

    /**
     * 根据参数获取
     * @param t
     * @return
     */
    public T getUpdate(T t);
}
