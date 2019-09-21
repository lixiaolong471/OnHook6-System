package com.mars.param.system;

import com.mars.core.bean.annotation.param.RequestParam;
import com.mars.core.bean.param.SaveRequest;
import com.mars.core.util.DateUtils;
import com.mars.core.util.EncryptionUtils;
import com.mars.model.system.SysOrgaEntity;
import com.mars.model.system.SysRoleEntity;
import com.mars.model.system.SysUserEntity;
import com.mars.service.SysContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixl on 2017/7/20.
 */
@RequestParam
public class SysSaveUserRequest extends SysUserEntity implements SaveRequest<SysUserEntity> {

    private Long[] orgaIds;

    private Long[] roleIds;

    public Long[] getOrgaIds() {
        return orgaIds;
    }

    public void setOrgaIds(Long[] orgaIds) {
        this.orgaIds = orgaIds;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public SysUserEntity getSave(){
        SysUserEntity entity = new SysUserEntity();
        try {
            entity.setLoginName(this.getLoginName());
            entity.setPassword(EncryptionUtils.md5s(this.getPassword()));
            entity.setAddress(this.getAddress());
            entity.setAvailable(true);
            entity.setName(this.getName());
            entity.setZjh(this.getZjh());
            entity.setTel(this.getTel());
            entity.setMobile(this.getMobile());
            entity.setEmail(this.getEmail());
            entity.setPhotoUrl(this.getPhotoUrl());
            entity.setPosition(this.getPosition());
            entity.setRemark(this.getRemark());
            entity.setCreator(SysContent.getLoginUser());
            entity.setUpdater(SysContent.getLoginUser());
            entity.setCreateTime(DateUtils.getDatetime());
            entity.setUpdateTime(DateUtils.getDatetime());
            entity.setRoles(getRoleList());
            entity.setOrgas(getOrgaList());
            entity.setReserve(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public SysUserEntity getUpdate(SysUserEntity entity){
        entity.setLoginName(this.getLoginName());
        entity.setPassword(this.getPassword());
        entity.setAddress(this.getAddress());
        entity.setAvailable(this.getAvailable());
        entity.setName(this.getName());
        entity.setZjh(this.getZjh());
        entity.setTel(this.getTel());
        entity.setMobile(this.getMobile());
        entity.setEmail(this.getEmail());
        entity.setPhotoUrl(this.getPhotoUrl());
        entity.setPosition(this.getPosition());
        entity.setRoles(getRoleList());
        entity.setOrgas(getOrgaList());
        entity.setUpdater(SysContent.getLoginUser());
        entity.setUpdateTime(DateUtils.getDatetime());
        return entity;
    }

    /**
     *
     * @return
     */
    public SysUserEntity settings(){
        SysUserEntity userEntity = SysContent.getLoginUser();
        userEntity.setTel(this.getTel());
        userEntity.setPhotoUrl(this.getPhotoUrl());
        userEntity.setEmail(this.getEmail());
        userEntity.setZjh(this.getZjh());
        return userEntity;
    }

    /**
     * 获取用户角色列表
     * @return
     */
    public List<SysRoleEntity> getRoleList(){
        List<SysRoleEntity> roleList = new ArrayList<>();
        if(roleIds != null && roleIds.length > 0){
            for(Long id:roleIds){
                SysRoleEntity role = new SysRoleEntity();
                role.setId(id);
                roleList.add(role);
            }
        }
        return roleList;
    }

    /**
     * 获取用户的组织列表
     * @return
     */
    public List<SysOrgaEntity> getOrgaList(){
        List<SysOrgaEntity> orgaList = new ArrayList<>();
        if(orgaIds != null && orgaIds.length > 0){
            for(Long id:orgaIds){
                SysOrgaEntity role = new SysOrgaEntity();
                role.setId(id);
                orgaList.add(role);
            }
        }
        return orgaList;
    }
}
