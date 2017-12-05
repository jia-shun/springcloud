package com.js.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestRest {
    @Value("${name.en}")
    private String name;
    @RequestMapping("/config")
    @ResponseBody
    public String test() throws InterruptedException{
        return name;
    }
}
