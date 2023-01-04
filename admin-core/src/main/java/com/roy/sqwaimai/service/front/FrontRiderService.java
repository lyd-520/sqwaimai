package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.entity.front.FrontRiderInfo;
import com.roy.sqwaimai.bean.entity.front.Order;
import com.roy.sqwaimai.bean.vo.front.Rets;
import com.roy.sqwaimai.service.MongoService;
import com.roy.sqwaimai.utils.Maps;
import org.springframework.stereotype.Service;

@Service
public class FrontRiderService extends MongoService {

    public FrontRiderInfo orderSended(Order order, long userid) {
        FrontRiderInfo frontRiderInfo = mongoRepository.findOne(FrontRiderInfo.class, "id", userid);
        frontRiderInfo.setSending_order_id(-1L);
        frontRiderInfo.setWork_status(FrontRiderInfo.STATUS_OFFLINE);
        frontRiderInfo.setOrder_count(frontRiderInfo.getOrder_count()+1);

        Double deliverFee = order.getBasket().getOrderFee().getDelevery();
        frontRiderInfo.setBalance(frontRiderInfo.getBalance()+deliverFee);
        frontRiderInfo.setBalance_amount(frontRiderInfo.getBalance_amount()+deliverFee);
        mongoRepository.update(frontRiderInfo);
        return frontRiderInfo;
    }

    public Object checkOrder(long orderid, long userid) {
        Order order = mongoRepository.findOne(Order.class, "id", orderid);
        FrontRiderInfo frontRiderInfo = mongoRepository.findOne(FrontRiderInfo.class, "rider_id", userid);
        if(order.getStatus_code() == Order.STATUS_DELIVERYING){
            return Maps.newHashMap("error","当前订单已由其他骑手派送。请刷新后重新抢单。");
        }else if(frontRiderInfo.getSending_order_id()>0){
            return Maps.newHashMap("error","当前派单中。请先派送完编号为["+frontRiderInfo.getSending_order_id()+"]的订单");
        }else{
            frontRiderInfo.setSending_order_id(orderid);
            frontRiderInfo.setWork_status(FrontRiderInfo.STATUS_SENDING);
            mongoRepository.update(frontRiderInfo);
            order.setStatus_code(Order.STATUS_DELIVERYING);
            order.setStatus_title(Order.getStatusCodeStr(Order.STATUS_DELIVERYING));
            mongoRepository.update(order);
            return Rets.success(frontRiderInfo);
        }
    }
}
