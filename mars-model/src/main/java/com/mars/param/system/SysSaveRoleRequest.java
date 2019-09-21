package com.mars.param.system;
import com.mars.core.bean.annotation.param.RequestParam;
import com.mars.core.bean.param.SaveRequest;
import com.mars.core.util.DateUtils;
import com.mars.model.system.SysAuthorityEntity;
import com.mars.model.system.SysRoleEntity;
import com.mars.service.SysContent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixl on 2017/7/18.
 */
@RequestParam
public class SysSaveRoleRequest extends SysRoleEntity implements SaveRequest<SysRoleEntity>{

    private Long[] authoritys;

    public Long[] getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(Long[] authoritys) {
        this.authoritys = authoritys;
    }

    @Override
    public SysRoleEntity getSave(){
        SysRoleEntity entity = new SysRoleEntity();
        entity.setRoleName(getRoleName());
        entity.setRemark(getRemark());
        entity.setCreateTime(DateUtils.getDatetime());
        entity.setUpdateTime(DateUtils.getDatetime());
        entity.setCreator(SysContent.getLoginUser());
        entity.setUpdater(SysContent.getLoginUser());
        entity.setAuthorityList(loadAuthorityList());
        entity.setAvailable(true);
        entity.setReserve(false);
        return entity;
    }

    @Override
    public SysRoleEntity getUpdate(SysRoleEntity entity){
        entity.setRoleName(getRoleName());
        entity.setRemark(getRemark());
        entity.setUpdateTime(DateUtils.getDatetime());
        entity.setUpdater(SysContent.getLoginUser());
        entity.setAuthorityList(loadAuthorityList());
        return entity;
    }


    private List<SysAuthorityEntity> loadAuthorityList(){
        List<SysAuthorityEntity> authorityEntityList = new ArrayList<>();
        if(authoritys != null){
            for(Long id:authoritys){
                SysAuthorityEntity authority = new SysAuthorityEntity();
                authority.setId(id);
                authorityEntityList.add(authority);
            }
        }
        return authorityEntityList;
    }

}
