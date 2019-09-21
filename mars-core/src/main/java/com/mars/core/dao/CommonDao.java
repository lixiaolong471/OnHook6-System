package com.mars.core.dao;

import com.mars.core.bean.param.PageRequest;
import com.mars.core.bean.param.Request;
import org.springframework.data.domain.Page;
import java.util.List;


@SuppressWarnings("rawtypes")
public interface CommonDao {


	/**
	 * HQL 查询
	 * @param hql
	 * @param param
	 * @return
	 */
	public List queryDataListByHql(String hql, Object[] param);

	/**
	 * HQL查询
	 * @param hql
	 * @param param
	 * @param dtp
	 * @return
	 */
	public Page queryDataPageByHql(String hql, Object[] param, PageRequest dtp);


	/**
	 * HQL 查询
	 * @param hql
	 * @param param
	 * @param dtp
	 * @return
	 */
	public List queryDataListByHql(String hql, Object[] param, PageRequest dtp);


	/**
	 * 查询hql统计记录数量
	 *
	 * @param hql
	 * @param param
	 * @return
	 */
	public long queryListSizeByHql(String hql, Object[] param);


	/**
	 * 根据sql语句获得查询的信息
	 *
	 * @param hql
	 * @param param
	 * @return
	 */
	public <T> List<T> queryList(String hql, Request param);


	/**
	 * 根据sql语句获得查询的信息
	 *
	 * @param hql
	 * @param dtp
	 * @return
	 */
	public <T> Page<T> queryPage(String hql, PageRequest dtp);


	/**
	 * 根据sql语句获得查询的信息
	 *
	 * @param hql
	 * @param dtp
	 * @return
	 */
	public <T> List<T> queryList(String hql, PageRequest dtp);

	/**
	 * 查询指定个数
	 * @param hql
	 * @param param
	 * @param count
	 * @return
	 */
	public <T> List<T> queryList(String hql, Request param, int count);

	/**
	 * 获取指定HQL语言查询的数据中的第一个
	 * @param hql
	 * @param param
	 * @return
	 */
	public <T> T queryFirst(String hql, Request param);

	/**
	 * 根据当前实体的某一个属性查找对象
	 * @param entity
	 * @return
	 */
	public <T> List<T> queryByExample(Class<T> cls, T entity);


	/**
	 * 根据当前实体的某一个属性查找对象
	 * @param cls
	 * @param entity
	 * @param <T>
	 * @return
	 */
	public <T> T queryByExampleFirst(Class<T> cls, T entity);

	/**
	 * 根据属性查找对象
	 * @param properity
	 * @param value
	 * @return
	 */
	public <T> List<T> queryByExample(Class<T> cls, String[] properity, Object[] value);

	/**
	 * 根据属性查找对象
	 * @param properity
	 * @param value
	 * @return
	 */
	public <T> T queryByExampleFirst(Class<T> cls, String[] properity, Object[] value);

	/**
	 * 获得查询的列表信息
	 *
	 * @param sql
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryList(String sql, Object[] param);

	/**
	 * 根据sql语句获得查询的信息
	 *
	 * @param sql
	 * @param param
	 * @param cls
	 * @return
	 */
	public <T> List<T> queryList(String sql, Object[] param, Class<T> cls);

	/**
	 * 获得查询的列表信息
	 *
	 * @param sql
	 * @param param
	 * @param count 指定个数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryList(String sql, Object[] param,int count);



	/**
	 * 根据sql语句获得查询的信息
	 *
	 * @param sql
	 * @param param
	 * @param cls
	 * @param count 指定个数
	 * @return
	 */
	public <T> List<T> queryList(String sql, Object[] param, Class<T> cls,int count);


	/**
	 * sql执行更新操作
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	public int exec(String sql, Object[] params);

	/**
	 * 保存数据
	 * @param o
	 */
	public void save(Object o);

	/**
	 * 删除数据
	 * @param o
	 */
	public void delete(Object o);

	/**
	 * 查询数据
	 * @param o
	 */
	public <T> T update(T o);

	/**
	 * 根据id查询数据
	 * @param cls
	 * @param id
	 * @return
	 */
	public <T> T findOne(Class<T> cls,Object id);

	/**
	 * 查询hql统计记录数量
	 * @param hql
	 * @param param
	 * @param requestParam
	 * @return
	 */
	long queryListSizeByHql(String hql, Object[] param, PageRequest requestParam);
}
