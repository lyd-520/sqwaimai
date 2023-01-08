package com.roy.sqwaimai.core.entity.sub;

import lombok.Data;

@Data
public class OrderFee {
    private Long category_id;
    private String name;
    private Double price;
    private Double quantity;
    private Double delevery;
}
