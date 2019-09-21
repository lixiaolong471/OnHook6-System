package com.mars.param.system;

import com.mars.core.bean.annotation.param.OptionType;
import com.mars.core.bean.annotation.param.QueryColumn;
import com.mars.core.bean.annotation.param.RequestParam;
import com.mars.core.bean.param.PageRequest;

/**
 * Created by lixl on 2017/7/27.
 */
@RequestParam
public class SysPageUserRequest extends PageRequest{

    @QueryColumn(name = "o.name",option = OptionType.LIKE)
    private String name;

    @QueryColumn(name = "r.id",option = OptionType.EQ)
    private Long roleId;
    
    @QueryColumn(name = "o.available")
    private Boolean available;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
    
}
