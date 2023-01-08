package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.service.CategoryService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class CategoryServiceImpl extends MongoService implements CategoryService {

    public Object findAll() {
        return mongoRepository.findAll("categories");
    }
}
