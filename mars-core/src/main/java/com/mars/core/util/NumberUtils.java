package com.mars.core.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {

	/**
	 * 
	 * 
	 * Description:object转int
	 * 
	 * @param obj
	 * @return
	 */
	public static int objToNum(Object obj) {

		return null == obj || "null".equals(obj.toString()) ? 0 : Integer
				.parseInt(obj.toString());

	}

	public static Long objToLong(Object obj) {

		return null == obj || "null".equals(obj.toString()) ? 0 : Long
				.parseLong(obj.toString());

	}

	/**
	 * 
	 * 
	 * Description:object转double
	 * 
	 * @param obj
	 * @return
	 */
	public static Double objToDouble(Object obj) {

		return null == obj || "null".equals(obj.toString()) ? 0 : Double
				.parseDouble(obj.toString());

	}

	public static boolean isEmptyLongOrZero(Object obj) {
		if (null == obj || "".equals(obj.toString())
				|| "null".equals(obj.toString())
				|| Long.parseLong(obj.toString()) == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmptyIntegerOrZero(Object obj) {
		if (null == obj || "".equals(obj.toString())
				|| "null".equals(obj.toString())
				|| Integer.valueOf(obj.toString()) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化两位小数
	 * 
	 * @param num
	 * @return
	 */
	public static String formatTowDecimal(Number num) {
		if(num == null){
			return null;
		}
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(num);
	}

	
	/**
	 * 格式化两位小数
	 * 
	 * @param num
	 * @return
	 */
	public static String formatTowDecimal(BigDecimal num) {
		if(num == null){
			return null;
		}
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(num);
	}

	/**
	 * 格式化两位小数
	 *
	 * @param num
	 * @return
	 */
	public static String formatFourDecimal(Number num) {
		if(num == null){
			return null;
		}
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(num);
	}

	/**
	 * 格式化两位小数
	 *
	 * @param num
	 * @return
	 */
	public static String formatSixDecimal(Number num) {
		if(num == null){
			return null;
		}
		DecimalFormat df = new DecimalFormat("#.######");
		return df.format(num);
	}

	/**
	 * 四舍五入，取两位小数
	 * 
	 * @param num
	 * @return
	 */
	public static double toTowDecimal(double num) {
		BigDecimal bd = new BigDecimal(num);
		num = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return num;
	}
	
	/**
	 * 格式化为金额格式(不四舍五入)
	 * @param s 需要格式化的数字字符串
	 * @param n 小数位数
	 * @return
	 */
	public static String fmoney(String s, int n) {
		if(StringUtils.isBlank(s)){
			return null;
		}
	    n = n >= 0 && n <= 20 ? n : 2; 
	    String str=s.replace("[^\\d\\.-]", "");
	    String [] arr = str.split("\\.");
	    if(arr.length>1){
	    	if(arr[1].length()>=n){
	    		str=str.substring(0, str.indexOf(".")+n+1);
	    	}
	    }
	    System.out.println(str);
	    StringBuffer format=new StringBuffer("#,##0");
	    for (int i = 0; i < n; i++) {
	    	if(i==0){
		    	format.append(".");
	    	}
	    	format.append("0");
		}
		DecimalFormat df = new DecimalFormat(format.toString()); 
	    return df.format(new BigDecimal(str));
	}
	
	/**
	 * 金额格式还原数字
	 * @param s
	 */
	public static String rmoney(String s){
		if(StringUtils.isBlank(s)){
			return null;
		}
		return s.replace("[^\\d\\.-]", "");
	}
	
	/**
	 * 是否金额
	 * @param s
	 * @return
	 */
	public static boolean isMoney(String s){
		String re = "([1-9]\\d*|0)(\\.\\d{1,2})?";
		try{
			return s.matches(re);
		}catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 格式化为金额格式(不四舍五入)
	 * @param s 需要格式化的数字字符串
	 * @param n 小数位数
	 * @return
	 */
	public static String formatTowDecimal(String s, int n) {
		if(StringUtils.isBlank(s)){
			return null;
		}
	    n = n >= 0 && n <= 20 ? n : 2; 
	    String str=s.replace("[^\\d\\.-]", "");
	    String [] arr = str.split("\\.");
	    if(arr.length>1){
	    	if(arr[1].length()>=n){
	    		str=str.substring(0, str.indexOf(".")+n+1);
	    	}
	    }
	    return str;
	}
	
	/**
	 * 不四舍五入
	 * @param oldDecimal
	 * @return
	 */
	public static BigDecimal getTowDecimal(BigDecimal oldDecimal){
		return new BigDecimal(formatTowDecimal(String.valueOf(oldDecimal), 2));
	}

}
