package com.roy.sqwaimai.bean.entity.front.sub;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExtraFee {

    private String description;
    private String name;
    private Double price;
    private int quantity;
    private int type;
}
