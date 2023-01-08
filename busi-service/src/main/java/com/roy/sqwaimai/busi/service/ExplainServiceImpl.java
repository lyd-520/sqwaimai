package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.Explain;
import com.roy.sqwaimai.core.service.ExplainService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class ExplainServiceImpl extends MongoService implements ExplainService {

    public Explain findOne(){
        return mongoRepository.findOne(Explain.class);
    }
}
