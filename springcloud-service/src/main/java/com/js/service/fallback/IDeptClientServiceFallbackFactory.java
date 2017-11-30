package com.js.service.fallback;

import com.js.service.IDeptClientService;
import com.js.vo.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jiashun on 2017/8/13.
 */
@Component
public class IDeptClientServiceFallbackFactory implements FallbackFactory<IDeptClientService>{
    public IDeptClientService create(Throwable throwable) {
        return new IDeptClientService() {
            public Dept get(long id) {
                Dept vo = new Dept();
                vo.setDeptno(888888L);
                vo.setDname("【测试服务降级】：此时服务繁忙，请稍后再试");
                vo.setLoc("马上回来");
                return vo;
            }

            public List<Dept> list() {
                return null;
            }

            public boolean add(Dept dept) {
                return false;
            }
        };
    }
}
