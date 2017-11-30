package com.js;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class zuul_springCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(zuul_springCloudApplication.class,args);
    }
}
