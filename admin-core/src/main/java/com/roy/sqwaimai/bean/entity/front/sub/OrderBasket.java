package com.roy.sqwaimai.bean.entity.front.sub;

import com.roy.sqwaimai.utils.Maps;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class OrderBasket {

    private List<OrderFee> abandoned_extra = new ArrayList<OrderFee>();
    private OrderFee deliver_fee = new OrderFee();
    private Map packing_fee = Maps.newHashMap();
    private List extra = new ArrayList();
    private List pindan_map = new ArrayList();
    private List<List<OrderItem>> group = new ArrayList<List<OrderItem>>();

}
