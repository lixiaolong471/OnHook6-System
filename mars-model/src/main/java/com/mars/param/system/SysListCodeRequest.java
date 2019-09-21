package com.mars.param.system;

import com.mars.core.bean.annotation.param.QueryColumn;
import com.mars.core.bean.param.Request;

/**
 * Created by lixl on 2017/8/14.
 */
public class SysListCodeRequest extends Request{

    @QueryColumn
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
