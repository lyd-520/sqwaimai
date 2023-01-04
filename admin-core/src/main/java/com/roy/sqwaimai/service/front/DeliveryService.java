package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.entity.front.Delivery;
import com.roy.sqwaimai.service.MongoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService extends MongoService {

    public List<Delivery> findAll(){
        return mongoRepository.findAll(Delivery.class);
    }
}
