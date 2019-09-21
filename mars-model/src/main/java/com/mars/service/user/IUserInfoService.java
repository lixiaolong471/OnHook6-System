package com.mars.service.user;

import com.mars.core.service.CommonService;
import com.mars.model.user.UserInfoEntity;

/**
 * Created by lixl on 2018/6/28 0028.
 */
public interface IUserInfoService extends CommonService<UserInfoEntity>{
    UserInfoEntity findBySerialKey(String serialKey);
    UserInfoEntity findByMachineCode(String machineCode,String name);
}
