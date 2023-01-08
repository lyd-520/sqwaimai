package com.roy.sqwaimai.app.listener;

import com.roy.sqwaimai.cache.ConfigCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *系统监听器<br>
 *系统启动时加载全局参数(t_sys_cfg标中的数据)到缓存中
 */
@Component
public class CacheListener implements CommandLineRunner {

    @Autowired
    private ConfigCache configCache;
    private Logger logger = LoggerFactory.getLogger(CacheListener.class);

    public void loadCache() {
        configCache.cache();
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info(".....................load cache........................");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                loadCache();
            }
        });
        thread.start();
    }
}
