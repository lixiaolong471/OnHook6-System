package com.mars.core.bean.annotation.interceptor;

import com.mars.core.bean.annotation.cache.CacheClean;
import com.mars.core.bean.annotation.cache.CacheKey;
import com.mars.core.bean.annotation.cache.CacheQuery;
import com.mars.core.bean.annotation.param.RequestParam;
import com.mars.core.bean.annotation.validate.Check;
import com.mars.core.bean.param.Result;
import com.mars.core.service.CacheService;
import com.mars.core.util.StringUtils;
import com.mars.core.util.ValidateUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixl on 2017/4/21.
 */
@Aspect
@Component
public class SysCacheInterceptor {
    @Autowired
    CacheService cacheService;

    @Around("execution(public * com.mars.service..*.*(..))")
    public Object  around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Annotation[][] parameterAnn = methodSignature.getMethod().getParameterAnnotations();
        String[] names = methodSignature.getParameterNames();
        Object[] argsArray = pjp.getArgs();
        CacheQuery cache = (CacheQuery) methodSignature.getMethod().getAnnotation(CacheQuery.class);
        CacheClean clean = (CacheClean) methodSignature.getMethod().getAnnotation(CacheClean.class);
        if(cache != null){
            String cacheKey = cacheKey(cache,parameterAnn,names,argsArray);
            Object data = null;
            if(cacheService.existKey(cacheKey)){
                data = getCache(cacheKey);
            }else{
               data = pjp.proceed();
               saveCache(cacheKey,data,cache.time());
            }
            return data;
        }
        if(clean != null){
            String cacheKey = cacheKey(clean,parameterAnn,names,argsArray);
            cacheService.remove(cacheKey);
        }
        return pjp.proceed();
    }

    public String cacheKey(CacheQuery cache,Annotation[][] parameterAnn,String[] names,Object[] args){
        String cacheKey = cache.value();
        return cacheKey + parseExpression(parameterAnn,names,args);
    }

    public String cacheKey(CacheClean clean, Annotation[][] parameterAnn,String[] names,Object[] args){
        String cacheKey = clean.value();
        return cacheKey + parseExpression(parameterAnn,names,args);
    }

    private String parseExpression(Annotation[][] parameterAnn,String[] names,Object[] args){
        String cacheKey = "";
        for (int i = 0;i<parameterAnn.length;i++) {
            Annotation[] anns = parameterAnn[i];
            for(Annotation ann : anns){
                if(ann instanceof CacheKey){
                    CacheKey cacheAnn = (CacheKey) ann;
                    ExpressionParser parser=new SpelExpressionParser();
                    EvaluationContext ctx = new StandardEvaluationContext();
                    ctx.setVariable(names[i],args[i]);
                    String key = (String) parser.parseExpression(cacheAnn.value()).getValue(ctx);
                    cacheKey += ("_"+key);
                    break;
                }
            }
        }
        return cacheKey;
    }

    /**
     * 緩存存儲
     * @param key
     * @param data
     */
    public void saveCache(String key,Object data,long time){
        if(time > 0){
            cacheService.put(key,data,TimeUnit.MINUTES,time);
        }else{
            cacheService.put(key,data);
        }
    }

    public Object getCache(String key){
       return cacheService.get(key);
    }
}
