package com.roy.sqwaimai.busi.dao;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component
public class RedisCacheDao {
    public static  final String CONFIG_HASH_KEY = "sqwaimaiconfig";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public Object hget(String hashKey, Serializable k) {
        return stringRedisTemplate.opsForHash().get(hashKey, k);
    }
}
