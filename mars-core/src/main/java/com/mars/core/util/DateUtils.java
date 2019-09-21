package com.mars.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lixl on 2017/2/17.
 */
public class DateUtils {

    public static final String FORMAT_TYPE_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String FORMAT_TYPE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_TYPE_YYYYMMDD = "yyyyMMdd";
    
    public static final String FORMAT_TYPE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    
    public static final String FORMAT_TYPE_YYYY = "yyyy";
    
    public static final String FORMAT_CN_TYPE_YYYY_MM_DD = "yyyy年MM月dd日";
    
    public static final String FORMAT_CN_TYPE_MM_DD = "MM月dd日";

    public static final String FORMAT_TYPE_YYYY_MM = "yyyy-MM";
    
    public static final String FORMAT_TYPE_DD = "dd";
    
    public static final String FORMAT_TYPE_MM = "MM";

    /**
     * 获取指定格式的日期类型
     * @param date
     * @param format
     * @return
     */
    public static String getStr(Date date,String format){
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 获取yyyy-mm-dd hh:mm:ss 格式的日期字符串
     * @param date
     * @return
     */
    public static String getYYYY_MM_DD_HH_MM_SS(Date date){
        return getStr(date,FORMAT_TYPE_YYYY_MM_DD_HH_MM_SS);
    }
    
    /**
     * 获取yyyymmddhhmmss 格式的日期字符串
     * @param date
     * @return
     */
    public static String getYYYYMMDDHHMMSS(Date date){
        return getStr(date,FORMAT_TYPE_YYYYMMDDHHMMSS);
    }

    /**
     * 获取yyyy-mm-dd 格式的日期字符串
     * @param date
     * @return
     */
    public static String getYYYY_MM_DD(Date date){
        return getStr(date,FORMAT_TYPE_YYYY_MM_DD);
    }
    
    /**
     * 获取yyyy-mm 格式的日期字符串
     * @param date
     * @return
     */
    public static String getYYYY_MM(Date date){
        return getStr(date,FORMAT_TYPE_YYYY_MM);
    }
    
    /**
     * 获取MM 格式的日期字符串
     * @param date
     * @return
     */
    public static String getMM(Date date){
        return getStr(date,FORMAT_TYPE_MM);
    }
    
    /**
     * 获取dd 格式的日期字符串
     * @param date
     * @return
     */
    public static String getDD(Date date){
        return getStr(date,FORMAT_TYPE_DD);
    }


    /**
     * 解析指定类型的日期
     * @param str
     * @param format
     * @return
     */
    public static Date parse(String str,String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析指定类型日期，格式为 yyyy-mm-dd
     * @param str
     * @return
     */
    public static Date parseYYYY_MM_DD(String str){
        return parse(str,FORMAT_TYPE_YYYY_MM_DD);
    }


    /**
     * 解析指定类型日期，格式为 yyyy-mm-dd hh:mm:ss
     * @param str
     * @return
     */
    public static Date parseYYYY_MM_DD_HH_MM_SS(String str){
        return parse(str,FORMAT_TYPE_YYYY_MM_DD_HH_MM_SS);
    }
    
    /**
     * 解析指定类型日期，格式为 yyyymmddhhmmss
     * @param str
     * @return
     */
    public static Date parseYYYYMMDDHHMMSS(String str){
        return parse(str,FORMAT_TYPE_YYYYMMDDHHMMSS);
    }
    
    /**
     * 解析指定类型日期，格式为 yyyymmdd
     * @param str
     * @return
     */
    public static Date parseYYYYMMDD(String str){
        return parse(str,FORMAT_TYPE_YYYYMMDD);
    }


    /**
     * 对当前时间进行 增加操作 ，例如当前时间为1月1日 ，可对当前时间加1天操作，
     * 操作完成后时间就为1月2日
     * @param date 当前时间
     * @param count 累加数
     * @param type 累加类型， 年、月、日、时、分、秒 等
     * @see java.util.Calendar#add(int,int)
     * @return
     */
    public static Date addValue(Date date,int count,int type){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(type,count);
        return cal.getTime();
    }

    /**
     * 给当前日期做日的累加操作 ，当day为负数时，即为减得操作
     * @param date
     * @param day
     * @return
     * @see #addValue(Date, int, int)
     */
    public static Date addDay(Date date,int day){
        return addValue(date,day,Calendar.DAY_OF_MONTH);
    }

    /**
     * 给当前日期做月的加/减操作
     * @param date
     * @param month
     * @return
     * @see #addValue(Date, int, int)
     */
    public static Date addMonth(Date date,int month){
        return addValue(date,month,Calendar.MONTH);
    }

    /**
     * 给当前日期做年的加/减操作
     * @param date
     * @param year
     * @return
     * @see #addValue(Date, int, int)
     */
    public static Date addYear(Date date,int year){
        return addValue(date,year,Calendar.YEAR);
    }

    /**
     * 获取系统当前时间 ，时间类型为Timestamp
     * @return
     */
    public static Timestamp getDatetime(){
        return new Timestamp(System.currentTimeMillis());
    }
    
    /**
     * 获取月的第一天
     * @return
     */
    public static Date getMonthFirstDay(Date date){
    	Calendar ca = Calendar.getInstance();
    	ca.setTime(date);
    	ca.set(Calendar.DAY_OF_MONTH,1);
    	Date dateTime=ca.getTime();
    	SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
    	String firstDate=datef.format(dateTime)+" 00:00:00";
    	return parseYYYY_MM_DD_HH_MM_SS(firstDate);
    }
    
    /**
     * 获取月的第一天
     * @return
     */
    public static Date getMonthLastDay(Date date){
    	Calendar ca = Calendar.getInstance();
    	ca.setTime(date);
    	ca.set(Calendar.DAY_OF_MONTH,ca.getActualMaximum(Calendar.DAY_OF_MONTH));
    	Date dateTime=ca.getTime();
    	SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
    	String firstDate=datef.format(dateTime)+" 23:59:59";
    	return parseYYYY_MM_DD_HH_MM_SS(firstDate);
    }
    
    /**
     * 通过出生年月日算出未来1~num天的生日日期
     * @param birthday
     * @param num
     * @return
     */
    public static Date getNewBirthday(Date birthday,int num){
    	Date d=DateUtils.parseYYYY_MM_DD(DateUtils.getYYYY_MM_DD(new Date()));
		String oldmmdd=DateUtils.getStr(birthday,"MM-dd");
    	for (int i = num; i > 0; i--) {
    		Date newDate=DateUtils.addDay(d, i);
    		String newmmdd=DateUtils.getStr(newDate,"MM-dd");
    		if(oldmmdd.equals(newmmdd)){
    			return newDate;
    		}
    		
		}
    	return null;
    }
    
    public static boolean isBirthday(Date nowDate,Date birthday){
		String oldmmdd=DateUtils.getStr(birthday,"MM-dd");
		String newmmdd=DateUtils.getStr(nowDate,"MM-dd");
		if(oldmmdd.equals(newmmdd)){
			return true;
		}
		return false;
    }
    
    public static String getMMDD(Date date){
    	return DateUtils.getStr(date,FORMAT_CN_TYPE_MM_DD);
    }
    
    public static String getYYYY(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TYPE_YYYY);
        return sdf.format(date);
    }
    
   /*
    * 获取yyyy-mm-dd格式的日期字符串
    * @param date
    * @return
    */
   public static String getYYYYMMDD(Date date){
       return getStr(date,FORMAT_TYPE_YYYYMMDD);
   }
   
   /*
    * 获取yyyy年mm月dd日 格式的日期字符串
    * @param date
    * @return
    */
   public static String getCN_YYYY_MM_DD(Date date){
       return getStr(date,FORMAT_CN_TYPE_YYYY_MM_DD);
   }
   
}
