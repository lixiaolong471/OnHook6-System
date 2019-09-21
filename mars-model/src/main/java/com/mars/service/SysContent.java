package com.mars.service;

import com.mars.core.SysConfig;
import com.mars.core.service.CacheService;
import com.mars.core.util.SpringUtils;
import com.mars.core.util.StringUtils;
import com.mars.model.system.SysUserEntity;
import com.mars.service.system.ISysConfigService;
import com.mars.service.system.ISysIdtableService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Created by lixl on 2017/7/4.
 */
public class SysContent {

    /**
     * 系统用户session id
     */
    private static final String SESSION_SYS_ID_LOGIN_USER = "sysUser";

    /**
     * 网站用户session id
     */
    private static final String SESSION_WEB_ID_LOGIN_USER = "webUser";
    
    public static final String TOKEN_APP_LOGIN_USER = "userToken";
	
	public static final String APP_ACCESS_TOKEN="appAccessToken";
	
	public static final String REFRESH_SESSION="refreshSession";

    private static CacheService cache;

    /**
     * 获取登录用户信息
     * @return
     */
    public static SysUserEntity getLoginUser(){
    	HttpSession session=SysConfig.getSession();
    	if(session==null){
    		return null;
    	}
        return (SysUserEntity)session.getAttribute(SESSION_SYS_ID_LOGIN_USER);
    }

    /**
     * 判断用户是否登录
     * @return
     */
    public static boolean isLogin(){
        if(getLoginUser() == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 设置/重置登录用户信息
     * @param user
     */
    public static void setLoginUser(SysUserEntity user){
        SysConfig.getSession().setAttribute(SESSION_SYS_ID_LOGIN_USER,user);
    }

    /**
     * 获取配置文件
     * @param configkey
     * @return
     */
    public static String getConfigValue(String configkey){
        return SpringUtils.getBean(ISysConfigService.class).getConfigValue(configkey);
    }

    /**
     * 获取布尔类型的系统参数值
     * @param configKey
     * @return
     */
    public static Boolean getConfigBoolean(String configKey){
         String value = getConfigValue(configKey);
         if(StringUtils.isNotEmpty(value)){
             return Boolean.parseBoolean(value);
         }else{
             return null;
         }
    }

    /**
     * 获取integer类型的数据值
     * @param configKey
     * @return
     */
    public static Integer getConfigInteger(String configKey){
        String value = getConfigValue(configKey);
        if(StringUtils.isNotEmpty(value)){
            return Integer.parseInt(value);
        }else{
            return null;
        }
    }
    
    /**
     * 获取Long类型的数据值
     * @param configKey
     * @return
     */
    public static Long getConfigLong(String configKey){
        String value = getConfigValue(configKey);
        if(StringUtils.isNotEmpty(value)){
            return Long.valueOf(value);
        }else{
            return null;
        }
    }
    
    /**
     * 获取integer类型的数据值
     * @param configKey
     * @return
     */
    public static BigDecimal getConfigBigDecimal(String configKey){
        String value = getConfigValue(configKey);
        if(StringUtils.isNotEmpty(value)){
            return new BigDecimal(value);
        }else{
            return null;
        }
    }

    /**
     * 获取自增序列 （++后的）
     * @param code
     * @return
     */
    public static Long incrementLong(String code){
        return SpringUtils.getBean(ISysIdtableService.class).increment(code);
    }

    /**
     * 获取自增序列 （++后的）
     * @param code
     * @return
     */
    public static Integer incrementInteger(String code){
        return SpringUtils.getBean(ISysIdtableService.class).increment(code).intValue();
    }

    /**
     * 获取自增序列当前最大值
     * @param code
     * @return
     */
    public static Long getValue(String code){
        return SpringUtils.getBean(ISysIdtableService.class).get(code);
    }

    /**
     * 获取系统缓存
     * @return
     */
    public static CacheService getCache() {
        return SpringUtils.getBean(CacheService.class);
    }

}
