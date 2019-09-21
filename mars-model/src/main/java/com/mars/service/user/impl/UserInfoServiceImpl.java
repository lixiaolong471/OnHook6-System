package com.mars.service.user.impl;

import com.mars.core.dao.CommonDao;
import com.mars.core.service.CommonServiceSupport;
import com.mars.model.user.UserInfoEntity;
import com.mars.service.user.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lixl on 2018/6/28 0028.
 */
@Service
@Transactional
public class UserInfoServiceImpl extends CommonServiceSupport<UserInfoEntity> implements IUserInfoService {

    @Autowired
    CommonDao dao;

    @Override
    public UserInfoEntity findBySerialKey(String serialKey) {
        return dao.queryByExampleFirst(UserInfoEntity.class,new String[]{"serialKey"},new Object[]{serialKey});
    }

    @Override
    public UserInfoEntity findByMachineCode(String machineCode, String name) {
        return dao.queryByExampleFirst(UserInfoEntity.class,new String[]{"machineCode","name"},new Object[]{machineCode,name});
    }


}
