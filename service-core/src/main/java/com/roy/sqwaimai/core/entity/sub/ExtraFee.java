package com.roy.sqwaimai.core.entity.sub;

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
