package com.mars.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by lixl on 2017/2/17.
 */
public class EncryptionUtils {

    private final static LoggerUtils logutil = new LoggerUtils(
            EncryptionUtils.class);

    /**
     * 返回plainText的MD5值.
     *
     * @param plainText
     *            一个需要MD5的字符串.
     * @return 返回plainText的MD5值.
     */
    public static String md5s(String plainText) {
        String result = null;

        if (plainText == null) {
            return null;
        }

        byte[] byteArray = null;
        byte b[] = null;
        int i;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byteArray = plainText.getBytes("GBK");
            md.update(byteArray);
            b = md.digest();

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (Exception e) {
            logutil.error("MD5.MD5s error!", e);
        }

        return result;
    }

    /**
     * 计算文件的MD5.
     *
     * @param file
     *            需要计算 MD5的File对象.
     * @return 成功则 返回 MD5字符串, 失败返回 null.
     */
    public static String getMD5(File file) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            return bytesToString(md.digest());
        } catch (IOException ex) {
            logutil.error("", ex);
            return null;
        } catch (NoSuchAlgorithmException ex) {
            logutil.error("", ex);
            return null;
        } finally {
            try {
                if(fis!=null) {
                    fis.close();
                }
            } catch (IOException ex) {
                logutil.error("", ex);
            }
        }
    }

    /**
     * 将byte[]转换成16进制字符串.
     *
     * @param data
     *            待转换byte[].
     * @return byte[]转换成的16进制字符串.
     */
    public static String bytesToString(byte[] data) {
        if (data == null) {
            return "";
        }

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        char[] temp = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            temp[i * 2] = hexDigits[b >>> 4 & 0x0f];
            temp[i * 2 + 1] = hexDigits[b & 0x0f];
        }

        return new String(temp);
    }
    
    /**
	 * 根据指定的加密算法，返回加密后的16进制字符串。
	 * 
	 * @param content
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encrypt(String content, String algorithm) throws NoSuchAlgorithmException {
		byte[] bytes = content.getBytes();
		MessageDigest msgDigest = MessageDigest.getInstance(algorithm);
		msgDigest.update(bytes);
		byte[] digestBytes = msgDigest.digest();
		String hexDigest = new String(Hex.encodeHex(digestBytes));

		return hexDigest;
	}
	
	/**
     * 解密
     * @param srcMsg 密文
     * @param password    密码
     * @return         解密后的明文
     */
   public static String decryptByDES(String srcMsg,String password){
       byte[] bb = Base64.decodeBase64(srcMsg.getBytes());
       try {
            // DES算法要求有一个可信任的随机数源
           SecureRandom random = new SecureRandom();
           // 创建一个DESKeySpec对象
           DESKeySpec desKey = new DESKeySpec(password.getBytes());
           // 创建一个密匙工厂
           SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
           // 将DESKeySpec对象转换成SecretKey对象
           SecretKey securekey = keyFactory.generateSecret(desKey);
           // Cipher对象实际完成解密操作
           Cipher cipher = Cipher.getInstance("DES");
           // 用密匙初始化Cipher对象
           cipher.init(Cipher.DECRYPT_MODE, securekey, random);
           // 真正开始解密操作
           byte[] decryResult = cipher.doFinal(bb);
           return new String (decryResult);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }
   /**
    * 加密
    * @param srcMsg  待加密字符串
    * @param password        密码
    * @return   加密后的密文
    */
   public static String encryptByDES(String srcMsg, String password){
       try {
           SecureRandom random = new SecureRandom();
           DESKeySpec desKey = new DESKeySpec(password.getBytes());
           // 创建一个密匙工厂，然后用它把DESKeySpec转换成
           SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
           SecretKey securekey = keyFactory.generateSecret(desKey);
           // Cipher对象实际完成加密操作
           Cipher cipher = Cipher.getInstance("DES");
           // 用密匙初始化Cipher对象
           cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
           // 现在，获取数据并加密
           // 正式执行加密操作
           byte[] result =cipher.doFinal(srcMsg.getBytes());
         //Base64编码
           byte[] resultBase = Base64.encodeBase64(result, false);
           return new String(resultBase);
       } catch (Throwable e) {
           e.printStackTrace();
       }
       return null;
   }
	
}
