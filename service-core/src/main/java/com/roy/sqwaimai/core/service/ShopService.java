package com.roy.sqwaimai.core.service;

import com.roy.sqwaimai.core.entity.Order;
import com.roy.sqwaimai.core.entity.Shop;
import com.roy.sqwaimai.core.entity.sys.AccountInfo;
import com.roy.sqwaimai.core.entity.vo.ShopVo;
import com.roy.sqwaimai.core.query.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShopService{
    Object findOne(Long id);

    void adminlistShop(Page<Shop> page, String name, String state,AccountInfo accountInfo);

    void clientListShop(Page<Shop> page, String name, String latitude, String longitude, Long[] categoryIds) ;

    long countShop() ;

    void deleteShop(Long id);

    void auditShop(Shop shop);

    void stopShop(Shop shop);

    void updateShop(Shop shop, AccountInfo accountInfo);

    Shop addShop(ShopVo shopVo, String ip);

    List<Map> searchNearShop(String geoHash, String keyWord);

    BigDecimal checnOutAvaiable(Long id);

    void addAvailable(Order order);

    Shop findOneByName(String username, String password);

    void updateShopPassword(Long shopId, String password);
}
