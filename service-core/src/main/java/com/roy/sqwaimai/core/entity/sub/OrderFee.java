package com.roy.sqwaimai.core.entity.sub;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderFee implements Serializable {
    private Long category_id;
    private String name;
    private Double price;
    private Double quantity;
    private Double delevery;
}
