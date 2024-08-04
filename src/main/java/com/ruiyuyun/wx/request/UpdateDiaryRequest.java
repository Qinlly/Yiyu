package com.ruiyuyun.wx.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateDiaryRequest {
    private Integer diaryId;
    private String content;
    private List<String> newTagNames;


}
