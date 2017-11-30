package com.js;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Consumer_80_StartSpringCloud {
    public static void main(String[] args) {
        SpringApplication.run(Consumer_80_StartSpringCloud.class,args);
    }
}
