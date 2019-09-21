package com.mars.param.system;

import com.mars.core.bean.annotation.param.OptionType;
import com.mars.core.bean.annotation.param.QueryColumn;
import com.mars.core.bean.param.Request;

/**
 * Created by lixl on 2017/8/18.
 */
public class SysListAuthorityRequest extends Request {

    @QueryColumn(name = "rs.id",option = OptionType.IN)
    private Long[] roles;

    @QueryColumn(name = "o.available")
    private Boolean available;

    public Long[] getRoles() {
        return roles;
    }

    public void setRoles(Long[] roles) {
        this.roles = roles;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
