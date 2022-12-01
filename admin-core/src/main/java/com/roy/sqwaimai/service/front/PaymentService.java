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

        OrderStatusBar statusBar = new OrderStatusBar();
        statusBar.setColor("f60");
        statusBar.setSub_title("");
        statusBar.setTitle("已支付");
        order.setStatus_bar(statusBar);
        mongoRepository.update(order);
        return true;
    }
}
