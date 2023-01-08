package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.Order;
import com.roy.sqwaimai.core.service.PaymentService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class PaymentServiceImpl extends MongoService implements PaymentService {

    private Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    public Boolean payOrder(String merchantOrderNo){
        Order order = mongoRepository.findOne(Order.class, "id", Long.valueOf(merchantOrderNo));

        order.setStatus_code(Order.STATUS_PAID);
        order.setStatus_title(Order.getStatusCodeStr(Order.STATUS_PAID));
        mongoRepository.update(order);
        return true;
    }
}
