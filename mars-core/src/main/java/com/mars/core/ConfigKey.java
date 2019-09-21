package com.mars.core;

/**
 * Created by lixl on 2017/2/21.
 */
public interface ConfigKey {

    /**
     * 系统基础配置key
     */
    public interface SYSTEM{
        /**系统素材绝对路径*/
//        public String SYS_WEB_HTTPPATH = "sys.web.httppath";
        /**素材存储目录*/
//        public String SYS_MATERIAL_REALPATH = "sys.material.realpath";
        /**素材访问路径*/
//        public String SYS_MATERIAL_HTTPPATH = "sys.material.httppath";
    }

    /**
     * 系统用户登录配置key
     */
    public interface LOGIN{
        /**密码修改提示时间(天)*/
        public String SYS_USER_PASSWORD_UPDATE_HINT = "sys.user.password.update.hint";
        /**锁定账户密码错误次数*/
        public String SYS_USER_PASSWORD_ERR_LOCK_TIME = "sys.user.password.err.lock.time";
        /**用户登录超时时间*/
        public String SYS_USER_LOGIN_SESSION_OUT_TIME = "sys.user.login.session.out.time";
        /**初始化密码*/
        public String SYS_USER_LOGIN_INIT_PASSWORD = "sys_user_login_init_password";
    }

    /**
     * 短信配置KEY
     */
    public interface MESSAGE{
        /**短信发送账户**/
        public static final String COM_SMS_SEND_UID = "com.sms.send.uid";
        /**短信发送密码**/
        public static final String COM_SMS_SEND_PASS = "com.sms.send.pass";
        /**短信发送地址**/
        public static final String COM_SMS_SEND_URL = "com.sms.send.url";
        /**短信接入号**/
        public static final String COM_SMS_SEND_SRCPHONE = "com.sms.send.srcphone";
        /** 单用户最多次数(天)*/
        public static final String COM_SMS_SEND_DAY = "com.sms.send.day";
    }


    public interface MAIL{
        /**邮件发送账户**/
        public static final String SYS_MAIL_SEND_NAME = "sys.mail.send.name";
        /**邮件发送密码**/
        public static final String SYS_MAIL_SEND_PASS = "sys.mail.send.pass";
        /**邮件发送地址**/
        public static final String SYS_MAIL_SEND_HOST = "sys.mail.send.host";
        /**邮件发送端口**/
        public static final String SYS_MAIL_SEND_PORT = "sys.mail.send.port";
        /**邮件发送邮件**/
        public static final String SYS_MAIL_SEND_FROM = "sys.mail.send.from";
        /**邮件发送标题**/
        public static final String SYS_MAIL_SEND_TITLE = "sys.mail.send.title";
    }
    
    /**
     * 职位
     * @author tantx
     *
     */
    public interface SYS_POSITION{
        /**职员/实习生/兼职**/
        int STAFF_MEMBER = 1;
        /**组长/主管/经理/副经理**/
        int MANAGER = 2;
        /**总监/总经理/总裁/董事长**/
        int GENERAL_MANAGER = 3;
    }

}
