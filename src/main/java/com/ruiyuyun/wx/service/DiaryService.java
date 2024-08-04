package com.ruiyuyun.wx.service;

import com.github.pagehelper.Page;
import com.ruiyuyun.wx.model.Diary;
import com.ruiyuyun.wx.model.PageBean;
import com.ruiyuyun.wx.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface DiaryService {

    public Diary insertDiary(Integer userId, String content, List<String> tagNames);
    public void updateDiary(Integer id, String content, List<String> newTagNames,Integer userId);
    public void deleteDiary(Integer diaryId, Integer userId);


    public PageBean searchDiaries(Integer page, Integer pageSize,  String content,
                                  Integer userId,String tagName,LocalDateTime start, LocalDateTime end);



    public Tag findOrCreateTag(String tagName, Integer userId);

    void deleteDiaryByTag(String tagName, Integer userId);

    void updateTag(Integer tagId, String newTagName, Integer userId);
}
