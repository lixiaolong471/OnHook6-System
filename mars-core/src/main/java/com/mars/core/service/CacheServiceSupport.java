package com.mars.core.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by lixl on 2017/9/7.
 */
@Service
public class CacheServiceSupport implements CacheService{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object put(String key, Object value) {
        Object returnValue  = redisTemplate.opsForValue().get(key);
        redisTemplate.opsForValue().set(key, value);
        return returnValue;
    }

    @Override
    public Object put(String key, Object value, TimeUnit unit, long time) {
        Object returnValue  = redisTemplate.opsForValue().get(key);
        redisTemplate.opsForValue().set(key, value, time, unit);
        return returnValue;
    }

    @Override
    public Object remove(String key) {
        redisTemplate.opsForValue().set(key, null);
        return null;
    }

    @Override
    public boolean existKey(String key) {
        if(redisTemplate.opsForValue().get(key)==null){
            return false;
        }
        return true;
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    /** 
     * 入队 
     *  
     * @param key 
     * @param value 
     * @return 返回当前队列的大小
     */  
    @Override
    public Long in(String key, Object value) {  
        return redisTemplate.opsForList().rightPush(key, value);  
    }  
  
    /** 
     * 出队 
     *  
     * @param key 
     * @return 
     */  
    @Override
    public Object out(String key) {  
        return redisTemplate.opsForList().leftPop(key);  
    }  
    
    /** 
     * 范围检索 
     *  
     * @param key 
     * @param start 
     * @param end 
     * @return 
     */  
    @Override
    public List<Object> range(String key, int start, int end) {  
        return redisTemplate.opsForList().range(key, start, end);  
    }


}
