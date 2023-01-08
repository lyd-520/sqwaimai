package com.roy.sqwaimai.core.entity.sub;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class OrderItem {
    @Id
    private String _id;
    private Long id;
    private String name;
    private Double price;
    private int quantity;

    private String specs;
    private List new_specs;
    private Double packing_fee;
    private int stock;


    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }


}
