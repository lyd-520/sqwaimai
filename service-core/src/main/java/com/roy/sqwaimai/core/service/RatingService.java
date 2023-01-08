package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.Ratings;
import com.roy.sqwaimai.core.entity.Shop;

public interface RatingService{

    Ratings findByShopId(Long restaurantId);

    void initRating(Shop shop);
}
