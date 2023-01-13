package com.roy.sqwaimai.core.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpecVo implements Serializable {
    private String specs;
    private String packing_fee;
    private String price;
}
