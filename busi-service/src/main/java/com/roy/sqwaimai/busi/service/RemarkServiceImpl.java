package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.service.RemarkService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class RemarkServiceImpl extends MongoService implements RemarkService {

    public Object findOne() {
        return mongoRepository.findOne("remarks");
    }
}
