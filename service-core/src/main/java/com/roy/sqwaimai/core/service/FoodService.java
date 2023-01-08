package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.Food;
import com.roy.sqwaimai.core.entity.Shop;
import com.roy.sqwaimai.core.entity.sys.AccountInfo;
import com.roy.sqwaimai.core.entity.vo.FoodVo;
import com.roy.sqwaimai.core.query.Page;

import java.util.Map;

public interface FoodService{

    void updateFoodRemark(Food food);

    Food findOne(Long foodId);

    void updateFood(FoodVo food, AccountInfo accountInfo);

    void addFood(FoodVo foodVo, AccountInfo accountInfo);

    void listPagedFood(Page<Food> page, String state, String name, Long restaurantId,AccountInfo accountInfo);
}
