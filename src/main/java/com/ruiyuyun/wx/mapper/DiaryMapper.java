package com.ruiyuyun.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruiyuyun.wx.model.Diary;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DiaryMapper extends BaseMapper<Diary> {
    //    @Select("SELECT * FROM diaries ")
    public List<Diary> searchDiaries(
                                      @Param("content") String content,
                                      @Param("userId")Integer userId,
                                      @Param("tagName")String tagName,
                                      @Param("start") LocalDateTime start,
                                      @Param("end")LocalDateTime end);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO diaries ( user_id,content, created_time,update_time ) VALUES ( #{userId},#{content}, #{createdTime}, #{updateTime} )")
    void insertDiary(Diary diary);

    @Select("SELECT * FROM diaries WHERE id = #{diaryId}")
    Diary selectById(Integer diaryId);


    @Update("UPDATE diaries SET  content = #{content}, update_time = #{updateTime} WHERE id = #{id} and user_id = #{userId}")
    void updateDiary(Diary diary);


/*    @Delete("DELETE FROM diaries WHERE id = #{diaryId}")
    void deleteDiary(Integer diaryId);*/
}
