package com.mars.service.system.impl;

import com.mars.core.bean.annotation.cache.CacheClean;
import com.mars.core.bean.annotation.cache.CacheKey;
import com.mars.core.bean.annotation.cache.CacheQuery;
import com.mars.core.service.CommonServiceSupport;
import com.mars.model.system.SysConfigEntity;
import com.mars.service.system.ISysConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lixl on 2017/7/4.
 */
@Transactional
@Service
public class SysConfigServiceImpl extends CommonServiceSupport<SysConfigEntity> implements ISysConfigService{

    @Override
    @CacheClean(value = "sys_config")
    public SysConfigEntity update(@CacheKey("#o.configkey") SysConfigEntity o) {
        return super.update(o);
    }

    @Override
    @CacheQuery(value = "sys_config")
    public String getConfigValue(@CacheKey("#key") String key) {
        SysConfigEntity entity = queryByExampleFirst(new String[]{"configkey"},new Object[]{key});
        if(entity != null){
            return entity.getConfigvalue();
        }
        return null;
    }
}
