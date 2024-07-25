package com.study.testCron.service.impl;

import com.study.testCron.bean.User;
import com.study.testCron.dao.common.UserDao;
import com.study.testCron.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
