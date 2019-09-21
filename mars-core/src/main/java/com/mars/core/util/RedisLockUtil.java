package com.mars.core.util;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;


public class RedisLockUtil implements Lock, Serializable {
	 
    private static final long serialVersionUID = -2951680710381145553L;
    private static final String RESP_OK = "OK";
    private static final Long RESP_1 = 1l;
    private static final Logger logger = Logger.getLogger(RedisLockUtil.class);
    private static final String PREFIX = "lock_";
	private RedisTemplate<String, String> redisTemplate;
 
    private String key;
    private int tryLock;//加锁失败重试次数
    private int tryUnlock;//解锁失败重试次数
    private int lockSleep;//加锁重试时,线程等待时间
    private int unlockSleep;//解锁重试时,线程等待时间
    private int lockExpire;//加锁过期时间(防止unlock失败)  单位:秒
 
    public RedisLockUtil(String key) {
        this(key, 10, 10, 300, 100, 60 * 5);
    }
 
    /**
     * @param key 锁id
     * @param tryLock 获取锁重试次数
     * @param tryUnlock 释放锁重试次数
     * @param lockSleep 获取锁重试等待时间(毫秒)
     * @param unlockSleep 释放锁重试等待时间(毫秒)
     * @param lockExpire 获取锁时,key过期时间
     * @param unlockExpire 释放锁时,key过期时间
     */
    public RedisLockUtil(String key, int tryLock, int tryUnlock, int lockSleep, int unlockSleep, int lockExpire) {
        this.key = key;
        this.tryLock = tryLock;
        this.tryUnlock = tryUnlock;
        this.lockSleep = lockSleep;
        this.unlockSleep = unlockSleep;
        this.lockExpire = lockExpire;
        redisTemplate= SpringUtils.getBean("redisTemplate",RedisTemplate.class);
    }
 
    @Override
    public void lock() {
        try {
            final String lockKey = getLockKey();
            for (int i = 0; i < tryLock; i++) {
                Boolean exists = redisTemplate.hasKey(lockKey);
                if (exists) {//存在, sleep, 重试
                    try {
                        Thread.sleep(lockSleep);
                    } catch (InterruptedException e) {
                        logger.warn(Thread.currentThread().getName() + " 收到中断信号 : " + this);
                    }
                    continue;
                } else {//不存在, 加锁
                	List<Object> exec = redisTemplate.execute(new SessionCallback<List<Object>>() {
						@Override
						public List<Object> execute(RedisOperations operations) {
							operations.watch(lockKey);
							operations.multi();
							operations.opsForValue().set(lockKey, key);
							operations.expire(lockKey, lockExpire,TimeUnit.SECONDS);
							return operations.exec();
						}
					});
                    
                	if(exec!=null&&((exec.size()>1&&exec.get(1).equals(RESP_1))
                			||(Boolean)exec.get(0)==true)){
                        return;
                    } else {//加锁失败, sleep, 重试
                        try {
                            Thread.sleep(lockSleep);
                        } catch (InterruptedException e) {
                            logger.warn(Thread.currentThread().getName() + " 获取锁 收到中断信号 : " + this);
                        }
                        continue;
                    }
                }
            }
            throw new RuntimeException(Thread.currentThread().getName() + " 获取锁失败, 超过最大重试次数 !");
        } catch (Exception e) {
            throw new RuntimeException(Thread.currentThread().getName() + " 获取锁失败 !",e);
        }
    }
 
    @Override
    public void unlock() {
        try {
            final String lockKey = getLockKey();
            for (int i = 0; i < tryUnlock; i++) {
                Boolean exists = redisTemplate.hasKey(lockKey);
                if (exists) {
                    List<Object> exec = redisTemplate.execute(new SessionCallback<List<Object>>() {
						@Override
						public List<Object> execute(RedisOperations operations){
							operations.watch(lockKey);
							operations.multi();
							operations.delete(lockKey);
							return operations.exec();
						}
					});
                    if(exec!=null&&exec.size()>0&&exec.get(0).equals(RESP_1)){
                        return;
                    } else {//解锁失败, sleep, 重试
                        try {
                            Thread.sleep(unlockSleep);
                        } catch (InterruptedException e) {
                            logger.warn(Thread.currentThread().getName() + " 释放锁 收到中断信号 : " + this);
                        }
                        continue;
                    }
                }
            }
            throw new RuntimeException(Thread.currentThread().getName() + " 释放锁失败, 超过最大重试次数 !");
        } catch (ClassCastException e) {
            throw new RuntimeException(Thread.currentThread().getName() + " 释放锁失败 !");
        } 
    }
 
    private String getLockKey() {
        return PREFIX + key;
    }
 
    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("不支持的方法!");
    }
 
    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException("不支持的方法!");
    }
 
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("不支持的方法!");
    }
 
    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("不支持的方法!");
    }
 
    @Override
    public String toString() {
        return "RedisLock [key=" + key + ", tryLock=" + tryLock + ", tryUnlock=" + tryUnlock + ", lockSleep="
                + lockSleep + ", unlockSleep=" + unlockSleep + ", lockExpire=" + lockExpire + "]";
    }
 
    public String getKey() {
        return key;
    }
 
}
