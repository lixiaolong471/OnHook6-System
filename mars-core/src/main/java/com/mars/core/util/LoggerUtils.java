package com.mars.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

/**
 * Created by lixl on 2017/2/17.
 */
public class LoggerUtils {

    private Logger logger;

    public static int level = 0 ;

    public final static int DEBUG = 1;
    public final static int INFO = 2;
    public final static int WARN = 3;
    public final static int ERROR = 4;

    public LoggerUtils(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * 检查日志级别
     * @param lvl
     * @return
     */
    private boolean checkLevel(int lvl) {
        if (level > lvl) {
            return false;
        }
        return true;


    }

    /**
     * 设置日志级别
     * @param lvl
     */
    public static  boolean setLevel(int lvl){

        if(lvl >=0 && lvl <=10){
            LoggerUtils.level = lvl;
            return true;
        }
        return false;

    }

    public void debug(String arg0, Throwable arg1) {
        if (checkLevel(DEBUG)) {
            logger.debug(getCallerInfo()  + arg0, arg1);
        }
    }

    private String getCallerInfo() {

        StringBuffer sb = new StringBuffer();
        StackTraceElement[] ste = new Throwable().getStackTrace();
        if(null != ste && ste.length > 2) {

            StackTraceElement st = ste[2];
            sb.append( st.getClassName() ).append(".").append(st.getMethodName()).append("(");
            sb.append( st.getClassName().replaceAll(".*\\.(.*)$", "$1.java:")).append(st.getLineNumber());
            sb.append(") ==> ");


        }

        return sb.toString();
    }

    public void debug(String msg) {
        if (checkLevel(DEBUG)) {

            logger.debug(getCallerInfo()  + msg);
        }


    }

    public void debug(String arg0, Object arg1) {
        if (checkLevel(DEBUG)) {
            logger.debug(getCallerInfo()  + arg0, arg1);
        }

    }

    public void debug(String arg0, Object[] arg1) {
        if (checkLevel(DEBUG)) {
            logger.debug(getCallerInfo()  + arg0, arg1);
        }
    }


    public void error(String arg0) {
        if (checkLevel(ERROR)) {
            logger.error(getCallerInfo()  + arg0);
        }
    }

    public void error(String arg0, Object arg1) {
        if (checkLevel(ERROR)) {
            logger.error(getCallerInfo()  + arg0, arg1);
        }


    }

    public void error(String arg0, Object[] arg1) {
        if (checkLevel(ERROR)) {
            logger.error(getCallerInfo()  + arg0, arg1);
        }
    }

    public void error(String arg0, Throwable arg1) {
        if (checkLevel(ERROR)) {
            logger.error(getCallerInfo()  + arg0, arg1);
        }
    }

    public void error(Marker arg0, String arg1) {
        if (logger.isErrorEnabled(arg0)) {
            logger.error(getCallerInfo()  + arg0, arg1);
        }
    }

    public void info(String arg0) {
        if (checkLevel(INFO)) {
            logger.info(getCallerInfo() + arg0);
        }
    }

    public void info(String arg0, Object arg1) {
        if (checkLevel(INFO)) {
            logger.info(getCallerInfo()  + arg0, arg1);
        }
    }

    public void info(String arg0, Object[] arg1) {
        if (checkLevel(INFO)) {
            logger.info(getCallerInfo()  + arg0, arg1);
        }
    }

    public void info(String arg0, Throwable arg1) {
        if (checkLevel(INFO)) {
            logger.info(getCallerInfo()  + arg0, arg1);
        }

    }

    public void info(Marker arg0, String arg1) {
        if (logger.isInfoEnabled(arg0)) {
            logger.info(getCallerInfo()  + arg0, arg1);
        }

    }

    public void info(String arg0, Object arg1, Object arg2) {
        if(checkLevel(INFO)){
            logger.info(getCallerInfo()  + arg0, arg1, arg2);
        }
    }
    public void warn(String arg0) {
        if (checkLevel(WARN)) {
            logger.warn(getCallerInfo()  + arg0);
        }
    }

    public void warn(String arg0, Object arg1) {
        if (checkLevel(WARN)) {
            logger.warn(getCallerInfo()  + arg0, arg1);
        }
    }

    public void warn(String arg0, Object[] arg1) {
        if (checkLevel(WARN)) {
            logger.warn(getCallerInfo()  + arg0, arg1);
        }
    }

    public void warn(String arg0, Throwable arg1) {
        if (checkLevel(WARN)) {
            logger.warn(getCallerInfo()  + arg0, arg1);
        }
    }

    public void warn(Marker arg0, String arg1) {
        if (logger.isWarnEnabled(arg0)) {
            logger.warn(getCallerInfo()  + arg0, arg1);
        }
    }


    public void debug(String arg0, Object arg1, Object arg2) {
        if (checkLevel(DEBUG)) {
            logger.debug(getCallerInfo() +arg0, arg1,arg2);
        }
    }

    public void error(String arg0, Object arg1, Object arg2) {
        if (checkLevel(ERROR)) {
            logger.error(getCallerInfo()  + arg0, arg1,arg2);
        }
    }


    public void warn(String arg0, Object arg1, Object arg2) {
        if (checkLevel(WARN)) {
            logger.warn(getCallerInfo()  + arg0, arg1,arg2);
        }
    }
}
