package com.roy.sqwaimai.core.entity.sub;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderShopAddress implements Serializable {

    private Long id;
    private String address;
    private Double latitude;
    private Double longitude;
    private String image_hash;
    private String image_url;
    private String name;
    private Integer type=0;

}
