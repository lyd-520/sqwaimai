package com.roy.sqwaimai.service.system;

import com.roy.sqwaimai.bean.entity.system.Cfg;
import com.roy.sqwaimai.cache.ConfigCache;
import com.roy.sqwaimai.core.query.SearchFilter;
import com.roy.sqwaimai.dao.system.CfgRepository;
import com.roy.sqwaimai.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CfgService extends BaseService<Cfg, Long, CfgRepository> {
    @Autowired
    private ConfigCache configCache;

    public Cfg saveOrUpdate(Cfg cfg) {
        if(cfg.getId()==null) {
            insert(cfg);
        }else{
            update(cfg);
        }
        configCache.cache();
        return cfg;
    }

    public Cfg getByCfgName(String cfgName) {

        return get(SearchFilter.build("cfgName", SearchFilter.Operator.EQ,cfgName));
    }

    /**
     * 根据参数名获取参数值
     * 系统获取参数值统一使用该方法
     * @param cfgName
     * @return
     */
    public String getCfgValue(String cfgName) {
//        return configCache.get(cfgName);
        return getByCfgName(cfgName).getCfgValue();
    }
    @Override
    public void delete(Long id) {
        super.delete(id);
        configCache.cache();
    }

}
