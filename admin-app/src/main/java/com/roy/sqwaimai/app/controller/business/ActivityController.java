package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.bean.entity.front.Activity;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.dao.MongoRepository;
import com.roy.sqwaimai.service.front.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class ActivityController extends BaseController {
    @Resource
    private ActivityService activityService;

    @RequestMapping(value = "/api/activity/nearbyactivities",method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(value = "latitude",required = false) String latitude,
                       @RequestParam(value = "longitude",required = false) String longitude){
        return Rets.success(activityService.findAll());
    }
}
