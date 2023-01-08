package com.roy.sqwaimai.core.entity.sub;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderBasket {

    private OrderFee orderFee = new OrderFee();
    private List extra = new ArrayList();
    private List pindan_map = new ArrayList();
    private List<OrderItem> items = new ArrayList<>();

}
