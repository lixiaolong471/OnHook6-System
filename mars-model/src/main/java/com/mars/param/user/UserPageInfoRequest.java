package com.mars.param.user;

import com.mars.core.bean.annotation.param.OptionType;
import com.mars.core.bean.annotation.param.QueryColumn;
import com.mars.core.bean.param.PageRequest;

/**
 * Created by lixl on 2018/6/28 0028.
 */
public class UserPageInfoRequest extends PageRequest {

    @QueryColumn
    private Boolean available;

    @QueryColumn(option = OptionType.LIKE)
    private String name;

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
