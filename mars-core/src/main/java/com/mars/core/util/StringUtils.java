package com.mars.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mars.core.SysConfig;
import org.apache.commons.lang.StringEscapeUtils;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixl on 2017/2/17.
 */
public class StringUtils {

    private static LoggerUtils logger = new LoggerUtils(StringUtils.class);

    public static String byteToString(byte[] b) {
        if(b!=null){
            try {
                return new String(b, SysConfig.getSystemCharacterCode());
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(),e);
                return "";
            }
        }
        return "";
    }

    public static byte[] stringTobyte(String str) {
        if(str != null){
            try {
                return str.getBytes(SysConfig.getSystemCharacterCode());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    /**
     * 清除特殊字符. java调用
     *
     * @param value
     * @return
     */

    public static String cleartSpecialCharacterJava(Object value) {
        if (value == null) {
            return "";
        }
        String tempValue = String.valueOf(value)
                .replaceAll("\"", "\\\"").replaceAll("\r", "")
                .replaceAll("\t", "").replaceAll("\u0000", "");
        tempValue = StringEscapeUtils.escapeHtml(tempValue);
        return tempValue;
    }


    /**
     * 取两个字符串的交集
     */
    public static String getIntersection(String str1, String str2) {
        String targetString = null;
        // 取出其中较短的字符串(照顾效率)
        String shorter = str1.length() > str2.length() ? str2 : str1;
        String longer = shorter.equals(str1) ? str2 : str1;

        // 在较短的字符串中抽取其‘所有长度’的子串，顺序由长到短
        out: for (int subLength = shorter.length(); subLength > 0; subLength--) {
            // 子串的起始角标由 0 开始右移，直至子串尾部与母串的尾部-重合为止
            for (int i = 0; i + subLength <= shorter.length(); i++) {
                String subString = shorter.substring(i, i + subLength); // 取子串
                if (longer.indexOf(subString) >= 0) { // 注意 ‘=’
                    targetString = subString;
                    break out;
                    // 一旦满足条件，则最大子串即找到，停止循环，
                }
            }
        }
        return targetString;
    }

    /**
     * 转义html代码
     *
     * @param str
     * @return
     */
    public static String decodeHtml(String str) {
        if (null == str || "".equals(str.trim())) {
            return "";
        }

        return str.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&quot;", "\"").replaceAll("&nbsp;", " ")
                .replaceAll("&apos;", "'").replaceAll("&amp;", "&");
    }

    /**
     * 解码字符串,utf-8编码格式
     *
     * @param str
     * @return
     */
    public static String decode(String str) {
        return decode(str, "UTF-8");
    }

    /**
     * 解码字符串
     *
     * @param str
     * @param charset
     * @return
     */
    public static String decode(String str, String charset) {
        if (null == str || "".equals(str.trim())) {
            return "";
        }
        try {
            str = URLDecoder.decode(str, charset);
        } catch (Exception e) {
            str = str.trim();
        }
        return str.trim();
    }


    /**
     * 解码字符串,utf-8编码格式
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        return decode(str, "UTF-8");
    }

    /**
     * 解码字符串
     *
     * @param str
     * @param charset
     * @return
     */
    public static String encode(String str, String charset) {
        if (null == str || "".equals(str.trim())) {
            return "";
        }
        try {
            str = URLEncoder.encode(str,charset);
        } catch (Exception e) {
            str = str.trim();
        }
        return str.trim();
    }

    /**
     * 转义html代码
     *
     * @param str
     * @return
     */
    public static String encodeHtml(String str) {
        if (null == str || "".equals(str.trim())) {
            return "";
        }
        return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
                .replaceAll("'", "&apos;");
    }

    /**
     * 判断对象是否为null
     *
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        return null == obj;
    }


    public static boolean isBlank(String str) {
        return isEmpty(str);
    }

    /**
     * 所传入的多个字段均不为空
     * @param strs
     * @return
     */
    public static boolean areNotEmpty(String ...strs) {
        if(strs != null){
            for(String s :strs){
                if(!StringUtils.isNotEmpty(s)){
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (isNull(str)) {
            return true;
        }
        if (str.equalsIgnoreCase("null")){
        	return true;
        }
        if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否不为null
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 是否不包含特殊字符
     * @param str
     * @return
     */
    public static boolean isEmojiCharacter(String str) {
        for (int i = 0; i < str.length(); i++) {
            Character c= str.charAt(i);
            if(!isEmojiCharacter(c)){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为特殊字符
     * @param codePoint
     * @return
     */
    public static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 去除掉sql中的回车等换行字符
     * @param sql
     * @return
     */
    public static String removeBlank(String sql) {
        if (isNotEmpty(sql)) {
            sql = sql.replace('\r', ' ').replace('\n', ' ')
                    .replaceAll(" {2,}", " ");
        }
        return sql;
    }

    public Integer getInteger(String obj) {
        if (StringUtils.isNotEmpty(obj)) {
            return Integer.parseInt(obj);
        }
        return null;
    }

    public Long getLong(String obj) {
        if (StringUtils.isNotEmpty(obj)) {
            return Long.parseLong(obj);
        }
        return null;
    }

    public Double getDouble(String obj) {
        if (StringUtils.isNotEmpty(obj)) {
            return Double.parseDouble(obj);
        }
        return null;
    }

    public Float getFloat(String obj) {
        if (StringUtils.isNotEmpty(obj)) {
            return Float.parseFloat(obj);
        }
        return null;
    }

    public BigDecimal getBigDecimal(String obj) {
        if (StringUtils.isNotEmpty(obj)) {
            return new BigDecimal(obj);
        }
        return null;
    }

    public <T extends Date> T getDate(String obj,Class<T> clz) {
        if (StringUtils.isNotEmpty(obj)) {
            Date date = DateUtils.parseYYYY_MM_DD_HH_MM_SS(obj);
            if(clz.isAssignableFrom(java.sql.Timestamp.class)){
                return (T) new Timestamp(date.getTime());
            }else if(clz.isAssignableFrom(java.sql.Date.class)){
                return (T) new java.sql.Date(date.getTime());
            }else if(clz.isAssignableFrom(java.util.Date.class)){
                return (T) date;
            }
        }
        return null;
    }

    public JSONObject getJSONObject(String obj){
        return JSONObject.parseObject(obj);
    }

    public JSONArray getJSONArray(String obj){
        return JSONArray.parseArray(obj);
    }

    public static String join(Object[] array, String separator){
    	return org.apache.commons.lang.StringUtils.join(array, separator);
    }
    
    public static String getBirthByIdCard(String idCard){
    	try{
        	return idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);
    	}catch (Exception e) {
    		logger.error("从身份证中获取生日日期失败");
		}
    	return null;
    }
    
    public static Date parseBirthByIdCard(String idCard){
    	String birth=getBirthByIdCard(idCard);
    	if(isNotEmpty(birth)){
    		return DateUtils.parseYYYY_MM_DD(getBirthByIdCard(idCard));
    	}
    	return null;
    }
    
    public static String getMatcher(String regex, String source) {  
        String result = "";  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(source);  
        while (matcher.find()) {  
            result = matcher.group(1);
        }  
        return result;  
    }
    
    public static String getValueInBrackets(String str){
    	Pattern pattern = Pattern.compile("[\\(|（](.*?)[\\)|）]"); //中英文括号
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			sb.append(matcher.group(1));
		}
		return sb.toString();
    }
    
    public static String genSignData(JSONObject jsonObject) {
		StringBuffer content = new StringBuffer();

		// 按照key做首字母升序排列
		List<String> keys = new ArrayList<String>(jsonObject.keySet());
		Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			// sign 和ip_client 不参与签名
			if ("sign".equals(key)) {
				continue;
			}
			String value = (String) jsonObject.getString(key);
			// 空串不参与签名
			if (StringUtils.isEmpty(value)) {
				continue;
			}
			content.append((i == 0 ? "" : "&") + key + "=" + value);

		}
		String signSrc = content.toString();
		if (signSrc.startsWith("&")) {
			signSrc = signSrc.replaceFirst("&", "");
		}
		return signSrc;
	}

}
