package com.mars.core.util;

import com.mars.core.bean.annotation.tree.Node;
import com.mars.core.bean.annotation.tree.Parent;
import com.mars.core.bean.annotation.tree.Tree;
import com.mars.core.exception.ServiceException;
import com.mars.core.util.collection.Convert;
import com.mars.core.util.collection.Filter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by lixl on 2017/2/21.
 */
public class CollectionUtils {

    public static <T>  List<T> toList(T[] obj){
        List list = new ArrayList();
        if(obj == null){
            return list;
        }
        for(int i=0;i<obj.length;i++){
            list.add(obj[i]);
        }
        return list;
    }

    public static List<Map<String,Object>> getTree(List<?> dataList){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Object> topList = getChild(null,dataList);
        for(Object top:topList){
            list.add(getTree(top,1,dataList));
        }
        return list;
    }

    private static Map<String,Object> getTree(Object data,int level,List dataList){
        Map<String,Object> map = new HashMap<>();
        try{
            Tree tree = data.getClass().getAnnotation(Tree.class);
            if(tree != null){
                Field[] fields = getDeclaredFields(data);
                List<String> fieldNames = new ArrayList<>();
                for(Field f:fields){
                    Node node = f.getAnnotation(Node.class);
                    if(node !=null){
                        f.setAccessible(true);
                        String key = f.getName();
                        if(StringUtils.isNotEmpty(node.name())){
                            key = node.name();
                        }
                        fieldNames.add(key);
                    }
                }
                String[] fieldArray = fieldNames.toArray(new String[0]);
                map = getMapByObject(false,data,fieldArray);
                map.put("level",level);
                List child = getChild(data,dataList);
                List<Map<String,Object>> childList = new ArrayList<>();
                if(child != null && !child.isEmpty()){
                    for(Object childData:child){
                        childList.add(getTree(childData,level+1,dataList));
                    }
                    map.put("child",childList);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return map;
    }

    /**
     * 获取树list中树元素的子一级元素列表
     * @param parent
     * @param datalist
     * @return
     * @throws IllegalAccessException
     */
    public static List getChild(Object parent,List datalist) {
        List<Object> data = new ArrayList<>();
        try{
            for(Object obj:datalist){
                Tree tree = obj.getClass().getAnnotation(Tree.class);
                if(tree != null){
                    Field[] fields = getDeclaredFields(obj);
                    for(Field f:fields) {
                        Parent node = f.getAnnotation(Parent.class);
                        if(node !=null){
                            f.setAccessible(true);
                            Object nodeObj =  f.get(obj);
                            if(nodeEq(parent,nodeObj)){
                                data.add(obj);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return data;
    }

    private static boolean nodeEq(Object one,Object two) throws IllegalAccessException {
        if(one == null && two == null){
            return true;
        }else if(one != null && two != null){
            Object onePk = null;
            Object twoPk = null;
            if(one != null){
                onePk = getNodeKey(one);
            }
            if(two != null){
                twoPk = getNodeKey(two);
            }
            if(onePk != null && twoPk != null){
                if(onePk.equals(twoPk)){
                    return true;
                }
            }
        }
        return false;
    }


    private static Object getNodeKey(Object obj) throws IllegalAccessException {
        Tree tree = obj.getClass().getAnnotation(Tree.class);
        if(tree !=null){
            Field[] fields = getDeclaredFields(obj);
            for(Field f:fields) {
                Node node = f.getAnnotation(Node.class);
                if(node !=null && node.key()){
                    f.setAccessible(true);
                    return f.get(obj);
                }
            }
        }
        return null;
    }

    /**
     * 获取指定list指定属性值的数组 ，例如获取一个列表对应id的数组
      * @param lst
     * @param prop
     * @return
     */
    public static <T> T[] getProperty(T[] ts,List lst,String prop){
        List<T> list = new ArrayList<>();
        try{
            for(Object obj:lst){
                Field[] fields = getDeclaredFields(obj);
                for(Field f:fields){
                    f.setAccessible(true);
                    if(f.getName().equals(prop) && f.get(obj) != null){
                        list.add((T)f.get(obj));
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list.toArray(ts);
    }


    /**
     * 把结果封装到map中，用于easyui datagrid返回rows
     * @param pageList
     * @param fields
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getList(List<?> pageList, String... fields) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(Object o : pageList){

            if(o instanceof Object[]){
                list.add(getMapByObjects(true, (Object[])o, fields));
            }else if(o instanceof Object){
                list.add(getMapByObject(true, o, fields));
            }

        }
        return list;
    }

    public static List<Map<String, Object>> getList(boolean enCode,List<?> pageList, String... fields) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try{
            for(Object o : pageList){
                if(o instanceof Object[]){
                    list.add(getMapByObjects(enCode, (Object[])o, fields));
                }else if(o instanceof Object){
                    list.add(getMapByObject(enCode, o, fields));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return list;
    }
    
    public static List<Map<String, Object>> getListWithObjKey(boolean enCode,List<?> pageList, String... fields) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try{
            for(Object o : pageList){
                if(o instanceof Object[]){
                    list.add(getMapByObjectsWithObjKey(enCode, (Object[])o, fields));
                }else if(o instanceof Object){
                    list.add(getMapByObjectWithObjKey(enCode, o, fields));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return list;
    }

    public static Map<String, Object> getMapByObject(boolean enCode, Object o, String... fields){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            //temp保存最初始的泛型
            Object temp = o;
            for(int i=0;i<fields.length;i++){
                o = temp;
                Object _o = o;
    //			Class<? extends Object> c = _o.getClass();
                Field f = null;
                String[] _s = null;
                String s = fields[i].replace(".", ",");
                if(s.indexOf(",")>0){
                    //获取对象映射类型数据
                    try{_s = s.split(",");
                        for(int j=0;j<_s.length;j++){
                            //f = _o.getClass().getDeclaredField(_s[j]);
                            f=getDeclaredFields(_o,_s[j]);
                            if(j<_s.length-1){
                                f.setAccessible(true);
                                _o = f.get(_o);
                            }
                        }
                    }catch (NullPointerException e) {
                        map.put(fields[i], "");
                        continue;
                    }
                }else{
                    //获取基本类型数据
                    try{
                        f = getDeclaredFields(_o,fields[i]);
                    }catch (Exception e) {
                        map.put(fields[i], "");
                        continue;
                    }
                }
                if(f!=null){
                    f.setAccessible(true);
                    map.put(fields[i], getFieldsValue(enCode, f.get(_o)));
                }else{
                    map.put(fields[i],"");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return map;
    }
    
    public static Map<String, Object> getMapByObjectWithObjKey(boolean enCode, Object o, String... fields){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            //temp保存最初始的泛型
            Object temp = o;
            for(int i=0;i<fields.length;i++){
                o = temp;
                Object _o = o;
                Field f = null;
                String[] _s = null;
                String s = fields[i].replace(".", ",");
                if(s.indexOf(",")>0){
                    //获取对象映射类型数据
                    try{_s = s.split(",");
                        for(int j=0;j<_s.length;j++){
                            f=getDeclaredFields(_o,_s[j]);
                            if(j<_s.length-1){
                                f.setAccessible(true);
                                _o = f.get(_o);
                            }
                        }
                    }catch (NullPointerException e) {
//                        getNextObjects(map, s, "");
                        continue;
                    }
                }else{
                    //获取基本类型数据
                    try{
                        f = getDeclaredFields(_o,fields[i]);
                    }catch (Exception e) {
//                        getNextObjects(map, s, "");
                        continue;
                    }
                }
                if(f!=null){
                    f.setAccessible(true);
                    if(f.get(_o) != null){
                        getNextObjects(map, s, getFieldsValue(enCode, f.get(_o)));
                    }
                }else{
//                    getNextObjects(map, s, "");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return map;
    }
    
    public static Map<String, Object> getMapByObjectsWithObjKey(boolean enCode, Object[] o, String... fields){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            for(int i=0;i<fields.length;i++){
                String s = fields[i].replace(".", ",");
                if(i+1 > o.length){
                    getNextObjects(map, s, "");
                }else {
                    Object _o = o[i];
                    getNextObjects(map, s, getFieldsValue(enCode, _o));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return map;
    }
    
    public static void getNextObjects(Map<String, Object> map,String s,Object obj){
		String [] strArr = s.split(",");
		if(strArr.length>1){
			if(map.get(strArr[0])==null){
				map.put(strArr[0], new HashMap<String, Object>());
			}
			getNextObjects((Map<String, Object>) map.get(strArr[0]),s.substring(s.indexOf(",")+1,s.length()), obj);
		}else{
            map.put(strArr[0], obj);
		}
    }

    public static Map<String, Object> getMapByObjects(boolean enCode, Object[] o, String... fields){
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            for(int i=0;i<fields.length;i++){
                if(i+1 > o.length){
                    map.put(fields[i], "");
                }else {
                    Object _o = o[i];
                    map.put(fields[i], getFieldsValue(enCode, _o));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return map;
    }
    
    public static List<Map<String, Object>> getList(boolean enCode,List<?> pageList, String[] names, String... fields) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try{
            for(Object o : pageList){
                if(o instanceof Object[]){
                    list.add(getMapByObjects(enCode, (Object[])o, names, fields));
                }else if(o instanceof Object){
                    list.add(getMapByObject(enCode, o,names, fields));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return list;
    }
    
    /**
     * @param enCode
     * @param o
     * @param names 别名
     * @param fields 字段名
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getMapByObject(boolean enCode, Object o, String[] names, String... fields)throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //temp保存最初始的泛型
        Object temp = o;
        for(int i=0;i<fields.length;i++){
            o = temp;
            Object _o = o;
//			Class<? extends Object> c = _o.getClass();
            Field f = null;
            String[] _s = null;
            String s = fields[i].replace(".", ",");
            if(s.indexOf(",")>0){
                //获取对象映射类型数据
                try{_s = s.split(",");
                    for(int j=0;j<_s.length;j++){
                        //f = _o.getClass().getDeclaredField(_s[j]);
                        f=getDeclaredFields(_o,_s[j]);
                        if(j<_s.length-1){
                            f.setAccessible(true);
                            _o = f.get(_o);
                        }
                    }
                }catch (NullPointerException e) {
                    map.put(names[i], null);
                    continue;
                }
            }else{
                //获取基本类型数据
                try{
                    f = getDeclaredFields(_o,fields[i]);
                }catch (Exception e) {
                    map.put(names[i], null);
                    continue;
                }
            }
            if(f!=null){
                f.setAccessible(true);
                map.put(names[i], getFieldsValue(enCode, f.get(_o)));
            }else{
                map.put(names[i], null);
            }
        }
        return map;
    }

    public static Map<String, Object> getMapByObjects(boolean enCode, Object[] o, String[] names, String... fields)throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        for(int i=0;i<fields.length;i++){
            if(i+1 > o.length){
                map.put(names[i], null);
            }else {
                Object _o = o[i];
                map.put(names[i], getFieldsValue(enCode, _o));
            }
        }
        return map;
    }
    
    /**
     * 根据类型返回值
     * @param _o
     * @return
     * @throws Exception
     */
    private static Object getFieldsValue(boolean enCode, Object _o ) throws Exception{
        try {
            if(_o instanceof java.sql.Date)
                return 	DateUtils.getYYYY_MM_DD((Date)_o);
            if(_o instanceof Date){
                return 	DateUtils.getYYYY_MM_DD_HH_MM_SS((Date)_o);
            }
            if(_o instanceof String){
                if(enCode){
                    return 	StringUtils.cleartSpecialCharacterJava((String)_o);
                }else{
                    return _o.toString();
                }
            }
            if(_o instanceof byte[]){
                return StringUtils.byteToString((byte[])_o);
            }
            if(_o instanceof Boolean || _o instanceof Number){
                return _o;
            }
            if(_o != null){
                return _o.toString();
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    public static Field getDeclaredFields(Object obj,String objectName){
        Field field = null;
        Class<?> clazz = obj.getClass();
        for(;clazz != Object.class;clazz = clazz.getSuperclass()){
            try{
                field = clazz.getDeclaredField(objectName);
                if(field != null){
                    return field;
                }
            }catch(Exception e){

            }
        }
        return null;
    }

    public static Field[] getDeclaredFields(Object obj){
        List<Field> fields = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        for(;clazz != Object.class;clazz = clazz.getSuperclass()){

            for(Field f:clazz.getDeclaredFields()){
                fields.add(f);
            }
        }
        return fields.toArray(new Field[]{});
    }

    public static Method[] getDeclaredMethods(Object obj){
        List<Method> fields = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        for(;clazz != Object.class;clazz = clazz.getSuperclass()){
            for(Method m:clazz.getDeclaredMethods()){
                fields.add(m);
            }
        }
        return fields.toArray(new Method[]{});
    }

    /**
     * Method description：取两个集合的交集,将通过T的equals方法判断,应当按自己需求重写equals和hascode
     * <br/>Created by yuanyi 2015-5-20 上午10:41:35
     * @return
     */
    public static <T> Collection<T> intersectCollection(Collection<T> coll1, Collection<T> coll2){
        @SuppressWarnings("unchecked")
        Collection<T> coll = getInstance(coll1.getClass());
        for(T t : coll1){
            if(coll2.contains(t)){
                coll.add(t);
            }
        }
        return coll;
    }

    /**
     * Method description：取两个集合的并集,将通过T的equals方法判断,应当按自己需求重写equals和hascode
     * <br>最终返回的集合中，所有的元素都是唯一的，若传递进来一个list集合，并且有重复数据，则返回的集合中只包含一个
     * <br/>Created by yuanyi 2015-5-20 上午10:41:35
     * @return
     */
    public static <T> Collection<T> unionCollection(Collection<T> coll1, Collection<T> coll2){
        @SuppressWarnings("unchecked")
        Collection<T> coll = getInstance(coll1.getClass());
        for(T t : coll1){
            if(!coll.contains(t)){
                coll.add(t);
            }
        }
        for(T t : coll2){
            if(!coll.contains(t)){
                coll.add(t);
            }
        }
        return coll;
    }

    /**
     * Method description：取两个集合的差集,将通过T的equals方法判断,应当按自己需求重写equals和hascode
     * <br/>Created by yuanyi 2015-5-20 上午10:41:35
     * @return 返回在coll1中且不在coll2中的元素,这些元素都是唯一的
     */
    public static <T> Collection<T> exceptCollection(Collection<T> coll1, Collection<T> coll2){
        @SuppressWarnings("unchecked")
        Collection<T> coll = getInstance(coll1.getClass());
        for(T t : coll1){
            if(!coll.contains(t)){
                coll.add(t);
            }
        }
        for(T t : coll2){
            if(coll.contains(t)){
                coll.remove(t);
            }
        }
        return coll;
    }

    /**
     * Method description：过滤filter为true的对象
     * <br/>Created by yuanyi 2015-6-24 下午5:01:37
     * @param coll
     * @return
     */
    public static <T> Collection<T> filter(Collection<T> coll, Filter<T> filter){
        Iterator<T> it = coll.iterator();
        for(;it.hasNext();){
            if(!filter.filter(it.next())) it.remove();
        }
        return coll;
    }

    /**
     * Method description：将coll中的T对象转换为E对象
     * <br/>Created by yuanyi 2015-7-16 下午3:50:06
     * @param coll
     * @param convert 转换函数
     * @return
     */
    public static <T, E> List<E> convert(Collection<T> coll, Convert<T, E> convert){
        List<E> resultList = new ArrayList<E>(coll.size());
        Iterator<T> it = coll.iterator();
        for(;it.hasNext();){
            resultList.add(convert.convert(it.next()));
        }
        return resultList;
    }

    private static <T> T getInstance(Class<T> clazz){
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

}
