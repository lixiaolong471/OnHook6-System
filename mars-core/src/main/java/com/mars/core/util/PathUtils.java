package com.mars.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by lixl on 2017/2/21.
 */
public class PathUtils {

    /**
     * 获取项目路径
     * @return
     */
    public static String getCurrentProjectPath(){
        String currentProjectPath = PathUtils.class.getClassLoader()
                .getResource("/").toString();
        try {
            currentProjectPath= URLDecoder.decode(currentProjectPath,"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//
        if(currentProjectPath.indexOf("%20")>0) {
            currentProjectPath.replaceAll("%20", " ");
        }
        if (currentProjectPath.contains("!")) {
            currentProjectPath = "/"
                    + currentProjectPath.substring(10,
                    currentProjectPath.lastIndexOf("/WEB-INF/classes/"));
            currentProjectPath = "/"
                    + currentProjectPath.substring(0,
                    currentProjectPath.lastIndexOf("/"));
        } else {
            currentProjectPath = currentProjectPath.substring(6,
                    currentProjectPath.lastIndexOf("/WEB-INF/classes/"));
        }
        return currentProjectPath+"/";
    }

    /**
     * 取当前项目的绝对路径
     * @return
     */
    public static String getBasePath() {
        String basePath = getCurrentProjectPath();
        String osName = System.getProperty("os.name");
        if (null != osName && "Linux".equalsIgnoreCase(osName.trim())) {
            // 如果是linux系统
            if (!basePath.startsWith("/")) {
                basePath = "/" + basePath;
            }
        }
        return basePath;
    }
}
