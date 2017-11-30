package com.js;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * Created by jiashun on 2017/8/13.
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardApplication_9001 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication_9001.class,args);
    }
}
