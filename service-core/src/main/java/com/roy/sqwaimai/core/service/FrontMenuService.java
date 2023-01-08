package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.ShopMenu;

import java.util.List;
import java.util.Map;

public interface FrontMenuService{

    void updateMenuFood(Long foodId);

    void deleteMenuFood(Long foodId);

    List<Map> listShopMenu(Long restaurantId);

    Object getMenuById(Long menuId);

    void saveInitMenu(ShopMenu menu);
}
