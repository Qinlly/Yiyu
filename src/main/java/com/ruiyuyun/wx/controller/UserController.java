package com.ruiyuyun.wx.controller;

import com.ruiyuyun.wx.model.User;
import com.ruiyuyun.wx.request.UserRequest;
import com.ruiyuyun.wx.response.Result;
import com.ruiyuyun.wx.service.UserService;
import com.ruiyuyun.wx.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@CrossOrigin
public class UserController {

    final UserService userService;

    @GetMapping("/diary/{phoneNumber}")
    public Result getByPhoneNumber(@PathVariable String phoneNumber) {
        //查询

        User user = userService.getByPhoneNumber(phoneNumber);
        return  Result.success(user);

    }

    @PutMapping("/diary/register")
    public Result register(@Valid UserRequest user, BindingResult result) {

        if(result.hasErrors()){
            return Result.error(result.getFieldError().getDefaultMessage());
        }

        try {
        userService.register(user.getPhoneNumber(), user.getPassword());
        } catch (Exception e) {
            return Result.error("用户已存在");
        }

        return Result.success();


    }

    @PostMapping("/diary/login")
    public Result login(@RequestBody UserRequest  user) {

        User U =new User();
        U.setPhoneNumber(user.getPhoneNumber());
        U.setPassword(user.getPassword());



        User u = userService.login(U);

        //登陆成功,生成令牌,下发令牌
        if(u!= null){
            Map<String, Object> map = new HashMap<>();
            map.put("userId", u.getId());
            map.put("phoneNumber", u.getPhoneNumber());
              String jwt=JwtUtils.getToken(map); //jwt包含用户信息id和手机号
            System.out.println("jwt:"+jwt);
            return Result.success(jwt);

        }

        return  Result.error("用户名或密码错误");
    }

    @PostMapping("/hello")

    public Result hello() {
        return Result.success("hello");
    }


}
