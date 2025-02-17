package com.ruiyuyun.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;


@ServletComponentScan
@SpringBootApplication
@MapperScan("com.ruiyuyun.wx.mapper")
public class YiYuApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiYuApplication.class, args);
        System.out.println("YiYu Application is running!");
    }


}
