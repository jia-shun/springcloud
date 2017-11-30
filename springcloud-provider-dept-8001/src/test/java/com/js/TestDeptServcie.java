package com.js;

import com.js.service.IDeptService;
import com.js.vo.Dept;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@SpringBootTest (classes = Dept_8001_StartSpringCloud.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestDeptServcie {
    @Resource
    private IDeptService deptService;

    @Test
    public void testGet(){
        System.out.println(this.deptService.get(1));
    }

    @Test
    public void testAdd(){
        Dept vo = new Dept();
        vo.setDname("Test" + System.currentTimeMillis());
        System.out.println(this.deptService.add(vo));
    }

    @Test
    public void testList(){
        System.out.println(this.deptService.list());
    }
}
