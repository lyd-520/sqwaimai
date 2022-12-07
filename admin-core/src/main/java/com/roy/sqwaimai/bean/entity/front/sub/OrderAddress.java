package com.roy.sqwaimai.bean.entity.front.sub;

import lombok.Data;

@Data
public class OrderAddress {
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
