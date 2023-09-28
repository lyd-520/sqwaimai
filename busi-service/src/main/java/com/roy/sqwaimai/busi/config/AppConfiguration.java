package com.roy.sqwaimai.busi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppConfiguration {
    @Value("${api.qq.map.url}")
    private String qqApiUrl;
    @Value("${cfg.tencentkey1}")
    private String tencentKey;
    @Value("${cfg.tencentkey2}")
    private String tencentKey2;
    @Value("${cfg.tencentkey3}")
    private String tencentKey3;
    @Value("${cfg.baidu.map.url}")
    private String baiduApiUrl;
    @Value(("${cfg.baidu.key1}"))
    private String baiduKey;
    @Value(("${cfg.baidu.key2}"))
    private String baiduKey2;
}
