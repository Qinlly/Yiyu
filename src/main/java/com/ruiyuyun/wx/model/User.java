package com.ruiyuyun.wx.model;

import io.swagger.annotations.ApiModelProperty;
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

    private String phoneNumber;


    private String password;

}
