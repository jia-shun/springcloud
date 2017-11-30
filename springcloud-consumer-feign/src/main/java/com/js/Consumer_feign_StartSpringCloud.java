package com.js;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.js.service"})
public class Consumer_feign_StartSpringCloud {
    public static void main(String[] args) {
        SpringApplication.run(Consumer_feign_StartSpringCloud.class,args);
    }
}
