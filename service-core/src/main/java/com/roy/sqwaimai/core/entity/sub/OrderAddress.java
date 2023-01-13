package com.roy.sqwaimai.core.entity.sub;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderAddress implements Serializable {
    private Long id;
    private String address;
    private String phone;
    private String bakupphone;
    private String name;
    private String st_geohash;
    private String address_detail;
    private Long user_id;
    private Integer city_id;
    private Integer sex;
}
