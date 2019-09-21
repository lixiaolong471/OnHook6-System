package com.mars.core.bean.init;

import org.apache.log4j.Logger;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/** 
*Description: <应用配置类>. <br>
*<p>
	<负责注册除Controller等web层以外的所有bean，包括aop代理，service层，dao层，缓存，等等>
 </p>
* @version V1.0                             
*/  
@Configuration
@ComponentScan(basePackages = "com.mars"/*,
        excludeFilters = {@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Controller.class)} */)
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass=true)
@Order(3)
public class AppConfig {

    private static final Logger logger = Logger.getLogger(AppConfig.class);

    @Bean
    public StringRedisSerializer stringRedisSerializer(){
        return new StringRedisSerializer();
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        // Defaults
        redisConnectionFactory.setHostName(SpringContent.getRedisWebUrl());
        redisConnectionFactory.setPort(SpringContent.getRedisWebPort());
        redisConnectionFactory.setPassword(SpringContent.getRedisWebPassword());
        return redisConnectionFactory;
    }


    @Bean(name="redisTemplate")
    public RedisTemplate redisTemplate() {
        RedisTemplate<String, Object> redis = new RedisTemplate<>();
        redis.setConnectionFactory(redisConnectionFactory());
        redis.setStringSerializer(this.stringRedisSerializer());
        return redis;
    }

    @Bean(name="cacheManager")
    public CacheManager cacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
        return cacheManager;
    }

}
