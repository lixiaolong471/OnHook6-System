package com.mars.service.system;

import java.util.Map;

import com.mars.core.service.CommonService;
import com.mars.model.system.SysConfigEntity;

/**
 * Created by lixl on 2017/7/4.
 * 系统配置信息服务接口，提供配置信息的增、删、改、查操作！！
 */
public interface ISysConfigService extends CommonService<SysConfigEntity> {
    /**
     * 获取配置项数据
     * @param key
     * @return
     */
    String getConfigValue(String key);
}
