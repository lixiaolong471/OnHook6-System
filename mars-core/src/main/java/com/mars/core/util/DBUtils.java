package com.mars.core.util;

import java.util.List;

/**
 * Created by lixl on 2017/3/1.
 */
public class DBUtils {

    /**
     * 不为空的话: and = ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullEqAnd(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " AND "+ name + "= ?";
            params.add(val);
        }
        return result;
    }

    /**
     * 不为空的话: or = ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullEqOr(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " OR "+ name + "= ?";
            params.add(val);
        }
        return result;
    }

    /**
     * 不为空的话: and = ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullLikeOr(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " OR "+ name + "like ?";
            params.add("%"+val+"%");
        }
        return result;
    }

    /**
     * 不为空的话: and like ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullLikeAnd(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " AND "+ name + "like ?";
            params.add("%"+val+"%");
        }
        return result;
    }

    /**
     * 不为空的话: and > ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullGtAnd(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " AND "+ name + " > ?";
            params.add(val);
        }
        return result;
    }
    /**
     * 不为空的话: or > ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullGtOr(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " OR "+ name + " > ?";
            params.add(val);
        }
        return result;
    }

    /**
     * 不为空的话: and >= ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullGteAnd(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " AND "+ name + " >= ?";
            params.add(val);
        }
        return result;
    }


    /**
     * 不为空的话: or >= ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullGteOr(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " OR "+ name + " >= ?";
            params.add(val);
        }
        return result;
    }


    /**
     * 不为空的话: and < ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullLtAnd(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " AND "+ name + " < ?";
            params.add(val);
        }
        return result;
    }
    /**
     * 不为空的话: or < ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullLtOr(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " OR "+ name + " < ?";
            params.add(val);
        }
        return result;
    }

    /**
     * 不为空的话: and <= ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullLteAnd(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " AND "+ name + " <= ?";
            params.add(val);
        }
        return result;
    }


    /**
     * 不为空的话: or <= ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullLteOr(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " OR "+ name + " <= ?";
            params.add(val);
        }
        return result;
    }

    /**
     * 不为空的话: or != ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullNeqAnd(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " AND "+ name + "!= ?";
            params.add(val);
        }
        return result;
    }

    /**
     * 不为空的话: and != ?
     * @param name 参数列名
     * @param val 参数值
     * @param params 参数容器，如果给出的参数值不为空，会将参数存入list中，按调用顺序存放
     * @return
     */
    public static String  notNullNeqOr(String name,Object val,List<Object> params){
        String result = "";
        if(val != null && ( !(val instanceof String) || StringUtils.isNotEmpty((String)val))){
            result = " OR "+ name + "!= ?";
            params.add(val);
        }
        return result;
    }

}
