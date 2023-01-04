package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.entity.front.Explain;
import com.roy.sqwaimai.service.MongoService;
import org.springframework.stereotype.Service;

@Service
public class ExplainService extends MongoService {

    public Explain findOne(){
        return mongoRepository.findOne(Explain.class);
    }
}
