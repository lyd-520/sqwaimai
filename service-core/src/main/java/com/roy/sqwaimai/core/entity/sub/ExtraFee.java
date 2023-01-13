package com.roy.sqwaimai.core.entity.sub;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ExtraFee implements Serializable {

    private String description;
    private String name;
    private Double price;
    private int quantity;
    private int type;
}
