package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.Order;
import com.roy.sqwaimai.core.entity.sys.AccountInfo;
import com.roy.sqwaimai.core.entity.vo.OrderVo;
import com.roy.sqwaimai.core.query.Page;

import java.util.Map;

public interface OrderService{
    Page<Order> queryPageUserOrder(Page<Order> page, Long userId, int orderStatus);

    Page<Order> queryPageOrder(Page<Order> page, Long restaurantId, Long orderId,AccountInfo accountInfo);

    Map getFullOrderInfo(Long orderid);

    Order getOrder(Long orderid);

    Order updateOrderStatus(Long orderId, Integer status);

    void cancelOrder(Order order);

    void finishOrder(Order order);

    Order sendOrder(long orderid);

    Order generateOrder(Long userId, Long cartId, OrderVo orderVo);

    Page<Order> queryPagePaidOrder();

    Page<Map> queryNearByOrder(Double longitude, Double latitude, int limit);
}
