package com.mars.param.system;

import com.mars.core.bean.annotation.param.OptionType;
import com.mars.core.bean.annotation.param.QueryColumn;
import com.mars.core.bean.param.PageRequest;

/**
 * Created by lixl on 2017/8/11.
 */
public class SysPageCodeRequest extends PageRequest{

    @QueryColumn(option = OptionType.LIKE)
    private String groupName;

    @QueryColumn
    private String type;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
