package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.Entry;
import com.roy.sqwaimai.core.service.EntryService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DubboService
public class EntryServiceImpl extends MongoService implements EntryService {

    public List<Entry> findAll(){
        return mongoRepository.findAll(Entry.class);
    }

}
