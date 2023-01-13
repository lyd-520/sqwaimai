package com.roy.sqwaimai.busi.service;

import com.roy.sqwaimai.core.entity.*;
import com.roy.sqwaimai.core.service.InitService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class InitServiceImpl extends MongoService implements InitService {
    @Override
    public void initMongdoData() {
        //删除全部mongodb测试数据
            mongoRepository.clear(Shop.class);
            mongoRepository.clear(Food.class);
            mongoRepository.clear(ShopMenu.class);
            mongoRepository.clear(Address.class);
            mongoRepository.clear(Ratings.class);
            mongoRepository.clear(Order.class);
            mongoRepository.clear(Carts.class);
            mongoRepository.clear("sesions");
            mongoRepository.clear("users");
            mongoRepository.clear("userinfos");
            mongoRepository.clear("riders");
            mongoRepository.clear("ridersinfo");
    }
}
