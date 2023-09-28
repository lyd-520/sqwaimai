package com.roy.sqwaimai.busi.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
public class RedisLockDao {

    private static final String LOCK_PREFIX = "RIDER_LOCK_";
    private static final long LOCK_EXPIRE = 600;
    @Resource
    private RedisTemplate redisTemplate;

    //返回true 表示拿到了锁， 返回false表示没有拿到锁
    public Boolean lock(Object lock){
        String lockKey = RedisLockDao.LOCK_PREFIX+lock.toString();
        long expireAt = System.currentTimeMillis()+LOCK_EXPIRE+1;
        Boolean acquire = (Boolean) redisTemplate.execute((RedisCallback) connection ->
                connection.setNX(lockKey.getBytes(StandardCharsets.UTF_8), String.valueOf(expireAt).getBytes(StandardCharsets.UTF_8)));
        if(acquire){
            redisTemplate.expire(lockKey.getBytes(StandardCharsets.UTF_8),Duration.ofMillis(LOCK_EXPIRE));
        }else{
            Object expireHis = redisTemplate.opsForValue().get(lockKey.getBytes(StandardCharsets.UTF_8));
            if(null != expireHis){
                long expireTime = Long.parseLong(expireHis.toString());
                //如果锁应该已经过期了，但是没有被Redis自己删掉。
                if(expireTime < System.currentTimeMillis()){
                    redisTemplate.delete(lockKey.getBytes(StandardCharsets.UTF_8));
                    //当前线程可以重新去拿锁。项目当中简化。
                }else{
//                    redisTemplate.expire(lockKey.getBytes(StandardCharsets.UTF_8),Duration.ofMillis(LOCK_EXPIRE));
                    long newExpireAt = expireTime+LOCK_EXPIRE+1;
                    redisTemplate.expireAt(lockKey.getBytes(StandardCharsets.UTF_8),new Date(newExpireAt));
                }
            }
        }
        return acquire;
    }

    public void unlock(Object lock){
        String lockKey = RedisLockDao.LOCK_PREFIX+lock.toString();
        redisTemplate.delete(lockKey.getBytes(StandardCharsets.UTF_8));
    }
}
