package com.roy.sqwaimai.cache.impl;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component
public class EhcacheDao {
    //缓存常量，永不过期
    public static  final String CONSTANT = "CONSTANT";
    public static  final String SESSION = "SESSION";
    @Resource
    private CacheManager cacheManager;

    public void hset(Serializable key, Serializable k, Object val) {
        Cache cache = cacheManager.getCache(String.valueOf(key));
        cache.put(k,val);
    }

    public Serializable hget(Serializable key, Serializable k) {
        Cache cache = cacheManager.getCache(String.valueOf(key));
        return cache.get(k,String.class);
    }

    public <T>T hget(Serializable key, Serializable k,Class<T> klass) {
        Cache cache = cacheManager.getCache(String.valueOf(key));
        return cache.get(k,klass);
    }

    public void hdel(Serializable key, Serializable k) {
        cacheManager.getCache(String.valueOf(key)).put(String.valueOf(k),null);
    }
}
