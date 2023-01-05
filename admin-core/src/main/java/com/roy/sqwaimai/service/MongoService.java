package com.roy.sqwaimai.service;

import com.roy.sqwaimai.dao.MongoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public abstract class MongoService {

    @Resource
    protected MongoRepository mongoRepository;
}
