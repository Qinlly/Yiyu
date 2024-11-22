package com.ruiyuyun.wx.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("diaries")
public class Diary {


    private Integer id;
    private Integer userId;
    private String content;

    private String tagName;
    private Integer tagId;

    private Date createdTime;
    private Date updateTime;



}
