package com.mars.core.service;
import com.mars.core.bean.param.PageRequest;
import com.mars.core.bean.param.Request;
import com.mars.core.bean.param.SaveRequest;
import com.mars.core.dao.CommonDao;
import com.mars.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.List;

/**
 * Created by lixl on 2017/5/30.
 */
@Transactional
public abstract class CommonServiceSupport<T> implements CommonService<T>{

    private Class<T> entityClass;

    public CommonServiceSupport(){
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    private String entityName(){
        if(StringUtils.isNotEmpty(entityClass.getName())){
            return entityClass.getSimpleName();
        }
        return null;
    }

    @Autowired
    protected CommonDao dao;

    @Override
    public T save(T o) {
        dao.save(o);
        return o;
    }

    @Override
    public T update(T o) {
        return dao.update(o);
    }

    @Override
    public T saveOrUpdate(SaveRequest<T> request) {
        try {
            Method[] methods = request.getClass().getMethods();
            for(Method m:methods){
                 if(m.getAnnotation(Id.class) != null){
                     Serializable id = (Serializable)m.invoke(request);
                     if(id == null){
                         return save(request.getSave());
                     }else{
                         T entity = findOne(id);
                         return update(request.getUpdate(entity));
                     }
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Object o) {
        dao.delete(o);
    }

    @Override
    public void deleteById(Serializable id) {
        T obj = dao.findOne(entityClass,id);
        if(obj != null){
            dao.delete(obj);
        }
    }

    @Override
    public void batchDelete(String ids) {
        Class idType = getIdClass();
        for(String id:ids.split(",")){
            if(idType.equals(Long.class)){
                deleteById(Long.parseLong(id));
            }else if(idType.equals(Integer.class)){
                deleteById(Integer.parseInt(id));
            }else if(idType.equals(String.class)){
                deleteById(id);
            }
        }
    }


    @Override
    public T findOne(Serializable id) {
        return dao.findOne(entityClass,id);
    }

    @Override
    public Page findByCondtion(PageRequest request) {
        String hql = "select o from "+entityName()+" as o where 1=1 ";
        return dao.queryDataPageByHql(hql,new Object[]{},request);
    }
    
    @Override
    public List findListByCondtion(PageRequest request) {
        String hql = "select o from "+entityName()+" as o where 1=1 ";
        return dao.queryDataListByHql(hql,new Object[]{},request);
    }

    @Override
    public List findByCondtion(Request request) {
        return  dao.queryList("select o from "+entityName()+" as o where 1=1 ",request);
    }

    @Override
    public T queryByExampleFirst(Object entity) {
        return dao.queryByExampleFirst(entityClass,(T)entity);
    }

    @Override
    public List queryByExample(Object example) {
        return dao.queryByExample(entityClass,(T)example);
    }

    @Override
    public List<T> queryByExample(String[] properity, Object[] value){
        return dao.queryByExample(entityClass,properity,value);
    }

    @Override
    public T queryByExampleFirst(String[] properity, Object[] value){
        return dao.queryByExampleFirst(entityClass,properity,value);
    }

    /**
     * 获取id类型
     * @return
     */
    private Class getIdClass(){
        Method[] ms = entityClass.getMethods();
        for(Method m:ms){
            if(m.getAnnotation(Id.class) != null){
                return m.getReturnType();
            }
        }
        return null;
    }
}
