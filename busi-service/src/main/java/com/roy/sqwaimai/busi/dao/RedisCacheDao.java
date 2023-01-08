package com.roy.sqwaimai.busi.dao;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component
public class RedisCacheDao {
    public static  final String CONFIG_HASH_KEY = "config";
    @Resource
    private RedisTemplate redisTemplate;

    public void hset(Serializable key, Serializable k, Object val) {
        redisTemplate.opsForHash().put(key,k,val);
    }

    public Object hget(Serializable key, Serializable k) {
        return redisTemplate.opsForHash().get(key, k);
    }
}
