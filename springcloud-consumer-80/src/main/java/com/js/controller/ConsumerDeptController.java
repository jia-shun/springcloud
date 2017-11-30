package com.js.controller;

import com.js.vo.Dept;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ConsumerDeptController {
    //public static final String DEPT_GET_URL = "http://dept-8001.com:8001/dept/get/";
    public static final String DEPT_GET_URL = "http://SPRINGCLOUD-PROVIDER-DEPT/dept/get/";
    //public static final String DEPT_LIST_URL = "http://dept-8001.com:8001/dept/list/";
    public static final String DEPT_LIST_URL = "http://SPRINGCLOUD-PROVIDER-DEPT/dept/list/";
    //public static final String DEPT_ADD_URL = "http://dept-8001.com:8001/dept/add?dname=";
    public static final String DEPT_ADD_URL = "http://SPRINGCLOUD-PROVIDER-DEPT/dept/add?dname=";

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private HttpHeaders headers;

    @RequestMapping(value = "/consumer/dept/get")
    public Object getDept(long id){
        Dept dept = this.restTemplate.exchange(DEPT_GET_URL + id, HttpMethod.GET,new HttpEntity<Object>(this.headers),Dept.class).getBody();
        return dept;
    }
    @RequestMapping(value = "/consumer/dept/list")
    public Object listDept(){
        List<Dept> allDepts = this.restTemplate.exchange(DEPT_LIST_URL , HttpMethod.GET,new HttpEntity<Object>(this.headers),List.class).getBody();
        return allDepts;
    }
    @RequestMapping(value = "/consumer/dept/add")
    public Object addDept(Dept dept) throws Exception{
        Boolean flag = this.restTemplate.exchange(DEPT_ADD_URL ,HttpMethod.POST,new HttpEntity<Object>(dept,this.headers),Boolean.class).getBody();
        return flag;
    }
    /*@RequestMapping(value = "/consumer/dept/get")
    public Object getDept(long id) {
        Dept dept = this.restTemplate.getForObject(DEPT_GET_URL + id,
                Dept.class);
        return dept;
    }
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/consumer/dept/list")
    public Object listDept() {
        List<Dept> allDepts = this.restTemplate.getForObject(DEPT_LIST_URL,
                List.class);
        return allDepts;
    }
    @RequestMapping(value = "/consumer/dept/add")
    public Object addDept(Dept dept) {
        Boolean flag = this.restTemplate.postForObject(DEPT_ADD_URL, dept,
                Boolean.class);
        return flag;
    }*/
}
