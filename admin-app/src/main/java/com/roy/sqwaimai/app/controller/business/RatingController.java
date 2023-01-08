package com.roy.sqwaimai.app.controller.business;

import com.roy.sqwaimai.app.controller.BaseController;
import com.roy.sqwaimai.core.entity.vo.front.Rets;
import com.roy.sqwaimai.core.entity.Ratings;
import com.roy.sqwaimai.core.service.RatingService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ratings/")
public class RatingController extends BaseController {
    @DubboReference
    private RatingService ratingService;

    @RequestMapping(value = "/restaurants/{restaurant_id}/ratings",method = RequestMethod.GET)
    public Object ratings(@PathVariable("restaurant_id") Long restaurantId){
        Ratings ratings = ratingService.findByShopId(restaurantId);
        return ratings.getRatings();
    }
    @RequestMapping(value = "/restaurants/{restaurant_id}/scores",method = RequestMethod.GET)
    public Object score(@PathVariable("restaurant_id") Long restaurantId){
        Ratings ratings = ratingService.findByShopId(restaurantId);
        return ratings.getScores();
    }
    @RequestMapping(value = "/restaurants/{restaurant_id}/tags",method = RequestMethod.GET)
    public Object tags(@PathVariable("restaurant_id") Long restaurantId){
        Ratings ratings = ratingService.findByShopId(restaurantId);
        return  Rets.success(ratings.getTags());
    }

}
