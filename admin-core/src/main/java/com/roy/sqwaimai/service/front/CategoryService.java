package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.service.MongoService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends MongoService {

    public Object findAll() {
        return mongoRepository.findAll("categories");
    }
}
