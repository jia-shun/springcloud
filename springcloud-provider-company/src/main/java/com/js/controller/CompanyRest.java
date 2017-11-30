package com.js.controller;

import com.js.vo.Company;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyRest {
    @RequestMapping(value = "/company/get/{title}",method = RequestMethod.GET)
    public Object get(@PathVariable("title")String title){
        Company vo = new Company();
        vo.setTitle(title);
        vo.setNote("中科安凯");
        return vo;
    }
}
