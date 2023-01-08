package com.roy.sqwaimai.busi.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public abstract class MongoService {
    @Resource
    protected MongoRepository mongoRepository;
}
