package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.Ratings;
import com.roy.sqwaimai.core.entity.Shop;
import com.roy.sqwaimai.core.entity.sub.Score;
import com.roy.sqwaimai.core.service.RatingService;
import com.roy.sqwaimai.core.util.Maps;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class RatingServiceImpl extends MongoService implements RatingService {

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
