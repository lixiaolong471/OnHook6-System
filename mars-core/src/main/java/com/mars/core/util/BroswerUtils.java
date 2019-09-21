package com.mars.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.mars.core.bean.param.Result;

/**
 * Created by lixl on 2017/2/17.
 */
public class BroswerUtils {
	private static final LoggerUtils LOG = new LoggerUtils(BroswerUtils.class);

    /**
     * 获取客户端IP,如果客户端通过代理访问，返回实际的IP
     *
     * @param request
     * @return
     */

    public static String getRemortIP(HttpServletRequest request) {
        String remoteIP = null;
        if (request.getHeader("x-forwarded-for") == null) {
            remoteIP = request.getRemoteAddr();
            if (remoteIP.indexOf(":") > 0) {
                remoteIP = "127.0.0.1";
            }
            return remoteIP;

        }
        return request.getHeader("x-forwarded-for");
    }

    /**
     * 根据不同浏览器的匹配规则，判断客户端的浏览器类型
     *
     * @param userAgent
     * @param parttenRule
     * @return
     */
    public static String matchBrowserVersion(String userAgent, String parttenRule) {
        String verersion = null;//
        Pattern patern = Pattern.compile(parttenRule);
        userAgent = userAgent.replace(" ", "");
        Matcher materche = patern.matcher(userAgent);
        while (materche.find()) {
            verersion = materche.group();
            break;
        }
        return verersion;
    }

    /**
     * 各种浏览器匹配规则，其中userAget当中包含Version字段的是苹果浏览器
     * 三大浏览器逐一匹配，如果找到相匹配的则跳出循环，不再继续匹配其他浏览器
     *
     * @param userAgent
     * @return
     */
    public static String getVersionBrowserVersion(String userAgent) {
        String verersion = null;
        // 各种浏览器匹配规则，其中userAget当中包含Version字段的是苹果浏览器
        String[] rules = new String[] { "Firefox\\/[\\d\\.]+",
                "Chrome\\/[\\d\\.]+", "Version\\/[\\d\\.]+", "IE[\\d]+" };
        for (int i = 0; i < rules.length; i++) {
            // 三大浏览器逐一匹配，如果找到相匹配的则跳出循环，不再继续匹配其他浏览器
            verersion = matchBrowserVersion(userAgent, rules[i]);
            if (verersion != null) {
                if (i == 2) {
                    verersion = verersion.replace("Version", "Safari");
                }
                break;

            }
        }
        return verersion;
    }
    
    /**
	 * 获取当前网络ip
	 *   
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request){
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try{
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e){
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15){ // "***.***.***.***".length()
									// = 15
			if (ipAddress.indexOf(",") > 0){
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	/**
	 * 判断 是否是微信浏览器
	 * @param request
	 * @return
	 */
	public static boolean isWechatClient(HttpServletRequest request){
		String userAgent = request.getHeader("user-agent").toLowerCase();
		if(userAgent.indexOf("micromessenger")>-1){//微信客户端
		    return true;
		}
		return false;
	}
	
	public static void responseResult(HttpServletResponse response,Result result){
		PrintWriter writer = null;
        try{
        	response.setHeader("Content-type", "text/html;charset=UTF-8");
            writer = response.getWriter();
            writer.write(JSON.toJSONString(result));
        }catch (Exception e) {
        	LOG.error("操作失败！",e);
        }finally{
            writer.flush();
            writer.close();
        }
	}
	
	/**
     * 读取request流
     * @param req
     * @return
     */
    public static String readReqStr(HttpServletRequest request)
    {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String line = null;

            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
        } catch (IOException e){
        	LOG.error("从请求的流中获取字符串参数失败", e);
        } finally{
            try
            {
                if (null != reader)
                {
                    reader.close();
                }
            } catch (IOException e)
            {

            }
        }
        return sb.toString();
    }

}
