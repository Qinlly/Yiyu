package com.ruiyuyun.wx.controller;

import com.ruiyuyun.wx.model.Diary;
import com.ruiyuyun.wx.model.PageBean;
import com.ruiyuyun.wx.request.InsertRequest;
import com.ruiyuyun.wx.request.SelectRequest;
import com.ruiyuyun.wx.request.UpdateDiaryRequest;
import com.ruiyuyun.wx.request.UpdateTagRequest;
import com.ruiyuyun.wx.response.Result;
import com.ruiyuyun.wx.service.DiaryService;
import com.ruiyuyun.wx.util.JwtUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/diary")
public class DiaryController {

    final DiaryService diaryService;

    @GetMapping("/select")  //查询功能,可实现分页查询,模糊查询等等
    public Result selectDiary(SelectRequest r,
            /*@RequestParam(defaultValue="1")Integer page,
                              @RequestParam(defaultValue="10")Integer pageSize,
                              @RequestParam(required = false)String content,*//*Integer userId,*//*
                              @RequestParam(required = false)String tagName,
                              @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDateTime start,
                              @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDateTime end,*/
                              ServletRequest servletRequest) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        Integer userId= JwtUtils.decodeUser(token).getId();  //只能查询自己登陆用户下的日记信息


        PageBean pageBean = diaryService.searchDiaries(r.getPage(),r.getPageSize(),r.getContent(), userId,r.getTagName(),r.getStart(),r.getEnd());

        return Result.success(pageBean);
    }

    @PostMapping("/insert") //根据用户ID添加日记
    public Result insertDiary(ServletRequest servletRequest, InsertRequest i
            /*@RequestParam String content, @RequestParam List<String> tagNames*/) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        Integer userId= JwtUtils.decodeUser(token).getId(); //只能在登录用户下添加日记

        Diary diary = diaryService.insertDiary(userId, i.getContent(), i.getTagNames());
        return Result.success(diary);
    }

    @PutMapping("/updateDiary") //根据日记ID更新日记
    public Result updateDiary(UpdateDiaryRequest u,
            /*@RequestParam Integer diaryId, @RequestParam String content, @RequestParam List<String> newTagNames,*/
                             ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        Integer userId= JwtUtils.decodeUser(token).getId();

        diaryService.updateDiary(u.getDiaryId(), u.getContent(),u.getNewTagNames(),userId);
        return Result.success();
    }

    @PutMapping("/updateTag")  //根据标签名修改所有日记对应的标签名
    public Result updateTag(UpdateTagRequest u,
            /*@RequestParam Integer tagId, @RequestParam String newTagName,*/
                            ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        Integer userId= JwtUtils.decodeUser(token).getId();

        diaryService.updateTag(u.getTagId(),u.getNewTagName(),userId);
        return Result.success();
    }

    @DeleteMapping("/deleteById") //根据日记id删除该日记,并删除该日记与对应标签的关联
    public Result deleteDiaryById(Integer diaryId,ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        Integer userId= JwtUtils.decodeUser(token).getId();

        diaryService.deleteDiary(diaryId,userId);
        return Result.success();
    }

    @DeleteMapping("/deleteByTag")  //删除该标签下的所有日记

    public Result deleteDiaryByTag(String tagName,ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        Integer userId= JwtUtils.decodeUser(token).getId();

        diaryService.deleteDiaryByTag(tagName,userId);
        return Result.success();
    }

}
