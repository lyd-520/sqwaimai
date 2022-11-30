package com.roy.sqwaimai.dao.system;


import com.roy.sqwaimai.bean.entity.system.Notice;
import com.roy.sqwaimai.dao.BaseRepository;

import java.util.List;

/**
 * Created  on 2018/3/21 0021.
 *
 *@Author enilu
 */
public interface NoticeRepository extends BaseRepository<Notice,Long> {
    List<Notice> findByTitleLike(String name);
}
