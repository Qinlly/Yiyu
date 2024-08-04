package com.ruiyuyun.wx.util;

import com.ruiyuyun.wx.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
public class JwtUtils {
    private static final String signKey = "itheima"; //签名密钥
    private static final Long expire = 1000 * 60 * 60 * 24L; //过期时间，单位：秒

    //生成jwt令牌

        public static String getToken(Map<String, Object> claims) {
            String jwt = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + expire))
                    .signWith(SignatureAlgorithm.HS256, signKey)
                    .compact();
            return jwt;
        }

    // 验证Token
    public boolean verify(String token) {
        try {
            Jwts.parser().setSigningKey(signKey).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            // 令牌无效或已过期
            return false;
        }
    }
    //校验解析jwt令牌
        public static User decodeUser(String jwt) {
            Claims claims = Jwts.parser()
                    .setSigningKey(signKey)
                    .parseClaimsJws(jwt)
                    .getBody();
            User user = new User();
            user.setId(Integer.parseInt(claims.get("userId").toString()));
            user.setPhoneNumber(claims.get("phoneNumber").toString());

            return user;
        }
    }
