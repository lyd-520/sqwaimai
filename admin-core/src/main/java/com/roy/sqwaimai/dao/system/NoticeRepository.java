package com.roy.sqwaimai.dao.system;


import com.roy.sqwaimai.bean.entity.system.Notice;
import com.roy.sqwaimai.dao.BaseRepository;

import java.util.List;

public interface NoticeRepository extends BaseRepository<Notice,Long> {
    List<Notice> findByTitleLike(String name);
}
