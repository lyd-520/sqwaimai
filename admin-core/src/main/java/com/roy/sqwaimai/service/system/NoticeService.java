package com.roy.sqwaimai.service.system;

import com.roy.sqwaimai.bean.entity.system.Notice;
import com.roy.sqwaimai.dao.system.NoticeRepository;
import com.roy.sqwaimai.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * descript
 */
@Service
public class NoticeService extends BaseService<Notice,Long, NoticeRepository> {
    @Autowired
    private NoticeRepository noticeRepository;
    public List<Notice> findByTitleLike(String title) {
        return noticeRepository.findByTitleLike("%"+title+"%");
    }
}
