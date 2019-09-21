package com.mars.core.service;

import com.mars.core.bean.param.PageRequest;
import com.mars.core.bean.param.Request;
import com.mars.core.bean.param.SaveRequest;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixl on 2017/5/30.
 */
public interface CommonService<T> {
    /**
     * 保存数据
     * @param t
     */
    public T save(T t);

    /**
     * 更新数据元素
     * @param t
     */
    public T update(T t);

    /**
     * 保存更新数据元素
     * @param t
     */
    public T saveOrUpdate(SaveRequest<T> t);

    /**
     * 删除数据元素
     * @param t
     */
    public void delete(T t);

    /**
     * 删除数据，根据id
     * @param id
     */
    public void deleteById(Serializable id);

    /**
     * 删除数据，根据id
     * @param ids
     */
    public void batchDelete(String ids);

    /**
     * 查找数据元素
     * @param id
     * @return
     */
    public T findOne(Serializable id);

    /**
     * 查询数据元素，分页查询方式
     * @param request
     * @return
     */
    public Page<T> findByCondtion(PageRequest request);
    
    /**
     * 查询数据元素，分页查询方式(没统计)
     * @param request
     * @return
     */
    public List findListByCondtion(PageRequest request);

    /**
     * 查询数据元素，list
     * @param request
     * @return
     */
    public List<T> findByCondtion(Request request);

    /**
     * 根据当前数据元素查找
     * @param example
     * @param <T>
     * @return
     */
    public <T> List<T> queryByExample(T example);

    /**
     * 查找数据元素，当前第一个
     * @param entity
     * @param <T>
     * @return
     */
    public <T> T queryByExampleFirst(T entity);


    /**
     * 根据属性查找对象
     * @param properity
     * @param value
     * @return
     */
    public List<T> queryByExample(String[] properity, Object[] value);

    /**
     * 根据属性查找对象
     * @param properity
     * @param value
     * @return
     */
    public T queryByExampleFirst(String[] properity, Object[] value);
}
