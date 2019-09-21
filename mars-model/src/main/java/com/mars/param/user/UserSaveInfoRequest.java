package com.mars.param.user;

import com.mars.core.bean.param.SaveRequest;
import com.mars.core.util.DateUtils;
import com.mars.model.user.UserInfoEntity;
import com.mars.service.SysConfigKey;
import com.mars.service.SysContent;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lixl on 2018/6/28 0028.
 */
public class UserSaveInfoRequest extends UserInfoEntity implements SaveRequest<UserInfoEntity> {
    /**
     * 续期天数
     */
    private Integer addDay;

    @Override
    public UserInfoEntity getSave() {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setName(getName());
        userInfo.setMachineCode(getMachineCode());
        userInfo.setCreateTime(DateUtils.getDatetime());
        userInfo.setUpdateTime(DateUtils.getDatetime());
        userInfo.setExpirationTime(getExpirationTime());
        userInfo.setAvailable(false);
        return userInfo;
    }

    @Override
    public UserInfoEntity getUpdate(UserInfoEntity userInfo) {
        userInfo.setName(this.getName());
        userInfo.setUpdateTime(DateUtils.getDatetime());
        int monthCount = SysContent.getConfigInteger(SysConfigKey.USER_REGISTER_CONFIG.RENEWAL_COUNT);
        userInfo.setExpirationTime(new Timestamp(DateUtils.addMonth(new Date(),monthCount).getTime()));
//        userInfo.setAvailable(Boolean.TRUE);
        return userInfo;
    }

    public UserInfoEntity getAddDateUpdate(UserInfoEntity userInfo) {
        userInfo.setUpdateTime(DateUtils.getDatetime());
        userInfo.setExpirationTime(new Timestamp(DateUtils.addDay(new Date(),addDay).getTime()));
        userInfo.setAvailable(Boolean.TRUE);
        return userInfo;
    }

    public Integer getAddDay() {
        return addDay;
    }

    public void setAddDay(Integer addDay) {
        this.addDay = addDay;
    }
}
