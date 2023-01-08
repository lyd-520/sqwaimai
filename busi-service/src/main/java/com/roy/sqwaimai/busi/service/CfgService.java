package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.busi.dao.RedisCacheDao;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CfgService {

    @Resource
    private RedisCacheDao redisCacheDao;
    /**
     * 根据参数名获取参数值
     * 系统获取参数值统一使用该方法
     * @param cfgName
     * @return
     */
    public String getCfgValue(String cfgName) {
        Object res = redisCacheDao.hget(RedisCacheDao.CONFIG_HASH_KEY, cfgName);
        return res.toString();
    }
}
