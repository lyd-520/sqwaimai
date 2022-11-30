package com.roy.sqwaimai.cache;


import com.roy.sqwaimai.bean.entity.system.Dict;

import java.util.List;

/**
 * 字典缓存
 */
public interface DictCache  extends Cache{

    List<Dict> getDictsByPname(String dictName);
    String getDict(Long dictId);
}
