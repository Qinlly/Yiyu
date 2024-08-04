package com.ruiyuyun.wx.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectRequest {


    String content;
    String tagName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime start;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime end;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer pageSize = 10;

}
