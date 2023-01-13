package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.busi.dao.RedisLockDao;
import com.roy.sqwaimai.core.entity.FrontRiderInfo;
import com.roy.sqwaimai.core.entity.Order;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.service.FrontRiderService;
import com.roy.sqwaimai.core.util.Maps;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@DubboService
public class FrontRiderServiceImpl extends MongoService implements FrontRiderService {

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

    @Resource
    private RedisLockDao redisLockDao;

    public Object checkOrder(long orderid, long userid) {
        Boolean isLocked = false;
        try{
            isLocked = redisLockDao.lock(orderid);
            if(isLocked){
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
                    return frontRiderInfo;
                }
            }else{
                return Maps.newHashMap("error","当前订单已被其他骑手抢单，请刷新后重新抢单。");
            }
        }finally {
                if(isLocked){
                redisLockDao.unlock(orderid);
            }
        }
    }
}
