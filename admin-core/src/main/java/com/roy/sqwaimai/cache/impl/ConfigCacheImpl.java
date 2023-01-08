package com.roy.sqwaimai.cache.impl;

import com.roy.sqwaimai.bean.entity.system.Cfg;
import com.roy.sqwaimai.cache.ConfigCache;
import com.roy.sqwaimai.dao.system.CfgRepository;
import com.roy.sqwaimai.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 全局参数缓存实现类
 */
@Service
public class ConfigCacheImpl implements ConfigCache {
    private static  final Logger logger = LoggerFactory.getLogger(ConfigCacheImpl.class);
    @Autowired
    private CfgRepository cfgRepository;

    @Resource
    private RedisCacheDao redisCacheDao;

    @Override
    public String get(String key) {
        Object result = redisCacheDao.hget(RedisCacheDao.CONFIG_HASH_KEY, key);
        return result.toString();
    }

    @Override
    public String get(String key, String def) {
        String ret = get(key);
        if(StringUtils.isEmpty(ret)){
            return def;
        }
        return ret;
    }

    @Override
    public void set(String key, String val) {
        redisCacheDao.hset(RedisCacheDao.CONFIG_HASH_KEY,key,val);
    }

    @Override
    public void cache() {
        logger.info("reset config cache");
        redisCacheDao.deleteConfig();
        List<Cfg> list = cfgRepository.findAll();
        if (list != null && !list.isEmpty()) {
            for (Cfg cfg : list) {
                if (StringUtils.isNotEmpty(cfg.getCfgName()) && StringUtils.isNotEmpty(cfg.getCfgValue())) {
                    set(cfg.getCfgName(),cfg.getCfgValue());
                }
            }
        }
    }
}
