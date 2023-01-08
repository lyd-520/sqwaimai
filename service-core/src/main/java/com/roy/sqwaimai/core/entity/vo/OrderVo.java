package com.roy.sqwaimai.core.entity.vo;

import com.roy.sqwaimai.core.entity.sub.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * 订单信息
 */
@Data
public class OrderVo {
    private String address_id;
    private String come_from;
    private String deliver_time;
    private String description;
    private String geohash;
    private Long paymethod_id;
    private String sig;
    private List<OrderItem> items;

}
