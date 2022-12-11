package com.roy.sqwaimai.bean.entity.front;

import com.roy.sqwaimai.bean.entity.front.sub.Rating;
import com.roy.sqwaimai.bean.entity.front.sub.Score;
import com.roy.sqwaimai.bean.entity.front.sub.Tag;
import com.roy.sqwaimai.utils.Lists;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "ratings")
public class Ratings extends BaseMongoEntity {
    private Long restaurant_id;
    private List<Rating> ratings = Lists.newArrayList(new Rating());
    private List<Tag> tags = Lists.newArrayList(new Tag());
    private Score scores = new Score();

    public Ratings(){

    }
    public Ratings(Long restaurant_id){
        this.restaurant_id = restaurant_id;
    }
}
