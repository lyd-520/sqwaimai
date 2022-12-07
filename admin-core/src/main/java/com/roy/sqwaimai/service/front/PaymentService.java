package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.AppConfiguration;
import com.roy.sqwaimai.bean.entity.front.Order;
import com.roy.sqwaimai.bean.entity.front.sub.OrderStatusBar;
import com.roy.sqwaimai.dao.MongoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    private MongoRepository mongoRepository;

    public Boolean payOrder(String merchantOrderNo){
        Order order = mongoRepository.findOne(Order.class, "id", Long.valueOf(merchantOrderNo));

        order.setStatus_code(Order.STATUS_PAID);
        order.setStatus_title(Order.getStatusCodeStr(Order.STATUS_PAID));
        mongoRepository.update(order);
        return true;
    }
}
