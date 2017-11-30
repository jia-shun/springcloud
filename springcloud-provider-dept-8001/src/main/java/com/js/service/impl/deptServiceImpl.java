package com.js.service.impl;

import com.js.dao.IDeptDAO;
import com.js.service.IDeptService;
import com.js.vo.Dept;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class deptServiceImpl implements IDeptService {

    @Resource
    private IDeptDAO deptDAO;


    public Dept get(long id) {
        return this.deptDAO.findById(id);
    }

    public boolean add(Dept dept) {
        return this.deptDAO.doCreate(dept);
    }

    public List<Dept> list() {
        return this.deptDAO.findAll();
    }
}
