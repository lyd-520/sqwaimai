package com.roy.sqwaimai.app.listener;

import com.roy.sqwaimai.bean.entity.front.*;
import com.roy.sqwaimai.dao.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebListener implements CommandLineRunner {
    @Autowired
    private MongoRepository mongoRepository;
    @Value("${sqwaimai.mongodb.init}")
    private Boolean init;

    /**
     * 初始化mongodb 数据
     */
    public  void initMongoData(){

        if(init) {
            //删除全部mongodb测试数据
            mongoRepository.clear(Shop.class);
            mongoRepository.clear(Food.class);
            mongoRepository.clear(Menu.class);
            mongoRepository.clear(Address.class);
            mongoRepository.clear(Ratings.class);
            mongoRepository.clear(Order.class);
            mongoRepository.clear(Carts.class);
            mongoRepository.clear("sesions");
            mongoRepository.clear("users");
            mongoRepository.clear("userinfos");
        }
    }
    @Override
    public void run(String... args) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                initMongoData();
            }
        });
        thread.start();
    }
}
