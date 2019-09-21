package com.mars.core.bean.annotation.interceptor;

import com.mars.core.bean.annotation.param.RequestParam;
import com.mars.core.bean.annotation.validate.Check;
import com.mars.core.bean.param.Result;
import com.mars.core.util.StringUtils;
import com.mars.core.util.ValidateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Created by lixl on 2017/4/21.
 */
@Aspect
@Component
public class ValidateInterceptor {

    @Around("execution(public * com.mars.*.controller..*.*(..))")
    public Object  around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] argsArray = pjp.getArgs();
        for (Object args : argsArray) {
            if (args != null && args.getClass().getAnnotation(RequestParam.class) != null) {
                Field[] fields = args.getClass().getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    Check check = f.getAnnotation(Check.class);
                    if (check == null) {
                        continue;
                    }
                    Object attr = f.get(args);
                    String params = null;
                    if (attr != null) {
                        params = attr.toString();
                    }
                    if (!check.nullable() && StringUtils.isEmpty(params)) {
                        return Result.getPromptResult(check.columnName() + "为空！");
                    }
                    if (!check.type().check(params)) {
                        return Result.getPromptResult(check.columnName() + "格式错误！");
                    }
                    if (!ValidateUtils.max(params, check.max())) {
                        return Result.getPromptResult(check.columnName() + "超过限定最大长度，最大限定长度为" + check.max()+"！");
                    }
                    if (!ValidateUtils.min(params, check.min())) {
                        return Result.getPromptResult(check.columnName() + "少于最小限定长度，最小限定长度为" + check.min()+"！");
                    }

                }
            }
        }
        return pjp.proceed();
    }
}
