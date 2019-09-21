package com.mars.core.util;

import java.io.*;
import java.util.Date;

/**
 * Created by lixl on 2017/2/21.
 */
public class FileUtils {

    private static LoggerUtils logger = new LoggerUtils(FileUtils.class);


    /**
     * 根据文件路径字符串提取文件名称
     *
     * @param filePath 文件路径
     * @return fileName 文件名
     */
    public static String getFileName(String filePath) {
        String temp[] = filePath.replaceAll("\\\\", "/").split("/");
        String fileName = null;
        if (temp.length > 0) {
            fileName = temp[temp.length - 1];
        }
        return fileName;
    }

    /**
     * 根据文件路径字符串提取文件(或文件夹)的父级路径
     *
     * @param filePath 文件路径
     * @return fileName 文件名
     */
    public static String getParentPath(String filePath) {
        String[] temp = filePath.replaceAll("\\\\", "/").split("/");
        StringBuilder paths = new StringBuilder();
        if (temp.length > 0) {
            for (int i = 0; i < temp.length - 1; i++) {
                paths.append(temp[i]).append(File.separator);
            }
        }
        return paths.toString();
    }

    /**
     * 创建文件夹
     *
     * @param dir 目录路径
     * @param ignoreIfExist true表示如果文件夹存在就不再创建了,false是重新创建
     * @throws IOException
     */
    public static synchronized void createDirs(String dir, boolean ignoreIfExist) throws IOException {
        File file = new File(dir);
        if (ignoreIfExist && file.exists()) {
            return;
        }
        if (!file.mkdirs()) {
            throw new IOException("Cannot create directories = " + dir);
        }
    }

    public static synchronized void saveSourceFile(InputStream inputStream, String path) throws IOException {
        OutputStream outputStream = null;
        try{
            File file=new File(path);//可以是任何图片格式.jpg,.png等
            if(!file.exists()){
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            outputStream =new FileOutputStream(file);
            byte[] b = new byte[1024];
            int nRead = 0;
            while ((nRead = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, nRead);
            }
            outputStream.flush();
        }catch(Exception e){
            e.printStackTrace();
            throw new IOException(e);
        }finally{
            outputStream.close();
        }

    }


    /**
     * 删除文件
     *
     * @param dirFile
     * @throws Exception
     */
    public static void deleteFile(String dirFile) throws Exception {

        try {
            File filename = new File(dirFile);
            if (filename.exists()) {
                filename.delete();
            }
        }

        catch (Exception e) {
            logger.error("删除文件失败，文件名为：" + dirFile, e);
            throw new Exception("删除失败", e);
        }

    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     *            文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     *
     * @param imgBytes
     * @param filepath
     * @param fileName
     * @throws Exception
     */
    public static void UpLoadFile(byte[] imgBytes, String filepath,
                                  String fileName) throws Exception {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(imgBytes);
            File file = new File(filepath, fileName); // 可以是任何图片格式.jpg,.png等
            if (!file.exists()) {
                File dir = new File(filepath);
                if (!dir.exists() && !dir.isDirectory()) {
                    dir.mkdirs();
                }
                file.createNewFile();
            }

            outputStream = new FileOutputStream(file);

            byte[] b = new byte[1024];
            int nRead = 0;
            while ((nRead = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, nRead);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("上传文件出错", e);
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
            if (null != outputStream) {
                outputStream.close();
            }
        }
    }

    /**
     * 上传文件
     * @param imgBytes
     * @param path
     * @param fileName
     */
    public static void uploadFile(byte[] imgBytes, String path, String fileName) {
        InputStream stream = null;
        FileOutputStream fos = null;
        InputStream stream1 = null;
        FileOutputStream fos1 = null;
        try {

            stream = new ByteArrayInputStream(imgBytes);
            fos = null;
            FileHelper fileHelper = new FileHelper();
            fileHelper.filepath = path;
            fileHelper.UpLoadFile(stream, fos, fileName);
        } catch (Exception e) {
            throw new RuntimeException("上传失败!");
        } finally {
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getSystemFileName(Long userId, String uploadName) {
        String type = uploadName.substring(uploadName.lastIndexOf("."),
                uploadName.length()).toLowerCase();
        String filename = userId + "_" + new Date().getTime() + type;
        return filename;
    }

    /**
     * 读TXT文件内容
     *
     * @param path
     * @return
     */
    public static String readTxtFile(String path) throws Exception {
        String result = "";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            File fileName = new File(PathUtils.getBasePath() + path);
            if (!fileName.exists()) {
                fileName.createNewFile();
            }
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);
            try {
                String read = null;
                while ((read = bufferedReader.readLine()) != null) {
                    result = result + read;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
        System.out.println("读取出来的文件内容是：" + "\r\n" + result);
        return result;
    }

    public static class FileHelper {
        public String  filepath;

        public synchronized void UpLoadFile(InputStream inputStream,OutputStream outputStream,
                                            String remoteFile) throws Exception {
            System.out.println("FileHelper begin ......");
            try{
                File file=new File(filepath,remoteFile); // 可以是任何图片格式.jpg,.png等
                if(!file.exists()){
                    File dir = new File(filepath);
                    if(!dir.exists()  && !dir .isDirectory()){
                        dir.mkdirs();
                    }
                    file.createNewFile();
                }

                outputStream = new FileOutputStream(file);

                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, nRead);
                }
                outputStream.flush();
            } catch(Exception e) {
                e.printStackTrace();
                throw new Exception("上传文件出错", e);
            } finally {
                if(null != inputStream) {
                    inputStream.close();
                }
                if(null != outputStream) {
                    outputStream.close();
                }
            }
            System.out.println("FileHelper end   ......");
        }



    }

}
