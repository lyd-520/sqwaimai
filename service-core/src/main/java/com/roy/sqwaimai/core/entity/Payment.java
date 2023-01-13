package com.roy.sqwaimai.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "payments")
@Data
public class Payment implements Serializable {
    @Id
    private String _id;
    private String description;
    private String disabled_reason;
    private Long id;
    private Boolean is_online_payment=true;
    private String name;
    private List promotion;
    private Integer select_state;


    @JSONField(name="_id")
    public String get_id() {
        return _id;
    }
    @JSONField(name="_id")
    public void set_id(String _id) {
        this._id = _id;
    }

}
