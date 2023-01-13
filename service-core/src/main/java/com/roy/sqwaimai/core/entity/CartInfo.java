package com.roy.sqwaimai.core.entity;

import com.roy.sqwaimai.core.entity.sub.ExtraFee;
import com.roy.sqwaimai.core.entity.sub.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车详细信息
 */
@Data
public class CartInfo implements Serializable {
    private Long id;
//    private List groups;
    //商品信息
    private List<OrderItem> items;
    //额外费用
    private List<ExtraFee> extra;
    //配送费
    private Double deliver_amount;
//    private String discount_amount;
//    private String dist_info;
//    private Boolean is_address_too_far=false;
    private Boolean is_deliver_by_fengniao=true;
//    private Integer is_online_paid=1;
//    private Integer is_ontime_available=0;
//    private Integer must_new_user=0;
//    private Integer must_pay_online=0;
//    private Integer ontime_status=0;
//    private String ontime_unavailable_reason;
    private Integer original_total;
    private String phone;
//    private Integer  promise_delivery_time=0;
    private Long shop_id;
    private Shop shop_info;
    private Integer restaurant_minimum_order_amount;
//    private String restaurant_name_for_url;
//    private Integer restaurant_status=1;
//    private Integer service_fee_explanation=0;
    //总费用
    private String total;
    private Long user_id;
}
