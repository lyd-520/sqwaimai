package com.roy.sqwaimai.service.front;

import com.roy.sqwaimai.bean.entity.front.Ratings;
import com.roy.sqwaimai.bean.entity.front.Shop;
import com.roy.sqwaimai.bean.entity.front.sub.Score;
import com.roy.sqwaimai.service.MongoService;
import com.roy.sqwaimai.utils.Maps;
import org.springframework.stereotype.Service;

@Service
public class RatingService extends MongoService {

    public Ratings findByShopId(Long restaurantId){
        return mongoRepository.findOne(Ratings.class,"restaurant_id",restaurantId);
    }

    public void initRating(Shop shop) {
        Ratings ratings = mongoRepository.findOne(Ratings.class, Maps.newHashMap("restaurant_id", shop.getId()));
        if (ratings == null) {
            ratings = new Ratings(shop.getId());
            ratings.setScores(new Score());
            mongoRepository.save(ratings);
        }
    }
}
