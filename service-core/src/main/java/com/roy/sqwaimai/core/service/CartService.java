package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.Carts;
import com.roy.sqwaimai.core.entity.sub.OrderItem;

import java.util.List;

public interface CartService{

    Carts checkOut(String from, Long restaurantId, List<OrderItem> entities);
}
