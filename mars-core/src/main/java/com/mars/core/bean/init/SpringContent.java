package com.mars.core.bean.init;

import com.mars.core.util.LoggerUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * spring 系统配置文件读取程序
 * Created by lixl on 2016/12/12.
 */
public class SpringContent {

    private static LoggerUtils logger = new LoggerUtils(SpringContent.class);

    static{
        init();
    }

    private final static String DBURL_KEY = "db.url";
    private final static String DB_DRIVERCLASSNAME = "db.driverClassName";
    private final static String DB_USERNAME = "db.username";
    private final static String DB_PASSWORD = "db.password";
    private final static String DB_DIALECT = "db.dialect";
    private final static String DB_SHOWSQL = "db.showsql";

    private final static String REDIS_WEB_URL = "redis.web.url";
    private final static String REDIS_WEB_PORT = "redis.web.port";
    private final static String REDIS_WEB_PASSWORD = "redis.web.password";
    private final static String SYSTEM_CHARACTERCODE = "system.characterCode";
    private final static String SYSTEM_CACHE = "system.cache";
    private final static String WX_TASK_IP = "wx.task.ip";
    private final static String WX_TASK_PORT = "wx.task.port";
    private final static String SYS_DEV = "system.dev";





    private static volatile SpringContent instances;


    private static final void init(){
        if(instances == null){
            instances = new SpringContent();
            Properties config = loadSpringConfig();
            instances.dburl = config.getProperty(DBURL_KEY);
            instances.dbDialect = config.getProperty(DB_DIALECT);
            instances.dbDriverClassName = config.getProperty(DB_DRIVERCLASSNAME);
            instances.dbUsername = config.getProperty(DB_USERNAME);
            instances.dbPassword = config.getProperty(DB_PASSWORD);

            if(config.getProperty(DB_SHOWSQL) != null){
                instances.dbShowsql = Boolean.parseBoolean(config.getProperty(DB_SHOWSQL));
            }

            if(config.getProperty(REDIS_WEB_PORT) != null){
                instances.redisWebPort = Integer.parseInt(config.getProperty(REDIS_WEB_PORT));
            }

            instances.redisWebUrl = config.getProperty(REDIS_WEB_URL);
            instances.redisWebPassword = config.getProperty(REDIS_WEB_PASSWORD);

            if(config.getProperty(SYSTEM_CACHE) != null){
                instances.systemCache = Boolean.parseBoolean(config.getProperty(SYSTEM_CACHE));
            }else{
                instances.systemCache = true;
            }

            instances.systemCharacterCode = config.getProperty(SYSTEM_CHARACTERCODE);
            instances.wxTaskIp = config.getProperty(WX_TASK_IP);

            if(config.getProperty(WX_TASK_PORT) != null){
                instances.wxTaskPort = Integer.parseInt(config.getProperty(WX_TASK_PORT));
            }

            isDev = Boolean.parseBoolean(config.getProperty(SYS_DEV,"false"));

            logger.info("成功加载spring数据配置。");
        }
    }

    private static String dburl;
    private static String dbDriverClassName;
    private static String dbUsername;
    private static String dbPassword;
    private static String dbDialect;
    private static boolean dbShowsql;

    private static String redisWebUrl;
    private static Integer redisWebPort;
    private static String redisWebPassword;

    private static String systemCharacterCode;
    private static boolean systemCache;
    private static String wxTaskIp;
    private static Integer wxTaskPort;
    private static boolean isDev;

    private static Properties loadSpringConfig(){
        Properties properties = new Properties();
        InputStream ins = null;
        try{
            ins =MvcConfig.class.getResourceAsStream("/spring.properties");
            properties.load(ins);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("加载spring系统配置文件出错"+e.getMessage());
        }finally{
            try {
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }


    public static String getDburl() {
        return dburl;
    }

    public static String getDbDriverClassName() {
        return dbDriverClassName;
    }

    public static String getDbUsername() {
        return dbUsername;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static String getDbDialect() {
        return dbDialect;
    }

    public static boolean getDbShowsql() {
        return dbShowsql;
    }

    public static String getRedisWebUrl() {
        return redisWebUrl;
    }

    public static Integer getRedisWebPort() {
        return redisWebPort;
    }

    public static String getRedisWebPassword() {
        return redisWebPassword;
    }

    public static String getSystemCharacterCode() {
        return systemCharacterCode;
    }

    public static boolean isSystemCache() {
        return systemCache;
    }

    public static String getWxTaskIp() {
        return wxTaskIp;
    }

    public static Integer getWxTaskPort() {
        return wxTaskPort;
    }

    public static boolean isDev(){
        return isDev;
    }
}
