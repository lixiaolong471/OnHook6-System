package com.mars.core.util.excel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixl on 2017/2/28.
 */
public class ExcelDataFormatter {

    private Map<String,Map<String,String>> formatter=new HashMap<String, Map<String,String>>();

    public void set(String key,Map<String,String> map){
        formatter.put(key, map);
    }

    public Map<String,String> get(String key){
        return formatter.get(key);
    }

}
