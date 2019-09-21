package com.mars.service.system.impl;

import com.mars.core.bean.annotation.aop.RedisLock;
import com.mars.core.service.CommonServiceSupport;
import com.mars.model.system.SysIdtableEntity;
import com.mars.service.system.ISysIdtableService;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lixl on 2017/7/23.
 */
@Service
public class SysIdtableServiceImpl extends CommonServiceSupport<SysIdtableEntity> implements ISysIdtableService {
    private Lock lock = new ReentrantLock();// 锁对象

    @Override
    public Long get(String code) {
        Long value = null;
        try{
            lock.lock();
           SysIdtableEntity entity = findOne(code);
            value = entity.getValue() + 1;
        }finally{
            lock.unlock();
        }
        return value;
    }

    @Override
	@RedisLock(key="sys_idtable_:code")
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public Long increment(@Param(value="code") String code) {
        Long value = null;
        try{
            lock.lock();
            SysIdtableEntity entity = findOne(code);
            entity.setValue(entity.getValue() + 1);
            update(entity);
            value = entity.getValue();
        }finally{
            lock.unlock();
        }
        return value;
    }
}
