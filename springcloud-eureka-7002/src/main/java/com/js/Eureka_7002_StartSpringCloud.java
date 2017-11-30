package com.js;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Eureka_7002_StartSpringCloud {
    public static void main(String[] args) {
        SpringApplication.run(Eureka_7002_StartSpringCloud.class,args);
    }
}
