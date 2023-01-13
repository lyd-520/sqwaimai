package com.roy.sqwaimai.core.entity.sub;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderStatusBar implements Serializable {
    private String color;
    private String image_type="";
    private String sub_title;
    private String title="";

}
