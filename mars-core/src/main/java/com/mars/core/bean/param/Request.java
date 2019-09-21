package com.mars.core.bean.param;

import com.mars.core.bean.annotation.param.OptionType;
import com.mars.core.bean.annotation.param.QueryColumn;

/**
 * Created by lixl on 2017/2/17.
 */
public class Request {

    public Request(){}

    public Request(String sort) {
        this.sort = sort;
    }

    private String sort;

    public void setSort(String sort){
        this.sort = sort;
    }

    public String getSort(){
        return sort;
    }

}
