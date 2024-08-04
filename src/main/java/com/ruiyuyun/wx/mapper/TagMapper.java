package com.ruiyuyun.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruiyuyun.wx.model.Tag;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    @Select("SELECT * FROM tags WHERE tag_name = #{tagName} and user_id=#{userId}")
    public Tag getTagByName(@Param("tagName") String tagName,@Param("userId") Integer userId);

    @Select("SELECT * FROM tags WHERE id = #{id}")
    public Tag getTagById(Integer id);

 @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO tags(tag_name,user_id) VALUES(#{tagName},#{userId})")
    public void insertTag(Tag tag);

 @Update("UPDATE tags SET tag_name = #{tagName} WHERE id = #{id}")
    public void updateTag(Tag tag);

}
