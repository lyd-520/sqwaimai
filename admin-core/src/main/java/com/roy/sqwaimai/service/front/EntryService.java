package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.entity.front.Entry;
import com.roy.sqwaimai.service.MongoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService extends MongoService {

    public List<Entry> findAll(){
        return mongoRepository.findAll(Entry.class);
    }

}
