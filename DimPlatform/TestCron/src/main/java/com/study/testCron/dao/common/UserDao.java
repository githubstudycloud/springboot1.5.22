package com.study.testCron.dao.common;

import com.study.testCron.bean.User;
import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
}