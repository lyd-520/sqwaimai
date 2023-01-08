package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.service.EntryService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/entry")
public class EntryController extends BaseController {
    @DubboReference
    private EntryService entryService;
    @RequestMapping(value = "/index_entry",method = RequestMethod.GET)
    public Object list(){
        return Rets.success(entryService.findAll());
    }
}
