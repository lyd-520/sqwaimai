package com.roy.sqwaimai.core.entity;

import com.roy.sqwaimai.core.entity.sub.Rating;
import com.roy.sqwaimai.core.entity.sub.Score;
import com.roy.sqwaimai.core.entity.sub.Tag;
import com.roy.sqwaimai.core.util.Lists;
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

    public Ratings(Long restaurant_id){
        this.restaurant_id = restaurant_id;
    }
}
