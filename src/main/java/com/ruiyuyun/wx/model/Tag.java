package com.ruiyuyun.wx.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tags")
public class Tag {
    private Integer id;

    private String tagName;

    private Integer userId;
}
