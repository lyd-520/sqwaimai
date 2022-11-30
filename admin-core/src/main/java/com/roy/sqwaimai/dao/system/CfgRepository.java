
package com.roy.sqwaimai.dao.system;

import com.roy.sqwaimai.bean.entity.system.Cfg;
import com.roy.sqwaimai.dao.BaseRepository;

/**
 * 全局参数dao
 */
public interface CfgRepository extends BaseRepository<Cfg,Long> {

    Cfg findByCfgName(String cfgName);
}
