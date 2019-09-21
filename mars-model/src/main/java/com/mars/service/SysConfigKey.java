package com.mars.service;

import com.mars.core.ConfigKey;

/**
 * Created by lixl on 2017/7/20.
 */
public interface SysConfigKey extends ConfigKey {
    public interface USER_REGISTER_CONFIG{
        String PASSWORD = "machine_code_password";
        String RENEWAL_COUNT = "renewal_month_count";
    }
}
