package com.roy.sqwaimai.app.controller.system;

import com.google.common.base.Strings;
import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.entity.system.Notice;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.service.system.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {
    @Autowired
    private NoticeService noticeService;
    /**
     * 获取通知列表
     */
    @RequestMapping(value = "/list")
    public Object list(String condition) {
        List<Notice> list = null;
        if(Strings.isNullOrEmpty(condition)) {
            list =  noticeService.queryAll();
        }else{
            list = noticeService.findByTitleLike(condition);
        }
        return Rets.success(list);
    }
}
