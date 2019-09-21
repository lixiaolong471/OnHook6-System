package com.mars.param.system;

import com.mars.core.bean.param.SaveRequest;
import com.mars.core.util.DateUtils;
import com.mars.model.system.SysOrgaEntity;
import com.mars.service.SysContent;

/**
 * Created by lixl on 2017/7/28.
 */
public class SysSaveOrgaRequest extends SysOrgaEntity implements SaveRequest<SysOrgaEntity> {
    @Override
    public SysOrgaEntity getSave() {
        SysOrgaEntity entity = new SysOrgaEntity();
        try{
            entity.setOrgaName(this.getOrgaName());
            entity.setTel(getTel());
            entity.setAvailable(true);
            entity.setAvailable(getAvailable());
            entity.setAddress(getAddress());
            entity.setLead(getLead());
            entity.setParent(getParent());
            entity.setRemark(getRemark());
            entity.setCreator(SysContent.getLoginUser());
            entity.setUpdater(SysContent.getLoginUser());
            entity.setUpdateTime(DateUtils.getDatetime());
            entity.setCreateTime(DateUtils.getDatetime());
            entity.setReserve(false);
        }catch (Exception e){
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public SysOrgaEntity getUpdate(SysOrgaEntity entity) {
        entity.setOrgaName(this.getOrgaName());
        entity.setTel(getTel());
        entity.setAddress(getAddress());
        entity.setLead(getLead());
        entity.setParent(getParent());
        entity.setRemark(getRemark());
        entity.setUpdateTime(DateUtils.getDatetime());
        entity.setUpdater(SysContent.getLoginUser());
        return entity;
    }
}
