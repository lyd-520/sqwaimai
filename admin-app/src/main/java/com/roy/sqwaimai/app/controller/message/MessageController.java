package com.roy.sqwaimai.app.controller.message;

import com.roy.sqwaimai.bean.core.BussinessLog;
import com.roy.sqwaimai.bean.entity.message.Message;
import com.roy.sqwaimai.bean.enumeration.Permission;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.query.Page;
import com.roy.sqwaimai.core.query.SearchFilter;
import com.roy.sqwaimai.service.message.MessageService;
import com.roy.sqwaimai.core.util.DateUtil;
import com.roy.sqwaimai.utils.PageFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.MSG})
    public Object list(  @RequestParam(required = false) String startDate,
                         @RequestParam(required = false) String endDate) {
        Page<Message> page = new PageFactory<Message>().defaultPage();
        page.addFilter("createTime", SearchFilter.Operator.GTE, DateUtil.parse(startDate,"yyyyMMddHHmmss"));
        page.addFilter("createTime", SearchFilter.Operator.LTE, DateUtil.parse(endDate,"yyyyMMddHHmmss"));
        page = messageService.queryPage(page);
        page.setRecords(page.getRecords());
        return Rets.success(page);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "清空所有历史消息")
    @RequiresPermissions(value = {Permission.MSG_CLEAR})
    public Object clear() {
        messageService.clear();
        return Rets.success();
    }
}