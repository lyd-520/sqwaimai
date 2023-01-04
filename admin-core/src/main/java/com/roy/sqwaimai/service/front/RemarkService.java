package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.service.MongoService;
import org.springframework.stereotype.Service;

@Service
public class RemarkService extends MongoService {

    public Object findOne() {
        return mongoRepository.findOne("remarks");
    }
}
