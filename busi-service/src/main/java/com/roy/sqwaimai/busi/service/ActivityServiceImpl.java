package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.Activity;
import com.roy.sqwaimai.core.service.ActivityService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DubboService
public class ActivityServiceImpl extends MongoService implements ActivityService {

    public List<Activity> findAll() {
        return mongoRepository.findAll(Activity.class);
    }
}
