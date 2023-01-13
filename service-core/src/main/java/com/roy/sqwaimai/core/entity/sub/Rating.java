package com.roy.sqwaimai.core.entity.sub;

import com.roy.sqwaimai.core.util.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Rating implements Serializable {
    private String avatar="";
    private List highlights;
    private List<RatingItem> item_ratings = Lists.newArrayList(new RatingItem());
    private String rated_at;
    private Integer rating_star;
    private String rating_text;
    private List tags;
    private String time_spent_desc;
    private String username = "匿名用户";
}
