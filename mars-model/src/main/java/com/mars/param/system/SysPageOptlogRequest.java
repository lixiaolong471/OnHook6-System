package com.mars.param.system;

import com.mars.core.bean.annotation.param.OptionType;
import com.mars.core.bean.annotation.param.QueryColumn;
import com.mars.core.bean.annotation.param.RequestParam;
import com.mars.core.bean.param.PageRequest;

/**
 * Created by lixl on 2017/7/27.
 */
@RequestParam
public class SysPageOptlogRequest extends PageRequest{

    @QueryColumn(option = OptionType.LIKE)
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
