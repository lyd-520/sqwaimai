package com.roy.sqwaimai.bean.entity.front.sub;

import lombok.Data;

import java.util.List;

@Data
public class OrderTimelineNode {
    private List actions;
    private String description;
    private String sub_description;
    private String title;
    private Integer in_processing;

}
