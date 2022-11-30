
package com.roy.sqwaimai.dao.cms;

import com.roy.sqwaimai.bean.entity.cms.Banner;
import com.roy.sqwaimai.dao.BaseRepository;

import java.util.List;

public interface BannerRepository extends BaseRepository<Banner,Long> {
    /**
     * 查询指定类别的banner列表
     * @param type
     * @return
     */
    List<Banner> findAllByType(String type);
}
