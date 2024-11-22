package com.ruiyuyun.wx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ruiyuyun.wx.mapper.DiaryMapper;
import com.ruiyuyun.wx.mapper.DiaryTagMapper;
import com.ruiyuyun.wx.mapper.TagMapper;
import com.ruiyuyun.wx.mapper.UserMapper;
import com.ruiyuyun.wx.model.Diary;
import com.ruiyuyun.wx.model.DiaryTag;
import com.ruiyuyun.wx.model.PageBean;
import com.ruiyuyun.wx.model.Tag;
import com.ruiyuyun.wx.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DiaryServiceImpl implements DiaryService {
//    @Autowired
    private final DiaryMapper diaryMapper;
//    @Autowired
    private final UserMapper userMapper;
//    @Autowired
    private final DiaryTagMapper diaryTagMapper;
//    @Autowired
    private final TagMapper tagMapper;

/*
    增
 */

    @Override //在userId下创建日记 并附加标签
    public Diary insertDiary(Integer userId, String content, List<String> tagNames) {
        Diary diary = new Diary();
        diary.setUserId(userId);
        diary.setContent(content);
        diary.setCreatedTime(new Date());
        diary.setUpdateTime(new Date());

        diaryMapper.insertDiary(diary);

        for (String tagName : tagNames) {
            Tag tag = findOrCreateTag(tagName,userId);
            DiaryTag diaryTag = new DiaryTag();
            diaryTag.setDiaryId(diary.getId());
            diaryTag.setTagId(tag.getId());
            diaryTagMapper.insertDiaryTag(diaryTag);

        }

        return diary;
    }

/*
    删
 */

        @Override //根据日记id删除此日记
    public void deleteDiary(Integer diaryId,Integer userId) {
        Diary diary = diaryMapper.selectById(diaryId);

        if(userId.equals(diary.getUserId())) {


            DiaryTag diaryTag = new DiaryTag();

            diaryTag.setDiaryId(diaryId);

            diaryTagMapper.deleteDiaryTagByDiaryId(diaryTag);

            diaryMapper.deleteById(diaryId);
        }
    }

    @Override//根据传入tagName删除该标签下所有日记
    public void deleteDiaryByTag(String tagName,Integer userId) {
        Tag tag = tagMapper.getTagByName(tagName,userId);
        DiaryTag diaryTag = new DiaryTag();
        diaryTag.setTagId(tag.getId());  //获取标签名对应的id
        List<DiaryTag> dt = diaryTagMapper.selectDiaryTagByTagId(diaryTag); //获取此标签id和日记的联系
        for (DiaryTag d : dt) {
            Diary diary = diaryMapper.selectById(d.getDiaryId());

            if(Objects.equals(diary.getUserId(), userId)){
                diaryTagMapper.deleteDiaryTagByTagId(d);
                diaryTagMapper.deleteDiaryTagByDiaryId(d);
                diaryMapper.deleteById(d.getDiaryId());
            }


        }

    }


/*
    改
 */

    @Override  //根据diaryId更新日记
    public void updateDiary(Integer diaryId, String content, List<String> newTagNames,Integer userId) {

        Diary diary = diaryMapper.selectById(diaryId);
        diary.setContent(content);
        diary.setUpdateTime(new Date());
        diary.setUserId(userId);
        diaryMapper.updateDiary(diary);

        //更新日记与标签的关联
        for (String tagName : newTagNames) { //遍历标签
            Tag tag = findOrCreateTag(tagName,userId);
            DiaryTag diaryTag = new DiaryTag();
            diaryTag.setDiaryId(diary.getId());
            diaryTag.setTagId(tag.getId());
            if(diaryTagMapper.selectDiaryTag(diaryTag) == null){
                diaryTagMapper.insertDiaryTag(diaryTag);//若日记与此标签无关联,则增加关联
            }//若已有关联,则无需改变
        }

    }
    @Override //更新标签
    public void updateTag(Integer tagId, String newTagName,Integer userId) {
        Tag tag = tagMapper.getTagById(tagId);
        tag.setTagName(newTagName);
        if(userId.equals(tag.getUserId())) {
            tagMapper.updateTag(tag);
        }


    }

/*
    查
 */

    @Override  //查询userId下的日记,可根据内容,标签名,更新时间范围查询,支持模糊查询,动态查询,根据更新时间按倒序排序
    public PageBean searchDiaries(Integer page, Integer pageSize,  String content,
                                  Integer userId, String tagName,LocalDateTime start, LocalDateTime end) {
        PageHelper.startPage(page, pageSize);

        List<Diary> diaries = diaryMapper.searchDiaries(content,userId,tagName,  start, end);
        Page<Diary> p = (Page<Diary>) diaries;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());

        return pageBean;
    }



    @Override
    public Tag findOrCreateTag(String tagName, Integer userId) {
        Tag tag = tagMapper.getTagByName(tagName,userId);
        if (tag == null) {//没有该标签则新增
            tag = new Tag();
            tag.setTagName(tagName);
            tag.setUserId(userId);
            tagMapper.insertTag(tag);
        }

        return tag;
    }


}
