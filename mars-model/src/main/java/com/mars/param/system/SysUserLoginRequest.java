package com.mars.param.system;

import com.mars.core.bean.annotation.param.RequestParam;

/**
 * Created by lixl on 2017/7/24.
 */
@RequestParam
public class SysUserLoginRequest{

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String passwd;

    /**  验证码 */
    private String authCode;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
