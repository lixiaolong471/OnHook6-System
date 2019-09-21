package com.mars.core.util;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;

/**
 * Created by lixl on 2017/2/17.
 */
public class ZipUtils {

    private static final LoggerUtils logger = new LoggerUtils(ZipUtils.class);
    /**
     * 用来将文件压缩到指定的zip包
     * dest:目标文件名(绝对路径)
     * src:源文件名(绝对路径)
     */
    public void zip(String dest, String src, String includFile, String exceptFile) throws Exception {
        Zip zip = new Zip();
        zip.setBasedir(new File(src));
        //zip.setIncludes(...); 包括哪些文件或文件夹eg:zip.setIncludes("*.java");//zip.setExcludes(...); 排除哪些文件或文件夹
        if (null != includFile && !"".equals(includFile)) {
            //包含需要制定到要包含的文件
            zip.setIncludes(includFile );
        }
        if (null != exceptFile && !"".equals(exceptFile)) {
            zip.setExcludes(exceptFile);
        }
        zip.setDestFile(new File(dest));
        Project p = new Project();
        p.setBaseDir(new File(src));
        zip.setProject(p);
        zip.execute();
    }

    public static void main(String[] args) throws Exception {
        ZipUtils zipUtil = new ZipUtils();
        // 将C盘test目录下, com目录下的所有目录和文件, 打包放置到c:/test/abc.zip
        zipUtil.zip("c:/test/abc.zip", "c:/test", "com/**", null);
    }

    public static boolean unzip(String zipFile, String unzipPath) {
        InputStream is = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ZipFile zf = null;
        try {
            zf = new ZipFile(zipFile, "utf-8");
            @SuppressWarnings("unchecked")
            Enumeration<ZipEntry> entries = zf.getEntries();
            boolean isSuccess = true;

            while (entries.hasMoreElements()) {
                try{
                    ZipEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    String path = unzipPath + entryName;
                    if (entry.isDirectory()) {
                        logger.info("正在创建解压目录 - " + entryName);
                        File decompressDirFile = new File(path);
                        if (!decompressDirFile.exists()) {
                            decompressDirFile.mkdirs();
                        }
                    } else {
                        logger.info("正在创建解压文件 - " + entryName);
                        File destDirFile = new File(path).getParentFile();
                        if (!destDirFile.exists()) {
                            destDirFile.mkdirs();
                        }

                        long size = entry.getSize();
                        fos = new FileOutputStream(path);
                        bos = new BufferedOutputStream(fos);

                        is = zf.getInputStream(entry);

                        if(size > 0 && null == is){
                            isSuccess = false;
                            logger.error("zip文件内包含中文目录或者中文文件名");
                        }

                        if(null != is){
                            bis = new BufferedInputStream(is);
                            byte[] buf = new byte[1024];
                            int len = 0;
                            while ((len = bis.read(buf)) != -1) {
                                bos.write(buf, 0, len);
                            }
                            bos.flush();
                            fos.flush();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    if(null != bos){
                        bos.close();
                    }
                    if(null != fos){
                        fos.close();
                    }
                    if(null != bis){
                        bis.close();
                    }
                    if(null != is){
                        is.close();
                    }
                }
            }
            return isSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally{
            try {
                if(null != zf){
                    zf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
