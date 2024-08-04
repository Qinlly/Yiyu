package com.ruiyuyun.wx.request;

import lombok.Data;

import java.util.List;

@Data
public class InsertRequest {
    private String content;
    private List<String> tagNames;
}
