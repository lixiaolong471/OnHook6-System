package com.mars.aop;

import java.lang.annotation.Annotation;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.mars.core.bean.annotation.aop.RedisLock;
import com.mars.core.util.RedisLockUtil;

/**
 * 注解锁处理
 * @author tantx
 *
 */
@Component
@Aspect
@Order(1)
public class RedisLockAop {
	private static final Logger LOG = Logger.getLogger(RedisLockAop.class);
	
	@Pointcut(value="execution(* com.mars..*.*(..)) && @annotation(com.mars.core.bean.annotation.aop.RedisLock)")
    public void point(){
		
    }
    
//    @Before(value="point()")
//    public void before(){
//        System.out.println("RedisLockAop begin");
//    }
//    
//    @AfterReturning(value = "point()")
//    public void after(){
//        System.out.println("RedisLockAop after");
//    }
    
    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        try{
        	MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        	Object[] paramValue=joinPoint.getArgs();
        	RedisLock redisLock=(RedisLock) methodSignature.getMethod().getAnnotation(RedisLock.class);
        	String lockKey=redisLock.key();
        	Annotation[][] parameterAnnotations= methodSignature.getMethod().getParameterAnnotations();
        	for (int i=0;i<parameterAnnotations.length;i++) {
        		Annotation[] annotations = parameterAnnotations[i];
        		if(annotations!=null&&annotations.length>0){
        			for (Annotation annotation : annotations) {
        				if(annotation instanceof Param){
                			Param param=(Param) annotation;
                			lockKey=lockKey.replaceAll(":"+param.value(), String.valueOf(paramValue[i]));
                			System.out.println(param.value());
        				}
					}
        		}
			}
        	LOG.info("redis lock key is "+lockKey);
    		RedisLockUtil lock=new RedisLockUtil(lockKey);
		    lock.lock();
		    try{
			   return joinPoint.proceed();
		    }catch (Exception e) {
				LOG.error("锁中处理方法异常",e);
		    	throw e;
		    }finally{
			   lock.unlock();
		    }
	    }catch (Exception e) {
		   LOG.error("获取锁失败");
		   throw e;
	    }
    
    }

}
