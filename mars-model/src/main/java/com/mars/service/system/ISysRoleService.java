package com.mars.service.system;

import com.mars.core.bean.param.Result;
import com.mars.core.service.CommonService;
import com.mars.model.system.SysRoleEntity;

/**
 * Created by lixl on 2017/7/4.
 */
public interface ISysRoleService extends CommonService<SysRoleEntity>{

    /**
     * 验证用户是否有权限访问对应菜单
     * @param roleMark
     * @return
     */
    boolean isRoleAction(String[] roleMark);
}
