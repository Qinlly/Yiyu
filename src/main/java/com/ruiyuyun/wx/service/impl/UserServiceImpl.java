package com.ruiyuyun.wx.service.impl;

import com.ruiyuyun.wx.mapper.UserMapper;
import com.ruiyuyun.wx.model.User;
import com.ruiyuyun.wx.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {
//    @Autowired
    final UserMapper userMapper;
    @Override
    public User getByPhoneNumber(String phoneNumber) {

        return userMapper.getUserByphoneNumber(phoneNumber);
    }

    @Override
    public void register(String phoneNumber, String password) {


        User user = new User();

        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);

            userMapper.register(user);


    }

    @Override
    public User login(User user) {
        User user1 = userMapper.getUserByphoneNumber(user.getPhoneNumber());
        if (user1 == null) {
            userMapper.register(user);
        }
        return userMapper.login(user);
    }
}
