package com.ruiyuyun.wx.service;

import com.ruiyuyun.wx.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User getByPhoneNumber(String phoneNumber);

     void register(String phoneNumber, String password);

    User login(User user);
}
