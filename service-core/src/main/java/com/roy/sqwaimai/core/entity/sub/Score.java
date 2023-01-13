package com.roy.sqwaimai.core.entity.sub;

import lombok.Data;

import java.io.Serializable;

@Data
public class Score implements Serializable {
    private Integer compare_rating = 0;
    private Integer deliver_time = 0;
    private Integer food_score = 0;
    private Integer order_rating_amount = 0;
    private Integer overall_score = 0;
    private Integer service_score = 0;
}
