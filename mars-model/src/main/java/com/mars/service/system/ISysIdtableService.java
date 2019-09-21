package com.mars.service.system;

import com.mars.core.service.CommonService;
import com.mars.model.system.SysIdtableEntity;

/**
 * Created by lixl on 2017/7/23.
 */
public interface ISysIdtableService extends CommonService<SysIdtableEntity>{

    /**
     * 获取最大表序列值，不更新到数据库
     * @param code
     * @return
     */
    Long get(String code);

    /**
     * 获取一个当前最大序列后后，并跟新数据库
     * @param code
     * @return
     */
    Long increment(String code);

}
