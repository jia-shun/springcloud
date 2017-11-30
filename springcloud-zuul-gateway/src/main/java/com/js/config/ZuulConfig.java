package com.js.config;

import com.js.filter.AuthorizedRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulConfig {
    @Bean
    public AuthorizedRequestFilter getAthorizedRequestFilter(){
        return new AuthorizedRequestFilter();
    }
}
