package com.roy.sqwaimai.busi;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class BusiServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(BusiServiceApp.class,args);
    }
}
