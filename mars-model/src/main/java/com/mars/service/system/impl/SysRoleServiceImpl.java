package com.mars.service.system.impl;

import com.mars.core.service.CommonServiceSupport;
import com.mars.model.system.SysAuthorityEntity;
import com.mars.model.system.SysRoleEntity;
import com.mars.model.system.SysUserEntity;
import com.mars.service.SysContent;
import com.mars.service.system.ISysRoleService;
import com.mars.service.system.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lixl on 2017/7/4.
 */
@Transactional
@Service
public class SysRoleServiceImpl extends CommonServiceSupport<SysRoleEntity> implements ISysRoleService{

    private static final Long ADMINS_ID = 1L;

    @Autowired
    private ISysUserService userService;

    @Override
    public boolean isRoleAction(String[] roleMark) {
    	SysUserEntity sysUser=SysContent.getLoginUser();
    	if(sysUser!=null){
    		List<SysRoleEntity> roleList = userService.findOne(sysUser.getId()).getRoles();
            for(SysRoleEntity role:roleList){
                for(SysAuthorityEntity authority:role.getAuthorityList()){
                    for(String owner:roleMark){
                        if(owner.equals(authority.getAuthorityMark())){
                            return true;
                        }
                    }
                }
            }
    	}
        return false;
    }

}
