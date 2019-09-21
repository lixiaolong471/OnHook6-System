package com.mars.core.util;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by lixl on 2017/2/17.
 */
public class ValidateUtils {

    /**
     * 正则表达式：验证用户名 5-18
     */
    private static final String REGEX_USERNAME = "^[a-zA-Z]\\w{4,17}$";

    /**
     * 正则表达式：验证密码
     */
    private static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    private static final String REGEX_MOBILE = "^1[34578][0-9]{9}$";

    /**
     * 正则表达式：验证邮箱
     */
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    private static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    private static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    private static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    private static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式：验证是否为数字
     */
    private static final String REGEX_NUMBER = "^(\\-|\\+)?\\d+(\\.\\d+)?$";

    /**
     * 正则表达式：验证是否为整数
     */
    private static final String REGEX_INTEGER = "^(\\-|\\+)?(\\d|([1-9]{1}\\d+))$";



    /**
     * 验证邮箱
     * @param line
     * @return
     */
    public static boolean validateEmail(String line) {
        return Pattern.matches(REGEX_EMAIL,line);
    }

    /**
     * 用户名验证
     * @param line
     * @return
     */
    public static boolean validateUserName(String line) {
        return Pattern.matches(REGEX_USERNAME,line);
    }

    /**
     * 密码验证
     * @param line
     * @return
     */
    public static boolean validatePasswd(String line) {
        return Pattern.matches(REGEX_PASSWORD,line);
    }

    /**
     * 严重是否为中文字符
     * @param line
     * @return
     */
    public static boolean validateChinese(String line) {
        return Pattern.matches(REGEX_CHINESE,line);
    }

    /**
     * 验证是否为身份证号码
     * @param line
     * @return
     */
    public static boolean validateIdCard(String line) {
        return Pattern.matches(REGEX_ID_CARD,line);
    }

    /**
     * 验证是否为URL地址
     * @param line
     * @return
     */
    public static boolean validateUrl(String line) {
        return Pattern.matches(REGEX_URL,line);
    }

    /**
     * 验证是否为IP地址
     * @param line
     * @return
     */
    public static boolean validateIp (String line) {
        return Pattern.matches(REGEX_IP_ADDR,line);
    }

    /**
     * 验证手机号码
     * @param line
     * @return
     */
    public static boolean validateMobile (String line) {
        return Pattern.matches(REGEX_MOBILE,line);
    }

    /**
     * 验证是否为数字
     * @param line
     * @return
     */
    public static boolean validateNumber (String line) {
        return Pattern.matches(REGEX_NUMBER,line);
    }

    /**
     * 验证是否为整数
     * @param line
     * @return
     */
    public static boolean validateInteger (String line) {
        return Pattern.matches(REGEX_INTEGER,line);
    }


    /**
     * 是否在允许范围能的长度
     * @param str
     * @param length
     * @return
     */
    public static boolean max(String str,int length){
        return StringUtils.isEmpty(str) || str.length() <=length;
    }

    /**
     * 验证限定必须超过最新范围
     * @param str
     * @param length
     * @return
     */
    public static boolean min(String str,int length){
        return StringUtils.isEmpty(str) || str.length() >= length;
    }

    /**
     * 判断两个数据是否相等
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean equals(Object obj1,Object obj2){
        if(obj1 == obj2){
            return true;
        }else if(obj1!=null && obj2 != null && obj1.equals(obj2)){
            return true;
        }else if(obj1 instanceof BigDecimal && obj1 != null && obj2 != null && ((BigDecimal) obj1).compareTo((BigDecimal) obj2) == 0) {
            return true;
        }else if(obj1 instanceof Timestamp && obj1 != null && obj2 != null) {
            return DateUtils.getYYYYMMDDHHMMSS((Timestamp)obj1).equals(DateUtils.getYYYYMMDDHHMMSS((Timestamp)obj2));
        }else if(obj1 instanceof Date && obj1 != null && obj2 != null) {
            return DateUtils.getYYYY_MM_DD((Date)obj1).equals(DateUtils.getYYYY_MM_DD((Date)obj2));
        }else{
            return false;
        }
    }




}
