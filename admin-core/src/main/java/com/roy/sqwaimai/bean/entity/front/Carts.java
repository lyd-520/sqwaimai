package com.roy.sqwaimai.bean.entity.front;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * 购物车主信息
 */
@Document(collection = "carts")
@Data
public class Carts extends BaseMongoEntity {
    @Id
    private String _id;
    private Long id;
    //到达时间 -- 根据百度地图估算
    private String delivery_reach_time;
    private String sig;
//    private Integer is_support_ninja=1;
//    private Boolean is_support_coupon = false;
    private CartInfo cartInfo;
    //发票信息
    private Map invoice;
    private Map currrent_address;
    private List<Payment> payments;

    //必须在setget方法加上该注释，否则_id值会覆盖在id上
    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }


}
