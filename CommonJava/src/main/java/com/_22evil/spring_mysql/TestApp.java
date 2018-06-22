package com._22evil.spring_mysql;

import com._22evil.spring_mysql.dao.UserRepository;
import com._22evil.spring_mysql.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class TestApp {
    @Autowired
    private UserRepository userRepository;

    public void test() {
        User user = new User();
        user.setName("zjs");
        user.setEmail("@@");
        userRepository.save(user);
    }
}