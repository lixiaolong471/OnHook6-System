package com.mars.web.interceptor;

import com.mars.core.ConfigKey;
import com.mars.core.SysConfig;
import com.mars.core.bean.annotation.interceptor.Interceptor;
import com.mars.core.bean.param.Result;
import com.mars.core.util.JSONUtils;
import com.mars.core.util.LoggerUtils;
import com.mars.core.util.SpringUtils;
import com.mars.model.system.SysUserEntity;
import com.mars.service.system.ISysUserService;
import com.mars.service.SysContent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Interceptor
public class LoginInterceptor implements HandlerInterceptor {

	private final static LoggerUtils logutil = new LoggerUtils(LoginInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		if (arg0.getRequestURI().indexOf(".do") < 0) {
			return true;
		}
		if (arg0.getRequestURI().indexOf("system/login/login.do") >= 0) {
			return true;
		}

		if (arg0.getRequestURI().indexOf("system/login/captcha.do") >= 0) {
			return true;
		}
		if (arg0.getRequestURI().indexOf("system/login/resetpwd.do") >= 0) {
			return true;
		}

		SysUserEntity usersEntity = SysContent.getLoginUser();

		//开发者模式，登录用户默认为管理员。
		if(usersEntity == null && SysConfig.isDev()){
			usersEntity = SpringUtils.getBean(ISysUserService.class).findOne(ISysUserService.ADMIN_USER_ID);
			SysContent.setLoginUser(usersEntity);
		}
		if(usersEntity==null){
			logutil.error("没登录");
			arg1.getWriter().print(JSONUtils.toJson(new Result(-10,"not login!")));
			return false;
		}
		return true;
	}
}
