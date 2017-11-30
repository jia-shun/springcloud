package com.js;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.js.service"})
@ComponentScan("com.js")
public class Consumer_Hystrix_StartSpringCloud {
    public static void main(String[] args) {
        SpringApplication.run(Consumer_Hystrix_StartSpringCloud.class,args);
    }
}
