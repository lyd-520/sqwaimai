package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.entity.front.Activity;
import com.roy.sqwaimai.service.MongoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService extends MongoService {

    public List<Activity> findAll() {
        return mongoRepository.findAll(Activity.class);
    }
}
