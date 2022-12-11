package com.roy.sqwaimai.bean.entity.front.sub;

import com.roy.sqwaimai.utils.Lists;
import lombok.Data;

import java.util.List;

@Data
public class Rating {
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
