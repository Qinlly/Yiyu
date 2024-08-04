package com.ruiyuyun.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruiyuyun.wx.model.DiaryTag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiaryTagMapper extends BaseMapper<DiaryTag> {


    @Select("SELECT * FROM diary_tags WHERE diary_id = #{diaryId}")
    List<DiaryTag> selectDiaryTagByDiaryId(DiaryTag diaryTag);

    @Select("SELECT * FROM diary_tags WHERE tag_id = #{tagId}")
    List<DiaryTag> selectDiaryTagByTagId(DiaryTag diaryTag);

    @Select("SELECT * FROM diary_tags WHERE diary_id = #{diaryId} AND tag_id = #{tagId}")
    DiaryTag selectDiaryTag(DiaryTag diaryTag);

    @Insert("INSERT INTO diary_tags (diary_id, tag_id) VALUES (#{diaryId}, #{tagId})")
    void insertDiaryTag(DiaryTag diaryTag);

    @Update("UPDATE diary_tags SET  tag_id = #{tagId} WHERE diary_id = #{diaryId} ")
    void updateDiaryTag(DiaryTag diaryTag);
    @Delete("DELETE FROM diary_tags WHERE diary_id = #{diaryId}")
    void deleteDiaryTagByDiaryId(DiaryTag diaryTag);


    @Delete("DELETE FROM diary_tags WHERE tag_id = #{tagId}")
    void deleteDiaryTagByTagId(DiaryTag diaryTag);
}
