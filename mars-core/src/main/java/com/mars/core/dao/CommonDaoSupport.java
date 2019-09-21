package com.mars.core.dao;

import com.mars.core.bean.annotation.param.OptionType;
import com.mars.core.bean.annotation.param.QueryColumn;
import com.mars.core.bean.param.PageRequest;
import com.mars.core.bean.param.Request;
import com.mars.core.util.CollectionUtils;
import com.mars.core.util.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 数据库访问基础类
 * 
 * @author lixl
 * 
 * */
@Service("commonDao")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CommonDaoSupport implements CommonDao {

	@PersistenceContext
	private EntityManager em;

	public CommonDaoSupport(){
	}

	@Override
	public List queryDataListByHql(String hql, Object[] param) {
		Query q = em.createQuery(hql.toString());
		for (int i = 0; i < param.length; i++) {
			q.setParameter(i + 1, param[i]);
		}

		List list = q.getResultList();

		return list;
	}

	@Override
	public Page queryDataPageByHql(String hql, Object[] param, PageRequest requestParam) {
		List sparams = CollectionUtils.toList(param);
		hql = sql(hql,requestParam,sparams);
		Query q = em.createQuery(hql.toString());
		for (int i = 0; i < sparams.size(); i++) {
			q.setParameter(i + 1, sparams.get(i));
		}
		long count = queryListSizeByHql(hql, sparams.toArray());

		q.setFirstResult(requestParam.getDisplayStart());
		q.setMaxResults(requestParam.getRows());
		List list = q.getResultList();

		Page page = new PageImpl(list, requestParam.getPageRequest(), count);
		return page;
	}

	@Override
	public List queryDataListByHql(String hql, Object[] param, PageRequest requestParam) {
		List sparams = CollectionUtils.toList(param);
		hql = sql(hql,requestParam,sparams);
		Query q = em.createQuery(hql.toString());
		for (int i = 0; i < sparams.size(); i++) {
			q.setParameter(i + 1, sparams.get(i));
		}
		q.setFirstResult(requestParam.getDisplayStart());
		q.setMaxResults(requestParam.getRows());
		List list = q.getResultList();
		return list;
	}
	
	@Override
	public long queryListSizeByHql(String hql, Object[] param, PageRequest requestParam){
		List sparams = CollectionUtils.toList(param);
		hql = sql(hql,requestParam,sparams);
		return queryListSizeByHql(hql, sparams.toArray());
	}

	@Override
	public long queryListSizeByHql(String hql, Object[] param) {
		// 去空格
		String noBlankSql = StringUtils.removeBlank(hql);
		// 全转小写
		String lowerSql = noBlankSql.toLowerCase();
		StringBuilder exeSql = new StringBuilder();
		exeSql.append("select count(*)");
		if (lowerSql.contains("group by")) {
			exeSql.append(" from (").append(noBlankSql)
					.append(") as countTableName");
		} else {
			exeSql.append(noBlankSql.substring(lowerSql.indexOf(" from ")));
		}
		List list = queryDataListByHql(exeSql.toString(), param);
		long returnLong = 0;
		if (list.size() == 0) {
			returnLong = 0;
		} else {
			returnLong = Long.parseLong(list.get(0).toString());
		}
		return returnLong;
	}

	@Override
	public <T> Page<T> queryPage(String hql, PageRequest dtp) {
		List<Object> sparams = new ArrayList<>();
		return queryDataPageByHql(hql,sparams.toArray(),dtp);
	}

	@Override
	public List queryList(String hql, Request param) {
		List<Object> sparams = new ArrayList<>();
		hql = sql(hql,param,sparams);
		return queryDataListByHql(hql,sparams.toArray());
	}

	@Override
	public List queryList(String hql, PageRequest dtp) {
		List<Object> sparams = new ArrayList<>();
		return queryDataListByHql(hql,sparams.toArray(),dtp);
	}

	@Override
	public List queryList(String hql, Request param, int count) {
		List<Object> sparams = new ArrayList<>();
		hql = sql(hql,param,sparams);
		Query q = em.createQuery(hql.toString());
		for (int i = 0; i < sparams.size(); i++) {
			q.setParameter(i + 1, sparams.get(i));
		}
		q.setFirstResult(1);
		q.setMaxResults(count);
		List list = q.getResultList();
		return list;
	}

	@Override
	public <T> T queryFirst(String hql, Request requestParam) {

		List sparams = new ArrayList();
		hql = sql(hql,requestParam,sparams);
		Query q = em.createQuery(hql.toString());
		for (int i = 0; i < sparams.size(); i++) {
			q.setParameter(i + 1, sparams.get(i));
		}
		q.setFirstResult(1);
		q.setMaxResults(1);
		return (T)q.getResultList().get(0);
	}

	@Override
	public <T> List<T> queryByExample(Class<T> cls,T exampleEntity) {
		Assert.notNull(exampleEntity, "Example entity must not be null");
		Session session =(Session)em.getDelegate();
		session = session.getSessionFactory().openSession();
		Example example = Example.create(exampleEntity).excludeZeroes();
		Criteria criteria = session.createCriteria(cls).add(example);
		List<T> list = criteria.list();
		return list;
	}

	@Override
	public <T> T queryByExampleFirst(Class<T> cls, T exampleEntity) {
		Assert.notNull(exampleEntity, "Example entity must not be null");
		Session session =(Session)em.getDelegate();
		session = session.getSessionFactory().openSession();
		Example example = Example.create(exampleEntity).excludeZeroes();
		Criteria criteria = session.createCriteria(cls).add(example);
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		List<T> list = criteria.list();
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public <T> List<T> queryByExample(Class<T> cls,String[] properity, Object[] value) {
		Session session =(Session)em.getDelegate();
		session = session.getSessionFactory().openSession();
		DetachedCriteria criteria = DetachedCriteria.forClass(cls);
		for(int i=0;i<properity.length;i++){
			criteria.add(Restrictions.eq(properity[i],value[i]));
		}
		return criteria.getExecutableCriteria(session).list();
	}

	

	@Override
	public <T> T queryByExampleFirst(Class<T> cls,String[] properity, Object[] value) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery c = cb.createQuery(cls);
		Root root = c.from(cls);
		c.select(root);
		List<Predicate> predicatesList = new ArrayList<>();
		for(int i=0;i<properity.length;i++){
			predicatesList.add(cb.equal(root.get(properity[i]),value[i]));
		}
		c.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
		Query query = em.createQuery(c);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<T> data = query.getResultList();
		if(data !=null && !data.isEmpty()){
		 	return data.get(0);
		}
		return null;
	}

	@Override
	public List queryList(String sql, Object[] param) {
		Query q = em.createNativeQuery(sql.toString());
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (null != param) {
			for (int i = 0; i < param.length; i++) {

				q.setParameter(i + 1, param[i]);
			}
		}
		return q.getResultList();
	}

	/**
	 * 根据sql语句获得查询的信息
	 *
	 * @param <T>
	 * @param sql
	 * @param param
	 * @param cls
	 * @return
	 */
	@Override
	public <T> List<T> queryList(String sql, Object[] param, Class<T> cls) {
		Query q = em.createNativeQuery(sql.toString(), cls);
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (null != param) {
			for (int i = 0; i < param.length; i++) {

				q.setParameter(i + 1, param[i]);
			}
		}
		return ((List<T>) q.getResultList());
	}

	@Override
	public List queryList(String sql, Object[] param, int count) {
		Query q = em.createNativeQuery(sql.toString());
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (null != param) {
			for (int i = 0; i < param.length; i++) {

				q.setParameter(i + 1, param[i]);
			}
		}
		q.setMaxResults(count);
		return q.getResultList();
	}

	@Override
	public <T> List<T> queryList(String sql, Object[] param, Class<T> cls, int count) {
		Query q = em.createNativeQuery(sql.toString(), cls);
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (null != param) {
			for (int i = 0; i < param.length; i++) {

				q.setParameter(i + 1, param[i]);
			}
		}
		q.setMaxResults(count);
		return ((List<T>) q.getResultList());
	}

	@Override
	public int exec(String sql, Object[] params) {
		Query q = em.createQuery(sql);
		for (int i = 0; i < params.length; i++) {
			q.setParameter(i + 1, params[i]);
		}
		return q.executeUpdate();
	}

	@Override
	public void save(Object o) {
		 em.persist(o);
	}

	@Override
	public void delete(Object o) {
		em.remove(o);
	}

	@Override
	public <T> T update(T o) {
		return em.merge(o);
	}

	@Override
	public <T> T findOne(Class<T> cls, Object id) {
		return em.find(cls,id);
	}


	private String sql(String sql, Request searchBean, List<Object> sparams){

		StringBuffer sqlBuffer = new StringBuffer(sql);
		StringBuffer whereBuffer = new StringBuffer();
		try{
			for(Field field:searchBean.getClass().getDeclaredFields()){
				field.setAccessible(true);
				if(!isNull(searchBean,field) && field.isAnnotationPresent(QueryColumn.class)){
					QueryColumn param = field.getAnnotation(QueryColumn.class);
					if(param.names().length > 0){
						whereBuffer.append(or(param,field,searchBean,sparams));
					}else{
						whereBuffer.append(and(param,field,searchBean,sparams));
					}
				}
			}
			sqlBuffer.append(whereBuffer);
			if(StringUtils.isNotEmpty(searchBean.getSort())){
				sqlBuffer.append(" order by ").append(searchBean.getSort());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sqlBuffer.toString();

	}

	private boolean isNull(Object obj,Field field) throws IllegalAccessException {
		if(field.getType().equals(String.class)
				&& field.get(obj) != null  && !field.get(obj).equals("")){
			return false;
		}else if(!field.getType().equals(String.class) && field.get(obj) != null){
			return false;
		}
		return true;
	}

	private String or(QueryColumn param, Field field, Object obj, List<Object> sparams)throws IllegalAccessException {
		StringBuffer orBuffer = new StringBuffer(" and ( 1=2 ");
		for(String name:param.names()){
			orBuffer.append(" or ").append(name).append(" ");
			orBuffer.append(option(param.option(),field.get(obj),sparams));
		}
		orBuffer.append(")");
		return orBuffer.toString();
	}


	private String option(OptionType option,Object value,List<Object> sparams){
		StringBuilder sql = new StringBuilder();
		switch(option){
			case LIKE:
				sql.append("like ? ");
				sparams.add("%"+value+"%");
				break;
			case LLIKE:
				sql.append("like ? ");
				sparams.add("%"+value);
				break;
			case RLIKE:
				sql.append("like ? ");
				sparams.add(value+"%");
				break;
			case EQ:
				sql.append("= ? ");
				sparams.add(value);
				break;
			case NEQ:
				sql.append(" != ? ");
				sparams.add(value);
				break;
			case GT:
				sql.append(" > ? ");
				sparams.add(value);
				break;
			case LT:
				sql.append(" < ? ");
				sparams.add(value);
				break;
			case GTE:
				sql.append(" >= ? ");
				sparams.add(value);
				break;
			case LTE:
				sql.append(" <= ? ");
				sparams.add(value);
				break;
			case IN:
				sql.append(" in (");
				Object [] arrObj=null;
				if(value instanceof Collection){
					arrObj=((Collection) value).toArray();
				}else if(value.getClass().isArray()){
					arrObj=(Object[])value;
				}else{
					sql.append("?");
					sparams.add(value);
				}
				if(arrObj!=null){
					for (int i = 0; i < arrObj.length; i++) {
						if(i==0){
							sql.append("? ");
						}else{
							sql.append(", ?");
						}
						sparams.add(arrObj[i]);
					}
				}
				sql.append(") ");
				break;
			default:
		}
		if(sql.length()>0){
			return sql.toString();
		}
		return null;
	}

	private String and(QueryColumn param, Field field, Object obj, List<Object> sparams) throws IllegalAccessException {
		StringBuffer whereBuffer = new StringBuffer(" and");
		if(!param.name().equals("")){
			whereBuffer.append(" ").append(param.name()).append(" ");
		}else{
			whereBuffer.append(" ").append(field.getName()).append(" ");
		}
		whereBuffer.append(option(param.option(),field.get(obj),sparams));
		return whereBuffer.toString();
	}



	
}
