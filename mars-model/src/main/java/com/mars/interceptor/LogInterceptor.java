package com.mars.interceptor;

import com.mars.core.bean.annotation.interceptor.Interceptor;
import com.mars.core.bean.annotation.logger.OpLog;
import com.mars.core.util.BroswerUtils;
import com.mars.core.util.DateUtils;
import com.mars.model.system.SysOptlogEntity;
import com.mars.service.SysContent;
import com.mars.service.ThreadPoolManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@Interceptor
public class LogInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception arg3)
			throws Exception {

	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView model) throws Exception {
		if(handler != null && handler instanceof HandlerMethod){
			log((HandlerMethod)handler,request,true);
		}
	}

	private void log(HandlerMethod handler, HttpServletRequest request,boolean post) throws ClassNotFoundException {
		Method method = handler.getMethod();
		SysOptlogEntity optlogEntity = null;
		OpLog needOpLog = method.getAnnotation(OpLog.class);
		if (needOpLog == null || StringUtils.isEmpty(needOpLog.value())) {
			return;
		} else if(needOpLog.post() == post){
			optlogEntity = crateOpLog(needOpLog.type().getName(), needOpLog.module(), BroswerUtils.getRemortIP(request),
					needOpLog.value(), BroswerUtils.getVersionBrowserVersion(request.getHeader("User-Agent")));
			if(SysContent.getLoginUser() != null){
				optlogEntity.setCreator(SysContent.getLoginUser());
			}
			ThreadPoolManager.getInstall().addOpLog(optlogEntity);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object handler) throws Exception {
		if(handler != null && handler instanceof HandlerMethod){
			log((HandlerMethod)handler,request,false);
		}
		return true;
	}

	public SysOptlogEntity crateOpLog(String type,String module,String ip,String desc,String browserVersion){
		SysOptlogEntity log = new SysOptlogEntity();
		log.setBrowserVersion(browserVersion);
		log.setIp(ip);
		log.setModule(module);
		log.setOptType(type);
		log.setRemark(desc);
		log.setCreateTime(DateUtils.getDatetime());
		return log;
	}
	
}
