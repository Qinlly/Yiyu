package com.ruiyuyun.wx.request;

import lombok.Data;

@Data
public class UpdateTagRequest {
    private Integer tagId;
    private String newTagName;



}
