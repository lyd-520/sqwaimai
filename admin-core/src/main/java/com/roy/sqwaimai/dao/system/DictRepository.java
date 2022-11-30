
package com.roy.sqwaimai.dao.system;

import com.roy.sqwaimai.bean.entity.system.Dict;
import com.roy.sqwaimai.dao.BaseRepository;

import java.util.List;

public interface DictRepository  extends BaseRepository<Dict,Long> {
    List<Dict> findByPid(Long pid);
    List<Dict> findByNameAndPid(String name,Long pid);

    List<Dict> findByNameLike(String name);
}
