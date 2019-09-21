package com.mars.interceptor;

import com.mars.core.bean.annotation.authority.RoleSecurity;
import com.mars.core.bean.param.Result;
import com.mars.core.util.LoggerUtils;
import com.mars.service.system.ISysRoleService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleInterceptor {

	private static final LoggerUtils LOG = new LoggerUtils(RoleInterceptor.class);

	@Autowired
	private ISysRoleService roleService;
	
	
	@Around("execution(public * com.mars.*.controller..*.*(..))")
	public Object  around(ProceedingJoinPoint pjp) throws Throwable {
		Signature signature = pjp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;

		RoleSecurity roleSecurity = methodSignature.getMethod().getAnnotation(RoleSecurity.class);
		boolean bool = true;
		if(roleSecurity != null){
			String[] roleMark = roleSecurity.roleMark();
			bool = roleService.isRoleAction(roleMark);
		}

		if(bool){
			return 	pjp.proceed();
		}
		String errorMsg = "Access denied";
		LOG.info(errorMsg);
		return Result.getErrorResult(errorMsg);
	}
}
