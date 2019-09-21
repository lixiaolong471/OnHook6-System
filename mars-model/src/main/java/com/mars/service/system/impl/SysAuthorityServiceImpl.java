package com.mars.service.system.impl;

import com.mars.core.bean.param.Request;
import com.mars.core.service.CommonServiceSupport;
import com.mars.model.system.SysAuthorityEntity;
import com.mars.service.system.ISysAuthorityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lixl on 2017/7/17.
 */
@Service
@Transactional
public class SysAuthorityServiceImpl extends CommonServiceSupport<SysAuthorityEntity> implements ISysAuthorityService {



    @Override
    public List<SysAuthorityEntity> findByCondtion(Request request){
        String hql = "select distinct o from SysAuthorityEntity o inner join  o.roleList rs where 1=1 ";
        return dao.queryList(hql,request);
    }
}
