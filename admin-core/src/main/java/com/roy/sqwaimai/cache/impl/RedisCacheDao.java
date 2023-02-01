package com.roy.sqwaimai.cache.impl;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component
public class RedisCacheDao {
    public static  final String CONFIG_HASH_KEY = "sqwaimaiconfig";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void hset(String hashKey, Serializable key, Object val) {
        stringRedisTemplate.opsForHash().put(hashKey,key,val);
    }

    public Object hget(String hashKey, Serializable k) {
        return stringRedisTemplate.opsForHash().get(hashKey, k);
    }

    public Boolean deleteConfig(){
        return stringRedisTemplate.delete(RedisCacheDao.CONFIG_HASH_KEY);
    }

}
