package com.ruiyuyun.wx.mapper;

import com.ruiyuyun.wx.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper

public interface UserMapper {

    @Select("SELECT * FROM users WHERE phone_number = #{phoneNumber}")
     User getUserByphoneNumber(String phoneNumber);


    @Insert("INSERT INTO users (phone_number, password) VALUES (#{phoneNumber}, #{password})")
    void register(User user);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Select("SELECT * FROM users WHERE phone_number = #{phoneNumber} AND password = #{password}")
    User login(User user);
}
