package com.mars.param.system;

import com.mars.core.bean.annotation.param.OptionType;
import com.mars.core.bean.annotation.param.QueryColumn;
import com.mars.core.bean.annotation.param.RequestParam;
import com.mars.core.bean.param.Request;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * 
 * @author tantx
 *
 */
public class SysUserRequest extends Request{

    @QueryColumn(name = "r.id",option = OptionType.EQ)
    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @QueryColumn(name = "o.available",option = OptionType.EQ)
    private Boolean available = true;

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
