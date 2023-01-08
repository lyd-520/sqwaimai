package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.service.ActivityService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityController extends BaseController {
    @DubboReference
    private ActivityService activityService;

    @RequestMapping(value = "/api/activity/nearbyactivities",method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(value = "latitude",required = false) String latitude,
                       @RequestParam(value = "longitude",required = false) String longitude){
        return Rets.success(activityService.findAll());
    }
}
