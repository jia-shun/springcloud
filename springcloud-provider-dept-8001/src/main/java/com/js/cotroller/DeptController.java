package com.js.cotroller;

import com.js.service.IDeptService;
import com.js.vo.Dept;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class DeptController {
    @Resource
    private IDeptService deptService;
    @Resource
    private DiscoveryClient discoveryClient ; //进行Eureka的注册服务
    @RequestMapping("/dept/discover")
    public Object discover(){   //直接返回发现服务信息
        return this.discoveryClient;
    }
    @RequestMapping(value = "/dept/get/{id}",method = RequestMethod.GET)
    public Object get(@PathVariable("id") long id){
        return this.deptService.get(id);
    }
    @RequestMapping(value = "dept/add" ,method = RequestMethod.POST)
    public Object add(@RequestBody Dept dept){
        return this.deptService.add(dept);
    }
    @RequestMapping(value = "dept/list" ,method = RequestMethod.GET)
    public Object list(){
        return this.deptService.list();
    }
}
