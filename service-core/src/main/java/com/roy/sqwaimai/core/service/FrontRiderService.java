package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.FrontRiderInfo;
import com.roy.sqwaimai.core.entity.Order;

public interface FrontRiderService{

    FrontRiderInfo orderSended(Order order, long userid);

    Object checkOrder(long orderid, long userid);
}
