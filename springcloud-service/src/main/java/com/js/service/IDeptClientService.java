package com.js.service;


import com.js.FeignClientConfig;
import com.js.service.fallback.IDeptClientServiceFallbackFactory;
import com.js.vo.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by jiashun on 2017/8/13.
 */
@FeignClient(value="SPRINGCLOUD-PROVIDER-DEPT",configuration=FeignClientConfig.class,fallbackFactory = IDeptClientServiceFallbackFactory.class)
public interface IDeptClientService {
    @RequestMapping(method= RequestMethod.GET,value = "/dept/get/{id}")
    public Dept get(@PathVariable("id") long id);
    @RequestMapping(method=RequestMethod.GET,value="/dept/list")
    public List<Dept> list() ;
    @RequestMapping(method=RequestMethod.POST,value="/dept/add")
    public boolean add(Dept dept) ;
}
