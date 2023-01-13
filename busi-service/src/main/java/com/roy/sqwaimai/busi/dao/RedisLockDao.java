package com.roy.sqwaimai.busi.dao;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class RedisLockDao {

    private static final String LOCK_PREFIX = "RIDER_LOCK_";
    private static final long LOCK_EXPIRE = 600;
    @Resource
    private RedisTemplate redisTemplate;

    public Boolean lock(Object lock){
        String lockKey = RedisLockDao.LOCK_PREFIX+lock.toString();
        return (Boolean)redisTemplate.execute((RedisCallback) connection -> {
            //获取时间毫秒值
            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
            //获取锁
            Boolean acquire = connection.setNX(lockKey.getBytes(StandardCharsets.UTF_8), String.valueOf(expireAt).getBytes());
            //非原子操作，有可能setNX成功，但是expire时，服务崩溃，过期设置失败。
            redisTemplate.expire(lockKey.getBytes(StandardCharsets.UTF_8), Duration.ofMillis(LOCK_EXPIRE));
            if (acquire) {
                return true;
            } else {
                //有可能是expire执行失败。通过value判断是否过期。
                byte[] bytes = connection.get(lockKey.getBytes(StandardCharsets.UTF_8));
                //非空判断
                if (null != bytes && bytes.length > 0) {
                    long expireTime = Long.parseLong(new String(bytes));
                    // 如果锁已经过期，删除锁，返回true
                    if (expireTime < System.currentTimeMillis()) {
                        redisTemplate.delete(lockKey);
                        return true;
                    }else{ //如果锁没有过期，给锁续命一小段时间，保证锁时间内，获得锁的线程能够完成抢单业务。
                        expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
                        redisTemplate.expire(lockKey.getBytes(StandardCharsets.UTF_8), Duration.ofMillis(LOCK_EXPIRE));
                        return false;
                    }
                }
            }
            return false;
        });
    }

    public void unlock(Object lock){
        String lockKey = RedisLockDao.LOCK_PREFIX+lock.toString();
        redisTemplate.delete(lockKey);
    }
}
