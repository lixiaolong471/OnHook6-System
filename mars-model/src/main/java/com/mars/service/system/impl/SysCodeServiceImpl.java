package com.mars.service.system.impl;

import com.mars.core.bean.annotation.cache.CacheClean;
import com.mars.core.bean.annotation.cache.CacheKey;
import com.mars.core.bean.annotation.cache.CacheQuery;
import com.mars.core.dao.CommonDao;
import com.mars.core.service.CommonServiceSupport;
import com.mars.core.util.SpringUtils;
import com.mars.model.system.SysCodeEntity;
import com.mars.service.system.ISysCodeService;
import com.mars.service.system.ISysConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lixl on 2017/7/23.
 */
@Transactional
@Service
public class SysCodeServiceImpl extends CommonServiceSupport<SysCodeEntity> implements ISysCodeService{

    @Autowired
    CommonDao commonDao;

	@CacheClean(value = "sys_cache_code_list")
	public SysCodeEntity update(@CacheKey("#o.code") SysCodeEntity o) {
		return super.update(o);
	}

	@CacheQuery(value="sys_cache_code_list")
    public List<SysCodeEntity> findByCode(@CacheKey("#code") String code) {
        String hql = "select o from SysCodeEntity o where o.code = ? order by indexnum ";
        return commonDao.queryDataListByHql(hql,new Object[]{code});
    }

    public SysCodeEntity getByCodeAndValue(String code,Integer value) {
    	ISysCodeService sysCodeService=SpringUtils.getBean(ISysCodeService.class);
		List<SysCodeEntity> list = sysCodeService.findByCode(code);
		if(code == null || value == null){
			return null;
		}
		for(SysCodeEntity entity:list){
			if(entity.getCode().equals(code) && entity.getValue().equals(value)){
				return entity;
			}
		}
    	return null;
    }
    
    @Override
    public String getNameByCodeAndValue(String code,Integer value) {
    	SysCodeEntity sysCodeEntity=getByCodeAndValue(code, value);
    	if(sysCodeEntity!=null){
    		return sysCodeEntity.getName();
    	}
    	return "";
    }
    
    @Override
    public HashMap<Integer, SysCodeEntity> getValueAndObjHashMap(String code){
    	List<SysCodeEntity> list=findByCode(code);
		HashMap<Integer, SysCodeEntity> hm=new HashMap<Integer, SysCodeEntity>();
    	if(list!=null&&list.size()>0){
    		for (SysCodeEntity sysCodeEntity : list) {
    			hm.put(sysCodeEntity.getValue(), sysCodeEntity);
			}
    	}
		return hm;
    }

	@Override
	public HashMap<String, String> getValueAndNameHashMap(String code) {
		List<SysCodeEntity> list=findByCode(code);
		HashMap<String, String> hm=new HashMap<>();
		if(list!=null&&list.size()>0){
			for (SysCodeEntity sysCodeEntity : list) {
				hm.put(sysCodeEntity.getValue().toString(), sysCodeEntity.getName());
			}
		}
		return hm;
	}
	

	@Override
    public HashMap<String, Integer> getNameAndValueHashMap(String code){
    	List<SysCodeEntity> list=findByCode(code);
		HashMap<String, Integer> hm=new HashMap<String, Integer>();
    	if(list!=null&&list.size()>0){
    		for (SysCodeEntity sysCodeEntity : list) {
    			hm.put(sysCodeEntity.getName(), sysCodeEntity.getValue());
			}
    	}
		return hm;
    }
}
