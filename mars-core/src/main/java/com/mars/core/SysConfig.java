package com.mars.core;

import com.mars.core.bean.init.MvcConfig;
import com.mars.core.bean.init.SpringContent;
import com.mars.core.util.LoggerUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by lixl on 2017/6/27.
 */
public class SysConfig {

    /**
     * 默认编码
     */
    private static final String DEFAULT_CHARSET_key = "UTF-8";


    /** 移动网页端首页地址*/
    private static String wapHttppath = null;

    /**pc网页端首页地址*/
    private static String pcHttppath = null;
    
    /** 当前项目首页地址*/
    private static String webHttppath = null;
    
    /** 后台管理端首页地址*/
    private static String sysHttppath = null;
    
    /** web_servername*/
    private static String webSrvername = null;
    
    /**图片资源文件保存路径*/
    private static String materialRealpath = null;
    
    /**图片资源http根路径*/
    private static String materialHttppath = null;
    
    /**聚合接口 OpenId*/
    private static String jhOpenId = null;
    
    /**聚合接口 万年历 当年假期列表*/
    private static String jhWannianliHolidayList = null;
    
    /**聚合接口 手机话费  key*/
    private static String jhShoujihuafeiKey = null;
    
    /**聚合接口 手机话费  检测手机号是否能充值*/
    private static String jhShoujihuafeiPhoneCheck = null;
    
    /**聚合接口 手机话费  手机充值*/
    private static String jhShoujihuafeiPhonePay = null;
    
    /**聚合接口 手机话费  订单状态*/
    private static String jhShoujihuafeiOrderStatus = null;
    
    /**聚合接口  身份证实名认证 */
    private static String jhShimingrenzhengIdcardCheck = null;
    
    /**付款接口PublicKey*/
    private static String paymentApiPublicKey = null;
    
    /**提现*/
    private static String paymentApiDrawCash = null;
    
    /**重付款*/
    private static String paymentApiRepayment = null;
    
    /**付款*/
    private static String paymentApiPayment = null;
    
    /**确认付款*/
    private static String paymentApiConfirmPayment = null;
    
    /**查询付款结果 */
    private static String paymentApiQueryPayment = null;
    
    /**查询银行卡是否可用 */
    private static String cardBinApiCheck=null;
    
    /**生成新单号 */
    private static String paymentApiChangeNoOrder=null;
    
    /**本地退款 */
    private static String paymentApiLocalRefund=null;
    
    /**聚合解密密码用户前8位 不够8位后补0 */
    private static String jhEncryptPassword=null;
    
    /**获取礼品卡key */
    private static String jhGiftCardKey=null;
    
    /**获取礼品卡信息 */
    private static String jhGiftCardProducts=null;

    /**购买礼品卡 */
    private static String jhGiftCardBuy=null;

    /**查询订单详情 */
    private static String jhGiftCardOrderDetail=null;

    /**历史订单查询 */
    private static String jhGiftCardOrders=null;

    /**账户变动记录  */
    private static String jhGiftCardAccount=null;


    private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();

    private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

    private static LoggerUtils logger = new LoggerUtils(SpringContent.class);

    static{
        loadSystemConfig();
    }

    private static Properties loadSystemConfig(){
        Properties properties = new Properties();
        InputStream ins = null;
        try{
            ins = SysConfig.class.getResourceAsStream("/system.properties");
            properties.load(ins);
            wapHttppath = properties.getProperty("wap_httppath");
            pcHttppath = properties.getProperty("pc_httppath");
            webHttppath = properties.getProperty("web_httppath");
            sysHttppath = properties.getProperty("sys_httppath");
            webSrvername = properties.getProperty("web_servername");
            materialRealpath = properties.getProperty("material_realpath");
            materialHttppath = properties.getProperty("material_httppath");
            
            jhOpenId=properties.getProperty("jh_openId");
            
            jhWannianliHolidayList = properties.getProperty("jh_wannianli_holiday_list");
            
            jhShoujihuafeiKey = properties.getProperty("jh_shoujihuafei_key");
            jhShoujihuafeiPhoneCheck = properties.getProperty("jh_shoujihuafei_phone_check");
            jhShoujihuafeiPhonePay = properties.getProperty("jh_shoujihuafei_phone_pay");
            jhShoujihuafeiOrderStatus = properties.getProperty("jh_shoujihuafei_order_status");
            
            jhShimingrenzhengIdcardCheck = properties.getProperty("jh_shimingrenzheng_idcard_check");
            
            jhEncryptPassword=properties.getProperty("jh_encrypt_password");
            jhGiftCardKey=properties.getProperty("jh_gift_card_key");
            jhGiftCardProducts = properties.getProperty("jh_gift_card_products");
            jhGiftCardBuy = properties.getProperty("jh_gift_card_buy");
            jhGiftCardOrderDetail = properties.getProperty("jh_gift_card_order_detail");
            jhGiftCardOrders = properties.getProperty("jh_gift_card_orders");
            jhGiftCardAccount = properties.getProperty("jh_gift_card_account");
            

            paymentApiPublicKey = properties.getProperty("payment_api_public_key");
            paymentApiDrawCash = properties.getProperty("payment_api_draw_cash");
            paymentApiRepayment = properties.getProperty("payment_api_repayment");
            paymentApiPayment = properties.getProperty("payment_api_payment");
            paymentApiConfirmPayment = properties.getProperty("payment_api_confirm_payment");
            paymentApiQueryPayment = properties.getProperty("payment_api_query_payment");
            cardBinApiCheck = properties.getProperty("card_bin_api_check");
            paymentApiChangeNoOrder = properties.getProperty("payment_api_change_no_order");
            paymentApiLocalRefund = properties.getProperty("payment_api_local_refund");
            
        }catch(Exception e){
            e.printStackTrace();
            logger.error("加载system系统配置文件出错"+e.getMessage());
        }finally{
            try {
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * 获取Wap端首页地址
     * @return
     */
    public static String getWapHttppath(){
        return wapHttppath;
    }

    /**
     * 获取PC端首页地址
     * @return
     */
    public static String getPcHttppath(){
        return pcHttppath;
    }

    /**
     * 当前项目首页地址
     * @return
     */
    public static String getWebHttppath(){
        return webHttppath;
    }

    /**
     * 后台管理端首页地址
     * @return
     */
    public static String getSysHttppath(){
        return sysHttppath;
    }


    /**
     * 获取web_servername
     * @return
     */
    public static String getWebSrvername(){
        return webSrvername;
    }

    /**
     * 图片资源文件保存路径
     * @return
     */
    public static String getMaterialRealpath(){
        return materialRealpath;
    }

    /**
     * 图片资源http根路径
     * @return
     */
    public static String getMaterialHttppath(){
        return materialHttppath;
    }
	
	/**
     * 聚合OpenId
     * @return
     */
	public static String getJhOpenId() {
		return jhOpenId;
	}

    /**
     * 万年历 当年假期列表
     * @return
     */
    public static String getJhWannianliHolidayList() {
		return jhWannianliHolidayList;
	}

	/**
    * 手机话费 key
    * @return
    */
	public static String getJhShoujihuafeiKey() {
		return jhShoujihuafeiKey;
	}
	
    /**
     * 手机话费  检测手机号是否能充值
     * @return
     */
	public static String getJhShoujihuafeiPhoneCheck() {
		return jhShoujihuafeiPhoneCheck;
	}

	 /**
     * 手机话费  手机充值
     * @return
     */
	public static String getJhShoujihuafeiPhonePay() {
		return jhShoujihuafeiPhonePay;
	}

	 /**
     * 手机话费  订单状态
     * @return
     */
	public static String getJhShoujihuafeiOrderStatus() {
		return jhShoujihuafeiOrderStatus;
	}

	 /**
     * 身份证实名认证
     * @return
     */
	public static String getJhShimingrenzhengIdcardCheck() {
		return jhShimingrenzhengIdcardCheck;
	}
	
	/**聚合解密密码用户前8位 不够8位后补0 */
	 public static String getJhEncryptPassword() {
		return jhEncryptPassword;
	}
	 
	 /**获取礼品卡key */
	 public static String getJhGiftCardKey() {
		return jhGiftCardKey;
	}

	/**获取礼品卡信息 */
	public static String getJhGiftCardProducts() {
		return jhGiftCardProducts;
	}

	/**购买礼品卡 */
	public static String getJhGiftCardBuy() {
		return jhGiftCardBuy;
	}

	/**查询订单详情 */
	public static String getJhGiftCardOrderDetail() {
		return jhGiftCardOrderDetail;
	}

	/**历史订单查询 */
	public static String getJhGiftCardOrders() {
		return jhGiftCardOrders;
	}

	/**账户变动记录  */
	public static String getJhGiftCardAccount() {
		return jhGiftCardAccount;
	}

	/**付款接口publicKey*/
    public static String getPaymentApiPublicKey() {
		return paymentApiPublicKey;
	}

	/**提现*/
    public static String getPaymentApiDrawCash() {
		return paymentApiDrawCash;
	}
    
    /**重付款*/
    public static String getPaymentApiRepayment() {
		return paymentApiRepayment;
	}

	/**付款*/
	public static String getPaymentApiPayment() {
		return paymentApiPayment;
	}

    /**确认付款*/
	public static String getPaymentApiConfirmPayment() {
		return paymentApiConfirmPayment;
	}

    /**查询付款结果 */
	public static String getPaymentApiQueryPayment() {
		return paymentApiQueryPayment;
	}
	
	/**查询银行卡是否可用 */
	public static String getCardBinApiCheck() {
		return cardBinApiCheck;
	}
	
	/**生成新单号 */
	public static String getPaymentApiChangeNoOrder() {
		return paymentApiChangeNoOrder;
	}
	
	/**本地退款 */
	public static String getPaymentApiLocalRefund() {
		return paymentApiLocalRefund;
	}

	

	/**
     * 获取请求
     * @return
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) requestLocal.get();
    }

    /**
     * 设置请求
     * @param request
     */
    public static void setRequest(HttpServletRequest request) {
        requestLocal.set(request);
    }

    /**
     * 获取当前登录用户的Response
     * @return
     */
    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) responseLocal.get();
    }

    /**
     * 设置当前登录用户的Response
     * @param response
     */
    public static void setResponse(HttpServletResponse response) {
        responseLocal.set(response);
    }

    /**
     * 获取当前登录用户Session
     * @return
     */
    public static HttpSession getSession() {
    	HttpServletRequest request=requestLocal.get();
    	if(request==null){
    		return null;
    	}
        return (HttpSession) request.getSession();
    }
    
	/**
     * 獲取當前系統的編碼
     * @return
     */
    public static String getSystemCharacterCode(){
        return SpringContent.getSystemCharacterCode();
    }

    /**
     * 当前系统是否为开发模式，在开发模式下，不做用户登录验证，默认为已登录状态，当前登录身份为管理员
     * @return
     */
    public static boolean isDev(){
        return SpringContent.isDev();
    }




}
