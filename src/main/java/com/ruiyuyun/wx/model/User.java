package com.ruiyuyun.wx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.Integer;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;

    @NotBlank(message = "手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号格式不正确")
    private String phoneNumber;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码应在6-20位之间")
    private String password;

}
