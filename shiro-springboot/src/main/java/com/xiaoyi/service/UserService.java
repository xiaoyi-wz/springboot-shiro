package com.xiaoyi.service;

import com.xiaoyi.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User queryUserByName(String name);
}
