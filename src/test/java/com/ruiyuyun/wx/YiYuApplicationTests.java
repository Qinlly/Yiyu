package com.ruiyuyun.wx;

import com.ruiyuyun.wx.model.Diary;
import com.ruiyuyun.wx.model.PageBean;
import com.ruiyuyun.wx.model.User;
import com.ruiyuyun.wx.service.DiaryService;
import com.ruiyuyun.wx.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class YiYuApplicationTests {

    final UserService userService;
    final DiaryService diaryService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testGenJwt() { //测试jwt令牌生成
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", "1");
        claims.put("name", "tom");

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "itheima")//签名算法
                .setClaims(claims)  //自定义内容(载荷)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))//1h有效期
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void testParseJwt() { //测试jwt令牌解析
        Claims claims = Jwts.parser()
                .setSigningKey("itheima")//签名的秘钥
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOiIxIiwiZXhwIjoxNzIyNTA5MTE1fQ.iUA4AXpZBUfpedVsN-kMmtLoWVdDGY8EOKWj4BhbUJc")
                //测试时需要根据前文测试生成的jwt进行测试
                .getBody();
        System.out.println(claims);

    }

    @Test
    public void testUserSelect() { //测试用户查询功能
        User user = userService.getByPhoneNumber("15763630684");
        System.out.println(user);

    }
    @Test
    public void testUserRegister() { //测试用户注册功能
        userService.register("12345678901","111111");
        System.out.println("请对比数据库");

    }
    @Test
    public void testUserLogin() { //测试用户登录功能

        User u=new User();

        u.setPhoneNumber("15763630684");
        u.setPassword("1234567");
        User user=userService.login(u);


        System.out.println(user);

    }

    @Test
    public void testDiaryInsert() {//测试创建日记功能
        Diary diary=diaryService.insertDiary(3, "测试日记", Collections.singletonList("test"));
        System.out.println(diary);
    }

    @Test
    public void testDiaryDelete() {//测试删除日记功能
       diaryService.deleteDiary(5,1); //根据日记id删除单个日记
        System.out.println("请对比数据库");


        diaryService.deleteDiaryByTag("gdf",1);
        System.out.println("请对比数据库");


    }

    @Test
    public void testDiaryUpdate() {//测试更新日记功能
        diaryService.updateDiary(10,"asdwq", Collections.singletonList("dggdfg"),1);
        System.out.println("请对比数据库");


    }
    @Test
    public void testTagUpdate() {//测试更新标签功能
        diaryService.updateTag(1,"测试标签更新",1);
        System.out.println("请对比数据库");

    }
    @Test
    public void testSelectDiary() {//测试日记查询功能
        PageBean p =diaryService.searchDiaries(1,3,null,null,null,null,null);
        System.out.println(p);

    }





}
