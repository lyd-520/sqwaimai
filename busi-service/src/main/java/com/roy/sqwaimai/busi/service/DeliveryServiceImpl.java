package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.Delivery;
import com.roy.sqwaimai.core.service.DeliveryService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DubboService
public class DeliveryServiceImpl extends MongoService implements DeliveryService {

    public List<Delivery> findAll(){
        return mongoRepository.findAll(Delivery.class);
    }
}
