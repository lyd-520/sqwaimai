package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.entity.Explain;
import com.roy.sqwaimai.core.service.ExplainService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/profile")
public class ExplainController extends BaseController {
    @DubboReference
    private ExplainService explainService;
    @RequestMapping(value="/explain",method = RequestMethod.GET)
    public Object getData(){
        Explain explain = explainService.findOne();
        return Rets.success(explain);
    }
}